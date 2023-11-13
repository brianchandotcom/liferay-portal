/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.filter.RangeFilterBuilder;

/**
 * @author Petteri Karttunen
 */
public class RangeFacetImpl extends RangeFacet implements Facet {

	public RangeFacetImpl(
		SearchContext searchContext, FilterBuilders filterBuilders) {

		super(searchContext);

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
		if (ArrayUtil.isEmpty(_selections) || isStatic()) {
			return null;
		}

		BooleanFilter booleanFilter = new BooleanFilter();

		for (String selection : _selections) {
			String[] rangeParts = RangeParserUtil.parserRange(selection);

			String from = rangeParts[0];
			String to = rangeParts[1];

			if (Validator.isNull(from) && Validator.isNull(to)) {
				continue;
			}

			RangeFilterBuilder rangeFilterBuilder =
				_filterBuilders.rangeFilterBuilder();

			rangeFilterBuilder.setFieldName(getFieldName());

			rangeFilterBuilder.setFrom(from);
			rangeFilterBuilder.setIncludeLower(true);
			rangeFilterBuilder.setIncludeUpper(true);
			rangeFilterBuilder.setTo(to);

			booleanFilter.add(
				rangeFilterBuilder.build(), BooleanClauseOccur.SHOULD);
		}

		return new BooleanClauseImpl(booleanFilter, BooleanClauseOccur.MUST);
	}

	private String _aggregationName;
	private final FilterBuilders _filterBuilders;
	private String[] _selections = {};

}