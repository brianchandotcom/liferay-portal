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

package com.liferay.portal.search.elasticsearch.internal.facet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.range.Range.Bucket;

/**
 * @author André de Oliveira
 */
public class RangeFacetCollector implements FacetCollector {

	public RangeFacetCollector(Range range) {
		_fieldName = range.getName();
		_termCollectorHolder = getTermCollectorHolder(range);
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

	protected TermCollectorHolder getTermCollectorHolder(Range range) {
		List<? extends Bucket> buckets = range.getBuckets();

		TermCollectorHolder termCollectorHolder = new TermCollectorHolder(
			buckets.size());

		for (Bucket bucket : buckets) {
			String key = StringUtil.replace(
				bucket.getKeyAsString(), CharPool.DASH, _TO_STRING);

			key = StringPool.OPEN_BRACKET.concat(key).concat(
				StringPool.CLOSE_BRACKET);

			termCollectorHolder.add(key, (int)bucket.getDocCount());
		}

		return termCollectorHolder;
	}

	private static final String _TO_STRING = " TO ";

	private final String _fieldName;
	private final TermCollectorHolder _termCollectorHolder;

}