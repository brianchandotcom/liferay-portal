/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author David Arques
 */
public enum TimeSpan {

	LAST_7_DAYS("last-7-days", 7), LAST_30_DAYS("last-30-days", 30),
	TODAY("today", 1);

	public static String defaultTimeSpanKey() {
		return LAST_7_DAYS.getKey();
	}

	public static TimeSpan of(String key) {
		if (Validator.isNull(key)) {
			throw new IllegalArgumentException("Time span key is null");
		}

		for (TimeSpan timeSpan : values()) {
			if (Objects.equals(key, timeSpan.getKey())) {
				return timeSpan;
			}
		}

		throw new IllegalArgumentException("Invalid time span key " + key);
	}

	public int getDays() {
		return _days;
	}

	public String getKey() {
		return _key;
	}

	public TimeRange toTimeRange(int timeSpanOffset) {
		if (timeSpanOffset < 0) {
			throw new IllegalArgumentException("Time span offset is negative");
		}

		return TimeRange.of(this, timeSpanOffset);
	}

	private TimeSpan(String key, int days) {
		_key = key;
		_days = days;
	}

	private final int _days;
	private final String _key;

}