/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.order.web.internal.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.filter.Filter;

/**
 * @author Andrea Di Giorgi
 */
public class NegatableMultiValueFacet extends MultiValueFacet {

	public NegatableMultiValueFacet(SearchContext searchContext) {
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