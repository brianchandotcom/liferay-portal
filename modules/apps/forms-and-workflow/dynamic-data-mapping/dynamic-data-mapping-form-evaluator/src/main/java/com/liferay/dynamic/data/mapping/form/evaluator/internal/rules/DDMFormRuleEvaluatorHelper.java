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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDMFormRuleEvaluatorHelper {

	public DDMFormRuleEvaluatorHelper(
		DDMExpressionFactory ddmExpressionFactory, DDMForm ddmForm,
		DDMFormValues ddmFormValues, Locale locale) {

		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmForm = ddmForm;

		if (ddmFormValues == null) {
			_ddmFormValues = createEmptyDDMFormValues(_ddmForm);
		}
		else {
			_ddmFormValues = ddmFormValues;
		}

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
		Map<String, DDMFormField> ddmFormFieldsMap =
			_ddmForm.getDDMFormFieldsMap(true);

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			createDDMFormFieldRuleEvaluationResult(ddmFormField);
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

		String valueString = StringPool.BLANK;

		if (value != null) {
			valueString = GetterUtil.getString(value.getString(_locale));
		}

		if (ddmFormField.getDataType().equals(FieldConstants.NUMBER)) {
			ddmFormFieldEvaluationResult.setValue(Double.valueOf(valueString));
		}
		else {
			ddmFormFieldEvaluationResult.setValue(valueString);
		}

		return ddmFormFieldEvaluationResult;
	}

	protected void createDDMFormFieldRuleEvaluationResult(
		DDMFormField ddmFormField) {

		List<DDMFormFieldEvaluationResult>
			ddmFormFieldEvaluationResultInstances = new ArrayList<>();

		_ddmFormFieldEvaluationResults.put(
			ddmFormField.getName(), ddmFormFieldEvaluationResultInstances);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			_ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			ddmFormField.getName());

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					ddmFormField, ddmFormFieldValue);

			ddmFormFieldEvaluationResultInstances.add(
				ddmFormFieldEvaluationResult);
		}
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

	protected boolean evaluate(DDMFormRule ddmFormRule)
		throws DDMFormEvaluationException {

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			_ddmExpressionFactory, _ddmFormFieldEvaluationResults,
			ddmFormRule.getCondition());

		return ddmFormRuleEvaluator.evaluate();
	}

	protected void executeActions(List<String> actions)
		throws DDMFormEvaluationException {

		for (String action : actions) {
			DDMFormRuleEvaluator ddmFormRuleEvaluator =
				new DDMFormRuleEvaluator(
					_ddmExpressionFactory, _ddmFormFieldEvaluationResults,
					action);

			ddmFormRuleEvaluator.execute();
		}
	}

	protected List<DDMFormFieldEvaluationResult>
		getDDMFormFieldEvaluationResults() {

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			new ArrayList<>();

		for (Map.Entry<String, List<DDMFormFieldEvaluationResult>>
				entry: _ddmFormFieldEvaluationResults.entrySet()) {

			ddmFormFieldEvaluationResults.addAll(
				ListUtil.fromCollection(entry.getValue()));
		}

		return ddmFormFieldEvaluationResults;
	}

	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMForm _ddmForm;
	private final Map<String, List<DDMFormFieldEvaluationResult>>
		_ddmFormFieldEvaluationResults = new HashMap<>();
	private final DDMFormValues _ddmFormValues;
	private final Locale _locale;

}