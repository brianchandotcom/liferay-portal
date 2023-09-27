/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Juan Fernández
 * @author Sergio González
 */
public abstract class BaseAdminPortletsUpgradeProcess extends UpgradeProcess {

	protected void addResourcePermission(
			long resourcePermissionId, long companyId, String name, int scope,
			String primKey, long roleId, long actionIds)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into ResourcePermission (resourcePermissionId, ",
					"companyId, name, scope, primKey, primKeyId, roleId, ",
					"actionIds, viewActionId) values (?, ?, ?, ?, ?, ?, ?, ?, ",
					"?)"))) {

			boolean viewActionId = false;

			if ((actionIds % 2) == 1) {
				viewActionId = true;
			}

			preparedStatement.setLong(1, resourcePermissionId);
			preparedStatement.setLong(2, companyId);
			preparedStatement.setString(3, name);
			preparedStatement.setInt(4, scope);
			preparedStatement.setString(5, primKey);
			preparedStatement.setLong(6, GetterUtil.getLong(primKey));
			preparedStatement.setLong(7, roleId);
			preparedStatement.setLong(8, actionIds);
			preparedStatement.setBoolean(9, viewActionId);

			preparedStatement.executeUpdate();
		}
	}

	protected long getBitwiseValue(String name, String actionId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select bitwiseValue from ResourceAction where name = ? and " +
					"actionId = ?")) {

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, actionId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("bitwiseValue");
				}

				return 0;
			}
		}
	}

	protected long getControlPanelGroupId() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select groupId from Group_ where name = '" +
					GroupConstants.CONTROL_PANEL + "'");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			if (resultSet.next()) {
				return resultSet.getLong("groupId");
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

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select * from ResourcePermission where name = ?")) {

				preparedStatement.setString(1, portletFrom);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						long actionIds = resultSet.getLong("actionIds");

						if ((actionIds & bitwiseValue) == 0) {
							continue;
						}

						actionIds = actionIds & ~bitwiseValue;

						long resourcePermissionId = resultSet.getLong(
							"resourcePermissionId");

						runSQL(
							StringBundler.concat(
								"update ResourcePermission set actionIds = ",
								actionIds, " where resourcePermissionId = ",
								resourcePermissionId));

						resourcePermissionId = increment(
							ResourcePermission.class.getName());

						long companyId = resultSet.getLong("companyId");
						int scope = resultSet.getInt("scope");
						String primKey = resultSet.getString("primKey");
						long roleId = resultSet.getLong("roleId");

						actionIds = resultSet.getLong("actionIds");

						actionIds |= bitwiseValue;

						addResourcePermission(
							resourcePermissionId, companyId, portletTo, scope,
							primKey, roleId, actionIds);
					}
				}
			}
		}
	}

}