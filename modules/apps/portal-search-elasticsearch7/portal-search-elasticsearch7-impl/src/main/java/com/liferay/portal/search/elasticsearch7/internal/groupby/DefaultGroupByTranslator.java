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

package com.liferay.portal.search.elasticsearch7.internal.groupby;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GeoDistanceSort;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.groupby.GroupByRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.TopHitsAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.BucketSortPipelineAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Tibor Lipusz
 */
@Component(immediate = true, service = GroupByTranslator.class)
public class DefaultGroupByTranslator implements GroupByTranslator {

	@Override
	public void translate(
		SearchRequestBuilder searchRequestBuilder,
		GroupByRequest groupByRequest, Locale locale,
		String[] selectedFieldNames, String[] highlightFieldNames,
		boolean highlightEnabled, boolean highlightRequireFieldMatch,
		int highlightFragmentSize, int highlightSnippetSize) {

		TermsAggregationBuilder termsAggregationBuilder =
			AggregationBuilders.terms(
				GROUP_BY_AGGREGATION_PREFIX + groupByRequest.getField());

		termsAggregationBuilder = termsAggregationBuilder.field(
			groupByRequest.getField());

		int termsSize = GetterUtil.getInteger(groupByRequest.getTermsSize());

		if (termsSize > 0) {
			termsAggregationBuilder.size(termsSize);
		}

		addTermsSorts(termsAggregationBuilder, groupByRequest);

		int termsStart = GetterUtil.getInteger(groupByRequest.getTermsStart());

		if ((termsSize > 0) || (termsStart > 0)) {
			termsAggregationBuilder.subAggregation(
				getBucketSortPipelineBuilder(termsStart, termsSize));
		}

		TopHitsAggregationBuilder topHitsAggregationBuilder = getTopHitsBuilder(
			groupByRequest, selectedFieldNames, locale, highlightFieldNames,
			highlightEnabled, highlightRequireFieldMatch, highlightFragmentSize,
			highlightSnippetSize);

		termsAggregationBuilder.subAggregation(topHitsAggregationBuilder);

		searchRequestBuilder.addAggregation(termsAggregationBuilder);
	}

	protected void addDocsSorts(
		TopHitsAggregationBuilder topHitsAggregationBuilder, Sort[] sorts) {

		if (ArrayUtil.isEmpty(sorts)) {
			return;
		}

		Set<String> sortFieldNames = new HashSet<>();

		for (Sort sort : sorts) {
			if (sort == null) {
				continue;
			}

			String sortFieldName = Field.getSortFieldName(
				sort, _ELASTICSEARCH_SCORE_FIELD);

			if (sortFieldNames.contains(sortFieldName)) {
				continue;
			}

			sortFieldNames.add(sortFieldName);

			SortOrder sortOrder = SortOrder.ASC;

			if (sort.isReverse() ||
				sortFieldName.equals(_ELASTICSEARCH_SCORE_FIELD)) {

				sortOrder = SortOrder.DESC;
			}

			SortBuilder sortBuilder = null;

			if (sortFieldName.equals(_ELASTICSEARCH_SCORE_FIELD)) {
				sortBuilder = SortBuilders.scoreSort();
			}
			else if (sort.getType() == Sort.GEO_DISTANCE_TYPE) {
				GeoDistanceSort geoDistanceSort = (GeoDistanceSort)sort;

				List<GeoPoint> geoPoints = new ArrayList<>();

				for (GeoLocationPoint geoLocationPoint :
						geoDistanceSort.getGeoLocationPoints()) {

					geoPoints.add(
						new GeoPoint(
							geoLocationPoint.getLatitude(),
							geoLocationPoint.getLongitude()));
				}

				GeoDistanceSortBuilder geoDistanceSortBuilder =
					SortBuilders.geoDistanceSort(
						sortFieldName, geoPoints.toArray(new GeoPoint[0]));

				geoDistanceSortBuilder.geoDistance(GeoDistance.ARC);

				Collection<String> geoHashes = geoDistanceSort.getGeoHashes();

				if (!geoHashes.isEmpty()) {
					geoDistanceSort.addGeoHash(
						geoHashes.toArray(new String[0]));
				}

				sortBuilder = geoDistanceSortBuilder;
			}
			else {
				FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(
					sortFieldName);

				fieldSortBuilder.unmappedType("keyword");

				sortBuilder = fieldSortBuilder;
			}

			sortBuilder.order(sortOrder);

			topHitsAggregationBuilder.sort(sortBuilder);
		}
	}

