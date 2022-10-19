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

package com.liferay.portal.vulcan.aggregation;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.HierarchicalAggregationResult;
import com.liferay.portal.search.aggregation.bucket.BucketAggregationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class FacetUtil {

	public static Facet toFacet(
		BucketAggregationResult bucketAggregationResult) {

		return new Facet(
			bucketAggregationResult.getName(),
			TransformUtil.transform(
				bucketAggregationResult.getBuckets(),
				bucket -> new Facet.FacetValue(
					GetterUtil.getInteger(bucket.getDocCount()),
					bucket.getKey())));
	}

	public static Facet toFacet(
		com.liferay.portal.kernel.search.facet.Facet facet) {

		FacetCollector facetCollector = facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		if (termCollectors.isEmpty()) {
			return null;
		}

		return new Facet(
			_getFacetCriteria(facet),
			TransformUtil.transform(
				termCollectors,
				termCollector -> new Facet.FacetValue(
					termCollector.getFrequency(), termCollector.getTerm())));
	}

	public static List<Facet> toFacets(AggregationResult aggregationResult) {
		List<Facet> facets = new ArrayList<>();

		if (aggregationResult instanceof BucketAggregationResult) {
			facets.add(toFacet((BucketAggregationResult)aggregationResult));
		}
		else if (aggregationResult instanceof HierarchicalAggregationResult) {
			facets.addAll(
				toFacets((HierarchicalAggregationResult)aggregationResult));
		}

		return facets;
	}

	public static List<Facet> toFacets(
		HierarchicalAggregationResult hierarchicalAggregationResult) {

		List<Facet> facets = new ArrayList<>();

		Map<String, AggregationResult> aggregationResults =
			hierarchicalAggregationResult.getChildrenAggregationResultsMap();

		for (AggregationResult aggregationResult :
				aggregationResults.values()) {

			facets.addAll(toFacets(aggregationResult));
		}

		return facets;
	}

	private static String _getFacetCriteria(
		com.liferay.portal.kernel.search.facet.Facet facet) {

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		if (facetConfiguration.getLabel() != null) {
			return facetConfiguration.getLabel();
		}

		return facet.getFieldName();
	}

}