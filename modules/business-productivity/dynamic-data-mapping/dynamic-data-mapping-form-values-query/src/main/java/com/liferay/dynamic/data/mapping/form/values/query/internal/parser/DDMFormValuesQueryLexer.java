// Generated from DDMFormValuesQuery.g by ANTLR 4.3

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

package com.liferay.dynamic.data.mapping.form.values.query.internal.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

/**
 * @author Brian Wing Shun Chan
 */
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DDMFormValuesQueryLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__5=1, T__4=2, T__3=3, T__2=4, T__1=5, T__0=6, IDENTIFIER=7, STRING_LITERAL=8, 
		SLASH=9, DOUBLE_SLASH=10, STAR=11, AT=12, OPEN_BRACKET=13, CLOSE_BRACKET=14, 
		EQUALS=15, WS=16;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'"
	};
	public static final String[] ruleNames = {
		"T__5", "T__4", "T__3", "T__2", "T__1", "T__0", "IDENTIFIER", "STRING_LITERAL", 
		"SLASH", "DOUBLE_SLASH", "STAR", "AT", "OPEN_BRACKET", "CLOSE_BRACKET", 
		"EQUALS", "ESC_SEQ", "OCTAL_ESC", "UNICODE_ESC", "HEX_DIGIT", "WS"
	};


	public DDMFormValuesQueryLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DDMFormValuesQuery.g"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\22\177\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\3\4\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\7\bD\n\b\f\b"+
		"\16\bG\13\b\3\t\3\t\3\t\7\tL\n\t\f\t\16\tO\13\t\3\t\3\t\3\n\3\n\3\13\3"+
		"\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21"+
		"\3\21\5\21f\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22q\n"+
		"\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\2"+
		"\2\26\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\2#\2%\2\'\2)\22\3\2\b\4\2C\\c|\5\2\62;C\\c|\4\2))^^\n\2$$)"+
		")^^ddhhppttvv\5\2\62;CHch\5\2\13\f\17\17\"\"\u0081\2\3\3\2\2\2\2\5\3\2"+
		"\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"+
		"\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2)\3\2\2\2\3+\3\2\2\2\5-\3\2\2\2\7\61\3"+
		"\2\2\2\t\66\3\2\2\2\139\3\2\2\2\r;\3\2\2\2\17A\3\2\2\2\21H\3\2\2\2\23"+
		"R\3\2\2\2\25T\3\2\2\2\27W\3\2\2\2\31Y\3\2\2\2\33[\3\2\2\2\35]\3\2\2\2"+
		"\37_\3\2\2\2!e\3\2\2\2#p\3\2\2\2%r\3\2\2\2\'y\3\2\2\2){\3\2\2\2+,\7+\2"+
		"\2,\4\3\2\2\2-.\7c\2\2./\7p\2\2/\60\7f\2\2\60\6\3\2\2\2\61\62\7v\2\2\62"+
		"\63\7{\2\2\63\64\7r\2\2\64\65\7g\2\2\65\b\3\2\2\2\66\67\7q\2\2\678\7t"+
		"\2\28\n\3\2\2\29:\7*\2\2:\f\3\2\2\2;<\7x\2\2<=\7c\2\2=>\7n\2\2>?\7w\2"+
		"\2?@\7g\2\2@\16\3\2\2\2AE\t\2\2\2BD\t\3\2\2CB\3\2\2\2DG\3\2\2\2EC\3\2"+
		"\2\2EF\3\2\2\2F\20\3\2\2\2GE\3\2\2\2HM\7)\2\2IL\5!\21\2JL\n\4\2\2KI\3"+
		"\2\2\2KJ\3\2\2\2LO\3\2\2\2MK\3\2\2\2MN\3\2\2\2NP\3\2\2\2OM\3\2\2\2PQ\7"+
		")\2\2Q\22\3\2\2\2RS\7\61\2\2S\24\3\2\2\2TU\7\61\2\2UV\7\61\2\2V\26\3\2"+
		"\2\2WX\7,\2\2X\30\3\2\2\2YZ\7B\2\2Z\32\3\2\2\2[\\\7]\2\2\\\34\3\2\2\2"+
		"]^\7_\2\2^\36\3\2\2\2_`\7?\2\2` \3\2\2\2ab\7^\2\2bf\t\5\2\2cf\5%\23\2"+
		"df\5#\22\2ea\3\2\2\2ec\3\2\2\2ed\3\2\2\2f\"\3\2\2\2gh\7^\2\2hi\4\62\65"+
		"\2ij\4\629\2jq\4\629\2kl\7^\2\2lm\4\629\2mq\4\629\2no\7^\2\2oq\4\629\2"+
		"pg\3\2\2\2pk\3\2\2\2pn\3\2\2\2q$\3\2\2\2rs\7^\2\2st\7w\2\2tu\5\'\24\2"+
		"uv\5\'\24\2vw\5\'\24\2wx\5\'\24\2x&\3\2\2\2yz\t\6\2\2z(\3\2\2\2{|\t\7"+
		"\2\2|}\3\2\2\2}~\b\25\2\2~*\3\2\2\2\b\2EKMep\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}