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

package com.liferay.analytics.reports.web.internal.data.model;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Adolfo Pérez
 * @author David Arques
 */
public class TimeRange {

	public static final TimeRange LAST_7_DAYS = new TimeRange(
		false, TimeSpan.LAST_7_DAYS, 0);

	public static final TimeRange LAST_24_HOURS = new TimeRange(
		true, TimeSpan.LAST_24_HOURS, 0) {

		@Override
		public LocalDateTime getStartLocalDateTime() {
			LocalDateTime localDateTime = getEndLocalDateTime();

			return localDateTime.minusHours(23);
		}

	};

	public static final TimeRange LAST_30_DAYS = new TimeRange(
		false, TimeSpan.LAST_30_DAYS, 0);

	public LocalDate getEndLocalDate() {
		LocalDateTime localDateTime = LocalDateTime.now(_clock);

		if (!_includeToday) {
			localDateTime = localDateTime.minusDays(1);
		}

		localDateTime = localDateTime.minusDays(_getOffsetDays());

		return localDateTime.toLocalDate();
	}

	public LocalDateTime getEndLocalDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now(_clock);

		if (_includeToday) {
			localDateTime = localDateTime.withMinute(0);
			localDateTime = localDateTime.withSecond(0);
			localDateTime = localDateTime.withNano(0);
		}
		else {
			localDateTime = localDateTime.minusDays(1);
			localDateTime = localDateTime.withHour(23);
			localDateTime = localDateTime.withMinute(59);
			localDateTime = localDateTime.withNano(999999999);
			localDateTime = localDateTime.withSecond(59);
		}

		return localDateTime.minusDays(_getOffsetDays());
	}

	public LocalDate getStartLocalDate() {
		LocalDate localDate = getEndLocalDate();

		return localDate.minusDays(_timeSpan.getDays() - 1);
	}

	public LocalDateTime getStartLocalDateTime() {
		LocalDateTime localDateTime = getEndLocalDateTime();

		localDateTime = localDateTime.withHour(0);
		localDateTime = localDateTime.withMinute(0);
		localDateTime = localDateTime.withNano(0);
		localDateTime = localDateTime.withSecond(0);

		return localDateTime.minusDays(_timeSpan.getDays() - 1);
	}

	public TimeSpan getTimeSpan() {
		return _timeSpan;
	}

	public int getTimeSpanOffset() {
		return _timeSpanOffset;
	}

	private TimeRange(
		boolean includeToday, TimeSpan timeSpan, int timeSpanOffset) {

		_clock = Clock.systemUTC();
		_includeToday = includeToday;
		_timeSpan = timeSpan;
		_timeSpanOffset = timeSpanOffset;
	}

	private int _getOffsetDays() {
		if (_timeSpanOffset == 0) {
			return 0;
		}

		if (_timeSpan.getDays() == 0) {
			return _timeSpanOffset;
		}

		return (_timeSpan.getDays() * _timeSpanOffset) - 1;
	}

	private final Clock _clock;
	private final boolean _includeToday;
	private final TimeSpan _timeSpan;
	private final int _timeSpanOffset;

}