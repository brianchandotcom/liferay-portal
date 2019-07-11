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

package com.liferay.portal.search.elasticsearch7.internal.search.response;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.search.StatsResults;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.SearchHitDocumentTranslator;
import com.liferay.portal.search.elasticsearch7.internal.facet.FacetCollectorFactory;
import com.liferay.portal.search.elasticsearch7.internal.facet.FacetUtil;
import com.liferay.portal.search.elasticsearch7.internal.groupby.GroupByTranslator;
import com.liferay.portal.search.elasticsearch7.internal.stats.StatsTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.groupby.GroupByRequest;
import com.liferay.portal.search.groupby.GroupByResponse;
import com.liferay.portal.search.groupby.GroupByResponseFactory;
import com.liferay.portal.search.legacy.stats.StatsRequestBuilderFactory;
import com.liferay.portal.search.legacy.stats.StatsResultsTranslator;
import com.liferay.portal.search.stats.StatsRequest;
import com.liferay.portal.search.stats.StatsRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.lucene.search.TotalHits;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.TopHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(service = SearchResponseTranslator.class)
public class DefaultSearchResponseTranslator
	implements SearchResponseTranslator {

	@Override
	public void populate(
		SearchSearchResponse searchSearchResponse,
		SearchResponse searchResponse,
		SearchSearchRequest searchSearchRequest) {

		SearchHits searchHits = searchResponse.getHits();

		Hits hits = new HitsImpl();

		updateFacetCollectors(searchResponse, searchSearchRequest.getFacets());

		updateGroupedHits(
			searchSearchResponse, searchResponse, searchSearchRequest, hits,
			searchSearchRequest.getAlternateUidFieldName(),
			searchSearchRequest.getHighlightFieldNames(),
			searchSearchRequest.getLocale());

		updateStatsResults(
			hits, searchResponse.getAggregations(),
			searchSearchRequest.getStats());

		TimeValue timeValue = searchResponse.getTook();

		hits.setSearchTime((float)timeValue.getSecondsFrac());

		processSearchHits(
			searchHits, hits, searchSearchRequest.getAlternateUidFieldName(),
			searchSearchRequest.getHighlightFieldNames(),
			searchSearchRequest.getLocale());

		searchSearchResponse.setHits(hits);
	}

	protected void addSnippets(
		Document document, Map<String, HighlightField> highlightFields,
		String fieldName, Locale locale) {

		String snippetFieldName = Field.getLocalizedName(locale, fieldName);

		HighlightField highlightField = highlightFields.get(snippetFieldName);

		if (highlightField == null) {
			highlightField = highlightFields.get(fieldName);

			snippetFieldName = fieldName;
		}

		if (highlightField == null) {
			return;
		}

		Object[] array = highlightField.fragments();

		document.addText(
			Field.SNIPPET.concat(
				StringPool.UNDERLINE
			).concat(
				snippetFieldName
			),
			StringUtil.merge(array, StringPool.TRIPLE_PERIOD));
	}

	protected void addSnippets(
		SearchHit hit, Document document, String[] highlightFieldNames,
		Locale locale) {

		Map<String, HighlightField> highlightFields = hit.getHighlightFields();

		if (MapUtil.isEmpty(highlightFields)) {
			return;
		}

		highlightFields.forEach(
			(fieldName, highlightField) -> addSnippets(
				document, highlightFields, fieldName, locale));
	}

	protected FacetCollector getFacetCollector(
		Facet facet, Map<String, Aggregation> aggregationsMap) {

		FacetCollectorFactory facetCollectorFactory =
			new FacetCollectorFactory();

		return facetCollectorFactory.getFacetCollector(
			aggregationsMap.get(FacetUtil.getAggregationName(facet)));
	}

	protected StatsResults getStatsResults(
		Map<String, Aggregation> aggregationsMap, Stats stats) {

		return _statsResultsTranslator.translate(
			_statsTranslator.translateResponse(
				aggregationsMap, translate(stats)));
	}

	protected void populateUID(
		Document document, String alternateUidFieldName) {

		Field uidField = document.getField(Field.UID);

		if (uidField != null) {
			return;
		}

		if (Validator.isNull(alternateUidFieldName)) {
			return;
		}

		String uidValue = document.get(alternateUidFieldName);

		if (Validator.isNotNull(uidValue)) {
			uidField = new Field(Field.UID, uidValue);

			document.add(uidField);
		}
	}

	protected Document processSearchHit(
		SearchHit searchHit, String alternateUidFieldName) {

		Document document = _searchHitDocumentTranslator.translate(searchHit);

		populateUID(document, alternateUidFieldName);

		return document;
	}

	protected Hits processSearchHits(
		SearchHits searchHits, Hits hits, String alternateUidFieldName,
		String[] highlightFieldNames, Locale locale) {

		List<Document> documents = new ArrayList<>();
		List<Float> scores = new ArrayList<>();

		TotalHits totalHits = searchHits.getTotalHits();

		if (totalHits.value > 0) {
			SearchHit[] searchHitsArray = searchHits.getHits();

			for (SearchHit searchHit : searchHitsArray) {
				Document document = processSearchHit(
					searchHit, alternateUidFieldName);

				documents.add(document);

				scores.add(searchHit.getScore());

				addSnippets(searchHit, document, highlightFieldNames, locale);
			}
		}

		hits.setDocs(documents.toArray(new Document[0]));
		hits.setLength((int)totalHits.value);
		hits.setQueryTerms(new String[0]);
		hits.setScores(ArrayUtil.toFloatArray(scores));

		return hits;
	}

	@Reference(unbind = "-")
	protected void setGroupByResponseFactory(
		GroupByResponseFactory groupByResponseFactory) {

		_groupByResponseFactory = groupByResponseFactory;
	}

	@Reference(unbind = "-")
	protected void setSearchHitDocumentTranslator(
		SearchHitDocumentTranslator searchHitDocumentTranslator) {

		_searchHitDocumentTranslator = searchHitDocumentTranslator;
	}

	@Reference(unbind = "-")
	protected void setStatsRequestBuilderFactory(
		StatsRequestBuilderFactory statsRequestBuilderFactory) {

		_statsRequestBuilderFactory = statsRequestBuilderFactory;
	}

	@Reference(unbind = "-")
	protected void setStatsResultsTranslator(
		StatsResultsTranslator statsResultsTranslator) {

		_statsResultsTranslator = statsResultsTranslator;
	}

	@Reference(unbind = "-")
	protected void setStatsTranslator(StatsTranslator statsTranslator) {
		_statsTranslator = statsTranslator;
	}

	protected StatsRequest translate(Stats stats) {
		StatsRequestBuilder statsRequestBuilder =
			_statsRequestBuilderFactory.getStatsRequestBuilder(stats);

		return statsRequestBuilder.build();
	}

	protected void updateFacetCollectors(
		SearchResponse searchResponse, Map<String, Facet> facetsMap) {

		Aggregations aggregations = searchResponse.getAggregations();

		if (aggregations == null) {
			return;
		}

		Map<String, Aggregation> aggregationsMap = aggregations.getAsMap();

		for (Facet facet : facetsMap.values()) {
			if (!facet.isStatic()) {
				facet.setFacetCollector(
					getFacetCollector(facet, aggregationsMap));
			}
		}
	}

	protected void updateGroupedHits(
		SearchSearchResponse searchSearchResponse,
		SearchResponse searchResponse, SearchSearchRequest searchSearchRequest,
		Hits hits, String alternateUidFieldName, String[] highlightFieldNames,
		Locale locale) {

		List<GroupByRequest> groupByRequests =
			searchSearchRequest.getGroupByRequests();

		if (ListUtil.isNotEmpty(groupByRequests)) {
			for (GroupByRequest groupByRequest : groupByRequests) {
				updateGroupedHits(
					searchSearchResponse, searchResponse,
					groupByRequest.getField(), hits, alternateUidFieldName,
					highlightFieldNames, locale);
			}
		}

		GroupBy groupBy = searchSearchRequest.getGroupBy();

		if (groupBy != null) {
			updateGroupedHits(
				searchSearchResponse, searchResponse, groupBy.getField(), hits,
				alternateUidFieldName, highlightFieldNames, locale);
		}
	}

	protected void updateGroupedHits(
		SearchSearchResponse searchSearchResponse,
		SearchResponse searchResponse, String field, Hits hits,
		String alternateUidFieldName, String[] highlightFieldNames,
		Locale locale) {

		Aggregations aggregations = searchResponse.getAggregations();

		Map<String, Aggregation> aggregationsMap = aggregations.getAsMap();

		Terms terms = (Terms)aggregationsMap.get(
			GroupByTranslator.GROUP_BY_AGGREGATION_PREFIX + field);

		List<? extends Terms.Bucket> buckets = terms.getBuckets();

		GroupByResponse groupByResponse =
			_groupByResponseFactory.getGroupByResponse(field);

		searchSearchResponse.addGroupByResponse(groupByResponse);

		for (Terms.Bucket bucket : buckets) {
			Aggregations bucketAggregations = bucket.getAggregations();

			TopHits topHits = bucketAggregations.get(
				GroupByTranslator.TOP_HITS_AGGREGATION_NAME);

			SearchHits groupedSearchHits = topHits.getHits();

			Hits groupedHits = new HitsImpl();

			processSearchHits(
				groupedSearchHits, groupedHits, alternateUidFieldName,
				highlightFieldNames, locale);

			TotalHits totalHits = groupedSearchHits.getTotalHits();

			groupedHits.setLength((int)totalHits.value);

			hits.addGroupedHits(bucket.getKeyAsString(), groupedHits);

			groupByResponse.putHits(bucket.getKeyAsString(), groupedHits);
		}
	}

	protected void updateStatsResults(
		Hits hits, Aggregations aggregations, Map<String, Stats> statsMap) {

		if (aggregations != null) {
			updateStatsResults(hits, aggregations.getAsMap(), statsMap);
		}
	}

	protected void updateStatsResults(
		Hits hits, Map<String, Aggregation> aggregationsMap,
		Map<String, Stats> statsMap) {

		if (!MapUtil.isEmpty(statsMap)) {
			for (Stats stats : statsMap.values()) {
				hits.addStatsResults(getStatsResults(aggregationsMap, stats));
			}
		}
	}

	private GroupByResponseFactory _groupByResponseFactory;
	private SearchHitDocumentTranslator _searchHitDocumentTranslator;
	private StatsRequestBuilderFactory _statsRequestBuilderFactory;
	private StatsResultsTranslator _statsResultsTranslator;
	private StatsTranslator _statsTranslator;

}