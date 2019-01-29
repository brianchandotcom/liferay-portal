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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search2;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.engine.adapter.search2.BaseSearchRequest;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.QueryTranslator;

import java.util.Collection;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.rescore.QueryRescorerBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = CommonSearchRequestBuilderAssembler.class)
public class CommonSearchRequestBuilderAssemblerImpl
	implements CommonSearchRequestBuilderAssembler {

	@Override
	public void assemble(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		setAggregations(searchRequestBuilder, baseSearchRequest);
		setExplain(searchRequestBuilder, baseSearchRequest);
		setIndexBoosts(searchRequestBuilder, baseSearchRequest);
		setIndices(searchRequestBuilder, baseSearchRequest);
		setMinScore(searchRequestBuilder, baseSearchRequest);
		setPipelineAggregations(searchRequestBuilder, baseSearchRequest);
		setPostFilter(searchRequestBuilder, baseSearchRequest);
		setQuery(searchRequestBuilder, baseSearchRequest);
		setRequestCache(searchRequestBuilder, baseSearchRequest);
		setRescorer(searchRequestBuilder, baseSearchRequest);
		setTimeout(searchRequestBuilder, baseSearchRequest);
		setTrackTotalHits(searchRequestBuilder, baseSearchRequest);
		setTypes(searchRequestBuilder, baseSearchRequest);
	}

	protected QueryBuilder getQueryBuilder(
		BaseSearchRequest baseSearchRequest) {

		return _queryTranslator.translate(baseSearchRequest.getQuery());
	}

	protected void setAggregations(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		Map<String, Aggregation> aggregationsMap =
			baseSearchRequest.getAggregationsMap();

		if (MapUtil.isNotEmpty(aggregationsMap)) {
			Collection<Aggregation> aggregations = aggregationsMap.values();

			aggregations.forEach(
				aggregation -> {
					AggregationBuilder aggregationBuilder =
						_aggregationTranslator.translate(aggregation);

					searchRequestBuilder.addAggregation(aggregationBuilder);
				});
		}
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setAggregationTranslator(
		AggregationTranslator<AggregationBuilder> aggregationTranslator) {

		_aggregationTranslator = aggregationTranslator;
	}

	protected void setExplain(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		if (baseSearchRequest.getExplain() != null) {
			searchRequestBuilder.setExplain(baseSearchRequest.getExplain());
		}
	}

	protected void setIndexBoosts(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		Map<String, Float> indexBoosts = baseSearchRequest.getIndexBoosts();

		if (MapUtil.isNotEmpty(indexBoosts)) {
			indexBoosts.forEach(searchRequestBuilder::addIndexBoost);
		}
	}

	protected void setIndices(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		searchRequestBuilder.setIndices(baseSearchRequest.getIndexNames());
	}

	protected void setMinScore(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		if (baseSearchRequest.getMinimumScore() != null) {
			searchRequestBuilder.setMinScore(
				baseSearchRequest.getMinimumScore());
		}
	}

	protected void setPipelineAggregations(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		Map<String, PipelineAggregation> pipelineAggregationsMap =
			baseSearchRequest.getPipelineAggregationsMap();

		if (MapUtil.isNotEmpty(pipelineAggregationsMap)) {
			Collection<PipelineAggregation> pipelineAggregations =
				pipelineAggregationsMap.values();

			pipelineAggregations.forEach(
				pipelineAggregation -> {
					PipelineAggregationBuilder pipelineAggregationBuilder =
						_pipelineAggregationTranslator.translate(
							pipelineAggregation);

					searchRequestBuilder.addAggregation(
						pipelineAggregationBuilder);
				});
		}
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setPipelineAggregationTranslator(
		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator) {

		_pipelineAggregationTranslator = pipelineAggregationTranslator;
	}

	protected void setPostFilter(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		if (baseSearchRequest.getPostFilterQuery() != null) {
			QueryBuilder postFilterQueryBuilder = _queryTranslator.translate(
				baseSearchRequest.getPostFilterQuery());

			searchRequestBuilder.setPostFilter(postFilterQueryBuilder);
		}
	}

	protected void setQuery(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		searchRequestBuilder.setQuery(getQueryBuilder(baseSearchRequest));
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setQueryTranslator(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_queryTranslator = queryTranslator;
	}

	protected void setRequestCache(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		if (baseSearchRequest.getRequestCache() != null) {
			searchRequestBuilder.setRequestCache(
				baseSearchRequest.getRequestCache());
		}
	}

	protected void setRescorer(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		Query query = baseSearchRequest.getRescoreQuery();

		if (query == null) {
			return;
		}

		searchRequestBuilder.setRescorer(
			new QueryRescorerBuilder(_queryTranslator.translate(query)));
	}

	protected void setTimeout(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		if (Validator.isNotNull(baseSearchRequest.getTimeoutInMilliseconds())) {
			searchRequestBuilder.setTimeout(
				TimeValue.timeValueMillis(
					baseSearchRequest.getTimeoutInMilliseconds()));
		}
	}

	protected void setTrackTotalHits(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		if (baseSearchRequest.getTrackTotalHits() != null) {
			searchRequestBuilder.setTrackTotalHits(
				baseSearchRequest.getTrackTotalHits());
		}
	}

	protected void setTypes(
		SearchRequestBuilder searchRequestBuilder,
		BaseSearchRequest baseSearchRequest) {

		if (baseSearchRequest.getTypes() != null) {
			searchRequestBuilder.setTypes(baseSearchRequest.getTypes());
		}
	}

	private AggregationTranslator<AggregationBuilder> _aggregationTranslator;
	private PipelineAggregationTranslator<PipelineAggregationBuilder>
		_pipelineAggregationTranslator;
	private QueryTranslator<QueryBuilder> _queryTranslator;

}