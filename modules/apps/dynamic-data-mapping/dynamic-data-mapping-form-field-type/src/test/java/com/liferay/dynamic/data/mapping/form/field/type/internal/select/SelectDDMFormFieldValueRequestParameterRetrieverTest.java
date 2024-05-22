/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Pedro Leite
 */
public class SelectDDMFormFieldValueRequestParameterRetrieverTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetRequestParameterValueJSONObject() {
		SelectDDMFormFieldValueRequestParameterRetriever
			selectDDMFormFieldValueRequestParameterRetriever =
				new SelectDDMFormFieldValueRequestParameterRetriever();

		selectDDMFormFieldValueRequestParameterRetriever.jsonFactory =
			new JSONFactoryImpl();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		String expectedParameterValue = JSONUtil.putAll(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString()
		).toString();

		String parameterName = RandomTestUtil.randomString();

		mockHttpServletRequest.addParameter(
			parameterName, expectedParameterValue);

		String parameterValue =
			selectDDMFormFieldValueRequestParameterRetriever.get(
				mockHttpServletRequest, parameterName, StringPool.BLANK);

		Assert.assertEquals(expectedParameterValue, parameterValue);

		String defaultDDMFormFieldParameterValue = JSONUtil.putAll(
			RandomTestUtil.randomString()
		).toString();

		parameterValue = selectDDMFormFieldValueRequestParameterRetriever.get(
			mockHttpServletRequest, StringPool.BLANK,
			defaultDDMFormFieldParameterValue);

		Assert.assertEquals(defaultDDMFormFieldParameterValue, parameterValue);

		themeDisplay.setLifecycleAction(true);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		parameterValue = selectDDMFormFieldValueRequestParameterRetriever.get(
			mockHttpServletRequest, StringPool.BLANK,
			defaultDDMFormFieldParameterValue);

		Assert.assertEquals("[]", parameterValue);
	}

}