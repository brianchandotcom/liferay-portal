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

package com.liferay.portal.vulcan.internal.template.servlet;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Alejandro Tardín
 */
public class RESTClientHttpRequest extends HttpServletRequestWrapper {

	public RESTClientHttpRequest(HttpServletRequest httpServletRequest) {
		super(httpServletRequest);

		_headers = HashMapBuilder.put(
			HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON
		).put(
			"Accept-Language",
			() -> {
				Locale locale = PortalUtil.getLocale(httpServletRequest);

				return locale.toLanguageTag();
			}
		).build();
	}

	@Override
	public Object getAttribute(String name) {
		return _attributes.getOrDefault(name, super.getAttribute(name));
	}

	@Override
	public String getHeader(String name) {
		return _headers.get(name);
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		String value = _headers.get(name);

		if (Validator.isNotNull(value)) {
			return Collections.enumeration(Arrays.asList(value));
		}

		return Collections.emptyEnumeration();
	}

	@Override
	public String getMethod() {
		return HttpMethods.GET;
	}

	@Override
	public void setAttribute(String name, Object object) {
		_attributes.put(name, object);
	}

	private final Map<String, Object> _attributes = new HashMap<>();
	private final Map<String, String> _headers;

}