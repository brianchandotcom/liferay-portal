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

/**
 * @author Arthur Chan
 */
public abstract class BaseURLPatternMapperTestCase {

	protected abstract URLPatternMapper<Integer> createURLPatternMapper(
		Map<String, Integer> values);

	protected Map<String, Integer> createValues() {
		Map<String, Integer> values = new HashMap<>();

		for (int i = 0; i < _URL_PATTERNS.length; ++i) {
			values.put(_URL_PATTERNS[i], i);
		}

		return values;
	}

	protected static final Map<String, Integer[]> expectedURLPatternIndexesMap =
		new HashMap<String, Integer[]>() {
			{
				put(null, new Integer[0]);
				put("/", new Integer[] {23});
				put("/*", new Integer[] {21});
				put("/*/", new Integer[] {22, 21});
				put("/c/portal/j_login", new Integer[0]);
				put("/c/portal/j_login/", new Integer[0]);
				put(
					"/c/portal/j_login/make/this/the/longest/exact/url/pattern",
					new Integer[] {1});
				put("/do", new Integer[0]);
				put("/do/", new Integer[0]);
				put("/do/test.mp3", new Integer[] {25, 17});
				put("/do/test.mp3/", new Integer[] {25});
				put("/do/test.mp3/do/test", new Integer[] {25});
				put("/random/one/two/three", new Integer[0]);
				put("/url/random/one/two/three", new Integer[] {36});
				put("/url/some/random/pattern/do/", new Integer[] {37, 36});
				put(
					"/url/some/random/pattern/do/test.mp3",
					new Integer[] {38, 37, 36, 17});
				put(
					"/url/some/random/pattern/do/test.mp3/",
					new Integer[] {2, 38, 37, 36});
				put(
					"/url/some/random/pattern/five/five/five/",
					new Integer[] {37, 36});
				put(
					"/url/some/random/pattern/one",
					new Integer[] {3, 39, 37, 36});
				put("/url/some/random/pattern/one*", new Integer[] {4, 37, 36});
				put(
					"/url/some/random/pattern/one/",
					new Integer[] {5, 39, 37, 36});
				put(
					"/url/some/random/pattern/one/*/",
					new Integer[] {6, 39, 37, 36});
				put(
					"/url/some/random/pattern/one/*/do/test",
					new Integer[] {7, 39, 37, 36});
				put(
					"/url/some/random/pattern/one/*/do/test/",
					new Integer[] {39, 37, 36});
				put(
					"/url/some/random/pattern/one/*/do/test/t",
					new Integer[] {39, 37, 36});
				put(
					"/url/some/random/pattern/one/do/test",
					new Integer[] {39, 37, 36});
				put(
					"/url/some/random/pattern/one/one",
					new Integer[] {40, 39, 37, 36});
				put(
					"/url/some/random/pattern/one/one/",
					new Integer[] {40, 39, 37, 36});
				put(
					"/url/some/random/pattern/one/one/do/test",
					new Integer[] {40, 39, 37, 36});
				put(
					"/url/some/random/pattern/one/one/one",
					new Integer[] {41, 40, 39, 37, 36});
				put(
					"/url/some/random/pattern/one/one/one/",
					new Integer[] {41, 40, 39, 37, 36});
				put(
					"/url/some/random/pattern/one/one/one/./some/very/long/do",
					new Integer[] {41, 40, 39, 37, 36});
				put(
					"/url/some/random/pattern/one/one/one/./some/very/long/do/",
					new Integer[] {41, 40, 39, 37, 36});
				put(
					"/url/some/random/pattern/one/one/one/do/test",
					new Integer[] {41, 40, 39, 37, 36});
				put(
					"/url/some/random/pattern/two/*/do/test/",
					new Integer[] {42, 37, 36});
				put(
					"/url/some/random/pattern/two/*/do/test/t",
					new Integer[] {42, 37, 36});
				put(
					"/url/some/random/pattern/two/do/test",
					new Integer[] {42, 37, 36});
				put(
					"/url/some/random/pattern/two/two",
					new Integer[] {43, 42, 37, 36});
				put(
					"/url/some/random/pattern/two/two/",
					new Integer[] {43, 42, 37, 36});
				put(
					"/url/some/random/pattern/two/two/do/test",
					new Integer[] {43, 42, 37, 36});
				put(
					"/url/some/random/pattern/two/two/two",
					new Integer[] {44, 43, 42, 37, 36});
				put(
					"/url/some/random/pattern/two/two/two/",
					new Integer[] {44, 43, 42, 37, 36});
				put(
					"/url/some/random/pattern/two/two/two/./some/very/long/do",
					new Integer[] {44, 43, 42, 37, 36});
				put(
					"/url/some/random/pattern/two/two/two/./some/very/long/do/",
					new Integer[] {44, 43, 42, 37, 36});
				put(
					"/url/some/random/pattern/two/two/two/do/test",
					new Integer[] {44, 43, 42, 37, 36});
				put(
					"/url/some/random/pattern/two/two/two/two",
					new Integer[] {44, 43, 42, 37, 36});
				put(
					"/url/some/random/pattern/two/two/two/two/",
					new Integer[] {44, 43, 42, 37, 36});
				put(
					"/url/some/random/pattern/two/two/two/two/*/long",
					new Integer[] {44, 43, 42, 37, 36});
				put(
					"/url/some/random/pattern/two/two/two/two/*/long/",
					new Integer[] {44, 43, 42, 37, 36});
				put("iff", new Integer[0]);
				put(
					"some/random/pattern/one/two/three/test.j/sp",
					new Integer[0]);
				put(
					"some/random/pattern/one/two/three/test.jbp",
					new Integer[0]);
				put(
					"some/random/pattern/one/two/three/test.jsp",
					new Integer[] {15});
				put(
					"some/random/pattern/one/two/three/test.mp3",
					new Integer[] {17});
				put("url/some/random/pattern/do/test", new Integer[0]);
				put("url/some/random/pattern/do/test.mp3/", new Integer[] {9});
				put("url/some/random/pattern/one", new Integer[] {10});
				put("url/some/random/pattern/one/", new Integer[] {11});
				put("url/some/random/pattern/one/*", new Integer[] {12});
			}
		};

