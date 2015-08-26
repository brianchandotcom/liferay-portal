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

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidator;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMFormValidator.class)
public class DDMFormValidatorImpl implements DDMFormValidator {

	@Override
	public void validate(DDMForm ddmForm) throws DDMFormValidationException {
		validateDDMFormLocales(ddmForm);

		validateDDMFormFields(
			ddmForm.getDDMFormFields(), new HashSet<String>(),
			ddmForm.getAvailableLocales(), ddmForm.getDefaultLocale());
	}

	protected void validateDDMFormAvailableLocales(
			Set<Locale> availableLocales, Locale defaultLocale)
		throws DDMFormValidationException {

		if ((availableLocales == null) || availableLocales.isEmpty()) {
			throw new DDMFormValidationException(
				"The available locales property was never set for DDM form");
		}

		if (!availableLocales.contains(defaultLocale)) {
			throw new DDMFormValidationException(
				"The default locale " + defaultLocale + " should be set as a " +
					"valid available locale");
		}
	}

	protected void validateDDMFormFieldIndexType(DDMFormField ddmFormField)
		throws DDMFormValidationException {

		if (!ArrayUtil.contains(
				_ddmFormFieldIndexTypes, ddmFormField.getIndexType())) {

			throw new DDMFormValidationException(
				"Invalid index type set for field " + ddmFormField.getName());
		}
	}

	protected void validateDDMFormFieldName(
			DDMFormField ddmFormField, Set<String> ddmFormFieldNames)
		throws DDMFormValidationException {

		Matcher matcher = _ddmFormFieldNamePattern.matcher(
			ddmFormField.getName());

		if (!matcher.matches()) {
			throw new DDMFormValidationException(
				"Invalid characters were defined for field name " +
					ddmFormField.getName());
		}

		if (ddmFormFieldNames.contains(
				StringUtil.toLowerCase(ddmFormField.getName()))) {

			throw new DDMFormValidationException(
				"The field name " + ddmFormField.getName() +
					" was defined more than once");
		}

		ddmFormFieldNames.add(StringUtil.toLowerCase(ddmFormField.getName()));
	}

	protected void validateDDMFormFieldOptions(
			DDMFormField ddmFormField, Set<Locale> ddmFormAvailableLocales,
			Locale ddmFormDefaultLocale)
		throws DDMFormValidationException {

		String fieldType = ddmFormField.getType();

		if (!fieldType.equals(DDMFormFieldType.RADIO) &&
			!fieldType.equals(DDMFormFieldType.SELECT)) {

			return;
		}

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		Set<String> optionValues = Collections.emptySet();

		if (ddmFormFieldOptions != null) {
			optionValues = ddmFormFieldOptions.getOptionsValues();
		}

		if (optionValues.isEmpty()) {
			throw new DDMFormValidationException(
				"At least one option should be set for field " +
					ddmFormField.getName());
		}

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
			LocalizedValue localizedValue = ddmFormFieldOptions.getOptionLabels(
				optionValue);

			validateDDMFormFieldPropertyValue(
				ddmFormField.getName(), "options", localizedValue,
				ddmFormAvailableLocales, ddmFormDefaultLocale);
		}
	}

	protected void validateDDMFormFieldPropertyValue(
			String fieldName, String propertyName, LocalizedValue propertyValue,
			Set<Locale> ddmFormAvailableLocales, Locale ddmFormDefaultLocale)
		throws DDMFormValidationException {

		if (!ddmFormDefaultLocale.equals(propertyValue.getDefaultLocale())) {
			throw new DDMFormValidationException(
				"Invalid default locale set for property \"" + propertyName +
					"\" of field name " + fieldName);
		}

		if (!ddmFormAvailableLocales.equals(
				propertyValue.getAvailableLocales())) {

			throw new DDMFormValidationException(
				"Invalid available locales set for property \"" +
					propertyName + "\" of field name " + fieldName);
		}
	}

	protected void validateDDMFormFields(
			List<DDMFormField> ddmFormFields, Set<String> ddmFormFieldNames,
			Set<Locale> ddmFormAvailableLocales, Locale ddmFormDefaultLocale)
		throws DDMFormValidationException {

		for (DDMFormField ddmFormField : ddmFormFields) {
			validateDDMFormFieldName(ddmFormField, ddmFormFieldNames);

			validateDDMFormFieldType(ddmFormField);

			validateDDMFormFieldIndexType(ddmFormField);

			validateDDMFormFieldOptions(
				ddmFormField, ddmFormAvailableLocales, ddmFormDefaultLocale);

			validateOptionalDDMFormFieldLocalizedProperty(
				ddmFormField, "label", ddmFormAvailableLocales,
				ddmFormDefaultLocale);

			validateOptionalDDMFormFieldLocalizedProperty(
				ddmFormField, "predefinedValue", ddmFormAvailableLocales,
				ddmFormDefaultLocale);

			validateOptionalDDMFormFieldLocalizedProperty(
				ddmFormField, "tip", ddmFormAvailableLocales,
				ddmFormDefaultLocale);

			validateDDMFormFields(
				ddmFormField.getNestedDDMFormFields(), ddmFormFieldNames,
				ddmFormAvailableLocales, ddmFormDefaultLocale);
		}
	}

	protected void validateDDMFormFieldType(DDMFormField ddmFormField)
		throws DDMFormValidationException {

		if ((ddmFormField.getType() == null) ||
			(ddmFormField.getType().trim().length() == 0)) {
				throw new DDMFormValidationException(
					"The field type was never set for DDM form field");
		}

		Matcher matcher = _ddmFormFieldTypePattern.matcher(
			ddmFormField.getType());

		if (!matcher.matches()) {
			throw new DDMFormValidationException(
				"Invalid characters were defined for field type " +
					ddmFormField.getType());
		}
	}

	protected void validateDDMFormLocales(DDMForm ddmForm)
		throws DDMFormValidationException {

		Locale defaultLocale = ddmForm.getDefaultLocale();

		if (defaultLocale == null) {
			throw new DDMFormValidationException(
				"The default locale property was never set for DDM form");
		}

		validateDDMFormAvailableLocales(
			ddmForm.getAvailableLocales(), defaultLocale);
	}

	protected void validateOptionalDDMFormFieldLocalizedProperty(
			DDMFormField ddmFormField, String propertyName,
			Set<Locale> ddmFormAvailableLocales, Locale ddmFormDefaultLocale)
		throws DDMFormValidationException {

		LocalizedValue propertyValue =
			(LocalizedValue)BeanPropertiesUtil.getObject(
				ddmFormField, propertyName);

		if (MapUtil.isEmpty(propertyValue.getValues())) {
			return;
		}

		validateDDMFormFieldPropertyValue(
			ddmFormField.getName(), propertyName, propertyValue,
			ddmFormAvailableLocales, ddmFormDefaultLocale);
	}

	private final String[] _ddmFormFieldIndexTypes = new String[] {
		StringPool.BLANK, "keyword", "text"
	};
	private final Pattern _ddmFormFieldNamePattern = Pattern.compile(
		"(\\w|_)+");
	private final Pattern _ddmFormFieldTypePattern = Pattern.compile(
		"(\\w|-|_)+");

}
