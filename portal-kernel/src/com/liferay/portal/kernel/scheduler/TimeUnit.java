/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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