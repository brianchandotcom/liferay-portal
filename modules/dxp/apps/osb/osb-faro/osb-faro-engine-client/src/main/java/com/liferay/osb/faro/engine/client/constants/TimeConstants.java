/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Matthew Kong
 */
public class TimeConstants {

	public static final String INTERVAL_DAY = "day";

	public static final String INTERVAL_MONTH = "month";

	public static final String INTERVAL_QUARTER = "quarter";

	public static final String INTERVAL_WEEK = "week";

	public static final String INTERVAL_YEAR = "year";

	public static final String TIME_SPAN_1_YEAR_AGO = "lastYear";

	public static final String TIME_SPAN_7_DAYS_AGO = "last7Days";

	public static final String TIME_SPAN_30_DAYS_AGO = "last30Days";

	public static final String TIME_SPAN_ALL_TIME = "ever";

	public static final String TIME_SPAN_TODAY = "today";

	public static Map<String, String> getIntervals() {
		return _intervals;
	}

	public static Map<String, String> getTimeSpans() {
		return _timeSpans;
	}

	private static final Map<String, String> _intervals = HashMapBuilder.put(
		INTERVAL_DAY, INTERVAL_DAY
	).put(
		INTERVAL_MONTH, INTERVAL_MONTH
	).put(
		INTERVAL_QUARTER, INTERVAL_QUARTER
	).put(
		INTERVAL_WEEK, INTERVAL_WEEK
	).put(
		INTERVAL_YEAR, INTERVAL_YEAR
	).build();
	private static final Map<String, String> _timeSpans = HashMapBuilder.put(
		"1YearAgo", TIME_SPAN_1_YEAR_AGO
	).put(
		"7DaysAgo", TIME_SPAN_7_DAYS_AGO
	).put(
		"30DaysAgo", TIME_SPAN_30_DAYS_AGO
	).put(
		"allTime", TIME_SPAN_ALL_TIME
	).put(
		"today", TIME_SPAN_TODAY
	).build();

}