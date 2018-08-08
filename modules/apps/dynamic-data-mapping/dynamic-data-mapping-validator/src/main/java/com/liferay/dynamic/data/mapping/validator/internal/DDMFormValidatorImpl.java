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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidator;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidatorError;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidatorErrorStatus;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidatorValidateRequest;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true)
public class DDMFormValidatorImpl implements DDMFormValidator {

	@Override
	public void validate(DDMFormValidatorValidateRequest validateFormRequest)
		throws DDMFormValidationException {

		DDMForm ddmForm = validateFormRequest.getDDMForm();

		List<DDMFormValidatorError> validateFormErrors = new ArrayList<>();

		validateFormErrors.addAll(validateDDMFormLocales(ddmForm));

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		if (ddmFormFields.isEmpty()) {
			DDMFormValidatorError validateFormResponseError =
				DDMFormValidatorError.Builder.of(
					"At least one field must be set",
					DDMFormValidatorErrorStatus.
						MUST_SET_FIELDS_FOR_FORM_EXCEPTION);

			validateFormErrors.add(validateFormResponseError);
		}

		validateFormErrors.addAll(
			validateDDMFormFields(
				ddmFormFields, new HashSet<String>(),
				ddmForm.getAvailableLocales(), ddmForm.getDefaultLocale()));

		validateFormErrors.addAll(
			validateDDMFormRules(ddmForm.getDDMFormRules()));

		if (!validateFormErrors.isEmpty()) {
			throw new DDMFormValidationException(validateFormErrors);
		}
	}

	protected List<DDMFormValidatorError> validateDDMExpression(
		String ddmExpressionString) {

		if (Validator.isNull(ddmExpressionString)) {
			return Collections.emptyList();
		}

		try {
			ddmExpressionFactory.createBooleanDDMExpression(
				ddmExpressionString);
		}
		catch (DDMExpressionException ddmee) {
			String errorMessage = String.format(
				"Invalid form rule expression set: \"%s\"",
				ddmExpressionString);

			DDMFormValidatorError.Builder builder =
				DDMFormValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValidatorErrorStatus.
						MUST_SET_VALID_FORM_RULE_EXPRESSION_EXCEPTION
				).withProperty(
					"expression", ddmExpressionString
				);

			return Arrays.asList(builder.build());
		}

