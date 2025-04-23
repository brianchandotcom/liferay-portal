/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.JakartaUpgradeProcessUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Luis Ortiz
 */
public abstract class BaseJakartaUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Map<String, Map<String, Set<String>>> modifiedData = new HashMap<>();

		for (String[] tableAndColumnNames : getTableAndColumnNames()) {
			String columnName = tableAndColumnNames[1];

			String tableName = tableAndColumnNames[0];

			String[] primaryKeyColumnNames = getPrimaryKeyColumnNames(
				connection, tableName);

			StringBundler selectSB = new StringBundler();

			selectSB.append("select ");

			StringBundler updateSB = new StringBundler();

			updateSB.append("update ");
			updateSB.append(tableName);
			updateSB.append(" set ");
			updateSB.append(columnName);
			updateSB.append(" = ? where ");

			for (String primaryKeyColumnName : primaryKeyColumnNames) {
				selectSB.append(primaryKeyColumnName);
				selectSB.append(", ");

				updateSB.append(primaryKeyColumnName);
				updateSB.append(" = ?");
				updateSB.append(" and ");
			}

			updateSB.setIndex(updateSB.index() - 1);

			selectSB.append(columnName);
			selectSB.append(" from ");
			selectSB.append(tableName);

			processConcurrently(
				selectSB.toString(), updateSB.toString(),
				resultSet -> {
					Object[] result =
						new Object[primaryKeyColumnNames.length + 1];

					int i = 0;

					for (String primaryKeyColumnName : primaryKeyColumnNames) {
						result[i] = resultSet.getObject(primaryKeyColumnName);
						i++;
					}

					result[i] = resultSet.getString(columnName);

					return result;
				},
				(values, preparedStatement) -> {
					String javaxValue = (String)values[values.length - 1];
					String jakartaValue;

					if (getCustomSeparators().length > 0) {
						jakartaValue = JakartaUpgradeProcessUtil.replace(
							javaxValue,
							SetUtil.fromArray(getCustomSeparators()));
					}
					else {
						jakartaValue = JakartaUpgradeProcessUtil.replace(
							javaxValue);
					}

					if (javaxValue.length() != jakartaValue.length()) {
						int i = 1;

						for (String primaryKeyColumnName :
								primaryKeyColumnNames) {

							preparedStatement.setObject(i, values[i - 1]);
							i++;
						}

						preparedStatement.setString(i, jakartaValue);

						preparedStatement.addBatch();

						Map<String, Set<String>> modifiedColumns =
							modifiedData.computeIfAbsent(
								tableName, key -> new HashMap<>());

						Set<String> modifiedKeys =
							modifiedColumns.computeIfAbsent(
								columnName, key -> new HashSet<>());

						StringBundler keySB = new StringBundler();

						int j = 1;

						for (String primaryKeyColumnName :
								primaryKeyColumnNames) {

							keySB.append(values[j]);
							keySB.append(", ");
							j++;
						}

						keySB.setIndex(keySB.index() - 1);

						modifiedKeys.add(keySB.toString());
					}
				},
				StringBundler.concat(
					"Unable to update javax references in table ", tableName,
					" column ", columnName));
		}

		if (_log.isInfoEnabled()) {
			for (Map.Entry<String, Map<String, Set<String>>> entry :
					modifiedData.entrySet()) {

				String tableName = entry.getKey();
				Map<String, Set<String>> data = entry.getValue();

				for (Map.Entry<String, Set<String>> columnEntry :
						data.entrySet()) {

					String columnName = columnEntry.getKey();
					Set<String> values = columnEntry.getValue();

					StringBundler sb = new StringBundler();

					for (String value : values) {
						sb.append(value);
						sb.append(", ");
					}

					sb.setIndex(sb.index() - 1);

					_log.info(
						StringBundler.concat(
							"Table/column ", tableName, "/", columnName,
							" has been upgraded for next IDs: ",
							sb.toString()));
				}
			}
		}
	}

	protected Character[] getCustomSeparators() {
		return new Character[0];
	}

	protected abstract String[][] getTableAndColumnNames();

	private static final Log _log = LogFactoryUtil.getLog(
		BaseJakartaUpgradeProcess.class);

}