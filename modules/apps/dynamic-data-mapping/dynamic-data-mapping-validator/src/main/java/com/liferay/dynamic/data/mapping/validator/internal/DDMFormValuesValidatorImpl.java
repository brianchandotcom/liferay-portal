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

package com.liferay.dynamic.data.mapping.validator.internal;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidator;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.dynamic.data.mapping.util.NumberUtil;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustNotSetValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidAvailableLocales;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidDefaultLocale;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidField;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidValuesSize;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.RequiredValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.dynamic.data.mapping.validator.internal.expression.DDMFormFieldValueExpressionParameterAccessor;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMFormValuesValidator.class)
public class DDMFormValuesValidatorImpl implements DDMFormValuesValidator {

	@Override
	public void validate(DDMFormValues ddmFormValues)
		throws DDMFormValuesValidationException {

		validate(ddmFormValues, null);
	}

	@Override
	public void validate(DDMFormValues ddmFormValues, String timeZoneId)
		throws DDMFormValuesValidationException {

		_ddmFormFieldValueExpressionParameterAccessor =
			new DDMFormFieldValueExpressionParameterAccessor(
				_jsonFactory, ddmFormValues.getDefaultLocale(), timeZoneId);

		DDMForm ddmForm = ddmFormValues.getDDMForm();

		if (ddmForm == null) {
			throw new NullPointerException("A DDM Form instance was never set");
		}

		_traverseDDMFormFields(
			ddmForm.getDDMFormFields(),
			ddmFormValues.getDDMFormFieldValuesMap());

		_traverseDDMFormFieldValues(
			ddmFormValues.getDDMFormFieldValues(),
			ddmForm.getDDMFormFieldsMap(false));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DDMFormFieldValueValidator.class,
			"ddm.form.field.type.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected Boolean evaluateValidationExpression(
			String dataType, String ddmFormFieldName,
			DDMFormFieldValidation ddmFormFieldValidation,
			DDMFormFieldValue ddmFormFieldValue)
		throws DDMFormValuesValidationException {

		if (ddmFormFieldValue.getValue() == null) {
			return true;
		}

		Value value = ddmFormFieldValue.getValue();

		Locale locale = value.getDefaultLocale();

		if (Validator.isNull(value.getString(locale))) {
			return true;
		}

		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			ddmFormFieldValidation.getDDMFormFieldValidationExpression();

		if ((ddmFormFieldValidationExpression == null) ||
			Validator.isNull(ddmFormFieldValidationExpression.getValue())) {

			return true;
		}

		DDMExpression<Boolean> ddmExpression = null;

		try {
			if (ddmFormFieldValidation.getParameterLocalizedValue() != null) {
				LocalizedValue parameterLocalizedValue =
					ddmFormFieldValidation.getParameterLocalizedValue();

				ddmExpression = _ddmExpressionFactory.createExpression(
					CreateExpressionRequest.Builder.newBuilder(
						ddmFormFieldValidationExpression.getExpression(
							ddmFormFieldValue.getDDMFormValues(),
							parameterLocalizedValue.getString(locale),
							_ddmFormFieldValueExpressionParameterAccessor.
								getTimeZoneId())
					).withDDMExpressionParameterAccessor(
						_ddmFormFieldValueExpressionParameterAccessor
					).build());
			}
			else {
				ddmExpression = _ddmExpressionFactory.createExpression(
					CreateExpressionRequest.Builder.newBuilder(
						ddmFormFieldValidationExpression.getValue()
					).build());
			}

			if (dataType.equals(FieldConstants.BOOLEAN)) {
				ddmExpression.setVariable(
					ddmFormFieldName,
					GetterUtil.getBoolean(value.getString(locale)));
			}
			else if (dataType.equals(FieldConstants.DOUBLE)) {
				String valueString = value.getString(locale);

				if (NumberUtil.hasDecimalSeparator(valueString)) {
					ddmExpression.setVariable(
						ddmFormFieldName,
						GetterUtil.getDouble(valueString, locale));
				}
				else {
					ddmExpression.setVariable(
						ddmFormFieldName, GetterUtil.getInteger(valueString));
				}
			}
			else if (dataType.equals(FieldConstants.INTEGER)) {
				ddmExpression.setVariable(
					ddmFormFieldName,
					GetterUtil.getInteger(value.getString(locale)));
			}
			else {
				ddmExpression.setVariable(
					ddmFormFieldName, value.getString(locale));
			}

			return ddmExpression.evaluate();
		}
		catch (DDMExpressionException ddmExpressionException) {
			throw new DDMFormValuesValidationException(ddmExpressionException);
		}
	}

