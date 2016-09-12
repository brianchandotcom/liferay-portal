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

package com.liferay.dynamic.data.mapping.form.evaluator.impl.internal.rules;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumerTracker;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleEvaluatorHelper {

	public DDMFormRuleEvaluatorHelper(
		DDMDataProviderConsumerTracker ddmDataProviderConsumerTracker,
		DDMDataProviderInstanceService ddmDataProviderInstanceService,
		DDMExpressionFactory ddmExpressionFactory, DDMForm ddmForm,
		DDMFormValues ddmFormValues,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		JSONFactory jsonFactory, Locale locale) {

		_ddmDataProviderConsumerTracker = ddmDataProviderConsumerTracker;
		_ddmDataProviderInstanceService = ddmDataProviderInstanceService;
		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmForm = ddmForm;
		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);

		if (ddmFormValues == null) {
			ddmFormValues = createEmptyDDMFormValues(_ddmForm);
		}

		createDDMFormFieldValues(ddmFormValues);

		createMissingDDMFormFieldValues(ddmFormValues);

		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
		_jsonFactory = jsonFactory;
		_locale = locale;
	}

	public List<DDMFormFieldEvaluationResult> evaluate()
		throws DDMFormEvaluationException {

		addDDMFormFieldRuleEvaluationResults();

		List<DDMFormRule> ddmFormRules = _ddmForm.getDDMFormRules();

		if (Validator.isNull(ddmFormRules)) {
			return getDDMFormFieldEvaluationResults();
		}

		for (DDMFormRule ddmFormRule : ddmFormRules) {
			if (!ddmFormRule.isEnabled()) {
				continue;
			}

			if (evaluate(ddmFormRule)) {
				executeActions(ddmFormRule.getActions());
			}
		}

		return getDDMFormFieldEvaluationResults();
	}

	protected void addDDMFormFieldRuleEvaluationResults() {
		List<DDMFormField> ddmFormFields = _ddmForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			createDDMFormFieldRuleEvaluationResult(ddmFormField);
		}
	}

	protected DDMFormFieldEvaluationResult createDDMFormFieldEvaluationResult(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				ddmFormField.getName(), ddmFormFieldValue.getInstanceId());

		ddmFormFieldEvaluationResult.setReadOnly(ddmFormField.isReadOnly());

		setDDMFormFieldEvaluationResultValidation(
			ddmFormFieldEvaluationResult, ddmFormField, ddmFormFieldValue);
		setDDMFormFieldEvaluationResultVisibility(
			ddmFormFieldEvaluationResult, ddmFormField, ddmFormFieldValue);

		Value value = ddmFormFieldValue.getValue();

		String valueString = getValueString(value, ddmFormField.getType());

		if (ddmFormField.getDataType().equals(FieldConstants.NUMBER)) {
			if (Validator.isNotNull(valueString)) {
				ddmFormFieldEvaluationResult.setValue(
					GetterUtil.getDouble(valueString));
			}
		}
		else if (ddmFormField.getDataType().equals(FieldConstants.INTEGER)) {
			if (Validator.isNotNull(valueString)) {
				ddmFormFieldEvaluationResult.setValue(
					GetterUtil.getInteger(valueString));
			}
		}
		else if (ddmFormField.getDataType().equals(FieldConstants.BOOLEAN)) {
			if (Validator.isNotNull(valueString)) {
				ddmFormFieldEvaluationResult.setValue(
					GetterUtil.getBoolean(valueString));
			}
		}
		else {
			ddmFormFieldEvaluationResult.setValue(valueString);
		}

		return ddmFormFieldEvaluationResult;
	}

	protected void createDDMFormFieldRuleEvaluationResult(
		DDMFormField ddmFormField) {

		List<DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultInstances = null;

		if (!_ddmFormFieldEvaluationResults.containsKey(
				ddmFormField.getName())) {

			ddmFormFieldEvaluationResultInstances = new ArrayList<>();

			_ddmFormFieldEvaluationResults.put(
				ddmFormField.getName(), ddmFormFieldEvaluationResultInstances);
		}
		else {
			ddmFormFieldEvaluationResultInstances =
				_ddmFormFieldEvaluationResults.get(ddmFormField.getName());
		}

		List<DDMFormFieldValue> ddmFormFieldValues = _ddmFormFieldValues.get(
			ddmFormField.getName());

		if (ListUtil.isEmpty(ddmFormFieldValues)) {
			return;
		}

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					ddmFormField, ddmFormFieldValue);

			if (!ddmFormFieldEvaluationResultInstances.contains(
					ddmFormFieldEvaluationResult)) {

				ddmFormFieldEvaluationResultInstances.add(
					ddmFormFieldEvaluationResult);
			}

			for (DDMFormFieldValue nestedDDMFormFieldValue :
					ddmFormFieldValue.getNestedDDMFormFieldValues()) {

				DDMFormField nestedDDMFormField =
					nestedDDMFormFieldValue.getDDMFormField();

				createDDMFormFieldRuleEvaluationResult(nestedDDMFormField);
			}
		}
	}

	protected void createDDMFormFieldValues(DDMFormValues ddmFormValues) {
		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			populateDDMFormFieldValues(ddmFormFieldValue);
		}
	}

	protected DDMFormFieldValue createDefaultDDMFormFieldValue(
		DDMFormField ddmFormField, DDMFormValues ddmFormValues) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setDDMFormValues(ddmFormValues);
		ddmFormFieldValue.setName(ddmFormField.getName());

		Value value = null;

		if (ddmFormField.isLocalizable()) {
			value = new LocalizedValue(_locale);

			value.addString(_locale, StringPool.BLANK);
		}
		else {
			value = new UnlocalizedValue(StringPool.BLANK);
		}

		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	protected DDMFormValues createEmptyDDMFormValues(DDMForm ddmForm) {
		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			populateDefaultDDMFormFieldValues(ddmFormField, ddmFormValues);
		}

		return ddmFormValues;
	}

	protected void createMissingDDMFormFieldValues(
		DDMFormValues ddmFormValues) {

		Map<String, DDMFormField> ddmFormFields = _ddmForm.getDDMFormFieldsMap(
			true);

		for (Map.Entry<String, DDMFormField> entry : ddmFormFields.entrySet()) {
			if (!_ddmFormFieldValues.containsKey(entry.getKey())) {
				DDMFormFieldValue ddmFormFieldValue =
					createDefaultDDMFormFieldValue(
						entry.getValue(), ddmFormValues);

				_ddmFormFieldValues.put(
					entry.getKey(), Arrays.asList(ddmFormFieldValue));
			}
		}
	}

	protected boolean evaluate(DDMFormRule ddmFormRule)
		throws DDMFormEvaluationException {

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			_ddmDataProviderConsumerTracker, _ddmDataProviderInstanceService,
			_ddmExpressionFactory, _ddmFormFieldEvaluationResults,
			_ddmFormValuesJSONDeserializer, ddmFormRule.getCondition(),
			_jsonFactory);

		return ddmFormRuleEvaluator.evaluate();
	}

	protected void executeActions(List<String> actions)
		throws DDMFormEvaluationException {

		for (String action : actions) {
			DDMFormRuleEvaluator ddmFormRuleEvaluator =
				new DDMFormRuleEvaluator(
					_ddmDataProviderConsumerTracker,
					_ddmDataProviderInstanceService, _ddmExpressionFactory,
					_ddmFormFieldEvaluationResults,
					_ddmFormValuesJSONDeserializer, action, _jsonFactory);

			ddmFormRuleEvaluator.execute();
		}
	}

	protected List<DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResults() {

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		for (List<DDMFormFieldEvaluationResult>
				ddmFormFieldEvaluationResultInstances :
					_ddmFormFieldEvaluationResults.values()) {

			ddmFormFieldEvaluationResults.addAll(
				ddmFormFieldEvaluationResultInstances);
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

	protected void populateDDMFormFieldValues(
		DDMFormFieldValue ddmFormFieldValue) {

		List<DDMFormFieldValue> ddmFormFieldValues = _ddmFormFieldValues.get(
			ddmFormFieldValue.getName());

		if (ddmFormFieldValues == null) {
			ddmFormFieldValues = new ArrayList<>();

			_ddmFormFieldValues.put(
				ddmFormFieldValue.getName(), ddmFormFieldValues);
		}

		ddmFormFieldValues.add(ddmFormFieldValue);

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			populateDDMFormFieldValues(nestedDDMFormFieldValue);
		}
	}

	protected void populateDefaultDDMFormFieldValues(
		DDMFormField ddmFormField, DDMFormValues ddmFormValues) {

		DDMFormFieldValue ddmFormFieldValue = createDefaultDDMFormFieldValue(
			ddmFormField, ddmFormValues);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			populateDefaultDDMFormFieldValues(
				nestedDDMFormField, ddmFormValues);
		}
	}

	protected void setDDMExpressionVariables(
			DDMExpression<Boolean> ddmExpression,
			DDMFormFieldValue ddmFormFieldValue)
		throws DDMExpressionException {

		for (String ddmFormFieldName : _ddmFormFieldValues.keySet()) {
			DDMFormField ddmFormField = _ddmFormFieldsMap.get(ddmFormFieldName);

			List<DDMFormFieldValue> ddmFormFieldValues =
				_ddmFormFieldValues.get(ddmFormFieldName);

			DDMFormFieldValue selectedDDMFormFieldValue =
				ddmFormFieldValues.get(0);

			if (ddmFormFieldName.equals(ddmFormFieldValue.getName())) {
				selectedDDMFormFieldValue = ddmFormFieldValue;
			}

			Value value = selectedDDMFormFieldValue.getValue();

			String valueString = getValueString(value, ddmFormField.getType());

			if (ddmFormField.getDataType().equals(FieldConstants.INTEGER)) {
				if (Validator.isNotNull(valueString)) {
					ddmExpression.setIntegerVariableValue(
						ddmFormFieldName, GetterUtil.getInteger(valueString));
				}
			}
			else if (ddmFormField.getDataType().equals(
						FieldConstants.BOOLEAN)) {

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

	protected void setDDMFormFieldEvaluationResultValidation(
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult,
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

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
			visibilityExpression.equals("TRUE")) {

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
		DDMFormRuleEvaluatorHelper.class);

	private final DDMDataProviderConsumerTracker
		_ddmDataProviderConsumerTracker;
	private final DDMDataProviderInstanceService
		_ddmDataProviderInstanceService;
	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMForm _ddmForm;
	private final Map<String, List<DDMFormFieldEvaluationResult>>
		_ddmFormFieldEvaluationResults = new HashMap<>();
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final Map<String, List<DDMFormFieldValue>> _ddmFormFieldValues =
		new LinkedHashMap<>();
	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private final JSONFactory _jsonFactory;
	private final Locale _locale;

}