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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 */
public class DBInspectorTest {

	public static final String TABLE_NAME = "FieldTypes";

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(_CREATE_TABLE);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		DB db = DBManagerUtil.getDB();

		db.runSQL(_DROP_TABLE);
	}

	@Before
	public void setUp() throws Exception {
		if (_dbInspector == null) {
			_dbInspector = new DBInspector(DataAccess.getConnection());
		}
	}

	@Test
	public void testHasColumnTypeBlob() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(TABLE_NAME, "typeBlob", "BLOB null"));
	}

	@Test
	public void testHasColumnTypeBoolean() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(
				TABLE_NAME, "typeBoolean", "BOOLEAN null"));
	}

	@Test
	public void testHasColumnTypeDate() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(TABLE_NAME, "typeDate", "DATE null"));
	}

	@Test
	public void testHasColumnTypeDouble() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(
				TABLE_NAME, "typeDouble", "DOUBLE null"));
	}

	@Test
	public void testHasColumnTypeInteger() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(
				TABLE_NAME, "typeInteger", "INTEGER null"));
	}

	@Test
	public void testHasColumnTypeLong() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(TABLE_NAME, "typeLong", "LONG"));
	}

	@Test
	public void testHasColumnTypeSBlob() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(TABLE_NAME, "typeSBlob", "SBLOB null"));
	}

	@Test
	public void testHasColumnTypeString() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(
				TABLE_NAME, "typeString", "STRING null"));
	}

	@Test
	public void testHasColumnTypeText() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(TABLE_NAME, "typeText", "TEXT null"));
	}

	@Test
	public void testHasColumnTypeVarchar() throws Exception {
		Assert.assertTrue(
			_dbInspector.hasColumnType(
				TABLE_NAME, "typeVarchar", "VARCHAR(75) null"));
	}

	private static final String _CREATE_TABLE = StringBundler.concat(
		"create table FieldTypes (typeBlob BLOB, typeSBlob SBLOB, typeBoolean ",
		"BOOLEAN, typeDate DATE null, typeDouble DOUBLE, typeInteger INTEGER, ",
		"typeLong LONG not null primary key, typeString STRING null, typeText ",
		"TEXT null, typeVarchar VARCHAR(75) null);");

	private static final String _DROP_TABLE = "drop table FieldTypes";

	private static DBInspector _dbInspector;

}