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

package com.liferay.dynamic.data.mapping.type.grid.internal;

import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Pedro Queiroz
 */
public class GridDDMFormFieldValueAccessorTest {

	@Test
	public void testGetGridValue() {
		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"Grid", new UnlocalizedValue("{\"RowValue\":\"ColumnValue\"}"));

		GridDDMFormFieldValueAccessor gridDDMFormFieldValueAccessor =
			new GridDDMFormFieldValueAccessor();

		gridDDMFormFieldValueAccessor.jsonFactory = new JSONFactoryImpl();

		Assert.assertEquals(
			"{\"RowValue\":\"ColumnValue\"}",
			gridDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, LocaleUtil.US).toString());
	}

}