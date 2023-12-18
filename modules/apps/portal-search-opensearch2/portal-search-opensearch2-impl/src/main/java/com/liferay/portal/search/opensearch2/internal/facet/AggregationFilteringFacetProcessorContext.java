/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.facet;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.facet.nested.NestedFacet;
import com.liferay.portal.search.opensearch2.internal.util.ConversionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch._types.aggregations.Aggregation.Builder;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.ChildScoreMode;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.QueryBuilders;
import org.opensearch.client.opensearch._types.query_dsl.RangeQuery;
import org.opensearch.client.opensearch._types.query_dsl.TermsQueryField;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class AggregationFilteringFacetProcessorContext
	implements FacetProcessorContext {

	public static FacetProcessorContext newInstance(Collection<Facet> facets) {
		return new AggregationFilteringFacetProcessorContext(
			_getSelectionFiltersMap(facets));
	}

	@Override
	public Builder.ContainerBuilder postProcessAggregationBuilder(
		Builder.ContainerBuilder containerBuilder, String facetName) {

		Builder.ContainerBuilder filterContainerBuilder =
			_getFilterContainerBuilder(facetName);

		if (filterContainerBuilder != null) {
			return filterContainerBuilder.aggregations(
				facetName, containerBuilder.build());
		}

		return containerBuilder;
	}

	private static void _addNestedFacetChildAggregationFilters(
		BoolQuery.Builder boolQueryBuilder, String fieldName,
		NestedFacet nestedFacet) {

		if (nestedFacet.getChildAggregation() instanceof DateRangeAggregation) {
			for (String value : nestedFacet.getSelections()) {
				DateRangeAggregation dateRangeAggregation =
					(DateRangeAggregation)nestedFacet.getChildAggregation();

				boolQueryBuilder.must(
					_rangeQuery(
						fieldName, dateRangeAggregation.getFormat(),
						RangeParserUtil.parserRange(value)));
			}
		}
		else {
			Aggregation childAggregation = nestedFacet.getChildAggregation();

			Class<?> clazz = childAggregation.getClass();

			throw new UnsupportedOperationException(
				"Nested facet does not support child aggregation " +
					clazz.getName());
		}
	}

	private static List<Query> _getSelectionFilters(
		com.liferay.portal.search.facet.Facet facet) {

		List<Query> queries = new ArrayList<>();

		String fieldName = facet.getFieldName();

		if (facet instanceof NestedFacet) {
			NestedFacet nestedFacet = (NestedFacet)facet;

			BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

			if (Validator.isNotNull(nestedFacet.getFilterField())) {
				boolQueryBuilder.must(
					new Query(
						QueryBuilders.terms(
						).field(
							nestedFacet.getFilterField()
						).terms(
							TermsQueryField.of(
								termsQueryField -> termsQueryField.value(
									ConversionUtil.toFieldValues(
										nestedFacet.getFilterValue())))
						).build()));
			}

			if (nestedFacet.getChildAggregation() != null) {
				_addNestedFacetChildAggregationFilters(
					boolQueryBuilder, fieldName, nestedFacet);
			}
			else {
				boolQueryBuilder.must(
					new Query(
						QueryBuilders.terms(
						).field(
							fieldName
						).terms(
							TermsQueryField.of(
								termsQueryField -> termsQueryField.value(
									ConversionUtil.toFieldValues(
										facet.getSelections())))
						).build()));
			}

			queries.add(
				new Query(
					QueryBuilders.nested(
					).path(
						nestedFacet.getPath()
					).query(
						new Query(boolQueryBuilder.build())
					).scoreMode(
						ChildScoreMode.Sum
					).build()));
		}
		else if (facet instanceof RangeFacet) {
			for (String value : facet.getSelections()) {
				queries.add(
					_rangeQuery(
						fieldName, null, RangeParserUtil.parserRange(value)));
			}
		}
		else {
			queries.add(
				new Query(
					QueryBuilders.terms(
					).field(
						fieldName
					).terms(
						TermsQueryField.of(
							termsQueryField -> termsQueryField.value(
								ConversionUtil.toFieldValues(
									facet.getSelections())))
					).build()));
		}

		return queries;
	}

	private static Map<String, List<Query>> _getSelectionFiltersMap(
		Collection<Facet> facets) {

		Map<String, List<Query>> map = new HashMap<>();

		for (Facet facet : facets) {
			if ((facet instanceof com.liferay.portal.search.facet.Facet) &&
				!facet.isStatic()) {

				com.liferay.portal.search.facet.Facet osgiFacet =
					(com.liferay.portal.search.facet.Facet)facet;

				if (!ArrayUtil.isEmpty(osgiFacet.getSelections())) {
					map.put(
						osgiFacet.getAggregationName(),
						_getSelectionFilters(osgiFacet));
				}
			}
		}

		return map;
	}

	private static Query _rangeQuery(
		String fieldName, String format, String[] rangeParts) {

		RangeQuery.Builder rangeQueryBuilder = new RangeQuery.Builder();

		rangeQueryBuilder.field(fieldName);
		rangeQueryBuilder.gte(JsonData.of(rangeParts[0]));
		rangeQueryBuilder.lte(JsonData.of(rangeParts[1]));

		if (!Validator.isBlank(format)) {
			rangeQueryBuilder.format(format);
		}

		return new Query(rangeQueryBuilder.build());
	}

	private AggregationFilteringFacetProcessorContext(
		Map<String, List<Query>> selectionFiltersMap) {

		_selectionFiltersMap = selectionFiltersMap;
	}

	private Builder.ContainerBuilder _getFilterContainerBuilder(
		String aggregationName) {

		if (_selectionFiltersMap.isEmpty()) {
			return null;
		}

		BoolQuery boolQuery = _getSelectionFiltersOfOthersAsBoolQuery(
			aggregationName);

		if (ListUtil.isEmpty(boolQuery.must()) &&
			ListUtil.isEmpty(boolQuery.mustNot()) &&
			ListUtil.isEmpty(boolQuery.should())) {

			return null;
		}

		Builder aggregationBuilder = new Builder();

		return aggregationBuilder.filter(new Query(boolQuery));
	}

	private BoolQuery _getSelectionFiltersOfOthersAsBoolQuery(
		String aggregationName) {

		BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

		for (Map.Entry<String, List<Query>> entry :
				_selectionFiltersMap.entrySet()) {

			String filterAggregationName = entry.getKey();

			if (filterAggregationName.equals(aggregationName)) {
				continue;
			}

			List<Query> queries = entry.getValue();

			if (queries.size() == 1) {
				boolQueryBuilder.must(queries.get(0));
			}
			else if (queries.size() > 1) {
				BoolQuery.Builder queryBuildersBoolQueryBuilder =
					QueryBuilders.bool();

				for (Query query : queries) {
					queryBuildersBoolQueryBuilder.should(query);
				}

				boolQueryBuilder.must(
					new Query(queryBuildersBoolQueryBuilder.build()));
			}
		}

		return boolQueryBuilder.build();
	}

	private final Map<String, List<Query>> _selectionFiltersMap;

}