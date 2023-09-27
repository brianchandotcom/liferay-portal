/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v6_1_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class UpgradeLayoutSetBranch extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateLayoutSetBranches();
	}

	protected void updateLayoutSetBranch(
			long layoutSetBranchId, String themeId, String colorSchemeId,
			String wapThemeId, String wapColorSchemeId, String css,
			String settings, String layoutSetPrototypeUuid,
			boolean layoutSetPrototypeLinkEnabled)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("update LayoutSetBranch set themeId = ?, colorSchemeId = ");
		sb.append("?, wapThemeId = ?, wapColorSchemeId = ?, css = ?, ");
		sb.append("settings_ = ?, layoutSetPrototypeUuid = ?, ");
		sb.append("layoutSetPrototypeLinkEnabled = ? where layoutSetBranchId ");
		sb.append("= ?");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.setString(1, themeId);
			preparedStatement.setString(2, colorSchemeId);
			preparedStatement.setString(3, wapThemeId);
			preparedStatement.setString(4, wapColorSchemeId);
			preparedStatement.setString(5, css);
			preparedStatement.setString(6, settings);
			preparedStatement.setString(7, layoutSetPrototypeUuid);
			preparedStatement.setBoolean(8, layoutSetPrototypeLinkEnabled);
			preparedStatement.setLong(9, layoutSetBranchId);

			preparedStatement.executeUpdate();
		}
	}

	protected void updateLayoutSetBranches() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select groupId, layoutSetBranchId, privateLayout from " +
					"LayoutSetBranch");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long layoutSetBranchId = resultSet.getLong("layoutSetBranchId");
				long groupId = resultSet.getLong("groupId");
				boolean privateLayout = resultSet.getBoolean("privateLayout");

				upgradeLayoutSetBranch(
					layoutSetBranchId, groupId, privateLayout);
			}
		}
	}

	protected void upgradeLayoutSetBranch(
			long layoutSetBranchId, long groupId, boolean privateLayout)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("select themeId, colorSchemeId, wapThemeId, ");
		sb.append("wapColorSchemeId, css, settings_, layoutSetPrototypeUuid, ");
		sb.append("layoutSetPrototypeLinkEnabled from LayoutSet where ");
		sb.append("groupId = ? and privateLayout = ?");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.setLong(1, groupId);
			preparedStatement.setBoolean(2, privateLayout);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String themeId = resultSet.getString("themeId");
					String colorSchemeId = resultSet.getString("colorSchemeId");
					String wapThemeId = resultSet.getString("wapThemeId");
					String wapColorSchemeId = resultSet.getString(
						"wapColorSchemeId");
					String css = resultSet.getString("css");
					String settings = resultSet.getString("settings_");
					String layoutSetPrototypeUuid = resultSet.getString(
						"layoutSetPrototypeUuid");
					boolean layoutSetPrototypeLinkEnabled =
						resultSet.getBoolean("layoutSetPrototypeLinkEnabled");

					updateLayoutSetBranch(
						layoutSetBranchId, themeId, colorSchemeId, wapThemeId,
						wapColorSchemeId, css, settings, layoutSetPrototypeUuid,
						layoutSetPrototypeLinkEnabled);
				}
			}
		}
	}

}