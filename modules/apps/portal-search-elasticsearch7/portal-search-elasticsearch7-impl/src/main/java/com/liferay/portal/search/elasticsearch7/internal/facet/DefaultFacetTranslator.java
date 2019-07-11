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

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.FilterTranslator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = FacetTranslator.class)
public class DefaultFacetTranslator implements FacetTranslator {

	@Override
	public void translate(
		SearchRequestBuilder searchRequestBuilder, Query query,
		Map<String, Facet> facetsMap, boolean basicFacetSelection) {

		if (MapUtil.isEmpty(facetsMap)) {
			return;
		}

		Collection<Facet> facets = facetsMap.values();

		FacetProcessorContext facetProcessorContext = getFacetProcessorContext(
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

			Optional<QueryBuilder> postFilterQueryBuilderOptional =
				translateFacetQuery(facet);

			postFilterQueryBuilderOptional.ifPresent(
				postFilterQueryBuilders::add);

			Optional<AggregationBuilder> optional =
				_facetProcessor.processFacet(facet);

			optional.map(
				aggregationBuilder -> postProcessAggregationBuilder(
					aggregationBuilder, facetProcessorContext)
			).ifPresent(
				searchRequestBuilder::addAggregation
			);
		}

		if (!ListUtil.isEmpty(postFilterQueryBuilders)) {
			searchRequestBuilder.setPostFilter(
				getPostFilter(postFilterQueryBuilders));
		}
	}

	protected FacetProcessorContext getFacetProcessorContext(
		Collection<Facet> facets, boolean basicFacetSelection) {

		if (basicFacetSelection) {
			return null;
		}

		return AggregationFilteringFacetProcessorContext.newInstance(facets);
	}

	protected QueryBuilder getPostFilter(List<QueryBuilder> queryBuilders) {
		if (queryBuilders.size() == 1) {
			return queryBuilders.get(0);
		}

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		for (QueryBuilder queryBuilder : queryBuilders) {
			boolQueryBuilder.must(queryBuilder);
		}

		return boolQueryBuilder;
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

	@Reference(service = CompositeFacetProcessor.class, unbind = "-")
	protected void setFacetProcessor(
		FacetProcessor<SearchRequestBuilder> facetProcessor) {

		_facetProcessor = facetProcessor;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setFilterTranslator(
		FilterTranslator<QueryBuilder> filterTranslator) {

		_filterTranslator = filterTranslator;
	}

	protected QueryBuilder translateBooleanClause(
		BooleanClause<Filter> booleanClause) {

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(
			booleanClause.getClause(), booleanClause.getBooleanClauseOccur());

		return _filterTranslator.translate(booleanFilter, null);
	}

	protected Optional<QueryBuilder> translateFacetQuery(Facet facet) {
		BooleanClause<Filter> booleanClause =
			facet.getFacetFilterBooleanClause();

		if (booleanClause == null) {
			return Optional.empty();
		}

		QueryBuilder queryBuilder = translateBooleanClause(booleanClause);

		return Optional.of(queryBuilder);
	}

	private FacetProcessor<SearchRequestBuilder> _facetProcessor;
	private FilterTranslator<QueryBuilder> _filterTranslator;

}