	protected boolean isNull(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		if ((value == null) || SetUtil.isEmpty(value.getAvailableLocales())) {
			return true;
		}

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			_getDDMFormFieldValueAccessor(ddmFormField.getType());

		Set<Locale> locales = value.getAvailableLocales();

		Stream<Locale> stream = locales.stream();

		return stream.allMatch(
			locale -> ddmFormFieldValueAccessor.isEmpty(
				ddmFormFieldValue, locale));
	}

	protected void validateDDMFormFieldValidationExpression(
			DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue)
		throws DDMFormValuesValidationException {

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		if (ddmFormFieldValidation == null) {
			return;
		}

		Boolean valid = evaluateValidationExpression(
			ddmFormField.getDataType(), ddmFormField.getName(),
			ddmFormFieldValidation, ddmFormFieldValue);

		if (!Objects.equals(Boolean.TRUE, valid)) {
			throw new MustSetValidValue(
				ddmFormField.getLabel(), ddmFormField.getName());
		}
	}

	private DDMFormFieldValueAccessor<?> _getDDMFormFieldValueAccessor(
		String type) {

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(type);

		if (ddmFormFieldValueAccessor != null) {
			return ddmFormFieldValueAccessor;
		}

		return _defaultDDMFormFieldValueAccessor;
	}

	private List<DDMFormFieldValue> _getDDMFormFieldValuesByFieldName(
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap,
		String fieldName) {

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			fieldName);

		if (ddmFormFieldValues == null) {
			return Collections.emptyList();
		}

