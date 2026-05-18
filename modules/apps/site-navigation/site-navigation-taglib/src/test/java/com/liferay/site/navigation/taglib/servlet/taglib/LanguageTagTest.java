/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.taglib.servlet.taglib;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

import jakarta.servlet.ServletContext;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;

/**
 * @author Georgel Pop
 */
public class LanguageTagTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testGetFormAction() throws Exception {
		String formAction = ReflectionTestUtil.invoke(
			_getLanguageTag(StringPool.BLANK), "_getFormAction",
			new Class<?>[0]);

		Assert.assertFalse(formAction.contains("doAsUserId="));

		String doAsUserId = RandomTestUtil.randomString();

		formAction = ReflectionTestUtil.invoke(
			_getLanguageTag(doAsUserId), "_getFormAction", new Class<?>[0]);

		Assert.assertTrue(formAction.contains("doAsUserId=" + doAsUserId));
	}

	private LanguageTag _getLanguageTag(String doAsUserId) {
		LanguageTag languageTag = new LanguageTag();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, RandomTestUtil.randomString());

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getDoAsUserId()
		).thenReturn(
			doAsUserId
		);

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			themeDisplay.getLayout()
		).thenReturn(
			layout
		);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		languageTag.setPageContext(
			new MockPageContext(
				Mockito.mock(ServletContext.class), mockHttpServletRequest));

		return languageTag;
	}

}