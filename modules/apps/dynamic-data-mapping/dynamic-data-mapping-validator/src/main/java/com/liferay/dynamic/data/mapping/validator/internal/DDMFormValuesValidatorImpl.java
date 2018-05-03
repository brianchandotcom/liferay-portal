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
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustNotSetValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidAvailableLocales;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidDefaultLocale;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidField;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.MustSetValidValuesSize;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidationException.RequiredValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true)
public class DDMFormValuesValidatorImpl implements DDMFormValuesValidator {

	@Override
	public void validate(DDMFormValues ddmFormValues)
		throws DDMFormValuesValidationException {

		DDMForm ddmForm = ddmFormValues.getDDMForm();

		if (ddmForm == null) {
			throw new NullPointerException("A DDM Form instance was never set");
		}

		traverseDDMFormFields(
			ddmForm.getDDMFormFields(),
			ddmFormValues.getDDMFormFieldValuesMap());

		traverseDDMFormFieldValues(
			ddmFormValues.getDDMFormFieldValues(),
			ddmForm.getDDMFormFieldsMap(false));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeDDMFormFieldValueValidator"
	)
	protected void addDDMFormFieldValueValidator(
		DDMFormFieldValueValidator ddmFormFieldValueValidator,
		Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "ddm.form.field.type.name");

		if (Validator.isNull(type)) {
			return;
		}