	private static final String[] _URL_PATTERNS = {

		// Exact

		"/b/c",
		"/c/portal/j_login/make/this/the/longest/exact/url/pattern",
		"/url/some/random/pattern/do/test.mp3/",
		"/url/some/random/pattern/one",
		"/url/some/random/pattern/one*",
		"/url/some/random/pattern/one/",
		"/url/some/random/pattern/one/*/",
		"/url/some/random/pattern/one/*/do/test",
		"no/leading/slash/*",
		"url/some/random/pattern/do/test.mp3/",
		"url/some/random/pattern/one",
		"url/some/random/pattern/one/",
		"url/some/random/pattern/one/*",

		// Extension

		"*.bmp",
		"*.jpg",
		"*.jsp",
		"*.jspf",
		"*.mp3",
		"*.mp4",
		"*.tiff",
		"*.zip",

		// Wildcard

		"/*/*",
		"/*//*",
		"//*",
		"/a/*/c/*",
		"/do/test.mp3/*",
		"/documents/*",
		"/documents/main.jsp/*",
		"/documents/main.jspf/*",
		"/documents/user1/*",
		"/documents/user1/folder1/*",
		"/documents/user1/folder2/*",
		"/documents/user2/*",
		"/documents/user2/folder1/*",
		"/documents/user2/folder2/*",
		"/documents2/*",
		"/url/*",
		"/url/some/random/pattern/*",
		"/url/some/random/pattern/do/test.mp3/*",
		"/url/some/random/pattern/one/*",
		"/url/some/random/pattern/one/one/*",
		"/url/some/random/pattern/one/one/one/*",
		"/url/some/random/pattern/two/*",
		"/url/some/random/pattern/two/two/*",
		"/url/some/random/pattern/two/two/two/*",
		"/url/some/random/pattern/two/two/two/two/*/long/pattern/*"
	};

}