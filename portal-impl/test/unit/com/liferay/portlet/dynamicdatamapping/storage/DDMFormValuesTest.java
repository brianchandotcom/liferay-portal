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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import org.testng.Assert;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesTest {

	@Before
	public void setUp() {
		_ddmFormValues = createDDMFormValues();
	}

	@Test
	public void testDDMFormFieldValuesMap() {
		Map<String, List<Value>> ddmFormFieldValuesMap =
			_ddmFormValues.getDDMFormFieldValuesMap();

		List<Value> values = ddmFormFieldValuesMap.get(_FIELD_NAME);

		Assert.assertEquals(3, values.size());
	}

	protected DDMFormFieldValue createDDMFormFieldValue() {
		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(_FIELD_NAME);

		Value value = new UnlocalizedValue(StringUtil.randomString());

		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	protected DDMFormValues createDDMFormValues() {
		DDMFormValues ddmFormValues = new DDMFormValues();

		ddmFormValues.addDDMFormFieldValue(createDDMFormFieldValue());
		ddmFormValues.addDDMFormFieldValue(createDDMFormFieldValue());
		ddmFormValues.addDDMFormFieldValue(createDDMFormFieldValue());

		return ddmFormValues;
	}

	private final String _FIELD_NAME = StringUtil.randomString();

	private DDMFormValues _ddmFormValues;

}