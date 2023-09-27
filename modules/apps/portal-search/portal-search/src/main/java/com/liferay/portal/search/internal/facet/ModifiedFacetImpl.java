/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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