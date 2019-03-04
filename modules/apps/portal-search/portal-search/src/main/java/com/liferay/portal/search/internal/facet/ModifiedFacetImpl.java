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
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.filter.DateRangeFilterBuilder;
import com.liferay.portal.search.filter.FilterBuilders;

/**
 * @author Bryan Engler
 */
public class ModifiedFacetImpl extends RangeFacet implements Facet {

	public ModifiedFacetImpl(
		String fieldName, SearchContext searchContext,
		FilterBuilders filterBuilders) {

		super(searchContext);

		setFieldName(fieldName);

		_filterBuilders = filterBuilders;
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
		if (selections != null) {
			_selections = selections;
		}
		else {
			_selections = new String[0];
		}
	}

	@Override
	public void setAggregationName(String aggregationName) {
		_aggregationName = aggregationName;
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		if (ArrayUtil.isEmpty(_selections)) {
			return null;
		}

		String rangeString = _selections[0];

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

		DateRangeFilterBuilder dateRangeFilterBuilder =
			_filterBuilders.dateRangeFilterBuilder();

		dateRangeFilterBuilder.setFieldName(getFieldName());

		if (Validator.isNotNull(start)) {
			dateRangeFilterBuilder.setFrom(start);
		}

		dateRangeFilterBuilder.setIncludeLower(true);
		dateRangeFilterBuilder.setIncludeUpper(true);

		if (Validator.isNotNull(end)) {
			dateRangeFilterBuilder.setTo(end);
		}

		return new BooleanClauseImpl(
			dateRangeFilterBuilder.build(), BooleanClauseOccur.MUST);
	}

	private String _aggregationName;
	private final FilterBuilders _filterBuilders;
	private String[] _selections = {};

}