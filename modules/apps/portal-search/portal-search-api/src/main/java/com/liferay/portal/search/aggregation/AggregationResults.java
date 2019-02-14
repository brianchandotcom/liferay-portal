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

import com.liferay.portal.search.aggregation.bucket.ChildrenAggregationResult;
import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregationResult;
import com.liferay.portal.search.aggregation.bucket.DiversifiedSamplerAggregationResult;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.bucket.FiltersAggregationResult;
import com.liferay.portal.search.aggregation.bucket.GeoDistanceAggregationResult;
import com.liferay.portal.search.aggregation.bucket.GeoHashGridAggregationResult;
import com.liferay.portal.search.aggregation.bucket.GlobalAggregationResult;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregationResult;
import com.liferay.portal.search.aggregation.bucket.MissingAggregationResult;
import com.liferay.portal.search.aggregation.bucket.NestedAggregationResult;
import com.liferay.portal.search.aggregation.bucket.RangeAggregationResult;
import com.liferay.portal.search.aggregation.bucket.ReverseNestedAggregationResult;
import com.liferay.portal.search.aggregation.bucket.SamplerAggregationResult;
import com.liferay.portal.search.aggregation.bucket.SignificantTermsAggregationResult;
import com.liferay.portal.search.aggregation.bucket.SignificantTextAggregationResult;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.AvgAggregationResult;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ExtendedStatsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.GeoBoundsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.GeoCentroidAggregationResult;
import com.liferay.portal.search.aggregation.metrics.MaxAggregationResult;
import com.liferay.portal.search.aggregation.metrics.MinAggregationResult;
import com.liferay.portal.search.aggregation.metrics.PercentileRanksAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregationResult;
import com.liferay.portal.search.aggregation.metrics.StatsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.SumAggregationResult;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregationResult;
import com.liferay.portal.search.aggregation.metrics.WeightedAvgAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.AvgBucketPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketScriptPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.CumulativeSumPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.DerivativePipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.ExtendedStatsBucketPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.MaxBucketPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.MinBucketPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.MovingFunctionPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.PercentilesBucketPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.SerialDiffPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.StatsBucketPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.SumBucketPipelineAggregationResult;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.hits.SearchHits;

/**
 * @author André de Oliveira
 */
public interface AggregationResults {

	public AvgAggregationResult avg(String name, double value);

	public AvgBucketPipelineAggregationResult avgBucket(
		String name, double value);

	public BucketScriptPipelineAggregationResult bucketScript(
		String name, double value);

	public CardinalityAggregationResult cardinality(String name, long value);

	public ChildrenAggregationResult children(String name, long docCount);

	public CumulativeSumPipelineAggregationResult cumulativeSum(
		String name, double value);

	public DateHistogramAggregationResult dateHistogram(String name);

	public DerivativePipelineAggregationResult derivative(
		String name, double normalizedValue);

	public DiversifiedSamplerAggregationResult diversifiedSampler(
		String name, long docCount);

	public ExtendedStatsAggregationResult extendedStats(
		String name, double avg, long count, double min, double max, double sum,
		double sumOfSquares, double variance, double stdDeviation);

	public ExtendedStatsBucketPipelineAggregationResult extendedStatsBucket(
		String name, double avg, long count, double min, double max, double sum,
		double sumOfSquares, double variance, double stdDeviation);

	public FilterAggregationResult filter(String name, long docCount);

	public FiltersAggregationResult filters(String name);

	public GeoBoundsAggregationResult geoBounds(
		String name, GeoLocationPoint topLeft, GeoLocationPoint bottomRight);

	public GeoCentroidAggregationResult geoCentroid(
		String name, GeoLocationPoint centroid, long count);

	public GeoDistanceAggregationResult geoDistance(String name);

	public GeoHashGridAggregationResult geoHashGrid(String name);

	public GlobalAggregationResult global(String name, long docCount);

	public HistogramAggregationResult histogram(String name);

	public MaxAggregationResult max(String name, double value);

	public MaxBucketPipelineAggregationResult maxBucket(
		String name, double value);

	public MinAggregationResult min(String name, double value);

	public MinBucketPipelineAggregationResult minBucket(
		String name, double value);

	public MissingAggregationResult missing(String name, long docCount);

	public MovingFunctionPipelineAggregationResult movingFunction(
		String name, double value);

	public NestedAggregationResult nested(String name, long docCount);

	public PercentileRanksAggregationResult percentileRanks(String name);

	public PercentilesBucketPipelineAggregationResult percentiles(String name);

	public PercentilesBucketPipelineAggregationResult percentilesBucket(
		String name);

	public RangeAggregationResult range(String name);

	public ReverseNestedAggregationResult reverseNested(
		String name, long docCount);

	public SamplerAggregationResult sampler(String name, long docCount);

	public ScriptedMetricAggregationResult scriptedMetric(
		String name, Object value);

	public SerialDiffPipelineAggregationResult serialDiff(
		String name, double value);

	public SignificantTermsAggregationResult significantTerms(
		String name, long errorDocCounts, long otherDocCounts);

	public SignificantTextAggregationResult significantText(
		String name, long docCountError, long sumOfOtherDocCounts);

	public StatsAggregationResult stats(
		String name, double avg, long count, double min, double max,
		double sum);

	public StatsBucketPipelineAggregationResult statsBucket(
		String name, double avg, long count, double min, double max,
		double sum);

	public SumAggregationResult sum(String name, double value);

	public SumBucketPipelineAggregationResult sumBucket(
		String name, double value);

	public TermsAggregationResult terms(
		String name, long errorDocCounts, long otherDocCounts);

	public TopHitsAggregationResult topHits(String name, SearchHits searchHits);

	public ValueCountAggregationResult valueCount(String name, long value);

	public WeightedAvgAggregationResult weightedAvg(String name, double value);

}