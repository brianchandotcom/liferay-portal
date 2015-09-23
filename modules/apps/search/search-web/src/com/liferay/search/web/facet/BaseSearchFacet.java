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

package com.liferay.search.web.facet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.util.FacetFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.web.util.SearchFacet;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseSearchFacet implements SearchFacet {

	@Override
	public String getClassName() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	@Override
	public JSONObject getData() {
		return _facetConfiguration.getData();
	}

	@Override
	public Facet getFacet() {
		return _facet;
	}

	@Override
	public FacetConfiguration getFacetConfiguration() {
		return _facetConfiguration;
	}

	@Override
	public String getId() {
		return getClassName();
	}

	@Override
	public String getOrder() {
		return "OrderHitsDesc";
	}

	@Override
	public String getTitle() {
		return getLabel();
	}

	@Override
	public double getWeight() {
		return _facetConfiguration.getWeight();
	}

	@Override
	public void init(long companyId, String searchConfiguration)
		throws Exception {

		init(companyId, searchConfiguration, null);
	}

	@Override
	public void init(
			long companyId, String searchConfiguration,
			SearchContext searchContext)
		throws Exception {

		_facetConfiguration = _getFacetConfiguration(searchConfiguration);

		if (_facetConfiguration == null) {
			_facetConfiguration = getDefaultConfiguration(companyId);
		}

		if (searchContext != null) {
			_facet = FacetFactoryUtil.create(
				searchContext, _facetConfiguration);
		}
	}

	@Override
	public boolean isStatic() {
		return _facetConfiguration.isStatic();
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

			String searchFacetId = facetJSONObject.getString("id");

			if (!searchFacetId.equals(getId())) {
				continue;
			}

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

		facetConfiguration.setFieldName(facetJSONObject.getString("fieldName"));
		facetConfiguration.setLabel(facetJSONObject.getString("label"));
		facetConfiguration.setOrder(facetJSONObject.getString("order"));
		facetConfiguration.setStatic(facetJSONObject.getBoolean("static"));
		facetConfiguration.setWeight(facetJSONObject.getDouble("weight"));

		return facetConfiguration;
	}

	private Facet _facet;
	private FacetConfiguration _facetConfiguration;

}