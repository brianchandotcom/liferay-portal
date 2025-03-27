/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jorge Avalos
 */
public class DeleteDuplicateUniqueFinderRows extends UpgradeProcess {

	public DeleteDuplicateUniqueFinderRows(
		String tableName, String[] columnNames) {

		this(tableName, columnNames, null);
	}

	public DeleteDuplicateUniqueFinderRows(
		String tableName, String[] columnNames, String orderByClause) {

		_tableName = tableName;
		_columnNames = columnNames;
		_orderByClause = orderByClause;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<String[]> duplicateColumnValuesList =
			_getDuplicateColumnValuesList();

		for (String[] duplicateColumnValues : duplicateColumnValuesList) {
			List<Map<String, String>> duplicateRows = getDuplicateRows(
				duplicateColumnValues);

			int duplicateRowsCount = duplicateRows.size();

			for (Map<String, String> duplicateRow : duplicateRows) {
				if (duplicateRowsCount == 1) {
					break;
				}

				StringBundler sb = new StringBundler();

				sb.append("delete from ");
				sb.append(_tableName);
				sb.append(" where ");

				String[] primaryKeyColumnNames = getPrimaryKeyColumnNames(
					connection, _tableName);

				for (String primaryKeyColumnName : primaryKeyColumnNames) {
					sb.append(primaryKeyColumnName);
					sb.append(" = ");
					sb.append(duplicateRow.get(primaryKeyColumnName));
					sb.append("and ");
				}

				sb.setIndex(sb.index() - 1);

				try (PreparedStatement preparedStatement =
						connection.prepareStatement(sb.toString())) {

					preparedStatement.execute();
				}
				catch (SQLException sqlException) {
					_log.error(
						StringBundler.concat(
							"Failed to delete duplicate row from table ",
							_tableName, " for index columns (",
							String.join(", ", _columnNames), "): ",
							duplicateRow.toString()),
						sqlException);
				}
				finally {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Deleted duplicate row from table ", _tableName,
								" for index columns (",
								String.join(", ", _columnNames), "): ",
								duplicateRow.toString()));
					}

					duplicateRowsCount--;
				}
			}
		}
	}

	protected List<Map<String, String>> getDuplicateRows(
			String[] duplicateColumnValues)
		throws SQLException {

		List<Map<String, String>> duplicateRows = new ArrayList<>();

		StringBundler sb = new StringBundler();

		sb.append("select * from ");
		sb.append(_tableName);
		sb.append(" where ");

		for (int i = 0; i < _columnNames.length; i++) {
			sb.append(_columnNames[i]);

			if (duplicateColumnValues[i] == null) {
				sb.append(" is null ");
			}
			else {
				sb.append(" = ? ");
			}

			sb.append("and ");
		}

		sb.setIndex(sb.index() - 1);

		if (_orderByClause != null) {
			sb.append("order by ");
			sb.append(_orderByClause);
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBInspector dbInspector = new DBInspector(connection);

			int parameterIndex = 1;

			for (int i = 0; i < _columnNames.length; i++) {
				if (duplicateColumnValues[i] == null) {
					continue;
				}

				try (ResultSet resultSet = databaseMetaData.getColumns(
						dbInspector.getCatalog(), dbInspector.getSchema(),
						dbInspector.normalizeName(_tableName, databaseMetaData),
						dbInspector.normalizeName(
							_columnNames[i], databaseMetaData))) {

					resultSet.next();

					preparedStatement.setObject(
						parameterIndex, duplicateColumnValues[i],
						resultSet.getInt("DATA_TYPE"));

					parameterIndex++;
				}
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				ResultSetMetaData metaData = resultSet.getMetaData();

				String[] columnNames = new String[metaData.getColumnCount()];

				for (int i = 0; i < columnNames.length; i++) {
					columnNames[i] = metaData.getColumnName(i + 1);
				}

				while (resultSet.next()) {
					Map<String, String> duplicateRow = new LinkedHashMap<>();

					for (String columnName : columnNames) {
						duplicateRow.put(
							dbInspector.normalizeName(columnName),
							resultSet.getString(columnName));
					}

					duplicateRows.add(duplicateRow);
				}
			}
		}

		if (_orderByClause == null) {
			Collections.reverse(duplicateRows);
		}

		return duplicateRows;
	}

	private List<String[]> _getDuplicateColumnValuesList() throws Exception {
		List<String[]> duplicateColumnValuesList = new ArrayList<>();

		StringBundler sb = new StringBundler(7);

		sb.append("select ");
		sb.append(String.join(", ", _columnNames));
		sb.append(" from ");
		sb.append(_tableName);
		sb.append(" group by ");
		sb.append(String.join(", ", _columnNames));
		sb.append(" having count(*) > 1");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String[] duplicateColumnValues =
					new String[_columnNames.length];

				for (int i = 0; i < duplicateColumnValues.length; i++) {
					duplicateColumnValues[i] = resultSet.getString(i + 1);
				}

				duplicateColumnValuesList.add(duplicateColumnValues);
			}
		}

		return duplicateColumnValuesList;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteDuplicateUniqueFinderRows.class);

	private final String[] _columnNames;
	private final String _orderByClause;
	private final String _tableName;

}