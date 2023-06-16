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

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.internal.util;

import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.internal.release.Release;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public static List<String> getPartitionedTableNames(Connection connection)
		throws Exception {

		DBInspector dbInspector = new DBInspector(connection);

		List<Long> companyIds = _getCompanyIds(connection);

		List<String> partitionedTableNames = new ArrayList<>();

		for (String tableName : dbInspector.getTableNames(null)) {
			if (!dbInspector.isControlTable(companyIds, tableName) &&
				!dbInspector.isObjectTable(companyIds, tableName)) {

				partitionedTableNames.add(tableName);
			}
		}

		return partitionedTableNames;
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

	public static Map<String, Release> getReleasesMap(Connection connection)
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
		throws Exception {

		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.hasTable("Company");
	}

	public static boolean isSingleVirtualInstance(Connection connection)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(1) from CompanyInfo");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next() && (resultSet.getInt(1) > 1)) {
				return false;
			}
		}

		return true;
	}

	public static void setSchemaPrefix(String schemaPrefix) {
		_schemaPrefix = schemaPrefix;
	}

	private static List<Long> _getCompanyIds(Connection connection)
		throws Exception {

		List<Long> companyIds = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId from Company");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				companyIds.add(resultSet.getLong("companyId"));
			}
		}

		return companyIds;
	}

	private static String _schemaPrefix = "lpartition_";

}