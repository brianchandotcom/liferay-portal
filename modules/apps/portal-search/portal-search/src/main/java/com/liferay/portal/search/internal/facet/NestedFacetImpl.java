/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.nested.NestedFacet;

/**
 * @author Jorge Díaz
 */
public class NestedFacetImpl extends FacetImpl implements NestedFacet {

	public NestedFacetImpl(String fieldName, SearchContext searchContext) {
		super(fieldName, searchContext);
	}

	@Override
	public String getFilterField() {
		return _filterField;
	}

	@Override
	public String getFilterValue() {
		return _filterValue;
	}

	@Override
	public String getPath() {
		return _path;
	}

	public void setFilterField(String filterField) {
		_filterField = filterField;
	}

	public void setFilterValue(String filterValue) {
		_filterValue = filterValue;
	}

	public void setPath(String path) {
		_path = path;
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		String[] selections = getSelections();

		if (ArrayUtil.isEmpty(selections)) {
			return null;
		}

		BooleanFilter booleanFilter = new BooleanFilter();

		if (Validator.isNotNull(_filterField)) {
			TermsFilter nestedTermsFilter = new TermsFilter(_filterField);

			nestedTermsFilter.addValue(_filterValue);

			booleanFilter.add(nestedTermsFilter, BooleanClauseOccur.MUST);
		}

		TermsFilter nestedAggregationTermsFilter = new TermsFilter(
			getFieldName());

		nestedAggregationTermsFilter.addValues(selections);

		booleanFilter.add(
			nestedAggregationTermsFilter, BooleanClauseOccur.MUST);

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.setPreBooleanFilter(booleanFilter);

		NestedQuery nestedQuery = new NestedQuery(_path, booleanQuery);

		QueryFilter queryFilter = new QueryFilter(nestedQuery);

		return new BooleanClauseImpl<>(queryFilter, BooleanClauseOccur.MUST);
	}

	private String _filterField;
	private String _filterValue;
	private String _path;

}