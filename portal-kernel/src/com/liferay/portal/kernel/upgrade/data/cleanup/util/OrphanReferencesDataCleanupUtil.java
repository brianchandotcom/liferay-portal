/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.data.cleanup.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
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

		if (_normalizedExcludedTableNames.isEmpty()) {
			DBInspector dbInspector = new DBInspector(connection);

			for (String excludedTableName : _excludedTableNames) {
				_normalizedExcludedTableNames.add(
					dbInspector.normalizeName(excludedTableName));
			}
		}

		if (_normalizedExcludedTableNames.contains(sourceTableName)) {
			return;
		}

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select ", sourceColumnName, ", count(1) from ",
					sourceTableName,
					_getWhereClause(
						connection, sourceAdditionalWhereClause,
						sourceColumnName, sourceTableName, targetColumnName,
						targetTableName),
					" group by ", sourceColumnName));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				StringBundler.concat(
					"delete from ", sourceTableName,
					_getWhereClause(
						connection, sourceAdditionalWhereClause,
						sourceColumnName, sourceTableName, targetColumnName,
						targetTableName)));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			preparedStatement2.execute();

			if (!_log.isInfoEnabled()) {
				return;
			}

			while (resultSet.next()) {
				_log.info(
					StringBundler.concat(
						String.valueOf(resultSet.getLong(2)),
						" orphan entries from table ", sourceTableName,
						" have been deleted because value ",
						String.valueOf(resultSet.getObject(1)),
						" was not found in the origin table ", targetTableName,
						" and column ", targetColumnName));
			}
		}
	}

	public static void fixOrphanUsers(
			Connection connection, String sourceColumnName,
			String sourceTableName, String targetColumnName,
			String targetTableName)
		throws Exception {

		if (_normalizedExcludedTableNames.isEmpty()) {
			DBInspector dbInspector = new DBInspector(connection);

			for (String excludedTableName : _excludedTableNames) {
				_normalizedExcludedTableNames.add(
					dbInspector.normalizeName(excludedTableName));
			}
		}

		if (_normalizedExcludedTableNames.contains(sourceTableName)) {
			return;
		}

		DBInspector dbInspector = new DBInspector(connection);

		if (!dbInspector.hasColumn(sourceTableName, "companyId")) {
			return;
		}

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select ", sourceColumnName, ", companyId, count(1) from ",
					sourceTableName,
					_getWhereClause(
						connection, null, sourceColumnName, sourceTableName,
						targetColumnName, targetTableName),
					" group by ", sourceColumnName, ", companyId"));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				StringBundler.concat(
					"delete from ", sourceTableName, " where ",
					sourceColumnName, " = ? and companyId = ?"));
			PreparedStatement preparedStatement3 = connection.prepareStatement(
				StringBundler.concat(
					"update ", sourceTableName, " set ", sourceColumnName,
					" = ? where ", sourceColumnName, " = ? and companyId = ?"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong(2);
				long userId = resultSet.getLong(1);

				if (_isPartOfUniqueIndex(
						connection, sourceColumnName, sourceTableName)) {

					preparedStatement2.setLong(1, userId);
					preparedStatement2.setLong(2, companyId);

					preparedStatement2.executeUpdate();

					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								String.valueOf(resultSet.getLong(3)),
								" orphan entries from table ", sourceTableName,
								" have been deleted because value ",
								String.valueOf(userId),
								" was not found in the origin table ",
								targetTableName, " and column ",
								targetColumnName));
					}
				}
				else {
					long newUserId = _getAdminUserId(connection, companyId);

					preparedStatement3.setLong(1, newUserId);

					preparedStatement3.setLong(2, userId);
					preparedStatement3.setLong(3, companyId);

					preparedStatement3.executeUpdate();

					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								String.valueOf(resultSet.getLong(3)),
								" orphan entries from table ", sourceTableName,
								" have been updated to value ",
								String.valueOf(newUserId), " because value ",
								String.valueOf(userId),
								" was not found in the origin table ",
								targetTableName, " and column ",
								targetColumnName));
					}
				}
			}
		}
	}

	private static long _getAdminUserId(Connection connection, long companyId)
		throws Exception {

		DBInspector dbInspector = new DBInspector(connection);

		boolean hasColumn = dbInspector.hasColumn("User_", "type_");

		StringBundler sb = new StringBundler(6);

		sb.append("select User_.userId from User_ inner join Users_Roles on ");
		sb.append("User_.userId = Users_Roles.userId inner join Role_ on ");
		sb.append("Users_Roles.roleId = Role_.roleId where Role_.name = ? ");
		sb.append("and User_.companyId = ? and Role_.companyId = ?");

		if (hasColumn) {
			sb.append(" and User_.type_ = ?");
		}

		sb.append(" order by User_.userId asc");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.setString(1, RoleConstants.ADMINISTRATOR);

			preparedStatement.setLong(2, companyId);
			preparedStatement.setLong(3, companyId);

			if (hasColumn) {
				preparedStatement.setInt(4, UserConstants.TYPE_REGULAR);
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					throw new Exception(
						"No admin user found for company " + companyId);
				}

				return resultSet.getLong(1);
			}
		}
	}

	private static String _getWhereClause(
			Connection connection, String sourceAdditionalWhereClause,
			String sourceColumnName, String sourceTableName,
			String targetColumnName, String targetTableName)
		throws Exception {

		DBInspector dbInspector = new DBInspector(connection);

		return StringBundler.concat(
			" where not exists (select 1 from ", targetTableName, " where ",
			targetTableName, StringPool.PERIOD, targetColumnName, " = ",
			sourceTableName, StringPool.PERIOD, sourceColumnName, ") and ",
			sourceColumnName, " is not null and ", sourceColumnName, " != ",
			dbInspector.isNumeric(sourceTableName, sourceColumnName) ? "0" :
				"''",
			(sourceAdditionalWhereClause != null) ?
				" and " + sourceAdditionalWhereClause : "");
	}

	private static boolean _isPartOfUniqueIndex(
			Connection connection, String sourceColumnName,
			String sourceTableName)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		List<IndexMetadata> indexes = db.getIndexMetadatas(
			connection, sourceTableName, sourceColumnName, true);

		if (!indexes.isEmpty()) {
			return true;
		}

		String[] columnNames = db.getPrimaryKeyColumnNames(
			connection, sourceTableName);

		return ArrayUtil.contains(columnNames, sourceColumnName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrphanReferencesDataCleanupUtil.class);

	private static final List<String> _excludedTableNames = new ArrayList<>(
		Arrays.asList("Audit_AuditEvent"));
	private static final List<String> _normalizedExcludedTableNames =
		new ArrayList<>();

}