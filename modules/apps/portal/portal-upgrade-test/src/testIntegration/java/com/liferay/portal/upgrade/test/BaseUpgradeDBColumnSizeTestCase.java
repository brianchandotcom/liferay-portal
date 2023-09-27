/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.BaseDBColumnSizeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public abstract class BaseUpgradeDBColumnSizeTestCase {

	@Before
	public void setUp() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				getCreateTestTableSQL())) {

			preparedStatement.executeUpdate();
		}
	}

	@After
	public void tearDown() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"drop table TestTable")) {

			preparedStatement.executeUpdate();
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		_assertSize(getInitialSize());

		UpgradeProcess upgradeProcess = getUpgradeProcess();

		upgradeProcess.upgrade();

		_assertSize(4000);
	}

	protected abstract String getCreateTestTableSQL();

	protected abstract int getInitialSize();

	protected abstract String getTypeName();

	protected abstract BaseDBColumnSizeUpgradeProcess getUpgradeProcess();

	private void _assertSize(int size) throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBInspector dbInspector = new DBInspector(connection);

			String catalog = dbInspector.getCatalog();
			String schema = dbInspector.getSchema();

			String tableName = dbInspector.normalizeName("TestTable");
			String columnName = dbInspector.normalizeName("testValue");

			try (ResultSet columnResultSet = databaseMetaData.getColumns(
					catalog, schema, tableName, columnName)) {

				Assert.assertTrue(columnResultSet.next());

				Assert.assertEquals(
					columnName, columnResultSet.getString("COLUMN_NAME"));

				Assert.assertEquals(
					dbInspector.normalizeName(getTypeName()),
					columnResultSet.getString("TYPE_NAME"));

				Assert.assertEquals(
					size, columnResultSet.getInt("COLUMN_SIZE"));

				Assert.assertFalse(columnResultSet.next());
			}
		}
	}

}