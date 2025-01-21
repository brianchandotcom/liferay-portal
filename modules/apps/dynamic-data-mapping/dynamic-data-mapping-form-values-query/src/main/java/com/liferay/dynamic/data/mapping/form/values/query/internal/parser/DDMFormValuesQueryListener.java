// Generated from DDMFormValuesQuery.g by ANTLR 4.13.2

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.values.query.internal.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DDMFormValuesQueryParser}.
 */
public interface DDMFormValuesQueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(DDMFormValuesQueryParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(DDMFormValuesQueryParser.PathContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#selectorExpression}.
	 * @param ctx the parse tree
	 */
	void enterSelectorExpression(DDMFormValuesQueryParser.SelectorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#selectorExpression}.
	 * @param ctx the parse tree
	 */
	void exitSelectorExpression(DDMFormValuesQueryParser.SelectorExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#stepType}.
	 * @param ctx the parse tree
	 */
	void enterStepType(DDMFormValuesQueryParser.StepTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#stepType}.
	 * @param ctx the parse tree
	 */
	void exitStepType(DDMFormValuesQueryParser.StepTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#fieldSelectorExpression}.
	 * @param ctx the parse tree
	 */
	void enterFieldSelectorExpression(DDMFormValuesQueryParser.FieldSelectorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#fieldSelectorExpression}.
	 * @param ctx the parse tree
	 */
	void exitFieldSelectorExpression(DDMFormValuesQueryParser.FieldSelectorExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#fieldSelector}.
	 * @param ctx the parse tree
	 */
	void enterFieldSelector(DDMFormValuesQueryParser.FieldSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#fieldSelector}.
	 * @param ctx the parse tree
	 */
	void exitFieldSelector(DDMFormValuesQueryParser.FieldSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#predicateExpression}.
	 * @param ctx the parse tree
	 */
	void enterPredicateExpression(DDMFormValuesQueryParser.PredicateExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#predicateExpression}.
	 * @param ctx the parse tree
	 */
	void exitPredicateExpression(DDMFormValuesQueryParser.PredicateExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#predicateOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterPredicateOrExpression(DDMFormValuesQueryParser.PredicateOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#predicateOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitPredicateOrExpression(DDMFormValuesQueryParser.PredicateOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#predicateAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterPredicateAndExpression(DDMFormValuesQueryParser.PredicateAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#predicateAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitPredicateAndExpression(DDMFormValuesQueryParser.PredicateAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#predicateEqualityExpression}.
	 * @param ctx the parse tree
	 */
	void enterPredicateEqualityExpression(DDMFormValuesQueryParser.PredicateEqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#predicateEqualityExpression}.
	 * @param ctx the parse tree
	 */
	void exitPredicateEqualityExpression(DDMFormValuesQueryParser.PredicateEqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(DDMFormValuesQueryParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(DDMFormValuesQueryParser.AttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#attributeType}.
	 * @param ctx the parse tree
	 */
	void enterAttributeType(DDMFormValuesQueryParser.AttributeTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#attributeType}.
	 * @param ctx the parse tree
	 */
	void exitAttributeType(DDMFormValuesQueryParser.AttributeTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#attributeValue}.
	 * @param ctx the parse tree
	 */
	void enterAttributeValue(DDMFormValuesQueryParser.AttributeValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#attributeValue}.
	 * @param ctx the parse tree
	 */
	void exitAttributeValue(DDMFormValuesQueryParser.AttributeValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link DDMFormValuesQueryParser#localeExpression}.
	 * @param ctx the parse tree
	 */
	void enterLocaleExpression(DDMFormValuesQueryParser.LocaleExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DDMFormValuesQueryParser#localeExpression}.
	 * @param ctx the parse tree
	 */
	void exitLocaleExpression(DDMFormValuesQueryParser.LocaleExpressionContext ctx);
}
