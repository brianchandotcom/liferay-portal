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
import com.liferay.dynamic.data.mapping.expression.DDMExpressionObserver;
import com.liferay.dynamic.data.mapping.expression.ExecuteActionRequest;
import com.liferay.dynamic.data.mapping.expression.ExecuteActionResponse;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.UpdateFieldPropertyResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Pablo Carvalho
 * @author Leonardo Barros
 */
@Component(
	immediate = true, scope = ServiceScope.PROTOTYPE,
	service = DDMFormEvaluator.class
)
public class DDMFormEvaluatorImpl
	implements DDMFormEvaluator, DDMExpressionObserver {

	@Override
	public DDMFormEvaluatorEvaluateResponse evaluate(
		DDMFormEvaluatorEvaluateRequest ddmFormEvaluatorEvaluateRequest) {

		_ddmFormEvaluatorEvaluateRequest = ddmFormEvaluatorEvaluateRequest;

		createResourceBundle(_ddmFormEvaluatorEvaluateRequest.getLocale());

		createDDMFormFieldValues(
			_ddmFormEvaluatorEvaluateRequest.getDDMFormValues());

		DDMForm ddmForm = ddmFormEvaluatorEvaluateRequest.getDDMForm();

		_ddmFormFieldsMap = ddmForm.getDDMFormFieldsMap(true);

		evaluateDDMForm(ddmForm);

		return null;
	}

	@Override
	public ExecuteActionResponse executeAction(
		ExecuteActionRequest executeActionRequest) {

		String action = executeActionRequest.getAction();

		if (action.equals("jumpPage")) {
			return jumpPage(executeActionRequest);
		}

		ExecuteActionResponse.Builder builder =
			ExecuteActionResponse.Builder.newBuilder(false);

		return builder.build();
	}

	@Override
	public long getCompanyId() {
		return _ddmFormEvaluatorEvaluateRequest.getCompanyId();
	}

	@Override
	public GetFieldPropertyResponse getFieldProperty(
		GetFieldPropertyRequest getFieldPropertyRequest) {

		GetFieldPropertyResponse.Builder builder =
			GetFieldPropertyResponse.Builder.newBuilder(
				getFieldProperty(
					getFieldPropertyRequest.getField(),
					getFieldPropertyRequest.getProperty()));

		return builder.build();
	}

	@Override
	public long getGroupId() {
		return _ddmFormEvaluatorEvaluateRequest.getGroupId();
	}

	@Override
	public Locale getLocale() {
		return _ddmFormEvaluatorEvaluateRequest.getLocale();
	}

	@Override
	public long getUserId() {
		return _ddmFormEvaluatorEvaluateRequest.getUserId();
	}

	@Override
	public boolean isField(String parameter) {
		return _ddmFormFieldsMap.containsKey(parameter);
	}

	@Override
	public UpdateFieldPropertyResponse updateFieldProperty(
		UpdateFieldPropertyRequest updateFieldPropertyRequest) {

		updateFieldProperty(
			updateFieldPropertyRequest.getField(),
			updateFieldPropertyRequest.getProperty(),
			updateFieldPropertyRequest.getValue());

		UpdateFieldPropertyResponse.Builder builder =
			UpdateFieldPropertyResponse.Builder.newBuilder(true);

		return builder.build();
	}

	protected void createDDMFormFieldValues(DDMFormValues ddmFormValues) {
		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			populateDDMFormFieldValues(ddmFormFieldValue);
		}
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

	protected void evaluateDDMForm(DDMForm ddmForm) {
		List<DDMFormRule> ddmFormRules = ddmForm.getDDMFormRules();

		Stream<DDMFormRule> stream = ddmFormRules.stream();

		stream.filter(
			DDMFormRule::isEnabled
		).forEach(
			this::evaluateDDMFormRule
		);

		verifyFieldsMarkedAsRequired();

		validateFields();
	}

	protected void evaluateDDMFormRule(DDMFormRule ddmFormRule) {
		if (evaluateDDMFormRuleCondition(ddmFormRule.getCondition())) {
			List<String> actions = ddmFormRule.getActions();

			Stream<String> stream = actions.stream();

			stream.forEach(this::evaluateDDMFormRuleAction);
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

		DDMExpression<T> ddmExpression =
			ddmExpressionFactory.createDDMExpression(expression);

		return (T)ddmExpression.evaluate();
	}

	protected boolean fieldIsVisible(Map.Entry<String, Object> entry) {
		String property = entry.getKey();

		if (property.equals("visible")) {
			return (Boolean)entry.getValue();
		}

		return true;
	}

	protected boolean fieldMarkedAsRequired(Map.Entry<String, Object> entry) {
		String property = entry.getKey();

		if (property.equals("required")) {
			return (Boolean)entry.getValue();
		}

		return false;
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
		Map.Entry<String, Map<String, Object>> entry) {

		Map<String, Object> changedProperties = entry.getValue();

		Set<Map.Entry<String, Object>> entrySet = changedProperties.entrySet();

		Stream<Map.Entry<String, Object>> stream = entrySet.stream();

		boolean anyMatch = stream.anyMatch(this::fieldMarkedAsRequired);

		if (!anyMatch) {
			return false;
		}

		stream = entrySet.stream();

		return stream.anyMatch(this::fieldIsVisible);
	}

	protected DDMFormFieldValidation getDDMFormFieldValidation(String field) {
		DDMFormField ddmFormField = _ddmFormFieldsMap.get(field);

		return ddmFormField.getDDMFormFieldValidation();
	}

	protected DDMFormFieldValue getDDMFormFieldValue(String field) {
		List<DDMFormFieldValue> ddmFormFieldValues = _ddmFormFieldValuesMap.get(
			field);

		if ((ddmFormFieldValues == null) || ddmFormFieldValues.isEmpty()) {
			return null;
		}

		return ddmFormFieldValues.get(0);
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

	protected String getFieldName(DDMFormField ddmFormField) {
		return ddmFormField.getName();
	}

	protected Object getFieldProperty(String field, String property) {
		Object value = getFieldPropertyChanged(field, property);

		if (value == null) {
			if (property.equals("value")) {
				value = getFieldValue(field);
			}
			else {
				DDMFormField ddmFormField = _ddmFormFieldsMap.get(field);

				if (ddmFormField != null) {
					value = ddmFormField.getProperty(property);
				}
			}
		}

		return value;
	}

	protected Object getFieldPropertyChanged(String field, String property) {
		Map<String, Object> ddmFormFieldProperties =
			_ddmFormFieldsPropertyChanges.get(field);

		if ((ddmFormFieldProperties != null) &&
			ddmFormFieldProperties.containsKey(property)) {

			return ddmFormFieldProperties.get(property);
		}

		return null;
	}

	protected Object getFieldValue(String field) {
		DDMFormFieldValue ddmFormFieldValue = getDDMFormFieldValue(field);

		if (ddmFormFieldValue == null) {
			return null;
		}

		Value ddmFormFieldValueValue = ddmFormFieldValue.getValue();

		Locale locale = ddmFormFieldValueValue.getDefaultLocale();

		if (ddmFormFieldValueValue.isLocalized()) {
			locale = _ddmFormEvaluatorEvaluateRequest.getLocale();
		}

		Object value = ddmFormFieldValueValue.getString(locale);

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(field);

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(
				ddmFormField.getType());

		if (ddmFormFieldValueAccessor == null) {
			value = ddmFormFieldValueAccessor.map(value);
		}

		return value;
	}

	protected boolean isFieldEmpty(String field) {
		DDMFormFieldValue ddmFormFieldValue = getDDMFormFieldValue(field);

		if (ddmFormFieldValue == null) {
			return true;
		}

		Object value = getFieldPropertyChanged(field, "value");

		if (value != null) {
			updateDDMFormFieldValue(ddmFormFieldValue, value);
		}

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(field);

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(
				ddmFormField.getType());

		return ddmFormFieldValueAccessor.isEmpty(
			ddmFormFieldValue, _ddmFormEvaluatorEvaluateRequest.getLocale());
	}

	protected ExecuteActionResponse jumpPage(
		ExecuteActionRequest executeActionRequest) {

		Optional<Integer> from = executeActionRequest.getParameter("from");
		Optional<Integer> to = executeActionRequest.getParameter("to");

		if (from.isPresent() && to.isPresent()) {
			_pageFlow.put(from.get(), to.get());
		}

		ExecuteActionResponse.Builder builder =
			ExecuteActionResponse.Builder.newBuilder(true);

		return builder.build();
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

	protected void setRequiredErrorMessage(String field) {
		updateFieldProperty(field, "valid", false);
		updateFieldProperty(
			field, "errorMessage",
			LanguageUtil.get(_resourceBundle, "this-field-is-required"));
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

	protected void updateFieldProperty(
		String field, String property, Object value) {

		_ddmFormFieldsPropertyChanges.putIfAbsent(field, new HashMap<>());

		Map<String, Object> ddmFormFieldProperties =
			_ddmFormFieldsPropertyChanges.get(field);

		ddmFormFieldProperties.put(property, value);
	}

	protected void validateField(
		Map.Entry<String, DDMFormFieldValidation> entry) {

		String field = entry.getKey();
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

		updateFieldProperty(field, "valid", valid);

		if (!valid) {
			String errorMessage = ddmFormFieldValidation.getErrorMessage();

			if (errorMessage == null) {
				errorMessage = LanguageUtil.get(
					_resourceBundle, "this-field-is-required");
			}

			updateFieldProperty(field, "errorMessage", errorMessage);
		}
	}

	protected void validateFields() {
		Collection<DDMFormField> ddmFormFields = _ddmFormFieldsMap.values();

		Stream<DDMFormField> ddmFormFieldStream = ddmFormFields.stream();

		Map<String, DDMFormFieldValidation> ddmFormFieldValidations =
			ddmFormFieldStream.filter(
				this::fieldsWithValidations
			).map(
				this::getFieldName
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
		Set<Map.Entry<String, Map<String, Object>>> entrySet =
			_ddmFormFieldsPropertyChanges.entrySet();

		Stream<Map.Entry<String, Map<String, Object>>> entryStream =
			entrySet.stream();

		entryStream.filter(
			this::filterVisibleFieldsMarkedAsRequired
		).map(
			Map.Entry::getKey
		).filter(
			this::isFieldEmpty
		).forEach(
			this::setRequiredErrorMessage
		);
	}

	@Reference
	protected DDMDataProviderInvoker ddmDataProviderInvoker;

	@Reference
	protected DDMExpressionFactory ddmExpressionFactory;

	@Reference
	protected DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected RoleLocalService roleLocalService;

	@Reference
	protected UserGroupRoleLocalService userGroupRoleLocalService;

	@Reference
	protected UserLocalService userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormEvaluatorImpl.class);

	private DDMFormEvaluatorEvaluateRequest _ddmFormEvaluatorEvaluateRequest;
	private Map<String, DDMFormField> _ddmFormFieldsMap;
	private final Map<String, Map<String, Object>>
		_ddmFormFieldsPropertyChanges = new HashMap<>();
	private final Map<String, List<DDMFormFieldValue>> _ddmFormFieldValuesMap =
		new LinkedHashMap<>();
	private final Map<Integer, Integer> _pageFlow = new HashMap<>();
	private ResourceBundle _resourceBundle;

}
