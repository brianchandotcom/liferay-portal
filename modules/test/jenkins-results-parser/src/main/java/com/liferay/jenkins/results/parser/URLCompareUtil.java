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

package com.liferay.jenkins.results.parser;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Yoo
 */
public class URLCompareUtil {

	public static boolean matches(URL url1, URL url2) {
		if (!Objects.equals(url1.getProtocol(), url2.getProtocol())) {
			return false;
		}

		if (!Objects.equals(url1.getHost(), url2.getHost())) {
			return false;
		}

		if (!Objects.equals(_getPort(url1), _getPort(url2))) {
			return false;
		}

		URI uri1 = _toURI(url1);
		URI uri2 = _toURI(url2);

		if (!Objects.equals(
				_normalizePath(uri1.getPath()),
				_normalizePath(uri2.getPath()))) {

			return false;
		}

		if (!Objects.equals(_getQueryMap(uri1), _getQueryMap(uri2))) {
			return false;
		}

		return true;
	}

	private static int _getPort(URL url) {
		int port = url.getPort();

		if (port == -1) {
			port = url.getDefaultPort();
		}

		return port;
	}

	private static Map<String, String> _getQueryMap(URI uri) {
		String query = uri.getQuery();

		Map<String, String> queryMap = new HashMap<>();

		if (query == null) {
			return queryMap;
		}

		Matcher matcher = _queryPattern.matcher(query);

		while (matcher.find()) {
			queryMap.put(matcher.group("name"), matcher.group("value"));
		}

		return queryMap;
	}

	private static String _normalizePath(String path) {
		String normalizedPath = path.replaceAll("/{2,}", "/");

		return normalizedPath.replaceAll("/*$", "");
	}

	private static URI _toURI(URL url) {
		URI uri;

		try {
			uri = url.toURI();
		}
		catch (URISyntaxException urise) {
			throw new RuntimeException(
				"Unable to create URI from URL " + url, urise);
		}

		return uri;
	}

	private static final Pattern _queryPattern = Pattern.compile(
		"(&|\\A)?(?<name>[^=]+)=(?<value>[^&]+)");

}