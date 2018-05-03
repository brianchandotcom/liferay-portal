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

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DDMExpressionParser}.
 *
 * @author Brian Wing Shun Chan
 */
public interface DDMExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code ToMultOrDiv} labeled
	 * alternative in {@link
	 * DDMExpressionParser#additionOrSubtractionExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToMultOrDiv(@NotNull DDMExpressionParser.ToMultOrDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToMultOrDiv} labeled alternative
	 * in {@link DDMExpressionParser#additionOrSubtractionExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToMultOrDiv(@NotNull DDMExpressionParser.ToMultOrDivContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NumericLiteral} labeled
	 * alternative in {@link DDMExpressionParser#numericTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void enterNumericLiteral(@NotNull DDMExpressionParser.NumericLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumericLiteral} labeled
	 * alternative in {@link DDMExpressionParser#numericTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void exitNumericLiteral(@NotNull DDMExpressionParser.NumericLiteralContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NotEqualsExpression} labeled
	 * alternative in {@link DDMExpressionParser#equalityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterNotEqualsExpression(@NotNull DDMExpressionParser.NotEqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotEqualsExpression} labeled
	 * alternative in {@link DDMExpressionParser#equalityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitNotEqualsExpression(@NotNull DDMExpressionParser.NotEqualsExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToBooleanOperandExpression}
	 * labeled alternative in {@link
	 * DDMExpressionParser#booleanUnaryExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToBooleanOperandExpression(@NotNull DDMExpressionParser.ToBooleanOperandExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToBooleanOperandExpression}
	 * labeled alternative in {@link
	 * DDMExpressionParser#booleanUnaryExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToBooleanOperandExpression(@NotNull DDMExpressionParser.ToBooleanOperandExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code GreaterThanExpression} labeled
	 * alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterGreaterThanExpression(@NotNull DDMExpressionParser.GreaterThanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterThanExpression} labeled
	 * alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitGreaterThanExpression(@NotNull DDMExpressionParser.GreaterThanExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NotExpression} labeled
	 * alternative in {@link DDMExpressionParser#booleanUnaryExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterNotExpression(@NotNull DDMExpressionParser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExpression} labeled
	 * alternative in {@link DDMExpressionParser#booleanUnaryExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitNotExpression(@NotNull DDMExpressionParser.NotExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code OrExpression} labeled
	 * alternative in {@link DDMExpressionParser#logicalOrExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterOrExpression(@NotNull DDMExpressionParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExpression} labeled
	 * alternative in {@link DDMExpressionParser#logicalOrExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitOrExpression(@NotNull DDMExpressionParser.OrExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code AndExpression} labeled
	 * alternative in {@link DDMExpressionParser#logicalAndExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAndExpression(@NotNull DDMExpressionParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExpression} labeled
	 * alternative in {@link DDMExpressionParser#logicalAndExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAndExpression(@NotNull DDMExpressionParser.AndExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToLogicalAndExpression} labeled
	 * alternative in {@link DDMExpressionParser#logicalOrExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToLogicalAndExpression(@NotNull DDMExpressionParser.ToLogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToLogicalAndExpression} labeled
	 * alternative in {@link DDMExpressionParser#logicalOrExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToLogicalAndExpression(@NotNull DDMExpressionParser.ToLogicalAndExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code Primary} labeled alternative in
	 * {@link DDMExpressionParser#numericUnaryEpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPrimary(@NotNull DDMExpressionParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Primary} labeled alternative in
	 * {@link DDMExpressionParser#numericUnaryEpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPrimary(@NotNull DDMExpressionParser.PrimaryContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * DDMExpressionParser#functionCallExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(@NotNull DDMExpressionParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * DDMExpressionParser#functionCallExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(@NotNull DDMExpressionParser.FunctionCallExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code DivisionExpression} labeled
	 * alternative in {@link
	 * DDMExpressionParser#multiplicationOrDivisionExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterDivisionExpression(@NotNull DDMExpressionParser.DivisionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DivisionExpression} labeled
	 * alternative in {@link
	 * DDMExpressionParser#multiplicationOrDivisionExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitDivisionExpression(@NotNull DDMExpressionParser.DivisionExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code BooleanParenthesis} labeled
	 * alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBooleanParenthesis(@NotNull DDMExpressionParser.BooleanParenthesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanParenthesis} labeled
	 * alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBooleanParenthesis(@NotNull DDMExpressionParser.BooleanParenthesisContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToBooleanUnaryExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToBooleanUnaryExpression(@NotNull DDMExpressionParser.ToBooleanUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToBooleanUnaryExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToBooleanUnaryExpression(@NotNull DDMExpressionParser.ToBooleanUnaryExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * DDMExpressionParser#functionParameters}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFunctionParameters(@NotNull DDMExpressionParser.FunctionParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * DDMExpressionParser#functionParameters}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFunctionParameters(@NotNull DDMExpressionParser.FunctionParametersContext ctx);

	/**
	 * Enter a parse tree produced by the {@code IntegerLiteral} labeled
	 * alternative in {@link DDMExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(@NotNull DDMExpressionParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntegerLiteral} labeled
	 * alternative in {@link DDMExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(@NotNull DDMExpressionParser.IntegerLiteralContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LogicalVariable} labeled
	 * alternative in {@link DDMExpressionParser#logicalTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLogicalVariable(@NotNull DDMExpressionParser.LogicalVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LogicalVariable} labeled
	 * alternative in {@link DDMExpressionParser#logicalTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLogicalVariable(@NotNull DDMExpressionParser.LogicalVariableContext ctx);

	/**
	 * Enter a parse tree produced by the {@code AdditionExpression} labeled
	 * alternative in {@link
	 * DDMExpressionParser#additionOrSubtractionExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAdditionExpression(@NotNull DDMExpressionParser.AdditionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AdditionExpression} labeled
	 * alternative in {@link
	 * DDMExpressionParser#additionOrSubtractionExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAdditionExpression(@NotNull DDMExpressionParser.AdditionExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link DDMExpressionParser#expression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull DDMExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMExpressionParser#expression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull DDMExpressionParser.ExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToEqualityExpression} labeled
	 * alternative in {@link DDMExpressionParser#logicalAndExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToEqualityExpression(@NotNull DDMExpressionParser.ToEqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToEqualityExpression} labeled
	 * alternative in {@link DDMExpressionParser#logicalAndExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToEqualityExpression(@NotNull DDMExpressionParser.ToEqualityExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LogicalConstant} labeled
	 * alternative in {@link DDMExpressionParser#logicalTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLogicalConstant(@NotNull DDMExpressionParser.LogicalConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LogicalConstant} labeled
	 * alternative in {@link DDMExpressionParser#logicalTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLogicalConstant(@NotNull DDMExpressionParser.LogicalConstantContext ctx);

	/**
	 * Enter a parse tree produced by the {@code
	 * ToAdditionOrSubtractionEpression} labeled alternative in {@link
	 * DDMExpressionParser#booleanOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToAdditionOrSubtractionEpression(@NotNull DDMExpressionParser.ToAdditionOrSubtractionEpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code
	 * ToAdditionOrSubtractionEpression} labeled alternative in {@link
	 * DDMExpressionParser#booleanOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToAdditionOrSubtractionEpression(@NotNull DDMExpressionParser.ToAdditionOrSubtractionEpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToFunctionCallExpression}
	 * labeled alternative in {@link
	 * DDMExpressionParser#numericOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToFunctionCallExpression(@NotNull DDMExpressionParser.ToFunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToFunctionCallExpression}
	 * labeled alternative in {@link
	 * DDMExpressionParser#numericOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToFunctionCallExpression(@NotNull DDMExpressionParser.ToFunctionCallExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NumericVariable} labeled
	 * alternative in {@link DDMExpressionParser#numericTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void enterNumericVariable(@NotNull DDMExpressionParser.NumericVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumericVariable} labeled
	 * alternative in {@link DDMExpressionParser#numericTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void exitNumericVariable(@NotNull DDMExpressionParser.NumericVariableContext ctx);

	/**
	 * Enter a parse tree produced by the {@code SubtractionExpression} labeled
	 * alternative in {@link
	 * DDMExpressionParser#additionOrSubtractionExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterSubtractionExpression(@NotNull DDMExpressionParser.SubtractionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubtractionExpression} labeled
	 * alternative in {@link
	 * DDMExpressionParser#additionOrSubtractionExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitSubtractionExpression(@NotNull DDMExpressionParser.SubtractionExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToNumericUnaryExpression}
	 * labeled alternative in {@link
	 * DDMExpressionParser#multiplicationOrDivisionExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToNumericUnaryExpression(@NotNull DDMExpressionParser.ToNumericUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToNumericUnaryExpression}
	 * labeled alternative in {@link
	 * DDMExpressionParser#multiplicationOrDivisionExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToNumericUnaryExpression(@NotNull DDMExpressionParser.ToNumericUnaryExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code GreaterThanOrEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterGreaterThanOrEqualsExpression(@NotNull DDMExpressionParser.GreaterThanOrEqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterThanOrEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitGreaterThanOrEqualsExpression(@NotNull DDMExpressionParser.GreaterThanOrEqualsExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToNumericTerm} labeled
	 * alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToNumericTerm(@NotNull DDMExpressionParser.ToNumericTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToNumericTerm} labeled
	 * alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToNumericTerm(@NotNull DDMExpressionParser.ToNumericTermContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NumericParenthesis} labeled
	 * alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterNumericParenthesis(@NotNull DDMExpressionParser.NumericParenthesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumericParenthesis} labeled
	 * alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitNumericParenthesis(@NotNull DDMExpressionParser.NumericParenthesisContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToLogicalTerm} labeled
	 * alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToLogicalTerm(@NotNull DDMExpressionParser.ToLogicalTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToLogicalTerm} labeled
	 * alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToLogicalTerm(@NotNull DDMExpressionParser.ToLogicalTermContext ctx);

	/**
	 * Enter a parse tree produced by the {@code StringLiteral} labeled
	 * alternative in {@link DDMExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(@NotNull DDMExpressionParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLiteral} labeled
	 * alternative in {@link DDMExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(@NotNull DDMExpressionParser.StringLiteralContext ctx);

	/**
	 * Enter a parse tree produced by the {@code MinusExpression} labeled
	 * alternative in {@link DDMExpressionParser#numericUnaryEpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterMinusExpression(@NotNull DDMExpressionParser.MinusExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MinusExpression} labeled
	 * alternative in {@link DDMExpressionParser#numericUnaryEpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitMinusExpression(@NotNull DDMExpressionParser.MinusExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code FloatingPointLiteral} labeled
	 * alternative in {@link DDMExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFloatingPointLiteral(@NotNull DDMExpressionParser.FloatingPointLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloatingPointLiteral} labeled
	 * alternative in {@link DDMExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFloatingPointLiteral(@NotNull DDMExpressionParser.FloatingPointLiteralContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToComparisonExpression} labeled
	 * alternative in {@link DDMExpressionParser#equalityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToComparisonExpression(@NotNull DDMExpressionParser.ToComparisonExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToComparisonExpression} labeled
	 * alternative in {@link DDMExpressionParser#equalityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToComparisonExpression(@NotNull DDMExpressionParser.ToComparisonExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LessThanOrEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLessThanOrEqualsExpression(@NotNull DDMExpressionParser.LessThanOrEqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessThanOrEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLessThanOrEqualsExpression(@NotNull DDMExpressionParser.LessThanOrEqualsExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code MultiplicationExpression}
	 * labeled alternative in {@link
	 * DDMExpressionParser#multiplicationOrDivisionExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterMultiplicationExpression(@NotNull DDMExpressionParser.MultiplicationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MultiplicationExpression}
	 * labeled alternative in {@link
	 * DDMExpressionParser#multiplicationOrDivisionExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitMultiplicationExpression(@NotNull DDMExpressionParser.MultiplicationExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code EqualsExpression} labeled
	 * alternative in {@link DDMExpressionParser#equalityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterEqualsExpression(@NotNull DDMExpressionParser.EqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EqualsExpression} labeled
	 * alternative in {@link DDMExpressionParser#equalityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitEqualsExpression(@NotNull DDMExpressionParser.EqualsExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LessThanExpression} labeled
	 * alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLessThanExpression(@NotNull DDMExpressionParser.LessThanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessThanExpression} labeled
	 * alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLessThanExpression(@NotNull DDMExpressionParser.LessThanExpressionContext ctx);
}