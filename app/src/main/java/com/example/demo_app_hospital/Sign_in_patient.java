package com.example.demo_app_hospital;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_in_patient extends AppCompatActivity {

    TextInputEditText editemail, editpassword;
    Button buttonReg;

    FirebaseAuth mAth;
    ProgressBar progressBar;

    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currFirebaseUser = mAth.getCurrentUser();
        if(currFirebaseUser != null)
        {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_patient);

        editemail = findViewById(R.id.email);
        editpassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_button);
        mAth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.prograssBar);
        textView = findViewById(R.id.RegisterNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_patient.class);
                startActivity(intent);
                finish();
            }
        });
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                progressBar.setVisibility(View.VISIBLE);
                email = String.valueOf(editemail.getText());
                password = String.valueOf(editpassword.getText());

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(Sign_in_patient.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(Sign_in_patient.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful())
                                {

                                    Toast.makeText(Sign_in_patient.this, "Accont created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login_patient.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(Sign_in_patient.this, "Autho falter", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
