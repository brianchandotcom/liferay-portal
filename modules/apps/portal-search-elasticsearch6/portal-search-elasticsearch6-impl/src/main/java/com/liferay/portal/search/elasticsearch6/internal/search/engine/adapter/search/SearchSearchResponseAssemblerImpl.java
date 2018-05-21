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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.search.StatsResults;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch6.internal.SearchHitDocumentTranslator;
import com.liferay.portal.search.elasticsearch6.internal.facet.FacetCollectorFactory;
import com.liferay.portal.search.elasticsearch6.internal.groupby.GroupByTranslator;
import com.liferay.portal.search.elasticsearch6.internal.stats.StatsTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SearchSearchResponseAssembler.class)
public class SearchSearchResponseAssemblerImpl
	implements SearchSearchResponseAssembler {

	@Override
	public void assemble(
		SearchResponse searchResponse,
		SearchSearchResponse searchSearchResponse,
		SearchSearchRequest searchSearchRequest,
		String searchRequestBuilderString) {

		commonSearchResponseAssembler.assemble(
			searchResponse, searchSearchResponse, searchRequestBuilderString);

		SearchHits searchHits = searchResponse.getHits();

		searchSearchResponse.setCount(searchHits.totalHits);

		updateFacetCollectors(searchSearchRequest.getFacets(), searchResponse);

		searchSearchResponse.setHits(
			getHits(searchSearchRequest, searchResponse, searchHits));
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
			Field.SNIPPET.concat(StringPool.UNDERLINE).concat(snippetFieldName),
			StringUtil.merge(array, StringPool.TRIPLE_PERIOD));
	}

	protected void addSnippets(
		SearchHit hit, Document document, QueryConfig queryConfig) {

		Map<String, HighlightField> highlightFields = hit.getHighlightFields();

		if (MapUtil.isEmpty(highlightFields)) {
			return;
		}

		for (String highlightFieldName : queryConfig.getHighlightFieldNames()) {
			addSnippets(
				document, highlightFields, highlightFieldName,
				queryConfig.getLocale());
		}
	}

	protected String getAggregationName(Facet facet) {
		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		JSONObject data = facetConfiguration.getData();

		return data.getString("aggregationName", facet.getFieldName());
	}

	protected FacetCollector getFacetCollector(
		Facet facet, Map<String, Aggregation> aggregationsMap) {

		FacetCollectorFactory facetCollectorFactory =
			new FacetCollectorFactory();

		return facetCollectorFactory.getFacetCollector(
			aggregationsMap.get(getAggregationName(facet)));
	}

	protected Hits getHits(
		SearchSearchRequest searchSearchRequest, SearchResponse searchResponse,
		SearchHits searchHits) {

		Hits hits = new HitsImpl();

		TimeValue timeValue = searchResponse.getTook();

		hits.setSearchTime((float)timeValue.getSecondsFrac());

		if (searchSearchRequest.getGroupBy() != null) {
			updateGroupedHits(
				searchSearchRequest.getGroupBy(),
				searchResponse.getAggregations(),
				searchSearchRequest.getQuery(), hits);
		}

		if (MapUtil.isNotEmpty(searchSearchRequest.getStats())) {
			updateStatsResults(
				searchSearchRequest.getStats(), searchResponse, hits);
		}

		processSearchHits(searchHits, searchSearchRequest.getQuery(), hits);

		return hits;
	}

	protected void populateUID(Document document, QueryConfig queryConfig) {
		Field uidField = document.getField(Field.UID);

		if (uidField != null) {
			return;
		}

		if (Validator.isNull(queryConfig.getAlternateUidFieldName())) {
			return;
		}

		String uidValue = document.get(queryConfig.getAlternateUidFieldName());

		if (Validator.isNotNull(uidValue)) {
			uidField = new Field(Field.UID, uidValue);

			document.add(uidField);
		}
	}

	protected Document processSearchHit(
		SearchHit searchHit, QueryConfig queryConfig) {

		Document document = searchHitDocumentTranslator.translate(searchHit);

		populateUID(document, queryConfig);

		return document;
	}

	protected Hits processSearchHits(
		SearchHits searchHits, Query query, Hits hits) {

		List<Document> documents = new ArrayList<>();
		List<Float> scores = new ArrayList<>();

		if (searchHits.getTotalHits() > 0) {
			SearchHit[] searchHitsArray = searchHits.getHits();

			for (SearchHit searchHit : searchHitsArray) {
				Document document = processSearchHit(
					searchHit, query.getQueryConfig());

				documents.add(document);

				scores.add(searchHit.getScore());

				addSnippets(searchHit, document, query.getQueryConfig());
			}
		}

		hits.setDocs(documents.toArray(new Document[documents.size()]));
		hits.setLength((int)searchHits.getTotalHits());
		hits.setQuery(query);
		hits.setQueryTerms(new String[0]);
		hits.setScores(ArrayUtil.toFloatArray(scores));

		return hits;
	}

	protected void updateFacetCollectors(
		Map<String, Facet> facets, SearchResponse searchResponse) {

		Aggregations aggregations = searchResponse.getAggregations();

		if (aggregations == null) {
			return;
		}

		Map<String, Aggregation> aggregationsMap = aggregations.getAsMap();

		facets.forEach(
			(facetName, facet) -> {
				if (!facet.isStatic()) {
					facet.setFacetCollector(
						getFacetCollector(facet, aggregationsMap));
				}
			});
	}

	protected void updateGroupedHits(
		GroupBy groupBy, Aggregations aggregations, Query query, Hits hits) {

		Map<String, Aggregation> aggregationsMap = aggregations.getAsMap();

		Terms terms = (Terms)aggregationsMap.get(
			GroupByTranslator.GROUP_BY_AGGREGATION_PREFIX + groupBy.getField());

		List<? extends Terms.Bucket> buckets = terms.getBuckets();

		for (Terms.Bucket bucket : buckets) {
			Aggregations bucketAggregations = bucket.getAggregations();

			TopHits topHits = bucketAggregations.get(
				GroupByTranslator.TOP_HITS_AGGREGATION_NAME);

			SearchHits groupedSearchHits = topHits.getHits();

			Hits groupedHits = new HitsImpl();

			processSearchHits(groupedSearchHits, query, groupedHits);

			groupedHits.setLength((int)groupedSearchHits.getTotalHits());

			hits.addGroupedHits(bucket.getKeyAsString(), groupedHits);
		}
	}

	protected void updateStatsResults(
		Map<String, Stats> statsMap, SearchResponse searchResponse, Hits hits) {

		Aggregations aggregations = searchResponse.getAggregations();

		if (aggregations == null) {
			return;
		}

		Map<String, Aggregation> aggregationsMap = aggregations.getAsMap();

		for (Stats stats : statsMap.values()) {
			if (!stats.isEnabled()) {
				continue;
			}

			StatsResults statsResults = statsTranslator.translate(
				aggregationsMap, stats);

			hits.addStatsResults(statsResults);
		}
	}

	@Reference
	protected CommonSearchResponseAssembler commonSearchResponseAssembler;

	@Reference
	protected SearchHitDocumentTranslator searchHitDocumentTranslator;

	@Reference
	protected StatsTranslator statsTranslator;

}