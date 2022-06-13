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

package com.liferay.cookies.internal.manager;

import com.liferay.cookies.configuration.CookiesConsentConfiguration;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.cookies.ConsentCookieType;
import com.liferay.portal.kernel.cookies.CookiesManager;
import com.liferay.portal.kernel.cookies.UnsupportedCookieException;
import com.liferay.portal.kernel.cookies.constants.CookiesConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tamas Molnar
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.cookies.configuration.CookiesConsentConfiguration",
	immediate = true, service = CookiesManager.class
)
public class CookiesManagerImpl implements CookiesManager {

	@Override
	public boolean addCookie(
		int consentType, Cookie cookie, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		return addCookie(
			consentType, cookie, httpServletRequest, httpServletResponse,
			_portal.isSecure(httpServletRequest));
	}

	@Override
	public boolean addCookie(
		int consentType, Cookie cookie, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, boolean secure) {

		if (!_SESSION_ENABLE_PERSISTENT_COOKIES) {
			return false;
		}

		if ((cookie.getMaxAge() != 0) &&
			!hasConsentType(consentType, httpServletRequest)) {

			return false;
		}

		// LEP-5175

		cookie.setSecure(secure);

		String originalCookieValue = cookie.getValue();

		String encodedCookieValue = originalCookieValue;

		if (isEncodedCookie(cookie.getName())) {
			encodedCookieValue = UnicodeFormatter.bytesToHex(
				originalCookieValue.getBytes());

			if (_log.isDebugEnabled()) {
				_log.debug("Add encoded cookie " + cookie.getName());
				_log.debug("Original value " + originalCookieValue);
				_log.debug("Hex encoded value " + encodedCookieValue);
			}
		}

		cookie.setValue(encodedCookieValue);

		cookie.setVersion(0);

		httpServletResponse.addCookie(cookie);

		if (httpServletRequest != null) {
			Map<String, Cookie> cookiesMap = _getCookiesMap(httpServletRequest);

			cookiesMap.put(StringUtil.toUpperCase(cookie.getName()), cookie);
		}

		return true;
	}

	@Override
	public boolean addSupportCookie(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		Cookie cookieSupportCookie = new Cookie(
			CookiesConstants.NAME_COOKIE_SUPPORT, "true");

		cookieSupportCookie.setMaxAge(CookiesConstants.MAX_AGE);
		cookieSupportCookie.setPath(StringPool.SLASH);

		return addCookie(
			CookiesConstants.CONSENT_TYPE_NECESSARY, cookieSupportCookie, null,
			httpServletResponse, httpServletRequest.isSecure());
	}

	@Override
	public boolean deleteCookies(
		String domain, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String... cookieNames) {

		if (!_SESSION_ENABLE_PERSISTENT_COOKIES) {
			return false;
		}

		Map<String, Cookie> cookiesMap = _getCookiesMap(httpServletRequest);

		for (String cookieName : cookieNames) {
			Cookie cookie = cookiesMap.remove(
				StringUtil.toUpperCase(cookieName));

			if (cookie == null) {
				continue;
			}

			if (domain != null) {
				cookie.setDomain(domain);
			}

			cookie.setMaxAge(0);
			cookie.setPath(StringPool.SLASH);
			cookie.setValue(StringPool.BLANK);

			httpServletResponse.addCookie(cookie);
		}

		return true;
	}

	@Override
	public String getCookieValue(
		String cookieName, HttpServletRequest httpServletRequest) {

		return getCookieValue(cookieName, httpServletRequest, true);
	}

	@Override
	public String getCookieValue(
		String cookieName, HttpServletRequest httpServletRequest,
		boolean toUpperCase) {

		if (!_SESSION_ENABLE_PERSISTENT_COOKIES) {
			return null;
		}

		String cookieValue = _getCookieValue(
			cookieName, httpServletRequest, toUpperCase);

		if ((cookieValue == null) || !isEncodedCookie(cookieName)) {
			return cookieValue;
		}

		try {
			String encodedCookieValue = cookieValue;

			String originalCookieValue = new String(
				UnicodeFormatter.hexToBytes(encodedCookieValue));

			if (_log.isDebugEnabled()) {
				_log.debug("Get encoded cookie " + cookieName);
				_log.debug("Hex encoded value " + encodedCookieValue);
				_log.debug("Original value " + originalCookieValue);
			}

			return originalCookieValue;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return cookieValue;
		}
	}

	@Override
	public String getDomain(HttpServletRequest httpServletRequest) {

		// See LEP-4602 and	LEP-4618.

		if (Validator.isNotNull(_SESSION_COOKIE_DOMAIN)) {
			return _SESSION_COOKIE_DOMAIN;
		}

		if (_SESSION_COOKIE_USE_FULL_HOSTNAME) {
			return StringPool.BLANK;
		}

		return getDomain(httpServletRequest.getServerName());
	}

	@Override
	public String getDomain(String host) {

		// See LEP-4602 and LEP-4645.

		if (host == null) {
			return null;
		}

		// See LEP-5595.

		if (Validator.isIPAddress(host)) {
			return host;
		}

		int x = host.lastIndexOf(CharPool.PERIOD);

		if (x <= 0) {
			return null;
		}

		int y = host.lastIndexOf(CharPool.PERIOD, x - 1);

		if (y <= 0) {
			return StringPool.PERIOD + host;
		}

		int z = host.lastIndexOf(CharPool.PERIOD, y - 1);

		String domain = null;

		if (z <= 0) {
			domain = host.substring(y);
		}
		else {
			domain = host.substring(z);
		}

		return domain;
	}

