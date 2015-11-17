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

package com.liferay.portal.search.elasticsearch.internal;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexSearcher;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GeoDistanceSort;
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
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.filter.FilterTranslator;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch.facet.FacetProcessor;
import com.liferay.portal.search.elasticsearch.groupby.GroupByTranslator;
import com.liferay.portal.search.elasticsearch.internal.facet.CompositeFacetProcessor;
import com.liferay.portal.search.elasticsearch.internal.facet.ElasticsearchFacetFieldCollector;
import com.liferay.portal.search.elasticsearch.internal.util.DocumentTypes;
import com.liferay.portal.search.elasticsearch.stats.StatsTranslator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Milen Dyankov
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	immediate = true, property = {"search.engine.impl=Elasticsearch"},
	service = IndexSearcher.class
)
public class ElasticsearchIndexSearcher extends BaseIndexSearcher {

	@Override
	public String getQueryString(SearchContext searchContext, Query query) {
		QueryBuilder queryBuilder = _queryTranslator.translate(
			query, searchContext);

		return queryBuilder.toString();
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			int total = (int)searchCount(searchContext, query);

			int start = searchContext.getStart();
			int end = searchContext.getEnd();

			if ((end == QueryUtil.ALL_POS) && (start == QueryUtil.ALL_POS)) {
				start = 0;
				end = total;
			}

			int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
				start, end, total);

			start = startAndEnd[0];
			end = startAndEnd[1];

			Hits hits = doSearchHits(searchContext, query, start, end);

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
	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	public void setQuerySuggester(QuerySuggester querySuggester) {
		super.setQuerySuggester(querySuggester);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		_logExceptionsOnly = _elasticsearchConfiguration.logExceptionsOnly();
	}

	protected void addFacets(
		SearchRequestBuilder searchRequestBuilder,
		SearchContext searchContext) {

		Map<String, Facet> facetsMap = searchContext.getFacets();

		for (Facet facet : facetsMap.values()) {
			if (facet.isStatic()) {
				continue;
			}

			_facetProcessor.processFacet(searchRequestBuilder, facet);
		}
	}

	protected void addGroupBy(
		SearchRequestBuilder searchRequestBuilder, SearchContext searchContext,
		int start, int end) {

		GroupBy groupBy = searchContext.getGroupBy();

		if (groupBy == null) {
			return;
		}

		_groupByTranslator.translate(
			searchRequestBuilder, searchContext, start, end);
	}

	protected void addHighlightedField(
		SearchRequestBuilder searchRequestBuilder, QueryConfig queryConfig,
		String fieldName) {

		searchRequestBuilder.addHighlightedField(
			fieldName, queryConfig.getHighlightFragmentSize(),
			queryConfig.getHighlightSnippetSize());

		String localizedFieldName = DocumentImpl.getLocalizedName(
			queryConfig.getLocale(), fieldName);

		searchRequestBuilder.addHighlightedField(
			localizedFieldName, queryConfig.getHighlightFragmentSize(),
			queryConfig.getHighlightSnippetSize());
	}

	protected void addHighlights(
		SearchRequestBuilder searchRequestBuilder, QueryConfig queryConfig) {

		for (String highlightFieldName : queryConfig.getHighlightFieldNames()) {
			addHighlightedField(
				searchRequestBuilder, queryConfig, highlightFieldName);
		}

		searchRequestBuilder.setHighlighterPostTags(
			HighlightUtil.HIGHLIGHT_TAG_CLOSE);
		searchRequestBuilder.setHighlighterPreTags(
			HighlightUtil.HIGHLIGHT_TAG_OPEN);
		searchRequestBuilder.setHighlighterRequireFieldMatch(
			queryConfig.isHighlightRequireFieldMatch());
	}

	protected void addPagination(
		SearchRequestBuilder searchRequestBuilder, int start, int end) {

		searchRequestBuilder.setFrom(start);
		searchRequestBuilder.setSize(end - start);
	}

	protected void addSelectedFields(
		SearchRequestBuilder searchRequestBuilder, QueryConfig queryConfig) {

		String[] selectedFieldNames = queryConfig.getSelectedFieldNames();

		if (ArrayUtil.isEmpty(selectedFieldNames)) {
			searchRequestBuilder.addField(StringPool.STAR);
		}
		else {
			searchRequestBuilder.addFields(selectedFieldNames);
		}
	}

