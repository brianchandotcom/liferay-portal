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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.io.Closeable;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ComparisonFailure;

/**
 * @author Arthur Chan
 */
public abstract class BaseURLToCORSSupportMapperTestBase {

	protected static void setLogFilePath(String path) throws IOException {
		_logFilePath = Paths.get(path);

		Files.deleteIfExists(_logFilePath);
	}

	protected Map<String, CORSSupport> buildCORSSupports() {
		Map<String, CORSSupport> corsSupports = new HashMap<>();

		for (String urlPattern : _URL_PATTERNS_EXACT) {
			CORSSupport corsSupport = new CORSSupport();

			corsSupport.setHeader("pattern", urlPattern);

			corsSupports.put(urlPattern, corsSupport);
		}

		for (String urlPattern : _URL_PATTERNS_WILDCARD) {
			CORSSupport corsSupport = new CORSSupport();

			corsSupport.setHeader("pattern", urlPattern);

			corsSupports.put(urlPattern, corsSupport);
		}

		for (String urlPattern : _URL_PATTERNS_EXTENSION) {
			CORSSupport corsSupport = new CORSSupport();

			corsSupport.setHeader("pattern", urlPattern);

			corsSupports.put(urlPattern, corsSupport);
		}

		return corsSupports;
	}

	protected void testGetCorrectness(
			URLToCORSSupportMapper urlToCORSSupportMapper)
		throws Exception {

		for (KeyValuePair keyValuePair : _expectedMatches) {
			CORSSupport corsSupport = urlToCORSSupportMapper.get(
				keyValuePair.getKey());

			if (corsSupport == null) {
				Assert.assertEquals(StringPool.BLANK, keyValuePair.getValue());

				continue;
			}

			Map<String, String> headers = new HashMap<>();

			corsSupport.writeResponseHeaders(__ -> "origin", headers::put);

			try {
				Assert.assertEquals(
					keyValuePair.getValue(), headers.get("pattern"));
			}
			catch (ComparisonFailure comparisonFailure) {
				throw new ComparisonFailure(
					"When testing " + keyValuePair.getKey() + ":",
					comparisonFailure.getExpected(),
					comparisonFailure.getActual());
			}
		}
	}

	protected void testGetPerformance(
			URLToCORSSupportMapper urlToCORSSupportMapper)
		throws Exception {

		for (int i = 0; i < _ITERATIONS_WARMUP; ++i) {
			for (KeyValuePair keyValuePair : _expectedMatches) {
				urlToCORSSupportMapper.get(keyValuePair.getKey());
			}
		}

		try (Closeable closeable = _startTimer()) {
			for (int i = 0; i < _ITERATIONS_TEST; ++i) {
				for (KeyValuePair keyValuePair : _expectedMatches) {
					urlToCORSSupportMapper.get(keyValuePair.getKey());
				}
			}
		}
	}

	private static void _writeToLogFile(String... contents) throws IOException {
		Files.write(
			_logFilePath, Arrays.asList(contents), StandardOpenOption.APPEND,
			StandardOpenOption.CREATE, StandardOpenOption.WRITE);
	}

	private String _getInvokerName() {
		Thread thread = Thread.currentThread();

		StackTraceElement stackTraceElement = thread.getStackTrace()[3];

		return StringBundler.concat(
			stackTraceElement.getClassName(), StringPool.POUND,
			stackTraceElement.getMethodName());
	}

	private Closeable _startTimer() {
		String invokerName = _getInvokerName();

		long startTime = System.currentTimeMillis();

		return () -> _writeToLogFile(
			StringBundler.concat(
				invokerName, " used ", System.currentTimeMillis() - startTime,
				"ms"));
	}

	private static final int _ITERATIONS_TEST = 5000000;

	private static final int _ITERATIONS_WARMUP = 2000000;

