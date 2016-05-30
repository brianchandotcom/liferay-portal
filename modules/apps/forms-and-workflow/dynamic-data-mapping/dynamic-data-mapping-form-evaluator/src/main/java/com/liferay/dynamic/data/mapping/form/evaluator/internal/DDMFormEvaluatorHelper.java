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

import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Pablo Carvalho
 */
public class DDMFormEvaluatorHelper {

	public DDMFormEvaluatorHelper(
		DDMForm ddmForm, DDMFormValues ddmFormValues, Locale locale) {

		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);

		if (ddmFormValues == null) {
			ddmFormValues = createEmptyDDMFormValues(ddmForm);
		}

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

	protected DDMFormValues createEmptyDDMFormValues(DDMForm ddmForm) {
		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setName(ddmFormField.getName());

			Value value = new UnlocalizedValue(StringPool.BLANK);

			if (ddmFormField.isLocalizable()) {
				value = new LocalizedValue(_locale);

				value.addString(_locale, StringPool.BLANK);
			}

			ddmFormFieldValue.setValue(value);

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	protected boolean evaluateBooleanExpression(
			String expressionString,
			Set<DDMFormFieldValue> ancestorDDMFormFieldValues)
		throws PortalException {

		if (Validator.isNull(expressionString)) {
			return true;
		}

		DDMExpression<Boolean> ddmExpression =
			_ddmExpressionFactory.createBooleanDDMExpression(expressionString);

		setDDMExpressionVariables(
			ddmExpression, _rootDDMFormFieldValues, ancestorDDMFormFieldValues);

		try {
			return ddmExpression.evaluate();
		}
		catch (DDMExpressionException ddmee) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Invalid expression or expression that is making " +
						"reference to a field no longer available: " +
							expressionString);
			}
		}

		return true;
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

		boolean visible = evaluateBooleanExpression(
			ddmFormField.getVisibilityExpression(), ancestorDDMFormFieldValues);

		ddmFormFieldEvaluationResult.setVisible(visible);

		if (visible && ddmFormField.isRequired() &&
			isDDMFormFieldValueEmpty(ddmFormFieldValue)) {

			ddmFormFieldEvaluationResult.setErrorMessage(
				LanguageUtil.get(_locale, "this-field-is-required"));

			ddmFormFieldEvaluationResult.setValid(false);
		}
		else if (!isDDMFormFieldValueEmpty(ddmFormFieldValue)) {
			DDMFormFieldValidation ddmFormFieldValidation =
				ddmFormField.getDDMFormFieldValidation();

			String validationExpression = getValidationExpression(
				ddmFormFieldValidation);

			boolean valid = evaluateBooleanExpression(
				validationExpression, ancestorDDMFormFieldValues);

			ddmFormFieldEvaluationResult.setValid(valid);

			if (!valid) {
				ddmFormFieldEvaluationResult.setErrorMessage(
					ddmFormFieldValidation.getErrorMessage());
			}
		}

		List<DDMFormFieldEvaluationResult> nestedDDMFormFieldEvaluationResults =
			evaluateDDMFormFieldValues(
				ddmFormFieldValue.getNestedDDMFormFieldValues(),
				ancestorDDMFormFieldValues);

		ddmFormFieldEvaluationResult.setNestedDDMFormFieldEvaluationResults(
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

	protected String getJSONArrayValueString(String valueString) {
		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(valueString);

			return jsonArray.getString(0);
		}
		catch (JSONException jsone) {
			return valueString;
		}
	}

	protected String getValidationExpression(
		DDMFormFieldValidation ddmFormFieldValidation) {

		if (ddmFormFieldValidation == null) {
			return null;
		}

		return ddmFormFieldValidation.getExpression();
	}

	protected String getValueString(Value value, String type) {
		if (value == null) {
			return null;
		}

		String valueString = value.getString(_locale);

		if (type.equals("select") || type.equals("radio")) {
			valueString = getJSONArrayValueString(valueString);
		}

		return valueString;
	}

	protected boolean isDDMFormFieldValueEmpty(
		DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return true;
		}

		String valueString = GetterUtil.getString(value.getString(_locale));

		if (valueString.isEmpty()) {
			return true;
		}

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		String dataType = ddmFormField.getDataType();

		if (Objects.equals(dataType, "boolean") &&
			Objects.equals(valueString, "false")) {

			return true;
		}

		return false;
	}

	protected void setDDMExpressionFactory(
		DDMExpressionFactory ddmExpressionFactory) {

		_ddmExpressionFactory = ddmExpressionFactory;
	}

	protected void setDDMExpressionVariables(
			DDMExpression<Boolean> ddmExpression,
			List<DDMFormFieldValue> ddmFormFieldValues,
			Set<DDMFormFieldValue> ancestorDDMFormFieldValues)
		throws PortalException {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			String name = ddmFormFieldValue.getName();

			DDMFormField ddmFormField = _ddmFormFieldsMap.get(name);

			if (ddmFormField.isRepeatable() &&
				!ancestorDDMFormFieldValues.contains(ddmFormFieldValue)) {

				continue;
			}

			String valueString = getValueString(
				ddmFormFieldValue.getValue(), ddmFormField.getType());

			if (valueString != null) {
				setExpressionVariableValue(
					ddmExpression, name, ddmFormField.getDataType(),
					valueString);
			}

			setDDMExpressionVariables(
				ddmExpression, ddmFormFieldValue.getNestedDDMFormFieldValues(),
				ancestorDDMFormFieldValues);
		}
	}

	protected void setExpressionVariableValue(
			DDMExpression<Boolean> ddmExpression, String variableName,
			String variableType, String variableValue)
		throws PortalException {

		if (variableType.equals("boolean")) {
			ddmExpression.setBooleanVariableValue(
				variableName, GetterUtil.getBoolean(variableValue));
		}
		else if (variableType.equals("integer")) {
			ddmExpression.setIntegerVariableValue(
				variableName, GetterUtil.getInteger(variableValue));
		}
		else if (variableType.equals("string")) {
			ddmExpression.setStringVariableValue(variableName, variableValue);
		}
	}

	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormEvaluatorHelper.class);

	private DDMExpressionFactory _ddmExpressionFactory;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private JSONFactory _jsonFactory;
	private final Locale _locale;
	private final List<DDMFormFieldValue> _rootDDMFormFieldValues;

}