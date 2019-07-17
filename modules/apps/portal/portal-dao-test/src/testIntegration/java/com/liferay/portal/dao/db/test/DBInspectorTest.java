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
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class DBInspectorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_dbInspector = new DBInspector(_connection);

		_db = DBManagerUtil.getDB();

		_db.runSQL(
			StringBundler.concat(
				"create table ", _TABLE_NAME, " (id LONG not null primary ",
				"key, typeBlob BLOB, typeBoolean BOOLEAN, typeDate DATE null, ",
				"typeDouble DOUBLE, typeInteger INTEGER, typeLong LONG null, ",
				"typeSBlob SBLOB, typeString STRING null, typeText TEXT null, ",
				"typeVarchar VARCHAR(75) null);"));
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("drop table " + _TABLE_NAME);

		DataAccess.cleanUp(_connection);
	}

	@Test
	public void testHasColumn() throws Exception {
		Assert.assertTrue(_dbInspector.hasColumn(_TABLE_NAME, _COLUMN_NAME));
	}

	@Test
	public void testHasColumnLowerCase() throws Exception {
		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		Assume.assumeTrue(databaseMetaData.storesLowerCaseIdentifiers());

		Assert.assertTrue(
			_dbInspector.hasColumn(
				_TABLE_NAME, StringUtil.toLowerCase(_COLUMN_NAME)));
	}

	@Test
	public void testHasColumnNonexisting() throws Exception {
		Assert.assertTrue(
			!_dbInspector.hasColumn(_TABLE_NAME, _COLUMN_NAME_NONEXISTING));
	}

	@Test
	public void testHasColumnTypeBlob() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeBlob", "BLOB null"));
	}

	@Test
	public void testHasColumnTypeBoolean() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "typeBoolean", "BOOLEAN null"));
	}

	@Test
	public void testHasColumnTypeDate() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeDate", "DATE null"));
	}

	@Test
	public void testHasColumnTypeDouble() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "typeDouble", "DOUBLE null"));
	}

	@Test
	public void testHasColumnTypeInteger() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "typeInteger", "INTEGER null"));
	}

	@Test
	public void testHasColumnTypeLong() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeLong", "LONG null"));
	}

	@Test
	public void testHasColumnTypeSBlob() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeSBlob", "SBLOB null"));
	}

	@Test
	public void testHasColumnTypeString() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "typeString", "STRING null"));
	}

	@Test
	public void testHasColumnTypeText() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeText", "TEXT null"));
	}

	@Test
	public void testHasColumnTypeVarchar() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "typeVarchar", "VARCHAR(75) null"));
	}

	@Test
	public void testHasColumnUpperCase() throws Exception {
		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		Assume.assumeTrue(databaseMetaData.storesUpperCaseIdentifiers());

		Assert.assertTrue(
			_dbInspector.hasColumn(
				_TABLE_NAME, StringUtil.toUpperCase(_COLUMN_NAME)));
	}

	@Test
	public void testHasTable() throws Exception {
		Assert.assertTrue(_dbInspector.hasTable(_TABLE_NAME));
	}

	@Test
	public void testHasTableLowerCase() throws Exception {
		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		Assume.assumeTrue(databaseMetaData.storesLowerCaseIdentifiers());

		Assert.assertTrue(
			_dbInspector.hasTable(StringUtil.toLowerCase(_TABLE_NAME)));
	}

	@Test
	public void testHasTableNonexisting() throws Exception {
		Assert.assertTrue(!_dbInspector.hasTable(_TABLE_NAME_NONEXISTING));
	}

	@Test
	public void testHasTableUpperCase() throws Exception {
		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		Assume.assumeTrue(databaseMetaData.storesUpperCaseIdentifiers());

		Assert.assertTrue(
			_dbInspector.hasTable(StringUtil.toUpperCase(_TABLE_NAME)));
	}

	private static final String _COLUMN_NAME = "id";

	private static final String _COLUMN_NAME_NONEXISTING = "nonexistingColumn";

	private static final String _TABLE_NAME = "DBInspectorTest";

	private static final String _TABLE_NAME_NONEXISTING = "NonexistingTable";

	private static Connection _connection;
	private static DB _db;
	private static DBInspector _dbInspector;

}