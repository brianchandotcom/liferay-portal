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

	public DuplicateRemovalUpgradeProcess(
		String tableName, String[] columnNames) {

		this(tableName, columnNames, null);
	}

	public DuplicateRemovalUpgradeProcess(
		String tableName, String[] columnNames, String orderByClause) {

		_tableName = tableName;
		_columnNames = columnNames;
		_orderByClause = orderByClause;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_removeDuplicates();
	}

	protected List<Map<String, String>> getDuplicatesSQL(
		String[] duplicatedColumnValues) {

		List<Map<String, String>> queryResult = new ArrayList<>();

		StringBundler sb = new StringBundler();

		sb.append("select * from ");
		sb.append(_tableName);
		sb.append(" where ");

		for (int i = 0; i < _columnNames.length; i++) {
			sb.append(_columnNames[i]);

			if (duplicatedColumnValues[i] == null) {
				sb.append(" is null ");
			}
			else {
				sb.append(" = '");
				sb.append(_escape(duplicatedColumnValues[i]));
				sb.append("' ");
			}

			if (i < (_columnNames.length - 1)) {
				sb.append("and ");
			}
		}

		if (_orderByClause != null) {
			sb.append("order by ");
			sb.append(_orderByClause);
		}

		String sql = sb.toString();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			ResultSetMetaData metaData = resultSet.getMetaData();

			int columnCount = metaData.getColumnCount();

			String[] columnNames = new String[columnCount];

			for (int i = 0; i < columnCount; i++) {
				String columnName = metaData.getColumnName(i+1);

				columnNames[i] = columnName;
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

	private List<String[]> _getDuplicatedColumnValuesList() throws Exception {
		List<String[]> columnValuesList = new ArrayList<>();

		StringBundler sb = new StringBundler(7);

		sb.append("select ");
		sb.append(String.join(", ", _columnNames));
		sb.append(" from ");
		sb.append(_tableName);
		sb.append(" group by ");
		sb.append(String.join(", ", _columnNames));
		sb.append(" having count(*) > 1");

		String sql = sb.toString();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String[] columnValues = new String[_columnNames.length];

				for (int i = 0; i < columnValues.length; i++) {
					columnValues[i] = resultSet.getString(i + 1);
				}

				columnValuesList.add(columnValues);
			}
		}

		return columnValuesList;
	}

	private void _logDeletedDuplicates(Map<String, String> duplicate) {
		if (_log.isWarnEnabled()) {
			_log.warn(
				StringBundler.concat(
					"Deleted duplicate entry from ", _tableName,
					" table for index columns (",
					String.join(", ", _columnNames), "): ",
					duplicate.toString()));
		}
	}

	private void _removeDuplicates() throws Exception {
		List<String[]> duplicatedColumnValuesList = _getDuplicatedColumnValuesList();

		for (String[] duplicatedColumnValues : duplicatedColumnValuesList) {
			List<Map<String, String>> duplicatesList = getDuplicatesSQL(
				duplicatedColumnValues);

			int duplicateCount = duplicatesList.size();

			for (Map<String, String> duplicate : duplicatesList) {
				if (duplicateCount == 1) {
					break;
				}

				StringBundler sb = new StringBundler();

				sb.append("delete from ");
				sb.append(_tableName);
				sb.append(" where ");

				int counter = 0;

				for (Map.Entry<String, String> querySet :
						duplicate.entrySet()) {

					sb.append(querySet.getKey());

					if (querySet.getValue() == null) {
						sb.append(" is null ");
					}
					else {
						sb.append(" = '");
						sb.append(_escape(querySet.getValue()));
						sb.append("' ");
					}

					if (counter < (duplicate.size() - 1)) {
						sb.append("and ");
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
							String.join(", ", _columnNames)),
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

	private final String[] _columnNames;
	private final String _orderByClause;
	private final String _tableName;

}