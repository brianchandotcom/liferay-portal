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

package com.liferay.calendar.web.internal.upgrade.v_1_2_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Balázs Sáfrány-Kovalik
 */
public class UpgradeStagingGroupTypeSettings extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_removeStagedCalendarInstanceId();
	}

	private String _getNewTypeSettings(String typeSettings) {
		Pattern pattern = Pattern.compile(
			_STAGED_CALENDAR_PORTLET + _INSTANCE_SEPARATOR + "\\w{12}",
			Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(typeSettings);

		return matcher.replaceAll(_STAGED_CALENDAR_PORTLET);
	}

	private void _removeStagedCalendarInstanceId() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select groupId, typeSettings from Group_ where ");
		sb.append("typeSettings like '%");
		sb.append(_STAGED_CALENDAR_PORTLET);
		sb.append(_INSTANCE_SEPARATOR);
		sb.append("%'");

		String sql = "update Group_ set typeSettings = ? where groupId = ?";

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sql);
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");

				String typeSettings = rs.getString("typeSettings");

				String newTypeSettings = _getNewTypeSettings(typeSettings);

				ps2.setString(1, newTypeSettings);

				ps2.setLong(2, groupId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	private static final String _INSTANCE_SEPARATOR = "_INSTANCE_";

	private static final String _STAGED_CALENDAR_PORTLET =
		"staged-portlet_com_liferay_calendar_web_portlet_CalendarPortlet";

}