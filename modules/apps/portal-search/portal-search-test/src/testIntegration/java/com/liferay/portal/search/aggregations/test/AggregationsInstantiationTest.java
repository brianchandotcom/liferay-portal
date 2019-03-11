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

package com.liferay.portal.search.aggregations.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bryan Engler
 */
@RunWith(Arquillian.class)
public class AggregationsInstantiationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAvgAggregation() {
		AvgAggregation avgAggregation = _aggregations.avg("name", "field");

		Assert.assertNotNull(avgAggregation);
	}

	@Test
	public void testAvgBucketPipelineAggregation() {
		AvgBucketPipelineAggregation avgBucketPipelineAggregation =
			_aggregations.avgBucket("name", "bucketsPath");

		Assert.assertNotNull(avgBucketPipelineAggregation);
	}

	@Test
	public void testBucketScriptPipelineAggregation() {
		Script script = null;

		BucketScriptPipelineAggregation bucketScriptPipelineAggregation =
			_aggregations.bucketScript("name", script);

		Assert.assertNotNull(bucketScriptPipelineAggregation);
	}

	@Test
	public void testBucketSelectorPipelineAggregation() {
		Script script = null;

		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation =
			_aggregations.bucketSelector("name", script);

		Assert.assertNotNull(bucketSelectorPipelineAggregation);
	}

	@Test
	public void testBucketSortPipelineAggregation() {
		BucketSortPipelineAggregation bucketSortPipelineAggregation =
			_aggregations.bucketSort("name");

		Assert.assertNotNull(bucketSortPipelineAggregation);
	}

	@Test
	public void testCardinalityAggregation() {
		CardinalityAggregation cardinalityAggregation =
			_aggregations.cardinality("name", "field");

		Assert.assertNotNull(cardinalityAggregation);
	}

	@Test
	public void testChildrenAggregation() {
		ChildrenAggregation childrenAggregation = _aggregations.children(
			"name", "field");

		Assert.assertNotNull(childrenAggregation);
	}

	@Test
	public void testCumulativeSumPipelineAggregation() {
		CumulativeSumPipelineAggregation cumulativeSumPipelineAggregation =
			_aggregations.cumulativeSum("name", "bucketsPath");

		Assert.assertNotNull(cumulativeSumPipelineAggregation);
	}

	@Test
	public void testDateHistogramAggregation() {
		DateHistogramAggregation dateHistogramAggregation =
			_aggregations.dateHistogram("name", "field");

		Assert.assertNotNull(dateHistogramAggregation);
	}

	@Test
	public void testDateRangeAggregation() {
		DateRangeAggregation dateRangeAggregation = _aggregations.dateRange(
			"name", "field");

		Assert.assertNotNull(dateRangeAggregation);
	}

	@Test
	public void testDerivativePipelineAggregation() {
		DerivativePipelineAggregation derivativePipelineAggregation =
			_aggregations.derivative("name", "bucketsPath");

		Assert.assertNotNull(derivativePipelineAggregation);
	}

	@Test
	public void testDiversifiedSamplerAggregation() {
		DiversifiedSamplerAggregation diversifiedSamplerAggregation =
			_aggregations.diversifiedSampler("name", "field");

		Assert.assertNotNull(diversifiedSamplerAggregation);
	}

	@Test
	public void testExtendedStatsAggregation() {
		ExtendedStatsAggregation extendedStatsAggregation =
			_aggregations.extendedStats("name", "field");

		Assert.assertNotNull(extendedStatsAggregation);
	}

	@Test
	public void testFilterAggregation() {
		Query query = null;

		FilterAggregation filterAggregation = _aggregations.filter(
			"name", query);

		Assert.assertNotNull(filterAggregation);
	}

	@Test
	public void testFiltersAggregation() {
		FiltersAggregation filtersAggregation = _aggregations.filters(
			"name", "field");

		Assert.assertNotNull(filtersAggregation);
	}

	@Test
	public void testGeoBoundsAggregation() {
		GeoBoundsAggregation geoBoundsAggregation = _aggregations.geoBounds(
			"name", "field");

		Assert.assertNotNull(geoBoundsAggregation);
	}

	@Test
	public void testGeoCentroidAggregation() {
		GeoCentroidAggregation geoCentroidAggregation =
			_aggregations.geoCentroid("name", "field");

		Assert.assertNotNull(geoCentroidAggregation);
	}

	@Test
	public void testGeoDistanceAggregation() {
		GeoLocationPoint geoLocationPoint = null;

		GeoDistanceAggregation geoDistanceAggregation =
			_aggregations.geoDistance("name", "field", geoLocationPoint);

		Assert.assertNotNull(geoDistanceAggregation);
	}

	@Test
	public void testGeoHashGridAggregation() {
		GeoHashGridAggregation geoHashGridAggregation =
			_aggregations.geoHashGrid("name", "field");

		Assert.assertNotNull(geoHashGridAggregation);
	}

	@Test
	public void testGlobalAggregation() {
		GlobalAggregation globalAggregation = _aggregations.global("name");

		Assert.assertNotNull(globalAggregation);
	}

	@Test
	public void testHistogramAggregation() {
		HistogramAggregation histogramAggregation = _aggregations.histogram(
			"name", "field");

		Assert.assertNotNull(histogramAggregation);
	}

	@Test
	public void testMaxAggregation() {
		MaxAggregation maxAggregation = _aggregations.max("name", "field");

		Assert.assertNotNull(maxAggregation);
	}

	@Test
	public void testMaxBucketPipelineAggregation() {
		MaxBucketPipelineAggregation maxBucketPipelineAggregation =
			_aggregations.maxBucket("name", "bucketsPath");

		Assert.assertNotNull(maxBucketPipelineAggregation);
	}

	@Test
	public void testMinAggregation() {
		MinAggregation minAggregation = _aggregations.min("name", "field");

		Assert.assertNotNull(minAggregation);
	}

	@Test
	public void testMinBucketPipelineAggregation() {
		MinBucketPipelineAggregation minBucketPipelineAggregation =
			_aggregations.minBucket("name", "bucketsPath");

		Assert.assertNotNull(minBucketPipelineAggregation);
	}

	@Test
	public void testMissingAggregation() {
		MissingAggregation missingAggregation = _aggregations.missing(
			"name", "field");

		Assert.assertNotNull(missingAggregation);
	}

	@Test
	public void testMovingFunctionPipelineAggregation() {
		Script script = null;

		MovingFunctionPipelineAggregation movingFunctionPipelineAggregation =
			_aggregations.movingFunction("name", script, "bucketsPath", 0);

		Assert.assertNotNull(movingFunctionPipelineAggregation);
	}

	@Test
	public void testNestedAggregation() {
		NestedAggregation nestedAggregation = _aggregations.nested(
			"name", "path");

		Assert.assertNotNull(nestedAggregation);
	}

	@Test
	public void testPercentileRanksAggregation() {
		double values = 1.0;

		PercentileRanksAggregation percentileRanksAggregation =
			_aggregations.percentileRanks("name", "field", values);

		Assert.assertNotNull(percentileRanksAggregation);
	}

	@Test
	public void testPercentilesAggregation() {
		PercentilesAggregation percentilesAggregation =
			_aggregations.percentiles("name", "field");

		Assert.assertNotNull(percentilesAggregation);
	}

	@Test
	public void testPercentilesBucketPipelineAggregation() {
		PercentilesBucketPipelineAggregation
			percentilesBucketPipelineAggregation =
				_aggregations.percentilesBucket("name", "bucketsPath");

		Assert.assertNotNull(percentilesBucketPipelineAggregation);
	}

	@Test
	public void testRangeAggregation() {
		RangeAggregation rangeAggregation = _aggregations.range(
			"name", "field");

		Assert.assertNotNull(rangeAggregation);
	}

	@Test
	public void testReverseNestedAggregation() {
		ReverseNestedAggregation reverseNestedAggregation =
			_aggregations.reverseNested("name", "path");

		Assert.assertNotNull(reverseNestedAggregation);
	}

	@Test
	public void testSamplerAggregation() {
		SamplerAggregation samplerAggregation = _aggregations.sampler("name");

		Assert.assertNotNull(samplerAggregation);
	}

	@Test
	public void testScriptedMetricAggregation() {
		ScriptedMetricAggregation scriptedMetricAggregation =
			_aggregations.scriptedMetric("name");

		Assert.assertNotNull(scriptedMetricAggregation);
	}

	@Test
	public void testSerialDiffPipelineAggregation() {
		SerialDiffPipelineAggregation serialDiffPipelineAggregation =
			_aggregations.serialDiff("name", "bucketsPath");

		Assert.assertNotNull(serialDiffPipelineAggregation);
	}

	@Test
	public void testSignificantTermsAggregation() {
		SignificantTermsAggregation significantTermsAggregation =
			_aggregations.significantTerms("name", "field");

		Assert.assertNotNull(significantTermsAggregation);
	}

	@Test
	public void testSignificantTextAggregation() {
		SignificantTextAggregation significantTextAggregation =
			_aggregations.significantText("name", "field");

		Assert.assertNotNull(significantTextAggregation);
	}

	@Test
	public void testStatsAggregation() {
		StatsAggregation statsAggregation = _aggregations.stats(
			"name", "field");

		Assert.assertNotNull(statsAggregation);
	}

	@Test
	public void testStatsBucketPipelineAggregation() {
		StatsBucketPipelineAggregation statsBucketPipelineAggregation =
			_aggregations.statsBucket("name", "bucketsPath");

		Assert.assertNotNull(statsBucketPipelineAggregation);
	}

	@Test
	public void testSumAggregation() {
		SumAggregation sumAggregation = _aggregations.sum("name", "field");

		Assert.assertNotNull(sumAggregation);
	}

	@Test
	public void testSumBucketPipelineAggregation() {
		SumBucketPipelineAggregation sumBucketPipelineAggregation =
			_aggregations.sumBucket("name", "bucketsPath");

		Assert.assertNotNull(sumBucketPipelineAggregation);
	}

	@Test
	public void testTermsAggregation() {
		TermsAggregation termsAggregation = _aggregations.terms(
			"name", "field");

		Assert.assertNotNull(termsAggregation);
	}

	@Test
	public void testTopHitsAggregation() {
		TopHitsAggregation topHitsAggregation = _aggregations.topHits("name");

		Assert.assertNotNull(topHitsAggregation);
	}

	@Test
	public void testValueCountAggregation() {
		ValueCountAggregation valueCountAggregation = _aggregations.valueCount(
			"name", "field");

		Assert.assertNotNull(valueCountAggregation);
	}

	@Test
	public void testWeightedAvgAggregation() {
		WeightedAvgAggregation weightedAvgAggregation =
			_aggregations.weightedAvg("name", "valueField", "weightField");

		Assert.assertNotNull(weightedAvgAggregation);
	}

	@Inject
	private static Aggregations _aggregations;

}