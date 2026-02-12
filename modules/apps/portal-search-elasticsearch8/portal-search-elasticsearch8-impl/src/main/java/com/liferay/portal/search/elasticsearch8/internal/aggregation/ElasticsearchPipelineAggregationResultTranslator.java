/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.aggregation;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.BucketMetricValueAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.DerivativeAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.ExtendedStatsBucketAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Percentiles;
import co.elastic.clients.elasticsearch._types.aggregations.PercentilesBucketAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.SimpleValueAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StatsBucketAggregate;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.AggregationResults;
import com.liferay.portal.search.aggregation.pipeline.AvgBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.AvgBucketPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketScriptPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketScriptPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketSelectorPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketSortPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.CumulativeSumPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.CumulativeSumPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.DerivativePipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.DerivativePipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.ExtendedStatsBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.ExtendedStatsBucketPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.MaxBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.MaxBucketPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.MinBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.MinBucketPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.MovingFunctionPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.MovingFunctionPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.PercentilesBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.PercentilesBucketPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationResultTranslator;
import com.liferay.portal.search.aggregation.pipeline.SerialDiffPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.SerialDiffPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.StatsBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.StatsBucketPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.SumBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.SumBucketPipelineAggregationResult;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class ElasticsearchPipelineAggregationResultTranslator
	implements PipelineAggregationResultTranslator {

	public ElasticsearchPipelineAggregationResultTranslator(
		Aggregate aggregate) {

		_aggregate = aggregate;
	}

	@Override
	public AvgBucketPipelineAggregationResult visit(
		AvgBucketPipelineAggregation avgBucketPipelineAggregation) {

		SimpleValueAggregate simpleValueAggregate = _aggregate.simpleValue();

		return AggregationResults.INSTANCE.avgBucket(
			avgBucketPipelineAggregation.getName(),
			simpleValueAggregate.value());
	}

	@Override
	public BucketScriptPipelineAggregationResult visit(
		BucketScriptPipelineAggregation bucketScriptPipelineAggregation) {

		SimpleValueAggregate simpleValueAggregate = _aggregate.simpleValue();

		return AggregationResults.INSTANCE.bucketScript(
			bucketScriptPipelineAggregation.getName(),
			simpleValueAggregate.value());
	}

	@Override
	public AggregationResult visit(
		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation) {

		throw new UnsupportedOperationException();
	}

	@Override
	public AggregationResult visit(
		BucketSortPipelineAggregation bucketSortPipelineAggregation) {

		throw new UnsupportedOperationException();
	}

	@Override
	public CumulativeSumPipelineAggregationResult visit(
		CumulativeSumPipelineAggregation cumulativeSumPipelineAggregation) {

		SimpleValueAggregate simpleValueAggregate = _aggregate.simpleValue();

		return AggregationResults.INSTANCE.cumulativeSum(
			cumulativeSumPipelineAggregation.getName(),
			simpleValueAggregate.value());
	}

	@Override
	public DerivativePipelineAggregationResult visit(
		DerivativePipelineAggregation derivativePipelineAggregation) {

		DerivativeAggregate derivativeAggregate = _aggregate.derivative();

		if (derivativePipelineAggregation.getUnit() != null) {
			return AggregationResults.INSTANCE.derivative(
				derivativePipelineAggregation.getName(),
				derivativeAggregate.normalizedValue());
		}

		return AggregationResults.INSTANCE.derivative(
			derivativePipelineAggregation.getName(),
			derivativeAggregate.value());
	}

	@Override
	public ExtendedStatsBucketPipelineAggregationResult visit(
		ExtendedStatsBucketPipelineAggregation
			extendedStatsBucketPipelineAggregation) {

		ExtendedStatsBucketAggregate extendedStatsBucketAggregate =
			_aggregate.extendedStatsBucket();

		return AggregationResults.INSTANCE.extendedStatsBucket(
			extendedStatsBucketPipelineAggregation.getName(),
			extendedStatsBucketAggregate.avg(),
			extendedStatsBucketAggregate.count(),
			extendedStatsBucketAggregate.min(),
			extendedStatsBucketAggregate.max(),
			extendedStatsBucketAggregate.sum(),
			extendedStatsBucketAggregate.sumOfSquares(),
			extendedStatsBucketAggregate.variance(),
			extendedStatsBucketAggregate.stdDeviation());
	}

	@Override
	public MaxBucketPipelineAggregationResult visit(
		MaxBucketPipelineAggregation maxBucketPipelineAggregation) {

		BucketMetricValueAggregate bucketMetricValueAggregate =
			_aggregate.bucketMetricValue();

		MaxBucketPipelineAggregationResult maxBucketPipelineAggregationResult =
			AggregationResults.INSTANCE.maxBucket(
				maxBucketPipelineAggregation.getName(),
				bucketMetricValueAggregate.value());

		List<String> keys = bucketMetricValueAggregate.keys();

		maxBucketPipelineAggregationResult.setKeys(keys.toArray(new String[0]));

		return maxBucketPipelineAggregationResult;
	}

	@Override
	public MinBucketPipelineAggregationResult visit(
		MinBucketPipelineAggregation minBucketPipelineAggregation) {

		BucketMetricValueAggregate bucketMetricValueAggregate =
			_aggregate.bucketMetricValue();

		MinBucketPipelineAggregationResult minBucketPipelineAggregationResult =
			AggregationResults.INSTANCE.minBucket(
				minBucketPipelineAggregation.getName(),
				bucketMetricValueAggregate.value());

		List<String> keys = bucketMetricValueAggregate.keys();

		minBucketPipelineAggregationResult.setKeys(keys.toArray(new String[0]));

		return minBucketPipelineAggregationResult;
	}

	@Override
	public MovingFunctionPipelineAggregationResult visit(
		MovingFunctionPipelineAggregation movingFunctionPipelineAggregation) {

		SimpleValueAggregate simpleValueAggregate = _aggregate.simpleValue();

		return AggregationResults.INSTANCE.movingFunction(
			movingFunctionPipelineAggregation.getName(),
			simpleValueAggregate.value());
	}

	@Override
	public PercentilesBucketPipelineAggregationResult visit(
		PercentilesBucketPipelineAggregation
			percentilesBucketPipelineAggregation) {

		PercentilesBucketAggregate percentilesBucketAggregate =
			_aggregate.percentilesBucket();

		PercentilesBucketPipelineAggregationResult
			percentilesBucketPipelineAggregationResult =
				AggregationResults.INSTANCE.percentilesBucket(
					percentilesBucketPipelineAggregation.getName());

		Percentiles percentiles = percentilesBucketAggregate.values();

		if (percentiles.isArray()) {
			ListUtil.isNotEmptyForEach(
				percentiles.array(),
				percentile ->
					percentilesBucketPipelineAggregationResult.addPercentile(
						Double.valueOf(percentile.key()),
						GetterUtil.getDouble(percentile.value())));
		}
		else {
			MapUtil.isNotEmptyForEach(
				percentiles.keyed(),
				(key, percentile) ->
					percentilesBucketPipelineAggregationResult.addPercentile(
						Double.valueOf(key), GetterUtil.getDouble(percentile)));
		}

		return percentilesBucketPipelineAggregationResult;
	}

	@Override
	public SerialDiffPipelineAggregationResult visit(
		SerialDiffPipelineAggregation serialDiffPipelineAggregation) {

		SimpleValueAggregate simpleValueAggregate = _aggregate.simpleValue();

		return AggregationResults.INSTANCE.serialDiff(
			serialDiffPipelineAggregation.getName(),
			simpleValueAggregate.value());
	}

	@Override
	public StatsBucketPipelineAggregationResult visit(
		StatsBucketPipelineAggregation statsBucketPipelineAggregation) {

		StatsBucketAggregate statsBucketAggregate = _aggregate.statsBucket();

		return AggregationResults.INSTANCE.statsBucket(
			statsBucketPipelineAggregation.getName(),
			statsBucketAggregate.avg(), statsBucketAggregate.count(),
			statsBucketAggregate.min(), statsBucketAggregate.max(),
			statsBucketAggregate.sum());
	}

	@Override
	public SumBucketPipelineAggregationResult visit(
		SumBucketPipelineAggregation sumBucketPipelineAggregation) {

		SimpleValueAggregate simpleValueAggregate = _aggregate.simpleValue();

		return AggregationResults.INSTANCE.sumBucket(
			sumBucketPipelineAggregation.getName(),
			simpleValueAggregate.value());
	}

	private final Aggregate _aggregate;

}