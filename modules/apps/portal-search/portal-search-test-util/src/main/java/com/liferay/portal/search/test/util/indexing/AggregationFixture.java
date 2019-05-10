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

package com.liferay.portal.search.test.util.indexing;

import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FiltersAggregation;
import com.liferay.portal.search.aggregation.bucket.GeoDistanceAggregation;
import com.liferay.portal.search.aggregation.bucket.GeoHashGridAggregation;
import com.liferay.portal.search.aggregation.bucket.GlobalAggregation;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.MissingAggregation;
import com.liferay.portal.search.aggregation.bucket.Order;
import com.liferay.portal.search.aggregation.bucket.RangeAggregation;
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
import com.liferay.portal.search.aggregation.metrics.PercentilesMethod;
import com.liferay.portal.search.aggregation.metrics.StatsAggregation;
import com.liferay.portal.search.aggregation.metrics.SumAggregation;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregation;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregation;
import com.liferay.portal.search.aggregation.metrics.WeightedAvgAggregation;
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
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.SerialDiffPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.StatsBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.SumBucketPipelineAggregation;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.internal.aggregation.AggregationsImpl;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.Sort;

/**
 * @author Wade Cao
 */
public class AggregationFixture {

	public AvgAggregation newAvgAggregation(String name, String field) {
		return _aggregations.avg(name, field);
	}

	public BucketScriptPipelineAggregation newBucketScriptPipelineAggregation(
		String name, Script script) {

		return _aggregations.bucketScript(name, script);
	}

	public BucketSelectorPipelineAggregation
		newBucketSelectorPipelineAggregation(String name, Script script) {

		return _aggregations.bucketSelector(name, script);
	}

	public BucketSortPipelineAggregation newBucketSortPipelineAggregation(
		String name, FieldSort fieldSort, Integer size) {

		BucketSortPipelineAggregation bucketSortPipelineAggregation =
			_aggregations.bucketSort(name);

		bucketSortPipelineAggregation.addSortFields(fieldSort);

		if (size != null) {
			bucketSortPipelineAggregation.setSize(size);
		}

		return bucketSortPipelineAggregation;
	}

	public CardinalityAggregation newCardinalityAggregation(
		String name, String field) {

		return _aggregations.cardinality(name, field);
	}

	public CumulativeSumPipelineAggregation newCumulativeSumPipelineAggregation(
		String name, String bucketsPath) {

		return _aggregations.cumulativeSum(name, bucketsPath);
	}

	public DateHistogramAggregation newDateHistogramAggregation(
		String name, String field, String dateHistogramInterval,
		Long minDocCount, Long offset) {

		DateHistogramAggregation dateHistogramAggregation =
			_aggregations.dateHistogram(name, field);

		dateHistogramAggregation.setDateHistogramInterval(
			dateHistogramInterval);

		if (minDocCount != null) {
			dateHistogramAggregation.setMinDocCount(minDocCount);
		}

		if (offset != null) {
			dateHistogramAggregation.setOffset(offset);
		}

		return dateHistogramAggregation;
	}

	public DateRangeAggregation newDateRangeAggregation(
		String name, String field, String format, Boolean keyed) {

		DateRangeAggregation dateRangeAggregation = _aggregations.dateRange(
			name, field);

		dateRangeAggregation.setFormat(format);

		if (keyed != null) {
			dateRangeAggregation.setKeyed(keyed);
		}

		return dateRangeAggregation;
	}

	public DerivativePipelineAggregation newDerivativePipelineAggregation(
		String name, String bucketsPath) {

		return _aggregations.derivative(name, bucketsPath);
	}

	public ExtendedStatsAggregation newExtendedStatsAggregation(
		String name, String field) {

		return _aggregations.extendedStats(name, field);
	}

	public ExtendedStatsBucketPipelineAggregation
		newExtendedStatsBucketPipelineAggregation(
			String name, String bucketsPath) {

		return _aggregations.extendedStatsBucket(name, bucketsPath);
	}

	public FilterAggregation newFilterAggregation(String name, Query query) {
		return _aggregations.filter(name, query);
	}

	public FiltersAggregation newFiltersAggregation(String name, String field) {
		return _aggregations.filters(name, field);
	}

	public GeoBoundsAggregation newGeoBoundsAggregation(
		String name, String field) {

		return _aggregations.geoBounds(name, field);
	}

	public GeoCentroidAggregation newGeoCentroidAggregation(
		String name, String field) {

		return _aggregations.geoCentroid(name, field);
	}

	public GeoDistanceAggregation newGeoDistanceAggregation(
		String name, String field, GeoLocationPoint geoLocationPoint) {

		return _aggregations.geoDistance(name, field, geoLocationPoint);
	}

