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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.util.Time;

/**
 * @author Brian Wing Shun Chan
 */
public enum TimeUnit {

	DAY("day", Time.DAY), HOUR("hour", Time.DAY), MILLISECOND("millisecond", 1),
	MINUTE("minute", Time.MINUTE), MONTH("month", Time.MONTH),
	SECOND("second", Time.SECOND), WEEK("week", Time.WEEK),
	YEAR("year", Time.YEAR);

	public String getValue() {
		return _value;
	}

	public long toMillis(long duration) {
		return _unitMillis * duration;
	}

	@Override
	public String toString() {
		return _value;
	}

	private TimeUnit(String value, long unitMills) {
		_value = value;

		_unitMillis = unitMills;
	}

	private final long _unitMillis;
	private final String _value;

}