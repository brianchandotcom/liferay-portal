/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.upgrade.v4_2_1;

import com.liferay.calendar.util.JCalendarUtil;
import com.liferay.petra.string.StringBundler;
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
						StringBundler.concat(
							"select calendarBookingId, companyId, userId, ",
							"startTime, endTime from CalendarBooking where ",
							"allDay = [$TRUE$]")));
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update CalendarBooking set startTime = ?, endTime = ? " +
						"where calendarBookingId = ?");
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long startTime = resultSet.getLong("startTime");
				long endTime = resultSet.getLong("endTime");

				if (_isValidAllDayEvent(endTime, startTime)) {
					continue;
				}

				User user = _userLocalService.fetchUser(
					resultSet.getLong("userId"));

				if (user == null) {
					long companyId = resultSet.getLong("companyId");

					user = _userLocalService.getGuestUser(companyId);
				}

				Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
					startTime, user.getTimeZone());
				Calendar endTimeJCalendar = JCalendarUtil.getJCalendar(
					endTime, user.getTimeZone());

				if (!_isLastHour(endTimeJCalendar) ||
					!_isMidnight(startTimeJCalendar) ||
					!_isSameDay(endTimeJCalendar, startTimeJCalendar)) {

					continue;
				}

				Calendar startTimeUTCJCalendar = JCalendarUtil.getJCalendar(
					startTimeJCalendar.get(Calendar.YEAR),
					startTimeJCalendar.get(Calendar.MONTH),
					startTimeJCalendar.get(Calendar.DATE), 0, 0, 0, 0,
					_utcTimeZone);

				updatePreparedStatement.setLong(
					1, startTimeUTCJCalendar.getTimeInMillis());

				Calendar endTimeUTCJCalendar = JCalendarUtil.getJCalendar(
					endTimeJCalendar.get(Calendar.YEAR),
					endTimeJCalendar.get(Calendar.MONTH),
					endTimeJCalendar.get(Calendar.DATE), 23, 59, 0, 0,
					_utcTimeZone);

				updatePreparedStatement.setLong(
					2, endTimeUTCJCalendar.getTimeInMillis());

				updatePreparedStatement.setLong(
					3, resultSet.getLong("calendarBookingId"));

				updatePreparedStatement.addBatch();
			}

			updatePreparedStatement.executeBatch();
		}
	}

	private boolean _isLastHour(Calendar endTimeJCalendar) {
		if ((endTimeJCalendar.get(Calendar.HOUR_OF_DAY) == 23) &&
			(endTimeJCalendar.get(Calendar.MINUTE) == 59)) {

			return true;
		}

		return false;
	}

	private boolean _isMidnight(Calendar startTimeJCalendar) {
		if ((startTimeJCalendar.get(Calendar.HOUR_OF_DAY) == 0) &&
			(startTimeJCalendar.get(Calendar.MINUTE) == 0)) {

			return true;
		}

		return false;
	}

	private boolean _isSameDay(
		Calendar endTimeJCalendar, Calendar startTimeJCalendar) {

		if (startTimeJCalendar.get(Calendar.DATE) == endTimeJCalendar.get(
				Calendar.DATE)) {

			return true;
		}

		return false;
	}

	private boolean _isValidAllDayEvent(long endTime, long startTime) {
		Calendar endTimeJCalendar = JCalendarUtil.getJCalendar(
			endTime, _utcTimeZone);
		Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			startTime, _utcTimeZone);

		if (_isMidnight(startTimeJCalendar) && _isLastHour(endTimeJCalendar) &&
			_isSameDay(endTimeJCalendar, startTimeJCalendar)) {

			return true;
		}

		return false;
	}

	private static final TimeZone _utcTimeZone = TimeZone.getTimeZone(
		StringPool.UTC);

	private final UserLocalService _userLocalService;

}