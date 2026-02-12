/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.ResponseBody;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.AggregationResultTranslator;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationResultTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.AggregationResultTranslatorFactory;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.ElasticsearchAggregationResultTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.ElasticsearchAggregationResultsTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.ElasticsearchPipelineAggregationResultTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.PipelineAggregationResultTranslatorFactory;
import com.liferay.portal.search.elasticsearch8.internal.hits.HitsMetadataTranslator;
import com.liferay.portal.search.elasticsearch8.internal.search.response.SearchResponseTranslator;
import com.liferay.portal.search.elasticsearch8.internal.util.SetterUtil;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHitBuilderFactory;
import com.liferay.portal.search.hits.SearchHitsBuilderFactory;
import com.liferay.portal.search.legacy.stats.StatsResultsTranslator;
import com.liferay.portal.search.searcher.SearchTimeValue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = SearchSearchResponseAssembler.class)
public class SearchSearchResponseAssemblerImpl
	implements AggregationResultTranslatorFactory,
			   PipelineAggregationResultTranslatorFactory,
			   SearchSearchResponseAssembler {

	@Override
	public void assemble(
		ResponseBody responseBody, String searchRequestString,
		SearchSearchRequest searchSearchRequest,
		SearchSearchResponse searchSearchResponse) {

		CommonSearchResponseAssembler.INSTANCE.assemble(
			searchSearchRequest, searchSearchResponse, responseBody,
			searchRequestString);

		_addAggregations(
			responseBody, searchSearchRequest, searchSearchResponse);
		setCount(responseBody, searchSearchResponse);
		_setScrollId(responseBody, searchSearchResponse);
		_setSearchHits(responseBody, searchSearchRequest, searchSearchResponse);
		_setSearchTimeValue(responseBody, searchSearchResponse);

		_searchResponseTranslator.populate(
			responseBody, searchSearchRequest, searchSearchResponse);
	}

	@Override
	public AggregationResultTranslator createAggregationResultTranslator(
		Aggregate aggregate) {

		return new ElasticsearchAggregationResultTranslator(
			aggregate,
			new HitsMetadataTranslator(
				_searchHitBuilderFactory, _searchHitsBuilderFactory));
	}

	@Override
	public PipelineAggregationResultTranslator
		createPipelineAggregationResultTranslator(Aggregate aggregate) {

		return new ElasticsearchPipelineAggregationResultTranslator(aggregate);
	}

	@Activate
	protected void activate() {
		_searchResponseTranslator = new SearchResponseTranslator(
			_statsResultsTranslator);
	}

	protected void setCount(
		ResponseBody responseBody, SearchSearchResponse searchSearchResponse) {

		HitsMetadata<JsonData> hitsMetadata = responseBody.hits();

		TotalHits totalHits = hitsMetadata.total();

		searchSearchResponse.setCount(totalHits.value());
	}

	private void _addAggregations(
		ResponseBody responseBody, SearchSearchRequest searchSearchRequest,
		SearchSearchResponse searchSearchResponse) {

		Map<String, Aggregate> aggregates = responseBody.aggregations();

		if (MapUtil.isEmpty(aggregates)) {
			return;
		}

		Map<String, Aggregation> aggregationsMap =
			searchSearchRequest.getAggregationsMap();

		Map<String, PipelineAggregation> pipelineAggregationsMap =
			searchSearchRequest.getPipelineAggregationsMap();

		ElasticsearchAggregationResultsTranslator
			elasticsearchAggregationResultsTranslator =
				new ElasticsearchAggregationResultsTranslator(
					aggregationsMap::get, this, pipelineAggregationsMap::get,
					this);

		List<AggregationResult> aggregationResults =
			elasticsearchAggregationResultsTranslator.translate(aggregates);

		for (AggregationResult aggregationResult : aggregationResults) {
			if (aggregationResult != null) {
				searchSearchResponse.addAggregationResult(aggregationResult);
			}
		}
	}

	private void _setScrollId(
		ResponseBody<JsonData> responseBody,
		SearchSearchResponse searchSearchResponse) {

		SetterUtil.setNotBlankString(
			searchSearchResponse::setScrollId, responseBody.scrollId());
	}

	private void _setSearchHits(
		ResponseBody responseBody, SearchSearchRequest searchSearchRequest,
		SearchSearchResponse searchSearchResponse) {

		HitsMetadataTranslator hitsMetadataTranslator =
			new HitsMetadataTranslator(
				_searchHitBuilderFactory, _searchHitsBuilderFactory);

		searchSearchResponse.setSearchHits(
			hitsMetadataTranslator.translate(
				searchSearchRequest.getAlternateUidFieldName(),
				responseBody.hits()));
	}

	private void _setSearchTimeValue(
		ResponseBody responseBody, SearchSearchResponse searchSearchResponse) {

		SearchTimeValue.Builder builder = SearchTimeValue.Builder.newBuilder();

		builder.duration(
			responseBody.took()
		).timeUnit(
			TimeUnit.MILLISECONDS
		);

		searchSearchResponse.setSearchTimeValue(builder.build());
	}

	@Reference
	private SearchHitBuilderFactory _searchHitBuilderFactory;

	@Reference
	private SearchHitsBuilderFactory _searchHitsBuilderFactory;

	private SearchResponseTranslator _searchResponseTranslator;

	@Reference
	private StatsResultsTranslator _statsResultsTranslator;

}