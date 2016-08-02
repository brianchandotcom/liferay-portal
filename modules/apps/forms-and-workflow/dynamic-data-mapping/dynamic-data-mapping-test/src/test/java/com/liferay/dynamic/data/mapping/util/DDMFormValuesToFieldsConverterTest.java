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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.impl.DDMFormValuesToFieldsConverterImpl;
import com.liferay.dynamic.data.mapping.util.impl.DDMImpl;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest(DDMStructureLocalServiceUtil.class)
@SuppressStaticInitializationFor(
	"com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil"
)
public class DDMFormValuesToFieldsConverterTest extends BaseDDMTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpConfigurationFactoryUtil();
		setUpAvailableLocales();
		setUpDDMFormJSONDeserializer();
		setUpDDMFormJSONSerializer();
		setUpDDMStructureLocalServiceUtil();
		setUpHtmlUtil();
		setUpJSONFactoryUtil();
		setUpLanguageUtil();
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

		Fields fields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, ddmFormValues);

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

	@Test
	public void testConversionWithEmptyField() throws Exception {
		DDMForm ddmForm = createDDMForm(
			createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US),
			LocaleUtil.US);

		DDMFormField ddmFormField = new DDMFormField(
			"Integer", DDMFormFieldType.INTEGER);

		ddmFormField.setDataType("integer");

		LocalizedValue label = new LocalizedValue(LocaleUtil.US);

		label.addString(LocaleUtil.BRAZIL, "Inteiro");
		label.addString(LocaleUtil.US, "Integer");

		ddmFormField.setLabel(label);

		ddmForm.addDDMFormField(ddmFormField);

		DDMStructure ddmStructure = createStructure("Test Structure", ddmForm);

		DDMFormValues ddmFormValues = createDDMFormValues(
			ddmForm, _availableLocales, LocaleUtil.US);

		DDMFormFieldValue integerDDMFormFieldValue = createDDMFormFieldValue(
			"rztm", "Integer",
			createLocalizedValue(
				StringPool.BLANK, StringPool.BLANK, LocaleUtil.US));

		ddmFormValues.addDDMFormFieldValue(integerDDMFormFieldValue);

		Fields fields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, ddmFormValues);

		Assert.assertNotNull(fields);

		Field integerField = fields.get("Integer");

		testField(
			integerField, createValuesList(""), createValuesList(""),
			_availableLocales, LocaleUtil.US);

		Field fieldsDisplayField = fields.get(DDMImpl.FIELDS_DISPLAY_NAME);

		Assert.assertEquals(
			"Integer_INSTANCE_rztm", fieldsDisplayField.getValue());
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
			"rght", "Name", createLocalizedValue("Joe", "Joao", LocaleUtil.US));

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

		Fields fields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, ddmFormValues);

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
				"Name_INSTANCE_rght,Phone_INSTANCE_latb," +
					"Phone_INSTANCE_jewp,Phone_INSTANCE_mkar",
			fieldsDisplayField.getValue());
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

		Fields fields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, ddmFormValues);

		Assert.assertNotNull(fields);

		Field nameField = fields.get("Name");

		testField(
			nameField, createValuesList("Name 1", "Name 2", "Name 3"),
			createValuesList("Nome 1", "Nome 2", "Nome 3"), _availableLocales,
			LocaleUtil.US);

		Field fieldsDisplayField = fields.get(DDMImpl.FIELDS_DISPLAY_NAME);

		Assert.assertEquals(
			"Name_INSTANCE_rztm,Name_INSTANCE_uayd,Name_INSTANCE_pamh",
			fieldsDisplayField.getValue());
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

		Fields fields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, ddmFormValues);

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

	@Test
	public void testConversionWithTransientField1() throws Exception {
		DDMForm ddmForm = createDDMForm();

		addDDMFormFields(
			ddmForm, createTextDDMFormField("Name", "", true, true, false));

		DDMStructure ddmStructure = createStructure("Test Structure", ddmForm);

		DDMForm templateDDMForm = createDDMForm();

		DDMFormField paragraphDDMFormField = createParagraphDDMFormField(
			"Paragraph");

		paragraphDDMFormField.addNestedDDMFormField(
			createTextDDMFormField("Name", "", true, true, false));

		addDDMFormFields(templateDDMForm, paragraphDDMFormField);

		DDMFormValues templateDDMFormValues = createDDMFormValues(
			templateDDMForm, _availableLocales, LocaleUtil.US);

		List<DDMFormFieldValue> ddmFormFieldValues =
			templateDDMFormValues.getDDMFormFieldValues();

		DDMFormFieldValue paragraphDDMFormFieldValue = createDDMFormFieldValue(
			"rztm", "Paragraph", null);

		DDMFormFieldValue nameDDMFormFieldValue1 = createDDMFormFieldValue(
			"uayd", "Name",
			createLocalizedValue("Name 1", "Nome 1", LocaleUtil.US));

		paragraphDDMFormFieldValue.addNestedDDMFormFieldValue(
			nameDDMFormFieldValue1);

		DDMFormFieldValue nameDDMFormFieldValue2 = createDDMFormFieldValue(
			"pamh", "Name",
			createLocalizedValue("Name 2", "Nome 2", LocaleUtil.US));

		paragraphDDMFormFieldValue.addNestedDDMFormFieldValue(
			nameDDMFormFieldValue2);

		ddmFormFieldValues.add(paragraphDDMFormFieldValue);

		Fields fields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, templateDDMFormValues);

		Assert.assertNotNull(fields);

		Field nameField = fields.get("Name");

		testField(
			nameField, createValuesList("Name 1", "Name 2"),
			createValuesList("Nome 1", "Nome 2"), _availableLocales,
			LocaleUtil.US);

		Field fieldsDisplayField = fields.get(DDMImpl.FIELDS_DISPLAY_NAME);

		Assert.assertEquals(
			"Paragraph_INSTANCE_rztm,Name_INSTANCE_uayd,Name_INSTANCE_pamh",
			fieldsDisplayField.getValue());
	}

	@Test
	public void testConversionWithTransientField2() throws Exception {
		DDMForm ddmForm = createDDMForm();

		addDDMFormFields(
			ddmForm, createTextDDMFormField("Name", "", true, false, false),
			createTextDDMFormField("Phone", "", true, true, false));

		DDMStructure ddmStructure = createStructure("Test Structure", ddmForm);

		DDMForm templateDDMForm = createDDMForm();

		DDMFormField separatorDDMFormField = createSeparatorDDMFormField(
			"Separator", true);

		separatorDDMFormField.addNestedDDMFormField(
			createTextDDMFormField("Name", "", true, false, false));

		addDDMFormFields(
			templateDDMForm, separatorDDMFormField,
			createTextDDMFormField("Phone", "", true, true, false));

		DDMFormValues templateDDMFormValues = createDDMFormValues(
			templateDDMForm, _availableLocales, LocaleUtil.US);

		List<DDMFormFieldValue> ddmFormFieldValues =
			templateDDMFormValues.getDDMFormFieldValues();

		DDMFormFieldValue separatorDDMFormFieldValue1 = createDDMFormFieldValue(
			"rztm", "Separator", null);

		DDMFormFieldValue nameDDMFormFieldValue1 = createDDMFormFieldValue(
			"uayd", "Name",
			createLocalizedValue("Name 1", "Nome 1", LocaleUtil.US));

		separatorDDMFormFieldValue1.addNestedDDMFormFieldValue(
			nameDDMFormFieldValue1);

		ddmFormFieldValues.add(separatorDDMFormFieldValue1);

		DDMFormFieldValue separatorDDMFormFieldValue2 = createDDMFormFieldValue(
			"abpg", "Separator", null);

		DDMFormFieldValue nameDDMFormFieldValue2 = createDDMFormFieldValue(
			"pamh", "Name",
			createLocalizedValue("Name 2", "Nome 2", LocaleUtil.US));

		separatorDDMFormFieldValue2.addNestedDDMFormFieldValue(
			nameDDMFormFieldValue2);

		ddmFormFieldValues.add(separatorDDMFormFieldValue2);

		DDMFormFieldValue phoneDDMFormFieldValue1 = createDDMFormFieldValue(
			"prft", "Phone",
			createLocalizedValue("Phone 1", "Telefone 1", LocaleUtil.US));

		ddmFormFieldValues.add(phoneDDMFormFieldValue1);

		DDMFormFieldValue phoneDDMFormFieldValue2 = createDDMFormFieldValue(
			"goik", "Phone",
			createLocalizedValue("Phone 2", "Telefone 2", LocaleUtil.US));

		ddmFormFieldValues.add(phoneDDMFormFieldValue2);

		Fields fields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, templateDDMFormValues);

		Assert.assertNotNull(fields);

		Field nameField = fields.get("Name");

		testField(
			nameField, createValuesList("Name 1", "Name 2"),
			createValuesList("Nome 1", "Nome 2"), _availableLocales,
			LocaleUtil.US);

		Field phoneField = fields.get("Phone");

		testField(
			phoneField, createValuesList("Phone 1", "Phone 2"),
			createValuesList("Telefone 1", "Telefone 2"), _availableLocales,
			LocaleUtil.US);

		Field fieldsDisplayField = fields.get(DDMImpl.FIELDS_DISPLAY_NAME);

		Assert.assertEquals(
			"Separator_INSTANCE_rztm,Name_INSTANCE_uayd," +
				"Separator_INSTANCE_abpg,Name_INSTANCE_pamh," +
					"Phone_INSTANCE_prft,Phone_INSTANCE_goik",
			fieldsDisplayField.getValue());
	}

	@Override
	protected List<Serializable> createValuesList(String... valuesString) {
		List<Serializable> values = new ArrayList<>();

		for (String valueString : valuesString) {
			values.add(valueString);
		}

		return values;
	}

	protected void setUpAvailableLocales() {
		_availableLocales = new LinkedHashSet<>();

		_availableLocales.add(LocaleUtil.BRAZIL);
		_availableLocales.add(LocaleUtil.US);
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
	private final DDMFormValuesToFieldsConverter
		_ddmFormValuesToFieldsConverter =
			new DDMFormValuesToFieldsConverterImpl();

}