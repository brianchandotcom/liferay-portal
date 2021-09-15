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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.BaseCompanyIdUpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alberto Chaparro
 */
public class UpgradeCompanyId extends BaseCompanyIdUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (TableUpdater tableUpdater : getTableUpdaters()) {
			if (!hasColumn(tableUpdater.getTableName(), "companyId")) {
				tableUpdater.setCreateCompanyIdColumn(true);
			}
		}

		super.doUpgrade();
	}

	@Override
	protected TableUpdater[] getTableUpdaters() {
		return new TableUpdater[] {
			new PortalPreferencesTableUpdater("PortalPreferences"),
			new TableUpdater(
				"PortalPreferenceValue", "PortalPreferences",
				"portalPreferencesId")
		};
	}

	private class PortalPreferencesTableUpdater extends TableUpdater {

		public PortalPreferencesTableUpdater(String tableName) {
			super(tableName, "", "");
		}

		@Override
		public void update(Connection connection)
			throws IOException, SQLException {

			List<Long> companyIds = getCompanyIds(connection);

			if (companyIds.size() == 1) {
				String selectSQL = String.valueOf(companyIds.get(0));

				runSQL(connection, getUpdateSQL(selectSQL));

				return;
			}

			// Company

			String updateSQL = _getUpdateSQL(
				"Company", "companyId", "ownerId",
				PortletKeys.PREFS_OWNER_TYPE_COMPANY);

			runSQL(connection, updateSQL);

			// Group

			updateSQL = _getUpdateSQL(
				"Group_", "groupId", "ownerId",
				PortletKeys.PREFS_OWNER_TYPE_GROUP);

			runSQL(connection, updateSQL);

			// Layout

			updateSQL = _getUpdateSQL(
				"Layout", "plid", "ownerId",
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT);

			runSQL(connection, updateSQL);

			// LayoutRevision

			updateSQL = _getUpdateSQL(
				"LayoutRevision", "layoutRevisionId", "ownerId",
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT);

			runSQL(connection, updateSQL);

			// Organization

			updateSQL = _getUpdateSQL(
				"Organization_", "organizationId", "ownerId",
				PortletKeys.PREFS_OWNER_TYPE_ORGANIZATION);

			runSQL(connection, updateSQL);

			// PortletItem

			updateSQL = _getUpdateSQL(
				"PortletItem", "portletItemId", "ownerId",
				PortletKeys.PREFS_OWNER_TYPE_ARCHIVED);

			runSQL(connection, updateSQL);

			// User_

			updateSQL = _getUpdateSQL(
				"User_", "userId", "ownerId",
				PortletKeys.PREFS_OWNER_TYPE_USER);

			runSQL(connection, updateSQL);

			// PortalPreferences companyId 0

			runSQL(
				connection,
				"update PortalPreferences set companyId = 0 where ownerId = 0");
		}

		private String _getSelectSQL(
				String foreignTableName, String foreignColumnName,
				String columnName)
			throws SQLException {

			List<Long> companyIds = new ArrayList<>();

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select distinct companyId from " + foreignTableName);
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					long companyId = resultSet.getLong(1);

					companyIds.add(companyId);
				}
			}

			if (companyIds.size() == 1) {
				return String.valueOf(companyIds.get(0));
			}

			return StringBundler.concat(
				"select companyId from ", foreignTableName, " where ",
				foreignTableName, ".", foreignColumnName, " = ", getTableName(),
				".", columnName);
		}

		private String _getUpdateSQL(
				String foreignTableName, String foreignColumnName,
				String columnName, int ownerType)
			throws IOException, SQLException {

			String selectSQL = _getSelectSQL(
				foreignTableName, foreignColumnName, columnName);

			return StringBundler.concat(
				getUpdateSQL(selectSQL), " where ownerType = ", ownerType,
				" and (companyId is null or companyId = 0)");
		}

	}

}