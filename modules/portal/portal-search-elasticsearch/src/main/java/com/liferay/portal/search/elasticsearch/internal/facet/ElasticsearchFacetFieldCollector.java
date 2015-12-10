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

import com.liferay.portal.kernel.search.facet.collector.DefaultTermCollector;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.range.Range;

/**
 * @author Michael C. Han
 * @author Milen Dyankov
 */
public class ElasticsearchFacetFieldCollector implements FacetCollector {

	public ElasticsearchFacetFieldCollector(Aggregation aggregation) {
		if (aggregation instanceof Range) {
			initialize((Range)aggregation);
		}
		else if (aggregation instanceof MultiBucketsAggregation) {
			initialize((MultiBucketsAggregation)aggregation);
		}
	}

	@Override
	public String getFieldName() {
		return _aggregation.getName();
	}

	@Override
	public TermCollector getTermCollector(String term) {
		int count = 0;

		if (_counts.containsKey(term)) {
			count = _counts.get(term);
		}

		return new DefaultTermCollector(term, count);
	}

	@Override
	public List<TermCollector> getTermCollectors() {
		if (_termCollectors != null) {
			return _termCollectors;
		}

		List<TermCollector> termCollectors = new ArrayList<>();

		for (Map.Entry<String, Integer> entry : _counts.entrySet()) {
			TermCollector termCollector = new DefaultTermCollector(
				entry.getKey(), entry.getValue());

			termCollectors.add(termCollector);
		}

		_termCollectors = termCollectors;

		return _termCollectors;
	}

	protected void initialize(Range range) {
		_aggregation = range;

		for (Range.Bucket bucket : range.getBuckets()) {
			String key = StringUtil.replace(
				bucket.getKeyAsString(), StringPool.DASH, _TO_STRING);

			key = StringPool.OPEN_BRACKET.concat(key).concat(
				StringPool.CLOSE_BRACKET);

			_counts.put(key, (int)bucket.getDocCount());
		}
	}

	private void initialize(MultiBucketsAggregation multiBucketsAggregation) {
		_aggregation = multiBucketsAggregation;

		for (MultiBucketsAggregation.Bucket bucket :
				multiBucketsAggregation.getBuckets()) {

			_counts.put(bucket.getKeyAsString(), (int)bucket.getDocCount());
		}
	}

	private static final String _TO_STRING = " TO ";

	private Aggregation _aggregation;
	private final Map<String, Integer> _counts = new ConcurrentHashMap<>();
	private List<TermCollector> _termCollectors;

}