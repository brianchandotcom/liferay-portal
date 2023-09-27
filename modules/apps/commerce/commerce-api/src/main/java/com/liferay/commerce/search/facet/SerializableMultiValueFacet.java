/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.search.facet;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.util.FacetValueValidator;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alec Sloan
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link SerializableFacet}
 */
@Deprecated
public class SerializableMultiValueFacet extends MultiValueFacet {

	public SerializableMultiValueFacet(SearchContext searchContext) {
		super(searchContext);
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		SearchContext searchContext = getSearchContext();

		String[] values = GetterUtil.getStringValues(
			searchContext.getAttribute(getFieldName()));

		if (ArrayUtil.isEmpty(values)) {
			values = StringUtil.split(
				GetterUtil.getString(
					searchContext.getAttribute(getFieldName())));
		}

		TermsFilter facetTermsFilter = new TermsFilter(getFieldName());

		for (String value : values) {
			FacetValueValidator facetValueValidator = getFacetValueValidator();

			if ((searchContext.getUserId() > 0) &&
				!facetValueValidator.check(searchContext, value)) {

				continue;
			}

			facetTermsFilter.addValue(value);
		}

		if (facetTermsFilter.isEmpty()) {
			return null;
		}

		return BooleanClauseFactoryUtil.createFilter(
			searchContext, facetTermsFilter, BooleanClauseOccur.MUST);
	}

}