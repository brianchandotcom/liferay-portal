/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Daniel Szimko
 */
public class ObjectMapperUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testReadValueWithHugeBase64String() {
		int targetLength = 60_000_000;

		String base64Chunk = "AQID";

		StringBuilder sb = new StringBuilder(targetLength + 8);

		while (sb.length() < targetLength) {
			sb.append(base64Chunk);
		}

		String hugeBase64 = sb.toString();

		LargePayload payload = ObjectMapperUtil.readValue(
			LargePayload.class,
			HashMapBuilder.<String, Object>put(
				"file", hugeBase64
			).build());

		Assert.assertNotNull(payload);
		Assert.assertNotNull(payload.file);
		Assert.assertEquals(hugeBase64.length(), payload.file.length());
		Assert.assertEquals(hugeBase64, payload.file);
	}

	public static class LargePayload {

		public String file;

	}

}