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

package com.liferay.portal.search.elasticsearch6.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexSearcher;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.search.filter.FilterTranslator;
import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.elasticsearch6.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch6.constants.ElasticsearchSearchContextAttributes;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch6.internal.facet.FacetTranslator;
import com.liferay.portal.search.elasticsearch6.internal.groupby.GroupByTranslator;
import com.liferay.portal.search.elasticsearch6.internal.highlight.HighlighterTranslator;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexNameBuilder;
import com.liferay.portal.search.elasticsearch6.internal.search.response.SearchResponseTranslator;
import com.liferay.portal.search.elasticsearch6.internal.sort.SortTranslator;
import com.liferay.portal.search.elasticsearch6.internal.stats.StatsTranslator;
import com.liferay.portal.search.elasticsearch6.internal.util.DocumentTypes;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Milen Dyankov
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch6.configuration.ElasticsearchConfiguration",
	immediate = true, property = "search.engine.impl=Elasticsearch",
	service = IndexSearcher.class
)
public class ElasticsearchIndexSearcher extends BaseIndexSearcher {

	@Override
	public String getQueryString(SearchContext searchContext, Query query) {
		QueryBuilder queryBuilder = queryTranslator.translate(
			query, searchContext);

		return queryBuilder.toString();
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

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
					props.get(PropsKeys.INDEX_SEARCH_LIMIT));
			}
			else if (end < 0) {
				throw new IllegalArgumentException("Invalid end " + end);
			}

			Hits hits = null;

			while (true) {
				hits = doSearchHits(searchContext, query, start, end);

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
			if (!handle(e)) {
				if (_logExceptionsOnly) {
					if (_log.isWarnEnabled()) {
						_log.warn(e, e);
					}
				}
				else {
					throw new SearchException(e.getMessage(), e);
				}
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
	public long searchCount(SearchContext searchContext, Query query)
		throws SearchException {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			return doSearchCount(searchContext, query);
		}
		catch (Exception e) {
			if (!handle(e)) {
				if (_logExceptionsOnly) {
					if (_log.isWarnEnabled()) {
						_log.warn(e, e);
					}
				}
				else {
					throw new SearchException(e.getMessage(), e);
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

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		_logExceptionsOnly = _elasticsearchConfiguration.logExceptionsOnly();
	}

	protected void addGroupBy(
		SearchRequestBuilder searchRequestBuilder, GroupBy groupBy,
		Sort[] sorts, String[] selectedFieldNames, String[] highlightFieldNames,
		boolean highlightEnabled, boolean highlightRequireFieldMatch,
		Locale locale, int highlightFragmentSize, int highlightSnippetSize,
		int start, int end) {

		if (groupBy == null) {
			return;
		}

		groupByTranslator.translate(
			searchRequestBuilder, groupBy, sorts, locale, selectedFieldNames,
			highlightFieldNames, highlightEnabled, highlightRequireFieldMatch,
			highlightFragmentSize, highlightSnippetSize, start, end);
	}

	protected void addHighlights(
		SearchRequestBuilder searchRequestBuilder, SearchContext searchContext,
		Locale locale, String[] highlightFieldNames,
		boolean highlightRequireFieldMatch, int highlightFragmentSize,
		int highlightSnippetSize) {

		boolean luceneSyntax = GetterUtil.getBoolean(
			searchContext.getAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_LUCENE_SYNTAX));

		highlighterTranslator.translate(
			searchRequestBuilder, locale, highlightFieldNames,
			highlightRequireFieldMatch, highlightFragmentSize,
			highlightSnippetSize, luceneSyntax);
	}

	protected void addPagination(
		SearchRequestBuilder searchRequestBuilder, int start, int end) {

		searchRequestBuilder.setFrom(start);
		searchRequestBuilder.setSize(end - start);
	}

	protected void addPreference(
		SearchRequestBuilder searchRequestBuilder,
		SearchContext searchContext) {

		String preference = (String)searchContext.getAttribute(
			ElasticsearchSearchContextAttributes.
				ATTRIBUTE_KEY_SEARCH_REQUEST_PREFERENCE);

		if (!Validator.isBlank(preference)) {
			searchRequestBuilder.setPreference(preference);
		}
	}

	protected void addSelectedFields(
		SearchRequestBuilder searchRequestBuilder, QueryConfig queryConfig) {

		String[] selectedFieldNames = queryConfig.getSelectedFieldNames();

		if (ArrayUtil.isEmpty(selectedFieldNames)) {
			searchRequestBuilder.addStoredField(StringPool.STAR);
		}
		else {
			searchRequestBuilder.storedFields(selectedFieldNames);
		}
	}

	protected void addStats(
		SearchRequestBuilder searchRequestBuilder,
		SearchContext searchContext) {

		Map<String, Stats> statsMap = searchContext.getStats();

		for (Stats stats : statsMap.values()) {
			statsTranslator.translate(searchRequestBuilder, stats);
		}
	}

	protected SearchResponse doSearch(
			SearchContext searchContext, Query query, int start, int end,
			boolean count)
		throws Exception {

		Client client = elasticsearchConnectionManager.getClient();

		QueryConfig queryConfig = searchContext.getQueryConfig();

		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(
			getSelectedIndexNames(queryConfig, searchContext));

		searchRequestBuilder.setTypes(getSelectedTypes(queryConfig));

		addStats(searchRequestBuilder, searchContext);

		boolean basicFacetSelection = GetterUtil.getBoolean(
			searchContext.getAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_BASIC_FACET_SELECTION));

		facetTranslator.translate(
			searchRequestBuilder, query, searchContext.getFacets(),
			basicFacetSelection);

		if (!count) {
			addGroupBy(
				searchRequestBuilder, searchContext.getGroupBy(),
				searchContext.getSorts(), queryConfig.getSelectedFieldNames(),
				queryConfig.getHighlightFieldNames(),
				queryConfig.isHighlightEnabled(),
				queryConfig.isHighlightRequireFieldMatch(),
				queryConfig.getLocale(), queryConfig.getHighlightFragmentSize(),
				queryConfig.getHighlightSnippetSize(), start, end);

			if (queryConfig.isHighlightEnabled()) {
				addHighlights(
					searchRequestBuilder, searchContext,
					queryConfig.getLocale(),
					queryConfig.getHighlightFieldNames(),
					queryConfig.isHighlightRequireFieldMatch(),
					queryConfig.getHighlightFragmentSize(),
					queryConfig.getHighlightSnippetSize());
			}

			addPagination(searchRequestBuilder, start, end);
			addPreference(searchRequestBuilder, searchContext);
			addSelectedFields(searchRequestBuilder, queryConfig);

			sortTranslator.translate(
				searchRequestBuilder, searchContext.getSorts());

			searchRequestBuilder.setTrackScores(queryConfig.isScoreEnabled());
		}
		else {
			searchRequestBuilder.setSize(0);
		}

		QueryBuilder queryBuilder = queryTranslator.translate(
			query, searchContext);

		if (query.getPreBooleanFilter() != null) {
			QueryBuilder preFilterQueryBuilder = filterTranslator.translate(
				query.getPreBooleanFilter(), searchContext);

			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

			boolQueryBuilder.filter(preFilterQueryBuilder);
			boolQueryBuilder.must(queryBuilder);

			queryBuilder = boolQueryBuilder;
		}

		searchRequestBuilder.setQuery(queryBuilder);

		String searchRequestBuilderString = searchRequestBuilder.toString();

		searchContext.setAttribute("queryString", searchRequestBuilderString);

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + searchRequestBuilderString);
		}

		SearchResponse searchResponse = searchRequestBuilder.get();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"The search engine processed ", searchRequestBuilderString,
					" in ", searchResponse.getTook()));
		}

		return searchResponse;
	}

	protected long doSearchCount(SearchContext searchContext, Query query)
		throws Exception {

		SearchResponse searchResponse = doSearch(
			searchContext, query, searchContext.getStart(),
			searchContext.getEnd(), true);

		SearchHits searchHits = searchResponse.getHits();

		return searchHits.getTotalHits();
	}

	protected Hits doSearchHits(
			SearchContext searchContext, Query query, int start, int end)
		throws Exception {

		SearchResponse searchResponse = doSearch(
			searchContext, query, start, end, false);

		Hits hits = processResponse(
			searchResponse, searchContext, query.getQueryConfig());

		hits.setQuery(query);

		return hits;
	}

	protected String[] getSelectedIndexNames(
		QueryConfig queryConfig, SearchContext searchContext) {

		String[] selectedIndexNames = queryConfig.getSelectedIndexNames();

		if (ArrayUtil.isNotEmpty(selectedIndexNames)) {
			return selectedIndexNames;
		}

		String indexName = indexNameBuilder.getIndexName(
			searchContext.getCompanyId());

		return new String[] {indexName};
	}

	protected String[] getSelectedTypes(QueryConfig queryConfig) {
		String[] selectedTypes = queryConfig.getSelectedTypes();

		if (ArrayUtil.isNotEmpty(selectedTypes)) {
			return selectedTypes;
		}

		return new String[] {DocumentTypes.LIFERAY};
	}

	protected boolean handle(Exception e) {
		Throwable throwable = e.getCause();

		if (throwable == null) {
			return false;
		}

		String message = throwable.getMessage();

		if (message == null) {
			return false;
		}

		if (message.contains(
				"Fielddata is disabled on text fields by default.")) {

			_log.error("Unable to aggregate facet on a nonkeyword field", e);

			return true;
		}

		return false;
	}

	protected Hits processResponse(
		SearchResponse searchResponse, SearchContext searchContext,
		QueryConfig queryConfig) {

		return searchResponseTranslator.translate(
			searchResponse, searchContext.getFacets(),
			searchContext.getGroupBy(), searchContext.getStats(),
			queryConfig.getAlternateUidFieldName(),
			queryConfig.getHighlightFieldNames(), queryConfig.getLocale());
	}

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	@Reference
	protected FacetTranslator facetTranslator;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	protected FilterTranslator<QueryBuilder> filterTranslator;

	@Reference
	protected GroupByTranslator groupByTranslator;

	@Reference
	protected HighlighterTranslator highlighterTranslator;

	@Reference
	protected IndexNameBuilder indexNameBuilder;

	@Reference
	protected Props props;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	protected QueryTranslator<QueryBuilder> queryTranslator;

	@Reference
	protected SearchResponseTranslator searchResponseTranslator;

	@Reference
	protected SortTranslator sortTranslator;

	@Reference
	protected StatsTranslator statsTranslator;

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchIndexSearcher.class);

	private volatile ElasticsearchConfiguration _elasticsearchConfiguration;
	private boolean _logExceptionsOnly;

}