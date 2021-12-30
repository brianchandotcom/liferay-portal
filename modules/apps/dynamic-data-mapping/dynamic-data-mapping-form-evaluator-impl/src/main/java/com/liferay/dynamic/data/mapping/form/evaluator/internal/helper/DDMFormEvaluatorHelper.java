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
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyRequest;
import com.liferay.dynamic.data.mapping.expression.GetFieldPropertyResponse;
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
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueEditingAware;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueLocalizer;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.page.change.DDMFormPageChange;
import com.liferay.dynamic.data.mapping.form.page.change.DDMFormPageChangeTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.util.NumericDDMFormFieldUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Rafael Praxedes
 */
public class DDMFormEvaluatorHelper {

	public DDMFormEvaluatorHelper(
		DDMExpressionFactory ddmExpressionFactory,
		DDMFormEvaluatorEvaluateRequest ddmFormEvaluatorEvaluateRequest,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormPageChangeTracker ddmFormPageChangeTracker) {

		_ddmExpressionFactory = ddmExpressionFactory;
		_ddmFormEvaluatorEvaluateRequest = ddmFormEvaluatorEvaluateRequest;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_ddmFormPageChangeTracker = ddmFormPageChangeTracker;

		_ddmForm = ddmFormEvaluatorEvaluateRequest.getDDMForm();

		_ddmFormEvaluatorFormValuesHelper =
			new DDMFormEvaluatorFormValuesHelper(
				_ddmFormEvaluatorEvaluateRequest.getDDMFormValues());

		ddmFormEvaluatorExpressionObserver =
			new DDMFormEvaluatorExpressionObserver(
				_ddmFormEvaluatorFormValuesHelper,
				_ddmFormFieldsPropertyChanges);

		_ddmFormFieldsMap = _ddmForm.getDDMFormFieldsMap(true);

		_ddmFormLayout = ddmFormEvaluatorEvaluateRequest.getDDMFormLayout();

		_ddmFormEvaluatorRuleHelper = new DDMFormEvaluatorRuleHelper(
			_ddmFormFieldsMap, ddmFormEvaluatorExpressionObserver);

		ddmFormEvaluatorDDMExpressionFieldAccessor =
			new DDMFormEvaluatorExpressionFieldAccessor(
				_ddmFormEvaluatorFormValuesHelper, _ddmFormFieldsMap,
				_ddmFormFieldsPropertyChanges, _ddmFormFieldTypeServicesTracker,
				ddmFormEvaluatorEvaluateRequest.getLocale());

		ddmFormEvaluatorExpressionActionHandler =
			new DDMFormEvaluatorExpressionActionHandler(_pageFlow);

		ddmFormEvaluatorExpressionParameterAccessor =
			new DDMFormEvaluatorExpressionParameterAccessor(
				_ddmFormEvaluatorEvaluateRequest);
	}

	public DDMFormEvaluatorEvaluateResponse evaluate() {
		_evaluateDDMFormPageChange();

		_evaluateVisibilityExpressions();

		List<DDMFormRule> ddmFormRules = null;

		if ((_ddmFormLayout != null) &&
			ListUtil.isNotEmpty(_ddmFormLayout.getDDMFormRules())) {

			ddmFormRules = _ddmFormLayout.getDDMFormRules();
		}
		else {
			ddmFormRules = _ddmForm.getDDMFormRules();
		}

		_evaluateDDMFormRules(ddmFormRules, false);

		_evaluateDDMFormRules(ddmFormRules, true);

		_validateFields();

		_localizeNumericDDMFormFieldValues();

		return _buildDDMFormEvaluatorEvaluateResponse();
	}

	protected Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
		getDDMFormFieldsPropertyChanges() {

		return _ddmFormFieldsPropertyChanges;
	}

