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
	immediate = true, property = "search.engine.impl=Elasticsearch",
	service = IndexSearcher.class
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

			SearchRequest searchRequest = getSearchRequest(searchContext);

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

				populateResponse(searchSearchResponse, searchResponseBuilder);

				searchResponseBuilder.searchHits(
					searchSearchResponse.getSearchHits());

				hits = searchSearchResponse.getHits();

				Document[] documents = hits.getDocs();

				if ((documents.length != 0) || (start == 0)) {
					break;
				}

				int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
					start, end, hits.getLength());

				start = startAndEnd[0];
				end = startAndEnd[1];
			}

			hits.setStart(stopWatch.getStartTime());

			return hits;
		}
		catch (RuntimeException runtimeException) {
			if (!handle(runtimeException)) {
				if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
					_log.error(runtimeException, runtimeException);
				}
				else {
					throw runtimeException;
				}
			}

			return new HitsImpl();
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					StringBundler.concat(
						"Searching ", query, " took ", stopWatch.getTime(),
						" ms"));
			}
		}
	}

	@Override
	public long searchCount(SearchContext searchContext, Query query) {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			CountSearchRequest countSearchRequest = createCountSearchRequest(
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

			populateResponse(
				countSearchResponse, _getSearchResponseBuilder(searchContext));

			return countSearchResponse.getCount();
		}
		catch (RuntimeException runtimeException) {
			if (!handle(runtimeException)) {
				if (_elasticsearchConfigurationWrapper.logExceptionsOnly()) {
					_log.error(runtimeException, runtimeException);
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
						"Searching ", query.toString(), " took ",
						stopWatch.getTime(), " ms"));
			}
		}
	}

	@Override
	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	public void setQuerySuggester(QuerySuggester querySuggester) {
		super.setQuerySuggester(querySuggester);
	}

	protected CountSearchRequest createCountSearchRequest(
		SearchContext searchContext, Query query) {

		CountSearchRequest countSearchRequest = new CountSearchRequest();

		prepare(
			countSearchRequest, getSearchRequest(searchContext), query,
			searchContext);

		return countSearchRequest;
	}

	protected SearchSearchRequest createSearchSearchRequest(
		SearchRequest searchRequest, SearchContext searchContext, Query query,
		int start, int end) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		prepare(searchSearchRequest, searchRequest, query, searchContext);

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

		boolean luceneSyntax = GetterUtil.getBoolean(
			searchContext.getAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_LUCENE_SYNTAX));

		searchSearchRequest.setLuceneSyntax(luceneSyntax);

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

		searchSearchRequest.setTrackTotalHits(
			_elasticsearchConfigurationWrapper.trackTotalHits());

		return searchSearchRequest;
	}

	protected String[] getIndexes(
		SearchRequest searchRequest, SearchContext searchContext) {

		List<String> indexes = searchRequest.getIndexes();

		if (!indexes.isEmpty()) {
			return indexes.toArray(new String[0]);
		}

		String indexName = _indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		return new String[] {indexName};
	}

	protected SearchRequest getSearchRequest(SearchContext searchContext) {
		SearchRequestBuilder searchRequestBuilder = _getSearchRequestBuilder(
			searchContext);

		return searchRequestBuilder.build();
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
				"Fielddata is disabled on text fields by default.")) {

			_log.error(
				"Unable to aggregate facet on a nonkeyword field", exception);

			return true;
		}

		return false;
	}

	protected void populateResponse(
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
		);
	}

	protected void populateResponse(
		SearchSearchResponse searchSearchResponse,
		SearchResponseBuilder searchResponseBuilder) {

		populateResponse(
			(BaseSearchResponse)searchSearchResponse, searchResponseBuilder);

		searchResponseBuilder.groupByResponses(
			searchSearchResponse.getGroupByResponses());
	}

	protected void prepare(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest,
		Query query, SearchContext searchContext) {

		baseSearchRequest.addComplexQueryParts(
			searchRequest.getComplexQueryParts());
		baseSearchRequest.setExplain(searchRequest.isExplain());
		baseSearchRequest.setIncludeResponseString(
			searchRequest.isIncludeResponseString());
		baseSearchRequest.setPostFilterQuery(
			searchRequest.getPostFilterQuery());
		baseSearchRequest.setRescores(searchRequest.getRescores());
		baseSearchRequest.setStatsRequests(searchRequest.getStatsRequests());

		setAggregations(baseSearchRequest, searchRequest);
		setConnectionId(baseSearchRequest, searchRequest);
		setIndexNames(baseSearchRequest, searchRequest, searchContext);
		setLegacyQuery(baseSearchRequest, query);
		setLegacyPostFilter(baseSearchRequest, query);
		setPipelineAggregations(baseSearchRequest, searchRequest);
		setQuery(baseSearchRequest, searchRequest);
	}

	protected void setAggregations(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest) {

		Map<String, Aggregation> map = searchRequest.getAggregationsMap();

		for (Aggregation aggregation : map.values()) {
			baseSearchRequest.addAggregation(aggregation);
		}
	}

	protected void setConnectionId(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest) {

		baseSearchRequest.setConnectionId(searchRequest.getConnectionId());
	}

	@Reference(unbind = "-")
	protected void setElasticsearchConfigurationWrapper(
		ElasticsearchConfigurationWrapper elasticsearchConfigurationWrapper) {

		_elasticsearchConfigurationWrapper = elasticsearchConfigurationWrapper;
	}

	@Reference(unbind = "-")
	protected void setIndexNameBuilder(IndexNameBuilder indexNameBuilder) {
		_indexNameBuilder = indexNameBuilder;
	}

	protected void setIndexNames(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest,
		SearchContext searchContext) {

		baseSearchRequest.setIndexNames(
			getIndexes(searchRequest, searchContext));
	}

	protected void setLegacyPostFilter(
		BaseSearchRequest baseSearchRequest, Query query) {

		if (query != null) {
			baseSearchRequest.setPostFilter(query.getPostFilter());
		}
	}

	protected void setLegacyQuery(
		BaseSearchRequest baseSearchRequest, Query query) {

		baseSearchRequest.setQuery(query);
	}

	protected void setPipelineAggregations(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest) {

		Map<String, PipelineAggregation> map =
			searchRequest.getPipelineAggregationsMap();

		for (PipelineAggregation aggregation : map.values()) {
			baseSearchRequest.addPipelineAggregation(aggregation);
		}
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	protected void setQuery(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest) {

		baseSearchRequest.setQuery(searchRequest.getQuery());
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setSearchEngineAdapter(
		SearchEngineAdapter searchEngineAdapter) {

		_searchEngineAdapter = searchEngineAdapter;
	}

	@Reference(unbind = "-")
	protected void setSearchRequestBuilderFactory(
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	@Reference(unbind = "-")
	protected void setSearchResponseBuilderFactory(
		SearchResponseBuilderFactory searchResponseBuilderFactory) {

		_searchResponseBuilderFactory = searchResponseBuilderFactory;
	}

	private SearchRequestBuilder _getSearchRequestBuilder(
		SearchContext searchContext) {

		return _searchRequestBuilderFactory.builder(searchContext);
	}

	private SearchResponseBuilder _getSearchResponseBuilder(
		SearchContext searchContext) {

		return _searchResponseBuilderFactory.builder(searchContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchIndexSearcher.class);

	private volatile ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper;
	private IndexNameBuilder _indexNameBuilder;
	private Props _props;
	private SearchEngineAdapter _searchEngineAdapter;
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private SearchResponseBuilderFactory _searchResponseBuilderFactory;

}