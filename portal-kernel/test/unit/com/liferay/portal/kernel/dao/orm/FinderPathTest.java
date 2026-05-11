/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FinderPathTest {

	@Test
	public void testExtractArgsReturnsEmptyArgsForNullArgsExtractorFunction() {
		FinderPath finderPath = new FinderPath(
			"TestImpl.List2", "findByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, 0, 0, true, null);

		Assert.assertArrayEquals(new Object[0], finderPath.extractArgs(null));
	}

	@Test
	public void testNormalizeArgumentCoercesNullForCaseInsensitiveConvertNullString() {
		FinderPath finderPath = new FinderPath(
			"TestImpl.List2", "findByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, 0b10, 0b10, true, null);

		Assert.assertEquals("", finderPath.normalizeArgument(1, null));
	}

	@Test
	public void testNormalizeArgumentCoercesNullForConvertNullString() {
		FinderPath finderPath = new FinderPath(
			"TestImpl.List2", "findByC_EA",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "emailAddress"}, 0, 0b10, true, null);

		Assert.assertEquals("", finderPath.normalizeArgument(1, null));
		Assert.assertEquals(
			"Alice@example.com",
			finderPath.normalizeArgument(1, "Alice@example.com"));
	}

	@Test
	public void testNormalizeArgumentConvertsDateToLong() {
		FinderPath finderPath = new FinderPath(
			"TestImpl.List2", "findByCreateDate",
			new String[] {Date.class.getName()}, new String[] {"createDate"},
			true);

		Assert.assertEquals(
			1_234_567_890L,
			finderPath.normalizeArgument(0, new Date(1_234_567_890L)));
	}

	@Test
	public void testNormalizeArgumentLowercasesCaseInsensitiveString() {
		FinderPath finderPath = new FinderPath(
			"TestImpl.List2", "findByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, 0b10, 0, true, null);

		Assert.assertEquals(
			"alice", finderPath.normalizeArgument(1, "Alice"));
		Assert.assertEquals(
			Long.valueOf(123L), finderPath.normalizeArgument(0, 123L));
	}

	@Test
	public void testNormalizeArgumentReturnsValueAsIsForDefaultBitmask() {
		FinderPath finderPath = new FinderPath(
			"TestImpl.List2", "findByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, true);

		Assert.assertEquals("Alice", finderPath.normalizeArgument(1, "Alice"));
	}

}