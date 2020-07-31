// Generated from DDMExpression.g4 by ANTLR 4.3

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

package com.liferay.dynamic.data.mapping.expression.internal.parser;

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
public class DDMExpressionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IntegerLiteral=1, FloatingPointLiteral=2, DecimalFloatingPointLiteral=3, 
		AND=4, COMMA=5, DIV=6, EQ=7, FALSE=8, GE=9, GT=10, LBRACKET=11, LE=12, 
		LPAREN=13, LT=14, MINUS=15, MULT=16, NEQ=17, NOT=18, OR=19, PLUS=20, RBRACKET=21, 
		RPAREN=22, STRING=23, TRUE=24, IDENTIFIER=25, WS=26;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'"
	};
	public static final String[] ruleNames = {
		"IntegerLiteral", "FloatingPointLiteral", "DecimalFloatingPointLiteral", 
		"AND", "COMMA", "DIV", "EQ", "FALSE", "GE", "GT", "LBRACKET", "LE", "LPAREN", 
		"LT", "MINUS", "MULT", "NEQ", "NOT", "OR", "PLUS", "RBRACKET", "RPAREN", 
		"STRING", "TRUE", "IDENTIFIER", "WS", "Digits", "Digit", "ExponentIndicator", 
		"ExponentPart", "NameChar", "NameStartChar", "SignedInteger", "Sign"
	};


	public DDMExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DDMExpression.g4"; }

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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\34\u00ef\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\3\2\3\2\3\3\3\3\3\4\3\4\3\4\5\4O\n\4\3\4\5\4R\n"+
		"\4\3\4\3\4\3\4\5\4W\n\4\3\4\3\4\3\4\5\4\\\n\4\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\5\5g\n\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\5\bp\n\b\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t|\n\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3"+
		"\r\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\22"+
		"\5\22\u0094\n\22\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u009c\n\23\3\24\3"+
		"\24\3\24\3\24\3\24\3\24\3\24\5\24\u00a5\n\24\3\25\3\25\3\26\3\26\3\27"+
		"\3\27\3\30\3\30\7\30\u00af\n\30\f\30\16\30\u00b2\13\30\3\30\3\30\3\30"+
		"\7\30\u00b7\n\30\f\30\16\30\u00ba\13\30\3\30\5\30\u00bd\n\30\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u00c7\n\31\3\32\3\32\7\32\u00cb\n"+
		"\32\f\32\16\32\u00ce\13\32\3\33\6\33\u00d1\n\33\r\33\16\33\u00d2\3\33"+
		"\3\33\3\34\6\34\u00d8\n\34\r\34\16\34\u00d9\3\35\3\35\3\36\3\36\3\37\3"+
		"\37\3\37\3 \3 \5 \u00e5\n \3!\3!\3\"\5\"\u00ea\n\"\3\"\3\"\3#\3#\2\2$"+
		"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20"+
		"\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\29\2;\2="+
		"\2?\2A\2C\2E\2\3\2\n\3\2$$\3\2))\5\2\13\f\16\17\"\"\3\2\62;\4\2GGgg\5"+
		"\2\62;\u0302\u0371\u2041\u2042\20\2C\\aac|\u00c2\u00d8\u00da\u00f8\u00fa"+
		"\u0301\u0372\u037f\u0381\u2001\u200e\u200f\u2072\u2191\u2c02\u2ff1\u3003"+
		"\ud801\uf902\ufdd1\ufdf2\uffff\4\2--//\u00fe\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2"+
		"\2\2\65\3\2\2\2\3G\3\2\2\2\5I\3\2\2\2\7[\3\2\2\2\tf\3\2\2\2\13h\3\2\2"+
		"\2\rj\3\2\2\2\17o\3\2\2\2\21{\3\2\2\2\23}\3\2\2\2\25\u0080\3\2\2\2\27"+
		"\u0082\3\2\2\2\31\u0084\3\2\2\2\33\u0087\3\2\2\2\35\u0089\3\2\2\2\37\u008b"+
		"\3\2\2\2!\u008d\3\2\2\2#\u0093\3\2\2\2%\u009b\3\2\2\2\'\u00a4\3\2\2\2"+
		")\u00a6\3\2\2\2+\u00a8\3\2\2\2-\u00aa\3\2\2\2/\u00bc\3\2\2\2\61\u00c6"+
		"\3\2\2\2\63\u00c8\3\2\2\2\65\u00d0\3\2\2\2\67\u00d7\3\2\2\29\u00db\3\2"+
		"\2\2;\u00dd\3\2\2\2=\u00df\3\2\2\2?\u00e4\3\2\2\2A\u00e6\3\2\2\2C\u00e9"+
		"\3\2\2\2E\u00ed\3\2\2\2GH\5\67\34\2H\4\3\2\2\2IJ\5\7\4\2J\6\3\2\2\2KL"+
		"\5\67\34\2LN\7\60\2\2MO\5\67\34\2NM\3\2\2\2NO\3\2\2\2OQ\3\2\2\2PR\5=\37"+
		"\2QP\3\2\2\2QR\3\2\2\2R\\\3\2\2\2ST\7\60\2\2TV\5\67\34\2UW\5=\37\2VU\3"+
		"\2\2\2VW\3\2\2\2W\\\3\2\2\2XY\5\67\34\2YZ\5=\37\2Z\\\3\2\2\2[K\3\2\2\2"+
		"[S\3\2\2\2[X\3\2\2\2\\\b\3\2\2\2]^\7(\2\2^g\7(\2\2_g\7(\2\2`a\7c\2\2a"+
		"b\7p\2\2bg\7f\2\2cd\7C\2\2de\7P\2\2eg\7F\2\2f]\3\2\2\2f_\3\2\2\2f`\3\2"+
		"\2\2fc\3\2\2\2g\n\3\2\2\2hi\7.\2\2i\f\3\2\2\2jk\7\61\2\2k\16\3\2\2\2l"+
		"m\7?\2\2mp\7?\2\2np\7?\2\2ol\3\2\2\2on\3\2\2\2p\20\3\2\2\2qr\7h\2\2rs"+
		"\7c\2\2st\7n\2\2tu\7u\2\2u|\7g\2\2vw\7H\2\2wx\7C\2\2xy\7N\2\2yz\7U\2\2"+
		"z|\7G\2\2{q\3\2\2\2{v\3\2\2\2|\22\3\2\2\2}~\7@\2\2~\177\7?\2\2\177\24"+
		"\3\2\2\2\u0080\u0081\7@\2\2\u0081\26\3\2\2\2\u0082\u0083\7]\2\2\u0083"+
		"\30\3\2\2\2\u0084\u0085\7>\2\2\u0085\u0086\7?\2\2\u0086\32\3\2\2\2\u0087"+
		"\u0088\7*\2\2\u0088\34\3\2\2\2\u0089\u008a\7>\2\2\u008a\36\3\2\2\2\u008b"+
		"\u008c\7/\2\2\u008c \3\2\2\2\u008d\u008e\7,\2\2\u008e\"\3\2\2\2\u008f"+
		"\u0090\7#\2\2\u0090\u0094\7?\2\2\u0091\u0092\7>\2\2\u0092\u0094\7@\2\2"+
		"\u0093\u008f\3\2\2\2\u0093\u0091\3\2\2\2\u0094$\3\2\2\2\u0095\u0096\7"+
		"p\2\2\u0096\u0097\7q\2\2\u0097\u009c\7v\2\2\u0098\u0099\7P\2\2\u0099\u009a"+
		"\7Q\2\2\u009a\u009c\7V\2\2\u009b\u0095\3\2\2\2\u009b\u0098\3\2\2\2\u009c"+
		"&\3\2\2\2\u009d\u009e\7~\2\2\u009e\u00a5\7~\2\2\u009f\u00a5\7~\2\2\u00a0"+
		"\u00a1\7q\2\2\u00a1\u00a5\7t\2\2\u00a2\u00a3\7Q\2\2\u00a3\u00a5\7T\2\2"+
		"\u00a4\u009d\3\2\2\2\u00a4\u009f\3\2\2\2\u00a4\u00a0\3\2\2\2\u00a4\u00a2"+
		"\3\2\2\2\u00a5(\3\2\2\2\u00a6\u00a7\7-\2\2\u00a7*\3\2\2\2\u00a8\u00a9"+
		"\7_\2\2\u00a9,\3\2\2\2\u00aa\u00ab\7+\2\2\u00ab.\3\2\2\2\u00ac\u00b0\7"+
		"$\2\2\u00ad\u00af\n\2\2\2\u00ae\u00ad\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0"+
		"\u00ae\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b3\3\2\2\2\u00b2\u00b0\3\2"+
		"\2\2\u00b3\u00bd\7$\2\2\u00b4\u00b8\7)\2\2\u00b5\u00b7\n\3\2\2\u00b6\u00b5"+
		"\3\2\2\2\u00b7\u00ba\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9"+
		"\u00bb\3\2\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00bd\7)\2\2\u00bc\u00ac\3\2"+
		"\2\2\u00bc\u00b4\3\2\2\2\u00bd\60\3\2\2\2\u00be\u00bf\7v\2\2\u00bf\u00c0"+
		"\7t\2\2\u00c0\u00c1\7w\2\2\u00c1\u00c7\7g\2\2\u00c2\u00c3\7V\2\2\u00c3"+
		"\u00c4\7T\2\2\u00c4\u00c5\7W\2\2\u00c5\u00c7\7G\2\2\u00c6\u00be\3\2\2"+
		"\2\u00c6\u00c2\3\2\2\2\u00c7\62\3\2\2\2\u00c8\u00cc\5A!\2\u00c9\u00cb"+
		"\5? \2\u00ca\u00c9\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc"+
		"\u00cd\3\2\2\2\u00cd\64\3\2\2\2\u00ce\u00cc\3\2\2\2\u00cf\u00d1\t\4\2"+
		"\2\u00d0\u00cf\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d3"+
		"\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d5\b\33\2\2\u00d5\66\3\2\2\2\u00d6"+
		"\u00d8\59\35\2\u00d7\u00d6\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00d7\3\2"+
		"\2\2\u00d9\u00da\3\2\2\2\u00da8\3\2\2\2\u00db\u00dc\t\5\2\2\u00dc:\3\2"+
		"\2\2\u00dd\u00de\t\6\2\2\u00de<\3\2\2\2\u00df\u00e0\5;\36\2\u00e0\u00e1"+
		"\5C\"\2\u00e1>\3\2\2\2\u00e2\u00e5\5A!\2\u00e3\u00e5\t\7\2\2\u00e4\u00e2"+
		"\3\2\2\2\u00e4\u00e3\3\2\2\2\u00e5@\3\2\2\2\u00e6\u00e7\t\b\2\2\u00e7"+
		"B\3\2\2\2\u00e8\u00ea\5E#\2\u00e9\u00e8\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea"+
		"\u00eb\3\2\2\2\u00eb\u00ec\5\67\34\2\u00ecD\3\2\2\2\u00ed\u00ee\t\t\2"+
		"\2\u00eeF\3\2\2\2\26\2NQV[fo{\u0093\u009b\u00a4\u00b0\u00b8\u00bc\u00c6"+
		"\u00cc\u00d2\u00d9\u00e4\u00e9\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}