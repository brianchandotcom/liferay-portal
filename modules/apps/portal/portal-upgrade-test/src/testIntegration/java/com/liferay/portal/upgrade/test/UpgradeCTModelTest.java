/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class UpgradeCTModelTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(
			StringBundler.concat(
				"create table UpgradeCTModelTest (mvccVersion LONG default 0 ",
				"not null, uuid_ VARCHAR(75) null, upgradeCTModelId LONG not ",
				"null primary key, companyId LONG, createDate DATE null, ",
				"modifiedDate DATE null, name STRING null)"));

		_db.runSQL(
			"insert into UpgradeCTModelTest values (0, 'uuid', 1, 2, NULL, " +
				"NULL, 'name')");

		_db.runSQL(
			StringBundler.concat(
				"create table UpgradeCTModelMappingTest (companyId LONG not ",
				"null, leftId LONG not null, rightId LONG not null, primary ",
				"key (leftId, rightId))"));

		_db.runSQL("insert into UpgradeCTModelMappingTest values (1, 2, 3)");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("drop table UpgradeCTModelTest");

		_db.runSQL("drop table UpgradeCTModelMappingTest");
	}

	@Test
	public void testUpgradeCTModel() throws Exception {
		CTModelUpgradeProcess upgradeCTModel = new CTModelUpgradeProcess(
			"UpgradeCTModelTest");

		upgradeCTModel.upgrade();

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from UpgradeCTModelTest");
			ResultSet resultSet1 = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet1.next());

			Assert.assertEquals(0, resultSet1.getLong("mvccVersion"));
			Assert.assertEquals("uuid", resultSet1.getString("uuid_"));
			Assert.assertEquals(1, resultSet1.getLong("upgradeCTModelId"));
			Assert.assertEquals(0, resultSet1.getLong("ctCollectionId"));
			Assert.assertEquals(2, resultSet1.getLong("companyId"));
			Assert.assertNull(resultSet1.getTimestamp("createDate"));
			Assert.assertNull(resultSet1.getTimestamp("modifiedDate"));
			Assert.assertEquals("name", resultSet1.getString("name"));

			Assert.assertFalse(resultSet1.next());

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBInspector dbInspector = new DBInspector(connection);

			List<String> pkNames = new ArrayList<>();

			try (ResultSet resultSet2 = databaseMetaData.getPrimaryKeys(
					dbInspector.getCatalog(), dbInspector.getSchema(),
					dbInspector.normalizeName(
						"UpgradeCTModelTest", databaseMetaData))) {

				Assert.assertTrue("Missing PK", resultSet2.next());

				pkNames.add(
					StringUtil.toUpperCase(
						resultSet2.getString("COLUMN_NAME")));

				Assert.assertTrue("Missing PK", resultSet2.next());

				pkNames.add(
					StringUtil.toUpperCase(
						resultSet2.getString("COLUMN_NAME")));

				Assert.assertFalse(pkNames.toString(), resultSet2.next());
			}

			pkNames.sort(null);

			Assert.assertArrayEquals(
				new String[] {"CTCOLLECTIONID", "UPGRADECTMODELID"},
				pkNames.toArray(new String[0]));
		}
	}

	@Test
	public void testUpgradeCTModelMapping() throws Exception {
		CTModelUpgradeProcess upgradeCTModel = new CTModelUpgradeProcess(
			"UpgradeCTModelMappingTest");

		upgradeCTModel.upgrade();

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from UpgradeCTModelMappingTest");
			ResultSet resultSet1 = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet1.next());

			Assert.assertEquals(1, resultSet1.getLong("companyId"));
			Assert.assertEquals(2, resultSet1.getLong("leftId"));
			Assert.assertEquals(3, resultSet1.getLong("rightId"));
			Assert.assertEquals(0, resultSet1.getLong("ctCollectionId"));
			Assert.assertFalse(resultSet1.getBoolean("ctChangeType"));

			Assert.assertFalse(resultSet1.next());

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBInspector dbInspector = new DBInspector(connection);

			List<String> pkNames = new ArrayList<>();

			try (ResultSet resultSet2 = databaseMetaData.getPrimaryKeys(
					dbInspector.getCatalog(), dbInspector.getSchema(),
					dbInspector.normalizeName(
						"UpgradeCTModelMappingTest", databaseMetaData))) {

				Assert.assertTrue("Missing PK", resultSet2.next());

				pkNames.add(
					StringUtil.toUpperCase(
						resultSet2.getString("COLUMN_NAME")));

				Assert.assertTrue("Missing PK", resultSet2.next());

				pkNames.add(
					StringUtil.toUpperCase(
						resultSet2.getString("COLUMN_NAME")));

				Assert.assertTrue("Missing PK", resultSet2.next());

				pkNames.add(
					StringUtil.toUpperCase(
						resultSet2.getString("COLUMN_NAME")));

				Assert.assertFalse(pkNames.toString(), resultSet2.next());
			}

			pkNames.sort(null);

			Assert.assertArrayEquals(
				new String[] {"CTCOLLECTIONID", "LEFTID", "RIGHTID"},
				pkNames.toArray(new String[0]));
		}
	}

	private static DB _db;

}