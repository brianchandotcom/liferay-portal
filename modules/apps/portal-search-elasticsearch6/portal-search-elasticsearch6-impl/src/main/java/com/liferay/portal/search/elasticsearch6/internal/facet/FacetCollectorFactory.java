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

import com.liferay.portal.kernel.search.facet.collector.FacetCollector;

import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilter;
import org.elasticsearch.search.aggregations.bucket.range.Range;

/**
 * @author André de Oliveira
 */
public class FacetCollectorFactory {

	public FacetCollector getFacetCollector(Aggregation aggregation) {
		if (aggregation instanceof InternalFilter) {
			InternalFilter internalFilter = (InternalFilter)aggregation;

			Aggregations aggregations = internalFilter.getAggregations();

			return getFacetCollector(aggregations.get(aggregation.getName()));
		}

		if (aggregation instanceof Range) {
			return new RangeFacetCollector((Range)aggregation);
		}

		if (aggregation instanceof MultiBucketsAggregation) {
			return new MultiBucketsAggregationFacetCollector(
				(MultiBucketsAggregation)aggregation);
		}

		return new ElasticsearchFacetFieldCollector(aggregation);
	}

}