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
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Adolfo Pérez
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

	public static final TimeRange LAST_28_DAYS = new TimeRange(
		false, "last-28-days", 28);

	public static final TimeRange LAST_30_DAYS = new TimeRange(
		false, "last-30-days", 30) {

		@Override
		public boolean isDefault() {
			return true;
		}

	};

	public static final TimeRange LAST_90_DAYS = new TimeRange(
		false, "last-90-days", 90);

	public static final TimeRange YESTERDAY = new TimeRange(
		false, "yesterday", 1) {

		@Override
		public LocalDateTime getEndLocalDateTime() {
			LocalDateTime localDateTime = LocalDateTime.of(
				LocalDate.now(getClock()), LocalTime.MIDNIGHT);

			return localDateTime.minusHours(1);
		}

		@Override
		public LocalDateTime getStartLocalDateTime() {
			LocalDateTime localDateTime = getEndLocalDateTime();

			return localDateTime.minusHours(23);
		}

	};

	public static TimeRange of(int rangeKey) {
		return Optional.ofNullable(
			_timeRanges.get(rangeKey)
		).orElseThrow(
			IllegalArgumentException::new
		);
	}

	public static TimeRange of(LocalDate startLocalDate) {
		return of(LocalDate.now(ZoneOffset.UTC), startLocalDate);
	}

	public static TimeRange of(
		LocalDate endLocalDate, LocalDate startLocalDate) {

		long delta = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);

		return new CustomTimeRange(
			"custom", (int)delta + 1, endLocalDate.atTime(23, 59, 59),
			startLocalDate.atStartOfDay());
	}

	public static TimeRange of(
		LocalDateTime endLocalDateTime, LocalDateTime startLocalDateTime) {

		long delta = ChronoUnit.DAYS.between(
			startLocalDateTime, endLocalDateTime);

		return new CustomTimeRange(
			"custom", (int)delta + 1, endLocalDateTime, startLocalDateTime);
	}

	public static Collection<TimeRange> values() {
		return _timeRanges.values();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TimeRange)) {
			return false;
		}

		TimeRange timeRange = (TimeRange)obj;

		if (Objects.equals(_includeToday, timeRange._includeToday) &&
			Objects.equals(_key, timeRange._key) &&
			Objects.equals(_rangeKey, timeRange._rangeKey)) {

			return true;
		}

		return false;
	}

	public Clock getClock() {
		return _clock;
	}

	public long getDeltaDays() {
		return ChronoUnit.DAYS.between(getStartLocalDate(), getEndLocalDate()) +
			1;
	}

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

	public boolean getIncludeToday() {
		return _includeToday;
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

	@Override
	public int hashCode() {
		return Objects.hash(_key, _rangeKey);
	}

	public boolean isDefault() {
		return false;
	}

	public TimeRange withClock(Clock clock) {
		return new TimeRange(clock, _includeToday, _key, _rangeKey);
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

	private static final Map<Integer, TimeRange> _timeRanges =
		new HashMap<Integer, TimeRange>() {
			{
				put(0, LAST_24_HOURS);
				put(1, YESTERDAY);
				put(7, LAST_7_DAYS);
				put(28, LAST_28_DAYS);
				put(30, LAST_30_DAYS);
				put(90, LAST_90_DAYS);
			}
		};

	private final Clock _clock;
	private final boolean _includeToday;
	private final String _key;
	private final int _rangeKey;

	private static class CustomTimeRange extends TimeRange {

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof CustomTimeRange)) {
				return false;
			}

			CustomTimeRange customTimeRange = (CustomTimeRange)obj;

			if (super.equals(customTimeRange) &&
				Objects.equals(
					_endLocalDateTime, customTimeRange._endLocalDateTime) &&
				Objects.equals(
					_startLocalDateTime, customTimeRange._startLocalDateTime)) {

				return true;
			}

			return false;
		}

		@Override
		public LocalDate getEndLocalDate() {
			return _endLocalDateTime.toLocalDate();
		}

		@Override
		public LocalDateTime getEndLocalDateTime() {
			return _endLocalDateTime;
		}

		@Override
		public LocalDate getStartLocalDate() {
			return _startLocalDateTime.toLocalDate();
		}

		@Override
		public LocalDateTime getStartLocalDateTime() {
			return _startLocalDateTime;
		}

		@Override
		public int hashCode() {
			return super.hashCode() ^
				   Objects.hash(_endLocalDateTime, _startLocalDateTime);
		}

		@Override
		public TimeRange withClock(Clock clock) {
			ZonedDateTime endZonedDateTime = _endLocalDateTime.atZone(
				clock.getZone());
			ZonedDateTime startZonedDateTime = _startLocalDateTime.atZone(
				clock.getZone());

			return of(
				endZonedDateTime.toLocalDateTime(),
				startZonedDateTime.toLocalDateTime());
		}

		private CustomTimeRange(
			String key, int rangeKey, LocalDateTime endLocalDateTime,
			LocalDateTime startLocalDateTime) {

			super(
				LocalDateTime.now().getDayOfMonth() ==
					endLocalDateTime.getDayOfMonth(),
				key, rangeKey);

			_endLocalDateTime = endLocalDateTime;
			_startLocalDateTime = startLocalDateTime;
		}

		private final LocalDateTime _endLocalDateTime;
		private final LocalDateTime _startLocalDateTime;

	}

}