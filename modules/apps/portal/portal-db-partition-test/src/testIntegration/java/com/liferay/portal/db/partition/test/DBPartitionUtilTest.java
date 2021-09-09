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

package com.liferay.portal.db.partition.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnection;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class DBPartitionUtilTest extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		enableDBPartition();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		disableDBPartition();
	}

	@Before
	public void setUp() throws Exception {
		db.runSQL(
			"create schema if not exists " + getSchemaName(COMPANY_ID) +
				" character set utf8");
	}

	@After
	public void tearDown() throws Exception {
		dropSchema();
	}

	@Test
	public void testAccessCompanyByCompanyThreadLocal() throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setInitializingCompanyIdWithSafeCloseable(
					COMPANY_ID);
			Connection connection = DataAccess.getConnection();
			Statement statement = connection.createStatement()) {

			createAndPopulateTable(TEST_TABLE_NAME);

			statement.execute("select 1 from " + TEST_TABLE_NAME);
		}
	}

	@Test
	public void testAccessDefaultCompanyByCompanyThreadLocal()
		throws SQLException {

		long currentCompanyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(portal.getDefaultCompanyId());

		try (Connection connection = DataAccess.getConnection();
			Statement statement = connection.createStatement()) {

			statement.execute("select 1 from CompanyInfo");
		}
		finally {
			CompanyThreadLocal.setCompanyId(currentCompanyId);
		}
	}

	@Test
	public void testAddDBPartition() throws Exception {
		addDBPartition();

		try (Statement statement = connection.createStatement()) {
			statement.execute(
				"select 1 from " + getSchemaName(COMPANY_ID) + ".CompanyInfo");
		}
	}

	@Test
	public void testAddDefaultDBPartition() throws PortalException {
		Assert.assertFalse(
			DBPartitionUtil.addDBPartition(portal.getDefaultCompanyId()));
	}

	@Test
	public void testForEachCompanyId() throws Exception {
		CompanyThreadLocal.setCompanyId(CompanyConstants.SYSTEM);

		DBPartitionUtil.forEachCompanyId(
			companyId -> Assert.assertEquals(
				companyId, CompanyThreadLocal.getCompanyId()));
	}

	@Test
	public void testRemoveDBPartition() throws Exception {
		addDBPartition();

		List<String> viewNames = _getObjectNames("VIEW");

		Assert.assertNotEquals(0, viewNames.size());

		int tablesCount = _getTablesCount();

		_removeDBPartition();

		Assert.assertEquals(tablesCount + viewNames.size(), _getTablesCount());
		Assert.assertEquals(0, _getViewsCount());

		for (String viewName : viewNames) {
			Assert.assertEquals(
				viewName + " count", _getCount(viewName, true),
				_getCount(viewName, false));
		}
	}

	@Test
	public void testRemoveDBPartitionRollback() throws Exception {
		addDBPartition();

		int tablesCount = _getTablesCount();
		int viewsCount = _getViewsCount();

		String fullTestTableName =
			getSchemaName(COMPANY_ID) + "." + TEST_CONTROL_TABLE_NAME;

		try {
			createAndPopulateControlTable(TEST_CONTROL_TABLE_NAME);
			createAndPopulateControlTable(fullTestTableName);

			try {
				_removeDBPartition();

				Assert.fail("Should throw an exception");
			}
			catch (Exception exception) {
				Assert.assertEquals(tablesCount, _getTablesCount());
				Assert.assertEquals(viewsCount, _getViewsCount() - 1);
			}
		}
		finally {
			dropTable(TEST_CONTROL_TABLE_NAME);
		}
	}

	private int _getCount(String tableName, boolean defaultSchema)
		throws Exception {

		String whereClause = StringPool.BLANK;

		if (dbInspector.hasColumn(tableName, "companyId")) {
			whereClause = " where companyId = " + COMPANY_ID;
		}

		String fullTableName = tableName;

		if (!defaultSchema) {
			fullTableName =
				getSchemaName(COMPANY_ID) + StringPool.PERIOD + tableName;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(1) from " + fullTableName + whereClause);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		}

		throw new Exception("Table does not exist");
	}

	private List<String> _getObjectNames(String objectType) throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		List<String> objectNames = new ArrayList<>();

		try (ResultSet resultSet = databaseMetaData.getTables(
				getSchemaName(COMPANY_ID), dbInspector.getSchema(), null,
				new String[] {objectType})) {

			while (resultSet.next()) {
				objectNames.add(resultSet.getString("TABLE_NAME"));
			}
		}

		return objectNames;
	}

	private int _getTablesCount() throws Exception {
		List<String> tableNames = _getObjectNames("TABLE");

		return tableNames.size();
	}

	private int _getViewsCount() throws Exception {
		List<String> viewNames = _getObjectNames("VIEW");

		return viewNames.size();
	}

	private void _removeDBPartition() throws Exception {
		CurrentConnection defaultCurrentConnection =
			CurrentConnectionUtil.getCurrentConnection();

		try {
			CurrentConnection currentConnection = dataSource -> connection;

			ReflectionTestUtil.setFieldValue(
				CurrentConnectionUtil.class, "_currentConnection",
				currentConnection);

			DBPartitionUtil.removeDBPartition(COMPANY_ID);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				CurrentConnectionUtil.class, "_currentConnection",
				defaultCurrentConnection);
		}
	}

}