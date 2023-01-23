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

package com.liferay.portal.search.internal.aggregation;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public abstract class BaseAggregation implements Aggregation {

	public BaseAggregation(String name) {
		_name = name;
	}

	@Override
	public void addChildAggregation(Aggregation aggregation) {
		_childrenAggregations.put(aggregation.getName(), aggregation);
	}

	@Override
	public void addChildrenAggregations(Aggregation... aggregations) {
		for (Aggregation aggregation : aggregations) {
			addChildAggregation(aggregation);
		}
	}

	@Override
	public void addPipelineAggregation(
		PipelineAggregation pipelineAggregation) {

		_pipelineAggregations.put(
			pipelineAggregation.getName(), pipelineAggregation);
	}

	@Override
	public void addPipelineAggregations(
		PipelineAggregation... pipelineAggregations) {

		for (PipelineAggregation pipelineAggregation : pipelineAggregations) {
			addPipelineAggregation(pipelineAggregation);
		}
	}

	@Override
	public Aggregation getChildAggregation(String name) {
		return _childrenAggregations.get(name);
	}

	@Override
	public Collection<Aggregation> getChildrenAggregations() {
		return Collections.unmodifiableCollection(
			_childrenAggregations.values());
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public PipelineAggregation getPipelineAggregation(String name) {
		return _pipelineAggregations.get(name);
	}

	@Override
	public Collection<PipelineAggregation> getPipelineAggregations() {
		return Collections.unmodifiableCollection(
			_pipelineAggregations.values());
	}

	@Override
	public void removeChildAggregation(Aggregation aggregation) {
		_childrenAggregations.remove(aggregation.getName());
	}

	@Override
	public void removePipelineAggregation(
		PipelineAggregation pipelineAggregation) {

		_pipelineAggregations.remove(pipelineAggregation.getName());
	}

	private final Map<String, Aggregation> _childrenAggregations =
		new LinkedHashMap<>();
	private final String _name;
	private final Map<String, PipelineAggregation> _pipelineAggregations =
		new LinkedHashMap<>();

}