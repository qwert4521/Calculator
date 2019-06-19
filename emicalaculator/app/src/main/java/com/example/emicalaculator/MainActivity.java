package com.example.emicalaculator;

import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
import android.content.Context;

import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    Button emiCalcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText P = (EditText)findViewById(R.id.principal);
        final EditText I = (EditText)findViewById(R.id.interest);
        final EditText Y = (EditText)findViewById(R.id.years);
        final EditText TI = (EditText)findViewById(R.id.interest_total);
        final EditText result = (EditText)findViewById(R.id.emi) ;

        emiCalcBtn = (Button) findViewById(R.id.btn_calculate);

        emiCalcBtn.setOnClickListener(new View.OnClickListener() {

            @Override
                public void onClick(View v) {

                closeKeyboard();

                //make the inputs useable
                String st1 = P.getText().toString();
                String st2 = I.getText().toString();
                String st3 = Y.getText().toString();

                /*Error*/

                if (TextUtils.isEmpty(st1)) {
                    P.setError("Enter Principal Amount ($)");
                    P.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(st2)) {
                    I.setError("Enter Interest Rate (%)");
                    I.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(st3)) {
                    Y.setError("Enter Term (Years)");
                    Y.requestFocus();
                    return;
                }

                float p = Float.parseFloat(st1);
                float i = Float.parseFloat(st2);
                float y = Float.parseFloat(st3);


                /* Outputs from helper methods for EMI formula*/
                float Principal = func1(p);
                float Rate = func2(i);
                float Months = func3(y);
                float Dvdnt = func4( Rate, Months);
                float FD = func5(Principal, Rate, Dvdnt);
                float D = func6(Dvdnt);
                float emi = func7(FD, D);
                float TA = func8(emi, Months);
                float ti = func9(TA, Principal) + p;



                /*OUTPUT adjusted for tax*/
                result.setText(String.valueOf(   Math.round((emi*1.06625)*100)/100 + 0.00  ));
                TI.setText(String.valueOf(   Math.round((ti*1.066252)*100)/100  +0.00 ));

                }

            });



    }


    /*Helper Methods*/

    public  float func1(float p) {
        return (float) (p);
    }

    public  float func2(float i) {
        return (float) (i/12/100);
    }

    public  float func3(float y) {
        return (float) (y * 12);
    }

    public  float func4(float Rate, float Months) {
        return (float) (Math.pow(1+Rate, Months));
    }

    public  float func5(float Principal, float Rate, float Dvdnt) {
        return (float) (Principal * Rate * Dvdnt);
    }

    public  float func6(float Dvdnt) {
        return (float) (Dvdnt-1);
    }

    public  float func7(float FD, Float D) {
        return (float) (FD/D);
    }

    public  float func8(float emi, Float Months) {
        return (float) (emi*Months);
    }

    public  float func9(float TA, float Principal) {
        return (float) (TA - Principal);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }}


}
