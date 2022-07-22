/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.cookies.internal.events;

import com.liferay.cookies.configuration.CookiesPreferenceHandlingConfiguration;
import com.liferay.cookies.internal.manager.CookiesManagerImpl;
import com.liferay.portal.kernel.cookies.CookiesManager;
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Carol Alonso
 */
public class CookiesPreActionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCookieManagementDisabledWithAllCookies() throws Exception {
		_setUp(false, false);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(
			_getHttpServletRequest(true, true, true), mockHttpServletResponse);

		Cookie[] cookies = mockHttpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 5, cookies.length);

		Cookie consentTypeCookie = mockHttpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNotNull(consentTypeCookie);
		Assert.assertEquals(0, consentTypeCookie.getMaxAge());
		Assert.assertNull(consentTypeCookie.getValue());

		Cookie userConsentConfiguredCookie = mockHttpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNotNull(userConsentConfiguredCookie);
		Assert.assertEquals(0, userConsentConfiguredCookie.getMaxAge());
		Assert.assertNull(userConsentConfiguredCookie.getValue());

		for (String cookieName : _OPTIONAL_COOKIE_NAMES) {
			Cookie cookie = mockHttpServletResponse.getCookie(cookieName);

			Assert.assertNotNull(cookie);
			Assert.assertEquals(0, cookie.getMaxAge());
			Assert.assertNull(cookie.getValue());
		}
	}

	@Test
	public void testCookieManagementDisabledWithConsentCookies()
		throws Exception {

		_setUp(false, false);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(
			_getHttpServletRequest(true, true, false), mockHttpServletResponse);

		Cookie[] cookies = mockHttpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 4, cookies.length);

		Cookie consentTypeCookie = mockHttpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNotNull(consentTypeCookie);
		Assert.assertEquals(0, consentTypeCookie.getMaxAge());
		Assert.assertNull(consentTypeCookie.getValue());

		Cookie userConsentConfiguredCookie = mockHttpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNull(userConsentConfiguredCookie);

		for (String cookieName : _OPTIONAL_COOKIE_NAMES) {
			Cookie cookie = mockHttpServletResponse.getCookie(cookieName);

			Assert.assertNotNull(cookie);
			Assert.assertEquals(0, cookie.getMaxAge());
			Assert.assertNull(cookie.getValue());
		}
	}

	@Test
	public void testCookieManagementDisabledWithoutCookies() throws Exception {
		_setUp(false, false);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(
			_getHttpServletRequest(false, false, false),
			mockHttpServletResponse);

		Cookie[] cookies = mockHttpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 0, cookies.length);
	}

	@Test
	public void testExplicitModeWithAllCookies() throws Exception {
		_setUp(true, true);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(
			_getHttpServletRequest(true, true, true), mockHttpServletResponse);

		Cookie[] cookies = mockHttpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 0, cookies.length);
	}

	@Test
	public void testExplicitModeWithConsentCookies() throws Exception {
		_setUp(true, true);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(
			_getHttpServletRequest(true, true, false), mockHttpServletResponse);

		Cookie[] cookies = mockHttpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 3, cookies.length);

		Cookie consentTypeCookie = mockHttpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNull(consentTypeCookie);

		Cookie userConsentConfiguredCookie = mockHttpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNull(userConsentConfiguredCookie);

		for (String cookieName : _OPTIONAL_COOKIE_NAMES) {
			Cookie cookie = mockHttpServletResponse.getCookie(cookieName);

			Assert.assertNotNull(cookie);
			Assert.assertNotEquals(0, cookie.getMaxAge());
			Assert.assertEquals("false", cookie.getValue());
		}
	}

	@Test
	public void testExplicitModeWithoutCookies() throws Exception {
		_setUp(true, true);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(
			_getHttpServletRequest(false, false, false),
			mockHttpServletResponse);

		Cookie[] cookies = mockHttpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 4, cookies.length);

		Cookie consentTypeCookie = mockHttpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNotNull(consentTypeCookie);
		Assert.assertNotEquals(0, consentTypeCookie.getMaxAge());
		Assert.assertEquals("true", consentTypeCookie.getValue());

		Cookie userConsentConfiguredCookie = mockHttpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNull(userConsentConfiguredCookie);

		for (String cookieName : _OPTIONAL_COOKIE_NAMES) {
			Cookie cookie = mockHttpServletResponse.getCookie(cookieName);

			Assert.assertNotNull(cookie);
			Assert.assertNotEquals(0, cookie.getMaxAge());
			Assert.assertEquals("false", cookie.getValue());
		}
	}

	@Test
	public void testImplicitModeWithAllCookies() throws Exception {
		_setUp(true, false);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(
			_getHttpServletRequest(true, true, true), mockHttpServletResponse);

		Cookie[] cookies = mockHttpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 0, cookies.length);
	}

	@Test
	public void testImplicitModeWithConsentCookies() throws Exception {
		_setUp(true, false);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(
			_getHttpServletRequest(true, true, false), mockHttpServletResponse);

		Cookie[] cookies = mockHttpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 0, cookies.length);
	}

	@Test
	public void testImplicitModeWithoutCookies() throws Exception {
		_setUp(true, false);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(
			_getHttpServletRequest(false, false, false),
			mockHttpServletResponse);

		Cookie[] cookies = mockHttpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 4, cookies.length);

		Cookie consentTypeCookie = mockHttpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNotNull(consentTypeCookie);
		Assert.assertNotEquals(0, consentTypeCookie.getMaxAge());
		Assert.assertEquals("true", consentTypeCookie.getValue());

		Cookie userConsentConfiguredCookie = mockHttpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNull(userConsentConfiguredCookie);

		for (String cookieName : _OPTIONAL_COOKIE_NAMES) {
			Cookie cookie = mockHttpServletResponse.getCookie(cookieName);

			Assert.assertNotNull(cookie);
			Assert.assertNotEquals(0, cookie.getMaxAge());
			Assert.assertEquals("true", cookie.getValue());
		}
	}

	private HttpServletRequest _getHttpServletRequest(
		boolean consentTypeNecessary, boolean consentTypeOptional,
		boolean consentTypeUser) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, new ThemeDisplay());

		List<Cookie> cookies = new ArrayList<>();

		if (consentTypeNecessary) {
			cookies.add(
				new Cookie(
					CookiesConstants.NAME_CONSENT_TYPE_NECESSARY, "true"));
		}

		if (consentTypeOptional) {
			for (String cookieName : _OPTIONAL_COOKIE_NAMES) {
				cookies.add(new Cookie(cookieName, "true"));
			}
		}

		if (consentTypeUser) {
			cookies.add(
				new Cookie(
					CookiesConstants.NAME_USER_CONSENT_CONFIGURED, "true"));
		}

		mockHttpServletRequest.setCookies(cookies.toArray(new Cookie[0]));

		return mockHttpServletRequest;
	}

	private void _setUp(Boolean enabled, Boolean explicitConsentMode)
		throws Exception {

		Mockito.when(
			_configurationProvider.getGroupConfiguration(
				CookiesPreferenceHandlingConfiguration.class, 0)
		).thenReturn(
			new CookiesPreferenceHandlingConfiguration() {

				@Override
				public boolean enabled() {
					return enabled;
				}

				@Override
				public boolean explicitConsentMode() {
					return explicitConsentMode;
				}

			}
		);

		ReflectionTestUtil.setFieldValue(
			_cookiesPreAction, "_configurationProvider",
			_configurationProvider);

		ReflectionTestUtil.setFieldValue(_cookiesManager, "_portal", _portal);

		ReflectionTestUtil.setFieldValue(
			CookiesManagerUtil.class, "_cookiesManager", _cookiesManager);

		Mockito.when(
			_portal.isSecure(Mockito.any())
		).thenReturn(
			false
		);
	}

	private static final String[] _OPTIONAL_COOKIE_NAMES = {
		CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL,
		CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
		CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION
	};

	private final ConfigurationProvider _configurationProvider = Mockito.mock(
		ConfigurationProvider.class);
	private final CookiesManager _cookiesManager = new CookiesManagerImpl();
	private final CookiesPreAction _cookiesPreAction = new CookiesPreAction();
	private final Portal _portal = Mockito.mock(Portal.class);

}