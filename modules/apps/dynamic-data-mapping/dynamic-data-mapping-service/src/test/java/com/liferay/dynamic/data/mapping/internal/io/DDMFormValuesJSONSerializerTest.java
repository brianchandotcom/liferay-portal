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

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONObject;

import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesJSONSerializerTest extends BaseDDMTestCase {

	public static String toOrderedJSONString(String jsonString) {
		JSONObject jsonObject = new JSONObject(jsonString) {

			@Override
			protected Set<Map.Entry<String, Object>> entrySet() {
				Set<Map.Entry<String, Object>> entrySet = new TreeSet<>(
					Comparator.comparing(Map.Entry::getKey));

				entrySet.addAll(super.entrySet());

				return entrySet;
			}

		};

		return jsonObject.toString();
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpDDMFormValuesJSONSerializer();
	}

	@Test
	public void testFormValuesSerialization() throws Exception {
		String expectedJSON = read(
			"ddm-form-values-json-serializer-test-data.json");

		DDMFormValues ddmFormValues = createDDMFormValues();

		String actualJSON = serialize(ddmFormValues);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	protected DDMFormFieldValue createBooleanDDMFormFieldValue() {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId("njar");
		ddmFormFieldValue.setName("Boolean");
		ddmFormFieldValue.setNestedDDMFormFields(
			createBooleanNestedDDMFormFieldValues());
		ddmFormFieldValue.setValue(createBooleanValue());

		return ddmFormFieldValue;
	}

	protected List<DDMFormFieldValue> createBooleanNestedDDMFormFieldValues() {
		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		ddmFormFieldValues.add(createHTMLDDMFormFieldValue(0, "nabr"));
		ddmFormFieldValues.add(createHTMLDDMFormFieldValue(1, "uwyg"));

		return ddmFormFieldValues;
	}

	protected Value createBooleanValue() {
		Value value = new LocalizedValue();

		value.addString(LocaleUtil.US, "false");
		value.addString(LocaleUtil.BRAZIL, "true");

		return value;
	}

	protected DDMForm createDDMForm() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField separatorDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Separator", "Separator", "separator", "", false, true, false);

		separatorDDMFormField.addNestedDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Text_Box", "Text_Box", "text", "string", true, false, false));

		ddmForm.addDDMFormField(separatorDDMFormField);

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Text", "Text", "text", "string", true, false, false));

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"Image", "Image", "image", "string", false, true, false));

		DDMFormField booleanDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Boolean", "Boolean", "checkbox", "boolean", true, false, false);

		booleanDDMFormField.addNestedDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"HTML", "HTML", "html", "string", true, true, false));

		ddmForm.addDDMFormField(booleanDDMFormField);

		return ddmForm;
	}

	protected List<DDMFormFieldValue> createDDMFormFieldValues() {
		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		ddmFormFieldValues.addAll(createSeparatorDDMFormFieldValues());
		ddmFormFieldValues.add(createTextDDMFormFieldValue());
		ddmFormFieldValues.addAll(createImageDDMFormFieldValues());
		ddmFormFieldValues.add(createBooleanDDMFormFieldValue());

		return ddmFormFieldValues;
	}

	protected DDMFormValues createDDMFormValues() {
		DDMForm ddmForm = createDDMForm();

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(
			DDMFormValuesTestUtil.createAvailableLocales(
				LocaleUtil.BRAZIL, LocaleUtil.US));
		ddmFormValues.setDDMFormFieldValues(createDDMFormFieldValues());
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		return ddmFormValues;
	}

	protected DDMFormFieldValue createHTMLDDMFormFieldValue(
		int index, String instanceId) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName("HTML");
		ddmFormFieldValue.setValue(createHTMLValue(index));

		return ddmFormFieldValue;
	}

	protected Value createHTMLValue(int index) {
		Value value = new LocalizedValue();

		value.addString(LocaleUtil.US, "<p>This is a test. " + index + "</p>");
		value.addString(
			LocaleUtil.BRAZIL, "<p>Isto e um teste. " + index + "</p>");

		return value;
	}

	protected DDMFormFieldValue createImageDDMFormFieldValue(
		int index, String instanceId) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName("Image");
		ddmFormFieldValue.setValue(createImageValue(index));

		return ddmFormFieldValue;
	}

	protected List<DDMFormFieldValue> createImageDDMFormFieldValues() {
		List<DDMFormFieldValue> imageDDMFormFieldValues = new ArrayList<>();

		imageDDMFormFieldValues.add(createImageDDMFormFieldValue(0, "uaht"));
		imageDDMFormFieldValues.add(createImageDDMFormFieldValue(1, "pppj"));
		imageDDMFormFieldValues.add(createImageDDMFormFieldValue(2, "nmab"));

		return imageDDMFormFieldValues;
	}

	protected Value createImageValue(int index) {
		JSONObject jsonObject = new JSONObject() {

			@Override
			protected Set<Map.Entry<String, Object>> entrySet() {
				Set<Map.Entry<String, Object>> entrySet = new TreeSet<>(
					Comparator.comparing(Map.Entry::getKey));

				entrySet.addAll(super.entrySet());

				return entrySet;
			}

		};

		jsonObject.put("alt", "This is a image description. " + index);
		jsonObject.put("data", "base64Value" + index);

		return new UnlocalizedValue(jsonObject.toString());
	}

	protected DDMFormFieldValue createSeparatorDDMFormFieldValue(
		int index, String instanceId) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName("Separator");
		ddmFormFieldValue.setNestedDDMFormFields(
			createSeparatorNestedDDMFormFieldValues(index, "xyz" + index));

		return ddmFormFieldValue;
	}

	protected List<DDMFormFieldValue> createSeparatorDDMFormFieldValues() {
		List<DDMFormFieldValue> separatorDDMFormFieldValues = new ArrayList<>();

		separatorDDMFormFieldValues.add(
			createSeparatorDDMFormFieldValue(0, "uayx"));

		separatorDDMFormFieldValues.add(
			createSeparatorDDMFormFieldValue(1, "lahy"));

		return separatorDDMFormFieldValues;
	}

	protected List<DDMFormFieldValue> createSeparatorNestedDDMFormFieldValues(
		int index, String instanceId) {

		return ListUtil.fromArray(
			createTextBoxDDMFormFieldValue(index, instanceId));
	}

	protected DDMFormFieldValue createTextBoxDDMFormFieldValue(
		int index, String instanceId) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName("Text_Box");
		ddmFormFieldValue.setValue(createTextBoxValue(index));

		return ddmFormFieldValue;
	}

	protected Value createTextBoxValue(int index) {
		Value value = new LocalizedValue();

		value.addString(LocaleUtil.US, "Content " + index);
		value.addString(LocaleUtil.BRAZIL, "Conteudo " + index);

		return value;
	}

	protected DDMFormFieldValue createTextDDMFormFieldValue() {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId("baht");
		ddmFormFieldValue.setName("Text");
		ddmFormFieldValue.setValue(createTextValue());

		return ddmFormFieldValue;
	}

	protected Value createTextValue() {
		Value value = new LocalizedValue();

		value.addString(LocaleUtil.US, "Text");
		value.addString(LocaleUtil.BRAZIL, "Texto");

		return value;
	}

	protected String serialize(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				_ddmFormValuesJSONSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	protected void setUpDDMFormValuesJSONSerializer() throws Exception {
		field(
			DDMFormValuesJSONSerializer.class, "_jsonFactory"
		).set(
			_ddmFormValuesJSONSerializer, new JSONFactoryImpl()
		);
	}

	private final DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer =
		new DDMFormValuesJSONSerializer();

}