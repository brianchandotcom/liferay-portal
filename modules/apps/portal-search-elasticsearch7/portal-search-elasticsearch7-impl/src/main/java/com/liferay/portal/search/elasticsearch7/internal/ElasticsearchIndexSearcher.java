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

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexSearcher;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.elasticsearch7.constants.ElasticsearchSearchContextAttributes;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.BaseSearchRequest;
import com.liferay.portal.search.engine.adapter.search.BaseSearchResponse;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponseBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Milen Dyankov
 */
@Component(
	property = "search.engine.impl=Elasticsearch", service = IndexSearcher.class
)
public class ElasticsearchIndexSearcher extends BaseIndexSearcher {

	@Override
	public String getQueryString(SearchContext searchContext, Query query) {
		return _searchEngineAdapter.getQueryString(query);
	}

	@Override
	public Hits search(SearchContext searchContext, Query query) {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			int end = searchContext.getEnd();
			int start = searchContext.getStart();

			SearchRequest searchRequest = _getSearchRequest(searchContext);

			Integer from = searchRequest.getFrom();
			Integer size = searchRequest.getSize();

			if ((from == null) && (size != null)) {
				end = size;
				start = 0;
			}
			else if ((from != null) && (size != null)) {
				end = from + size;
				start = from;
			}

			if (start == QueryUtil.ALL_POS) {
				start = 0;
			}
			else if (start < 0) {
				throw new IllegalArgumentException("Invalid start " + start);
			}

			if (end == QueryUtil.ALL_POS) {
				end = GetterUtil.getInteger(
					_props.get(PropsKeys.INDEX_SEARCH_LIMIT));
			}
			else if (end < 0) {
				throw new IllegalArgumentException("Invalid end " + end);
			}

			SearchResponseBuilder searchResponseBuilder =
				_getSearchResponseBuilder(searchContext);

			Hits hits = null;

			if (FeatureFlagManagerUtil.isEnabled("LPS-172416")) {
				// TODO: LPS-172416
			} else {

				while (true) {
					SearchSearchRequest searchSearchRequest =
						createSearchSearchRequest(
							searchRequest, searchContext, query, start, end);

					SearchSearchResponse searchSearchResponse =
						_searchEngineAdapter.execute(searchSearchRequest);

					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"The search engine processed ",
								searchSearchResponse.getSearchRequestString(),
								" in ", searchSearchResponse.getExecutionTime(),
								" ms"));
					}

					_populateResponse(
						searchSearchResponse, searchResponseBuilder);

					searchResponseBuilder.searchHits(
						searchSearchResponse.getSearchHits());

					hits = searchSearchResponse.getHits();

					Document[] documents = hits.getDocs();

					if ((documents.length != 0) || (start == 0)) {
						break;
					}

					int[] startAndEnd =
						SearchPaginationUtil.calculateStartAndEnd(
							start, end, hits.getLength());

