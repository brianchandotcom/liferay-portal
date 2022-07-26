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

package com.liferay.portal.dao.db.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.BaseDBProcess;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;

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
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class BaseDBProcessTest extends BaseDBProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_dbInspector = new DBInspector(_connection);

		_db = DBManagerUtil.getDB();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		DataAccess.cleanUp(_connection);
	}

	@Before
	public void setUp() throws Exception {
		connection = _connection;

		runSQL(
			StringBundler.concat(
				"create table ", _TABLE_NAME, " (id LONG not null primary ",
				"key, notNilColumn VARCHAR(75) not null, nilColumn ",
				"VARCHAR(75) null, typeBlob BLOB, typeBoolean BOOLEAN,",
				"typeDate DATE null, typeDouble DOUBLE, typeInteger INTEGER, ",
				"typeLong LONG null, typeSBlob SBLOB, typeString STRING null, ",
				"typeText TEXT null, typeVarchar VARCHAR(75) null);"));
	}

	@After
	public void tearDown() throws Exception {
		runSQL("drop table " + _TABLE_NAME);
	}

	@Test
	public void testAlterColumnNameChangeType() throws Exception {
		try {
			alterColumnName(_TABLE_NAME, "typeBoolean", "typeChanged VARCHAR");

			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		Assert.assertFalse(_dbInspector.hasColumn(_TABLE_NAME, "typeChanged"));
	}

	@Test
	public void testAlterColumnNameNoNeeded() throws Exception {
		alterColumnName(_TABLE_NAME, "deletedColumn", "typeBoolean BOOLEAN");

		Assert.assertFalse(
			_dbInspector.hasColumn(_TABLE_NAME, "deletedColumn"));

		Assert.assertTrue(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeBoolean", "BOOLEAN"));
	}

	@Test
	public void testAlterColumnNameNoNeededOldColumnExist() throws Exception {
		try {
			alterColumnName(_TABLE_NAME, "typeInteger", "typeBoolean BOOLEAN");

			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		Assert.assertTrue(_dbInspector.hasColumn(_TABLE_NAME, "typeInteger"));

		Assert.assertTrue(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeBoolean", "BOOLEAN"));
	}

	@Test
	public void testAlterColumnNameNoNeededWithAlterType() throws Exception {
		try {
			alterColumnName(
				_TABLE_NAME, "deletedColumn", "typeBoolean VARCHAR");

			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		Assert.assertFalse(
			_dbInspector.hasColumn(_TABLE_NAME, "deletedColumn"));

		Assert.assertFalse(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeBoolean", "VARCHAR"));
	}

	@Test
	public void testAlterColumnNameNoNeededWithAlterTypeAndOldColumnExist()
		throws Exception {

		try {
			alterColumnName(_TABLE_NAME, "typeInteger", "typeBoolean VARCHAR");

			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		Assert.assertTrue(_dbInspector.hasColumn(_TABLE_NAME, "typeInteger"));

		Assert.assertFalse(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeBoolean", "VARCHAR"));
	}

	@Test
	public void testAlterColumnNameNonexistedColumn() throws Exception {
		try {
			alterColumnName(
				_TABLE_NAME, "nonexistedColumn",
				"newNonexistedColumn LONG null");

			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		Assert.assertFalse(
			_dbInspector.hasColumn(_TABLE_NAME, "nonexistedColumn"));
	}

	@Test
	public void testAlterColumnNameSameColumnDifferentCase() throws Exception {
		alterColumnName(_TABLE_NAME, "typeInteger", "TypeInteger INTEGER");

		if (StringUtil.equals(
				_dbInspector.normalizeName("typeInteger"),
				_dbInspector.normalizeName("TypeInteger"))) {

			Assert.assertTrue(
				_dbInspector.hasColumn(_TABLE_NAME, "typeInteger"));
		}
		else {
			Assert.assertTrue(
				_dbInspector.hasColumn(_TABLE_NAME, "TypeInteger"));
		}
	}

	@Test
	public void testAlterColumnTypeAlterSize() throws Exception {
		alterColumnType(_TABLE_NAME, "notNilColumn", "VARCHAR(200) not null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "notNilColumn", "VARCHAR(200) not null"));
	}

	@Test
	public void testAlterColumnTypeChangeToNotNull() throws Exception {
		alterColumnType(_TABLE_NAME, "nilColumn", "VARCHAR(75) not null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "nilColumn", "VARCHAR(75) not null"));
	}

	@Test
	public void testAlterColumnTypeChangeToNull() throws Exception {
		alterColumnType(_TABLE_NAME, "notNilColumn", "VARCHAR(75) null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "notNilColumn", "VARCHAR(75) null"));
	}

	@Test
	public void testAlterColumnTypeChangeToText() throws Exception {
		alterColumnType(_TABLE_NAME, "typeString", "TEXT null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeString", "TEXT null"));
	}

	@Test
	public void testAlterColumnTypeNoChangesNotNull() throws Exception {
		alterColumnType(_TABLE_NAME, "notNilColumn", "VARCHAR(75) not null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "notNilColumn", "VARCHAR(75) not null"));
	}

	@Test
	public void testAlterColumnTypeNoChangesNull() throws Exception {
		alterColumnType(_TABLE_NAME, "nilColumn", "VARCHAR(75) null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "nilColumn", "VARCHAR(75) null"));
	}

	@Test
	public void testAlterColumnTypeNoNeeded() throws Exception {
		alterColumnType(_TABLE_NAME, "typeText", "TEXT null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeText", "TEXT null"));
	}

	@Test
	public void testAlterColumnTypeNonexistedColumn() throws Exception {
		try {
			alterColumnType(_TABLE_NAME, "nonexistedColumn", "TEXT not null");

			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		Assert.assertFalse(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "nonexistedColumn", "TEXT not null"));
	}

	@Test
	public void testAlterColumnTypeWithData() throws Exception {
		runSQL(
			"insert into " + _TABLE_NAME +
				" (id, notNilColumn, typeString) values (1, '1', 'testValue')");

		alterColumnType(_TABLE_NAME, "typeString", "TEXT null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeString", "TEXT null"));

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select typeString from " + _TABLE_NAME);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.next();

			Assert.assertEquals("testValue", resultSet.getString(1));
		}
	}

	@Test
	public void testAlterIndexedColumnName() throws Exception {
		_addIndex(new String[] {"typeVarchar", "typeBoolean"});

		alterColumnName(
			_TABLE_NAME, "typeVarchar", "typeVarcharTest VARCHAR(75) null");

		Assert.assertTrue(
			_dbInspector.hasColumn(_TABLE_NAME, "typeVarcharTest"));

		_validateIndex(
			new String[] {
				_dbInspector.normalizeName("typeVarcharTest"),
				_dbInspector.normalizeName("typeBoolean")
			});
	}

	@Test
	public void testAlterIndexedColumnType() throws Exception {
		_addIndex(new String[] {"typeVarchar", "typeBoolean"});

		alterColumnType(_TABLE_NAME, "typeVarchar", "VARCHAR(50) null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "typeVarchar", "VARCHAR(50) null"));

		_validateIndex(
			new String[] {
				_dbInspector.normalizeName("typeVarchar"),
				_dbInspector.normalizeName("typeBoolean")
			});
	}

	@Test
	public void testAlterPrimaryKeyName() throws Exception {
		alterColumnName(_TABLE_NAME, "id", "idTest LONG not null");

		String[] primaryKeyColumnNames = ReflectionTestUtil.invoke(
			_db, "getPrimaryKeyColumnNames",
			new Class<?>[] {Connection.class, String.class}, _connection,
			_TABLE_NAME);

		Assert.assertTrue(
			ArrayUtil.contains(
				primaryKeyColumnNames, _dbInspector.normalizeName("idTest")));
	}

	@Test
	public void testAlterPrimaryKeyType() throws Exception {
		alterColumnType(_TABLE_NAME, "id", "VARCHAR(75) not null");

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "id", "VARCHAR(75) not null"));
	}

	@Test
	public void testAlterTableAddColumn() throws Exception {
		alterTableAddColumn(_TABLE_NAME, "testColumn", "LONG null");

		Assert.assertTrue(_dbInspector.hasColumn(_TABLE_NAME, "testColumn"));
	}

	@Test
	public void testAlterTableAddExistingColumn() throws Exception {
		alterTableAddColumn(_TABLE_NAME, "typeDouble", "DOUBLE");
	}

	@Test
	public void testAlterTableAddExistingColumnWithDifferentType()
		throws Exception {

		try {
			alterTableAddColumn(_TABLE_NAME, "typeDouble", "VARCHAR");

			Assert.fail();
		}
		catch (SQLException sqlException) {
		}

		Assert.assertFalse(hasColumnType(_TABLE_NAME, "typeDouble", "VARCHAR"));
	}

	@Test
	public void testAlterTableDropIndexedColumn() throws Exception {
		_addIndex(new String[] {"typeVarchar", "typeBoolean"});

		alterTableDropColumn(_TABLE_NAME, "typeVarchar");

		Assert.assertFalse(_dbInspector.hasColumn(_TABLE_NAME, "typeVarchar"));

		List<IndexMetadata> indexMetadatas = ReflectionTestUtil.invoke(
			_db, "getIndexes",
			new Class<?>[] {
				Connection.class, String.class, String.class, boolean.class
			},
			_connection, _TABLE_NAME, "typeVarchar", false);

		Assert.assertEquals(
			indexMetadatas.toString(), 0, indexMetadatas.size());
	}

	@Test
	public void testAlterTableDropNonexistedColumn() throws Exception {
		alterTableDropColumn(_TABLE_NAME, "nonexistedColumn");
	}

	private void _addIndex(String[] columnNames) {
		List<IndexMetadata> indexMetadatas = Arrays.asList(
			new IndexMetadata(_INDEX_NAME, _TABLE_NAME, false, columnNames));

		ReflectionTestUtil.invoke(
			_db, "addIndexes", new Class<?>[] {Connection.class, List.class},
			_connection, indexMetadatas);
	}

	private void _validateIndex(String[] columnNames) throws Exception {
		List<IndexMetadata> indexMetadatas = ReflectionTestUtil.invoke(
			_db, "getIndexes",
			new Class<?>[] {
				Connection.class, String.class, String.class, boolean.class
			},
			_connection, _TABLE_NAME, columnNames[0], false);

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

	private static final String _TABLE_NAME = "BasedDBProcessTest";

	private static Connection _connection;
	private static DB _db;
	private static DBInspector _dbInspector;

}