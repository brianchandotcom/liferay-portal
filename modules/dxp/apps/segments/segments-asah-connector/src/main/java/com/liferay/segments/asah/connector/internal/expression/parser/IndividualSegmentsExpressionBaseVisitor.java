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

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link
 * IndividualSegmentsExpressionVisitor}, which can be extended to create a
 * visitor which only needs to handle a subset of the available methods.
 *
 * @author Brian Wing Shun Chan
 * @param  <T> The return type of the visit operation. Use {@link Void} for
 *         operations with no return type.
 */
public class IndividualSegmentsExpressionBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements IndividualSegmentsExpressionVisitor<T> {
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitNotEqualsExpression(@NotNull IndividualSegmentsExpressionParser.NotEqualsExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitBooleanLiteral(@NotNull IndividualSegmentsExpressionParser.BooleanLiteralContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitToBooleanOperandExpression(@NotNull IndividualSegmentsExpressionParser.ToBooleanOperandExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitToFilterByCountExpression(@NotNull IndividualSegmentsExpressionParser.ToFilterByCountExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitNotExpression(@NotNull IndividualSegmentsExpressionParser.NotExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitGreaterThanExpression(@NotNull IndividualSegmentsExpressionParser.GreaterThanExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitOrExpression(@NotNull IndividualSegmentsExpressionParser.OrExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitAndExpression(@NotNull IndividualSegmentsExpressionParser.AndExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitToLogicalAndExpression(@NotNull IndividualSegmentsExpressionParser.ToLogicalAndExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitFunctionCallExpression(@NotNull IndividualSegmentsExpressionParser.FunctionCallExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitBooleanParenthesis(@NotNull IndividualSegmentsExpressionParser.BooleanParenthesisContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitToBooleanUnaryExpression(@NotNull IndividualSegmentsExpressionParser.ToBooleanUnaryExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitFunctionParameters(@NotNull IndividualSegmentsExpressionParser.FunctionParametersContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitIntegerLiteral(@NotNull IndividualSegmentsExpressionParser.IntegerLiteralContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitExpression(@NotNull IndividualSegmentsExpressionParser.ExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitToEqualityExpression(@NotNull IndividualSegmentsExpressionParser.ToEqualityExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitToFunctionCallExpression(@NotNull IndividualSegmentsExpressionParser.ToFunctionCallExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitGreaterThanOrEqualsExpression(@NotNull IndividualSegmentsExpressionParser.GreaterThanOrEqualsExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitToLogicalTerm(@NotNull IndividualSegmentsExpressionParser.ToLogicalTermContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitStringLiteral(@NotNull IndividualSegmentsExpressionParser.StringLiteralContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitFloatingPointLiteral(@NotNull IndividualSegmentsExpressionParser.FloatingPointLiteralContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitToComparisonExpression(@NotNull IndividualSegmentsExpressionParser.ToComparisonExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitFilterExpression(@NotNull IndividualSegmentsExpressionParser.FilterExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitLessThanOrEqualsExpression(@NotNull IndividualSegmentsExpressionParser.LessThanOrEqualsExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitFilterByCountExpression(@NotNull IndividualSegmentsExpressionParser.FilterByCountExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitToFilterExpression(@NotNull IndividualSegmentsExpressionParser.ToFilterExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitToLiteral(@NotNull IndividualSegmentsExpressionParser.ToLiteralContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitNullLiteral(@NotNull IndividualSegmentsExpressionParser.NullLiteralContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitLessThanExpression(@NotNull IndividualSegmentsExpressionParser.LessThanExpressionContext ctx) { return visitChildren(ctx); }

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitEqualsExpression(@NotNull IndividualSegmentsExpressionParser.EqualsExpressionContext ctx) { return visitChildren(ctx); }
}