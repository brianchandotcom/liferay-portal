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
		_tableNameOrderByComparator = new TableNameOrderByComparator(
			new testGetOrderByComparator("field"), "table.");

		Assert.assertEquals(
			"table.field", _tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithBlankTableNameReturnsUndecoratedTableName() {
		_tableNameOrderByComparator = new TableNameOrderByComparator(
			new testGetOrderByComparator("field1, field2"), "");

		Assert.assertEquals(
			"field1, field2", _tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithMultipleFieldsReturnsDecoratedTableName() {
		_tableNameOrderByComparator = new TableNameOrderByComparator(
			new testGetOrderByComparator("field1, field2"), "table");

		Assert.assertEquals("table.field1, table.field2", _tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithMultipleTableNameReturnsOriginalTableName() {
		_tableNameOrderByComparator = new TableNameOrderByComparator(
			new testGetOrderByComparator("table1.field1, field2"), "table2");

		Assert.assertEquals("table1.field1, table2.field2", _tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithNullTableNameReturnsUndecoratedTableName() {
		_tableNameOrderByComparator = new TableNameOrderByComparator(
			new testGetOrderByComparator("field1, field2"), null);

		Assert.assertEquals("field1, field2", _tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithSingleFieldReturnsDecoratedTableName() {
		_tableNameOrderByComparator = new TableNameOrderByComparator(
			new testGetOrderByComparator("field"), "table");

		Assert.assertEquals("table.field", _tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithSingleTableNameReturnsOriginalTableName() {
		_tableNameOrderByComparator = new TableNameOrderByComparator(
			new testGetOrderByComparator("table1.field1"), "table2");

		Assert.assertEquals("table1.field1", _tableNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithSortDirectionReturnsDecoratedTableName() {
		_tableNameOrderByComparator = new TableNameOrderByComparator(
			new testGetOrderByComparator("field ASC"), "table");

		Assert.assertEquals("table.field ASC", _tableNameOrderByComparator.getOrderBy());
	}

	private TableNameOrderByComparator _tableNameOrderByComparator;

	private class testGetOrderByComparator extends OrderByComparator {

		public testGetOrderByComparator(String orderBy) {
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

		private String _orderBy;

	}

}