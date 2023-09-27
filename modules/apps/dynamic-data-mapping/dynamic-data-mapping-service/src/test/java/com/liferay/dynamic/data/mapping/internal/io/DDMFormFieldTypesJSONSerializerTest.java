/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerSerializeResponse;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldTypesJSONSerializerTest extends BaseDDMTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpDDMFormFieldTypesJSONSerializer();
	}

	@Test
	public void testSerializationWithEmptyParameterList() {
		List<DDMFormFieldType> ddmFormFieldTypes = Collections.emptyList();

		String actualJSON = serialize(ddmFormFieldTypes);

		Assert.assertEquals("[]", actualJSON);
	}

	@Test
	public void testSerializationWithNonemptyParameterList() {
		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);

		List<DDMFormFieldType> ddmFormFieldTypes = new ArrayList<>();

		DDMFormFieldType ddmFormFieldType = _getMockedDDMFormFieldType();

		ddmFormFieldTypes.add(ddmFormFieldType);

		String actualJSON = serialize(ddmFormFieldTypes);

		JSONAssert.assertEquals(_createExpectedJSON(), actualJSON, false);
	}

	protected DDMFormFieldTypeServicesRegistry
		getMockedDDMFormFieldTypeServicesRegistry() {

		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry =
			Mockito.mock(DDMFormFieldTypeServicesRegistry.class);

		DDMFormFieldRenderer ddmFormFieldRenderer = Mockito.mock(
			DDMFormFieldRenderer.class);

		Mockito.when(
			ddmFormFieldTypeServicesRegistry.getDDMFormFieldRenderer(
				Mockito.anyString())
		).thenReturn(
			ddmFormFieldRenderer
		);

		Mockito.when(
			ddmFormFieldTypeServicesRegistry.getDDMFormFieldTypeProperties(
				Mockito.anyString())
		).thenReturn(
			HashMapBuilder.<String, Object>put(
				"ddm.form.field.type.icon", "my-icon"
			).put(
				"ddm.form.field.type.js.class.name", "myJavaScriptClass"
			).put(
				"ddm.form.field.type.js.module", "myJavaScriptModule"
			).build()
		);

		return ddmFormFieldTypeServicesRegistry;
	}

	protected String serialize(List<DDMFormFieldType> ddmFormFieldTypes) {
		DDMFormFieldTypesSerializerSerializeRequest.Builder builder =
			DDMFormFieldTypesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormFieldTypes);

		DDMFormFieldTypesSerializerSerializeResponse
			ddmFormFieldTypesSerializerSerializeResponse =
				_ddmFormFieldTypesSerializer.serialize(builder.build());

		return ddmFormFieldTypesSerializerSerializeResponse.getContent();
	}

	private String _createExpectedJSON() {
		JSONArray jsonArray = JSONUtil.put(
			JSONUtil.put(
				"icon", "my-icon"
			).put(
				"javaScriptClass", "myJavaScriptClass"
			).put(
				"javaScriptModule", "myJavaScriptModule"
			).put(
				"name", "Text"
			));

		return jsonArray.toString();
	}

	private DDMFormFieldType _getMockedDDMFormFieldType() {
		DDMFormFieldType ddmFormFieldType = Mockito.mock(
			DDMFormFieldType.class);

		_whenDDMFormFieldTypeGetName(ddmFormFieldType, "Text");

		return ddmFormFieldType;
	}

	private void _setUpDDMFormFieldTypesJSONSerializer() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			DDMFormFieldTypesJSONSerializer.class,
			"_ddmFormFieldTypeServicesRegistry");

		field.set(
			_ddmFormFieldTypesSerializer,
			getMockedDDMFormFieldTypeServicesRegistry());

		field = ReflectionUtil.getDeclaredField(
			DDMFormFieldTypesJSONSerializer.class, "_jsonFactory");

		field.set(_ddmFormFieldTypesSerializer, new JSONFactoryImpl());
	}

	private void _whenDDMFormFieldTypeGetName(
		DDMFormFieldType ddmFormFieldType, String returnName) {

		Mockito.when(
			ddmFormFieldType.getName()
		).thenReturn(
			returnName
		);
	}

	private final DDMFormFieldTypesSerializer _ddmFormFieldTypesSerializer =
		new DDMFormFieldTypesJSONSerializer();

}