/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static Map<String, String> buildCORSHeaders(
		String[] corsHeaderStrings) {

		Map<String, String> headers = new HashMap<>();

		for (String header : corsHeaderStrings) {
			int index = header.indexOf(CharPool.COLON);

			if (index < 1) {
				if (_log.isWarnEnabled()) {
					_log.warn("Incorrect CORS header: " + header);
				}

				continue;
			}

			headers.put(
				StringUtil.trim(header.substring(0, index)),
				StringUtil.trim(header.substring(index + 1)));
		}

		return headers;
	}

	public static boolean isCORSRequest(
		Function<String, String> requestHeadersFunction) {

		String origin = requestHeadersFunction.apply(ORIGIN);

		if (Validator.isBlank(origin)) {
			return false;
		}

		return true;
	}

	public boolean isValidCORSPreflightRequest(
		Function<String, String> requestHeadersFunction) {

		String origin = requestHeadersFunction.apply(ORIGIN);

		if (!isValidOrigin(origin)) {
			return false;
		}

		String accessControlRequestMethod = requestHeadersFunction.apply(
			ACCESS_CONTROL_REQUEST_METHOD);

		if (Validator.isBlank(accessControlRequestMethod)) {
			return false;
		}

		String accessControlAllowedMethods = _corsHeaders.get(
			ACCESS_CONTROL_ALLOW_METHODS);

		if (!StringUtil.equals(accessControlAllowedMethods, StringPool.STAR) &&
			!ArrayUtil.contains(
				StringUtil.split(accessControlAllowedMethods),
				accessControlRequestMethod)) {

			return false;
		}

		return true;
	}

	public boolean isValidCORSRequest(
		String httpMethod, Function<String, String> requestHeadersFunction) {

		if (!isValidOrigin(requestHeadersFunction.apply(ORIGIN))) {
			return false;
		}

		String accessControlAllowedMethods = _corsHeaders.get(
			ACCESS_CONTROL_ALLOW_METHODS);

		if (!StringUtil.equals(accessControlAllowedMethods, StringPool.STAR) &&
			!ArrayUtil.contains(
				StringUtil.split(accessControlAllowedMethods), httpMethod)) {

			return false;
		}

		return true;
	}

	public boolean isValidOrigin(String origin) {
		if (Validator.isBlank(origin)) {
			return false;
		}

		String accessControlAllowOrigin = _corsHeaders.get(
			ACCESS_CONTROL_ALLOW_ORIGIN);

		if (Validator.isBlank(accessControlAllowOrigin) ||
			StringUtil.equals(accessControlAllowOrigin, StringPool.STAR) ||
			ArrayUtil.contains(
				StringUtil.split(accessControlAllowOrigin), origin)) {

			return true;
		}

		return false;
	}

	public void setCORSHeaders(Map<String, String> corsHeaders) {
		_corsHeaders.putAll(corsHeaders);
	}

	public void setHeader(String key, String value) {
		_corsHeaders.put(key, value);
	}

	public void writeResponseHeaders(
		Function<String, String> requestHeadersFunction,
		BiConsumer<String, String> responseHeadersBiConsumer) {

		String origin = requestHeadersFunction.apply(ORIGIN);

		responseHeadersBiConsumer.accept(ACCESS_CONTROL_ALLOW_ORIGIN, origin);

		for (Map.Entry<String, String> entry : _corsHeaders.entrySet()) {
			String key = entry.getKey();

			if (StringUtil.equals(key, ACCESS_CONTROL_ALLOW_ORIGIN)) {
				continue;
			}

			responseHeadersBiConsumer.accept(key, entry.getValue());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(CORSSupport.class);

	private final Map<String, String> _corsHeaders = new HashMap<>();

}