/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Date;

/**
 * @author Inácio Nery
 */
public class TimeRangeUtil {

	public static Date getEndDate(Integer id, String timeZoneId) {
		if (id == null) {
			return null;
		}

		return _toDate(_getEndLocalDateTime(id, timeZoneId), timeZoneId);
	}

	public static Date getStartDate(Integer id, String timeZoneId) {
		if (id == null) {
			return null;
		}

		LocalDateTime localDateTime = _getEndLocalDateTime(id, timeZoneId);

		localDateTime = localDateTime.withMinute(0);
		localDateTime = localDateTime.withNano(0);
		localDateTime = localDateTime.withSecond(0);

		if (id == 1) {
			return _toDate(localDateTime.minusHours(23), timeZoneId);
		}

		localDateTime = localDateTime.withHour(0);

		if (id == 0) {
			return _toDate(localDateTime, timeZoneId);
		}
		else if (id == 365) {
			return _toDate(localDateTime.minusYears(1), timeZoneId);
		}

		return _toDate(localDateTime.minusDays(id - 1), timeZoneId);
	}

	private static LocalDateTime _getEndLocalDateTime(
		Integer id, String timeZoneId) {

		if (id == 1) {
			LocalDateTime localDateTime = LocalDateTime.of(
				LocalDate.now(ZoneId.of(timeZoneId)), LocalTime.MIDNIGHT);

			return localDateTime.minusHours(1);
		}

		return LocalDateTime.now(ZoneId.of(timeZoneId));
	}

	private static Date _toDate(
		LocalDateTime localDateTime, String timeZoneId) {

		ZonedDateTime zonedDateTime = localDateTime.atZone(
			ZoneId.of(timeZoneId));

		return Date.from(zonedDateTime.toInstant());
	}

}