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

package com.liferay.dynamic.data.lists.form.web.internal.converter;

import com.liferay.dynamic.data.lists.form.web.internal.converter.DDMFormRuleToDDLFormRuleConverter.ActionExpressionVisitor;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.DDLFormRuleAction;
import com.liferay.dynamic.data.lists.form.web.internal.converter.model.action.AutoFillDDLFormRuleAction;
import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class AutoFillDDLFormRuleActionFactory {

	public static DDLFormRuleAction create(
		List<Expression> expressions, ActionExpressionVisitor visitor) {

		String ddmDataProviderInstanceUUID = visitor.doVisit(
			expressions.get(0));
		String paramsExpression = visitor.doVisit(expressions.get(1));
		String resultMapExpression = visitor.doVisit(expressions.get(2));

		return new AutoFillDDLFormRuleAction(
			ddmDataProviderInstanceUUID,
			createAutoFillInputParameters(paramsExpression),
			createAutoFillOutputParameters(resultMapExpression));
	}

	protected static Map<String, String> createAutoFillInputParameters(
		String paramsExpression) {

		Map<String, String> map = new LinkedHashMap<>();

		String[] innerExpressions = StringUtil.split(
			paramsExpression, CharPool.SEMICOLON);

		if (innerExpressions.length == 0) {
			String[] tokens = StringUtil.split(
				paramsExpression, CharPool.EQUAL);

			map.put(tokens[0], tokens[1]);
		}
		else {
			for (String innerExpression : innerExpressions) {
				String[] tokens = StringUtil.split(
					innerExpression, CharPool.EQUAL);

				map.put(tokens[0], tokens[1]);
			}
		}

		return map;
	}

	protected static Map<String, String> createAutoFillOutputParameters(
		String resultMapExpression) {

		Map<String, String> map = new LinkedHashMap<>();

		String[] innerExpressions = StringUtil.split(
			resultMapExpression, CharPool.SEMICOLON);

		if (innerExpressions.length == 0) {
			String[] tokens = StringUtil.split(
				resultMapExpression, CharPool.EQUAL);

			map.put(tokens[1], tokens[0]);
		}
		else {
			for (String innerExpression : innerExpressions) {
				String[] tokens = StringUtil.split(
					innerExpression, CharPool.EQUAL);

				map.put(tokens[1], tokens[0]);
			}
		}

		return map;
	}

}