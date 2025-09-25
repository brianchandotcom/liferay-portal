/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.FilterTranslator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

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
			postFilterQueryBuilders.add(
				_filterTranslator.translate(query.getPostFilter(), null));
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

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FacetProcessor.class,
			"(&(class.name=*)(!(class.name=DEFAULT)))",
			(serviceReference, emitter) -> {
				List<String> classNames = StringUtil.asList(
					serviceReference.getProperty("class.name"));

				for (String className : classNames) {
					emitter.emit(className);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
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
			_serviceTrackerMap.getService(clazz.getName());

		if (facetProcessor == null) {
			facetProcessor = _defaultFacetProcessor;
		}

		return facetProcessor.processFacet(facet);
	}

	private QueryBuilder _translateBooleanClause(
		BooleanClause<Filter> booleanClause) {

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(
			booleanClause.getClause(), booleanClause.getBooleanClauseOccur());

		return _filterTranslator.translate(booleanFilter, null);
	}

	private final FacetProcessor<SearchRequestBuilder> _defaultFacetProcessor =
		new FacetProcessor<SearchRequestBuilder>() {

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

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private FilterTranslator<QueryBuilder> _filterTranslator;

	@SuppressWarnings("rawtypes")
	private ServiceTrackerMap<String, FacetProcessor> _serviceTrackerMap;

}