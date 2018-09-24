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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.helper;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.expression.DDMFormEvaluatorExpressionActionHandler;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.expression.DDMFormEvaluatorExpressionFieldAccessor;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.expression.DDMFormEvaluatorExpressionObserver;
import com.liferay.dynamic.data.mapping.form.evaluator.internal.expression.DDMFormEvaluatorExpressionParameterAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* @author Rafael Praxedes
*/
public class DDMFormEvaluatorHelper {

	public DDMFormEvaluatorHelper(
		DDMFormEvaluatorEvaluateRequest ddmFormEvaluatorEvaluateRequest,
		DDMExpressionFactory ddmExpressionFactory,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormEvaluatorEvaluateRequest = ddmFormEvaluatorEvaluateRequest;
		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;

		createResourceBundle(_ddmFormEvaluatorEvaluateRequest.getLocale());

		_ddmFormEvaluatorFormValuesHelper =
			new DDMFormEvaluatorFormValuesHelper(
				_ddmFormEvaluatorEvaluateRequest.getDDMFormValues());

		ddmFormEvaluatorExpressionObserver =
			new DDMFormEvaluatorExpressionObserver(
				_ddmFormEvaluatorFormValuesHelper,
				_ddmFormFieldsPropertyChanges);

		ddmFormEvaluatorExpressionActionHandler =
			new DDMFormEvaluatorExpressionActionHandler(_pageFlow);

		_ddmForm = ddmFormEvaluatorEvaluateRequest.getDDMForm();

		_ddmFormFieldsMap = _ddmForm.getDDMFormFieldsMap(true);

		ddmFormEvaluatorDDMExpressionFieldAccessor =
			new DDMFormEvaluatorExpressionFieldAccessor(
				_ddmFormEvaluatorFormValuesHelper, _ddmFormFieldsMap,
				_ddmFormFieldsPropertyChanges, _ddmFormFieldTypeServicesTracker,
				ddmFormEvaluatorEvaluateRequest.getLocale());

		ddmFormEvaluatorExpressionParameterAccessor =
			new DDMFormEvaluatorExpressionParameterAccessor(
				_ddmFormEvaluatorEvaluateRequest);

		_ddmFormRuleHelper = new DDMFormEvaluatorRuleHelper(
			_ddmFormFieldsMap, ddmFormEvaluatorExpressionObserver);
	}

	public DDMFormEvaluatorEvaluateResponse evaluate() {
		evaluateVisibilityExpressions();

		List<DDMFormRule> ddmFormRules = _ddmForm.getDDMFormRules();

		Stream<DDMFormRule> stream = ddmFormRules.stream();

		stream.filter(
			DDMFormRule::isEnabled
		).forEach(
			this::evaluateDDMFormRule
		);

		verifyFieldsMarkedAsRequired();

		validateFields();

		return buildDDMFormEvaluatorEvaluateResponse();
	}

	protected DDMFormEvaluatorEvaluateResponse
		buildDDMFormEvaluatorEvaluateResponse() {

		DDMFormEvaluatorEvaluateResponse.Builder formEvaluatorEvaluateResponse =
			DDMFormEvaluatorEvaluateResponse.Builder.newBuilder(
				getDDMFormFieldsPropertyChanges());

		formEvaluatorEvaluateResponse.withDisabledPagesIndexes(
			getDisabledPagesIndexes());

		return formEvaluatorEvaluateResponse.build();
	}

	protected void createResourceBundle(Locale locale) {
		ResourceBundleLoader portalResourceBundleLoader =
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader();

		ResourceBundle portalResourceBundle =
			portalResourceBundleLoader.loadResourceBundle(locale);

		ResourceBundle portletResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		_resourceBundle = new AggregateResourceBundle(
			portletResourceBundle, portalResourceBundle);
	}

	protected void evaluateDDMFormRule(DDMFormRule ddmFormRule) {
		List<String> actions = ddmFormRule.getActions();

		if (evaluateDDMFormRuleCondition(ddmFormRule.getCondition())) {
			Stream<String> stream = actions.stream();

			evaluateDDMFormRuleAction(
				stream.collect(Collectors.joining(" AND ")));
		}
		else {
			_ddmFormRuleHelper.checkFieldAffectedByAction(ddmFormRule);
		}
	}

