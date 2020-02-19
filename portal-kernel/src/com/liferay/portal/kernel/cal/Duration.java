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

/**
 * Copyright (c) 2000, Columbia University.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the University nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ``AS
 * IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.liferay.portal.kernel.cal;

import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Jonathan Lennox
 */
public class Duration implements Cloneable, Serializable {

	/**
	 * Constructor Duration
	 */
	public Duration() {

		// Zero-initialization of all fields happens by default

	}

	/**
	 * Constructor Duration
	 */
	public Duration(int weeks) {
		_weeks = weeks;
	}

	/**
	 * Constructor Duration
	 */
	public Duration(int hours, int minutes, int seconds) {
		this(0, hours, minutes, seconds);
	}

	/**
	 * Constructor Duration
	 */
	public Duration(int days, int hours, int minutes, int seconds) {
		_days = days;
		_hours = hours;
		_minutes = minutes;
		_seconds = seconds;
	}

	/**
	 * Method clear
	 */
	public void clear() {
		_weeks = 0;
		_days = 0;
		_hours = 0;
		_minutes = 0;
		_seconds = 0;
	}

	/**
	 * Method clone
	 *
	 * @return Object
	 */
	@Override
	public Object clone() {
		try {
			Duration other = (Duration)super.clone();

			other._weeks = _weeks;
			other._days = _days;
			other._hours = _hours;
			other._minutes = _minutes;
			other._seconds = _seconds;

			return other;
		}
		catch (CloneNotSupportedException cloneNotSupportedException) {
			throw new InternalError();
		}
	}

	/**
	 * Method getDays
	 *
	 * @return int
	 */
	public int getDays() {
		return _days;
	}

	/**
	 * Method getHours
	 *
	 * @return int
	 */
	public int getHours() {
		return _hours;
	}

	/**
	 * Method getInterval
	 *
	 * @return long
	 */
	public long getInterval() {
		return _seconds * _MILLIS_PER_SECOND + _minutes * _MILLIS_PER_MINUTE +
			_hours * _MILLIS_PER_HOUR + _days * _MILLIS_PER_DAY +
				_weeks * _MILLIS_PER_WEEK;
	}

	/**
	 * Method getMinutes
	 *
	 * @return int
	 */
	public int getMinutes() {
		return _minutes;
	}

	/**
	 * Method getSeconds
	 *
	 * @return int
	 */
	public int getSeconds() {
		return _seconds;
	}

	/**
	 * Method getWeeks
	 *
	 * @return int
	 */
	public int getWeeks() {
		return _weeks;
	}

	/**
	 * Method setDays
	 */
	public void setDays(int d) {
		if (d < 0) {
			throw new IllegalArgumentException("Day value out of range");
		}

		checkNonWeeksOkay(d);

		_days = d;

		normalize();
	}

	/**
	 * Method setHours
	 */
	public void setHours(int h) {
		if (h < 0) {
			throw new IllegalArgumentException("Hour value out of range");
		}

		checkNonWeeksOkay(h);

		_hours = h;

		normalize();
	}

	/**
	 * Method setInterval
	 */
	public void setInterval(long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException("Negative-length interval");
		}

		clear();

		_days = (int)(millis / _MILLIS_PER_DAY);
		_seconds = (int)((millis % _MILLIS_PER_DAY) / _MILLIS_PER_SECOND);

		normalize();
	}

	/**
	 * Method setMinutes
	 */
	public void setMinutes(int m) {
		if (m < 0) {
			throw new IllegalArgumentException("Minute value out of range");
		}

		checkNonWeeksOkay(m);

		_minutes = m;

		normalize();
	}

	/**
	 * Method setSeconds
	 */
	public void setSeconds(int s) {
		if (s < 0) {
			throw new IllegalArgumentException("Second value out of range");
		}

		checkNonWeeksOkay(s);

		_seconds = s;

		normalize();
	}

	/**
	 * Method setWeeks
	 */
	public void setWeeks(int w) {
		if (w < 0) {
			throw new IllegalArgumentException("Week value out of range");
		}

		checkWeeksOkay(w);

		_weeks = w;
	}

	/**
	 * Method toString
	 *
	 * @return String
	 */
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(12);

		Class<?> clazz = getClass();

		sb.append(clazz.getName());

		sb.append("[weeks=");
		sb.append(_weeks);
		sb.append(",days=");
		sb.append(_days);
		sb.append(",hours=");
		sb.append(_hours);
		sb.append(",minutes=");
		sb.append(_minutes);
		sb.append(",seconds=");
		sb.append(_seconds);
		sb.append("]");

		return sb.toString();
	}

	/**
	 * Method checkNonWeeksOkay
	 */
	protected void checkNonWeeksOkay(int f) {
		if ((f != 0) && (_weeks != 0)) {
			throw new IllegalStateException(
				"Weeks and non-weeks are incompatible");
		}
	}

	/**
	 * Method checkWeeksOkay
	 */
	protected void checkWeeksOkay(int f) {
		if ((f != 0) &&
			((_days != 0) || (_hours != 0) || (_minutes != 0) ||
			 (_seconds != 0))) {

			throw new IllegalStateException(
				"Weeks and non-weeks are incompatible");
		}
	}

	/**
	 * Method normalize
	 */
	protected void normalize() {
		_minutes += _seconds / _SECONDS_PER_MINUTE;
		_seconds %= _SECONDS_PER_MINUTE;
		_hours += _minutes / _MINUTES_PER_HOUR;
		_minutes %= _MINUTES_PER_HOUR;
		_days += _hours / _HOURS_PER_DAY;
		_hours %= _HOURS_PER_DAY;
	}

	/**
	 * Field DAYS_PER_WEEK
	 */
	private static final int _DAYS_PER_WEEK = 7;

	/**
	 * Field HOURS_PER_DAY
	 */
	private static final int _HOURS_PER_DAY = 24;

	/**
	 * Field MILLIS_PER_DAY
	 */
	private static final long _MILLIS_PER_DAY =
		_HOURS_PER_DAY * Duration._MILLIS_PER_HOUR;

	/**
	 * Field MILLIS_PER_HOUR
	 */
	private static final long _MILLIS_PER_HOUR =
		Duration._MINUTES_PER_HOUR * Duration._MILLIS_PER_MINUTE;

	/**
	 * Field MILLIS_PER_MINUTE
	 */
	private static final long _MILLIS_PER_MINUTE =
		Duration._SECONDS_PER_MINUTE * Duration._MILLIS_PER_SECOND;

	/**
	 * Field MILLIS_PER_SECOND
	 */
	private static final long _MILLIS_PER_SECOND = 1000;

	/**
	 * Field MILLIS_PER_WEEK
	 */
	private static final long _MILLIS_PER_WEEK =
		_DAYS_PER_WEEK * _MILLIS_PER_DAY;

	/**
	 * Field MINUTES_PER_HOUR
	 */
	private static final int _MINUTES_PER_HOUR = 60;

	/**
	 * Field SECONDS_PER_MINUTE
	 */
	private static final int _SECONDS_PER_MINUTE = 60;

	/**
	 * Field days
	 */
	private int _days;

	/**
	 * Field hours
	 */
	private int _hours;

	/**
	 * Field minutes
	 */
	private int _minutes;

	/**
	 * Field seconds
	 */
	private int _seconds;

	/**
	 * Field weeks
	 */
	private int _weeks;

}