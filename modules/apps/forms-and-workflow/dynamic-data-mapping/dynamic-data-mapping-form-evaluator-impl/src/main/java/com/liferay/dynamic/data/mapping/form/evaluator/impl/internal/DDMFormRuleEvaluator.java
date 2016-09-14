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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationException;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleEvaluator {

	public DDMFormRuleEvaluator(
		DDMFormRule ddmFormRule, DDMExpressionFactory ddmExpressionFactory) {

		_ddmFormRule = ddmFormRule;
		_ddmExpressionFactory = ddmExpressionFactory;
	}

	public void evaluate() throws DDMFormEvaluationException {
		if (!_ddmFormRule.isEnabled()) {
			return;
		}

		try {
			boolean conditionEvaluationResult = evaluateDDMExpression(
				_ddmFormRule.getCondition());

			if (!conditionEvaluationResult) {
				return;
			}

			for (String action : _ddmFormRule.getActions()) {
				evaluateDDMExpression(action);
			}
		}
		catch (DDMExpressionException ddmee) {
			throw new DDMFormEvaluationException(ddmee);
		}
	}

	public void setDDMExpressionFunction(
		String functionName, DDMExpressionFunction ddmExpressionFunction) {

		_ddmExpressionFunctionsMap.put(functionName, ddmExpressionFunction);
	}

	protected boolean evaluateDDMExpression(String ddmExpressionString)
		throws DDMExpressionException {

		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression(
				ddmExpressionString);

		setDDMExpressionFunctions(ddmExpression);

		return ddmExpression.evaluate();
	}

	protected void setDDMExpressionFunctions(DDMExpression<?> ddmExpression) {
		for (Map.Entry<String, DDMExpressionFunction> entry :
				_ddmExpressionFunctionsMap.entrySet()) {

			ddmExpression.setDDMExpressionFunction(
				entry.getKey(), entry.getValue());
		}
	}

	private final DDMExpressionFactory _ddmExpressionFactory;
	private final Map<String, DDMExpressionFunction>
		_ddmExpressionFunctionsMap = new HashMap<>();
	private final DDMFormRule _ddmFormRule;

}