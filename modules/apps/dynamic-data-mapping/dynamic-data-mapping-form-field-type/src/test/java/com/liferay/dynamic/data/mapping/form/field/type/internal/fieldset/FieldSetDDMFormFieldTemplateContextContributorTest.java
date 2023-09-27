/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.fieldset;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldTypeSettingsTestCase;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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

		_fieldSetDDMFormFieldTemplateContextContributor.jsonFactory =
			new JSONFactoryImpl();
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

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private final FieldSetDDMFormFieldTemplateContextContributor
		_fieldSetDDMFormFieldTemplateContextContributor =
			new FieldSetDDMFormFieldTemplateContextContributor();

}