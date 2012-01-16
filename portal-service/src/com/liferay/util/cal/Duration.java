/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

/*
 * Copyright (c) 2000, Columbia University.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *	  notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *	  notice, this list of conditions and the following disclaimer in the
 *	  documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the University nor the names of its contributors
 *	  may be used to endorse or promote products derived from this software
 *	  without specific prior written permission.
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

package com.liferay.util.cal;

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * @author     Jonathan Lennox
 * @deprecated Moved to {@link com.liferay.portal.kernel.cal.Duration}
 */
public class Duration implements Cloneable, Serializable {

	/**
	 * Constructor Duration
	 */
	public Duration() {

		/* Zero-initialization of all fields happens by default */

	}

	/**
	 * Constructor Duration
	 */
	public Duration(int d, int h, int m, int s) {
		days = d;
		hours = h;
		minutes = m;
		seconds = s;
	}

	/**
	 * Constructor Duration
	 */
	public Duration(int h, int m, int s) {
		this(0, h, m, s);
	}

	/**
	 * Constructor Duration
	 */
	public Duration(int w) {
		weeks = w;
	}

	/**
	 * Method clear
	 */
	public void clear() {
		weeks = 0;
		days = 0;
		hours = 0;
		minutes = 0;
		seconds = 0;
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

			other.weeks = weeks;
			other.days = days;
			other.hours = hours;
			other.minutes = minutes;
			other.seconds = seconds;

			return other;
		}
		catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	/**
	 * Method getWeeks
	 *
	 * @return int
	 */
	public int getWeeks() {
		return weeks;
	}

	/**
	 * Method setWeeks
	 */
	public void setWeeks(int w) {
		if (w < 0) {
			throw new IllegalArgumentException("Week value out of range");
		}

		checkWeeksOkay(w);

		weeks = w;
	}

	/**
	 * Method getDays
	 *
	 * @return int
	 */
	public int getDays() {
		return days;
	}

	/**
	 * Method setDays
	 */
	public void setDays(int d) {
		if (d < 0) {
			throw new IllegalArgumentException("Day value out of range");
		}

		checkNonWeeksOkay(d);

		days = d;

		normalize();
	}

	/**
	 * Method getHours
	 *
	 * @return int
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * Method setHours
	 */
	public void setHours(int h) {
		if (h < 0) {
			throw new IllegalArgumentException("Hour value out of range");
		}

		checkNonWeeksOkay(h);

		hours = h;

		normalize();
	}

	/**
	 * Method getMinutes
	 *
	 * @return int
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * Method setMinutes
	 */
	public void setMinutes(int m) {
		if (m < 0) {
			throw new IllegalArgumentException("Minute value out of range");
		}

		checkNonWeeksOkay(m);

		minutes = m;

		normalize();
	}

	/**
	 * Method getSeconds
	 *
	 * @return int
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * Method setSeconds
	 */
	public void setSeconds(int s) {
		if (s < 0) {
			throw new IllegalArgumentException("Second value out of range");
		}

		checkNonWeeksOkay(s);

		seconds = s;

		normalize();
	}

	/**
	 * Method getInterval
	 *
	 * @return long
	 */
	public long getInterval() {
		return seconds * MILLIS_PER_SECOND + minutes * MILLIS_PER_MINUTE
			   + hours * MILLIS_PER_HOUR + days * MILLIS_PER_DAY
			   + weeks * MILLIS_PER_WEEK;
	}

	/**
	 * Method setInterval
	 */
	public void setInterval(long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException("Negative-length interval");
		}

		clear();

		days = (int)(millis / MILLIS_PER_DAY);
		seconds = (int)((millis % MILLIS_PER_DAY) / MILLIS_PER_SECOND);

		normalize();
	}

	/**
	 * Method toString
	 *
	 * @return String
	 */
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(12);

		sb.append(getClass().getName());
		sb.append("[weeks=");
		sb.append(weeks);
		sb.append(",days=");
		sb.append(days);
		sb.append(",hours=");
		sb.append(hours);
		sb.append(",minutes=");
		sb.append(minutes);
		sb.append(",seconds=");
		sb.append(seconds);
		sb.append("]");

		return sb.toString();
	}

	/**
	 * Method normalize
	 */
	protected void normalize() {
		minutes += seconds / SECONDS_PER_MINUTE;
		seconds %= SECONDS_PER_MINUTE;
		hours += minutes / MINUTES_PER_HOUR;
		minutes %= MINUTES_PER_HOUR;
		days += hours / HOURS_PER_DAY;
		hours %= HOURS_PER_DAY;
	}

	/**
	 * Method checkWeeksOkay
	 */
	protected void checkWeeksOkay(int f) {
		if ((f != 0)
			&& ((days != 0) || (hours != 0) || (minutes != 0)
				|| (seconds != 0))) {
			throw new IllegalStateException(
				"Weeks and non-weeks are incompatible");
		}
	}

	/**
	 * Method checkNonWeeksOkay
	 */
	protected void checkNonWeeksOkay(int f) {
		if ((f != 0) && (weeks != 0)) {
			throw new IllegalStateException(
				"Weeks and non-weeks are incompatible");
		}
	}

	/**
	 * Field SECONDS_PER_MINUTE
	 */
	private static final int SECONDS_PER_MINUTE = 60;

	/**
	 * Field MINUTES_PER_HOUR
	 */
	private static final int MINUTES_PER_HOUR = 60;

	/**
	 * Field HOURS_PER_DAY
	 */
	private static final int HOURS_PER_DAY = 24;

	/**
	 * Field DAYS_PER_WEEK
	 */
	private static final int DAYS_PER_WEEK = 7;

	/**
	 * Field MILLIS_PER_SECOND
	 */
	private static final int MILLIS_PER_SECOND = 1000;

	/**
	 * Field MILLIS_PER_MINUTE
	 */
	private static final int MILLIS_PER_MINUTE = SECONDS_PER_MINUTE
												 * MILLIS_PER_SECOND;

	/**
	 * Field MILLIS_PER_HOUR
	 */
	private static final int MILLIS_PER_HOUR = MINUTES_PER_HOUR
											   * MILLIS_PER_MINUTE;

	/**
	 * Field MILLIS_PER_DAY
	 */
	private static final int MILLIS_PER_DAY = HOURS_PER_DAY * MILLIS_PER_HOUR;

	/**
	 * Field MILLIS_PER_WEEK
	 */
	private static final int MILLIS_PER_WEEK = DAYS_PER_WEEK * MILLIS_PER_DAY;

	/**
	 * Field weeks
	 */
	private int weeks;

	/**
	 * Field days
	 */
	private int days;

	/**
	 * Field hours
	 */
	private int hours;

	/**
	 * Field minutes
	 */
	private int minutes;

	/**
	 * Field seconds
	 */
	private int seconds;

}