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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.BaseDDMTestCase;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.core.classloader.annotations.PrepareForTest;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest({LocaleUtil.class})
public class DDMFormValuesJSONDeserializerTest extends BaseDDMTestCase {

	@Before
	public void setUp() {
		setUpDDMFormValuesJSONDeserializerUtil();
		setUpLanguageUtil();
		setUpLocaleUtil();
		setUpJSONFactoryUtil();
	}

	@Test
	public void testDeserializationWithParentRepeatableField()
		throws Exception {

		String serializedDDMFormValues = read(
			"ddm-form-values-json-deserializer-parent-repeatable-field.json");

		DDMFormValues ddmFormValues =
			DDMFormValuesJSONDeserializerUtil.deserialize(
				null, serializedDDMFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());

		for (int i = 0; i < ddmFormFieldValues.size(); i++) {
			DDMFormFieldValue separatorDDMFormFieldValue =
				ddmFormFieldValues.get(i);

			testSeparatorDDMFormFieldValueValue(separatorDDMFormFieldValue);

			List<DDMFormFieldValue> separatorNestedDDMFormFieldValues =
				separatorDDMFormFieldValue.getNestedDDMFormFieldValues();

			Assert.assertEquals(1, separatorNestedDDMFormFieldValues.size());

			testTextDDMFormFieldValue(
				separatorNestedDDMFormFieldValues.get(0), "Content " + i,
				"Conteudo " + i);
		}
	}

