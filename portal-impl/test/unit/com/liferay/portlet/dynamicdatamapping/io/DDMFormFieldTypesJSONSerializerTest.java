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

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldRenderer;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldTypesJSONSerializerTest extends Mockito {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		setUpDDMFormFieldTypesJSONSerializerUtil();
		setUpJSONFactoryUtil();
	}

	@Test
	public void testSerializationWithEmptyParameterList() {
		List<DDMFormFieldType> ddmFormFieldTypes = Collections.emptyList();

		String actualJSON = DDMFormFieldTypesJSONSerializerUtil.serialize(
			ddmFormFieldTypes);

		Assert.assertEquals("[]", actualJSON);
	}

	@Test
	public void testSerializationWithNonEmptyParameterList() throws Exception {
		List<DDMFormFieldType> ddmFormFieldTypes = new ArrayList<>();

		DDMFormFieldType ddmFormFieldType = mock(DDMFormFieldType.class);

		whenDDMFormFieldTypeGetName(ddmFormFieldType, "Text");

		DDMFormFieldRenderer ddmFormFieldRenderer = mock(
			DDMFormFieldRenderer.class);

		whenDDMFormFieldRendererGetTemplateNamespace(
			ddmFormFieldRenderer, "_templateNamespace_");

		whenDDMFormFieldTypeGetDDMFormFieldRenderer(
			ddmFormFieldType, ddmFormFieldRenderer);

		ddmFormFieldTypes.add(ddmFormFieldType);

		String expectedJSON =
			"[{\"name\": \"Text\", \"templateNamespace\": " +
				"\"_templateNamespace_\"}]";

		String actualJSON = DDMFormFieldTypesJSONSerializerUtil.serialize(
			ddmFormFieldTypes);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	protected void setUpDDMFormFieldTypesJSONSerializerUtil() {
		DDMFormFieldTypesJSONSerializerUtil
			ddmFormFieldTypesJSONSerializerUtil =
				new DDMFormFieldTypesJSONSerializerUtil();

		ddmFormFieldTypesJSONSerializerUtil.setDDMFormFieldTypesJSONSerializer(
			new DDMFormFieldTypesJSONSerializerImpl());
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
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

	protected void whenDDMFormFieldTypeGetName(
		DDMFormFieldType ddmFormFieldType, String returnName) {

		when(
			ddmFormFieldType.getName()
		).thenReturn(
			returnName
		);
	}

}