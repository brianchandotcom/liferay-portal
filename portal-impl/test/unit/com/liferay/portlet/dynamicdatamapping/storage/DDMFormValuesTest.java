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
import com.liferay.portlet.dynamicdatamapping.BaseDDMTestCase;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesTest extends BaseDDMTestCase {

	@Before
	public void setUp() {
		_ddmFormValues = createDDMFormValues(null);

		_ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(_FIELD_NAME, null));
		_ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(_FIELD_NAME, null));
		_ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(_FIELD_NAME, null));
	}

	@Test
	public void testDDMFormFieldValuesMap() {
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			_ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			_FIELD_NAME);

		Assert.assertEquals(3, ddmFormFieldValues.size());
	}

	private static final String _FIELD_NAME = StringUtil.randomString();

	private DDMFormValues _ddmFormValues;

}