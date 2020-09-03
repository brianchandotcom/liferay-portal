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
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public class SimpleURLToCORSSupportMapperPerformanceTest {

	@Test
	public void testGet() {
		KeyValuePair[] keyValuePairs = _createKeyValuePairs();
		URLToCORSSupportMapper urlToCORSSupportMapper =
			createURLToCORSSupportMapper(_createCORSSupports());

		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			for (KeyValuePair keyValuePair : keyValuePairs) {
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

		return new SimpleURLToCORSSupportMapper(corsSupports);
	}

	private Map<String, CORSSupport> _createCORSSupports() {
		Map<String, CORSSupport> corsSupports = new HashMap<>();

		// Exact

		_put(corsSupports, "/url/some/random/pattern/do/test.mp3/");
		_put(corsSupports, "/url/some/random/pattern/four");
		_put(corsSupports, "/url/some/random/pattern/four/");
		_put(corsSupports, "/url/some/random/pattern/one");
		_put(corsSupports, "/url/some/random/pattern/one*");
		_put(corsSupports, "/url/some/random/pattern/one/");
		_put(corsSupports, "/url/some/random/pattern/one/*/");
		_put(corsSupports, "/url/some/random/pattern/one/*/do/test");
		_put(corsSupports, "/url/some/random/pattern/three");
		_put(corsSupports, "/url/some/random/pattern/three/");
		_put(corsSupports, "/url/some/random/pattern/two");
		_put(corsSupports, "/url/some/random/pattern/two*");
		_put(corsSupports, "/url/some/random/pattern/two/");
		_put(corsSupports, "/url/some/random/pattern/two/*/");
		_put(corsSupports, "/url/some/random/pattern/two/*/do/test");
		_put(corsSupports, "url/some/random/pattern/do/test.mp3/");
		_put(corsSupports, "url/some/random/pattern/four");
		_put(corsSupports, "url/some/random/pattern/four/");
		_put(corsSupports, "url/some/random/pattern/one");
		_put(corsSupports, "url/some/random/pattern/one/");
		_put(corsSupports, "url/some/random/pattern/one/*");
		_put(corsSupports, "url/some/random/pattern/three");
		_put(corsSupports, "url/some/random/pattern/three/");
		_put(corsSupports, "url/some/random/pattern/two");
		_put(corsSupports, "url/some/random/pattern/two/");
		_put(corsSupports, "url/some/random/pattern/two/*");

		// Extension

		_put(corsSupports, "*.bin");
		_put(corsSupports, "*.cpp");
		_put(corsSupports, "*.deb");
		_put(corsSupports, "*.doc");
		_put(corsSupports, "*.jpg");
		_put(corsSupports, "*.jsp");
		_put(corsSupports, "*.jspf");
		_put(corsSupports, "*.mov");
		_put(corsSupports, "*.mp3");
		_put(corsSupports, "*.pdf");
		_put(corsSupports, "*.png");
		_put(corsSupports, "*.tar");
		_put(corsSupports, "*.txt");
		_put(corsSupports, "*.xml");
		_put(corsSupports, "*.xpm");
		_put(corsSupports, "*.zip");

		// Wildcard

		_put(corsSupports, "/*");
		_put(corsSupports, "/*/*");
		_put(corsSupports, "/*//*");
		_put(corsSupports, "//*");
		_put(corsSupports, "/do/test.mp3/*");
		_put(corsSupports, "/one/*");
		_put(corsSupports, "/some/random/pattern/*");
		_put(corsSupports, "/url/*");
		_put(corsSupports, "/url/some/random/pattern/*");
		_put(corsSupports, "/url/some/random/pattern/do/test.mp3/*");
		_put(corsSupports, "/url/some/random/pattern/four/*");
		_put(corsSupports, "/url/some/random/pattern/four/four/*");
		_put(corsSupports, "/url/some/random/pattern/four/four/four/*");
		_put(corsSupports, "/url/some/random/pattern/one/*");
		_put(corsSupports, "/url/some/random/pattern/one/one/*");
		_put(corsSupports, "/url/some/random/pattern/one/one/one/*");
		_put(corsSupports, "/url/some/random/pattern/three/*");
		_put(corsSupports, "/url/some/random/pattern/three/three/*");
		_put(corsSupports, "/url/some/random/pattern/three/three/three/*");
		_put(corsSupports, "/url/some/random/pattern/two/*");
		_put(corsSupports, "/url/some/random/pattern/two/two/*");
		_put(corsSupports, "/url/some/random/pattern/two/two/two/*");

		return corsSupports;
	}

	private KeyValuePair[] _createKeyValuePairs() {
		return new KeyValuePair[] {

			// Exact

			new KeyValuePair(
				"/url/some/random/pattern/do/test.mp3/",
				"/url/some/random/pattern/do/test.mp3/"),
			new KeyValuePair(
				"/url/some/random/pattern/one",
				"/url/some/random/pattern/one"),
			new KeyValuePair(
				"/url/some/random/pattern/one*",
				"/url/some/random/pattern/one*"),
			new KeyValuePair(
				"/url/some/random/pattern/one/",
				"/url/some/random/pattern/one/"),
			new KeyValuePair(
				"/url/some/random/pattern/one/*/",
				"/url/some/random/pattern/one/*/"),
			new KeyValuePair(
				"/url/some/random/pattern/one/*/do/test",
				"/url/some/random/pattern/one/*/do/test"),
			new KeyValuePair(
				"url/some/random/pattern/do/test.mp3/",
				"url/some/random/pattern/do/test.mp3/"),
			new KeyValuePair(
				"url/some/random/pattern/one", "url/some/random/pattern/one"),
			new KeyValuePair(
				"url/some/random/pattern/one/", "url/some/random/pattern/one/"),
			new KeyValuePair(
				"url/some/random/pattern/one/*",
				"url/some/random/pattern/one/*"),

			// Extension

			new KeyValuePair("random/path/do/test.jsp", "*.jsp"),
			new KeyValuePair("random/path/do/test.mp3", "*.mp3"),
			new KeyValuePair("test.jsp", "*.jsp"),

			// Wildcard

			new KeyValuePair("/", "//*"),
			new KeyValuePair("/*", "/*/*"),
			new KeyValuePair("/*/", "/*//*"),
			new KeyValuePair("/do/test.mp3", "/do/test.mp3/*"),
			new KeyValuePair("/do/test.mp3/", "/do/test.mp3/*"),
			new KeyValuePair("/do/test.mp3/do/test", "/do/test.mp3/*"),
			new KeyValuePair("/random/one/two/three", "/*"),
			new KeyValuePair("/url/random/one/two/three", "/url/*"),
			new KeyValuePair(
				"/url/some/random/pattern/do/test.mp3",
				"/url/some/random/pattern/do/test.mp3/*"),
			new KeyValuePair(
				"/url/some/random/pattern/five/five/five/",
				"/url/some/random/pattern/*"),
			new KeyValuePair(
				"/url/some/random/pattern/one/*/do/test/",
				"/url/some/random/pattern/one/*"),
			new KeyValuePair(
				"/url/some/random/pattern/one/*/do/test/t",
				"/url/some/random/pattern/one/*"),
			new KeyValuePair(
				"/url/some/random/pattern/one/do/test",
				"/url/some/random/pattern/one/*"),
			new KeyValuePair(
				"/url/some/random/pattern/one/one",
				"/url/some/random/pattern/one/one/*"),
			new KeyValuePair(
				"/url/some/random/pattern/one/one/",
				"/url/some/random/pattern/one/one/*"),
			new KeyValuePair(
				"/url/some/random/pattern/one/one/do/test",
				"/url/some/random/pattern/one/one/*"),
			new KeyValuePair(
				"/url/some/random/pattern/one/one/one",
				"/url/some/random/pattern/one/one/one/*"),
			new KeyValuePair(
				"/url/some/random/pattern/one/one/one/",
				"/url/some/random/pattern/one/one/one/*"),
			new KeyValuePair(
				"/url/some/random/pattern/one/one/one/./some/very/long/./do" +
					"/test",
				"/url/some/random/pattern/one/one/one/*"),
			new KeyValuePair(
				"/url/some/random/pattern/one/one/one/./some/very/long/./do" +
					"/test/",
				"/url/some/random/pattern/one/one/one/*"),
			new KeyValuePair(
				"/url/some/random/pattern/one/one/one/do/test",
				"/url/some/random/pattern/one/one/one/*"),
			new KeyValuePair(
				"/url/some/random/pattern/two/*/do/test/",
				"/url/some/random/pattern/two/*"),
			new KeyValuePair(
				"/url/some/random/pattern/two/*/do/test/t",
				"/url/some/random/pattern/two/*"),
			new KeyValuePair(
				"/url/some/random/pattern/two/do/test",
				"/url/some/random/pattern/two/*"),
			new KeyValuePair(
				"/url/some/random/pattern/two/two",
				"/url/some/random/pattern/two/two/*"),
			new KeyValuePair(
				"/url/some/random/pattern/two/two/",
				"/url/some/random/pattern/two/two/*"),
			new KeyValuePair(
				"/url/some/random/pattern/two/two/do/test",
				"/url/some/random/pattern/two/two/*"),
			new KeyValuePair(
				"/url/some/random/pattern/two/two/two",
				"/url/some/random/pattern/two/two/two/*"),
			new KeyValuePair(
				"/url/some/random/pattern/two/two/two/",
				"/url/some/random/pattern/two/two/two/*"),
			new KeyValuePair(
				"/url/some/random/pattern/two/two/two/./some/very/long/./do" +
					"/test",
				"/url/some/random/pattern/two/two/two/*"),
			new KeyValuePair(
				"/url/some/random/pattern/two/two/two/./some/very/long/./do" +
					"/test/",
				"/url/some/random/pattern/two/two/two/*"),
			new KeyValuePair(
				"/url/some/random/pattern/two/two/two/do/test",
				"/url/some/random/pattern/two/two/two/*")
		};
	}

	private void _put(
		Map<String, CORSSupport> corsSupports, String urlPattern) {

		CORSSupport corsSupport = new CORSSupport();

		corsSupport.setHeader("pattern", urlPattern);

		corsSupports.put(urlPattern, corsSupport);
	}

}