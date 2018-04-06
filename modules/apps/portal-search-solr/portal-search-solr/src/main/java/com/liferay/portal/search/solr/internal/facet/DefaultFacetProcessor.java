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

package com.liferay.portal.search.solr.internal.facet;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.search.solr.facet.FacetProcessor;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.FacetParams;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, property = "class.name=DEFAULT")
public class DefaultFacetProcessor implements FacetProcessor<SolrQuery> {

	@Override
	public void processFacet(SolrQuery solrQuery, Facet facet) {
		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		String fieldName = facetConfiguration.getFieldName();

		String prefix = "f." + fieldName + ".";

		JSONObject dataJSONObject = facetConfiguration.getData();

		applyFrequencyThreshold(solrQuery, prefix, dataJSONObject);
		applyMaxTerms(solrQuery, prefix, dataJSONObject);

		solrQuery.addFacetField(fieldName);
	}

	protected void applyFrequencyThreshold(
		SolrQuery solrQuery, String prefix, JSONObject dataJSONObject) {

		int minCount = dataJSONObject.getInt("frequencyThreshold");

		if (minCount > 0) {
			solrQuery.set(prefix.concat(FacetParams.FACET_MINCOUNT), minCount);
		}
	}

	protected void applyMaxTerms(
		SolrQuery solrQuery, String prefix, JSONObject dataJSONObject) {

		int limit = dataJSONObject.getInt("maxTerms");

		if (limit > 0) {
			solrQuery.set(prefix.concat(FacetParams.FACET_LIMIT), limit);
		}
	}

}