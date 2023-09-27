/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.upgrade.v1_0_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adam Brandizzi
 */
public class CalendarUpgradeProcess extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		updateCalendarTable();
		updateCalendarTimeZoneIds();
	}

	public void updateCalendarTable() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			alterTableAddColumn("Calendar", "timeZoneId", "VARCHAR(75) null");
		}
	}

	public void updateCalendarTimeZoneId(long calendarId, String timeZoneId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update Calendar set timeZoneId = ? where calendarId = ?")) {

			preparedStatement.setString(1, timeZoneId);
			preparedStatement.setLong(2, calendarId);

			preparedStatement.execute();
		}
	}

	public void updateCalendarTimeZoneIds() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"select Calendar.calendarId, CalendarResource.",
							"classNameId, User_.timeZoneId from Calendar ",
							"inner join CalendarResource on ",
							"Calendar.calendarResourceId = ",
							"CalendarResource.calendarResourceId inner join ",
							"User_ on CalendarResource.userId = User_.userId"));
				ResultSet resultSet = preparedStatement.executeQuery()) {

				long userClassNameId = PortalUtil.getClassNameId(User.class);

				while (resultSet.next()) {
					long calendarId = resultSet.getLong(1);
					long classNameId = resultSet.getLong(2);

					String timeZoneId = null;

					if (classNameId == userClassNameId) {
						timeZoneId = resultSet.getString(3);
					}
					else {
						timeZoneId = PropsUtil.get(
							PropsKeys.COMPANY_DEFAULT_TIME_ZONE);
					}

					updateCalendarTimeZoneId(calendarId, timeZoneId);
				}
			}
		}
	}

}