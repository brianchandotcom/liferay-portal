/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.search.facet.collector.FacetCollector;

import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.SingleBucketAggregation;
import org.elasticsearch.search.aggregations.bucket.range.Range;

/**
 * @author André de Oliveira
 */
public class FacetCollectorFactory {

	public FacetCollector getFacetCollector(Aggregation aggregation) {
		if (aggregation instanceof SingleBucketAggregation) {
			SingleBucketAggregation singleBucketAggregation =
				(SingleBucketAggregation)aggregation;

			Aggregations aggregations =
				singleBucketAggregation.getAggregations();

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