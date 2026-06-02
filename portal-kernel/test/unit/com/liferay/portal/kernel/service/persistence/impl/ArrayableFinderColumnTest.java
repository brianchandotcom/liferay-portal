/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.Arrays;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ArrayableFinderColumnTest {

	@Test
	public void testEmptyArrayDropsConstraint() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn = _newLongColumn(
			false, entity -> 0L);

		Object normalizedValue = arrayableFinderColumn.normalizeValue(null);

		Assert.assertEquals(
			"", arrayableFinderColumn.getSqlFragment(normalizedValue, false));
		Assert.assertEquals(
			"", arrayableFinderColumn.toFinderArg(normalizedValue));
	}

	@Test
	public void testGetSqlFragmentNative() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn =
			new ArrayableFinderColumn<>(
				"t.", "active", "active_", FinderColumn.Type.BOOLEAN, "=",
				false, true, true, entity -> true);

		Object normalizedValue = arrayableFinderColumn.normalizeValue(
			new boolean[] {true, false});

		Assert.assertEquals(
			"(t.active IN (?,?))",
			arrayableFinderColumn.getSqlFragment(normalizedValue, false));

		Assert.assertEquals(
			"(t.active_ IN (?,?))",
			arrayableFinderColumn.getSqlFragment(normalizedValue, true));
	}

	@Test
	public void testIntegerWidensToObjectArray() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn =
			new ArrayableFinderColumn<>(
				"t.", "col", FinderColumn.Type.INTEGER, "=", false, true, true,
				entity -> 2);

		Object normalizedValue = arrayableFinderColumn.normalizeValue(
			new int[] {1, 2, 3});

		Assert.assertTrue(normalizedValue instanceof Integer[]);
		Assert.assertEquals(
			"(t.col IN (?,?,?))",
			arrayableFinderColumn.getSqlFragment(normalizedValue, false));
		Assert.assertTrue(
			arrayableFinderColumn.matches(_TEST_MODEL, normalizedValue));
		Assert.assertEquals(
			"1,2,3", arrayableFinderColumn.toFinderArg(normalizedValue));
	}

	@Test
	public void testMatchesAndOperator() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn = _newLongColumn(
			true, entity -> 99L);

		Object normalizedValue = arrayableFinderColumn.normalizeValue(
			new long[] {1L, 2L, 3L});

		Assert.assertEquals(
			"(t.col NOT IN (?,?,?))",
			arrayableFinderColumn.getSqlFragment(normalizedValue, false));
	}

	@Test
	public void testMatchesScalarValue() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn = _newLongColumn(
			false, entity -> 2L);

		Object normalizedValue = arrayableFinderColumn.normalizeValue(
			new long[] {1L, 2L, 3L});

		Assert.assertTrue(
			arrayableFinderColumn.matches(_TEST_MODEL, normalizedValue));

		ArrayableFinderColumn<TestModel> missingColumn = _newLongColumn(
			false, entity -> 9L);

		Assert.assertFalse(missingColumn.matches(_TEST_MODEL, normalizedValue));
	}

	@Test
	public void testNormalizeNullToEmpty() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn = _newLongColumn(
			false, entity -> 0L);

		Object normalizedValue = arrayableFinderColumn.normalizeValue(null);

		Assert.assertTrue(normalizedValue instanceof Object[]);

		Object[] objects = (Object[])normalizedValue;

		Assert.assertEquals(Arrays.toString(objects), 0, objects.length);
	}

	@Test
	public void testSqlFragmentBuildsInClause() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn = _newLongColumn(
			false, entity -> 0L);

		Object normalizedValue = arrayableFinderColumn.normalizeValue(
			new long[] {10L, 20L, 30L});

		Assert.assertEquals(
			"(t.col IN (?,?,?))",
			arrayableFinderColumn.getSqlFragment(normalizedValue, false));
	}

	@Test
	public void testStringEmptyArrayDropsConstraint() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn =
			_newStringColumn(false, true, false, entity -> "anything");

		Object normalizedValue = arrayableFinderColumn.normalizeValue(null);

		Assert.assertEquals(
			"", arrayableFinderColumn.getSqlFragment(normalizedValue, false));
	}

	@Test
	public void testStringMatchesCaseInsensitive() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn =
			_newStringColumn(false, false, true, entity -> "Bar");

		Object normalizedValue = arrayableFinderColumn.normalizeValue(
			new String[] {"Foo", "BAR"});

		Assert.assertTrue(
			arrayableFinderColumn.matches(_TEST_MODEL, normalizedValue));
	}

	@Test
	public void testStringNullElementUsesIsNull() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn =
			_newStringColumn(false, true, false, entity -> "value");

		Object normalizedValue = arrayableFinderColumn.normalizeValue(
			new String[] {"x", null});

		Assert.assertEquals(
			"((t.col = ?) OR (t.col IS NULL))",
			arrayableFinderColumn.getSqlFragment(normalizedValue, false));
	}

	@Test
	public void testStringPerElementOrChain() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn =
			_newStringColumn(false, true, true, entity -> "any");

		Object normalizedValue = arrayableFinderColumn.normalizeValue(
			new String[] {"a", "b", "c"});

		Assert.assertEquals(
			"((t.col = ?) OR (t.col = ?) OR (t.col = ?))",
			arrayableFinderColumn.getSqlFragment(normalizedValue, false));
	}

	@Test
	public void testToFinderArgLength1LongReturnsLong() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn = _newLongColumn(
			false, entity -> 0L);

		Object normalizedValue = arrayableFinderColumn.normalizeValue(
			new long[] {42L});

		Assert.assertEquals(
			42L, arrayableFinderColumn.toFinderArg(normalizedValue));
	}

	@Test
	public void testToFinderArgLength1StringReturnsString() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn =
			_newStringColumn(false, true, true, entity -> "any");

		Object normalizedValue = arrayableFinderColumn.normalizeValue(
			new String[] {"alice"});

		Assert.assertEquals(
			"alice", arrayableFinderColumn.toFinderArg(normalizedValue));
	}

	@Test
	public void testToFinderArgMergesValues() {
		ArrayableFinderColumn<TestModel> arrayableFinderColumn = _newLongColumn(
			false, entity -> 0L);

		Object normalizedValue = arrayableFinderColumn.normalizeValue(
			new long[] {10L, 20L});

		Assert.assertEquals(
			"10,20", arrayableFinderColumn.toFinderArg(normalizedValue));
	}

	private ArrayableFinderColumn<TestModel> _newLongColumn(
		boolean andOperator, Function<TestModel, Object> valueExtractor) {

		return new ArrayableFinderColumn<>(
			"t.", "col", FinderColumn.Type.LONG, "=", andOperator, true, true,
			valueExtractor);
	}

	private ArrayableFinderColumn<TestModel> _newStringColumn(
		boolean andOperator, boolean caseSensitive, boolean convertNull,
		Function<TestModel, Object> valueExtractor) {

		return new ArrayableFinderColumn<>(
			"t.", "col", FinderColumn.Type.STRING, "=", andOperator,
			caseSensitive, convertNull, valueExtractor);
	}

	private static final TestModel _TEST_MODEL =
		(TestModel)ProxyUtil.newProxyInstance(
			ArrayableFinderColumnTest.class.getClassLoader(),
			new Class<?>[] {TestModel.class}, (proxy, method, args) -> null);

	private interface TestModel extends BaseModel<TestModel> {
	}

}