	protected void addHighlightedField(
		TopHitsAggregationBuilder topHitsAggregationBuilder,
		HighlightBuilder highlightBuilder, Locale locale, String fieldName,
		int highlightFragmentSize, int highlightSnippetSize) {

		highlightBuilder.field(
			fieldName, highlightFragmentSize, highlightSnippetSize);

		String localizedFieldName = Field.getLocalizedName(locale, fieldName);

		highlightBuilder.field(
			localizedFieldName, highlightFragmentSize, highlightSnippetSize);

		topHitsAggregationBuilder.highlighter(highlightBuilder);
	}

	protected void addHighlights(
		TopHitsAggregationBuilder topHitsAggregationBuilder, Locale locale,
		String[] highlightFieldNames, int highlightFragmentSize,
		int highlightSnippetSize, boolean highlightRequireFieldMatch) {

		HighlightBuilder highlightBuilder = new HighlightBuilder();

		for (String highlightFieldName : highlightFieldNames) {
			addHighlightedField(
				topHitsAggregationBuilder, highlightBuilder, locale,
				highlightFieldName, highlightFragmentSize,
				highlightSnippetSize);
		}

		highlightBuilder.postTags(HighlightUtil.HIGHLIGHT_TAG_CLOSE);
		highlightBuilder.preTags(HighlightUtil.HIGHLIGHT_TAG_OPEN);
		highlightBuilder.requireFieldMatch(highlightRequireFieldMatch);

		topHitsAggregationBuilder.highlighter(highlightBuilder);
	}

	protected void addSelectedFields(
		TopHitsAggregationBuilder topHitsAggregationBuilder,
		String[] selectedFieldNames) {

		if (ArrayUtil.isEmpty(selectedFieldNames)) {
			topHitsAggregationBuilder.storedField(StringPool.STAR);
		}
		else {
			topHitsAggregationBuilder.storedFields(
				Arrays.asList(selectedFieldNames));
		}
	}

	protected void addTermsSorts(
		TermsAggregationBuilder termsAggregationBuilder,
		GroupByRequest groupByRequest) {

		Sort[] sorts = groupByRequest.getTermsSorts();

		if (ArrayUtil.isEmpty(sorts)) {
			return;
		}

		Set<String> sortFieldNames = new HashSet<>();

		List<BucketOrder> bucketOrders = new ArrayList<>(sorts.length);

		for (Sort sort : sorts) {
			if (sort == null) {
				continue;
			}

			String sortFieldName = sort.getFieldName();

			if (sortFieldNames.contains(sortFieldName)) {
				continue;
			}

			sortFieldNames.add(sortFieldName);

			if (sortFieldName.equals("_count")) {
				bucketOrders.add(BucketOrder.count(!sort.isReverse()));
			}
			else if (sortFieldName.equals("_key")) {
				bucketOrders.add(BucketOrder.key(!sort.isReverse()));
			}
		}

		if (!bucketOrders.isEmpty()) {
			termsAggregationBuilder.order(bucketOrders);
		}
	}

	protected BucketSortPipelineAggregationBuilder getBucketSortPipelineBuilder(
		int start, int size) {

		BucketSortPipelineAggregationBuilder
			bucketSortPipelineAggregationBuilder =
				new BucketSortPipelineAggregationBuilder(
					BUCKET_SORT_AGGREGATION_NAME, Collections.emptyList());

		if (start > 0) {
			bucketSortPipelineAggregationBuilder.from(start);
		}

		if (size > 0) {
			bucketSortPipelineAggregationBuilder.size(size);
		}

		return bucketSortPipelineAggregationBuilder;
	}

	protected TopHitsAggregationBuilder getTopHitsBuilder(
		GroupByRequest groupByRequest, String[] selectedFieldNames,
		Locale locale, String[] highlightFieldNames, boolean highlightEnabled,
		boolean highlightRequireFieldMatch, int highlightFragmentSize,
		int highlightSnippetSize) {

		TopHitsAggregationBuilder topHitsAggregationBuilder =
			AggregationBuilders.topHits(TOP_HITS_AGGREGATION_NAME);

		int docsStart = GetterUtil.getInteger(groupByRequest.getDocsStart());

		if (docsStart > 0) {
			topHitsAggregationBuilder.from(docsStart);
		}

		int docsSize = GetterUtil.getInteger(groupByRequest.getDocsSize());

		if (docsSize > 0) {
			topHitsAggregationBuilder.size(docsSize);
		}

		addDocsSorts(topHitsAggregationBuilder, groupByRequest.getDocsSorts());

		if (highlightEnabled) {
			addHighlights(
				topHitsAggregationBuilder, locale, highlightFieldNames,
				highlightFragmentSize, highlightSnippetSize,
				highlightRequireFieldMatch);
		}

		addSelectedFields(topHitsAggregationBuilder, selectedFieldNames);

		return topHitsAggregationBuilder;
	}

	private static final String _ELASTICSEARCH_SCORE_FIELD = "_score";

}