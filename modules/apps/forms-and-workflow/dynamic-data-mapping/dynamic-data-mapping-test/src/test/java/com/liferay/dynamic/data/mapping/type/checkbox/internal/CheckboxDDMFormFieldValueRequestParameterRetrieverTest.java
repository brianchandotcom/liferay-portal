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

package com.liferay.dynamic.data.mapping.type.checkbox.internal;

import com.liferay.petra.string.StringPool;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Marcellus Tavares
 */
@RunWith(PowerMockRunner.class)
public class CheckboxDDMFormFieldValueRequestParameterRetrieverTest {

	@Test
	public void testGetRequestParameterValueFalse() {
		MockHttpServletRequest request = new MockHttpServletRequest();

		String defaultParameterValue = StringPool.TRUE;
		String expectedParameterValue = StringPool.FALSE;

		request.addParameter("ddmFormFieldCheckbox", expectedParameterValue);

		String actualParameterValue =
			_checkboxDDMFormFieldValueRequestParameterRetriever.get(
				request, "ddmFormFieldCheckbox", defaultParameterValue);

		Assert.assertEquals(expectedParameterValue, actualParameterValue);
	}

	@Test
	public void testGetRequestParameterValueTrue() {
		MockHttpServletRequest request = new MockHttpServletRequest();

		String defaultParameterValue = StringPool.FALSE;
		String expectedParameterValue = StringPool.TRUE;

		request.addParameter("ddmFormFieldCheckbox", expectedParameterValue);

		String actualParameterValue =
			_checkboxDDMFormFieldValueRequestParameterRetriever.get(
				request, "ddmFormFieldCheckbox", defaultParameterValue);

		Assert.assertEquals(expectedParameterValue, actualParameterValue);
	}

	@Test
	public void testGetValueWithNullRequestParameter() {
		MockHttpServletRequest request = new MockHttpServletRequest();

		String defaultParameterValue = StringPool.TRUE;

		String parameterValue =
			_checkboxDDMFormFieldValueRequestParameterRetriever.get(
				request, "ddmFormFieldCheckbox", defaultParameterValue);

		Assert.assertEquals(parameterValue, defaultParameterValue);
	}

	private final CheckboxDDMFormFieldValueRequestParameterRetriever
		_checkboxDDMFormFieldValueRequestParameterRetriever =
			new CheckboxDDMFormFieldValueRequestParameterRetriever();

}