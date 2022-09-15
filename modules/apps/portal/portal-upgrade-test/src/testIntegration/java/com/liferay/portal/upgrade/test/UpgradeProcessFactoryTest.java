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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
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

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class UpgradeProcessFactoryTest {

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
				"create table ", _TABLE_NAME, " (id LONG not null primary ",
				"key, notNilColumn VARCHAR(75) not null, nilColumn ",
				"VARCHAR(75) null, typeBlob BLOB, typeBoolean BOOLEAN,",
				"typeDate DATE null, typeDouble DOUBLE, typeInteger INTEGER, ",
				"typeLong LONG null, typeSBlob SBLOB, typeString STRING null, ",
				"typeText TEXT null, typeVarchar VARCHAR(75) null);"));
	}

	@After
	public void tearDown() throws Exception {
		_db.runSQL("DROP_TABLE_IF_EXISTS(" + _TABLE_NAME + ")");
	}

	@Test
	public void testAddColumn() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeProcessFactory.addColumns(
			_TABLE_NAME, "newColumn LONG default 0 NOT NULL");

		upgradeProcess.upgrade();

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "newColumn", "LONG default 0 NOT NULL"));
	}

	@Test
	public void testAlterColumnName() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeProcessFactory.alterColumnName(
			_TABLE_NAME, "typeLong", "newTypeLong LONG null");

		upgradeProcess.upgrade();

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "newTypeLong", "LONG null"));
		Assert.assertFalse(_dbInspector.hasColumn(_TABLE_NAME, "typeLong"));
	}

	@Test
	public void testAlterColumnType() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeProcessFactory.alterColumnType(
			_TABLE_NAME, "typeText", "VARCHAR(250) null");

		upgradeProcess.upgrade();

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "typeText", "VARCHAR(250) null"));
		Assert.assertFalse(
			_dbInspector.hasColumnType(_TABLE_NAME, "typeText", "TEXT null"));
	}

	@Test
	public void testDropColumn() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeProcessFactory.dropColumns(
			_TABLE_NAME, "typeDate");

		upgradeProcess.upgrade();

		Assert.assertFalse(_dbInspector.hasColumn(_TABLE_NAME, "typeDate"));
	}

	@Test
	public void testUtilOnPostUgpradeSteps() throws Exception {
		UpgradeProcess upgradeProcess = new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
			}

			@Override
			protected UpgradeStep[] getPostUpgradeSteps() {
				return new UpgradeStep[] {
					UpgradeProcessFactory.addColumns(
						_TABLE_NAME, "newColumn LONG null"),
					UpgradeProcessFactory.dropColumns(_TABLE_NAME, "typeDate")
				};
			}

		};

		UpgradeStep[] upgradeSteps = upgradeProcess.getUpgradeSteps();

		for (UpgradeStep upgradeStep : upgradeSteps) {
			UpgradeProcess innerUpgradeProcess = (UpgradeProcess)upgradeStep;

			innerUpgradeProcess.upgrade();
		}

		Assert.assertTrue(
			_dbInspector.hasColumnType(_TABLE_NAME, "newColumn", "LONG null"));
		Assert.assertFalse(_dbInspector.hasColumn(_TABLE_NAME, "typeDate"));
	}

	@Test
	public void testUtilOnPreAndPostUgpradeSteps() throws Exception {
		UpgradeProcess upgradeProcess = new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
			}

			@Override
			protected UpgradeStep[] getPostUpgradeSteps() {
				return new UpgradeStep[] {
					UpgradeProcessFactory.alterColumnName(
						_TABLE_NAME, "newColumn", "newColumnModified LONG null")
				};
			}

			@Override
			protected UpgradeStep[] getPreUpgradeSteps() {
				return new UpgradeStep[] {
					UpgradeProcessFactory.addColumns(
						_TABLE_NAME, "newColumn LONG null"),
					UpgradeProcessFactory.dropColumns(_TABLE_NAME, "typeDate")
				};
			}

		};

		UpgradeStep[] upgradeSteps = upgradeProcess.getUpgradeSteps();

		for (UpgradeStep upgradeStep : upgradeSteps) {
			UpgradeProcess innerUpgradeProcess = (UpgradeProcess)upgradeStep;

			innerUpgradeProcess.upgrade();
		}

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_TABLE_NAME, "newColumnModified", "LONG null"));
		Assert.assertFalse(_dbInspector.hasColumn(_TABLE_NAME, "typeDate"));
	}

	@Test
	public void testUtilOnPreUgpradeSteps() throws Exception {
		UpgradeProcess upgradeProcess = new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
			}

			@Override
			protected UpgradeStep[] getPreUpgradeSteps() {
				return new UpgradeStep[] {
					UpgradeProcessFactory.addColumns(
						_TABLE_NAME, "newColumn LONG null"),
					UpgradeProcessFactory.dropColumns(_TABLE_NAME, "typeDate")
				};
			}

		};

		UpgradeStep[] upgradeSteps = upgradeProcess.getUpgradeSteps();

		for (UpgradeStep upgradeStep : upgradeSteps) {
			UpgradeProcess innerUpgradeProcess = (UpgradeProcess)upgradeStep;

			innerUpgradeProcess.upgrade();
		}

		Assert.assertTrue(
			_dbInspector.hasColumnType(_TABLE_NAME, "newColumn", "LONG null"));
		Assert.assertFalse(_dbInspector.hasColumn(_TABLE_NAME, "typeDate"));
	}

	private static final String _TABLE_NAME = "UpgradeProcessFactoryTest";

	private static Connection _connection;
	private static DB _db;
	private static DBInspector _dbInspector;

}