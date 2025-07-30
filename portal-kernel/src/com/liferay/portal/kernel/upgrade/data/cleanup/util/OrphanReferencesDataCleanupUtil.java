/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.data.cleanup.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class OrphanReferencesDataCleanupUtil {

	public static void cleanUpTable(
			Connection connection, String sourceAdditionalWhereClause,
			String sourceColumnName, String sourceTableName,
			String targetColumnName, String targetTableName)
		throws Exception {

		List<String> excludedTableNames = getNormalizedExcludedTableNames(
			connection);

		if (excludedTableNames.contains(sourceTableName)) {
			return;
		}

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select ", sourceColumnName, ", count(1) from ",
					sourceTableName,
					getWhereClause(
						connection, sourceAdditionalWhereClause,
						sourceColumnName, sourceTableName, targetColumnName,
						targetTableName),
					" group by ", sourceColumnName));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				StringBundler.concat(
					"delete from ", sourceTableName,
					getWhereClause(
						connection, sourceAdditionalWhereClause,
						sourceColumnName, sourceTableName, targetColumnName,
						targetTableName)));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			preparedStatement2.execute();

			if (!_log.isInfoEnabled()) {
				return;
			}

			while (resultSet.next()) {
				long count = resultSet.getLong(2);

				_log.info(
					StringBundler.concat(
						"Table ", sourceTableName, ", ", String.valueOf(count),
						(count == 1) ? " entry " : " entries ",
						"deleted because ", sourceColumnName, StringPool.SPACE,
						String.valueOf(resultSet.getObject(1)),
						" was not found in ", targetTableName,
						StringPool.PERIOD, targetColumnName));
			}
		}
	}

	public static List<String> getNormalizedExcludedTableNames(
			Connection connection)
		throws Exception {

		if (_normalizedExcludedTableNames.isEmpty()) {
			DBInspector dbInspector = new DBInspector(connection);

			for (String excludedTableName : _excludedTableNames) {
				_normalizedExcludedTableNames.add(
					dbInspector.normalizeName(excludedTableName));
			}
		}

		return _normalizedExcludedTableNames;
	}

	public static String getWhereClause(
			Connection connection, String sourceAdditionalWhereClause,
			String sourceColumnName, String sourceTableName,
			String targetColumnName, String targetTableName)
		throws Exception {

		String additionalNullCheck = "";

		DB db = DBManagerUtil.getDB();
		DBInspector dbInspector = new DBInspector(connection);

		if (dbInspector.isNumeric(sourceTableName, sourceColumnName)) {
			additionalNullCheck = " and " + sourceColumnName + " != 0";
		}
		else if (db.getDBType() != DBType.ORACLE) {
			additionalNullCheck = " and " + sourceColumnName + " != ''";
		}

		return StringBundler.concat(
			" where not exists (select 1 from ", targetTableName, " where ",
			targetTableName, StringPool.PERIOD, targetColumnName, " = ",
			sourceTableName, StringPool.PERIOD, sourceColumnName, ") and ",
			sourceColumnName, " is not null", additionalNullCheck,
			(sourceAdditionalWhereClause != null) ?
				" and " + sourceAdditionalWhereClause : "");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrphanReferencesDataCleanupUtil.class);

	private static final List<String> _excludedTableNames = new ArrayList<>(
		Arrays.asList("Audit_AuditEvent", "CyrusUser", "CyrusVirtual"));
	private static final List<String> _normalizedExcludedTableNames =
		new ArrayList<>();

}