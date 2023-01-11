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

import com.liferay.portal.kernel.cookies.CookiesManagerUtil;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

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

		Map<String, String> cookieValues = _getCookieValues(
			httpServletRequest.getCookies());

		boolean functionalConsent = Validator.isNotNull(
			cookieValues.get(CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL));
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

		if (!optionalConsent && userConsent) {
			CookiesManagerUtil.deleteCookies(
				CookiesManagerUtil.getDomain(httpServletRequest),
				httpServletRequest, httpServletResponse,
				CookiesConstants.NAME_USER_CONSENT_CONFIGURED);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CookiesPreAction.class);

}