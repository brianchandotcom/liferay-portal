/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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