	/**
	 * URL patterns that are neither wildcard nor extension, are considered
	 * exact URL patterns.
	 */
	private static final String[] _URL_PATTERNS_EXACT = {
		"url/some/random/pattern/one", "/url/some/random/pattern/one/",
		"url/some/random/pattern/one/", "/url/some/random/pattern/one",
		"url/some/random/pattern/two", "/url/some/random/pattern/two/",
		"url/some/random/pattern/two/", "/url/some/random/pattern/two",
		"url/some/random/pattern/three", "/url/some/random/pattern/three/",
		"url/some/random/pattern/three/", "/url/some/random/pattern/three",
		"url/some/random/pattern/four", "/url/some/random/pattern/four/",
		"url/some/random/pattern/four/", "/url/some/random/pattern/four",
		"/url/some/random/pattern/one/*/", "url/some/random/pattern/one/*",
		"/url/some/random/pattern/one*", "/url/some/random/pattern/two/*/",
		"url/some/random/pattern/two/*", "/url/some/random/pattern/two*",
		"/url/some/random/pattern/one/*/do/test",
		"/url/some/random/pattern/two/*/do/test",
		"url/some/random/pattern/do/test.mp3/",
		"/url/some/random/pattern/do/test.mp3/"
	};

	/**
	 * URL patterns that start with "*.", and end with a valid file extension
	 * are considered extension URL patterns.
	 */
	private static final String[] _URL_PATTERNS_EXTENSION = {
		"*.mov", "*.xml", "*.cpp", "*.xpm", "*.pdf", "*.deb", "*.doc", "*.bin",
		"*.zip", "*.mp3", "*.jpg", "*.png", "*.txt", "*.tar", "*.jsp", "*.jspf"
	};

	/**
	 * URL patterns that start with '/', and end with "/*" are considered
	 * wildcard URL patterns.
	 */
	private static final String[] _URL_PATTERNS_WILDCARD = {
		"/url/some/random/pattern/one/one/one/*",
		"/url/some/random/pattern/one/one/*", "/url/some/random/pattern/one/*",
		"/url/some/random/pattern/two/two/two/*",
		"/url/some/random/pattern/two/two/*", "/url/some/random/pattern/two/*",
		"/url/some/random/pattern/three/three/three/*",
		"/url/some/random/pattern/three/three/*",
		"/url/some/random/pattern/three/*",
		"/url/some/random/pattern/four/four/four/*",
		"/url/some/random/pattern/four/four/*",
		"/url/some/random/pattern/four/*", "/url/some/random/pattern/*",
		"/url/*", "/*", "/*/*", "//*", "/*//*",
		"/url/some/random/pattern/do/test.mp3/*", "/do/test.mp3/*",
		"/some/random/pattern/*", "/one/*"
	};

	private static Path _logFilePath;