		_ddmFormFieldValueValidators.put(type, ddmFormFieldValueValidator);
	}

	protected boolean evaluateValidationExpression(
			String expressionString, String ddmFormFieldName, String dataType,
			String valueString)
		throws DDMFormValuesValidationException {

		if (Validator.isNull(valueString)) {
			return true;
		}

		try {
			DDMExpression<Boolean> ddmExpression =
				_ddmExpressionFactory.createBooleanDDMExpression(
					expressionString);

			if (dataType.equals(FieldConstants.INTEGER)) {
				ddmExpression.setIntegerVariableValue(
					ddmFormFieldName, GetterUtil.getInteger(valueString));
			}
			else if (dataType.equals(FieldConstants.BOOLEAN)) {
				ddmExpression.setBooleanVariableValue(
					ddmFormFieldName, GetterUtil.getBoolean(valueString));
			}
			else {
				ddmExpression.setStringVariableValue(
					ddmFormFieldName, valueString);
			}

			return ddmExpression.evaluate();
		}
		catch (DDMExpressionException ddmee) {
			throw new DDMFormValuesValidationException(ddmee);
		}
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

	protected List<DDMFormFieldValue> getDDMFormFieldValuesByFieldName(
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap,
		String fieldName) {

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			fieldName);

		if (ddmFormFieldValues == null) {
			return Collections.emptyList();
		}

		return ddmFormFieldValues;
	}

	protected void invokeDDMFormFieldValueValidator(
			DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue)
		throws DDMFormValuesValidationException {

		DDMFormFieldValueValidator ddmFormFieldValueValidator =
			_ddmFormFieldValueValidators.get(ddmFormField.getType());

		if (ddmFormFieldValueValidator == null) {
			return;
		}

		try {
			ddmFormFieldValueValidator.validate(
				ddmFormField, ddmFormFieldValue.getValue());
		}
		catch (Exception e) {
			throw new MustSetValidValue(ddmFormField.getName(), e);
		}
	}

	protected boolean isNull(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return true;
		}

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			getDDMFormFieldValueAccessor(ddmFormField.getType());

		for (Locale availableLocale : value.getAvailableLocales()) {
			if (ddmFormFieldValueAccessor.isEmpty(
					ddmFormFieldValue, availableLocale)) {

				return true;
			}
		}

		return false;
	}

	protected void removeDDMFormFieldValueValidator(
		DDMFormFieldValueValidator ddmFormFieldValueValidator,
		Map<String, Objects> properties) {

		String type = MapUtil.getString(properties, "ddm.form.field.type.name");

		_ddmFormFieldValueValidators.remove(type);
	}

	@Reference(unbind = "-")
	protected void setDDMExpressionFactory(
		DDMExpressionFactory ddmExpressionFactory) {

		_ddmExpressionFactory = ddmExpressionFactory;
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected void traverseDDMFormFields(
			List<DDMFormField> ddmFormFields,
			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap)
		throws DDMFormValuesValidationException {

		for (DDMFormField ddmFormField : ddmFormFields) {
			List<DDMFormFieldValue> ddmFormFieldValues =
				getDDMFormFieldValuesByFieldName(
					ddmFormFieldValuesMap, ddmFormField.getName());

			validateDDMFormFieldValues(ddmFormField, ddmFormFieldValues);

			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				traverseDDMFormFields(
					ddmFormField.getNestedDDMFormFields(),
					ddmFormFieldValue.getNestedDDMFormFieldValuesMap());
			}
		}
	}

	protected void traverseDDMFormFieldValues(
			List<DDMFormFieldValue> ddmFormFieldValues,
			Map<String, DDMFormField> ddmFormFieldsMap)
		throws DDMFormValuesValidationException {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormField ddmFormField = ddmFormFieldsMap.get(
				ddmFormFieldValue.getName());

			if (Validator.isNotNull(ddmFormField)) {
				validateDDMFormFieldValue(
					ddmFormFieldsMap.get(ddmFormFieldValue.getName()),
					ddmFormFieldValue);

				traverseDDMFormFieldValues(
					ddmFormFieldValue.getNestedDDMFormFieldValues(),
					ddmFormField.getNestedDDMFormFieldsMap());
			}
		}
	}

	protected void validateDDMFormFieldValidationExpression(
			DDMFormField ddmFormField, Value value)
		throws DDMFormValuesValidationException {

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		if (ddmFormFieldValidation == null) {
			return;
		}

		String validationExpression = ddmFormFieldValidation.getExpression();

		if (Validator.isNull(validationExpression)) {
			return;
		}

		for (Locale locale : value.getAvailableLocales()) {
			boolean valid = evaluateValidationExpression(
				validationExpression, ddmFormField.getName(),
				ddmFormField.getDataType(), value.getString(locale));

			if (!valid) {
				throw new MustSetValidValue(ddmFormField.getName());
			}
		}
	}

	protected void validateDDMFormFieldValue(
			DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue)
		throws DDMFormValuesValidationException {

		if (ddmFormField == null) {
			throw new MustSetValidField(ddmFormFieldValue.getName());
		}

		DDMFormValues ddmFormValues = ddmFormFieldValue.getDDMFormValues();

		validateDDMFormFieldValue(
			ddmFormField, ddmFormValues.getAvailableLocales(),
			ddmFormValues.getDefaultLocale(), ddmFormFieldValue);

		invokeDDMFormFieldValueValidator(ddmFormField, ddmFormFieldValue);

		traverseDDMFormFieldValues(
			ddmFormFieldValue.getNestedDDMFormFieldValues(),
			ddmFormField.getNestedDDMFormFieldsMap());
	}

	protected void validateDDMFormFieldValue(
			DDMFormField ddmFormField, Set<Locale> availableLocales,
			Locale defaultLocale, DDMFormFieldValue ddmFormFieldValue)
		throws DDMFormValuesValidationException {

		Value value = ddmFormFieldValue.getValue();

		if (Validator.isNull(ddmFormField.getDataType())) {
			if (value != null) {
				throw new MustNotSetValue(ddmFormField.getName());
			}
		}
		else {
			if ((value == null) ||
				(ddmFormField.isRequired() &&
				 isNull(ddmFormField, ddmFormFieldValue))) {

				throw new RequiredValue(ddmFormField.getName());
			}

			if ((ddmFormField.isLocalizable() && !value.isLocalized()) ||
				(!ddmFormField.isLocalizable() && value.isLocalized())) {

				throw new MustSetValidValue(ddmFormField.getName());
			}

			validateDDMFormFieldValueLocales(
				ddmFormField, availableLocales, defaultLocale, value);

			validateDDMFormFieldValidationExpression(ddmFormField, value);
		}
	}

	protected void validateDDMFormFieldValueLocales(
			DDMFormField ddmFormField, Set<Locale> availableLocales,
			Locale defaultLocale, Value value)
		throws DDMFormValuesValidationException {

		if (!value.isLocalized()) {
			return;
		}

		if (!availableLocales.equals(value.getAvailableLocales())) {
			throw new MustSetValidAvailableLocales(ddmFormField.getName());
		}

		if (!defaultLocale.equals(value.getDefaultLocale())) {
			throw new MustSetValidDefaultLocale(ddmFormField.getName());
		}
	}

	protected void validateDDMFormFieldValues(
			DDMFormField ddmFormField,
			List<DDMFormFieldValue> ddmFormFieldValues)
		throws DDMFormValuesValidationException {

		if (ddmFormField.isRequired() && ddmFormFieldValues.isEmpty()) {
			throw new RequiredValue(ddmFormField.getName());
		}

		if (!ddmFormField.isRepeatable() && (ddmFormFieldValues.size() > 1)) {
			throw new MustSetValidValuesSize(ddmFormField.getName());
		}
	}

	private DDMExpressionFactory _ddmExpressionFactory;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private final Map<String, DDMFormFieldValueValidator>
		_ddmFormFieldValueValidators = new ConcurrentHashMap<>();
	private final DDMFormFieldValueAccessor<String>
		_defaultDDMFormFieldValueAccessor =
			new DefaultDDMFormFieldValueAccessor();
	private JSONFactory _jsonFactory;

}