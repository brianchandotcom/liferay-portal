/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.rich.text;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Carolina Barbosa
 */
public class RichTextDDMFormFieldValueRequestParameterRetrieverTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_mockHttpServletRequest = new MockHttpServletRequest();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			themeDisplay.getScopeGroupId()
		).thenReturn(
			_SCOPE_GROUP_ID
		);

		Mockito.when(
			themeDisplay.getUserId()
		).thenReturn(
			_USER_ID
		);

		_mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);
	}

	@Test
	public void testGetRequestParameterValue() {
		String parameterValue = RandomTestUtil.randomString();

		_mockHttpServletRequest.addParameter(
			"ddmFormFieldRichText", parameterValue);

		MockedStatic<SanitizerUtil> sanitizerUtilMockedStatic =
			Mockito.mockStatic(SanitizerUtil.class);

		String sanitizedParameterValue = RandomTestUtil.randomString();

		sanitizerUtilMockedStatic.when(
			() -> SanitizerUtil.sanitize(
				_COMPANY_ID, _SCOPE_GROUP_ID, _USER_ID, null, 0,
				ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL, parameterValue,
				null)
		).thenReturn(
			sanitizedParameterValue
		);

		RichTextDDMFormFieldValueRequestParameterRetriever
			richTextDDMFormFieldValueRequestParameterRetriever =
				new RichTextDDMFormFieldValueRequestParameterRetriever();

		Assert.assertEquals(
			sanitizedParameterValue,
			richTextDDMFormFieldValueRequestParameterRetriever.get(
				_mockHttpServletRequest, "ddmFormFieldRichText",
				StringPool.BLANK));
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _SCOPE_GROUP_ID = RandomTestUtil.randomLong();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();

}