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

package com.liferay.portal.search.aggregation;

import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface Aggregation {

	public <T> T accept(AggregationVisitor<T> aggregationVisitor);

	public void addChildAggregation(Aggregation aggregation);

	public void addChildrenAggregations(Aggregation... aggregation);

	public void addPipelineAggregation(PipelineAggregation pipelineAggregation);

	public void addPipelineAggregations(
		PipelineAggregation... pipelineAggregations);

	public Aggregation getChildAggregation(String name);

	public Collection<Aggregation> getChildrenAggregations();

	public String getName();

	public PipelineAggregation getPipelineAggregation(String name);

	public Collection<PipelineAggregation> getPipelineAggregations();

	public void removeChildAggregation(Aggregation aggregation);

	public void removePipelineAggregation(
		PipelineAggregation pipelineAggregation);

}