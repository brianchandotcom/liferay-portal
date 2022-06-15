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
import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carol Alonso
 */
@Component(
	property = "key=servlet.service.events.pre", service = LifecycleAction.class
)
public class CookiesPreAction extends Action {

	@Override
	public void run(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			_run(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _addCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Cookie cookie) {

		cookie.setPath("/");
		cookie.setVersion(0);

		if (cookie.getMaxAge() != 0) {
			cookie.setMaxAge(365 * 24 * 60 * 60);
		}

		CookiesManagerUtil.addCookie(
			CookiesConstants.CONSENT_TYPE_NECESSARY, cookie, httpServletRequest,
			httpServletResponse);
	}

	private void _expireCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String name) {

		Cookie cookie = new Cookie(name, null);

		cookie.setMaxAge(0);

		_addCookie(httpServletRequest, httpServletResponse, cookie);
	}

	private void _expireNecessaryCookies(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		for (String necessaryCookieName : CookiesPreAction._NECESSARY_COOKIES) {
			_expireCookie(
				httpServletRequest, httpServletResponse, necessaryCookieName);
		}
	}

	private void _expireOptionalCookies(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		for (String optionalCookieName : CookiesPreAction._OPTIONAL_COOKIES) {
			_expireCookie(
				httpServletRequest, httpServletResponse, optionalCookieName);
		}
	}

	private void _expireUserConsentConfiguredCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		_expireCookie(
			httpServletRequest, httpServletResponse,
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);
	}

	private Map<String, String> _parseCookieMap(Cookie[] cookies) {
		Map<String, String> cookieMap = new HashMap<>();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();

				if (cookieName.startsWith("CONSENT_TYPE_") ||
					cookieName.equals(
						CookiesConstants.NAME_USER_CONSENT_CONFIGURED)) {

					cookieMap.put(cookieName, cookie.getValue());
				}
			}
		}

		return cookieMap;
	}

	private void _run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ConfigurationException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CookiesPreferenceHandlingConfiguration
			cookiesPreferenceHandlingConfiguration =
				_configurationProvider.getGroupConfiguration(
					CookiesPreferenceHandlingConfiguration.class,
					themeDisplay.getScopeGroupId());

		Map<String, String> requestCookies = _parseCookieMap(
			httpServletRequest.getCookies());

		String necessaryConsentCookie = requestCookies.get(
			CookiesConstants.NAME_CONSENT_TYPE_NECESSARY);
		String performanceConsentCookie = requestCookies.get(
			CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE);
		String functionalConsentCookie = requestCookies.get(
			CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL);
		String personalizationConsentCookie = requestCookies.get(
			CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION);
		String userConsentCookie = requestCookies.get(
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		boolean optionalCookiesSet = false;

		if ((performanceConsentCookie != null) &&
			(functionalConsentCookie != null) &&
			(personalizationConsentCookie != null)) {

			optionalCookiesSet = true;
		}

		boolean allConsentCookiesSet = false;

		if ((necessaryConsentCookie != null) && optionalCookiesSet) {
			allConsentCookiesSet = true;
		}

		if (!cookiesPreferenceHandlingConfiguration.enabled()) {
			if (userConsentCookie != null) {
				_expireUserConsentConfiguredCookie(
					httpServletRequest, httpServletResponse);
			}

			if (necessaryConsentCookie != null) {
				_expireNecessaryCookies(
					httpServletRequest, httpServletResponse);
			}

			if (optionalCookiesSet) {
				_expireOptionalCookies(httpServletRequest, httpServletResponse);
			}
		}
		else {
			if (cookiesPreferenceHandlingConfiguration.explicitConsentMode()) {
				if (!(optionalCookiesSet && (necessaryConsentCookie != null) &&
					  (userConsentCookie != null))) {

					if (userConsentCookie != null) {
						_expireUserConsentConfiguredCookie(
							httpServletRequest, httpServletResponse);
					}

					if (necessaryConsentCookie == null) {
						_setNecessaryCookies(
							httpServletRequest, httpServletResponse);
					}

					if ((userConsentCookie == null) || !optionalCookiesSet) {
						_setOptionalCookies(
							httpServletRequest, httpServletResponse, "false");
					}
				}
			}
			else {
				if (!allConsentCookiesSet) {
					if (userConsentCookie != null) {
						_expireUserConsentConfiguredCookie(
							httpServletRequest, httpServletResponse);
					}

					if (necessaryConsentCookie == null) {
						_setNecessaryCookies(
							httpServletRequest, httpServletResponse);
					}

					if (!optionalCookiesSet) {
						_setOptionalCookies(
							httpServletRequest, httpServletResponse, "true");
					}
				}
			}
		}
	}

	private void _setNecessaryCookies(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		for (String necessaryCookieName : CookiesPreAction._NECESSARY_COOKIES) {
			Cookie cookie = new Cookie(necessaryCookieName, "true");

			_addCookie(httpServletRequest, httpServletResponse, cookie);
		}
	}

	private void _setOptionalCookies(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String value) {

		for (String optionalCookieName : CookiesPreAction._OPTIONAL_COOKIES) {
			Cookie cookie = new Cookie(optionalCookieName, value);

			_addCookie(httpServletRequest, httpServletResponse, cookie);
		}
	}

	private static final String[] _NECESSARY_COOKIES = {
		CookiesConstants.NAME_CONSENT_TYPE_NECESSARY
	};

	private static final String[] _OPTIONAL_COOKIES = {
		CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL,
		CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
		CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CookiesPreAction.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}