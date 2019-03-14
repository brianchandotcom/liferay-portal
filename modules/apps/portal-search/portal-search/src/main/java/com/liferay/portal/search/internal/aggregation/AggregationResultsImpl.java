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

import com.liferay.portal.search.aggregation.AggregationResults;
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
import com.liferay.portal.search.aggregation.metrics.PercentilesAggregationResult;
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
import com.liferay.portal.search.internal.aggregation.bucket.ChildrenAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.DateHistogramAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.DiversifiedSamplerAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.FilterAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.FiltersAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.GeoDistanceAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.GeoHashGridAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.GlobalAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.HistogramAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.MissingAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.NestedAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.RangeAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.ReverseNestedAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.SamplerAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.SignificantTermsAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.SignificantTextAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.bucket.TermsAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.AvgAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.CardinalityAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.ExtendedStatsAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.GeoBoundsAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.GeoCentroidAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.MaxAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.MinAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.PercentileRanksAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.PercentilesAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.ScriptedMetricAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.StatsAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.SumAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.TopHitsAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.ValueCountAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.metrics.WeightedAvgAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.AvgBucketPipelineAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.BucketScriptPipelineAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.CumulativeSumPipelineAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.DerivativePipelineAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.ExtendedStatsBucketPipelineAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.MaxBucketPipelineAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.MinBucketPipelineAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.MovingFunctionPipelineAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.PercentilesBucketPipelineAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.SerialDiffPipelineAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.StatsBucketPipelineAggregationResultImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.SumBucketPipelineAggregationResultImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = AggregationResults.class)
public class AggregationResultsImpl implements AggregationResults {

	@Override
	public AvgAggregationResult avg(String name, double value) {
		return new AvgAggregationResultImpl(name, value);
	}

	@Override
	public AvgBucketPipelineAggregationResult avgBucket(
		String name, double value) {

		return new AvgBucketPipelineAggregationResultImpl(name, value);
	}

	@Override
	public BucketScriptPipelineAggregationResult bucketScript(
		String name, double value) {

		return new BucketScriptPipelineAggregationResultImpl(name, value);
	}

	@Override
	public CardinalityAggregationResult cardinality(String name, long value) {
		return new CardinalityAggregationResultImpl(name, value);
	}

	@Override
	public ChildrenAggregationResult children(String name, long docCount) {
		return new ChildrenAggregationResultImpl(name, docCount);
	}

	@Override
	public CumulativeSumPipelineAggregationResult cumulativeSum(
		String name, double value) {

		return new CumulativeSumPipelineAggregationResultImpl(name, value);
	}

	@Override
	public DateHistogramAggregationResult dateHistogram(String name) {
		return new DateHistogramAggregationResultImpl(name);
	}

	@Override
	public DerivativePipelineAggregationResult derivative(
		String name, double normalizedValue) {

		return new DerivativePipelineAggregationResultImpl(
			name, normalizedValue);
	}

	@Override
	public DiversifiedSamplerAggregationResult diversifiedSampler(
		String name, long docCount) {

		return new DiversifiedSamplerAggregationResultImpl(name, docCount);
	}

	@Override
	public ExtendedStatsAggregationResult extendedStats(
		String name, double avg, long count, double min, double max, double sum,
		double sumOfSquares, double variance, double stdDeviation) {

		return new ExtendedStatsAggregationResultImpl(
			name, avg, count, min, max, sum, sumOfSquares, variance,
			stdDeviation);
	}

	@Override
	public ExtendedStatsBucketPipelineAggregationResult extendedStatsBucket(
		String name, double avg, long count, double min, double max, double sum,
		double sumOfSquares, double variance, double stdDeviation) {

		return new ExtendedStatsBucketPipelineAggregationResultImpl(
			name, avg, count, min, max, sum, sumOfSquares, variance,
			stdDeviation);
	}

	@Override
	public FilterAggregationResult filter(String name, long docCount) {
		return new FilterAggregationResultImpl(name, docCount);
	}

	@Override
	public FiltersAggregationResult filters(String name) {
		return new FiltersAggregationResultImpl(name);
	}

	@Override
	public GeoBoundsAggregationResult geoBounds(
		String name, GeoLocationPoint topLeftGeoLocationPoint,
		GeoLocationPoint bottomRightGeoLocationPoint) {

		return new GeoBoundsAggregationResultImpl(
			name, topLeftGeoLocationPoint, bottomRightGeoLocationPoint);
	}

	@Override
	public GeoCentroidAggregationResult geoCentroid(
		String name, GeoLocationPoint centroidGeoLocationPoint, long count) {

		return new GeoCentroidAggregationResultImpl(
			name, centroidGeoLocationPoint, count);
	}

