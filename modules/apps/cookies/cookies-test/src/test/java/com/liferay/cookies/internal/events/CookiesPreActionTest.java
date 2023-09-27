/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.internal.events;

import com.liferay.cookies.internal.manager.CookiesManagerImpl;
import com.liferay.portal.kernel.cookies.CookiesManager;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Carol Alonso
 * @author Olivér Kecskeméty
 */
public class CookiesPreActionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			CookiesManagerUtil.class, "_cookiesManager", _cookiesManager);
	}

	@Test
	public void testDeleteUserConsentCookieWhenAnyOptionalConsentCookiesAreMissing()
		throws Exception {

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, new ThemeDisplay());

		List<Cookie> mockCookies = new ArrayList<Cookie>() {
			{
				add(
					new Cookie(
						CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, "true"));
				add(
					new Cookie(
						CookiesConstants.NAME_CONSENT_TYPE_NECESSARY, "true"));
				add(
					new Cookie(
						CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
						"false"));
				add(
					new Cookie(
						CookiesConstants.NAME_USER_CONSENT_CONFIGURED, "true"));
			}
		};

		mockHttpServletRequest.setCookies(mockCookies.toArray(new Cookie[0]));

		_cookiesPreAction.run(mockHttpServletRequest, mockHttpServletResponse);

		Cookie[] cookies = mockHttpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 1, cookies.length);

		Cookie userConsentConfiguredCookie = mockHttpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNotNull(userConsentConfiguredCookie);
		Assert.assertEquals(0, userConsentConfiguredCookie.getMaxAge());
		Assert.assertEquals("", userConsentConfiguredCookie.getValue());
	}

	private final CookiesManager _cookiesManager = new CookiesManagerImpl();
	private final CookiesPreAction _cookiesPreAction = new CookiesPreAction();

}