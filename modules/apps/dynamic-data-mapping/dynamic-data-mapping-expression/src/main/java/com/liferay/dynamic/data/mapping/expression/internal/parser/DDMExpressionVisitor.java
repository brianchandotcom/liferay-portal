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
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DDMExpressionParser}.
 *
 * @author Brian Wing Shun Chan
 */
public interface DDMExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code ToMultOrDiv} labeled
	 * alternative in {@link
	 * DDMExpressionParser#additionOrSubtractionExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToMultOrDiv(@NotNull DDMExpressionParser.ToMultOrDivContext ctx);

	/**
	 * Visit a parse tree produced by the {@code NumericLiteral} labeled
	 * alternative in {@link DDMExpressionParser#numericTerm}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLiteral(@NotNull DDMExpressionParser.NumericLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code NotEqualsExpression} labeled
	 * alternative in {@link DDMExpressionParser#equalityExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotEqualsExpression(@NotNull DDMExpressionParser.NotEqualsExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToBooleanOperandExpression}
	 * labeled alternative in {@link
	 * DDMExpressionParser#booleanUnaryExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToBooleanOperandExpression(@NotNull DDMExpressionParser.ToBooleanOperandExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code GreaterThanExpression} labeled
	 * alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterThanExpression(@NotNull DDMExpressionParser.GreaterThanExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code NotExpression} labeled
	 * alternative in {@link DDMExpressionParser#booleanUnaryExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpression(@NotNull DDMExpressionParser.NotExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code OrExpression} labeled
	 * alternative in {@link DDMExpressionParser#logicalOrExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(@NotNull DDMExpressionParser.OrExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code AndExpression} labeled
	 * alternative in {@link DDMExpressionParser#logicalAndExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(@NotNull DDMExpressionParser.AndExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link DDMExpressionParser#array}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(@NotNull DDMExpressionParser.ArrayContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToLogicalAndExpression} labeled
	 * alternative in {@link DDMExpressionParser#logicalOrExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToLogicalAndExpression(@NotNull DDMExpressionParser.ToLogicalAndExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code Primary} labeled alternative in
	 * {@link DDMExpressionParser#numericUnaryEpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(@NotNull DDMExpressionParser.PrimaryContext ctx);

	/**
	 * Visit a parse tree produced by {@link
	 * DDMExpressionParser#functionCallExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpression(@NotNull DDMExpressionParser.FunctionCallExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code DivisionExpression} labeled
	 * alternative in {@link
	 * DDMExpressionParser#multiplicationOrDivisionExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivisionExpression(@NotNull DDMExpressionParser.DivisionExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code BooleanParenthesis} labeled
	 * alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanParenthesis(@NotNull DDMExpressionParser.BooleanParenthesisContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToBooleanUnaryExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToBooleanUnaryExpression(@NotNull DDMExpressionParser.ToBooleanUnaryExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link
	 * DDMExpressionParser#functionParameters}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParameters(@NotNull DDMExpressionParser.FunctionParametersContext ctx);

	/**
	 * Visit a parse tree produced by the {@code IntegerLiteral} labeled
	 * alternative in {@link DDMExpressionParser#literal}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(@NotNull DDMExpressionParser.IntegerLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code LogicalVariable} labeled
	 * alternative in {@link DDMExpressionParser#logicalTerm}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalVariable(@NotNull DDMExpressionParser.LogicalVariableContext ctx);

	/**
	 * Visit a parse tree produced by the {@code AdditionExpression} labeled
	 * alternative in {@link
	 * DDMExpressionParser#additionOrSubtractionExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditionExpression(@NotNull DDMExpressionParser.AdditionExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link DDMExpressionParser#expression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(@NotNull DDMExpressionParser.ExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToEqualityExpression} labeled
	 * alternative in {@link DDMExpressionParser#logicalAndExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToEqualityExpression(@NotNull DDMExpressionParser.ToEqualityExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code LogicalConstant} labeled
	 * alternative in {@link DDMExpressionParser#logicalTerm}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalConstant(@NotNull DDMExpressionParser.LogicalConstantContext ctx);

	/**
	 * Visit a parse tree produced by the {@code
	 * ToAdditionOrSubtractionEpression} labeled alternative in {@link
	 * DDMExpressionParser#booleanOperandExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToAdditionOrSubtractionEpression(@NotNull DDMExpressionParser.ToAdditionOrSubtractionEpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToStringArray} labeled
	 * alternative in {@link DDMExpressionParser#stringArray}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToStringArray(@NotNull DDMExpressionParser.ToStringArrayContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToFunctionCallExpression}
	 * labeled alternative in {@link
	 * DDMExpressionParser#numericOperandExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToFunctionCallExpression(@NotNull DDMExpressionParser.ToFunctionCallExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code NumericVariable} labeled
	 * alternative in {@link DDMExpressionParser#numericTerm}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericVariable(@NotNull DDMExpressionParser.NumericVariableContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToFloatingPointArray} labeled
	 * alternative in {@link DDMExpressionParser#floatingPointArray}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToFloatingPointArray(@NotNull DDMExpressionParser.ToFloatingPointArrayContext ctx);

	/**
	 * Visit a parse tree produced by the {@code SubtractionExpression} labeled
	 * alternative in {@link
	 * DDMExpressionParser#additionOrSubtractionExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubtractionExpression(@NotNull DDMExpressionParser.SubtractionExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToNumericUnaryExpression}
	 * labeled alternative in {@link
	 * DDMExpressionParser#multiplicationOrDivisionExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToNumericUnaryExpression(@NotNull DDMExpressionParser.ToNumericUnaryExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code GreaterThanOrEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterThanOrEqualsExpression(@NotNull DDMExpressionParser.GreaterThanOrEqualsExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToNumericTerm} labeled
	 * alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToNumericTerm(@NotNull DDMExpressionParser.ToNumericTermContext ctx);

	/**
	 * Visit a parse tree produced by the {@code NumericParenthesis} labeled
	 * alternative in {@link DDMExpressionParser#numericOperandExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericParenthesis(@NotNull DDMExpressionParser.NumericParenthesisContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToLogicalTerm} labeled
	 * alternative in {@link DDMExpressionParser#booleanOperandExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToLogicalTerm(@NotNull DDMExpressionParser.ToLogicalTermContext ctx);

	/**
	 * Visit a parse tree produced by the {@code StringLiteral} labeled
	 * alternative in {@link DDMExpressionParser#literal}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(@NotNull DDMExpressionParser.StringLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code MinusExpression} labeled
	 * alternative in {@link DDMExpressionParser#numericUnaryEpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinusExpression(@NotNull DDMExpressionParser.MinusExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code FloatingPointLiteral} labeled
	 * alternative in {@link DDMExpressionParser#literal}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatingPointLiteral(@NotNull DDMExpressionParser.FloatingPointLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToComparisonExpression} labeled
	 * alternative in {@link DDMExpressionParser#equalityExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToComparisonExpression(@NotNull DDMExpressionParser.ToComparisonExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code LessThanOrEqualsExpression}
	 * labeled alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessThanOrEqualsExpression(@NotNull DDMExpressionParser.LessThanOrEqualsExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link
	 * DDMExpressionParser#functionParameter}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParameter(@NotNull DDMExpressionParser.FunctionParameterContext ctx);

	/**
	 * Visit a parse tree produced by the {@code MultiplicationExpression}
	 * labeled alternative in {@link
	 * DDMExpressionParser#multiplicationOrDivisionExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicationExpression(@NotNull DDMExpressionParser.MultiplicationExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToIntegerArray} labeled
	 * alternative in {@link DDMExpressionParser#integerArray}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToIntegerArray(@NotNull DDMExpressionParser.ToIntegerArrayContext ctx);

	/**
	 * Visit a parse tree produced by the {@code EqualsExpression} labeled
	 * alternative in {@link DDMExpressionParser#equalityExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualsExpression(@NotNull DDMExpressionParser.EqualsExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code LessThanExpression} labeled
	 * alternative in {@link DDMExpressionParser#comparisonExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessThanExpression(@NotNull DDMExpressionParser.LessThanExpressionContext ctx);
}