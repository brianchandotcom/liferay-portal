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
import com.liferay.portal.upgrade.live.LiveUpgradeExecutor;
import com.liferay.portal.upgrade.live.LiveUpgradeProcessFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
public class LiveUpgradeExecutorTest {

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
	public void testAddColumns() throws Exception {
		_liveUpgradeExecutor.upgrade(
			_TABLE_NAME,
			LiveUpgradeProcessFactory.addColumns(
				"version LONG null", "content SBLOB"));

		String tempTableName = _getTempTableName();

		Assert.assertTrue(_dbInspector.hasColumn(tempTableName, "version"));
		Assert.assertTrue(_dbInspector.hasColumn(tempTableName, "content"));
	}

	@Test
	public void testAlterColumnName() throws Exception {
		_liveUpgradeExecutor.upgrade(
			_TABLE_NAME,
			LiveUpgradeProcessFactory.alterColumnName(
				"name", "title VARCHAR(128) not null"));

		String tempTableName = _getTempTableName();

		Assert.assertFalse(_dbInspector.hasColumn(tempTableName, "name"));
		Assert.assertTrue(_dbInspector.hasColumn(tempTableName, "title"));

		_checkData(tempTableName, "title");
	}

	@Test
	public void testAlterColumnType() throws Exception {
		_liveUpgradeExecutor.upgrade(
			_TABLE_NAME,
			LiveUpgradeProcessFactory.alterColumnType(
				"name", "VARCHAR(255) null"));

		String tempTableName = _getTempTableName();

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				tempTableName, "name", "VARCHAR(255) null"));

		_checkData(tempTableName, "name");
	}

	@Test
	public void testDropColumns() throws Exception {
		_liveUpgradeExecutor.upgrade(
			_TABLE_NAME, LiveUpgradeProcessFactory.dropColumns("name"));

		Assert.assertFalse(_dbInspector.hasColumn(_getTempTableName(), "name"));
	}

	@Test
	public void testEmptyUpgrade() throws Exception {
		try {
			_liveUpgradeExecutor.upgrade(_TABLE_NAME);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"At least one live upgrade process is required",
				illegalArgumentException.getMessage());
		}
	}

	@Test
	public void testMultipleUpgrades() throws Exception {
		_liveUpgradeExecutor.upgrade(
			_TABLE_NAME,
			LiveUpgradeProcessFactory.addColumns(
				"version LONG null", "content SBLOB"),
			LiveUpgradeProcessFactory.alterColumnName(
				"name", "title VARCHAR(128) not null"),
			LiveUpgradeProcessFactory.alterColumnType(
				"title", "VARCHAR(255) null"),
			LiveUpgradeProcessFactory.dropColumns("content"));

		String tempTableName = _getTempTableName();

		Assert.assertFalse(_dbInspector.hasColumn(tempTableName, "name"));
		Assert.assertFalse(_dbInspector.hasColumn(tempTableName, "content"));

		Assert.assertTrue(_dbInspector.hasColumn(tempTableName, "id"));
		Assert.assertTrue(_dbInspector.hasColumn(tempTableName, "title"));
		Assert.assertTrue(_dbInspector.hasColumn(tempTableName, "version"));

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				tempTableName, "title", "VARCHAR(255) null"));

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select * from " + tempTableName + " order by id asc");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(1, resultSet.getLong("id"));
			Assert.assertEquals(0, resultSet.getLong("version"));
			Assert.assertEquals("test_a", resultSet.getString("title"));

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(2, resultSet.getLong("id"));
			Assert.assertEquals(0, resultSet.getLong("version"));
			Assert.assertEquals("test_b", resultSet.getString("title"));

			Assert.assertFalse(resultSet.next());
		}
	}

	private void _checkData(String tempTableName, String columnName)
		throws Exception {

		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select * from " + tempTableName + " order by id asc");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(1, resultSet.getLong("id"));
			Assert.assertEquals("test_a", resultSet.getString(columnName));

			Assert.assertTrue(resultSet.next());
			Assert.assertEquals(2, resultSet.getLong("id"));
			Assert.assertEquals("test_b", resultSet.getString(columnName));

			Assert.assertFalse(resultSet.next());
		}
	}

	private String _getTempTableName() {
		return ReflectionTestUtil.invoke(
			_liveUpgradeExecutor, "_getTempTableName",
			new Class<?>[] {String.class}, _TABLE_NAME);
	}

	private static final String _TABLE_NAME = "LiveUpgradeTest";

	private static Connection _connection;
	private static DB _db;
	private static DBInspector _dbInspector;

	@Inject
	private LiveUpgradeExecutor _liveUpgradeExecutor;

}