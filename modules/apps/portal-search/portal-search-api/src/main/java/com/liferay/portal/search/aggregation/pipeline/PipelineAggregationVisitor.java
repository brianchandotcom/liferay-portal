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

package com.liferay.portal.search.aggregation.pipeline;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface PipelineAggregationVisitor<T> {

	public T visit(AvgBucketPipelineAggregation avgBucketPipelineAggregation);

	public T visit(
		BucketScriptPipelineAggregation bucketScriptPipelineAggregation);

	public T visit(
		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation);

	public T visit(BucketSortPipelineAggregation bucketSortPipelineAggregation);

	public T visit(
		CumulativeSumPipelineAggregation cumulativeSumPipelineAggregation);

	public T visit(DerivativePipelineAggregation derivativePipelineAggregation);

	public T visit(
		ExtendedStatsBucketPipelineAggregation
			extendedStatsBucketPipelineAggregation);

	public T visit(MaxBucketPipelineAggregation maxBucketPipelineAggregation);

	public T visit(MinBucketPipelineAggregation minBucketPipelineAggregation);

	public T visit(
		MovingFunctionPipelineAggregation movingFunctionPipelineAggregation);

	public T visit(
		PercentilesBucketPipelineAggregation
			percentilesBucketPipelineAggregation);

	public T visit(SerialDiffPipelineAggregation serialDiffPipelineAggregation);

	public T visit(
		StatsBucketPipelineAggregation statsBucketPipelineAggregation);

	public T visit(SumBucketPipelineAggregation sumBucketPipelineAggregation);

}