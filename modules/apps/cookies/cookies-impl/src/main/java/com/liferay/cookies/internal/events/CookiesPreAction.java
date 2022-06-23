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
			cookie.setPath("/");
			cookie.setVersion(0);

			if (cookie.getMaxAge() != 0) {
				cookie.setMaxAge(365 * 24 * 60 * 60);
			}

			CookiesManagerUtil.addCookie(
				CookiesConstants.CONSENT_TYPE_NECESSARY, cookie,
				httpServletRequest, httpServletResponse);
		}
	}

	private void _expireCookiesIfSet(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Map<String, String> cookies,
		String... cookiesToExpire) {

		for (String cookieToExpire : cookiesToExpire) {
			String cookieValue = cookies.get(cookieToExpire);

			if (cookieValue != null) {
				Cookie expiredCookie = new Cookie(cookieToExpire, null);

				expiredCookie.setMaxAge(0);

				_addCookies(
					httpServletRequest, httpServletResponse, expiredCookie);
			}
		}
	}

	private Map<String, String> _parseCookieMap(Cookie[] cookies) {
		Map<String, String> cookieMap = new HashMap<>();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();

				if (cookieName.equals(
						CookiesConstants.NAME_USER_CONSENT_CONFIGURED) ||
					cookieName.startsWith("CONSENT_TYPE_")) {

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

		if (!cookiesPreferenceHandlingConfiguration.enabled()) {
			_expireCookiesIfSet(
				httpServletRequest, httpServletResponse, requestCookies,
				CookiesConstants.NAME_USER_CONSENT_CONFIGURED,
				CookiesConstants.NAME_CONSENT_TYPE_NECESSARY,
				CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
				CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
				CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL);
		}
		else {
			boolean necessaryConsentCookieSet = Validator.isNotNull(
				requestCookies.get(
					CookiesConstants.NAME_CONSENT_TYPE_NECESSARY));
			boolean performanceConsentCookieSet = Validator.isNotNull(
				requestCookies.get(
					CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE));
			boolean functionalConsentCookieSet = Validator.isNotNull(
				requestCookies.get(
					CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL));
			boolean personalizationConsentCookieSet = Validator.isNotNull(
				requestCookies.get(
					CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION));

			boolean optionalConsentCookiesSet = false;

			if (performanceConsentCookieSet && functionalConsentCookieSet &&
				personalizationConsentCookieSet) {

				optionalConsentCookiesSet = true;
			}

			boolean userConsentCookieSet = Validator.isNotNull(
				requestCookies.get(
					CookiesConstants.NAME_USER_CONSENT_CONFIGURED));

			if (!(optionalConsentCookiesSet && necessaryConsentCookieSet &&
				  userConsentCookieSet)) {

				if (!cookiesPreferenceHandlingConfiguration.
						explicitConsentMode() ||
					!userConsentCookieSet) {

					_expireCookiesIfSet(
						httpServletRequest, httpServletResponse, requestCookies,
						CookiesConstants.NAME_USER_CONSENT_CONFIGURED);
				}

				if (!necessaryConsentCookieSet) {
					_addCookies(
						httpServletRequest, httpServletResponse,
						new Cookie(
							CookiesConstants.NAME_CONSENT_TYPE_NECESSARY,
							"true"));
				}

				if (!optionalConsentCookiesSet ||
					(cookiesPreferenceHandlingConfiguration.
						explicitConsentMode() &&
					 !userConsentCookieSet)) {

					String optionalCookiesValue = "true";

					if (cookiesPreferenceHandlingConfiguration.
							explicitConsentMode()) {

						optionalCookiesValue = "false";
					}

					_addCookies(
						httpServletRequest, httpServletResponse,
						new Cookie(
							CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL,
							optionalCookiesValue),
						new Cookie(
							CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
							optionalCookiesValue),
						new Cookie(
							CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
							optionalCookiesValue));
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CookiesPreAction.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}