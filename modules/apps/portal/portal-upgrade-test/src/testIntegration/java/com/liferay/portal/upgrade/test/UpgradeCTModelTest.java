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
import com.liferay.portal.kernel.upgrade.UpgradeCTModel;
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
				"modifiedDate DATE null, name STRING null);"));

		_db.runSQL(
			"insert into UpgradeCTModelTest values (0, 'uuid', 1, 2, NULL, " +
				"NULL, 'name');");

		_db.runSQL(
			StringBundler.concat(
				"create table UpgradeCTModelMappingTest (companyId LONG not ",
				"null, leftId LONG not null, rightId LONG not null, primary ",
				"key (leftId, rightId));"));

		_db.runSQL("insert into UpgradeCTModelMappingTest values (1, 2, 3);");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("drop table UpgradeCTModelTest");

		_db.runSQL("drop table UpgradeCTModelMappingTest");
	}

	@Test
	public void testUpgradeCTModel() throws Exception {
		UpgradeCTModel upgradeCTModel = new UpgradeCTModel(
			"UpgradeCTModelTest");

		upgradeCTModel.upgrade();

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement ps = connection.prepareStatement(
				"select * from UpgradeCTModelTest");
			ResultSet rs1 = ps.executeQuery()) {

			Assert.assertTrue(rs1.next());

			Assert.assertEquals(0, rs1.getLong("mvccVersion"));
			Assert.assertEquals("uuid", rs1.getString("uuid_"));
			Assert.assertEquals(1, rs1.getLong("upgradeCTModelId"));
			Assert.assertEquals(0, rs1.getLong("ctCollectionId"));
			Assert.assertEquals(2, rs1.getLong("companyId"));
			Assert.assertNull(rs1.getTimestamp("createDate"));
			Assert.assertNull(rs1.getTimestamp("modifiedDate"));
			Assert.assertEquals("name", rs1.getString("name"));

			Assert.assertFalse(rs1.next());

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBInspector dbInspector = new DBInspector(connection);

			List<String> pkNames = new ArrayList<>();

			try (ResultSet rs2 = databaseMetaData.getPrimaryKeys(
					dbInspector.getCatalog(), dbInspector.getSchema(),
					dbInspector.normalizeName(
						"UpgradeCTModelTest", databaseMetaData))) {

				Assert.assertTrue("Missing PK", rs2.next());

				pkNames.add(
					StringUtil.toUpperCase(rs2.getString("COLUMN_NAME")));

				Assert.assertTrue("Missing PK", rs2.next());

				pkNames.add(
					StringUtil.toUpperCase(rs2.getString("COLUMN_NAME")));

				Assert.assertFalse(pkNames.toString(), rs2.next());
			}

			pkNames.sort(null);

			Assert.assertArrayEquals(
				new String[] {"CTCOLLECTIONID", "UPGRADECTMODELID"},
				pkNames.toArray(new String[0]));
		}
	}

	@Test
	public void testUpgradeCTModelMapping() throws Exception {
		UpgradeCTModel upgradeCTModel = new UpgradeCTModel(
			"UpgradeCTModelMappingTest");

		upgradeCTModel.upgrade();

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement ps = connection.prepareStatement(
				"select * from UpgradeCTModelMappingTest");
			ResultSet rs1 = ps.executeQuery()) {

			Assert.assertTrue(rs1.next());

			Assert.assertEquals(1, rs1.getLong("companyId"));
			Assert.assertEquals(2, rs1.getLong("leftId"));
			Assert.assertEquals(3, rs1.getLong("rightId"));
			Assert.assertEquals(0, rs1.getLong("ctCollectionId"));
			Assert.assertFalse(rs1.getBoolean("ctChangeType"));

			Assert.assertFalse(rs1.next());

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBInspector dbInspector = new DBInspector(connection);

			List<String> pkNames = new ArrayList<>();

			try (ResultSet rs2 = databaseMetaData.getPrimaryKeys(
					dbInspector.getCatalog(), dbInspector.getSchema(),
					dbInspector.normalizeName(
						"UpgradeCTModelMappingTest", databaseMetaData))) {

				Assert.assertTrue("Missing PK", rs2.next());

				pkNames.add(
					StringUtil.toUpperCase(rs2.getString("COLUMN_NAME")));

				Assert.assertTrue("Missing PK", rs2.next());

				pkNames.add(
					StringUtil.toUpperCase(rs2.getString("COLUMN_NAME")));

				Assert.assertTrue("Missing PK", rs2.next());

				pkNames.add(
					StringUtil.toUpperCase(rs2.getString("COLUMN_NAME")));

				Assert.assertFalse(pkNames.toString(), rs2.next());
			}

			pkNames.sort(null);

			Assert.assertArrayEquals(
				new String[] {"CTCOLLECTIONID", "LEFTID", "RIGHTID"},
				pkNames.toArray(new String[0]));
		}
	}

	private static DB _db;

}