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

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal;

import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleEvaluator {

	public DDMFormRuleEvaluator(
		DDMFormRule ddmFormRule, DDMExpressionFactory ddmExpressionFactory,
		DDMExpressionFunctionRegister ddmExpressionFunctionRegister) {

		_ddmFormRule = ddmFormRule;
		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmExpressionFunctionRegister = ddmExpressionFunctionRegister;
	}

	public void evaluate() {
		if (!_ddmFormRule.isEnabled()) {
			return;
		}

		boolean conditionEvaluationResult = evaluateCondition(
			_ddmFormRule.getCondition());

		if (!conditionEvaluationResult) {
			return;
		}

		for (String action : _ddmFormRule.getActions()) {
			executeAction(action);
		}
	}

	protected boolean evaluateCondition(String condition) {
		try {
			return evaluateDDMExpression(condition);
		}
		catch (DDMExpressionException ddmee) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmee);
			}

			return false;
		}
	}

	protected boolean evaluateDDMExpression(String ddmExpressionString)
		throws DDMExpressionException {

		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression(
				ddmExpressionString);

		_ddmExpressionFunctionRegister.applyDDMExpressionFunctions(
			ddmExpression);

		return ddmExpression.evaluate();
	}

	protected void executeAction(String action) {
		try {
			evaluateDDMExpression(action);
		}
		catch (DDMExpressionException ddmee) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmee);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormRuleEvaluator.class);

	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMExpressionFunctionRegister _ddmExpressionFunctionRegister;
	private final DDMFormRule _ddmFormRule;

}