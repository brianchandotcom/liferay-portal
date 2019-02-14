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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FiltersAggregation;
import com.liferay.portal.search.aggregation.bucket.GeoHashGridAggregation;
import com.liferay.portal.search.aggregation.bucket.GlobalAggregation;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.MissingAggregation;
import com.liferay.portal.search.aggregation.bucket.RangeAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.metrics.AvgAggregation;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregation;
import com.liferay.portal.search.aggregation.metrics.ExtendedStatsAggregation;
import com.liferay.portal.search.aggregation.metrics.GeoBoundsAggregation;
import com.liferay.portal.search.aggregation.metrics.MaxAggregation;
import com.liferay.portal.search.aggregation.metrics.MinAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentileRanksAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentilesAggregation;
import com.liferay.portal.search.aggregation.metrics.StatsAggregation;
import com.liferay.portal.search.aggregation.metrics.SumAggregation;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregation;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregation;
import com.liferay.portal.search.aggregation.pipeline.AvgBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.CumulativeSumPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.DerivativePipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.ExtendedStatsBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.MaxBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.MinBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.PercentilesBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.SerialDiffPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.StatsBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.SumBucketPipelineAggregation;
import com.liferay.portal.search.query.Query;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface Aggregations {

	public AvgAggregation avg(String name, String field);

	public AvgBucketPipelineAggregation avgBucket(
		String name, String bucketsPath);

	public CardinalityAggregation cardinality(String name, String field);

	public CumulativeSumPipelineAggregation cumulativeSum(
		String name, String bucketsPath);

	public DateHistogramAggregation dateHistogram(String name, String field);

	public DateRangeAggregation dateRange(String name, String field);

	public DerivativePipelineAggregation derivative(
		String name, String bucketsPath);

	public ExtendedStatsAggregation extendedStats(String name, String field);

	public ExtendedStatsBucketPipelineAggregation extendedStatsBucket(
		String name, String bucketsPath);

	public FilterAggregation filter(String name, Query query);

	public FiltersAggregation filters(String name, String field);

	public GeoBoundsAggregation geoBounds(String name, String field);

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

	public PercentileRanksAggregation percentileRanks(
		String name, String field, double... values);

	public PercentilesAggregation percentiles(String name, String field);

	public PercentilesBucketPipelineAggregation percentilesBucket(
		String name, String bucketsPath);

	public RangeAggregation range(String name, String field);

	public SerialDiffPipelineAggregation serialDiff(
		String name, String bucketsPath);

	public StatsAggregation stats(String name, String field);

	public StatsBucketPipelineAggregation statsBucket(
		String name, String bucketsPath);

	public SumAggregation sum(String name, String field);

	public SumBucketPipelineAggregation sumBucket(
		String name, String bucketsPath);

	public TermsAggregation terms(String name, String field);

	public TopHitsAggregation topHits(String name);

	public ValueCountAggregation valueCount(String name, String field);

}