/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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