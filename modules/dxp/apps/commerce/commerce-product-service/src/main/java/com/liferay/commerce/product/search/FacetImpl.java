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