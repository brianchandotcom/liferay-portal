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

package com.liferay.portal.upgrade.v7_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage;

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
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class UpgradeKernelPackageTest extends UpgradeKernelPackage {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(
			"create table UpgradeKernelPackageTest (" +
				"id LONG not null primary key, data VARCHAR(40) null, " +
					"textData TEXT null)");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("drop table UpgradeKernelPackageTest");
	}

	@Before
	public void setUp() throws Exception {
		connection = DataAccess.getConnection();
	}

	@After
	public void tearDown() {
		DataAccess.cleanUp(connection);

		connection = null;
	}

	@Test
	public void testDeprecatedUpgradeLongTextTable() throws Exception {
		try {
			upgradeLongTextTable(
				"UpgradeKernelPackageTest", "textData", _TEST_CLASS_NAMES,
				WildcardMode.SURROUND);

			Assert.fail("Should throw UnsupportedOperationException");
		}
		catch (UnsupportedOperationException uoe) {
			Assert.assertEquals(
				"This method is deprecated and replaced by " +
					"upgradeLongTextTable(String, String, String, " +
						"String[][], WildcardMode)",
				uoe.getMessage());
		}

		try {
			upgradeLongTextTable(
				"textData", "selectSQL", "updateSQL", _TEST_CLASS_NAMES[0]);

			Assert.fail("Should throw UnsupportedOperationException");
		}
		catch (UnsupportedOperationException uoe) {
			Assert.assertEquals(
				"This method is deprecated and replaced by " +
					"upgradeLongTextTable(String, String, String, String, " +
						"String[])",
				uoe.getMessage());
		}
	}

	@Test
	public void testDoUpgrade() throws Exception {

		// For code coverage

		doUpgrade();

		// Check that the table and column combination is correct

		DBInspector dbInspector = new DBInspector(connection);

		_assertTableAndColumn(dbInspector, "ClassName_", "value");
		_assertTableAndColumn(dbInspector, "Counter", "name");
		_assertTableAndColumn(dbInspector, "Lock_", "className");
		_assertTableAndColumn(dbInspector, "ResourceAction", "name");
		_assertTableAndColumn(dbInspector, "ResourceBlock", "name");
		_assertTableAndColumn(dbInspector, "ResourcePermission", "name");
		_assertTableAndColumn(dbInspector, "ListType", "type_");
		_assertTableAndColumn(
			dbInspector, "UserNotificationEvent", "payload",
			"userNotificationEventId");
	}

	@Test
	public void testUpgradeLongTextTable() throws Exception {
		try {

			// Test WildcardMode.LEADING

			_insertData(1, "", _PREFIX_CLASS_NAME_OLD);
			_insertData(2, "", _POSTFIX_CLASS_NAME_OLD);
			_insertData(3, "", _PREFIX_POSTFIX_CLASS_NAME_OLD);

			upgradeLongTextTable(
				"UpgradeKernelPackageTest", "textData", "id", _TEST_CLASS_NAMES,
				WildcardMode.LEADING);

			_assertData(1, "textData", _PREFIX_CLASS_NAME_NEW);
			_assertData(2, "textData", _POSTFIX_CLASS_NAME_OLD);
			_assertData(3, "textData", _PREFIX_POSTFIX_CLASS_NAME_OLD);

			// Test WildcardMode.TRAILING

			_insertData(4, "", _PREFIX_CLASS_NAME_OLD);
			_insertData(5, "", _POSTFIX_CLASS_NAME_OLD);
			_insertData(6, "", _PREFIX_POSTFIX_CLASS_NAME_OLD);

			upgradeLongTextTable(
				"UpgradeKernelPackageTest", "textData", "id", _TEST_CLASS_NAMES,
				WildcardMode.TRAILING);

			_assertData(4, "textData", _PREFIX_CLASS_NAME_OLD);
			_assertData(5, "textData", _POSTFIX_CLASS_NAME_NEW);
			_assertData(6, "textData", _PREFIX_POSTFIX_CLASS_NAME_OLD);

			// Test WildcardMode.SURROUND

			_insertData(7, "", _PREFIX_CLASS_NAME_OLD);
			_insertData(8, "", _POSTFIX_CLASS_NAME_OLD);
			_insertData(9, "", _PREFIX_POSTFIX_CLASS_NAME_OLD);

			upgradeLongTextTable(
				"UpgradeKernelPackageTest", "textData", "id", _TEST_CLASS_NAMES,
				WildcardMode.SURROUND);

			_assertData(7, "textData", _PREFIX_CLASS_NAME_NEW);
			_assertData(8, "textData", _POSTFIX_CLASS_NAME_NEW);
			_assertData(9, "textData", _PREFIX_POSTFIX_CLASS_NAME_NEW);
		}
		finally {
			runSQL("delete from UpgradeKernelPackageTest");
		}
	}

	@Test
	public void testUpgradeLongTextTableWithSelectAndUpdateSQL()
		throws Exception {

		try {
			_insertData(1, "", _PREFIX_CLASS_NAME_OLD);
			_insertData(2, "", _POSTFIX_CLASS_NAME_OLD);
			_insertData(3, "", _PREFIX_POSTFIX_CLASS_NAME_OLD);
			_insertData(4, "", "NOT_CLASS_NAME_OLD");

			upgradeLongTextTable(
				"textData", "id",
				"select textData, id from UpgradeKernelPackageTest where " +
					"textData like '%" + _CLASS_NAME_OLD + "%'",
				"update UpgradeKernelPackageTest set textData = ? where id = ?",
				_TEST_CLASS_NAMES[0]);

			_assertData(1, "textData", _PREFIX_CLASS_NAME_NEW);
			_assertData(2, "textData", _POSTFIX_CLASS_NAME_NEW);
			_assertData(3, "textData", _PREFIX_POSTFIX_CLASS_NAME_NEW);
			_assertData(4, "textData", "NOT_CLASS_NAME_OLD");
		}
		finally {
			runSQL("delete from UpgradeKernelPackageTest");
		}
	}

	@Test
	public void testUpgradeTable() throws Exception {
		try {

			// Test WildcardMode.LEADING

			_insertData(1, _PREFIX_CLASS_NAME_OLD, "");
			_insertData(2, _POSTFIX_CLASS_NAME_OLD, "");
			_insertData(3, _PREFIX_POSTFIX_CLASS_NAME_OLD, "");

			upgradeTable(
				"UpgradeKernelPackageTest", "data", _TEST_CLASS_NAMES,
				WildcardMode.LEADING);

			_assertData(1, "data", _PREFIX_CLASS_NAME_NEW);
			_assertData(2, "data", _POSTFIX_CLASS_NAME_OLD);
			_assertData(3, "data", _PREFIX_POSTFIX_CLASS_NAME_OLD);

			// Test WildcardMode.TRAILING

			_insertData(4, _PREFIX_CLASS_NAME_OLD, "");
			_insertData(5, _POSTFIX_CLASS_NAME_OLD, "");
			_insertData(6, _PREFIX_POSTFIX_CLASS_NAME_OLD, "");

			upgradeTable(
				"UpgradeKernelPackageTest", "data", _TEST_CLASS_NAMES,
				WildcardMode.TRAILING);

			_assertData(4, "data", _PREFIX_CLASS_NAME_OLD);
			_assertData(5, "data", _POSTFIX_CLASS_NAME_NEW);
			_assertData(6, "data", _PREFIX_POSTFIX_CLASS_NAME_OLD);

			// Test WildcardMode.SURROUND

			_insertData(7, _PREFIX_CLASS_NAME_OLD, "");
			_insertData(8, _POSTFIX_CLASS_NAME_OLD, "");
			_insertData(9, _PREFIX_POSTFIX_CLASS_NAME_OLD, "");

			upgradeTable(
				"UpgradeKernelPackageTest", "data", _TEST_CLASS_NAMES,
				WildcardMode.SURROUND);

			_assertData(7, "data", _PREFIX_CLASS_NAME_NEW);
			_assertData(8, "data", _POSTFIX_CLASS_NAME_NEW);
			_assertData(9, "data", _PREFIX_POSTFIX_CLASS_NAME_NEW);

			// Test preventDuplicates

			_insertData(10, _PREFIX_POSTFIX_CLASS_NAME_OLD, "");
			_insertData(11, _PREFIX_POSTFIX_CLASS_NAME_NEW, "");

			upgradeTable(
				"UpgradeKernelPackageTest", "data", _TEST_CLASS_NAMES,
				WildcardMode.SURROUND, true);

			_assertData(10, "data", _PREFIX_POSTFIX_CLASS_NAME_NEW);
			_assertData(11, "data", null);
		}
		finally {
			runSQL("delete from UpgradeKernelPackageTest");
		}
	}

	@Override
	protected String[][] getClassNames() {
		return new String[0][0];
	}

	@Override
	protected String[][] getResourceNames() {
		return new String[0][0];
	}

	private void _assertData(long id, String columnName, String expectedValue)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("select ");
		sb.append(columnName);
		sb.append(" from UpgradeKernelPackageTest where id =");
		sb.append(id);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(sb.toString()));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (expectedValue == null) {
				Assert.assertFalse(
					"Entry with id " + id + "should not exsit",
					resultSet.next());
			}
			else {
				Assert.assertTrue(
					"Entry with id " + id + " should exist", resultSet.next());

				Assert.assertEquals(
					expectedValue, resultSet.getString(columnName));
			}
		}
	}

	private void _assertTableAndColumn(
			DBInspector dbInspector, String tableName, String... columnNames)
		throws Exception {

		Assert.assertTrue(
			StringBundler.concat("Table \"", tableName, "\" does not exist"),
			dbInspector.hasTable(tableName));

		for (String columnName : columnNames) {
			Assert.assertTrue(
				StringBundler.concat(
					"Table \"", tableName, "\" does not have column \"",
					columnName, "\""),
				dbInspector.hasColumn(tableName, columnName));
		}
	}

	private void _insertData(long id, String data, String textData)
		throws Exception {

		StringBundler sb = new StringBundler(7);

		sb.append("insert into UpgradeKernelPackageTest values(");
		sb.append(id);
		sb.append(", '");
		sb.append(data);
		sb.append("', '");
		sb.append(textData);
		sb.append("')");

		runSQL(sb.toString());
	}

	private static final String _CLASS_NAME_NEW = "UPDATED_CLASS_NAME";

	private static final String _CLASS_NAME_OLD = "ORIGINAL_CLASS_NAME";

	private static final String _POSTFIX_CLASS_NAME_NEW =
		_CLASS_NAME_NEW + "_POSTFIX";

	private static final String _POSTFIX_CLASS_NAME_OLD =
		_CLASS_NAME_OLD + "_POSTFIX";

	private static final String _PREFIX_CLASS_NAME_NEW =
		"PREFIX_" + _CLASS_NAME_NEW;

	private static final String _PREFIX_CLASS_NAME_OLD =
		"PREFIX_" + _CLASS_NAME_OLD;

	private static final String _PREFIX_POSTFIX_CLASS_NAME_NEW =
		"PREFIX_" + _CLASS_NAME_NEW + "_POSTFIX";

	private static final String _PREFIX_POSTFIX_CLASS_NAME_OLD =
		"PREFIX_" + _CLASS_NAME_OLD + "_POSTFIX";

	private static final String[][] _TEST_CLASS_NAMES = {
		{_CLASS_NAME_OLD, _CLASS_NAME_NEW}
	};

	private static DB _db;

}