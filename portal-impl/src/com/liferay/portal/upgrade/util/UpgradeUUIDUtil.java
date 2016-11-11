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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Michael Bowerman
 */
public class UpgradeUUIDUtil {

	public static void upgradeUUID(
			Connection con, String tableName, String pkColumnName)
		throws SQLException {

		String selectSQL = getSelectSQL(tableName, pkColumnName);
		String updateSQL = getUpdateSQL(tableName, pkColumnName);

		try (PreparedStatement ps1 = con.prepareStatement(selectSQL);
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				con.prepareStatement(updateSQL))) {

			while (rs.next()) {
				long voteId = rs.getLong(1);

				ps2.setString(1, PortalUUIDUtil.generate());
				ps2.setLong(2, voteId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected static String getSelectSQL(
		String tableName, String pkColumnName) {

		StringBundler sb = new StringBundler(5);

		sb.append("select ");
		sb.append(pkColumnName);
		sb.append(" from ");
		sb.append(tableName);
		sb.append(" where uuid_ is null or uuid_ = ''");

		return sb.toString();
	}

	protected static String getUpdateSQL(
		String tableName, String pkColumnName) {

		StringBundler sb = new StringBundler(5);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set uuid_ = ? where ");
		sb.append(pkColumnName);
		sb.append(" = ?");

		return sb.toString();
	}

}