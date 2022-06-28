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
import com.liferay.petra.string.StringPool;
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
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Carol Alonso
 */
@RunWith(MockitoJUnitRunner.class)
public class CookiesPreActionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCookieManagementDisabledWithAllCookies() throws Exception {
		_setCookieManagementConfiguration(false, false);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			true, true, true);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] cookies = httpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 5, cookies.length);

		Cookie userConsentConfiguredCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNotNull(userConsentConfiguredCookie);
		Assert.assertNull(userConsentConfiguredCookie.getValue());
		Assert.assertEquals(0, userConsentConfiguredCookie.getMaxAge());

		Cookie necessaryCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNotNull(necessaryCookie);
		Assert.assertNull(necessaryCookie.getValue());
		Assert.assertEquals(0, necessaryCookie.getMaxAge());

		for (String cookieName : _OPTIONAL_COOKIES) {
			Cookie cookie = httpServletResponse.getCookie(cookieName);

			Assert.assertNotNull(cookie);
			Assert.assertNull(cookie.getValue());
			Assert.assertEquals(0, cookie.getMaxAge());
		}
	}

	@Test
	public void testCookieManagementDisabledWithConsentCookies()
		throws Exception {

		_setCookieManagementConfiguration(false, false);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			true, true, false);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] cookies = httpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 4, cookies.length);

		Cookie userConsentConfiguredCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNull(userConsentConfiguredCookie);

		Cookie necessaryCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNotNull(necessaryCookie);
		Assert.assertNull(necessaryCookie.getValue());
		Assert.assertEquals(0, necessaryCookie.getMaxAge());

		for (String cookieName : _OPTIONAL_COOKIES) {
			Cookie cookie = httpServletResponse.getCookie(cookieName);

			Assert.assertNotNull(cookie);
			Assert.assertNull(cookie.getValue());
			Assert.assertEquals(0, cookie.getMaxAge());
		}
	}

	@Test
	public void testCookieManagementDisabledWithoutCookies() throws Exception {
		_setCookieManagementConfiguration(false, false);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			false, false, false);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] cookies = httpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 0, cookies.length);
	}

	@Test
	public void testExplicitModeWithAllCookies() throws Exception {
		_setCookieManagementConfiguration(true, true);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			true, true, true);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] cookies = httpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 0, cookies.length);
	}

	@Test
	public void testExplicitModeWithConsentCookies() throws Exception {
		_setCookieManagementConfiguration(true, true);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			true, true, false);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] cookies = httpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 3, cookies.length);

		Cookie userConsentConfiguredCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNull(userConsentConfiguredCookie);

		Cookie necessaryCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNull(necessaryCookie);

		for (String cookieName : _OPTIONAL_COOKIES) {
			Cookie cookie = httpServletResponse.getCookie(cookieName);

			Assert.assertNotNull(cookie);
			Assert.assertEquals("false", cookie.getValue());
			Assert.assertNotEquals(0, cookie.getMaxAge());
		}
	}

	@Test
	public void testExplicitModeWithoutCookies() throws Exception {
		_setCookieManagementConfiguration(true, true);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			false, false, false);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] cookies = httpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 4, cookies.length);

		Cookie userConsentConfiguredCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNull(userConsentConfiguredCookie);

		Cookie necessaryCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNotNull(necessaryCookie);
		Assert.assertEquals("true", necessaryCookie.getValue());
		Assert.assertNotEquals(0, necessaryCookie.getMaxAge());

		for (String cookieName : _OPTIONAL_COOKIES) {
			Cookie cookie = httpServletResponse.getCookie(cookieName);

			Assert.assertNotNull(cookie);
			Assert.assertEquals("false", cookie.getValue());
			Assert.assertNotEquals(0, cookie.getMaxAge());
		}
	}

	@Test
	public void testImplicitModeWithAllCookies() throws Exception {
		_setCookieManagementConfiguration(true, false);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			true, true, true);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] cookies = httpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 0, cookies.length);
	}

	@Test
	public void testImplicitModeWithConsentCookies() throws Exception {
		_setCookieManagementConfiguration(true, false);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			true, true, false);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] cookies = httpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 0, cookies.length);
	}

	@Test
	public void testImplicitModeWithoutCookies() throws Exception {
		_setCookieManagementConfiguration(true, false);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			false, false, false);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] cookies = httpServletResponse.getCookies();

		Assert.assertEquals(Arrays.toString(cookies), 4, cookies.length);

		Cookie userConsentConfiguredCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNull(userConsentConfiguredCookie);

		Cookie necessaryCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNotNull(necessaryCookie);
		Assert.assertEquals("true", necessaryCookie.getValue());
		Assert.assertNotEquals(0, necessaryCookie.getMaxAge());

		for (String cookieName : _OPTIONAL_COOKIES) {
			Cookie cookie = httpServletResponse.getCookie(cookieName);

			Assert.assertNotNull(cookie);
			Assert.assertEquals("true", cookie.getValue());
			Assert.assertNotEquals(0, cookie.getMaxAge());
		}
	}

	private HttpServletRequest _getHttpServletRequest(
		boolean necessaryConsent, boolean optionalConsent,
		boolean userConsent) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setScopeGroupId(0);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		List<Cookie> cookies = new ArrayList<>();

		if (necessaryConsent) {
			cookies.add(
				new Cookie(
					CookiesConstants.NAME_CONSENT_TYPE_NECESSARY, "true"));
		}

		if (optionalConsent) {
			for (String cookieName : _OPTIONAL_COOKIES) {
				cookies.add(new Cookie(cookieName, "true"));
			}
		}

		if (userConsent) {
			cookies.add(
				new Cookie(
					CookiesConstants.NAME_USER_CONSENT_CONFIGURED, "true"));
		}

		mockHttpServletRequest.setCookies(cookies.toArray(new Cookie[0]));
		mockHttpServletRequest.setPathInfo(StringPool.BLANK);

		return mockHttpServletRequest;
	}

	private void _setCookieManagementConfiguration(
			Boolean enabled, Boolean explicitConsentMode)
		throws Exception {

		ReflectionTestUtil.setFieldValue(_cookiesManager, "_portal", _portal);

		ReflectionTestUtil.setFieldValue(
			CookiesManagerUtil.class, "_cookiesManager", _cookiesManager);

		Mockito.when(
			_portal.isSecure(Mockito.any())
		).thenReturn(
			false
		);

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
	}

	private static final String[] _OPTIONAL_COOKIES = {
		CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL,
		CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
		CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION
	};

	@Mock
	private ConfigurationProvider _configurationProvider;

	private final CookiesManager _cookiesManager = new CookiesManagerImpl();
	private final CookiesPreAction _cookiesPreAction = new CookiesPreAction();

	@Mock
	private Portal _portal;

}