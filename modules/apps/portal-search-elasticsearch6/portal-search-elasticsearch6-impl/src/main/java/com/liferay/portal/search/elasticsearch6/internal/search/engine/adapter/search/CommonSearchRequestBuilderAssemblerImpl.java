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
		BaseSearchRequest searchSearchRequest) {

		searchRequestBuilder.setIndices(searchSearchRequest.getIndexNames());

		if (searchSearchRequest.getMinimumScore() > 0) {
			searchRequestBuilder.setMinScore(
				searchSearchRequest.getMinimumScore());
		}

		if (searchSearchRequest.getPostFilter() != null) {
			QueryBuilder postFilterQueryBuilder = filterTranslator.translate(
				searchSearchRequest.getPostFilter(), null);

			searchRequestBuilder.setPostFilter(postFilterQueryBuilder);
		}

		searchRequestBuilder.setQuery(getQueryBuilder(searchSearchRequest));

		if (searchSearchRequest.isRequestCache()) {
			searchRequestBuilder.setRequestCache(
				searchSearchRequest.isRequestCache());
		}

		if (searchSearchRequest.getTimeoutInMilliseconds() > 0) {
			searchRequestBuilder.setTimeout(
				TimeValue.timeValueMillis(
					searchSearchRequest.getTimeoutInMilliseconds()));
		}

		searchRequestBuilder.setTrackTotalHits(
			searchSearchRequest.isTrackTotalHits());
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

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	protected FilterTranslator<QueryBuilder> filterTranslator;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	protected QueryTranslator<QueryBuilder> queryTranslator;

}