	protected void addSnippets(
		Document document, Set<String> queryTerms,
		Map<String, HighlightField> highlightFields, String fieldName,
		Locale locale) {

		String snippet = StringPool.BLANK;

		String localizedContentName = DocumentImpl.getLocalizedName(
			locale, fieldName);

		String snippetFieldName = localizedContentName;

		HighlightField highlightField = highlightFields.get(
			localizedContentName);

		if (highlightField == null) {
			highlightField = highlightFields.get(fieldName);

			snippetFieldName = fieldName;
		}

		if (highlightField != null) {
			Text[] texts = highlightField.fragments();

			StringBundler sb = new StringBundler(texts.length * 2);

			for (Text text : texts) {
				sb.append(text);
				sb.append(StringPool.TRIPLE_PERIOD);
			}

			sb.setIndex(sb.index() - 1);

			snippet = sb.toString();
		}

		HighlightUtil.addSnippet(
			document, queryTerms, snippet, snippetFieldName);
	}

	protected void addSnippets(
		SearchHit hit, Document document, QueryConfig queryConfig,
		Set<String> queryTerms) {

		Map<String, HighlightField> highlightFields = hit.getHighlightFields();

		if (MapUtil.isEmpty(highlightFields)) {
			return;
		}

		for (String highlightFieldName : queryConfig.getHighlightFieldNames()) {
			addSnippets(
				document, queryTerms, highlightFields, highlightFieldName,
				queryConfig.getLocale());
		}
	}

	protected void addSort(
		SearchRequestBuilder searchRequestBuilder, Sort[] sorts) {

		if (ArrayUtil.isEmpty(sorts)) {
			return;
		}

		Set<String> sortFieldNames = new HashSet<>(sorts.length);

		for (Sort sort : sorts) {
			if (sort == null) {
				continue;
			}

			String sortFieldName = DocumentImpl.getSortFieldName(
				sort, "_score");

			if (sortFieldNames.contains(sortFieldName)) {
				continue;
			}

			sortFieldNames.add(sortFieldName);

			SortOrder sortOrder = SortOrder.ASC;

			if (sort.isReverse() || sortFieldName.equals("_score")) {
				sortOrder = SortOrder.DESC;
			}

			SortBuilder sortBuilder = null;

			if (sortFieldName.equals("_score")) {
				sortBuilder = SortBuilders.scoreSort();
			}
			else if (sort.getType() == Sort.GEO_DISTANCE_TYPE) {
				GeoDistanceSort geoDistanceSort = (GeoDistanceSort)sort;

				GeoDistanceSortBuilder geoDistanceSortBuilder =
					SortBuilders.geoDistanceSort(sortFieldName);

				geoDistanceSortBuilder.geoDistance(GeoDistance.DEFAULT);

				for (GeoLocationPoint geoLocationPoint :
						geoDistanceSort.getGeoLocationPoints()) {

					geoDistanceSortBuilder.point(
						geoLocationPoint.getLatitude(),
						geoLocationPoint.getLongitude());
				}

				Collection<String> geoHashes = geoDistanceSort.getGeoHashes();

				if (!geoHashes.isEmpty()) {
					geoDistanceSort.addGeoHash(
						geoHashes.toArray(new String[geoHashes.size()]));
				}

				sortBuilder = geoDistanceSortBuilder;
			}
			else {
				FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(
					sortFieldName);

				fieldSortBuilder.unmappedType("string");

				sortBuilder = fieldSortBuilder;
			}

			sortBuilder.order(sortOrder);

			searchRequestBuilder.addSort(sortBuilder);
		}
	}

	protected void addStats(
		SearchRequestBuilder searchRequestBuilder,
		SearchContext searchContext) {

		Map<String, Stats> statsMap = searchContext.getStats();

		for (Stats stats : statsMap.values()) {
			_statsTranslator.translate(searchRequestBuilder, stats);
		}
	}

