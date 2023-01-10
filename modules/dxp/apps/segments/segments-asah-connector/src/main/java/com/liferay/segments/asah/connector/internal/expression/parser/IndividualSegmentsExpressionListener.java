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
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link IndividualSegmentsExpressionParser}.
 *
 * @author Brian Wing Shun Chan
 */
public interface IndividualSegmentsExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code NotEqualsExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#equalityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterNotEqualsExpression(@NotNull IndividualSegmentsExpressionParser.NotEqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotEqualsExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#equalityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitNotEqualsExpression(@NotNull IndividualSegmentsExpressionParser.NotEqualsExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code BooleanLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(@NotNull IndividualSegmentsExpressionParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(@NotNull IndividualSegmentsExpressionParser.BooleanLiteralContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToBooleanOperandExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#booleanUnaryExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToBooleanOperandExpression(@NotNull IndividualSegmentsExpressionParser.ToBooleanOperandExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToBooleanOperandExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#booleanUnaryExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToBooleanOperandExpression(@NotNull IndividualSegmentsExpressionParser.ToBooleanOperandExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToFilterByCountExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToFilterByCountExpression(@NotNull IndividualSegmentsExpressionParser.ToFilterByCountExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToFilterByCountExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToFilterByCountExpression(@NotNull IndividualSegmentsExpressionParser.ToFilterByCountExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NotExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#booleanUnaryExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterNotExpression(@NotNull IndividualSegmentsExpressionParser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#booleanUnaryExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitNotExpression(@NotNull IndividualSegmentsExpressionParser.NotExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code GreaterThanExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterGreaterThanExpression(@NotNull IndividualSegmentsExpressionParser.GreaterThanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterThanExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitGreaterThanExpression(@NotNull IndividualSegmentsExpressionParser.GreaterThanExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code OrExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalOrExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterOrExpression(@NotNull IndividualSegmentsExpressionParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalOrExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitOrExpression(@NotNull IndividualSegmentsExpressionParser.OrExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code AndExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalAndExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAndExpression(@NotNull IndividualSegmentsExpressionParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AndExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalAndExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAndExpression(@NotNull IndividualSegmentsExpressionParser.AndExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToLogicalAndExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalOrExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToLogicalAndExpression(@NotNull IndividualSegmentsExpressionParser.ToLogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToLogicalAndExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalOrExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToLogicalAndExpression(@NotNull IndividualSegmentsExpressionParser.ToLogicalAndExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#functionCallExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(@NotNull IndividualSegmentsExpressionParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#functionCallExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(@NotNull IndividualSegmentsExpressionParser.FunctionCallExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code BooleanParenthesis} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#booleanOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterBooleanParenthesis(@NotNull IndividualSegmentsExpressionParser.BooleanParenthesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanParenthesis} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#booleanOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitBooleanParenthesis(@NotNull IndividualSegmentsExpressionParser.BooleanParenthesisContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToBooleanUnaryExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToBooleanUnaryExpression(@NotNull IndividualSegmentsExpressionParser.ToBooleanUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToBooleanUnaryExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToBooleanUnaryExpression(@NotNull IndividualSegmentsExpressionParser.ToBooleanUnaryExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#functionParameters}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFunctionParameters(@NotNull IndividualSegmentsExpressionParser.FunctionParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#functionParameters}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFunctionParameters(@NotNull IndividualSegmentsExpressionParser.FunctionParametersContext ctx);

	/**
	 * Enter a parse tree produced by the {@code IntegerLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(@NotNull IndividualSegmentsExpressionParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntegerLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(@NotNull IndividualSegmentsExpressionParser.IntegerLiteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#expression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull IndividualSegmentsExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#expression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull IndividualSegmentsExpressionParser.ExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToEqualityExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalAndExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToEqualityExpression(@NotNull IndividualSegmentsExpressionParser.ToEqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToEqualityExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalAndExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToEqualityExpression(@NotNull IndividualSegmentsExpressionParser.ToEqualityExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToFunctionCallExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToFunctionCallExpression(@NotNull IndividualSegmentsExpressionParser.ToFunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToFunctionCallExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#logicalTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToFunctionCallExpression(@NotNull IndividualSegmentsExpressionParser.ToFunctionCallExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code GreaterThanOrEqualsExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterGreaterThanOrEqualsExpression(@NotNull IndividualSegmentsExpressionParser.GreaterThanOrEqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GreaterThanOrEqualsExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitGreaterThanOrEqualsExpression(@NotNull IndividualSegmentsExpressionParser.GreaterThanOrEqualsExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToLogicalTerm} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#booleanOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToLogicalTerm(@NotNull IndividualSegmentsExpressionParser.ToLogicalTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToLogicalTerm} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#booleanOperandExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToLogicalTerm(@NotNull IndividualSegmentsExpressionParser.ToLogicalTermContext ctx);

	/**
	 * Enter a parse tree produced by the {@code StringLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(@NotNull IndividualSegmentsExpressionParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(@NotNull IndividualSegmentsExpressionParser.StringLiteralContext ctx);

	/**
	 * Enter a parse tree produced by the {@code FloatingPointLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFloatingPointLiteral(@NotNull IndividualSegmentsExpressionParser.FloatingPointLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloatingPointLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFloatingPointLiteral(@NotNull IndividualSegmentsExpressionParser.FloatingPointLiteralContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToComparisonExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#equalityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToComparisonExpression(@NotNull IndividualSegmentsExpressionParser.ToComparisonExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToComparisonExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#equalityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToComparisonExpression(@NotNull IndividualSegmentsExpressionParser.ToComparisonExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#filterExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFilterExpression(@NotNull IndividualSegmentsExpressionParser.FilterExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#filterExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFilterExpression(@NotNull IndividualSegmentsExpressionParser.FilterExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LessThanOrEqualsExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLessThanOrEqualsExpression(@NotNull IndividualSegmentsExpressionParser.LessThanOrEqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessThanOrEqualsExpression}
	 * labeled alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLessThanOrEqualsExpression(@NotNull IndividualSegmentsExpressionParser.LessThanOrEqualsExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#filterByCountExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFilterByCountExpression(@NotNull IndividualSegmentsExpressionParser.FilterByCountExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * IndividualSegmentsExpressionParser#filterByCountExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFilterByCountExpression(@NotNull IndividualSegmentsExpressionParser.FilterByCountExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToFilterExpression} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#logicalTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToFilterExpression(@NotNull IndividualSegmentsExpressionParser.ToFilterExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToFilterExpression} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#logicalTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToFilterExpression(@NotNull IndividualSegmentsExpressionParser.ToFilterExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code ToLiteral} labeled alternative
	 * in {@link IndividualSegmentsExpressionParser#logicalTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void enterToLiteral(@NotNull IndividualSegmentsExpressionParser.ToLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToLiteral} labeled alternative
	 * in {@link IndividualSegmentsExpressionParser#logicalTerm}.
	 *
	 * @param ctx the parse tree
	 */
	void exitToLiteral(@NotNull IndividualSegmentsExpressionParser.ToLiteralContext ctx);

	/**
	 * Enter a parse tree produced by the {@code NullLiteral} labeled
	 * alternative in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void enterNullLiteral(@NotNull IndividualSegmentsExpressionParser.NullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NullLiteral} labeled alternative
	 * in {@link IndividualSegmentsExpressionParser#literal}.
	 *
	 * @param ctx the parse tree
	 */
	void exitNullLiteral(@NotNull IndividualSegmentsExpressionParser.NullLiteralContext ctx);

	/**
	 * Enter a parse tree produced by the {@code LessThanExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLessThanExpression(@NotNull IndividualSegmentsExpressionParser.LessThanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LessThanExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#comparisonExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLessThanExpression(@NotNull IndividualSegmentsExpressionParser.LessThanExpressionContext ctx);

	/**
	 * Enter a parse tree produced by the {@code EqualsExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#equalityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterEqualsExpression(@NotNull IndividualSegmentsExpressionParser.EqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EqualsExpression} labeled
	 * alternative in {@link
	 * IndividualSegmentsExpressionParser#equalityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitEqualsExpression(@NotNull IndividualSegmentsExpressionParser.EqualsExpressionContext ctx);
}