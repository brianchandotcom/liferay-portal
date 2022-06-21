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
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
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

	public void setCookieManagementConfiguration(
			Boolean enabled, Boolean explicitConsentMode)
		throws ConfigurationException {

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

	@Test
	public void testCookieManagementDisabledWithAllCookies() throws Exception {
		setCookieManagementConfiguration(false, false);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			true, true, true);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] responseCookies = httpServletResponse.getCookies();

		Assert.assertEquals(
			Arrays.toString(responseCookies), 5, responseCookies.length);

		Cookie userConsentConfiguredCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNotNull(userConsentConfiguredCookie);
		Assert.assertNull(userConsentConfiguredCookie.getValue());
		Assert.assertEquals(0, userConsentConfiguredCookie.getMaxAge());

		Cookie consentTypeNecessaryCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNotNull(consentTypeNecessaryCookie);
		Assert.assertNull(consentTypeNecessaryCookie.getValue());
		Assert.assertEquals(0, consentTypeNecessaryCookie.getMaxAge());

		String[] optionalConsentCookieNames = {
			CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
			CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
			CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL
		};

		for (String optionalCookieName : optionalConsentCookieNames) {
			Cookie optionalCookie = httpServletResponse.getCookie(
				optionalCookieName);

			Assert.assertNotNull(optionalCookie);
			Assert.assertNull(optionalCookie.getValue());
			Assert.assertEquals(0, optionalCookie.getMaxAge());
		}
	}

	@Test
	public void testCookieManagementDisabledWithConsentCookies()
		throws Exception {

		setCookieManagementConfiguration(false, false);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			true, true, false);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] responseCookies = httpServletResponse.getCookies();

		Assert.assertEquals(
			Arrays.toString(responseCookies), 4, responseCookies.length);

		Cookie userConsentConfiguredCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNull(userConsentConfiguredCookie);

		Cookie consentTypeNecessaryCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNotNull(consentTypeNecessaryCookie);
		Assert.assertNull(consentTypeNecessaryCookie.getValue());
		Assert.assertEquals(0, consentTypeNecessaryCookie.getMaxAge());

		String[] optionalConsentCookieNames = {
			CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
			CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
			CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL
		};

		for (String optionalCookieName : optionalConsentCookieNames) {
			Cookie optionalCookie = httpServletResponse.getCookie(
				optionalCookieName);

			Assert.assertNotNull(optionalCookie);
			Assert.assertNull(optionalCookie.getValue());
			Assert.assertEquals(0, optionalCookie.getMaxAge());
		}
	}

	@Test
	public void testCookieManagementDisabledWithoutCookies() throws Exception {
		setCookieManagementConfiguration(false, false);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			false, false, false);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] responseCookies = httpServletResponse.getCookies();

		Assert.assertEquals(
			Arrays.toString(responseCookies), 0, responseCookies.length);
	}

	@Test
	public void testExplicitModeWithAllCookies() throws Exception {
		setCookieManagementConfiguration(true, true);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			true, true, true);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] responseCookies = httpServletResponse.getCookies();

		Assert.assertEquals(
			Arrays.toString(responseCookies), 0, responseCookies.length);
	}

	@Test
	public void testExplicitModeWithConsentCookies() throws Exception {
		setCookieManagementConfiguration(true, true);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			true, true, false);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] responseCookies = httpServletResponse.getCookies();

		Assert.assertEquals(
			Arrays.toString(responseCookies), 3, responseCookies.length);

		Cookie userConsentConfiguredCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNull(userConsentConfiguredCookie);

		Cookie consentTypeNecessaryCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNull(consentTypeNecessaryCookie);

		String[] optionalConsentCookieNames = {
			CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
			CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
			CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL
		};

		for (String optionalCookieName : optionalConsentCookieNames) {
			Cookie optionalCookie = httpServletResponse.getCookie(
				optionalCookieName);

			Assert.assertNotNull(optionalCookie);
			Assert.assertEquals("false", optionalCookie.getValue());
			Assert.assertNotEquals(0, optionalCookie.getMaxAge());
		}
	}

	@Test
	public void testExplicitModeWithoutCookies() throws Exception {
		setCookieManagementConfiguration(true, true);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			false, false, false);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] responseCookies = httpServletResponse.getCookies();

		Assert.assertEquals(
			Arrays.toString(responseCookies), 4, responseCookies.length);

		Cookie userConsentConfiguredCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNull(userConsentConfiguredCookie);

		Cookie consentTypeNecessaryCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNotNull(consentTypeNecessaryCookie);
		Assert.assertEquals("true", consentTypeNecessaryCookie.getValue());
		Assert.assertNotEquals(0, consentTypeNecessaryCookie.getMaxAge());

		String[] optionalConsentCookieNames = {
			CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
			CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
			CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL
		};

		for (String optionalCookieName : optionalConsentCookieNames) {
			Cookie optionalCookie = httpServletResponse.getCookie(
				optionalCookieName);

			Assert.assertNotNull(optionalCookie);
			Assert.assertEquals("false", optionalCookie.getValue());
			Assert.assertNotEquals(0, optionalCookie.getMaxAge());
		}
	}

	@Test
	public void testImplicitModeWithAllCookies() throws Exception {
		setCookieManagementConfiguration(true, false);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			true, true, true);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] responseCookies = httpServletResponse.getCookies();

		Assert.assertEquals(
			Arrays.toString(responseCookies), 0, responseCookies.length);
	}

	@Test
	public void testImplicitModeWithConsentCookies() throws Exception {
		setCookieManagementConfiguration(true, false);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			true, true, false);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] responseCookies = httpServletResponse.getCookies();

		Assert.assertEquals(
			Arrays.toString(responseCookies), 0, responseCookies.length);
	}

	@Test
	public void testImplicitModeWithoutCookies() throws Exception {
		setCookieManagementConfiguration(true, false);

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			false, false, false);
		MockHttpServletResponse httpServletResponse =
			new MockHttpServletResponse();

		_cookiesPreAction.run(httpServletRequest, httpServletResponse);

		Cookie[] responseCookies = httpServletResponse.getCookies();

		Assert.assertEquals(
			Arrays.toString(responseCookies), 4, responseCookies.length);

		Cookie userConsentConfiguredCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		Assert.assertNull(userConsentConfiguredCookie);

		Cookie consentTypeNecessaryCookie = httpServletResponse.getCookie(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);

		Assert.assertNotNull(consentTypeNecessaryCookie);
		Assert.assertEquals("true", consentTypeNecessaryCookie.getValue());
		Assert.assertNotEquals(0, consentTypeNecessaryCookie.getMaxAge());

		String[] optionalConsentCookieNames = {
			CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
			CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
			CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL
		};

		for (String optionalCookieName : optionalConsentCookieNames) {
			Cookie optionalCookie = httpServletResponse.getCookie(
				optionalCookieName);

			Assert.assertNotNull(optionalCookie);
			Assert.assertEquals("true", optionalCookie.getValue());
			Assert.assertNotEquals(0, optionalCookie.getMaxAge());
		}
	}

	private HttpServletRequest _getHttpServletRequest(
		boolean optionalCookies, boolean necessaryCookies,
		boolean userConsentCookie) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setScopeGroupId(0);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		List<Cookie> cookies = new ArrayList<>();

		if (optionalCookies) {
			cookies.add(
				new Cookie(
					CookiesConstants.NAME_CONSENT_TYPE_NECESSARY, "true"));
		}

		if (necessaryCookies) {
			cookies.add(
				new Cookie(
					CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, "true"));
			cookies.add(
				new Cookie(
					CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE, "true"));
			cookies.add(
				new Cookie(
					CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
					"true"));
		}

		if (userConsentCookie) {
			cookies.add(
				new Cookie(
					CookiesConstants.NAME_USER_CONSENT_CONFIGURED, "true"));
		}

		mockHttpServletRequest.setCookies(cookies.toArray(new Cookie[0]));
		mockHttpServletRequest.setPathInfo(StringPool.BLANK);

		return mockHttpServletRequest;
	}

	@Mock
	private ConfigurationProvider _configurationProvider;

	private final CookiesManager _cookiesManager = new CookiesManagerImpl();
	private final CookiesPreAction _cookiesPreAction = new CookiesPreAction();

	@Mock
	private Portal _portal;

}