	protected DDMFormFieldValue getDDMFormFieldValue(
		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey) {

		DDMFormFieldValue ddmFormFieldValue =
			_ddmFormEvaluatorFormValuesHelper.getDDMFormFieldValue(
				ddmFormEvaluatorFieldContextKey);

		if (ddmFormFieldValue == null) {
			return null;
		}

		Object value =
			ddmFormEvaluatorDDMExpressionFieldAccessor.getFieldPropertyChanged(
				ddmFormEvaluatorFieldContextKey, "value");

		if (value != null) {
			_updateDDMFormFieldValue(ddmFormFieldValue, value);
		}

		return ddmFormFieldValue;
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

	private DDMFormEvaluatorEvaluateResponse
		_buildDDMFormEvaluatorEvaluateResponse() {

		DDMFormEvaluatorEvaluateResponse.Builder formEvaluatorEvaluateResponse =
			DDMFormEvaluatorEvaluateResponse.Builder.newBuilder(
				getDDMFormFieldsPropertyChanges());

		formEvaluatorEvaluateResponse.withDisabledPagesIndexes(
			getDisabledPagesIndexes());

		return formEvaluatorEvaluateResponse.build();
	}

	private <T> DDMExpression<T> _createExpression(String expression)
		throws DDMExpressionException {

		return _ddmExpressionFactory.createExpression(
			CreateExpressionRequest.Builder.newBuilder(
				expression
			).withDDMExpressionActionHandler(
				ddmFormEvaluatorExpressionActionHandler
			).withDDMExpressionFieldAccessor(
				ddmFormEvaluatorDDMExpressionFieldAccessor
			).withDDMExpressionObserver(
				ddmFormEvaluatorExpressionObserver
			).withDDMExpressionParameterAccessor(
				ddmFormEvaluatorExpressionParameterAccessor
			).build());
	}

	private void _evaluateDDMFormPageChange() {
		if ((_ddmFormLayout == null) ||
			(_ddmFormLayout.getNextPage() ==
				_ddmFormLayout.getPreviousPage())) {

			return;
		}

		DDMFormPageChange ddmFormPageChange =
			_ddmFormPageChangeTracker.getDDMFormPageChangeByDDMFormInstanceId(
				String.valueOf(
					_ddmFormEvaluatorEvaluateRequest.getDDMFormInstanceId()));

		if (ddmFormPageChange == null) {
			return;
		}

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			ddmFormPageChange.evaluate(_ddmFormEvaluatorEvaluateRequest);

		_ddmFormFieldsPropertyChanges.putAll(
			ddmFormEvaluatorEvaluateResponse.getDDMFormFieldsPropertyChanges());
	}

	private void _evaluateDDMFormRule(
		DDMFormRule ddmFormRule, boolean ddmFormRuleConditionEvaluationResult) {

		if (ddmFormRuleConditionEvaluationResult) {
			List<String> actions = ddmFormRule.getActions();

			Stream<String> stream = actions.stream();

			_evaluateDDMFormRuleAction(
				stream.collect(Collectors.joining(" AND ")));

			_evaluatedActions = ListUtil.copy(actions);
		}
		else {
			DDMFormRule copyDDMFormRule = new DDMFormRule(ddmFormRule);

			if (_evaluatedActions != null) {
				List<String> actions = copyDDMFormRule.getActions();

				Stream<String> stream = actions.stream();

				List<String> unevaluatedActions = stream.filter(
					action -> !_evaluatedActions.contains(action)
				).collect(
					Collectors.toList()
				);

				copyDDMFormRule.setActions(unevaluatedActions);
			}

			_ddmFormEvaluatorRuleHelper.checkFieldAffectedByAction(
				copyDDMFormRule);
		}
	}

	private void _evaluateDDMFormRuleAction(String action) {
		try {
			_evaluateExpression(action);
		}
		catch (DDMExpressionException ddmExpressionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmExpressionException, ddmExpressionException);
			}
		}
	}

	private Boolean _evaluateDDMFormRuleCondition(String condition) {
		try {
			return _evaluateExpression(condition);
		}
		catch (DDMExpressionException ddmExpressionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmExpressionException, ddmExpressionException);
			}

			return false;
		}
	}

	private void _evaluateDDMFormRules(
		List<DDMFormRule> ddmFormRules,
		Boolean ddmFormRuleConditionEvaluationResult) {

		Stream<DDMFormRule> stream = ddmFormRules.stream();

		stream.filter(
			DDMFormRule::isEnabled
		).filter(
			ddmFormRule ->
				Validator.isNotNull(ddmFormRule.getCondition()) &&
				Objects.equals(
					ddmFormRuleConditionEvaluationResult,
					_evaluateDDMFormRuleCondition(ddmFormRule.getCondition()))
		).forEach(
			ddmFormRule -> {
				_evaluateDDMFormRule(
					ddmFormRule, ddmFormRuleConditionEvaluationResult);

				_resetInvisibleFieldValue();
			}
		);
	}

	private <T> T _evaluateExpression(String expression)
		throws DDMExpressionException {

		DDMExpression<T> ddmExpression = _createExpression(expression);

		return (T)ddmExpression.evaluate();
	}

	private void _evaluateVisibilityExpression(
		Map.Entry<String, String> entry) {

		try {
			UpdateFieldPropertyRequest.Builder builder =
				UpdateFieldPropertyRequest.Builder.newBuilder(
					entry.getKey(), "visible",
					_evaluateExpression(entry.getValue()));

			ddmFormEvaluatorExpressionObserver.updateFieldProperty(
				builder.build());
		}
		catch (DDMExpressionException ddmExpressionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmExpressionException, ddmExpressionException);
			}
		}
	}

	private void _evaluateVisibilityExpressions() {
		Collection<DDMFormField> ddmFormFields = _ddmFormFieldsMap.values();

		Stream<DDMFormField> ddmFormFieldsStream = ddmFormFields.stream();

		Map<String, String> nameVisibilityExpressionMap =
			ddmFormFieldsStream.filter(
				field -> Validator.isNotNull(field.getVisibilityExpression())
			).collect(
				Collectors.toMap(
					field -> field.getName(),
					field -> field.getVisibilityExpression())
			);

		_forEachEntry(
			nameVisibilityExpressionMap, this::_evaluateVisibilityExpression);
	}

	private boolean _filterFieldsWithDDMFormFieldValidation(
		DDMFormField ddmFormField) {

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		if (ddmFormFieldValidation == null) {
			return false;
		}

		return Validator.isNotNull(
			ddmFormFieldValidation.getDDMFormFieldValidationExpression());
	}

	private boolean _filterVisibleFieldsMarkedAsRequired(
		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey) {

		if (!_getBooleanPropertyValue(
				ddmFormFieldContextKey, "required", false)) {

			return false;
		}

		return _getBooleanPropertyValue(
			ddmFormFieldContextKey, "visible", true);
	}

	private boolean _filterVisibleFieldsWithInputMask(
		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey) {

		if (!_getBooleanPropertyValue(
				ddmFormEvaluatorFieldContextKey, "inputMask", false)) {

			return false;
		}

		return _getBooleanPropertyValue(
			ddmFormEvaluatorFieldContextKey, "visible", true);
	}

	private <K, V> void _forEachEntry(
		Map<K, V> map, Consumer<Map.Entry<K, V>> entryConsumer) {

		Set<Map.Entry<K, V>> set = map.entrySet();

		Stream<Map.Entry<K, V>> stream = set.stream();

		stream.forEach(entryConsumer);
	}

	private boolean _getBooleanPropertyValue(
		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey,
		String booleanPropertyName, boolean defaultValue) {

		Map<String, Object> changedProperties =
			_ddmFormFieldsPropertyChanges.getOrDefault(
				ddmFormFieldContextKey, Collections.emptyMap());

		if (changedProperties.containsKey(booleanPropertyName)) {
			return MapUtil.getBoolean(changedProperties, booleanPropertyName);
		}

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormFieldContextKey.getName());

		return GetterUtil.getBoolean(
			ddmFormField.getProperty(booleanPropertyName), defaultValue);
	}

	private Stream<DDMFormEvaluatorFieldContextKey>
		_getDDMFormEvaluatorFieldContextKeysStream(String name) {

		Set<DDMFormEvaluatorFieldContextKey> ddmFormFieldContextKeys =
			_ddmFormEvaluatorFormValuesHelper.getDDMFormFieldContextKeys(name);

		return ddmFormFieldContextKeys.stream();
	}

	private DDMFormFieldValidation _getDDMFormFieldValidation(
		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey) {

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormEvaluatorFieldContextKey.getName());

		return ddmFormField.getDDMFormFieldValidation();
	}

	private Object _getFieldPropertyResponseValue(
		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey,
		String propertyName) {

		String fieldName = ddmFormEvaluatorFieldContextKey.getName();
		String instanceId = ddmFormEvaluatorFieldContextKey.getInstanceId();

		GetFieldPropertyRequest.Builder builder =
			GetFieldPropertyRequest.Builder.newBuilder(fieldName, propertyName);

		builder.withInstanceId(instanceId);

		GetFieldPropertyResponse getFieldPropertyResponse =
			ddmFormEvaluatorDDMExpressionFieldAccessor.getFieldProperty(
				builder.build());

		return getFieldPropertyResponse.getValue();
	}

	private boolean _isConfirmationValueInvalid(
		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey) {

		DDMFormFieldValue ddmFormFieldValue = getDDMFormFieldValue(
			ddmFormEvaluatorFieldContextKey);

		if (ddmFormFieldValue == null) {
			return false;
		}

		String confirmationValue = Objects.toString(
			ddmFormFieldValue.getConfirmationValue(), StringPool.BLANK);

		String valueString = StringPool.BLANK;

		Value value = ddmFormFieldValue.getValue();

		if (value != null) {
			valueString = value.getString(
				_ddmFormEvaluatorEvaluateRequest.getLocale());
		}

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormEvaluatorFieldContextKey.getName());

		if (Objects.equals(ddmFormField.getDataType(), "double") &&
			Objects.equals(ddmFormField.getType(), "numeric")) {

			confirmationValue = NumericDDMFormFieldUtil.getFormattedValue(
				_ddmFormEvaluatorEvaluateRequest.getLocale(),
				confirmationValue);
			valueString = NumericDDMFormFieldUtil.getFormattedValue(
				_ddmFormEvaluatorEvaluateRequest.getLocale(), valueString);
		}

		if (Objects.equals(confirmationValue, valueString)) {
			return false;
		}

		return true;
	}

	private boolean _isFieldEmpty(
		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey) {

		DDMFormFieldValue ddmFormFieldValue = getDDMFormFieldValue(
			ddmFormEvaluatorFieldContextKey);

		if (ddmFormFieldValue == null) {
			return true;
		}

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormEvaluatorFieldContextKey.getName());

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			getDDMFormFieldValueAccessor(ddmFormField.getType());

		if (ddmFormFieldValueAccessor.isEmpty(
				ddmFormFieldValue,
				_ddmFormEvaluatorEvaluateRequest.getLocale())) {

			return true;
		}

		return false;
	}

	private boolean _isFieldNative(
		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey) {

		return _getBooleanPropertyValue(
			ddmFormFieldContextKey, "nativeField", true);
	}

	private boolean _isFieldReadOnly(
		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey) {

		return _getBooleanPropertyValue(
			ddmFormEvaluatorFieldContextKey, "readOnly", false);
	}

	private boolean _isFieldVisible(
		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey) {

		Map<String, Object> ddmFormFieldPropertyChanges =
			_ddmFormFieldsPropertyChanges.get(ddmFormFieldContextKey);

		if (ddmFormFieldPropertyChanges == null) {
			return true;
		}

		return GetterUtil.get(ddmFormFieldPropertyChanges.get("visible"), true);
	}

	private boolean _isFieldWithConfirmationFieldAndVisible(
		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey) {

		if (!_getBooleanPropertyValue(
				ddmFormEvaluatorFieldContextKey, "requireConfirmation",
				false)) {

			return false;
		}

		return _getBooleanPropertyValue(
			ddmFormEvaluatorFieldContextKey, "visible", true);
	}

	private boolean _isHideField(
		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey) {

		return _getBooleanPropertyValue(
			ddmFormEvaluatorFieldContextKey, "hideField", false);
	}

	private boolean _isIntegerNumericField(DDMFormField ddmFormField) {
		if (Objects.equals(ddmFormField.getDataType(), "integer") &&
			Objects.equals(ddmFormField.getType(), "numeric")) {

			return true;
		}

		return false;
	}

	private boolean _isNumericField(DDMFormField ddmFormField) {
		return Objects.equals(ddmFormField.getType(), "numeric");
	}

	private boolean _isObjectRelationshipFieldInvalid(
		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey) {

		if (!_getBooleanPropertyValue(
				ddmFormEvaluatorFieldContextKey, "visible", true)) {

			return false;
		}

		DDMFormField ddmFormField = _ddmFormFieldsMap.get(
			ddmFormEvaluatorFieldContextKey.getName());

		Object value = ddmFormField.getProperty("value");

		if (value instanceof String) {
			return false;
		}

		return true;
	}

	private boolean _isValueWithInputMaskInvalid(
		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey) {

		DDMFormFieldValue ddmFormFieldValue = getDDMFormFieldValue(
			ddmFormEvaluatorFieldContextKey);

		if (ddmFormFieldValue == null) {
			return false;
		}

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return false;
		}

		String valueString = value.getString(
			_ddmFormEvaluatorEvaluateRequest.getLocale());

		if (Validator.isNull(valueString)) {
			return false;
		}

		LocalizedValue localizedValue =
			(LocalizedValue)_getFieldPropertyResponseValue(
				ddmFormEvaluatorFieldContextKey, "inputMaskFormat");

		if (localizedValue == null) {
			return false;
		}

		String inputMaskFormat = localizedValue.getString(
			_ddmFormEvaluatorEvaluateRequest.getLocale());

		if (valueString.length() < StringUtil.count(inputMaskFormat, "9")) {
			return true;
		}

		return false;
	}

	private void _localizeDDMFormFieldValue(
		DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey) {

		DDMFormFieldValue ddmFormFieldValue =
			_ddmFormEvaluatorFormValuesHelper.getDDMFormFieldValue(
				ddmFormFieldContextKey);

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return;
		}

		_forEachEntry(
			value.getValues(),
			entry -> {
				if (Validator.isNotNull(entry.getValue())) {
					DDMFormFieldValueLocalizer ddmFormFieldValueLocalizer =
						_ddmFormFieldTypeServicesTracker.
							getDDMFormFieldValueLocalizer(
								ddmFormFieldValue.getType());

					if (ddmFormFieldValueLocalizer != null) {
						if (ddmFormFieldValueLocalizer instanceof
								DDMFormFieldValueEditingAware) {

							DDMFormFieldValueEditingAware
								ddmFormFieldValueEditingAware =
									(DDMFormFieldValueEditingAware)
										ddmFormFieldValueLocalizer;

							ddmFormFieldValueEditingAware.setEditingFieldValue(
								_ddmFormEvaluatorEvaluateRequest.
									isEditingFieldValue());
						}

						value.addString(
							entry.getKey(),
							ddmFormFieldValueLocalizer.localize(
								entry.getValue(), entry.getKey()));
					}
				}
			});
	}

	private void _localizeNumericDDMFormFieldValues() {
		Collection<DDMFormField> ddmFormFields = _ddmFormFieldsMap.values();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		stream.filter(
			this::_isNumericField
		).flatMap(
			ddmFormField -> _getDDMFormEvaluatorFieldContextKeysStream(
				ddmFormField.getName())
		).forEach(
			this::_localizeDDMFormFieldValue
		);
	}

	private void _resetInvisibleFieldValue() {
		_ddmFormFieldsPropertyChanges.forEach(
			(ddmFormFieldContextKey, ddmFormFieldProperties) -> {
				if (_ddmFormEvaluatorEvaluateRequest.isViewMode() &&
					_ddmFormEvaluatorEvaluateRequest.isEditingFieldValue() &&
					!_isFieldNative(ddmFormFieldContextKey) &&
					!_isFieldVisible(ddmFormFieldContextKey)) {

					ddmFormFieldProperties.put("value", StringPool.BLANK);
				}
			});
	}

	private void _setFieldAsInvalid(
		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey,
		String errorMessage) {

		UpdateFieldPropertyRequest.Builder builder =
			UpdateFieldPropertyRequest.Builder.newBuilder(
				ddmFormEvaluatorFieldContextKey.getName(), "errorMessage",
				errorMessage);

		builder.withInstanceId(
			ddmFormEvaluatorFieldContextKey.getInstanceId()
		).withParameter(
			"valid", false
		);

		ddmFormEvaluatorExpressionObserver.updateFieldProperty(builder.build());
	}

	private void _updateDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue, Object newValue) {

		Value value = ddmFormFieldValue.getValue();

		Locale locale = value.getDefaultLocale();

		if (value.isLocalized()) {
			DDMForm ddmForm = _ddmFormEvaluatorEvaluateRequest.getDDMForm();

			Set<Locale> availableLocales = ddmForm.getAvailableLocales();

			if (availableLocales.contains(
					_ddmFormEvaluatorEvaluateRequest.getLocale())) {

				locale = _ddmFormEvaluatorEvaluateRequest.getLocale();
			}
		}

		value.addString(locale, String.valueOf(newValue));
	}

	private void _validateFields() {
		_validateFieldsMarkedAsRequired();
		_validateFieldsWithConfirmationField();
		_validateFieldsWithDDMFormFieldValidation();
		_validateNumericFieldsWithInputMask();
		_validateObjectRelationshipFields();
	}

	private void _validateFieldsMarkedAsRequired() {
		Set<Map.Entry<String, DDMFormField>> entrySet =
			_ddmFormFieldsMap.entrySet();

		Stream<Map.Entry<String, DDMFormField>> stream = entrySet.stream();

		stream.flatMap(
			entry -> _getDDMFormEvaluatorFieldContextKeysStream(entry.getKey())
		).filter(
			this::_filterVisibleFieldsMarkedAsRequired
		).filter(
			this::_isFieldEmpty
		).forEach(
			ddmFormEvaluatorFieldContextKey -> {
				String requiredErrorMessage = LanguageUtil.get(
					_ddmFormEvaluatorEvaluateRequest.getLocale(),
					"this-field-is-required");

				DDMFormField ddmFormField = _ddmFormFieldsMap.get(
					ddmFormEvaluatorFieldContextKey.getName());

				LocalizedValue localizedValue =
					ddmFormField.getRequiredErrorMessage();

				if (localizedValue != null) {
					Map<Locale, String> values = localizedValue.getValues();

					String value = values.get(
						_ddmFormEvaluatorEvaluateRequest.getLocale());

					if (Validator.isNotNull(value)) {
						requiredErrorMessage = value;
					}
				}

				_setFieldAsInvalid(
					ddmFormEvaluatorFieldContextKey, requiredErrorMessage);
			}
		);
	}

	private void _validateFieldsWithConfirmationField() {
		Set<Map.Entry<String, DDMFormField>> entrySet =
			_ddmFormFieldsMap.entrySet();

		Stream<Map.Entry<String, DDMFormField>> stream = entrySet.stream();

		stream.flatMap(
			entry -> _getDDMFormEvaluatorFieldContextKeysStream(entry.getKey())
		).filter(
			this::_isFieldWithConfirmationFieldAndVisible
		).filter(
			this::_isConfirmationValueInvalid
		).forEach(
			ddmFormEvaluatorFieldContextKey -> _setFieldAsInvalid(
				ddmFormEvaluatorFieldContextKey, StringPool.BLANK)
		);
	}

	private void _validateFieldsWithDDMFormFieldValidation() {
		Collection<DDMFormField> ddmFormFields = _ddmFormFieldsMap.values();

		Stream<DDMFormField> ddmFormFieldsStream = ddmFormFields.stream();

		Map<DDMFormEvaluatorFieldContextKey, DDMFormFieldValidation>
			ddmFormFieldValidations = ddmFormFieldsStream.filter(
				this::_filterFieldsWithDDMFormFieldValidation
			).flatMap(
				ddmFormField -> _getDDMFormEvaluatorFieldContextKeysStream(
					ddmFormField.getName())
			).collect(
				Collectors.toMap(
					Function.identity(), this::_getDDMFormFieldValidation)
			);

		_forEachEntry(
			ddmFormFieldValidations,
			this::_validateFieldWithDDMFormFieldValidation);
	}

	private void _validateFieldWithDDMFormFieldValidation(
		Map.Entry<DDMFormEvaluatorFieldContextKey, DDMFormFieldValidation>
			entry) {

		DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey =
			entry.getKey();

		if ((_isConfirmationValueInvalid(ddmFormEvaluatorFieldContextKey) &&
			 _isFieldWithConfirmationFieldAndVisible(
				 ddmFormEvaluatorFieldContextKey)) ||
			_isFieldEmpty(ddmFormEvaluatorFieldContextKey)) {

			return;
		}

		if (_isFieldReadOnly(ddmFormEvaluatorFieldContextKey) ||
			!_isFieldVisible(ddmFormEvaluatorFieldContextKey) ||
			_isHideField(ddmFormEvaluatorFieldContextKey)) {

			return;
		}

		DDMFormFieldValidation ddmFormFieldValidation = entry.getValue();

		if (ddmFormFieldValidation == null) {
			return;
		}

		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			ddmFormFieldValidation.getDDMFormFieldValidationExpression();

		if (Validator.isNull(ddmFormFieldValidationExpression.getValue())) {
			return;
		}

		String fieldName = ddmFormEvaluatorFieldContextKey.getName();
		String fieldInstanceId =
			ddmFormEvaluatorFieldContextKey.getInstanceId();

		Boolean valid = Boolean.FALSE;

		try {
			String localizedValueString = null;

			LocalizedValue parameterLocalizedValue =
				ddmFormFieldValidation.getParameterLocalizedValue();

			if (parameterLocalizedValue != null) {
				localizedValueString = parameterLocalizedValue.getString(
					_ddmFormEvaluatorEvaluateRequest.getLocale());

				if (Validator.isNull(localizedValueString)) {
					localizedValueString = parameterLocalizedValue.getString(
						parameterLocalizedValue.getDefaultLocale());
				}
			}

			DDMExpression<Boolean> ddmExpression = null;

			if (Validator.isNull(localizedValueString)) {
				ddmExpression = _createExpression(
					ddmFormFieldValidationExpression.getValue());
			}
			else {
				ddmExpression = _createExpression(
					ddmFormFieldValidationExpression.getExpression(
						_ddmFormEvaluatorEvaluateRequest.getDDMFormValues(),
						localizedValueString,
						ddmFormEvaluatorExpressionParameterAccessor.
							getTimeZoneId()));
			}

			ddmExpression.setVariable(
				fieldName,
				_getFieldPropertyResponseValue(
					ddmFormEvaluatorFieldContextKey, "value"));

			valid = ddmExpression.evaluate();
		}
		catch (DDMExpressionException ddmExpressionException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ddmExpressionException, ddmExpressionException);
			}
		}

		UpdateFieldPropertyRequest.Builder builder =
			UpdateFieldPropertyRequest.Builder.newBuilder(
				fieldName, "valid", valid);

		builder.withInstanceId(fieldInstanceId);

		if (!Objects.equals(Boolean.TRUE, valid)) {
			String errorMessage = null;

			LocalizedValue errorMessageLocalizedValue =
				ddmFormFieldValidation.getErrorMessageLocalizedValue();

			if (errorMessageLocalizedValue != null) {
				errorMessage = errorMessageLocalizedValue.getString(
					_ddmFormEvaluatorEvaluateRequest.getLocale());
			}

			if (Validator.isNull(errorMessage)) {
				errorMessage = LanguageUtil.get(
					_ddmFormEvaluatorEvaluateRequest.getLocale(),
					"this-field-is-invalid");
			}

			builder.withParameter("errorMessage", errorMessage);
		}

		ddmFormEvaluatorExpressionObserver.updateFieldProperty(builder.build());
	}

	private void _validateNumericFieldsWithInputMask() {
		Collection<DDMFormField> ddmFormFields = _ddmFormFieldsMap.values();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		stream.filter(
			this::_isIntegerNumericField
		).flatMap(
			ddmFormField -> _getDDMFormEvaluatorFieldContextKeysStream(
				ddmFormField.getName())
		).filter(
			this::_filterVisibleFieldsWithInputMask
		).filter(
			this::_isValueWithInputMaskInvalid
		).forEach(
			ddmFormEvaluatorFieldContextKey -> _setFieldAsInvalid(
				ddmFormEvaluatorFieldContextKey,
				LanguageUtil.get(
					_ddmFormEvaluatorEvaluateRequest.getLocale(),
					"input-format-is-not-satisfied"))
		);
	}

	private void _validateObjectRelationshipFields() {
		Collection<DDMFormField> ddmFormFields = _ddmFormFieldsMap.values();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		stream.filter(
			ddmFormField -> Objects.equals(
				ddmFormField.getType(), "object-relationship")
		).flatMap(
			ddmFormField -> _getDDMFormEvaluatorFieldContextKeysStream(
				ddmFormField.getName())
		).filter(
			this::_isObjectRelationshipFieldInvalid
		).forEach(
			ddmFormEvaluatorFieldContextKey -> _setFieldAsInvalid(
				ddmFormEvaluatorFieldContextKey,
				LanguageUtil.get(
					_ddmFormEvaluatorEvaluateRequest.getLocale(),
					"the-field-value-is-invalid"))
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormEvaluatorHelper.class);

	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DDMForm _ddmForm;
	private final DDMFormEvaluatorEvaluateRequest
		_ddmFormEvaluatorEvaluateRequest;
	private final DDMFormEvaluatorFormValuesHelper
		_ddmFormEvaluatorFormValuesHelper;
	private final DDMFormEvaluatorRuleHelper _ddmFormEvaluatorRuleHelper;
	private final Map<String, DDMFormField> _ddmFormFieldsMap;
	private final Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
		_ddmFormFieldsPropertyChanges = new HashMap<>();
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormLayout _ddmFormLayout;
	private final DDMFormPageChangeTracker _ddmFormPageChangeTracker;
	private List<String> _evaluatedActions;
	private final Map<Integer, Integer> _pageFlow = new HashMap<>();

}