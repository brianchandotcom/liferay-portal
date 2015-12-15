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

package com.liferay.calendar.recurrence;

import java.util.Calendar;

/**
 * @author Marcellus Tavares
 */
public class PositionalWeekday {

	public PositionalWeekday(Calendar calendar) {
		this(
			Weekday.getWeekday(calendar),
			calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
	}

	public PositionalWeekday(Weekday weekday, int position) {
		if ((position < -53) || (position > 53)) {
			throw new IllegalArgumentException();
		}

		_weekday = weekday;
		_position = position;
	}

	public int getPosition() {
		return _position;
	}

	public Weekday getWeekday() {
		return _weekday;
	}

	private final int _position;
	private final Weekday _weekday;

}