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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexSearcher;
import com.liferay.portal.kernel.search.BooleanClause;
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
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.FilterTranslator;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.solr7.configuration.SolrConfiguration;
import com.liferay.portal.search.solr7.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr7.internal.facet.CompositeFacetProcessor;
import com.liferay.portal.search.solr7.internal.facet.FacetProcessor;
import com.liferay.portal.search.solr7.internal.facet.FacetUtil;
import com.liferay.portal.search.solr7.internal.facet.SolrFacetFieldCollector;
import com.liferay.portal.search.solr7.internal.facet.SolrFacetQueryCollector;
import com.liferay.portal.search.solr7.internal.groupby.GroupByTranslator;
import com.liferay.portal.search.solr7.internal.stats.StatsTranslator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.time.StopWatch;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;

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
		return translateQuery(query, searchContext);
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

		_logExceptionsOnly = _solrConfiguration.logExceptionsOnly();
	}

	protected void addFacets(SolrQuery solrQuery, SearchContext searchContext) {
		Map<String, Facet> facets = searchContext.getFacets();

		Map<String, JSONObject> jsonObjects = new LinkedHashMap<>();

		List<String> postFilterQueries = new ArrayList<>();

		FacetProcessorContext facetProcessorContext = getFacetProcessorContext(
			facets, searchContext);

		for (Facet facet : facets.values()) {
			if (facet.isStatic()) {
				continue;
			}

			String tag = FacetUtil.getAggregationName(facet);

			addFilterQuery(postFilterQueries, facet, tag, searchContext);

			Map<String, JSONObject> facetParameters = getFacetParameters(facet);

			excludeTags(
				facetParameters,
				getExcludeTagsString(tag, facetProcessorContext));

			jsonObjects.putAll(facetParameters);
		}

		if (!jsonObjects.isEmpty()) {
			solrQuery.add("json.facet", getFacetString(jsonObjects));
		}

		if (!postFilterQueries.isEmpty()) {
			solrQuery.setFilterQueries(
				ArrayUtil.toStringArray(postFilterQueries));
		}
	}

	protected void addFilterQuery(
		List<String> filterQueries, Facet facet, String tag,
		SearchContext searchContext) {

		BooleanClause<Filter> booleanClause =
			facet.getFacetFilterBooleanClause();

		if (booleanClause == null) {
			return;
		}

		String filterString = translate(booleanClause, searchContext);

		filterQueries.add(
			StringBundler.concat(
				StringPool.OPEN_CURLY_BRACE, "!tag", StringPool.EQUAL, tag,
				StringPool.CLOSE_CURLY_BRACE, filterString));
	}

	protected void addGroupBy(
		SolrQuery solrQuery, SearchContext searchContext, int start, int end) {

		GroupBy groupBy = searchContext.getGroupBy();

		if (groupBy == null) {
			return;
		}

		_groupByTranslator.translate(solrQuery, searchContext, start, end);
	}

	protected void addHighlightedField(
		SolrQuery solrQuery, QueryConfig queryConfig, String fieldName) {

		solrQuery.addHighlightField(fieldName);

		String localizedFieldName = Field.getLocalizedName(
			queryConfig.getLocale(), fieldName);

		solrQuery.addHighlightField(localizedFieldName);
	}

	protected void addHighlights(
		SolrQuery solrQuery, SearchContext searchContext,
		QueryConfig queryConfig) {

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

		boolean luceneSyntax = GetterUtil.getBoolean(
			searchContext.getAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_LUCENE_SYNTAX));

		if (!luceneSyntax) {
			solrQuery.setHighlightRequireFieldMatch(
				queryConfig.isHighlightRequireFieldMatch());
		}
	}

	protected void addPagination(
		SolrQuery solrQuery, SearchContext searchContext, int start, int end) {

		GroupBy groupBy = searchContext.getGroupBy();

		if (groupBy != null) {
			return;
		}

		solrQuery.setRows(end - start);
		solrQuery.setStart(start);
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
		Document document, Map<String, List<String>> highlights,
		String fieldName, Locale locale) {

		String snippetFieldName = Field.getLocalizedName(locale, fieldName);

		List<String> list = highlights.get(snippetFieldName);

		if (list == null) {
			list = highlights.get(fieldName);

			snippetFieldName = fieldName;
		}

		if (ListUtil.isEmpty(list)) {
			return;
		}

		document.addText(
			Field.SNIPPET.concat(StringPool.UNDERLINE).concat(snippetFieldName),
			StringUtil.merge(list, StringPool.TRIPLE_PERIOD));
	}

	protected void addSnippets(
		SolrDocument solrDocument, Document document, QueryConfig queryConfig,
		QueryResponse queryResponse) {

		if (!queryConfig.isHighlightEnabled()) {
			return;
		}

		Map<String, Map<String, List<String>>> highlights =
			queryResponse.getHighlighting();

		if (MapUtil.isEmpty(highlights)) {
			return;
		}

		String uid = (String)solrDocument.getFieldValue(Field.UID);

		for (String highlightFieldName : queryConfig.getHighlightFieldNames()) {
			addSnippets(
				document, highlights.get(uid), highlightFieldName,
				queryConfig.getLocale());
		}
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

			String sortFieldName = getSortFieldName(sort, "score");

			if (sortFieldNames.contains(sortFieldName)) {
				continue;
			}

			sortFieldNames.add(sortFieldName);

			SolrQuery.ORDER order = SolrQuery.ORDER.asc;

			if (sort.isReverse() || sortFieldName.equals("score")) {
				order = SolrQuery.ORDER.desc;
			}

			solrQuery.addSort(new SolrQuery.SortClause(sortFieldName, order));
		}
	}

	protected void addStats(SolrQuery solrQuery, SearchContext searchContext) {
		Map<String, Stats> statsMap = searchContext.getStats();

		for (Stats stats : statsMap.values()) {
			_statsTranslator.translate(solrQuery, stats);
		}
	}

	protected QueryResponse doSearch(
			SearchContext searchContext, Query query, int start, int end,
			boolean count)
		throws Exception {

		QueryConfig queryConfig = searchContext.getQueryConfig();

		SolrQuery solrQuery = new SolrQuery();

		addStats(solrQuery, searchContext);

		if (!count) {
			addFacets(solrQuery, searchContext);
			addGroupBy(solrQuery, searchContext, start, end);
			addHighlights(solrQuery, searchContext, queryConfig);
			addPagination(solrQuery, searchContext, start, end);
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

		_add(filterQueries, query.getPreBooleanFilter(), searchContext);

		_add(filterQueries, query.getPostFilter(), searchContext);

		_addAll(filterQueries, solrQuery.getFilterQueries());

		if (!filterQueries.isEmpty()) {
			solrQuery.setFilterQueries(
				filterQueries.toArray(new String[filterQueries.size()]));

			if (Validator.isBlank(solrQuery.getQuery())) {
				solrQuery.setQuery("*:*");
			}
		}

		String solrQueryString = solrQuery.toString();

		searchContext.setAttribute("queryString", solrQueryString);

		if (_log.isDebugEnabled()) {
			_log.debug("Search query " + solrQueryString);
		}

		QueryResponse queryResponse = executeSearchRequest(solrQuery);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"The search engine processed ", solrQueryString, " in ",
					queryResponse.getElapsedTime(), " ms"));
		}

		return queryResponse;
	}

	protected long doSearchCount(SearchContext searchContext, Query query)
		throws Exception {

		QueryResponse queryResponse = doSearch(
			searchContext, query, searchContext.getStart(),
			searchContext.getEnd(), true);

		SolrDocumentList solrDocumentList = queryResponse.getResults();

		return solrDocumentList.getNumFound();
	}

	protected Hits doSearchHits(
			SearchContext searchContext, Query query, int start, int end)
		throws Exception {

		QueryResponse queryResponse = doSearch(
			searchContext, query, start, end, false);

		Hits hits = processResponse(queryResponse, searchContext, query);

		return hits;
	}

	protected void excludeTags(
		Map<String, JSONObject> map, String excludeTagsString) {

		for (JSONObject jsonObject : map.values()) {
			jsonObject.put("excludeTags", excludeTagsString);
		}
	}

	protected QueryResponse executeSearchRequest(SolrQuery solrQuery)
		throws Exception {

		SolrClient solrClient = _solrClientManager.getSolrClient();

		return solrClient.query(solrQuery, SolrRequest.METHOD.POST);
	}

	protected String getExcludeTagsString(
		String tag, FacetProcessorContext facetProcessorContext) {

		Optional<String> optional =
			facetProcessorContext.getExcludeTagsStringOptional();

		return optional.orElse(tag);
	}

	protected FacetCollector getFacetCollector(
		Facet facet, NamedList namedList) {

		if (facet instanceof RangeFacet) {
			return new SolrFacetQueryCollector(facet, namedList);
		}

		return new SolrFacetFieldCollector(facet, namedList);
	}

	protected Map<String, JSONObject> getFacetParameters(Facet facet) {
		return _facetProcessor.processFacet(facet);
	}

	protected FacetProcessorContext getFacetProcessorContext(
		Map<String, Facet> facets, SearchContext searchContext) {

		boolean basicFacetSelection = GetterUtil.getBoolean(
			searchContext.getAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_BASIC_FACET_SELECTION));

		return AggregationFilteringFacetProcessorContext.newInstance(
			facets, basicFacetSelection);
	}

	protected String getFacetString(Map<String, JSONObject> jsonObjects) {
		Set<Map.Entry<String, JSONObject>> entrySet = jsonObjects.entrySet();

		Stream<Map.Entry<String, JSONObject>> stream = entrySet.stream();

		String jsonString = stream.map(
			entry -> StringBundler.concat(
				StringPool.QUOTE, entry.getKey(), StringPool.QUOTE,
				StringPool.COLON, entry.getValue())
		).collect(
			Collectors.joining(
				StringPool.COMMA, StringPool.OPEN_CURLY_BRACE,
				StringPool.CLOSE_CURLY_BRACE)
		);

		return jsonString;
	}

	protected String getSortFieldName(Sort sort, String scoreFieldName) {
		String sortFieldName = sort.getFieldName();

		if (Objects.equals(sortFieldName, Field.PRIORITY)) {
			return sortFieldName;
		}

		return Field.getSortFieldName(sort, scoreFieldName);
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
		List<Float> scores = new ArrayList<>();

		processSolrDocumentList(
			queryResponse, solrDocumentList, query, hits, documents, scores);

		hits.setDocs(documents.toArray(new Document[documents.size()]));
		hits.setQueryTerms(new String[0]);
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
		Query query, Hits hits, List<Document> documents, List<Float> scores) {

		if (solrDocumentList == null) {
			Collections.addAll(documents, hits.getDocs());

			return;
		}

		hits.setLength((int)solrDocumentList.getNumFound());

		for (SolrDocument solrDocument : solrDocumentList) {
			QueryConfig queryConfig = query.getQueryConfig();

			Document document = processSolrDocument(solrDocument, queryConfig);

			documents.add(document);

			addSnippets(solrDocument, document, queryConfig, queryResponse);

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

	protected String translate(
		BooleanClause<Filter> booleanClause, SearchContext searchContext) {

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(
			booleanClause.getClause(), booleanClause.getBooleanClauseOccur());

		return _filterTranslator.translate(booleanFilter, searchContext);
	}

	protected String translateQuery(Query query, SearchContext searchContext) {
		return _queryTranslator.translate(query, searchContext);
	}

	protected void updateFacetCollectors(
		QueryResponse queryResponse, SearchContext searchContext) {

		NamedList namedList1 = queryResponse.getResponse();

		NamedList namedList2 = (NamedList)namedList1.get("facets");

		if (namedList2 == null) {
			return;
		}

		Map<String, Facet> facetsMap = searchContext.getFacets();

		for (Facet facet : facetsMap.values()) {
			if (!facet.isStatic()) {
				facet.setFacetCollector(getFacetCollector(facet, namedList2));
			}
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

				Document[] docs = groupedHits.getDocs();

				hits.setDocs(docs);
				hits.setLength(docs.length);
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

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected Props props;

	private void _add(
		Collection<String> filterQueries, Filter filter,
		SearchContext searchContext) {

		if (filter != null) {
			filterQueries.add(
				_filterTranslator.translate(filter, searchContext));
		}
	}

	private void _addAll(
		List<String> filterQueries, String[] facetPostFilterQueries) {

		if (!ArrayUtil.isEmpty(facetPostFilterQueries)) {
			Collections.addAll(filterQueries, facetPostFilterQueries);
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