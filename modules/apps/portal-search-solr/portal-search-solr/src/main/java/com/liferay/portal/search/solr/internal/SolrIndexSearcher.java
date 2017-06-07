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

package com.liferay.portal.search.solr.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexSearcher;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
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
import com.liferay.portal.kernel.search.StatsResults;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.filter.FilterTranslator;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.solr.configuration.SolrConfiguration;
import com.liferay.portal.search.solr.connection.SolrClientManager;
import com.liferay.portal.search.solr.facet.FacetProcessor;
import com.liferay.portal.search.solr.groupby.GroupByTranslator;
import com.liferay.portal.search.solr.internal.facet.CompositeFacetProcessor;
import com.liferay.portal.search.solr.internal.facet.SolrFacetFieldCollector;
import com.liferay.portal.search.solr.internal.facet.SolrFacetQueryCollector;
import com.liferay.portal.search.solr.internal.pagination.Pagination;
import com.liferay.portal.search.solr.stats.StatsTranslator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.FacetParams;

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
	configurationPid = "com.liferay.portal.search.solr.configuration.SolrConfiguration",
	immediate = true, property = {"search.engine.impl=Solr"},
	service = IndexSearcher.class
)
public class SolrIndexSearcher extends BaseIndexSearcher {

	@Override
	public String getQueryString(SearchContext searchContext, Query query) {
		return translateQuery(query, searchContext);
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			Pagination pagination = new Pagination(
				searchContext.getStart(), searchContext.getEnd());

			Hits hits = null;

			while (true) {
				hits = doSearchHits(searchContext, query, pagination);

				Document[] documents = hits.getDocs();

				if (documents.length != 0) {
					break;
				}

				Optional<Pagination> paginationOptional =
					pagination.repageToLast(hits.getLength());

				if (!paginationOptional.isPresent()) {
					break;
				}

				pagination = paginationOptional.get();
			}

			hits.setStart(stopWatch.getStartTime());

			return hits;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			if (!_logExceptionsOnly) {
				throw new SearchException(e.getMessage(), e);
			}

			return new HitsImpl();
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"Searching " + query.toString() + " took " +
						stopWatch.getTime() + " ms");
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
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			if (!_logExceptionsOnly) {
				throw new SearchException(e.getMessage(), e);
			}

