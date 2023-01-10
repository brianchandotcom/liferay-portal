// Generated from IndividualSegmentsExpression.g4 by ANTLR 4.3

/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.expression.parser;

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
public class IndividualSegmentsExpressionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__6=1, T__5=2, T__4=3, T__3=4, T__2=5, T__1=6, T__0=7, AND=8, COMMA=9, 
		EQ=10, FLOATING_POINT_LITERAL=11, NEQ=12, GE=13, GT=14, INTEGER_LITERAL=15, 
		LE=16, LPAREN=17, RPAREN=18, LT=19, NOT=20, OR=21, STRING_LITERAL=22, 
		VARIABLE_IDENTIFIER=23, WS=24;
	public static final String[] tokenNames = {
		"<INVALID>", "'value='", "'.filter(filter='", "'operator='", "'null'", 
		"'true'", "'.filterByCount(filter='", "'false'", "AND", "','", "EQ", "FLOATING_POINT_LITERAL", 
		"'ne'", "'ge'", "'gt'", "INTEGER_LITERAL", "'le'", "'('", "')'", "'lt'", 
		"NOT", "OR", "STRING_LITERAL", "VARIABLE_IDENTIFIER", "WS"
	};
	public static final int
		RULE_booleanOperandExpression = 0, RULE_booleanUnaryExpression = 1, RULE_comparisonExpression = 2, 
		RULE_equalityExpression = 3, RULE_expression = 4, RULE_filterExpression = 5, 
		RULE_filterByCountExpression = 6, RULE_functionCallExpression = 7, RULE_functionParameters = 8, 
		RULE_literal = 9, RULE_logicalAndExpression = 10, RULE_logicalOrExpression = 11, 
		RULE_logicalTerm = 12;
	public static final String[] ruleNames = {
		"booleanOperandExpression", "booleanUnaryExpression", "comparisonExpression", 
		"equalityExpression", "expression", "filterExpression", "filterByCountExpression", 
		"functionCallExpression", "functionParameters", "literal", "logicalAndExpression", 
		"logicalOrExpression", "logicalTerm"
	};

	@Override
	public String getGrammarFileName() { return "IndividualSegmentsExpression.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public IndividualSegmentsExpressionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
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
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterToLogicalTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitToLogicalTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitToLogicalTerm(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BooleanParenthesisContext extends BooleanOperandExpressionContext {
		public TerminalNode LPAREN() { return getToken(IndividualSegmentsExpressionParser.LPAREN, 0); }
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(IndividualSegmentsExpressionParser.RPAREN, 0); }
		public BooleanParenthesisContext(BooleanOperandExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterBooleanParenthesis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitBooleanParenthesis(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitBooleanParenthesis(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanOperandExpressionContext booleanOperandExpression() throws RecognitionException {
		BooleanOperandExpressionContext _localctx = new BooleanOperandExpressionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_booleanOperandExpression);
		try {
			setState(31);
			switch (_input.LA(1)) {
			case T__3:
			case T__2:
			case T__0:
			case FLOATING_POINT_LITERAL:
			case INTEGER_LITERAL:
			case STRING_LITERAL:
			case VARIABLE_IDENTIFIER:
				_localctx = new ToLogicalTermContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(26); logicalTerm();
				}
				break;
			case LPAREN:
				_localctx = new BooleanParenthesisContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(27); match(LPAREN);
				setState(28); logicalOrExpression(0);
				setState(29); match(RPAREN);
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
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterToBooleanOperandExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitToBooleanOperandExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitToBooleanOperandExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotExpressionContext extends BooleanUnaryExpressionContext {
		public TerminalNode NOT() { return getToken(IndividualSegmentsExpressionParser.NOT, 0); }
		public BooleanUnaryExpressionContext booleanUnaryExpression() {
			return getRuleContext(BooleanUnaryExpressionContext.class,0);
		}
		public NotExpressionContext(BooleanUnaryExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterNotExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitNotExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitNotExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanUnaryExpressionContext booleanUnaryExpression() throws RecognitionException {
		BooleanUnaryExpressionContext _localctx = new BooleanUnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_booleanUnaryExpression);
		try {
			setState(36);
			switch (_input.LA(1)) {
			case NOT:
				_localctx = new NotExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(33); match(NOT);
				setState(34); booleanUnaryExpression();
				}
				break;
			case T__3:
			case T__2:
			case T__0:
			case FLOATING_POINT_LITERAL:
			case INTEGER_LITERAL:
			case LPAREN:
			case STRING_LITERAL:
			case VARIABLE_IDENTIFIER:
				_localctx = new ToBooleanOperandExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(35); booleanOperandExpression();
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
		public TerminalNode GE() { return getToken(IndividualSegmentsExpressionParser.GE, 0); }
		public BooleanOperandExpressionContext booleanOperandExpression() {
			return getRuleContext(BooleanOperandExpressionContext.class,0);
		}
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(IndividualSegmentsExpressionParser.VARIABLE_IDENTIFIER, 0); }
		public GreaterThanOrEqualsExpressionContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterGreaterThanOrEqualsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitGreaterThanOrEqualsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitGreaterThanOrEqualsExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessThanOrEqualsExpressionContext extends ComparisonExpressionContext {
		public BooleanOperandExpressionContext booleanOperandExpression() {
			return getRuleContext(BooleanOperandExpressionContext.class,0);
		}
		public TerminalNode LE() { return getToken(IndividualSegmentsExpressionParser.LE, 0); }
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(IndividualSegmentsExpressionParser.VARIABLE_IDENTIFIER, 0); }
		public LessThanOrEqualsExpressionContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterLessThanOrEqualsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitLessThanOrEqualsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitLessThanOrEqualsExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GreaterThanExpressionContext extends ComparisonExpressionContext {
		public TerminalNode GT() { return getToken(IndividualSegmentsExpressionParser.GT, 0); }
		public BooleanOperandExpressionContext booleanOperandExpression() {
			return getRuleContext(BooleanOperandExpressionContext.class,0);
		}
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(IndividualSegmentsExpressionParser.VARIABLE_IDENTIFIER, 0); }
		public GreaterThanExpressionContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterGreaterThanExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitGreaterThanExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitGreaterThanExpression(this);
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
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterToBooleanUnaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitToBooleanUnaryExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitToBooleanUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessThanExpressionContext extends ComparisonExpressionContext {
		public TerminalNode LT() { return getToken(IndividualSegmentsExpressionParser.LT, 0); }
		public BooleanOperandExpressionContext booleanOperandExpression() {
			return getRuleContext(BooleanOperandExpressionContext.class,0);
		}
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(IndividualSegmentsExpressionParser.VARIABLE_IDENTIFIER, 0); }
		public LessThanExpressionContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterLessThanExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitLessThanExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitLessThanExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonExpressionContext comparisonExpression() throws RecognitionException {
		ComparisonExpressionContext _localctx = new ComparisonExpressionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_comparisonExpression);
		try {
			setState(51);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				_localctx = new GreaterThanExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(38); match(VARIABLE_IDENTIFIER);
				setState(39); match(GT);
				setState(40); booleanOperandExpression();
				}
				break;

			case 2:
				_localctx = new GreaterThanOrEqualsExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(41); match(VARIABLE_IDENTIFIER);
				setState(42); match(GE);
				setState(43); booleanOperandExpression();
				}
				break;

			case 3:
				_localctx = new LessThanExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(44); match(VARIABLE_IDENTIFIER);
				setState(45); match(LT);
				setState(46); booleanOperandExpression();
				}
				break;

			case 4:
				_localctx = new LessThanOrEqualsExpressionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(47); match(VARIABLE_IDENTIFIER);
				setState(48); match(LE);
				setState(49); booleanOperandExpression();
				}
				break;

			case 5:
				_localctx = new ToBooleanUnaryExpressionContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(50); booleanUnaryExpression();
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
		public TerminalNode NEQ() { return getToken(IndividualSegmentsExpressionParser.NEQ, 0); }
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(IndividualSegmentsExpressionParser.VARIABLE_IDENTIFIER, 0); }
		public NotEqualsExpressionContext(EqualityExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterNotEqualsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitNotEqualsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitNotEqualsExpression(this);
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
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterToComparisonExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitToComparisonExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitToComparisonExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualsExpressionContext extends EqualityExpressionContext {
		public ComparisonExpressionContext comparisonExpression() {
			return getRuleContext(ComparisonExpressionContext.class,0);
		}
		public TerminalNode EQ() { return getToken(IndividualSegmentsExpressionParser.EQ, 0); }
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(IndividualSegmentsExpressionParser.VARIABLE_IDENTIFIER, 0); }
		public EqualsExpressionContext(EqualityExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterEqualsExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitEqualsExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitEqualsExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExpressionContext equalityExpression() throws RecognitionException {
		EqualityExpressionContext _localctx = new EqualityExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_equalityExpression);
		try {
			setState(60);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				_localctx = new EqualsExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(53); match(VARIABLE_IDENTIFIER);
				setState(54); match(EQ);
				setState(55); comparisonExpression();
				}
				break;

			case 2:
				_localctx = new NotEqualsExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(56); match(VARIABLE_IDENTIFIER);
				setState(57); match(NEQ);
				setState(58); comparisonExpression();
				}
				break;

			case 3:
				_localctx = new ToComparisonExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(59); comparisonExpression();
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

	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(IndividualSegmentsExpressionParser.EOF, 0); }
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62); logicalOrExpression(0);
			setState(63); match(EOF);
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

	public static class FilterExpressionContext extends ParserRuleContext {
		public Token filterType;
		public Token filter;
		public TerminalNode STRING_LITERAL() { return getToken(IndividualSegmentsExpressionParser.STRING_LITERAL, 0); }
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(IndividualSegmentsExpressionParser.VARIABLE_IDENTIFIER, 0); }
		public FilterExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filterExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterFilterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitFilterExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitFilterExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FilterExpressionContext filterExpression() throws RecognitionException {
		FilterExpressionContext _localctx = new FilterExpressionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_filterExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65); ((FilterExpressionContext)_localctx).filterType = match(VARIABLE_IDENTIFIER);
			setState(66); match(T__5);
			setState(67); ((FilterExpressionContext)_localctx).filter = match(STRING_LITERAL);
			setState(68); match(RPAREN);
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

	public static class FilterByCountExpressionContext extends ParserRuleContext {
		public Token filterType;
		public Token filter;
		public Token operator;
		public Token value;
		public TerminalNode INTEGER_LITERAL() { return getToken(IndividualSegmentsExpressionParser.INTEGER_LITERAL, 0); }
		public List<TerminalNode> COMMA() { return getTokens(IndividualSegmentsExpressionParser.COMMA); }
		public List<TerminalNode> STRING_LITERAL() { return getTokens(IndividualSegmentsExpressionParser.STRING_LITERAL); }
		public TerminalNode STRING_LITERAL(int i) {
			return getToken(IndividualSegmentsExpressionParser.STRING_LITERAL, i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(IndividualSegmentsExpressionParser.COMMA, i);
		}
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(IndividualSegmentsExpressionParser.VARIABLE_IDENTIFIER, 0); }
		public FilterByCountExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filterByCountExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterFilterByCountExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitFilterByCountExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitFilterByCountExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FilterByCountExpressionContext filterByCountExpression() throws RecognitionException {
		FilterByCountExpressionContext _localctx = new FilterByCountExpressionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_filterByCountExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70); ((FilterByCountExpressionContext)_localctx).filterType = match(VARIABLE_IDENTIFIER);
			setState(71); match(T__1);
			setState(72); ((FilterByCountExpressionContext)_localctx).filter = match(STRING_LITERAL);
			setState(73); match(COMMA);
			setState(74); match(T__4);
			setState(75); ((FilterByCountExpressionContext)_localctx).operator = match(STRING_LITERAL);
			setState(76); match(COMMA);
			setState(77); match(T__6);
			setState(78); ((FilterByCountExpressionContext)_localctx).value = match(INTEGER_LITERAL);
			setState(79); match(RPAREN);
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
		public TerminalNode LPAREN() { return getToken(IndividualSegmentsExpressionParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(IndividualSegmentsExpressionParser.RPAREN, 0); }
		public FunctionParametersContext functionParameters() {
			return getRuleContext(FunctionParametersContext.class,0);
		}
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(IndividualSegmentsExpressionParser.VARIABLE_IDENTIFIER, 0); }
		public FunctionCallExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCallExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterFunctionCallExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitFunctionCallExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitFunctionCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallExpressionContext functionCallExpression() throws RecognitionException {
		FunctionCallExpressionContext _localctx = new FunctionCallExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_functionCallExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81); ((FunctionCallExpressionContext)_localctx).functionName = match(VARIABLE_IDENTIFIER);
			setState(82); match(LPAREN);
			setState(83); functionParameters();
			setState(84); match(RPAREN);
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
		public TerminalNode COMMA() { return getToken(IndividualSegmentsExpressionParser.COMMA, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode VARIABLE_IDENTIFIER() { return getToken(IndividualSegmentsExpressionParser.VARIABLE_IDENTIFIER, 0); }
		public FunctionParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterFunctionParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitFunctionParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitFunctionParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionParametersContext functionParameters() throws RecognitionException {
		FunctionParametersContext _localctx = new FunctionParametersContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_functionParameters);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86); match(VARIABLE_IDENTIFIER);
			setState(87); match(COMMA);
			setState(88); literal();
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
		public TerminalNode STRING_LITERAL() { return getToken(IndividualSegmentsExpressionParser.STRING_LITERAL, 0); }
		public StringLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FloatingPointLiteralContext extends LiteralContext {
		public TerminalNode FLOATING_POINT_LITERAL() { return getToken(IndividualSegmentsExpressionParser.FLOATING_POINT_LITERAL, 0); }
		public FloatingPointLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterFloatingPointLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitFloatingPointLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitFloatingPointLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BooleanLiteralContext extends LiteralContext {
		public BooleanLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitBooleanLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NullLiteralContext extends LiteralContext {
		public NullLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterNullLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitNullLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitNullLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntegerLiteralContext extends LiteralContext {
		public TerminalNode INTEGER_LITERAL() { return getToken(IndividualSegmentsExpressionParser.INTEGER_LITERAL, 0); }
		public IntegerLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitIntegerLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitIntegerLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_literal);
		int _la;
		try {
			setState(95);
			switch (_input.LA(1)) {
			case FLOATING_POINT_LITERAL:
				_localctx = new FloatingPointLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(90); match(FLOATING_POINT_LITERAL);
				}
				break;
			case INTEGER_LITERAL:
				_localctx = new IntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(91); match(INTEGER_LITERAL);
				}
				break;
			case T__2:
			case T__0:
				_localctx = new BooleanLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(92);
				_la = _input.LA(1);
				if ( !(_la==T__2 || _la==T__0) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
				break;
			case T__3:
				_localctx = new NullLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(93); match(T__3);
				}
				break;
			case STRING_LITERAL:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(94); match(STRING_LITERAL);
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
		public TerminalNode AND() { return getToken(IndividualSegmentsExpressionParser.AND, 0); }
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public AndExpressionContext(LogicalAndExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitAndExpression(this);
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
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterToEqualityExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitToEqualityExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitToEqualityExpression(this);
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
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_logicalAndExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ToEqualityExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(98); equalityExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(105);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new AndExpressionContext(new LogicalAndExpressionContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_logicalAndExpression);
					setState(100);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(101); match(AND);
					setState(102); equalityExpression();
					}
					} 
				}
				setState(107);
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
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterToLogicalAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitToLogicalAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitToLogicalAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrExpressionContext extends LogicalOrExpressionContext {
		public LogicalOrExpressionContext logicalOrExpression() {
			return getRuleContext(LogicalOrExpressionContext.class,0);
		}
		public TerminalNode OR() { return getToken(IndividualSegmentsExpressionParser.OR, 0); }
		public LogicalAndExpressionContext logicalAndExpression() {
			return getRuleContext(LogicalAndExpressionContext.class,0);
		}
		public OrExpressionContext(LogicalOrExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitOrExpression(this);
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
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_logicalOrExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ToLogicalAndExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(109); logicalAndExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(116);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new OrExpressionContext(new LogicalOrExpressionContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_logicalOrExpression);
					setState(111);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(112); match(OR);
					setState(113); logicalAndExpression(0);
					}
					} 
				}
				setState(118);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
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
	public static class ToFilterByCountExpressionContext extends LogicalTermContext {
		public FilterByCountExpressionContext filterByCountExpression() {
			return getRuleContext(FilterByCountExpressionContext.class,0);
		}
		public ToFilterByCountExpressionContext(LogicalTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterToFilterByCountExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitToFilterByCountExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitToFilterByCountExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ToFilterExpressionContext extends LogicalTermContext {
		public FilterExpressionContext filterExpression() {
			return getRuleContext(FilterExpressionContext.class,0);
		}
		public ToFilterExpressionContext(LogicalTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterToFilterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitToFilterExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitToFilterExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ToLiteralContext extends LogicalTermContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ToLiteralContext(LogicalTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterToLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitToLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitToLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ToFunctionCallExpressionContext extends LogicalTermContext {
		public FunctionCallExpressionContext functionCallExpression() {
			return getRuleContext(FunctionCallExpressionContext.class,0);
		}
		public ToFunctionCallExpressionContext(LogicalTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).enterToFunctionCallExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof IndividualSegmentsExpressionListener ) ((IndividualSegmentsExpressionListener)listener).exitToFunctionCallExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof IndividualSegmentsExpressionVisitor ) return ((IndividualSegmentsExpressionVisitor<? extends T>)visitor).visitToFunctionCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalTermContext logicalTerm() throws RecognitionException {
		LogicalTermContext _localctx = new LogicalTermContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_logicalTerm);
		try {
			setState(123);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				_localctx = new ToLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(119); literal();
				}
				break;

			case 2:
				_localctx = new ToFunctionCallExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(120); functionCallExpression();
				}
				break;

			case 3:
				_localctx = new ToFilterExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(121); filterExpression();
				}
				break;

			case 4:
				_localctx = new ToFilterByCountExpressionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(122); filterByCountExpression();
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10: return logicalAndExpression_sempred((LogicalAndExpressionContext)_localctx, predIndex);

		case 11: return logicalOrExpression_sempred((LogicalOrExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean logicalAndExpression_sempred(LogicalAndExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean logicalOrExpression_sempred(LogicalOrExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\32\u0080\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\2\3\2\5\2\"\n\2\3\3\3\3"+
		"\3\3\5\3\'\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4"+
		"\66\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5?\n\5\3\6\3\6\3\6\3\7\3\7\3\7\3"+
		"\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\5\13b\n\13\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\7\fj\n\f\f\f\16\fm\13\f\3\r\3\r\3\r\3\r\3\r\3\r\7\ru\n\r\f\r\16"+
		"\rx\13\r\3\16\3\16\3\16\3\16\5\16~\n\16\3\16\2\4\26\30\17\2\4\6\b\n\f"+
		"\16\20\22\24\26\30\32\2\3\4\2\7\7\t\t\u0083\2!\3\2\2\2\4&\3\2\2\2\6\65"+
		"\3\2\2\2\b>\3\2\2\2\n@\3\2\2\2\fC\3\2\2\2\16H\3\2\2\2\20S\3\2\2\2\22X"+
		"\3\2\2\2\24a\3\2\2\2\26c\3\2\2\2\30n\3\2\2\2\32}\3\2\2\2\34\"\5\32\16"+
		"\2\35\36\7\23\2\2\36\37\5\30\r\2\37 \7\24\2\2 \"\3\2\2\2!\34\3\2\2\2!"+
		"\35\3\2\2\2\"\3\3\2\2\2#$\7\26\2\2$\'\5\4\3\2%\'\5\2\2\2&#\3\2\2\2&%\3"+
		"\2\2\2\'\5\3\2\2\2()\7\31\2\2)*\7\20\2\2*\66\5\2\2\2+,\7\31\2\2,-\7\17"+
		"\2\2-\66\5\2\2\2./\7\31\2\2/\60\7\25\2\2\60\66\5\2\2\2\61\62\7\31\2\2"+
		"\62\63\7\22\2\2\63\66\5\2\2\2\64\66\5\4\3\2\65(\3\2\2\2\65+\3\2\2\2\65"+
		".\3\2\2\2\65\61\3\2\2\2\65\64\3\2\2\2\66\7\3\2\2\2\678\7\31\2\289\7\f"+
		"\2\29?\5\6\4\2:;\7\31\2\2;<\7\16\2\2<?\5\6\4\2=?\5\6\4\2>\67\3\2\2\2>"+
		":\3\2\2\2>=\3\2\2\2?\t\3\2\2\2@A\5\30\r\2AB\7\2\2\3B\13\3\2\2\2CD\7\31"+
		"\2\2DE\7\4\2\2EF\7\30\2\2FG\7\24\2\2G\r\3\2\2\2HI\7\31\2\2IJ\7\b\2\2J"+
		"K\7\30\2\2KL\7\13\2\2LM\7\5\2\2MN\7\30\2\2NO\7\13\2\2OP\7\3\2\2PQ\7\21"+
		"\2\2QR\7\24\2\2R\17\3\2\2\2ST\7\31\2\2TU\7\23\2\2UV\5\22\n\2VW\7\24\2"+
		"\2W\21\3\2\2\2XY\7\31\2\2YZ\7\13\2\2Z[\5\24\13\2[\23\3\2\2\2\\b\7\r\2"+
		"\2]b\7\21\2\2^b\t\2\2\2_b\7\6\2\2`b\7\30\2\2a\\\3\2\2\2a]\3\2\2\2a^\3"+
		"\2\2\2a_\3\2\2\2a`\3\2\2\2b\25\3\2\2\2cd\b\f\1\2de\5\b\5\2ek\3\2\2\2f"+
		"g\f\4\2\2gh\7\n\2\2hj\5\b\5\2if\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2"+
		"l\27\3\2\2\2mk\3\2\2\2no\b\r\1\2op\5\26\f\2pv\3\2\2\2qr\f\4\2\2rs\7\27"+
		"\2\2su\5\26\f\2tq\3\2\2\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2w\31\3\2\2\2xv"+
		"\3\2\2\2y~\5\24\13\2z~\5\20\t\2{~\5\f\7\2|~\5\16\b\2}y\3\2\2\2}z\3\2\2"+
		"\2}{\3\2\2\2}|\3\2\2\2~\33\3\2\2\2\n!&\65>akv}";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}