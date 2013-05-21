/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search.facet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.DateFormat;

import java.util.Calendar;

/**
 * @author Raymond Augé
 */
public class ModifiedFacet extends RangeFacet {

	public ModifiedFacet(SearchContext searchContext) {
		super(searchContext);

		setFieldName(Field.MODIFIED_DATE);
	}

	@Override
	protected BooleanClause doGetFacetClause() {
		SearchContext searchContext = getSearchContext();

		FacetConfiguration facetConfiguration = getFacetConfiguration();

		normalizeDates(searchContext, facetConfiguration);

		return super.doGetFacetClause();
	}

	protected void normalizeDates(
		SearchContext searchContext, FacetConfiguration facetConfiguration) {

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
				}
			);

			rangeObject.put("range", rangeString);
		}
	}

}