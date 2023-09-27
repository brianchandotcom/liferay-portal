/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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