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
			new String[] {"groupId", "name"}, 0, true, null);

		Assert.assertArrayEquals(
			"null argsExtractorFunction returns empty Object[]", new Object[0],
			finderPath.extractArgs(null));
	}

	@Test
	public void testNormalizeArgumentCoercesNullForCaseInsensitiveString() {
		FinderPath finderPath = new FinderPath(
			"TestImpl.List2", "findByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, 0b10, true, null);

		Assert.assertEquals(
			"caseInsensitive on null returns \"\"", "",
			finderPath.normalizeArgument(1, null));
	}

	@Test
	public void testNormalizeArgumentConvertsDateToLong() {
		FinderPath finderPath = new FinderPath(
			"TestImpl.List2", "findByCreateDate",
			new String[] {Date.class.getName()}, new String[] {"createDate"},
			true);

		Assert.assertEquals(
			"Date returns getTime() as long", 1_234_567_890L,
			finderPath.normalizeArgument(0, new Date(1_234_567_890L)));
	}

	@Test
	public void testNormalizeArgumentLowercasesCaseInsensitiveString() {
		FinderPath finderPath = new FinderPath(
			"TestImpl.List2", "findByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, 0b10, true, null);

		Assert.assertEquals(
			"non-flagged Long passes through", Long.valueOf(123L),
			finderPath.normalizeArgument(0, 123L));
		Assert.assertEquals(
			"caseInsensitive lowercases", "alice",
			finderPath.normalizeArgument(1, "Alice"));
	}

	@Test
	public void testNormalizeArgumentReturnsValueAsIsForDefaultBitmask() {
		FinderPath finderPath = new FinderPath(
			"TestImpl.List2", "findByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, true);

		Assert.assertEquals(
			"default bitmask 0 passes through", "Alice",
			finderPath.normalizeArgument(1, "Alice"));
	}

}