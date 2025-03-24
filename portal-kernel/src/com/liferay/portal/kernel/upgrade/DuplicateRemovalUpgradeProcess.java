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
			String[] duplicatedColumnValues)
		throws SQLException {

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

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			ResultSetMetaData metaData = resultSet.getMetaData();

			String[] columnNames = new String[metaData.getColumnCount()];

			for (int i = 0; i < columnNames.length; i++) {
				columnNames[i] = metaData.getColumnName(i + 1);
			}

			while (resultSet.next()) {
				Map<String, String> queryMap = new LinkedHashMap<>();

				for (String columnName : columnNames) {
					queryMap.put(columnName, resultSet.getString(columnName));
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

	private List<String[]> _getDuplicatedColumnEntries() throws Exception {
		List<String[]> duplicatedColumnEntries = new ArrayList<>();

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
				String[] columnValues = new String[_columnNames.length];

				for (int i = 0; i < columnValues.length; i++) {
					columnValues[i] = resultSet.getString(i + 1);
				}

				duplicatedColumnEntries.add(columnValues);
			}
		}

		return duplicatedColumnEntries;
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
		List<String[]> duplicatedColumnEntries = _getDuplicatedColumnEntries();

		for (String[] duplicatedColumnEntry : duplicatedColumnEntries) {
			List<Map<String, String>> duplicates = getDuplicatesSQL(
				duplicatedColumnEntry);

			int duplicatesCount = duplicates.size();

			for (Map<String, String> duplicate : duplicates) {
				if (duplicatesCount == 1) {
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
					sb.append(duplicate.get(primaryKeyColumnName));
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
							"Failed to remove duplicate entry: ",
							duplicate.toString(), " in ", _tableName, " for ",
							String.join(", ", _columnNames)),
						sqlException);
				}
				finally {
					_logDeletedDuplicates(duplicate);
					duplicatesCount--;
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