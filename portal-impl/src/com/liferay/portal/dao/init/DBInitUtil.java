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

package com.liferay.portal.dao.init;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSync;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.module.util.ServiceLatch;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.hibernate.DialectDetector;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.util.PropsUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Properties;

import javax.sql.DataSource;

/**
 * @author Preston Crary
 */
public class DBInitUtil {

	public static DataSource getDataSource() {
		return _dataSource;
	}

	public static DataSource getReadDataSource() {
		return _readDataSource;
	}

	public static DataSource getWriteDataSource() {
		return _writeDataSource;
	}

	public static void init() throws Exception {
		_readDataSource = _initDataSource("jdbc.read.");

		_writeDataSource = _initDataSource("jdbc.write.");

		_dataSource = _writeDataSource;

		if ((_readDataSource == null) && (_writeDataSource == null)) {
			_dataSource = _initDataSource("jdbc.default.");
		}

		try (Connection connection = _dataSource.getConnection()) {
			_init(DBManagerUtil.getDB(), connection);

			_dataSource = DBPartitionUtil.wrapDataSource(_dataSource);

			DBPartitionUtil.setDefaultCompanyId(connection);
		}
	}

	private static void _addReleaseInfo(Connection connection)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into Release_ (releaseId, createDate, ",
					"modifiedDate, servletContextName, schemaVersion, ",
					"buildNumber, verified, testString) values (",
					ReleaseConstants.DEFAULT_ID, ", ?, ?, ?, ?, ?, ?, ?)"))) {

			Date date = new Date(System.currentTimeMillis());

			preparedStatement.setDate(1, date);
			preparedStatement.setDate(2, date);

			preparedStatement.setString(
				3, ReleaseConstants.DEFAULT_SERVLET_CONTEXT_NAME);

			preparedStatement.setString(
				4,
				String.valueOf(PortalUpgradeProcess.getLatestSchemaVersion()));

			preparedStatement.setInt(5, ReleaseInfo.getBuildNumber());
			preparedStatement.setBoolean(6, false);
			preparedStatement.setString(7, ReleaseConstants.TEST_STRING);

			preparedStatement.executeUpdate();
		}
	}

	private static boolean _checkDefaultRelease(Connection connection) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select mvccVersion, schemaVersion, buildNumber, state_ from " +
					"Release_ where releaseId = " +
						ReleaseConstants.DEFAULT_ID);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (!resultSet.next()) {
				_addReleaseInfo(connection);

				StartupHelperUtil.setDbNew(true);
			}

			return true;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	private static void _createTablesAndPopulate(DB db, Connection connection)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Create tables and populate with default data");
		}

		ClassLoader classLoader = DBInitUtil.class.getClassLoader();

		_runSQLTemplate(db, connection, classLoader, "portal-tables.sql");
		_runSQLTemplate(db, connection, classLoader, "portal-data-common.sql");
		_runSQLTemplate(db, connection, classLoader, "portal-data-counter.sql");
		_runSQLTemplate(db, connection, classLoader, "indexes.sql");
		_runSQLTemplate(db, connection, classLoader, "sequences.sql");

		_addReleaseInfo(connection);

		StartupHelperUtil.setDbNew(true);

		ServiceLatch serviceLatch = SystemBundleUtil.newServiceLatch();

		serviceLatch.waitFor(
			DependencyManagerSync.class,
			dependencyManagerSync -> dependencyManagerSync.registerSyncCallable(
				() -> {
					StartupHelperUtil.setDbNew(false);

					return null;
				}));

		serviceLatch.openOn(
			() -> {
			});
	}

	private static boolean _hasDefaultReleaseWithTestString(
			Connection connection, String testString)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from Release_ where releaseId = ? and " +
					"testString = ?")) {

			preparedStatement.setLong(1, ReleaseConstants.DEFAULT_ID);
			preparedStatement.setString(2, testString);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next() && (resultSet.getInt(1) > 0)) {
					return true;
				}
			}
		}

		return false;
	}

	private static void _init(DB db, Connection connection) throws Exception {
		if (_checkDefaultRelease(connection)) {
			_setSupportsStringCaseSensitiveQuery(db, connection);

			return;
		}

		try {
			db.runSQL(
				connection,
				"alter table Release_ add mvccVersion LONG default 0 not null");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		try {
			db.runSQL(
				connection,
				"alter table Release_ add schemaVersion VARCHAR(75) null");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		try {
			db.runSQL(connection, "alter table Release_ add state_ INTEGER");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		if (_checkDefaultRelease(connection)) {
			_setSupportsStringCaseSensitiveQuery(db, connection);

			return;
		}

		// Create tables and populate with default data

		if (GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.SCHEMA_RUN_ENABLED))) {

			_createTablesAndPopulate(db, connection);

			_setSupportsStringCaseSensitiveQuery(db, connection);
		}
	}

	private static DataSource _initDataSource(String prefix) throws Exception {
		Properties properties = PropsUtil.getProperties(prefix, true);

		if ((properties == null) || properties.isEmpty()) {
			return null;
		}

		DataSource dataSource = DataSourceFactoryUtil.initDataSource(
			properties);

		DBManagerUtil.setDB(DialectDetector.getDialect(dataSource), dataSource);

		return dataSource;
	}

	private static void _runSQLTemplate(
			DB db, Connection connection, ClassLoader classLoader, String path)
		throws Exception {

		db.runSQLTemplateString(
			connection,
			StreamUtil.toString(
				classLoader.getResourceAsStream(
					"com/liferay/portal/tools/sql/dependencies/".concat(path))),
			false);
	}

	private static void _setSupportsStringCaseSensitiveQuery(
			DB db, Connection connection)
		throws Exception {

		if (!_hasDefaultReleaseWithTestString(
				connection, ReleaseConstants.TEST_STRING)) {

			throw new SystemException(
				"Release_ table was not initialized properly");
		}

		if (_hasDefaultReleaseWithTestString(
				connection,
				StringUtil.toUpperCase(ReleaseConstants.TEST_STRING))) {

			db.setSupportsStringCaseSensitiveQuery(false);
		}
		else {
			db.setSupportsStringCaseSensitiveQuery(true);
		}
	}

	private DBInitUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(DBInitUtil.class);

	private static DataSource _dataSource;
	private static DataSource _readDataSource;
	private static DataSource _writeDataSource;

}