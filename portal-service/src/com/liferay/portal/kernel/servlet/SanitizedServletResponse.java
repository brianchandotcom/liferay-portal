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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author László Csontos
 * @author Shuyang Zhou
 * @author Tomas Polesovsky
 */
public class SanitizedServletResponse extends HttpServletResponseWrapper {

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

		for (KeyValuePair keyValuePair : _xFrameOptions) {
			String url = keyValuePair.getKey();

			if (requestURI.startsWith(url)) {
				response.setHeader(
					HttpHeaders.X_FRAME_OPTIONS, keyValuePair.getValue());

				return;
			}
		}

		response.setHeader(HttpHeaders.X_FRAME_OPTIONS, "DENY");
	}

	protected static void setXXSSProtection(
		HttpServletRequest request, HttpServletResponse response) {

		if (!_X_XSS_PROTECTION) {
			return;
		}

		response.setHeader(HttpHeaders.X_XSS_PROTECTION, "1; mode=block");
	}

	private SanitizedServletResponse(HttpServletResponse response) {
		super(response);
	}

	private static final boolean _X_CONTENT_TYPE_OPTIONS =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.HTTP_HEADER_SECURE_X_CONTENT_TYPE_OPTIONS),
			true);

	private static final String[] _X_CONTENT_TYPE_OPTIONS_URLS_EXCLUDES =
		PropsUtil.getArray(
			PropsKeys.HTTP_HEADER_SECURE_X_CONTENT_TYPE_OPTIONS_URLS_EXCLUDES);

	private static final boolean _X_FRAME_OPTIONS;

	private static final boolean _X_XSS_PROTECTION = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.HTTP_HEADER_SECURE_X_XSS_PROTECTION), true);

	private static final KeyValuePair[] _xFrameOptions;

	static {
		Properties properties =
			PropsUtil.getProperties(
				PropsKeys.HTTP_HEADER_SECURE_X_FRAME_OPTIONS +
					StringPool.PERIOD, true);

		List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>(
			properties.size());

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String)entry.getKey();

			String value = (String)entry.getValue();

			keyValuePairs.add(new KeyValuePair(key, value));
		}

		Collections.sort(keyValuePairs, new Comparator<KeyValuePair>() {

			@Override
			public int compare(
				KeyValuePair keyValuePair1, KeyValuePair keyValuePair2) {

				int key1 = GetterUtil.getIntegerStrict(keyValuePair1.getKey());

				int key2 = GetterUtil.getIntegerStrict(keyValuePair2.getKey());

				return key1 - key2;
			}

		});

		List<KeyValuePair> xFrameOptions = new ArrayList<KeyValuePair>(
			keyValuePairs.size());

		for (KeyValuePair keyValuePair : keyValuePairs) {
			String xFrameOptionLine = keyValuePair.getValue();

			String[] xFrameOption = StringUtil.split(
				xFrameOptionLine, CharPool.COMMA);

			if (xFrameOption.length != 2) {
				continue;
			}

			String url = StringUtil.trim(xFrameOption[0]);

			if (Validator.isNull(url)) {
				continue;
			}

			String value = StringUtil.trim(xFrameOption[1]);

			if (Validator.isNull(value)) {
				continue;
			}

			xFrameOptions.add(new KeyValuePair(url, value));
		}

		_xFrameOptions = xFrameOptions.toArray(
			new KeyValuePair[xFrameOptions.size()]);

		if (_xFrameOptions.length == 0) {
			_X_FRAME_OPTIONS = false;
		}
		else {
			_X_FRAME_OPTIONS = GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.HTTP_HEADER_SECURE_X_FRAME_OPTIONS),
				true);
		}
	}

}