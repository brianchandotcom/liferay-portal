/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.facet;

import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;

import java.util.List;

import org.opensearch.client.opensearch._types.aggregations.Buckets;
import org.opensearch.client.opensearch._types.aggregations.RangeBucket;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class RangeFacetCollector implements FacetCollector {

	public RangeFacetCollector(String fieldName, Buckets<RangeBucket> buckets) {
		this.fieldName = fieldName;

		termCollectorHolder = getTermCollectorHolder(buckets);
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	@Override
	public TermCollector getTermCollector(String term) {
		return termCollectorHolder.getTermCollector(term);
	}

	@Override
	public List<TermCollector> getTermCollectors() {
		return termCollectorHolder.getTermCollectors();
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

	protected final String fieldName;
	protected final TermCollectorHolder termCollectorHolder;

}