/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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

		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldValueRequestParameterRetriever, "_jsonFactory",
			new JSONFactoryImpl());

		String ddmFormFieldParameterName = RandomTestUtil.randomString();

		Assert.assertEquals(
			"[]",
			selectDDMFormFieldValueRequestParameterRetriever.get(
				_createMockHttpServletRequest(
					ddmFormFieldParameterName, null, false),
				ddmFormFieldParameterName, null));
		Assert.assertEquals(
			"[]",
			selectDDMFormFieldValueRequestParameterRetriever.get(
				_createMockHttpServletRequest(
					ddmFormFieldParameterName, null, false),
				ddmFormFieldParameterName, "[]"));

		String defaultDDMFormFieldParameterValue = JSONUtil.putAll(
			RandomTestUtil.randomString(), RandomTestUtil.randomString()
		).toString();

		Assert.assertEquals(
			"[]",
			selectDDMFormFieldValueRequestParameterRetriever.get(
				_createMockHttpServletRequest(
					ddmFormFieldParameterName, RandomTestUtil.randomString(),
					RandomTestUtil.randomBoolean()),
				ddmFormFieldParameterName, defaultDDMFormFieldParameterValue));
		Assert.assertEquals(
			"[]",
			selectDDMFormFieldValueRequestParameterRetriever.get(
				_createMockHttpServletRequest(
					ddmFormFieldParameterName, null, true),
				ddmFormFieldParameterName, defaultDDMFormFieldParameterValue));
		Assert.assertEquals(
			defaultDDMFormFieldParameterValue,
			selectDDMFormFieldValueRequestParameterRetriever.get(
				_createMockHttpServletRequest(
					ddmFormFieldParameterName, null, false),
				ddmFormFieldParameterName, defaultDDMFormFieldParameterValue));

		String ddmFormFieldParameterValue = JSONUtil.putAll(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString()
		).toString();

		Assert.assertEquals(
			ddmFormFieldParameterValue,
			selectDDMFormFieldValueRequestParameterRetriever.get(
				_createMockHttpServletRequest(
					ddmFormFieldParameterName, ddmFormFieldParameterValue,
					RandomTestUtil.randomBoolean()),
				ddmFormFieldParameterName, defaultDDMFormFieldParameterValue));
	}

	private MockHttpServletRequest _createMockHttpServletRequest(
		String ddmFormFieldParameterName, String ddmFormFieldParameterValue,
		boolean lifecycleAction) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.addParameter(
			ddmFormFieldParameterName, ddmFormFieldParameterValue);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLifecycleAction(lifecycleAction);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

}