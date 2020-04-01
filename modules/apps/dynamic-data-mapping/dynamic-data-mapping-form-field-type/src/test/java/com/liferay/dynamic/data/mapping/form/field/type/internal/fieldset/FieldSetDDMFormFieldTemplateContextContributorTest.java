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
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.support.membermodification.MemberMatcher;

/**
 * @author Leonardo Barros
 */
public class FieldSetDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTypeSettingsTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpJSONFactory();
	}

	@Test
	public void testCreateRowJSONObject() {
		List<Object> nestedFields = Arrays.<Object>asList(
			HashMapBuilder.<String, Object>put(
				"fieldName", "field0"
			).build(),
			HashMapBuilder.<String, Object>put(
				"fieldName", "field1"
			).build());

		JSONObject rowJSONObject =
			_fieldSetDDMFormFieldTemplateContextContributor.createRowJSONObject(
				nestedFields);

		Assert.assertTrue(rowJSONObject.has("columns"));

		JSONArray columnsJSONArray = rowJSONObject.getJSONArray("columns");

		Assert.assertEquals(2, columnsJSONArray.length());

		JSONObject firstColumnJSONObject = columnsJSONArray.getJSONObject(0);

		Assert.assertTrue(firstColumnJSONObject.has("fields"));

		JSONArray firstColumnFieldsJSONArray =
			firstColumnJSONObject.getJSONArray("fields");

		Assert.assertEquals(1, firstColumnFieldsJSONArray.length());
		Assert.assertEquals("field0", firstColumnFieldsJSONArray.getString(0));

		Assert.assertTrue(firstColumnJSONObject.has("size"));
		Assert.assertEquals(6, firstColumnJSONObject.getInt("size"));

		JSONObject secondColumnJSONObject = columnsJSONArray.getJSONObject(1);

		Assert.assertTrue(secondColumnJSONObject.has("fields"));

		JSONArray secondColumnFieldsJSONArray =
			secondColumnJSONObject.getJSONArray("fields");

		Assert.assertEquals(1, secondColumnFieldsJSONArray.length());
		Assert.assertEquals("field1", secondColumnFieldsJSONArray.getString(0));

		Assert.assertTrue(secondColumnJSONObject.has("size"));
		Assert.assertEquals(6, secondColumnJSONObject.getInt("size"));
	}

	@Test
	public void testGetRowsJSONArray() {
		DDMFormField ddmFormField = DDMFormTestUtil.createDDMFormField(
			"field0", "Field 0", "text", "string", false, false, false);

		Map<String, Object> ddmFormFieldProperties =
			ddmFormField.getProperties();

		ddmFormFieldProperties.put("rows", "");

		JSONArray rowsWithInvisibleFieldJSONArray =
			_fieldSetDDMFormFieldTemplateContextContributor.getRowsJSONArray(
				ddmFormField,
				Arrays.<Object>asList(
					HashMapBuilder.<String, Object>put(
						"fieldName", "field0"
					).build(),
					HashMapBuilder.<String, Object>put(
						"fieldName", "field1"
					).build(),
					HashMapBuilder.<String, Object>put(
						"fieldName", "field2"
					).put(
						"visible", false
					).build()));

		Assert.assertEquals(2, rowsWithInvisibleFieldJSONArray.length());

		JSONArray rowsWithoutInvisibleFieldJSONArray =
			_fieldSetDDMFormFieldTemplateContextContributor.getRowsJSONArray(
				ddmFormField,
				Arrays.<Object>asList(
					HashMapBuilder.<String, Object>put(
						"fieldName", "field0"
					).build(),
					HashMapBuilder.<String, Object>put(
						"fieldName", "field1"
					).build()));

		Assert.assertEquals(1, rowsWithoutInvisibleFieldJSONArray.length());
	}

	protected void setUpJSONFactory() throws Exception {
		MemberMatcher.field(
			FieldSetDDMFormFieldTemplateContextContributor.class, "jsonFactory"
		).set(
			_fieldSetDDMFormFieldTemplateContextContributor, _jsonFactory
		);
	}

	private final FieldSetDDMFormFieldTemplateContextContributor
		_fieldSetDDMFormFieldTemplateContextContributor =
			new FieldSetDDMFormFieldTemplateContextContributor();
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}