		return ddmFormFieldValues;
	}

	private void _invokeDDMFormFieldValueValidator(
			DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue)
		throws DDMFormValuesValidationException {

		DDMFormFieldValueValidator ddmFormFieldValueValidator =
			_serviceTrackerMap.getService(ddmFormField.getType());

		if (ddmFormFieldValueValidator == null) {
			return;
		}

		try {
			ddmFormFieldValueValidator.validate(
				ddmFormField, ddmFormFieldValue.getValue());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Value is invalid for field " + ddmFormField.getName(),
					exception);
			}

			throw new MustSetValidValue(
				ddmFormField.getLabel(), ddmFormField.getName());
		}
	}

	private void _traverseDDMFormFields(
			List<DDMFormField> ddmFormFields,
			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap)
		throws DDMFormValuesValidationException {

		for (DDMFormField ddmFormField : ddmFormFields) {
			List<DDMFormFieldValue> ddmFormFieldValues =
				_getDDMFormFieldValuesByFieldName(
					ddmFormFieldValuesMap, ddmFormField.getName());

			_validateDDMFormFieldValues(ddmFormField, ddmFormFieldValues);

			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				_traverseDDMFormFields(
					ddmFormField.getNestedDDMFormFields(),
					ddmFormFieldValue.getNestedDDMFormFieldValuesMap());
			}
		}
	}

	private void _traverseDDMFormFieldValues(
			List<DDMFormFieldValue> ddmFormFieldValues,
			Map<String, DDMFormField> ddmFormFieldsMap)
		throws DDMFormValuesValidationException {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormField ddmFormField = ddmFormFieldsMap.get(
				ddmFormFieldValue.getName());

			if (ddmFormField != null) {
				_validateDDMFormFieldValue(
					ddmFormFieldsMap.get(ddmFormFieldValue.getName()),
					ddmFormFieldValue);

				_traverseDDMFormFieldValues(
					ddmFormFieldValue.getNestedDDMFormFieldValues(),
					ddmFormField.getNestedDDMFormFieldsMap());
			}
		}
	}

	private void _validateDDMFormFieldValue(
			DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue)
		throws DDMFormValuesValidationException {

		if (ddmFormField == null) {
			throw new MustSetValidField(
				ddmFormField.getLabel(), ddmFormFieldValue.getName());
		}

		DDMFormValues ddmFormValues = ddmFormFieldValue.getDDMFormValues();

		_validateDDMFormFieldValue(
			ddmFormField, ddmFormValues.getAvailableLocales(),
			ddmFormValues.getDefaultLocale(), ddmFormFieldValue);

		_invokeDDMFormFieldValueValidator(ddmFormField, ddmFormFieldValue);

		_traverseDDMFormFieldValues(
			ddmFormFieldValue.getNestedDDMFormFieldValues(),
			ddmFormField.getNestedDDMFormFieldsMap());
	}

	private void _validateDDMFormFieldValue(
			DDMFormField ddmFormField, Set<Locale> availableLocales,
			Locale defaultLocale, DDMFormFieldValue ddmFormFieldValue)
		throws DDMFormValuesValidationException {

		Value value = ddmFormFieldValue.getValue();

		if (Validator.isNull(ddmFormField.getDataType())) {
			if (value != null) {
				throw new MustNotSetValue(
					ddmFormField.getLabel(), ddmFormField.getName());
			}
		}
		else {
			if ((value == null) ||
				(ddmFormField.isRequired() &&
				 isNull(ddmFormField, ddmFormFieldValue))) {

				throw new RequiredValue(
					ddmFormField.getLabel(), ddmFormField.getName());
			}

			if ((ddmFormField.isLocalizable() && !value.isLocalized()) ||
				(!ddmFormField.isLocalizable() && value.isLocalized())) {

				throw new MustSetValidValue(
					ddmFormField.getLabel(), ddmFormField.getName());
			}

			_validateDDMFormFieldValueLocales(
				ddmFormField, availableLocales, defaultLocale, value);

			validateDDMFormFieldValidationExpression(
				ddmFormField, ddmFormFieldValue);
		}
	}

	private void _validateDDMFormFieldValueLocales(
			DDMFormField ddmFormField, Set<Locale> availableLocales,
			Locale defaultLocale, Value value)
		throws DDMFormValuesValidationException {

		if (!value.isLocalized()) {
			return;
		}

		if (!availableLocales.equals(value.getAvailableLocales())) {
			throw new MustSetValidAvailableLocales(
				ddmFormField.getLabel(), ddmFormField.getName());
		}

		if (!defaultLocale.equals(value.getDefaultLocale())) {
			throw new MustSetValidDefaultLocale(
				ddmFormField.getLabel(), ddmFormField.getName());
		}
	}

	private void _validateDDMFormFieldValues(
			DDMFormField ddmFormField,
			List<DDMFormFieldValue> ddmFormFieldValues)
		throws DDMFormValuesValidationException {

		if (ddmFormField.isRequired() && ddmFormFieldValues.isEmpty()) {
			throw new RequiredValue(
				ddmFormField.getLabel(), ddmFormField.getName());
		}

		if (!ddmFormField.isRepeatable() && (ddmFormFieldValues.size() > 1)) {
			throw new MustSetValidValuesSize(
				ddmFormField.getLabel(), ddmFormField.getName());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormValuesValidatorImpl.class);

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	private DDMFormFieldValueExpressionParameterAccessor
		_ddmFormFieldValueExpressionParameterAccessor;
	private final DDMFormFieldValueAccessor<String>
		_defaultDDMFormFieldValueAccessor =
			new DefaultDDMFormFieldValueAccessor();

	@Reference
	private JSONFactory _jsonFactory;

	private ServiceTrackerMap<String, DDMFormFieldValueValidator>
		_serviceTrackerMap;

}