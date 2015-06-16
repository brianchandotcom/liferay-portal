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

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * @author Brian Wing Shun Chan
 */
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DDMFormValuesQueryParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__5=1, T__4=2, T__3=3, T__2=4, T__1=5, T__0=6, IDENTIFIER=7, STRING_LITERAL=8, 
		SLASH=9, DOUBLE_SLASH=10, STAR=11, AT=12, OPEN_BRACKET=13, CLOSE_BRACKET=14, 
		EQUALS=15, WS=16;
	public static final String[] tokenNames = {
		"<INVALID>", "')'", "'and'", "'type'", "'or'", "'('", "'value'", "IDENTIFIER", 
		"STRING_LITERAL", "'/'", "'//'", "'*'", "'@'", "'['", "']'", "'='", "WS"
	};
	public static final int
		RULE_path = 0, RULE_selectorExpression = 1, RULE_stepType = 2, RULE_fieldSelectorExpression = 3, 
		RULE_fieldSelector = 4, RULE_predicateExpression = 5, RULE_predicateOrExpression = 6, 
		RULE_predicateAndExpression = 7, RULE_predicateEqualityExpression = 8, 
		RULE_attribute = 9, RULE_attributeType = 10, RULE_attributeValue = 11, 
		RULE_localeExpression = 12;
	public static final String[] ruleNames = {
		"path", "selectorExpression", "stepType", "fieldSelectorExpression", "fieldSelector", 
		"predicateExpression", "predicateOrExpression", "predicateAndExpression", 
		"predicateEqualityExpression", "attribute", "attributeType", "attributeValue", 
		"localeExpression"
	};

	@Override
	public String getGrammarFileName() { return "DDMFormValuesQuery.g"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

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
				setState(26); selectorExpression();
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
			setState(32); stepType();
			setState(33); fieldSelectorExpression();
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

	public static class StepTypeContext extends ParserRuleContext {
		public TerminalNode DOUBLE_SLASH() { return getToken(DDMFormValuesQueryParser.DOUBLE_SLASH, 0); }
		public TerminalNode SLASH() { return getToken(DDMFormValuesQueryParser.SLASH, 0); }
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
			consume();
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
			setState(37); fieldSelector();
			setState(39);
			_la = _input.LA(1);
			if (_la==OPEN_BRACKET) {
				{
				setState(38); predicateExpression();
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

	public static class FieldSelectorContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(DDMFormValuesQueryParser.IDENTIFIER, 0); }
		public TerminalNode STAR() { return getToken(DDMFormValuesQueryParser.STAR, 0); }
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
			consume();
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

	public static class PredicateExpressionContext extends ParserRuleContext {
		public TerminalNode CLOSE_BRACKET() { return getToken(DDMFormValuesQueryParser.CLOSE_BRACKET, 0); }
		public TerminalNode OPEN_BRACKET() { return getToken(DDMFormValuesQueryParser.OPEN_BRACKET, 0); }
		public PredicateOrExpressionContext predicateOrExpression() {
			return getRuleContext(PredicateOrExpressionContext.class,0);
		}
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
			setState(43); match(OPEN_BRACKET);
			setState(44); predicateOrExpression();
			setState(45); match(CLOSE_BRACKET);
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
			setState(47); predicateAndExpression();
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(48); match(T__2);
				setState(49); predicateAndExpression();
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

	public static class PredicateAndExpressionContext extends ParserRuleContext {
		public PredicateEqualityExpressionContext predicateEqualityExpression(int i) {
			return getRuleContext(PredicateEqualityExpressionContext.class,i);
		}
		public List<PredicateEqualityExpressionContext> predicateEqualityExpression() {
			return getRuleContexts(PredicateEqualityExpressionContext.class);
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
			setState(55); predicateEqualityExpression();
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(56); match(T__4);
				setState(57); predicateEqualityExpression();
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

	public static class PredicateEqualityExpressionContext extends ParserRuleContext {
		public AttributeContext attribute() {
			return getRuleContext(AttributeContext.class,0);
		}
		public TerminalNode EQUALS() { return getToken(DDMFormValuesQueryParser.EQUALS, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(DDMFormValuesQueryParser.STRING_LITERAL, 0); }
		public TerminalNode AT() { return getToken(DDMFormValuesQueryParser.AT, 0); }
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
			setState(63); match(AT);
			setState(64); attribute();
			setState(65); match(EQUALS);
			setState(66); match(STRING_LITERAL);
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
			switch (_input.LA(1)) {
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(68); attributeType();
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(69); attributeValue();
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
			setState(72); match(T__3);
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
			setState(74); match(T__0);
			setState(76);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(75); localeExpression();
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
			setState(78); match(T__1);
			setState(79); match(STRING_LITERAL);
			setState(80); match(T__5);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\22U\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\3\2\7\2\36\n\2\f\2\16\2!\13\2\3\3\3\3\3\3\3\4"+
		"\3\4\3\5\3\5\5\5*\n\5\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\7\b\65\n\b\f"+
		"\b\16\b8\13\b\3\t\3\t\3\t\7\t=\n\t\f\t\16\t@\13\t\3\n\3\n\3\n\3\n\3\n"+
		"\3\13\3\13\5\13I\n\13\3\f\3\f\3\r\3\r\5\rO\n\r\3\16\3\16\3\16\3\16\3\16"+
		"\2\2\17\2\4\6\b\n\f\16\20\22\24\26\30\32\2\4\3\2\13\f\4\2\t\t\r\rM\2\37"+
		"\3\2\2\2\4\"\3\2\2\2\6%\3\2\2\2\b\'\3\2\2\2\n+\3\2\2\2\f-\3\2\2\2\16\61"+
		"\3\2\2\2\209\3\2\2\2\22A\3\2\2\2\24H\3\2\2\2\26J\3\2\2\2\30L\3\2\2\2\32"+
		"P\3\2\2\2\34\36\5\4\3\2\35\34\3\2\2\2\36!\3\2\2\2\37\35\3\2\2\2\37 \3"+
		"\2\2\2 \3\3\2\2\2!\37\3\2\2\2\"#\5\6\4\2#$\5\b\5\2$\5\3\2\2\2%&\t\2\2"+
		"\2&\7\3\2\2\2\')\5\n\6\2(*\5\f\7\2)(\3\2\2\2)*\3\2\2\2*\t\3\2\2\2+,\t"+
		"\3\2\2,\13\3\2\2\2-.\7\17\2\2./\5\16\b\2/\60\7\20\2\2\60\r\3\2\2\2\61"+
		"\66\5\20\t\2\62\63\7\6\2\2\63\65\5\20\t\2\64\62\3\2\2\2\658\3\2\2\2\66"+
		"\64\3\2\2\2\66\67\3\2\2\2\67\17\3\2\2\28\66\3\2\2\29>\5\22\n\2:;\7\4\2"+
		"\2;=\5\22\n\2<:\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3\2\2\2?\21\3\2\2\2@>\3"+
		"\2\2\2AB\7\16\2\2BC\5\24\13\2CD\7\21\2\2DE\7\n\2\2E\23\3\2\2\2FI\5\26"+
		"\f\2GI\5\30\r\2HF\3\2\2\2HG\3\2\2\2I\25\3\2\2\2JK\7\5\2\2K\27\3\2\2\2"+
		"LN\7\b\2\2MO\5\32\16\2NM\3\2\2\2NO\3\2\2\2O\31\3\2\2\2PQ\7\7\2\2QR\7\n"+
		"\2\2RS\7\3\2\2S\33\3\2\2\2\b\37)\66>HN";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}