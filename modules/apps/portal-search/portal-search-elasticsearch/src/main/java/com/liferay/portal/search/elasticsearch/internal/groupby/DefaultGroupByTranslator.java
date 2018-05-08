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

package com.liferay.portal.search.elasticsearch.internal.groupby;

import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.GeoDistanceSort;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsBuilder;
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

		TermsBuilder termsBuilder = AggregationBuilders.terms(
			GROUP_BY_AGGREGATION_PREFIX + groupBy.getField());

		termsBuilder = termsBuilder.field(groupBy.getField());

		TopHitsBuilder topHitsBuilder = getTopHitsBuilder(
			searchContext, start, end, groupBy);

		termsBuilder.subAggregation(topHitsBuilder);

		searchRequestBuilder.addAggregation(termsBuilder);
	}

	protected void addHighlightedField(
		TopHitsBuilder topHitsBuilder, QueryConfig queryConfig,
		String fieldName) {

		topHitsBuilder.addHighlightedField(
			fieldName, queryConfig.getHighlightFragmentSize(),
			queryConfig.getHighlightSnippetSize());

		String localizedFieldName = DocumentImpl.getLocalizedName(
			queryConfig.getLocale(), fieldName);

		topHitsBuilder.addHighlightedField(
			localizedFieldName, queryConfig.getHighlightFragmentSize(),
			queryConfig.getHighlightSnippetSize());
	}

	protected void addHighlights(
		TopHitsBuilder topHitsBuilder, QueryConfig queryConfig) {

		if (!queryConfig.isHighlightEnabled()) {
			return;
		}

		for (String highlightFieldName : queryConfig.getHighlightFieldNames()) {
			addHighlightedField(
				topHitsBuilder, queryConfig, highlightFieldName);
		}

		topHitsBuilder.setHighlighterPostTags(
			HighlightUtil.HIGHLIGHT_TAG_CLOSE);
		topHitsBuilder.setHighlighterPreTags(HighlightUtil.HIGHLIGHT_TAG_OPEN);
		topHitsBuilder.setHighlighterRequireFieldMatch(
			queryConfig.isHighlightRequireFieldMatch());
	}

	protected void addSorts(TopHitsBuilder topHitsBuilder, Sort[] sorts) {
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

			topHitsBuilder.addSort(sortBuilder);
		}
	}

	protected TopHitsBuilder getTopHitsBuilder(
		SearchContext searchContext, int start, int end, GroupBy groupBy) {

		TopHitsBuilder topHitsBuilder = AggregationBuilders.topHits(
			TOP_HITS_AGGREGATION_NAME);

		int groupyByStart = groupBy.getStart();

		if (groupyByStart == 0) {
			groupyByStart = start;
		}

		topHitsBuilder.setFrom(groupyByStart);

		int groupBySize = groupBy.getSize();

		if (groupBySize == 0) {
			groupBySize = end - start + 1;
		}

		topHitsBuilder.setSize(groupBySize);

		addHighlights(topHitsBuilder, searchContext.getQueryConfig());
		addSorts(topHitsBuilder, searchContext.getSorts());

		return topHitsBuilder;
	}

}