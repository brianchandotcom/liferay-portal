/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.dao.db.BaseDB;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.DBColumnSizeUpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class DBColumnSizeUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_db.runSQL(
			"create table TestTable1 (testTable1Id INT not null primary key, " +
				"testValue VARCHAR(1) null)");
		_db.runSQL(
			"create table TestTable2 (testTable2Id INT not null primary key, " +
				"testValue VARCHAR(1) null)");
	}

	@After
	public void tearDown() throws Exception {
		_db.runSQL("DROP_TABLE_IF_EXISTS(TestTable1)");
		_db.runSQL("DROP_TABLE_IF_EXISTS(TestTable2)");
	}

	@Test
	public void testUpgrade() throws Exception {
		new DBColumnSizeUpgradeProcess(
			_db.getDBType(), _getVarcharTemplate(), 1, "VARCHAR(2000)"
		).upgrade();

		try (Connection connection = DataAccess.getConnection()) {
			_assertColumn(connection, "TestTable1", "testValue");
			_assertColumn(connection, "TestTable2", "testValue");
		}
	}

	private void _assertColumn(
			Connection connection, String tableName, String columnName)
		throws Exception {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		try (ResultSet resultSet = databaseMetaData.getColumns(
				dbInspector.getCatalog(), dbInspector.getSchema(),
				dbInspector.normalizeName(tableName),
				dbInspector.normalizeName(columnName))) {

			Assert.assertTrue(resultSet.next());

			Assert.assertTrue(
				StringUtil.equalsIgnoreCase(
					dbInspector.normalizeName(_getVarcharTemplate()),
					resultSet.getString("TYPE_NAME")));

			Assert.assertEquals(2000, resultSet.getInt("COLUMN_SIZE"));

			Assert.assertFalse(resultSet.next());
		}
	}

	private String _getVarcharTemplate() {
		if (_varcharTemplate != null) {
			return _varcharTemplate;
		}

		String[] templates = ReflectionTestUtil.invoke(
			_db, "getTemplate", new Class<?>[0]);

		_varcharTemplate = StringUtil.trim(
			templates
				[Arrays.binarySearch(
					ReflectionTestUtil.getFieldValue(BaseDB.class, "TEMPLATE"),
					" VARCHAR")]);

		return _varcharTemplate;
	}

	private final DB _db = DBManagerUtil.getDB();
	private String _varcharTemplate;

}