/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.nested.NestedFacet;

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
	property = "class.name=com.liferay.portal.search.internal.facet.NestedFacetImpl",
	service = FacetProcessor.class
)
public class NestedFacetProcessor
	implements FacetProcessor<SearchRequestBuilder> {

	@Override
	public AggregationBuilder processFacet(Facet facet) {
		if (!(facet instanceof NestedFacet)) {
			return null;
		}

		NestedFacet nestedFacet = (NestedFacet)facet;

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		TermsAggregationBuilder termsAggregationBuilder =
			AggregationBuilders.terms(FacetUtil.getAggregationName(facet));

		termsAggregationBuilder.field(nestedFacet.getFieldName());

		int minDocCount = dataJSONObject.getInt("frequencyThreshold", -1);

		if (minDocCount >= 0) {
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

		return nestedAggregationBuilder;
	}

}