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

import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public class URLToCORSSupportMapperPerformanceTest {

	@Before
	public void setUp() {
		urlToCORSSupportMapper = createURLToCORSSupportMapper(
			_buildCORSSupports());
	}

	@Test
	public void testGet() {
		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			for (KeyValuePair keyValuePair : _expectedMatches) {
				urlToCORSSupportMapper.get(keyValuePair.getKey());
			}
		}

		long end = System.currentTimeMillis();

		long delta = end - start;

		System.out.println("Iterated 100 thousand times in " + delta + " ms");

		Assert.assertTrue(delta < 2000);
	}

	protected URLToCORSSupportMapper createURLToCORSSupportMapper(
		Map<String, CORSSupport> corsSupports) {

		return new URLToCORSSupportMapper(corsSupports);
	}

	protected URLToCORSSupportMapper urlToCORSSupportMapper;

	private Map<String, CORSSupport> _buildCORSSupports() {
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