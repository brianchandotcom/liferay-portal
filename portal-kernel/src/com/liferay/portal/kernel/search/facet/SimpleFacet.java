/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.facet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Raymond Augé
 */
public class SimpleFacet extends BaseFacet {

	public SimpleFacet(SearchContext searchContext) {
		super(searchContext);
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		SearchContext searchContext = getSearchContext();

		FacetConfiguration facetConfiguration = getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		String value = StringPool.BLANK;

		if (isStatic() && dataJSONObject.has("value")) {
			value = dataJSONObject.getString("value");
		}

		String valueParam = GetterUtil.getString(
			searchContext.getAttribute(getFieldId()));

		if (!isStatic() && Validator.isNotNull(valueParam)) {
			value = valueParam;
		}

		if (Validator.isNull(value)) {
			return null;
		}

		return BooleanClauseFactoryUtil.createFilter(
			searchContext, getFieldName(), value, BooleanClauseOccur.MUST);
	}

}