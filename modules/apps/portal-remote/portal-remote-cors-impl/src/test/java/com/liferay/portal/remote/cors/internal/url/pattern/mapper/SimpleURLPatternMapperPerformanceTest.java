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

package com.liferay.portal.remote.cors.internal.url.pattern.mapper;

import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public class SimpleURLPatternMapperPerformanceTest {

	@Test
	public void testGet() {
		KeyValuePair[] keyValuePairs = _createKeyValuePairs();
		URLPatternMapper<String> urlPatternMapper = createURLPatternMapper(
			_createCargos());

		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			for (KeyValuePair keyValuePair : keyValuePairs) {
				urlPatternMapper.get(keyValuePair.getKey());
			}
		}

		long end = System.currentTimeMillis();

		long delta = end - start;

		System.out.println("Iterated 100 thousand times in " + delta + " ms");

		Assert.assertTrue(delta < 2000);
	}

	protected URLPatternMapper<String> createURLPatternMapper(
		Map<String, String> cargos) {

		return new SimpleURLPatternMapper<>(cargos);
	}

	private Map<String, String> _createCargos() {
		Map<String, String> cargos = new HashMap<>();

		// Exact

		_put(cargos, "/url/some/random/pattern/do/test.mp3/");
		_put(cargos, "/url/some/random/pattern/four");
		_put(cargos, "/url/some/random/pattern/four/");
		_put(cargos, "/url/some/random/pattern/one");
		_put(cargos, "/url/some/random/pattern/one*");
		_put(cargos, "/url/some/random/pattern/one/");
		_put(cargos, "/url/some/random/pattern/one/*/");
		_put(cargos, "/url/some/random/pattern/one/*/do/test");
		_put(cargos, "/url/some/random/pattern/three");
		_put(cargos, "/url/some/random/pattern/three/");
		_put(cargos, "/url/some/random/pattern/two");
		_put(cargos, "/url/some/random/pattern/two*");
		_put(cargos, "/url/some/random/pattern/two/");
		_put(cargos, "/url/some/random/pattern/two/*/");
		_put(cargos, "/url/some/random/pattern/two/*/do/test");
		_put(cargos, "url/some/random/pattern/do/test.mp3/");
		_put(cargos, "url/some/random/pattern/four");
		_put(cargos, "url/some/random/pattern/four/");
		_put(cargos, "url/some/random/pattern/one");
		_put(cargos, "url/some/random/pattern/one/");
		_put(cargos, "url/some/random/pattern/one/*");
		_put(cargos, "url/some/random/pattern/three");
		_put(cargos, "url/some/random/pattern/three/");
		_put(cargos, "url/some/random/pattern/two");
		_put(cargos, "url/some/random/pattern/two/");
		_put(cargos, "url/some/random/pattern/two/*");

		// Extension

		_put(cargos, "*.bin");
		_put(cargos, "*.cpp");
		_put(cargos, "*.deb");
		_put(cargos, "*.doc");
		_put(cargos, "*.jpg");
		_put(cargos, "*.jsp");
		_put(cargos, "*.jspf");
		_put(cargos, "*.mov");
		_put(cargos, "*.mp3");
		_put(cargos, "*.pdf");
		_put(cargos, "*.png");
		_put(cargos, "*.tar");
		_put(cargos, "*.txt");
		_put(cargos, "*.xml");
		_put(cargos, "*.xpm");
		_put(cargos, "*.zip");

		// Wildcard

		_put(cargos, "/*");
		_put(cargos, "/*/*");
		_put(cargos, "/*//*");
		_put(cargos, "//*");
		_put(cargos, "/do/test.mp3/*");
		_put(cargos, "/one/*");
		_put(cargos, "/some/random/pattern/*");
		_put(cargos, "/url/*");
		_put(cargos, "/url/some/random/pattern/*");
		_put(cargos, "/url/some/random/pattern/do/test.mp3/*");
		_put(cargos, "/url/some/random/pattern/four/*");
		_put(cargos, "/url/some/random/pattern/four/four/*");
		_put(cargos, "/url/some/random/pattern/four/four/four/*");
		_put(cargos, "/url/some/random/pattern/one/*");
		_put(cargos, "/url/some/random/pattern/one/one/*");
		_put(cargos, "/url/some/random/pattern/one/one/one/*");
		_put(cargos, "/url/some/random/pattern/three/*");
		_put(cargos, "/url/some/random/pattern/three/three/*");
		_put(cargos, "/url/some/random/pattern/three/three/three/*");
		_put(cargos, "/url/some/random/pattern/two/*");
		_put(cargos, "/url/some/random/pattern/two/two/*");
		_put(cargos, "/url/some/random/pattern/two/two/two/*");

		return cargos;
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

	private void _put(Map<String, String> cargos, String urlPattern) {
		cargos.put(urlPattern, urlPattern);
	}

}