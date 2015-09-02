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

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.BaseDDMTestCase;
import com.liferay.dynamic.data.mapping.io.impl.DDMFormFieldTypesJSONSerializerImpl;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldTypeServicesTrackerUtil;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldTypeSettings;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.core.classloader.annotations.PrepareForTest;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest(DDMFormFieldTypeServicesTrackerUtil.class)
public class DDMFormFieldTypesJSONSerializerTest extends BaseDDMTestCase {

	@Before
	public void setUp() {
		setUpDDMFormFieldTypeServicesTrackerUtil();
		setUpDDMFormFieldTypesJSONSerializerUtil();
		setUpDDMFormJSONSerializerUtil();
		setUpJSONFactoryUtil();
		setUpLanguageUtil();
	}

	@Test
	public void testSerializationWithEmptyParameterList() throws Exception {
		List<DDMFormFieldType> ddmFormFieldTypes = Collections.emptyList();

		String actualJSON = DDMFormFieldTypesJSONSerializerUtil.serialize(
			ddmFormFieldTypes);

		Assert.assertEquals("[]", actualJSON);
	}

	@Test
	public void testSerializationWithNonEmptyParameterList() throws Exception {
		List<DDMFormFieldType> ddmFormFieldTypes = new ArrayList<>();

		ddmFormFieldTypes.add(_ddmFormFieldType);

		String actualJSON = DDMFormFieldTypesJSONSerializerUtil.serialize(
			ddmFormFieldTypes);

		JSONAssert.assertEquals(createExpectedJSON(), actualJSON, false);
	}

	protected String createExpectedJSON() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("icon", "my-icon");
		jsonObject.put("name", "Text");
		jsonObject.put("javaScriptClass", "myJavaScriptClass");
		jsonObject.put("javaScriptModule", "myJavaScriptModule");

		jsonArray.put(jsonObject);

		return jsonArray.toString();
	}

	@Override
	protected void setUpDDMFormFieldTypeServicesTrackerUtil() {
		mockStatic(DDMFormFieldTypeServicesTrackerUtil.class);

		whenDDMFormFieldTypeGetDDMFormFieldTypeSettings(
			_ddmFormFieldType, DDMFormFieldTypeSettings.class);
		whenDDMFormFieldTypeGetName(_ddmFormFieldType, "Text");

		when(
			DDMFormFieldTypeServicesTrackerUtil.getDDMFormFieldType(
				Matchers.anyString())
		).thenReturn(
			_ddmFormFieldType
		);

		Map<String, Object> properties = new HashMap<>();

		properties.put("ddm.form.field.type.icon", "my-icon");
		properties.put(
			"ddm.form.field.type.js.class.name", "myJavaScriptClass");
		properties.put("ddm.form.field.type.js.module", "myJavaScriptModule");

		when(
			DDMFormFieldTypeServicesTrackerUtil.getDDMFormFieldTypeProperties(
				Matchers.anyString())
		).thenReturn(
			properties
		);

		whenDDMFormFieldRendererGetTemplateNamespace(
			_ddmFormFieldRenderer, "_templateNamespace_");

		when(
			DDMFormFieldTypeServicesTrackerUtil.getDDMFormFieldRenderer(
				Matchers.anyString())
		).thenReturn(
			_ddmFormFieldRenderer
		);
	}

	protected void setUpDDMFormFieldTypesJSONSerializerUtil() {
		DDMFormFieldTypesJSONSerializerUtil
			ddmFormFieldTypesJSONSerializerUtil =
				new DDMFormFieldTypesJSONSerializerUtil();

		ddmFormFieldTypesJSONSerializerUtil.setDDMFormFieldTypesJSONSerializer(
			new DDMFormFieldTypesJSONSerializerImpl());
	}

	protected void whenDDMFormFieldRendererGetTemplateNamespace(
		DDMFormFieldRenderer ddmFormFieldRenderer,
		String returnTemplateNamespace) {

		when(
			ddmFormFieldRenderer.getTemplateNamespace()
		).thenReturn(
			returnTemplateNamespace
		);
	}

	protected void whenDDMFormFieldTypeGetDDMFormFieldTypeSettings(
		DDMFormFieldType ddmFormFieldType,
		final Class<? extends DDMFormFieldTypeSettings>
			returnDDMFormFieldTypeSettings) {

		when(
			ddmFormFieldType.getDDMFormFieldTypeSettings()
		).then(
			new Answer<Class<? extends DDMFormFieldTypeSettings>>() {

				@Override
				public Class<? extends DDMFormFieldTypeSettings> answer(
						InvocationOnMock invocationOnMock)
					throws Throwable {

					return returnDDMFormFieldTypeSettings;
				}

			}
		);
	}

	protected void whenDDMFormFieldTypeGetName(
		DDMFormFieldType ddmFormFieldType, String returnName) {

		when(
			ddmFormFieldType.getName()
		).thenReturn(
			returnName
		);
	}

	@Mock
	private DDMFormFieldRenderer _ddmFormFieldRenderer;

	@Mock
	private DDMFormFieldType _ddmFormFieldType;

}