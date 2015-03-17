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

package com.liferay.search.web.facets;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.FacetFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.web.util.SearchFacet;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseSearchFacet implements SearchFacet {

	@Override
	public String getConfigurationView() {
		return StringPool.BLANK;
	}

	@Override
	public Facet getFacet(
			SearchContext searchContext, String searchConfiguration)
		throws Exception {

		return FacetFactoryUtil.create(
			searchContext, getFacetConfiguration(searchConfiguration));
	}

	@Override
	public FacetConfiguration getFacetConfiguration() {
		return getDefaultConfiguration();
	}

	@Override
	public FacetConfiguration getFacetConfiguration(String searchConfiguration)
		throws JSONException {

		FacetConfiguration facetConfiguration = _getFacetConfiguration(
			searchConfiguration);

		if (facetConfiguration != null) {
			return facetConfiguration;
		}

		return getDefaultConfiguration();
	}

	private FacetConfiguration _getFacetConfiguration(String configuration)
		throws JSONException {

		if (Validator.isNull(configuration)) {
			return null;
		}

		JSONObject configurationJSONObject = JSONFactoryUtil.createJSONObject(
			configuration);

		JSONArray facetsJSONArray = configurationJSONObject.getJSONArray(
			"facets");

		if (facetsJSONArray == null) {
			return null;
		}

		for (int i = 0; i < facetsJSONArray.length(); i++) {
			JSONObject facetJSONObject = facetsJSONArray.getJSONObject(i);

			return _toFacetConfiguration(facetJSONObject);
		}

		return null;
	}

	private FacetConfiguration _toFacetConfiguration(
		JSONObject facetJSONObject) {

		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setClassName(facetJSONObject.getString("className"));

		if (facetJSONObject.has("data")) {
			facetConfiguration.setDataJSONObject(
				facetJSONObject.getJSONObject("data"));
		}

		facetConfiguration.setDisplayStyle(
			facetJSONObject.getString("displayStyle"));
		facetConfiguration.setFieldName(facetJSONObject.getString("fieldName"));
		facetConfiguration.setLabel(facetJSONObject.getString("label"));
		facetConfiguration.setOrder(facetJSONObject.getString("order"));
		facetConfiguration.setStatic(facetJSONObject.getBoolean("static"));
		facetConfiguration.setWeight(facetJSONObject.getDouble("weight"));

		return facetConfiguration;
	}

}