/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.db;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBTypeSQLMapTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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