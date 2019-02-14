package com.sjsu.calculator;

import java.util.List;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private List<Button> nums = new ArrayList<>();
    private List<Button> operator = new ArrayList<>();
    StringBuilder operand1 = new StringBuilder("0");
    StringBuilder currentOperand = operand1;
    StringBuilder operand2 = new StringBuilder("");
    String oper = "";
    boolean isEqualPressed = false;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        Button btn = null;
        for(int i=0; i<=9; i++) {
            int identifier = this.getResources().getIdentifier("button"+i, "id", this.getPackageName());
            btn = (Button) findViewById(identifier);
            nums.add(btn);
        }
        btn = (Button) findViewById(R.id.buttonDot);
        nums.add(btn);


        btn = (Button) findViewById(R.id.buttonAdd);
        operator.add(btn);
        btn = (Button) findViewById(R.id.buttonSub);
        operator.add(btn);
        btn = (Button) findViewById(R.id.buttonMul);
        operator.add(btn);
        btn = (Button) findViewById(R.id.buttonDiv);
        operator.add(btn);
        btn = (Button) findViewById(R.id.buttonEq);
        operator.add(btn);
        btn = (Button) findViewById(R.id.buttonClear);
        operator.add(btn);

        textView = (TextView) findViewById(R.id.Exp);

        setOperandOnClickListener();
        setOperatorOnClickListener();
    }

    private void setOperandOnClickListener() {
        for(Button btn : nums){
            btn.setOnClickListener( view -> {
                Button clickedButton = (Button) view;
                String text = clickedButton.getText().toString();
                text = ".".equals(text)? currentOperand.toString().contains(".")? "": text : text;
                if("0".equals(currentOperand.toString())){
                    currentOperand.deleteCharAt(0);
                }
                currentOperand.append(text);
                //notify outputScreen
                updateOutputScreen(currentOperand.toString());
                isEqualPressed = false;
            });
        }
    }

    private void setOperatorOnClickListener() {
        for(Button btn : operator){
            btn.setOnClickListener( view -> {
                Button clickedButton = (Button) view;
                String text = clickedButton.getText().toString();
                switch(text){
                    case "AC":
                        reset();
                        //notify output screen
                        updateOutputScreen(currentOperand.toString());
                        break;
                    case "+":
                    case "-":
                    case "/":
                    case "*":
                        if(!operand2.toString().isEmpty() && !isEqualPressed ){
                            calculate();
                        }
                        oper = text;
                        currentOperand = operand2 = new StringBuilder();
                        isEqualPressed = false;
                        break;
                    case "=":
                        if(!operand1.toString().isEmpty() && !operand2.toString().isEmpty() && !oper.toString().isEmpty()){
                            calculate();
                        }
                        isEqualPressed = true;
                        break;
                }

            });
        }
    }

    private void calculate() {
        Double a = Double.parseDouble(operand1.toString());
        Double b = Double.parseDouble(operand2.toString());
        Double result = null;
        switch(oper){
            case "+":
                result = a+b;
                break;
            case "-":
                result = a-b;
                break;
            case "*":
                result = a*b;
                break;
            case "/":
                result = b==0?a:a/b;
                break;
        }
        currentOperand = operand1 = new StringBuilder(result.toString());
        //notify output screen
        updateOutputScreen(currentOperand.toString());
    }

    private void updateOutputScreen(String text){
        textView.setText(text);
    }
    private void reset(){
        oper= "";
        operand1 = new StringBuilder("0");
        operand2 = new StringBuilder("");
        currentOperand = operand1;
        isEqualPressed = false;
    }
}
