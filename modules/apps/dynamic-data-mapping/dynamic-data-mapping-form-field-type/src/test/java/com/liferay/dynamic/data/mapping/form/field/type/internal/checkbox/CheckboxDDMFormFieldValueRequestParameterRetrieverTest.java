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

package com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Marcellus Tavares
 */
public class CheckboxDDMFormFieldValueRequestParameterRetrieverTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_setUpJSONFactoryUtil();
	}

	@Test
	public void testGetRequestParameterValueFalse() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		String expectedParameterValue = StringPool.FALSE;

		mockHttpServletRequest.addParameter(
			"ddmFormFieldCheckbox", expectedParameterValue);

		String defaultParameterValue = StringPool.TRUE;

		String actualParameterValue =
			_checkboxDDMFormFieldValueRequestParameterRetriever.get(
				mockHttpServletRequest, "ddmFormFieldCheckbox",
				defaultParameterValue);

		Assert.assertEquals(expectedParameterValue, actualParameterValue);
	}

	@Test
	public void testGetRequestParameterValueTrue() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		String expectedParameterValue = StringPool.TRUE;

		mockHttpServletRequest.addParameter(
			"ddmFormFieldCheckbox", expectedParameterValue);

		String defaultParameterValue = StringPool.FALSE;

		String actualParameterValue =
			_checkboxDDMFormFieldValueRequestParameterRetriever.get(
				mockHttpServletRequest, "ddmFormFieldCheckbox",
				defaultParameterValue);

		Assert.assertEquals(expectedParameterValue, actualParameterValue);
	}

	@Test
	public void testGetValueWithNullRequestParameter() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		String defaultParameterValue = StringPool.TRUE;

		String parameterValue =
			_checkboxDDMFormFieldValueRequestParameterRetriever.get(
				mockHttpServletRequest, "ddmFormFieldCheckbox",
				defaultParameterValue);

		Assert.assertEquals(parameterValue, defaultParameterValue);
	}

	private static void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private final CheckboxDDMFormFieldValueRequestParameterRetriever
		_checkboxDDMFormFieldValueRequestParameterRetriever =
			new CheckboxDDMFormFieldValueRequestParameterRetriever();

}