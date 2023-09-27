/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class StringParserTest {

	@Test
	public void testBuild() {
		StringParser stringParser = StringParser.create(
			"/{nodeId:\\d+}/{title:[^/]+}/");

		Assert.assertEquals(
			"/123/abc/",
			stringParser.build(
				HashMapBuilder.put(
					"nodeId", "123"
				).put(
					"title", "abc"
				).build()));

		Assert.assertNull(
			stringParser.build(
				HashMapBuilder.put(
					"nodeId", "1a3"
				).put(
					"title", "abc"
				).build()));

		Assert.assertNull(
			stringParser.build(
				HashMapBuilder.put(
					"nodeId", "123"
				).put(
					"title", "ab/c"
				).build()));

		stringParser = StringParser.create("{mvcPathName}");

		Assert.assertEquals(
			"test-path",
			stringParser.build(
				HashMapBuilder.put(
					"mvcPathName", "test-path"
				).build()));

		stringParser = StringParser.create("/maximized");

		Assert.assertEquals(
			"/maximized",
			stringParser.build(
				HashMapBuilder.put(
					"nodeId", "123"
				).put(
					"title", "abc"
				).build()));

		stringParser = StringParser.create(
			"/{userIdAndInstanceId}/{type}/{urlTitle:(?!id/)[^/]+}");

		Assert.assertEquals(
			"/123/abc/xyz",
			stringParser.build(
				HashMapBuilder.put(
					"type", "abc"
				).put(
					"urlTitle", "xyz"
				).put(
					"userIdAndInstanceId", "123"
				).build()));

		Assert.assertNull(
			stringParser.build(
				HashMapBuilder.put(
					"type", "abc"
				).put(
					"urlTitle", "id/xyz"
				).put(
					"userIdAndInstanceId", "123"
				).build()));

		Assert.assertNull(
			stringParser.build(
				HashMapBuilder.put(
					"type", "abc"
				).put(
					"urlTitle", "xy/z"
				).put(
					"userIdAndInstanceId", "123"
				).build()));

		stringParser = StringParser.create("/{test}");

		Assert.assertNull(
			stringParser.build(
				HashMapBuilder.put(
					"test", "a."
				).build()));

		stringParser = StringParser.create("/{test:\\d+}");

		Assert.assertNull(
			stringParser.build(
				HashMapBuilder.put(
					"test", "1a"
				).build()));
	}

	@Test
	public void testParse() {
		StringParser stringParser = StringParser.create(
			"/{nodeId:\\d+}/{title:[^/]+}/");

		Map<String, String> params = new HashMap<>();

		stringParser.parse("/123/abc/", params);

		Assert.assertEquals(params.toString(), 2, params.size());
		Assert.assertEquals("123", params.get("nodeId"));
		Assert.assertEquals("abc", params.get("title"));

		stringParser = StringParser.create("{mvcPathName}");

		params = new HashMap<>();

		stringParser.parse("test-path", params);

		Assert.assertEquals(params.toString(), 1, params.size());
		Assert.assertEquals("test-path", params.get("mvcPathName"));

		stringParser = StringParser.create("/maximized");

		params = new HashMap<>();

		stringParser.parse("/maximized", params);

		Assert.assertTrue(params.toString(), params.isEmpty());

		stringParser = StringParser.create(
			"/{userIdAndInstanceId}/{type}/{urlTitle:(?!id/)[^/]+}");

		params = new HashMap<>();

		stringParser.parse("/123/abc/xyz", params);

		Assert.assertEquals(params.toString(), 3, params.size());
		Assert.assertEquals("123", params.get("userIdAndInstanceId"));
		Assert.assertEquals("abc", params.get("type"));
		Assert.assertEquals("xyz", params.get("urlTitle"));

		stringParser = StringParser.create(
			"/{userIdAndInstanceId}/{type}/{urlTitle:(?!id/)[^/]+}");

		params = new HashMap<>();

		stringParser.parse("/123/abc/id/", params);

		Assert.assertTrue(params.toString(), params.isEmpty());

		stringParser = StringParser.create("/{test}");

		params = new HashMap<>();

		stringParser.parse("/a.", params);

		Assert.assertTrue(params.toString(), params.isEmpty());

		stringParser = StringParser.create("/{test:\\d+}");

		params = new HashMap<>();

		stringParser.parse("/1a", params);

		Assert.assertTrue(params.toString(), params.isEmpty());
	}

}