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

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Renato Rego
 */
public class DDMFormFactoryHelperTest {

	@Test
	public void testGetNamesOfDDMDataProviderInputParametersSettings() {
		_ddmFormFactoryHelper = new DDMFormFactoryHelper(
			DDMDataProviderInputParametersSettings.class);

		String[] expectedNames = {
			"inputParameterLabel", "inputParameterName", "inputParameterType",
			"inputParameterRequired"
		};

		Assert.assertArrayEquals(expectedNames, getNames());
	}

	@Test
	public void testGetNamesOfDDMDataProviderOutputParametersSettings() {
		_ddmFormFactoryHelper = new DDMFormFactoryHelper(
			DDMDataProviderOutputParametersSettings.class);

		String[] expectedNames = {
			"outputParameterId", "outputParameterName", "outputParameterPath",
			"outputParameterType"
		};

		Assert.assertArrayEquals(expectedNames, getNames());
	}

	protected String[] getNames() {
		Collection<Method> ddmFormFieldMethods =
			_ddmFormFactoryHelper.getDDMFormFieldMethods();

		Stream<Method> stream = ddmFormFieldMethods.stream();

		return stream.map(
			ddmFormFieldMethod -> ddmFormFieldMethod.getName()
		).toArray(
			String[]::new
		);
	}

	private DDMFormFactoryHelper _ddmFormFactoryHelper;

}