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

package com.liferay.portal.remote.cors.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Carlos Sierra Andrés
 * @author Tomas Polesovsky
 */
public class CORSSupport {

	public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS =
		"Access-Control-Allow-Credentials";

	public static final String ACCESS_CONTROL_ALLOW_HEADERS =
		"Access-Control-Allow-Headers";

	public static final String ACCESS_CONTROL_ALLOW_METHODS =
		"Access-Control-Allow-Methods";

	public static final String ACCESS_CONTROL_ALLOW_ORIGIN =
		"Access-Control-Allow-Origin";

	public static final String ACCESS_CONTROL_EXPOSE_HEADERS =
		"Access-Control-Expose-Headers";

	public static final String ACCESS_CONTROL_MAX_AGE =
		"Access-Control-Max-Age";

	public static final String ACCESS_CONTROL_REQUEST_METHOD =
		"Access-Control-Request-Method";

	public static final String ORIGIN = "Origin";

	public static Map<String, String> buildCorsHeaders(
		String[] corsHeaderStrings) {

		Map<String, String> headers = new HashMap<>();

		for (String header : corsHeaderStrings) {
			int pos = header.indexOf(CharPool.COLON);

			if (pos < 1) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Incorrect syntax of OAuth2CORSConfiguration header: " +
							header);
				}

				continue;
			}

			headers.put(
				StringUtil.trim(header.substring(0, pos)),
				StringUtil.trim(header.substring(pos + 1)));
		}

		return headers;
	}

	public boolean isCorsRequest(
		Function<String, String> requestHeaderAccessorFunction) {

		String origin = requestHeaderAccessorFunction.apply(ORIGIN);

		if (Validator.isBlank(origin)) {
			return false;
		}

		return true;
	}

	public boolean isValidCorsPreflightRequest(
		Function<String, String> requestHeaderAccessorFunction) {

		String origin = requestHeaderAccessorFunction.apply(ORIGIN);

		if (!isValidOrigin(origin)) {
			return false;
		}

		String accessControlRequestMethod = requestHeaderAccessorFunction.apply(
			ACCESS_CONTROL_REQUEST_METHOD);

		if (Validator.isBlank(accessControlRequestMethod)) {
			return false;
		}

		String accessControlAllowedMethods = _corsHeaders.get(
			ACCESS_CONTROL_ALLOW_METHODS);

		if (!StringUtil.equals(accessControlAllowedMethods, StringPool.STAR)) {
			if (!ArrayUtil.contains(
					StringUtil.split(accessControlAllowedMethods),
					accessControlRequestMethod)) {

				return false;
			}
		}

		return true;
	}

	public boolean isValidCorsRequest(
		String httpMethod,
		Function<String, String> requestHeaderAccessorFunction) {

		if (!isValidOrigin(requestHeaderAccessorFunction.apply(ORIGIN))) {
			return false;
		}

		String accessControlAllowedMethods = _corsHeaders.get(
			ACCESS_CONTROL_ALLOW_METHODS);

		if (!StringUtil.equals(accessControlAllowedMethods, StringPool.STAR)) {
			if (!ArrayUtil.contains(
					StringUtil.split(accessControlAllowedMethods),
					httpMethod)) {

				return false;
			}
		}

		return true;
	}

	public boolean isValidOrigin(String origin) {
		if (Validator.isBlank(origin)) {
			return false;
		}

		String accessControlAllowOrigin = _corsHeaders.get(
			ACCESS_CONTROL_ALLOW_ORIGIN);

		if (Validator.isBlank(accessControlAllowOrigin)) {
			return true;
		}

		if (StringUtil.equals(accessControlAllowOrigin, StringPool.STAR)) {
			return true;
		}

		if (ArrayUtil.contains(
				StringUtil.split(accessControlAllowOrigin), origin)) {

			return true;
		}

		return false;
	}

	public void setCorsHeaders(Map<String, String> corsHeaders) {
		_corsHeaders.putAll(corsHeaders);
	}

	public void setHeader(String key, String value) {
		_corsHeaders.put(key, value);
	}

	public void writeResponseHeaders(
		Function<String, String> requestHeadersAccessor,
		BiConsumer<String, String> responseHeadersConsumer) {

		String origin = requestHeadersAccessor.apply(ORIGIN);

		responseHeadersConsumer.accept(ACCESS_CONTROL_ALLOW_ORIGIN, origin);

		for (Map.Entry<String, String> entry : _corsHeaders.entrySet()) {
			String key = entry.getKey();

			if (StringUtil.equals(key, ACCESS_CONTROL_ALLOW_ORIGIN)) {
				continue;
			}

			responseHeadersConsumer.accept(key, entry.getValue());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(CORSSupport.class);

	private final Map<String, String> _corsHeaders = new HashMap<>();

}