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

package com.liferay.announcements.web.internal.upgrade.v1_1_0;

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
public class UpgradePortletPreferences extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				StringBundler.concat(
					"select PP1.portletPreferencesId, PP1.preferences from ",
					"PortletPreferences as PP1 inner join PortletPreferences ",
					"as PP2 on PP1.companyId = PP2.ownerId where ",
					"PP1.portletId = '", _PORTLET_ID, "' AND PP2.portletId = '",
					_PORTLET_ID, "' AND PP1.ownerType = ",
					PortletKeys.PREFS_OWNER_TYPE_COMPANY, ";"));
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update PortletPreferences set preferences = ? where " +
						"portletPreferencesId = ?"));
			ResultSet rs1 = ps1.executeQuery()) {

			while (rs1.next()) {
				String preferences = rs1.getString("preferences");

				if (preferences.equals(PortletConstants.DEFAULT_PREFERENCES)) {
					continue;
				}

				long portletPreferencesId = rs1.getLong("portletPreferencesId");

				ps2.setString(1, PortletConstants.DEFAULT_PREFERENCES);
				ps2.setLong(2, portletPreferencesId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	private static final String _PORTLET_ID =
		"com_liferay_announcements_web_portlet_AnnouncementsPortlet";

}