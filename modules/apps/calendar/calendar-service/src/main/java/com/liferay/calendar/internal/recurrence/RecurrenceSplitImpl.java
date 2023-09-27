/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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