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

package com.liferay.commerce.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.SimpleFacet;
import com.liferay.portal.kernel.search.filter.Filter;

/**
 * @author Andrea Di Giorgi
 */
public class NegatableSimpleFacet extends SimpleFacet {

	public NegatableSimpleFacet(SearchContext searchContext) {
		super(searchContext);
	}

	public boolean isNegated() {
		return _negated;
	}

	public void setNegated(boolean negated) {
		_negated = negated;
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		BooleanClause<Filter> booleanClause =
			super.doGetFacetFilterBooleanClause();

		if (isNegated()) {
			booleanClause = BooleanClauseFactoryUtil.createFilter(
				booleanClause.getClause(), BooleanClauseOccur.MUST_NOT);
		}

		return booleanClause;
	}

	private boolean _negated;

}