/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.util.DataCleanupLoggingUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Jorge Avalos
 */
public class DatabaseTableAndColumnCaseDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DB db = DBManagerUtil.getDB();

		if ((db.getDBType() != DBType.MARIADB) &&
			(db.getDBType() != DBType.MYSQL) &&
			(db.getDBType() != DBType.SQLSERVER)) {

			return;
		}

		Set<String> expectedTableNames = DBResourceUtil.getLiferayTableNames(
			connection);

		CompanyLocalServiceUtil.forEachCompanyId(
			companyId -> {
				try {
					expectedTableNames.addAll(
						DBResourceUtil.getNonserviceBuilderTableNames(
							companyId));
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to get table names for company " + companyId,
						portalException);
				}
			});

		DBInspector dbInspector = new DBInspector(connection);

		Map<String, String> tableNames = new TreeMap<>(
			String.CASE_INSENSITIVE_ORDER);

		for (String tableName : dbInspector.getTableNames(null)) {
			tableNames.put(tableName, tableName);
		}

		for (String expectedTableName : expectedTableNames) {
			String tableName = tableNames.get(expectedTableName);

			if ((tableName == null) || tableName.equals(expectedTableName)) {
				continue;
			}

			DataCleanupLoggingUtil.logRename(
				_log, tableName, expectedTableName,
				"it had an incorrect table name casing");

			alterTableName(tableName, expectedTableName + "_temp");

			alterTableName(expectedTableName + "_temp", expectedTableName);
		}

		Map<String, List<String>> columnDefinitionsMap =
			DBResourceUtil.getServiceComponentPortalColumnDefinitionsMap(
				connection);

		if (columnDefinitionsMap.isEmpty()) {
			return;
		}

		columnDefinitionsMap.putAll(
			DBResourceUtil.getServiceComponentModuleColumnDefinitionsMap(
				connection));

		Map<String, Map<String, String>> columnsMap = new TreeMap<>();
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		for (String tableName : expectedTableNames) {
			try (ResultSet resultSet = databaseMetaData.getColumns(
					dbInspector.getCatalog(), dbInspector.getSchema(),
					tableName, "%")) {

				while (resultSet.next()) {
					String columnName = resultSet.getString("COLUMN_NAME");

					columnsMap.computeIfAbsent(
						tableName,
						k -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER)
					).put(
						columnName, columnName
					);
				}
			}
		}

		for (Map.Entry<String, List<String>> entry :
				columnDefinitionsMap.entrySet()) {

			String tableName = entry.getKey();
			List<String> columnDefinitions = entry.getValue();

			Map<String, String> columnNames = columnsMap.get(tableName);

			_validateColumnNamesCasing(
				dbInspector, columnDefinitions, columnNames,
				dbInspector.normalizeName(tableName));
		}
	}

	private void _validateColumnNamesCasing(
			DBInspector dbInspector, List<String> columnDefinitions,
			Map<String, String> columnNames, String tableName)
		throws Exception {

		if ((columnNames == null) || columnNames.isEmpty()) {
			return;
		}

		DB db = DBManagerUtil.getDB();

		for (String columnDefinition : columnDefinitions) {
			if (Validator.isNull(columnDefinition)) {
				continue;
			}

			String expectedColumnName = StringUtil.split(
				dbInspector.normalizeName(columnDefinition), StringPool.SPACE)
				[0];

			String columnName = columnNames.get(expectedColumnName);

			if ((columnName == null) || columnName.equals(expectedColumnName)) {
				continue;
			}

			DataCleanupLoggingUtil.logRename(
				_log, tableName + StringPool.PERIOD + columnName,
				tableName + StringPool.PERIOD + expectedColumnName,
				"it had an incorrect column name casing");

			int index = columnDefinition.indexOf(StringPool.SPACE);

			String columnDataType =
				(index != -1) ? columnDefinition.substring(index + 1) : "";

			String tempColumnDefinition =
				expectedColumnName + "_temp " + columnDataType;

			db.alterColumnName(
				connection, tableName, columnName, tempColumnDefinition);

			db.alterColumnName(
				connection, tableName, expectedColumnName + "_temp",
				columnDefinition);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DatabaseTableAndColumnCaseDataCleanupPreupgradeProcess.class);

}