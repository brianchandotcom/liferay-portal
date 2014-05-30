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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.xml.SAXReaderImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Pablo Carvalho
 */
@PrepareForTest( {
	DDMFormXSDSerializerUtil.class, LocaleUtil.class, SAXReaderUtil.class,
	StringUtil.class
})
@RunWith(PowerMockRunner.class)
public class DDMFormXSDSerializerTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpFormToXSDConverter();
		setUpLocale();
		setUpSAXReader();
		setUpStringUtil();
	}

	@Test
	public void testDDMFormSerialization() throws Exception {
		DDMForm ddmForm = createTestDDMForm();

		String xsd = DDMFormXSDSerializerUtil.serialize(ddmForm);

		testXSDMatchesExpected(xsd);
	}

	protected List<Locale> createAvailableLocales() {
		List<Locale> availableLocales = new ArrayList<Locale>();

		availableLocales.add(LocaleUtil.BRAZIL);
		availableLocales.add(LocaleUtil.US);

		return availableLocales;
	}

	protected DDMFormFieldOptions createDDMFormFieldOptions() {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.addOption("value 1");

		ddmFormFieldOptions.addOptionLabel(
			"value 1", LocaleUtil.BRAZIL, "opcao 1");
		ddmFormFieldOptions.addOptionLabel(
			"value 1", LocaleUtil.US, "option 1");

		ddmFormFieldOptions.addOption("value 2");

		ddmFormFieldOptions.addOptionLabel(
			"value 2", LocaleUtil.BRAZIL, "opcao 2");
		ddmFormFieldOptions.addOptionLabel(
			"value 2", LocaleUtil.US, "option 2");

		return ddmFormFieldOptions;
	}

	protected List<DDMFormField> createDDMFormFields() {
		List<DDMFormField> ddmFormFields = new ArrayList<DDMFormField>();

		ddmFormFields.add(createRadioDDMFormField(BOOLEAN_FIELD_NAME));
		ddmFormFields.add(
			createNestedDDMFormFields(PARENT_FIELD_NAME, CHILD_FIELD_NAME));
		ddmFormFields.add(createSelectDDMFormField(SELECT_FIELD_NAME));
		ddmFormFields.add(createTextDDMFormField(TEXT_FIELD_NAME));

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
		DDMFormField radioDDMFormField = new DDMFormField(name, "radio");

		radioDDMFormField.setDataType("string");
		radioDDMFormField.setDDMFormFieldOptions(createDDMFormFieldOptions());
		radioDDMFormField.setRequired(true);

		return radioDDMFormField;
	}

	protected DDMFormField createSelectDDMFormField(String name) {

		DDMFormField selectDDMFormField = new DDMFormField(name, "select");

		selectDDMFormField.setDataType("string");
		selectDDMFormField.setIndexType("");
		selectDDMFormField.setMultiple(true);

		DDMFormFieldOptions ddmFormFieldOptions = createDDMFormFieldOptions();

		selectDDMFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		return selectDDMFormField;
	}

	protected DDMForm createTestDDMForm() {
		List<Locale> availableLocales = createAvailableLocales();
		List<DDMFormField> ddmFormFields = createDDMFormFields();
		Locale defaultLocale = LocaleUtil.US;

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(availableLocales);
		ddmForm.setDDMFormFields(ddmFormFields);
		ddmForm.setDefaultLocale(defaultLocale);

		return ddmForm;
	}

	protected DDMFormField createTextDDMFormField(String name) {
		DDMFormField textDDMFormField = new DDMFormField(name, "text");

		textDDMFormField.setDataType("string");
		textDDMFormField.setIndexType("keyword");
		textDDMFormField.setLabel(createTextDDMFormFieldLabel());
		textDDMFormField.setPredefinedValue(
			createTextDDMFormFieldPredefinedValue());
		textDDMFormField.setRepeatable(true);

		return textDDMFormField;
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

	protected String readXML(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected void setUpFormToXSDConverter() {
		spy(DDMFormXSDSerializerUtil.class);

		when(
			DDMFormXSDSerializerUtil.getDDMFormXSDSerializer()
		).thenReturn(
			_ddmFormXSDSerializer
		);
	}

	protected void setUpLocale() {
		spy(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId("en_US")
		).thenReturn(
			LocaleUtil.US
		);

		when(
			LocaleUtil.fromLanguageId("pt_BR")
		).thenReturn(
			LocaleUtil.BRAZIL
		);
	}

	protected void setUpSAXReader() {
		spy(SAXReaderUtil.class);

		when(
			SAXReaderUtil.getSAXReader()
		).thenReturn(
			new SAXReaderImpl()
		);
	}

	protected void setUpStringUtil() {
		spy(StringUtil.class);

		when(
			StringUtil.randomId()
		).thenReturn(
			"1234"
		);
	}

	protected void testXSDMatchesExpected(String xsd) throws Exception {
		String expectedXSD = readXML("ddm-form-xsd-serializer-test-data.xml");

		Document document = SAXReaderUtil.read(xsd);

		String actualXSD = document.formattedString();

		Assert.assertEquals(expectedXSD, actualXSD);
	}

	private static final String BOOLEAN_FIELD_NAME = "BooleanField";

	private static final String CHILD_FIELD_NAME = "ChildField";

	private static final String PARENT_FIELD_NAME = "ParentField";

	private static final String SELECT_FIELD_NAME = "SelectField";

	private static final String TEXT_FIELD_NAME = "TextField";

	private DDMFormXSDSerializer _ddmFormXSDSerializer =
		new DDMFormXSDSerializerImpl();

}