		return Collections.emptyList();
	}

	protected List<DDMFormValidatorError> validateDDMFormAvailableLocales(
		Set<Locale> availableLocales, Locale defaultLocale) {

		List<DDMFormValidatorError> validateFormResponseErrors =
			new ArrayList<>();

		if ((availableLocales == null) || availableLocales.isEmpty()) {
			DDMFormValidatorError validateFormResponseError =
				DDMFormValidatorError.Builder.of(
					"The available locales property was not set for the DDM " +
						"form",
					DDMFormValidatorErrorStatus.
						MUST_SET_AVAILABLE_LOCALES_EXCEPTION
				);

			validateFormResponseErrors.add(validateFormResponseError);
		}

		if ((defaultLocale != null) &&
			!availableLocales.contains(defaultLocale)) {

			String errorMessage = String.format(
				"The default locale %s must be set to a valid available locale",
				defaultLocale);

			DDMFormValidatorError.Builder builder =
				DDMFormValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValidatorErrorStatus.
						MUST_SET_DEFAULT_LOCALE_AS_AVAILABLE_LOCALE_EXCEPTION
				).withProperty(
					"locale", defaultLocale
				);

			validateFormResponseErrors.add(builder.build());
		}

		return validateFormResponseErrors;
	}

	protected List<DDMFormValidatorError> validateDDMFormFieldIndexType(
		DDMFormField ddmFormField) {

		List<DDMFormValidatorError> validateFormResponseErrors =
			new ArrayList<>();

		if (!ArrayUtil.contains(
				_DDM_FORM_FIELD_INDEX_TYPES, ddmFormField.getIndexType())) {

			String errorMessage = String.format(
				"Invalid index type set for field %s", ddmFormField.getName());

			DDMFormValidatorError.Builder builder =
				DDMFormValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValidatorErrorStatus.
						MUST_SET_VALID_INDEX_TYPE_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				);

			validateFormResponseErrors.add(builder.build());
		}

		return validateFormResponseErrors;
	}

	protected List<DDMFormValidatorError> validateDDMFormFieldName(
		DDMFormField ddmFormField, Set<String> ddmFormFieldNames) {

		List<DDMFormValidatorError> validateFormResponseErrors =
			new ArrayList<>();

		Matcher matcher = _ddmFormFieldNamePattern.matcher(
			ddmFormField.getName());

		if (!matcher.matches()) {
			String errorMessage = String.format(
				"Invalid characters entered for field name %s",
				ddmFormField.getName());

			DDMFormValidatorError.Builder builder =
				DDMFormValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValidatorErrorStatus.
						MUST_SET_VALID_CHARACTERS_FOR_FIELD_NAME_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				);

			validateFormResponseErrors.add(builder.build());
		}

		if (ddmFormFieldNames.contains(
				StringUtil.toLowerCase(ddmFormField.getName()))) {

			String errorMessage = String.format(
				"The field name %s cannot be defined more than once",
				ddmFormField.getName());

			DDMFormValidatorError.Builder builder =
				DDMFormValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValidatorErrorStatus.
						MUST_NOT_DUPLICATE_FIELD_NAME_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				);

			validateFormResponseErrors.add(builder.build());
		}

		ddmFormFieldNames.add(StringUtil.toLowerCase(ddmFormField.getName()));

		return validateFormResponseErrors;
	}

	protected List<DDMFormValidatorError> validateDDMFormFieldOptions(
		DDMFormField ddmFormField, Set<Locale> ddmFormAvailableLocales,
		Locale ddmFormDefaultLocale) {

		String fieldType = ddmFormField.getType();

		if (!fieldType.equals(DDMFormFieldType.CHECKBOX_MULTIPLE) &&
			!fieldType.equals(DDMFormFieldType.RADIO) &&
			!fieldType.equals(DDMFormFieldType.SELECT)) {

			return Collections.emptyList();
		}

		String dataSourceType = GetterUtil.getString(
			ddmFormField.getProperty("dataSourceType"), "manual");

		if (!Objects.equals(dataSourceType, "manual")) {
			return Collections.emptyList();
		}

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		Set<String> optionValues = Collections.emptySet();

		if (ddmFormFieldOptions != null) {
			optionValues = ddmFormFieldOptions.getOptionsValues();
		}

		List<DDMFormValidatorError> validateFormResponseErrors =
			new ArrayList<>();

		if (optionValues.isEmpty()) {
			String errorMessage = String.format(
				"At least one option must be set for field %s",
				ddmFormField.getName());

			DDMFormValidatorError.Builder builder =
				DDMFormValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValidatorErrorStatus.
						MUST_SET_OPTIONS_FOR_FIELD_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				);

			validateFormResponseErrors.add(builder.build());
		}

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
			LocalizedValue localizedValue = ddmFormFieldOptions.getOptionLabels(
				optionValue);

			validateFormResponseErrors.addAll(
				validateDDMFormFieldPropertyValue(
					ddmFormField.getName(), "options", localizedValue,
					ddmFormAvailableLocales, ddmFormDefaultLocale));
		}

		return validateFormResponseErrors;
	}

	protected List<DDMFormValidatorError> validateDDMFormFieldPropertyValue(
		String fieldName, String propertyName, LocalizedValue propertyValue,
		Set<Locale> ddmFormAvailableLocales, Locale ddmFormDefaultLocale) {

		List<DDMFormValidatorError> validateFormResponseErrors =
			new ArrayList<>();

		if ((ddmFormDefaultLocale != null) &&
			!ddmFormDefaultLocale.equals(propertyValue.getDefaultLocale())) {

			String errorMessage = String.format(
				"Invalid default locale set for the property '%s' of field " +
					"name %s",
				propertyName, fieldName);

			DDMFormValidatorError.Builder builder =
				DDMFormValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValidatorErrorStatus.
						MUST_SET_VALID_DEFAULT_LOCALE_FOR_PROPERTY_EXCEPTION
				).withProperty(
					"field", fieldName
				).withProperty(
					"property", propertyName
				);

			validateFormResponseErrors.add(builder.build());
		}

		if (!ddmFormAvailableLocales.equals(
				propertyValue.getAvailableLocales())) {

			String errorMessage = String.format(
				"Invalid available locales set for the property '%s' of " +
					"field name %s",
				propertyName, fieldName);

			DDMFormValidatorError.Builder builder =
				DDMFormValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValidatorErrorStatus.
						MUST_SET_VALID_AVAILABLE_LOCALES_FOR_PROPERTY_EXCEPTION
				).withProperty(
					"field", fieldName
				).withProperty(
					"property", propertyName
				);

			validateFormResponseErrors.add(builder.build());
		}

		return validateFormResponseErrors;
	}

	protected List<DDMFormValidatorError> validateDDMFormFields(
		List<DDMFormField> ddmFormFields, Set<String> ddmFormFieldNames,
		Set<Locale> ddmFormAvailableLocales, Locale ddmFormDefaultLocale) {

		List<DDMFormValidatorError> validateFormResponseErrors =
			new ArrayList<>();

		for (DDMFormField ddmFormField : ddmFormFields) {
			validateFormResponseErrors.addAll(
				validateDDMFormFieldName(ddmFormField, ddmFormFieldNames));

			validateFormResponseErrors.addAll(
				validateDDMFormFieldType(ddmFormField));

			validateFormResponseErrors.addAll(
				validateDDMFormFieldIndexType(ddmFormField));

			validateFormResponseErrors.addAll(
				validateDDMFormFieldOptions(
					ddmFormField, ddmFormAvailableLocales,
					ddmFormDefaultLocale));

			validateFormResponseErrors.addAll(
				validateOptionalDDMFormFieldLocalizedProperty(
					ddmFormField, "label", ddmFormAvailableLocales,
					ddmFormDefaultLocale));

			validateFormResponseErrors.addAll(
				validateOptionalDDMFormFieldLocalizedProperty(
					ddmFormField, "predefinedValue", ddmFormAvailableLocales,
					ddmFormDefaultLocale));

			validateFormResponseErrors.addAll(
				validateOptionalDDMFormFieldLocalizedProperty(
					ddmFormField, "tip", ddmFormAvailableLocales,
					ddmFormDefaultLocale));

			validateFormResponseErrors.addAll(
				validateDDMFormFieldValidationExpression(ddmFormField));

			validateFormResponseErrors.addAll(
				validateDDMFormFieldVisibilityExpression(ddmFormField));

			validateFormResponseErrors.addAll(
				validateDDMFormFields(
					ddmFormField.getNestedDDMFormFields(), ddmFormFieldNames,
					ddmFormAvailableLocales, ddmFormDefaultLocale));
		}

		return validateFormResponseErrors;
	}

	protected List<DDMFormValidatorError> validateDDMFormFieldType(
		DDMFormField ddmFormField) {

		List<DDMFormValidatorError> validateFormResponseErrors =
			new ArrayList<>();

		if (Validator.isNull(ddmFormField.getType())) {
			String errorMessage = String.format(
				"The field type was never set for the DDM form field with " +
					"the field name %s",
				ddmFormField.getName());

			DDMFormValidatorError.Builder builder =
				DDMFormValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValidatorErrorStatus.MUST_SET_FIELD_TYPE_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				);

			validateFormResponseErrors.add(builder.build());
		}

		Matcher matcher = _ddmFormFieldTypePattern.matcher(
			ddmFormField.getType());

		if (!matcher.matches()) {
			String errorMessage = String.format(
				"Invalid characters entered for field type %s",
				ddmFormField.getType());

			DDMFormValidatorError.Builder builder =
				DDMFormValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValidatorErrorStatus.
						MUST_SET_VALID_CHARACTERS_FOR_FIELD_TYPE_EXCEPTION
				).withProperty(
					"type", ddmFormField.getType()
				);

			validateFormResponseErrors.add(builder.build());
		}

		return validateFormResponseErrors;
	}

	protected List<DDMFormValidatorError>
		validateDDMFormFieldValidationExpression(DDMFormField ddmFormField) {

		DDMFormFieldValidation ddmFormFieldValidation =
			ddmFormField.getDDMFormFieldValidation();

		if (ddmFormFieldValidation == null) {
			return Collections.emptyList();
		}

		String validationExpression = ddmFormFieldValidation.getExpression();

		if (Validator.isNull(validationExpression)) {
			return Collections.emptyList();
		}

		try {
			ddmExpressionFactory.createBooleanDDMExpression(
				validationExpression);
		}
		catch (DDMExpressionException ddmee) {
			String errorMessage = String.format(
				"Invalid validation expression set for field %s: %s",
				ddmFormField.getName(), validationExpression);

			DDMFormValidatorError.Builder builder =
				DDMFormValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValidatorErrorStatus.
						MUST_SET_VALID_VALIDATION_EXPRESSION_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				).withProperty(
					"expression", validationExpression
				);

			return Arrays.asList(builder.build());
		}

		return Collections.emptyList();
	}

	protected List<DDMFormValidatorError>
		validateDDMFormFieldVisibilityExpression(DDMFormField ddmFormField) {

		String visibilityExpression = ddmFormField.getVisibilityExpression();

		if (Validator.isNull(visibilityExpression)) {
			return Collections.emptyList();
		}

		try {
			ddmExpressionFactory.createBooleanDDMExpression(
				visibilityExpression);
		}
		catch (DDMExpressionException ddmee) {
			String errorMessage = String.format(
				"Invalid visibility expression set for field %s: %s",
				ddmFormField.getName(), visibilityExpression);

			DDMFormValidatorError.Builder builder =
				DDMFormValidatorError.Builder.newBuilder(
					errorMessage,
					DDMFormValidatorErrorStatus.
						MUST_SET_VALID_VISIBILITY_EXPRESSION_EXCEPTION
				).withProperty(
					"field", ddmFormField.getName()
				).withProperty(
					"expression", visibilityExpression
				);

			return Arrays.asList(builder.build());
		}

		return Collections.emptyList();
	}

	protected List<DDMFormValidatorError> validateDDMFormLocales(
		DDMForm ddmForm) {

		List<DDMFormValidatorError> validateFormResponseErrors =
			new ArrayList<>();

		Locale defaultLocale = ddmForm.getDefaultLocale();

		if (defaultLocale == null) {
			DDMFormValidatorError validateFormResponseError =
				DDMFormValidatorError.Builder.of(
					"The default locale property was not set for the DDM form",
					DDMFormValidatorErrorStatus.
						MUST_SET_DEFAULT_LOCALE_EXCEPTION
				);

			validateFormResponseErrors.add(validateFormResponseError);
		}

		validateFormResponseErrors.addAll(
			validateDDMFormAvailableLocales(
				ddmForm.getAvailableLocales(), defaultLocale));

		return validateFormResponseErrors;
	}

	protected List<DDMFormValidatorError> validateDDMFormRule(
		DDMFormRule ddmFormRule) {

		List<DDMFormValidatorError> validateFormResponseErrors =
			new ArrayList<>();

		for (String action : ddmFormRule.getActions()) {
			validateFormResponseErrors.addAll(validateDDMExpression(action));
		}

		validateFormResponseErrors.addAll(
			validateDDMExpression(ddmFormRule.getCondition()));

		return validateFormResponseErrors;
	}

	protected List<DDMFormValidatorError> validateDDMFormRules(
		List<DDMFormRule> ddmFormRules) {

		List<DDMFormValidatorError> validateFormResponseErrors =
			new ArrayList<>();

		for (DDMFormRule ddmFormRule : ddmFormRules) {
			validateFormResponseErrors.addAll(validateDDMFormRule(ddmFormRule));
		}

		return validateFormResponseErrors;
	}

	protected List<DDMFormValidatorError>
		validateOptionalDDMFormFieldLocalizedProperty(
			DDMFormField ddmFormField, String propertyName,
			Set<Locale> ddmFormAvailableLocales, Locale ddmFormDefaultLocale) {

		LocalizedValue propertyValue =
			(LocalizedValue)BeanPropertiesUtil.getObject(
				ddmFormField, propertyName);

		if (MapUtil.isEmpty(propertyValue.getValues())) {
			return Collections.emptyList();
		}

		return validateDDMFormFieldPropertyValue(
			ddmFormField.getName(), propertyName, propertyValue,
			ddmFormAvailableLocales, ddmFormDefaultLocale);
	}

	@Reference
	protected DDMExpressionFactory ddmExpressionFactory;

	private static final String[] _DDM_FORM_FIELD_INDEX_TYPES =
		{StringPool.BLANK, "keyword", "text"};

	private final Pattern _ddmFormFieldNamePattern = Pattern.compile(
		"([^\\p{Punct}|\\p{Space}$]|_)+");
	private final Pattern _ddmFormFieldTypePattern = Pattern.compile(
		"([^\\p{Punct}|\\p{Space}$]|[-_])+");

}