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

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.xml.SAXReaderImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
	DDMFormXSDSerializerUtil.class, HtmlUtil.class, LocaleUtil.class,
	SAXReaderUtil.class
})
@RunWith(PowerMockRunner.class)
public class DDMFormXSDSerializerTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpDDMFormXSDSerializer();
		setUpHtml();
		setUpLocale();
		setUpSAXReader();
	}

	@Test
	public void testAllFieldsTypesDeserialization() throws Exception {
		String xsd = readXML("dynamic-data-mapping-all-fields-structure.xml");

		DDMForm ddmForm = DDMFormXSDSerializerUtil.deserialize(xsd);

		checkExpectedDefaultLanguage(ddmForm);

		checkExpectedAvailableLanguages(ddmForm);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		checkBooleanDDMFormField(ddmFormFieldsMap.get("Boolean2282"));

		checkDateDDMFormField(ddmFormFieldsMap.get("Date2510"));

		checkDecimalDDMFormField(ddmFormFieldsMap.get("Decimal3479"));

		checkDocumentLibraryDDMFormField(
			ddmFormFieldsMap.get("Documents_and_Media4036"));

		checkRadioDDMFormField(ddmFormFieldsMap.get("Radio5699"));

		checkNestedDDMFormFields(ddmFormFieldsMap.get("Text6980"));
	}

	protected void checkBooleanDDMFormField(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertEquals("boolean", ddmFormField.getDataType());
		Assert.assertEquals("keyword", ddmFormField.getIndexType());

		Assert.assertFalse(ddmFormField.isRepeatable());
		Assert.assertFalse(ddmFormField.isRequired());

		Assert.assertEquals("checkbox", ddmFormField.getType());

		LocalizedValue label = ddmFormField.getLabel();

		Assert.assertEquals("Boolean", label.getValue(LocaleUtil.US));
		Assert.assertEquals("Booleano", label.getValue(LocaleUtil.BRAZIL));

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		Assert.assertEquals("false", predefinedValue.getValue(LocaleUtil.US));
	}

	protected void checkDateDDMFormField(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertEquals("date", ddmFormField.getDataType());
		Assert.assertEquals("ddm-date", ddmFormField.getType());
	}

	protected void checkDecimalDDMFormField(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertEquals("double", ddmFormField.getDataType());
		Assert.assertEquals("ddm-decimal", ddmFormField.getType());
	}

	protected void checkDocumentLibraryDDMFormField(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertEquals("document-library", ddmFormField.getDataType());
		Assert.assertEquals("ddm-documentlibrary", ddmFormField.getType());
	}

	protected void checkExpectedAvailableLanguages(DDMForm ddmForm) {
		List<Locale> availableLocales = ddmForm.getAvailableLocales();

		Assert.assertEquals(2, availableLocales.size());

		Assert.assertTrue(availableLocales.contains(LocaleUtil.US));
		Assert.assertTrue(availableLocales.contains(LocaleUtil.BRAZIL));
	}

	protected void checkExpectedDefaultLanguage(DDMForm ddmForm) {
		Locale defaultLocale = ddmForm.getDefaultLocale();

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		Assert.assertEquals("en_US", defaultLanguageId);
	}

	protected void checkNestedDDMFormFields(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		List<DDMFormField> nestedDDMFormFields =
			ddmFormField.getNestedDDMFormFields();

		Assert.assertEquals(1, nestedDDMFormFields.size());

		DDMFormField separatorDDMFormField = nestedDDMFormFields.get(0);

		Assert.assertEquals("Separator7211", separatorDDMFormField.getName());

		nestedDDMFormFields = separatorDDMFormField.getNestedDDMFormFields();

		Assert.assertEquals(1, nestedDDMFormFields.size());

		DDMFormField selectDDMFormField = nestedDDMFormFields.get(0);

		Assert.assertEquals("Select7450", selectDDMFormField.getName());

		DDMFormFieldOptions selectDDMFormFieldOptions =
			selectDDMFormField.getDDMFormFieldOptions();

		Set<String> optionValues = selectDDMFormFieldOptions.getOptionsValues();

		Assert.assertEquals(3, optionValues.size());
	}

	protected void checkRadioDDMFormField(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);

		Assert.assertEquals("string", ddmFormField.getDataType());
		Assert.assertEquals("radio", ddmFormField.getType());

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		Assert.assertEquals(
			"[\"value 1\"]", predefinedValue.getValue(LocaleUtil.US));

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

		Assert.assertEquals(3, optionsValues.size());

		Assert.assertTrue(optionsValues.contains("value 1"));
		Assert.assertTrue(optionsValues.contains("value 2"));
		Assert.assertTrue(optionsValues.contains("value 3"));

		LocalizedValue value1Labels = ddmFormFieldOptions.getOptionLabels(
			"value 1");

		Assert.assertEquals("option 1", value1Labels.getValue(LocaleUtil.US));
		Assert.assertEquals(
			"opcao 1", value1Labels.getValue(LocaleUtil.BRAZIL));
	}

	protected String readXML(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected void setUpDDMFormXSDSerializer() {
		spy(DDMFormXSDSerializerUtil.class);

		when(
			DDMFormXSDSerializerUtil.getDDMFormXSDSerializer()
		).thenReturn(
			_ddmFormXSDSerializer
		);
	}

	protected void setUpHtml() {
		spy(HtmlUtil.class);

		when(
			HtmlUtil.getHtml()
		).thenReturn(
			new HtmlImpl()
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

	private DDMFormXSDSerializer _ddmFormXSDSerializer =
		new DDMFormXSDSerializerImpl();

}