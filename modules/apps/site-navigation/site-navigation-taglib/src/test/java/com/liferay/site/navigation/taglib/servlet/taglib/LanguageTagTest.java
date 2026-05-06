/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.taglib.servlet.taglib;

import com.liferay.petra.lang.ClassLoaderPool;
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
		ClassLoaderPool.register(
			"ShieldedContainerClassLoader", PortalImpl.class.getClassLoader());

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	@Test
	public void testGetFormAction() throws Exception {
		_testGetFormActionOmitsDoAsUserId();
		_testGetFormActionPropagatesDoAsUserId();
	}

	private LanguageTag _createLanguageTag(String doAsUserId) {
		LanguageTag languageTag = new LanguageTag();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setDoAsUserId(doAsUserId);
		themeDisplay.setPathMain("/c");
		themeDisplay.setScopeGroupId(RandomTestUtil.randomLong());

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.isPrivateLayout()
		).thenReturn(
			false
		);

		Mockito.when(
			layout.getLayoutId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		themeDisplay.setLayout(layout);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "/web/guest/home");
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		languageTag.setPageContext(
			new MockPageContext(
				Mockito.mock(ServletContext.class), mockHttpServletRequest));

		return languageTag;
	}

	private void _testGetFormActionOmitsDoAsUserId() throws Exception {
		LanguageTag languageTag = _createLanguageTag(StringPool.BLANK);

		String formAction = ReflectionTestUtil.invoke(
			languageTag, "_getFormAction", new Class<?>[0]);

		Assert.assertFalse(formAction.contains("doAsUserId="));
	}

	private void _testGetFormActionPropagatesDoAsUserId() throws Exception {
		String doAsUserId = RandomTestUtil.randomString();

		LanguageTag languageTag = _createLanguageTag(doAsUserId);

		String formAction = ReflectionTestUtil.invoke(
			languageTag, "_getFormAction", new Class<?>[0]);

		Assert.assertTrue(formAction.contains("doAsUserId=" + doAsUserId));
	}

}