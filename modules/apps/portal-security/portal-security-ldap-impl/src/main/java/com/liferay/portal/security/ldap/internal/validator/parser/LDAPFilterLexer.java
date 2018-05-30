// $ANTLR 3.0.1 LDAPFilter.g 2018-02-19 15:27:52

/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.ldap.internal.validator.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class LDAPFilterLexer extends Lexer {
    public static final int UTF=6;
    public static final int DASH=10;
    public static final int T21=21;
    public static final int T20=20;
    public static final int T23=23;
    public static final int ASCII_LATIN1=4;
    public static final int T22=22;
    public static final int EOF=-1;
    public static final int Tokens=24;
    public static final int DOT=5;
    public static final int COLON=7;
    public static final int DIGIT=9;
    public static final int ASCII_LETTER=8;
    public static final int T12=12;
    public static final int T11=11;
    public static final int T14=14;
    public static final int T13=13;
    public static final int T16=16;
    public static final int T15=15;
    public static final int T18=18;
    public static final int T17=17;
    public static final int T19=19;
    
	@Override
	public void reportError(RecognitionException e) {
		throw new RuntimeException(e);
	}

    public LDAPFilterLexer() {;} 
    public LDAPFilterLexer(CharStream input) {
	super(input);
    }
    public String getGrammarFileName() { return "LDAPFilter.g"; }

    // $ANTLR start T11
    public final void mT11() throws RecognitionException {
	try {
	    int _type = T11;
	    // LDAPFilter.g:30:5: ( '(' )
	    // LDAPFilter.g:30:7: '('
	    {
	    match('('); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T11

    // $ANTLR start T12
    public final void mT12() throws RecognitionException {
	try {
	    int _type = T12;
	    // LDAPFilter.g:31:5: ( ')' )
	    // LDAPFilter.g:31:7: ')'
	    {
	    match(')'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T12

    // $ANTLR start T13
    public final void mT13() throws RecognitionException {
	try {
	    int _type = T13;
	    // LDAPFilter.g:32:5: ( '&' )
	    // LDAPFilter.g:32:7: '&'
	    {
	    match('&'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T13

    // $ANTLR start T14
    public final void mT14() throws RecognitionException {
	try {
	    int _type = T14;
	    // LDAPFilter.g:33:5: ( '|' )
	    // LDAPFilter.g:33:7: '|'
	    {
	    match('|'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T14

    // $ANTLR start T15
    public final void mT15() throws RecognitionException {
	try {
	    int _type = T15;
	    // LDAPFilter.g:34:5: ( '!' )
	    // LDAPFilter.g:34:7: '!'
	    {
	    match('!'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T15

    // $ANTLR start T16
    public final void mT16() throws RecognitionException {
	try {
	    int _type = T16;
	    // LDAPFilter.g:35:5: ( '=' )
	    // LDAPFilter.g:35:7: '='
	    {
	    match('='); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T16

    // $ANTLR start T17
    public final void mT17() throws RecognitionException {
	try {
	    int _type = T17;
	    // LDAPFilter.g:36:5: ( '~=' )
	    // LDAPFilter.g:36:7: '~='
	    {
	    match("~="); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T17

    // $ANTLR start T18
    public final void mT18() throws RecognitionException {
	try {
	    int _type = T18;
	    // LDAPFilter.g:37:5: ( '>=' )
	    // LDAPFilter.g:37:7: '>='
	    {
	    match(">="); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T18

    // $ANTLR start T19
    public final void mT19() throws RecognitionException {
	try {
	    int _type = T19;
	    // LDAPFilter.g:38:5: ( '<=' )
	    // LDAPFilter.g:38:7: '<='
	    {
	    match("<="); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T19

    // $ANTLR start T20
    public final void mT20() throws RecognitionException {
	try {
	    int _type = T20;
	    // LDAPFilter.g:39:5: ( ':dn' )
	    // LDAPFilter.g:39:7: ':dn'
	    {
	    match(":dn"); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T20

    // $ANTLR start T21
    public final void mT21() throws RecognitionException {
	try {
	    int _type = T21;
	    // LDAPFilter.g:40:5: ( ':=' )
	    // LDAPFilter.g:40:7: ':='
	    {
	    match(":="); 


	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T21

    // $ANTLR start T22
    public final void mT22() throws RecognitionException {
	try {
	    int _type = T22;
	    // LDAPFilter.g:41:5: ( '*' )
	    // LDAPFilter.g:41:7: '*'
	    {
	    match('*'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T22

    // $ANTLR start T23
    public final void mT23() throws RecognitionException {
	try {
	    int _type = T23;
	    // LDAPFilter.g:42:5: ( ';' )
	    // LDAPFilter.g:42:7: ';'
	    {
	    match(';'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end T23

    // $ANTLR start ASCII_LETTER
    public final void mASCII_LETTER() throws RecognitionException {
	try {
	    // LDAPFilter.g:153:2: ( 'a' .. 'z' | 'A' .. 'Z' )
	    // LDAPFilter.g:
	    {
	    if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
		input.consume();

	    }
	    else {
		MismatchedSetException mse =
		    new MismatchedSetException(null,input);
		recover(mse);	 throw mse;
	    }


	    }

	}
	finally {
	}
    }
    // $ANTLR end ASCII_LETTER

    // $ANTLR start DIGIT
    public final void mDIGIT() throws RecognitionException {
	try {
	    // LDAPFilter.g:156:15: ( '0' .. '9' )
	    // LDAPFilter.g:156:18: '0' .. '9'
	    {
	    matchRange('0','9'); 

	    }

	}
	finally {
	}
    }
    // $ANTLR end DIGIT

    // $ANTLR start DASH
    public final void mDASH() throws RecognitionException {
	try {
	    // LDAPFilter.g:157:14: ( '-' )
	    // LDAPFilter.g:157:16: '-'
	    {
	    match('-'); 

	    }

	}
	finally {
	}
    }
    // $ANTLR end DASH

    // $ANTLR start DOT
    public final void mDOT() throws RecognitionException {
	try {
	    int _type = DOT;
	    // LDAPFilter.g:159:4: ( '.' )
	    // LDAPFilter.g:159:6: '.'
	    {
	    match('.'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end DOT

    // $ANTLR start COLON
    public final void mCOLON() throws RecognitionException {
	try {
	    int _type = COLON;
	    // LDAPFilter.g:160:6: ( ':' )
	    // LDAPFilter.g:160:8: ':'
	    {
	    match(':'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end COLON

    // $ANTLR start UTF
    public final void mUTF() throws RecognitionException {
	try {
	    int _type = UTF;
	    // LDAPFilter.g:161:4: ( '\\u0080' .. '\\ufffe' )
	    // LDAPFilter.g:161:6: '\\u0080' .. '\\ufffe'
	    {
	    matchRange('\u0080','\uFFFE'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end UTF

    // $ANTLR start ASCII_LATIN1
    public final void mASCII_LATIN1() throws RecognitionException {
	try {
	    int _type = ASCII_LATIN1;
	    // LDAPFilter.g:162:13: ( '\\u0000' .. '\\u007f' )
	    // LDAPFilter.g:162:15: '\\u0000' .. '\\u007f'
	    {
	    matchRange('\u0000','\u007F'); 

	    }

	    this.type = _type;
	}
	finally {
	}
    }
    // $ANTLR end ASCII_LATIN1

    public void mTokens() throws RecognitionException {
	// LDAPFilter.g:1:8: ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | DOT | COLON | UTF | ASCII_LATIN1 )
	int alt1=17;
	int LA1_0 = input.LA(1);

	if ( (LA1_0=='(') ) {
	    alt1=1;
	}
	else if ( (LA1_0==')') ) {
	    alt1=2;
	}
	else if ( (LA1_0=='&') ) {
	    alt1=3;
	}
	else if ( (LA1_0=='|') ) {
	    alt1=4;
	}
	else if ( (LA1_0=='!') ) {
	    alt1=5;
	}
	else if ( (LA1_0=='=') ) {
	    alt1=6;
	}
	else if ( (LA1_0=='~') ) {
	    int LA1_7 = input.LA(2);

	    if ( (LA1_7=='=') ) {
		alt1=7;
	    }
	    else {
		alt1=17;}
	}
	else if ( (LA1_0=='>') ) {
	    int LA1_8 = input.LA(2);

	    if ( (LA1_8=='=') ) {
		alt1=8;
	    }
	    else {
		alt1=17;}
	}
	else if ( (LA1_0=='<') ) {
	    int LA1_9 = input.LA(2);

	    if ( (LA1_9=='=') ) {
		alt1=9;
	    }
	    else {
		alt1=17;}
	}
	else if ( (LA1_0==':') ) {
	    switch ( input.LA(2) ) {
	    case 'd':
		{
		alt1=10;
		}
		break;
	    case '=':
		{
		alt1=11;
		}
		break;
	    default:
		alt1=15;}

	}
	else if ( (LA1_0=='*') ) {
	    alt1=12;
	}
	else if ( (LA1_0==';') ) {
	    alt1=13;
	}
	else if ( (LA1_0=='.') ) {
	    alt1=14;
	}
	else if ( ((LA1_0>='\u0080' && LA1_0<='\uFFFE')) ) {
	    alt1=16;
	}
	else if ( ((LA1_0>='\u0000' && LA1_0<=' ')||(LA1_0>='\"' && LA1_0<='%')||LA1_0=='\''||(LA1_0>='+' && LA1_0<='-')||(LA1_0>='/' && LA1_0<='9')||(LA1_0>='?' && LA1_0<='{')||LA1_0=='}'||LA1_0=='\u007F') ) {
            alt1=17;
        }
        else {
            NoViableAltException nvae =
                new NoViableAltException("1:1: Tokens : ( T11 | T12 | T13 | T14 | T15 | T16 | T17 | T18 | T19 | T20 | T21 | T22 | T23 | DOT | COLON | UTF | ASCII_LATIN1 );", 1, 0, input);

            throw nvae;
        }
        switch (alt1) {
            case 1 :
                // LDAPFilter.g:1:10: T11
                {
                mT11(); 

                }
                break;
            case 2 :
                // LDAPFilter.g:1:14: T12
                {
                mT12(); 

                }
                break;
            case 3 :
                // LDAPFilter.g:1:18: T13
                {
                mT13(); 

                }
                break;
            case 4 :
                // LDAPFilter.g:1:22: T14
                {
                mT14(); 

                }
                break;
            case 5 :
                // LDAPFilter.g:1:26: T15
                {
                mT15(); 

                }
                break;
            case 6 :
                // LDAPFilter.g:1:30: T16
                {
                mT16(); 

                }
                break;
            case 7 :
                // LDAPFilter.g:1:34: T17
                {
                mT17(); 

                }
                break;
            case 8 :
                // LDAPFilter.g:1:38: T18
                {
                mT18(); 

                }
                break;
            case 9 :
                // LDAPFilter.g:1:42: T19
                {
                mT19(); 

                }
                break;
            case 10 :
                // LDAPFilter.g:1:46: T20
                {
                mT20(); 

                }
                break;
            case 11 :
                // LDAPFilter.g:1:50: T21
                {
                mT21(); 

                }
                break;
            case 12 :
                // LDAPFilter.g:1:54: T22
                {
                mT22(); 

                }
                break;
            case 13 :
                // LDAPFilter.g:1:58: T23
                {
                mT23(); 

                }
                break;
            case 14 :
                // LDAPFilter.g:1:62: DOT
                {
                mDOT(); 

                }
                break;
            case 15 :
                // LDAPFilter.g:1:66: COLON
                {
                mCOLON(); 

                }
                break;
            case 16 :
                // LDAPFilter.g:1:72: UTF
                {
                mUTF(); 

                }
                break;
            case 17 :
                // LDAPFilter.g:1:76: ASCII_LATIN1
                {
                mASCII_LATIN1(); 

                }
                break;

        }

    }


 

}
