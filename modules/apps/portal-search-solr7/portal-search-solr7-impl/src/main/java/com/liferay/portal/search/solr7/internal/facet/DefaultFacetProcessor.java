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

package com.liferay.portal.search.solr7.internal.facet;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = "class.name=DEFAULT",
	service = FacetProcessor.class
)
public class DefaultFacetProcessor implements FacetProcessor<SolrQuery> {

	@Override
	public Map<String, JSONObject> processFacet(Facet facet) {
		Map<String, JSONObject> map = new LinkedHashMap<>();

		String name = FacetUtil.getAggregationName(facet);

		map.put(name, getFacetParameters(facet));

		return map;
	}

	protected void applyFrequencyThreshold(
		JSONObject jsonObject, JSONObject dataJSONObject) {

		int minCount = dataJSONObject.getInt("frequencyThreshold");

		if (minCount > 0) {
			jsonObject.put("mincount", minCount);
		}
	}

	protected void applyMaxTerms(
		JSONObject jsonObject, JSONObject dataJSONObject) {

		int limit = dataJSONObject.getInt("maxTerms");

		if (limit > 0) {
			jsonObject.put("limit", limit);
		}
	}

	protected void applySort(
		JSONObject jsonObject, FacetConfiguration facetConfiguration) {

		String sortParam = "count";
		String sortValue = "desc";

		String order = facetConfiguration.getOrder();

		if (order.equals("OrderValueAsc")) {
			sortParam = "index";
			sortValue = "asc";
		}

		JSONObject sortJSONObject = jsonFactory.createJSONObject();

		sortJSONObject.put(sortParam, sortValue);

		jsonObject.put("sort", sortJSONObject);
	}

	protected JSONObject getFacetParameters(Facet facet) {
		JSONObject jsonObject = jsonFactory.createJSONObject();

		jsonObject.put("field", facet.getFieldName());

		jsonObject.put("type", "terms");

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		applyFrequencyThreshold(jsonObject, dataJSONObject);
		applyMaxTerms(jsonObject, dataJSONObject);

		applySort(jsonObject, facetConfiguration);

		return jsonObject;
	}

	@Reference
	protected JSONFactory jsonFactory;

}