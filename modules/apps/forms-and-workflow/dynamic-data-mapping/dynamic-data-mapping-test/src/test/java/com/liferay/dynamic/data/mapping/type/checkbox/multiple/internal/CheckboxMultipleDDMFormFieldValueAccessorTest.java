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

package com.liferay.dynamic.data.mapping.type.checkbox.multiple.internal;

import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 */
public class CheckboxMultipleDDMFormFieldValueAccessorTest {

	@Before
	public void setUp() {
		_checkboxMultipleDDMFormFieldValueAccessor =
			new CheckboxMultipleDDMFormFieldValueAccessor();

		_checkboxMultipleDDMFormFieldValueAccessor.jsonFactory = _jsonFactory;
	}

	@Test
	public void testGetCheckboxMultipleValue() {
		JSONArray expectedJSONArray = createJSONArray("value 1");

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"CheckboxMultiple",
				new UnlocalizedValue(expectedJSONArray.toString()));

		JSONArray actualJSONArray =
			_checkboxMultipleDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals(
			expectedJSONArray.toString(), actualJSONArray.toString());
	}

	@Test
	public void testGetCheckboxMultipleValue1() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"CheckboxMultiple", new UnlocalizedValue("value 1"));

		JSONArray actualJSONArray =
			_checkboxMultipleDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, LocaleUtil.US);

		Assert.assertEquals("value 1", actualJSONArray.getString(0));
	}

	@Test
	public void testGetCheckboxMultipleValue2() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"CheckboxMultiple", new UnlocalizedValue(StringPool.BLANK));

		JSONArray actualJSONArray =
			_checkboxMultipleDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, LocaleUtil.US);

		Assert.assertTrue(actualJSONArray.length() == 0);
	}

	protected JSONArray createJSONArray(String... strings) {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (String string : strings) {
			jsonArray.put(string);
		}

		return jsonArray;
	}

	private CheckboxMultipleDDMFormFieldValueAccessor
		_checkboxMultipleDDMFormFieldValueAccessor;
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}