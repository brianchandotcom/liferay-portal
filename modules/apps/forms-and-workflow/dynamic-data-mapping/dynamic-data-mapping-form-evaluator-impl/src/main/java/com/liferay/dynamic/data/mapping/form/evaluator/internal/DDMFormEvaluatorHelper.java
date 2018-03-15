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

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInvoker;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorContext;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormFieldEvaluationResult;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.AllFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.BelongsToRoleFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.CallFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.GetValueFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.JumpPageFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.SetEnabledFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.SetInvalidFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.SetOptionsFunction;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.functions.SetPropertyFunction;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class DDMFormEvaluatorHelper {

	public DDMFormEvaluatorHelper(
		DDMDataProviderInvoker ddmDataProviderInvoker,
		DDMExpressionFactory ddmExpressionFactory,
		DDMFormEvaluatorContext ddmFormEvaluatorContext,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		JSONFactory jsonFactory, RoleLocalService roleLocalService,
		UserGroupRoleLocalService userGroupRoleLocalService,
		UserLocalService userLocalService) {

		_ddmDataProviderInvoker = ddmDataProviderInvoker;
		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_jsonFactory = jsonFactory;
		_roleLocalService = roleLocalService;
		_userGroupRoleLocalService = userGroupRoleLocalService;
		_userLocalService = userLocalService;

		_ddmForm = ddmFormEvaluatorContext.getDDMForm();

		_ddmFormFieldsMap = _ddmForm.getDDMFormFieldsMap(true);

		_groupId = ddmFormEvaluatorContext.getProperty("groupId");
		_locale = ddmFormEvaluatorContext.getLocale();
		_request = ddmFormEvaluatorContext.getProperty("request");

		createDDMFormFieldValues(ddmFormEvaluatorContext.getDDMFormValues());

		createDDMFormFieldRuleEvaluationResultsMap();

		_resourceBundle = createResourceBundle();

		registerDDMExpressionCustomFunctions();
	}

	public DDMFormEvaluationResult evaluate()
		throws DDMFormEvaluationException {

		for (DDMFormRule ddmFormRule : _ddmForm.getDDMFormRules()) {
			evaluateDDMFormRule(ddmFormRule);
		}

		List<DDMFormFieldEvaluationResult> ddmFormFieldEvaluationResults =
			getDDMFormFieldEvaluationResults();

		setDDMFormFieldEvaluationResultsValidation(
			ddmFormFieldEvaluationResults);

		return DDMFormEvaluationResultBuilder.build(
			ddmFormFieldEvaluationResults, getDisabledPagesIndexes());
	}

	protected Object convertToTargetDataType(
		DDMFormField ddmFormField, Object value) {

		if (value instanceof JSONArray) {
			return value;
		}

		String dataType = ddmFormField.getDataType();

		if (FieldConstants.isNumericType(dataType) ||
			dataType.equals(FieldConstants.BOOLEAN) ||
			dataType.equals(FieldConstants.DATE)) {

			return FieldConstants.getSerializable(
				ddmFormField.getDataType(), String.valueOf(value));
		}

		return value;
	}

	protected DDMFormFieldEvaluationResult createDDMFormFieldEvaluationResult(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
			new DDMFormFieldEvaluationResult(
				ddmFormField.getName(), ddmFormFieldValue.getInstanceId());

		setDDMFormFieldEvaluationResultDataType(
			ddmFormField, ddmFormFieldEvaluationResult);
		setDDMFormFieldEvaluationResultReadOnly(
			ddmFormFieldEvaluationResult, ddmFormField);
		setDDMFormFieldEvaluationResultRequired(
			ddmFormFieldEvaluationResult, ddmFormField);

		setDDMFormFieldEvaluationResultVisibility(
			ddmFormFieldEvaluationResult, ddmFormField, ddmFormFieldValue);

		setDDMFormFieldEvaluationResultValidation(
			ddmFormFieldEvaluationResult, ddmFormField, ddmFormFieldValue);

		Object value = getValue(ddmFormField, ddmFormFieldValue);

		ddmFormFieldEvaluationResult.setValue(
			convertToTargetDataType(ddmFormField, value));

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

		if (ddmFormFieldValues == null) {
			return;
		}

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult =
				createDDMFormFieldEvaluationResult(
					ddmFormField, ddmFormFieldValue);

			ddmFormFieldEvaluationResultInstances.add(
				ddmFormFieldEvaluationResult);

			for (DDMFormFieldValue nestedDDMFormFieldValue :
					ddmFormFieldValue.getNestedDDMFormFieldValues()) {

				DDMFormField nestedDDMFormField =
					nestedDDMFormFieldValue.getDDMFormField();

				ddmFormFieldEvaluationResult =
					createDDMFormFieldEvaluationResult(
						nestedDDMFormField, nestedDDMFormFieldValue);

				ddmFormFieldEvaluationResultInstances.add(
					ddmFormFieldEvaluationResult);
			}
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

	protected ResourceBundle createResourceBundle() {
		ResourceBundleLoader portalResourceBundleLoader =
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader();

		ResourceBundle portalResourceBundle =
			portalResourceBundleLoader.loadResourceBundle(_locale);

		ResourceBundle portletResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", _locale, getClass());

		return new AggregateResourceBundle(
			portletResourceBundle, portalResourceBundle);
	}

	protected void evaluateDDMFormRule(DDMFormRule ddmFormRule)
		throws DDMFormEvaluationException {

		DDMFormRuleEvaluator ddmFormRuleEvaluator = new DDMFormRuleEvaluator(
			ddmFormRule, _ddmExpressionFactory, _ddmExpressionFunctionRegistry);

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

	protected String getDDMFormFieldValidationErrorMessage(
		DDMFormFieldValidation ddmFormFieldValidation) {

		String errorMessage = ddmFormFieldValidation.getErrorMessage();

		if (Validator.isNotNull(errorMessage)) {
			return errorMessage;
		}

		return LanguageUtil.get(_resourceBundle, "this-field-is-invalid");
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

			for (DDMFormFieldValue nestedDDMFormFieldValue :
					ddmFormFieldValue.getNestedDDMFormFieldValues()) {

				if (instanceId.equals(
						nestedDDMFormFieldValue.getInstanceId())) {

					return nestedDDMFormFieldValue;
				}
			}
		}

		return null;
	}

	protected DDMFormFieldValueAccessor<?> getDDMFormFieldValueAccessor(
		String type) {

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(type);

		if (ddmFormFieldValueAccessor != null) {
			return ddmFormFieldValueAccessor;
		}

		return _defaultDDMFormFieldValueAccessor;
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

	protected Set<Integer> getDisabledPagesIndexes() {
		Set<Integer> disabledPagesIndexes = new HashSet<>();

		for (Map.Entry<Integer, Integer> entry : _pageFlow.entrySet()) {
			int fromPageIndex = entry.getKey();
			int toPageIndex = entry.getValue();

			for (int i = fromPageIndex + 1; i < toPageIndex; i++) {
				disabledPagesIndexes.add(i);
			}
		}

		return disabledPagesIndexes;
	}

	protected Object getValue(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		if (ddmFormFieldValue == null) {
			return null;
		}

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			getDDMFormFieldValueAccessor(ddmFormField.getType());

		return ddmFormFieldValueAccessor.getValue(ddmFormFieldValue, _locale);
	}

	protected boolean isDDMFormFieldValueEmpty(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			getDDMFormFieldValueAccessor(ddmFormField.getType());

		return ddmFormFieldValueAccessor.isEmpty(ddmFormFieldValue, _locale);
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

	protected void registerDDMExpressionCustomFunctions() {
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"all",
			new AllFunction(
				_ddmExpressionFactory, _ddmExpressionFunctionRegistry));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"belongsTo",
			new BelongsToRoleFunction(
				_request, _groupId, _roleLocalService,
				_userGroupRoleLocalService, _userLocalService));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"calculate",
			new SetPropertyFunction(
				_ddmFormFieldEvaluationResultsMap, "value"));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"call",
			new CallFunction(
				_ddmDataProviderInvoker, _ddmFormFieldEvaluationResultsMap,
				_request, _jsonFactory));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"getValue",
			new GetValueFunction(
				_ddmForm.getDDMFormFieldsMap(true),
				_ddmFormFieldEvaluationResultsMap,
				_ddmFormFieldTypeServicesTracker));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"jumpPage", new JumpPageFunction(_pageFlow));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"setDataType",
			new SetPropertyFunction(
				_ddmFormFieldEvaluationResultsMap, "dataType"));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"setEnabled",
			new SetEnabledFunction(_ddmFormFieldEvaluationResultsMap));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"setInvalid",
			new SetInvalidFunction(_ddmFormFieldEvaluationResultsMap));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"setOptions",
			new SetOptionsFunction(
				_ddmFormFieldEvaluationResultsMap, _locale, _jsonFactory));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"setMultiple",
			new SetPropertyFunction(
				_ddmFormFieldEvaluationResultsMap, "multiple"));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"setRequired",
			new SetPropertyFunction(
				_ddmFormFieldEvaluationResultsMap, "required"));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"setValue",
			new SetPropertyFunction(
				_ddmFormFieldEvaluationResultsMap, "value"));
		_ddmExpressionFunctionRegistry.registerDDMExpressionFunction(
			"setVisible",
			new SetPropertyFunction(
				_ddmFormFieldEvaluationResultsMap, "visible"));
	}

	protected void setDDMExpressionVariables(
			DDMExpression<Boolean> ddmExpression,
			DDMFormFieldValue ddmFormFieldValue)
		throws DDMExpressionException {

		for (Map.Entry<String, List<DDMFormFieldValue>> entry :
				_ddmFormFieldValuesMap.entrySet()) {

			String ddmFormFieldName = entry.getKey();

			DDMFormField ddmFormField = _ddmFormFieldsMap.get(ddmFormFieldName);

			List<DDMFormFieldValue> ddmFormFieldValues = entry.getValue();

			DDMFormFieldValue selectedDDMFormFieldValue =
				ddmFormFieldValues.get(0);

			if (ddmFormFieldName.equals(ddmFormFieldValue.getName())) {
				selectedDDMFormFieldValue = ddmFormFieldValue;
			}

			Object value = getValue(ddmFormField, selectedDDMFormFieldValue);

			String dataType = ddmFormField.getDataType();

			if (FieldConstants.isNumericType(ddmFormField.getDataType())) {
				if (Validator.isNotNull(value)) {
					ddmExpression.setDoubleVariableValue(
						ddmFormFieldName, GetterUtil.getDouble(value));
				}
			}
			else if (dataType.equals(FieldConstants.BOOLEAN)) {
				if (Validator.isNotNull(value)) {
					ddmExpression.setBooleanVariableValue(
						ddmFormFieldName, GetterUtil.getBoolean(value));
				}
			}
			else {
				ddmExpression.setObjectVariableValue(ddmFormFieldName, value);
			}
		}
	}

	protected void setDDMFormFieldEvaluationResultDataType(
		DDMFormField ddmFormField,
		DDMFormFieldEvaluationResult ddmFormFieldEvaluationResult) {

		ddmFormFieldEvaluationResult.setProperty(
			"dataType", ddmFormField.getDataType());
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
				LanguageUtil.get(_resourceBundle, "this-field-is-required"));

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
					getDDMFormFieldValidationErrorMessage(
						ddmFormFieldValidation));

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

	private final DDMDataProviderInvoker _ddmDataProviderInvoker;
	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMExpressionFunctionRegistry _ddmExpressionFunctionRegistry =
		new DDMExpressionFunctionRegistry();
	private final DDMForm _ddmForm;
	private final Map<String, List<DDMFormFieldEvaluationResult>>
		_ddmFormFieldEvaluationResultsMap = new HashMap<>();
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final Map<String, List<DDMFormFieldValue>> _ddmFormFieldValuesMap =
		new LinkedHashMap<>();
	private final DDMFormFieldValueAccessor<String>
		_defaultDDMFormFieldValueAccessor =
			new DefaultDDMFormFieldValueAccessor();
	private final long _groupId;
	private final JSONFactory _jsonFactory;
	private final Locale _locale;
	private final Map<Integer, Integer> _pageFlow = new HashMap<>();
	private final HttpServletRequest _request;
	private final ResourceBundle _resourceBundle;
	private final RoleLocalService _roleLocalService;
	private final UserGroupRoleLocalService _userGroupRoleLocalService;
	private final UserLocalService _userLocalService;

}