					start = startAndEnd[0];
					end = startAndEnd[1];
				}
			}

			hits.setStart(stopWatch.getStartTime());

			return hits;
		}
		catch (RuntimeException runtimeException) {
			if (!handle(runtimeException)) {
				if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
					_log.error(runtimeException);
				}
				else {
					throw runtimeException;
				}
			}

			searchContext.setAttribute(
				"search.exception.message",
				_getExceptionMessage(runtimeException));

			return new HitsImpl();
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					StringBundler.concat(
						"Searching took ", stopWatch.getTime(), " ms"));
			}
		}
	}

	@Override
	public long searchCount(SearchContext searchContext, Query query) {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			CountSearchRequest countSearchRequest = _createCountSearchRequest(
				searchContext, query);

			CountSearchResponse countSearchResponse =
				_searchEngineAdapter.execute(countSearchRequest);

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"The search engine processed ",
						countSearchResponse.getSearchRequestString(), " in ",
						countSearchResponse.getExecutionTime(), " ms"));
			}

			_populateResponse(
				countSearchResponse, _getSearchResponseBuilder(searchContext));

			return countSearchResponse.getCount();
		}
		catch (RuntimeException runtimeException) {
			if (!handle(runtimeException)) {
				if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
					_log.error(runtimeException);
				}
				else {
					throw runtimeException;
				}
			}

			return 0;
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					StringBundler.concat(
						"Searching took ", stopWatch.getTime(), " ms"));
			}
		}
	}

	protected SearchSearchRequest createSearchSearchRequest(
		SearchRequest searchRequest, SearchContext searchContext, Query query,
		int start, int end) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		_prepare(searchSearchRequest, searchRequest, query, searchContext);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		searchSearchRequest.setAlternateUidFieldName(
			queryConfig.getAlternateUidFieldName());

		searchSearchRequest.setBasicFacetSelection(
			searchRequest.isBasicFacetSelection());

		searchSearchRequest.putAllFacets(searchContext.getFacets());

		searchSearchRequest.setFetchSource(searchRequest.getFetchSource());
		searchSearchRequest.setFetchSourceExcludes(
			searchRequest.getFetchSourceExcludes());
		searchSearchRequest.setFetchSourceIncludes(
			searchRequest.getFetchSourceIncludes());
		searchSearchRequest.setGroupBy(searchContext.getGroupBy());
		searchSearchRequest.setGroupByRequests(
			searchRequest.getGroupByRequests());
		searchSearchRequest.setHighlightEnabled(
			queryConfig.isHighlightEnabled());
		searchSearchRequest.setHighlightFieldNames(
			queryConfig.getHighlightFieldNames());
		searchSearchRequest.setHighlightFragmentSize(
			queryConfig.getHighlightFragmentSize());
		searchSearchRequest.setHighlightSnippetSize(
			queryConfig.getHighlightSnippetSize());
		searchSearchRequest.setLocale(queryConfig.getLocale());
		searchSearchRequest.setHighlightRequireFieldMatch(
			queryConfig.isHighlightRequireFieldMatch());
		searchSearchRequest.setLuceneSyntax(
			GetterUtil.getBoolean(
				searchContext.getAttribute(
					SearchContextAttributes.ATTRIBUTE_KEY_LUCENE_SYNTAX)));

		String preference = (String)searchContext.getAttribute(
			ElasticsearchSearchContextAttributes.
				ATTRIBUTE_KEY_SEARCH_REQUEST_PREFERENCE);

		if (!Validator.isBlank(preference)) {
			searchSearchRequest.setPreference(preference);
		}

		searchSearchRequest.setScoreEnabled(queryConfig.isScoreEnabled());
		searchSearchRequest.setSelectedFieldNames(
			queryConfig.getSelectedFieldNames());

		int size = end - start;

		searchSearchRequest.setSize(size);

		searchSearchRequest.setStart(start);
		searchSearchRequest.setSorts(searchContext.getSorts());
		searchSearchRequest.setSorts(searchRequest.getSorts());
		searchSearchRequest.setStats(searchContext.getStats());

		return searchSearchRequest;
	}

	@Override
	protected QuerySuggester getQuerySuggester() {
		return _querySuggester;
	}

	protected boolean handle(Exception exception) {
		Throwable throwable = exception.getCause();

		if (throwable == null) {
			return false;
		}

		String message = throwable.getMessage();

		if (message == null) {
			return false;
		}

		if (message.contains(
				"Text fields are not optimised for operations that require " +
					"per-document field data")) {

			_log.error(
				"Unable to aggregate facet on a nonkeyword field", exception);

			return true;
		}

		return false;
	}

	protected void setIndexNames(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest,
		SearchContext searchContext) {

		baseSearchRequest.setIndexNames(
			_getIndexes(searchRequest, searchContext));
	}

	protected void setQuery(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest) {

		baseSearchRequest.setQuery(searchRequest.getQuery());
	}

	private CountSearchRequest _createCountSearchRequest(
		SearchContext searchContext, Query query) {

		CountSearchRequest countSearchRequest = new CountSearchRequest();

		_prepare(
			countSearchRequest, _getSearchRequest(searchContext), query,
			searchContext);

		return countSearchRequest;
	}

	private String _getExceptionMessage(RuntimeException runtimeException) {
		String message = runtimeException.toString();

		for (Throwable throwable : runtimeException.getSuppressed()) {
			message = message.concat("\nSuppressed: " + throwable.getMessage());
		}

		return message;
	}

	private String[] _getIndexes(
		SearchRequest searchRequest, SearchContext searchContext) {

		List<String> indexes = searchRequest.getIndexes();

		if (!indexes.isEmpty()) {
			return indexes.toArray(new String[0]);
		}

		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		return new String[] {indexName};
	}

	private SearchRequest _getSearchRequest(SearchContext searchContext) {
		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			searchContext);

		return searchRequestBuilder.build();
	}

	private SearchRequestBuilder _getSearchRequestBuilder(
		SearchContext searchContext) {

		return _searchRequestBuilderFactory.builder(searchContext);
	}

	private SearchResponseBuilder _getSearchResponseBuilder(
		SearchContext searchContext) {

		return _searchResponseBuilderFactory.builder(searchContext);
	}

	private void _populateResponse(
		BaseSearchResponse baseSearchResponse,
		SearchResponseBuilder searchResponseBuilder) {

		searchResponseBuilder.aggregationResultsMap(
			baseSearchResponse.getAggregationResultsMap()
		).count(
			baseSearchResponse.getCount()
		).requestString(
			baseSearchResponse.getSearchRequestString()
		).responseString(
			baseSearchResponse.getSearchResponseString()
		).statsResponseMap(
			baseSearchResponse.getStatsResponseMap()
		).searchTimeValue(
			baseSearchResponse.getSearchTimeValue()
		);
	}

	private void _populateResponse(
		SearchSearchResponse searchSearchResponse,
		SearchResponseBuilder searchResponseBuilder) {

		_populateResponse(
			(BaseSearchResponse)searchSearchResponse, searchResponseBuilder);

		searchResponseBuilder.groupByResponses(
			searchSearchResponse.getGroupByResponses());
	}

	private void _prepare(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest,
		Query query, SearchContext searchContext) {

		baseSearchRequest.addComplexQueryParts(
			searchRequest.getComplexQueryParts());
		baseSearchRequest.setExplain(searchRequest.isExplain());
		baseSearchRequest.setHighlight(searchRequest.getHighlight());
		baseSearchRequest.setIncludeResponseString(
			searchRequest.isIncludeResponseString());
		baseSearchRequest.setPostFilterQuery(
			searchRequest.getPostFilterQuery());
		baseSearchRequest.addPostFilterComplexQueryParts(
			searchRequest.getPostFilterComplexQueryParts());
		baseSearchRequest.setRescores(searchRequest.getRescores());
		baseSearchRequest.setStatsRequests(searchRequest.getStatsRequests());
		baseSearchRequest.setTrackTotalHits(
			_elasticsearchConfigurationWrapper.trackTotalHits());

		_setAggregations(baseSearchRequest, searchRequest);
		_setConnectionId(baseSearchRequest, searchRequest);
		setIndexNames(baseSearchRequest, searchRequest, searchContext);
		_setLegacyQuery(baseSearchRequest, query);
		_setLegacyPostFilter(baseSearchRequest, query);
		_setPipelineAggregations(baseSearchRequest, searchRequest);
		setQuery(baseSearchRequest, searchRequest);
	}

	private void _setAggregations(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest) {

		Map<String, Aggregation> map = searchRequest.getAggregationsMap();

		for (Aggregation aggregation : map.values()) {
			baseSearchRequest.addAggregation(aggregation);
		}
	}

	private void _setConnectionId(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest) {

		baseSearchRequest.setConnectionId(searchRequest.getConnectionId());
	}

	private void _setLegacyPostFilter(
		BaseSearchRequest baseSearchRequest, Query query) {

		if (query != null) {
			baseSearchRequest.setPostFilter(query.getPostFilter());
		}
	}

	private void _setLegacyQuery(
		BaseSearchRequest baseSearchRequest, Query query) {

		baseSearchRequest.setQuery(query);
	}

	private void _setPipelineAggregations(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest) {

		Map<String, PipelineAggregation> map =
			searchRequest.getPipelineAggregationsMap();

		for (PipelineAggregation aggregation : map.values()) {
			baseSearchRequest.addPipelineAggregation(aggregation);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchIndexSearcher.class);

	@Reference
	private volatile ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Props _props;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private QuerySuggester _querySuggester;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SearchResponseBuilderFactory _searchResponseBuilderFactory;

}