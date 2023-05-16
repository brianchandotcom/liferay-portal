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

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.online.OnlineUpgradeExecutor;
import com.liferay.portal.upgrade.online.OnlineUpgradeStepFactory;

import java.sql.Connection;

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
 * @author Kevin Lee
 */
@RunWith(Arquillian.class)
public class OnlineUpgradeExecutorTest {

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
	public static void tearDownClass() {
		DataAccess.cleanUp(_connection);
	}

	@Before
	public void setUp() throws Exception {
		_db.runSQL(
			StringBundler.concat(
				"create table ", _TABLE_NAME,
				" (id LONG not null primary key, name VARCHAR(128) not null)"));
		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME,
				" (id, name) values (1, 'test_a')"));
		_db.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME,
				" (id, name) values (2, 'test_b')"));
	}

	@After
	public void tearDown() throws Exception {
		_db.runSQL("DROP_TABLE_IF_EXISTS(" + _TABLE_NAME + ")");
		_db.runSQL("DROP_TABLE_IF_EXISTS(" + _getTempTableName() + ")");
	}

	@Test
	public void testAlterColumnName() throws Exception {
		_onlineUpgradeExecutor.upgrade(
			_TABLE_NAME,
			OnlineUpgradeStepFactory.alterColumnName(
				"name", "title VARCHAR(128) not null"));

		String tempTableName = _getTempTableName();

		Assert.assertFalse(_dbInspector.hasColumn(tempTableName, "name"));
		Assert.assertTrue(_dbInspector.hasColumn(tempTableName, "title"));
	}

	@Test
	public void testAlterColumnType() throws Exception {
		_onlineUpgradeExecutor.upgrade(
			_TABLE_NAME,
			OnlineUpgradeStepFactory.alterColumnType(
				"name", "VARCHAR(255) null"));

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_getTempTableName(), "name", "VARCHAR(255) null"));
	}

	@Test
	public void testEmptyUpgrade() throws Exception {
		try {
			_onlineUpgradeExecutor.upgrade(_TABLE_NAME);

			Assert.fail();
		}
		catch (Exception exception) {
			if (!(exception instanceof IllegalArgumentException)) {
				Assert.fail();
			}
		}
	}

	@Test
	public void testMultipleUpgrades() throws Exception {
		_onlineUpgradeExecutor.upgrade(
			_TABLE_NAME,
			OnlineUpgradeStepFactory.alterColumnName(
				"name", "title VARCHAR(128) not null"),
			OnlineUpgradeStepFactory.alterColumnType(
				"title", "VARCHAR(255) null"));

		String tempTableName = _getTempTableName();

		Assert.assertFalse(_dbInspector.hasColumn(tempTableName, "name"));
		Assert.assertTrue(_dbInspector.hasColumn(tempTableName, "title"));
		Assert.assertTrue(
			_dbInspector.hasColumnType(
				tempTableName, "title", "VARCHAR(255) null"));
	}

	private String _getTempTableName() {
		return ReflectionTestUtil.invoke(
			_onlineUpgradeExecutor, "_getTempTableName",
			new Class<?>[] {String.class}, _TABLE_NAME);
	}

	private static final String _TABLE_NAME = "OnlineUpgradeTest";

	private static Connection _connection;
	private static DB _db;
	private static DBInspector _dbInspector;

	@Inject
	private OnlineUpgradeExecutor _onlineUpgradeExecutor;

}