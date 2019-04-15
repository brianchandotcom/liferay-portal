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

import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Juan Fernández
 * @author Sergio González
 */
public abstract class BaseUpgradeAdminPortlets extends UpgradeProcess {

	protected void addResourcePermission(
			long resourcePermissionId, long companyId, String name, int scope,
			String primKey, long roleId, long actionIds)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into ResourcePermission (resourcePermissionId, " +
					"companyId, name, scope, primKey, roleId, actionIds) " +
						"values (?, ?, ?, ?, ?, ?, ?)")) {

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

	protected long getBitwiseValue(String name, String actionId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select bitwiseValue from ResourceAction where name = ? and " +
					"actionId = ?")) {

			ps.setString(1, name);
			ps.setString(2, actionId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("bitwiseValue");
				}

				return 0;
			}
		}
	}

	protected long getControlPanelGroupId() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId from Group_ where name = '" +
					GroupConstants.CONTROL_PANEL + "'");
			ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return rs.getLong("groupId");
			}

			return 0;
		}
	}

	protected void updateAccessInControlPanelPermission(
			String portletFrom, String portletTo)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long bitwiseValue = getBitwiseValue(
				portletFrom, ActionKeys.ACCESS_IN_CONTROL_PANEL);

			try (PreparedStatement ps = connection.prepareStatement(
					"select * from ResourcePermission where name = ?")) {

				ps.setString(1, portletFrom);

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						long actionIds = rs.getLong("actionIds");

						if ((actionIds & bitwiseValue) != 0) {
							actionIds = actionIds & ~bitwiseValue;

							long resourcePermissionId = rs.getLong(
								"resourcePermissionId");

							runSQL(
								StringBundler.concat(
									"update ResourcePermission set actionIds ",
									"= ", String.valueOf(actionIds),
									" where resourcePermissionId = ",
									String.valueOf(resourcePermissionId)));

							resourcePermissionId = increment(
								ResourcePermission.class.getName());

							long companyId = rs.getLong("companyId");
							int scope = rs.getInt("scope");
							String primKey = rs.getString("primKey");
							long roleId = rs.getLong("roleId");

							actionIds = rs.getLong("actionIds");

							actionIds |= bitwiseValue;

							addResourcePermission(
								resourcePermissionId, companyId, portletTo,
								scope, primKey, roleId, actionIds);
						}
					}
				}
			}
		}
	}

}