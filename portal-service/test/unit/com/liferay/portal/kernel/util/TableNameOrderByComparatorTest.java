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

package com.liferay.portal.kernel.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author José Manuel Navarro
 */
public class TableNameOrderByComparatorTest {

	@Test
	public void testGetOrderByTableNameWithPeriodReturnsDecoratedTableName() {
		TableNameOrderByComparator<?> tableNameOrderByComparator =
			new TableNameOrderByComparator<>(
				new TestGetOrderByComparator("column"), "table.");

		Assert.assertEquals(
			"table.column", tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithBlankTableNameReturnsUndecoratedTableName() {
		TableNameOrderByComparator<?> tableNameOrderByComparator =
			new TableNameOrderByComparator<>(
				new TestGetOrderByComparator("column1, column2"), "");

		Assert.assertEquals(
			"column1, column2", tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void
		testGetOrderByWithMultipleColumnNamesReturnsDecoratedTableName() {

		TableNameOrderByComparator<?> tableNameOrderByComparator =
			new TableNameOrderByComparator<>(
				new TestGetOrderByComparator("column1, column2"), "table");

		Assert.assertEquals(
			"table.column1, table.column2",
			tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithMultipleTableNameReturnsNewTableName() {
		TableNameOrderByComparator<?> tableNameOrderByComparator =
			new TableNameOrderByComparator<Object>(
				new TestGetOrderByComparator("table1.column1, column2"),
				"table2");

		Assert.assertEquals(
			"table2.column1, table2.column2",
			tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithNullTableNameReturnsUndecoratedTableName() {
		TableNameOrderByComparator<?> tableNameOrderByComparator =
			new TableNameOrderByComparator<>(
				new TestGetOrderByComparator("column1, column2"), null);

		Assert.assertEquals(
			"column1, column2", tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithSingleColumnNameReturnsDecoratedTableName() {
		TableNameOrderByComparator<?> tableNameOrderByComparator =
			new TableNameOrderByComparator<>(
				new TestGetOrderByComparator("column"), "table");

		Assert.assertEquals(
			"table.column", tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithSingleTableNameReturnsNewTableName() {
		TableNameOrderByComparator<?> tableNameOrderByComparator =
			new TableNameOrderByComparator<>(
				new TestGetOrderByComparator("table1.column1"), "table2");

		Assert.assertEquals(
			"table2.column1", tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithSortDirectionReturnsDecoratedTableName() {
		TableNameOrderByComparator<?> tableNameOrderByComparator =
			new TableNameOrderByComparator<>(
				new TestGetOrderByComparator("column ASC"), "table");

		Assert.assertEquals(
			"table.column ASC", tableNameOrderByComparator.getOrderBy());
	}

	private static class TestGetOrderByComparator
		extends OrderByComparator<Object> {

		public TestGetOrderByComparator(String orderBy) {
			_orderBy = orderBy;
		}

		@Override
		public int compare(Object obj1, Object obj2) {
			return 0;
		}

		@Override
		public String getOrderBy() {
			return _orderBy;
		}

		private final String _orderBy;

	}

}