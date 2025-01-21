// Generated from DDMExpression.g4 by ANTLR 4.13.2

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.internal.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DDMExpressionParser}.
 */
public interface DDMExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DDMExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(DDMExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(DDMExpressionParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToLogicalAndExpression}
	 * labeled alternative in {@link DDMExpressionParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterToLogicalAndExpression(DDMExpressionParser.ToLogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToLogicalAndExpression}
	 * labeled alternative in {@link DDMExpressionParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitToLogicalAndExpression(DDMExpressionParser.ToLogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrExpression}
	 * labeled alternative in {@link DDMExpressionParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(DDMExpressionParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExpression}
	 * labeled alternative in {@link DDMExpressionParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(DDMExpressionParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AndExpression}
	 * labeled alternative in {@link DDMExpressionParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(DDMExpressionParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExpression}
	 * labeled alternative in {@link DDMExpressionParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(DDMExpressionParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToEqualityExpression}
	 * labeled alternative in {@link DDMExpressionParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterToEqualityExpression(DDMExpressionParser.ToEqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToEqualityExpression}
	 * labeled alternative in {@link DDMExpressionParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitToEqualityExpression(DDMExpressionParser.ToEqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterNotEqualsExpression(DDMExpressionParser.NotEqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitNotEqualsExpression(DDMExpressionParser.NotEqualsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToComparisonExpression}
	 * labeled alternative in {@link DDMExpressionParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterToComparisonExpression(DDMExpressionParser.ToComparisonExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToComparisonExpression}
	 * labeled alternative in {@link DDMExpressionParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitToComparisonExpression(DDMExpressionParser.ToComparisonExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualsExpression(DDMExpressionParser.EqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualsExpression(DDMExpressionParser.EqualsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GreaterThanOrEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void enterGreaterThanOrEqualsExpression(DDMExpressionParser.GreaterThanOrEqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterThanOrEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void exitGreaterThanOrEqualsExpression(DDMExpressionParser.GreaterThanOrEqualsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LessThanOrEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void enterLessThanOrEqualsExpression(DDMExpressionParser.LessThanOrEqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessThanOrEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void exitLessThanOrEqualsExpression(DDMExpressionParser.LessThanOrEqualsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GreaterThanExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void enterGreaterThanExpression(DDMExpressionParser.GreaterThanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterThanExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void exitGreaterThanExpression(DDMExpressionParser.GreaterThanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToBooleanUnaryExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void enterToBooleanUnaryExpression(DDMExpressionParser.ToBooleanUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToBooleanUnaryExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void exitToBooleanUnaryExpression(DDMExpressionParser.ToBooleanUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LessThanExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void enterLessThanExpression(DDMExpressionParser.LessThanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessThanExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void exitLessThanExpression(DDMExpressionParser.LessThanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotExpression}
	 * labeled alternative in {@link DDMExpressionParser#booleanUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpression(DDMExpressionParser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExpression}
	 * labeled alternative in {@link DDMExpressionParser#booleanUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpression(DDMExpressionParser.NotExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToBooleanOperandExpression}
	 * labeled alternative in {@link DDMExpressionParser#booleanUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterToBooleanOperandExpression(DDMExpressionParser.ToBooleanOperandExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToBooleanOperandExpression}
	 * labeled alternative in {@link DDMExpressionParser#booleanUnaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitToBooleanOperandExpression(DDMExpressionParser.ToBooleanOperandExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToLogicalTerm}
	 * labeled alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 * @param ctx the parse tree
	 */
	void enterToLogicalTerm(DDMExpressionParser.ToLogicalTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToLogicalTerm}
	 * labeled alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 * @param ctx the parse tree
	 */
	void exitToLogicalTerm(DDMExpressionParser.ToLogicalTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToAdditionOrSubtractionEpression}
	 * labeled alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 * @param ctx the parse tree
	 */
	void enterToAdditionOrSubtractionEpression(DDMExpressionParser.ToAdditionOrSubtractionEpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToAdditionOrSubtractionEpression}
	 * labeled alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 * @param ctx the parse tree
	 */
	void exitToAdditionOrSubtractionEpression(DDMExpressionParser.ToAdditionOrSubtractionEpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanParenthesis}
	 * labeled alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanParenthesis(DDMExpressionParser.BooleanParenthesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanParenthesis}
	 * labeled alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanParenthesis(DDMExpressionParser.BooleanParenthesisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LogicalConstant}
	 * labeled alternative in {@link DDMExpressionParser#logicalTerm}.
	 * @param ctx the parse tree
	 */
	void enterLogicalConstant(DDMExpressionParser.LogicalConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LogicalConstant}
	 * labeled alternative in {@link DDMExpressionParser#logicalTerm}.
	 * @param ctx the parse tree
	 */
	void exitLogicalConstant(DDMExpressionParser.LogicalConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LogicalVariable}
	 * labeled alternative in {@link DDMExpressionParser#logicalTerm}.
	 * @param ctx the parse tree
	 */
	void enterLogicalVariable(DDMExpressionParser.LogicalVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LogicalVariable}
	 * labeled alternative in {@link DDMExpressionParser#logicalTerm}.
	 * @param ctx the parse tree
	 */
	void exitLogicalVariable(DDMExpressionParser.LogicalVariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AdditionExpression}
	 * labeled alternative in {@link DDMExpressionParser#additionOrSubtractionExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditionExpression(DDMExpressionParser.AdditionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AdditionExpression}
	 * labeled alternative in {@link DDMExpressionParser#additionOrSubtractionExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditionExpression(DDMExpressionParser.AdditionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubtractionExpression}
	 * labeled alternative in {@link DDMExpressionParser#additionOrSubtractionExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubtractionExpression(DDMExpressionParser.SubtractionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubtractionExpression}
	 * labeled alternative in {@link DDMExpressionParser#additionOrSubtractionExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubtractionExpression(DDMExpressionParser.SubtractionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToMultOrDiv}
	 * labeled alternative in {@link DDMExpressionParser#additionOrSubtractionExpression}.
	 * @param ctx the parse tree
	 */
	void enterToMultOrDiv(DDMExpressionParser.ToMultOrDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToMultOrDiv}
	 * labeled alternative in {@link DDMExpressionParser#additionOrSubtractionExpression}.
	 * @param ctx the parse tree
	 */
	void exitToMultOrDiv(DDMExpressionParser.ToMultOrDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToNumericUnaryExpression}
	 * labeled alternative in {@link DDMExpressionParser#multiplicationOrDivisionExpression}.
	 * @param ctx the parse tree
	 */
	void enterToNumericUnaryExpression(DDMExpressionParser.ToNumericUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToNumericUnaryExpression}
	 * labeled alternative in {@link DDMExpressionParser#multiplicationOrDivisionExpression}.
	 * @param ctx the parse tree
	 */
	void exitToNumericUnaryExpression(DDMExpressionParser.ToNumericUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DivisionExpression}
	 * labeled alternative in {@link DDMExpressionParser#multiplicationOrDivisionExpression}.
	 * @param ctx the parse tree
	 */
	void enterDivisionExpression(DDMExpressionParser.DivisionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DivisionExpression}
	 * labeled alternative in {@link DDMExpressionParser#multiplicationOrDivisionExpression}.
	 * @param ctx the parse tree
	 */
	void exitDivisionExpression(DDMExpressionParser.DivisionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MultiplicationExpression}
	 * labeled alternative in {@link DDMExpressionParser#multiplicationOrDivisionExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicationExpression(DDMExpressionParser.MultiplicationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MultiplicationExpression}
	 * labeled alternative in {@link DDMExpressionParser#multiplicationOrDivisionExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicationExpression(DDMExpressionParser.MultiplicationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MinusExpression}
	 * labeled alternative in {@link DDMExpressionParser#numericUnaryEpression}.
	 * @param ctx the parse tree
	 */
	void enterMinusExpression(DDMExpressionParser.MinusExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MinusExpression}
	 * labeled alternative in {@link DDMExpressionParser#numericUnaryEpression}.
	 * @param ctx the parse tree
	 */
	void exitMinusExpression(DDMExpressionParser.MinusExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Primary}
	 * labeled alternative in {@link DDMExpressionParser#numericUnaryEpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(DDMExpressionParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Primary}
	 * labeled alternative in {@link DDMExpressionParser#numericUnaryEpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(DDMExpressionParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToNumericTerm}
	 * labeled alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 * @param ctx the parse tree
	 */
	void enterToNumericTerm(DDMExpressionParser.ToNumericTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToNumericTerm}
	 * labeled alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 * @param ctx the parse tree
	 */
	void exitToNumericTerm(DDMExpressionParser.ToNumericTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToFunctionCallExpression}
	 * labeled alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 * @param ctx the parse tree
	 */
	void enterToFunctionCallExpression(DDMExpressionParser.ToFunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToFunctionCallExpression}
	 * labeled alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 * @param ctx the parse tree
	 */
	void exitToFunctionCallExpression(DDMExpressionParser.ToFunctionCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumericParenthesis}
	 * labeled alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 * @param ctx the parse tree
	 */
	void enterNumericParenthesis(DDMExpressionParser.NumericParenthesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumericParenthesis}
	 * labeled alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 * @param ctx the parse tree
	 */
	void exitNumericParenthesis(DDMExpressionParser.NumericParenthesisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumericLiteral}
	 * labeled alternative in {@link DDMExpressionParser#numericTerm}.
	 * @param ctx the parse tree
	 */
	void enterNumericLiteral(DDMExpressionParser.NumericLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumericLiteral}
	 * labeled alternative in {@link DDMExpressionParser#numericTerm}.
	 * @param ctx the parse tree
	 */
	void exitNumericLiteral(DDMExpressionParser.NumericLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumericVariable}
	 * labeled alternative in {@link DDMExpressionParser#numericTerm}.
	 * @param ctx the parse tree
	 */
	void enterNumericVariable(DDMExpressionParser.NumericVariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumericVariable}
	 * labeled alternative in {@link DDMExpressionParser#numericTerm}.
	 * @param ctx the parse tree
	 */
	void exitNumericVariable(DDMExpressionParser.NumericVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMExpressionParser#functionCallExpression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(DDMExpressionParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMExpressionParser#functionCallExpression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(DDMExpressionParser.FunctionCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMExpressionParser#functionParameters}.
	 * @param ctx the parse tree
	 */
	void enterFunctionParameters(DDMExpressionParser.FunctionParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMExpressionParser#functionParameters}.
	 * @param ctx the parse tree
	 */
	void exitFunctionParameters(DDMExpressionParser.FunctionParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMExpressionParser#functionParameter}.
	 * @param ctx the parse tree
	 */
	void enterFunctionParameter(DDMExpressionParser.FunctionParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMExpressionParser#functionParameter}.
	 * @param ctx the parse tree
	 */
	void exitFunctionParameter(DDMExpressionParser.FunctionParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMExpressionParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(DDMExpressionParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMExpressionParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(DDMExpressionParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToFloatingPointArray}
	 * labeled alternative in {@link DDMExpressionParser#floatingPointArray}.
	 * @param ctx the parse tree
	 */
	void enterToFloatingPointArray(DDMExpressionParser.ToFloatingPointArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToFloatingPointArray}
	 * labeled alternative in {@link DDMExpressionParser#floatingPointArray}.
	 * @param ctx the parse tree
	 */
	void exitToFloatingPointArray(DDMExpressionParser.ToFloatingPointArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToIntegerArray}
	 * labeled alternative in {@link DDMExpressionParser#integerArray}.
	 * @param ctx the parse tree
	 */
	void enterToIntegerArray(DDMExpressionParser.ToIntegerArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToIntegerArray}
	 * labeled alternative in {@link DDMExpressionParser#integerArray}.
	 * @param ctx the parse tree
	 */
	void exitToIntegerArray(DDMExpressionParser.ToIntegerArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToStringArray}
	 * labeled alternative in {@link DDMExpressionParser#stringArray}.
	 * @param ctx the parse tree
	 */
	void enterToStringArray(DDMExpressionParser.ToStringArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToStringArray}
	 * labeled alternative in {@link DDMExpressionParser#stringArray}.
	 * @param ctx the parse tree
	 */
	void exitToStringArray(DDMExpressionParser.ToStringArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FloatingPointLiteral}
	 * labeled alternative in {@link DDMExpressionParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterFloatingPointLiteral(DDMExpressionParser.FloatingPointLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloatingPointLiteral}
	 * labeled alternative in {@link DDMExpressionParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitFloatingPointLiteral(DDMExpressionParser.FloatingPointLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntegerLiteral}
	 * labeled alternative in {@link DDMExpressionParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(DDMExpressionParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntegerLiteral}
	 * labeled alternative in {@link DDMExpressionParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(DDMExpressionParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link DDMExpressionParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(DDMExpressionParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link DDMExpressionParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(DDMExpressionParser.StringLiteralContext ctx);
}
