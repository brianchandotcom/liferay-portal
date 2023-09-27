/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.facet.Facet;

/**
 * @author Marco Leo
 * @author Riccardo Alberti
 */
public class SerializableFacet extends BaseFacet implements Facet {

	public SerializableFacet(String fieldName, SearchContext searchContext) {
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

		TermsFilter termsFilter = new TermsFilter(getFieldName());

		termsFilter.addValues(_selections);

		return new BooleanClauseImpl<>(termsFilter, BooleanClauseOccur.MUST);
	}

	private String _aggregationName;
	private String[] _selections = {};

}