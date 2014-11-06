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

import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.BaseDDMTestCase;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest({DDMStructureLocalServiceUtil.class})
public class DDMFormValuesToFieldsConverterTest extends BaseDDMTestCase {

	@Before
	public void setUp() throws Exception {
		setUpAvailableLocales();
		setUpDDMFormValuesToFieldsConverterUtil();
		setUpDDMFormXSDDeserializerUtil();
		setUpDDMFormXSDSerializerUtil();
		setUpDDMStructureLocalServiceUtil();
		setUpHtmlUtil();
		setUpPropsUtil();
		setUpSAXReaderUtil();
	}

	@Test
	public void testConversionWithBooleanField() throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMFormField booleanDDMFormField = new DDMFormField(
			"Boolean", "checkbox");

		booleanDDMFormField.setDataType("boolean");

		LocalizedValue localizedValue = booleanDDMFormField.getLabel();

		localizedValue.addString(LocaleUtil.US, "Boolean Field");

		addDDMFormFields(ddmForm, booleanDDMFormField);

		DDMStructure ddmStructure = createStructure("Test Structure", ddmForm);

		DDMFormValues ddmFormValues = createDDMFormValues(
			ddmForm, _availableLocales, LocaleUtil.US);

		DDMFormFieldValue titleDDMFormFieldValue = createDDMFormFieldValue(
			"rztm", "Boolean", new UnlocalizedValue("true"));

