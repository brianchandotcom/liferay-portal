// Generated from DDMExpression.g4 by ANTLR 4.13.2

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.internal.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DDMExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DDMExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DDMExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(DDMExpressionParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToLogicalAndExpression}
	 * labeled alternative in {@link DDMExpressionParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToLogicalAndExpression(DDMExpressionParser.ToLogicalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrExpression}
	 * labeled alternative in {@link DDMExpressionParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(DDMExpressionParser.OrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExpression}
	 * labeled alternative in {@link DDMExpressionParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(DDMExpressionParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToEqualityExpression}
	 * labeled alternative in {@link DDMExpressionParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToEqualityExpression(DDMExpressionParser.ToEqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotEqualsExpression(DDMExpressionParser.NotEqualsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToComparisonExpression}
	 * labeled alternative in {@link DDMExpressionParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToComparisonExpression(DDMExpressionParser.ToComparisonExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualsExpression(DDMExpressionParser.EqualsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreaterThanOrEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterThanOrEqualsExpression(DDMExpressionParser.GreaterThanOrEqualsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessThanOrEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessThanOrEqualsExpression(DDMExpressionParser.LessThanOrEqualsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GreaterThanExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterThanExpression(DDMExpressionParser.GreaterThanExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToBooleanUnaryExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToBooleanUnaryExpression(DDMExpressionParser.ToBooleanUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LessThanExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessThanExpression(DDMExpressionParser.LessThanExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotExpression}
	 * labeled alternative in {@link DDMExpressionParser#booleanUnaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpression(DDMExpressionParser.NotExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToBooleanOperandExpression}
	 * labeled alternative in {@link DDMExpressionParser#booleanUnaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToBooleanOperandExpression(DDMExpressionParser.ToBooleanOperandExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToLogicalTerm}
	 * labeled alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToLogicalTerm(DDMExpressionParser.ToLogicalTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToAdditionOrSubtractionEpression}
	 * labeled alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToAdditionOrSubtractionEpression(DDMExpressionParser.ToAdditionOrSubtractionEpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BooleanParenthesis}
	 * labeled alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanParenthesis(DDMExpressionParser.BooleanParenthesisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LogicalConstant}
	 * labeled alternative in {@link DDMExpressionParser#logicalTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalConstant(DDMExpressionParser.LogicalConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LogicalVariable}
	 * labeled alternative in {@link DDMExpressionParser#logicalTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalVariable(DDMExpressionParser.LogicalVariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AdditionExpression}
	 * labeled alternative in {@link DDMExpressionParser#additionOrSubtractionExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditionExpression(DDMExpressionParser.AdditionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubtractionExpression}
	 * labeled alternative in {@link DDMExpressionParser#additionOrSubtractionExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubtractionExpression(DDMExpressionParser.SubtractionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToMultOrDiv}
	 * labeled alternative in {@link DDMExpressionParser#additionOrSubtractionExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToMultOrDiv(DDMExpressionParser.ToMultOrDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToNumericUnaryExpression}
	 * labeled alternative in {@link DDMExpressionParser#multiplicationOrDivisionExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToNumericUnaryExpression(DDMExpressionParser.ToNumericUnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DivisionExpression}
	 * labeled alternative in {@link DDMExpressionParser#multiplicationOrDivisionExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivisionExpression(DDMExpressionParser.DivisionExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MultiplicationExpression}
	 * labeled alternative in {@link DDMExpressionParser#multiplicationOrDivisionExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicationExpression(DDMExpressionParser.MultiplicationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MinusExpression}
	 * labeled alternative in {@link DDMExpressionParser#numericUnaryEpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinusExpression(DDMExpressionParser.MinusExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Primary}
	 * labeled alternative in {@link DDMExpressionParser#numericUnaryEpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(DDMExpressionParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToNumericTerm}
	 * labeled alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToNumericTerm(DDMExpressionParser.ToNumericTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToFunctionCallExpression}
	 * labeled alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToFunctionCallExpression(DDMExpressionParser.ToFunctionCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumericParenthesis}
	 * labeled alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericParenthesis(DDMExpressionParser.NumericParenthesisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumericLiteral}
	 * labeled alternative in {@link DDMExpressionParser#numericTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLiteral(DDMExpressionParser.NumericLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumericVariable}
	 * labeled alternative in {@link DDMExpressionParser#numericTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericVariable(DDMExpressionParser.NumericVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link DDMExpressionParser#functionCallExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpression(DDMExpressionParser.FunctionCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link DDMExpressionParser#functionParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParameters(DDMExpressionParser.FunctionParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link DDMExpressionParser#functionParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParameter(DDMExpressionParser.FunctionParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link DDMExpressionParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(DDMExpressionParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToFloatingPointArray}
	 * labeled alternative in {@link DDMExpressionParser#floatingPointArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToFloatingPointArray(DDMExpressionParser.ToFloatingPointArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToIntegerArray}
	 * labeled alternative in {@link DDMExpressionParser#integerArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToIntegerArray(DDMExpressionParser.ToIntegerArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToStringArray}
	 * labeled alternative in {@link DDMExpressionParser#stringArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToStringArray(DDMExpressionParser.ToStringArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FloatingPointLiteral}
	 * labeled alternative in {@link DDMExpressionParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatingPointLiteral(DDMExpressionParser.FloatingPointLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntegerLiteral}
	 * labeled alternative in {@link DDMExpressionParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(DDMExpressionParser.IntegerLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link DDMExpressionParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(DDMExpressionParser.StringLiteralContext ctx);
}
