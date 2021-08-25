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

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Mariano Álvaro Sáiz
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class UpgradeProcessTest {

	@Before
	public void setUp() {
		_mockPropsUtil();
	}

	public static final String TABLE_NAME = "UpgradeProcessTest";

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_upgradeProcess = new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
			}

		};

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
		_db.runSQL(
			StringBundler.concat(
				"create table ", TABLE_NAME, " (id LONG not null primary key, ",
				"typeVarchar VARCHAR(75) not null);"));
	}

	@After
	public void tearDown() throws Exception {
		_db.runSQL("drop table " + TABLE_NAME);
	}

	@Test
	public void testDeprecatedAlterColumnName() throws Exception {
		_upgradeProcess.alter(
			getClass(),
			_upgradeProcess.new AlterColumnName(
				"typeVarchar", "typeVarcharTest"));

		Assert.assertTrue(
			_dbInspector.hasColumn(TABLE_NAME, "typeVarcharTest"));
	}

	@Test
	public void testDeprecatedAlterColumnType() throws Exception {
		_upgradeProcess.alter(
			getClass(),
			_upgradeProcess.new AlterColumnType("typeVarchar", "LONG null"));

		Assert.assertTrue(
			_dbInspector.hasColumnType(TABLE_NAME, "typeVarchar", "LONG null"));
	}

	@Test
	public void testDeprecatedAlterTableAddColumn() throws Exception {
		_upgradeProcess.alter(
			getClass(),
			_upgradeProcess.new AlterTableAddColumn(
				"testColumn", "STRING null"));

		Assert.assertTrue(_dbInspector.hasColumn(TABLE_NAME, "testColumn"));
	}

	@Test
	public void testDeprecatedAlterTableDropColumn() throws Exception {
		_upgradeProcess.alter(
			getClass(),
			_upgradeProcess.new AlterTableDropColumn("typeVarchar"));

		Assert.assertFalse(_dbInspector.hasColumn(TABLE_NAME, "typeVarchar"));
	}

	private void _mockPropsUtil() {
		Props props = PowerMockito.mock(Props.class);

		Mockito.when(
			props.get(PropsKeys.UPGRADE_CONCURRENT_PROCESS_FUTURE_LIST_MAX_SIZE)
		).thenReturn(
			_UPGRADE_CONCURRENT_PROCESS_FUTURE_LIST_MAX_SIZE_DEFAULT_VALUE
		);

		Mockito.when(
			props.get(PropsKeys.UPGRADE_CONCURRENT_FETCH_SIZE)
		).thenReturn(
			_UPGRADE_CONCURRENT_FETCH_SIZE_DEFAULT_VALUE
		);

		PropsUtil.setProps(props);
	}

	private static final String _NEW_COLUMN =
		UpgradeProcessTest._NEW_COLUMN_NAME + " VARCHAR2(30) NOT NULL";

	private static final String _NEW_COLUMN_NAME = "newColumnName";

	private static final String _NEW_COLUMN_NAME_NOT_IN_INDEX =
		"newNotIndexColumn";

	private static final String _OLD_COLUMN_NAME = "oldColumnName";

	private static final String _OLD_COLUMN_NAME_NOT_IN_INDEX =
		"oldNotIndexColumn";

	private static final String _TABLE_NAME = "Table";

	private static final String _UPGRADE_CONCURRENT_FETCH_SIZE_DEFAULT_VALUE =
		"1000";

	private static final String
		_UPGRADE_CONCURRENT_PROCESS_FUTURE_LIST_MAX_SIZE_DEFAULT_VALUE = "1000";

	private static final List<String> _newIndexColumnNames = Arrays.asList(
		"newColumn1", _NEW_COLUMN_NAME, "newColumn2");
	private static final List<String> _newIndexColumnsNamesLowerCase =
		Arrays.asList(StringUtil.toLowerCase(_NEW_COLUMN_NAME));
	private static final List<String> _oldIndexColumnNames = Arrays.asList(
		"oldColumn1", _OLD_COLUMN_NAME, "oldColumn3");
	private static final List<String> _oldIndexColumnsNamesLowercase =
		Arrays.asList(StringUtil.toLowerCase(_OLD_COLUMN_NAME));

	private UpgradeProcess _upgradeProcess;

	private static Connection _connection;
	private static DB _db;
	private static DBInspector _dbInspector;
	private static UpgradeProcess _upgradeProcess;

}