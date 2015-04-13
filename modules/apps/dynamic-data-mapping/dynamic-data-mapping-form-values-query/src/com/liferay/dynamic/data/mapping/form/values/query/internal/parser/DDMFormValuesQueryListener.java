// Generated from DDMFormValuesQuery.g by ANTLR 4.3

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

package com.liferay.dynamic.data.mapping.form.values.query.internal.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DDMFormValuesQueryParser}.
 *
 * @author Brian Wing Shun Chan
 */
public interface DDMFormValuesQueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link
	 * DDMFormValuesQueryParser#localeExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterLocaleExpression(@NotNull DDMFormValuesQueryParser.LocaleExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * DDMFormValuesQueryParser#localeExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitLocaleExpression(@NotNull DDMFormValuesQueryParser.LocaleExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * DDMFormValuesQueryParser#attribute}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAttribute(@NotNull DDMFormValuesQueryParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#attribute}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAttribute(@NotNull DDMFormValuesQueryParser.AttributeContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * DDMFormValuesQueryParser#predicateAndExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPredicateAndExpression(@NotNull DDMFormValuesQueryParser.PredicateAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * DDMFormValuesQueryParser#predicateAndExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPredicateAndExpression(@NotNull DDMFormValuesQueryParser.PredicateAndExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * DDMFormValuesQueryParser#attributeType}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAttributeType(@NotNull DDMFormValuesQueryParser.AttributeTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * DDMFormValuesQueryParser#attributeType}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAttributeType(@NotNull DDMFormValuesQueryParser.AttributeTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * DDMFormValuesQueryParser#predicateExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPredicateExpression(@NotNull DDMFormValuesQueryParser.PredicateExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * DDMFormValuesQueryParser#predicateExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPredicateExpression(@NotNull DDMFormValuesQueryParser.PredicateExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * DDMFormValuesQueryParser#fieldSelectorExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFieldSelectorExpression(@NotNull DDMFormValuesQueryParser.FieldSelectorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * DDMFormValuesQueryParser#fieldSelectorExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFieldSelectorExpression(@NotNull DDMFormValuesQueryParser.FieldSelectorExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * DDMFormValuesQueryParser#predicateOrExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPredicateOrExpression(@NotNull DDMFormValuesQueryParser.PredicateOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * DDMFormValuesQueryParser#predicateOrExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPredicateOrExpression(@NotNull DDMFormValuesQueryParser.PredicateOrExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * DDMFormValuesQueryParser#attributeValue}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAttributeValue(@NotNull DDMFormValuesQueryParser.AttributeValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * DDMFormValuesQueryParser#attributeValue}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAttributeValue(@NotNull DDMFormValuesQueryParser.AttributeValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#path}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPath(@NotNull DDMFormValuesQueryParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#path}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPath(@NotNull DDMFormValuesQueryParser.PathContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * DDMFormValuesQueryParser#fieldSelector}.
	 *
	 * @param ctx the parse tree
	 */
	void enterFieldSelector(@NotNull DDMFormValuesQueryParser.FieldSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * DDMFormValuesQueryParser#fieldSelector}.
	 *
	 * @param ctx the parse tree
	 */
	void exitFieldSelector(@NotNull DDMFormValuesQueryParser.FieldSelectorContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * DDMFormValuesQueryParser#selectorExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterSelectorExpression(@NotNull DDMFormValuesQueryParser.SelectorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * DDMFormValuesQueryParser#selectorExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitSelectorExpression(@NotNull DDMFormValuesQueryParser.SelectorExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#stepType}.
	 *
	 * @param ctx the parse tree
	 */
	void enterStepType(@NotNull DDMFormValuesQueryParser.StepTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#stepType}.
	 *
	 * @param ctx the parse tree
	 */
	void exitStepType(@NotNull DDMFormValuesQueryParser.StepTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link
	 * DDMFormValuesQueryParser#predicateEqualityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void enterPredicateEqualityExpression(@NotNull DDMFormValuesQueryParser.PredicateEqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link
	 * DDMFormValuesQueryParser#predicateEqualityExpression}.
	 *
	 * @param ctx the parse tree
	 */
	void exitPredicateEqualityExpression(@NotNull DDMFormValuesQueryParser.PredicateEqualityExpressionContext ctx);
}