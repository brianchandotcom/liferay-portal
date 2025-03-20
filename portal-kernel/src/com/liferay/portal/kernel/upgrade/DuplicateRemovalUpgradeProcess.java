/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

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
public class DuplicateRemovalUpgradeProcess extends UpgradeProcess {

	public DuplicateRemovalUpgradeProcess(String tableName, String columns) {
		_tableName = tableName;
		_columns = columns;

		_orderByClause = null;
	}

	public DuplicateRemovalUpgradeProcess(
		String tableName, String columns, String orderByColumns,
		String sortOrder) {

		_tableName = tableName;
		_columns = columns;

		_orderByClause = _getOrderByClause(orderByColumns, sortOrder);
	}

	@Override
	protected void doUpgrade() {
		_removeDuplicates();
	}

	protected List<Map<String, String>> getDuplicatesSQL(
		String[] indexDuplicateColumns) {

		List<Map<String, String>> queryResult = new ArrayList<>();

		String[] columns = _columns.split(", ");

		StringBundler sb = new StringBundler();

		sb.append("SELECT * FROM ");
		sb.append(_tableName);
		sb.append(" WHERE ");

		for (int i = 0; i < indexDuplicateColumns.length; i++) {
			sb.append(columns[i]);

			if (indexDuplicateColumns[i] == null) {
				sb.append(" IS NULL ");
			}
			else {
				sb.append(" = '");
				sb.append(_escape(indexDuplicateColumns[i]));
				sb.append("' ");
			}

			if (i < (columns.length - 1)) {
				sb.append("AND ");
			}
		}

		if (_orderByClause != null) {
			sb.append(_orderByClause);
		}

		String sql = sb.toString();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			ResultSetMetaData metaData = resultSet.getMetaData();

			int columnCount = metaData.getColumnCount();

			String[] columnNames = new String[columnCount];

			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnName(i);

				columnNames[i - 1] = columnName;
			}

			while (resultSet.next()) {
				Map<String, String> queryMap = new LinkedHashMap<>();

				for (int i = 0; i < columnCount; i++) {
					String value = resultSet.getString(columnNames[i]);

					queryMap.put(columnNames[i], value);
				}

				queryResult.add(queryMap);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		if (_orderByClause == null) {
			Collections.reverse(queryResult);
		}

		return queryResult;
	}

	private String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _DB_ESCAPE_STRINGS[0],
			_DB_ESCAPE_STRINGS[1]);
	}

	private List<String[]> _getIndexDuplicatesList() {
		List<String[]> indexesDuplicatesList = new ArrayList<>();

		StringBundler sb = new StringBundler(7);

		sb.append("SELECT ");
		sb.append(_columns);
		sb.append(" FROM ");
		sb.append(_tableName);
		sb.append(" GROUP BY ");
		sb.append(_columns);
		sb.append(" HAVING COUNT(*) > 1");

		String sql = sb.toString();

		try (PreparedStatement preparedStatement =
				 connection.prepareStatement(sql);

			 ResultSet resultSet = preparedStatement.executeQuery()) {

			ResultSetMetaData metaData = resultSet.getMetaData();

			int columnCount = metaData.getColumnCount();

			while (resultSet.next()) {
				String[] columnResults = new String[columnCount];

				for (int i = 1; i <= columnCount; i++) {
					String value = resultSet.getString(i);

					columnResults[i - 1] = value;
				}

				indexesDuplicatesList.add(columnResults);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return indexesDuplicatesList;
	}

	private String _getOrderByClause(String orderByColumns, String sortOrder) {
		if ((orderByColumns != null) || (sortOrder != null)) {
			StringBuilder ordeByClause = new StringBuilder();

			ordeByClause.append("ORDER BY ");

			String[] orderByArray = orderByColumns.split(", ");

			for (int i = 0; i < orderByArray.length; i++) {
				while (i < (orderByArray.length - 1)) {
					ordeByClause.append(
						orderByArray[i]
					).append(
						" "
					);

					ordeByClause.append(
						sortOrder
					).append(
						", "
					);
				}

				ordeByClause.append(
					orderByArray[i]
				).append(
					" "
				);

				ordeByClause.append(sortOrder);
			}

			return ordeByClause.toString();
		}

		return null;
	}

	private void _logDeletedDuplicates(Map<String, String> duplicate) {
		if (_log.isWarnEnabled()) {
			_log.warn(
				StringBundler.concat(
					"Deleted duplicate entry from ", _tableName,
					" table for index columns (", _columns, "): ",
					duplicate.toString()));
		}
	}

	private void _removeDuplicates() {
		List<String[]> indexDuplicatesList = _getIndexDuplicatesList();

		for (String[] indexDuplicateColumns : indexDuplicatesList) {
			List<Map<String, String>> duplicatesList = getDuplicatesSQL(
				indexDuplicateColumns);

			int duplicateCount = duplicatesList.size();

			for (Map<String, String> duplicate : duplicatesList) {
				if (duplicateCount == 1) {
					break;
				}

				StringBundler sb = new StringBundler();

				sb.append("DELETE FROM ");
				sb.append(_tableName);
				sb.append(" WHERE ");

				int counter = 0;

				for (Map.Entry<String, String> querySet :
						duplicate.entrySet()) {

					sb.append(querySet.getKey());

					if (querySet.getValue() == null) {
						sb.append(" IS NULL ");
					}
					else {
						sb.append(" = '");
						sb.append(_escape(querySet.getValue()));
						sb.append("' ");
					}

					if (counter < (duplicate.size() - 1)) {
						sb.append("AND ");
					}

					counter++;
				}

				String sql = sb.toString();

				try (PreparedStatement preparedStatement1 =
						connection.prepareStatement(sql)) {

					preparedStatement1.execute();
				}
				catch (SQLException sqlException) {
					_log.error(
						StringBundler.concat(
							"Failed to remove duplicate entry: ",
							duplicate.toString(), " in ", _tableName, " for ",
							_columns),
						sqlException);
				}
				finally {
					_logDeletedDuplicates(duplicate);
					duplicateCount--;
				}
			}
		}
	}

	private static final String[][] _DB_ESCAPE_STRINGS = {
		{"\\", "'"}, {"\\\\", "''"}
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DuplicateRemovalUpgradeProcess.class);

	private final String _columns;
	private final String _orderByClause;
	private final String _tableName;

}