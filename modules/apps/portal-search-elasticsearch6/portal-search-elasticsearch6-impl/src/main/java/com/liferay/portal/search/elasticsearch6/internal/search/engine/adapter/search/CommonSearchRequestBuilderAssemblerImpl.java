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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.filter.FilterTranslator;
import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.search.elasticsearch6.internal.facet.FacetTranslator;
import com.liferay.portal.search.engine.adapter.search.BaseSearchRequest;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, service = CommonSearchRequestBuilderAssembler.class
)
public class CommonSearchRequestBuilderAssemblerImpl
	implements CommonSearchRequestBuilderAssembler {

	@Override
	public void assemble(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		searchRequestBuilder.setIndices(baseSearchRequest.getIndexNames());

		if (baseSearchRequest.getMinimumScore() > 0) {
			searchRequestBuilder.setMinScore(
				baseSearchRequest.getMinimumScore());
		}

		if (baseSearchRequest.getPostFilter() != null) {
			QueryBuilder postFilterQueryBuilder = filterTranslator.translate(
				baseSearchRequest.getPostFilter(), null);

			searchRequestBuilder.setPostFilter(postFilterQueryBuilder);
		}

		searchRequestBuilder.setQuery(getQueryBuilder(baseSearchRequest));

		if (baseSearchRequest.isRequestCache()) {
			searchRequestBuilder.setRequestCache(
				baseSearchRequest.isRequestCache());
		}

		if (baseSearchRequest.getTimeoutInMilliseconds() > 0) {
			searchRequestBuilder.setTimeout(
				TimeValue.timeValueMillis(
					baseSearchRequest.getTimeoutInMilliseconds()));
		}

		searchRequestBuilder.setTrackTotalHits(
			baseSearchRequest.isTrackTotalHits());

		facetTranslator.translate(
			searchRequestBuilder, baseSearchRequest.getQuery(),
			baseSearchRequest.getFacets(),
			baseSearchRequest.isBasicFacetSelection());
	}

	protected QueryBuilder getQueryBuilder(
		BaseSearchRequest searchSearchRequest) {

		Query query = searchSearchRequest.getQuery();

		QueryBuilder queryBuilder = queryTranslator.translate(query, null);

		if (query.getPreBooleanFilter() == null) {
			return queryBuilder;
		}

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		boolQueryBuilder.filter(
			filterTranslator.translate(query.getPreBooleanFilter(), null));
		boolQueryBuilder.must(queryBuilder);

		return boolQueryBuilder;
	}

	@Reference
	protected FacetTranslator facetTranslator;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	protected FilterTranslator<QueryBuilder> filterTranslator;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	protected QueryTranslator<QueryBuilder> queryTranslator;

}