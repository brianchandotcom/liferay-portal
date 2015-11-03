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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.security.permission.ActionKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Juan Fernández
 * @author Sergio González
 */
public class BaseUpgradeAdminPortlets extends UpgradeProcess {

	protected void addResourcePermission(
			Connection con, long resourcePermissionId, long companyId,
			String name, int scope, String primKey, long roleId, long actionIds)
		throws Exception {

		String sql =
			"insert into ResourcePermission (resourcePermissionId, " +
				"companyId, name, scope, primKey, roleId, actionIds) values " +
					"(?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setLong(1, resourcePermissionId);
			ps.setLong(2, companyId);
			ps.setString(3, name);
			ps.setInt(4, scope);
			ps.setString(5, primKey);
			ps.setLong(6, roleId);
			ps.setLong(7, actionIds);

			ps.executeUpdate();
		}
	}

	protected long getBitwiseValue(Connection con, String name, String actionId)
		throws Exception {

		String sql =
			"select bitwiseValue from ResourceAction where name = ? and " +
				"actionId = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, name);
			ps.setString(2, actionId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("bitwiseValue");
				}
			}

			return 0;
		}
	}

	protected void updateAccessInControlPanelPermission(
			String portletFrom, String portletTo)
		throws Exception {

		String sql = "select * from ResourcePermission where name = ?";

		try (Connection con = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, portletFrom);

			long bitwiseValue = getBitwiseValue(
				con, portletFrom, ActionKeys.ACCESS_IN_CONTROL_PANEL);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long resourcePermissionId = rs.getLong(
						"resourcePermissionId");
					long actionIds = rs.getLong("actionIds");

					if ((actionIds & bitwiseValue) != 0) {
						actionIds &= (~bitwiseValue);

						runSQL(
							con,
							"update ResourcePermission set actionIds = " +
								actionIds + " where resourcePermissionId = " +
									resourcePermissionId);

						resourcePermissionId = increment(
							ResourcePermission.class.getName());

						long companyId = rs.getLong("companyId");
						int scope = rs.getInt("scope");
						String primKey = rs.getString("primKey");
						long roleId = rs.getLong("roleId");

						actionIds = rs.getLong("actionIds");

						actionIds |= bitwiseValue;

						addResourcePermission(
							con, resourcePermissionId, companyId, portletTo,
							scope, primKey, roleId, actionIds);
					}
				}
			}
		}
	}

}