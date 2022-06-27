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

package com.liferay.portal.upgrade.v7_1_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Roberto Díaz
 */
public class UpgradeAnnouncementsPortletPreferences extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select companyId, preferences from PortletPreferences ",
					"where portletId = '", _PORTLET_ID, "' AND ownerType = ",
					PortletKeys.PREFS_OWNER_TYPE_COMPANY));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				StringBundler.concat(
					"select portletPreferencesId, preferences from ",
					"PortletPreferences where companyId = ? AND portletId = ? ",
					"AND ownerType = ?"));
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update PortletPreferences set preferences = ? where " +
						"portletPreferencesId = ?");
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			while (resultSet1.next()) {
				String preferences = resultSet1.getString("preferences");

				if (preferences.equals(PortletConstants.DEFAULT_PREFERENCES)) {
					continue;
				}

				long companyId = resultSet1.getLong("companyId");

				preparedStatement2.setLong(1, companyId);

				preparedStatement2.setString(2, _PORTLET_ID);
				preparedStatement2.setInt(
					3, PortletKeys.PREFS_OWNER_TYPE_LAYOUT);

				try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
					while (resultSet2.next()) {
						String preferences2 = resultSet2.getString(
							"preferences");

						if (preferences2.equals(
								PortletConstants.DEFAULT_PREFERENCES)) {

							preparedStatement3.setString(1, preferences);
							preparedStatement3.setLong(
								2, resultSet2.getLong("portletPreferencesId"));

							preparedStatement3.addBatch();
						}
					}

					preparedStatement3.executeBatch();
				}
			}
		}
	}

	private static final String _PORTLET_ID =
		"com_liferay_announcements_web_portlet_AnnouncementsPortlet";

}