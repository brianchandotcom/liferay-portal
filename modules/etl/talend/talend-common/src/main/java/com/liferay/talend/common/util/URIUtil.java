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

package com.liferay.talend.common.util;

import com.liferay.talend.common.exception.MalformedURLException;
import com.liferay.talend.common.exception.URIPathException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zoltán Takács
 */
public class URIUtil {

	public static URI addQueryConditionToURL(
		String resourceURL, String queryCondition) {

		if (StringUtil.isEmpty(queryCondition)) {
			if (_logger.isDebugEnabled()) {
				_logger.debug("Query condition was empty!");
			}

			return toURI(resourceURL);
		}

		return updateWithQueryParameters(
			resourceURL, Collections.singletonMap("filter", queryCondition));
	}

	public static String extractEndpointPathSegment(URL openAPISpecURL) {
		String openAPISpecRef = openAPISpecURL.toExternalForm();

		Matcher serverURLMatcher = _openAPISpecURLPattern.matcher(
			openAPISpecRef);

		if (!serverURLMatcher.matches()) {
			throw new MalformedURLException(
				"Unable to extract OpenAPI endpoint from URL " +
					openAPISpecRef);
		}

		String serverInstanceURL = serverURLMatcher.group(1);

		String endpoint = openAPISpecRef.substring(serverInstanceURL.length());

		String endpointExtension = serverURLMatcher.group(7);

		if (endpointExtension.equals("yaml")) {
			endpoint = endpoint.replace(".yaml", ".json");
		}

		return endpoint;
	}

	public static String extractJaxRSAppBasePathSegment(URL openAPISpecURL) {
		String openAPISpecRef = openAPISpecURL.toExternalForm();

		Matcher serverURLMatcher = _openAPISpecURLPattern.matcher(
			openAPISpecRef);

		if (!serverURLMatcher.matches()) {
			throw new MalformedURLException(
				"Unable to extract OpenAPI endpoint from URL " +
					openAPISpecRef);
		}

		return serverURLMatcher.group(3);
	}

	public static URL extractServerURL(URL openAPISpecURL) {
		String protocol = openAPISpecURL.getProtocol();
		String host = openAPISpecURL.getHost();
		int port = openAPISpecURL.getPort();

		URL serverURL = null;

		try {
			serverURL = new URL(protocol, host, port, "");
		}
		catch (java.net.MalformedURLException murle) {
			throw new MalformedURLException(murle);
		}

		return serverURL;
	}

	public static String getLastPathSegment(String url) {
		URI uri = toURI(url);

		String path = uri.getPath();

		if (path == null) {
			throw new URIPathException("Missing URI path from the URL: " + url);
		}

		while (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}

		int pos = path.lastIndexOf("/");

		if (pos == -1) {
			throw new URIPathException(
				"Unable to find path segments in the URL: " + url);
		}

		return path.substring(pos + 1);
	}

	public static boolean isValidOpenAPISpecURL(String endpointURL) {
		Matcher serverURLMatcher = _openAPISpecURLPattern.matcher(endpointURL);

		if (serverURLMatcher.matches()) {
			return true;
		}

		return false;
	}

	public static URI setPaginationLimitOnURL(String resourceURL, int limit) {
		return updateWithQueryParameters(
			resourceURL,
			Collections.singletonMap("pageSize", String.valueOf(limit)));
	}

	public static URI toURI(String href) {
		try {
			return new URI(StringUtil.removeQuotes(href));
		}
		catch (URISyntaxException urise) {
			_logger.error("Unable to convert URL to URI: " + href);

			throw new RuntimeException(urise);
		}
	}

	public static URL toURL(String href) {
		URL url = null;

		try {
			url = new URL(StringUtil.removeQuotes(href));
		}
		catch (java.net.MalformedURLException murle) {
			throw new MalformedURLException(murle);
		}

		return url;
	}

	public static URI updateWithQueryParameters(
		String url, Map<String, String> queryParameters) {

		if ((queryParameters == null) || queryParameters.isEmpty()) {
			return toURI(url);
		}

		URI uri = null;

		for (Map.Entry<String, String> parameter : queryParameters.entrySet()) {
			UriBuilder uriBuilder = UriBuilder.fromUri(url);

			uri = uriBuilder.replaceQueryParam(
				parameter.getKey(), parameter.getValue()
			).build();
		}

		return uri;
	}

	public static URI updateWithQueryParameters(
		URI uri, Map<String, String> queryParameters) {

		if ((queryParameters == null) || queryParameters.isEmpty()) {
			return uri;
		}

		for (Map.Entry<String, String> parameter : queryParameters.entrySet()) {
			UriBuilder uriBuilder = UriBuilder.fromUri(uri);

			uri = uriBuilder.replaceQueryParam(
				parameter.getKey(), parameter.getValue()
			).build();
		}

		return uri;
	}

	public static void validateOpenAPISpecURL(String openAPISpecURL) {
		if (!isValidOpenAPISpecURL(openAPISpecURL)) {
			throw new MalformedURLException(
				"Provided OpenAPI specification URL does not match pattern: " +
					_openAPISpecURLPattern.pattern());
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		URIUtil.class);

	private static final Pattern _openAPISpecURLPattern = Pattern.compile(
		"(https?://.+(:\\d+)?)(/o/(.+)/)(v\\d+(.\\d+)*)/openapi\\.(yaml|json)");

}