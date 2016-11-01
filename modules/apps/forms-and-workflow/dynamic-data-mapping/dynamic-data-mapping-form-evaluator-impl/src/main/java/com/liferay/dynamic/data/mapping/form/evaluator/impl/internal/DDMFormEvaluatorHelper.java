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

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderTracker;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.functions.CallFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.functions.GetPropertyFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.functions.SetEnabledFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.functions.SetInvalidFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.functions.SetPropertyFunction;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class DDMFormEvaluatorHelper {

	public DDMFormEvaluatorHelper(
		DDMDataProviderTracker ddmDataProviderTracker,
		DDMDataProviderInstanceService ddmDataProviderInstanceService,
		DDMExpressionFactory ddmExpressionFactory, DDMForm ddmForm,
		DDMFormValues ddmFormValues,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		JSONFactory jsonFactory, Locale locale) {

		_ddmDataProviderTracker = ddmDataProviderTracker;
		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmForm = ddmForm;
		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);
		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
		_jsonFactory = jsonFactory;
		_locale = locale;

		createDDMFormFieldValues(ddmFormValues);

		createDDMFormFieldRuleEvaluationResultsMap();
	}

	public DDMFormEvaluationResult evaluate()
		throws DDMFormEvaluationException {

		for (DDMFormRule ddmFormRule : _ddmForm.getDDMFormRules()) {
			evaluateDDMFormRule(ddmFormRule);
		}

		DDMFormEvaluationResult ddmFormEvaluationResult =
			new DDMFormEvaluationResult();

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			getDDMFormFieldEvaluationResults();

		setDDMFormFieldEvaluationResultsValidation(
			ddmFormFieldEvaluationResults);

		ddmFormEvaluationResult.setDDMFormFieldEvaluationResults(
			ddmFormFieldEvaluationResults);

		return ddmFormEvaluationResult;
	}

	protected DDMFormFieldEvaluationResult createDDMFormFieldEvaluationResult(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				ddmFormField.getName(), ddmFormFieldValue.getInstanceId());

		setDDMFormFieldEvaluationResultReadOnly(
			ddmFormFieldEvaluationResult, ddmFormField);
		setDDMFormFieldEvaluationResultRequired(
			ddmFormFieldEvaluationResult, ddmFormField);

		setDDMFormFieldEvaluationResultVisibility(
			ddmFormFieldEvaluationResult, ddmFormField, ddmFormFieldValue);

		setDDMFormFieldEvaluationResultValidation(
			ddmFormFieldEvaluationResult, ddmFormField, ddmFormFieldValue);

		String valueString = getValueString(
			ddmFormFieldValue.getValue(), ddmFormField.getType());

		Object value = FieldConstants.getSerializable(
			ddmFormField.getDataType(), valueString);

		ddmFormFieldEvaluationResult.setValue(value);

		return ddmFormFieldEvaluationResult;
	}

	protected void createDDMFormFieldRuleEvaluationResultsMap() {
		Map<String, DDMFormField> ddmFormFieldMap =
			_ddmForm.getDDMFormFieldsMap(true);

		for (DDMFormField ddmFormField : ddmFormFieldMap.values()) {
			createDDMFormFieldRuleEvaluationResultsMap(ddmFormField);
		}
	}

	protected void createDDMFormFieldRuleEvaluationResultsMap(
		DDMFormField ddmFormField) {

		List<DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultInstances = new ArrayList<>();

		List<DDMFormFieldValue> ddmFormFieldValues = _ddmFormFieldValuesMap.get(
			ddmFormField.getName());

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					ddmFormField, ddmFormFieldValue);

			ddmFormFieldEvaluationResultInstances.add(
				ddmFormFieldEvaluationResult);
		}

		_ddmFormFieldEvaluationResultsMap.put(
			ddmFormField.getName(), ddmFormFieldEvaluationResultInstances);
	}

	protected void createDDMFormFieldValues(DDMFormValues ddmFormValues) {
		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			populateDDMFormFieldValues(ddmFormFieldValue);
		}
	}

	protected void evaluateDDMFormRule(DDMFormRule ddmFormRule)
		throws DDMFormEvaluationException {

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			ddmFormRule, _ddmExpressionFactory);

		registerDDMExpressionCustomFunctions(ddmFormRuleEvaluator);

		ddmFormRuleEvaluator.evaluate();
	}

	protected List<DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResults() {

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		for (List<DDMFormFieldEvaluationResult>
				ddmFormFieldEvaluationResultInstances :
					_ddmFormFieldEvaluationResultsMap.values()) {

			ddmFormFieldEvaluationResults.addAll(
				ddmFormFieldEvaluationResultInstances);
		}

		return ddmFormFieldEvaluationResults;
	}

	protected DDMFormFieldValue getDDMFormFieldValue(
		String ddmFormFieldName, String instanceId) {

		List<DDMFormFieldValue> ddmFormFieldValues = _ddmFormFieldValuesMap.get(
			ddmFormFieldName);

		if (ListUtil.isEmpty(ddmFormFieldValues)) {
			return null;
		}

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			if (instanceId.equals(ddmFormFieldValue.getInstanceId())) {
				return ddmFormFieldValue;
			}
		}

		return null;
	}

	protected boolean getDefaultBooleanPropertyState(
		String functionName, String ddmFormFieldName, boolean defaultValue) {

		String setFieldAction = String.format(
			"%s('%s', true)", functionName, ddmFormFieldName);

		for (DDMFormRule ddmFormRule : _ddmForm.getDDMFormRules()) {
			for (String action : ddmFormRule.getActions()) {
				if (Objects.equals(setFieldAction, action)) {
					return false;
				}
			}
		}

		return defaultValue;
	}

	protected String getJSONArrayValueString(String valueString) {
		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(valueString);

			return jsonArray.getString(0);
		}
		catch (JSONException jsone) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			return valueString;
		}
	}

	protected String getValueString(Value value, String type) {
		if (value == null) {
			return null;
		}

		String valueString = GetterUtil.getString(value.getString(_locale));

		if (type.equals("select") || type.equals("radio")) {
			valueString = getJSONArrayValueString(valueString);
		}

		return valueString;
	}

	protected boolean isDDMFormFieldValueEmpty(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return true;
		}

		String valueString = value.getString(_locale);

		if (Validator.isNull(StringUtil.trim(valueString))) {
			return true;
		}

		String dataType = ddmFormField.getDataType();

		if (Objects.equals(dataType, "boolean") &&
			Objects.equals(valueString, "false")) {

			return true;
		}

		return false;
	}

	protected void populateDDMFormFieldValues(
		DDMFormFieldValue ddmFormFieldValue) {

		List<DDMFormFieldValue> ddmFormFieldValues = _ddmFormFieldValuesMap.get(
			ddmFormFieldValue.getName());

		if (ddmFormFieldValues == null) {
			ddmFormFieldValues = new ArrayList<>();

			_ddmFormFieldValuesMap.put(
				ddmFormFieldValue.getName(), ddmFormFieldValues);
		}

		ddmFormFieldValues.add(ddmFormFieldValue);

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			populateDDMFormFieldValues(nestedDDMFormFieldValue);
		}
	}

	protected void registerDDMExpressionCustomFunctions(
		DDMFormRuleEvaluator ddmFormRuleEvaluator) {

		ddmFormRuleEvaluator.setDDMExpressionFunction(
			"call",
			new CallFunction(
				_ddmDataProviderTracker, _ddmDataProviderInstanceService,
				_ddmFormFieldEvaluationResultsMap,
				_ddmFormValuesJSONDeserializer, _jsonFactory));
		ddmFormRuleEvaluator.setDDMExpressionFunction(
			"getValue",
			new GetPropertyFunction(
				_ddmFormFieldEvaluationResultsMap, "value"));
		ddmFormRuleEvaluator.setDDMExpressionFunction(
			"setEnabled",
			new SetEnabledFunction(_ddmFormFieldEvaluationResultsMap));
		ddmFormRuleEvaluator.setDDMExpressionFunction(
			"setInvalid",
			new SetInvalidFunction(_ddmFormFieldEvaluationResultsMap));
		ddmFormRuleEvaluator.setDDMExpressionFunction(
			"setRequired",
			new SetPropertyFunction(
				_ddmFormFieldEvaluationResultsMap, "required"));
		ddmFormRuleEvaluator.setDDMExpressionFunction(
			"setValue",
			new SetPropertyFunction(
				_ddmFormFieldEvaluationResultsMap, "value"));
		ddmFormRuleEvaluator.setDDMExpressionFunction(
			"setVisible",
			new SetPropertyFunction(
				_ddmFormFieldEvaluationResultsMap, "visible"));
	}

	protected void setDDMExpressionVariables(
			DDMExpression<Boolean> ddmExpression,
			DDMFormFieldValue ddmFormFieldValue)
		throws DDMExpressionException {

		for (String ddmFormFieldName : _ddmFormFieldValuesMap.keySet()) {
			DDMFormField ddmFormField = _ddmFormFieldsMap.get(ddmFormFieldName);

			List<DDMFormFieldValue> ddmFormFieldValues =
				_ddmFormFieldValuesMap.get(ddmFormFieldName);

			DDMFormFieldValue selectedDDMFormFieldValue =
				ddmFormFieldValues.get(0);

			if (ddmFormFieldName.equals(ddmFormFieldValue.getName())) {
				selectedDDMFormFieldValue = ddmFormFieldValue;
			}

			String valueString = getValueString(
				selectedDDMFormFieldValue.getValue(), ddmFormField.getType());

			String dataType = ddmFormField.getDataType();

			if (ddmFormField.getDataType().equals(FieldConstants.INTEGER)) {
				if (Validator.isNotNull(valueString)) {
					ddmExpression.setIntegerVariableValue(
						ddmFormFieldName, GetterUtil.getInteger(valueString));
				}
			}
			else if (dataType.equals(FieldConstants.BOOLEAN)) {
				if (Validator.isNotNull(valueString)) {
					ddmExpression.setBooleanVariableValue(
						ddmFormFieldName, GetterUtil.getBoolean(valueString));
				}
			}
			else {
				ddmExpression.setStringVariableValue(
					ddmFormFieldName, valueString);
			}
		}
	}

	protected void setDDMFormFieldEvaluationResultReadOnly(
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		DDMFormField ddmFormField) {

		boolean enabled = getDefaultBooleanPropertyState(
			"setEnabled", ddmFormField.getName(), !ddmFormField.isReadOnly());

		ddmFormFieldEvaluationResult.setReadOnly(!enabled);
	}

	protected void setDDMFormFieldEvaluationResultRequired(
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		DDMFormField ddmFormField) {

		boolean required = getDefaultBooleanPropertyState(
			"setRequired", ddmFormField.getName(), ddmFormField.isRequired());

		ddmFormFieldEvaluationResult.setRequired(required);
	}

	protected void setDDMFormFieldEvaluationResultsValidation(
		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults) {

		for (DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult :
				ddmFormFieldEvaluationResults) {

			String ddmFormFieldName = ddmFormFieldEvaluationResult.getName();

			DDMFormFieldValue ddmFormFieldValue = getDDMFormFieldValue(
				ddmFormFieldName, ddmFormFieldEvaluationResult.getInstanceId());

			setDDMFormFieldEvaluationResultValidation(
				ddmFormFieldEvaluationResult,
				_ddmFormFieldsMap.get(ddmFormFieldName), ddmFormFieldValue);
		}
	}

	protected void setDDMFormFieldEvaluationResultValidation(
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		boolean required = ddmFormFieldEvaluationResult.isRequired();
		boolean emptyValue = isDDMFormFieldValueEmpty(
			ddmFormField, ddmFormFieldValue);

		if (!required && emptyValue) {
			return;
		}

		boolean visible = ddmFormFieldEvaluationResult.isVisible();

		if (required && visible && emptyValue) {
			ddmFormFieldEvaluationResult.setErrorMessage(
				LanguageUtil.get(_locale, "this-field-is-required"));

			ddmFormFieldEvaluationResult.setValid(false);

			return;
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		if (ddmFormFieldValidation == null) {
			return;
		}

		String validationExpression = ddmFormFieldValidation.getExpression();

		if (Validator.isNull(validationExpression)) {
			return;
		}

		try {
			DDMExpression<Boolean> ddmExpression =
				_ddmExpressionFactory.createBooleanDDMExpression(
					validationExpression);

			setDDMExpressionVariables(ddmExpression, ddmFormFieldValue);

			boolean valid = ddmExpression.evaluate();

			if (!valid) {
				ddmFormFieldEvaluationResult.setErrorMessage(
					ddmFormFieldValidation.getErrorMessage());
				ddmFormFieldEvaluationResult.setValid(false);
			}
		}
		catch (DDMExpressionException ddmee) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Error processing validation expression \"%s\" for " +
							"field \"%s\"",
						validationExpression, ddmFormField.getName()),
					ddmee);
			}
		}
	}

	protected void setDDMFormFieldEvaluationResultVisibility(
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		String visibilityExpression = ddmFormField.getVisibilityExpression();

		if (Validator.isNull(visibilityExpression) ||
			StringUtil.equalsIgnoreCase(visibilityExpression, "TRUE")) {

			boolean defaultState = getDefaultBooleanPropertyState(
				"setVisible", ddmFormField.getName(), true);

			ddmFormFieldEvaluationResult.setVisible(defaultState);

			return;
		}

		try {
			DDMExpression<Boolean> ddmExpression =
				_ddmExpressionFactory.createBooleanDDMExpression(
					visibilityExpression);

			setDDMExpressionVariables(ddmExpression, ddmFormFieldValue);

			ddmFormFieldEvaluationResult.setVisible(ddmExpression.evaluate());
		}
		catch (DDMExpressionException ddmee) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Error processing visibility expression \"%s\" for " +
							"field \"%s\"",
						visibilityExpression, ddmFormField.getName()),
					ddmee);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormEvaluatorHelper.class);

	private final DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;
	private final DDMDataProviderTracker _ddmDataProviderTracker;
	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMForm _ddmForm;
	private final Map<String, List<DDMFormFieldEvaluationResult>>
		_ddmFormFieldEvaluationResultsMap = new HashMap<>();
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final Map<String, List<DDMFormFieldValue>> _ddmFormFieldValuesMap =
		new LinkedHashMap<>();
	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private final JSONFactory _jsonFactory;
	private final Locale _locale;

}