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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderOutputParametersSettings;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Renato Rego
 */
public class DDMFormFactoryHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetNamesOfDDMDataProviderInputParametersSettings() {
		_ddmFormFactoryHelper = new DDMFormFactoryHelper(
			DDMDataProviderInputParametersSettings.class);

		Assert.assertArrayEquals(
			new String[] {
				"inputParameterLabel", "inputParameterName",
				"inputParameterType", "inputParameterRequired"
			},
			_getNames());
	}

	@Test
	public void testGetNamesOfDDMDataProviderOutputParametersSettings() {
		_ddmFormFactoryHelper = new DDMFormFactoryHelper(
			DDMDataProviderOutputParametersSettings.class);

		Assert.assertArrayEquals(
			new String[] {
				"outputParameterId", "outputParameterName",
				"outputParameterPath", "outputParameterType"
			},
			_getNames());
	}

	private String[] _getNames() {
		return TransformUtil.transformToArray(
			_ddmFormFactoryHelper.getDDMFormFieldMethods(), Method::getName,
			String.class);
	}

	private DDMFormFactoryHelper _ddmFormFactoryHelper;

}