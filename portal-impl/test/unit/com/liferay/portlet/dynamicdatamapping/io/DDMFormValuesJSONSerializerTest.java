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

package com.liferay.portlet.dynamicdatamapping.io;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.BaseDDMTest;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import org.powermock.core.classloader.annotations.PrepareForTest;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest({LocaleUtil.class})
public class DDMFormValuesJSONSerializerTest extends BaseDDMTest {

	@Before
	public void setUp() {
		setUpDDMFormValuesJSONSerializerUtil();
		setUpLocaleUtil();
		setUpJSONFactoryUtil();
	}

	@Test
	public void testFormValuesSerialization() throws Exception {
		String expectedJSON = read(
			"ddm-form-values-json-serializer-test-data.json");

		DDMFormValues ddmFormValues = createDDMFormValues();

		String actualJSON = DDMFormValuesJSONSerializerUtil.serialize(
			ddmFormValues);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	protected List<Locale> createAvailableLocales() {
		List<Locale> availableLocales = new ArrayList<Locale>();

		availableLocales.add(LocaleUtil.US);
		availableLocales.add(LocaleUtil.BRAZIL);

		return availableLocales;
	}

	protected DDMFormFieldValue createBooleanDDMFormFieldValue() {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("Boolean2282");
		ddmFormFieldValue.setNestedDDMFormFields(
			createBooleanNestedDDMFormFieldValues());
		ddmFormFieldValue.setValue(createBooleanValue());

		return ddmFormFieldValue;
	}

	protected List<DDMFormFieldValue> createBooleanNestedDDMFormFieldValues() {
		List<DDMFormFieldValue> ddmFormFieldValues =
			new ArrayList<DDMFormFieldValue>();

		ddmFormFieldValues.add(createHTMLDDMFormFieldValue(0));
		ddmFormFieldValues.add(createHTMLDDMFormFieldValue(1));

		return ddmFormFieldValues;
	}

	protected Value createBooleanValue() {
		Value value = new LocalizedValue();

		value.addValue(LocaleUtil.US, "false");
		value.addValue(LocaleUtil.BRAZIL, "true");

		return value;
	}

	protected List<DDMFormFieldValue> createDDMFormFieldValues() {
		List<DDMFormFieldValue> ddmFormFieldValues =
			new ArrayList<DDMFormFieldValue>();

		ddmFormFieldValues.addAll(createSeparatorDDMFormFieldValues());
		ddmFormFieldValues.add(createTextDDMFormFieldValue());
		ddmFormFieldValues.addAll(createImageDDMFormFieldValues());
		ddmFormFieldValues.add(createBooleanDDMFormFieldValue());

		return ddmFormFieldValues;
	}

	protected DDMFormValues createDDMFormValues() {
		DDMFormValues ddmFormValues = new DDMFormValues();

		ddmFormValues.setAvailableLocales(createAvailableLocales());
		ddmFormValues.setDDMFormFieldValues(createDDMFormFieldValues());
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		return ddmFormValues;
	}

	protected DDMFormFieldValue createHTMLDDMFormFieldValue(int index) {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("HTML4512");
		ddmFormFieldValue.setValue(createHTMLValue(index));

		return ddmFormFieldValue;
	}

	protected Value createHTMLValue(int index) {
		Value value = new LocalizedValue();

		value.addValue(LocaleUtil.US, "<p>This is a test. " + index + "</p>");
		value.addValue(
			LocaleUtil.BRAZIL, "<p>Isto e um teste. " + index + "</p>");

		return value;
	}

	protected DDMFormFieldValue createImageDDMFormFieldValue(int index) {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("Image4751");
		ddmFormFieldValue.setValue(createImageValue(index));

		return ddmFormFieldValue;
	}

	protected List<DDMFormFieldValue> createImageDDMFormFieldValues() {
		List<DDMFormFieldValue> imageDDMFormFieldValues =
			new ArrayList<DDMFormFieldValue>();

		for (int i = 0; i < 3; i++) {
			imageDDMFormFieldValues.add(createImageDDMFormFieldValue(i));
		}

		return imageDDMFormFieldValues;
	}

	protected Value createImageValue(int index) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("alt", "This is a image description. " + index);
		jsonObject.put("data", "base64Value" + index);

		return new UnlocalizedValue(jsonObject.toString());
	}

	protected DDMFormFieldValue createSeparatorDDMFormFieldValue(int index) {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("Separator7211");
		ddmFormFieldValue.setNestedDDMFormFields(
			createSeparatorNestedDDMFormFieldValues(index));

		return ddmFormFieldValue;
	}

	protected List<DDMFormFieldValue> createSeparatorDDMFormFieldValues() {
		List<DDMFormFieldValue> separatorDDMFormFieldValues =
			new ArrayList<DDMFormFieldValue>();

		for (int i = 0; i < 2; i++) {
			separatorDDMFormFieldValues.add(
				createSeparatorDDMFormFieldValue(i));
		}

		return separatorDDMFormFieldValues;
	}

	protected List<DDMFormFieldValue> createSeparatorNestedDDMFormFieldValues(
		int index) {

		List<DDMFormFieldValue> ddmFormFieldValues =
			new ArrayList<DDMFormFieldValue>();

		ddmFormFieldValues.add(createTextBoxDDMFormFieldValue(index));

		return ddmFormFieldValues;
	}

	protected DDMFormFieldValue createTextBoxDDMFormFieldValue(int index) {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("Text_Box6748");
		ddmFormFieldValue.setValue(createTextBoxValue(index));

		return ddmFormFieldValue;
	}

	protected Value createTextBoxValue(int index) {
		Value value = new LocalizedValue();

		value.addValue(LocaleUtil.US, "Content " + index);
		value.addValue(LocaleUtil.BRAZIL, "Conteudo " + index);

		return value;
	}

	protected DDMFormFieldValue createTextDDMFormFieldValue() {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName("Text6513");
		ddmFormFieldValue.setValue(createTextValue());

		return ddmFormFieldValue;
	}

	protected Value createTextValue() {
		Value value = new LocalizedValue();

		value.addValue(LocaleUtil.US, "Text");
		value.addValue(LocaleUtil.BRAZIL, "Texto");

		return value;
	}

	protected void setUpDDMFormValuesJSONSerializerUtil() {
		DDMFormValuesJSONSerializerUtil ddmFormValuesJSONSerializerUtil =
			new DDMFormValuesJSONSerializerUtil();

		ddmFormValuesJSONSerializerUtil.setDDMFormValuesJSONSerializer(
			new DDMFormValuesJSONSerializerImpl());
	}

}