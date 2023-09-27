/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

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
		if (!Objects.equals(url1.getProtocol(), url2.getProtocol()) ||
			!Objects.equals(url1.getHost(), url2.getHost()) ||
			!Objects.equals(_getPort(url1), _getPort(url2)) ||
			!Objects.equals(
				_normalizePath(url1.getPath()),
				_normalizePath(url2.getPath())) ||
			!Objects.equals(_getQueryMap(url1), _getQueryMap(url2))) {

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

	private static Map<String, String> _getQueryMap(URL url) {
		String query = url.getQuery();

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

	private static final Pattern _queryPattern = Pattern.compile(
		"(&|\\A)?(?<name>[^=]+)=(?<value>[^&]+)");

}