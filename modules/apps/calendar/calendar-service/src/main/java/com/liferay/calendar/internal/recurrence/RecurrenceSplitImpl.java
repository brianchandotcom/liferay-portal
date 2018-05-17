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

package com.liferay.calendar.internal.recurrence;

import com.liferay.calendar.recurrence.Recurrence;

/**
 * @author Adam Brandizzi
 */
public class RecurrenceSplitImpl implements RecurrenceSplit {

	public RecurrenceSplitImpl(
		Recurrence firstRecurrence, Recurrence secondRecurrence) {

		_firstRecurrence = firstRecurrence;
		_secondRecurrence = secondRecurrence;
	}

	@Override
	public Recurrence getFirstRecurrence() {
		return _firstRecurrence;
	}

	@Override
	public Recurrence getSecondRecurrence() {
		return _secondRecurrence;
	}

	@Override
	public boolean isSplit() {
		if (_secondRecurrence != null) {
			return true;
		}

		return false;
	}

	private final Recurrence _firstRecurrence;
	private final Recurrence _secondRecurrence;

}