	private final KeyValuePair[] _expectedMatches = {

		// Exact matches

		new KeyValuePair("url/some/random/pattern/one", _URL_PATTERNS_EXACT[0]),
		new KeyValuePair(
			"/url/some/random/pattern/one/", _URL_PATTERNS_EXACT[1]),
		new KeyValuePair(
			"url/some/random/pattern/one/", _URL_PATTERNS_EXACT[2]),
		new KeyValuePair(
			"/url/some/random/pattern/one", _URL_PATTERNS_EXACT[3]),
		new KeyValuePair(
			"/url/some/random/pattern/one/*/", _URL_PATTERNS_EXACT[16]),
		new KeyValuePair(
			"url/some/random/pattern/one/*", _URL_PATTERNS_EXACT[17]),
		new KeyValuePair(
			"/url/some/random/pattern/one*", _URL_PATTERNS_EXACT[18]),
		new KeyValuePair(
			"/url/some/random/pattern/one/*/do/test", _URL_PATTERNS_EXACT[22]),
		new KeyValuePair(
			"url/some/random/pattern/do/test.mp3/", _URL_PATTERNS_EXACT[24]),
		new KeyValuePair(
			"/url/some/random/pattern/do/test.mp3/", _URL_PATTERNS_EXACT[25]),

		// Wildcard matches

		new KeyValuePair(
			"/url/some/random/pattern/one/one/one/./some/very/long/./do/test",
			_URL_PATTERNS_WILDCARD[0]),
		new KeyValuePair(
			"/url/some/random/pattern/one/one/one/./some/very/long/./do/test/",
			_URL_PATTERNS_WILDCARD[0]),
		new KeyValuePair(
			"/url/some/random/pattern/one/one/one/do/test",
			_URL_PATTERNS_WILDCARD[0]),
		new KeyValuePair(
			"/url/some/random/pattern/one/one/one/", _URL_PATTERNS_WILDCARD[0]),
		new KeyValuePair(
			"/url/some/random/pattern/one/one/one", _URL_PATTERNS_WILDCARD[0]),
		new KeyValuePair(
			"/url/some/random/pattern/one/one/do/test",
			_URL_PATTERNS_WILDCARD[1]),
		new KeyValuePair(
			"/url/some/random/pattern/one/one/", _URL_PATTERNS_WILDCARD[1]),
		new KeyValuePair(
			"/url/some/random/pattern/one/one", _URL_PATTERNS_WILDCARD[1]),
		new KeyValuePair(
			"/url/some/random/pattern/one/do/test", _URL_PATTERNS_WILDCARD[2]),
		new KeyValuePair(
			"/url/some/random/pattern/one/*/do/test/t",
			_URL_PATTERNS_WILDCARD[2]),
		new KeyValuePair(
			"/url/some/random/pattern/one/*/do/test/",
			_URL_PATTERNS_WILDCARD[2]),
		new KeyValuePair(
			"/url/some/random/pattern/two/two/two/./some/very/long/./do/test",
			_URL_PATTERNS_WILDCARD[3]),
		new KeyValuePair(
			"/url/some/random/pattern/two/two/two/./some/very/long/./do/test/",
			_URL_PATTERNS_WILDCARD[3]),
		new KeyValuePair(
			"/url/some/random/pattern/two/two/two/do/test",
			_URL_PATTERNS_WILDCARD[3]),
		new KeyValuePair(
			"/url/some/random/pattern/two/two/two/", _URL_PATTERNS_WILDCARD[3]),
		new KeyValuePair(
			"/url/some/random/pattern/two/two/two", _URL_PATTERNS_WILDCARD[3]),
		new KeyValuePair(
			"/url/some/random/pattern/two/two/do/test",
			_URL_PATTERNS_WILDCARD[4]),
		new KeyValuePair(
			"/url/some/random/pattern/two/two/", _URL_PATTERNS_WILDCARD[4]),
		new KeyValuePair(
			"/url/some/random/pattern/two/two", _URL_PATTERNS_WILDCARD[4]),
		new KeyValuePair(
			"/url/some/random/pattern/two/do/test", _URL_PATTERNS_WILDCARD[5]),
		new KeyValuePair(
			"/url/some/random/pattern/two/*/do/test/t",
			_URL_PATTERNS_WILDCARD[5]),
		new KeyValuePair(
			"/url/some/random/pattern/two/*/do/test/",
			_URL_PATTERNS_WILDCARD[5]),
		new KeyValuePair(
			"/url/some/random/pattern/five/five/five/",
			_URL_PATTERNS_WILDCARD[12]),
		new KeyValuePair(
			"/url/random/one/two/three", _URL_PATTERNS_WILDCARD[13]),
		new KeyValuePair(
			"/random/one/two/three", _URL_PATTERNS_WILDCARD[14]),
		new KeyValuePair("/*", _URL_PATTERNS_WILDCARD[15]),
		new KeyValuePair("/", _URL_PATTERNS_WILDCARD[16]),
		new KeyValuePair("/*/", _URL_PATTERNS_WILDCARD[17]),
		new KeyValuePair(
			"/url/some/random/pattern/do/test.mp3", _URL_PATTERNS_WILDCARD[18]),
		new KeyValuePair("/do/test.mp3/do/test", _URL_PATTERNS_WILDCARD[19]),
		new KeyValuePair("/do/test.mp3/", _URL_PATTERNS_WILDCARD[19]),
		new KeyValuePair("/do/test.mp3", _URL_PATTERNS_WILDCARD[19]),

		// Extension matches

		new KeyValuePair("random/path/do/test.mp3", _URL_PATTERNS_EXTENSION[9]),
		new KeyValuePair(
			"random/path/do/test.jsp", _URL_PATTERNS_EXTENSION[14]),
		new KeyValuePair("test.jsp", _URL_PATTERNS_EXTENSION[14]),
	};

}