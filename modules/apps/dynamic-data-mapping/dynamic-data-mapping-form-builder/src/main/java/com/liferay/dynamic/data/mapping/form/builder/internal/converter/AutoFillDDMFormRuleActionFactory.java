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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter;

import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.DDMFormRuleAction;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.action.AutoFillDDMFormRuleAction;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor.ActionExpressionVisitor;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class AutoFillDDMFormRuleActionFactory {

	public static DDMFormRuleAction create(
		List<Expression> expressions,
		ActionExpressionVisitor actionExpressionVisitor) {

		String ddmDataProviderInstanceUUID = actionExpressionVisitor.doVisit(
			expressions.get(0));
		String paramsExpression = actionExpressionVisitor.doVisit(
			expressions.get(1));
		String resultMapExpression = actionExpressionVisitor.doVisit(
			expressions.get(2));

		return new AutoFillDDMFormRuleAction(
			ddmDataProviderInstanceUUID,
			createAutoFillInputParameters(paramsExpression),
			createAutoFillOutputParameters(resultMapExpression));
	}

	protected static Map<String, String> createAutoFillInputParameters(
		String paramsExpression) {

		Map<String, String> map = new LinkedHashMap<>();

		if (Validator.isNull(paramsExpression)) {
			return map;
		}

		String[] innerExpressions = StringUtil.split(
			paramsExpression, CharPool.SEMICOLON);

		for (String innerExpression : innerExpressions) {
			String[] tokens = StringUtil.split(innerExpression, CharPool.EQUAL);

			if (!isValidExpression(tokens)) {
				continue;
			}

			map.put(tokens[0], tokens[1]);
		}

		return map;
	}

	protected static Map<String, String> createAutoFillOutputParameters(
		String resultMapExpression) {

		Map<String, String> map = new LinkedHashMap<>();

		if (Validator.isNull(resultMapExpression)) {
			return map;
		}

		String[] innerExpressions = StringUtil.split(
			resultMapExpression, CharPool.SEMICOLON);

		for (String innerExpression : innerExpressions) {
			String[] tokens = StringUtil.split(innerExpression, CharPool.EQUAL);

			if (!isValidExpression(tokens)) {
				continue;
			}

			map.put(tokens[1], tokens[0]);
		}

		return map;
	}

	protected static boolean isValidExpression(String[] tokens) {
		if (tokens.length < 2) {
			return false;
		}

		if (Validator.isNull(tokens[0]) || Validator.isNull(tokens[1])) {
			return false;
		}

		return true;
	}

}