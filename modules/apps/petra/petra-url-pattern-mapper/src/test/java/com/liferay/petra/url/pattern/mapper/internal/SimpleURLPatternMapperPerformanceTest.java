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

package com.liferay.petra.url.pattern.mapper.internal;

import com.liferay.petra.url.pattern.mapper.URLPatternMapper;

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
		Map<String, String> urlPatterns = _createURLPatterns();
		URLPatternMapper<String> urlPatternMapper = createURLPatternMapper(
			_createValues());

		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			for (String urlPath : urlPatterns.keySet()) {
				urlPatternMapper.getValue(urlPath);
			}
		}

		long end = System.currentTimeMillis();

		long delta = end - start;

		System.out.println("Iterated 100 thousand times in " + delta + " ms");

		Assert.assertTrue(delta < 2000);
	}

	protected URLPatternMapper<String> createURLPatternMapper(
		Map<String, String> values) {

		return new SimpleURLPatternMapper<>(values);
	}

	private Map<String, String> _createURLPatterns() {
		return new HashMap<String, String>() {
			{

				// Exact

				put(
					"/url/some/random/pattern/do/test.mp3/",
					"/url/some/random/pattern/do/test.mp3/");
				put(
					"/url/some/random/pattern/one",
					"/url/some/random/pattern/one");
				put(
					"/url/some/random/pattern/one*",
					"/url/some/random/pattern/one*");
				put(
					"/url/some/random/pattern/one/",
					"/url/some/random/pattern/one/");
				put(
					"/url/some/random/pattern/one/*/",
					"/url/some/random/pattern/one/*/");
				put(
					"/url/some/random/pattern/one/*/do/test",
					"/url/some/random/pattern/one/*/do/test");
				put(
					"url/some/random/pattern/do/test.mp3/",
					"url/some/random/pattern/do/test.mp3/");
				put(
					"url/some/random/pattern/one",
					"url/some/random/pattern/one");
				put(
					"url/some/random/pattern/one/",
					"url/some/random/pattern/one/");
				put(
					"url/some/random/pattern/one/*",
					"url/some/random/pattern/one/*");

				// Extension

				put("random/path/do/test.jsp", "*.jsp");
				put("random/path/do/test.mp3", "*.mp3");
				put("test.jsp", "*.jsp");

				// Wildcard

				put("/", "//*");
				put("/*", "/*/*");
				put("/*/", "/*//*");
				put("/do/test.mp3", "/do/test.mp3/*");
				put("/do/test.mp3/", "/do/test.mp3/*");
				put("/do/test.mp3/do/test", "/do/test.mp3/*");
				put("/random/one/two/three", "/*");
				put("/url/random/one/two/three", "/url/*");
				put(
					"/url/some/random/pattern/do/test.mp3",
					"/url/some/random/pattern/do/test.mp3/*");
				put(
					"/url/some/random/pattern/five/five/five/",
					"/url/some/random/pattern/*");
				put(
					"/url/some/random/pattern/one/*/do/test/",
					"/url/some/random/pattern/one/*");
				put(
					"/url/some/random/pattern/one/*/do/test/t",
					"/url/some/random/pattern/one/*");
				put(
					"/url/some/random/pattern/one/do/test",
					"/url/some/random/pattern/one/*");
				put(
					"/url/some/random/pattern/one/one",
					"/url/some/random/pattern/one/one/*");
				put(
					"/url/some/random/pattern/one/one/",
					"/url/some/random/pattern/one/one/*");
				put(
					"/url/some/random/pattern/one/one/do/test",
					"/url/some/random/pattern/one/one/*");
				put(
					"/url/some/random/pattern/one/one/one",
					"/url/some/random/pattern/one/one/one/*");
				put(
					"/url/some/random/pattern/one/one/one/",
					"/url/some/random/pattern/one/one/one/*");
				put(
					"/url/some/random/pattern/one/one/one/./some/very/long/do",
					"/url/some/random/pattern/one/one/one/*");
				put(
					"/url/some/random/pattern/one/one/one/./some/very/long/do/",
					"/url/some/random/pattern/one/one/one/*");
				put(
					"/url/some/random/pattern/one/one/one/do/test",
					"/url/some/random/pattern/one/one/one/*");
				put(
					"/url/some/random/pattern/two/*/do/test/",
					"/url/some/random/pattern/two/*");
				put(
					"/url/some/random/pattern/two/*/do/test/t",
					"/url/some/random/pattern/two/*");
				put(
					"/url/some/random/pattern/two/do/test",
					"/url/some/random/pattern/two/*");
				put(
					"/url/some/random/pattern/two/two",
					"/url/some/random/pattern/two/two/*");
				put(
					"/url/some/random/pattern/two/two/",
					"/url/some/random/pattern/two/two/*");
				put(
					"/url/some/random/pattern/two/two/do/test",
					"/url/some/random/pattern/two/two/*");
				put(
					"/url/some/random/pattern/two/two/two",
					"/url/some/random/pattern/two/two/two/*");
				put(
					"/url/some/random/pattern/two/two/two/",
					"/url/some/random/pattern/two/two/two/*");
				put(
					"/url/some/random/pattern/two/two/two/./some/very/long/do",
					"/url/some/random/pattern/two/two/two/*");
				put(
					"/url/some/random/pattern/two/two/two/./some/very/long/do/",
					"/url/some/random/pattern/two/two/two/*");
				put(
					"/url/some/random/pattern/two/two/two/do/test",
					"/url/some/random/pattern/two/two/two/*");
			}
		};
	}

	private Map<String, String> _createValues() {
		Map<String, String> values = new HashMap<>();

		// Exact

		_put(values, "/url/some/random/pattern/do/test.mp3/");
		_put(values, "/url/some/random/pattern/four");
		_put(values, "/url/some/random/pattern/four/");
		_put(values, "/url/some/random/pattern/one");
		_put(values, "/url/some/random/pattern/one*");
		_put(values, "/url/some/random/pattern/one/");
		_put(values, "/url/some/random/pattern/one/*/");
		_put(values, "/url/some/random/pattern/one/*/do/test");
		_put(values, "/url/some/random/pattern/three");
		_put(values, "/url/some/random/pattern/three/");
		_put(values, "/url/some/random/pattern/two");
		_put(values, "/url/some/random/pattern/two*");
		_put(values, "/url/some/random/pattern/two/");
		_put(values, "/url/some/random/pattern/two/*/");
		_put(values, "/url/some/random/pattern/two/*/do/test");
		_put(values, "url/some/random/pattern/do/test.mp3/");
		_put(values, "url/some/random/pattern/four");
		_put(values, "url/some/random/pattern/four/");
		_put(values, "url/some/random/pattern/one");
		_put(values, "url/some/random/pattern/one/");
		_put(values, "url/some/random/pattern/one/*");
		_put(values, "url/some/random/pattern/three");
		_put(values, "url/some/random/pattern/three/");
		_put(values, "url/some/random/pattern/two");
		_put(values, "url/some/random/pattern/two/");
		_put(values, "url/some/random/pattern/two/*");

		// Extension

		_put(values, "*.bin");
		_put(values, "*.cpp");
		_put(values, "*.deb");
		_put(values, "*.doc");
		_put(values, "*.jpg");
		_put(values, "*.jsp");
		_put(values, "*.jspf");
		_put(values, "*.mov");
		_put(values, "*.mp3");
		_put(values, "*.pdf");
		_put(values, "*.png");
		_put(values, "*.tar");
		_put(values, "*.txt");
		_put(values, "*.xml");
		_put(values, "*.xpm");
		_put(values, "*.zip");

		// Wildcard

		_put(values, "/*");
		_put(values, "/*/*");
		_put(values, "/*//*");
		_put(values, "//*");
		_put(values, "/do/test.mp3/*");
		_put(values, "/one/*");
		_put(values, "/some/random/pattern/*");
		_put(values, "/url/*");
		_put(values, "/url/some/random/pattern/*");
		_put(values, "/url/some/random/pattern/do/test.mp3/*");
		_put(values, "/url/some/random/pattern/four/*");
		_put(values, "/url/some/random/pattern/four/four/*");
		_put(values, "/url/some/random/pattern/four/four/four/*");
		_put(values, "/url/some/random/pattern/one/*");
		_put(values, "/url/some/random/pattern/one/one/*");
		_put(values, "/url/some/random/pattern/one/one/one/*");
		_put(values, "/url/some/random/pattern/three/*");
		_put(values, "/url/some/random/pattern/three/three/*");
		_put(values, "/url/some/random/pattern/three/three/three/*");
		_put(values, "/url/some/random/pattern/two/*");
		_put(values, "/url/some/random/pattern/two/two/*");
		_put(values, "/url/some/random/pattern/two/two/two/*");

		return values;
	}

	private void _put(Map<String, String> values, String urlPattern) {
		values.put(urlPattern, urlPattern);
	}

}