	public GeoHashGridAggregation newGeoHashGridAggregation(
		String name, String field, Integer precision) {

		GeoHashGridAggregation geoHashGridAggregation =
			_aggregations.geoHashGrid(name, field);

		geoHashGridAggregation.setPrecision(precision);

		return geoHashGridAggregation;
	}

	public GlobalAggregation newGlobalAggregation(String name) {
		return _aggregations.global(name);
	}

	public HistogramAggregation newHistogramAggregation(
		String name, String field, Double interval, Long minDocCount) {

		HistogramAggregation histogramAggregation = _aggregations.histogram(
			name, field);

		histogramAggregation.setInterval(interval);
		histogramAggregation.setMinDocCount(minDocCount);

		return histogramAggregation;
	}

	public MaxAggregation newMaxAggregation(String name, String field) {
		return _aggregations.max(name, field);
	}

	public MaxBucketPipelineAggregation newMaxBucketPipelineAggregation(
		String name, String bucketsPath) {

		return _aggregations.maxBucket(name, bucketsPath);
	}

	public MinAggregation newMinAggregation(String name, String field) {
		return _aggregations.min(name, field);
	}

	public MinBucketPipelineAggregation newMinBucketPipelineAggregation(
		String name, String bucketsPath) {

		return _aggregations.minBucket(name, bucketsPath);
	}

	public MissingAggregation newMissingAggregation(String name, String field) {
		return _aggregations.missing(name, field);
	}

	public MovingFunctionPipelineAggregation
		newMovingFunctionPipelineAggregation(
			String name, Script script, String bucketsPath, int window) {

		return _aggregations.movingFunction(name, script, bucketsPath, window);
	}

	public PercentileRanksAggregation newPercentileRanksAggregation(
		String name, String field, PercentilesMethod percentilesMethod,
		double... values) {

		PercentileRanksAggregation percentileRanksAggregation =
			_aggregations.percentileRanks(name, field, values);

		if (percentilesMethod != null) {
			percentileRanksAggregation.setPercentilesMethod(percentilesMethod);
		}

		return percentileRanksAggregation;
	}

	public PercentilesAggregation newPercentilesAggregation(
		String name, String field, PercentilesMethod percentilesMethod,
		double... values) {

		PercentilesAggregation percentilesAggregation =
			_aggregations.percentiles(name, field);

		if (percentilesMethod != null) {
			percentilesAggregation.setPercentilesMethod(percentilesMethod);
		}

		return percentilesAggregation;
	}

	public PercentilesBucketPipelineAggregation
		newPercentilesBucketPipelineAggregation(
			String name, String bucketsPath) {

		return _aggregations.percentilesBucket(name, bucketsPath);
	}

	public PipelineAggregation newPipelineAggregation(
		String name, String bucketsPath) {

		return _aggregations.avgBucket(name, bucketsPath);
	}

	public RangeAggregation newRangeAggregation(
		String name, String field, Boolean keyed) {

		RangeAggregation rangeAggregation = _aggregations.range(name, field);

		if (keyed != null) {
			rangeAggregation.setKeyed(keyed);
		}

		return rangeAggregation;
	}

	public SerialDiffPipelineAggregation newSerialDiffPipelineAggregation(
		String name, String bucketsPath) {

		return _aggregations.serialDiff(name, bucketsPath);
	}

	public StatsAggregation newStatsAggregation(String name, String field) {
		return _aggregations.stats(name, field);
	}

	public StatsBucketPipelineAggregation newStatsBucketPipelineAggregation(
		String name, String bucketsPath) {

		return _aggregations.statsBucket(name, bucketsPath);
	}

	public SumAggregation newSumAggregation(String name, String field) {
		return _aggregations.sum(name, field);
	}

	public SumBucketPipelineAggregation newSumBucketPipelineAggregation(
		String name, String bucketsPath) {

		return _aggregations.sumBucket(name, bucketsPath);
	}

	public TermsAggregation newTermsAggregation(
		String name, String field, Order... orders) {

		TermsAggregation termsAggregation = _aggregations.terms(name, field);

		if (orders != null) {
			termsAggregation.addOrders(orders);
		}

		return termsAggregation;
	}

	public TopHitsAggregation newTopHitsAggregation(
		String name, Integer size, Sort... sortFields) {

		TopHitsAggregation topHitsAggregation = _aggregations.topHits(name);

		if (size != null) {
			topHitsAggregation.setSize(size);
		}

		if (sortFields != null) {
			topHitsAggregation.addSortFields(sortFields);
		}

		return topHitsAggregation;
	}

	public ValueCountAggregation newValueCountAggregation(
		String name, String field) {

		return _aggregations.valueCount(name, field);
	}

	public WeightedAvgAggregation newWeightedAvgAggregation(
		String name, String valueField, String weightField) {

		return _aggregations.weightedAvg(name, valueField, weightField);
	}

	private final Aggregations _aggregations = new AggregationsImpl();

}