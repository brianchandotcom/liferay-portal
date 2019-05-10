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
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.script.Script;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface Aggregations {

	public AvgAggregation avg(String name, String field);

	public AvgBucketPipelineAggregation avgBucket(
		String name, String bucketsPath);

	public BucketScriptPipelineAggregation bucketScript(
		String name, Script script);

	public BucketSelectorPipelineAggregation bucketSelector(
		String name, Script script);

	public BucketSortPipelineAggregation bucketSort(String name);

	public CardinalityAggregation cardinality(String name, String field);

	public ChildrenAggregation children(String name, String field);

	public CumulativeSumPipelineAggregation cumulativeSum(
		String name, String bucketsPath);

	public DateHistogramAggregation dateHistogram(String name, String field);

	public DateRangeAggregation dateRange(String name, String field);

	public DerivativePipelineAggregation derivative(
		String name, String bucketsPath);

	public DiversifiedSamplerAggregation diversifiedSampler(
		String name, String field);

	public ExtendedStatsAggregation extendedStats(String name, String field);

	public ExtendedStatsBucketPipelineAggregation extendedStatsBucket(
		String name, String bucketsPath);

	public FilterAggregation filter(String name, Query query);

	public FiltersAggregation filters(String name, String field);

	public GeoBoundsAggregation geoBounds(String name, String field);

	public GeoCentroidAggregation geoCentroid(String name, String field);

	public GeoDistanceAggregation geoDistance(
		String name, String field, GeoLocationPoint geoLocationPoint);

	public GeoHashGridAggregation geoHashGrid(String name, String field);

	public GlobalAggregation global(String name);

	public HistogramAggregation histogram(String name, String field);

	public MaxAggregation max(String name, String field);

	public MaxBucketPipelineAggregation maxBucket(
		String name, String bucketsPath);

	public MinAggregation min(String name, String field);

	public MinBucketPipelineAggregation minBucket(
		String name, String bucketsPath);

	public MissingAggregation missing(String name, String field);

	public MovingFunctionPipelineAggregation movingFunction(
		String name, Script script, String bucketsPath, int window);

	public NestedAggregation nested(String name, String path);

	public PercentileRanksAggregation percentileRanks(
		String name, String field, double... values);

	public PercentilesAggregation percentiles(String name, String field);

	public PercentilesBucketPipelineAggregation percentilesBucket(
		String name, String bucketsPath);

	public RangeAggregation range(String name, String field);

	public ReverseNestedAggregation reverseNested(String name, String path);

	public SamplerAggregation sampler(String name);

	public ScriptedMetricAggregation scriptedMetric(String name);

	public SerialDiffPipelineAggregation serialDiff(
		String name, String bucketsPath);

	public SignificantTermsAggregation significantTerms(
		String name, String field);

	public SignificantTextAggregation significantText(
		String name, String field);

	public StatsAggregation stats(String name, String field);

	public StatsBucketPipelineAggregation statsBucket(
		String name, String bucketsPath);

	public SumAggregation sum(String name, String field);

	public SumBucketPipelineAggregation sumBucket(
		String name, String bucketsPath);

	public TermsAggregation terms(String name, String field);

	public TopHitsAggregation topHits(String name);

	public ValueCountAggregation valueCount(String name, String field);

	public WeightedAvgAggregation weightedAvg(
		String name, String valueField, String weightField);

}