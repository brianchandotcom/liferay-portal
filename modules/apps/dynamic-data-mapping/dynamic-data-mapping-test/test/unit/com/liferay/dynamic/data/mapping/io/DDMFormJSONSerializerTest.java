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

import com.liferay.dynamic.data.mapping.io.impl.DDMFormJSONSerializerImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;

import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormJSONSerializerTest extends BaseDDMFormSerializerTestCase {

	@Before
	public void setUp() {
		setUpDDMFormFieldTypeRegistryUtil();
		setUpDDMFormToJSONSerializerUtil();
		setUpJSONFactoryUtil();
		setUpLanguageUtil();
	}

	@Test
	public void testDDMFormSerialization() throws Exception {
		String expectedJSON = read("ddm-form-json-serializer-test-data.json");

		DDMForm ddmForm = createDDMForm();

		String actualJSON = DDMFormJSONSerializerUtil.serialize(ddmForm);

		JSONAssert.assertEquals(expectedJSON, actualJSON, false);
	}

	protected void setUpDDMFormToJSONSerializerUtil() {
		DDMFormJSONSerializerUtil ddmFormJSONSerializerUtil =
			new DDMFormJSONSerializerUtil();

		ddmFormJSONSerializerUtil.setDDMFormJSONSerializer(
			new DDMFormJSONSerializerImpl());
	}

}