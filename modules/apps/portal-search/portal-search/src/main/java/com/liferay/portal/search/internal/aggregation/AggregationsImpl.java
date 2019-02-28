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

import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.ChildrenAggregation;
import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.bucket.DiversifiedSamplerAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FiltersAggregation;
import com.liferay.portal.search.aggregation.bucket.GeoDistanceAggregation;
import com.liferay.portal.search.aggregation.bucket.GeoHashGridAggregation;
import com.liferay.portal.search.aggregation.bucket.GlobalAggregation;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.MissingAggregation;
import com.liferay.portal.search.aggregation.bucket.NestedAggregation;
import com.liferay.portal.search.aggregation.bucket.RangeAggregation;
import com.liferay.portal.search.aggregation.bucket.ReverseNestedAggregation;
import com.liferay.portal.search.aggregation.bucket.SamplerAggregation;
import com.liferay.portal.search.aggregation.bucket.SignificantTermsAggregation;
import com.liferay.portal.search.aggregation.bucket.SignificantTextAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.metrics.AvgAggregation;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregation;
import com.liferay.portal.search.aggregation.metrics.ExtendedStatsAggregation;
import com.liferay.portal.search.aggregation.metrics.GeoBoundsAggregation;
import com.liferay.portal.search.aggregation.metrics.GeoCentroidAggregation;
import com.liferay.portal.search.aggregation.metrics.MaxAggregation;
import com.liferay.portal.search.aggregation.metrics.MinAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentileRanksAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentilesAggregation;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregation;
import com.liferay.portal.search.aggregation.metrics.StatsAggregation;
import com.liferay.portal.search.aggregation.metrics.SumAggregation;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregation;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregation;
import com.liferay.portal.search.aggregation.metrics.WeightedAvgAggregation;
import com.liferay.portal.search.aggregation.pipeline.AvgBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketScriptPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketSelectorPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketSortPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.CumulativeSumPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.DerivativePipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.ExtendedStatsBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.MaxBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.MinBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.MovingFunctionPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.PercentilesBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.SerialDiffPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.StatsBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.SumBucketPipelineAggregation;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.internal.aggregation.bucket.ChildrenAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.DateHistogramAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.DateRangeAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.DiversifiedSamplerAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.FilterAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.FiltersAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.GeoDistanceAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.GeoHashGridAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.GlobalAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.HistogramAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.MissingAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.NestedAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.RangeAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.ReverseNestedAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.SamplerAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.SignificantTermsAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.SignificantTextAggregationImpl;
import com.liferay.portal.search.internal.aggregation.bucket.TermsAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.AvgAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.CardinalityAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.ExtendedStatsAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.GeoBoundsAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.GeoCentroidAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.MaxAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.MinAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.PercentileRanksAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.PercentilesAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.ScriptedMetricAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.StatsAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.SumAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.TopHitsAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.ValueCountAggregationImpl;
import com.liferay.portal.search.internal.aggregation.metrics.WeightedAvgAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.AvgBucketPipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.BucketScriptPipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.BucketSelectorPipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.BucketSortPipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.CumulativeSumPipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.DerivativePipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.ExtendedStatsBucketPipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.MaxBucketPipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.MinBucketPipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.MovingFunctionPipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.PercentilesBucketPipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.SerialDiffPipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.StatsBucketPipelineAggregationImpl;
import com.liferay.portal.search.internal.aggregation.pipeline.SumBucketPipelineAggregationImpl;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.script.Script;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = Aggregations.class)
public class AggregationsImpl implements Aggregations {

	@Override
	public AvgAggregation avg(String name, String field) {
		return new AvgAggregationImpl(name, field);
	}

	@Override
	public AvgBucketPipelineAggregation avgBucket(
		String name, String bucketsPath) {

		return new AvgBucketPipelineAggregationImpl(name, bucketsPath);
	}

	@Override
	public BucketScriptPipelineAggregation bucketScript(
		String name, Script script) {

		return new BucketScriptPipelineAggregationImpl(name, script);
	}

	@Override
	public BucketSelectorPipelineAggregation bucketSelector(
		String name, Script script) {

		return new BucketSelectorPipelineAggregationImpl(name, script);
	}

	@Override
	public BucketSortPipelineAggregation bucketSort(String name) {
		return new BucketSortPipelineAggregationImpl(name);
	}

	@Override
	public CardinalityAggregation cardinality(String name, String field) {
		return new CardinalityAggregationImpl(name, field);
	}

	@Override
	public ChildrenAggregation children(String name, String field) {
		return new ChildrenAggregationImpl(name, field);
	}

	@Override
	public CumulativeSumPipelineAggregation cumulativeSum(
		String name, String bucketsPath) {

		return new CumulativeSumPipelineAggregationImpl(name, bucketsPath);
	}

	@Override
	public DateHistogramAggregation dateHistogram(String name, String field) {
		return new DateHistogramAggregationImpl(name, field);
	}

	@Override
	public DateRangeAggregation dateRange(String name, String field) {
		return new DateRangeAggregationImpl(name, field);
	}

	@Override
	public DerivativePipelineAggregation derivative(
		String name, String bucketsPath) {

		return new DerivativePipelineAggregationImpl(name, bucketsPath);
	}

	@Override
	public DiversifiedSamplerAggregation diversifiedSampler(
		String name, String field) {

		return new DiversifiedSamplerAggregationImpl(name, field);
	}