			return 0;
		}
		finally {
			if (_log.isInfoEnabled()) {
				stopWatch.stop();

				_log.info(
					"Searching " + query.toString() + " took " +
						stopWatch.getTime() + " ms");
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

		_logExceptionsOnly = _solrConfiguration.logExceptionsOnly();
	}

	protected void addFacets(SolrQuery solrQuery, SearchContext searchContext) {
		Map<String, Facet> facets = searchContext.getFacets();

		for (Facet facet : facets.values()) {
			if (facet.isStatic()) {
				continue;
			}

			FacetConfiguration facetConfiguration =
				facet.getFacetConfiguration();

			_facetProcessor.processFacet(solrQuery, facet);

			String facetSort = FacetParams.FACET_SORT_COUNT;

			String order = facetConfiguration.getOrder();

			if (order.equals("OrderValueAsc")) {
				facetSort = FacetParams.FACET_SORT_INDEX;
			}

			solrQuery.add(
				"f." + facetConfiguration.getFieldName() + ".facet.sort",
				facetSort);
		}

		solrQuery.setFacetLimit(-1);
	}

	protected void addGroupBy(
		SolrQuery solrQuery, SearchContext searchContext,
		Pagination pagination) {

		GroupBy groupBy = searchContext.getGroupBy();

		if (groupBy == null) {
			return;
		}

		_groupByTranslator.translate(
			solrQuery, searchContext, pagination.getStart(),
			pagination.getEnd());
	}

	protected void addHighlightedField(
		SolrQuery solrQuery, QueryConfig queryConfig, String fieldName) {

		solrQuery.addHighlightField(fieldName);

		String localizedFieldName = DocumentImpl.getLocalizedName(
			queryConfig.getLocale(), fieldName);

		solrQuery.addHighlightField(localizedFieldName);
	}

	protected void addHighlights(SolrQuery solrQuery, QueryConfig queryConfig) {
		if (!queryConfig.isHighlightEnabled()) {
			return;
		}

		solrQuery.setHighlight(true);
		solrQuery.setHighlightFragsize(queryConfig.getHighlightFragmentSize());
		solrQuery.setHighlightSimplePost(HighlightUtil.HIGHLIGHT_TAG_CLOSE);
		solrQuery.setHighlightSimplePre(HighlightUtil.HIGHLIGHT_TAG_OPEN);
		solrQuery.setHighlightSnippets(queryConfig.getHighlightSnippetSize());

		for (String highlightFieldName : queryConfig.getHighlightFieldNames()) {
			addHighlightedField(solrQuery, queryConfig, highlightFieldName);
		}

		solrQuery.setHighlightRequireFieldMatch(
			queryConfig.isHighlightRequireFieldMatch());
	}

	protected void addPagination(
		SolrQuery solrQuery, SearchContext searchContext,
		Pagination pagination) {

		GroupBy groupBy = searchContext.getGroupBy();

		if (groupBy != null) {
			return;
		}

		Optional<Integer> fromOptional = pagination.getFrom();

		fromOptional.ifPresent(solrQuery::setStart);

		Optional<Integer> sizeOptional = pagination.getSize();

		sizeOptional.ifPresent(solrQuery::setRows);
	}

	protected void addSelectedFields(
		SolrQuery solrQuery, QueryConfig queryConfig) {

		if (queryConfig.isAllFieldsSelected()) {
			return;
		}

		Set<String> selectedFieldNames = SetUtil.fromArray(
			queryConfig.getSelectedFieldNames());

		if (!selectedFieldNames.contains(Field.UID)) {
			selectedFieldNames.add(Field.UID);
		}

		solrQuery.setFields(
			selectedFieldNames.toArray(new String[selectedFieldNames.size()]));
	}

	protected void addSnippets(
		SolrDocument solrDocument, Document document, QueryConfig queryConfig,
		Set<String> queryTerms, QueryResponse queryResponse) {

		Map<String, Map<String, List<String>>> highlights =
			queryResponse.getHighlighting();

		if (!queryConfig.isHighlightEnabled()) {
			return;
		}

		for (String highlightFieldName : queryConfig.getHighlightFieldNames()) {
			addSnippets(
				solrDocument, document, queryTerms, highlights,
				highlightFieldName, queryConfig.getLocale());
		}
	}

	protected void addSnippets(
		SolrDocument solrDocument, Document document, Set<String> queryTerms,
		Map<String, Map<String, List<String>>> highlights, String fieldName,
		Locale locale) {

		if (MapUtil.isEmpty(highlights)) {
			return;
		}

		String key = (String)solrDocument.getFieldValue(Field.UID);

		Map<String, List<String>> uidHighlights = highlights.get(key);

		String localizedFieldName = DocumentImpl.getLocalizedName(
			locale, fieldName);

		String snippetFieldName = localizedFieldName;

		List<String> snippets = uidHighlights.get(localizedFieldName);

		if (snippets == null) {
			snippets = uidHighlights.get(fieldName);

			snippetFieldName = fieldName;
		}

		String snippet = StringPool.BLANK;

		if (ListUtil.isNotEmpty(snippets)) {
			snippet = StringUtil.merge(snippets, StringPool.TRIPLE_PERIOD);

			if (Validator.isNotNull(snippet)) {
				snippet = snippet.concat(StringPool.TRIPLE_PERIOD);
			}
		}

		HighlightUtil.addSnippet(
			document, queryTerms, snippet, snippetFieldName);
	}

	protected void addSort(SolrQuery solrQuery, Sort[] sorts) {
		if (ArrayUtil.isEmpty(sorts)) {
			return;
		}

		Set<String> sortFieldNames = new HashSet<>(sorts.length);

		for (Sort sort : sorts) {
			if (sort == null) {
				continue;
			}

			String sortFieldName = DocumentImpl.getSortFieldName(sort, "score");

			if (sortFieldNames.contains(sortFieldName)) {
				continue;
			}

			sortFieldNames.add(sortFieldName);

			ORDER order = ORDER.asc;

			if (sort.isReverse() || sortFieldName.equals("score")) {
				order = ORDER.desc;
			}

			solrQuery.addSort(new SortClause(sortFieldName, order));
		}
	}

	protected void addStats(SolrQuery solrQuery, SearchContext searchContext) {
		Map<String, Stats> statsMap = searchContext.getStats();

		for (Stats stats : statsMap.values()) {
			_statsTranslator.translate(solrQuery, stats);
		}
	}

	protected QueryResponse doSearch(
			SearchContext searchContext, Query query, Pagination pagination,
			boolean count)
		throws Exception {

		QueryConfig queryConfig = query.getQueryConfig();

		SolrQuery solrQuery = new SolrQuery();

		addStats(solrQuery, searchContext);

		if (!count) {
			addFacets(solrQuery, searchContext);
			addGroupBy(solrQuery, searchContext, pagination);
			addHighlights(solrQuery, queryConfig);
			addPagination(solrQuery, searchContext, pagination);
			addSelectedFields(solrQuery, queryConfig);
			addSort(solrQuery, searchContext.getSorts());

			solrQuery.setIncludeScore(queryConfig.isScoreEnabled());
		}
		else {
			solrQuery.setRows(0);
		}

		String queryString = translateQuery(query, searchContext);

		solrQuery.setQuery(queryString);

		List<String> filterQueries = new ArrayList<>();

		if (query.getPreBooleanFilter() != null) {
			String filterQuery = _filterTranslator.translate(
				query.getPreBooleanFilter(), searchContext);

			filterQueries.add(filterQuery);
		}

		if (query.getPostFilter() != null) {
			String filterQuery = _filterTranslator.translate(
				query.getPreBooleanFilter(), searchContext);

			filterQueries.add(filterQuery);
		}

		if (!filterQueries.isEmpty()) {
			solrQuery.setFilterQueries(
				filterQueries.toArray(new String[filterQueries.size()]));
		}

		String solrQueryString = solrQuery.toString();

		searchContext.setAttribute("queryString", solrQueryString);

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + solrQueryString);
		}

		QueryResponse queryResponse = executeSearchRequest(solrQuery);

		if (_log.isInfoEnabled()) {
			_log.info(
				"The search engine processed " + solrQueryString + " in " +
					queryResponse.getElapsedTime() + " ms");
		}

		return queryResponse;
	}

	protected long doSearchCount(SearchContext searchContext, Query query)
		throws Exception {

		QueryResponse queryResponse = doSearch(
			searchContext, query, null, true);

		SolrDocumentList solrDocumentList = queryResponse.getResults();

		return solrDocumentList.getNumFound();
	}

	protected Hits doSearchHits(
			SearchContext searchContext, Query query, Pagination pagination)
		throws Exception {

		QueryResponse queryResponse = doSearch(
			searchContext, query, pagination, false);

		return processResponse(queryResponse, searchContext, query);
	}

	protected QueryResponse executeSearchRequest(SolrQuery solrQuery)
		throws Exception {

		SolrClient solrClient = _solrClientManager.getSolrClient();

		return solrClient.query(solrQuery, METHOD.POST);
	}

	protected Hits processResponse(
		QueryResponse queryResponse, SearchContext searchContext, Query query) {

		Hits hits = new HitsImpl();

		updateFacetCollectors(queryResponse, searchContext);
		updateGroupedHits(queryResponse, searchContext, query, hits);
		updateStatsResults(searchContext, queryResponse, hits);

		hits.setQuery(query);
		hits.setSearchTime(queryResponse.getQTime());

		processSearchHits(
			queryResponse, queryResponse.getResults(), query, hits);

		return hits;
	}

	protected void processSearchHits(
		QueryResponse queryResponse, SolrDocumentList solrDocumentList,
		Query query, Hits hits) {

		List<Document> documents = new ArrayList<>();
		Set<String> queryTerms = new HashSet<>();
		List<Float> scores = new ArrayList<>();

		processSolrDocumentList(
			queryResponse, solrDocumentList, query, hits, documents, queryTerms,
			scores);

		hits.setDocs(documents.toArray(new Document[documents.size()]));
		hits.setQueryTerms(queryTerms.toArray(new String[queryTerms.size()]));
		hits.setScores(ArrayUtil.toFloatArray(scores));
	}

	protected Document processSolrDocument(
		SolrDocument solrDocument, QueryConfig queryConfig) {

		Document document = new DocumentImpl();

		Collection<String> fieldNames = solrDocument.getFieldNames();

		for (String fieldName : fieldNames) {
			if (fieldName.equals(_VERSION_FIELD)) {
				continue;
			}

			Collection<Object> fieldValues = solrDocument.getFieldValues(
				fieldName);

			Field field = new Field(
				fieldName,
				ArrayUtil.toStringArray(
					fieldValues.toArray(new Object[fieldValues.size()])));

			document.add(field);
		}

		populateUID(document, queryConfig);

		return document;
	}

	protected void processSolrDocumentList(
		QueryResponse queryResponse, SolrDocumentList solrDocumentList,
		Query query, Hits hits, List<Document> documents,
		Set<String> queryTerms, List<Float> scores) {

		if (solrDocumentList == null) {
			return;
		}

		hits.setLength((int)solrDocumentList.getNumFound());

		for (SolrDocument solrDocument : solrDocumentList) {
			QueryConfig queryConfig = query.getQueryConfig();

			Document document = processSolrDocument(solrDocument, queryConfig);

			documents.add(document);

			addSnippets(
				solrDocument, document, queryConfig, queryTerms, queryResponse);

			float score = GetterUtil.getFloat(
				String.valueOf(solrDocument.getFieldValue("score")));

			scores.add(score);
		}
	}

	@Reference(service = CompositeFacetProcessor.class, unbind = "-")
	protected void setFacetProcessor(FacetProcessor<SolrQuery> facetProcessor) {
		_facetProcessor = facetProcessor;
	}

	@Reference(target = "(search.engine.impl=Solr)", unbind = "-")
	protected void setFilterTranslator(
		FilterTranslator<String> filterTranslator) {

		_filterTranslator = filterTranslator;
	}

	@Reference(unbind = "-")
	protected void setGroupByTranslator(GroupByTranslator groupByTranslator) {
		_groupByTranslator = groupByTranslator;
	}

	@Reference(target = "(search.engine.impl=Solr)", unbind = "-")
	protected void setQueryTranslator(QueryTranslator<String> queryTranslator) {
		_queryTranslator = queryTranslator;
	}

	@Reference(unbind = "-")
	protected void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	@Reference(unbind = "-")
	protected void setStatsTranslator(StatsTranslator statsTranslator) {
		_statsTranslator = statsTranslator;
	}

	protected String translateQuery(Query query, SearchContext searchContext) {
		return _queryTranslator.translate(query, searchContext);
	}

	protected void updateFacetCollectors(
		QueryResponse queryResponse, SearchContext searchContext) {

		Map<String, Facet> facetsMap = searchContext.getFacets();

		List<FacetField> facetFields = queryResponse.getFacetFields();

		if (ListUtil.isEmpty(facetFields)) {
			return;
		}

		for (FacetField facetField : facetFields) {
			Facet facet = facetsMap.get(facetField.getName());

			FacetCollector facetCollector = null;

			if (facet instanceof RangeFacet) {
				facetCollector = new SolrFacetQueryCollector(
					facetField.getName(), queryResponse.getFacetQuery());
			}
			else {
				facetCollector = new SolrFacetFieldCollector(
					facetField.getName(), facetField);
			}

			facet.setFacetCollector(facetCollector);
		}
	}

	protected void updateGroupedHits(
		QueryResponse queryResponse, SearchContext searchContext, Query query,
		Hits hits) {

		GroupBy groupBy = searchContext.getGroupBy();

		if (groupBy == null) {
			return;
		}

		GroupResponse groupResponse = queryResponse.getGroupResponse();

		List<GroupCommand> groupCommands = groupResponse.getValues();

		for (GroupCommand groupCommand : groupCommands) {
			List<Group> groups = groupCommand.getValues();

			for (Group group : groups) {
				Hits groupedHits = new HitsImpl();

				processSearchHits(
					queryResponse, group.getResult(), query, groupedHits);

				hits.addGroupedHits(group.getGroupValue(), groupedHits);
			}
		}
	}

	protected void updateStatsResults(
		SearchContext searchContext, QueryResponse queryResponse, Hits hits) {

		Map<String, Stats> statsMap = searchContext.getStats();

		if (statsMap.isEmpty()) {
			return;
		}

		Map<String, FieldStatsInfo> fieldsStatsInfo =
			queryResponse.getFieldStatsInfo();

		if (MapUtil.isEmpty(fieldsStatsInfo)) {
			return;
		}

		for (Stats stats : statsMap.values()) {
			if (!stats.isEnabled()) {
				continue;
			}

			StatsResults statsResults = _statsTranslator.translate(
				fieldsStatsInfo.get(stats.getField()), stats);

			hits.addStatsResults(statsResults);
		}
	}

	private static final String _VERSION_FIELD = "_version_";

	private static final Log _log = LogFactoryUtil.getLog(
		SolrIndexSearcher.class);

	private FacetProcessor<SolrQuery> _facetProcessor;
	private FilterTranslator<String> _filterTranslator;
	private GroupByTranslator _groupByTranslator;
	private boolean _logExceptionsOnly;
	private QueryTranslator<String> _queryTranslator;
	private SolrClientManager _solrClientManager;
	private volatile SolrConfiguration _solrConfiguration;
	private StatsTranslator _statsTranslator;

}