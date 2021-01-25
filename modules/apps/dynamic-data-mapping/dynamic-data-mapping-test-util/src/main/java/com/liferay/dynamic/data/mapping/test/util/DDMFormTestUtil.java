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

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMFormTestUtil {

	public static void addDDMFormFields(
		DDMForm ddmForm, DDMFormField... ddmFormFieldsArray) {

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFieldsArray) {
			ddmFormFields.add(ddmFormField);
		}
	}

	public static DDMFormField addDocumentLibraryDDMFormField(
		DDMForm ddmForm, String fieldName) {

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		DDMFormField ddmFormField = createDDMFormField(
			fieldName, RandomTestUtil.randomString(),
			DDMFormFieldTypeConstants.DOCUMENT_LIBRARY, "document-library",
			true, false, true);

		ddmFormField.setDDMForm(ddmForm);
		ddmFormField.setLocalizable(true);
		ddmFormField.setFieldNamespace("ddm");

		ddmFormFields.add(ddmFormField);

		return ddmFormField;
	}

	public static void addNestedTextDDMFormFields(
		DDMFormField ddmFormField, String... fieldNames) {

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		for (String fieldName : fieldNames) {
			nestedDDMFormFields.add(
				createLocalizableTextDDMFormField(fieldName));
		}
	}

	public static void addTextDDMFormFields(
		DDMForm ddmForm, String... fieldNames) {

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (String fieldName : fieldNames) {
			DDMFormField ddmFormField = createLocalizableTextDDMFormField(
				fieldName);

			ddmFormField.setDDMForm(ddmForm);

			ddmFormFields.add(ddmFormField);
		}
	}

	public static Set<Locale> createAvailableLocales(Locale... locales) {
		Set<Locale> availableLocales = new LinkedHashSet<>();

		for (Locale locale : locales) {
			availableLocales.add(locale);
		}

		return availableLocales;
	}

	public static DDMForm createDDMForm(
		Set<Locale> availableLocales, Locale defaultLocale) {

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(availableLocales);
		ddmForm.setDefaultLocale(defaultLocale);

		return ddmForm;
	}

	public static DDMForm createDDMForm(String... fieldNames) {
		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		addTextDDMFormFields(ddmForm, fieldNames);

		return ddmForm;
	}

	public static DDMFormField createDDMFormField(
		String name, String label, String type, String dataType,
		boolean localizable, boolean repeatable, boolean required) {

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType(dataType);
		ddmFormField.setFieldReference(name);
		ddmFormField.setLocalizable(localizable);
		ddmFormField.setRepeatable(repeatable);
		ddmFormField.setRequired(required);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, label);

		return ddmFormField;
	}

	public static DDMFormField createDDMFormField(
		String name, String label, String type, String dataType,
		boolean localizable, boolean repeatable, boolean required, String tip) {

		return createDDMFormField(
			name, label, type, dataType, localizable, repeatable, required, tip,
			null);
	}

	public static DDMFormField createDDMFormField(
		String name, String label, String type, String dataType,
		boolean localizable, boolean repeatable, boolean required, String tip,
		String option) {

		return createDDMFormField(
			name, label, type, dataType, localizable, repeatable, required, tip,
			null, option);
	}

	public static DDMFormField createDDMFormField(
		String name, String label, String type, String dataType,
		boolean localizable, boolean repeatable, boolean required, String tip,
		String predefinedValue, String option) {

		return createDDMFormField(
			name, label, type, dataType, localizable, repeatable, required, tip,
			predefinedValue, null, null, option);
	}

	public static DDMFormField createDDMFormField(
		String name, String label, String type, String dataType,
		boolean localizable, boolean repeatable, boolean required, String tip,
		String predefinedValue, String placeHolder, String toolTip,
		String option) {

		DDMFormField ddmFormField = createDDMFormField(
			name, label, type, dataType, localizable, repeatable, required);

		LocalizedValue tipLocalizedValue =
			DDMFormValuesTestUtil.createLocalizedValue(tip, LocaleUtil.US);

		ddmFormField.setTip(tipLocalizedValue);

		LocalizedValue predefinedValueLocalizedValue =
			DDMFormValuesTestUtil.createLocalizedValue(
				predefinedValue, LocaleUtil.US);

		ddmFormField.setPredefinedValue(predefinedValueLocalizedValue);

		LocalizedValue placeHolderLocalizedValue =
			DDMFormValuesTestUtil.createLocalizedValue(
				placeHolder, LocaleUtil.US);

		ddmFormField.setProperty("placeholder", placeHolderLocalizedValue);

		LocalizedValue toolTipLocalizedValue =
			DDMFormValuesTestUtil.createLocalizedValue(toolTip, LocaleUtil.US);

		ddmFormField.setProperty("tooltip", toolTipLocalizedValue);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOptionLabel(option, LocaleUtil.US, option);

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, label);

		return ddmFormField;
	}

	public static DDMFormField createGridDDMFormField(
		String name, String label, String type, String dataType,
		boolean localizable, boolean repeatable, boolean required, String tip,
		String option) {

		DDMFormField ddmFormField = createDDMFormField(
			name, label, type, dataType, localizable, repeatable, required, tip,
			null, null, null, option);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormField.setProperty("columns", ddmFormFieldOptions);

		ddmFormField.setProperty("rows", ddmFormFieldOptions);

		return ddmFormField;
	}

	public static DDMFormField createLocalizableTextDDMFormField(String name) {
		return createTextDDMFormField(name, true, false, false);
	}

	public static DDMFormField createNumericDDMFormField(
		String name, String label, String dataType, boolean localizable,
		boolean repeatable, boolean required, String tip, String placeHolder,
		String toolTip) {

		return createDDMFormField(
			name, label, "numeric", dataType, localizable, repeatable, required,
			tip, null, placeHolder, toolTip, null);
	}

	public static DDMFormField createSeparatorDDMFormField(
		String name, boolean repeatable) {

		DDMFormField ddmFormField = new DDMFormField(name, "separator");

		ddmFormField.setRepeatable(repeatable);

		LocalizedValue localizedValue = ddmFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, name);

		return ddmFormField;
	}

	public static DDMFormField createTextDDMFormField(
		String name, boolean localizable, boolean repeatable,
		boolean required) {

		return createDDMFormField(
			name, name, "text", "string", localizable, repeatable, required);
	}

	public static DDMFormField createTextDDMFormField(
		String name, String label, boolean localizable, boolean repeatable,
		boolean required) {

		return createDDMFormField(
			name, label, "text", "string", localizable, repeatable, required);
	}

	public static void setIndexTypeProperty(
		DDMForm ddmForm, String indexTypeValue) {

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			ddmFormField.setIndexType(indexTypeValue);
		}
	}

}