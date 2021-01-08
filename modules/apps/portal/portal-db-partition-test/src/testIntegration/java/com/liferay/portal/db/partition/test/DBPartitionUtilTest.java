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
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.init.DBInitUtil;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnection;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.test.rule.Inject;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class DBPartitionUtilTest extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dbPartitionEnabled = GetterUtil.getBoolean(
			_props.get("database.partition.enabled"));

		_lazyConnectionDataSourceProxy =
			(LazyConnectionDataSourceProxy)PortalBeanLocatorUtil.locate(
				"liferayDataSource");

		_connection = DataAccess.getConnection();

		_defaultSchemaName = _connection.getCatalog();

		_dbInspector = new DBInspector(_connection);

		_enableDBPartition();

		db.runSQL(
			"create schema " + _getSchemaName(_COMPANY_ID) +
				" character set utf8");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		db.runSQL("drop schema " + _getSchemaName(_COMPANY_ID));

		DataAccess.cleanUp(_connection);

		_disableDBPartition();
	}

	@After
	public void tearDown() throws SQLException {
		try (Statement statement = _connection.createStatement()) {
			statement.execute("use " + _defaultSchemaName);
		}
	}

	@Test
	public void testAccessCompanyByCompanyThreadLocal() throws SQLException {
		try (SafeClosable safeClosable =
				CompanyThreadLocal.setInitializingCompanyId(_COMPANY_ID);
			Connection connection = DataAccess.getConnection();
			Statement statement = connection.createStatement()) {

			statement.executeUpdate(
				StringBundler.concat(
					"create table ", _getSchemaName(_COMPANY_ID),
					".TestTable (testColumn int)"));

			statement.execute("select 1 from TestTable");

			statement.execute("use " + _defaultSchemaName);
		}
	}

	@Test
	public void testAccessDefaultCompanyByCompanyThreadLocal()
		throws SQLException {

		long currentCompanyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(_portal.getDefaultCompanyId());

		try (Connection connection = DataAccess.getConnection();
			Statement statement = connection.createStatement()) {

			statement.execute("select 1 from CompanyInfo");

			statement.execute("use " + _defaultSchemaName);
		}
		finally {
			CompanyThreadLocal.setCompanyId(currentCompanyId);
		}
	}

	@Test
	public void testAddDBPartition() throws Exception {
		_addDBPartition();

		try (Statement statement = _connection.createStatement()) {
			statement.execute(
				"select 1 from " + _getSchemaName(_COMPANY_ID) +
					".CompanyInfo");
		}
	}

	@Test
	public void testAddDefaultDBPartition() throws PortalException {
		Assert.assertFalse(
			DBPartitionUtil.addDBPartition(_portal.getDefaultCompanyId()));
	}

	@Test
	public void testRemoveDBPartition() throws Exception {
		_addDBPartition();

		List<String> initialViewNames = _getObjectNames("VIEW");

		int initialViewCount = initialViewNames.size();

		Assert.assertNotEquals(0, initialViewCount);

		int initialTableCount = _getTablesCount();

		_removeDBPartition();

		int finalTableCount = _getTablesCount();
		int finalViewCount = _getViewCount();

		Assert.assertEquals(
			initialTableCount + initialViewCount, finalTableCount);

		Assert.assertEquals(0, finalViewCount);

		for (String initialViewName : initialViewNames) {
			Assert.assertEquals(
				initialViewName + " count", _getCount(initialViewName, true),
				_getCount(initialViewName, false));
		}
	}

	@Test
	public void testRollbackRemoveDBPartition() throws Exception {
		_addDBPartition();

		int initialViewCount = _getViewCount();
		int initialTableCount = _getTablesCount();

		String fullTestTableName =
			_getSchemaName(_COMPANY_ID) + StringPool.PERIOD + "test";

		try (Statement statement = _connection.createStatement()) {
			statement.execute("create table test (test varchar(1))");
			statement.execute(
				"create table " + fullTestTableName + "(test varchar(1))");

			try {
				_removeDBPartition();

				Assert.fail("Should throw an exception");
			}
			catch (Exception exception) {
				Assert.assertEquals(initialViewCount, _getViewCount() - 1);
				Assert.assertEquals(initialTableCount, _getTablesCount());
			}
		}
		finally {
			try (Statement statement = _connection.createStatement()) {
				statement.execute("drop table if exists test");
				statement.execute("drop table if exists " + fullTestTableName);
			}
		}
	}

	private static void _disableDBPartition() {
		if (_dbPartitionEnabled) {
			return;
		}

		ReflectionTestUtil.setFieldValue(
			DBInitUtil.class, "_dataSource", _currentDataSource);
		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_ENABLED", false);
		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_SCHEMA_NAME_PREFIX",
			StringPool.BLANK);

		_lazyConnectionDataSourceProxy.setTargetDataSource(_currentDataSource);

		ReflectionTestUtil.setFieldValue(
			InfrastructureUtil.class, "_dataSource",
			_lazyConnectionDataSourceProxy);
	}

	private static void _enableDBPartition() throws Exception {
		if (_dbPartitionEnabled) {
			return;
		}

		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_ENABLED", true);

		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_SCHEMA_NAME_PREFIX",
			_DB_PARTITION_SCHEMA_NAME_PREFIX);

		DBPartitionUtil.setDefaultCompanyId(PortalUtil.getDefaultCompanyId());

		DataSource dbPartitionDataSource = DBPartitionUtil.wrapDataSource(
			_currentDataSource);

		_lazyConnectionDataSourceProxy.setTargetDataSource(
			dbPartitionDataSource);

		ReflectionTestUtil.setFieldValue(
			DBInitUtil.class, "_dataSource", dbPartitionDataSource);

		ReflectionTestUtil.setFieldValue(
			InfrastructureUtil.class, "_dataSource",
			_lazyConnectionDataSourceProxy);
	}

	private static String _getSchemaName(long companyId) {
		if (_dbPartitionEnabled) {
			return (String)ReflectionTestUtil.getFieldValue(
				DBPartitionUtil.class,
				"_DATABASE_PARTITION_SCHEMA_NAME_PREFIX") + companyId;
		}

		return _DB_PARTITION_SCHEMA_NAME_PREFIX + companyId;
	}

	private void _addDBPartition() throws Exception {
		CurrentConnection defaultCurrentConnection =
			CurrentConnectionUtil.getCurrentConnection();

		try {
			CurrentConnection currentConnection = dataSource -> _connection;

			ReflectionTestUtil.setFieldValue(
				CurrentConnectionUtil.class, "_currentConnection",
				currentConnection);

			DBPartitionUtil.addDBPartition(_COMPANY_ID);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				CurrentConnectionUtil.class, "_currentConnection",
				defaultCurrentConnection);
		}
	}

	private int _getCount(String tableName, boolean defaultSchema)
		throws Exception {

		String whereClause = StringPool.BLANK;

		if (_dbInspector.hasColumn(tableName, "companyId")) {
			whereClause = " where companyId = " + _COMPANY_ID;
		}

		String fullTableName = tableName;

		if (!defaultSchema) {
			fullTableName =
				_getSchemaName(_COMPANY_ID) + StringPool.PERIOD + tableName;
		}

		try (PreparedStatement ps = _connection.prepareStatement(
				"select count(1) from " + fullTableName + whereClause);
			ResultSet resultSet = ps.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		}

		throw new Exception("Table does not exist");
	}

	private List<String> _getObjectNames(String objectType) throws Exception {
		DatabaseMetaData databaseMetaData = _connection.getMetaData();

		List<String> objectNames = new ArrayList<>();

		try (ResultSet resultSet = databaseMetaData.getTables(
				_getSchemaName(_COMPANY_ID), _dbInspector.getSchema(), null,
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

	private int _getViewCount() throws Exception {
		List<String> viewNames = _getObjectNames("VIEW");

		return viewNames.size();
	}

	private void _removeDBPartition() throws Exception {
		CurrentConnection defaultCurrentConnection =
			CurrentConnectionUtil.getCurrentConnection();

		try {
			CurrentConnection currentConnection = dataSource -> _connection;

			ReflectionTestUtil.setFieldValue(
				CurrentConnectionUtil.class, "_currentConnection",
				currentConnection);

			DBPartitionUtil.removeDBPartition(_COMPANY_ID);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				CurrentConnectionUtil.class, "_currentConnection",
				defaultCurrentConnection);
		}
	}

	private static final long _COMPANY_ID = 1L;

	private static final String _DB_PARTITION_SCHEMA_NAME_PREFIX =
		"lpartitiontest_";

	private static Connection _connection;
	private static final DataSource _currentDataSource =
		ReflectionTestUtil.getFieldValue(DBInitUtil.class, "_dataSource");
	private static DBInspector _dbInspector;
	private static boolean _dbPartitionEnabled;
	private static String _defaultSchemaName;
	private static LazyConnectionDataSourceProxy _lazyConnectionDataSourceProxy;

	@Inject
	private static Portal _portal;

	@Inject
	private static Props _props;

}