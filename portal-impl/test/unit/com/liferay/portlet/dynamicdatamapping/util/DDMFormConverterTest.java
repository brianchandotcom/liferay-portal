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
	DDMFormConverterUtil.class, HtmlUtil.class, LocaleUtil.class,
	SAXReaderUtil.class
})
@RunWith(PowerMockRunner.class)
public class DDMFormConverterTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		setUpDDMConverter();
		setUpHtml();
		setUpLocale();
		setUpSAXReader();
	}

	@Test
	public void testAllFieldsStructureConversion() throws Exception {
		String xsd = readXML("dynamic-data-mapping-all-fields-structure.xml");

		DDMForm form = DDMFormConverterUtil.getForm(xsd);

		checkExpectedDefaultLanguage(form);

		checkExpectedAvailableLanguages(form);

		Map<String, DDMFormField> fieldsMap = form.getFieldsMap(true);

		checkBooleanField(fieldsMap.get("Boolean2282"));

		checkDateField(fieldsMap.get("Date2510"));

		checkDecimalField(fieldsMap.get("Decimal3479"));

		checkDocumentLibraryField(fieldsMap.get("Documents_and_Media4036"));

		checkRadioField(fieldsMap.get("Radio5699"));

		checkNestedFields(fieldsMap.get("Text6980"));
	}

	protected void checkBooleanField(DDMFormField booleanField) {
		Assert.assertNotNull(booleanField);

		Assert.assertEquals("boolean", booleanField.getDataType());
		Assert.assertEquals("keyword", booleanField.getIndexType());

		Assert.assertFalse(booleanField.isRepeatable());
		Assert.assertFalse(booleanField.isRequired());

		Assert.assertEquals("checkbox", booleanField.getType());

		LocalizedValue label = booleanField.getLabel();

		Assert.assertEquals("Boolean", label.getValue(LocaleUtil.US));
		Assert.assertEquals("Booleano", label.getValue(LocaleUtil.BRAZIL));

		LocalizedValue predefinedValue = booleanField.getPredefinedValue();

		Assert.assertEquals("false", predefinedValue.getValue(LocaleUtil.US));
	}

	protected void checkDateField(DDMFormField dateField) {
		Assert.assertNotNull(dateField);

		Assert.assertEquals("date", dateField.getDataType());
		Assert.assertEquals("ddm-date", dateField.getType());
	}

	protected void checkDecimalField(DDMFormField decimalField) {
		Assert.assertNotNull(decimalField);

		Assert.assertEquals("double", decimalField.getDataType());
		Assert.assertEquals("ddm-decimal", decimalField.getType());
	}

	protected void checkDocumentLibraryField(
		DDMFormField documentLibraryField) {

		Assert.assertNotNull(documentLibraryField);

		Assert.assertEquals(
			"document-library", documentLibraryField.getDataType());
		Assert.assertEquals(
			"ddm-documentlibrary", documentLibraryField.getType());
	}

	protected void checkExpectedAvailableLanguages(DDMForm form) {
		List<Locale> availableLocales = form.getAvailableLocales();

		Assert.assertEquals(2, availableLocales.size());

		Assert.assertTrue(availableLocales.contains(LocaleUtil.US));
		Assert.assertTrue(availableLocales.contains(LocaleUtil.BRAZIL));
	}

	protected void checkExpectedDefaultLanguage(DDMForm form) {
		Locale defaultLocale = form.getDefaultLocale();

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		Assert.assertEquals("en_US", defaultLanguageId);
	}

	protected void checkNestedFields(DDMFormField textField) {
		Assert.assertNotNull(textField);

		List<DDMFormField> nestedFields = textField.getNestedFields();

		Assert.assertEquals(1, nestedFields.size());

		DDMFormField separatorField = nestedFields.get(0);

		Assert.assertEquals("Separator7211", separatorField.getName());

		nestedFields = separatorField.getNestedFields();

		Assert.assertEquals(1, nestedFields.size());

		DDMFormField selectField = nestedFields.get(0);

		Assert.assertEquals("Select7450", selectField.getName());

		DDMFormFieldOptions selectOptions = selectField.getOptions();

		Assert.assertEquals(3, selectOptions.getOptionsValues().size());
	}

	protected void checkRadioField(DDMFormField radioField) {
		Assert.assertNotNull(radioField);

		Assert.assertEquals("string", radioField.getDataType());
		Assert.assertEquals("radio", radioField.getType());

		LocalizedValue predefinedValue = radioField.getPredefinedValue();

		Assert.assertEquals(
			"[\"value 1\"]", predefinedValue.getValue(LocaleUtil.US));

		DDMFormFieldOptions options = radioField.getOptions();

		Set<String> optionsValues = options.getOptionsValues();

		Assert.assertEquals(3, optionsValues.size());

		Assert.assertTrue(optionsValues.contains("value 1"));
		Assert.assertTrue(optionsValues.contains("value 2"));
		Assert.assertTrue(optionsValues.contains("value 3"));

		LocalizedValue value1Labels = options.getOptionLabels("value 1");

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

	protected void setUpDDMConverter() {
		spy(DDMFormConverterUtil.class);

		when(
			DDMFormConverterUtil.getDDMFormConverter()
		).thenReturn(
			_ddmFormConverter
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

	private DDMFormConverter _ddmFormConverter = new DDMFormConverterImpl();

}