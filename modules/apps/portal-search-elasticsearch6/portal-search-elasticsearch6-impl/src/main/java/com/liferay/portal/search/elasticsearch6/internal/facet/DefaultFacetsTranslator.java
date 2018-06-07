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

package com.liferay.portal.search.elasticsearch6.internal.facet;

import com.liferay.portal.kernel.search.facet.Facet;

import java.util.Collection;
import java.util.Optional;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = FacetsTranslator.class)
public class DefaultFacetsTranslator implements FacetsTranslator {

	@Override
	public void translate(
		SearchRequestBuilder searchRequestBuilder, Collection<Facet> facets,
		boolean basicFacetSelection) {

		FacetProcessorContext facetProcessorContext = getFacetProcessorContext(
			facets, basicFacetSelection);

		for (Facet facet : facets) {
			if (facet.isStatic()) {
				continue;
			}

			Optional<AggregationBuilder> optional = facetProcessor.processFacet(
				facet);

			optional.map(
				aggregationBuilder -> postProcessAggregationBuilder(
					aggregationBuilder, facetProcessorContext)
			).ifPresent(
				searchRequestBuilder::addAggregation
			);
		}
	}

	protected FacetProcessorContext getFacetProcessorContext(
		Collection<Facet> facets, boolean basicFacetSelection) {

		if (basicFacetSelection) {
			return null;
		}

		return AggregationFilteringFacetProcessorContext.newInstance(facets);
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

	@Reference(service = CompositeFacetProcessor.class)
	protected FacetProcessor<SearchRequestBuilder> facetProcessor;

}