	@Test
	public void testDeserializationWithRepeatableField() throws Exception {
		String serializedDDMFormValues = read(
			"ddm-form-values-json-deserializer-repeatable-field.json");

		DDMFormValues ddmFormValues =
			DDMFormValuesJSONDeserializerUtil.deserialize(
				null, serializedDDMFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(3, ddmFormFieldValues.size());

		for (int i = 0; i < ddmFormFieldValues.size(); i++) {
			testTextDDMFormFieldValue(
				ddmFormFieldValues.get(i), "Name " + i, "Nome " + i);
		}
	}

	@Test
	public void testDeserializationWithSimpleFields() throws Exception {
		String serializedDDMFormValues = read(
			"ddm-form-values-json-deserializer-test-data.json");

		DDMFormValues ddmFormValues =
			DDMFormValuesJSONDeserializerUtil.deserialize(
				null, serializedDDMFormValues);

		testAvailableLocales(ddmFormValues);
		testDefaultLocale(ddmFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(7, ddmFormFieldValues.size());

		testBooleanDDMFormFieldValueValues(ddmFormFieldValues.get(0));
		testDocumentLibraryDDMFormFieldValueValues(ddmFormFieldValues.get(1));
		testGeolocationDDMFormFieldValueValues(ddmFormFieldValues.get(2));
		testHTMLDDMFormFieldValueValues(ddmFormFieldValues.get(3));
		testImageDDMFormFieldValueValues(ddmFormFieldValues.get(4));
		testLinkToPageDDMFormFieldValueValues(ddmFormFieldValues.get(5));
		testSelectDDMFormFieldValueValues(ddmFormFieldValues.get(6));
	}

	@Test
	public void testDeserializationWithUnlocalizableField() throws Exception {
		String serializedDDMFormValues = read(
			"ddm-form-values-json-deserializer-unlocalizable-fields.json");

		DDMFormValues ddmFormValues =
			DDMFormValuesJSONDeserializerUtil.deserialize(
				null, serializedDDMFormValues);

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Assert.assertEquals(2, ddmFormFieldValues.size());

		DDMFormFieldValue booleanDDMFormFieldValue = ddmFormFieldValues.get(0);

		Assert.assertEquals("usht", booleanDDMFormFieldValue.getInstanceId());

		Value booleanValue = booleanDDMFormFieldValue.getValue();

		Assert.assertFalse(booleanValue.isLocalized());
		Assert.assertEquals("false", booleanValue.getString(LocaleUtil.US));
		Assert.assertEquals("false", booleanValue.getString(LocaleUtil.BRAZIL));

		DDMFormFieldValue documentLibraryDDMFormFieldValue =
			ddmFormFieldValues.get(1);

		Assert.assertEquals(
			"xdwp", documentLibraryDDMFormFieldValue.getInstanceId());

		Value documentLibraryValue =
			documentLibraryDDMFormFieldValue.getValue();

		Assert.assertFalse(documentLibraryValue.isLocalized());

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject();

		expectedJSONObject.put("groupId", 10192);
		expectedJSONObject.put("uuid", "c8acdf70-e101-46a6-83e5-c5f5e087b0dc");
		expectedJSONObject.put("version", 1.0);

		JSONAssert.assertEquals(
			expectedJSONObject.toString(),
			documentLibraryValue.getString(LocaleUtil.US), false);
		JSONAssert.assertEquals(
			expectedJSONObject.toString(),
			documentLibraryValue.getString(LocaleUtil.BRAZIL), false);
	}

	protected void setUpDDMFormValuesJSONDeserializerUtil() {
		DDMFormValuesJSONDeserializerUtil ddmFormValuesJSONDeserializerUtil =
			new DDMFormValuesJSONDeserializerUtil();

		ddmFormValuesJSONDeserializerUtil.setDDMFormValuesJSONDeserializer(
			new DDMFormValuesJSONDeserializerImpl());
	}

	protected void testAvailableLocales(DDMFormValues ddmFormValues) {
		Set<Locale> availableLocales = ddmFormValues.getAvailableLocales();

		Assert.assertEquals(2, availableLocales.size());
		Assert.assertTrue(availableLocales.contains(LocaleUtil.US));
		Assert.assertTrue(availableLocales.contains(LocaleUtil.BRAZIL));
	}

	protected void testBooleanDDMFormFieldValueValues(
		DDMFormFieldValue ddmFormFieldValue) {

		Assert.assertEquals("maky", ddmFormFieldValue.getInstanceId());

		Value value = ddmFormFieldValue.getValue();

		Assert.assertEquals("false", value.getString(LocaleUtil.US));
		Assert.assertEquals("true", value.getString(LocaleUtil.BRAZIL));
	}

	protected void testDefaultLocale(DDMFormValues ddmFormValues) {
		Locale defaultLocale = ddmFormValues.getDefaultLocale();

		Assert.assertEquals(LocaleUtil.US, defaultLocale);
	}

	protected void testDocumentLibraryDDMFormFieldValueValues(
			DDMFormFieldValue ddmFormFieldValue)
		throws Exception {

		Assert.assertEquals("autx", ddmFormFieldValue.getInstanceId());

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject();

		expectedJSONObject.put("groupId", 10192);
		expectedJSONObject.put("uuid", "c8acdf70-e101-46a6-83e5-c5f5e087b0dc");
		expectedJSONObject.put("version", 1.0);

		Value value = ddmFormFieldValue.getValue();

		JSONAssert.assertEquals(
			expectedJSONObject.toString(), value.getString(LocaleUtil.US),
			false);
		JSONAssert.assertEquals(
			expectedJSONObject.toString(), value.getString(LocaleUtil.BRAZIL),
			false);
	}

	protected void testGeolocationDDMFormFieldValueValues(
			DDMFormFieldValue ddmFormFieldValue)
		throws Exception {

		Assert.assertEquals("powq", ddmFormFieldValue.getInstanceId());

		Value value = ddmFormFieldValue.getValue();

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject();

		expectedJSONObject.put("latitude", 34.0286226);
		expectedJSONObject.put("longitude", -117.8103367);

		JSONAssert.assertEquals(
			expectedJSONObject.toString(), value.getString(LocaleUtil.US),
			false);

		expectedJSONObject = JSONFactoryUtil.createJSONObject();

		expectedJSONObject.put("latitude", -8.0349219);
		expectedJSONObject.put("longitude", -34.91922120);

		JSONAssert.assertEquals(
			expectedJSONObject.toString(), value.getString(LocaleUtil.BRAZIL),
			false);
	}

	protected void testHTMLDDMFormFieldValueValues(
		DDMFormFieldValue ddmFormFieldValue) {

		Assert.assertEquals("lamn", ddmFormFieldValue.getInstanceId());

		Value value = ddmFormFieldValue.getValue();

		Assert.assertEquals(
			"<p>This is a test.</p>", value.getString(LocaleUtil.US));
		Assert.assertEquals(
			"<p>Isto e um teste.</p>", value.getString(LocaleUtil.BRAZIL));
	}

	protected void testImageDDMFormFieldValueValues(
			DDMFormFieldValue ddmFormFieldValue)
		throws Exception {

		Assert.assertEquals("labt", ddmFormFieldValue.getInstanceId());

		Value value = ddmFormFieldValue.getValue();

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject();

		expectedJSONObject.put("alt", "This is a image description.");
		expectedJSONObject.put("data", "base64Value");

		JSONAssert.assertEquals(
			expectedJSONObject.toString(), value.getString(LocaleUtil.US),
			false);

		expectedJSONObject = JSONFactoryUtil.createJSONObject();

		expectedJSONObject.put("alt", "Isto e uma descricao de imagem.");
		expectedJSONObject.put("data", "valorEmBase64");

		JSONAssert.assertEquals(
			expectedJSONObject.toString(), value.getString(LocaleUtil.BRAZIL),
			false);
	}

	protected void testLinkToPageDDMFormFieldValueValues(
			DDMFormFieldValue ddmFormFieldValue)
		throws Exception {

		Assert.assertEquals("nast", ddmFormFieldValue.getInstanceId());

		Value value = ddmFormFieldValue.getValue();

		JSONObject expectedJSONObject = JSONFactoryUtil.createJSONObject();

		expectedJSONObject.put("groupId", 10192);
		expectedJSONObject.put("layoutId", 1);
		expectedJSONObject.put("privateLayout", false);

		JSONAssert.assertEquals(
			expectedJSONObject.toString(), value.getString(LocaleUtil.US),
			false);

		expectedJSONObject = JSONFactoryUtil.createJSONObject();

		expectedJSONObject.put("groupId", 10192);
		expectedJSONObject.put("layoutId", 2);
		expectedJSONObject.put("privateLayout", false);

		JSONAssert.assertEquals(
			expectedJSONObject.toString(), value.getString(LocaleUtil.BRAZIL),
			false);
	}

	protected void testSelectDDMFormFieldValueValues(
			DDMFormFieldValue ddmFormFieldValue)
		throws Exception {

		Assert.assertEquals("yhar", ddmFormFieldValue.getInstanceId());

		Value value = ddmFormFieldValue.getValue();

		JSONArray expectedJSONArray = JSONFactoryUtil.createJSONArray();

		expectedJSONArray.put("Value 1");
		expectedJSONArray.put("Value 3");

		JSONAssert.assertEquals(
			expectedJSONArray.toString(), value.getString(LocaleUtil.US),
			false);

		expectedJSONArray = JSONFactoryUtil.createJSONArray();

		expectedJSONArray.put("Value 2");
		expectedJSONArray.put("Value 3");

		JSONAssert.assertEquals(
			expectedJSONArray.toString(), value.getString(LocaleUtil.BRAZIL),
			false);
	}

	protected void testSeparatorDDMFormFieldValueValue(
		DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		Assert.assertNull(value);
	}

	protected void testTextDDMFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue, String expected_en_US,
		String expected_pt_BR) {

		Assert.assertNotNull(ddmFormFieldValue);

		Value value = ddmFormFieldValue.getValue();

		Assert.assertEquals(expected_en_US, value.getString(LocaleUtil.US));
		Assert.assertEquals(expected_pt_BR, value.getString(LocaleUtil.BRAZIL));
	}

}