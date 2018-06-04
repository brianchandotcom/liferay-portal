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

package com.liferay.portal.search.internal.facet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.Facet;

import java.text.DateFormat;

import java.util.Calendar;

/**
 * @author Bryan Engler
 */
public class ModifiedFacetImpl extends RangeFacet implements Facet {

	public ModifiedFacetImpl(String fieldName, SearchContext searchContext) {
		super(searchContext);

		setFieldName(fieldName);
	}

	@Override
	public String getAggregationName() {
		if (_aggregationName != null) {
			return _aggregationName;
		}

		return getFieldName();
	}

	@Override
	public String[] getSelections() {
		return _selections;
	}

	@Override
	public void select(String... selections) {
		_selections = selections;
	}

	@Override
	public void setAggregationName(String aggregationName) {
		_aggregationName = aggregationName;
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		normalizeDates(getFacetConfiguration());

		if (ArrayUtil.isEmpty(_selections)) {
			return null;
		}

		String rangeString = _selections[0];

		SearchContext searchContext = getSearchContext();

		String start = StringPool.BLANK;
		String end = StringPool.BLANK;

		if (!isStatic() && Validator.isNotNull(rangeString)) {
			String[] range = RangeParserUtil.parserRange(rangeString);

			start = range[0];
			end = range[1];
		}

		if (Validator.isNull(start) && Validator.isNull(end)) {
			return null;
		}

		if (Validator.isNotNull(start) && Validator.isNotNull(end) &&
			(start.compareTo(end) > 0)) {

			throw new IllegalArgumentException(
				"End value must be greater than start value");
		}

		String startString = StringPool.STAR;

		if (Validator.isNotNull(start)) {
			startString = start;
		}

		String endString = StringPool.STAR;

		if (Validator.isNotNull(end)) {
			endString = end;
		}

		RangeTermFilter rangeTermFilter = new RangeTermFilter(
			getFieldName(), true, true, startString, endString);

		return BooleanClauseFactoryUtil.createFilter(
			searchContext, rangeTermFilter, BooleanClauseOccur.MUST);
	}

	protected void normalizeDates(FacetConfiguration facetConfiguration) {
		Calendar now = Calendar.getInstance();

		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MINUTE, 0);

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

		now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) + 1);

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		JSONObject dataJSONObject = facetConfiguration.getData();

		if (!dataJSONObject.has("ranges")) {
			return;
		}

		JSONArray rangesJSONArray = dataJSONObject.getJSONArray("ranges");

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject rangeObject = rangesJSONArray.getJSONObject(i);

			String rangeString = rangeObject.getString("range");

			rangeString = StringUtil.replace(
				rangeString,
				new String[] {
					"past-hour", "past-24-hours", "past-week", "past-month",
					"past-year", "*"
				},
				new String[] {
					dateFormat.format(pastHour.getTime()),
					dateFormat.format(past24Hours.getTime()),
					dateFormat.format(pastWeek.getTime()),
					dateFormat.format(pastMonth.getTime()),
					dateFormat.format(pastYear.getTime()),
					dateFormat.format(now.getTime())
				});

			rangeObject.put("range", rangeString);
		}
	}

	private String _aggregationName;
	private String[] _selections = {};

}