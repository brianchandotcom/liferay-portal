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
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link IndividualSegmentsExpressionParser}.
 *
 * @author Brian Wing Shun Chan
 * @param  <T> The return type of the visit operation. Use {@link Void} for
 *         operations with no return type.
 */
public interface IndividualSegmentsExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code NotEqualsExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#equalityExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotEqualsExpression(@NotNull IndividualSegmentsExpressionParser.NotEqualsExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code BooleanLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(@NotNull IndividualSegmentsExpressionParser.BooleanLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToBooleanOperandExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#booleanUnaryExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToBooleanOperandExpression(@NotNull IndividualSegmentsExpressionParser.ToBooleanOperandExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToFilterByCountExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalTerm}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToFilterByCountExpression(@NotNull IndividualSegmentsExpressionParser.ToFilterByCountExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code NotExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#booleanUnaryExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpression(@NotNull IndividualSegmentsExpressionParser.NotExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code GreaterThanExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterThanExpression(@NotNull IndividualSegmentsExpressionParser.GreaterThanExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code OrExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalOrExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(@NotNull IndividualSegmentsExpressionParser.OrExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code AndExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalAndExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(@NotNull IndividualSegmentsExpressionParser.AndExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToLogicalAndExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalOrExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToLogicalAndExpression(@NotNull IndividualSegmentsExpressionParser.ToLogicalAndExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#functionCallExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpression(@NotNull IndividualSegmentsExpressionParser.FunctionCallExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code BooleanParenthesis} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#booleanOperandExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanParenthesis(@NotNull IndividualSegmentsExpressionParser.BooleanParenthesisContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToBooleanUnaryExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToBooleanUnaryExpression(@NotNull IndividualSegmentsExpressionParser.ToBooleanUnaryExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#functionParameters}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParameters(@NotNull IndividualSegmentsExpressionParser.FunctionParametersContext ctx);

	/**
	 * Visit a parse tree produced by the {@code IntegerLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(@NotNull IndividualSegmentsExpressionParser.IntegerLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#expression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(@NotNull IndividualSegmentsExpressionParser.ExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToEqualityExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalAndExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToEqualityExpression(@NotNull IndividualSegmentsExpressionParser.ToEqualityExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToFunctionCallExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalTerm}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToFunctionCallExpression(@NotNull IndividualSegmentsExpressionParser.ToFunctionCallExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code GreaterThanOrEqualsExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitGreaterThanOrEqualsExpression(@NotNull IndividualSegmentsExpressionParser.GreaterThanOrEqualsExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToLogicalTerm} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#booleanOperandExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToLogicalTerm(@NotNull IndividualSegmentsExpressionParser.ToLogicalTermContext ctx);

	/**
	 * Visit a parse tree produced by the {@code StringLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(@NotNull IndividualSegmentsExpressionParser.StringLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code FloatingPointLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatingPointLiteral(@NotNull IndividualSegmentsExpressionParser.FloatingPointLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToComparisonExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#equalityExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToComparisonExpression(@NotNull IndividualSegmentsExpressionParser.ToComparisonExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#filterExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterExpression(@NotNull IndividualSegmentsExpressionParser.FilterExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code LessThanOrEqualsExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessThanOrEqualsExpression(@NotNull IndividualSegmentsExpressionParser.LessThanOrEqualsExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#filterByCountExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterByCountExpression(@NotNull IndividualSegmentsExpressionParser.FilterByCountExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToFilterExpression} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#logicalTerm}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToFilterExpression(@NotNull IndividualSegmentsExpressionParser.ToFilterExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code ToLiteral} labeled alternative
	 * in {@link IndividualSegmentsExpressionParser#logicalTerm}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitToLiteral(@NotNull IndividualSegmentsExpressionParser.ToLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code NullLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullLiteral(@NotNull IndividualSegmentsExpressionParser.NullLiteralContext ctx);

	/**
	 * Visit a parse tree produced by the {@code LessThanExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitLessThanExpression(@NotNull IndividualSegmentsExpressionParser.LessThanExpressionContext ctx);

	/**
	 * Visit a parse tree produced by the {@code EqualsExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#equalityExpression}.
	 *
	 * @param  ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualsExpression(@NotNull IndividualSegmentsExpressionParser.EqualsExpressionContext ctx);
}