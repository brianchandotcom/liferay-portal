/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.db.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class DBTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_db = DBManagerUtil.getDB();

		_dbInspector = new DBInspector(_connection);

		for (int i = 0; i < _SYNC_TABLES_COLUMN_NAMES.length; i++) {
			_SYNC_TABLES_COLUMN_NAMES[i] = _dbInspector.normalizeName(
				_SYNC_TABLES_COLUMN_NAMES[i]);
		}
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
		_db.runSQL("DROP_TABLE_IF_EXISTS(" + _TABLE_NAME_2 + ")");
		_db.runSQL("DROP_TABLE_IF_EXISTS(" + _TABLE_NAME_3 + ")");
	}

	@Test
	public void testAlterColumnNameNoNullableChange() throws Exception {
		_db.alterColumnName(
			_connection, _TABLE_NAME_1, "nilColumn",
			"nilColumnTest VARCHAR(75) null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "nilColumnTest", "VARCHAR(75) null"));

		_db.alterColumnName(
			_connection, _TABLE_NAME_1, "notNilColumn",
			"notNilColumnTest VARCHAR(75) not null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "notNilColumnTest", "VARCHAR(75) not null"));
	}

	@Test
	public void testAlterColumnTypeAlterSize() throws Exception {
		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "notNilColumn",
			"VARCHAR(200) not null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "notNilColumn", "VARCHAR(200) not null"));
	}

	@Test
	public void testAlterColumnTypeChangeToDefaultNotNull() throws Exception {
		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "nilColumn",
			"VARCHAR(75) default 'test' not null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "nilColumn",
				"VARCHAR(75) default 'test' not null"));
	}

	@Test
	public void testAlterColumnTypeChangeToDefaultNull() throws Exception {
		try {
			_db.alterColumnType(
				_connection, _TABLE_NAME_1, "notNilColumn",
				"VARCHAR(75) default 'test' null");

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"Invalid alter column type statement",
				illegalArgumentException.getMessage());

			Assert.assertTrue(
				_dbInspector.hasColumnType(
					_TABLE_NAME_1, "notNilColumn", "VARCHAR(75) not null"));
		}
	}

	@Test
	public void testAlterColumnTypeChangeToNotNull() throws Exception {
		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "nilColumn", "VARCHAR(75) not null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "nilColumn", "VARCHAR(75) not null"));
	}

	@Test
	public void testAlterColumnTypeChangeToNull() throws Exception {
		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "notNilColumn", "VARCHAR(75) null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "notNilColumn", "VARCHAR(75) null"));
	}

	@Test
	public void testAlterColumnTypeChangeToText() throws Exception {
		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "typeString", "TEXT null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "typeString", "TEXT null"));
	}

	@Test
	public void testAlterColumnTypeChangeWithoutDefaultClause()
		throws Exception {

		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "typeVarcharDefault",
			"VARCHAR(10) not null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "typeVarcharDefault", "VARCHAR(10) not null"));
	}

	@Test
	public void testAlterColumnTypeChangeWithoutNullClause() throws Exception {
		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "notNilColumn", "VARCHAR(75)");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "notNilColumn", "VARCHAR(75) null"));

		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "nilColumn", "VARCHAR(75)");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "nilColumn", "VARCHAR(75) null"));
	}

	@Test
	public void testAlterColumnTypeDefaultWithData() throws Exception {
		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "nilColumn",
			"VARCHAR(75) default 'test' not null");

		_db.runSQL(
			"insert into " + _TABLE_NAME_1 +
				" (id, notNilColumn) values (1, '1')");

		_db.runSQL(
			"insert into " + _TABLE_NAME_1 +
				" (id, notNilColumn, nilColumn) values (2, '2', 'nil')");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "nilColumn",
				"VARCHAR(75) default 'test' not null"));

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select nilColumn from " + _TABLE_NAME_1 + " order by id");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.next();

			Assert.assertEquals("test", resultSet.getString(1));

			resultSet.next();

			Assert.assertEquals("nil", resultSet.getString(1));
		}
	}

	@Test
	public void testAlterColumnTypeNoChangesNotNull() throws Exception {
		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "notNilColumn", "VARCHAR(75) not null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "notNilColumn", "VARCHAR(75) not null"));
	}

	@Test
	public void testAlterColumnTypeNoChangesNull() throws Exception {
		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "nilColumn", "VARCHAR(75) null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "nilColumn", "VARCHAR(75) null"));
	}

	@Test
	public void testAlterColumnTypeWithData() throws Exception {
		_db.runSQL(
			"insert into " + _TABLE_NAME_1 +
				" (id, notNilColumn, typeString) values (1, '1', 'testValue')");

		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "typeString", "TEXT null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "typeString", "TEXT null"));

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select typeString from " + _TABLE_NAME_1);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.next();

			Assert.assertEquals("testValue", resultSet.getString(1));
		}
	}

	@Test
	public void testAlterIndexedColumnName() throws Exception {
		_addIndex(new String[] {"typeVarchar", "typeBoolean"});

		_db.alterColumnName(
			_connection, _TABLE_NAME_1, "typeVarchar",
			"typeVarcharTest VARCHAR(75) null");

		Assert.assertTrue(
			_dbInspector.hasColumn(_TABLE_NAME_1, "typeVarcharTest"));

		_validateIndex(
			new String[] {
				_dbInspector.normalizeName("typeVarcharTest"),
				_dbInspector.normalizeName("typeBoolean")
			});
	}

	@Test
	public void testAlterIndexedColumnType() throws Exception {
		_addIndex(new String[] {"typeVarchar", "typeBoolean"});

		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "typeVarchar", "VARCHAR(50) null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "typeVarchar", "VARCHAR(50) null"));

		_validateIndex(
			new String[] {
				_dbInspector.normalizeName("typeVarchar"),
				_dbInspector.normalizeName("typeBoolean")
			});
	}

	@Test
	public void testAlterPrimaryKeyName() throws Exception {
		_db.alterColumnName(
			_connection, _TABLE_NAME_1, "id", "idTest LONG not null");

		Assert.assertTrue(
			ArrayUtil.contains(
				_db.getPrimaryKeyColumnNames(_connection, _TABLE_NAME_1),
				_dbInspector.normalizeName("idTest")));
	}

	@Test
	public void testAlterPrimaryKeyType() throws Exception {
		_db.alterColumnType(
			_connection, _TABLE_NAME_1, "id", "VARCHAR(75) not null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "id", "VARCHAR(75) not null"));
	}

	@Test
	public void testAlterTableAddColumn() throws Exception {
		_db.alterTableAddColumn(
			_connection, _TABLE_NAME_1, "testColumn", "LONG null");

		Assert.assertTrue(_dbInspector.hasColumn(_TABLE_NAME_1, "testColumn"));
	}

	@Test
	public void testAlterTableAddColumnLongDefaultNotNull() throws Exception {
		_db.alterTableAddColumn(
			_connection, _TABLE_NAME_1, "testColumn",
			"LONG default 2 not null");

		_db.runSQL(
			"insert into " + _TABLE_NAME_1 +
				" (id, notNilColumn) values (1, '1')");

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select testColumn from " + _TABLE_NAME_1);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.next();

			Assert.assertEquals(2, resultSet.getLong(1));
		}

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "testColumn", "LONG default 2 not null"));
	}

	@Test
	public void testAlterTableAddColumnVarcharDefaultNotNull()
		throws Exception {

		_db.alterTableAddColumn(
			_connection, _TABLE_NAME_1, "testColumn",
			"VARCHAR(40) default 'test value' not null");

		_db.runSQL(
			"insert into " + _TABLE_NAME_1 +
				" (id, notNilColumn) values (1, '1')");

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select testColumn from " + _TABLE_NAME_1);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.next();

			Assert.assertEquals("test value", resultSet.getString(1));
		}

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME_1, "testColumn",
				"VARCHAR(40) default 'test value' not null"));
	}

	@Test
	public void testAlterTableDropIndexedColumn() throws Exception {
		_addIndex(new String[] {"typeVarchar", "typeBoolean"});

		_db.alterTableDropColumn(_connection, _TABLE_NAME_1, "typeVarchar");

		Assert.assertFalse(
			_dbInspector.hasColumn(_TABLE_NAME_1, "typeVarchar"));

		List<IndexMetadata> indexMetadatas = ReflectionTestUtil.invoke(
			_db, "getIndexes",
			new Class<?>[] {
				Connection.class, String.class, String.class, boolean.class
			},
			_connection, _TABLE_NAME_1, "typeVarchar", false);

		Assert.assertEquals(
			indexMetadatas.toString(), 0, indexMetadatas.size());
	}

	@Test
	public void testAlterTableName() throws Exception {
		_db.runSQL(
			StringBundler.concat(
				"alter_table_name ", _TABLE_NAME_1, StringPool.SPACE,
				_TABLE_NAME_2));

		Assert.assertTrue(_dbInspector.hasTable(_TABLE_NAME_2));

		_db.runSQL("DROP_TABLE_IF_EXISTS(" + _TABLE_NAME_2 + ")");

		Assert.assertFalse(_dbInspector.hasTable(_TABLE_NAME_1));
	}

	@Test
	public void testCopyTableRows() throws Exception {
		_createTestTable(_TABLE_NAME_2);

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME_1,
				" (id, notNilColumn, typeString) values (1, '1', ",
				"'testTable1Value1')"));

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME_1,
				" (id, notNilColumn, typeString) values (2, '2', ",
				"'testTable1Value2')"));

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME_1,
				" (id, notNilColumn) values (3, '3')"));

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME_2,
				" (id, notNilColumn, typeString) values (1, '1', ",
				"'testTable2Value1')"));

		Map<String, String> columnNamesMap = new HashMap<>();

		for (String columnName : _SYNC_TABLES_COLUMN_NAMES) {
			columnNamesMap.put(columnName, columnName);
		}

		_db.copyTableRows(
			_connection, _TABLE_NAME_1, _TABLE_NAME_2, columnNamesMap,
			HashMapBuilder.put(
				_dbInspector.normalizeName("typeString"), "'test'"
			).build());

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select * from " + _TABLE_NAME_2 + " order by id asc");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(1, resultSet.getLong("id"));
			Assert.assertEquals("1", resultSet.getString("notNilColumn"));
			Assert.assertEquals(
				"testTable2Value1", resultSet.getString("typeString"));

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(2, resultSet.getLong("id"));
			Assert.assertEquals("2", resultSet.getString("notNilColumn"));
			Assert.assertEquals(
				"testTable1Value2", resultSet.getString("typeString"));

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(3, resultSet.getLong("id"));
			Assert.assertEquals("3", resultSet.getString("notNilColumn"));
			Assert.assertEquals("test", resultSet.getString("typeString"));

			Assert.assertFalse(resultSet.next());
		}
	}

	@Test
	public void testCopyTableRowsDifferentColumnNames() throws Exception {
		_db.runSQL(
			StringBundler.concat(
				"create table ", _TABLE_NAME_2, " (id2 LONG not null primary ",
				"key, notNilColumn2 VARCHAR(75) not null, nilColumn2 ",
				"VARCHAR(75) null, typeBlob2 BLOB, typeBoolean2 BOOLEAN,",
				"typeDate2 DATE null, typeDouble2 DOUBLE, typeInteger2 ",
				"INTEGER, typeLong2 LONG null, typeSBlob2 SBLOB, typeString2 ",
				"STRING null, typeText2 TEXT null, typeVarchar2 VARCHAR(75) ",
				"null);"));

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME_1,
				" (id, notNilColumn, typeString) values (1, '1', ",
				"'testTable1Value1')"));

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME_1,
				" (id, notNilColumn, typeString) values (2, '2', ",
				"'testTable1Value2')"));

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME_1,
				" (id, notNilColumn) values (3, '3')"));

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME_2,
				" (id2, notNilColumn2, typeString2) values (1, '1', ",
				"'testTable2Value1')"));

		Map<String, String> columnNamesMap = new HashMap<>();

		for (String columnName : _SYNC_TABLES_COLUMN_NAMES) {
			columnNamesMap.put(columnName, columnName + "2");
		}

		_db.copyTableRows(
			_connection, _TABLE_NAME_1, _TABLE_NAME_2, columnNamesMap,
			HashMapBuilder.put(
				_dbInspector.normalizeName("typeString2"), "'test'"
			).build());

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select * from " + _TABLE_NAME_2 + " order by id2 asc");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(1, resultSet.getLong("id2"));
			Assert.assertEquals("1", resultSet.getString("notNilColumn2"));
			Assert.assertEquals(
				"testTable2Value1", resultSet.getString("typeString2"));

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(2, resultSet.getLong("id2"));
			Assert.assertEquals("2", resultSet.getString("notNilColumn2"));
			Assert.assertEquals(
				"testTable1Value2", resultSet.getString("typeString2"));

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(3, resultSet.getLong("id2"));
			Assert.assertEquals("3", resultSet.getString("notNilColumn2"));
			Assert.assertEquals("test", resultSet.getString("typeString2"));

			Assert.assertFalse(resultSet.next());
		}
	}

	@Test
	public void testCopyTableStructure() throws Exception {
		String[] indexColumnNames = {"typeVarchar", "typeBoolean"};

		_addIndex(indexColumnNames);

		_db.copyTableStructure(_connection, _TABLE_NAME_1, _TABLE_NAME_2);

		boolean supportsDuplicatedIndexName = ReflectionTestUtil.invoke(
			_db, "isSupportsDuplicatedIndexName", new Class<?>[0]);

		Assert.assertTrue(_dbInspector.hasTable(_TABLE_NAME_2));
		Assert.assertFalse(_dbInspector.hasRows(_TABLE_NAME_2));
		Assert.assertFalse(_dbInspector.isNullable(_TABLE_NAME_2, "id"));
		Assert.assertFalse(
			_dbInspector.isNullable(_TABLE_NAME_2, "notNilColumn"));

		String indexNamePrefix = StringPool.BLANK;

		if (!supportsDuplicatedIndexName) {
			indexNamePrefix = "TMP_";
		}

		Assert.assertTrue(
			_dbInspector.hasIndex(
				_TABLE_NAME_2, indexNamePrefix + _INDEX_NAME));

		Assert.assertArrayEquals(
			new String[] {_dbInspector.normalizeName("id")},
			_db.getPrimaryKeyColumnNames(_connection, _TABLE_NAME_2));
	}

	@Test
	public void testGetPrimaryKeyColumnNames() throws Exception {
		_db.runSQL(_SQL_CREATE_TABLE_2);

		Assert.assertArrayEquals(
			new String[] {
				_dbInspector.normalizeName("id2"),
				_dbInspector.normalizeName("id1")
			},
			_db.getPrimaryKeyColumnNames(_connection, _TABLE_NAME_2));
	}

	@Test
	public void testGetPrimaryKeyColumnNamesIncorrectOrder() throws Exception {
		_db.runSQL(_SQL_CREATE_TABLE_2);

		Assert.assertFalse(
			Arrays.equals(
				new String[] {
					_dbInspector.normalizeName("id1"),
					_dbInspector.normalizeName("id2")
				},
				_db.getPrimaryKeyColumnNames(_connection, _TABLE_NAME_2)));
	}

	@Test
	public void testRenameTables() throws Exception {
		_db.runSQL(_SQL_CREATE_TABLE_2);

		_db.renameTables(
			_connection, new ObjectValuePair<>(_TABLE_NAME_1, _TABLE_NAME_3),
			new ObjectValuePair<>(_TABLE_NAME_2, _TABLE_NAME_1),
			new ObjectValuePair<>(_TABLE_NAME_3, _TABLE_NAME_2));

		Assert.assertTrue(_dbInspector.hasTable(_TABLE_NAME_1));
		Assert.assertTrue(_dbInspector.hasTable(_TABLE_NAME_2));

		Assert.assertTrue(_dbInspector.hasColumn(_TABLE_NAME_1, "id1"));
		Assert.assertTrue(_dbInspector.hasColumn(_TABLE_NAME_2, "id"));
	}

	@Test
	public void testRenameTablesRollback() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.dao.db.BaseDB", LoggerTestUtil.OFF)) {

			_db.renameTables(
				_connection,
				new ObjectValuePair<>(_TABLE_NAME_1, _TABLE_NAME_3),
				new ObjectValuePair<>(_TABLE_NAME_2, _TABLE_NAME_1));

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertTrue(_dbInspector.hasTable(_TABLE_NAME_1));
			Assert.assertFalse(_dbInspector.hasTable(_TABLE_NAME_2));
			Assert.assertFalse(_dbInspector.hasTable(_TABLE_NAME_3));
		}
	}

	@Test
	public void testSyncTables() throws Exception {
		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME_1,
				" (id, notNilColumn, typeString) values (1, '1', ",
				"'testValueA')"));

		_createTestTable(_TABLE_NAME_2);

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME_2,
				" (id, notNilColumn, typeString) values (1, '1', ",
				"'testValueA')"));

		Map<String, String> columnNamesMap = new HashMap<>();

		for (String columnName : _SYNC_TABLES_COLUMN_NAMES) {
			columnNamesMap.put(columnName, columnName);
		}

		try (AutoCloseable autoCloseable = _db.syncTables(
				_connection, _TABLE_NAME_1, _TABLE_NAME_2, columnNamesMap,
				HashMapBuilder.put(
					_dbInspector.normalizeName("typeString"), "'test'"
				).build())) {

			_db.runSQL(
				StringBundler.concat(
					"insert into ", _TABLE_NAME_1,
					" (id, notNilColumn, typeString) values (2, '2', ",
					"'testValueB')"));

			_db.runSQL(
				StringBundler.concat(
					"insert into ", _TABLE_NAME_1,
					" (id, notNilColumn) values (3, '3')"));

			_db.runSQL("delete from " + _TABLE_NAME_1 + " where id = 1");

			_db.runSQL(
				"update " + _TABLE_NAME_1 +
					" set typeString = NULL where id = 2");
		}

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select * from " + _TABLE_NAME_2 + " order by id");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(2, resultSet.getLong("id"));
			Assert.assertEquals("2", resultSet.getString("notNilColumn"));
			Assert.assertEquals("test", resultSet.getString("typeString"));

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(3, resultSet.getLong("id"));
			Assert.assertEquals("3", resultSet.getString("notNilColumn"));
			Assert.assertEquals("test", resultSet.getString("typeString"));

			Assert.assertFalse(resultSet.next());
		}
	}

	@Test
	public void testSyncTablesDifferentColumnNames() throws Exception {
		_db.runSQL(
			StringBundler.concat(
				"create table ", _TABLE_NAME_2, " (id2 LONG not null primary ",
				"key, notNilColumn2 VARCHAR(75) not null, nilColumn2 ",
				"VARCHAR(75) null, typeBlob2 BLOB, typeBoolean2 BOOLEAN,",
				"typeDate2 DATE null, typeDouble2 DOUBLE, typeInteger2 ",
				"INTEGER, typeLong2 LONG null, typeLongDefault2 LONG default ",
				"10 not null, typeSBlob2 SBLOB, typeString2 STRING null, ",
				"typeText2 TEXT null, typeVarchar2 VARCHAR(75) null,",
				"typeVarcharDefault2 VARCHAR(10) default 'testValue' not ",
				"null);"));

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME_1,
				" (id, notNilColumn, typeString) values (1, '1', ",
				"'testValueA')"));

		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME_2,
				" (id2, notNilColumn2, typeString2) values (1, '1', ",
				"'testValueA')"));

		Map<String, String> columnNamesMap = new HashMap<>();

		for (String columnName : _SYNC_TABLES_COLUMN_NAMES) {
			columnNamesMap.put(columnName, columnName + "2");
		}

		try (AutoCloseable autoCloseable = _db.syncTables(
				_connection, _TABLE_NAME_1, _TABLE_NAME_2, columnNamesMap,
				HashMapBuilder.put(
					_dbInspector.normalizeName("typeString2"), "'test'"
				).build())) {

			_db.runSQL(
				StringBundler.concat(
					"insert into ", _TABLE_NAME_1,
					" (id, notNilColumn, typeString) values (2, '2', ",
					"'testValueB')"));

			_db.runSQL(
				StringBundler.concat(
					"insert into ", _TABLE_NAME_1,
					" (id, notNilColumn) values (3, '3')"));

			_db.runSQL("delete from " + _TABLE_NAME_1 + " where id = 1");

			_db.runSQL(
				"update " + _TABLE_NAME_1 +
					" set typeString = NULL where id = 2");
		}

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select * from " + _TABLE_NAME_2 + " order by id2");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(2, resultSet.getLong("id2"));
			Assert.assertEquals("2", resultSet.getString("notNilColumn2"));
			Assert.assertEquals("test", resultSet.getString("typeString2"));

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(3, resultSet.getLong("id2"));
			Assert.assertEquals("3", resultSet.getString("notNilColumn2"));
			Assert.assertEquals("test", resultSet.getString("typeString2"));

			Assert.assertFalse(resultSet.next());
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

	private List<IndexMetadata> _getIndexes(
		String tableName, String[] columnNames) {

		return ReflectionTestUtil.invoke(
			_db, "getIndexes",
			new Class<?>[] {
				Connection.class, String.class, String.class, boolean.class
			},
			_connection, tableName, columnNames[0], false);
	}

	private void _validateIndex(String[] columnNames) throws Exception {
		List<IndexMetadata> indexMetadatas = _getIndexes(
			_TABLE_NAME_1, columnNames);

		Assert.assertEquals(
			indexMetadatas.toString(), 1, indexMetadatas.size());

		IndexMetadata indexMetadata = indexMetadatas.get(0);

		Assert.assertEquals(
			_dbInspector.normalizeName(_INDEX_NAME),
			indexMetadata.getIndexName());

		Assert.assertArrayEquals(
			ArrayUtil.sortedUnique(columnNames),
			ArrayUtil.sortedUnique(indexMetadata.getColumnNames()));
	}

	private static final String _INDEX_NAME = "IX_TEMP";

	private static final String _SQL_CREATE_TABLE_2 =
		"create table " + DBTest._TABLE_NAME_2 +
			" (id1 LONG not null, id2 LONG not null, primary key (id2, id1))";

	private static final String[] _SYNC_TABLES_COLUMN_NAMES = {
		"id", "notNilColumn", "nilColumn", "typeBlob", "typeBoolean",
		"typeDate", "typeDouble", "typeInteger", "typeLong", "typeSBlob",
		"typeString", "typeText", "typeVarchar"
	};

	private static final String _TABLE_NAME_1 = "DBTest1";

	private static final String _TABLE_NAME_2 = "DBTest2";

	private static final String _TABLE_NAME_3 = "DBTest3";

	private static Connection _connection;
	private static DB _db;
	private static DBInspector _dbInspector;

}