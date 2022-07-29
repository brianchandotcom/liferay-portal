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

package com.liferay.calendar.internal.upgrade.v4_2_1;

import com.liferay.calendar.util.JCalendarUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author István András Dézsi
 */
public class CalendarBookingUpgradeProcess extends UpgradeProcess {

	public CalendarBookingUpgradeProcess(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					SQLTransformer.transform(
						"select calendarBookingId, userId, startTime, " +
							"endtime from CalendarBooking where allDay = " +
								"[$TRUE$]"));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update CalendarBooking set startTime = ?, endTime = ? " +
						"where calendarBookingId = ?");
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long userId = resultSet.getLong("userId");

				User user = _userLocalService.fetchUser(userId);

				if (user == null) {
					continue;
				}

				Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
					resultSet.getLong("startTime"), user.getTimeZone());
				Calendar endTimeJCalendar = JCalendarUtil.getJCalendar(
					resultSet.getLong("endTime"), user.getTimeZone());

				if (!_isMidnight(startTimeJCalendar) &&
					!_isLastHour(endTimeJCalendar)) {

					continue;
				}

				Calendar startTimeUTCJCalendar = JCalendarUtil.getJCalendar(
					startTimeJCalendar.get(Calendar.YEAR),
					startTimeJCalendar.get(Calendar.MONTH),
					startTimeJCalendar.get(Calendar.DATE),
					startTimeJCalendar.get(Calendar.HOUR_OF_DAY),
					startTimeJCalendar.get(Calendar.MINUTE),
					startTimeJCalendar.get(Calendar.SECOND),
					startTimeJCalendar.get(Calendar.MILLISECOND), _utcTimeZone);

				updatePreparedStatement.setLong(
					1, startTimeUTCJCalendar.getTimeInMillis());

				Calendar endTimeUTCJCalendar = JCalendarUtil.getJCalendar(
					endTimeJCalendar.get(Calendar.YEAR),
					endTimeJCalendar.get(Calendar.MONTH),
					endTimeJCalendar.get(Calendar.DATE),
					endTimeJCalendar.get(Calendar.HOUR_OF_DAY),
					endTimeJCalendar.get(Calendar.MINUTE),
					endTimeJCalendar.get(Calendar.SECOND),
					endTimeJCalendar.get(Calendar.MILLISECOND), _utcTimeZone);

				updatePreparedStatement.setLong(
					2, endTimeUTCJCalendar.getTimeInMillis());

				updatePreparedStatement.setLong(
					3, resultSet.getLong("calendarBookingId"));

				updatePreparedStatement.addBatch();
			}

			updatePreparedStatement.executeBatch();
		}
	}

	private boolean _isLastHour(Calendar jCalendar) {
		if ((jCalendar.get(Calendar.HOUR_OF_DAY) == 23) && (jCalendar.get(Calendar.MINUTE) == 59)) {
			return true;
		}

		return false;
	}

	private boolean _isMidnight(Calendar jCalendar) {
		if ((jCalendar.get(Calendar.HOUR_OF_DAY) == 0) && (jCalendar.get(Calendar.MINUTE) == 0)) {
			return true;
		}

		return false;
	}

	private static final TimeZone _utcTimeZone = TimeZone.getTimeZone(
		StringPool.UTC);

	private final UserLocalService _userLocalService;

}