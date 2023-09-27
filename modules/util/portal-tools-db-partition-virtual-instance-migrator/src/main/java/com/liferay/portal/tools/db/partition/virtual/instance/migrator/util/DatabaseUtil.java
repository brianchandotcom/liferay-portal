/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.util;

import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.Release;

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

		List<String> partitionedTableNames = new ArrayList<>();

		List<Long> companyIds = _getCompanyIds(connection);

		DBInspector dbInspector = new DBInspector(connection);

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

		Map<String, Release> releasesMap = getReleasesMap(connection);

		for (Release release : releasesMap.values()) {
			releases.add(release);
		}

		return releases;
	}

	public static Map<String, Release> getReleasesMap(Connection connection)
		throws SQLException {

		Map<String, Release> releasesMap = new HashMap<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select servletContextName, schemaVersion, verified from " +
					"Release_");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String servletContextName = resultSet.getString(1);

				releasesMap.put(
					servletContextName,
					new Release(
						Version.parseVersion(resultSet.getString(2)),
						servletContextName, resultSet.getBoolean(3)));
			}
		}

		return releasesMap;
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

	public static boolean hasSingleCompanyInfo(Connection connection)
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