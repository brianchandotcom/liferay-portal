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