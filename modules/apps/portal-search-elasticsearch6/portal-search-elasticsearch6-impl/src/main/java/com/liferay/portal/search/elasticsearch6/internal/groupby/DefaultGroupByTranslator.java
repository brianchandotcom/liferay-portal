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

package com.liferay.portal.search.elasticsearch6.internal.groupby;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GeoDistanceSort;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;
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
		SearchRequestBuilder searchRequestBuilder, GroupBy groupBy,
		Sort[] sorts, Locale locale, String[] selectedFieldNames,
		String[] highlightFieldNames, boolean highlightEnabled,
		boolean highlightRequireFieldMatch, int highlightFragmentSize,
		int highlightSnippetSize, int start, int end) {

		TermsAggregationBuilder termsAggregationBuilder =
			AggregationBuilders.terms(
				GROUP_BY_AGGREGATION_PREFIX + groupBy.getField());

		termsAggregationBuilder = termsAggregationBuilder.field(
			groupBy.getField());

		TopHitsAggregationBuilder topHitsAggregationBuilder = getTopHitsBuilder(
			groupBy, sorts, selectedFieldNames, locale, highlightFieldNames,
			highlightEnabled, highlightRequireFieldMatch, highlightFragmentSize,
			highlightSnippetSize, start, end);

		termsAggregationBuilder.subAggregation(topHitsAggregationBuilder);

		searchRequestBuilder.addAggregation(termsAggregationBuilder);
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

	protected void addSorts(
		TopHitsAggregationBuilder topHitsAggregationBuilder, Sort[] sorts) {

		if (ArrayUtil.isEmpty(sorts)) {
			return;
		}

		Set<String> sortFieldNames = new HashSet<>(sorts.length);

		for (Sort sort : sorts) {
			if (sort == null) {
				continue;
			}

			String sortFieldName = Field.getSortFieldName(sort, "_score");

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
						sortFieldName,
						geoPoints.toArray(new GeoPoint[geoPoints.size()]));

				geoDistanceSortBuilder.geoDistance(GeoDistance.ARC);

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

			topHitsAggregationBuilder.sort(sortBuilder);
		}
	}

	protected TopHitsAggregationBuilder getTopHitsBuilder(
		GroupBy groupBy, Sort[] sorts, String[] selectedFieldNames,
		Locale locale, String[] highlightFieldNames, boolean highlightEnabled,
		boolean highlightRequireFieldMatch, int highlightFragmentSize,
		int highlightSnippetSize, int start, int end) {

		TopHitsAggregationBuilder topHitsAggregationBuilder =
			AggregationBuilders.topHits(TOP_HITS_AGGREGATION_NAME);

		int groupyByStart = groupBy.getStart();

		if (groupyByStart == 0) {
			groupyByStart = start;
		}

		topHitsAggregationBuilder.from(groupyByStart);

		int groupBySize = groupBy.getSize();

		if (groupBySize == 0) {
			groupBySize = end - start + 1;
		}

		topHitsAggregationBuilder.size(groupBySize);

		if (highlightEnabled) {
			addHighlights(
				topHitsAggregationBuilder, locale, highlightFieldNames,
				highlightFragmentSize, highlightSnippetSize,
				highlightRequireFieldMatch);
		}

		addSelectedFields(topHitsAggregationBuilder, selectedFieldNames);
		addSorts(topHitsAggregationBuilder, sorts);

		return topHitsAggregationBuilder;
	}

}