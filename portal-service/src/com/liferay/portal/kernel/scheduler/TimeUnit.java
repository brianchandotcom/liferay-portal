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

/**
 * @author Brian Wing Shun Chan
 */
public enum TimeUnit {

	DAY("day"), HOUR("hour"), MINUTE("minute"), SECOND("second"), WEEK("week");

	public static TimeUnit parse(String value) {
		if (DAY.getValue().equals(value)) {
			return DAY;
		}
		else if (HOUR.getValue().equals(value)) {
			return HOUR;
		}
		else if (MINUTE.getValue().equals(value)) {
			return MINUTE;
		}
		else if (SECOND.getValue().equals(value)) {
			return SECOND;
		}
		else if (WEEK.getValue().equals(value)) {
			return WEEK;
		}

		throw new IllegalArgumentException("Invalid value " + value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private TimeUnit(String value) {
		_value = value;
	}

	private final String _value;

}