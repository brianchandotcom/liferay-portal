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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Adolfo Pérez
 */
public class ReplacePortletId extends UpgradePortletId {

	protected boolean isResourceActionPresent(String name) throws SQLException {
		String sql =
			"SELECT COUNT(resourceActionId) FROM ResourceAction WHERE name = ?";

		return isRowPresent(name, sql);
	}

	protected boolean isResourcePermissionPresent(String newName)
		throws SQLException {

		String sql =
			"SELECT COUNT(resourcePermissionId) FROM ResourcePermission " +
				"WHERE name = ?";

		return isRowPresent(newName, sql);
	}

	protected boolean isRowPresent(String name, String sql)
		throws SQLException {

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, name);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	@Override
	protected void updateResourceAction(String oldName, String newName)
		throws Exception {

		if (isResourceActionPresent(newName)) {
			String sql = "delete from ResourceAction where name = ?";

			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setString(1, oldName);

				ps.execute();
			}
		}
		else {
			super.updateResourceAction(oldName, newName);
		}
	}

	@Override
	protected void updateResourcePermission(
			String oldRootPortletId, String newRootPortletId,
			boolean updateName)
		throws Exception {

		if (isResourcePermissionPresent(newRootPortletId)) {
			String sql = "delete from ResourcePermission where name = ?";

			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setString(1, oldRootPortletId);

				ps.execute();
			}
		}
		else {
			super.updateResourcePermission(
				oldRootPortletId, newRootPortletId, updateName);
		}
	}

}