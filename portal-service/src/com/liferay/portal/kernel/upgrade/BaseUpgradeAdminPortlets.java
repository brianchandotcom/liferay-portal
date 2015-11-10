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
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.security.permission.ActionKeys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Juan Fernández
 * @author Sergio González
 */
public class BaseUpgradeAdminPortlets extends UpgradeProcess {

	protected void addResourcePermission(
			long resourcePermissionId, long companyId, String name, int scope,
			String primKey, long roleId, long actionIds)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"insert into ResourcePermission (resourcePermissionId, " +
					"companyId, name, scope, primKey, roleId, actionIds) " +
						"values (?, ?, ?, ?, ?, ?, ?)");

			ps.setLong(1, resourcePermissionId);
			ps.setLong(2, companyId);
			ps.setString(3, name);
			ps.setInt(4, scope);
			ps.setString(5, primKey);
			ps.setLong(6, roleId);
			ps.setLong(7, actionIds);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected long getBitwiseValue(String name, String actionId)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select bitwiseValue from ResourceAction where name = ? and " +
					"actionId = ?");

			ps.setString(1, name);
			ps.setString(2, actionId);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("bitwiseValue");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

	protected long getControlPanelGroupId() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select groupId from Group_ where name = '" +
					GroupConstants.CONTROL_PANEL + "'");

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("groupId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

	protected void updateAccessInControlPanelPermission(
			String portletFrom, String portletTo)
		throws Exception {

		long bitwiseValue = getBitwiseValue(
			portletFrom, ActionKeys.ACCESS_IN_CONTROL_PANEL);

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select * from ResourcePermission where name = ?");

			ps.setString(1, portletFrom);

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePermissionId = rs.getLong("resourcePermissionId");
				long actionIds = rs.getLong("actionIds");

				if ((actionIds & bitwiseValue) != 0) {
					actionIds = actionIds & (~bitwiseValue);

					runSQL(
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
						resourcePermissionId, companyId, portletTo, scope,
						primKey, roleId, actionIds);
				}
			}
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

}