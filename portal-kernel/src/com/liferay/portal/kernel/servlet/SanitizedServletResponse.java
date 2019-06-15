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

package com.liferay.portal.kernel.servlet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

/**
 * @author László Csontos
 * @author Shuyang Zhou
 * @author Tomas Polesovsky
 */
public class SanitizedServletResponse extends HttpServletResponseWrapper {

	public static void disableXSSAuditor(
		HttpServletResponse httpServletResponse) {

		httpServletResponse.setHeader(HttpHeaders.X_XSS_PROTECTION, "0");
	}

	public static void disableXSSAuditor(PortletResponse portletResponse) {
		disableXSSAuditor(PortalUtil.getHttpServletResponse(portletResponse));
	}

	public static void disableXSSAuditorOnNextRequest(
		HttpServletRequest httpServletRequest) {

		HttpSession session = httpServletRequest.getSession();

		session.setAttribute(_DISABLE_XSS_AUDITOR, Boolean.TRUE);
	}

	public static void disableXSSAuditorOnNextRequest(
		PortletRequest portletRequest) {

		disableXSSAuditorOnNextRequest(
			PortalUtil.getHttpServletRequest(portletRequest));
	}

	public static HttpServletResponse getSanitizedServletResponse(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		setXContentOptions(httpServletRequest, httpServletResponse);
		setXFrameOptions(httpServletRequest, httpServletResponse);
		setXXSSProtection(httpServletRequest, httpServletResponse);

		return httpServletResponse;
	}

	@Override
	public void addHeader(String name, String value) {
		super.addHeader(
			HttpUtil.sanitizeHeader(name), HttpUtil.sanitizeHeader(value));
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		super.sendRedirect(HttpUtil.sanitizeHeader(location));
	}

	@Override
	public void setCharacterEncoding(String charset) {
		super.setCharacterEncoding(HttpUtil.sanitizeHeader(charset));
	}

	@Override
	public void setContentType(String type) {
		super.setContentType(HttpUtil.sanitizeHeader(type));
	}

	@Override
	public void setHeader(String name, String value) {
		super.setHeader(
			HttpUtil.sanitizeHeader(name), HttpUtil.sanitizeHeader(value));
	}

	protected static void setXContentOptions(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (!_X_CONTENT_TYPE_OPTIONS) {
			return;
		}

		if (_X_CONTENT_TYPE_OPTIONS_URLS_EXCLUDES.length > 0) {
			String requestURI = httpServletRequest.getRequestURI();

			for (String url : _X_CONTENT_TYPE_OPTIONS_URLS_EXCLUDES) {
				if (requestURI.startsWith(url)) {
					return;
				}
			}
		}

		httpServletResponse.setHeader(
			HttpHeaders.X_CONTENT_TYPE_OPTIONS, "nosniff");
	}

	protected static void setXFrameOptions(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (!_X_FRAME_OPTIONS) {
			return;
		}

		String requestURI = httpServletRequest.getRequestURI();

		for (KeyValuePair xFrameOptionKVP : _xFrameOptionKVPs) {
			String url = xFrameOptionKVP.getKey();

			if (requestURI.startsWith(url)) {
				String value = xFrameOptionKVP.getValue();

				if (value != null) {
					httpServletResponse.setHeader(
						HttpHeaders.X_FRAME_OPTIONS,
						xFrameOptionKVP.getValue());
				}

				return;
			}
		}

		httpServletResponse.setHeader(HttpHeaders.X_FRAME_OPTIONS, "DENY");
	}

	protected static void setXXSSProtection(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		HttpSession session = httpServletRequest.getSession(false);

		if ((session != null) &&
			(session.getAttribute(_DISABLE_XSS_AUDITOR) != null)) {

			session.removeAttribute(_DISABLE_XSS_AUDITOR);

			httpServletResponse.setHeader(HttpHeaders.X_XSS_PROTECTION, "0");

			return;
		}

		if (_X_XSS_PROTECTION == null) {
			return;
		}

		httpServletResponse.setHeader(
			HttpHeaders.X_XSS_PROTECTION, _X_XSS_PROTECTION);
	}

	private SanitizedServletResponse(HttpServletResponse httpServletResponse) {
		super(httpServletResponse);
	}

	private static final String _DISABLE_XSS_AUDITOR =
		SanitizedServletResponse.class.getName() + "DISABLE_XSS_AUDITOR";

	private static final boolean _X_CONTENT_TYPE_OPTIONS =
		GetterUtil.getBoolean(
			SystemProperties.get("http.header.secure.x.content.type.options"),
			true);

	private static final String[] _X_CONTENT_TYPE_OPTIONS_URLS_EXCLUDES =
		StringUtil.split(
			SystemProperties.get(
				"http.header.secure.x.content.type.options.urls.excludes"));

	private static final boolean _X_FRAME_OPTIONS;

	private static final String _X_XSS_PROTECTION;

	private static final KeyValuePair[] _xFrameOptionKVPs;

	static {
		String httpHeaderSecureXFrameOptionsKey =
			"http.header.secure.x.frame.options";

		Properties properties = new SortedProperties(
			new Comparator<String>() {

				@Override
				public int compare(String key1, String key2) {
					return GetterUtil.getIntegerStrict(key1) -
						GetterUtil.getIntegerStrict(key2);
				}

			},
			PropertiesUtil.getProperties(
				SystemProperties.getProperties(),
				httpHeaderSecureXFrameOptionsKey.concat(StringPool.PERIOD),
				true));

		List<KeyValuePair> xFrameOptionKVPs = new ArrayList<>(
			properties.size());

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String propertyValue = (String)entry.getValue();

			String[] propertyValueParts = StringUtil.split(
				propertyValue, CharPool.PIPE);

			if (propertyValueParts.length > 2) {
				continue;
			}

			String url = StringUtil.trim(propertyValueParts[0]);

			if (Validator.isNull(url)) {
				continue;
			}

			if (propertyValueParts.length == 1) {
				xFrameOptionKVPs.add(new KeyValuePair(url, null));

				continue;
			}

			String value = StringUtil.trim(propertyValueParts[1]);

			if (Validator.isNull(value)) {
				value = null;
			}

			xFrameOptionKVPs.add(new KeyValuePair(url, value));
		}

		_xFrameOptionKVPs = xFrameOptionKVPs.toArray(new KeyValuePair[0]);

		if (_xFrameOptionKVPs.length == 0) {
			_X_FRAME_OPTIONS = false;
		}
		else {
			_X_FRAME_OPTIONS = GetterUtil.getBoolean(
				SystemProperties.get(httpHeaderSecureXFrameOptionsKey), true);
		}

		String xXssProtection = SystemProperties.get(
			"http.header.secure.x.xss.protection");

		if (Validator.isNull(xXssProtection)) {
			_X_XSS_PROTECTION = null;
		}
		else {
			_X_XSS_PROTECTION = xXssProtection;
		}
	}

}