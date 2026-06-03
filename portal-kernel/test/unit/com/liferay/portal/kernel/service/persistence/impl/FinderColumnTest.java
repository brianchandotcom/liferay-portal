/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FinderColumnTest {

	@Test
	public void testGetSqlFragmentNative() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "active", "active_", FinderColumn.Type.BOOLEAN, "=", true,
			true, entity -> true);

		Assert.assertEquals(
			"t.active = ?", finderColumn.getSqlFragment(true, false));
		Assert.assertEquals(
			"t.active_ = ?", finderColumn.getSqlFragment(true, true));
	}

	@Test
	public void testMatchesEquals() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "value", FinderColumn.Type.LONG, "=", true, true,
			entity -> 5L);

		Assert.assertFalse(finderColumn.matches(_TEST_MODEL, 6L));
		Assert.assertTrue(finderColumn.matches(_TEST_MODEL, 5L));
	}

	@Test
	public void testMatchesEqualsCaseInsensitive() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "name", FinderColumn.Type.STRING, "=", false, true,
			entity -> "Alice");

		Assert.assertFalse(finderColumn.matches(_TEST_MODEL, "bob"));
		Assert.assertTrue(finderColumn.matches(_TEST_MODEL, "alice"));
	}

	@Test
	public void testMatchesGreaterThanDate() {
		Date entityDate = new Date(2_000_000L);

		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "createDate", FinderColumn.Type.DATE, ">", true, true,
			entity -> entityDate);

		Assert.assertFalse(
			finderColumn.matches(_TEST_MODEL, new Date(2_000_000L)));
		Assert.assertFalse(
			finderColumn.matches(_TEST_MODEL, new Date(3_000_000L)));
		Assert.assertTrue(
			finderColumn.matches(_TEST_MODEL, new Date(1_000_000L)));
	}

	@Test
	public void testMatchesGreaterThanLong() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "value", FinderColumn.Type.LONG, ">", true, true,
			entity -> 10L);

		Assert.assertFalse(finderColumn.matches(_TEST_MODEL, 10L));
		Assert.assertFalse(finderColumn.matches(_TEST_MODEL, 20L));
		Assert.assertTrue(finderColumn.matches(_TEST_MODEL, 5L));
	}

	@Test
	public void testMatchesGreaterThanOrEqualLong() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "value", FinderColumn.Type.LONG, ">=", true, true,
			entity -> 10L);

		Assert.assertFalse(finderColumn.matches(_TEST_MODEL, 20L));
		Assert.assertTrue(finderColumn.matches(_TEST_MODEL, 10L));
		Assert.assertTrue(finderColumn.matches(_TEST_MODEL, 5L));
	}

	@Test
	public void testMatchesGreaterThanReturnsFalseForNullEntityValue() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "value", FinderColumn.Type.LONG, ">", true, true,
			entity -> null);

		Assert.assertFalse(finderColumn.matches(_TEST_MODEL, 5L));
	}

	@Test
	public void testMatchesLessThanLong() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "value", FinderColumn.Type.LONG, "<", true, true,
			entity -> 5L);

		Assert.assertFalse(finderColumn.matches(_TEST_MODEL, 3L));
		Assert.assertFalse(finderColumn.matches(_TEST_MODEL, 5L));
		Assert.assertTrue(finderColumn.matches(_TEST_MODEL, 10L));
	}

	@Test
	public void testMatchesLessThanOrEqualLong() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "value", FinderColumn.Type.LONG, "<=", true, true,
			entity -> 5L);

		Assert.assertFalse(finderColumn.matches(_TEST_MODEL, 3L));
		Assert.assertTrue(finderColumn.matches(_TEST_MODEL, 10L));
		Assert.assertTrue(finderColumn.matches(_TEST_MODEL, 5L));
	}

	@Test
	public void testMatchesLikeAlternateNotEquals() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "status", FinderColumn.Type.INTEGER, "<>", true, true,
			entity -> 3);

		Assert.assertFalse(finderColumn.matches(_TEST_MODEL, 3));
		Assert.assertTrue(finderColumn.matches(_TEST_MODEL, 5));
	}

	@Test
	public void testMatchesLikeMatchesWildcard() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "name", FinderColumn.Type.STRING, "LIKE", true, true,
			entity -> "Hello World");

		Assert.assertFalse(finderColumn.matches(_TEST_MODEL, "Goodbye%"));
		Assert.assertTrue(finderColumn.matches(_TEST_MODEL, "%World"));
		Assert.assertTrue(finderColumn.matches(_TEST_MODEL, "Hello%"));
	}

	@Test
	public void testMatchesNotEquals() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "status", FinderColumn.Type.INTEGER, "!=", true, true,
			entity -> 3);

		Assert.assertFalse(finderColumn.matches(_TEST_MODEL, 3));
		Assert.assertTrue(finderColumn.matches(_TEST_MODEL, 5));
	}

	@Test
	public void testNormalizeValueLowercasesCaseInsensitiveString() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "name", FinderColumn.Type.STRING, "=", false, true,
			entity -> "ignored");

		Assert.assertEquals("alice", finderColumn.normalizeValue("Alice"));
	}

	private static final TestModel _TEST_MODEL =
		(TestModel)ProxyUtil.newProxyInstance(
			FinderColumnTest.class.getClassLoader(),
			new Class<?>[] {TestModel.class}, (proxy, method, args) -> null);

	private interface TestModel extends BaseModel<TestModel> {
	}

}