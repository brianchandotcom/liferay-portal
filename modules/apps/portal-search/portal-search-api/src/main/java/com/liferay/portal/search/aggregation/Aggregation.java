/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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