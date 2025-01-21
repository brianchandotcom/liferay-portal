// $ANTLR 3.5.3 LDAPFilter.g 2025-01-21 13:45:17

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
	public static final int EOF=-1;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int ASCII_LATIN1=4;
	public static final int ASCII_LETTER=5;
	public static final int COLON=6;
	public static final int DASH=7;
	public static final int DIGIT=8;
	public static final int DOT=9;
	public static final int UTF=10;

		@Override
		public void reportError(RecognitionException e) {
			throw new RuntimeException(e);
		}


	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public LDAPFilterLexer() {} 
	public LDAPFilterLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public LDAPFilterLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "LDAPFilter.g"; }

	// $ANTLR start "T__11"
	public final void mT__11() throws RecognitionException {
		try {
			int _type = T__11;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:31:7: ( '!' )
			// LDAPFilter.g:31:9: '!'
			{
			match('!'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__11"

	// $ANTLR start "T__12"
	public final void mT__12() throws RecognitionException {
		try {
			int _type = T__12;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:32:7: ( '&' )
			// LDAPFilter.g:32:9: '&'
			{
			match('&'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__12"

	// $ANTLR start "T__13"
	public final void mT__13() throws RecognitionException {
		try {
			int _type = T__13;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:33:7: ( '(' )
			// LDAPFilter.g:33:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__13"

	// $ANTLR start "T__14"
	public final void mT__14() throws RecognitionException {
		try {
			int _type = T__14;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:34:7: ( ')' )
			// LDAPFilter.g:34:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__14"

	// $ANTLR start "T__15"
	public final void mT__15() throws RecognitionException {
		try {
			int _type = T__15;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:35:7: ( '*' )
			// LDAPFilter.g:35:9: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__15"

	// $ANTLR start "T__16"
	public final void mT__16() throws RecognitionException {
		try {
			int _type = T__16;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:36:7: ( ':=' )
			// LDAPFilter.g:36:9: ':='
			{
			match(":="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__16"

	// $ANTLR start "T__17"
	public final void mT__17() throws RecognitionException {
		try {
			int _type = T__17;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:37:7: ( ':dn' )
			// LDAPFilter.g:37:9: ':dn'
			{
			match(":dn"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__17"

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:38:7: ( ';' )
			// LDAPFilter.g:38:9: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:39:7: ( '<=' )
			// LDAPFilter.g:39:9: '<='
			{
			match("<="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:40:7: ( '=' )
			// LDAPFilter.g:40:9: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:41:7: ( '>=' )
			// LDAPFilter.g:41:9: '>='
			{
			match(">="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:42:7: ( '|' )
			// LDAPFilter.g:42:9: '|'
			{
			match('|'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:43:7: ( '~=' )
			// LDAPFilter.g:43:9: '~='
			{
			match("~="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__23"

	// $ANTLR start "ASCII_LETTER"
	public final void mASCII_LETTER() throws RecognitionException {
		try {
			// LDAPFilter.g:153:2: ( 'a' .. 'z' | 'A' .. 'Z' )
			// LDAPFilter.g:
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ASCII_LETTER"

	// $ANTLR start "DIGIT"
	public final void mDIGIT() throws RecognitionException {
		try {
			// LDAPFilter.g:156:15: ( '0' .. '9' )
			// LDAPFilter.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIGIT"

	// $ANTLR start "DASH"
	public final void mDASH() throws RecognitionException {
		try {
			// LDAPFilter.g:157:14: ( '-' )
			// LDAPFilter.g:157:16: '-'
			{
			match('-'); 
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DASH"

	// $ANTLR start "DOT"
	public final void mDOT() throws RecognitionException {
		try {
			int _type = DOT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:159:4: ( '.' )
			// LDAPFilter.g:159:6: '.'
			{
			match('.'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DOT"

	// $ANTLR start "COLON"
	public final void mCOLON() throws RecognitionException {
		try {
			int _type = COLON;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:160:6: ( ':' )
			// LDAPFilter.g:160:8: ':'
			{
			match(':'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COLON"

	// $ANTLR start "UTF"
	public final void mUTF() throws RecognitionException {
		try {
			int _type = UTF;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:161:4: ( '\\u0080' .. '\\ufffe' )
			// LDAPFilter.g:
			{
			if ( (input.LA(1) >= '\u0080' && input.LA(1) <= '\uFFFE') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "UTF"

	// $ANTLR start "ASCII_LATIN1"
	public final void mASCII_LATIN1() throws RecognitionException {
		try {
			int _type = ASCII_LATIN1;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// LDAPFilter.g:162:13: ( '\\u0000' .. '\\u007f' )
			// LDAPFilter.g:
			{
			if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\u007F') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ASCII_LATIN1"

	@Override
	public void mTokens() throws RecognitionException {
		// LDAPFilter.g:1:8: ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | DOT | COLON | UTF | ASCII_LATIN1 )
		int alt1=17;
		int LA1_0 = input.LA(1);
		if ( (LA1_0=='!') ) {
			alt1=1;
		}
		else if ( (LA1_0=='&') ) {
			alt1=2;
		}
		else if ( (LA1_0=='(') ) {
			alt1=3;
		}
		else if ( (LA1_0==')') ) {
			alt1=4;
		}
		else if ( (LA1_0=='*') ) {
			alt1=5;
		}
		else if ( (LA1_0==':') ) {
			switch ( input.LA(2) ) {
			case '=':
				{
				alt1=6;
				}
				break;
			case 'd':
				{
				alt1=7;
				}
				break;
			default:
				alt1=15;
			}
		}
		else if ( (LA1_0==';') ) {
			alt1=8;
		}
		else if ( (LA1_0=='<') ) {
			int LA1_8 = input.LA(2);
			if ( (LA1_8=='=') ) {
				alt1=9;
			}

			else {
				alt1=17;
			}

		}
		else if ( (LA1_0=='=') ) {
			alt1=10;
		}
		else if ( (LA1_0=='>') ) {
			int LA1_10 = input.LA(2);
			if ( (LA1_10=='=') ) {
				alt1=11;
			}

			else {
				alt1=17;
			}

		}
		else if ( (LA1_0=='|') ) {
			alt1=12;
		}
		else if ( (LA1_0=='~') ) {
			int LA1_12 = input.LA(2);
			if ( (LA1_12=='=') ) {
				alt1=13;
			}

			else {
				alt1=17;
			}

		}
		else if ( (LA1_0=='.') ) {
			alt1=14;
		}
		else if ( ((LA1_0 >= '\u0080' && LA1_0 <= '\uFFFE')) ) {
			alt1=16;
		}
		else if ( ((LA1_0 >= '\u0000' && LA1_0 <= ' ')||(LA1_0 >= '\"' && LA1_0 <= '%')||LA1_0=='\''||(LA1_0 >= '+' && LA1_0 <= '-')||(LA1_0 >= '/' && LA1_0 <= '9')||(LA1_0 >= '?' && LA1_0 <= '{')||LA1_0=='}'||LA1_0=='\u007F') ) {
			alt1=17;
		}

		else {
			NoViableAltException nvae =
				new NoViableAltException("", 1, 0, input);
			throw nvae;
		}

		switch (alt1) {
			case 1 :
				// LDAPFilter.g:1:10: T__11
				{
				mT__11(); 

				}
				break;
			case 2 :
				// LDAPFilter.g:1:16: T__12
				{
				mT__12(); 

				}
				break;
			case 3 :
				// LDAPFilter.g:1:22: T__13
				{
				mT__13(); 

				}
				break;
			case 4 :
				// LDAPFilter.g:1:28: T__14
				{
				mT__14(); 

				}
				break;
			case 5 :
				// LDAPFilter.g:1:34: T__15
				{
				mT__15(); 

				}
				break;
			case 6 :
				// LDAPFilter.g:1:40: T__16
				{
				mT__16(); 

				}
				break;
			case 7 :
				// LDAPFilter.g:1:46: T__17
				{
				mT__17(); 

				}
				break;
			case 8 :
				// LDAPFilter.g:1:52: T__18
				{
				mT__18(); 

				}
				break;
			case 9 :
				// LDAPFilter.g:1:58: T__19
				{
				mT__19(); 

				}
				break;
			case 10 :
				// LDAPFilter.g:1:64: T__20
				{
				mT__20(); 

				}
				break;
			case 11 :
				// LDAPFilter.g:1:70: T__21
				{
				mT__21(); 

				}
				break;
			case 12 :
				// LDAPFilter.g:1:76: T__22
				{
				mT__22(); 

				}
				break;
			case 13 :
				// LDAPFilter.g:1:82: T__23
				{
				mT__23(); 

				}
				break;
			case 14 :
				// LDAPFilter.g:1:88: DOT
				{
				mDOT(); 

				}
				break;
			case 15 :
				// LDAPFilter.g:1:92: COLON
				{
				mCOLON(); 

				}
				break;
			case 16 :
				// LDAPFilter.g:1:98: UTF
				{
				mUTF(); 

				}
				break;
			case 17 :
				// LDAPFilter.g:1:102: ASCII_LATIN1
				{
				mASCII_LATIN1(); 

				}
				break;

		}
	}



}
