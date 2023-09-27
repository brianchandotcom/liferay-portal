/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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