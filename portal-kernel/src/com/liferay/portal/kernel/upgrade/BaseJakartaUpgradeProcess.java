/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.util.JakartaUpgradeProcessUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Luis Ortiz
 */
public abstract class BaseJakartaUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (String[] tableAndColumnNames : getTableAndColumnNames()) {
			Set<String> modifiedKeys = new CopyOnWriteArraySet<>();

			String columnName = tableAndColumnNames[1];

			String tableName = tableAndColumnNames[0];

			String[] primaryKeyColumnNames = getPrimaryKeyColumnNames(
				connection, tableName);

			processConcurrently(
				_getSelectSQL(columnName, primaryKeyColumnNames, tableName),
				_getUpdateSQL(columnName, primaryKeyColumnNames, tableName),
				resultSet -> _getSelectResultSetData(
					columnName, primaryKeyColumnNames, resultSet),
				(values, preparedStatement) -> {
					String modifiedKey = _updateJakartaValue(
						getCustomSeparators(), preparedStatement,
						primaryKeyColumnNames, values);

					if (modifiedKey == null) {
						return;
					}

					modifiedKeys.add(modifiedKey);
				},
				StringBundler.concat(
					"Unable to update javax references in table ", tableName,
					" column ", columnName, " for company ",
					CompanyThreadLocal.getCompanyId(
					).toString()));

			if (_log.isInfoEnabled()) {
				StringBundler sb = new StringBundler();

				for (String key : modifiedKeys) {
					sb.append(key);
					sb.append(", ");
				}

				sb.setIndex(sb.index() - 1);

				_log.info(
					StringBundler.concat(
						"Table/column ", tableName, "/", columnName,
						" for company ",
						CompanyThreadLocal.getCompanyId(
						).toString(),
						" has been upgraded for next IDs: ", sb.toString()));
			}
		}
	}

	protected char[] getCustomSeparators() {
		return new char[0];
	}

	protected abstract String[][] getTableAndColumnNames();

	private Object[] _getSelectResultSetData(
			String columnName, String[] primaryKeyColumnNames,
			ResultSet resultSet)
		throws SQLException {

		Object[] result = new Object[primaryKeyColumnNames.length + 1];

		int i = 0;

		for (String primaryKeyColumnName : primaryKeyColumnNames) {
			result[i] = resultSet.getObject(primaryKeyColumnName);
			i++;
		}

		result[i] = resultSet.getString(columnName);

		return result;
	}

	private String _getSelectSQL(
		String columnName, String[] primaryKeyColumnNames, String tableName) {

		StringBundler sb = new StringBundler();

		sb.append("select ");

		for (String primaryKeyColumnName : primaryKeyColumnNames) {
			sb.append(primaryKeyColumnName);
			sb.append(", ");
		}

		sb.append(columnName);
		sb.append(" from ");
		sb.append(tableName);
		sb.append(" where ");
		sb.append(columnName);
		sb.append(" is not null");

		return sb.toString();
	}

	private String _getUpdateSQL(
		String columnName, String[] primaryKeyColumnNames, String tableName) {

		StringBundler sb = new StringBundler();

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set ");
		sb.append(columnName);
		sb.append(" = ? where ");

		for (String primaryKeyColumnName : primaryKeyColumnNames) {
			sb.append(primaryKeyColumnName);
			sb.append(" = ?");
			sb.append(" and ");
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private String _updateJakartaValue(
			char[] customSeparators, PreparedStatement preparedStatement,
			String[] primaryKeyColumnNames, Object[] values)
		throws SQLException {

		String javaxValue = (String)values[values.length - 1];

		if (javaxValue == null) {
			return null;
		}

		String jakartaValue;

		if (customSeparators.length > 0) {
			jakartaValue = JakartaUpgradeProcessUtil.replace(
				javaxValue, customSeparators);
		}
		else {
			jakartaValue = JakartaUpgradeProcessUtil.replace(javaxValue);
		}

		if (javaxValue.length() != jakartaValue.length()) {
			preparedStatement.setString(1, jakartaValue);

			for (int i = 0; i < primaryKeyColumnNames.length; i++) {
				preparedStatement.setObject(i + 2, values[i]);
			}

			preparedStatement.addBatch();

			StringBundler sb = new StringBundler("(");

			for (int i = 0; i < primaryKeyColumnNames.length; i++) {
				sb.append(values[i]);
				sb.append(", ");
			}

			sb.setIndex(sb.index() - 1);
			sb.append(")");

			return sb.toString();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseJakartaUpgradeProcess.class);

}