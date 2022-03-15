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

package com.liferay.portal.db.partition.test.util;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.init.DBInitUtil;
import com.liferay.portal.dao.jdbc.util.ConnectionWrapper;
import com.liferay.portal.dao.jdbc.util.DataSourceWrapper;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnection;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalInstances;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Assume;
import org.junit.ClassRule;
import org.junit.Rule;

import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

/**
 * @author Alberto Chaparro
 */
public abstract class BaseDBPartitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		db = DBManagerUtil.getDB();

		Assume.assumeTrue(db.getDBType() == DBType.MYSQL);
	}

	protected static void addDBPartition() throws Exception {
		CurrentConnection defaultCurrentConnection =
			CurrentConnectionUtil.getCurrentConnection();

		try {
			CurrentConnection currentConnection = dataSource -> connection;

			ReflectionTestUtil.setFieldValue(
				CurrentConnectionUtil.class, "_currentConnection",
				currentConnection);

			DBPartitionUtil.addDBPartition(COMPANY_ID);
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				CurrentConnectionUtil.class, "_currentConnection",
				defaultCurrentConnection);
		}
	}

	protected static void createControlTable(String tableName)
		throws Exception {

		db.runSQL(
			"create table " + tableName + " (testColumn bigint primary key)");
	}

	protected static void createIndex(String tableName) throws Exception {
		db.runSQL(getCreateIndexSQL(tableName));
	}

	protected static void createTable(String tableName) throws Exception {
		db.runSQL(getCreateTableSQL(tableName));
	}

	protected static void createUniqueIndex(String tableName) throws Exception {
		db.runSQL(
			StringBundler.concat(
				"create unique index ", TEST_INDEX_NAME, " on ", tableName,
				" (testColumn)"));
	}

	protected static void deleteCompanyAndDefaultUser() throws Exception {
		try (Statement statement = connection.createStatement()) {
			statement.execute(
				"delete from Company where companyId = " + COMPANY_ID);

			statement.execute(
				"delete from User_ where companyId = " + COMPANY_ID);
		}
	}

	protected static void disableDBPartition() throws SQLException {
		DataAccess.cleanUp(connection);

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

	protected static void dropIndex(String tableName) throws Exception {
		db.runSQL(
			StringBundler.concat(
				"drop index ", TEST_INDEX_NAME, " on ", tableName));
	}

	protected static void dropSchema() throws Exception {
		db.runSQL("drop schema if exists " + getSchemaName(COMPANY_ID));
	}

	protected static void dropTable(String tableName) throws Exception {
		db.runSQL("drop table if exists " + tableName);
	}

	protected static void enableDBPartition() throws Exception {
		CompanyThreadLocal.setCompanyId(PortalInstances.getDefaultCompanyId());

		_dbPartitionEnabled = GetterUtil.getBoolean(
			_props.get("database.partition.enabled"));

		if (_dbPartitionEnabled) {
			return;
		}

		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_ENABLED", true);
		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_SCHEMA_NAME_PREFIX",
			_DB_PARTITION_SCHEMA_NAME_PREFIX);
		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_THREAD_POOL_ENABLED",
			true);

		DBPartitionUtil.setDefaultCompanyId(portal.getDefaultCompanyId());

		DataSource dbPartitionDataSource = _wrapDataSource(
			DBPartitionUtil.wrapDataSource(_currentDataSource));

		_lazyConnectionDataSourceProxy =
			(LazyConnectionDataSourceProxy)PortalBeanLocatorUtil.locate(
				"liferayDataSource");

		_lazyConnectionDataSourceProxy.setTargetDataSource(
			dbPartitionDataSource);

		ReflectionTestUtil.setFieldValue(
			DBInitUtil.class, "_dataSource", dbPartitionDataSource);
		ReflectionTestUtil.setFieldValue(
			InfrastructureUtil.class, "_dataSource",
			_lazyConnectionDataSourceProxy);

		connection = DataAccess.getConnection();

		dbInspector = new DBInspector(connection);
	}

	protected static String getCreateIndexSQL(String tableName) {
		return StringBundler.concat(
			"create index ", TEST_INDEX_NAME, " on ", tableName,
			" (testColumn)");
	}

	protected static String getCreateTableSQL(String tableName) {
		return "create table " + tableName +
			" (testColumn bigint primary key, companyId bigint)";
	}

	protected static String getSchemaName(long companyId) {
		if (_dbPartitionEnabled) {
			return (String)ReflectionTestUtil.getFieldValue(
				DBPartitionUtil.class,
				"_DATABASE_PARTITION_SCHEMA_NAME_PREFIX") + companyId;
		}

		return _DB_PARTITION_SCHEMA_NAME_PREFIX + companyId;
	}

	protected static void insertCompanyAndDefaultUser() throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(COMPANY_ID);
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				"insert into Company (companyId, webId) values (?, ?)");
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"insert into User_ (userId, companyId, defaultUser, " +
					"screenName, emailAddress, languageId, timeZoneId) " +
						"values (?, ?, ?, ?, ?, ?, ?)")) {

			preparedStatement1.setLong(1, COMPANY_ID);
			preparedStatement1.setString(2, "Test");

			preparedStatement1.executeUpdate();

			preparedStatement2.setLong(1, 1);
			preparedStatement2.setLong(2, COMPANY_ID);
			preparedStatement2.setBoolean(3, true);
			preparedStatement2.setString(4, "Test");
			preparedStatement2.setString(5, "test@test.com");
			preparedStatement2.setString(6, "en_US");
			preparedStatement2.setString(7, "UTC");

			preparedStatement2.executeUpdate();
		}
	}

	protected static void removeDBPartition(boolean migrate) throws Exception {
		CurrentConnection defaultCurrentConnection =
			CurrentConnectionUtil.getCurrentConnection();

		try {
			CurrentConnection currentConnection = dataSource -> connection;

			ReflectionTestUtil.setFieldValue(
				DBPartitionUtil.class, "_DATABASE_PARTITION_MIGRATE_ENABLED",
				migrate);
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

	protected void createAndPopulateControlTable(String tableName)
		throws Exception {

		try (Statement statement = connection.createStatement()) {
			statement.execute(
				"create table " + tableName +
					" (testColumn bigint primary key)");

			statement.execute("insert into " + tableName + " values (1)");
		}
	}

	protected void createAndPopulateTable(String tableName) throws Exception {
		try (Statement statement = connection.createStatement()) {
			statement.execute(getCreateTableSQL(tableName));

			statement.execute(
				StringBundler.concat(
					"insert into ", tableName, " values (1, ",
					CompanyThreadLocal.getCompanyId(), ")"));
		}
	}

	protected static final long COMPANY_ID = 123456789L;

	protected static final String TEST_CONTROL_TABLE_NAME = "TestControlTable";

	protected static final String TEST_CONTROL_TABLE_NEW_COLUMN =
		"testControlTableNewColumn";

	protected static final String TEST_INDEX_NAME = "IX_Test";

	protected static final String TEST_TABLE_NAME = "TestTable";

	protected static Connection connection;
	protected static DB db;
	protected static DBInspector dbInspector;

	@Inject
	protected static Portal portal;

	private static DataSource _wrapDataSource(DataSource dataSource) {
		return new DataSourceWrapper(dataSource) {

			@Override
			public Connection getConnection() throws SQLException {
				return _wrapConnection(super.getConnection());
			}

			@Override
			public Connection getConnection(String userName, String password)
				throws SQLException {

				return _wrapConnection(super.getConnection());
			}

			private Connection _wrapConnection(Connection connection) {
				return new ConnectionWrapper(connection) {

					@Override
					public void close() throws SQLException {
						String defaultSchemaName =
							ReflectionTestUtil.getFieldValue(
								DBPartitionUtil.class, "_defaultSchemaName");

						setCatalog(defaultSchemaName);

						super.close();
					}

				};
			}

		};
	}

	private static final String _DB_PARTITION_SCHEMA_NAME_PREFIX =
		"lpartitiontest_";

	private static final DataSource _currentDataSource =
		ReflectionTestUtil.getFieldValue(DBInitUtil.class, "_dataSource");
	private static boolean _dbPartitionEnabled;
	private static LazyConnectionDataSourceProxy _lazyConnectionDataSourceProxy;

	@Inject
	private static Props _props;

}