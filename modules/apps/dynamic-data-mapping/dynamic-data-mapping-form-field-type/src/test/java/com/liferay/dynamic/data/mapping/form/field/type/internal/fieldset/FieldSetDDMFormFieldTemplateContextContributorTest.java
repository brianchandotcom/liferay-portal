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

package com.liferay.dynamic.data.mapping.form.field.type.internal.fieldset;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class FieldSetDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_setUpJSONFactoryUtil();
	}

	@Test
	public void testGetRows() throws Exception {
		String ddmFormLayoutDefinition = _read("ddm-structure-layout.json");

		JSONArray rowsJSONArray =
			_fieldSetDDMFormFieldTemplateContextContributor.getRowsJSONArray(
				ddmFormLayoutDefinition);

		Assert.assertEquals(2, rowsJSONArray.length());

		JSONObject row0JSONObject = rowsJSONArray.getJSONObject(0);

		Assert.assertTrue(row0JSONObject.has("columns"));

		JSONArray columnsJSONArray = row0JSONObject.getJSONArray("columns");

		Assert.assertEquals(2, columnsJSONArray.length());

		JSONObject firstColumnJSONObject = columnsJSONArray.getJSONObject(0);

		Assert.assertTrue(firstColumnJSONObject.has("fields"));

		JSONArray firstColumnFieldsJSONArray =
			firstColumnJSONObject.getJSONArray("fields");

		Assert.assertEquals(1, firstColumnFieldsJSONArray.length());
		Assert.assertEquals("field1", firstColumnFieldsJSONArray.getString(0));

		Assert.assertTrue(firstColumnJSONObject.has("size"));
		Assert.assertEquals(6, firstColumnJSONObject.getInt("size"));

		JSONObject secondColumnJSONObject = columnsJSONArray.getJSONObject(1);

		Assert.assertTrue(secondColumnJSONObject.has("fields"));

		JSONArray secondColumnFieldsJSONArray =
			secondColumnJSONObject.getJSONArray("fields");

		Assert.assertEquals(1, secondColumnFieldsJSONArray.length());
		Assert.assertEquals("field2", secondColumnFieldsJSONArray.getString(0));

		Assert.assertTrue(secondColumnJSONObject.has("size"));
		Assert.assertEquals(6, secondColumnJSONObject.getInt("size"));

		JSONObject row1JSONObject = rowsJSONArray.getJSONObject(1);

		Assert.assertTrue(row0JSONObject.has("columns"));

		columnsJSONArray = row1JSONObject.getJSONArray("columns");

		Assert.assertEquals(1, columnsJSONArray.length());

		firstColumnJSONObject = columnsJSONArray.getJSONObject(0);

		Assert.assertTrue(firstColumnJSONObject.has("fields"));

		firstColumnFieldsJSONArray = firstColumnJSONObject.getJSONArray(
			"fields");

		Assert.assertEquals(3, firstColumnFieldsJSONArray.length());
		Assert.assertEquals("field3", firstColumnFieldsJSONArray.getString(0));
		Assert.assertEquals("field4", firstColumnFieldsJSONArray.getString(1));
		Assert.assertEquals("field5", firstColumnFieldsJSONArray.getString(2));

		Assert.assertTrue(firstColumnJSONObject.has("size"));
		Assert.assertEquals(12, firstColumnJSONObject.getInt("size"));
	}

	private String _read(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private final FieldSetDDMFormFieldTemplateContextContributor
		_fieldSetDDMFormFieldTemplateContextContributor =
			new FieldSetDDMFormFieldTemplateContextContributor();

}