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

package com.liferay.portal.search.solr7.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexSearcher;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.BaseSearchRequest;
import com.liferay.portal.search.engine.adapter.search.BaseSearchResponse;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchResponseBuilder;
import com.liferay.portal.search.solr7.configuration.SolrConfiguration;
import com.liferay.portal.search.solr7.internal.search.response.HitsImpl;

import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Zsolt Berentey
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.search.solr7.configuration.SolrConfiguration",
	immediate = true, property = "search.engine.impl=Solr",
	service = IndexSearcher.class
)
public class SolrIndexSearcher extends BaseIndexSearcher {

	@Override
	public String getQueryString(SearchContext searchContext, Query query) {
		return _searchEngineAdapter.getQueryString(query);
	}

	@Override
	public Hits search(SearchContext searchContext, Query query) {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			int start = searchContext.getStart();
			int end = searchContext.getEnd();

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

			Hits hits = null;

			while (true) {
				SearchSearchRequest searchSearchRequest =
					createSearchSearchRequest(searchContext, query, start, end);

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

				populateResponse(
					searchSearchResponse,
					_getSearchResponseBuilder(searchContext));

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
		catch (Exception e) {
			if (_logExceptionsOnly) {
				_log.error(e, e);
			}
			else {
				if (e instanceof RuntimeException) {
					throw (RuntimeException)e;
				}

				throw new SystemException(e.getMessage(), e);
			}

			return new HitsImpl();
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
		catch (Exception e) {
			if (_logExceptionsOnly) {
				_log.error(e, e);
			}
			else {
				if (e instanceof RuntimeException) {
					throw (RuntimeException)e;
				}

				throw new SystemException(e.getMessage(), e);
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
	@Reference(target = "(search.engine.impl=Solr)", unbind = "-")
	public void setQuerySuggester(QuerySuggester querySuggester) {
		super.setQuerySuggester(querySuggester);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_solrConfiguration = ConfigurableUtil.createConfigurable(
			SolrConfiguration.class, properties);

		_defaultCollection = _solrConfiguration.defaultCollection();
		_logExceptionsOnly = _solrConfiguration.logExceptionsOnly();
	}

	protected CountSearchRequest createCountSearchRequest(
		SearchContext searchContext, Query query) {

		CountSearchRequest countSearchRequest = new CountSearchRequest();

		SearchRequest searchRequest = _getSearchRequest(searchContext);

		populateBaseSearchRequest(
			countSearchRequest, searchRequest, searchContext, query);

		return countSearchRequest;
	}

	protected SearchSearchRequest createSearchSearchRequest(
		SearchContext searchContext, Query query, int start, int end) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		SearchRequest searchRequest = _getSearchRequest(searchContext);

		populateBaseSearchRequest(
			searchSearchRequest, searchRequest, searchContext, query);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		searchSearchRequest.setAllFieldsSelected(
			queryConfig.isAllFieldsSelected());
		searchSearchRequest.setAlternateUidFieldName(
			queryConfig.getAlternateUidFieldName());
		searchSearchRequest.setGroupBy(searchContext.getGroupBy());
		searchSearchRequest.setGroupByRequests(
			searchRequest.getGroupByRequests());
		searchSearchRequest.setHighlightEnabled(
			queryConfig.isHighlightEnabled());
		searchSearchRequest.setHighlightFieldNames(
			queryConfig.getHighlightFieldNames());
		searchSearchRequest.setHighlightFragmentSize(
			queryConfig.getHighlightFragmentSize());
		searchSearchRequest.setHighlightRequireFieldMatch(
			queryConfig.isHighlightRequireFieldMatch());
		searchSearchRequest.setHighlightSnippetSize(
			queryConfig.getHighlightSnippetSize());
		searchSearchRequest.setIncludeResponseString(
			searchRequest.isIncludeResponseString());
		searchSearchRequest.setLocale(queryConfig.getLocale());

		boolean luceneSyntax = GetterUtil.getBoolean(
			searchContext.getAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_LUCENE_SYNTAX));

		searchSearchRequest.setLuceneSyntax(luceneSyntax);

		searchSearchRequest.setPostFilter(query.getPostFilter());
		searchSearchRequest.setScoreEnabled(queryConfig.isScoreEnabled());
		searchSearchRequest.setSelectedFieldNames(
			queryConfig.getSelectedFieldNames());
		searchSearchRequest.setSize(end - start);
		searchSearchRequest.setSorts(searchContext.getSorts());
		searchSearchRequest.setSorts(searchRequest.getSorts());
		searchSearchRequest.setStart(start);
		searchSearchRequest.setStats(searchContext.getStats());

		return searchSearchRequest;
	}

	protected void populateBaseSearchRequest(
		BaseSearchRequest baseSearchRequest, SearchRequest searchRequest,
		SearchContext searchContext, Query query) {

		baseSearchRequest.putAllFacets(searchContext.getFacets());

		boolean basicFacetSelection = GetterUtil.getBoolean(
			searchContext.getAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_BASIC_FACET_SELECTION));

		baseSearchRequest.setBasicFacetSelection(basicFacetSelection);

		baseSearchRequest.setExplain(searchRequest.isExplain());
		baseSearchRequest.setIncludeResponseString(
			searchRequest.isIncludeResponseString());
		baseSearchRequest.setIndexNames(new String[] {_defaultCollection});

		long companyId = searchContext.getCompanyId();

		if (companyId >= 0) {
			BooleanFilter preBooleanFilter = query.getPreBooleanFilter();

			if (preBooleanFilter == null) {
				preBooleanFilter = new BooleanFilter();
			}

			preBooleanFilter.addRequiredTerm(
				Field.COMPANY_ID, searchContext.getCompanyId());

			query.setPreBooleanFilter(preBooleanFilter);
		}

		baseSearchRequest.setQuery(query);
		baseSearchRequest.setStatsRequests(searchRequest.getStatsRequests());
	}

	protected void populateResponse(
		BaseSearchResponse baseSearchResponse,
		SearchResponseBuilder searchResponseBuilder) {

		searchResponseBuilder.requestString(
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

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	@Reference(target = "(search.engine.impl=Solr)", unbind = "-")
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

	private SearchRequest _getSearchRequest(SearchContext searchContext) {
		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(searchContext);

		return searchRequestBuilder.build();
	}

	private SearchResponseBuilder _getSearchResponseBuilder(
		SearchContext searchContext) {

		return _searchResponseBuilderFactory.builder(searchContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SolrIndexSearcher.class);

	private String _defaultCollection;
	private boolean _logExceptionsOnly;
	private Props _props;
	private SearchEngineAdapter _searchEngineAdapter;
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;
	private SearchResponseBuilderFactory _searchResponseBuilderFactory;
	private volatile SolrConfiguration _solrConfiguration;

}