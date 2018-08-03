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
public class UpgradeCalendar extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		updateCalendarTable();
		updateCalendarTimeZoneIds();
	}

	public void updateCalendarTable() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasColumn("Calendar", "timeZoneId")) {
				runSQL("alter table Calendar add timeZoneId VARCHAR(75) null");
			}
		}
	}

	public void updateCalendarTimeZoneId(long calendarId, String timeZoneId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update Calendar set timeZoneId = ? where calendarId = ?")) {

			ps.setString(1, timeZoneId);
			ps.setLong(2, calendarId);

			ps.execute();
		}
	}

	public void updateCalendarTimeZoneIds() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(5);

			sb.append("select Calendar.calendarId, CalendarResource.");
			sb.append("classNameId, User_.timeZoneId from Calendar inner ");
			sb.append("join CalendarResource on Calendar.calendarResourceId ");
			sb.append("= CalendarResource.calendarResourceId inner join ");
			sb.append("User_ on CalendarResource.userId = User_.userId");

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString());
				ResultSet rs = ps.executeQuery()) {

				long userClassNameId = PortalUtil.getClassNameId(User.class);

				while (rs.next()) {
					long calendarId = rs.getLong(1);
					long classNameId = rs.getLong(2);

					String timeZoneId = null;

					if (classNameId == userClassNameId) {
						timeZoneId = rs.getString(3);
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