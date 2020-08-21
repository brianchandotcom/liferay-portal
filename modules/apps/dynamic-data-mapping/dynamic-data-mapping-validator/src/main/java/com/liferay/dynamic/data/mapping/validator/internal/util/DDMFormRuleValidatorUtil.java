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

package com.liferay.dynamic.data.mapping.validator.internal.util;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Gabriel Albuquerque
 */
public class DDMFormRuleValidatorUtil {

	public static void validateDDMFormRules(
			DDMExpressionFactory ddmExpressionFactory,
			List<DDMFormRule> ddmFormRules)
		throws DDMFormValidationException {

		for (DDMFormRule ddmFormRule : ddmFormRules) {
			_validateDDMFormRule(ddmExpressionFactory, ddmFormRule);
		}
	}

	private static void _validateDDMExpression(
			DDMExpressionFactory ddmExpressionFactory,
			String ddmExpressionString, String expressionType)
		throws DDMFormValidationException {

		if (Validator.isNull(ddmExpressionString)) {
			throw new DDMFormValidationException.MustSetValidFormRuleExpression(
				expressionType, ddmExpressionString,
				new DDMExpressionException());
		}

		try {
			ddmExpressionFactory.createExpression(
				CreateExpressionRequest.Builder.newBuilder(
					ddmExpressionString
				).build());
		}
		catch (DDMExpressionException ddmExpressionException) {
			throw new DDMFormValidationException.MustSetValidFormRuleExpression(
				expressionType, ddmExpressionString, ddmExpressionException);
		}
	}

	private static void _validateDDMFormRule(
			DDMExpressionFactory ddmExpressionFactory, DDMFormRule ddmFormRule)
		throws DDMFormValidationException {

		for (String action : ddmFormRule.getActions()) {
			_validateDDMExpression(ddmExpressionFactory, action, "action");
		}

		_validateDDMExpression(
			ddmExpressionFactory, ddmFormRule.getCondition(), "condition");
	}

}