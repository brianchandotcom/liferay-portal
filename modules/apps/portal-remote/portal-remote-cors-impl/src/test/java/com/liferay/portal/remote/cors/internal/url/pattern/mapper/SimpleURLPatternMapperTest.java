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

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

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
	public void testGet() {
		KeyValuePair[] keyValuePairs = _createKeyValuePairs();

		URLPatternMapper<String> urlPatternMapper = createURLPatternMapper(
			_createValues(keyValuePairs));

		for (KeyValuePair keyValuePair : keyValuePairs) {
			String value = urlPatternMapper.getValue(keyValuePair.getKey());

			if (value == null) {
				Assert.assertEquals("", keyValuePair.getValue());

				continue;
			}

			try {
				Assert.assertEquals(keyValuePair.getValue(), value);
			}
			catch (ComparisonFailure comparisonFailure) {
				throw new ComparisonFailure(
					"When testing " + keyValuePair.getKey() + ":",
					comparisonFailure.getExpected(),
					comparisonFailure.getActual());
			}
		}
	}

	@Test
	public void testGetExtensionEdgeCases() {
		URLPatternMapper<String> urlPatternMapper = createURLPatternMapper(
			HashMapBuilder.put(
				"*.jsp", "*.jsp"
			).build());

		Assert.assertNull(urlPatternMapper.getValue("jsp"));
		Assert.assertEquals("*.jsp", urlPatternMapper.getValue(".jsp"));
	}

	protected URLPatternMapper<String> createURLPatternMapper(
		Map<String, String> values) {

		return new SimpleURLPatternMapper<>(values);
	}

	private KeyValuePair[] _createKeyValuePairs() {
		return new KeyValuePair[] {
			new KeyValuePair("/", "//*"), new KeyValuePair("/*", "/*/*"),
			new KeyValuePair("/*/", "/*//*"),
			new KeyValuePair("/c/portal/j_login", "/c/portal/j_login"),
			new KeyValuePair("/documents", "/documents/*"),
			new KeyValuePair("/documents/", "/documents/*"),
			new KeyValuePair("/documents/main.jsp", "/documents/main.jsp/*"),
			new KeyValuePair("/documents/main.jsp/*", "/documents/main.jsp/*"),
			new KeyValuePair("/documents/main.jspf", "/documents/main.jspf/*"),
			new KeyValuePair("/documents/main.jspf/", "/documents/main.jspf/*"),
			new KeyValuePair("/documents/user1", "/documents/user1/*"),
			new KeyValuePair("/documents/user1/", "/documents/user1/*"),
			new KeyValuePair(
				"/documents/user1/folder1", "/documents/user1/folder1/*"),
			new KeyValuePair(
				"/documents/user1/folder1/", "/documents/user1/folder1/*"),
			new KeyValuePair(
				"/documents/user1/folder2", "/documents/user1/folder2/*"),
			new KeyValuePair(
				"/documents/user1/folder2/", "/documents/user1/folder2/*"),
			new KeyValuePair("/documents/user2", "/documents/user2/*"),
			new KeyValuePair("/documents/user2/", "/documents/user2/*"),
			new KeyValuePair(
				"/documents/user2/folder1", "/documents/user2/folder1/*"),
			new KeyValuePair(
				"/documents/user2/folder1/", "/documents/user2/folder1/*"),
			new KeyValuePair(
				"/documents/user2/folder2", "/documents/user2/folder2/*"),
			new KeyValuePair(
				"/documents/user2/folder2/", "/documents/user2/folder2/*"),
			new KeyValuePair("/documents/user3", "/documents/*"),
			new KeyValuePair("/documents/user3/", "/documents/*"),
			new KeyValuePair("/documents/user3/folder1", "/documents/*"),
			new KeyValuePair("/documents/user3/folder1/", "/documents/*"),
			new KeyValuePair("/documents/user3/folder2", "/documents/*"),
			new KeyValuePair("/documents/user3/folder2/", "/documents/*"),
			new KeyValuePair("/documents2/a", "/documents2/*"),
			new KeyValuePair("/test", "/*"), new KeyValuePair("/test/", "/*"),
			new KeyValuePair("no/leading/slash", ""),
			new KeyValuePair("no/leading/slash/", ""),
			new KeyValuePair("no/leading/slash/*", "no/leading/slash/*"),
			new KeyValuePair("no/leading/slash/test", ""),
			new KeyValuePair("test", ""), new KeyValuePair("test.jsp", "*.jsp"),
			new KeyValuePair("test.jspf/", ""), new KeyValuePair("test/", ""),
			new KeyValuePair("test/main.jsp/*", ""),
			new KeyValuePair("test/main.jspf", "*.jspf"),
			new KeyValuePair("nonexisting.extension", "")
		};
	}

	private Map<String, String> _createValues(KeyValuePair[] keyValuePairs) {
		Map<String, String> values = new HashMap<>();

		for (KeyValuePair keyValuePair : keyValuePairs) {
			if (Validator.isBlank(keyValuePair.getValue())) {
				continue;
			}

			values.put(keyValuePair.getValue(), keyValuePair.getValue());
		}

		return values;
	}

}