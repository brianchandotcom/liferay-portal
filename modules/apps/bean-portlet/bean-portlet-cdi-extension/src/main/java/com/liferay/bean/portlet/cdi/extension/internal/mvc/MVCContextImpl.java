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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import com.liferay.bean.portlet.extension.CSRFLiferayPortletURL;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.net.URI;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mvc.MvcContext;
import javax.mvc.locale.LocaleResolver;
import javax.mvc.locale.LocaleResolverContext;
import javax.mvc.security.Csrf;
import javax.mvc.security.Encoders;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;

import javax.servlet.http.Cookie;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.UriBuilder;

/**
 * @author  Neil Griffin
 */
public class MVCContextImpl implements MvcContext {

	public MVCContextImpl(
		Configuration configuration, Encoders encoders,
		List<LocaleResolver> localeResolvers, PortletContext portletContext,
		PortletRequest portletRequest) {

		_configuration = configuration;
		_encoders = encoders;
		_portletContext = portletContext;

		_portletRequest = portletRequest;

		ThemeDisplay themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		CSRFLiferayPortletURL csrfLiferayPortletURL = new CSRFLiferayPortletURL(
			portletDisplay.getId());

		AuthTokenUtil.addCSRFToken(
			themeDisplay.getRequest(), csrfLiferayPortletURL);

		_csrf = new CsrfImpl(
			"p_auth", csrfLiferayPortletURL.getParameter("p_auth"));

		Map<String, javax.ws.rs.core.Cookie> cookieMap = new HashMap<>();

		Cookie[] cookies = _portletRequest.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookieMap.put(
					cookie.getName(),
					new javax.ws.rs.core.Cookie(
						cookie.getName(), cookie.getValue()));
			}
		}

		Map<String, String> headerMap = new HashMap<>();

		Enumeration<String> propertyNames = _portletRequest.getPropertyNames();

		while (propertyNames.hasMoreElements()) {
			String header = propertyNames.nextElement();

			headerMap.put(header, _portletRequest.getProperty(header));
		}

		LocaleResolverContext localeResolverContext =
			new LocaleResolverContextImpl(
				Collections.list(_portletRequest.getLocales()), _configuration,
				cookieMap, headerMap, new UriInfoImpl());

		Locale locale = null;

		for (LocaleResolver localeResolver : localeResolvers) {
			locale = localeResolver.resolveLocale(localeResolverContext);

			if (locale != null) {
				break;
			}
		}

		_locale = locale;
	}

	@Override
	public String getBasePath() {
		return _portletContext.getContextPath();
	}

	@Override
	public Configuration getConfig() {
		return _configuration;
	}

	@Override
	public Csrf getCsrf() {
		return _csrf;
	}

	@Override
	public Encoders getEncoders() {
		return _encoders;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public URI uri(String identifier) {
		throw new UnsupportedOperationException();
	}

	@Override
	public URI uri(String identifier, Map<String, Object> parameters) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UriBuilder uriBuilder(String identifier) {
		throw new UnsupportedOperationException();
	}

	private final Configuration _configuration;
	private final Csrf _csrf;
	private final Encoders _encoders;
	private final Locale _locale;
	private final PortletContext _portletContext;
	private final PortletRequest _portletRequest;

}