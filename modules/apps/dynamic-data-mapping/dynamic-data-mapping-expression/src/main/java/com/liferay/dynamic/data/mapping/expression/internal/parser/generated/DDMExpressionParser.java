// Generated from DDMExpression.g4 by ANTLR 4.13.2

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.internal.parser.generated;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class DDMExpressionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IntegerLiteral=1, FloatingPointLiteral=2, DecimalFloatingPointLiteral=3, 
		AND=4, COMMA=5, DIV=6, EQ=7, FALSE=8, GE=9, GT=10, LBRACKET=11, LE=12, 
		LPAREN=13, LT=14, MINUS=15, MULT=16, NEQ=17, NOT=18, OR=19, PLUS=20, RBRACKET=21, 
		RPAREN=22, STRING=23, TRUE=24, IDENTIFIER=25, WS=26;
	public static final int
		RULE_expression = 0, RULE_logicalOrExpression = 1, RULE_logicalAndExpression = 2, 
		RULE_equalityExpression = 3, RULE_comparisonExpression = 4, RULE_booleanUnaryExpression = 5, 
		RULE_booleanOperandExpression = 6, RULE_logicalTerm = 7, RULE_additionOrSubtractionExpression = 8, 
		RULE_multiplicationOrDivisionExpression = 9, RULE_numericUnaryEpression = 10, 
		RULE_numericOperandExpression = 11, RULE_numericTerm = 12, RULE_functionCallExpression = 13, 
		RULE_functionParameters = 14, RULE_functionParameter = 15, RULE_array = 16, 
		RULE_floatingPointArray = 17, RULE_integerArray = 18, RULE_stringArray = 19, 
		RULE_literal = 20;
	private static String[] makeRuleNames() {
		return new String[] {
			"expression", "logicalOrExpression", "logicalAndExpression", "equalityExpression", 
			"comparisonExpression", "booleanUnaryExpression", "booleanOperandExpression", 
			"logicalTerm", "additionOrSubtractionExpression", "multiplicationOrDivisionExpression", 
			"numericUnaryEpression", "numericOperandExpression", "numericTerm", "functionCallExpression", 
			"functionParameters", "functionParameter", "array", "floatingPointArray", 
			"integerArray", "stringArray", "literal"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "','", "'/'", null, null, "'>='", "'>'", 
			"'['", "'<='", "'('", "'<'", "'-'", "'*'", null, null, null, "'+'", "']'", 
			"')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IntegerLiteral", "FloatingPointLiteral", "DecimalFloatingPointLiteral", 
			"AND", "COMMA", "DIV", "EQ", "FALSE", "GE", "GT", "LBRACKET", "LE", "LPAREN", 
			"LT", "MINUS", "MULT", "NEQ", "NOT", "OR", "PLUS", "RBRACKET", "RPAREN", 
			"STRING", "TRUE", "IDENTIFIER", "WS"
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
	public String getGrammarFileName() { return "DDMExpression.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DDMExpressionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(DDMExpressionParser.EOF, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			logicalOrExpression(0);
			setState(43);
			match(EOF);
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
	public static class LogicalOrExpressionContext extends ParserRuleContext {
		public LogicalOrExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOrExpression; }
	 
		public LogicalOrExpressionContext() { }
		public void copyFrom(LogicalOrExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToLogicalAndExpressionContext extends LogicalOrExpressionContext {
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public ToLogicalAndExpressionContext(LogicalOrExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToLogicalAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToLogicalAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToLogicalAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrExpressionContext extends LogicalOrExpressionContext {
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public TerminalNode OR() { return getToken(DDMExpressionParser.OR, 0); }
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public OrExpressionContext(LogicalOrExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOrExpressionContext logicalOrExpression() throws RecognitionException {
		return logicalOrExpression(0);
	}

	private LogicalOrExpressionContext logicalOrExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicalOrExpressionContext _localctx = new LogicalOrExpressionContext(_ctx, _parentState);
		LogicalOrExpressionContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_logicalOrExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ToLogicalAndExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(46);
			logicalAndExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(53);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new OrExpressionContext(new LogicalOrExpressionContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_logicalOrExpression);
					setState(48);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(49);
					match(OR);
					setState(50);
					logicalAndExpression(0);
					}
					} 
				}
				setState(55);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicalAndExpressionContext extends ParserRuleContext {
		public LogicalAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAndExpression; }
	 
		public LogicalAndExpressionContext() { }
		public void copyFrom(LogicalAndExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndExpressionContext extends LogicalAndExpressionContext {
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public TerminalNode AND() { return getToken(DDMExpressionParser.AND, 0); }
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public AndExpressionContext(LogicalAndExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToEqualityExpressionContext extends LogicalAndExpressionContext {
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public ToEqualityExpressionContext(LogicalAndExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToEqualityExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToEqualityExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToEqualityExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalAndExpressionContext logicalAndExpression() throws RecognitionException {
		return logicalAndExpression(0);
	}

	private LogicalAndExpressionContext logicalAndExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		LogicalAndExpressionContext _localctx = new LogicalAndExpressionContext(_ctx, _parentState);
		LogicalAndExpressionContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_logicalAndExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ToEqualityExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(57);
			equalityExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(64);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new AndExpressionContext(new LogicalAndExpressionContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_logicalAndExpression);
					setState(59);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(60);
					match(AND);
					setState(61);
					equalityExpression(0);
					}
					} 
				}
				setState(66);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EqualityExpressionContext extends ParserRuleContext {
		public EqualityExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpression; }
	 
		public EqualityExpressionContext() { }
		public void copyFrom(EqualityExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotEqualsExpressionContext extends EqualityExpressionContext {
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public TerminalNode NEQ() { return getToken(DDMExpressionParser.NEQ, 0); }
		public ComparisonExpressionContext comparisonExpression() {
			return getRuleContext(ComparisonExpressionContext.class,0);
		}
		public NotEqualsExpressionContext(EqualityExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterNotEqualsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitNotEqualsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitNotEqualsExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToComparisonExpressionContext extends EqualityExpressionContext {
		public ComparisonExpressionContext comparisonExpression() {
			return getRuleContext(ComparisonExpressionContext.class,0);
		}
		public ToComparisonExpressionContext(EqualityExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToComparisonExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToComparisonExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToComparisonExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualsExpressionContext extends EqualityExpressionContext {
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public TerminalNode EQ() { return getToken(DDMExpressionParser.EQ, 0); }
		public ComparisonExpressionContext comparisonExpression() {
			return getRuleContext(ComparisonExpressionContext.class,0);
		}
		public EqualsExpressionContext(EqualityExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterEqualsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitEqualsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitEqualsExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExpressionContext equalityExpression() throws RecognitionException {
		return equalityExpression(0);
	}

	private EqualityExpressionContext equalityExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		EqualityExpressionContext _localctx = new EqualityExpressionContext(_ctx, _parentState);
		EqualityExpressionContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_equalityExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ToComparisonExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(68);
			comparisonExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(78);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(76);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new EqualsExpressionContext(new EqualityExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpression);
						setState(70);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(71);
						match(EQ);
						setState(72);
						comparisonExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new NotEqualsExpressionContext(new EqualityExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpression);
						setState(73);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(74);
						match(NEQ);
						setState(75);
						comparisonExpression(0);
						}
						break;
					}
					} 
				}
				setState(80);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonExpressionContext extends ParserRuleContext {
		public ComparisonExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonExpression; }
	 
		public ComparisonExpressionContext() { }
		public void copyFrom(ComparisonExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GreaterThanOrEqualsExpressionContext extends ComparisonExpressionContext {
		public ComparisonExpressionContext comparisonExpression() {
			return getRuleContext(ComparisonExpressionContext.class,0);
		}
		public TerminalNode GE() { return getToken(DDMExpressionParser.GE, 0); }
		public AdditionOrSubtractionExpressionContext additionOrSubtractionExpression() {
			return getRuleContext(AdditionOrSubtractionExpressionContext.class,0);
		}
		public GreaterThanOrEqualsExpressionContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterGreaterThanOrEqualsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitGreaterThanOrEqualsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitGreaterThanOrEqualsExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LessThanOrEqualsExpressionContext extends ComparisonExpressionContext {
		public ComparisonExpressionContext comparisonExpression() {
			return getRuleContext(ComparisonExpressionContext.class,0);
		}
		public TerminalNode LE() { return getToken(DDMExpressionParser.LE, 0); }
		public AdditionOrSubtractionExpressionContext additionOrSubtractionExpression() {
			return getRuleContext(AdditionOrSubtractionExpressionContext.class,0);
		}
		public LessThanOrEqualsExpressionContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterLessThanOrEqualsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitLessThanOrEqualsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitLessThanOrEqualsExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GreaterThanExpressionContext extends ComparisonExpressionContext {
		public ComparisonExpressionContext comparisonExpression() {
			return getRuleContext(ComparisonExpressionContext.class,0);
		}
		public TerminalNode GT() { return getToken(DDMExpressionParser.GT, 0); }
		public AdditionOrSubtractionExpressionContext additionOrSubtractionExpression() {
			return getRuleContext(AdditionOrSubtractionExpressionContext.class,0);
		}
		public GreaterThanExpressionContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterGreaterThanExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitGreaterThanExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitGreaterThanExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToBooleanUnaryExpressionContext extends ComparisonExpressionContext {
		public BooleanUnaryExpressionContext booleanUnaryExpression() {
			return getRuleContext(BooleanUnaryExpressionContext.class,0);
		}
		public ToBooleanUnaryExpressionContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToBooleanUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToBooleanUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToBooleanUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LessThanExpressionContext extends ComparisonExpressionContext {
		public ComparisonExpressionContext comparisonExpression() {
			return getRuleContext(ComparisonExpressionContext.class,0);
		}
		public TerminalNode LT() { return getToken(DDMExpressionParser.LT, 0); }
		public AdditionOrSubtractionExpressionContext additionOrSubtractionExpression() {
			return getRuleContext(AdditionOrSubtractionExpressionContext.class,0);
		}
		public LessThanExpressionContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterLessThanExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitLessThanExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitLessThanExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonExpressionContext comparisonExpression() throws RecognitionException {
		return comparisonExpression(0);
	}

	private ComparisonExpressionContext comparisonExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ComparisonExpressionContext _localctx = new ComparisonExpressionContext(_ctx, _parentState);
		ComparisonExpressionContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_comparisonExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ToBooleanUnaryExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(82);
			booleanUnaryExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(98);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(96);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
					case 1:
						{
						_localctx = new GreaterThanExpressionContext(new ComparisonExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_comparisonExpression);
						setState(84);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(85);
						match(GT);
						setState(86);
						additionOrSubtractionExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new GreaterThanOrEqualsExpressionContext(new ComparisonExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_comparisonExpression);
						setState(87);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(88);
						match(GE);
						setState(89);
						additionOrSubtractionExpression(0);
						}
						break;
					case 3:
						{
						_localctx = new LessThanExpressionContext(new ComparisonExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_comparisonExpression);
						setState(90);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(91);
						match(LT);
						setState(92);
						additionOrSubtractionExpression(0);
						}
						break;
					case 4:
						{
						_localctx = new LessThanOrEqualsExpressionContext(new ComparisonExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_comparisonExpression);
						setState(93);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(94);
						match(LE);
						setState(95);
						additionOrSubtractionExpression(0);
						}
						break;
					}
					} 
				}
				setState(100);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BooleanUnaryExpressionContext extends ParserRuleContext {
		public BooleanUnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanUnaryExpression; }
	 
		public BooleanUnaryExpressionContext() { }
		public void copyFrom(BooleanUnaryExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToBooleanOperandExpressionContext extends BooleanUnaryExpressionContext {
		public BooleanOperandExpressionContext booleanOperandExpression() {
			return getRuleContext(BooleanOperandExpressionContext.class,0);
		}
		public ToBooleanOperandExpressionContext(BooleanUnaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToBooleanOperandExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToBooleanOperandExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToBooleanOperandExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotExpressionContext extends BooleanUnaryExpressionContext {
		public TerminalNode NOT() { return getToken(DDMExpressionParser.NOT, 0); }
		public BooleanUnaryExpressionContext booleanUnaryExpression() {
			return getRuleContext(BooleanUnaryExpressionContext.class,0);
		}
		public NotExpressionContext(BooleanUnaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterNotExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitNotExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitNotExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanUnaryExpressionContext booleanUnaryExpression() throws RecognitionException {
		BooleanUnaryExpressionContext _localctx = new BooleanUnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_booleanUnaryExpression);
		try {
			setState(104);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOT:
				_localctx = new NotExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				match(NOT);
				setState(102);
				booleanUnaryExpression();
				}
				break;
			case IntegerLiteral:
			case FloatingPointLiteral:
			case FALSE:
			case LPAREN:
			case MINUS:
			case STRING:
			case TRUE:
			case IDENTIFIER:
				_localctx = new ToBooleanOperandExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(103);
				booleanOperandExpression();
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
	public static class BooleanOperandExpressionContext extends ParserRuleContext {
		public BooleanOperandExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanOperandExpression; }
	 
		public BooleanOperandExpressionContext() { }
		public void copyFrom(BooleanOperandExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToLogicalTermContext extends BooleanOperandExpressionContext {
		public LogicalTermContext logicalTerm() {
			return getRuleContext(LogicalTermContext.class,0);
		}
		public ToLogicalTermContext(BooleanOperandExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToLogicalTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToLogicalTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToLogicalTerm(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToAdditionOrSubtractionEpressionContext extends BooleanOperandExpressionContext {
		public AdditionOrSubtractionExpressionContext additionOrSubtractionExpression() {
			return getRuleContext(AdditionOrSubtractionExpressionContext.class,0);
		}
		public ToAdditionOrSubtractionEpressionContext(BooleanOperandExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToAdditionOrSubtractionEpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToAdditionOrSubtractionEpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToAdditionOrSubtractionEpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BooleanParenthesisContext extends BooleanOperandExpressionContext {
		public TerminalNode LPAREN() { return getToken(DDMExpressionParser.LPAREN, 0); }
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(DDMExpressionParser.RPAREN, 0); }
		public BooleanParenthesisContext(BooleanOperandExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterBooleanParenthesis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitBooleanParenthesis(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitBooleanParenthesis(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanOperandExpressionContext booleanOperandExpression() throws RecognitionException {
		BooleanOperandExpressionContext _localctx = new BooleanOperandExpressionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_booleanOperandExpression);
		try {
			setState(112);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				_localctx = new ToLogicalTermContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				logicalTerm();
				}
				break;
			case 2:
				_localctx = new ToAdditionOrSubtractionEpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(107);
				additionOrSubtractionExpression(0);
				}
				break;
			case 3:
				_localctx = new BooleanParenthesisContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(108);
				match(LPAREN);
				setState(109);
				logicalOrExpression(0);
				setState(110);
				match(RPAREN);
				}
				break;
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
	public static class LogicalTermContext extends ParserRuleContext {
		public LogicalTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalTerm; }
	 
		public LogicalTermContext() { }
		public void copyFrom(LogicalTermContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LogicalVariableContext extends LogicalTermContext {
		public TerminalNode IDENTIFIER() { return getToken(DDMExpressionParser.IDENTIFIER, 0); }
		public LogicalVariableContext(LogicalTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterLogicalVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitLogicalVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitLogicalVariable(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LogicalConstantContext extends LogicalTermContext {
		public TerminalNode TRUE() { return getToken(DDMExpressionParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(DDMExpressionParser.FALSE, 0); }
		public LogicalConstantContext(LogicalTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterLogicalConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitLogicalConstant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitLogicalConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalTermContext logicalTerm() throws RecognitionException {
		LogicalTermContext _localctx = new LogicalTermContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_logicalTerm);
		int _la;
		try {
			setState(116);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FALSE:
			case TRUE:
				_localctx = new LogicalConstantContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(114);
				_la = _input.LA(1);
				if ( !(_la==FALSE || _la==TRUE) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case IDENTIFIER:
				_localctx = new LogicalVariableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(115);
				match(IDENTIFIER);
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
	public static class AdditionOrSubtractionExpressionContext extends ParserRuleContext {
		public AdditionOrSubtractionExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additionOrSubtractionExpression; }
	 
		public AdditionOrSubtractionExpressionContext() { }
		public void copyFrom(AdditionOrSubtractionExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AdditionExpressionContext extends AdditionOrSubtractionExpressionContext {
		public AdditionOrSubtractionExpressionContext additionOrSubtractionExpression() {
			return getRuleContext(AdditionOrSubtractionExpressionContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(DDMExpressionParser.PLUS, 0); }
		public MultiplicationOrDivisionExpressionContext multiplicationOrDivisionExpression() {
			return getRuleContext(MultiplicationOrDivisionExpressionContext.class,0);
		}
		public AdditionExpressionContext(AdditionOrSubtractionExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterAdditionExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitAdditionExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitAdditionExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubtractionExpressionContext extends AdditionOrSubtractionExpressionContext {
		public AdditionOrSubtractionExpressionContext additionOrSubtractionExpression() {
			return getRuleContext(AdditionOrSubtractionExpressionContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(DDMExpressionParser.MINUS, 0); }
		public MultiplicationOrDivisionExpressionContext multiplicationOrDivisionExpression() {
			return getRuleContext(MultiplicationOrDivisionExpressionContext.class,0);
		}
		public SubtractionExpressionContext(AdditionOrSubtractionExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterSubtractionExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitSubtractionExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitSubtractionExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToMultOrDivContext extends AdditionOrSubtractionExpressionContext {
		public MultiplicationOrDivisionExpressionContext multiplicationOrDivisionExpression() {
			return getRuleContext(MultiplicationOrDivisionExpressionContext.class,0);
		}
		public ToMultOrDivContext(AdditionOrSubtractionExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToMultOrDiv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToMultOrDiv(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToMultOrDiv(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditionOrSubtractionExpressionContext additionOrSubtractionExpression() throws RecognitionException {
		return additionOrSubtractionExpression(0);
	}

	private AdditionOrSubtractionExpressionContext additionOrSubtractionExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AdditionOrSubtractionExpressionContext _localctx = new AdditionOrSubtractionExpressionContext(_ctx, _parentState);
		AdditionOrSubtractionExpressionContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_additionOrSubtractionExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ToMultOrDivContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(119);
			multiplicationOrDivisionExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(129);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(127);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new AdditionExpressionContext(new AdditionOrSubtractionExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_additionOrSubtractionExpression);
						setState(121);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(122);
						match(PLUS);
						setState(123);
						multiplicationOrDivisionExpression(0);
						}
						break;
					case 2:
						{
						_localctx = new SubtractionExpressionContext(new AdditionOrSubtractionExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_additionOrSubtractionExpression);
						setState(124);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(125);
						match(MINUS);
						setState(126);
						multiplicationOrDivisionExpression(0);
						}
						break;
					}
					} 
				}
				setState(131);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MultiplicationOrDivisionExpressionContext extends ParserRuleContext {
		public MultiplicationOrDivisionExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicationOrDivisionExpression; }
	 
		public MultiplicationOrDivisionExpressionContext() { }
		public void copyFrom(MultiplicationOrDivisionExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToNumericUnaryExpressionContext extends MultiplicationOrDivisionExpressionContext {
		public NumericUnaryEpressionContext numericUnaryEpression() {
			return getRuleContext(NumericUnaryEpressionContext.class,0);
		}
		public ToNumericUnaryExpressionContext(MultiplicationOrDivisionExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToNumericUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToNumericUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToNumericUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DivisionExpressionContext extends MultiplicationOrDivisionExpressionContext {
		public MultiplicationOrDivisionExpressionContext multiplicationOrDivisionExpression() {
			return getRuleContext(MultiplicationOrDivisionExpressionContext.class,0);
		}
		public TerminalNode DIV() { return getToken(DDMExpressionParser.DIV, 0); }
		public NumericUnaryEpressionContext numericUnaryEpression() {
			return getRuleContext(NumericUnaryEpressionContext.class,0);
		}
		public DivisionExpressionContext(MultiplicationOrDivisionExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterDivisionExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitDivisionExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitDivisionExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MultiplicationExpressionContext extends MultiplicationOrDivisionExpressionContext {
		public MultiplicationOrDivisionExpressionContext multiplicationOrDivisionExpression() {
			return getRuleContext(MultiplicationOrDivisionExpressionContext.class,0);
		}
		public TerminalNode MULT() { return getToken(DDMExpressionParser.MULT, 0); }
		public NumericUnaryEpressionContext numericUnaryEpression() {
			return getRuleContext(NumericUnaryEpressionContext.class,0);
		}
		public MultiplicationExpressionContext(MultiplicationOrDivisionExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterMultiplicationExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitMultiplicationExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitMultiplicationExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicationOrDivisionExpressionContext multiplicationOrDivisionExpression() throws RecognitionException {
		return multiplicationOrDivisionExpression(0);
	}

	private MultiplicationOrDivisionExpressionContext multiplicationOrDivisionExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MultiplicationOrDivisionExpressionContext _localctx = new MultiplicationOrDivisionExpressionContext(_ctx, _parentState);
		MultiplicationOrDivisionExpressionContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_multiplicationOrDivisionExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ToNumericUnaryExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(133);
			numericUnaryEpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(143);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(141);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicationExpressionContext(new MultiplicationOrDivisionExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicationOrDivisionExpression);
						setState(135);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(136);
						match(MULT);
						setState(137);
						numericUnaryEpression();
						}
						break;
					case 2:
						{
						_localctx = new DivisionExpressionContext(new MultiplicationOrDivisionExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicationOrDivisionExpression);
						setState(138);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(139);
						match(DIV);
						setState(140);
						numericUnaryEpression();
						}
						break;
					}
					} 
				}
				setState(145);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NumericUnaryEpressionContext extends ParserRuleContext {
		public NumericUnaryEpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericUnaryEpression; }
	 
		public NumericUnaryEpressionContext() { }
		public void copyFrom(NumericUnaryEpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MinusExpressionContext extends NumericUnaryEpressionContext {
		public TerminalNode MINUS() { return getToken(DDMExpressionParser.MINUS, 0); }
		public NumericUnaryEpressionContext numericUnaryEpression() {
			return getRuleContext(NumericUnaryEpressionContext.class,0);
		}
		public MinusExpressionContext(NumericUnaryEpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterMinusExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitMinusExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitMinusExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryContext extends NumericUnaryEpressionContext {
		public NumericOperandExpressionContext numericOperandExpression() {
			return getRuleContext(NumericOperandExpressionContext.class,0);
		}
		public PrimaryContext(NumericUnaryEpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericUnaryEpressionContext numericUnaryEpression() throws RecognitionException {
		NumericUnaryEpressionContext _localctx = new NumericUnaryEpressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_numericUnaryEpression);
		try {
			setState(149);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MINUS:
				_localctx = new MinusExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(146);
				match(MINUS);
				setState(147);
				numericUnaryEpression();
				}
				break;
			case IntegerLiteral:
			case FloatingPointLiteral:
			case LPAREN:
			case STRING:
			case IDENTIFIER:
				_localctx = new PrimaryContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(148);
				numericOperandExpression();
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
	public static class NumericOperandExpressionContext extends ParserRuleContext {
		public NumericOperandExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericOperandExpression; }
	 
		public NumericOperandExpressionContext() { }
		public void copyFrom(NumericOperandExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToNumericTermContext extends NumericOperandExpressionContext {
		public NumericTermContext numericTerm() {
			return getRuleContext(NumericTermContext.class,0);
		}
		public ToNumericTermContext(NumericOperandExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToNumericTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToNumericTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToNumericTerm(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumericParenthesisContext extends NumericOperandExpressionContext {
		public TerminalNode LPAREN() { return getToken(DDMExpressionParser.LPAREN, 0); }
		public AdditionOrSubtractionExpressionContext additionOrSubtractionExpression() {
			return getRuleContext(AdditionOrSubtractionExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(DDMExpressionParser.RPAREN, 0); }
		public NumericParenthesisContext(NumericOperandExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterNumericParenthesis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitNumericParenthesis(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitNumericParenthesis(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToFunctionCallExpressionContext extends NumericOperandExpressionContext {
		public FunctionCallExpressionContext functionCallExpression() {
			return getRuleContext(FunctionCallExpressionContext.class,0);
		}
		public ToFunctionCallExpressionContext(NumericOperandExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToFunctionCallExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToFunctionCallExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToFunctionCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericOperandExpressionContext numericOperandExpression() throws RecognitionException {
		NumericOperandExpressionContext _localctx = new NumericOperandExpressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_numericOperandExpression);
		try {
			setState(157);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new ToNumericTermContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(151);
				numericTerm();
				}
				break;
			case 2:
				_localctx = new ToFunctionCallExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(152);
				functionCallExpression();
				}
				break;
			case 3:
				_localctx = new NumericParenthesisContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(153);
				match(LPAREN);
				setState(154);
				additionOrSubtractionExpression(0);
				setState(155);
				match(RPAREN);
				}
				break;
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
	public static class NumericTermContext extends ParserRuleContext {
		public NumericTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericTerm; }
	 
		public NumericTermContext() { }
		public void copyFrom(NumericTermContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumericLiteralContext extends NumericTermContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public NumericLiteralContext(NumericTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterNumericLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitNumericLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitNumericLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumericVariableContext extends NumericTermContext {
		public TerminalNode IDENTIFIER() { return getToken(DDMExpressionParser.IDENTIFIER, 0); }
		public NumericVariableContext(NumericTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterNumericVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitNumericVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitNumericVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericTermContext numericTerm() throws RecognitionException {
		NumericTermContext _localctx = new NumericTermContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_numericTerm);
		try {
			setState(161);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IntegerLiteral:
			case FloatingPointLiteral:
			case STRING:
				_localctx = new NumericLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(159);
				literal();
				}
				break;
			case IDENTIFIER:
				_localctx = new NumericVariableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(160);
				match(IDENTIFIER);
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
	public static class FunctionCallExpressionContext extends ParserRuleContext {
		public Token functionName;
		public TerminalNode LPAREN() { return getToken(DDMExpressionParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DDMExpressionParser.RPAREN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(DDMExpressionParser.IDENTIFIER, 0); }
		public FunctionParametersContext functionParameters() {
			return getRuleContext(FunctionParametersContext.class,0);
		}
		public FunctionCallExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCallExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterFunctionCallExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitFunctionCallExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitFunctionCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallExpressionContext functionCallExpression() throws RecognitionException {
		FunctionCallExpressionContext _localctx = new FunctionCallExpressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_functionCallExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			((FunctionCallExpressionContext)_localctx).functionName = match(IDENTIFIER);
			setState(164);
			match(LPAREN);
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 59025670L) != 0)) {
				{
				setState(165);
				functionParameters();
				}
			}

			setState(168);
			match(RPAREN);
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
	public static class FunctionParametersContext extends ParserRuleContext {
		public List<FunctionParameterContext> functionParameter() {
			return getRuleContexts(FunctionParameterContext.class);
		}
		public FunctionParameterContext functionParameter(int i) {
			return getRuleContext(FunctionParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DDMExpressionParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DDMExpressionParser.COMMA, i);
		}
		public FunctionParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterFunctionParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitFunctionParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitFunctionParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionParametersContext functionParameters() throws RecognitionException {
		FunctionParametersContext _localctx = new FunctionParametersContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_functionParameters);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			functionParameter();
			setState(175);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(171);
				match(COMMA);
				setState(172);
				functionParameter();
				}
				}
				setState(177);
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
	public static class FunctionParameterContext extends ParserRuleContext {
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public FunctionParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterFunctionParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitFunctionParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitFunctionParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionParameterContext functionParameter() throws RecognitionException {
		FunctionParameterContext _localctx = new FunctionParameterContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_functionParameter);
		try {
			setState(180);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACKET:
				enterOuterAlt(_localctx, 1);
				{
				setState(178);
				array();
				}
				break;
			case IntegerLiteral:
			case FloatingPointLiteral:
			case FALSE:
			case LPAREN:
			case MINUS:
			case NOT:
			case STRING:
			case TRUE:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(179);
				logicalOrExpression(0);
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
	public static class ArrayContext extends ParserRuleContext {
		public FloatingPointArrayContext floatingPointArray() {
			return getRuleContext(FloatingPointArrayContext.class,0);
		}
		public IntegerArrayContext integerArray() {
			return getRuleContext(IntegerArrayContext.class,0);
		}
		public StringArrayContext stringArray() {
			return getRuleContext(StringArrayContext.class,0);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_array);
		try {
			setState(185);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(182);
				floatingPointArray();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(183);
				integerArray();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(184);
				stringArray();
				}
				break;
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
	public static class FloatingPointArrayContext extends ParserRuleContext {
		public FloatingPointArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_floatingPointArray; }
	 
		public FloatingPointArrayContext() { }
		public void copyFrom(FloatingPointArrayContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToFloatingPointArrayContext extends FloatingPointArrayContext {
		public TerminalNode LBRACKET() { return getToken(DDMExpressionParser.LBRACKET, 0); }
		public List<TerminalNode> FloatingPointLiteral() { return getTokens(DDMExpressionParser.FloatingPointLiteral); }
		public TerminalNode FloatingPointLiteral(int i) {
			return getToken(DDMExpressionParser.FloatingPointLiteral, i);
		}
		public TerminalNode RBRACKET() { return getToken(DDMExpressionParser.RBRACKET, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DDMExpressionParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DDMExpressionParser.COMMA, i);
		}
		public ToFloatingPointArrayContext(FloatingPointArrayContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToFloatingPointArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToFloatingPointArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToFloatingPointArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FloatingPointArrayContext floatingPointArray() throws RecognitionException {
		FloatingPointArrayContext _localctx = new FloatingPointArrayContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_floatingPointArray);
		int _la;
		try {
			_localctx = new ToFloatingPointArrayContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(LBRACKET);
			setState(188);
			match(FloatingPointLiteral);
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(189);
				match(COMMA);
				setState(190);
				match(FloatingPointLiteral);
				}
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(196);
			match(RBRACKET);
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
	public static class IntegerArrayContext extends ParserRuleContext {
		public IntegerArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integerArray; }
	 
		public IntegerArrayContext() { }
		public void copyFrom(IntegerArrayContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToIntegerArrayContext extends IntegerArrayContext {
		public TerminalNode LBRACKET() { return getToken(DDMExpressionParser.LBRACKET, 0); }
		public List<TerminalNode> IntegerLiteral() { return getTokens(DDMExpressionParser.IntegerLiteral); }
		public TerminalNode IntegerLiteral(int i) {
			return getToken(DDMExpressionParser.IntegerLiteral, i);
		}
		public TerminalNode RBRACKET() { return getToken(DDMExpressionParser.RBRACKET, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DDMExpressionParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DDMExpressionParser.COMMA, i);
		}
		public ToIntegerArrayContext(IntegerArrayContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToIntegerArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToIntegerArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToIntegerArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntegerArrayContext integerArray() throws RecognitionException {
		IntegerArrayContext _localctx = new IntegerArrayContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_integerArray);
		int _la;
		try {
			_localctx = new ToIntegerArrayContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(LBRACKET);
			setState(199);
			match(IntegerLiteral);
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(200);
				match(COMMA);
				setState(201);
				match(IntegerLiteral);
				}
				}
				setState(206);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(207);
			match(RBRACKET);
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
	public static class StringArrayContext extends ParserRuleContext {
		public StringArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringArray; }
	 
		public StringArrayContext() { }
		public void copyFrom(StringArrayContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ToStringArrayContext extends StringArrayContext {
		public TerminalNode LBRACKET() { return getToken(DDMExpressionParser.LBRACKET, 0); }
		public List<TerminalNode> STRING() { return getTokens(DDMExpressionParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(DDMExpressionParser.STRING, i);
		}
		public TerminalNode RBRACKET() { return getToken(DDMExpressionParser.RBRACKET, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DDMExpressionParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(DDMExpressionParser.COMMA, i);
		}
		public ToStringArrayContext(StringArrayContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterToStringArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitToStringArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitToStringArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringArrayContext stringArray() throws RecognitionException {
		StringArrayContext _localctx = new StringArrayContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_stringArray);
		int _la;
		try {
			_localctx = new ToStringArrayContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(LBRACKET);
			setState(210);
			match(STRING);
			setState(215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(211);
				match(COMMA);
				setState(212);
				match(STRING);
				}
				}
				setState(217);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(218);
			match(RBRACKET);
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
	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	 
		public LiteralContext() { }
		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringLiteralContext extends LiteralContext {
		public TerminalNode STRING() { return getToken(DDMExpressionParser.STRING, 0); }
		public StringLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FloatingPointLiteralContext extends LiteralContext {
		public TerminalNode FloatingPointLiteral() { return getToken(DDMExpressionParser.FloatingPointLiteral, 0); }
		public FloatingPointLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterFloatingPointLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitFloatingPointLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitFloatingPointLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntegerLiteralContext extends LiteralContext {
		public TerminalNode IntegerLiteral() { return getToken(DDMExpressionParser.IntegerLiteral, 0); }
		public IntegerLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).enterIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DDMExpressionListener ) ((DDMExpressionListener)listener).exitIntegerLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DDMExpressionVisitor ) return ((DDMExpressionVisitor<? extends T>)visitor).visitIntegerLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_literal);
		try {
			setState(223);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FloatingPointLiteral:
				_localctx = new FloatingPointLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(220);
				match(FloatingPointLiteral);
				}
				break;
			case IntegerLiteral:
				_localctx = new IntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(221);
				match(IntegerLiteral);
				}
				break;
			case STRING:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(222);
				match(STRING);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return logicalOrExpression_sempred((LogicalOrExpressionContext)_localctx, predIndex);
		case 2:
			return logicalAndExpression_sempred((LogicalAndExpressionContext)_localctx, predIndex);
		case 3:
			return equalityExpression_sempred((EqualityExpressionContext)_localctx, predIndex);
		case 4:
			return comparisonExpression_sempred((ComparisonExpressionContext)_localctx, predIndex);
		case 8:
			return additionOrSubtractionExpression_sempred((AdditionOrSubtractionExpressionContext)_localctx, predIndex);
		case 9:
			return multiplicationOrDivisionExpression_sempred((MultiplicationOrDivisionExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean logicalOrExpression_sempred(LogicalOrExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean logicalAndExpression_sempred(LogicalAndExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean equalityExpression_sempred(EqualityExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 3);
		case 3:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean comparisonExpression_sempred(ComparisonExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 5);
		case 5:
			return precpred(_ctx, 4);
		case 6:
			return precpred(_ctx, 3);
		case 7:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean additionOrSubtractionExpression_sempred(AdditionOrSubtractionExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 3);
		case 9:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean multiplicationOrDivisionExpression_sempred(MultiplicationOrDivisionExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10:
			return precpred(_ctx, 3);
		case 11:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001a\u00e2\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0005\u00014\b\u0001\n\u0001\f\u00017\t\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005"+
		"\u0002?\b\u0002\n\u0002\f\u0002B\t\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0005\u0003M\b\u0003\n\u0003\f\u0003P\t\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0005\u0004a\b\u0004\n\u0004\f\u0004d\t\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0003\u0005i\b\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006q\b"+
		"\u0006\u0001\u0007\u0001\u0007\u0003\u0007u\b\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005\b\u0080\b\b\n"+
		"\b\f\b\u0083\t\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0005\t\u008e\b\t\n\t\f\t\u0091\t\t\u0001\n\u0001\n"+
		"\u0001\n\u0003\n\u0096\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0003\u000b\u009e\b\u000b\u0001\f\u0001\f\u0003"+
		"\f\u00a2\b\f\u0001\r\u0001\r\u0001\r\u0003\r\u00a7\b\r\u0001\r\u0001\r"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u00ae\b\u000e\n\u000e"+
		"\f\u000e\u00b1\t\u000e\u0001\u000f\u0001\u000f\u0003\u000f\u00b5\b\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00ba\b\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0005\u0011\u00c0\b\u0011\n\u0011"+
		"\f\u0011\u00c3\t\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0005\u0012\u00cb\b\u0012\n\u0012\f\u0012\u00ce"+
		"\t\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0005\u0013\u00d6\b\u0013\n\u0013\f\u0013\u00d9\t\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u00e0\b\u0014"+
		"\u0001\u0014\u0000\u0006\u0002\u0004\u0006\b\u0010\u0012\u0015\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e"+
		" \"$&(\u0000\u0001\u0002\u0000\b\b\u0018\u0018\u00ea\u0000*\u0001\u0000"+
		"\u0000\u0000\u0002-\u0001\u0000\u0000\u0000\u00048\u0001\u0000\u0000\u0000"+
		"\u0006C\u0001\u0000\u0000\u0000\bQ\u0001\u0000\u0000\u0000\nh\u0001\u0000"+
		"\u0000\u0000\fp\u0001\u0000\u0000\u0000\u000et\u0001\u0000\u0000\u0000"+
		"\u0010v\u0001\u0000\u0000\u0000\u0012\u0084\u0001\u0000\u0000\u0000\u0014"+
		"\u0095\u0001\u0000\u0000\u0000\u0016\u009d\u0001\u0000\u0000\u0000\u0018"+
		"\u00a1\u0001\u0000\u0000\u0000\u001a\u00a3\u0001\u0000\u0000\u0000\u001c"+
		"\u00aa\u0001\u0000\u0000\u0000\u001e\u00b4\u0001\u0000\u0000\u0000 \u00b9"+
		"\u0001\u0000\u0000\u0000\"\u00bb\u0001\u0000\u0000\u0000$\u00c6\u0001"+
		"\u0000\u0000\u0000&\u00d1\u0001\u0000\u0000\u0000(\u00df\u0001\u0000\u0000"+
		"\u0000*+\u0003\u0002\u0001\u0000+,\u0005\u0000\u0000\u0001,\u0001\u0001"+
		"\u0000\u0000\u0000-.\u0006\u0001\uffff\uffff\u0000./\u0003\u0004\u0002"+
		"\u0000/5\u0001\u0000\u0000\u000001\n\u0002\u0000\u000012\u0005\u0013\u0000"+
		"\u000024\u0003\u0004\u0002\u000030\u0001\u0000\u0000\u000047\u0001\u0000"+
		"\u0000\u000053\u0001\u0000\u0000\u000056\u0001\u0000\u0000\u00006\u0003"+
		"\u0001\u0000\u0000\u000075\u0001\u0000\u0000\u000089\u0006\u0002\uffff"+
		"\uffff\u00009:\u0003\u0006\u0003\u0000:@\u0001\u0000\u0000\u0000;<\n\u0002"+
		"\u0000\u0000<=\u0005\u0004\u0000\u0000=?\u0003\u0006\u0003\u0000>;\u0001"+
		"\u0000\u0000\u0000?B\u0001\u0000\u0000\u0000@>\u0001\u0000\u0000\u0000"+
		"@A\u0001\u0000\u0000\u0000A\u0005\u0001\u0000\u0000\u0000B@\u0001\u0000"+
		"\u0000\u0000CD\u0006\u0003\uffff\uffff\u0000DE\u0003\b\u0004\u0000EN\u0001"+
		"\u0000\u0000\u0000FG\n\u0003\u0000\u0000GH\u0005\u0007\u0000\u0000HM\u0003"+
		"\b\u0004\u0000IJ\n\u0002\u0000\u0000JK\u0005\u0011\u0000\u0000KM\u0003"+
		"\b\u0004\u0000LF\u0001\u0000\u0000\u0000LI\u0001\u0000\u0000\u0000MP\u0001"+
		"\u0000\u0000\u0000NL\u0001\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000"+
		"O\u0007\u0001\u0000\u0000\u0000PN\u0001\u0000\u0000\u0000QR\u0006\u0004"+
		"\uffff\uffff\u0000RS\u0003\n\u0005\u0000Sb\u0001\u0000\u0000\u0000TU\n"+
		"\u0005\u0000\u0000UV\u0005\n\u0000\u0000Va\u0003\u0010\b\u0000WX\n\u0004"+
		"\u0000\u0000XY\u0005\t\u0000\u0000Ya\u0003\u0010\b\u0000Z[\n\u0003\u0000"+
		"\u0000[\\\u0005\u000e\u0000\u0000\\a\u0003\u0010\b\u0000]^\n\u0002\u0000"+
		"\u0000^_\u0005\f\u0000\u0000_a\u0003\u0010\b\u0000`T\u0001\u0000\u0000"+
		"\u0000`W\u0001\u0000\u0000\u0000`Z\u0001\u0000\u0000\u0000`]\u0001\u0000"+
		"\u0000\u0000ad\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000bc\u0001"+
		"\u0000\u0000\u0000c\t\u0001\u0000\u0000\u0000db\u0001\u0000\u0000\u0000"+
		"ef\u0005\u0012\u0000\u0000fi\u0003\n\u0005\u0000gi\u0003\f\u0006\u0000"+
		"he\u0001\u0000\u0000\u0000hg\u0001\u0000\u0000\u0000i\u000b\u0001\u0000"+
		"\u0000\u0000jq\u0003\u000e\u0007\u0000kq\u0003\u0010\b\u0000lm\u0005\r"+
		"\u0000\u0000mn\u0003\u0002\u0001\u0000no\u0005\u0016\u0000\u0000oq\u0001"+
		"\u0000\u0000\u0000pj\u0001\u0000\u0000\u0000pk\u0001\u0000\u0000\u0000"+
		"pl\u0001\u0000\u0000\u0000q\r\u0001\u0000\u0000\u0000ru\u0007\u0000\u0000"+
		"\u0000su\u0005\u0019\u0000\u0000tr\u0001\u0000\u0000\u0000ts\u0001\u0000"+
		"\u0000\u0000u\u000f\u0001\u0000\u0000\u0000vw\u0006\b\uffff\uffff\u0000"+
		"wx\u0003\u0012\t\u0000x\u0081\u0001\u0000\u0000\u0000yz\n\u0003\u0000"+
		"\u0000z{\u0005\u0014\u0000\u0000{\u0080\u0003\u0012\t\u0000|}\n\u0002"+
		"\u0000\u0000}~\u0005\u000f\u0000\u0000~\u0080\u0003\u0012\t\u0000\u007f"+
		"y\u0001\u0000\u0000\u0000\u007f|\u0001\u0000\u0000\u0000\u0080\u0083\u0001"+
		"\u0000\u0000\u0000\u0081\u007f\u0001\u0000\u0000\u0000\u0081\u0082\u0001"+
		"\u0000\u0000\u0000\u0082\u0011\u0001\u0000\u0000\u0000\u0083\u0081\u0001"+
		"\u0000\u0000\u0000\u0084\u0085\u0006\t\uffff\uffff\u0000\u0085\u0086\u0003"+
		"\u0014\n\u0000\u0086\u008f\u0001\u0000\u0000\u0000\u0087\u0088\n\u0003"+
		"\u0000\u0000\u0088\u0089\u0005\u0010\u0000\u0000\u0089\u008e\u0003\u0014"+
		"\n\u0000\u008a\u008b\n\u0002\u0000\u0000\u008b\u008c\u0005\u0006\u0000"+
		"\u0000\u008c\u008e\u0003\u0014\n\u0000\u008d\u0087\u0001\u0000\u0000\u0000"+
		"\u008d\u008a\u0001\u0000\u0000\u0000\u008e\u0091\u0001\u0000\u0000\u0000"+
		"\u008f\u008d\u0001\u0000\u0000\u0000\u008f\u0090\u0001\u0000\u0000\u0000"+
		"\u0090\u0013\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000\u0000\u0000"+
		"\u0092\u0093\u0005\u000f\u0000\u0000\u0093\u0096\u0003\u0014\n\u0000\u0094"+
		"\u0096\u0003\u0016\u000b\u0000\u0095\u0092\u0001\u0000\u0000\u0000\u0095"+
		"\u0094\u0001\u0000\u0000\u0000\u0096\u0015\u0001\u0000\u0000\u0000\u0097"+
		"\u009e\u0003\u0018\f\u0000\u0098\u009e\u0003\u001a\r\u0000\u0099\u009a"+
		"\u0005\r\u0000\u0000\u009a\u009b\u0003\u0010\b\u0000\u009b\u009c\u0005"+
		"\u0016\u0000\u0000\u009c\u009e\u0001\u0000\u0000\u0000\u009d\u0097\u0001"+
		"\u0000\u0000\u0000\u009d\u0098\u0001\u0000\u0000\u0000\u009d\u0099\u0001"+
		"\u0000\u0000\u0000\u009e\u0017\u0001\u0000\u0000\u0000\u009f\u00a2\u0003"+
		"(\u0014\u0000\u00a0\u00a2\u0005\u0019\u0000\u0000\u00a1\u009f\u0001\u0000"+
		"\u0000\u0000\u00a1\u00a0\u0001\u0000\u0000\u0000\u00a2\u0019\u0001\u0000"+
		"\u0000\u0000\u00a3\u00a4\u0005\u0019\u0000\u0000\u00a4\u00a6\u0005\r\u0000"+
		"\u0000\u00a5\u00a7\u0003\u001c\u000e\u0000\u00a6\u00a5\u0001\u0000\u0000"+
		"\u0000\u00a6\u00a7\u0001\u0000\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000"+
		"\u0000\u00a8\u00a9\u0005\u0016\u0000\u0000\u00a9\u001b\u0001\u0000\u0000"+
		"\u0000\u00aa\u00af\u0003\u001e\u000f\u0000\u00ab\u00ac\u0005\u0005\u0000"+
		"\u0000\u00ac\u00ae\u0003\u001e\u000f\u0000\u00ad\u00ab\u0001\u0000\u0000"+
		"\u0000\u00ae\u00b1\u0001\u0000\u0000\u0000\u00af\u00ad\u0001\u0000\u0000"+
		"\u0000\u00af\u00b0\u0001\u0000\u0000\u0000\u00b0\u001d\u0001\u0000\u0000"+
		"\u0000\u00b1\u00af\u0001\u0000\u0000\u0000\u00b2\u00b5\u0003 \u0010\u0000"+
		"\u00b3\u00b5\u0003\u0002\u0001\u0000\u00b4\u00b2\u0001\u0000\u0000\u0000"+
		"\u00b4\u00b3\u0001\u0000\u0000\u0000\u00b5\u001f\u0001\u0000\u0000\u0000"+
		"\u00b6\u00ba\u0003\"\u0011\u0000\u00b7\u00ba\u0003$\u0012\u0000\u00b8"+
		"\u00ba\u0003&\u0013\u0000\u00b9\u00b6\u0001\u0000\u0000\u0000\u00b9\u00b7"+
		"\u0001\u0000\u0000\u0000\u00b9\u00b8\u0001\u0000\u0000\u0000\u00ba!\u0001"+
		"\u0000\u0000\u0000\u00bb\u00bc\u0005\u000b\u0000\u0000\u00bc\u00c1\u0005"+
		"\u0002\u0000\u0000\u00bd\u00be\u0005\u0005\u0000\u0000\u00be\u00c0\u0005"+
		"\u0002\u0000\u0000\u00bf\u00bd\u0001\u0000\u0000\u0000\u00c0\u00c3\u0001"+
		"\u0000\u0000\u0000\u00c1\u00bf\u0001\u0000\u0000\u0000\u00c1\u00c2\u0001"+
		"\u0000\u0000\u0000\u00c2\u00c4\u0001\u0000\u0000\u0000\u00c3\u00c1\u0001"+
		"\u0000\u0000\u0000\u00c4\u00c5\u0005\u0015\u0000\u0000\u00c5#\u0001\u0000"+
		"\u0000\u0000\u00c6\u00c7\u0005\u000b\u0000\u0000\u00c7\u00cc\u0005\u0001"+
		"\u0000\u0000\u00c8\u00c9\u0005\u0005\u0000\u0000\u00c9\u00cb\u0005\u0001"+
		"\u0000\u0000\u00ca\u00c8\u0001\u0000\u0000\u0000\u00cb\u00ce\u0001\u0000"+
		"\u0000\u0000\u00cc\u00ca\u0001\u0000\u0000\u0000\u00cc\u00cd\u0001\u0000"+
		"\u0000\u0000\u00cd\u00cf\u0001\u0000\u0000\u0000\u00ce\u00cc\u0001\u0000"+
		"\u0000\u0000\u00cf\u00d0\u0005\u0015\u0000\u0000\u00d0%\u0001\u0000\u0000"+
		"\u0000\u00d1\u00d2\u0005\u000b\u0000\u0000\u00d2\u00d7\u0005\u0017\u0000"+
		"\u0000\u00d3\u00d4\u0005\u0005\u0000\u0000\u00d4\u00d6\u0005\u0017\u0000"+
		"\u0000\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d6\u00d9\u0001\u0000\u0000"+
		"\u0000\u00d7\u00d5\u0001\u0000\u0000\u0000\u00d7\u00d8\u0001\u0000\u0000"+
		"\u0000\u00d8\u00da\u0001\u0000\u0000\u0000\u00d9\u00d7\u0001\u0000\u0000"+
		"\u0000\u00da\u00db\u0005\u0015\u0000\u0000\u00db\'\u0001\u0000\u0000\u0000"+
		"\u00dc\u00e0\u0005\u0002\u0000\u0000\u00dd\u00e0\u0005\u0001\u0000\u0000"+
		"\u00de\u00e0\u0005\u0017\u0000\u0000\u00df\u00dc\u0001\u0000\u0000\u0000"+
		"\u00df\u00dd\u0001\u0000\u0000\u0000\u00df\u00de\u0001\u0000\u0000\u0000"+
		"\u00e0)\u0001\u0000\u0000\u0000\u00185@LN`bhpt\u007f\u0081\u008d\u008f"+
		"\u0095\u009d\u00a1\u00a6\u00af\u00b4\u00b9\u00c1\u00cc\u00d7\u00df";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
