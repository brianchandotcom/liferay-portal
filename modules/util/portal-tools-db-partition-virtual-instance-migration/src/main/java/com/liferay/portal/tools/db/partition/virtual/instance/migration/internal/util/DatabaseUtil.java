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

package com.liferay.portal.tools.db.partition.virtual.instance.migration.internal.util;

import com.liferay.portal.tools.db.partition.virtual.instance.migration.internal.Release;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.internal.version.Version;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Luis Ortiz
 */
public class DatabaseUtil {

	public static List<String> getFailedServletContextNames(
			Connection connection)
		throws SQLException {

		List<String> failedServletContextNames = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select servletContextName from Release_ where state_ != 0;");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				failedServletContextNames.add(resultSet.getString(1));
			}
		}

		return failedServletContextNames;
	}

	public static Map<String, Release> getReleaseMap(Connection connection)
		throws SQLException {

		Map<String, Release> releaseMap = new HashMap<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select servletContextName, schemaVersion, verified from " +
					"Release_");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				releaseMap.put(
					resultSet.getString(1),
					new Release(
						resultSet.getString(1),
						Version.parseVersion(resultSet.getString(2)),
						resultSet.getBoolean(3)));
			}
		}

		return releaseMap;
	}

	public static List<Release> getReleases(Connection connection)
		throws SQLException {

		List<Release> releases = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select servletContextName, schemaVersion, verified from " +
					"Release_");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				releases.add(
					new Release(
						resultSet.getString(1),
						Version.parseVersion(resultSet.getString(2)),
						resultSet.getBoolean(3)));
			}
		}

		return releases;
	}

	public static List<String> getTables(Connection connection)
		throws SQLException {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		List<String> tableNames = new ArrayList<>();

		try (ResultSet resultSet = databaseMetaData.getTables(
				connection.getCatalog(), connection.getSchema(), null,
				new String[] {"TABLE"})) {

			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");

				if (!_isObjectTable(connection, tableName) &&
					!_isControlTable(connection, tableName)) {

					tableNames.add(tableName);
				}
			}
		}

		return tableNames;
	}

	public static String getWebId(Connection connection) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select webId from Company, CompanyInfo where " +
					"CompanyInfo.companyId = Company.companyId");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getString(1);
			}

			return null;
		}
	}

	public static boolean hasWebId(Connection connection, String webId)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId from Company where webId = ?")) {

			preparedStatement.setString(1, webId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isDefaultPartition(Connection connection)
		throws SQLException {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		for (String tableName : _controlTableNames) {
			try (ResultSet resultSet = databaseMetaData.getTables(
					connection.getCatalog(), connection.getSchema(),
					_normalizeName(databaseMetaData, tableName),
					new String[] {"TABLE"})) {

				if (!resultSet.next()) {
					return false;
				}
			}
		}

		return true;
	}

	public static boolean isSingleVirtualInstance(Connection connection)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(1) from CompanyInfo");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				int count = resultSet.getInt(1);

				if (count > 1) {
					return false;
				}
			}
		}

		return true;
	}

	public static void setSchemaPrefix(String schemaPrefix) {
		_schemaPrefix = schemaPrefix;
	}

	private static List<Long> _getCompanyIds(Connection connection)
		throws SQLException {

		if (_companyIds.containsKey(connection)) {
			return _companyIds.get(connection);
		}

		List<Long> companyIds = new ArrayList<>();

		long defaultCompanyId = _getDefaultCompanyIdBySQL(connection);

		if (defaultCompanyId != 0) {
			companyIds.add(defaultCompanyId);
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId from Company");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");

				if (companyId != defaultCompanyId) {
					companyIds.add(companyId);
				}
			}
		}

		_companyIds.put(connection, companyIds);

		return companyIds;
	}

	private static long _getDefaultCompanyIdBySQL(Connection connection)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId from CompanyInfo");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		}

		return 0;
	}

	private static boolean _hasColumn(
			String tableName, String columnName, Connection connection)
		throws SQLException {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		try (ResultSet resultSet = databaseMetaData.getColumns(
				connection.getCatalog(), connection.getSchema(),
				_normalizeName(databaseMetaData, tableName),
				_normalizeName(databaseMetaData, columnName))) {

			if (!resultSet.next()) {
				return false;
			}

			return true;
		}
	}

	private static boolean _isControlTable(
			Connection connection, String tableName)
		throws SQLException {

		if (!_isObjectTable(connection, tableName) &&
			(_controlTableNames.contains(tableName) ||
			 !_hasColumn(tableName, "companyId", connection))) {

			return true;
		}

		return false;
	}

	private static boolean _isObjectTable(
			Connection connection, String tableName)
		throws SQLException {

		for (long companyId : _getCompanyIds(connection)) {
			if (tableName.endsWith("_x_" + companyId) ||
				tableName.startsWith("O_" + companyId + "_")) {

				return true;
			}
		}

		return false;
	}

	private static String _normalizeName(
			DatabaseMetaData databaseMetaData, String name)
		throws SQLException {

		if (databaseMetaData.storesLowerCaseIdentifiers()) {
			return name.toLowerCase();
		}

		if (databaseMetaData.storesUpperCaseIdentifiers()) {
			return name.toUpperCase();
		}

		return name;
	}

	private static final HashMap<Connection, List<Long>> _companyIds =
		new HashMap<>();
	private static final Set<String> _controlTableNames = new HashSet<>(
		Arrays.asList("Company", "VirtualHost"));
	private static String _schemaPrefix = "lpartition_";

}