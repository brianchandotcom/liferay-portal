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

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.nested.NestedFacet;

import java.util.Optional;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Díaz
 */
@Component(
	immediate = true,
	property = "class.name=com.liferay.portal.search.internal.facet.NestedFacetImpl",
	service = FacetProcessor.class
)
public class NestedFacetProcessor
	implements FacetProcessor<SearchRequestBuilder> {

	@Override
	public Optional<AggregationBuilder> processFacet(Facet facet) {
		if (!(facet instanceof NestedFacet)) {
			return Optional.empty();
		}

		NestedFacet nestedFacet = (NestedFacet)facet;

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		TermsAggregationBuilder termsAggregationBuilder =
			AggregationBuilders.terms(FacetUtil.getAggregationName(facet));

		termsAggregationBuilder.field(nestedFacet.getFieldName());

		int minDocCount = dataJSONObject.getInt("frequencyThreshold");

		if (minDocCount > 0) {
			termsAggregationBuilder.minDocCount(minDocCount);
		}

		int size = dataJSONObject.getInt("maxTerms");

		if (size > 0) {
			termsAggregationBuilder.size(size);
		}

		AggregationBuilder aggregationBuilder = termsAggregationBuilder;

		if (Validator.isNotNull(nestedFacet.getFilterField())) {
			TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(
				nestedFacet.getFilterField(), nestedFacet.getFilterValue());

			FilterAggregationBuilder filterAggregationBuilder =
				AggregationBuilders.filter(
					FacetUtil.getAggregationName(facet), termQueryBuilder);

			filterAggregationBuilder.subAggregation(termsAggregationBuilder);

			aggregationBuilder = filterAggregationBuilder;
		}

		NestedAggregationBuilder nestedAggregationBuilder =
			AggregationBuilders.nested(
				FacetUtil.getAggregationName(facet), nestedFacet.getPath());

		nestedAggregationBuilder.subAggregation(aggregationBuilder);

		return Optional.of(nestedAggregationBuilder);
	}

}