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

package com.liferay.dynamic.data.mapping.form.field.type.internal.grid;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Carolina Barbosa
 */
public class GridDDMFormFieldValueRequestParameterRetrieverTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_gridDDMFormFieldValueRequestParameterRetriever =
			new GridDDMFormFieldValueRequestParameterRetriever();

		_gridDDMFormFieldValueRequestParameterRetriever.jsonFactory =
			_jsonFactory;
	}

	@Test
	public void testGetRequestParameterValue() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addParameter(
			_PARAMETER_NAME, "row1;column2", "row2;column1");

		String parameterValue =
			_gridDDMFormFieldValueRequestParameterRetriever.get(
				mockHttpServletRequest, _PARAMETER_NAME, StringPool.BLANK);

		Assert.assertEquals(
			JSONUtil.put(
				"row1", "column2"
			).put(
				"row2", "column1"
			).toString(),
			parameterValue);
	}

	@Test
	public void testGetRequestParameterValueJSONObject() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		String expectedParameterValue = JSONUtil.put(
			"row1", "column2"
		).put(
			"row2", "column1"
		).toString();

		mockHttpServletRequest.addParameter(
			_PARAMETER_NAME, expectedParameterValue);

		String parameterValue =
			_gridDDMFormFieldValueRequestParameterRetriever.get(
				mockHttpServletRequest, _PARAMETER_NAME, StringPool.BLANK);

		Assert.assertEquals(expectedParameterValue, parameterValue);
	}

	@Test
	public void testGetRequestParameterValueWithEmptyString() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addParameter(
			_PARAMETER_NAME, "", "row2;column1");

		String parameterValue =
			_gridDDMFormFieldValueRequestParameterRetriever.get(
				mockHttpServletRequest, _PARAMETER_NAME, StringPool.BLANK);

		Assert.assertEquals(
			JSONUtil.put(
				"row2", "column1"
			).toString(),
			parameterValue);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetRequestParameterWithMalformedString() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addParameter(
			_PARAMETER_NAME, "row1/column2", "row2;column1");

		_gridDDMFormFieldValueRequestParameterRetriever.get(
			mockHttpServletRequest, _PARAMETER_NAME, StringPool.BLANK);
	}

	@Test
	public void testGetValueWithNullRequestParameter() {
		String parameterValue =
			_gridDDMFormFieldValueRequestParameterRetriever.get(
				new MockHttpServletRequest(), _PARAMETER_NAME,
				StringPool.BLANK);

		Assert.assertEquals("{}", parameterValue);
	}

	private static final String _PARAMETER_NAME = "ddmFormFieldGrid";

	private GridDDMFormFieldValueRequestParameterRetriever
		_gridDDMFormFieldValueRequestParameterRetriever;
	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}