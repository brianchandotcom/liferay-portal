/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.HierarchicalAggregationResult;
import com.liferay.portal.search.internal.aggregation.BaseAggregationResult;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class BaseHierarchicalAggregationResult
	extends BaseAggregationResult implements HierarchicalAggregationResult {

	public BaseHierarchicalAggregationResult(String name) {
		super(name);
	}

	public void addChildAggregationResultImpl(
		AggregationResult aggregationResult) {

		_childrenAggregationResultsMap.put(
			aggregationResult.getName(), aggregationResult);
	}

	@Override
	public void addChildrenAggregationResults(
		List<AggregationResult> aggregationResults) {

		aggregationResults.forEach(
			aggregationResult -> _childrenAggregationResultsMap.put(
				aggregationResult.getName(), aggregationResult));
	}

	@Override
	public AggregationResult getChildAggregationResult(String name) {
		return _childrenAggregationResultsMap.get(name);
	}

	@Override
	public Map<String, AggregationResult> getChildrenAggregationResultsMap() {
		return Collections.unmodifiableMap(_childrenAggregationResultsMap);
	}

	private final Map<String, AggregationResult>
		_childrenAggregationResultsMap = new LinkedHashMap<>();

}