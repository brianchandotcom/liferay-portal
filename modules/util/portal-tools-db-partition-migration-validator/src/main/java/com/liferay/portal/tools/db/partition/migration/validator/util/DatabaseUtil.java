/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.migration.validator.Company;
import com.liferay.portal.tools.db.partition.migration.validator.LiferayInstance;
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

	public static LiferayInstance exportLiferayInstance(Connection connection)
		throws Exception {

		LiferayInstance liferayInstance = new LiferayInstance();

		liferayInstance.setExportedCompanyId(_getExportedCompanyId(connection));

		liferayInstance.setExportedCompanyDefault(
			_isDefaultCompany(connection));

		liferayInstance.setTableNames(_getPartitionedTableNames(connection));

		liferayInstance.setReleases(_getReleases(connection));

		liferayInstance.setCompanies(_getCompanies(connection));

		return liferayInstance;
	}

	public static String replaceSchemaName(String jdbcUrl, String schemaName) {
		if (schemaName == null) {
			return jdbcUrl;
		}

		String replacedJdbcUrl;

		int paramsIndex = jdbcUrl.indexOf("?");

		if (paramsIndex == -1) {
			replacedJdbcUrl =
				jdbcUrl.substring(0, jdbcUrl.lastIndexOf("/") + 1) + schemaName;
		}
		else {
			String onlyUrl = jdbcUrl.substring(0, paramsIndex);

			replacedJdbcUrl = StringBundler.concat(
				jdbcUrl.substring(0, onlyUrl.lastIndexOf("/") + 1), schemaName,
				jdbcUrl.substring(paramsIndex));
		}

		return replacedJdbcUrl;
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
						"Source multi company or target with DB Partitioning " +
							"disabled environments are not supported");
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

}