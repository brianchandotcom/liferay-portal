/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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