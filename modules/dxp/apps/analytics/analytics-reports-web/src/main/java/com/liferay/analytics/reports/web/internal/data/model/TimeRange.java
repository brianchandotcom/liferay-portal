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
		false, "last-7-days", 7);

	public static final TimeRange LAST_24_HOURS = new TimeRange(
		true, "last-24-hours", 0) {

		@Override
		public LocalDateTime getStartLocalDateTime() {
			LocalDateTime localDateTime = getEndLocalDateTime();

			return localDateTime.minusHours(23);
		}

	};

	public static final TimeRange LAST_30_DAYS = new TimeRange(
		false, "last-30-days", 30);

	public LocalDate getEndLocalDate() {
		LocalDateTime localDateTime = LocalDateTime.now(_clock);

		if (!_includeToday) {
			localDateTime = localDateTime.minusDays(1);
		}

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

		return localDateTime;
	}

	public String getKey() {
		return _key;
	}

	public int getRangeKey() {
		return _rangeKey;
	}

	public LocalDate getStartLocalDate() {
		LocalDate localDate = getEndLocalDate();

		return localDate.minusDays(getRangeKey() - 1);
	}

	public LocalDateTime getStartLocalDateTime() {
		LocalDateTime localDateTime = getEndLocalDateTime();

		localDateTime = localDateTime.withHour(0);
		localDateTime = localDateTime.withMinute(0);
		localDateTime = localDateTime.withNano(0);
		localDateTime = localDateTime.withSecond(0);

		return localDateTime.minusDays(getRangeKey() - 1);
	}

	private TimeRange(boolean includeToday, String key, int rangeKey) {
		_clock = Clock.systemUTC();
		_includeToday = includeToday;
		_key = key;
		_rangeKey = rangeKey;
	}

	private TimeRange(
		Clock clock, boolean includeToday, String key, int rangeKey) {

		_clock = clock;
		_includeToday = includeToday;
		_key = key;
		_rangeKey = rangeKey;
	}

	private final Clock _clock;
	private final boolean _includeToday;
	private final String _key;
	private final int _rangeKey;

}