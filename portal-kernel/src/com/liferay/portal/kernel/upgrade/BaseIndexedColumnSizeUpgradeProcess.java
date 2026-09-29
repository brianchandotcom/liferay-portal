/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Marcela Cunha
 */
public abstract class BaseIndexedColumnSizeUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DB db = DBManagerUtil.getDB();

		DBType dbType = db.getDBType();

		String lengthFunctionName = "CHAR_LENGTH";
		String substringFunctionName = "SUBSTRING";

		if (dbType == DBType.ORACLE) {
			lengthFunctionName = "LENGTH";
			substringFunctionName = "SUBSTR";
		}
		else if (dbType == DBType.SQLSERVER) {
			lengthFunctionName = "LEN";
		}

		String[][] tableAndColumnNames = getTableAndColumnNames();

		Set<String> columnNames = new HashSet<>();

		for (String[] tableAndColumnName : tableAndColumnNames) {
			columnNames.add(StringUtil.merge(tableAndColumnName, "."));
		}

		int maxColumnLength = getMaxColumnLength();

		for (String[] tableAndUniqueIndexColumnNames :
				getTableAndUniqueIndexColumnNames()) {

			List<String> groupByExpressions = new ArrayList<>();
			List<String> havingExpressions = new ArrayList<>();

			String tableName = tableAndUniqueIndexColumnNames[0];

			String[] uniqueIndexColumnNames = ArrayUtil.subset(
				tableAndUniqueIndexColumnNames, 1,
				tableAndUniqueIndexColumnNames.length);

			for (String uniqueIndexColumnName : uniqueIndexColumnNames) {
				if (!columnNames.contains(
						tableName + "." + uniqueIndexColumnName)) {

					groupByExpressions.add(uniqueIndexColumnName);

					continue;
				}

				groupByExpressions.add(
					StringBundler.concat(
						substringFunctionName, "(", uniqueIndexColumnName,
						", 1, ", maxColumnLength, ")"));
				havingExpressions.add(
					StringBundler.concat(
						"max(", lengthFunctionName, "(", uniqueIndexColumnName,
						")) > ", maxColumnLength));
			}

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"select 1 from ", tableName, " group by ",
							StringUtil.merge(groupByExpressions, ", "),
							" having count(*) > 1 and (",
							StringUtil.merge(havingExpressions, " or "), ")"));

				ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next()) {
					throw new UpgradeException(
						StringBundler.concat(
							"Unable to truncate \"", tableName,
							"\" because it would produce duplicate entries in ",
							"the unique index on \"",
							StringUtil.merge(uniqueIndexColumnNames, ", "),
							"\""));
				}
			}
		}

		List<IndexMetadata> indexMetadatas = new ArrayList<>();

		for (String[] tableAndColumnName : tableAndColumnNames) {
			String tableName = tableAndColumnName[0];
			String columnName = tableAndColumnName[1];

			runSQL(
				StringBundler.concat(
					"update ", tableName, " set ", columnName, " = ",
					substringFunctionName, "(", columnName, ", 1, ",
					maxColumnLength, ") where ", lengthFunctionName, "(",
					columnName, ") > ", maxColumnLength));

			indexMetadatas.addAll(dropIndexes(tableName, columnName));

			alterColumnType(
				tableName, columnName,
				StringBundler.concat("VARCHAR(", maxColumnLength, ") null"));
		}

		addIndexes(connection, indexMetadatas);
	}

	protected abstract int getMaxColumnLength();

	protected abstract String[][] getTableAndColumnNames();

	protected abstract String[][] getTableAndUniqueIndexColumnNames();

}