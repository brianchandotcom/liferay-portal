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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.AggregationResultTranslator;
import com.liferay.portal.search.aggregation.AggregationResults;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationResultTranslator;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.AggregationResultTranslatorFactory;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.ElasticsearchAggregationResultTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.ElasticsearchAggregationResultsTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.PipelineAggregationResultTranslatorFactory;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.pipeline.ElasticsearchPipelineAggregationResultTranslator;
import com.liferay.portal.search.elasticsearch7.internal.hits.SearchHitsTranslator;
import com.liferay.portal.search.elasticsearch7.internal.search.response.SearchResponseTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.highlight.HighlightFieldBuilderFactory;
import com.liferay.portal.search.hits.SearchHitBuilderFactory;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.hits.SearchHitsBuilderFactory;

import java.util.Map;
import java.util.stream.Stream;

import org.apache.lucene.search.TotalHits;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregations;

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
		SearchRequestBuilder searchRequestBuilder,
		SearchResponse searchResponse, SearchSearchRequest searchSearchRequest,
		SearchSearchResponse searchSearchResponse) {

		_commonSearchResponseAssembler.assemble(
			searchRequestBuilder, searchResponse, searchSearchRequest,
			searchSearchResponse);

		addAggregations(
			searchResponse, searchSearchResponse, searchSearchRequest);
		setCount(searchResponse, searchSearchResponse);
		setScrollId(searchResponse, searchSearchResponse);
		setSearchHits(
			searchResponse, searchSearchResponse, searchSearchRequest);

		_searchResponseTranslator.populate(
			searchSearchResponse, searchResponse, searchSearchRequest);
	}

	@Override
	public AggregationResultTranslator createAggregationResultTranslator(
		org.elasticsearch.search.aggregations.Aggregation
			elasticsearchAggregation) {

		return new ElasticsearchAggregationResultTranslator(
			elasticsearchAggregation, _aggregationResults,
			new SearchHitsTranslator(
				_searchHitBuilderFactory, _searchHitsBuilderFactory,
				_documentBuilderFactory, _highlightFieldBuilderFactory,
				_geoBuilders),
			_geoBuilders);
	}

	@Override
	public PipelineAggregationResultTranslator
		createPipelineAggregationResultTranslator(
			org.elasticsearch.search.aggregations.Aggregation
				elasticsearchAggregation) {

		return new ElasticsearchPipelineAggregationResultTranslator(
			elasticsearchAggregation, _aggregationResults);
	}

	protected void addAggregations(
		SearchResponse searchResponse,
		SearchSearchResponse searchSearchResponse,
		SearchSearchRequest searchSearchRequest) {

		Aggregations elasticsearchAggregations =
			searchResponse.getAggregations();

		if (elasticsearchAggregations == null) {
			return;
		}

		Map<String, Aggregation> aggregationsMap =
			searchSearchRequest.getAggregationsMap();

		Map<String, PipelineAggregation> pipelineAggregationsMap =
			searchSearchRequest.getPipelineAggregationsMap();

		ElasticsearchAggregationResultsTranslator
			elasticsearchAggregationResultsTranslator =
				new ElasticsearchAggregationResultsTranslator(
					this, this, aggregationsMap::get,
					pipelineAggregationsMap::get);

		Stream<AggregationResult> stream =
			elasticsearchAggregationResultsTranslator.translate(
				elasticsearchAggregations);

		stream.forEach(searchSearchResponse::addAggregationResult);
	}

	@Reference(unbind = "-")
	protected void setAggregationResults(
		AggregationResults aggregationResults) {

		_aggregationResults = aggregationResults;
	}

	@Reference(unbind = "-")
	protected void setCommonSearchResponseAssembler(
		CommonSearchResponseAssembler commonSearchResponseAssembler) {

		_commonSearchResponseAssembler = commonSearchResponseAssembler;
	}

	protected void setCount(
		SearchResponse searchResponse,
		SearchSearchResponse searchSearchResponse) {

		org.elasticsearch.search.SearchHits searchHits =
			searchResponse.getHits();

		TotalHits totalHits = searchHits.getTotalHits();

		searchSearchResponse.setCount(totalHits.value);
	}

	@Reference(unbind = "-")
	protected void setDocumentBuilderFactory(
		DocumentBuilderFactory documentBuilderFactory) {

		_documentBuilderFactory = documentBuilderFactory;
	}

	@Reference(unbind = "-")
	protected void setGeoBuilders(GeoBuilders geoBuilders) {
		_geoBuilders = geoBuilders;
	}

	@Reference(unbind = "-")
	protected void setHighlightFieldBuilderFactory(
		HighlightFieldBuilderFactory highlightFieldBuilderFactory) {

		_highlightFieldBuilderFactory = highlightFieldBuilderFactory;
	}

	protected void setScrollId(
		SearchResponse searchResponse,
		SearchSearchResponse searchSearchResponse) {

		if (Validator.isNotNull(searchResponse.getScrollId())) {
			searchSearchResponse.setScrollId(searchResponse.getScrollId());
		}
	}

	@Reference(unbind = "-")
	protected void setSearchHitBuilderFactory(
		SearchHitBuilderFactory searchHitBuilderFactory) {

		_searchHitBuilderFactory = searchHitBuilderFactory;
	}

	protected void setSearchHits(
		SearchResponse searchResponse,
		SearchSearchResponse searchSearchResponse,
		SearchSearchRequest searchSearchRequest) {

		SearchHitsTranslator searchHitsTranslator = new SearchHitsTranslator(
			_searchHitBuilderFactory, _searchHitsBuilderFactory,
			_documentBuilderFactory, _highlightFieldBuilderFactory,
			_geoBuilders);

		org.elasticsearch.search.SearchHits elasticsearchSearchHits =
			searchResponse.getHits();

		SearchHits searchHits = searchHitsTranslator.translate(
			searchSearchRequest, elasticsearchSearchHits,
			searchSearchRequest.getAlternateUidFieldName());

		searchSearchResponse.setSearchHits(searchHits);
	}

	@Reference(unbind = "-")
	protected void setSearchHitsBuilderFactory(
		SearchHitsBuilderFactory searchHitsBuilderFactory) {

		_searchHitsBuilderFactory = searchHitsBuilderFactory;
	}

	@Reference(unbind = "-")
	protected void setSearchResponseTranslator(
		SearchResponseTranslator searchResponseTranslator) {

		_searchResponseTranslator = searchResponseTranslator;
	}

	private AggregationResults _aggregationResults;
	private CommonSearchResponseAssembler _commonSearchResponseAssembler;
	private DocumentBuilderFactory _documentBuilderFactory;
	private GeoBuilders _geoBuilders;
	private HighlightFieldBuilderFactory _highlightFieldBuilderFactory;
	private SearchHitBuilderFactory _searchHitBuilderFactory;
	private SearchHitsBuilderFactory _searchHitsBuilderFactory;
	private SearchResponseTranslator _searchResponseTranslator;

}