/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.aggregation;

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

import org.opensearch.client.opensearch._types.aggregations.Aggregate;
import org.opensearch.client.opensearch._types.aggregations.BucketMetricValueAggregate;
import org.opensearch.client.opensearch._types.aggregations.DerivativeAggregate;
import org.opensearch.client.opensearch._types.aggregations.ExtendedStatsBucketAggregate;
import org.opensearch.client.opensearch._types.aggregations.Percentiles;
import org.opensearch.client.opensearch._types.aggregations.PercentilesBucketAggregate;
import org.opensearch.client.opensearch._types.aggregations.SimpleValueAggregate;
import org.opensearch.client.opensearch._types.aggregations.StatsBucketAggregate;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class OpenSearchPipelineAggregationResultTranslator
	implements PipelineAggregationResultTranslator {

	public OpenSearchPipelineAggregationResultTranslator(
		Aggregate aggregate, AggregationResults aggregationResults) {

		_aggregate = aggregate;
		_aggregationResults = aggregationResults;
	}

	@Override
	public AvgBucketPipelineAggregationResult visit(
		AvgBucketPipelineAggregation avgBucketPipelineAggregation) {

		SimpleValueAggregate simpleValueAggregate = _aggregate.simpleValue();

		return _aggregationResults.avgBucket(
			avgBucketPipelineAggregation.getName(),
			simpleValueAggregate.value());
	}

	@Override
	public BucketScriptPipelineAggregationResult visit(
		BucketScriptPipelineAggregation bucketScriptPipelineAggregation) {

		SimpleValueAggregate simpleValueAggregate = _aggregate.simpleValue();

		return _aggregationResults.bucketScript(
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

		return _aggregationResults.cumulativeSum(
			cumulativeSumPipelineAggregation.getName(),
			simpleValueAggregate.value());
	}

	@Override
	public DerivativePipelineAggregationResult visit(
		DerivativePipelineAggregation derivativePipelineAggregation) {

		DerivativeAggregate derivativeAggregate = _aggregate.derivative();

		if (derivativePipelineAggregation.getUnit() != null) {
			return _aggregationResults.derivative(
				derivativePipelineAggregation.getName(),
				derivativeAggregate.normalizedValue());
		}

		return _aggregationResults.derivative(
			derivativePipelineAggregation.getName(),
			derivativeAggregate.value());
	}

	@Override
	public ExtendedStatsBucketPipelineAggregationResult visit(
		ExtendedStatsBucketPipelineAggregation
			extendedStatsBucketPipelineAggregation) {

		ExtendedStatsBucketAggregate extendedStatsBucketAggregate =
			_aggregate.extendedStatsBucket();

		return _aggregationResults.extendedStatsBucket(
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
			_aggregationResults.maxBucket(
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
			_aggregationResults.minBucket(
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

		return _aggregationResults.movingFunction(
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
				_aggregationResults.percentilesBucket(
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

		return _aggregationResults.serialDiff(
			serialDiffPipelineAggregation.getName(),
			simpleValueAggregate.value());
	}

	@Override
	public StatsBucketPipelineAggregationResult visit(
		StatsBucketPipelineAggregation statsBucketPipelineAggregation) {

		StatsBucketAggregate statsBucketAggregate = _aggregate.statsBucket();

		return _aggregationResults.statsBucket(
			statsBucketPipelineAggregation.getName(),
			statsBucketAggregate.avg(), statsBucketAggregate.count(),
			statsBucketAggregate.min(), statsBucketAggregate.max(),
			statsBucketAggregate.sum());
	}

	@Override
	public SumBucketPipelineAggregationResult visit(
		SumBucketPipelineAggregation sumBucketPipelineAggregation) {

		SimpleValueAggregate simpleValueAggregate = _aggregate.simpleValue();

		return _aggregationResults.sumBucket(
			sumBucketPipelineAggregation.getName(),
			simpleValueAggregate.value());
	}

	private final Aggregate _aggregate;
	private final AggregationResults _aggregationResults;

}