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
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONObject;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesJSONSerializerTest extends BaseDDMTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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

		_setUpDDMFormValuesJSONSerializer();
	}

	@Test
	public void testFormValuesSerialization() throws Exception {
		String expectedJSON = read(
			"ddm-form-values-json-serializer-test-data.json");

		DDMFormValues ddmFormValues = _createDDMFormValues();

		String actualJSON = serialize(ddmFormValues);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
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

	protected String serialize(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				_ddmFormValuesJSONSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	private DDMFormFieldValue _createBooleanDDMFormFieldValue() {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId("njar");
		ddmFormFieldValue.setName("Boolean");
		ddmFormFieldValue.setNestedDDMFormFields(
			_createBooleanNestedDDMFormFieldValues());
		ddmFormFieldValue.setValue(_createBooleanValue());

		return ddmFormFieldValue;
	}

	private List<DDMFormFieldValue> _createBooleanNestedDDMFormFieldValues() {
		return ListUtil.fromArray(
			_createHTMLDDMFormFieldValue(0, "nabr"),
			_createHTMLDDMFormFieldValue(1, "uwyg"));
	}

	private Value _createBooleanValue() {
		Value value = new LocalizedValue();

		value.addString(LocaleUtil.US, "false");
		value.addString(LocaleUtil.BRAZIL, "true");

		return value;
	}

	private List<DDMFormFieldValue> _createDDMFormFieldValues() {
		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		ddmFormFieldValues.addAll(_createSeparatorDDMFormFieldValues());
		ddmFormFieldValues.add(_createTextDDMFormFieldValue());
		ddmFormFieldValues.addAll(_createImageDDMFormFieldValues());
		ddmFormFieldValues.add(_createBooleanDDMFormFieldValue());

		return ddmFormFieldValues;
	}

	private DDMFormValues _createDDMFormValues() {
		DDMForm ddmForm = createDDMForm();

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.setAvailableLocales(
			DDMFormValuesTestUtil.createAvailableLocales(
				LocaleUtil.BRAZIL, LocaleUtil.US));
		ddmFormValues.setDDMFormFieldValues(_createDDMFormFieldValues());
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		return ddmFormValues;
	}

	private DDMFormFieldValue _createHTMLDDMFormFieldValue(
		int index, String instanceId) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName("HTML");
		ddmFormFieldValue.setValue(_createHTMLValue(index));

		return ddmFormFieldValue;
	}

	private Value _createHTMLValue(int index) {
		Value value = new LocalizedValue();

		value.addString(LocaleUtil.US, "<p>This is a test. " + index + "</p>");
		value.addString(
			LocaleUtil.BRAZIL, "<p>Isto e um teste. " + index + "</p>");

		return value;
	}

	private DDMFormFieldValue _createImageDDMFormFieldValue(
		int index, String instanceId) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName("Image");
		ddmFormFieldValue.setValue(_createImageValue(index));

		return ddmFormFieldValue;
	}

	private List<DDMFormFieldValue> _createImageDDMFormFieldValues() {
		return ListUtil.fromArray(
			_createImageDDMFormFieldValue(0, "uaht"),
			_createImageDDMFormFieldValue(1, "pppj"),
			_createImageDDMFormFieldValue(2, "nmab"));
	}

	private Value _createImageValue(int index) {
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

	private DDMFormFieldValue _createSeparatorDDMFormFieldValue(
		int index, String instanceId) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName("Separator");
		ddmFormFieldValue.setNestedDDMFormFields(
			_createSeparatorNestedDDMFormFieldValues(index, "xyz" + index));

		return ddmFormFieldValue;
	}

	private List<DDMFormFieldValue> _createSeparatorDDMFormFieldValues() {
		return ListUtil.fromArray(
			_createSeparatorDDMFormFieldValue(0, "uayx"),
			_createSeparatorDDMFormFieldValue(1, "lahy"));
	}

	private List<DDMFormFieldValue> _createSeparatorNestedDDMFormFieldValues(
		int index, String instanceId) {

		return ListUtil.fromArray(
			_createTextBoxDDMFormFieldValue(index, instanceId));
	}

	private DDMFormFieldValue _createTextBoxDDMFormFieldValue(
		int index, String instanceId) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(instanceId);
		ddmFormFieldValue.setName("Text_Box");
		ddmFormFieldValue.setValue(_createTextBoxValue(index));

		return ddmFormFieldValue;
	}

	private Value _createTextBoxValue(int index) {
		Value value = new LocalizedValue();

		value.addString(LocaleUtil.US, "Content " + index);
		value.addString(LocaleUtil.BRAZIL, "Conteudo " + index);

		return value;
	}

	private DDMFormFieldValue _createTextDDMFormFieldValue() {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId("baht");
		ddmFormFieldValue.setName("Text");
		ddmFormFieldValue.setValue(_createTextValue());

		return ddmFormFieldValue;
	}

	private Value _createTextValue() {
		Value value = new LocalizedValue();

		value.addString(LocaleUtil.US, "Text");
		value.addString(LocaleUtil.BRAZIL, "Texto");

		return value;
	}

	private void _setUpDDMFormValuesJSONSerializer() throws Exception {
		field(
			DDMFormValuesJSONSerializer.class, "_jsonFactory"
		).set(
			_ddmFormValuesJSONSerializer, new JSONFactoryImpl()
		);

		field(
			DDMFormValuesJSONSerializer.class, "_serviceTrackerMap"
		).set(
			_ddmFormValuesJSONSerializer,
			ProxyFactory.newDummyInstance(ServiceTrackerMap.class)
		);
	}

	private final DDMFormValuesJSONSerializer _ddmFormValuesJSONSerializer =
		new DDMFormValuesJSONSerializer();

}