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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import org.eclipse.core.runtime.Assert;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 */
public class DBInspectorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_con = DataAccess.getConnection();

		_dbInspector = new DBInspector(_con);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		DataAccess.cleanUp(_con);
	}

	@Test
	public void testHasColumn() throws Exception {
		Assert.isTrue(
			_dbInspector.hasColumn(
				_EXISTING_TABLE_NAME, _EXISTING_COLUMN_NAME));
	}

	@Test
	public void testHasColumnLowerCase() throws Exception {
		DatabaseMetaData databaseMetaData = _con.getMetaData();

		Assume.assumeTrue(databaseMetaData.storesLowerCaseIdentifiers());

		Assert.isTrue(
			_dbInspector.hasColumn(
				_EXISTING_TABLE_NAME,
				StringUtil.toLowerCase(_EXISTING_COLUMN_NAME)));
	}

	@Test
	public void testHasColumnUpperCase() throws Exception {
		DatabaseMetaData databaseMetaData = _con.getMetaData();

		Assume.assumeTrue(databaseMetaData.storesUpperCaseIdentifiers());

		Assert.isTrue(
			_dbInspector.hasColumn(
				_EXISTING_TABLE_NAME,
				StringUtil.toUpperCase(_EXISTING_COLUMN_NAME)));
	}

	@Test
	public void testHasNoColumn() throws Exception {
		Assert.isTrue(
			!_dbInspector.hasColumn(
				_EXISTING_TABLE_NAME, _NON_EXISTING_COLUMN_NAME));
	}

	@Test
	public void testHasNoTable() throws Exception {
		Assert.isTrue(!_dbInspector.hasTable(_NON_EXISTING_TABLE_NAME));
	}

	@Test
	public void testHasTable() throws Exception {
		Assert.isTrue(_dbInspector.hasTable(_EXISTING_TABLE_NAME));
	}

	@Test
	public void testHasTableLowerCase() throws Exception {
		DatabaseMetaData databaseMetaData = _con.getMetaData();

		Assume.assumeTrue(databaseMetaData.storesLowerCaseIdentifiers());

		Assert.isTrue(
			_dbInspector.hasTable(
				StringUtil.toLowerCase(_EXISTING_TABLE_NAME)));
	}

	@Test
	public void testHasTableUpperCase() throws Exception {
		DatabaseMetaData databaseMetaData = _con.getMetaData();

		Assume.assumeTrue(databaseMetaData.storesUpperCaseIdentifiers());

		Assert.isTrue(
			_dbInspector.hasTable(
				StringUtil.toUpperCase(_EXISTING_TABLE_NAME)));
	}

	private static final String _EXISTING_COLUMN_NAME = "releaseId";

	private static final String _EXISTING_TABLE_NAME = "Release_";

	private static final String _NON_EXISTING_COLUMN_NAME = "NonExistingColumn";

	private static final String _NON_EXISTING_TABLE_NAME = "NonExistingTable";

	private static Connection _con;
	private static DBInspector _dbInspector;

}