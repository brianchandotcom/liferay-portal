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
import com.liferay.portlet.dynamicdatamapping.BaseDDMTestCase;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldRenderer;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldTypesJSONSerializerTest extends BaseDDMTestCase {

	@Before
	public void setUp() {
		setUpDDMFormFieldTypeRegistryUtil();
		setUpDDMFormFieldTypesJSONSerializerUtil();
		setUpDDMFormJSONSerializerUtil();
		setUpJSONFactoryUtil();
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

		DDMFormFieldType ddmFormFieldType = mock(DDMFormFieldType.class);

		whenDDMFormFieldTypeGetIcon(ddmFormFieldType, "my-icon");
		whenDDMFormFieldTypeGetName(ddmFormFieldType, "Text");
		whenDDMFormFieldTypeGetJavaScriptClass(
			ddmFormFieldType, "myJavaScriptClass");
		whenDDMFormFieldTypeGetJavaScriptModule(
			ddmFormFieldType, "myJavaScriptModule");
		whenDDMFormFieldTypeGetDDMFormFieldTypeSettings(
			ddmFormFieldType, DDMFormFieldTypeSettings.class);

		DDMFormFieldRenderer ddmFormFieldRenderer = mock(
			DDMFormFieldRenderer.class);

		whenDDMFormFieldTypeGetDDMFormFieldRenderer(
			ddmFormFieldType, ddmFormFieldRenderer);

		whenDDMFormFieldRendererGetTemplateNamespace(
			ddmFormFieldRenderer, "_templateNamespace_");

		ddmFormFieldTypes.add(ddmFormFieldType);

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

	protected void whenDDMFormFieldTypeGetDDMFormFieldRenderer(
		DDMFormFieldType ddmFormFieldType,
		DDMFormFieldRenderer returnDDMFormFieldRenderer) {

		when(
			ddmFormFieldType.getDDMFormFieldRenderer()
		).thenReturn(
			returnDDMFormFieldRenderer
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

	protected void whenDDMFormFieldTypeGetIcon(
		DDMFormFieldType ddmFormFieldType, String returnIcon) {

		when(
			ddmFormFieldType.getIcon()
		).thenReturn(
			returnIcon
		);
	}

	protected void whenDDMFormFieldTypeGetJavaScriptClass(
		DDMFormFieldType ddmFormFieldType,
		String returnDDMFormFieldTypeJavaScriptClass) {

		when(
			ddmFormFieldType.getDDMFormFieldTypeJavaScriptClass()
		).thenReturn(
			returnDDMFormFieldTypeJavaScriptClass
		);
	}

	protected void whenDDMFormFieldTypeGetJavaScriptModule(
		DDMFormFieldType ddmFormFieldType,
		String returnDDMFormFieldTypeJavaScriptModule) {

		when(
			ddmFormFieldType.getDDMFormFieldTypeJavaScriptModule()
		).thenReturn(
			returnDDMFormFieldTypeJavaScriptModule
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

}