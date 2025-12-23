/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.facet;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.DoubleTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.MultiBucketAggregateBase;
import co.elastic.clients.elasticsearch._types.aggregations.MultiBucketBase;
import co.elastic.clients.elasticsearch._types.aggregations.MultiTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class MultiBucketsAggregationFacetCollector implements FacetCollector {

	public MultiBucketsAggregationFacetCollector(
		String fieldName, MultiBucketAggregateBase multiBucketAggregateBase) {

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

		List<? extends MultiBucketBase> multiBucketBases = buckets.array();

		TermCollectorHolder termCollectorHolder = new TermCollectorHolder(
			multiBucketBases.size());

		for (MultiBucketBase multiBucketBase : multiBucketBases) {
			if (multiBucketBase instanceof DoubleTermsBucket) {
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
					key = String.valueOf(longTermsBucket.key());
				}

				termCollectorHolder.add(key, (int)longTermsBucket.docCount());
			}
			else if (multiBucketBase instanceof MultiTermsBucket) {
				MultiTermsBucket multiTermsBucket =
					(MultiTermsBucket)multiBucketBase;

				String key = multiTermsBucket.keyAsString();

				if (Validator.isBlank(key)) {
					List<FieldValue> fieldValues = multiTermsBucket.key();

					List<String> keys = new ArrayList<>();

					for (FieldValue fieldValue : fieldValues) {
						keys.add(fieldValue._toJsonString());
					}

					key = StringUtil.merge(keys, StringPool.PIPE);
				}

				termCollectorHolder.add(key, (int)multiTermsBucket.docCount());
			}
			else if (multiBucketBase instanceof StringTermsBucket) {
				StringTermsBucket stringTermsBucket =
					(StringTermsBucket)multiBucketBase;

				FieldValue fieldValue = stringTermsBucket.key();

				termCollectorHolder.add(
					fieldValue._toJsonString(),
					(int)stringTermsBucket.docCount());
			}
		}

		return termCollectorHolder;
	}

	private final String _fieldName;
	private final TermCollectorHolder _termCollectorHolder;

}