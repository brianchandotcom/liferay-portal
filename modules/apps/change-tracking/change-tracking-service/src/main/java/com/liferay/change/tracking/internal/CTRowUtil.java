/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.registry.CTModelRegistration;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;

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
			CTModelRegistration ctModelRegistration, Connection connection,
			String selectSQL)
		throws SQLException {

		Map<String, Integer> tableColumnsMap =
			ctModelRegistration.getTableColumnsMap();

		if (_isPostgresBlobTable(tableColumnsMap)) {
			StringBundler sb = new StringBundler(
				3 * tableColumnsMap.size() + 4);

			sb.append("insert into ");
			sb.append(ctModelRegistration.getTableName());
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

			try (PreparedStatement selectPS = connection.prepareStatement(
					selectSQL);
				PreparedStatement insertPS = connection.prepareStatement(
					sb.toString());
				ResultSet rs = selectPS.executeQuery()) {

				while (rs.next()) {
					int parameterIndex = 1;

					for (int type : tableColumnsMap.values()) {
						if (type == Types.BLOB) {
							Blob blob = rs.getBlob(parameterIndex);

							insertPS.setBlob(
								parameterIndex, blob.getBinaryStream());
						}
						else {
							insertPS.setObject(
								parameterIndex, rs.getObject(parameterIndex));
						}

						parameterIndex++;
					}

					insertPS.addBatch();
				}

				int result = 0;

				for (int count : insertPS.executeBatch()) {
					result += count;
				}

				return result;
			}
		}

		StringBundler sb = new StringBundler(2 * tableColumnsMap.size() + 4);

		sb.append("insert into ");
		sb.append(ctModelRegistration.getTableName());
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