/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.bucket;

import com.liferay.portal.search.aggregation.AggregationResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class Bucket {

	public Bucket(String key, long docCount) {
		_key = key;
		_docCount = docCount;
	}

	public void addChildAggregationResult(AggregationResult aggregationResult) {
		_childrenAggregationResults.put(
			aggregationResult.getName(), aggregationResult);
	}

	public void addChildrenAggregationResults(
		List<AggregationResult> aggregationResults) {

		aggregationResults.forEach(this::addChildAggregationResult);
	}

	public AggregationResult getChildAggregationResult(String name) {
		return _childrenAggregationResults.get(name);
	}

	public Map<String, AggregationResult> getChildrenAggregationResults() {
		return Collections.unmodifiableMap(_childrenAggregationResults);
	}

	public long getDocCount() {
		return _docCount;
	}

	public String getKey() {
		return _key;
	}

	@Override
	public String toString() {

		// Purposely same string representation as java.util.Map.Entry

		return _key + "=" + _docCount;
	}

	private final Map<String, AggregationResult> _childrenAggregationResults =
		new HashMap<>();
	private final long _docCount;
	private final String _key;

}