		ddmFormValues.addDDMFormFieldValue(titleDDMFormFieldValue);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			LocaleUtil.class.getName(), Level.WARNING);

		try {
			Fields fields = DDMFormValuesToFieldsConverterUtil.convert(
				ddmStructure, ddmFormValues);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(3, logRecords.size());

			for (LogRecord logRecord : logRecords) {
				Assert.assertEquals(
					"en_US is not a valid language id", logRecord.getMessage());
			}

			Assert.assertNotNull(fields);

			Field field = fields.get("Boolean");

			Serializable value = field.getValue();

			Class<?> clazz = value.getClass();

			Assert.assertEquals(true, clazz.isAssignableFrom(Boolean.class));
			Assert.assertEquals(true, value);

			Field fieldsDisplayField = fields.get(DDMImpl.FIELDS_DISPLAY_NAME);

			Assert.assertEquals(
				"Boolean_INSTANCE_rztm", fieldsDisplayField.getValue());
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testConversionWithNestedFields() throws Exception {
		DDMForm ddmForm = createDDMForm();

		DDMFormField nameDDMFormField = createTextDDMFormField("Name");

		List<DDMFormField> nestedNameDDMFormFields =
			nameDDMFormField.getNestedDDMFormFields();

		nestedNameDDMFormFields.add(createTextDDMFormField("Phone"));

		addDDMFormFields(ddmForm, nameDDMFormField);

		DDMStructure ddmStructure = createStructure("Test Structure", ddmForm);

		DDMFormValues ddmFormValues = createDDMFormValues(
			ddmForm, _availableLocales, LocaleUtil.US);

		DDMFormFieldValue paulDDMFormFieldValue = createDDMFormFieldValue(
			"rztm", "Name",
			createLocalizedValue("Paul", "Paulo", LocaleUtil.US));

		List<DDMFormFieldValue> paulNestedDDMFormFieldValue =
			paulDDMFormFieldValue.getNestedDDMFormFieldValues();

		paulNestedDDMFormFieldValue.add(
			createDDMFormFieldValue(
				"ovho", "Phone",
				createLocalizedValue(
					"Paul's Phone 1", "Telefone de Paulo 1", LocaleUtil.US)));

		paulNestedDDMFormFieldValue.add(
			createDDMFormFieldValue(
				"krvx", "Phone",
				createLocalizedValue(
					"Paul's Phone 2", "Telefone de Paulo 2", LocaleUtil.US)));

		ddmFormValues.addDDMFormFieldValue(paulDDMFormFieldValue);

		DDMFormFieldValue joeDDMFormFieldValue = createDDMFormFieldValue(
			"rght", "Name",
			createLocalizedValue("Joe", "Joao", LocaleUtil.US));

		List<DDMFormFieldValue> joeNestedDDMFormFieldValue =
			joeDDMFormFieldValue.getNestedDDMFormFieldValues();

		joeNestedDDMFormFieldValue.add(
			createDDMFormFieldValue(
				"latb", "Phone",
				createLocalizedValue(
					"Joe's Phone 1", "Telefone de Joao 1", LocaleUtil.US)));

		joeNestedDDMFormFieldValue.add(
			createDDMFormFieldValue(
				"jewp", "Phone",
				createLocalizedValue(
					"Joe's Phone 2", "Telefone de Joao 2", LocaleUtil.US)));

		joeNestedDDMFormFieldValue.add(
			createDDMFormFieldValue(
				"mkar", "Phone",
				createLocalizedValue(
					"Joe's Phone 3", "Telefone de Joao 3", LocaleUtil.US)));

		ddmFormValues.addDDMFormFieldValue(joeDDMFormFieldValue);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			LocaleUtil.class.getName(), Level.WARNING);

		try {
			Fields fields = DDMFormValuesToFieldsConverterUtil.convert(
				ddmStructure, ddmFormValues);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(4, logRecords.size());

			for (LogRecord logRecord : logRecords) {
				Assert.assertEquals(
					"en_US is not a valid language id", logRecord.getMessage());
			}

			Assert.assertNotNull(fields);

			Field nameField = fields.get("Name");

			testField(
				nameField, createValuesList("Paul", "Joe"),
				createValuesList("Paulo", "Joao"), _availableLocales,
				LocaleUtil.US);

			Field phoneField = fields.get("Phone");

			testField(
				phoneField,
				createValuesList(
					"Paul's Phone 1", "Paul's Phone 2", "Joe's Phone 1",
					"Joe's Phone 2", "Joe's Phone 3"),
				createValuesList(
					"Telefone de Paulo 1", "Telefone de Paulo 2",
					"Telefone de Joao 1", "Telefone de Joao 2",
					"Telefone de Joao 3"),
				_availableLocales, LocaleUtil.US);

			Field fieldsDisplayField = fields.get(DDMImpl.FIELDS_DISPLAY_NAME);

			Assert.assertEquals(
				"Name_INSTANCE_rztm,Phone_INSTANCE_ovho,Phone_INSTANCE_krvx," +
				"Name_INSTANCE_rght,Phone_INSTANCE_latb,Phone_INSTANCE_jewp," +
				"Phone_INSTANCE_mkar", fieldsDisplayField.getValue());
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testConversionWithRepeatableField() throws Exception {
		DDMForm ddmForm = createDDMForm();

		addDDMFormFields(
			ddmForm, createTextDDMFormField("Name", "", true, true, false));

		DDMStructure ddmStructure = createStructure("Test Structure", ddmForm);

		DDMFormValues ddmFormValues = createDDMFormValues(
			ddmForm, _availableLocales, LocaleUtil.US);

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		DDMFormFieldValue nameDDMFormFieldValue1 = createDDMFormFieldValue(
			"rztm", "Name",
			createLocalizedValue("Name 1", "Nome 1", LocaleUtil.US));

		ddmFormFieldValues.add(nameDDMFormFieldValue1);

		DDMFormFieldValue nameDDMFormFieldValue2 = createDDMFormFieldValue(
			"uayd", "Name",
			createLocalizedValue("Name 2", "Nome 2", LocaleUtil.US));

		ddmFormFieldValues.add(nameDDMFormFieldValue2);

		DDMFormFieldValue nameDDMFormFieldValue3 = createDDMFormFieldValue(
			"pamh", "Name",
			createLocalizedValue("Name 3", "Nome 3", LocaleUtil.US));

		ddmFormFieldValues.add(nameDDMFormFieldValue3);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			LocaleUtil.class.getName(), Level.WARNING);

		try {
			Fields fields = DDMFormValuesToFieldsConverterUtil.convert(
				ddmStructure, ddmFormValues);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(3, logRecords.size());

			for (LogRecord logRecord : logRecords) {
				Assert.assertEquals(
					"en_US is not a valid language id", logRecord.getMessage());
			}

			Assert.assertNotNull(fields);

			Field nameField = fields.get("Name");

			testField(
				nameField, createValuesList("Name 1", "Name 2", "Name 3"),
				createValuesList(
					"Nome 1", "Nome 2", "Nome 3"), _availableLocales,
					LocaleUtil.US);

			Field fieldsDisplayField = fields.get(DDMImpl.FIELDS_DISPLAY_NAME);

			Assert.assertEquals(
				"Name_INSTANCE_rztm,Name_INSTANCE_uayd,Name_INSTANCE_pamh",
				fieldsDisplayField.getValue());
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testConversionWithTextField() throws Exception {
		DDMForm ddmForm = createDDMForm();

		addDDMFormFields(
			ddmForm, createTextDDMFormField("Title"),
			createTextDDMFormField("Content"));

		DDMStructure ddmStructure = createStructure("Test Structure", ddmForm);

		DDMFormValues ddmFormValues = createDDMFormValues(
			ddmForm, _availableLocales, LocaleUtil.US);

		DDMFormFieldValue titleDDMFormFieldValue = createDDMFormFieldValue(
			"rztm", "Title",
			createLocalizedValue(
				"Title Example", "Titulo Exemplo", LocaleUtil.US));

		ddmFormValues.addDDMFormFieldValue(titleDDMFormFieldValue);

		DDMFormFieldValue contentDDMFormFieldValue = createDDMFormFieldValue(
			"ovho", "Content",
			createLocalizedValue(
				"Content Example", "Conteudo Exemplo", LocaleUtil.US));

		ddmFormValues.addDDMFormFieldValue(contentDDMFormFieldValue);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			LocaleUtil.class.getName(), Level.WARNING);

		try {
			Fields fields = DDMFormValuesToFieldsConverterUtil.convert(
				ddmStructure, ddmFormValues);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(4, logRecords.size());

			for (LogRecord logRecord : logRecords) {
				Assert.assertEquals(
					"en_US is not a valid language id", logRecord.getMessage());
			}

			Assert.assertNotNull(fields);

			Field titleField = fields.get("Title");

			testField(
				titleField, createValuesList("Title Example"),
				createValuesList("Titulo Exemplo"), _availableLocales,
				LocaleUtil.US);

			Field contentField = fields.get("Content");

			testField(
				contentField, createValuesList("Content Example"),
				createValuesList("Conteudo Exemplo"), _availableLocales,
				LocaleUtil.US);

			Field fieldsDisplayField = fields.get(DDMImpl.FIELDS_DISPLAY_NAME);

			Assert.assertEquals(
				"Title_INSTANCE_rztm,Content_INSTANCE_ovho",
				fieldsDisplayField.getValue());
		}
		finally {
			captureHandler.close();
		}
	}

	@Override
	protected List<Serializable> createValuesList(String... valuesString) {
		List<Serializable> values = new ArrayList<Serializable>();

		for (String valueString : valuesString) {
			values.add(valueString);
		}

		return values;
	}

	protected void setUpAvailableLocales() {
		_availableLocales = new LinkedHashSet<Locale>();

		_availableLocales.add(LocaleUtil.BRAZIL);
		_availableLocales.add(LocaleUtil.US);
	}

	protected void setUpDDMFormValuesToFieldsConverterUtil() {
		DDMFormValuesToFieldsConverterUtil ddmFormValuesToFieldsConverterUtil =
			new DDMFormValuesToFieldsConverterUtil();

		ddmFormValuesToFieldsConverterUtil.setDDMFormValuesToFieldsConverter(
			new DDMFormValuesToFieldsConverterImpl());
	}

	protected void testField(
		Field field, List<Serializable> expectedEnValues,
		List<Serializable> expectedPtValues,
		Set<Locale> expectedAvailableLocales, Locale expectedDefaultLocale) {

		Assert.assertNotNull(field);
		Assert.assertEquals(
			expectedAvailableLocales, field.getAvailableLocales());
		Assert.assertEquals(expectedEnValues, field.getValues(LocaleUtil.US));
		Assert.assertEquals(
			expectedPtValues, field.getValues(LocaleUtil.BRAZIL));
	}

	private Set<Locale> _availableLocales;

}