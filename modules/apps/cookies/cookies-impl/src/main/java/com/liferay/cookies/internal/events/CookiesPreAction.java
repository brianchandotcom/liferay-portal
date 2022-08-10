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
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
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

	private void _addCookies(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Cookie... cookies) {

		for (Cookie cookie : cookies) {
			if (cookie.getMaxAge() != 0) {
				cookie.setMaxAge(365 * 24 * 60 * 60);
			}

			cookie.setPath("/");
			cookie.setVersion(0);

			CookiesManagerUtil.addCookie(
				CookiesConstants.CONSENT_TYPE_NECESSARY, cookie,
				httpServletRequest, httpServletResponse);
		}
	}

	private void _expireCookies(
		Map<String, String> cookieValues, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String... cookieNames) {

		for (String cookieName : cookieNames) {
			String cookieValue = cookieValues.get(cookieName);

			if (cookieValue == null) {
				continue;
			}

			Cookie cookie = new Cookie(cookieName, null);

			cookie.setMaxAge(0);

			_addCookies(httpServletRequest, httpServletResponse, cookie);
		}
	}

	private Map<String, String> _getCookieValues(Cookie[] cookies) {
		Map<String, String> cookieValues = new HashMap<>();

		if (cookies == null) {
			return cookieValues;
		}

		for (Cookie cookie : cookies) {
			String cookieName = cookie.getName();

			if (cookieName.equals(
					CookiesConstants.NAME_USER_CONSENT_CONFIGURED) ||
				cookieName.startsWith("CONSENT_TYPE_")) {

				cookieValues.put(cookieName, cookie.getValue());
			}
		}

		return cookieValues;
	}

	private void _run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CookiesPreferenceHandlingConfiguration
			cookiesPreferenceHandlingConfiguration =
				_configurationProvider.getGroupConfiguration(
					CookiesPreferenceHandlingConfiguration.class,
					themeDisplay.getScopeGroupId());

		Map<String, String> cookieValues = _getCookieValues(
			httpServletRequest.getCookies());

		if (!cookiesPreferenceHandlingConfiguration.enabled()) {
			_expireCookies(
				cookieValues, httpServletRequest, httpServletResponse,
				CookiesConstants.NAME_USER_CONSENT_CONFIGURED,
				CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL,
				CookiesConstants.NAME_CONSENT_TYPE_NECESSARY,
				CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
				CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION);

			return;
		}

		boolean functionalConsent = Validator.isNotNull(
			cookieValues.get(CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL));
		boolean necessaryConsent = Validator.isNotNull(
			cookieValues.get(CookiesConstants.NAME_CONSENT_TYPE_NECESSARY));
		boolean performanceConsent = Validator.isNotNull(
			cookieValues.get(CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE));
		boolean personalizationConsent = Validator.isNotNull(
			cookieValues.get(
				CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION));

		boolean optionalConsent = false;

		if (performanceConsent && functionalConsent && personalizationConsent) {
			optionalConsent = true;
		}

		boolean userConsent = Validator.isNotNull(
			cookieValues.get(CookiesConstants.NAME_USER_CONSENT_CONFIGURED));

		if (optionalConsent && necessaryConsent && userConsent) {
			return;
		}

		_expireCookies(
			cookieValues, httpServletRequest, httpServletResponse,
			CookiesConstants.NAME_USER_CONSENT_CONFIGURED);

		if (!necessaryConsent) {
			_addCookies(
				httpServletRequest, httpServletResponse,
				new Cookie(
					CookiesConstants.NAME_CONSENT_TYPE_NECESSARY, "true"));
		}

		if (!optionalConsent || !userConsent) {
			String cookieValue = "true";

			if (cookiesPreferenceHandlingConfiguration.explicitConsentMode()) {
				cookieValue = "false";
			}

			_addCookies(
				httpServletRequest, httpServletResponse,
				new Cookie(
					CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL, cookieValue),
				new Cookie(
					CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
					cookieValue),
				new Cookie(
					CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
					cookieValue));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CookiesPreAction.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}