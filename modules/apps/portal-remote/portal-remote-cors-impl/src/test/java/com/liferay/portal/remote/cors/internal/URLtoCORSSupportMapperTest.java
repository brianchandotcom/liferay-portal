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
import org.junit.Test;

/**
 * @author Arthur Chan
 */
public class URLtoCORSSupportMapperTest {

	@Test
	public void testGet() throws Exception {
		Map<String, CORSSupport> corsSupports = new HashMap<>();

		for (KeyValuePair keyValuePair : _KEY_VALUE_PAIRS) {
			CORSSupport corsSupport = new CORSSupport();

			corsSupport.setHeader("pattern", keyValuePair.getValue());

			corsSupports.put(keyValuePair.getValue(), corsSupport);
		}

		URLtoCORSSupportMapper urLtoCORSSupportMapper =
			new URLtoCORSSupportMapper(corsSupports);

		for (KeyValuePair keyValuePair : _KEY_VALUE_PAIRS) {
			CORSSupport corsSupport = urLtoCORSSupportMapper.get(
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

	private static final KeyValuePair[] _KEY_VALUE_PAIRS = {
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
		new KeyValuePair("/test", "/*"), new KeyValuePair("/test/", "/*"),
		new KeyValuePair("no/leading/slash", StringPool.BLANK),
		new KeyValuePair("no/leading/slash/", StringPool.BLANK),
		new KeyValuePair("no/leading/slash/*", "no/leading/slash/*"),
		new KeyValuePair("no/leading/slash/test", StringPool.BLANK),
		new KeyValuePair("test", StringPool.BLANK),
		new KeyValuePair("test.jsp", "*.jsp"),
		new KeyValuePair("test.jspf/", StringPool.BLANK),
		new KeyValuePair("test/", StringPool.BLANK),
		new KeyValuePair("test/main.jsp/*", StringPool.BLANK),
		new KeyValuePair("test/main.jspf", "*.jspf")
	};

}