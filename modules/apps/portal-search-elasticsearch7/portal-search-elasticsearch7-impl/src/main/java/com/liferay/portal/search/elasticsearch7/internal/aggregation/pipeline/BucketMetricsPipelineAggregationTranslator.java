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

package com.liferay.portal.search.elasticsearch7.internal.aggregation.pipeline;

import com.liferay.portal.search.aggregation.pipeline.BucketMetricsPipelineAggregation;

import org.elasticsearch.search.aggregations.pipeline.BucketMetricsPipelineAggregationBuilder;

/**
 * @author Michael C. Han
 */
public class BucketMetricsPipelineAggregationTranslator {

	public <T extends BucketMetricsPipelineAggregationBuilder<T>> T translate(
		BucketMetricsPipelineAggregationBuilderFactory<T>
			bucketMetricsPipelineAggregationBuilderFactory,
		BucketMetricsPipelineAggregation bucketMetricsPipelineAggregation) {

		T bucketMetricsPipelineAggregationBuilder =
			bucketMetricsPipelineAggregationBuilderFactory.create(
				bucketMetricsPipelineAggregation);

		if (bucketMetricsPipelineAggregation.getFormat() != null) {
			bucketMetricsPipelineAggregationBuilder.format(
				bucketMetricsPipelineAggregation.getFormat());
		}

		if (bucketMetricsPipelineAggregation.getGapPolicy() != null) {
			bucketMetricsPipelineAggregationBuilder.gapPolicy(
				_gapPolicyTranslator.translate(
					bucketMetricsPipelineAggregation.getGapPolicy()));
		}

		return bucketMetricsPipelineAggregationBuilder;
	}

	public interface BucketMetricsPipelineAggregationBuilderFactory
		<T extends BucketMetricsPipelineAggregationBuilder<T>> {

		public T create(
			BucketMetricsPipelineAggregation bucketMetricsPipelineAggregation);

	}

	private final GapPolicyTranslator _gapPolicyTranslator =
		new GapPolicyTranslator();

}