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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.BaseDDMTest;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Pablo Carvalho
 */
public class BaseDDMFormSerializer extends BaseDDMTest {

	protected List<Locale> createAvailableLocales() {
		List<Locale> availableLocales = new ArrayList<Locale>();

		availableLocales.add(LocaleUtil.BRAZIL);
		availableLocales.add(LocaleUtil.US);

		return availableLocales;
	}

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(createAvailableLocales());
		ddmForm.setDDMFormFields(createDDMFormFields());
		ddmForm.setDefaultLocale(LocaleUtil.US);

		return ddmForm;
	}

	protected DDMFormFieldOptions createDDMFormFieldOptions() {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOption("Value 1");

		ddmFormFieldOptions.addOptionLabel(
			"Value 1", LocaleUtil.BRAZIL, "Opcao 1");
		ddmFormFieldOptions.addOptionLabel(
			"Value 1", LocaleUtil.US, "Option 1");

		ddmFormFieldOptions.addOption("Value 2");

		ddmFormFieldOptions.addOptionLabel(
			"Value 2", LocaleUtil.BRAZIL, "Opcao 2");
		ddmFormFieldOptions.addOptionLabel(
			"Value 2", LocaleUtil.US, "Option 2");

		return ddmFormFieldOptions;
	}

	protected List<DDMFormField> createDDMFormFields() {
		List<DDMFormField> ddmFormFields = new ArrayList<DDMFormField>();

		ddmFormFields.add(
			createNestedDDMFormFields("ParentField", "ChildField"));
		ddmFormFields.add(createRadioDDMFormField("BooleanField"));
		ddmFormFields.add(createSelectDDMFormField("SelectField"));
		ddmFormFields.add(createTextDDMFormField("TextField"));

		return ddmFormFields;
	}

	protected DDMFormField createNestedDDMFormFields(
		String parentName, String childName) {

		DDMFormField parentDDMFormField = createTextDDMFormField(parentName);

		List<DDMFormField> nestedDDMFormFields = new ArrayList<DDMFormField>();

		nestedDDMFormFields.add(createSelectDDMFormField(childName));

		parentDDMFormField.setNestedDDMFormFields(nestedDDMFormFields);

		return parentDDMFormField;
	}

	protected DDMFormField createRadioDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "radio");

		ddmFormField.setDataType("string");
		ddmFormField.setDDMFormFieldOptions(createDDMFormFieldOptions());
		ddmFormField.setRequired(true);

		return ddmFormField;
	}

	protected DDMFormField createSelectDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "select");

		ddmFormField.setDataType("string");
		ddmFormField.setIndexType("");
		ddmFormField.setMultiple(true);

		DDMFormFieldOptions ddmFormFieldOptions = createDDMFormFieldOptions();

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		return ddmFormField;
	}

	protected DDMFormField createTextDDMFormField(String name) {
		DDMFormField ddmFormField = new DDMFormField(name, "text");

		ddmFormField.setDataType("string");
		ddmFormField.setIndexType("keyword");
		ddmFormField.setLabel(createTextDDMFormFieldLabel());
		ddmFormField.setPredefinedValue(
			createTextDDMFormFieldPredefinedValue());
		ddmFormField.setRepeatable(true);

		return ddmFormField;
	}

	protected LocalizedValue createTextDDMFormFieldLabel() {
		LocalizedValue label = new LocalizedValue();

		label.addValue(LocaleUtil.BRAZIL, "Texto");
		label.addValue(LocaleUtil.US, "Text");

		return label;
	}

	protected LocalizedValue createTextDDMFormFieldPredefinedValue() {
		LocalizedValue predefinedValue = new LocalizedValue();

		predefinedValue.addValue(LocaleUtil.BRAZIL, "Exemplo");
		predefinedValue.addValue(LocaleUtil.US, "Example");

		return predefinedValue;
	}

}