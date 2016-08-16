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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.rules;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderConsumerTracker;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.VariableDependencies;
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
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
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
		DDMExpressionFactory ddmExpressionFactory, DDMForm ddmForm,
		DDMFormValues ddmFormValues,
		DDMFormValuesJSONDeserializer ddmFormValuesJSONDeserializer,
		JSONFactory jsonFactory, Locale locale) {

		_ddmDataProviderConsumerTracker = ddmDataProviderConsumerTracker;
		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmForm = ddmForm;

		if (ddmFormValues == null) {
			ddmFormValues = createEmptyDDMFormValues(_ddmForm);
		}

		createDDMFormFieldValues(ddmFormValues);

		_ddmFormValuesJSONDeserializer = ddmFormValuesJSONDeserializer;
		_jsonFactory = jsonFactory;
		_locale = locale;
	}

	public List<DDMFormFieldEvaluationResult> evaluate()
		throws DDMFormEvaluationException {

		addDDMFormFieldRuleEvaluationResults();

		addDDMFormRuleForVisibilityAndValidation();

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

	protected void addDDMFormRuleForVisibilityAndValidation() {
		Map<String, DDMFormField> ddmFormFields = _ddmForm.getDDMFormFieldsMap(
			true);

		for (DDMFormField ddmFormField : ddmFormFields.values()) {
			String visibilityExpression =
				ddmFormField.getVisibilityExpression();

			try {
				if (Validator.isNotNull(visibilityExpression) &&
					!visibilityExpression.equals("TRUE")) {

					createDDMFormRule(
						ddmFormField.getName(), visibilityExpression);
				}

				DDMFormFieldValidation ddmFormFieldValidation =
					ddmFormField.getDDMFormFieldValidation();

				if (ddmFormFieldValidation != null) {
					createDDMFormRule(
						ddmFormField.getName(), ddmFormFieldValidation);
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Error processing visiblity expression for field: " +
							ddmFormField.getName() +
							", visibilityExpression: " +
							visibilityExpression,
						e);
				}
			}
		}
	}

	protected DDMFormFieldEvaluationResult createDDMFormFieldEvaluationResult(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				ddmFormField.getName(), ddmFormFieldValue.getInstanceId());

		ddmFormFieldEvaluationResult.setErrorMessage(StringPool.BLANK);
		ddmFormFieldEvaluationResult.setReadOnly(ddmFormField.isReadOnly());
		ddmFormFieldEvaluationResult.setValid(true);
		ddmFormFieldEvaluationResult.setVisible(true);

		Value value = ddmFormFieldValue.getValue();

		String valueString = getValueString(value, ddmFormField.getType());

		if (ddmFormField.getDataType().equals(FieldConstants.NUMBER)) {
			ddmFormFieldEvaluationResult.setValue(Double.valueOf(valueString));
		}
		else if (ddmFormField.getDataType().equals(FieldConstants.INTEGER)) {
			ddmFormFieldEvaluationResult.setValue(Integer.valueOf(valueString));
		}
		else if (ddmFormField.getDataType().equals(FieldConstants.BOOLEAN)) {
			ddmFormFieldEvaluationResult.setValue(Boolean.valueOf(valueString));
		}
		else {
			ddmFormFieldEvaluationResult.setValue(valueString);
		}

		return ddmFormFieldEvaluationResult;
	}

	protected void createDDMFormFieldRuleEvaluationResult(
		DDMFormField ddmFormField) {

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		_ddmFormFieldEvaluationResults.put(
			ddmFormField.getName(), ddmFormFieldEvaluationResults);

		List<DDMFormFieldValue> ddmFormFieldValues = _ddmFormFieldValues.get(
			ddmFormField.getName());

		if (ListUtil.isEmpty(ddmFormFieldValues)) {
			return;
		}

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					ddmFormField, ddmFormFieldValue);

			ddmFormFieldEvaluationResults.add(ddmFormFieldEvaluationResult);

			List<DDMFormFieldEvaluationResult>
				nestedDDMFormFieldEvaluationResults =
					ddmFormFieldEvaluationResult.
						getNestedDDMFormFieldEvaluationResults();

			for (DDMFormFieldValue nestedDDMFormFieldValue :
					ddmFormFieldValue.getNestedDDMFormFieldValues()) {

				DDMFormField nestedDDMFormField =
					nestedDDMFormFieldValue.getDDMFormField();

				List<DDMFormFieldEvaluationResult>
					nestedDDMFormFieldEvaluationResultInstances;

				if (!_ddmFormFieldEvaluationResults.containsKey(
						nestedDDMFormField.getName())) {

					nestedDDMFormFieldEvaluationResultInstances =
						new ArrayList<>();

					_ddmFormFieldEvaluationResults.put(
						nestedDDMFormField.getName(),
						nestedDDMFormFieldEvaluationResultInstances);
				}
				else {
					nestedDDMFormFieldEvaluationResultInstances =
						_ddmFormFieldEvaluationResults.get(
							nestedDDMFormField.getName());
				}

				DDMFormFieldEvaluationResult
					nestedDDMFormFieldEvaluationResult =
						createDDMFormFieldEvaluationResult(
							nestedDDMFormField, nestedDDMFormFieldValue);

				nestedDDMFormFieldEvaluationResults.add(
					nestedDDMFormFieldEvaluationResult);

				nestedDDMFormFieldEvaluationResultInstances.add(
					nestedDDMFormFieldEvaluationResult);
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

	protected void createDDMFormRule(
			String ddmFormFieldName,
			DDMFormFieldValidation ddmFormFieldValidation)
		throws Exception {

		if (Validator.isNull(ddmFormFieldValidation.getExpression())) {
			return;
		}

		String translatedExpression = translateExpression(
			ddmFormFieldValidation.getExpression());

		String errorMessage = StringUtil.replace(
			ddmFormFieldValidation.getErrorMessage(), CharPool.QUOTE,
			StringPool.DOUBLE_APOSTROPHE);

		String action = String.format(
			"set(fieldAt(\"%s\", 0), \"valid\", %s, \"%s\")", ddmFormFieldName,
			translatedExpression, errorMessage);

		DDMFormRule ddmFormRule = new DDMFormRule("TRUE", action);

		_ddmForm.addDDMFormRule(ddmFormRule);
	}

	protected void createDDMFormRule(
			String ddmFormFieldName, String visibilityExpression)
		throws Exception {

		String action = String.format(
			"set(fieldAt(\"%s\", 0), \"visible\", %s)", ddmFormFieldName,
			translateExpression(visibilityExpression));

		DDMFormRule ddmFormRule = new DDMFormRule("TRUE", action);

		_ddmForm.addDDMFormRule(ddmFormRule);
	}

	protected DDMFormFieldValue createDefaultDDMFormFieldValue(
		DDMFormField ddmFormField) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

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
			DDMFormFieldValue ddmFormFieldValue =
				createDefaultDDMFormFieldValue(ddmFormField);

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	protected boolean evaluate(DDMFormRule ddmFormRule)
		throws DDMFormEvaluationException {

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			_ddmDataProviderConsumerTracker, _ddmExpressionFactory,
			_ddmFormFieldEvaluationResults, _ddmFormValuesJSONDeserializer,
			ddmFormRule.getCondition(), _jsonFactory);

		return ddmFormRuleEvaluator.evaluate();
	}

	protected void executeActions(List<String> actions)
		throws DDMFormEvaluationException {

		for (String action : actions) {
			DDMFormRuleEvaluator ddmFormRuleEvaluator =
				new DDMFormRuleEvaluator(
					_ddmDataProviderConsumerTracker, _ddmExpressionFactory,
					_ddmFormFieldEvaluationResults,
					_ddmFormValuesJSONDeserializer, action, _jsonFactory);

			ddmFormRuleEvaluator.execute();
		}
	}

	protected List<DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResults() {

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		List<DDMFormField> ddmFormFields = _ddmForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			ddmFormFieldEvaluationResults.addAll(
				_ddmFormFieldEvaluationResults.get(ddmFormField.getName()));
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

	protected String translateExpression(String expression) throws Exception {
		DDMExpression<Boolean> booleanDDMExpression =
			_ddmExpressionFactory.createBooleanDDMExpression(expression);

		Map<String, VariableDependencies> variableDependencies =
			booleanDDMExpression.getVariableDependenciesMap();

		for (String variable : variableDependencies.keySet()) {
			if (_ddmFormFieldEvaluationResults.containsKey(variable)) {
				expression = expression.replaceAll(
					String.format(
						"([,\\s\\(]+|.*)(%s)([,\\s\\)]+|.*)", variable),
					String.format(
						"$1get(fieldAt(\"%s\", 0), \"value\")$3", variable));
			}
		}

		return expression;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormRuleEvaluatorHelper.class);

	private final DDMDataProviderConsumerTracker
		_ddmDataProviderConsumerTracker;
	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMForm _ddmForm;
	private final Map<String, List<DDMFormFieldEvaluationResult>>
		_ddmFormFieldEvaluationResults = new HashMap<>();
	private final Map<String, List<DDMFormFieldValue>> _ddmFormFieldValues =
		new LinkedHashMap<>();
	private final DDMFormValuesJSONDeserializer _ddmFormValuesJSONDeserializer;
	private final JSONFactory _jsonFactory;
	private final Locale _locale;

}