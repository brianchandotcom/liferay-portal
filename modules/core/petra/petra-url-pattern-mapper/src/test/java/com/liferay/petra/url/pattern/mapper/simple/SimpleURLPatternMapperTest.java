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

package com.liferay.petra.url.pattern.mapper.simple;

import com.liferay.petra.url.pattern.mapper.URLPatternMapper;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.ComparisonFailure;
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public class SimpleURLPatternMapperTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		Map<String, String> values = new HashMap<>();

		values.put("", "");

		try {
			createURLPatternMapper(values);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		try {
			Map<String, String> map = new HashMap<>();

			map.put(null, "null");

			createURLPatternMapper(map);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}
	}

	@Test
	public void testGetValue() {
		Map<String, String> pathToPatternMap = _createpathToPatternMap();

		URLPatternMapper<String> urlPatternMapper = createURLPatternMapper(
			_createValues(pathToPatternMap));

		for (Map.Entry<String, String> pathToPattern :
				pathToPatternMap.entrySet()) {

			String value = urlPatternMapper.getValue(pathToPattern.getKey());

			try {
				if (Objects.isNull(value)) {
					Assert.assertEquals("", pathToPattern.getValue());

					continue;
				}

				Assert.assertEquals(pathToPattern.getValue(), value);
			}
			catch (ComparisonFailure comparisonFailure) {
				throw new ComparisonFailure(
					"When testing " + pathToPattern.getKey() + ":",
					comparisonFailure.getExpected(),
					comparisonFailure.getActual());
			}
		}

		Map<String, String> values = new HashMap<>();

		values.put("*.jsp", "*.jsp");

		urlPatternMapper = createURLPatternMapper(values);

		Assert.assertEquals("*.jsp", urlPatternMapper.getValue(".jsp"));
		Assert.assertNull(urlPatternMapper.getValue("jsp"));
		Assert.assertNull(urlPatternMapper.getValue(null));
	}

	protected URLPatternMapper<String> createURLPatternMapper(
		Map<String, String> values) {

		return new SimpleURLPatternMapper<>(values);
	}

	private Map<String, String> _createpathToPatternMap() {
		Map<String, String> pathToPatternMap = new HashMap<>();

		pathToPatternMap.put("/", "//*");
		pathToPatternMap.put("/*", "/*/*");
		pathToPatternMap.put("/*/", "/*//*");
		pathToPatternMap.put("/a", "/*");
		pathToPatternMap.put("/a/*/c/d", "/a/*/c/*");
		pathToPatternMap.put("/a/b", "/*");
		pathToPatternMap.put("/b", "/*");
		pathToPatternMap.put("/b/c", "/b/c");
		pathToPatternMap.put("/c/portal/j_login", "/c/portal/j_login");
		pathToPatternMap.put("/documents", "/documents/*");
		pathToPatternMap.put("/documents/", "/documents/*");
		pathToPatternMap.put("/documents/main.jsp", "/documents/main.jsp/*");
		pathToPatternMap.put("/documents/main.jsp/*", "/documents/main.jsp/*");
		pathToPatternMap.put("/documents/main.jspf", "/documents/main.jspf/*");
		pathToPatternMap.put("/documents/main.jspf/", "/documents/main.jspf/*");
		pathToPatternMap.put("/documents/user1", "/documents/user1/*");
		pathToPatternMap.put("/documents/user1/", "/documents/user1/*");
		pathToPatternMap.put(
			"/documents/user1/folder1", "/documents/user1/folder1/*");
		pathToPatternMap.put(
			"/documents/user1/folder1/", "/documents/user1/folder1/*");
		pathToPatternMap.put(
			"/documents/user1/folder2", "/documents/user1/folder2/*");
		pathToPatternMap.put(
			"/documents/user1/folder2/", "/documents/user1/folder2/*");
		pathToPatternMap.put("/documents/user2", "/documents/user2/*");
		pathToPatternMap.put("/documents/user2/", "/documents/user2/*");
		pathToPatternMap.put(
			"/documents/user2/folder1", "/documents/user2/folder1/*");
		pathToPatternMap.put(
			"/documents/user2/folder1/", "/documents/user2/folder1/*");
		pathToPatternMap.put(
			"/documents/user2/folder2", "/documents/user2/folder2/*");
		pathToPatternMap.put(
			"/documents/user2/folder2/", "/documents/user2/folder2/*");
		pathToPatternMap.put("/documents/user3", "/documents/*");
		pathToPatternMap.put("/documents/user3/", "/documents/*");
		pathToPatternMap.put("/documents/user3/folder1", "/documents/*");
		pathToPatternMap.put("/documents/user3/folder1/", "/documents/*");
		pathToPatternMap.put("/documents/user3/folder2", "/documents/*");
		pathToPatternMap.put("/documents/user3/folder2/", "/documents/*");
		pathToPatternMap.put("/documents2/a", "/documents2/*");
		pathToPatternMap.put("/test", "/*");
		pathToPatternMap.put("/test/", "/*");
		pathToPatternMap.put("no/leading/slash", "");
		pathToPatternMap.put("no/leading/slash/", "");
		pathToPatternMap.put("no/leading/slash/*", "no/leading/slash/*");
		pathToPatternMap.put("no/leading/slash/test", "");
		pathToPatternMap.put("nonexisting.extension", "");
		pathToPatternMap.put("test", "");
		pathToPatternMap.put("test.jsp", "*.jsp");
		pathToPatternMap.put("test.jspf/", "");
		pathToPatternMap.put("test/", "");
		pathToPatternMap.put("test/main.jsp/*", "");
		pathToPatternMap.put("test/main.jspf", "*.jspf");

		return pathToPatternMap;
	}

	private Map<String, String> _createValues(
		Map<String, String> pathToPatternMap) {

		Map<String, String> values = new HashMap<>();

		for (String pattern : pathToPatternMap.values()) {
			if ((pattern == null) || pattern.isEmpty()) {
				continue;
			}

			values.put(pattern, pattern);
		}

		return values;
	}

}