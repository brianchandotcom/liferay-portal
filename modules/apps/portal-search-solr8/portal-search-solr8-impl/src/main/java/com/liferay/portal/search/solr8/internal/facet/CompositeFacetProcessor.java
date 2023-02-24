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

package com.liferay.portal.search.solr8.internal.facet;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(service = {CompositeFacetProcessor.class, FacetProcessor.class})
public class CompositeFacetProcessor implements FacetProcessor<SolrQuery> {

	@Override
	public Map<String, JSONObject> processFacet(Facet facet) {
		Class<?> clazz = facet.getClass();

		FacetProcessor<SolrQuery> facetProcessor = _facetProcessors.get(
			clazz.getName());

		if (facetProcessor == null) {
			facetProcessor = _defaultFacetProcessor;
		}

		return facetProcessor.processFacet(facet);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, target = "(class.name=*)"
	)
	protected void setFacetProcessor(
		FacetProcessor<SolrQuery> facetProcessor,
		Map<String, Object> properties) {

		String className = MapUtil.getString(properties, "class.name");

		_facetProcessors.put(className, facetProcessor);
	}

	protected void unsetFacetProcessor(
		FacetProcessor<SolrQuery> facetProcessor,
		Map<String, Object> properties) {

		String className = MapUtil.getString(properties, "class.name");

		_facetProcessors.remove(className);
	}

	private final FacetProcessor<SolrQuery> _defaultFacetProcessor =
		new FacetProcessor<SolrQuery>() {

			@Override
			public Map<String, JSONObject> processFacet(Facet facet) {
				return LinkedHashMapBuilder.<String, JSONObject>put(
					FacetUtil.getAggregationName(facet),
					_getFacetParametersJSONObject(facet)
				).build();
			}

			private JSONObject _getFacetParametersJSONObject(Facet facet) {
				JSONObject jsonObject = _jsonFactory.createJSONObject();

				jsonObject.put(
					"field", facet.getFieldName()
				).put(
					"type", "terms"
				);

				FacetConfiguration facetConfiguration =
					facet.getFacetConfiguration();

				JSONObject dataJSONObject = facetConfiguration.getData();

				int minCount = dataJSONObject.getInt("frequencyThreshold");

				if (minCount > 0) {
					jsonObject.put("mincount", minCount);
				}

				int limit = dataJSONObject.getInt("maxTerms");

				if (limit > 0) {
					jsonObject.put("limit", limit);
				}

				String sortParam = "count";
				String sortValue = "desc";

				String order = facetConfiguration.getOrder();

				if (order.equals("OrderValueAsc")) {
					sortParam = "index";
					sortValue = "asc";
				}

				JSONObject sortJSONObject = _jsonFactory.createJSONObject();

				sortJSONObject.put(sortParam, sortValue);

				jsonObject.put("sort", sortJSONObject);

				return jsonObject;
			}

		};

	private final Map<String, FacetProcessor<SolrQuery>> _facetProcessors =
		new HashMap<>();

	@Reference
	private JSONFactory _jsonFactory;

}