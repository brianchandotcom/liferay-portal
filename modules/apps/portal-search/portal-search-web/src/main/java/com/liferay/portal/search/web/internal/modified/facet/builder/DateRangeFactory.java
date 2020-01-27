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

package com.liferay.portal.search.web.internal.modified.facet.builder;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Adam Brandizzi
 * @author André de Oliveira
 */
public class DateRangeFactory {

	public DateRangeFactory(DateFormatFactory dateFormatFactory) {
		_dateFormatFactory = dateFormatFactory;
	}

	public String getRangeString(String label, Calendar calendar) {
		return replaceAliases(_rangeMap.get(label), calendar);
	}

	public String getRangeString(String from, String to) {
		StringBundler sb = new StringBundler(5);

		sb.append("[");
		sb.append(_normalizeRangeBoundary(from, "000000"));
		sb.append(" TO ");
		sb.append(_normalizeRangeBoundary(to, "235959"));
		sb.append("]");

		return sb.toString();
	}

	public Map<String, String> getRangeStrings(Calendar calendar) {
		Map<String, String> map = new LinkedHashMap<>();

		for (String label : _rangeMap.keySet()) {
			map.put(label, getRangeString(label, calendar));
		}

		return map;
	}

	public JSONArray replaceAliases(
		JSONArray rangesJSONArray, Calendar calendar, JSONFactory jsonFactory) {

		JSONArray normalizedRangesJSONArray = jsonFactory.createJSONArray();

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject rangeJSONObject = rangesJSONArray.getJSONObject(i);

			JSONObject normalizedJSONObject = jsonFactory.createJSONObject();

			normalizedJSONObject.put(
				"label", rangeJSONObject.getString("label")
			).put(
				"range",
				replaceAliases(rangeJSONObject.getString("range"), calendar)
			);

			normalizedRangesJSONArray.put(normalizedJSONObject);
		}

		return normalizedRangesJSONArray;
	}

	public String replaceAliases(String rangeString, Calendar calendar) {
		Calendar now = (Calendar)calendar.clone();

		Calendar pastHour = (Calendar)now.clone();

		pastHour.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) - 1);

		Calendar past24Hours = (Calendar)now.clone();

		past24Hours.set(
			Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) - 1);

		Calendar pastWeek = (Calendar)now.clone();

		pastWeek.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) - 7);

		Calendar pastMonth = (Calendar)now.clone();

		pastMonth.set(Calendar.MONTH, now.get(Calendar.MONTH) - 1);

		Calendar pastYear = (Calendar)now.clone();

		pastYear.set(Calendar.YEAR, now.get(Calendar.YEAR) - 1);

		DateFormat dateFormat = _dateFormatFactory.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		rangeString = StringUtil.replace(
			rangeString, _ALIASES,
			new String[] {
				dateFormat.format(pastHour.getTime()),
				dateFormat.format(past24Hours.getTime()),
				dateFormat.format(pastWeek.getTime()),
				dateFormat.format(pastMonth.getTime()),
				dateFormat.format(pastYear.getTime()),
				dateFormat.format(now.getTime())
			});

		return rangeString;
	}

	public void validateRange(String ranges)
		throws JSONException, ParseException {

		JSONArray rangesJSONArray = JSONFactoryUtil.createJSONArray(ranges);

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			String range = rangesJSONArray.getJSONObject(
				i
			).getString(
				"range"
			);

			String from = range.split("TO")[0].trim();

			from = from.substring(1);

			validateDateFormat(from);

			String to = range.split("TO")[1].trim();

			to = to.substring(0, to.length() - 1);

			validateDateFormat(to);
		}
	}

	protected void validateDateFormat(String date) throws ParseException {
		DateFormat dateFormat = _dateFormatFactory.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		if (!ArrayUtil.contains(_ALIASES, date)) {
			dateFormat.parse(date);
		}
	}

	private String _normalizeRangeBoundary(String dateString, String pad) {
		dateString = StringUtil.removeSubstring(dateString, '-');

		return dateString + pad;
	}

	private static final String[] _ALIASES = {
		"past-hour", "past-24-hours", "past-week", "past-month", "past-year",
		StringPool.STAR
	};

	private static final Map<String, String> _rangeMap =
		LinkedHashMapBuilder.put(
			"past-hour", "[past-hour TO *]"
		).put(
			"past-24-hours", "[past-24-hours TO *]"
		).put(
			"past-week", "[past-week TO *]"
		).put(
			"past-month", "[past-month TO *]"
		).put(
			"past-year", "[past-year TO *]"
		).build();

	private final DateFormatFactory _dateFormatFactory;

}