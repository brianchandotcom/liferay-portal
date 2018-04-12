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

package com.liferay.commerce.product.search;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.BaseFacet;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.facet.Facet;

/**
 * @author Marco Leo
 */
public class FacetImpl extends BaseFacet implements Facet {

	public FacetImpl(String fieldName, SearchContext searchContext) {
		super(searchContext);

		setFieldName(fieldName);
	}

	@Override
	public void select(String... selections) {
		_selections = selections;
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		if (ArrayUtil.isEmpty(_selections)) {
			return null;
		}

		BooleanFilter booleanFilter = new BooleanFilter();

		for (String selection : _selections) {
			TermFilter termFilter = new TermFilter(getFieldName(), selection);

			booleanFilter.add(termFilter, BooleanClauseOccur.MUST);
		}

		return new BooleanClauseImpl<>(booleanFilter, BooleanClauseOccur.MUST);
	}

	private String[] _selections;

}