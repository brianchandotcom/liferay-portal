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

package com.liferay.portal.kernel.search.facet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Raymond Augé
 */
public class RangeFacet extends BaseFacet {

	public RangeFacet(SearchContext searchContext) {
		super(searchContext);
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		SearchContext searchContext = getSearchContext();

		FacetConfiguration facetConfiguration = getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		String start = StringPool.BLANK;
		String end = StringPool.BLANK;

		if (isStatic() && dataJSONObject.has("ranges")) {
			JSONArray rangesJSONArray = dataJSONObject.getJSONArray("ranges");

			JSONObject rangeJSONObject = rangesJSONArray.getJSONObject(0);

			String rangeString = rangeJSONObject.getString("range");

			String[] range = RangeParserUtil.parserRange(rangeString);

			start = range[0];
			end = range[1];
		}

		String rangeString = GetterUtil.getString(
			searchContext.getAttribute(getFieldId()));

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

}