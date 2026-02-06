/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.facet.DateRangeFacet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.elasticsearch7.internal.filter.ElasticsearchFilterVisitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.IncludeExclude;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = FacetTranslator.class)
public class DefaultFacetTranslator implements FacetTranslator {

	@Override
	public void translate(
		SearchSourceBuilder searchSourceBuilder, Query query,
		Map<String, Facet> facetsMap, boolean basicFacetSelection) {

		if (MapUtil.isEmpty(facetsMap)) {
			return;
		}

		Collection<Facet> facets = facetsMap.values();

		FacetProcessorContext facetProcessorContext = _getFacetProcessorContext(
			facets, basicFacetSelection);

		List<QueryBuilder> postFilterQueryBuilders = new ArrayList<>();

		if ((query != null) && (query.getPostFilter() != null)) {
			Filter filter = query.getPostFilter();

			postFilterQueryBuilders.add(
				filter.accept(ElasticsearchFilterVisitor.INSTANCE));
		}

		for (Facet facet : facets) {
			if (facet.isStatic()) {
				continue;
			}

			BooleanClause<Filter> booleanClause =
				facet.getFacetFilterBooleanClause();

			if (booleanClause != null) {
				QueryBuilder postFilterQueryBuilder = _translateBooleanClause(
					booleanClause);

				if (postFilterQueryBuilder != null) {
					postFilterQueryBuilders.add(postFilterQueryBuilder);
				}
			}

			AggregationBuilder aggregationBuilder = _processFacet(facet);

			if (aggregationBuilder != null) {
				AggregationBuilder postProcessAggregationBuilder =
					postProcessAggregationBuilder(
						aggregationBuilder, facetProcessorContext);

				if (postProcessAggregationBuilder != null) {
					searchSourceBuilder.aggregation(
						postProcessAggregationBuilder);
				}
			}
		}

		if (ListUtil.isNotEmpty(postFilterQueryBuilders)) {
			searchSourceBuilder.postFilter(
				_getPostFilter(postFilterQueryBuilders));
		}
	}

	protected AggregationBuilder postProcessAggregationBuilder(
		AggregationBuilder aggregationBuilder,
		FacetProcessorContext facetProcessorContext) {

		if (facetProcessorContext != null) {
			return facetProcessorContext.postProcessAggregationBuilder(
				aggregationBuilder);
		}

		return aggregationBuilder;
	}

	private FacetProcessorContext _getFacetProcessorContext(
		Collection<Facet> facets, boolean basicFacetSelection) {

		if (basicFacetSelection) {
			return null;
		}

		return AggregationFilteringFacetProcessorContext.newInstance(facets);
	}

	private QueryBuilder _getPostFilter(List<QueryBuilder> queryBuilders) {
		if (queryBuilders.size() == 1) {
			return queryBuilders.get(0);
		}

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		for (QueryBuilder queryBuilder : queryBuilders) {
			boolQueryBuilder.must(queryBuilder);
		}

		return boolQueryBuilder;
	}

	private AggregationBuilder _processFacet(Facet facet) {
		Class<?> clazz = facet.getClass();

		FacetProcessor<SearchRequestBuilder> facetProcessor =
			_facetProcessors.getOrDefault(
				clazz.getName(), _defaultFacetProcessor);

		return facetProcessor.processFacet(facet);
	}

	private QueryBuilder _translateBooleanClause(
		BooleanClause<Filter> booleanClause) {

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(
			booleanClause.getClause(), booleanClause.getBooleanClauseOccur());

		return booleanFilter.accept(ElasticsearchFilterVisitor.INSTANCE);
	}

	private static final FacetProcessor<SearchRequestBuilder>
		_defaultFacetProcessor = new FacetProcessor<SearchRequestBuilder>() {

			@Override
			public AggregationBuilder processFacet(Facet facet) {
				TermsAggregationBuilder termsAggregationBuilder =
					AggregationBuilders.terms(
						FacetUtil.getAggregationName(facet));

				termsAggregationBuilder.field(facet.getFieldName());

				FacetConfiguration facetConfiguration =
					facet.getFacetConfiguration();

				JSONObject dataJSONObject = facetConfiguration.getData();

				String include = dataJSONObject.getString("include", null);

				if (include != null) {
					termsAggregationBuilder.includeExclude(
						new IncludeExclude(include, null));
				}

				int minDocCount = dataJSONObject.getInt(
					"frequencyThreshold", -1);

				if (minDocCount >= 0) {
					termsAggregationBuilder.minDocCount(minDocCount);
				}

				termsAggregationBuilder.order(BucketOrder.count(false));

				int size = dataJSONObject.getInt("maxTerms");

				if (size > 0) {
					termsAggregationBuilder.size(size);
				}

				return termsAggregationBuilder;
			}

		};

	private static final Map<String, FacetProcessor<SearchRequestBuilder>>
		_facetProcessors =
			HashMapBuilder.<String, FacetProcessor<SearchRequestBuilder>>put(
				DateRangeFacet.class.getName(), DateRangeFacetProcessor.INSTANCE
			).put(
				"com.liferay.portal.search.internal.facet.DateRangeFacetImpl",
				DateRangeFacetProcessor.INSTANCE
			).put(
				"com.liferay.portal.search.internal.facet.ModifiedFacetImpl",
				RangeFacetProcessor.INSTANCE
			).put(
				"com.liferay.portal.search.internal.facet.NestedFacetImpl",
				NestedFacetProcessor.INSTANCE
			).put(
				RangeFacet.class.getName(), RangeFacetProcessor.INSTANCE
			).put(
				"com.liferay.portal.search.internal.facet.RangeFacetImpl",
				RangeFacetProcessor.INSTANCE
			).build();

}