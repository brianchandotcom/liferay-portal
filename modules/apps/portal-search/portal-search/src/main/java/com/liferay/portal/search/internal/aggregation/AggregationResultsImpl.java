/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return new AvgAggregationResult(name, value);
	}

	@Override
	public AvgBucketPipelineAggregationResult avgBucket(
		String name, double value) {

		return new AvgBucketPipelineAggregationResult(name, value);
	}

	@Override
	public BucketScriptPipelineAggregationResult bucketScript(
		String name, double value) {

		return new BucketScriptPipelineAggregationResult(name, value);
	}

	@Override
	public CardinalityAggregationResult cardinality(String name, long value) {
		return new CardinalityAggregationResult(name, value);
	}

	@Override
	public ChildrenAggregationResult children(String name, long docCount) {
		return new ChildrenAggregationResult(name, docCount);
	}

	@Override
	public CumulativeSumPipelineAggregationResult cumulativeSum(
		String name, double value) {

		return new CumulativeSumPipelineAggregationResult(name, value);
	}

	@Override
	public DateHistogramAggregationResult dateHistogram(String name) {
		return new DateHistogramAggregationResult(name);
	}

	@Override
	public DerivativePipelineAggregationResult derivative(
		String name, double normalizedValue) {

		return new DerivativePipelineAggregationResult(name, normalizedValue);
	}

	@Override
	public DiversifiedSamplerAggregationResult diversifiedSampler(
		String name, long docCount) {

		return new DiversifiedSamplerAggregationResult(name, docCount);
	}

	@Override
	public ExtendedStatsAggregationResult extendedStats(
		String name, double avg, long count, double min, double max, double sum,
		double sumOfSquares, double variance, double stdDeviation) {

		return new ExtendedStatsAggregationResult(
			name, avg, count, min, max, sum, sumOfSquares, variance,
			stdDeviation);
	}

	@Override
	public ExtendedStatsBucketPipelineAggregationResult extendedStatsBucket(
		String name, double avg, long count, double min, double max, double sum,
		double sumOfSquares, double variance, double stdDeviation) {

		return new ExtendedStatsBucketPipelineAggregationResult(
			name, avg, count, min, max, sum, sumOfSquares, variance,
			stdDeviation);
	}

	@Override
	public FilterAggregationResult filter(String name, long docCount) {
		return new FilterAggregationResult(name, docCount);
	}

	@Override
	public FiltersAggregationResult filters(String name) {
		return new FiltersAggregationResult(name);
	}

	@Override
	public GeoBoundsAggregationResult geoBounds(
		String name, GeoLocationPoint topLeftGeoLocationPoint,
		GeoLocationPoint bottomRightGeoLocationPoint) {

		return new GeoBoundsAggregationResult(
			name, topLeftGeoLocationPoint, bottomRightGeoLocationPoint);
	}

	@Override
	public GeoCentroidAggregationResult geoCentroid(
		String name, GeoLocationPoint centroidGeoLocationPoint, long count) {

		return new GeoCentroidAggregationResult(
			name, centroidGeoLocationPoint, count);
	}

	@Override
	public GeoDistanceAggregationResult geoDistance(String name) {
		return new GeoDistanceAggregationResult(name);
	}

	@Override
	public GeoHashGridAggregationResult geoHashGrid(String name) {
		return new GeoHashGridAggregationResult(name);
	}

	@Override
	public GlobalAggregationResult global(String name, long docCount) {
		return new GlobalAggregationResult(name, docCount);
	}

	@Override
	public HistogramAggregationResult histogram(String name) {
		return new HistogramAggregationResult(name);
	}

	@Override
	public MaxAggregationResult max(String name, double value) {
		return new MaxAggregationResult(name, value);
	}

	@Override
	public MaxBucketPipelineAggregationResult maxBucket(
		String name, double value) {

		return new MaxBucketPipelineAggregationResult(name, value);
	}

	@Override
	public MinAggregationResult min(String name, double value) {
		return new MinAggregationResult(name, value);
	}

	@Override
	public MinBucketPipelineAggregationResult minBucket(
		String name, double value) {

		return new MinBucketPipelineAggregationResult(name, value);
	}

	@Override
	public MissingAggregationResult missing(String name, long docCount) {
		return new MissingAggregationResult(name, docCount);
	}

	@Override
	public MovingFunctionPipelineAggregationResult movingFunction(
		String name, double value) {

		return new MovingFunctionPipelineAggregationResult(name, value);
	}

	@Override
	public NestedAggregationResult nested(String name, long docCount) {
		return new NestedAggregationResult(name, docCount);
	}

	@Override
	public PercentileRanksAggregationResult percentileRanks(String name) {
		return new PercentileRanksAggregationResult(name);
	}

	@Override
	public PercentilesAggregationResult percentiles(String name) {
		return new PercentilesAggregationResult(name);
	}

	@Override
	public PercentilesBucketPipelineAggregationResult percentilesBucket(
		String name) {

		return new PercentilesBucketPipelineAggregationResultImpl(name);
	}

	@Override
	public RangeAggregationResult range(String name) {
		return new RangeAggregationResult(name);
	}

	@Override
	public ReverseNestedAggregationResult reverseNested(
		String name, long docCount) {

		return new ReverseNestedAggregationResult(name, docCount);
	}

	@Override
	public SamplerAggregationResult sampler(String name, long docCount) {
		return new SamplerAggregationResult(name, docCount);
	}

	@Override
	public ScriptedMetricAggregationResult scriptedMetric(
		String name, Object value) {

		return new ScriptedMetricAggregationResult(name, value);
	}

	@Override
	public SerialDiffPipelineAggregationResult serialDiff(
		String name, double value) {

		return new SerialDiffPipelineAggregationResultImpl(name, value);
	}

	@Override
	public SignificantTermsAggregationResult significantTerms(
		String name, long errorDocCounts, long otherDocCounts) {

		return new SignificantTermsAggregationResult(
			name, errorDocCounts, otherDocCounts);
	}

	@Override
	public SignificantTextAggregationResult significantText(
		String name, long errorDocCounts, long otherDocCounts) {

		return new SignificantTextAggregationResult(
			name, errorDocCounts, otherDocCounts);
	}

	@Override
	public StatsAggregationResult stats(
		String name, double avg, long count, double min, double max,
		double sum) {

		return new StatsAggregationResult(name, avg, count, min, max, sum);
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
		return new SumAggregationResult(name, value);
	}

	@Override
	public SumBucketPipelineAggregationResult sumBucket(
		String name, double value) {

		return new SumBucketPipelineAggregationResultImpl(name, value);
	}

	@Override
	public TermsAggregationResult terms(
		String name, long errorDocCounts, long otherDocCounts) {

		return new TermsAggregationResult(name, errorDocCounts, otherDocCounts);
	}

	@Override
	public TopHitsAggregationResult topHits(
		String name, SearchHits searchHits) {

		return new TopHitsAggregationResult(name, searchHits);
	}

	@Override
	public ValueCountAggregationResult valueCount(String name, long value) {
		return new ValueCountAggregationResult(name, value);
	}

	@Override
	public WeightedAvgAggregationResult weightedAvg(String name, double value) {
		return new WeightedAvgAggregationResult(name, value);
	}

}