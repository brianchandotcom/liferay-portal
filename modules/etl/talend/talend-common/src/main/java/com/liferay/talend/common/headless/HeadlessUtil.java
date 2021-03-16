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

package com.liferay.talend.common.headless;

import com.liferay.talend.common.exception.MalformedURLException;
import com.liferay.talend.common.util.StringUtil;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.UriBuilder;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class HeadlessUtil {

	public static boolean isValidOpenAPISpecURL(String endpointURL) {
		Matcher serverURLMatcher = _liferayOpenAPIURLPattern.matcher(
			endpointURL);

		if (serverURLMatcher.matches()) {
			return true;
		}

		return false;
	}

	public static URI toURI(String href) {
		try {
			return new URI(StringUtil.removeQuotes(href));
		}
		catch (URISyntaxException uriSyntaxException) {
			throw new RuntimeException(uriSyntaxException);
		}
	}

	public static URI updateWithQueryParameters(
		String url, Map<String, String> queryParameters) {

		if ((queryParameters == null) || queryParameters.isEmpty()) {
			return toURI(url);
		}

		UriBuilder uriBuilder = UriBuilder.fromUri(url);

		for (Map.Entry<String, String> parameter : queryParameters.entrySet()) {
			uriBuilder.replaceQueryParam(
				parameter.getKey(), parameter.getValue());
		}

		return uriBuilder.build();
	}

	public static void validateOpenAPISpecURL(String openAPISpecURL) {
		if (openAPISpecURL == null) {
			throw new MalformedURLException("OpenAPI spec URL is null");
		}

		if (!isValidOpenAPISpecURL(openAPISpecURL)) {
			throw new MalformedURLException(
				"Provided OpenAPI specification URL does not match pattern: " +
					_liferayOpenAPIURLPattern.pattern());
		}
	}

	private static final Pattern _liferayOpenAPIURLPattern = Pattern.compile(
		"(https?://.+(:\\d+)?)(/o/(.+)/)(v\\d+(.\\d+)*)/openapi\\.(yaml|json)");

}