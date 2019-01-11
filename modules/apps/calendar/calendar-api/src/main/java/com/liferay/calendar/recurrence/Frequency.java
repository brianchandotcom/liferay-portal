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

import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public enum Frequency {

	DAILY("DAILY"), MONTHLY("MONTHLY"), WEEKLY("WEEKLY"), YEARLY("YEARLY");

	public static Frequency parse(String value) {
		if (Objects.equals(DAILY.getValue(), value)) {
			return DAILY;
		}
		else if (Objects.equals(MONTHLY.getValue(), value)) {
			return MONTHLY;
		}
		else if (Objects.equals(WEEKLY.getValue(), value)) {
			return WEEKLY;
		}
		else if (Objects.equals(YEARLY.getValue(), value)) {
			return YEARLY;
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

	private Frequency(String value) {
		_value = value;
	}

	private final String _value;

}