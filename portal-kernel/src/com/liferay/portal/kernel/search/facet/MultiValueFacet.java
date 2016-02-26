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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.FacetValueValidator;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Raymond Augé
 */
public class MultiValueFacet extends BaseFacet {

	public MultiValueFacet(SearchContext searchContext) {
		super(searchContext);
	}

	public void setValues(boolean[] values) {
		if (ArrayUtil.isEmpty(values)) {
			return;
		}

		JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray();

		for (boolean value : values) {
			valuesJSONArray.put(value);
		}

		doSetValues(valuesJSONArray);
	}

	public void setValues(double[] values) {
		if (ArrayUtil.isEmpty(values)) {
			return;
		}

		JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray();

		for (double value : values) {
			valuesJSONArray.put(value);
		}

		doSetValues(valuesJSONArray);
	}

	public void setValues(int[] values) {
		if (ArrayUtil.isEmpty(values)) {
			return;
		}

		JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray();

		for (int value : values) {
			valuesJSONArray.put(value);
		}

		doSetValues(valuesJSONArray);
	}

	public void setValues(JSONArray values) {
		doSetValues(values);
	}

	public void setValues(JSONObject[] values) {
		if (ArrayUtil.isEmpty(values)) {
			return;
		}

		JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray();

		for (JSONObject value : values) {
			valuesJSONArray.put(value);
		}

		doSetValues(valuesJSONArray);
	}

	public void setValues(long[] values) {
		if (ArrayUtil.isEmpty(values)) {
			return;
		}

		JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray();

		for (long value : values) {
			valuesJSONArray.put(value);
		}

		doSetValues(valuesJSONArray);
	}

	public void setValues(String[] values) {
		if (ArrayUtil.isEmpty(values)) {
			return;
		}

		JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray();

		for (String value : values) {
			valuesJSONArray.put(value);
		}

		doSetValues(valuesJSONArray);
	}

	@Override
	protected BooleanClause<Filter> doGetFacetFilterBooleanClause() {
		SearchContext searchContext = getSearchContext();

		FacetConfiguration facetConfiguration = getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		String[] values = null;

		if (isStatic() && dataJSONObject.has("values")) {
			JSONArray valuesJSONArray = dataJSONObject.getJSONArray("values");

			values = new String[valuesJSONArray.length()];

			for (int i = 0; i < valuesJSONArray.length(); i++) {
				values[i] = valuesJSONArray.getString(i);
			}
		}

		String[] valuesParam = StringUtil.split(
			GetterUtil.getString(searchContext.getAttribute(getFieldId())));

		if (!isStatic() && (valuesParam != null) && (valuesParam.length > 0)) {
			values = valuesParam;
		}

		if (ArrayUtil.isEmpty(values)) {
			return null;
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

	protected void doSetValues(JSONArray valuesJSONArray) {
		FacetConfiguration facetConfiguration = getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		dataJSONObject.put("values", valuesJSONArray);
	}

}