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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author László Csontos
 * @author Shuyang Zhou
 * @author Tomas Polesovsky
 */
public class SecureHttpServletResponseWrapper
	extends HttpServletResponseWrapper {

	public SecureHttpServletResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void addHeader(String name, String value) {
		if (_sanitizeHeaders) {
			super.addHeader(
				HttpUtil.sanitizeHeader(name), HttpUtil.sanitizeHeader(value));
		}
		else {
			super.addHeader(name, value);
		}
	}

	public void applySecurityHeaders(HttpServletRequest request) {
		initConfiguration();

		setXContentOptions(request);
		setXFrameOptions(request);
		setXXSSProtection(request);
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		if (_sanitizeHeaders) {
			super.sendRedirect(HttpUtil.sanitizeHeader(location));
		}
		else {
			super.sendRedirect(location);
		}
	}

	@Override
	public void setCharacterEncoding(String charset) {
		if (_sanitizeHeaders) {
			super.setCharacterEncoding(HttpUtil.sanitizeHeader(charset));
		}
		else {
			super.setCharacterEncoding(charset);
		}
	}

	@Override
	public void setContentType(String type) {
		if (_sanitizeHeaders) {
			super.setContentType(HttpUtil.sanitizeHeader(type));
		}
		else {
			super.setContentType(type);
		}
	}

	@Override
	public void setHeader(String name, String value) {
		if (_sanitizeHeaders) {
			super.setHeader(
				HttpUtil.sanitizeHeader(name), HttpUtil.sanitizeHeader(value));
		}
		else {
			super.setHeader(name, value);
		}
	}

	public void setSanitizeHeaders(boolean sanitizeHeaders) {
		this._sanitizeHeaders = sanitizeHeaders;
	}

	protected void setXContentOptions(HttpServletRequest request) {
		if (!_X_CONTENT_TYPE_OPTIONS_ENABLED) {
			return;
		}

		if (_X_CONTENT_TYPE_OPTIONS_WHITELIST.length > 0) {
			String uri = request.getRequestURI();

			for (String whitelistedURL : _X_CONTENT_TYPE_OPTIONS_WHITELIST) {
				if (uri.startsWith(whitelistedURL)) {
					return;
				}
			}
		}

		super.setHeader(HttpHeaders.X_CONTENT_TYPE_OPTIONS, "nosniff");
	}

	protected void setXFrameOptions(HttpServletRequest request) {
		if (!_X_FRAME_OPTIONS_ENABLED) {
			return;
		}

		if (_X_FRAME_OPTIONS_WHITELIST.size()> 0) {
			String requestURI = request.getRequestURI();

			for (int i = 0; i < 256; i++) {
				String ruleURLKey = i + ".url";

				if (!_X_FRAME_OPTIONS_WHITELIST.containsKey(ruleURLKey)) {
					continue;
				}

				String urlPrefix = StringUtil.trim(
					_X_FRAME_OPTIONS_WHITELIST.getProperty(ruleURLKey));

				if (!requestURI.startsWith(urlPrefix)) {
					continue;
				}

				String value = StringUtil.trim(
					_X_FRAME_OPTIONS_WHITELIST.getProperty(i + ".value"));

				if (Validator.isNotNull(value)) {
					setHeader(HttpHeaders.X_FRAME_OPTIONS, value);
				}

				return;
			}
		}

		super.setHeader(HttpHeaders.X_FRAME_OPTIONS, "DENY");
	}

	protected void setXXSSProtection(HttpServletRequest request) {
		if (!_X_XSS_PROTECTION_ENABLED) {
			return;
		}

		super.setHeader(HttpHeaders.X_XSS_PROTECTION, "1; mode=block");
	}

	private static void initConfiguration() {
		if (_X_CONTENT_TYPE_OPTIONS_WHITELIST != null) {
			return;
		}

		synchronized (SecureHttpServletResponseWrapper.class) {
			if (_X_CONTENT_TYPE_OPTIONS_WHITELIST != null) {
				return;
			}

			_X_CONTENT_TYPE_OPTIONS_ENABLED = GetterUtil.getBoolean(
					PropsUtil.get(
						PropsKeys.HTTP_HEADER_X_CONTENT_TYPE_OPTIONS_ENABLED),
					true);

			_X_CONTENT_TYPE_OPTIONS_WHITELIST =
				PropsUtil.getArray(
					PropsKeys.HTTP_HEADER_X_CONTENT_TYPE_OPTIONS_WHITELIST);

			_X_FRAME_OPTIONS_ENABLED = GetterUtil.getBoolean(
					PropsUtil.get(
						PropsKeys.HTTP_HEADER_X_FRAME_OPTIONS_ENABLED), true);

			_X_FRAME_OPTIONS_WHITELIST = PropsUtil.getProperties(
					PropsKeys.HTTP_HEADER_X_FRAME_OPTIONS_WHITELIST, true);

			_X_XSS_PROTECTION_ENABLED = GetterUtil.getBoolean(
					PropsUtil.get(
						PropsKeys.HTTP_HEADER_X_XSS_PROTECTION_ENABLED),
					true);
		}
	}

	private static boolean _X_CONTENT_TYPE_OPTIONS_ENABLED;

	private static String[] _X_CONTENT_TYPE_OPTIONS_WHITELIST;

	private static boolean _X_FRAME_OPTIONS_ENABLED;

	private static Properties _X_FRAME_OPTIONS_WHITELIST;

	private static boolean _X_XSS_PROTECTION_ENABLED;

	private boolean _sanitizeHeaders;

}