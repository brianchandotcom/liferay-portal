/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.db.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.Statement;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class OracleDBTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		_db = DBManagerUtil.getDB();

		_dbInspector = new DBInspector(_connection);

		Assume.assumeTrue(_db.getDBType() == DBType.ORACLE);
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_db = DBManagerUtil.getDB();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		DataAccess.cleanUp(_connection);
	}

	@Before
	public void setUp() throws Exception {
		_createTestTable(_TABLE_NAME_1);
	}

	@After
	public void tearDown() throws Exception {
		_db.runSQL("DROP_TABLE_IF_EXISTS(" + _TABLE_NAME_1 + ")");
	}

	@Test
	public void testGetIndexesWithLockedStatisticsTable() throws Exception {
		_addIndex(new String[] {"typeVarchar", "typeBoolean"});

		try (Statement statement = _connection.createStatement()) {
			statement.execute(
				StringBundler.concat(
					"CALL dbms_stats.lock_table_stats(ownname => '",
					_connection.getSchema(), "', tabname => '", _TABLE_NAME_1,
					"')"));

			List<IndexMetadata> indexMetadatas = ReflectionTestUtil.invoke(
				_db, "getIndexes",
				new Class<?>[] {
					Connection.class, String.class, String.class, boolean.class
				},
				_connection, _TABLE_NAME_1, "typeVarchar", false);

			for (IndexMetadata indexMetadata : indexMetadatas) {
				Assert.assertEquals(
					_dbInspector.normalizeName("IX_TEMP"),
					indexMetadata.getIndexName());
			}

			statement.execute(
				StringBundler.concat(
					"CALL dbms_stats.unlock_table_stats(ownname => '",
					_connection.getSchema(), "', tabname => '", _TABLE_NAME_1,
					"')"));
		}
	}

	private void _addIndex(String[] columnNames) {
		List<IndexMetadata> indexMetadatas = Arrays.asList(
			new IndexMetadata(_INDEX_NAME, _TABLE_NAME_1, false, columnNames));

		ReflectionTestUtil.invoke(
			_db, "addIndexes", new Class<?>[] {Connection.class, List.class},
			_connection, indexMetadatas);
	}

	private void _createTestTable(String tableName) throws Exception {
		_db.runSQL(
			StringBundler.concat(
				"create table ", tableName, " (id LONG not null primary key, ",
				"notNilColumn VARCHAR(75) not null, nilColumn VARCHAR(75) ",
				"null , typeBlob BLOB, typeBoolean BOOLEAN, typeDate DATE ",
				"null, typeDouble DOUBLE, typeInteger INTEGER, typeLong LONG ",
				"null, typeLongDefault LONG default 10 not null, typeSBlob ",
				"SBLOB, typeString STRING null, typeText TEXT null, ",
				"typeVarchar VARCHAR(75) null, typeVarcharDefault VARCHAR(10) ",
				"default 'testValue' not null);"));
	}

	private static final String _INDEX_NAME = "IX_TEMP";

	private static final String _TABLE_NAME_1 = "DBTest1";

	private static Connection _connection;
	private static DB _db;
	private static DBInspector _dbInspector;

}