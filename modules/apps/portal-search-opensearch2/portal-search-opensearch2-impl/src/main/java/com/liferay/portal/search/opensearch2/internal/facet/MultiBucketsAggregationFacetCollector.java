/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.facet;

import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.opensearch.client.opensearch._types.aggregations.Buckets;
import org.opensearch.client.opensearch._types.aggregations.DoubleTermsBucket;
import org.opensearch.client.opensearch._types.aggregations.LongTermsBucket;
import org.opensearch.client.opensearch._types.aggregations.MultiBucketAggregateBase;
import org.opensearch.client.opensearch._types.aggregations.MultiBucketBase;
import org.opensearch.client.opensearch._types.aggregations.MultiTermsBucket;
import org.opensearch.client.opensearch._types.aggregations.StringTermsBucket;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class MultiBucketsAggregationFacetCollector implements FacetCollector {

	public MultiBucketsAggregationFacetCollector(
		MultiBucketAggregateBase multiBucketAggregateBase, String fieldName) {

		_fieldName = fieldName;

		_termCollectorHolder = getTermCollectorHolder(multiBucketAggregateBase);
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
		MultiBucketAggregateBase multiBucketAggregateBase) {

		Buckets<? extends MultiBucketBase> buckets =
			multiBucketAggregateBase.buckets();

		List<? extends MultiBucketBase> bucketsList = buckets.array();

		TermCollectorHolder termCollectorHolder = new TermCollectorHolder(
			bucketsList.size());

		for (MultiBucketBase multiBucketBase : bucketsList) {
			if (multiBucketBase instanceof StringTermsBucket) {
				StringTermsBucket stringTermsBucket =
					(StringTermsBucket)multiBucketBase;

				termCollectorHolder.add(
					stringTermsBucket.key(), (int)stringTermsBucket.docCount());
			}
			else if (multiBucketBase instanceof DoubleTermsBucket) {
				DoubleTermsBucket doubleTermsBucket =
					(DoubleTermsBucket)multiBucketBase;

				String key = doubleTermsBucket.keyAsString();

				if (Validator.isBlank(key)) {
					key = String.valueOf(doubleTermsBucket.key());
				}

				termCollectorHolder.add(key, (int)doubleTermsBucket.docCount());
			}
			else if (multiBucketBase instanceof LongTermsBucket) {
				LongTermsBucket longTermsBucket =
					(LongTermsBucket)multiBucketBase;

				String key = longTermsBucket.keyAsString();

				if (Validator.isBlank(key)) {
					key = longTermsBucket.key();
				}

				termCollectorHolder.add(key, (int)longTermsBucket.docCount());
			}
			else if (multiBucketBase instanceof MultiTermsBucket) {
				MultiTermsBucket multiTermsBucket =
					(MultiTermsBucket)multiBucketBase;

				termCollectorHolder.add(
					multiTermsBucket.keyAsString(),
					(int)multiTermsBucket.docCount());
			}
		}

		return termCollectorHolder;
	}

	private final String _fieldName;
	private final TermCollectorHolder _termCollectorHolder;

}