	protected void evaluateDDMFormRuleAction(String action) {
		try {
			evaluateExpression(action);
		}
		catch (DDMExpressionException ddmee) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmee, ddmee);
			}
		}
	}

	protected boolean evaluateDDMFormRuleCondition(String condition) {
		try {
			return evaluateExpression(condition);
		}
		catch (DDMExpressionException ddmee) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmee, ddmee);
			}

			return false;
		}
	}

	protected <T> T evaluateExpression(String expression)
		throws DDMExpressionException {

		CreateExpressionRequest.Builder createExpressionRequestBuilder =
			CreateExpressionRequest.Builder.newBuilder(expression);

		createExpressionRequestBuilder.withDDMExpressionActionHandler(
			ddmFormEvaluatorExpressionActionHandler
		).withDDMExpressionFieldAccessor(
			ddmFormEvaluatorDDMExpressionFieldAccessor
		).withDDMExpressionObserver(
			ddmFormEvaluatorExpressionObserver
		).withDDMExpressionParameterAccessor(
			ddmFormEvaluatorExpressionParameterAccessor
		);

		DDMExpression<T> ddmExpression = _ddmExpressionFactory.createExpression(
			createExpressionRequestBuilder.build());

		return (T)ddmExpression.evaluate();
	}

	protected void evaluateVisibilityExpression(
		Map.Entry<String, String> entry) {

		try {
			UpdateFieldPropertyRequest.Builder builder =
				UpdateFieldPropertyRequest.Builder.newBuilder(
					entry.getKey(), "visible",
					evaluateExpression(entry.getValue()));

			ddmFormEvaluatorExpressionObserver.updateFieldProperty(
				builder.build());
		}
		catch (DDMExpressionException ddmee) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmee, ddmee);
			}
		}
	}

	protected void evaluateVisibilityExpressions() {
		Collection<DDMFormField> ddmFormFields = _ddmFormFieldsMap.values();

		Stream<DDMFormField> ddmFormFieldStream = ddmFormFields.stream();

		Map<String, String> nameVisibilityExpressionMap =
			ddmFormFieldStream.filter(
				field ->
					Validator.isNotNull(field.getVisibilityExpression())
			).collect(
				Collectors.toMap(
					field -> field.getName(),
					field -> field.getVisibilityExpression())
			);

		Set<Map.Entry<String, String>> entrySet =
			nameVisibilityExpressionMap.entrySet();

		Stream<Map.Entry<String, String>> entryStream = entrySet.stream();

		entryStream.forEach(this::evaluateVisibilityExpression);
	}

	protected boolean fieldsWithValidations(DDMFormField ddmFormField) {
		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		if (ddmFormFieldValidation == null) {
			return false;
		}

		return Validator.isNotNull(ddmFormFieldValidation.getExpression());
	}

	protected boolean filterVisibleFieldsMarkedAsRequired(
		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey) {

		if (!getBooleanPropertyValue(
				ddmFormFieldContextKey, "required", false)) {

			return false;
		}

		return getBooleanPropertyValue(ddmFormFieldContextKey, "visible", true);
	}

	protected boolean getBooleanPropertyValue(
		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey,
		String booleanPropertyName, boolean defaultValue) {

		Map<String, Object> changedProperties =
			_ddmFormFieldsPropertyChanges.getOrDefault(
				ddmFormFieldContextKey, Collections.emptyMap());

		if (changedProperties.containsKey(booleanPropertyName)) {
			return MapUtil.getBoolean(changedProperties, booleanPropertyName);
		}
		else {
			DDMFormField ddmFormField = _ddmFormFieldsMap.get(
				ddmFormFieldContextKey.getName());

			return GetterUtil.getBoolean(
				ddmFormField.getProperty(booleanPropertyName), defaultValue);
		}
	}

	protected Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
		getDDMFormFieldsPropertyChanges() {

		return _ddmFormFieldsPropertyChanges;
	}

	protected DDMFormFieldValidation getDDMFormFieldValidation(String field) {
		DDMFormField ddmFormField = _ddmFormFieldsMap.get(field);

		return ddmFormField.getDDMFormFieldValidation();
	}

	protected DDMFormFieldValueAccessor<?> getDDMFormFieldValueAccessor(
		String type) {

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(type);

		if (ddmFormFieldValueAccessor != null) {
			return ddmFormFieldValueAccessor;
		}

		return defaultDDMFormFieldValueAccessor;
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

	protected boolean isFieldEmpty(
		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey) {

		DDMFormFieldValue ddmFormFieldValue =
			_ddmFormEvaluatorFormValuesHelper.getDDMFormFieldValue(
				ddmFormFieldContextKey);

		if (ddmFormFieldValue == null) {
			return true;
		}

		Object value =
			ddmFormEvaluatorDDMExpressionFieldAccessor.getFieldPropertyChanged(
				ddmFormFieldContextKey, "value");

		if (value != null) {
			updateDDMFormFieldValue(ddmFormFieldValue, value);
		}

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormFieldContextKey.getName());

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			getDDMFormFieldValueAccessor(ddmFormField.getType());

		if (ddmFormFieldValueAccessor.isEmpty(
				ddmFormFieldValue,
				_ddmFormEvaluatorEvaluateRequest.getLocale())) {

			return true;
		}

		return false;
	}

	protected void setRequiredErrorMessage(
		DDMFormEvaluatorFieldContextKey fieldContextKey) {

		Map<String, Object> properties = new HashMap<>();

		properties.put("valid", false);

		UpdateFieldPropertyRequest.Builder builder =
			UpdateFieldPropertyRequest.Builder.newBuilder(
				fieldContextKey.getName(), "errorMessage",
				LanguageUtil.get(_resourceBundle, "this-field-is-required"));

		builder.withInstanceId(
			fieldContextKey.getInstanceId()
		).withParameter(
			"valid", false
		);

		ddmFormEvaluatorExpressionObserver.updateFieldProperty(builder.build());
	}

	protected void updateDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue, Object newValue) {

		Value value = ddmFormFieldValue.getValue();

		Locale locale = value.getDefaultLocale();

		if (value.isLocalized()) {
			locale = _ddmFormEvaluatorEvaluateRequest.getLocale();
		}

		value.addString(locale, String.valueOf(newValue));
	}

	protected void validateField(
		Map.Entry<String, DDMFormFieldValidation> entry) {

		String fieldName = entry.getKey();

		DDMFormFieldValidation ddmFormFieldValidation = entry.getValue();

		boolean valid = false;

		try {
			valid = evaluateExpression(ddmFormFieldValidation.getExpression());
		}
		catch (DDMExpressionException ddmee) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmee, ddmee);
			}
		}

		UpdateFieldPropertyRequest updateFieldPropertyRequest = null;

		if (valid) {
			UpdateFieldPropertyRequest.Builder builder =
				UpdateFieldPropertyRequest.Builder.newBuilder(
					fieldName, "valid", true);

			updateFieldPropertyRequest = builder.build();
		}
		else {
			String errorMessage = ddmFormFieldValidation.getErrorMessage();

			if (errorMessage == null) {
				errorMessage = LanguageUtil.get(
					_resourceBundle, "this-field-is-invalid");
			}

			UpdateFieldPropertyRequest.Builder builder =
				UpdateFieldPropertyRequest.Builder.newBuilder(
					fieldName, "valid", false);

			updateFieldPropertyRequest = builder.withParameter(
				"errorMessage", errorMessage
			).build();
		}

		ddmFormEvaluatorExpressionObserver.updateFieldProperty(
			updateFieldPropertyRequest);
	}

	protected void validateFields() {
		Collection<DDMFormField> ddmFormFields = _ddmFormFieldsMap.values();

		Stream<DDMFormField> ddmFormFieldStream = ddmFormFields.stream();

		Map<String, DDMFormFieldValidation> ddmFormFieldValidations =
			ddmFormFieldStream.filter(
				this::fieldsWithValidations
			).map(
				formField -> formField.getName()
			).collect(
				Collectors.toMap(
					Function.identity(), this::getDDMFormFieldValidation)
			);

		Set<Map.Entry<String, DDMFormFieldValidation>> entrySet =
			ddmFormFieldValidations.entrySet();

		Stream<Map.Entry<String, DDMFormFieldValidation>> entryStream =
			entrySet.stream();

		entryStream.forEach(this::validateField);
	}

	protected void verifyFieldsMarkedAsRequired() {
		Set<Map.Entry<String, DDMFormField>> entrySet =
			_ddmFormFieldsMap.entrySet();

		Stream<Map.Entry<String, DDMFormField>> stream = entrySet.stream();

		stream.flatMap(
			entry -> {
				Set<DDMFormEvaluatorFieldContextKey> ddmFormFieldContextKeySet =
					_ddmFormEvaluatorFormValuesHelper.
						getDDMFormFieldContextKeySet(entry.getKey());

				return ddmFormFieldContextKeySet.stream();
			}
		).filter(
			this::filterVisibleFieldsMarkedAsRequired
		).filter(
			this::isFieldEmpty
		).forEach(
			this::setRequiredErrorMessage
		);
	}

	protected final DDMFormEvaluatorExpressionFieldAccessor
		ddmFormEvaluatorDDMExpressionFieldAccessor;
	protected DDMFormEvaluatorExpressionActionHandler
		ddmFormEvaluatorExpressionActionHandler;
	protected final DDMFormEvaluatorExpressionObserver
		ddmFormEvaluatorExpressionObserver;
	protected final DDMFormEvaluatorExpressionParameterAccessor
		ddmFormEvaluatorExpressionParameterAccessor;
	protected final DDMFormFieldValueAccessor<String>
		defaultDDMFormFieldValueAccessor =
			new DefaultDDMFormFieldValueAccessor();

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormEvaluatorHelper.class);

	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMForm _ddmForm;
	private final DDMFormEvaluatorEvaluateRequest
		_ddmFormEvaluatorEvaluateRequest;
	private final DDMFormEvaluatorFormValuesHelper
		_ddmFormEvaluatorFormValuesHelper;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
		_ddmFormFieldsPropertyChanges = new HashMap<>();
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormEvaluatorRuleHelper _ddmFormRuleHelper;
	private final Map<Integer, Integer> _pageFlow = new HashMap<>();
	private ResourceBundle _resourceBundle;

}