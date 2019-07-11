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

import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;

import java.util.List;

import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;

/**
 * @author André de Oliveira
 */
public class MultiBucketsAggregationFacetCollector implements FacetCollector {

	public MultiBucketsAggregationFacetCollector(
		MultiBucketsAggregation multiBucketsAggregation) {

		_fieldName = multiBucketsAggregation.getName();
		_termCollectorHolder = getTermCollectorHolder(multiBucketsAggregation);
	}

	@Override
	public String getFieldName() {
		return _fieldName;
	}

	@Override
	public TermCollector getTermCollector(String term) {
		return _termCollectorHolder.getTermCollector(term);
	}

	@Override
	public List<TermCollector> getTermCollectors() {
		return _termCollectorHolder.getTermCollectors();
	}

	protected TermCollectorHolder getTermCollectorHolder(
		MultiBucketsAggregation multiBucketsAggregation) {

		List<? extends MultiBucketsAggregation.Bucket> buckets =
			multiBucketsAggregation.getBuckets();

		TermCollectorHolder termCollectorHolder = new TermCollectorHolder(
			buckets.size());

		for (MultiBucketsAggregation.Bucket bucket : buckets) {
			termCollectorHolder.add(
				bucket.getKeyAsString(), (int)bucket.getDocCount());
		}

		return termCollectorHolder;
	}

	private final String _fieldName;
	private final TermCollectorHolder _termCollectorHolder;

}