	@Override
	public GeoDistanceAggregationResult geoDistance(String name) {
		return new GeoDistanceAggregationResultImpl(name);
	}

	@Override
	public GeoHashGridAggregationResult geoHashGrid(String name) {
		return new GeoHashGridAggregationResultImpl(name);
	}

	@Override
	public GlobalAggregationResult global(String name, long docCount) {
		return new GlobalAggregationResultImpl(name, docCount);
	}

	@Override
	public HistogramAggregationResult histogram(String name) {
		return new HistogramAggregationResultImpl(name);
	}

	@Override
	public MaxAggregationResult max(String name, double value) {
		return new MaxAggregationResultImpl(name, value);
	}

	@Override
	public MaxBucketPipelineAggregationResult maxBucket(
		String name, double value) {

		return new MaxBucketPipelineAggregationResultImpl(name, value);
	}

	@Override
	public MinAggregationResult min(String name, double value) {
		return new MinAggregationResultImpl(name, value);
	}

	@Override
	public MinBucketPipelineAggregationResult minBucket(
		String name, double value) {

		return new MinBucketPipelineAggregationResultImpl(name, value);
	}

	@Override
	public MissingAggregationResult missing(String name, long docCount) {
		return new MissingAggregationResultImpl(name, docCount);
	}

	@Override
	public MovingFunctionPipelineAggregationResult movingFunction(
		String name, double value) {

		return new MovingFunctionPipelineAggregationResultImpl(name, value);
	}

	@Override
	public NestedAggregationResult nested(String name, long docCount) {
		return new NestedAggregationResultImpl(name, docCount);
	}

	@Override
	public PercentileRanksAggregationResult percentileRanks(String name) {
		return new PercentileRanksAggregationResultImpl(name);
	}

	@Override
	public PercentilesAggregationResult percentiles(String name) {
		return new PercentilesAggregationResultImpl(name);
	}

	@Override
	public PercentilesBucketPipelineAggregationResult percentilesBucket(
		String name) {

		return new PercentilesBucketPipelineAggregationResultImpl(name);
	}

	@Override
	public RangeAggregationResult range(String name) {
		return new RangeAggregationResultImpl(name);
	}

	@Override
	public ReverseNestedAggregationResult reverseNested(
		String name, long docCount) {

		return new ReverseNestedAggregationResultImpl(name, docCount);
	}

	@Override
	public SamplerAggregationResult sampler(String name, long docCount) {
		return new SamplerAggregationResultImpl(name, docCount);
	}

	@Override
	public ScriptedMetricAggregationResult scriptedMetric(
		String name, Object value) {

		return new ScriptedMetricAggregationResultImpl(name, value);
	}

	@Override
	public SerialDiffPipelineAggregationResult serialDiff(
		String name, double value) {

		return new SerialDiffPipelineAggregationResultImpl(name, value);
	}

	@Override
	public SignificantTermsAggregationResult significantTerms(
		String name, long errorDocCounts, long otherDocCounts) {

		return new SignificantTermsAggregationResultImpl(
			name, errorDocCounts, otherDocCounts);
	}

	@Override
	public SignificantTextAggregationResult significantText(
		String name, long errorDocCounts, long otherDocCounts) {

		return new SignificantTextAggregationResultImpl(
			name, errorDocCounts, otherDocCounts);
	}

	@Override
	public StatsAggregationResult stats(
		String name, double avg, long count, double min, double max,
		double sum) {

		return new StatsAggregationResultImpl(name, avg, count, min, max, sum);
	}

	@Override
	public StatsBucketPipelineAggregationResult statsBucket(
		String name, double avg, long count, double min, double max,
		double sum) {

		return new StatsBucketPipelineAggregationResultImpl(
			name, avg, count, min, max, sum);
	}

	@Override
	public SumAggregationResult sum(String name, double value) {
		return new SumAggregationResultImpl(name, value);
	}

	@Override
	public SumBucketPipelineAggregationResult sumBucket(
		String name, double value) {

		return new SumBucketPipelineAggregationResultImpl(name, value);
	}

	@Override
	public TermsAggregationResult terms(
		String name, long errorDocCounts, long otherDocCounts) {

		return new TermsAggregationResultImpl(
			name, errorDocCounts, otherDocCounts);
	}

	@Override
	public TopHitsAggregationResult topHits(
		String name, SearchHits searchHits) {

		return new TopHitsAggregationResultImpl(name, searchHits);
	}

	@Override
	public ValueCountAggregationResult valueCount(String name, long value) {
		return new ValueCountAggregationResultImpl(name, value);
	}

	@Override
	public WeightedAvgAggregationResult weightedAvg(String name, double value) {
		return new WeightedAvgAggregationResultImpl(name, value);
	}

}