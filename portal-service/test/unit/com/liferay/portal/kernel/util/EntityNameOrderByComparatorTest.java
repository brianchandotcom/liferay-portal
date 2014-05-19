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
public class EntityNameOrderByComparatorTest {

	@Test
	public void testGetOrderByWithBlankEntityNameReturnsUndecoratedTableName() {
		EntityNameOrderByComparator entityNameOrderByComparator =
			new EntityNameOrderByComparator(
				new TestGetOrderByComparator("column1, column2"), "");

		Assert.assertEquals(
			"column1, column2", entityNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithMultipleColumnNamesReturnsDecoratedEntityName() {
		EntityNameOrderByComparator entityNameOrderByComparator =
			new EntityNameOrderByComparator(
				new TestGetOrderByComparator("column1, column2"), "entity");

		Assert.assertEquals(
			"{entity.column1}, {entity.column2}",
			entityNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithNullEntityNameReturnsUndecoratedTableName() {
		EntityNameOrderByComparator entityNameOrderByComparator =
			new EntityNameOrderByComparator(
				new TestGetOrderByComparator("column1, column2"), null);

		Assert.assertEquals(
			"column1, column2", entityNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithSingleEntityNameReturnsDecoratedTableName() {
		EntityNameOrderByComparator entityNameOrderByComparator =
			new EntityNameOrderByComparator(
				new TestGetOrderByComparator("column"), "entity");

		Assert.assertEquals(
			"{entity.column}", entityNameOrderByComparator.getOrderBy());
	}

	@Test
	public void testGetOrderByWithSortDirectionReturnsDecoratedEntityName() {
		EntityNameOrderByComparator entityNameOrderByComparator =
			new EntityNameOrderByComparator(
				new TestGetOrderByComparator("column ASC"), "entity");

		Assert.assertEquals(
			"{entity.column} ASC", entityNameOrderByComparator.getOrderBy());
	}

	private class TestGetOrderByComparator extends OrderByComparator {

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

		private String _orderBy;

	}

}