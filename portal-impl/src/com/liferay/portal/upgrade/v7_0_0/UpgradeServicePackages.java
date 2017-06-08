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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Preston Crary
 */
public class UpgradeServicePackages extends UpgradeProcess {

	public static final String[][] CLASS_NAMES = new String[][] {
		{
			"com.liferay.portlet.announcements.model.AnnouncementsDelivery",
			"com.liferay.announcements.kernel.model.AnnouncementsDelivery"
		},
		{
			"com.liferay.portlet.announcements.model.AnnouncementsEntry",
			"com.liferay.announcements.kernel.model.AnnouncementsEntry"
		},
		{
			"com.liferay.portlet.announcements.model.AnnouncementsFlag",
			"com.liferay.announcements.kernel.model.AnnouncementsFlag"
		}
	};

	@Override
	protected void doUpgrade() throws SQLException {
		upgradeTable("Counter", "name");
		upgradeTable("ClassName_", "value");
		upgradeTable("ResourceBlock", "name");
		upgradeTable("ResourcePermission", "name");
	}

	protected String[][] getClassNames() {
		return CLASS_NAMES;
	}

	protected void upgradeTable(String tableName, String columnName)
		throws SQLException {

		StringBundler updateSB = new StringBundler(7);

		updateSB.append("update ");
		updateSB.append(tableName);
		updateSB.append(" set ");
		updateSB.append(columnName);
		updateSB.append(" = ? where ");
		updateSB.append(columnName);
		updateSB.append(" = ?");

		String updateSQL = updateSB.toString();

		for (String[] className : getClassNames()) {
			StringBundler selectSB = new StringBundler(9);

			selectSB.append("select ");
			selectSB.append(columnName);
			selectSB.append(" from ");
			selectSB.append(tableName);
			selectSB.append(" where ");
			selectSB.append(columnName);
			selectSB.append(" like '%");
			selectSB.append(className[0]);
			selectSB.append("%'");

			upgradeTable(columnName, selectSB.toString(), updateSQL, className);
		}
	}

	protected void upgradeTable(
			String columnName, String selectSQL, String updateSQL,
			String[] className)
		throws SQLException {

		try (PreparedStatement ps1 = connection.prepareStatement(selectSQL);
				ResultSet rs = ps1.executeQuery();
					PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.autoBatch(
							connection.prepareStatement(updateSQL))) {

			while (rs.next()) {
				String oldName = rs.getString(columnName);

				String newName = StringUtil.replace(
					oldName, className[0], className[1]);

				ps2.setString(1, newName);
				ps2.setString(2, oldName);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}