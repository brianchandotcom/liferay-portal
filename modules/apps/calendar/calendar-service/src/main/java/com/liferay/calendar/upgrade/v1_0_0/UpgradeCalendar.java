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

package com.liferay.calendar.upgrade.v1_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adam Brandizzi
 */
public class UpgradeCalendar extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!tableHasColumn("CalendarBooking", "vEventUid")) {
			runSQL("alter table Calendar add timeZoneId VARCHAR(75) null");
		}

		updateCalendarTimeZoneIds();
	}

	protected void updateCalendarTimeZoneId(long calendarId, String timeZoneId)
		throws Exception {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update Calendar set timeZoneId = ? where calendarId = ?");

			ps.setString(1, timeZoneId);
			ps.setLong(2, calendarId);

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void updateCalendarTimeZoneIds() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBundler sb = new StringBundler(6);

			sb.append("select Calendar.calendarId, CalendarResource.");
			sb.append("classNameId, User_.timeZoneId from Calendar ");
			sb.append("inner join CalendarResource on Calendar.");
			sb.append("calendarResourceId = CalendarResource.");
			sb.append("calendarResourceId inner join User_ on ");
			sb.append("CalendarResource.userId = User_.userId");

			ps = connection.prepareStatement(sb.toString());

			rs = ps.executeQuery();

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
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}
	}

}