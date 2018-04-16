/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mandelag.extractor;

import java.util.function.Consumer;

/**
 *
 * @author Keenan Gebze (@mandelag) */
public class ReaderExtractor {
    
    private String startString;
    private String stopString;
    private Consumer<String> callback;
    private int cursor = 0;
    private boolean extractMode = false;
    private StringBuffer buffer = new StringBuffer();
    
    public ReaderExtractor(String start, String stop, Consumer<String> callback) {
        this.startString = start;
        this.stopString = stop;
        this.callback = callback;
    }

    public void listen(char a) { 
        if(!extractMode) {
            if (a == startString.charAt(cursor)) {
                cursor++;
                if (cursor == startString.length()) {
                    extractMode = true;
                    cursor = 0;
                }
            } else {
                cursor = 0;
            }
        } else {
            buffer.append(a);
            if (a == stopString.charAt(cursor)) {
                cursor++;
                if (cursor == stopString.length()) {
                    extractMode = false;
                    cursor = 0;
                    callback.accept(buffer.substring(0, buffer.length()-stopString.length()));
                    buffer.setLength(0);
                }
            }else {
                cursor = 0;
            }
        }
    }
}
