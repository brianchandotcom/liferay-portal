/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.display.context.helper;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Pedro Leite
 */
public class ObjectRequestHelperTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetDefaultLocale() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.GERMANY
		);

		Mockito.when(
			themeDisplay.getSiteDefaultLocale()
		).thenReturn(
			null
		);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		ObjectRequestHelper objectRequestHelper = new ObjectRequestHelper(
			mockHttpServletRequest);

		Assert.assertEquals(
			LocaleUtil.GERMANY, objectRequestHelper.getDefaultLocale());

		Mockito.when(
			themeDisplay.getSiteDefaultLocale()
		).thenReturn(
			LocaleUtil.US
		);

		Assert.assertEquals(
			LocaleUtil.US, objectRequestHelper.getDefaultLocale());
	}

}