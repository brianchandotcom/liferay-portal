/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Collection;
import java.util.Map;

/**
 * @author Preston Crary
 */
public class CTRowUtil {

	public static int copyCTRows(
			CTPersistence<?> ctPersistence, Connection connection,
			String selectSQL)
		throws SQLException {

		Map<String, Integer> tableColumnsMap =
			ctPersistence.getTableColumnsMap();

		if (_isPostgresBlobTable(tableColumnsMap)) {
			StringBundler sb = new StringBundler(
				(3 * tableColumnsMap.size()) + 4);

			sb.append("insert into ");
			sb.append(ctPersistence.getTableName());
			sb.append(" (");

			for (String columnName : tableColumnsMap.keySet()) {
				sb.append(columnName);
				sb.append(", ");
			}

			sb.setStringAt(") values (?", sb.index() - 1);

			for (int i = 1; i < tableColumnsMap.size(); i++) {
				sb.append(", ?");
			}

			sb.append(")");

			try (PreparedStatement selectPreparedStatement =
					connection.prepareStatement(selectSQL);
				PreparedStatement insertPreparedStatement =
					connection.prepareStatement(sb.toString());
				ResultSet resultSet = selectPreparedStatement.executeQuery()) {

				while (resultSet.next()) {
					int parameterIndex = 1;

					for (int type : tableColumnsMap.values()) {
						if (type == Types.BLOB) {
							Blob blob = resultSet.getBlob(parameterIndex);

							insertPreparedStatement.setBlob(
								parameterIndex, blob.getBinaryStream());
						}
						else {
							insertPreparedStatement.setObject(
								parameterIndex,
								resultSet.getObject(parameterIndex));
						}

						parameterIndex++;
					}

					insertPreparedStatement.addBatch();
				}

				int result = 0;

				for (int count : insertPreparedStatement.executeBatch()) {
					result += count;
				}

				return result;
			}
		}

		StringBundler sb = new StringBundler((2 * tableColumnsMap.size()) + 4);

		sb.append("insert into ");
		sb.append(ctPersistence.getTableName());
		sb.append(" (");

		for (String name : tableColumnsMap.keySet()) {
			sb.append(name);
			sb.append(", ");
		}

		sb.setStringAt(") ", sb.index() - 1);

		sb.append(selectSQL);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			return preparedStatement.executeUpdate();
		}
	}

	public static String getConstraintConflictsSQL(
		String tableName, String primaryColumnName,
		String[] uniqueIndexColumnNames, long sourceCTCollectionId,
		long targetCTCollectionId) {

		StringBundler sb = new StringBundler(
			(9 * uniqueIndexColumnNames.length) + 17);

		sb.append("select sourceTable.");
		sb.append(primaryColumnName);
		sb.append(" as sourcePK, targetTable.");
		sb.append(primaryColumnName);
		sb.append(" as targetPK from ");
		sb.append(tableName);
		sb.append(" sourceTable inner join ");
		sb.append(tableName);
		sb.append(" targetTable on sourceTable.");
		sb.append(primaryColumnName);
		sb.append(" != targetTable.");
		sb.append(primaryColumnName);
		sb.append(" and sourceTable.ctCollectionId = ");
		sb.append(sourceCTCollectionId);
		sb.append(" and targetTable.ctCollectionId = ");
		sb.append(targetCTCollectionId);

		for (String uniqueIndexColumnName : uniqueIndexColumnNames) {
			sb.append(" and ((sourceTable.");
			sb.append(uniqueIndexColumnName);
			sb.append(" = targetTable.");
			sb.append(uniqueIndexColumnName);
			sb.append(") or (sourceTable.");
			sb.append(uniqueIndexColumnName);
			sb.append(" is null and targetTable.");
			sb.append(uniqueIndexColumnName);
			sb.append(" is null))");
		}

		return sb.toString();
	}

	private static boolean _isPostgresBlobTable(
		Map<String, Integer> tableColumnsMap) {

		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.POSTGRESQL) {
			return false;
		}

		Collection<Integer> values = tableColumnsMap.values();

		if (values.contains(Types.BLOB)) {
			return true;
		}

		return false;
	}

	private CTRowUtil() {
	}

}