	@Override
	public ExtendedStatsAggregation extendedStats(String name, String field) {
		return new ExtendedStatsAggregationImpl(name, field);
	}

	@Override
	public ExtendedStatsBucketPipelineAggregation extendedStatsBucket(
		String name, String bucketsPath) {

		return new ExtendedStatsBucketPipelineAggregationImpl(
			name, bucketsPath);
	}

	@Override
	public FilterAggregation filter(String name, Query query) {
		return new FilterAggregationImpl(name, query);
	}

	@Override
	public FiltersAggregation filters(String name, String field) {
		return new FiltersAggregationImpl(name, field);
	}

	@Override
	public GeoBoundsAggregation geoBounds(String name, String field) {
		return new GeoBoundsAggregationImpl(name, field);
	}

	@Override
	public GeoCentroidAggregation geoCentroid(String name, String field) {
		return new GeoCentroidAggregationImpl(name, field);
	}

	@Override
	public GeoDistanceAggregation geoDistance(
		String name, String field, GeoLocationPoint geoLocationPoint) {

		return new GeoDistanceAggregationImpl(name, field, geoLocationPoint);
	}

	@Override
	public GeoHashGridAggregation geoHashGrid(String name, String field) {
		return new GeoHashGridAggregationImpl(name, field);
	}

	@Override
	public GlobalAggregation global(String name) {
		return new GlobalAggregationImpl(name);
	}

	@Override
	public HistogramAggregation histogram(String name, String field) {
		return new HistogramAggregationImpl(name, field);
	}

	@Override
	public MaxAggregation max(String name, String field) {
		return new MaxAggregationImpl(name, field);
	}

	@Override
	public MaxBucketPipelineAggregation maxBucket(
		String name, String bucketsPath) {

		return new MaxBucketPipelineAggregationImpl(name, bucketsPath);
	}

	@Override
	public MinAggregation min(String name, String field) {
		return new MinAggregationImpl(name, field);
	}

	@Override
	public MinBucketPipelineAggregation minBucket(
		String name, String bucketsPath) {

		return new MinBucketPipelineAggregationImpl(name, bucketsPath);
	}

	@Override
	public MissingAggregation missing(String name, String field) {
		return new MissingAggregationImpl(name, field);
	}

	@Override
	public MovingFunctionPipelineAggregation movingFunction(
		String name, Script script, String bucketsPath, int window) {

		return new MovingFunctionPipelineAggregationImpl(
			name, script, bucketsPath, window);
	}

	@Override
	public NestedAggregation nested(String name, String path) {
		return new NestedAggregationImpl(name, path);
	}

	@Override
	public PercentileRanksAggregation percentileRanks(
		String name, String field, double... values) {

		return new PercentileRanksAggregationImpl(name, field, values);
	}

	@Override
	public PercentilesAggregation percentiles(String name, String field) {
		return new PercentilesAggregationImpl(name, field);
	}

	@Override
	public PercentilesBucketPipelineAggregation percentilesBucket(
		String name, String bucketsPath) {

		return new PercentilesBucketPipelineAggregationImpl(name, bucketsPath);
	}

	@Override
	public RangeAggregation range(String name, String field) {
		return new RangeAggregationImpl(name, field);
	}

	@Override
	public ReverseNestedAggregation reverseNested(String name, String path) {
		return new ReverseNestedAggregationImpl(name, path);
	}

	@Override
	public SamplerAggregation sampler(String name) {
		return new SamplerAggregationImpl(name);
	}

	@Override
	public ScriptedMetricAggregation scriptedMetric(String name) {
		return new ScriptedMetricAggregationImpl(name);
	}

	@Override
	public SerialDiffPipelineAggregation serialDiff(
		String name, String bucketsPath) {

		return new SerialDiffPipelineAggregationImpl(name, bucketsPath);
	}

	@Override
	public SignificantTermsAggregation significantTerms(
		String name, String field) {

		return new SignificantTermsAggregationImpl(name, field);
	}

	@Override
	public SignificantTextAggregation significantText(
		String name, String field) {

		return new SignificantTextAggregationImpl(name, field);
	}

	@Override
	public StatsAggregation stats(String name, String field) {
		return new StatsAggregationImpl(name, field);
	}

	@Override
	public StatsBucketPipelineAggregation statsBucket(
		String name, String bucketsPath) {

		return new StatsBucketPipelineAggregationImpl(name, bucketsPath);
	}

	@Override
	public SumAggregation sum(String name, String field) {
		return new SumAggregationImpl(name, field);
	}

	@Override
	public SumBucketPipelineAggregation sumBucket(
		String name, String bucketsPath) {

		return new SumBucketPipelineAggregationImpl(name, bucketsPath);
	}

	@Override
	public TermsAggregation terms(String name, String field) {
		return new TermsAggregationImpl(name, field);
	}

	@Override
	public TopHitsAggregation topHits(String name) {
		return new TopHitsAggregationImpl(name);
	}

	@Override
	public ValueCountAggregation valueCount(String name, String field) {
		return new ValueCountAggregationImpl(name, field);
	}

	@Override
	public WeightedAvgAggregation weightedAvg(
		String name, String valueField, String weightField) {

		return new WeightedAvgAggregationImpl(name, valueField, weightField);
	}

}