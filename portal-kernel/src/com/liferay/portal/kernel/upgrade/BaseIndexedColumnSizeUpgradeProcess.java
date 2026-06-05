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
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

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

		String columnName = getColumnName();
		int maxColumnLength = getMaxColumnLength();
		String tableName = getTableName();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select 1 from ", tableName, " group by ",
					StringUtil.merge(getGroupByColumnNames(), ", "), ", ",
					substringFunctionName, "(", columnName, ", 1, ",
					maxColumnLength, ") having count(*) > 1 and max(",
					lengthFunctionName, "(", columnName, ")) > ",
					maxColumnLength));

			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				throw new UpgradeException(
					StringBundler.concat(
						"Unable to truncate \"", columnName, "\" in \"",
						tableName, "\" because it would produce duplicate ",
						"unique index entries"));
			}
		}

		runSQL(
			StringBundler.concat(
				"update ", tableName, " set ", columnName, " = ",
				substringFunctionName, "(", columnName, ", 1, ",
				maxColumnLength, ") where ", lengthFunctionName, "(",
				columnName, ") > ", maxColumnLength));

		List<IndexMetadata> indexMetadatas = dropIndexes(tableName, columnName);

		alterColumnType(
			tableName, columnName,
			StringBundler.concat("VARCHAR(", maxColumnLength, ") null"));

		addIndexes(connection, indexMetadatas);
	}

	protected abstract String getColumnName();

	protected abstract String[] getGroupByColumnNames();

	protected abstract int getMaxColumnLength();

	protected abstract String getTableName();

}