	@Override
	public List<ConsentCookieType> getOptionalConsentCookieTypes(long groupId)
		throws Exception {

		CookiesConsentConfiguration cookiesConsentConfiguration =
			_configurationProvider.getGroupConfiguration(
				CookiesConsentConfiguration.class, groupId);

		return ListUtil.fromArray(
			new ConsentCookieType(
				cookiesConsentConfiguration.functionalCookiesDescription(),
				CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL,
				cookiesConsentConfiguration.functionalCookiesPrechecked()),
			new ConsentCookieType(
				cookiesConsentConfiguration.performanceCookiesDescription(),
				CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE,
				cookiesConsentConfiguration.performanceCookiesPrechecked()),
			new ConsentCookieType(
				cookiesConsentConfiguration.personalizationCookiesDescription(),
				CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION,
				cookiesConsentConfiguration.
					personalizationCookiesPrechecked()));
	}

	@Override
	public List<ConsentCookieType> getRequiredConsentCookieTypes(long groupId)
		throws Exception {

		CookiesConsentConfiguration cookiesConsentConfiguration =
			_configurationProvider.getGroupConfiguration(
				CookiesConsentConfiguration.class, groupId);

		return ListUtil.fromArray(
			new ConsentCookieType(
				cookiesConsentConfiguration.
					strictlyNecessaryCookiesDescription(),
				CookiesConstants.NAME_CONSENT_TYPE_NECESSARY, true));
	}

	@Override
	public boolean hasConsentType(
		int consentType, HttpServletRequest httpServletRequest) {

		if (consentType == CookiesConstants.CONSENT_TYPE_NECESSARY) {
			return true;
		}

		String consentCookieName = StringPool.BLANK;

		if (consentType == CookiesConstants.CONSENT_TYPE_FUNCTIONAL) {
			consentCookieName = CookiesConstants.NAME_CONSENT_TYPE_FUNCTIONAL;
		}
		else if (consentType == CookiesConstants.CONSENT_TYPE_PERFORMANCE) {
			consentCookieName = CookiesConstants.NAME_CONSENT_TYPE_PERFORMANCE;
		}
		else if (consentType == CookiesConstants.CONSENT_TYPE_PERSONALIZATION) {
			consentCookieName =
				CookiesConstants.NAME_CONSENT_TYPE_PERSONALIZATION;
		}

		String consentCookieValue = getCookieValue(
			consentCookieName, httpServletRequest);

		if (Validator.isNull(consentCookieValue)) {
			return true;
		}

		return GetterUtil.getBoolean(consentCookieValue);
	}

	@Override
	public boolean hasSessionId(HttpServletRequest httpServletRequest) {
		String cookieValue = getCookieValue(
			CookiesConstants.NAME_JSESSIONID, httpServletRequest, false);

		if (cookieValue != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isEncodedCookie(String cookieName) {
		if (cookieName.equals(CookiesConstants.NAME_ID) ||
			cookieName.equals(CookiesConstants.NAME_LOGIN) ||
			cookieName.equals(CookiesConstants.NAME_PASSWORD) ||
			cookieName.equals(CookiesConstants.NAME_USER_UUID)) {

			return true;
		}

		return false;
	}

	@Override
	public void validateSupportCookie(HttpServletRequest httpServletRequest)
		throws UnsupportedCookieException {

		if (_SESSION_ENABLE_PERSISTENT_COOKIES &&
			_SESSION_TEST_COOKIE_SUPPORT &&
			Validator.isNull(
				getCookieValue(
					CookiesConstants.NAME_COOKIE_SUPPORT, httpServletRequest,
					false))) {

			throw new UnsupportedCookieException();
		}
	}

	private Map<String, Cookie> _getCookiesMap(
		HttpServletRequest httpServletRequest) {

		Map<String, Cookie> cookiesMap =
			(Map<String, Cookie>)httpServletRequest.getAttribute(
				CookieKeys.class.getName());

		if (cookiesMap != null) {
			return cookiesMap;
		}

		Cookie[] cookies = httpServletRequest.getCookies();

		if (cookies == null) {
			cookiesMap = new HashMap<>();
		}
		else {
			cookiesMap = new HashMap<>(cookies.length * 4 / 3);

			for (Cookie cookie : cookies) {
				String cookieName = GetterUtil.getString(cookie.getName());

				cookieName = StringUtil.toUpperCase(cookieName);

				cookiesMap.put(cookieName, cookie);
			}
		}

		httpServletRequest.setAttribute(CookieKeys.class.getName(), cookiesMap);

		return cookiesMap;
	}

	private String _getCookieValue(
		String cookieName, HttpServletRequest httpServletRequest,
		boolean toUpperCase) {

		Map<String, Cookie> cookiesMap = _getCookiesMap(httpServletRequest);

		if (toUpperCase) {
			cookieName = StringUtil.toUpperCase(cookieName);
		}

		Cookie cookie = cookiesMap.get(cookieName);

		if (cookie == null) {
			return null;
		}

		return cookie.getValue();
	}

	private static final String _SESSION_COOKIE_DOMAIN = PropsUtil.get(
		PropsKeys.SESSION_COOKIE_DOMAIN);

	private static final boolean _SESSION_COOKIE_USE_FULL_HOSTNAME =
		GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.SESSION_COOKIE_USE_FULL_HOSTNAME,
				new Filter(ServerDetector.getServerId())));

	private static final boolean _SESSION_ENABLE_PERSISTENT_COOKIES =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.SESSION_ENABLE_PERSISTENT_COOKIES));

	private static final boolean _SESSION_TEST_COOKIE_SUPPORT =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.SESSION_TEST_COOKIE_SUPPORT));

	private static final Log _log = LogFactoryUtil.getLog(
		CookiesManagerImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CookiesConsentConfiguration _cookiesConsentConfiguration;

	@Reference
	private Portal _portal;

}