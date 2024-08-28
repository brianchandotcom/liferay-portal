/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.web.internal.display.context;

import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class TranslateDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpLanguageUtil();
		_setUpPortalUtil();
	}

	@Test
	public void testRedirect() {
		String redirect = _getRedirect("javascript:alert(document.domain)");

		Assert.assertNull(redirect);

		redirect = _getRedirect("/myexample1.png");

		Assert.assertEquals("/myexample1.png", redirect);
	}

	private MockHttpServletRequest _getMockHttpServletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, Mockito.mock(ThemeDisplay.class));

		return mockHttpServletRequest;
	}

	private String _getRedirect(String value) {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest();

		mockHttpServletRequest.setParameter("redirect", value);

		mockLiferayPortletActionRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST, mockHttpServletRequest);

		TranslateDisplayContext translateDisplayContext =
			new TranslateDisplayContext(
				new ArrayList<>(), new ArrayList<>(), () -> true,
				RandomTestUtil.randomString(), RandomTestUtil.randomLong(),
				Mockito.mock(InfoForm.class), mockLiferayPortletActionRequest,
				new MockLiferayPortletRenderResponse(), null,
				RandomTestUtil.randomLong(),
				Mockito.mock(InfoItemFieldValues.class),
				LanguageUtil.getLanguageId(LocaleUtil.ENGLISH),
				Mockito.mock(InfoItemFieldValues.class),
				LanguageUtil.getLanguageId(LocaleUtil.SPAIN),
				Mockito.mock(TranslationInfoFieldChecker.class));

		Assert.assertNotNull(translateDisplayContext);

		return ReflectionTestUtil.invoke(
			translateDisplayContext, "_getRedirect", new Class<?>[0]);
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

}