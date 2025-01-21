// Generated from DDMFormValuesQuery.g by ANTLR 4.13.2

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class DDMFormValuesQueryLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, IDENTIFIER=7, STRING_LITERAL=8, 
		SLASH=9, DOUBLE_SLASH=10, STAR=11, AT=12, OPEN_BRACKET=13, CLOSE_BRACKET=14, 
		EQUALS=15, WS=16;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "IDENTIFIER", "STRING_LITERAL", 
			"SLASH", "DOUBLE_SLASH", "STAR", "AT", "OPEN_BRACKET", "CLOSE_BRACKET", 
			"EQUALS", "ESC_SEQ", "OCTAL_ESC", "UNICODE_ESC", "HEX_DIGIT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'or'", "'and'", "'type'", "'value'", "'('", "')'", null, null, 
			"'/'", "'//'", "'*'", "'@'", "'['", "']'", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, "IDENTIFIER", "STRING_LITERAL", 
			"SLASH", "DOUBLE_SLASH", "STAR", "AT", "OPEN_BRACKET", "CLOSE_BRACKET", 
			"EQUALS", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public DDMFormValuesQueryLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DDMFormValuesQuery.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0010}\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0005\u0006B\b\u0006\n\u0006"+
		"\f\u0006E\t\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0005\u0007J\b\u0007"+
		"\n\u0007\f\u0007M\t\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0003\u000fd\b\u000f\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0003\u0010o\b\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0000\u0000\u0014\u0001\u0001"+
		"\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f"+
		"\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f"+
		"\u001f\u0000!\u0000#\u0000%\u0000\'\u0010\u0001\u0000\u0006\u0002\u0000"+
		"AZaz\u0003\u000009AZaz\u0002\u0000\'\'\\\\\b\u0000\"\"\'\'\\\\bbffnnr"+
		"rtt\u0003\u000009AFaf\u0003\u0000\t\n\r\r  \u007f\u0000\u0001\u0001\u0000"+
		"\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000"+
		"\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000"+
		"\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000"+
		"\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000"+
		"\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000"+
		"\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000"+
		"\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000"+
		"\u0000\'\u0001\u0000\u0000\u0000\u0001)\u0001\u0000\u0000\u0000\u0003"+
		",\u0001\u0000\u0000\u0000\u00050\u0001\u0000\u0000\u0000\u00075\u0001"+
		"\u0000\u0000\u0000\t;\u0001\u0000\u0000\u0000\u000b=\u0001\u0000\u0000"+
		"\u0000\r?\u0001\u0000\u0000\u0000\u000fF\u0001\u0000\u0000\u0000\u0011"+
		"P\u0001\u0000\u0000\u0000\u0013R\u0001\u0000\u0000\u0000\u0015U\u0001"+
		"\u0000\u0000\u0000\u0017W\u0001\u0000\u0000\u0000\u0019Y\u0001\u0000\u0000"+
		"\u0000\u001b[\u0001\u0000\u0000\u0000\u001d]\u0001\u0000\u0000\u0000\u001f"+
		"c\u0001\u0000\u0000\u0000!n\u0001\u0000\u0000\u0000#p\u0001\u0000\u0000"+
		"\u0000%w\u0001\u0000\u0000\u0000\'y\u0001\u0000\u0000\u0000)*\u0005o\u0000"+
		"\u0000*+\u0005r\u0000\u0000+\u0002\u0001\u0000\u0000\u0000,-\u0005a\u0000"+
		"\u0000-.\u0005n\u0000\u0000./\u0005d\u0000\u0000/\u0004\u0001\u0000\u0000"+
		"\u000001\u0005t\u0000\u000012\u0005y\u0000\u000023\u0005p\u0000\u0000"+
		"34\u0005e\u0000\u00004\u0006\u0001\u0000\u0000\u000056\u0005v\u0000\u0000"+
		"67\u0005a\u0000\u000078\u0005l\u0000\u000089\u0005u\u0000\u00009:\u0005"+
		"e\u0000\u0000:\b\u0001\u0000\u0000\u0000;<\u0005(\u0000\u0000<\n\u0001"+
		"\u0000\u0000\u0000=>\u0005)\u0000\u0000>\f\u0001\u0000\u0000\u0000?C\u0007"+
		"\u0000\u0000\u0000@B\u0007\u0001\u0000\u0000A@\u0001\u0000\u0000\u0000"+
		"BE\u0001\u0000\u0000\u0000CA\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000"+
		"\u0000D\u000e\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000FK\u0005"+
		"\'\u0000\u0000GJ\u0003\u001f\u000f\u0000HJ\b\u0002\u0000\u0000IG\u0001"+
		"\u0000\u0000\u0000IH\u0001\u0000\u0000\u0000JM\u0001\u0000\u0000\u0000"+
		"KI\u0001\u0000\u0000\u0000KL\u0001\u0000\u0000\u0000LN\u0001\u0000\u0000"+
		"\u0000MK\u0001\u0000\u0000\u0000NO\u0005\'\u0000\u0000O\u0010\u0001\u0000"+
		"\u0000\u0000PQ\u0005/\u0000\u0000Q\u0012\u0001\u0000\u0000\u0000RS\u0005"+
		"/\u0000\u0000ST\u0005/\u0000\u0000T\u0014\u0001\u0000\u0000\u0000UV\u0005"+
		"*\u0000\u0000V\u0016\u0001\u0000\u0000\u0000WX\u0005@\u0000\u0000X\u0018"+
		"\u0001\u0000\u0000\u0000YZ\u0005[\u0000\u0000Z\u001a\u0001\u0000\u0000"+
		"\u0000[\\\u0005]\u0000\u0000\\\u001c\u0001\u0000\u0000\u0000]^\u0005="+
		"\u0000\u0000^\u001e\u0001\u0000\u0000\u0000_`\u0005\\\u0000\u0000`d\u0007"+
		"\u0003\u0000\u0000ad\u0003#\u0011\u0000bd\u0003!\u0010\u0000c_\u0001\u0000"+
		"\u0000\u0000ca\u0001\u0000\u0000\u0000cb\u0001\u0000\u0000\u0000d \u0001"+
		"\u0000\u0000\u0000ef\u0005\\\u0000\u0000fg\u000203\u0000gh\u000207\u0000"+
		"ho\u000207\u0000ij\u0005\\\u0000\u0000jk\u000207\u0000ko\u000207\u0000"+
		"lm\u0005\\\u0000\u0000mo\u000207\u0000ne\u0001\u0000\u0000\u0000ni\u0001"+
		"\u0000\u0000\u0000nl\u0001\u0000\u0000\u0000o\"\u0001\u0000\u0000\u0000"+
		"pq\u0005\\\u0000\u0000qr\u0005u\u0000\u0000rs\u0003%\u0012\u0000st\u0003"+
		"%\u0012\u0000tu\u0003%\u0012\u0000uv\u0003%\u0012\u0000v$\u0001\u0000"+
		"\u0000\u0000wx\u0007\u0004\u0000\u0000x&\u0001\u0000\u0000\u0000yz\u0007"+
		"\u0005\u0000\u0000z{\u0001\u0000\u0000\u0000{|\u0006\u0013\u0000\u0000"+
		"|(\u0001\u0000\u0000\u0000\u0006\u0000CIKcn\u0001\u0000\u0001\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
