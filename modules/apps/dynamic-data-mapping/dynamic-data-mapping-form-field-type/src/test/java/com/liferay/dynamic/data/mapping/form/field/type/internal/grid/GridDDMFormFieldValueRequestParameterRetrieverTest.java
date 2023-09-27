/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Test
	public void testGetRequestParameterValueWithOneOption() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addParameter(_PARAMETER_NAME, "row1;column1");

		String parameterValue =
			_gridDDMFormFieldValueRequestParameterRetriever.get(
				mockHttpServletRequest, _PARAMETER_NAME, StringPool.BLANK);

		Assert.assertEquals(
			JSONUtil.put(
				"row1", "column1"
			).toString(),
			parameterValue);
	}

	@Test
	public void testGetRequestParameterWithMalformedString() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addParameter(
			_PARAMETER_NAME, "row1/column2", "row2;column1");

		String parameterValue =
			_gridDDMFormFieldValueRequestParameterRetriever.get(
				mockHttpServletRequest, _PARAMETER_NAME, StringPool.BLANK);

		Assert.assertEquals(
			JSONUtil.put(
				"row2", "column1"
			).toString(),
			parameterValue);
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