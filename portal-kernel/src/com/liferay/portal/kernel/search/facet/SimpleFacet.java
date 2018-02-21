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