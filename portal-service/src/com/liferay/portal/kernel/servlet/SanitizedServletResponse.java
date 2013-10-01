/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

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

	public static void disableXSSAuditor(HttpServletResponse response) {
		response.setHeader(HttpHeaders.X_XSS_PROTECTION, "0");
	}

	public static void disableXSSAuditor(PortletResponse response) {
		disableXSSAuditor(PortalUtil.getHttpServletResponse(response));
	}

	public static void disableXSSAuditorOnNextRequest(
		HttpServletRequest request) {

		request.getSession().setAttribute(_DISABLE_XSS_AUDITOR, Boolean.TRUE);
	}

	public static void disableXSSAuditorOnNextRequest(PortletRequest request) {
		disableXSSAuditorOnNextRequest(
			PortalUtil.getHttpServletRequest(request));
	}

	public static HttpServletResponse getSanitizedServletResponse(
		HttpServletRequest request, HttpServletResponse response) {

		setXContentOptions(request, response);
		setXFrameOptions(request, response);
		setXXSSProtection(request, response);

		if (ServerDetector.isResin()) {
			response = new SanitizedServletResponse(response);
		}

		return response;
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
		HttpServletRequest request, HttpServletResponse response) {

		if (!_X_CONTENT_TYPE_OPTIONS) {
			return;
		}

		if (_X_CONTENT_TYPE_OPTIONS_URLS_EXCLUDES.length > 0) {
			String requestURI = request.getRequestURI();

			for (String url : _X_CONTENT_TYPE_OPTIONS_URLS_EXCLUDES) {
				if (requestURI.startsWith(url)) {
					return;
				}
			}
		}

		response.setHeader(HttpHeaders.X_CONTENT_TYPE_OPTIONS, "nosniff");
	}

	protected static void setXFrameOptions(
		HttpServletRequest request, HttpServletResponse response) {

		if (!_X_FRAME_OPTIONS) {
			return;
		}

		String requestURI = request.getRequestURI();

		for (KeyValuePair xFrameOptionKVP : _xFrameOptionKVPs) {
			String url = xFrameOptionKVP.getKey();
			String value = xFrameOptionKVP.getValue();

			if (requestURI.startsWith(url)) {
				if (value != null) {
					response.setHeader(
						HttpHeaders.X_FRAME_OPTIONS,
						xFrameOptionKVP.getValue());
				}

				return;
			}
		}

		response.setHeader(HttpHeaders.X_FRAME_OPTIONS, "DENY");
	}

	protected static void setXXSSProtection(
		HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);

		if ((session != null) &&
			(session.getAttribute(_DISABLE_XSS_AUDITOR) != null)) {

			session.removeAttribute(_DISABLE_XSS_AUDITOR);

			response.setHeader(HttpHeaders.X_XSS_PROTECTION, "0");

			return;
		}

		if (Validator.isNull(_X_XSS_PROTECTION)) {
			return;
		}

		response.setHeader(HttpHeaders.X_XSS_PROTECTION, _X_XSS_PROTECTION);
	}

	private SanitizedServletResponse(HttpServletResponse response) {
		super(response);
	}

	private static final String _DISABLE_XSS_AUDITOR =
		SanitizedServletResponse.class.getName() + ".disableXSSAuditor";

	private static final boolean _X_CONTENT_TYPE_OPTIONS =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.HTTP_HEADER_SECURE_X_CONTENT_TYPE_OPTIONS),
			true);

	private static final String[] _X_CONTENT_TYPE_OPTIONS_URLS_EXCLUDES =
		PropsUtil.getArray(
			PropsKeys.HTTP_HEADER_SECURE_X_CONTENT_TYPE_OPTIONS_URLS_EXCLUDES);

	private static final boolean _X_FRAME_OPTIONS;

	private static final String _X_XSS_PROTECTION = PropsUtil.get(
		PropsKeys.HTTP_HEADER_SECURE_X_XSS_PROTECTION);

	private static final KeyValuePair[] _xFrameOptionKVPs;

	static {
		Properties properties = new SortedProperties(
			new Comparator<String>() {

				@Override
				public int compare(String key1, String key2) {
					return GetterUtil.getIntegerStrict(key1) -
						GetterUtil.getIntegerStrict(key2);
				}

			},
			PropsUtil.getProperties(
				PropsKeys.HTTP_HEADER_SECURE_X_FRAME_OPTIONS +
					StringPool.PERIOD,
				true));

		List<KeyValuePair> xFrameOptionKVPs = new ArrayList<KeyValuePair>(
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

		_xFrameOptionKVPs = xFrameOptionKVPs.toArray(
			new KeyValuePair[xFrameOptionKVPs.size()]);

		if (_xFrameOptionKVPs.length == 0) {
			_X_FRAME_OPTIONS = false;
		}
		else {
			_X_FRAME_OPTIONS = GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.HTTP_HEADER_SECURE_X_FRAME_OPTIONS),
				true);
		}
	}

}