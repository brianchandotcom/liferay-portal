/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.internal.events;

import com.liferay.cookies.internal.manager.CookiesManagerImpl;
import com.liferay.portal.kernel.cookies.CookiesManager;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

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

	@BeforeClass
	public static void setUpClass() {
		_cookiesManagerServiceRegistration = _bundleContext.registerService(
			CookiesManager.class, new CookiesManagerImpl(), null);
	}

	@AfterClass
	public static void tearDownClass() {
		_cookiesManagerServiceRegistration.unregister();
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

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static ServiceRegistration<CookiesManager>
		_cookiesManagerServiceRegistration;

	private final CookiesPreAction _cookiesPreAction = new CookiesPreAction();

}