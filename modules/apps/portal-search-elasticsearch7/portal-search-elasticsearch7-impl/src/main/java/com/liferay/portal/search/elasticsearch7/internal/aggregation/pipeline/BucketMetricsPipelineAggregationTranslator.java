/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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