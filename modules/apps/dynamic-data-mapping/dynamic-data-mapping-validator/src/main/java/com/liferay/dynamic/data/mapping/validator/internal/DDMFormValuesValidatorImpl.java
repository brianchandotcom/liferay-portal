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
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidatorError;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidatorErrorStatus;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidatorValidateRequest;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
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
	public void validate(
			DDMFormValuesValidatorValidateRequest validateFormValuesRequest)
		throws DDMFormValuesValidationException {

		DDMFormValues ddmFormValues =
			validateFormValuesRequest.getDDMFormValues();

		DDMForm ddmForm = ddmFormValues.getDDMForm();

		if (ddmForm == null) {
			throw new NullPointerException("A DDM Form instance was never set");
		}

		List<DDMFormValuesValidatorError> validateFormValuesErrors =
			new ArrayList<>();

		validateFormValuesErrors.addAll(
			traverseDDMFormFields(
				ddmForm.getDDMFormFields(),
				ddmFormValues.getDDMFormFieldValuesMap()));

		validateFormValuesErrors.addAll(
			traverseDDMFormFieldValues(
				ddmFormValues.getDDMFormFieldValues(),
				ddmForm.getDDMFormFieldsMap(false)));

		if (!validateFormValuesErrors.isEmpty()) {
			throw new DDMFormValuesValidationException(
				validateFormValuesErrors);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
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
		String valueString) {

		if (Validator.isNull(valueString)) {
			return true;
		}

		try {
			DDMExpression<Boolean> ddmExpression =
				ddmExpressionFactory.createBooleanDDMExpression(
					expressionString);

			if (dataType.equals(FieldConstants.BOOLEAN)) {
				ddmExpression.setBooleanVariableValue(
					ddmFormFieldName, GetterUtil.getBoolean(valueString));
			}
			else if (dataType.equals(FieldConstants.DOUBLE)) {
				ddmExpression.setDoubleVariableValue(
					ddmFormFieldName, GetterUtil.getDouble(valueString));
			}
			else if (dataType.equals(FieldConstants.INTEGER)) {
				ddmExpression.setIntegerVariableValue(
					ddmFormFieldName, GetterUtil.getInteger(valueString));
			}
			else {
				ddmExpression.setStringVariableValue(
					ddmFormFieldName, valueString);
			}

			return ddmExpression.evaluate();
		}
		catch (DDMExpressionException ddmee) {
			return false;
		}
	}

	protected DDMFormFieldValueAccessor<?> getDDMFormFieldValueAccessor(
		String type) {

		DDMFormFieldValueAccessor<?> ddmFormFieldValueAccessor =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldValueAccessor(type);

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

	protected List<DDMFormValuesValidatorError>
		invokeDDMFormFieldValueValidator(
			DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		DDMFormFieldValueValidator ddmFormFieldValueValidator =
			_ddmFormFieldValueValidators.get(ddmFormField.getType());

		if (ddmFormFieldValueValidator == null) {
			return Collections.emptyList();
		}

		try {
			ddmFormFieldValueValidator.validate(
				ddmFormField, ddmFormFieldValue.getValue());
		}
		catch (Exception e) {
			String errorMessage = String.format(
				"Invalid value set for field name %s", ddmFormField.getName());

			DDMFormValuesValidatorError.Builder builder =
				DDMFormValuesValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_VALUE_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				);

			return Arrays.asList(builder.build());
		}

		return Collections.emptyList();
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

	protected List<DDMFormValuesValidatorError> traverseDDMFormFields(
		List<DDMFormField> ddmFormFields,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap) {

		List<DDMFormValuesValidatorError> validateFormValuesResponseErrors =
			new ArrayList<>();

		for (DDMFormField ddmFormField : ddmFormFields) {
			List<DDMFormFieldValue> ddmFormFieldValues =
				getDDMFormFieldValuesByFieldName(
					ddmFormFieldValuesMap, ddmFormField.getName());

			validateFormValuesResponseErrors.addAll(
				validateDDMFormFieldValues(ddmFormField, ddmFormFieldValues));

			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				validateFormValuesResponseErrors.addAll(
					traverseDDMFormFields(
						ddmFormField.getNestedDDMFormFields(),
						ddmFormFieldValue.getNestedDDMFormFieldValuesMap()));
			}
		}

		return validateFormValuesResponseErrors;
	}

	protected List<DDMFormValuesValidatorError> traverseDDMFormFieldValues(
		List<DDMFormFieldValue> ddmFormFieldValues,
		Map<String, DDMFormField> ddmFormFieldsMap) {

		List<DDMFormValuesValidatorError> validateFormValuesResponseErrors =
			new ArrayList<>();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			DDMFormField ddmFormField = ddmFormFieldsMap.get(
				ddmFormFieldValue.getName());

			if (ddmFormField != null) {
				validateFormValuesResponseErrors.addAll(
					validateDDMFormFieldValue(
						ddmFormFieldsMap.get(ddmFormFieldValue.getName()),
						ddmFormFieldValue));

				validateFormValuesResponseErrors.addAll(
					traverseDDMFormFieldValues(
						ddmFormFieldValue.getNestedDDMFormFieldValues(),
						ddmFormField.getNestedDDMFormFieldsMap()));
			}
		}

		return validateFormValuesResponseErrors;
	}

	protected List<DDMFormValuesValidatorError>
		validateDDMFormFieldValidationExpression(
			DDMFormField ddmFormField, Value value) {

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		if (ddmFormFieldValidation == null) {
			return Collections.emptyList();
		}

		String validationExpression = ddmFormFieldValidation.getExpression();

		if (Validator.isNull(validationExpression)) {
			return Collections.emptyList();
		}

		List<DDMFormValuesValidatorError> validateFormValuesResponseErrors =
			new ArrayList<>();

		for (Locale locale : value.getAvailableLocales()) {
			boolean valid = evaluateValidationExpression(
				validationExpression, ddmFormField.getName(),
				ddmFormField.getDataType(), value.getString(locale));

			if (!valid) {
				String errorMessage = String.format(
					"Invalid value set for field name %s",
					ddmFormField.getName());

				DDMFormValuesValidatorError.Builder builder =
					DDMFormValuesValidatorError.Builder.newBuilder(
						errorMessage,
						DDMFormValuesValidatorErrorStatus.
							MUST_SET_VALID_VALUE_EXCEPTION
					).withProperty(
						"field", ddmFormField.getName()
					);

				validateFormValuesResponseErrors.add(builder.build());
			}
		}

		return validateFormValuesResponseErrors;
	}

	protected List<DDMFormValuesValidatorError> validateDDMFormFieldValue(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		List<DDMFormValuesValidatorError> validateFormValuesResponseErrors =
			new ArrayList<>();

		if (ddmFormField == null) {
			String errorMessage = String.format(
				"There is no field name %s defined on form",
				ddmFormField.getName());

			DDMFormValuesValidatorError.Builder builder =
				DDMFormValuesValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_FIELD_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				);

			validateFormValuesResponseErrors.add(builder.build());
		}

		DDMFormValues ddmFormValues = ddmFormFieldValue.getDDMFormValues();

		validateFormValuesResponseErrors.addAll(
			validateDDMFormFieldValue(
				ddmFormField, ddmFormValues.getAvailableLocales(),
				ddmFormValues.getDefaultLocale(), ddmFormFieldValue));

		validateFormValuesResponseErrors.addAll(
			invokeDDMFormFieldValueValidator(ddmFormField, ddmFormFieldValue));

		validateFormValuesResponseErrors.addAll(
			traverseDDMFormFieldValues(
				ddmFormFieldValue.getNestedDDMFormFieldValues(),
				ddmFormField.getNestedDDMFormFieldsMap()));

		return validateFormValuesResponseErrors;
	}

	protected List<DDMFormValuesValidatorError> validateDDMFormFieldValue(
		DDMFormField ddmFormField, Set<Locale> availableLocales,
		Locale defaultLocale, DDMFormFieldValue ddmFormFieldValue) {

		List<DDMFormValuesValidatorError> validateFormValuesResponseErrors =
			new ArrayList<>();

		Value value = ddmFormFieldValue.getValue();

		if (Validator.isNull(ddmFormField.getDataType())) {
			if (value != null) {
				String errorMessage = String.format(
					"Value should not be set for transient field name %s",
					ddmFormField.getName());

				DDMFormValuesValidatorError.Builder builder =
					DDMFormValuesValidatorError.Builder.newBuilder(
						errorMessage,
						DDMFormValuesValidatorErrorStatus.
							MUST_NOT_SET_VALUE_EXCEPTION
					).withProperty(
						"field", ddmFormField.getName()
					);

				validateFormValuesResponseErrors.add(builder.build());
			}
		}
		else {
			if ((value == null) ||
				(ddmFormField.isRequired() &&
				 isNull(ddmFormField, ddmFormFieldValue))) {

				String errorMessage = String.format(
					"No value defined for field name %s",
					ddmFormField.getName());

				DDMFormValuesValidatorError.Builder builder =
					DDMFormValuesValidatorError.Builder.newBuilder(
						errorMessage,
						DDMFormValuesValidatorErrorStatus.
							REQUIRED_VALUE_EXCEPTION
					).withProperty(
						"field", ddmFormField.getName()
					);

				validateFormValuesResponseErrors.add(builder.build());
			}

			if ((ddmFormField.isLocalizable() && !value.isLocalized()) ||
				(!ddmFormField.isLocalizable() && value.isLocalized())) {

				String errorMessage = String.format(
					"Invalid value set for field name %s",
					ddmFormField.getName());

				DDMFormValuesValidatorError.Builder builder =
					DDMFormValuesValidatorError.Builder.newBuilder(
						errorMessage,
						DDMFormValuesValidatorErrorStatus.
							MUST_SET_VALID_VALUE_EXCEPTION
					).withProperty(
						"field", ddmFormField.getName()
					);

				validateFormValuesResponseErrors.add(builder.build());
			}

			validateFormValuesResponseErrors.addAll(
				validateDDMFormFieldValueLocales(
					ddmFormField, availableLocales, defaultLocale, value));

			validateFormValuesResponseErrors.addAll(
				validateDDMFormFieldValidationExpression(ddmFormField, value));
		}

		return validateFormValuesResponseErrors;
	}

	protected List<DDMFormValuesValidatorError>
		validateDDMFormFieldValueLocales(
			DDMFormField ddmFormField, Set<Locale> availableLocales,
			Locale defaultLocale, Value value) {

		if (!value.isLocalized()) {
			return Collections.emptyList();
		}

		List<DDMFormValuesValidatorError> validateFormValuesResponseErrors =
			new ArrayList<>();

		if (!availableLocales.equals(value.getAvailableLocales())) {
			String errorMessage = String.format(
				"Invalid available locales set for field name %s",
				ddmFormField.getName());

			DDMFormValuesValidatorError.Builder builder =
				DDMFormValuesValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_AVAILABLE_LOCALES_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				);

			validateFormValuesResponseErrors.add(builder.build());
		}

		if (!defaultLocale.equals(value.getDefaultLocale())) {
			String errorMessage = String.format(
				"Invalid default locale set for field name %s",
				ddmFormField.getName());

			DDMFormValuesValidatorError.Builder builder =
				DDMFormValuesValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_DEFAULT_LOCALE_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				);

			validateFormValuesResponseErrors.add(builder.build());
		}

		return validateFormValuesResponseErrors;
	}

	protected List<DDMFormValuesValidatorError> validateDDMFormFieldValues(
		DDMFormField ddmFormField, List<DDMFormFieldValue> ddmFormFieldValues) {

		List<DDMFormValuesValidatorError> validateFormValuesResponseErrors =
			new ArrayList<>();

		if (ddmFormField.isRequired() && ddmFormFieldValues.isEmpty()) {
			String errorMessage = String.format(
				"No value defined for field name %s", ddmFormField.getName());

			DDMFormValuesValidatorError.Builder builder =
				DDMFormValuesValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValuesValidatorErrorStatus.REQUIRED_VALUE_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				);

			validateFormValuesResponseErrors.add(builder.build());
		}

		if (!ddmFormField.isRepeatable() && (ddmFormFieldValues.size() > 1)) {
			String errorMessage = String.format(
				"Incorrect number of values set for field name %s",
				ddmFormField.getName());

			DDMFormValuesValidatorError.Builder builder =
				DDMFormValuesValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValuesValidatorErrorStatus.
						MUST_SET_VALID_VALUES_SIZE_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				);

			validateFormValuesResponseErrors.add(builder.build());
		}

		return validateFormValuesResponseErrors;
	}

	@Reference
	protected DDMExpressionFactory ddmExpressionFactory;

	@Reference
	protected DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker;

	@Reference
	protected JSONFactory jsonFactory;

	private final Map<String, DDMFormFieldValueValidator>
		_ddmFormFieldValueValidators = new ConcurrentHashMap<>();
	private final DDMFormFieldValueAccessor<String>
		_defaultDDMFormFieldValueAccessor =
			new DefaultDDMFormFieldValueAccessor();

}