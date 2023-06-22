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
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.live.LiveUpgradeSchemaDiff;

import java.sql.Connection;

import java.util.Map;

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
public class LiveUpgradeSchemaDiffTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_db = DBManagerUtil.getDB();

		_db.runSQL(
			StringBundler.concat(
				"create table ", _TABLE_NAME,
				" (id LONG not null primary key, name VARCHAR(128) not null)"));
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("DROP_TABLE_IF_EXISTS(" + _TABLE_NAME + ")");

		DataAccess.cleanUp(_connection);
	}

	@Before
	public void setUp() throws Exception {
		_liveUpgradeSchemaDiff = new LiveUpgradeSchemaDiff(
			_connection, _TABLE_NAME);
	}

	@Test
	public void testRecordAddColumns() throws Exception {
		_liveUpgradeSchemaDiff.recordAddColumns(
			"version LONG default 0 not null");

		_checkResultColumnNamesMap(
			HashMapBuilder.put(
				"id", "id"
			).put(
				"name", "name"
			).build());
	}

	@Test
	public void testRecordAlterColumnName() throws Exception {
		_liveUpgradeSchemaDiff.recordAlterColumnName(
			"name", "title VARCHAR(128) not null");

		_checkResultColumnNamesMap(
			HashMapBuilder.put(
				"id", "id"
			).put(
				"name", "title"
			).build());
	}

	@Test
	public void testRecordAlterColumnType() throws Exception {
		_liveUpgradeSchemaDiff.recordAlterColumnName(
			"name", "VARCHAR(255) null");

		_checkResultColumnNamesMap(
			HashMapBuilder.put(
				"id", "id"
			).put(
				"name", "name"
			).build());

		// TODO: Check type

	}

	@Test
	public void testRecordDropColumns() throws Exception {
		_liveUpgradeSchemaDiff.recordDropColumns("name");

		_checkResultColumnNamesMap(
			HashMapBuilder.put(
				"id", "id"
			).build());
	}

	private void _checkResultColumnNamesMap(
		Map<String, String> expectedColumnNamesMap) {

		Map<String, String> actualColumnNamesMap =
			_liveUpgradeSchemaDiff.getResultColumnNamesMap();

		Assert.assertEquals(
			actualColumnNamesMap.toString(), expectedColumnNamesMap.size(),
			actualColumnNamesMap.size());

		for (Map.Entry<String, String> entry :
				actualColumnNamesMap.entrySet()) {

			Assert.assertTrue(
				expectedColumnNamesMap.containsKey(entry.getKey()));
			Assert.assertEquals(
				expectedColumnNamesMap.get(entry.getKey()), entry.getValue());
		}
	}

	private static final String _TABLE_NAME = "LiveUpgradeSchemaTest";

	private static Connection _connection;
	private static DB _db;

	private LiveUpgradeSchemaDiff _liveUpgradeSchemaDiff;

}