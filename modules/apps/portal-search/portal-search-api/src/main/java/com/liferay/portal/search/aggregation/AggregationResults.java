/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

/**
 * @author André de Oliveira
 */
public class AggregationResults {

	public static final AggregationResults INSTANCE = new AggregationResults();

	public AvgAggregationResult avg(String name, double value) {
		return new AvgAggregationResult(name, value);
	}

	public AvgBucketPipelineAggregationResult avgBucket(
		String name, double value) {

		return new AvgBucketPipelineAggregationResult(name, value);
	}

	public BucketScriptPipelineAggregationResult bucketScript(
		String name, double value) {

		return new BucketScriptPipelineAggregationResult(name, value);
	}

	public CardinalityAggregationResult cardinality(String name, long value) {
		return new CardinalityAggregationResult(name, value);
	}

	public ChildrenAggregationResult children(String name, long docCount) {
		return new ChildrenAggregationResult(name, docCount);
	}

	public CumulativeSumPipelineAggregationResult cumulativeSum(
		String name, double value) {

		return new CumulativeSumPipelineAggregationResult(name, value);
	}

	public DateHistogramAggregationResult dateHistogram(String name) {
		return new DateHistogramAggregationResult(name);
	}

	public DerivativePipelineAggregationResult derivative(
		String name, double normalizedValue) {

		return new DerivativePipelineAggregationResult(name, normalizedValue);
	}

	public DiversifiedSamplerAggregationResult diversifiedSampler(
		String name, long docCount) {

		return new DiversifiedSamplerAggregationResult(name, docCount);
	}

	public ExtendedStatsAggregationResult extendedStats(
		String name, double avg, long count, double min, double max, double sum,
		double sumOfSquares, double variance, double stdDeviation) {

		return new ExtendedStatsAggregationResult(
			name, avg, count, min, max, sum, sumOfSquares, variance,
			stdDeviation);
	}

	public ExtendedStatsBucketPipelineAggregationResult extendedStatsBucket(
		String name, double avg, long count, double min, double max, double sum,
		double sumOfSquares, double variance, double stdDeviation) {

		return new ExtendedStatsBucketPipelineAggregationResult(
			name, avg, count, min, max, sum, sumOfSquares, variance,
			stdDeviation);
	}

	public FilterAggregationResult filter(String name, long docCount) {
		return new FilterAggregationResult(name, docCount);
	}

	public FiltersAggregationResult filters(String name) {
		return new FiltersAggregationResult(name);
	}

	public GeoBoundsAggregationResult geoBounds(
		String name, GeoLocationPoint topLeftGeoLocationPoint,
		GeoLocationPoint bottomRightGeoLocationPoint) {

		return new GeoBoundsAggregationResult(
			name, topLeftGeoLocationPoint, bottomRightGeoLocationPoint);
	}

	public GeoCentroidAggregationResult geoCentroid(
		String name, GeoLocationPoint centroidGeoLocationPoint, long count) {

		return new GeoCentroidAggregationResult(
			name, centroidGeoLocationPoint, count);
	}

	public GeoDistanceAggregationResult geoDistance(String name) {
		return new GeoDistanceAggregationResult(name);
	}

	public GeoHashGridAggregationResult geoHashGrid(String name) {
		return new GeoHashGridAggregationResult(name);
	}

	public GlobalAggregationResult global(String name, long docCount) {
		return new GlobalAggregationResult(name, docCount);
	}

	public HistogramAggregationResult histogram(String name) {
		return new HistogramAggregationResult(name);
	}

	public MaxAggregationResult max(String name, double value) {
		return new MaxAggregationResult(name, value);
	}

	public MaxBucketPipelineAggregationResult maxBucket(
		String name, double value) {

		return new MaxBucketPipelineAggregationResult(name, value);
	}

	public MinAggregationResult min(String name, double value) {
		return new MinAggregationResult(name, value);
	}

	public MinBucketPipelineAggregationResult minBucket(
		String name, double value) {

		return new MinBucketPipelineAggregationResult(name, value);
	}

	public MissingAggregationResult missing(String name, long docCount) {
		return new MissingAggregationResult(name, docCount);
	}

	public MovingFunctionPipelineAggregationResult movingFunction(
		String name, double value) {

		return new MovingFunctionPipelineAggregationResult(name, value);
	}

	public NestedAggregationResult nested(String name, long docCount) {
		return new NestedAggregationResult(name, docCount);
	}

	public PercentileRanksAggregationResult percentileRanks(String name) {
		return new PercentileRanksAggregationResult(name);
	}

	public PercentilesAggregationResult percentiles(String name) {
		return new PercentilesAggregationResult(name);
	}

	public PercentilesBucketPipelineAggregationResult percentilesBucket(
		String name) {

		return new PercentilesBucketPipelineAggregationResult(name);
	}

	public RangeAggregationResult range(String name) {
		return new RangeAggregationResult(name);
	}

	public ReverseNestedAggregationResult reverseNested(
		String name, long docCount) {

		return new ReverseNestedAggregationResult(name, docCount);
	}

	public SamplerAggregationResult sampler(String name, long docCount) {
		return new SamplerAggregationResult(name, docCount);
	}

	public ScriptedMetricAggregationResult scriptedMetric(
		String name, Object value) {

		return new ScriptedMetricAggregationResult(name, value);
	}

	public SerialDiffPipelineAggregationResult serialDiff(
		String name, double value) {

		return new SerialDiffPipelineAggregationResult(name, value);
	}

	public SignificantTermsAggregationResult significantTerms(
		String name, long errorDocCounts, long otherDocCounts) {

		return new SignificantTermsAggregationResult(
			name, errorDocCounts, otherDocCounts);
	}

	public SignificantTextAggregationResult significantText(
		String name, long errorDocCounts, long otherDocCounts) {

		return new SignificantTextAggregationResult(
			name, errorDocCounts, otherDocCounts);
	}

	public StatsAggregationResult stats(
		String name, double avg, long count, double min, double max,
		double sum) {

		return new StatsAggregationResult(name, avg, count, min, max, sum);
	}

	public StatsBucketPipelineAggregationResult statsBucket(
		String name, double avg, long count, double min, double max,
		double sum) {

		return new StatsBucketPipelineAggregationResult(
			name, avg, count, min, max, sum);
	}

	public SumAggregationResult sum(String name, double value) {
		return new SumAggregationResult(name, value);
	}

	public SumBucketPipelineAggregationResult sumBucket(
		String name, double value) {

		return new SumBucketPipelineAggregationResult(name, value);
	}

	public TermsAggregationResult terms(
		String name, long errorDocCounts, long otherDocCounts) {

		return new TermsAggregationResult(name, errorDocCounts, otherDocCounts);
	}

	public TopHitsAggregationResult topHits(
		String name, SearchHits searchHits) {

		return new TopHitsAggregationResult(name, searchHits);
	}

	public ValueCountAggregationResult valueCount(String name, long value) {
		return new ValueCountAggregationResult(name, value);
	}

	public WeightedAvgAggregationResult weightedAvg(String name, double value) {
		return new WeightedAvgAggregationResult(name, value);
	}

	private AggregationResults() {
	}

}