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
		try {
			createURLPatternMapper(
				new HashMap() {
					{
						put("", "");
					}
				});

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		try {
			createURLPatternMapper(
				new HashMap() {
					{
						put(null, "null");
					}
				});

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}
	}

	@Test
	public void testGetValue() {
		Map<String, String> urlPatterns = _createURLPatterns();

		URLPatternMapper<String> urlPatternMapper = createURLPatternMapper(
			_createValues(urlPatterns));

		for (Map.Entry<String, String> entry : urlPatterns.entrySet()) {
			String value = urlPatternMapper.getValue(entry.getKey());

			try {
				if (Objects.isNull(value)) {
					Assert.assertEquals("", entry.getValue());

					continue;
				}

				Assert.assertEquals(entry.getValue(), value);
			}
			catch (ComparisonFailure comparisonFailure) {
				throw new ComparisonFailure(
					"When testing " + entry.getKey() + ":",
					comparisonFailure.getExpected(),
					comparisonFailure.getActual());
			}
		}

		urlPatternMapper = createURLPatternMapper(
			new HashMap() {
				{
					put("*.jsp", "*.jsp");
				}
			});

		Assert.assertEquals("*.jsp", urlPatternMapper.getValue(".jsp"));
		Assert.assertNull(urlPatternMapper.getValue("jsp"));
		Assert.assertNull(urlPatternMapper.getValue(null));
	}

	protected URLPatternMapper<String> createURLPatternMapper(
		Map<String, String> values) {

		return new SimpleURLPatternMapper<>(values);
	}

	private Map<String, String> _createURLPatterns() {
		return new HashMap<String, String>() {
			{
				put("/", "//*");
				put("/*", "/*/*");
				put("/*/", "/*//*");
				put("/a", "/*");
				put("/a/*/c/d", "/a/*/c/*");
				put("/a/b", "/*");
				put("/b", "/*");
				put("/b/c", "/b/c");
				put("/c/portal/j_login", "/c/portal/j_login");
				put("/documents", "/documents/*");
				put("/documents/", "/documents/*");
				put("/documents/main.jsp", "/documents/main.jsp/*");
				put("/documents/main.jsp/*", "/documents/main.jsp/*");
				put("/documents/main.jspf", "/documents/main.jspf/*");
				put("/documents/main.jspf/", "/documents/main.jspf/*");
				put("/documents/user1", "/documents/user1/*");
				put("/documents/user1/", "/documents/user1/*");
				put("/documents/user1/folder1", "/documents/user1/folder1/*");
				put("/documents/user1/folder1/", "/documents/user1/folder1/*");
				put("/documents/user1/folder2", "/documents/user1/folder2/*");
				put("/documents/user1/folder2/", "/documents/user1/folder2/*");
				put("/documents/user2", "/documents/user2/*");
				put("/documents/user2/", "/documents/user2/*");
				put("/documents/user2/folder1", "/documents/user2/folder1/*");
				put("/documents/user2/folder1/", "/documents/user2/folder1/*");
				put("/documents/user2/folder2", "/documents/user2/folder2/*");
				put("/documents/user2/folder2/", "/documents/user2/folder2/*");
				put("/documents/user3", "/documents/*");
				put("/documents/user3/", "/documents/*");
				put("/documents/user3/folder1", "/documents/*");
				put("/documents/user3/folder1/", "/documents/*");
				put("/documents/user3/folder2", "/documents/*");
				put("/documents/user3/folder2/", "/documents/*");
				put("/documents2/a", "/documents2/*");
				put("/test", "/*");
				put("/test/", "/*");
				put("no/leading/slash", "");
				put("no/leading/slash/", "");
				put("no/leading/slash/*", "no/leading/slash/*");
				put("no/leading/slash/test", "");
				put("nonexisting.extension", "");
				put("test", "");
				put("test.jsp", "*.jsp");
				put("test.jspf/", "");
				put("test/", "");
				put("test/main.jsp/*", "");
				put("test/main.jspf", "*.jspf");
			}
		};
	}

	private Map<String, String> _createValues(Map<String, String> urlPatterns) {
		Map<String, String> values = new HashMap<>();

		for (String urlPattern : urlPatterns.values()) {
			if ((urlPattern == null) || urlPattern.isEmpty()) {
				continue;
			}

			values.put(urlPattern, urlPattern);
		}

		return values;
	}

}