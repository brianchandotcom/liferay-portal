// Generated from DDMFormValuesQuery.g by ANTLR 4.13.2

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.values.query.internal.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class DDMFormValuesQueryParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, IDENTIFIER=7, STRING_LITERAL=8, 
		SLASH=9, DOUBLE_SLASH=10, STAR=11, AT=12, OPEN_BRACKET=13, CLOSE_BRACKET=14, 
		EQUALS=15, WS=16;
	public static final int
		RULE_path = 0, RULE_selectorExpression = 1, RULE_stepType = 2, RULE_fieldSelectorExpression = 3, 
		RULE_fieldSelector = 4, RULE_predicateExpression = 5, RULE_predicateOrExpression = 6, 
		RULE_predicateAndExpression = 7, RULE_predicateEqualityExpression = 8, 
		RULE_attribute = 9, RULE_attributeType = 10, RULE_attributeValue = 11, 
		RULE_localeExpression = 12;
	private static String[] makeRuleNames() {
		return new String[] {
			"path", "selectorExpression", "stepType", "fieldSelectorExpression", 
			"fieldSelector", "predicateExpression", "predicateOrExpression", "predicateAndExpression", 
			"predicateEqualityExpression", "attribute", "attributeType", "attributeValue", 
			"localeExpression"
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

	@Override
	public String getGrammarFileName() { return "DDMFormValuesQuery.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DDMFormValuesQueryParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PathContext extends ParserRuleContext {
		public List<SelectorExpressionContext> selectorExpression() {
			return getRuleContexts(SelectorExpressionContext.class);
		}
		public SelectorExpressionContext selectorExpression(int i) {
			return getRuleContext(SelectorExpressionContext.class,i);
		}
		public PathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitPath(this);
		}
	}

	public final PathContext path() throws RecognitionException {
		PathContext _localctx = new PathContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_path);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SLASH || _la==DOUBLE_SLASH) {
				{
				{
				setState(26);
				selectorExpression();
				}
				}
				setState(31);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectorExpressionContext extends ParserRuleContext {
		public StepTypeContext stepType() {
			return getRuleContext(StepTypeContext.class,0);
		}
		public FieldSelectorExpressionContext fieldSelectorExpression() {
			return getRuleContext(FieldSelectorExpressionContext.class,0);
		}
		public SelectorExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectorExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterSelectorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitSelectorExpression(this);
		}
	}

	public final SelectorExpressionContext selectorExpression() throws RecognitionException {
		SelectorExpressionContext _localctx = new SelectorExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_selectorExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			stepType();
			setState(33);
			fieldSelectorExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StepTypeContext extends ParserRuleContext {
		public TerminalNode SLASH() { return getToken(DDMFormValuesQueryParser.SLASH, 0); }
		public TerminalNode DOUBLE_SLASH() { return getToken(DDMFormValuesQueryParser.DOUBLE_SLASH, 0); }
		public StepTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stepType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterStepType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitStepType(this);
		}
	}

	public final StepTypeContext stepType() throws RecognitionException {
		StepTypeContext _localctx = new StepTypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stepType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			_la = _input.LA(1);
			if ( !(_la==SLASH || _la==DOUBLE_SLASH) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FieldSelectorExpressionContext extends ParserRuleContext {
		public FieldSelectorContext fieldSelector() {
			return getRuleContext(FieldSelectorContext.class,0);
		}
		public PredicateExpressionContext predicateExpression() {
			return getRuleContext(PredicateExpressionContext.class,0);
		}
		public FieldSelectorExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldSelectorExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterFieldSelectorExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitFieldSelectorExpression(this);
		}
	}

	public final FieldSelectorExpressionContext fieldSelectorExpression() throws RecognitionException {
		FieldSelectorExpressionContext _localctx = new FieldSelectorExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_fieldSelectorExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			fieldSelector();
			setState(39);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OPEN_BRACKET) {
				{
				setState(38);
				predicateExpression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FieldSelectorContext extends ParserRuleContext {
		public TerminalNode STAR() { return getToken(DDMFormValuesQueryParser.STAR, 0); }
		public TerminalNode IDENTIFIER() { return getToken(DDMFormValuesQueryParser.IDENTIFIER, 0); }
		public FieldSelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fieldSelector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterFieldSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitFieldSelector(this);
		}
	}

	public final FieldSelectorContext fieldSelector() throws RecognitionException {
		FieldSelectorContext _localctx = new FieldSelectorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_fieldSelector);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			_la = _input.LA(1);
			if ( !(_la==IDENTIFIER || _la==STAR) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PredicateExpressionContext extends ParserRuleContext {
		public TerminalNode OPEN_BRACKET() { return getToken(DDMFormValuesQueryParser.OPEN_BRACKET, 0); }
		public PredicateOrExpressionContext predicateOrExpression() {
			return getRuleContext(PredicateOrExpressionContext.class,0);
		}
		public TerminalNode CLOSE_BRACKET() { return getToken(DDMFormValuesQueryParser.CLOSE_BRACKET, 0); }
		public PredicateExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicateExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterPredicateExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitPredicateExpression(this);
		}
	}

	public final PredicateExpressionContext predicateExpression() throws RecognitionException {
		PredicateExpressionContext _localctx = new PredicateExpressionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_predicateExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			match(OPEN_BRACKET);
			setState(44);
			predicateOrExpression();
			setState(45);
			match(CLOSE_BRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PredicateOrExpressionContext extends ParserRuleContext {
		public List<PredicateAndExpressionContext> predicateAndExpression() {
			return getRuleContexts(PredicateAndExpressionContext.class);
		}
		public PredicateAndExpressionContext predicateAndExpression(int i) {
			return getRuleContext(PredicateAndExpressionContext.class,i);
		}
		public PredicateOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicateOrExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterPredicateOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitPredicateOrExpression(this);
		}
	}

	public final PredicateOrExpressionContext predicateOrExpression() throws RecognitionException {
		PredicateOrExpressionContext _localctx = new PredicateOrExpressionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_predicateOrExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			predicateAndExpression();
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(48);
				match(T__0);
				setState(49);
				predicateAndExpression();
				}
				}
				setState(54);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PredicateAndExpressionContext extends ParserRuleContext {
		public List<PredicateEqualityExpressionContext> predicateEqualityExpression() {
			return getRuleContexts(PredicateEqualityExpressionContext.class);
		}
		public PredicateEqualityExpressionContext predicateEqualityExpression(int i) {
			return getRuleContext(PredicateEqualityExpressionContext.class,i);
		}
		public PredicateAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicateAndExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterPredicateAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitPredicateAndExpression(this);
		}
	}

	public final PredicateAndExpressionContext predicateAndExpression() throws RecognitionException {
		PredicateAndExpressionContext _localctx = new PredicateAndExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_predicateAndExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			predicateEqualityExpression();
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(56);
				match(T__1);
				setState(57);
				predicateEqualityExpression();
				}
				}
				setState(62);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PredicateEqualityExpressionContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(DDMFormValuesQueryParser.AT, 0); }
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public TerminalNode EQUALS() { return getToken(DDMFormValuesQueryParser.EQUALS, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(DDMFormValuesQueryParser.STRING_LITERAL, 0); }
		public PredicateEqualityExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicateEqualityExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterPredicateEqualityExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitPredicateEqualityExpression(this);
		}
	}

	public final PredicateEqualityExpressionContext predicateEqualityExpression() throws RecognitionException {
		PredicateEqualityExpressionContext _localctx = new PredicateEqualityExpressionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_predicateEqualityExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			match(AT);
			setState(64);
			attribute();
			setState(65);
			match(EQUALS);
			setState(66);
			match(STRING_LITERAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeContext extends ParserRuleContext {
		public AttributeTypeContext attributeType() {
			return getRuleContext(AttributeTypeContext.class,0);
		}
		public AttributeValueContext attributeValue() {
			return getRuleContext(AttributeValueContext.class,0);
		}
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitAttribute(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_attribute);
		try {
			setState(70);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(68);
				attributeType();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				attributeValue();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeTypeContext extends ParserRuleContext {
		public AttributeTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterAttributeType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitAttributeType(this);
		}
	}

	public final AttributeTypeContext attributeType() throws RecognitionException {
		AttributeTypeContext _localctx = new AttributeTypeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_attributeType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeValueContext extends ParserRuleContext {
		public LocaleExpressionContext localeExpression() {
			return getRuleContext(LocaleExpressionContext.class,0);
		}
		public AttributeValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterAttributeValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitAttributeValue(this);
		}
	}

	public final AttributeValueContext attributeValue() throws RecognitionException {
		AttributeValueContext _localctx = new AttributeValueContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_attributeValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(T__3);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(75);
				localeExpression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LocaleExpressionContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(DDMFormValuesQueryParser.STRING_LITERAL, 0); }
		public LocaleExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localeExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).enterLocaleExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMFormValuesQueryListener ) ((DDMFormValuesQueryListener)listener).exitLocaleExpression(this);
		}
	}

	public final LocaleExpressionContext localeExpression() throws RecognitionException {
		LocaleExpressionContext _localctx = new LocaleExpressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_localeExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(T__4);
			setState(79);
			match(STRING_LITERAL);
			setState(80);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0010S\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0001\u0000\u0005\u0000\u001c\b\u0000\n\u0000\f\u0000\u001f"+
		"\t\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0003\u0003(\b\u0003\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0005\u00063\b\u0006\n\u0006\f\u00066\t\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0005\u0007;\b\u0007\n\u0007\f\u0007>\t\u0007\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0003\tG\b\t\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0003\u000bM\b\u000b\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0000\u0000\r\u0000\u0002\u0004\u0006\b\n\f"+
		"\u000e\u0010\u0012\u0014\u0016\u0018\u0000\u0002\u0001\u0000\t\n\u0002"+
		"\u0000\u0007\u0007\u000b\u000bK\u0000\u001d\u0001\u0000\u0000\u0000\u0002"+
		" \u0001\u0000\u0000\u0000\u0004#\u0001\u0000\u0000\u0000\u0006%\u0001"+
		"\u0000\u0000\u0000\b)\u0001\u0000\u0000\u0000\n+\u0001\u0000\u0000\u0000"+
		"\f/\u0001\u0000\u0000\u0000\u000e7\u0001\u0000\u0000\u0000\u0010?\u0001"+
		"\u0000\u0000\u0000\u0012F\u0001\u0000\u0000\u0000\u0014H\u0001\u0000\u0000"+
		"\u0000\u0016J\u0001\u0000\u0000\u0000\u0018N\u0001\u0000\u0000\u0000\u001a"+
		"\u001c\u0003\u0002\u0001\u0000\u001b\u001a\u0001\u0000\u0000\u0000\u001c"+
		"\u001f\u0001\u0000\u0000\u0000\u001d\u001b\u0001\u0000\u0000\u0000\u001d"+
		"\u001e\u0001\u0000\u0000\u0000\u001e\u0001\u0001\u0000\u0000\u0000\u001f"+
		"\u001d\u0001\u0000\u0000\u0000 !\u0003\u0004\u0002\u0000!\"\u0003\u0006"+
		"\u0003\u0000\"\u0003\u0001\u0000\u0000\u0000#$\u0007\u0000\u0000\u0000"+
		"$\u0005\u0001\u0000\u0000\u0000%\'\u0003\b\u0004\u0000&(\u0003\n\u0005"+
		"\u0000\'&\u0001\u0000\u0000\u0000\'(\u0001\u0000\u0000\u0000(\u0007\u0001"+
		"\u0000\u0000\u0000)*\u0007\u0001\u0000\u0000*\t\u0001\u0000\u0000\u0000"+
		"+,\u0005\r\u0000\u0000,-\u0003\f\u0006\u0000-.\u0005\u000e\u0000\u0000"+
		".\u000b\u0001\u0000\u0000\u0000/4\u0003\u000e\u0007\u000001\u0005\u0001"+
		"\u0000\u000013\u0003\u000e\u0007\u000020\u0001\u0000\u0000\u000036\u0001"+
		"\u0000\u0000\u000042\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u0000"+
		"5\r\u0001\u0000\u0000\u000064\u0001\u0000\u0000\u00007<\u0003\u0010\b"+
		"\u000089\u0005\u0002\u0000\u00009;\u0003\u0010\b\u0000:8\u0001\u0000\u0000"+
		"\u0000;>\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000<=\u0001\u0000"+
		"\u0000\u0000=\u000f\u0001\u0000\u0000\u0000><\u0001\u0000\u0000\u0000"+
		"?@\u0005\f\u0000\u0000@A\u0003\u0012\t\u0000AB\u0005\u000f\u0000\u0000"+
		"BC\u0005\b\u0000\u0000C\u0011\u0001\u0000\u0000\u0000DG\u0003\u0014\n"+
		"\u0000EG\u0003\u0016\u000b\u0000FD\u0001\u0000\u0000\u0000FE\u0001\u0000"+
		"\u0000\u0000G\u0013\u0001\u0000\u0000\u0000HI\u0005\u0003\u0000\u0000"+
		"I\u0015\u0001\u0000\u0000\u0000JL\u0005\u0004\u0000\u0000KM\u0003\u0018"+
		"\f\u0000LK\u0001\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000M\u0017\u0001"+
		"\u0000\u0000\u0000NO\u0005\u0005\u0000\u0000OP\u0005\b\u0000\u0000PQ\u0005"+
		"\u0006\u0000\u0000Q\u0019\u0001\u0000\u0000\u0000\u0006\u001d\'4<FL";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
