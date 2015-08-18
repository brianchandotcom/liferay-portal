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

package com.liferay.dynamic.data.mapping.form.evaluator.internal;

import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.expression.Expression;
import com.liferay.portal.expression.ExpressionFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Pablo Carvalho
 */
public class DDMFormEvaluatorHelper {

	public DDMFormEvaluatorHelper(
		DDMForm ddmForm, DDMFormValues ddmFormValues, Locale locale) {

		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);
		_rootDDMFormFieldValues = ddmFormValues.getDDMFormFieldValues();
		_locale = locale;
	}

	public DDMFormEvaluationResult evaluate() throws PortalException {
		DDMFormEvaluationResult ddmFormEvaluationResult =
			new DDMFormEvaluationResult();

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			evaluateDDMFormFieldValues(
				_rootDDMFormFieldValues, new HashSet<DDMFormFieldValue>());

		ddmFormEvaluationResult.setDDMFormFieldEvaluationResults(
			ddmFormFieldEvaluationResults);

		return ddmFormEvaluationResult;
	}

	protected boolean evaluateBooleanExpression(
			String expressionString,
			Set<DDMFormFieldValue> ancestorDDMFormFieldValues)
		throws PortalException {

		if (Validator.isNull(expressionString)) {
			return true;
		}

		Expression<Boolean> expression =
			_expressionFactory.createBooleanExpression(expressionString);

		setExpressionVariables(
			expression, _rootDDMFormFieldValues, ancestorDDMFormFieldValues);

		return expression.evaluate();
	}

	protected DDMFormFieldEvaluationResult evaluateDDMFormFieldValue(
			DDMFormFieldValue ddmFormFieldValue,
			Set<DDMFormFieldValue> ancestorDDMFormFieldValues)
		throws PortalException {

		ancestorDDMFormFieldValues.add(ddmFormFieldValue);

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormFieldValue.getName());

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			evaluateDDMFormFieldValue(
				ddmFormFieldValue, ancestorDDMFormFieldValues, ddmFormField);

		ancestorDDMFormFieldValues.remove(ddmFormFieldValue);

		return ddmFormFieldEvaluationResult;
	}

	protected DDMFormFieldEvaluationResult evaluateDDMFormFieldValue(
			DDMFormFieldValue ddmFormFieldValue,
			Set<DDMFormFieldValue> ancestorDDMFormFieldValues,
			DDMFormField ddmFormField)
		throws PortalException {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				ddmFormFieldValue.getName(), ddmFormFieldValue.getInstanceId());

		boolean valid = evaluateBooleanExpression(
			ddmFormField.getValidationExpression(), ancestorDDMFormFieldValues);

		ddmFormFieldEvaluationResult.setValid(valid);

		if (!valid) {
			ddmFormFieldEvaluationResult.setValidationMessage(
				ddmFormField.getValidationMessage());
		}

		boolean visible = evaluateBooleanExpression(
			ddmFormField.getVisibilityExpression(), ancestorDDMFormFieldValues);

		ddmFormFieldEvaluationResult.setVisible(visible);

		List<DDMFormFieldEvaluationResult> nestedDDMFormFieldEvaluationResults =
			evaluateDDMFormFieldValues(
				ddmFormFieldValue.getNestedDDMFormFieldValues(),
				ancestorDDMFormFieldValues);

		ddmFormFieldEvaluationResult. setNestedDDMFormFieldEvaluationResults(
			nestedDDMFormFieldEvaluationResults);

		return ddmFormFieldEvaluationResult;
	}

	protected List<DDMFormFieldEvaluationResult> evaluateDDMFormFieldValues(
			List<DDMFormFieldValue> ddmFormFieldValues,
			Set<DDMFormFieldValue> ancestorDDMFormFieldValues)
		throws PortalException {

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				evaluateDDMFormFieldValue(
					ddmFormFieldValue, ancestorDDMFormFieldValues);

			ddmFormFieldEvaluationResults.add(ddmFormFieldEvaluationResult);
		}

		return ddmFormFieldEvaluationResults;
	}

	protected void setExpressionFactory(ExpressionFactory expressionFactory) {
		_expressionFactory = expressionFactory;
	}

	protected void setExpressionVariables(
		Expression<Boolean> expression,
		List<DDMFormFieldValue> ddmFormFieldValues,
		Set<DDMFormFieldValue> ancestorDDMFormFieldValues) {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			String name = ddmFormFieldValue.getName();

			DDMFormField ddmFormField = _ddmFormFieldsMap.get(name);

			if (ddmFormField.isRepeatable() &&
				!ancestorDDMFormFieldValues.contains(ddmFormFieldValue)) {

				continue;
			}

			Value value = ddmFormFieldValue.getValue();

			setExpressionVariableValue(
				expression, name, ddmFormField.getDataType(),
				value.getString(_locale));

			setExpressionVariables(
				expression, ddmFormFieldValue.getNestedDDMFormFieldValues(),
				ancestorDDMFormFieldValues);
		}
	}

	protected void setExpressionVariableValue(
		Expression<Boolean> expression, String variableName,
		String variableType, String variableValue) {

		if (variableType.equals("boolean")) {
			expression.setBooleanVariableValue(
				variableName, Boolean.valueOf(variableValue));
		}
		else if (variableType.equals("integer")) {
			expression.setIntegerVariableValue(
				variableName, Integer.valueOf(variableValue));
		}
		else if (variableType.equals("string")) {
			expression.setStringVariableValue(variableName, variableValue);
		}
	}

	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private ExpressionFactory _expressionFactory;
	private final Locale _locale;
	private final List<DDMFormFieldValue> _rootDDMFormFieldValues;

}