	protected SearchResponse doSearch(
			SearchContext searchContext, Query query, int start, int end,
			boolean count)
		throws Exception {

		Client client = _elasticsearchConnectionManager.getClient();

		QueryConfig queryConfig = query.getQueryConfig();

		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(
			getSelectedIndexNames(queryConfig, searchContext));

		searchRequestBuilder.setTypes(getSelectedTypes(queryConfig));

		addStats(searchRequestBuilder, searchContext);

		if (!count) {
			addFacets(searchRequestBuilder, searchContext);
			addGroupBy(searchRequestBuilder, searchContext, start, end);
			addHighlights(searchRequestBuilder, queryConfig);
			addPagination(searchRequestBuilder, start, end);
			addSelectedFields(searchRequestBuilder, queryConfig);
			addSort(searchRequestBuilder, searchContext.getSorts());

			searchRequestBuilder.setTrackScores(queryConfig.isScoreEnabled());
		}
		else {
			searchRequestBuilder.setSize(0);
		}

		if (query.getPostFilter() != null) {
			QueryBuilder postFilterQueryBuilder = _filterTranslator.translate(
				query.getPostFilter(), searchContext);

			searchRequestBuilder.setPostFilter(postFilterQueryBuilder);
		}

		QueryBuilder queryBuilder = _queryTranslator.translate(
			query, searchContext);

		if (query.getPreBooleanFilter() != null) {
			QueryBuilder preFilterQueryBuilder = _filterTranslator.translate(
				query.getPreBooleanFilter(), searchContext);

			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

			boolQueryBuilder.filter(preFilterQueryBuilder);
			boolQueryBuilder.must(queryBuilder);

			queryBuilder = boolQueryBuilder;
		}

		searchRequestBuilder.setQuery(queryBuilder);

		SearchResponse searchResponse = searchRequestBuilder.get();

		if (_log.isInfoEnabled()) {
			_log.info(
				"The search engine processed " + queryBuilder.toString() +
					" in " + searchResponse.getTook());
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

		return processResponse(searchResponse, searchContext, query);
	}

	protected String[] getSelectedIndexNames(
		QueryConfig queryConfig, SearchContext searchContext) {

		String[] selectedIndexNames = queryConfig.getSelectedIndexNames();

		if (ArrayUtil.isNotEmpty(selectedIndexNames)) {
			return selectedIndexNames;
		}

		return new String[] {String.valueOf(searchContext.getCompanyId())};
	}

	protected String[] getSelectedTypes(QueryConfig queryConfig) {
		String[] selectedTypes = queryConfig.getSelectedTypes();

		if (ArrayUtil.isNotEmpty(selectedTypes)) {
			return selectedTypes;
		}

		return new String[] {DocumentTypes.LIFERAY};
	}

	protected Hits processResponse(
		SearchResponse searchResponse, SearchContext searchContext,
		Query query) {

		SearchHits searchHits = searchResponse.getHits();

		Hits hits = new HitsImpl();

		updateFacetCollectors(searchContext, searchResponse);
		updateGroupedHits(searchResponse, searchContext, query, hits);
		updateStatsResults(searchContext, searchResponse, hits);

		TimeValue timeValue = searchResponse.getTook();

		hits.setSearchTime((float)timeValue.getSecondsFrac());

		return processSearchHits(searchHits, query, hits);
	}

	protected Document processSearchHit(
		SearchHit searchHit, QueryConfig queryConfig) {

		Document document = new DocumentImpl();

		Map<String, SearchHitField> searchHitFields = searchHit.getFields();

		for (Map.Entry<String, SearchHitField> entry :
				searchHitFields.entrySet()) {

			SearchHitField searchHitField = entry.getValue();

			Collection<Object> fieldValues = searchHitField.getValues();

			Field field = new Field(
				entry.getKey(),
				ArrayUtil.toStringArray(
					fieldValues.toArray(new Object[fieldValues.size()])));

			document.add(field);
		}

		populateUID(document, queryConfig);

		return document;
	}

	protected Hits processSearchHits(
		SearchHits searchHits, Query query, Hits hits) {

		List<Document> documents = new ArrayList<>();
		Set<String> queryTerms = new HashSet<>();
		List<Float> scores = new ArrayList<>();

		if (searchHits.totalHits() > 0) {
			SearchHit[] searchHitsArray = searchHits.getHits();

			for (SearchHit searchHit : searchHitsArray) {
				Document document = processSearchHit(
					searchHit, query.getQueryConfig());

				documents.add(document);

				scores.add(searchHit.getScore());

				addSnippets(
					searchHit, document, query.getQueryConfig(), queryTerms);
			}
		}

		hits.setDocs(documents.toArray(new Document[documents.size()]));
		hits.setLength((int)searchHits.getTotalHits());
		hits.setQuery(query);
		hits.setQueryTerms(queryTerms.toArray(new String[queryTerms.size()]));
		hits.setScores(ArrayUtil.toFloatArray(scores));

		return hits;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchConnectionManager(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	@Reference(service = CompositeFacetProcessor.class, unbind = "-")
	protected void setFacetProcessor(
		FacetProcessor<SearchRequestBuilder> facetProcessor) {

		_facetProcessor = facetProcessor;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setFilterTranslator(
		FilterTranslator<QueryBuilder> filterTranslator) {

		_filterTranslator = filterTranslator;
	}

	@Reference(unbind = "-")
	protected void setGroupByTranslator(GroupByTranslator groupByTranslator) {
		_groupByTranslator = groupByTranslator;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setQueryTranslator(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_queryTranslator = queryTranslator;
	}

	@Reference(unbind = "-")
	protected void setStatsTranslator(StatsTranslator statsTranslator) {
		_statsTranslator = statsTranslator;
	}

	protected void updateFacetCollectors(
		SearchContext searchContext, SearchResponse searchResponse) {

		Aggregations aggregations = searchResponse.getAggregations();

		if (aggregations == null) {
			return;
		}

		Map<String, Aggregation> aggregationsMap = aggregations.getAsMap();

		Map<String, Facet> facetsMap = searchContext.getFacets();

		for (Facet facet : facetsMap.values()) {
			if (facet.isStatic()) {
				continue;
			}

			Aggregation aggregation = aggregationsMap.get(facet.getFieldName());

			FacetCollector facetCollector =
				new ElasticsearchFacetFieldCollector(aggregation);

			facet.setFacetCollector(facetCollector);
		}
	}

	protected void updateGroupedHits(
		SearchResponse searchResponse, SearchContext searchContext, Query query,
		Hits hits) {

		GroupBy groupBy = searchContext.getGroupBy();

		if (groupBy == null) {
			return;
		}

		Aggregations aggregations = searchResponse.getAggregations();

		Map<String, Aggregation> aggregationsMap = aggregations.getAsMap();

		Terms terms = (Terms)aggregationsMap.get(
			GroupByTranslator.GROUP_BY_AGGREGATION_PREFIX + groupBy.getField());

		List<Terms.Bucket> buckets = terms.getBuckets();

		for (Terms.Bucket bucket : buckets) {
			Aggregations bucketAggregations = bucket.getAggregations();
			TopHits topHits = bucketAggregations.get(
				GroupByTranslator.TOP_HITS_AGGREGATION_NAME);

			SearchHits groupedSearchHits = topHits.getHits();

			Hits groupedHits = new HitsImpl();

			processSearchHits(groupedSearchHits, query, groupedHits);

			groupedHits.setLength((int)groupedSearchHits.getTotalHits());

			hits.addGroupedHits(bucket.getKey(), groupedHits);
		}
	}

	protected void updateStatsResults(
		SearchContext searchContext, SearchResponse searchResponse, Hits hits) {

		Map<String, Stats> statsMap = searchContext.getStats();

		if (statsMap.isEmpty()) {
			return;
		}

		Aggregations aggregations = searchResponse.getAggregations();

		if (aggregations == null) {
			return;
		}

		Map<String, Aggregation> aggregationsMap = aggregations.getAsMap();

		for (Stats stats : statsMap.values()) {
			if (!stats.isEnabled()) {
				continue;
			}

			StatsResults statsResults = _statsTranslator.translate(
				aggregationsMap, stats);

			hits.addStatsResults(statsResults);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchIndexSearcher.class);

	private volatile ElasticsearchConfiguration _elasticsearchConfiguration;
	private volatile ElasticsearchConnectionManager
		_elasticsearchConnectionManager;
	private volatile FacetProcessor<SearchRequestBuilder> _facetProcessor;
	private volatile FilterTranslator<QueryBuilder> _filterTranslator;
	private volatile GroupByTranslator _groupByTranslator;
	private boolean _logExceptionsOnly;
	private volatile QueryTranslator<QueryBuilder> _queryTranslator;
	private volatile StatsTranslator _statsTranslator;

}