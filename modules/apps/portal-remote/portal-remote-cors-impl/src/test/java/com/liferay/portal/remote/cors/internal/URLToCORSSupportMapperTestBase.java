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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ComparisonFailure;

/**
 * @author Arthur Chan
 */
public abstract class URLToCORSSupportMapperTestBase {

	protected void setUp(String... addtionalURLPatterns) {
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

		for (String urlPattern : addtionalURLPatterns) {
			CORSSupport corsSupport = new CORSSupport();

			corsSupport.setHeader("pattern", urlPattern);

			corsSupports.put(urlPattern, corsSupport);
		}

		_urlToCORSSupportMapper = URLToCORSSupportMapperFactory.create(
			corsSupports);
	}

	protected void testGet() throws Exception {
		for (KeyValuePair keyValuePair : _expectedMatches) {
			CORSSupport corsSupport = _urlToCORSSupportMapper.get(
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

	/**
	 * URL patterns that are neither wildcard nor extension, are considered
	 * exact URL patterns.
	 */
	private static final String[] _URL_PATTERNS_EXACT = {
		"url/pattern/1", "/url/pattern/1/", "url/pattern/1/", "/url/pattern/1",
		"url/pattern/2", "/url/pattern/2/", "url/pattern/2/", "/url/pattern/2",
		"url/pattern/3", "/url/pattern/3/", "url/pattern/3/", "/url/pattern/3",
		"url/pattern/4", "/url/pattern/4/", "url/pattern/4/", "/url/pattern/4",
		"/url/pattern/1/*/", "url/pattern/1/*", "/url/pattern/1*",
		"/url/pattern/2/*/", "url/pattern/2/*", "/url/pattern/2*",
		"/url/pattern/1/*/test", "/url/pattern/2/*/test",
		"url/pattern/test.mp3/", "/url/pattern/test.mp3/"
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
		"/url/pattern/1/1/1/*", "/url/pattern/1/1/*", "/url/pattern/1/*",
		"/url/pattern/2/2/2/*", "/url/pattern/2/2/*", "/url/pattern/2/*",
		"/url/pattern/3/3/3/*", "/url/pattern/3/3/*", "/url/pattern/3/*",
		"/url/pattern/4/4/4/*", "/url/pattern/4/4/*", "/url/pattern/4/*",
		"/url/pattern/*", "/url/*", "/*", "/*/*", "//*", "/*//*",
		"/url/pattern/test.mp3/*", "/test.mp3/*", "/pattern/*", "/1/*"
	};

	private final KeyValuePair[] _expectedMatches = {

		// Exact matches

		new KeyValuePair("url/pattern/1", _URL_PATTERNS_EXACT[0]),
		new KeyValuePair("/url/pattern/1/", _URL_PATTERNS_EXACT[1]),
		new KeyValuePair("url/pattern/1/", _URL_PATTERNS_EXACT[2]),
		new KeyValuePair("/url/pattern/1", _URL_PATTERNS_EXACT[3]),
		new KeyValuePair("/url/pattern/1/*/", _URL_PATTERNS_EXACT[16]),
		new KeyValuePair("url/pattern/1/*", _URL_PATTERNS_EXACT[17]),
		new KeyValuePair("/url/pattern/1*", _URL_PATTERNS_EXACT[18]),
		new KeyValuePair("/url/pattern/1/*/test", _URL_PATTERNS_EXACT[22]),
		new KeyValuePair("url/pattern/test.mp3/", _URL_PATTERNS_EXACT[24]),
		new KeyValuePair("/url/pattern/test.mp3/", _URL_PATTERNS_EXACT[25]),

		// Wildcard matches

		new KeyValuePair("/url/pattern/1/1/1/test", _URL_PATTERNS_WILDCARD[0]),
		new KeyValuePair("/url/pattern/1/1/1/", _URL_PATTERNS_WILDCARD[0]),
		new KeyValuePair("/url/pattern/1/1/1", _URL_PATTERNS_WILDCARD[0]),
		new KeyValuePair("/url/pattern/1/1/test", _URL_PATTERNS_WILDCARD[1]),
		new KeyValuePair("/url/pattern/1/1/", _URL_PATTERNS_WILDCARD[1]),
		new KeyValuePair("/url/pattern/1/1", _URL_PATTERNS_WILDCARD[1]),
		new KeyValuePair("/url/pattern/1/test", _URL_PATTERNS_WILDCARD[2]),
		new KeyValuePair("/url/pattern/1/*/test/t", _URL_PATTERNS_WILDCARD[2]),
		new KeyValuePair("/url/pattern/1/*/test/", _URL_PATTERNS_WILDCARD[2]),
		new KeyValuePair("/url/pattern/5/5/5/", _URL_PATTERNS_WILDCARD[12]),
		new KeyValuePair("/url/random/1/2/3", _URL_PATTERNS_WILDCARD[13]),
		new KeyValuePair("/random/1/2/3", _URL_PATTERNS_WILDCARD[14]),
		new KeyValuePair("/*", _URL_PATTERNS_WILDCARD[15]),
		new KeyValuePair("/", _URL_PATTERNS_WILDCARD[16]),
		new KeyValuePair("/*/", _URL_PATTERNS_WILDCARD[17]),
		new KeyValuePair("/url/pattern/test.mp3", _URL_PATTERNS_WILDCARD[18]),
		new KeyValuePair("/test.mp3/test", _URL_PATTERNS_WILDCARD[19]),
		new KeyValuePair("/test.mp3/", _URL_PATTERNS_WILDCARD[19]),
		new KeyValuePair("/test.mp3", _URL_PATTERNS_WILDCARD[19]),

		// Extension matches

		new KeyValuePair("random/path/test.mp3", _URL_PATTERNS_EXTENSION[9]),
		new KeyValuePair("random/path/test.jsp", _URL_PATTERNS_EXTENSION[14]),
		new KeyValuePair("test.jsp", _URL_PATTERNS_EXTENSION[14]),
	};

	private URLToCORSSupportMapper _urlToCORSSupportMapper;

}