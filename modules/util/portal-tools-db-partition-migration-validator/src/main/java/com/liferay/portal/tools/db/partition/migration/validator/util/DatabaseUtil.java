/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.migration.validator.Company;
import com.liferay.portal.tools.db.partition.migration.validator.LiferayDatabase;
import com.liferay.portal.tools.db.partition.migration.validator.Release;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class DatabaseUtil {

	public static LiferayDatabase exportLiferayDatabase(Connection connection)
		throws Exception {

		LiferayDatabase liferayDatabase = new LiferayDatabase();

		liferayDatabase.setCompanies(_getCompanies(connection));
		liferayDatabase.setExportedCompanyId(_getExportedCompanyId(connection));
		liferayDatabase.setExportedCompanyDefault(
			_isDefaultCompany(connection));
		liferayDatabase.setReleases(_getReleases(connection));
		liferayDatabase.setTableNames(_getPartitionedTableNames(connection));

		return liferayDatabase;
	}

	public static boolean isPostgreSQL(String jdbcURL) {
		if (jdbcURL.indexOf("postgresql") != -1) {
			return true;
		}

		return false;
	}

	public static String replaceSchemaName(String jdbcURL, String schemaName) {
		if (schemaName == null) {
			return jdbcURL;
		}

		if (isPostgreSQL(jdbcURL)) {
			return _replacePostgreSQLSchemaName(jdbcURL, schemaName);
		}

		return _replaceMySQLSchemaName(jdbcURL, schemaName);
	}

	private static List<Company> _getCompanies(Connection connection)
		throws Exception {

		List<Company> companies = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select Company.companyId, webId, name, hostname from " +
					"Company left join VirtualHost on Company.companyId = " +
						"VirtualHost.companyId");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				companies.add(
					new Company(
						resultSet.getLong(1), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(2)));
			}
		}

		return companies;
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

	private static long _getExportedCompanyId(Connection connection)
		throws Exception {

		long companyId = 0;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId from CompanyInfo");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				if (companyId > 0) {
					throw new UnsupportedOperationException(
						"Database schema has to have a single company or " +
							"database partitioning must be enabled");
				}

				companyId = resultSet.getLong(1);
			}
		}

		return companyId;
	}

	private static List<String> _getPartitionedTableNames(Connection connection)
		throws Exception {

		List<String> partitionedTableNames = new ArrayList<>();

		List<Long> companyIds = _getCompanyIds(connection);

		DBInspector dbInspector = new DBInspector(connection);

		for (String tableName : dbInspector.getTableNames(null)) {
			if (!dbInspector.isControlTable(tableName) &&
				!dbInspector.isObjectTable(companyIds, tableName)) {

				partitionedTableNames.add(tableName);
			}
		}

		return partitionedTableNames;
	}

	private static List<Release> _getReleases(Connection connection)
		throws Exception {

		List<Release> releases = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select servletContextName, schemaVersion, state_, verified " +
					"from Release_");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				releases.add(
					new Release(
						Version.parseVersion(resultSet.getString(2)),
						resultSet.getString(1), resultSet.getInt(3),
						resultSet.getBoolean(4)));
			}
		}

		return releases;
	}

	private static boolean _isDefaultCompany(Connection connection)
		throws Exception {

		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.hasTable("Company");
	}

	private static String _replaceMySQLSchemaName(
		String jdbcURL, String schemaName) {

		int index = jdbcURL.indexOf("?");

		if (index == -1) {
			return jdbcURL.substring(0, jdbcURL.lastIndexOf("/") + 1) +
				schemaName;
		}

		String baseJDBCURL = jdbcURL.substring(0, index);

		return StringBundler.concat(
			jdbcURL.substring(0, baseJDBCURL.lastIndexOf("/") + 1), schemaName,
			jdbcURL.substring(index));
	}

	private static String _replacePostgreSQLSchemaName(
		String jdbcURL, String schemaName) {

		int index = jdbcURL.indexOf("?");

		if (index == -1) {
			return jdbcURL + "?currentSchema=" + schemaName;
		}

		return jdbcURL + "&currentSchema=" + schemaName;
	}

}