/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.facet;

import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.RangeBucket;

import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;

import java.util.List;

/**
 * @author André de Oliveira
 */
public class RangeFacetCollector implements FacetCollector {

	public RangeFacetCollector(Buckets<RangeBucket> buckets, String fieldName) {
		_fieldName = fieldName;

		_termCollectorHolder = getTermCollectorHolder(buckets);
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
		Buckets<RangeBucket> buckets) {

		List<RangeBucket> rangeBuckets = buckets.array();

		TermCollectorHolder termCollectorHolder = new TermCollectorHolder(
			rangeBuckets.size());

		for (RangeBucket rangeBucket : rangeBuckets) {
			termCollectorHolder.add(
				rangeBucket.key(), (int)rangeBucket.docCount());
		}

		return termCollectorHolder;
	}

	private final String _fieldName;
	private final TermCollectorHolder _termCollectorHolder;

}