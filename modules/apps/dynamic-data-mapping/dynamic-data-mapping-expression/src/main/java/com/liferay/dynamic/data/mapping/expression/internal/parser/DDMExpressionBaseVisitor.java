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
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link DDMExpressionVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @author Brian Wing Shun Chan
 */
public class DDMExpressionBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements DDMExpressionVisitor<T> {
	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToMultOrDiv(@NotNull DDMExpressionParser.ToMultOrDivContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitNumericLiteral(@NotNull DDMExpressionParser.NumericLiteralContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitNotEqualsExpression(@NotNull DDMExpressionParser.NotEqualsExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToBooleanOperandExpression(@NotNull DDMExpressionParser.ToBooleanOperandExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitGreaterThanExpression(@NotNull DDMExpressionParser.GreaterThanExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitNotExpression(@NotNull DDMExpressionParser.NotExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitOrExpression(@NotNull DDMExpressionParser.OrExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitAndExpression(@NotNull DDMExpressionParser.AndExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitArray(@NotNull DDMExpressionParser.ArrayContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToLogicalAndExpression(@NotNull DDMExpressionParser.ToLogicalAndExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitPrimary(@NotNull DDMExpressionParser.PrimaryContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitFunctionCallExpression(@NotNull DDMExpressionParser.FunctionCallExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitDivisionExpression(@NotNull DDMExpressionParser.DivisionExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitBooleanParenthesis(@NotNull DDMExpressionParser.BooleanParenthesisContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToBooleanUnaryExpression(@NotNull DDMExpressionParser.ToBooleanUnaryExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitFunctionParameters(@NotNull DDMExpressionParser.FunctionParametersContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitIntegerLiteral(@NotNull DDMExpressionParser.IntegerLiteralContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitLogicalVariable(@NotNull DDMExpressionParser.LogicalVariableContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitAdditionExpression(@NotNull DDMExpressionParser.AdditionExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitExpression(@NotNull DDMExpressionParser.ExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToEqualityExpression(@NotNull DDMExpressionParser.ToEqualityExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitLogicalConstant(@NotNull DDMExpressionParser.LogicalConstantContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToAdditionOrSubtractionEpression(@NotNull DDMExpressionParser.ToAdditionOrSubtractionEpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToStringArray(@NotNull DDMExpressionParser.ToStringArrayContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToFunctionCallExpression(@NotNull DDMExpressionParser.ToFunctionCallExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitNumericVariable(@NotNull DDMExpressionParser.NumericVariableContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToFloatingPointArray(@NotNull DDMExpressionParser.ToFloatingPointArrayContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitSubtractionExpression(@NotNull DDMExpressionParser.SubtractionExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToNumericUnaryExpression(@NotNull DDMExpressionParser.ToNumericUnaryExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitGreaterThanOrEqualsExpression(@NotNull DDMExpressionParser.GreaterThanOrEqualsExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToNumericTerm(@NotNull DDMExpressionParser.ToNumericTermContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitNumericParenthesis(@NotNull DDMExpressionParser.NumericParenthesisContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToLogicalTerm(@NotNull DDMExpressionParser.ToLogicalTermContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitStringLiteral(@NotNull DDMExpressionParser.StringLiteralContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitMinusExpression(@NotNull DDMExpressionParser.MinusExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitFloatingPointLiteral(@NotNull DDMExpressionParser.FloatingPointLiteralContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToComparisonExpression(@NotNull DDMExpressionParser.ToComparisonExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitLessThanOrEqualsExpression(@NotNull DDMExpressionParser.LessThanOrEqualsExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitFunctionParameter(@NotNull DDMExpressionParser.FunctionParameterContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitMultiplicationExpression(@NotNull DDMExpressionParser.MultiplicationExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitToIntegerArray(@NotNull DDMExpressionParser.ToIntegerArrayContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitEqualsExpression(@NotNull DDMExpressionParser.EqualsExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * The default implementation returns the result of calling {@link
	 * #visitChildren} on {@code ctx}.
	 * </p>
	 */
	@Override public T visitLessThanExpression(@NotNull DDMExpressionParser.LessThanExpressionContext ctx) { return visitChildren(ctx); }
}