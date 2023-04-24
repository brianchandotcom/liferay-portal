/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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