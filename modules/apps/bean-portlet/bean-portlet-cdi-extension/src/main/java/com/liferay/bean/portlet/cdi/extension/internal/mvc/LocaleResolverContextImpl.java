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

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mvc.locale.LocaleResolverContext;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

/**
 * @author  Neil Griffin
 */
public class LocaleResolverContextImpl implements LocaleResolverContext {

	public LocaleResolverContextImpl(
		List<Locale> acceptableLanguages, Configuration configuration,
		Map<String, Cookie> cookies, Map<String, String> headers,
		UriInfo uriInfo) {

		_acceptableLanguages = acceptableLanguages;
		_configuration = configuration;
		_cookies = cookies;
		_headers = headers;
		_uriInfo = uriInfo;
	}

	@Override
	public List<Locale> getAcceptableLanguages() {
		return _acceptableLanguages;
	}

	@Override
	public Configuration getConfiguration() {
		return _configuration;
	}

	@Override
	public Cookie getCookie(String name) {
		return _cookies.get(name);
	}

	@Override
	public String getHeaderString(String name) {
		return _headers.get(name);
	}

	@Override
	public Request getRequest() {
		return new RequestImpl();
	}

	@Override
	public UriInfo getUriInfo() {
		return _uriInfo;
	}

	private final List<Locale> _acceptableLanguages;
	private final Configuration _configuration;
	private final Map<String, Cookie> _cookies;
	private final Map<String, String> _headers;
	private final UriInfo _uriInfo;

}