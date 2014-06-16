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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portlet.dynamicdatamapping.BaseDDMTest;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Pablo Carvalho
 */
public abstract class BaseDDMFormDeserializer extends BaseDDMTest {

	@Test
	public void testDDMFormAndFieldsDefaultLocales() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("ddm-form-");
		sb.append(getDeserializerType());
		sb.append("-deserializer-different-default-locale");
		sb.append(getTestFileExtension());

		String serializedDDMForm = read(sb.toString());

		DDMForm ddmForm = deserialize(serializedDDMForm);

		Assert.assertEquals(LocaleUtil.BRAZIL, ddmForm.getDefaultLocale());

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		DDMFormField selectDDMFormField = ddmFormFieldsMap.get("Select5979");

		LocalizedValue selectLabel = selectDDMFormField.getLabel();

		Assert.assertEquals(LocaleUtil.BRAZIL, selectLabel.getDefaultLocale());

		DDMFormFieldOptions ddmFormFieldOptions =
			selectDDMFormField.getDDMFormFieldOptions();

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
			LocalizedValue optionLabel = ddmFormFieldOptions.getOptionLabels(
				optionValue);

			Assert.assertEquals(
				LocaleUtil.BRAZIL, optionLabel.getDefaultLocale());
		}
	}

	@Test
	public void testDDMFormDeserialization() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("ddm-form-");
		sb.append(getDeserializerType());
		sb.append("-deserializer-test-data");
		sb.append(getTestFileExtension());

		String serializedDDMForm = read(sb.toString());

		DDMForm ddmForm = deserialize(serializedDDMForm);

		testAvailableLocales(ddmForm);
		testDefaultLocale(ddmForm);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		testBooleanDDMFormField(ddmFormFieldsMap.get("Boolean2282"));
		testDateDDMFormField(ddmFormFieldsMap.get("Date2510"));
		testDecimalDDMFormField(ddmFormFieldsMap.get("Decimal3479"));
		testDocumentLibraryDDMFormField(
			ddmFormFieldsMap.get("Documents_and_Media4036"));
		testNestedDDMFormFields(ddmFormFieldsMap.get("Text6980"));
		testRadioDDMFormField(ddmFormFieldsMap.get("Radio5699"));
	}

	protected abstract DDMForm deserialize(String serializedDDMForm)
		throws PortalException;

	protected abstract String getDeserializerType();

	protected abstract String getTestFileExtension();

	protected void testAvailableLocales(DDMForm ddmForm) {
		List<Locale> availableLocales = ddmForm.getAvailableLocales();

		Assert.assertEquals(2, availableLocales.size());
		Assert.assertTrue(availableLocales.contains(LocaleUtil.US));
		Assert.assertTrue(availableLocales.contains(LocaleUtil.BRAZIL));
	}

	protected void testBooleanDDMFormField(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals("boolean", ddmFormField.getDataType());
		Assert.assertEquals("keyword", ddmFormField.getIndexType());

		LocalizedValue label = ddmFormField.getLabel();

		Assert.assertEquals("Boolean", label.getValue(LocaleUtil.US));
		Assert.assertEquals("Booleano", label.getValue(LocaleUtil.BRAZIL));

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		Assert.assertEquals("false", predefinedValue.getValue(LocaleUtil.US));

		Assert.assertEquals("checkbox", ddmFormField.getType());
		Assert.assertFalse(ddmFormField.isRepeatable());
		Assert.assertFalse(ddmFormField.isRequired());
	}

	protected void testDateDDMFormField(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals("date", ddmFormField.getDataType());
		Assert.assertEquals("ddm-date", ddmFormField.getType());
	}

	protected void testDecimalDDMFormField(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals("double", ddmFormField.getDataType());
		Assert.assertEquals("ddm-decimal", ddmFormField.getType());
	}

	protected void testDefaultLocale(DDMForm ddmForm) {
		Locale defaultLocale = ddmForm.getDefaultLocale();

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		Assert.assertEquals("en_US", defaultLanguageId);
	}

	protected void testDocumentLibraryDDMFormField(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals("document-library", ddmFormField.getDataType());
		Assert.assertEquals("ddm-documentlibrary", ddmFormField.getType());
	}

	protected void testNestedDDMFormFields(DDMFormField ddmFormField) {
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

	protected void testRadioDDMFormField(DDMFormField ddmFormField) {
		Assert.assertNotNull(ddmFormField);
		Assert.assertEquals("string", ddmFormField.getDataType());
		Assert.assertEquals("radio", ddmFormField.getType());

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		Assert.assertEquals(
			"[\"Value 1\"]", predefinedValue.getValue(LocaleUtil.US));

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

		Assert.assertEquals(3, optionsValues.size());
		Assert.assertTrue(optionsValues.contains("Value 1"));
		Assert.assertTrue(optionsValues.contains("Value 2"));
		Assert.assertTrue(optionsValues.contains("Value 3"));

		LocalizedValue value1Labels = ddmFormFieldOptions.getOptionLabels(
			"Value 1");

		Assert.assertEquals("Option 1", value1Labels.getValue(LocaleUtil.US));
		Assert.assertEquals(
			"Opcao 1", value1Labels.getValue(LocaleUtil.BRAZIL));
	}

}