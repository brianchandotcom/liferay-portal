/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.extractor.util;

import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.common.Company;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.common.InstanceData;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.common.Release;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class DatabaseUtil {

	public static InstanceData exportInstanceData(Connection connection)
		throws Exception {

		InstanceData instanceData = new InstanceData();

		instanceData.setJdbcUrl(
			connection.getMetaData(
			).getURL());

		instanceData.setCompanyId(_getCompanyId(connection));

		instanceData.setDefaultPartition(_isDefaultPartition(connection));

		instanceData.setTableNames(_getPartitionedTableNames(connection));

		instanceData.setReleases(_getReleases(connection));

		instanceData.setCompanies(_getCompanies(connection));

		return instanceData;
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

	private static Long _getCompanyId(Connection connection) throws Exception {
		Long companyId = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId from CompanyInfo");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			int companyCount = 0;

			while (resultSet.next()) {
				companyId = resultSet.getLong(1);

				if (++companyCount > 1) {
					return null;
				}
			}
		}

		return companyId;
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

	private static List<String> _getPartitionedTableNames(Connection connection)
		throws Exception {

		List<String> partitionedTableNames = new ArrayList<>();

		List<Long> companyIds = _getCompanyIds(connection);

		DBInspector dbInspector = new DBInspector(connection);

		for (String tableName : dbInspector.getTableNames(null)) {
			if (!dbInspector.isControlTable(companyIds, tableName) &&
				!DBInspector.isObjectTable(companyIds, tableName)) {

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

	private static boolean _isDefaultPartition(Connection connection)
		throws Exception {

		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.hasTable("Company");
	}

}