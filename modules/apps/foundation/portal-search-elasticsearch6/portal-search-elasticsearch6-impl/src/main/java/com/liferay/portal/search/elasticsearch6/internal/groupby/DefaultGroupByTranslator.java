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

import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.GeoDistanceSort;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
 */
@Component(immediate = true, service = GroupByTranslator.class)
public class DefaultGroupByTranslator implements GroupByTranslator {

	@Override
	public void translate(
		SearchRequestBuilder searchRequestBuilder, SearchContext searchContext,
		int start, int end) {

		GroupBy groupBy = searchContext.getGroupBy();

		TermsAggregationBuilder termsAggregationBuilder =
			AggregationBuilders.terms(
				GROUP_BY_AGGREGATION_PREFIX + groupBy.getField());

		termsAggregationBuilder = termsAggregationBuilder.field(
			groupBy.getField());

		TopHitsAggregationBuilder topHitsAggregationBuilder = getTopHitsBuilder(
			searchContext, start, end, groupBy);

		termsAggregationBuilder.subAggregation(topHitsAggregationBuilder);

		searchRequestBuilder.addAggregation(termsAggregationBuilder);
	}

	protected void addHighlightedField(
		TopHitsAggregationBuilder topHitsAggregationBuilder,
		HighlightBuilder highlightBuilder, QueryConfig queryConfig,
		String fieldName) {

		highlightBuilder.field(
			fieldName, queryConfig.getHighlightFragmentSize(),
			queryConfig.getHighlightSnippetSize());

		String localizedFieldName = DocumentImpl.getLocalizedName(
			queryConfig.getLocale(), fieldName);

		highlightBuilder.field(
			localizedFieldName, queryConfig.getHighlightFragmentSize(),
			queryConfig.getHighlightSnippetSize());

		topHitsAggregationBuilder.highlighter(highlightBuilder);
	}

	protected void addHighlights(
		TopHitsAggregationBuilder topHitsAggregationBuilder,
		QueryConfig queryConfig) {

		if (!queryConfig.isHighlightEnabled()) {
			return;
		}

		HighlightBuilder highlightBuilder = new HighlightBuilder();

		for (String highlightFieldName : queryConfig.getHighlightFieldNames()) {
			addHighlightedField(
				topHitsAggregationBuilder, highlightBuilder, queryConfig,
				highlightFieldName);
		}

		highlightBuilder.postTags(HighlightUtil.HIGHLIGHT_TAG_CLOSE);
		highlightBuilder.preTags(HighlightUtil.HIGHLIGHT_TAG_OPEN);
		highlightBuilder.requireFieldMatch(
			queryConfig.isHighlightRequireFieldMatch());

		topHitsAggregationBuilder.highlighter(highlightBuilder);
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
		SearchContext searchContext, int start, int end, GroupBy groupBy) {

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

		addHighlights(
			topHitsAggregationBuilder, searchContext.getQueryConfig());
		addSorts(topHitsAggregationBuilder, searchContext.getSorts());

		return topHitsAggregationBuilder;
	}

}