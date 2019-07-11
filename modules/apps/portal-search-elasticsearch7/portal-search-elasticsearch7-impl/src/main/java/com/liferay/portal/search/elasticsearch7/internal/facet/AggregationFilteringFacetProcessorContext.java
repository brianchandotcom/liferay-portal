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

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class AggregationFilteringFacetProcessorContext
	implements FacetProcessorContext {

	public static FacetProcessorContext newInstance(Collection<Facet> facets) {
		return new AggregationFilteringFacetProcessorContext(
			getSelectionFiltersMap(facets));
	}

	@Override
	public AggregationBuilder postProcessAggregationBuilder(
		AggregationBuilder aggregationBuilder) {

		String aggregationName = aggregationBuilder.getName();

		AggregationBuilder superAggregationBuilder = getSuperAggregationBuilder(
			aggregationName);

		if (superAggregationBuilder != null) {
			return superAggregationBuilder.subAggregation(aggregationBuilder);
		}

		return aggregationBuilder;
	}

	protected static List<QueryBuilder> getSelectionFilters(
		com.liferay.portal.search.facet.Facet facet) {

		List<QueryBuilder> queryBuilders = new ArrayList<>();

		String fieldName = facet.getFieldName();

		if (facet instanceof RangeFacet) {
			for (String value : facet.getSelections()) {
				queryBuilders.add(
					rangeQuery(fieldName, RangeParserUtil.parserRange(value)));
			}
		}
		else {
			queryBuilders.add(
				QueryBuilders.termsQuery(fieldName, facet.getSelections()));
		}

		return queryBuilders;
	}

	protected static Map<String, List<QueryBuilder>> getSelectionFiltersMap(
		Collection<Facet> facets) {

		Map<String, List<QueryBuilder>> map = new HashMap<>();

		for (Facet facet : facets) {
			if ((facet instanceof com.liferay.portal.search.facet.Facet) &&
				!facet.isStatic()) {

				com.liferay.portal.search.facet.Facet facet2 =
					(com.liferay.portal.search.facet.Facet)facet;

				if (!ArrayUtil.isEmpty(facet2.getSelections())) {
					map.put(
						facet2.getAggregationName(),
						getSelectionFilters(facet2));
				}
			}
		}

		return map;
	}

	protected static QueryBuilder rangeQuery(
		String fieldName, String[] ranges) {

		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(
			fieldName);

		rangeQueryBuilder.gte(ranges[0]);
		rangeQueryBuilder.lte(ranges[1]);

		return rangeQueryBuilder;
	}

	protected BoolQueryBuilder getSelectionFiltersOfOthersAsBoolQueryBuilder(
		String aggregationName) {

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		for (Map.Entry<String, List<QueryBuilder>> entry :
				_selectionFiltersMap.entrySet()) {

			String filterAggregationName = entry.getKey();

			if (!filterAggregationName.equals(aggregationName)) {
				List<QueryBuilder> queryBuilders = entry.getValue();

				for (QueryBuilder queryBuilder : queryBuilders) {
					boolQueryBuilder.must(queryBuilder);
				}
			}
		}

		return boolQueryBuilder;
	}

	protected AggregationBuilder getSuperAggregationBuilder(
		String aggregationName) {

		if (_selectionFiltersMap.isEmpty()) {
			return null;
		}

		BoolQueryBuilder boolQueryBuilder =
			getSelectionFiltersOfOthersAsBoolQueryBuilder(aggregationName);

		if (!boolQueryBuilder.hasClauses()) {
			return null;
		}

		return new FilterAggregationBuilder(aggregationName, boolQueryBuilder);
	}

	private AggregationFilteringFacetProcessorContext(
		Map<String, List<QueryBuilder>> selectionFiltersMap) {

		_selectionFiltersMap = selectionFiltersMap;
	}

	private final Map<String, List<QueryBuilder>> _selectionFiltersMap;

}