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

import com.liferay.portlet.dynamicdatamapping.BaseDDMTestCase;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;

import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormLayoutJSONSerializerTest extends BaseDDMTestCase {

	@Before
	public void setUp() {
		setUpDDMFormLayoutJSONSerializerUtil();
		setUpJSONFactoryUtil();
	}

	@Test
	public void testDDMFormLayoutSerialization() throws Exception {
		String expectedJSON = read(
			"ddm-form-layout-json-serializer-test-data.json");

		DDMFormLayout ddmFormLayout = createDDMFormLayout();

		String actualJSON = DDMFormLayoutJSONSerializerUtil.serialize(
			ddmFormLayout);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	protected DDMFormLayout createDDMFormLayout() {
		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.addDDMFormLayoutRow(
			createDDMFormLayoutRow(
				createDDMFormLayoutColumns("text1", "text2")));

		ddmFormLayout.addDDMFormLayoutRow(
			createDDMFormLayoutRow(
				createDDMFormLayoutColumns("text3", "text4", "text5")));

		ddmFormLayout.addDDMFormLayoutRow(
			createDDMFormLayoutRow(createDDMFormLayoutColumns("text6")));

		return ddmFormLayout;
	}

	protected void setUpDDMFormLayoutJSONSerializerUtil() {
		DDMFormLayoutJSONSerializerUtil ddmFormLayoutJSONSerializerUtil =
			new DDMFormLayoutJSONSerializerUtil();

		ddmFormLayoutJSONSerializerUtil.setDDMFormLayoutJSONSerializer(
			new DDMFormLayoutJSONSerializerImpl());
	}

}