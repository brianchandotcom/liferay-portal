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
public class DDMExpressionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IntegerLiteral=1, FloatingPointLiteral=2, DecimalFloatingPointLiteral=3, 
		AND=4, COMMA=5, DIV=6, EQ=7, FALSE=8, GE=9, GT=10, LBRACKET=11, LE=12, 
		LPAREN=13, LT=14, MINUS=15, MULT=16, NEQ=17, NOT=18, OR=19, PLUS=20, RBRACKET=21, 
		RPAREN=22, STRING=23, TRUE=24, IDENTIFIER=25, WS=26;
	public static final String[] tokenNames = {
		"<INVALID>", "IntegerLiteral", "FloatingPointLiteral", "DecimalFloatingPointLiteral", 
		"AND", "','", "'/'", "EQ", "FALSE", "'>='", "'>'", "'['", "'<='", "'('", 
		"'<'", "'-'", "'*'", "NEQ", "NOT", "OR", "'+'", "']'", "')'", "STRING", 
		"TRUE", "IDENTIFIER", "WS"
	};
	public static final int
		RULE_expression = 0, RULE_logicalOrExpression = 1, RULE_logicalAndExpression = 2, 
		RULE_equalityExpression = 3, RULE_comparisonExpression = 4, RULE_booleanUnaryExpression = 5, 
		RULE_booleanOperandExpression = 6, RULE_logicalTerm = 7, RULE_additionOrSubtractionExpression = 8, 
		RULE_multiplicationOrDivisionExpression = 9, RULE_numericUnaryEpression = 10, 
		RULE_numericOperandExpression = 11, RULE_numericTerm = 12, RULE_functionCallExpression = 13, 
		RULE_functionParameters = 14, RULE_functionParameter = 15, RULE_array = 16, 
		RULE_floatingPointArray = 17, RULE_integerArray = 18, RULE_stringArray = 19, 
		RULE_literal = 20;
	public static final String[] ruleNames = {
		"expression", "logicalOrExpression", "logicalAndExpression", "equalityExpression", 
		"comparisonExpression", "booleanUnaryExpression", "booleanOperandExpression", 
		"logicalTerm", "additionOrSubtractionExpression", "multiplicationOrDivisionExpression", 
		"numericUnaryEpression", "numericOperandExpression", "numericTerm", "functionCallExpression", 
		"functionParameters", "functionParameter", "array", "floatingPointArray", 
		"integerArray", "stringArray", "literal"
	};

	@Override
	public String getGrammarFileName() { return "DDMExpression.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

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
	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(DDMExpressionParser.EOF, 0); }
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
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
			setState(42); logicalOrExpression(0);
			setState(43); match(EOF);
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

			setState(46); logicalAndExpression(0);
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
					setState(49); match(OR);
					setState(50); logicalAndExpression(0);
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
	public static class AndExpressionContext extends LogicalAndExpressionContext {
		public TerminalNode AND() { return getToken(DDMExpressionParser.AND, 0); }
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
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

			setState(57); equalityExpression(0);
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
					setState(60); match(AND);
					setState(61); equalityExpression(0);
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
	public static class NotEqualsExpressionContext extends EqualityExpressionContext {
		public ComparisonExpressionContext comparisonExpression() {
			return getRuleContext(ComparisonExpressionContext.class,0);
		}
		public TerminalNode NEQ() { return getToken(DDMExpressionParser.NEQ, 0); }
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
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
	public static class EqualsExpressionContext extends EqualityExpressionContext {
		public ComparisonExpressionContext comparisonExpression() {
			return getRuleContext(ComparisonExpressionContext.class,0);
		}
		public TerminalNode EQ() { return getToken(DDMExpressionParser.EQ, 0); }
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
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

			setState(68); comparisonExpression(0);
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
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new EqualsExpressionContext(new EqualityExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpression);
						setState(70);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(71); match(EQ);
						setState(72); comparisonExpression(0);
						}
						break;

					case 2:
						{
						_localctx = new NotEqualsExpressionContext(new EqualityExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_equalityExpression);
						setState(73);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(74); match(NEQ);
						setState(75); comparisonExpression(0);
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

			setState(82); booleanUnaryExpression();
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
					switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
					case 1:
						{
						_localctx = new GreaterThanExpressionContext(new ComparisonExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_comparisonExpression);
						setState(84);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(85); match(GT);
						setState(86); additionOrSubtractionExpression(0);
						}
						break;

					case 2:
						{
						_localctx = new GreaterThanOrEqualsExpressionContext(new ComparisonExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_comparisonExpression);
						setState(87);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(88); match(GE);
						setState(89); additionOrSubtractionExpression(0);
						}
						break;

					case 3:
						{
						_localctx = new LessThanExpressionContext(new ComparisonExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_comparisonExpression);
						setState(90);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(91); match(LT);
						setState(92); additionOrSubtractionExpression(0);
						}
						break;

					case 4:
						{
						_localctx = new LessThanOrEqualsExpressionContext(new ComparisonExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_comparisonExpression);
						setState(93);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(94); match(LE);
						setState(95); additionOrSubtractionExpression(0);
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
			switch (_input.LA(1)) {
			case NOT:
				_localctx = new NotExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(101); match(NOT);
				setState(102); booleanUnaryExpression();
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
				setState(103); booleanOperandExpression();
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
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				_localctx = new ToLogicalTermContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(106); logicalTerm();
				}
				break;

			case 2:
				_localctx = new ToAdditionOrSubtractionEpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(107); additionOrSubtractionExpression(0);
				}
				break;

			case 3:
				_localctx = new BooleanParenthesisContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(108); match(LPAREN);
				setState(109); logicalOrExpression(0);
				setState(110); match(RPAREN);
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
	public static class LogicalConstantContext extends LogicalTermContext {
		public TerminalNode FALSE() { return getToken(DDMExpressionParser.FALSE, 0); }
		public TerminalNode TRUE() { return getToken(DDMExpressionParser.TRUE, 0); }
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
				consume();
				}
				break;
			case IDENTIFIER:
				_localctx = new LogicalVariableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(115); match(IDENTIFIER);
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
	public static class AdditionExpressionContext extends AdditionOrSubtractionExpressionContext {
		public MultiplicationOrDivisionExpressionContext multiplicationOrDivisionExpression() {
			return getRuleContext(MultiplicationOrDivisionExpressionContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(DDMExpressionParser.PLUS, 0); }
		public AdditionOrSubtractionExpressionContext additionOrSubtractionExpression() {
			return getRuleContext(AdditionOrSubtractionExpressionContext.class,0);
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
	public static class SubtractionExpressionContext extends AdditionOrSubtractionExpressionContext {
		public MultiplicationOrDivisionExpressionContext multiplicationOrDivisionExpression() {
			return getRuleContext(MultiplicationOrDivisionExpressionContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(DDMExpressionParser.MINUS, 0); }
		public AdditionOrSubtractionExpressionContext additionOrSubtractionExpression() {
			return getRuleContext(AdditionOrSubtractionExpressionContext.class,0);
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

			setState(119); multiplicationOrDivisionExpression(0);
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
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new AdditionExpressionContext(new AdditionOrSubtractionExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_additionOrSubtractionExpression);
						setState(121);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(122); match(PLUS);
						setState(123); multiplicationOrDivisionExpression(0);
						}
						break;

					case 2:
						{
						_localctx = new SubtractionExpressionContext(new AdditionOrSubtractionExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_additionOrSubtractionExpression);
						setState(124);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(125); match(MINUS);
						setState(126); multiplicationOrDivisionExpression(0);
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

			setState(133); numericUnaryEpression();
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
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicationExpressionContext(new MultiplicationOrDivisionExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicationOrDivisionExpression);
						setState(135);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(136); match(MULT);
						setState(137); numericUnaryEpression();
						}
						break;

					case 2:
						{
						_localctx = new DivisionExpressionContext(new MultiplicationOrDivisionExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_multiplicationOrDivisionExpression);
						setState(138);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(139); match(DIV);
						setState(140); numericUnaryEpression();
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
			switch (_input.LA(1)) {
			case MINUS:
				_localctx = new MinusExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(146); match(MINUS);
				setState(147); numericUnaryEpression();
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
				setState(148); numericOperandExpression();
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
	public static class NumericParenthesisContext extends NumericOperandExpressionContext {
		public TerminalNode LPAREN() { return getToken(DDMExpressionParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(DDMExpressionParser.RPAREN, 0); }
		public AdditionOrSubtractionExpressionContext additionOrSubtractionExpression() {
			return getRuleContext(AdditionOrSubtractionExpressionContext.class,0);
		}
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
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new ToNumericTermContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(151); numericTerm();
				}
				break;

			case 2:
				_localctx = new ToFunctionCallExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(152); functionCallExpression();
				}
				break;

			case 3:
				_localctx = new NumericParenthesisContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(153); match(LPAREN);
				setState(154); additionOrSubtractionExpression(0);
				setState(155); match(RPAREN);
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
			switch (_input.LA(1)) {
			case IntegerLiteral:
			case FloatingPointLiteral:
			case STRING:
				_localctx = new NumericLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(159); literal();
				}
				break;
			case IDENTIFIER:
				_localctx = new NumericVariableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(160); match(IDENTIFIER);
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
			setState(163); ((FunctionCallExpressionContext)_localctx).functionName = match(IDENTIFIER);
			setState(164); match(LPAREN);
			setState(166);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IntegerLiteral) | (1L << FloatingPointLiteral) | (1L << FALSE) | (1L << LBRACKET) | (1L << LPAREN) | (1L << MINUS) | (1L << NOT) | (1L << STRING) | (1L << TRUE) | (1L << IDENTIFIER))) != 0)) {
				{
				setState(165); functionParameters();
				}
			}

			setState(168); match(RPAREN);
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

	public static class FunctionParametersContext extends ParserRuleContext {
		public FunctionParameterContext functionParameter(int i) {
			return getRuleContext(FunctionParameterContext.class,i);
		}
		public List<FunctionParameterContext> functionParameter() {
			return getRuleContexts(FunctionParameterContext.class);
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
			setState(170); functionParameter();
			setState(175);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(171); match(COMMA);
				setState(172); functionParameter();
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

	public static class FunctionParameterContext extends ParserRuleContext {
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
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
			switch (_input.LA(1)) {
			case LBRACKET:
				enterOuterAlt(_localctx, 1);
				{
				setState(178); array();
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
				setState(179); logicalOrExpression(0);
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
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(182); floatingPointArray();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(183); integerArray();
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(184); stringArray();
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
	public static class ToFloatingPointArrayContext extends FloatingPointArrayContext {
		public TerminalNode RBRACKET() { return getToken(DDMExpressionParser.RBRACKET, 0); }
		public TerminalNode FloatingPointLiteral(int i) {
			return getToken(DDMExpressionParser.FloatingPointLiteral, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DDMExpressionParser.COMMA); }
		public TerminalNode LBRACKET() { return getToken(DDMExpressionParser.LBRACKET, 0); }
		public List<TerminalNode> FloatingPointLiteral() { return getTokens(DDMExpressionParser.FloatingPointLiteral); }
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
			setState(187); match(LBRACKET);
			setState(188); match(FloatingPointLiteral);
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(189); match(COMMA);
				setState(190); match(FloatingPointLiteral);
				}
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(196); match(RBRACKET);
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
	public static class ToIntegerArrayContext extends IntegerArrayContext {
		public TerminalNode RBRACKET() { return getToken(DDMExpressionParser.RBRACKET, 0); }
		public List<TerminalNode> COMMA() { return getTokens(DDMExpressionParser.COMMA); }
		public List<TerminalNode> IntegerLiteral() { return getTokens(DDMExpressionParser.IntegerLiteral); }
		public TerminalNode LBRACKET() { return getToken(DDMExpressionParser.LBRACKET, 0); }
		public TerminalNode IntegerLiteral(int i) {
			return getToken(DDMExpressionParser.IntegerLiteral, i);
		}
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
			setState(198); match(LBRACKET);
			setState(199); match(IntegerLiteral);
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(200); match(COMMA);
				setState(201); match(IntegerLiteral);
				}
				}
				setState(206);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(207); match(RBRACKET);
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
	public static class ToStringArrayContext extends StringArrayContext {
		public TerminalNode RBRACKET() { return getToken(DDMExpressionParser.RBRACKET, 0); }
		public TerminalNode STRING(int i) {
			return getToken(DDMExpressionParser.STRING, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(DDMExpressionParser.COMMA); }
		public TerminalNode LBRACKET() { return getToken(DDMExpressionParser.LBRACKET, 0); }
		public List<TerminalNode> STRING() { return getTokens(DDMExpressionParser.STRING); }
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
			setState(209); match(LBRACKET);
			setState(210); match(STRING);
			setState(215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(211); match(COMMA);
				setState(212); match(STRING);
				}
				}
				setState(217);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(218); match(RBRACKET);
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
			switch (_input.LA(1)) {
			case FloatingPointLiteral:
				_localctx = new FloatingPointLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(220); match(FloatingPointLiteral);
				}
				break;
			case IntegerLiteral:
				_localctx = new IntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(221); match(IntegerLiteral);
				}
				break;
			case STRING:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(222); match(STRING);
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
		case 1: return logicalOrExpression_sempred((LogicalOrExpressionContext)_localctx, predIndex);

		case 2: return logicalAndExpression_sempred((LogicalAndExpressionContext)_localctx, predIndex);

		case 3: return equalityExpression_sempred((EqualityExpressionContext)_localctx, predIndex);

		case 4: return comparisonExpression_sempred((ComparisonExpressionContext)_localctx, predIndex);

		case 8: return additionOrSubtractionExpression_sempred((AdditionOrSubtractionExpressionContext)_localctx, predIndex);

		case 9: return multiplicationOrDivisionExpression_sempred((MultiplicationOrDivisionExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean additionOrSubtractionExpression_sempred(AdditionOrSubtractionExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8: return precpred(_ctx, 3);

		case 9: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean logicalAndExpression_sempred(LogicalAndExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean equalityExpression_sempred(EqualityExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2: return precpred(_ctx, 3);

		case 3: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean comparisonExpression_sempred(ComparisonExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4: return precpred(_ctx, 5);

		case 5: return precpred(_ctx, 4);

		case 6: return precpred(_ctx, 3);

		case 7: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean multiplicationOrDivisionExpression_sempred(MultiplicationOrDivisionExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 10: return precpred(_ctx, 3);

		case 11: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean logicalOrExpression_sempred(LogicalOrExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\34\u00e4\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\7\3\66\n\3\f\3\16\39\13\3\3\4\3\4\3\4\3\4\3\4\3\4\7\4A\n\4\f\4"+
		"\16\4D\13\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5O\n\5\f\5\16\5R\13"+
		"\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6c\n"+
		"\6\f\6\16\6f\13\6\3\7\3\7\3\7\5\7k\n\7\3\b\3\b\3\b\3\b\3\b\3\b\5\bs\n"+
		"\b\3\t\3\t\5\tw\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u0082\n\n"+
		"\f\n\16\n\u0085\13\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\7\13"+
		"\u0090\n\13\f\13\16\13\u0093\13\13\3\f\3\f\3\f\5\f\u0098\n\f\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\5\r\u00a0\n\r\3\16\3\16\5\16\u00a4\n\16\3\17\3\17\3\17"+
		"\5\17\u00a9\n\17\3\17\3\17\3\20\3\20\3\20\7\20\u00b0\n\20\f\20\16\20\u00b3"+
		"\13\20\3\21\3\21\5\21\u00b7\n\21\3\22\3\22\3\22\5\22\u00bc\n\22\3\23\3"+
		"\23\3\23\3\23\7\23\u00c2\n\23\f\23\16\23\u00c5\13\23\3\23\3\23\3\24\3"+
		"\24\3\24\3\24\7\24\u00cd\n\24\f\24\16\24\u00d0\13\24\3\24\3\24\3\25\3"+
		"\25\3\25\3\25\7\25\u00d8\n\25\f\25\16\25\u00db\13\25\3\25\3\25\3\26\3"+
		"\26\3\26\5\26\u00e2\n\26\3\26\2\b\4\6\b\n\22\24\27\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*\2\3\4\2\n\n\32\32\u00ec\2,\3\2\2\2\4/\3\2\2"+
		"\2\6:\3\2\2\2\bE\3\2\2\2\nS\3\2\2\2\fj\3\2\2\2\16r\3\2\2\2\20v\3\2\2\2"+
		"\22x\3\2\2\2\24\u0086\3\2\2\2\26\u0097\3\2\2\2\30\u009f\3\2\2\2\32\u00a3"+
		"\3\2\2\2\34\u00a5\3\2\2\2\36\u00ac\3\2\2\2 \u00b6\3\2\2\2\"\u00bb\3\2"+
		"\2\2$\u00bd\3\2\2\2&\u00c8\3\2\2\2(\u00d3\3\2\2\2*\u00e1\3\2\2\2,-\5\4"+
		"\3\2-.\7\2\2\3.\3\3\2\2\2/\60\b\3\1\2\60\61\5\6\4\2\61\67\3\2\2\2\62\63"+
		"\f\4\2\2\63\64\7\25\2\2\64\66\5\6\4\2\65\62\3\2\2\2\669\3\2\2\2\67\65"+
		"\3\2\2\2\678\3\2\2\28\5\3\2\2\29\67\3\2\2\2:;\b\4\1\2;<\5\b\5\2<B\3\2"+
		"\2\2=>\f\4\2\2>?\7\6\2\2?A\5\b\5\2@=\3\2\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2"+
		"\2\2C\7\3\2\2\2DB\3\2\2\2EF\b\5\1\2FG\5\n\6\2GP\3\2\2\2HI\f\5\2\2IJ\7"+
		"\t\2\2JO\5\n\6\2KL\f\4\2\2LM\7\23\2\2MO\5\n\6\2NH\3\2\2\2NK\3\2\2\2OR"+
		"\3\2\2\2PN\3\2\2\2PQ\3\2\2\2Q\t\3\2\2\2RP\3\2\2\2ST\b\6\1\2TU\5\f\7\2"+
		"Ud\3\2\2\2VW\f\7\2\2WX\7\f\2\2Xc\5\22\n\2YZ\f\6\2\2Z[\7\13\2\2[c\5\22"+
		"\n\2\\]\f\5\2\2]^\7\20\2\2^c\5\22\n\2_`\f\4\2\2`a\7\16\2\2ac\5\22\n\2"+
		"bV\3\2\2\2bY\3\2\2\2b\\\3\2\2\2b_\3\2\2\2cf\3\2\2\2db\3\2\2\2de\3\2\2"+
		"\2e\13\3\2\2\2fd\3\2\2\2gh\7\24\2\2hk\5\f\7\2ik\5\16\b\2jg\3\2\2\2ji\3"+
		"\2\2\2k\r\3\2\2\2ls\5\20\t\2ms\5\22\n\2no\7\17\2\2op\5\4\3\2pq\7\30\2"+
		"\2qs\3\2\2\2rl\3\2\2\2rm\3\2\2\2rn\3\2\2\2s\17\3\2\2\2tw\t\2\2\2uw\7\33"+
		"\2\2vt\3\2\2\2vu\3\2\2\2w\21\3\2\2\2xy\b\n\1\2yz\5\24\13\2z\u0083\3\2"+
		"\2\2{|\f\5\2\2|}\7\26\2\2}\u0082\5\24\13\2~\177\f\4\2\2\177\u0080\7\21"+
		"\2\2\u0080\u0082\5\24\13\2\u0081{\3\2\2\2\u0081~\3\2\2\2\u0082\u0085\3"+
		"\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\23\3\2\2\2\u0085"+
		"\u0083\3\2\2\2\u0086\u0087\b\13\1\2\u0087\u0088\5\26\f\2\u0088\u0091\3"+
		"\2\2\2\u0089\u008a\f\5\2\2\u008a\u008b\7\22\2\2\u008b\u0090\5\26\f\2\u008c"+
		"\u008d\f\4\2\2\u008d\u008e\7\b\2\2\u008e\u0090\5\26\f\2\u008f\u0089\3"+
		"\2\2\2\u008f\u008c\3\2\2\2\u0090\u0093\3\2\2\2\u0091\u008f\3\2\2\2\u0091"+
		"\u0092\3\2\2\2\u0092\25\3\2\2\2\u0093\u0091\3\2\2\2\u0094\u0095\7\21\2"+
		"\2\u0095\u0098\5\26\f\2\u0096\u0098\5\30\r\2\u0097\u0094\3\2\2\2\u0097"+
		"\u0096\3\2\2\2\u0098\27\3\2\2\2\u0099\u00a0\5\32\16\2\u009a\u00a0\5\34"+
		"\17\2\u009b\u009c\7\17\2\2\u009c\u009d\5\22\n\2\u009d\u009e\7\30\2\2\u009e"+
		"\u00a0\3\2\2\2\u009f\u0099\3\2\2\2\u009f\u009a\3\2\2\2\u009f\u009b\3\2"+
		"\2\2\u00a0\31\3\2\2\2\u00a1\u00a4\5*\26\2\u00a2\u00a4\7\33\2\2\u00a3\u00a1"+
		"\3\2\2\2\u00a3\u00a2\3\2\2\2\u00a4\33\3\2\2\2\u00a5\u00a6\7\33\2\2\u00a6"+
		"\u00a8\7\17\2\2\u00a7\u00a9\5\36\20\2\u00a8\u00a7\3\2\2\2\u00a8\u00a9"+
		"\3\2\2\2\u00a9\u00aa\3\2\2\2\u00aa\u00ab\7\30\2\2\u00ab\35\3\2\2\2\u00ac"+
		"\u00b1\5 \21\2\u00ad\u00ae\7\7\2\2\u00ae\u00b0\5 \21\2\u00af\u00ad\3\2"+
		"\2\2\u00b0\u00b3\3\2\2\2\u00b1\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2"+
		"\37\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b4\u00b7\5\"\22\2\u00b5\u00b7\5\4\3"+
		"\2\u00b6\u00b4\3\2\2\2\u00b6\u00b5\3\2\2\2\u00b7!\3\2\2\2\u00b8\u00bc"+
		"\5$\23\2\u00b9\u00bc\5&\24\2\u00ba\u00bc\5(\25\2\u00bb\u00b8\3\2\2\2\u00bb"+
		"\u00b9\3\2\2\2\u00bb\u00ba\3\2\2\2\u00bc#\3\2\2\2\u00bd\u00be\7\r\2\2"+
		"\u00be\u00c3\7\4\2\2\u00bf\u00c0\7\7\2\2\u00c0\u00c2\7\4\2\2\u00c1\u00bf"+
		"\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4"+
		"\u00c6\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c6\u00c7\7\27\2\2\u00c7%\3\2\2\2"+
		"\u00c8\u00c9\7\r\2\2\u00c9\u00ce\7\3\2\2\u00ca\u00cb\7\7\2\2\u00cb\u00cd"+
		"\7\3\2\2\u00cc\u00ca\3\2\2\2\u00cd\u00d0\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce"+
		"\u00cf\3\2\2\2\u00cf\u00d1\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d1\u00d2\7\27"+
		"\2\2\u00d2\'\3\2\2\2\u00d3\u00d4\7\r\2\2\u00d4\u00d9\7\31\2\2\u00d5\u00d6"+
		"\7\7\2\2\u00d6\u00d8\7\31\2\2\u00d7\u00d5\3\2\2\2\u00d8\u00db\3\2\2\2"+
		"\u00d9\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00dc\3\2\2\2\u00db\u00d9"+
		"\3\2\2\2\u00dc\u00dd\7\27\2\2\u00dd)\3\2\2\2\u00de\u00e2\7\4\2\2\u00df"+
		"\u00e2\7\3\2\2\u00e0\u00e2\7\31\2\2\u00e1\u00de\3\2\2\2\u00e1\u00df\3"+
		"\2\2\2\u00e1\u00e0\3\2\2\2\u00e2+\3\2\2\2\32\67BNPbdjrv\u0081\u0083\u008f"+
		"\u0091\u0097\u009f\u00a3\u00a8\u00b1\u00b6\u00bb\u00c3\u00ce\u00d9\u00e1";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}