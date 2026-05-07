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
	public void testMatchesEquals() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "value", FinderColumn.Type.LONG, "=", true, true,
			entity -> 5L);

		Assert.assertTrue("5 matches 5", finderColumn.matches(_TEST_MODEL, 5L));
		Assert.assertFalse(
			"5 does not match 6", finderColumn.matches(_TEST_MODEL, 6L));
	}

	@Test
	public void testMatchesGreaterThanDate() {
		Date entityDate = new Date(2_000_000L);

		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "createDate", FinderColumn.Type.DATE, ">", true, true,
			entity -> entityDate);

		Assert.assertTrue(
			"entity 2000000 > input 1000000",
			finderColumn.matches(_TEST_MODEL, new Date(1_000_000L)));
		Assert.assertFalse(
			"entity 2000000 not > input 2000000",
			finderColumn.matches(_TEST_MODEL, new Date(2_000_000L)));
		Assert.assertFalse(
			"entity 2000000 not > input 3000000",
			finderColumn.matches(_TEST_MODEL, new Date(3_000_000L)));
	}

	@Test
	public void testMatchesGreaterThanLong() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "value", FinderColumn.Type.LONG, ">", true, true,
			entity -> 10L);

		Assert.assertTrue("10 > 5", finderColumn.matches(_TEST_MODEL, 5L));
		Assert.assertFalse(
			"10 not > 10", finderColumn.matches(_TEST_MODEL, 10L));
		Assert.assertFalse(
			"10 not > 20", finderColumn.matches(_TEST_MODEL, 20L));
	}

	@Test
	public void testMatchesGreaterThanOrEqualLong() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "value", FinderColumn.Type.LONG, ">=", true, true,
			entity -> 10L);

		Assert.assertTrue("10 >= 5", finderColumn.matches(_TEST_MODEL, 5L));
		Assert.assertTrue("10 >= 10", finderColumn.matches(_TEST_MODEL, 10L));
		Assert.assertFalse(
			"10 not >= 20", finderColumn.matches(_TEST_MODEL, 20L));
	}

	@Test
	public void testMatchesGreaterThanReturnsFalseForNullEntityValue() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "value", FinderColumn.Type.LONG, ">", true, true,
			entity -> null);

		Assert.assertFalse(
			"null entity value cannot satisfy > comparison",
			finderColumn.matches(_TEST_MODEL, 5L));
	}

	@Test
	public void testMatchesLessThanLong() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "value", FinderColumn.Type.LONG, "<", true, true,
			entity -> 5L);

		Assert.assertTrue("5 < 10", finderColumn.matches(_TEST_MODEL, 10L));
		Assert.assertFalse("5 not < 5", finderColumn.matches(_TEST_MODEL, 5L));
		Assert.assertFalse("5 not < 3", finderColumn.matches(_TEST_MODEL, 3L));
	}

	@Test
	public void testMatchesLessThanOrEqualLong() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "value", FinderColumn.Type.LONG, "<=", true, true,
			entity -> 5L);

		Assert.assertTrue("5 <= 10", finderColumn.matches(_TEST_MODEL, 10L));
		Assert.assertTrue("5 <= 5", finderColumn.matches(_TEST_MODEL, 5L));
		Assert.assertFalse("5 not <= 3", finderColumn.matches(_TEST_MODEL, 3L));
	}

	@Test
	public void testMatchesLikeAlternateNotEquals() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "status", FinderColumn.Type.INTEGER, "<>", true, true,
			entity -> 3);

		Assert.assertTrue("3 <> 5", finderColumn.matches(_TEST_MODEL, 5));
		Assert.assertFalse("3 not <> 3", finderColumn.matches(_TEST_MODEL, 3));
	}

	@Test
	public void testMatchesLikeMatchesWildcard() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "name", FinderColumn.Type.STRING, "LIKE", true, true,
			entity -> "Hello World");

		Assert.assertTrue(
			"Hello World LIKE Hello%",
			finderColumn.matches(_TEST_MODEL, "Hello%"));
		Assert.assertTrue(
			"Hello World LIKE %World",
			finderColumn.matches(_TEST_MODEL, "%World"));
		Assert.assertFalse(
			"Hello World does not LIKE Goodbye%",
			finderColumn.matches(_TEST_MODEL, "Goodbye%"));
	}

	@Test
	public void testMatchesNotEquals() {
		FinderColumn<TestModel> finderColumn = new FinderColumn<>(
			"t.", "status", FinderColumn.Type.INTEGER, "!=", true, true,
			entity -> 3);

		Assert.assertTrue(
			"entity 3 != input 5", finderColumn.matches(_TEST_MODEL, 5));
		Assert.assertFalse(
			"entity 3 not != input 3", finderColumn.matches(_TEST_MODEL, 3));
	}

	private static final TestModel _TEST_MODEL =
		(TestModel)ProxyUtil.newProxyInstance(
			FinderColumnTest.class.getClassLoader(),
			new Class<?>[] {TestModel.class}, (proxy, method, args) -> null);

	private interface TestModel extends BaseModel<TestModel> {
	}

}