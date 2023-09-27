/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.facet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.DateFormat;

import java.util.Calendar;

/**
 * @author     Raymond Augé
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class ModifiedFacet extends RangeFacet {

	public ModifiedFacet(SearchContext searchContext) {
		super(searchContext);

		setFieldName(Field.MODIFIED_DATE);
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		normalizeDates(getFacetConfiguration());

		return super.doGetFacetFilterBooleanClause();
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

		JSONObject dataJSONObject = facetConfiguration.getData();

		if (!dataJSONObject.has("ranges")) {
			return;
		}

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		JSONArray rangesJSONArray = dataJSONObject.getJSONArray("ranges");

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject rangeJSONObject = rangesJSONArray.getJSONObject(i);

			String rangeString = rangeJSONObject.getString("range");

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

			rangeJSONObject.put("range", rangeString);
		}
	}

}