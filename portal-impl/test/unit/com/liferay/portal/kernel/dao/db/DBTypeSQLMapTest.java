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

package com.liferay.portal.kernel.dao.db;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBTypeSQLMapTest {

	@Test
	public void testGetReturnsActual() {
		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(_SQL_DEFAULT);

		dbTypeToSQLMap.add(DBType.MYSQL, _SQL_MYSQL);

		String sql = dbTypeToSQLMap.get(DBType.MYSQL);

		Assert.assertEquals(_SQL_MYSQL, sql);
	}

	@Test
	public void testGetReturnsDefault() {
		DBTypeToSQLMap dbTypeToSQLMap = new DBTypeToSQLMap(_SQL_DEFAULT);

		dbTypeToSQLMap.add(DBType.MYSQL, _SQL_MYSQL);

		String sql = dbTypeToSQLMap.get(DBType.ORACLE);

		Assert.assertEquals(_SQL_DEFAULT, sql);
	}

	private static final String _SQL_DEFAULT = "select * from table";

	private static final String _SQL_MYSQL = "select * from myTable";

}