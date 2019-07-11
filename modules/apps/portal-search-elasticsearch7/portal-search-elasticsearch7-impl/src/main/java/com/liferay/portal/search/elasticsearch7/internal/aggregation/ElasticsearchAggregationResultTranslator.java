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

package com.liferay.portal.search.elasticsearch7.internal.aggregation;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.AggregationResultTranslator;
import com.liferay.portal.search.aggregation.AggregationResults;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.BucketAggregationResult;
import com.liferay.portal.search.aggregation.bucket.ChildrenAggregation;
import com.liferay.portal.search.aggregation.bucket.ChildrenAggregationResult;
import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregationResult;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.bucket.DiversifiedSamplerAggregation;
import com.liferay.portal.search.aggregation.bucket.DiversifiedSamplerAggregationResult;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.bucket.FiltersAggregation;
import com.liferay.portal.search.aggregation.bucket.FiltersAggregationResult;
import com.liferay.portal.search.aggregation.bucket.GeoDistanceAggregation;
import com.liferay.portal.search.aggregation.bucket.GeoDistanceAggregationResult;
import com.liferay.portal.search.aggregation.bucket.GeoHashGridAggregation;
import com.liferay.portal.search.aggregation.bucket.GeoHashGridAggregationResult;
import com.liferay.portal.search.aggregation.bucket.GlobalAggregation;
import com.liferay.portal.search.aggregation.bucket.GlobalAggregationResult;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregationResult;
import com.liferay.portal.search.aggregation.bucket.MissingAggregation;
import com.liferay.portal.search.aggregation.bucket.MissingAggregationResult;
import com.liferay.portal.search.aggregation.bucket.NestedAggregation;
import com.liferay.portal.search.aggregation.bucket.NestedAggregationResult;
import com.liferay.portal.search.aggregation.bucket.RangeAggregation;
import com.liferay.portal.search.aggregation.bucket.RangeAggregationResult;
import com.liferay.portal.search.aggregation.bucket.ReverseNestedAggregation;
import com.liferay.portal.search.aggregation.bucket.ReverseNestedAggregationResult;
import com.liferay.portal.search.aggregation.bucket.SamplerAggregation;
import com.liferay.portal.search.aggregation.bucket.SamplerAggregationResult;
import com.liferay.portal.search.aggregation.bucket.SignificantTermsAggregation;
import com.liferay.portal.search.aggregation.bucket.SignificantTermsAggregationResult;
import com.liferay.portal.search.aggregation.bucket.SignificantTextAggregation;
import com.liferay.portal.search.aggregation.bucket.SignificantTextAggregationResult;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.AvgAggregation;
import com.liferay.portal.search.aggregation.metrics.AvgAggregationResult;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregation;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ExtendedStatsAggregation;
import com.liferay.portal.search.aggregation.metrics.ExtendedStatsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.GeoBoundsAggregation;
import com.liferay.portal.search.aggregation.metrics.GeoBoundsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.GeoCentroidAggregation;
import com.liferay.portal.search.aggregation.metrics.GeoCentroidAggregationResult;
import com.liferay.portal.search.aggregation.metrics.MaxAggregation;
import com.liferay.portal.search.aggregation.metrics.MaxAggregationResult;
import com.liferay.portal.search.aggregation.metrics.MinAggregation;
import com.liferay.portal.search.aggregation.metrics.MinAggregationResult;
import com.liferay.portal.search.aggregation.metrics.PercentileRanksAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentileRanksAggregationResult;
import com.liferay.portal.search.aggregation.metrics.PercentilesAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentilesAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregation;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregationResult;
import com.liferay.portal.search.aggregation.metrics.StatsAggregation;
import com.liferay.portal.search.aggregation.metrics.StatsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.SumAggregation;
import com.liferay.portal.search.aggregation.metrics.SumAggregationResult;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregation;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregation;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregationResult;
import com.liferay.portal.search.aggregation.metrics.WeightedAvgAggregation;
import com.liferay.portal.search.aggregation.metrics.WeightedAvgAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationResultTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.pipeline.ElasticsearchPipelineAggregationResultTranslator;
import com.liferay.portal.search.elasticsearch7.internal.hits.SearchHitsTranslator;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.geolocation.GeoLocationPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.join.aggregations.Children;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.filter.Filters;
import org.elasticsearch.search.aggregations.bucket.geogrid.GeoGrid;
import org.elasticsearch.search.aggregations.bucket.global.Global;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.missing.Missing;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.ReverseNested;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.sampler.Sampler;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.ExtendedStats;
import org.elasticsearch.search.aggregations.metrics.GeoBounds;
import org.elasticsearch.search.aggregations.metrics.GeoCentroid;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.aggregations.metrics.Min;
import org.elasticsearch.search.aggregations.metrics.PercentileRanks;
import org.elasticsearch.search.aggregations.metrics.Percentiles;
import org.elasticsearch.search.aggregations.metrics.ScriptedMetric;
import org.elasticsearch.search.aggregations.metrics.Stats;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.aggregations.metrics.TopHits;
import org.elasticsearch.search.aggregations.metrics.ValueCount;
import org.elasticsearch.search.aggregations.metrics.WeightedAvg;

/**
 * @author Michael C. Han
 */
public class ElasticsearchAggregationResultTranslator
	implements AggregationResultTranslator, AggregationResultTranslatorFactory,
			   PipelineAggregationResultTranslatorFactory {

	public ElasticsearchAggregationResultTranslator(
		org.elasticsearch.search.aggregations.Aggregation
			elasticsearchAggregation,
		AggregationResults aggregationResults,
		SearchHitsTranslator searchHitsTranslator, GeoBuilders geoBuilders) {

		_elasticsearchAggregation = elasticsearchAggregation;
		_aggregationResults = aggregationResults;
		_searchHitsTranslator = searchHitsTranslator;
		_geoBuilders = geoBuilders;
	}

	@Override
	public AggregationResultTranslator createAggregationResultTranslator(
		org.elasticsearch.search.aggregations.Aggregation
			elasticsearchAggregation) {

		return new ElasticsearchAggregationResultTranslator(
			elasticsearchAggregation, _aggregationResults,
			_searchHitsTranslator, _geoBuilders);
	}

	@Override
	public PipelineAggregationResultTranslator
		createPipelineAggregationResultTranslator(
			org.elasticsearch.search.aggregations.Aggregation
				elasticsearchAggregation) {

		return new ElasticsearchPipelineAggregationResultTranslator(
			elasticsearchAggregation, _aggregationResults);
	}

	@Override
	public AvgAggregationResult visit(AvgAggregation avgAggregation) {
		Avg avg = (Avg)_elasticsearchAggregation;

		return _aggregationResults.avg(avg.getName(), avg.getValue());
	}

	@Override
	public CardinalityAggregationResult visit(
		CardinalityAggregation cardinalityAggregation) {

		Cardinality cardinality = (Cardinality)_elasticsearchAggregation;

		return _aggregationResults.cardinality(
			cardinality.getName(), cardinality.getValue());
	}

	@Override
	public ChildrenAggregationResult visit(
		ChildrenAggregation childrenAggregation) {

		Children children = (Children)_elasticsearchAggregation;

		ChildrenAggregationResult childrenAggregationResult =
			_aggregationResults.children(
				children.getName(), children.getDocCount());

		childrenAggregationResult.addChildrenAggregationResults(
			translateAggregationResults(
				children.getAggregations(), childrenAggregation));

		return childrenAggregationResult;
	}

	@Override
	public DateHistogramAggregationResult visit(
		DateHistogramAggregation dateHistogramAggregation) {

		return translateBuckets(
			(Histogram)_elasticsearchAggregation,
			_aggregationResults.dateHistogram(
				_elasticsearchAggregation.getName()),
			dateHistogramAggregation);
	}

	@Override
	public RangeAggregationResult visit(
		DateRangeAggregation dateRangeAggregation) {

		return translateBuckets(
			(Range)_elasticsearchAggregation,
			_aggregationResults.range(_elasticsearchAggregation.getName()),
			dateRangeAggregation);
	}

	@Override
	public DiversifiedSamplerAggregationResult visit(
		DiversifiedSamplerAggregation diversifiedSamplerAggregation) {

		Sampler sampler = (Sampler)_elasticsearchAggregation;

		DiversifiedSamplerAggregationResult
			diversifiedSamplerAggregationResult =
				_aggregationResults.diversifiedSampler(
					sampler.getName(), sampler.getDocCount());

		diversifiedSamplerAggregationResult.addChildrenAggregationResults(
			translateAggregationResults(
				sampler.getAggregations(), diversifiedSamplerAggregation));

		return diversifiedSamplerAggregationResult;
	}

	@Override
	public ExtendedStatsAggregationResult visit(
		ExtendedStatsAggregation extendedStatsAggregation) {

		ExtendedStats extendedStats = (ExtendedStats)_elasticsearchAggregation;

		return _aggregationResults.extendedStats(
			extendedStats.getName(), extendedStats.getAvg(),
			extendedStats.getCount(), extendedStats.getMin(),
			extendedStats.getMax(), extendedStats.getSum(),
			extendedStats.getSumOfSquares(), extendedStats.getVariance(),
			extendedStats.getStdDeviation());
	}

	@Override
	public FilterAggregationResult visit(FilterAggregation filterAggregation) {
		Filter filter = (Filter)_elasticsearchAggregation;

		FilterAggregationResult filterAggregationResult =
			_aggregationResults.filter(filter.getName(), filter.getDocCount());

		filterAggregationResult.addChildrenAggregationResults(
			translateAggregationResults(
				filter.getAggregations(), filterAggregation));

		return filterAggregationResult;
	}

	@Override
	public FiltersAggregationResult visit(
		FiltersAggregation filtersAggregation) {

		Filters filters = (Filters)_elasticsearchAggregation;

		return translateBuckets(
			filters, _aggregationResults.filters(filters.getName()),
			filtersAggregation);
	}

	@Override
	public GeoBoundsAggregationResult visit(
		GeoBoundsAggregation geoBoundsAggregation) {

		GeoBounds geoBounds = (GeoBounds)_elasticsearchAggregation;

		return _aggregationResults.geoBounds(
			geoBounds.getName(), translateGeoPoint(geoBounds.topLeft()),
			translateGeoPoint(geoBounds.bottomRight()));
	}

	@Override
	public GeoCentroidAggregationResult visit(
		GeoCentroidAggregation geoCentroidAggregation) {

		GeoCentroid geoCentroid = (GeoCentroid)_elasticsearchAggregation;

		GeoPoint geoPoint = geoCentroid.centroid();

		return _aggregationResults.geoCentroid(
			geoCentroid.getName(), translateGeoPoint(geoPoint),
			geoCentroid.count());
	}

	@Override
	public GeoDistanceAggregationResult visit(
		GeoDistanceAggregation geoDistanceAggregation) {

		return translateBuckets(
			(Range)_elasticsearchAggregation,
			_aggregationResults.geoDistance(
				_elasticsearchAggregation.getName()),
			geoDistanceAggregation);
	}

	@Override
	public GeoHashGridAggregationResult visit(
		GeoHashGridAggregation geoHashGridAggregation) {

		GeoGrid geoGrid = (GeoGrid)_elasticsearchAggregation;

		return translateBuckets(
			geoGrid, _aggregationResults.geoHashGrid(geoGrid.getName()),
			geoHashGridAggregation);
	}

	@Override
	public GlobalAggregationResult visit(GlobalAggregation globalAggregation) {
		Global global = (Global)_elasticsearchAggregation;

		GlobalAggregationResult globalAggregationResult =
			_aggregationResults.global(global.getName(), global.getDocCount());

		globalAggregationResult.addChildrenAggregationResults(
			translateAggregationResults(
				global.getAggregations(), globalAggregation));

		return globalAggregationResult;
	}

	@Override
	public HistogramAggregationResult visit(
		HistogramAggregation histogramAggregation) {

		return translateBuckets(
			(Histogram)_elasticsearchAggregation,
			_aggregationResults.histogram(_elasticsearchAggregation.getName()),
			histogramAggregation);
	}

	@Override
	public MaxAggregationResult visit(MaxAggregation maxAggregation) {
		Max max = (Max)_elasticsearchAggregation;

		return _aggregationResults.max(max.getName(), max.getValue());
	}

	@Override
	public MinAggregationResult visit(MinAggregation minAggregation) {
		Min min = (Min)_elasticsearchAggregation;

		return _aggregationResults.min(min.getName(), min.getValue());
	}

	@Override
	public MissingAggregationResult visit(
		MissingAggregation missingAggregation) {

		Missing missing = (Missing)_elasticsearchAggregation;

		MissingAggregationResult missingAggregationResult =
			_aggregationResults.missing(
				missing.getName(), missing.getDocCount());

		missingAggregationResult.addChildrenAggregationResults(
			translateAggregationResults(
				missing.getAggregations(), missingAggregation));

		return missingAggregationResult;
	}

	@Override
	public NestedAggregationResult visit(NestedAggregation nestedAggregation) {
		Nested nested = (Nested)_elasticsearchAggregation;

		NestedAggregationResult nestedAggregationResult =
			_aggregationResults.nested(nested.getName(), nested.getDocCount());

		List<AggregationResult> aggregationResults =
			translateAggregationResults(
				nested.getAggregations(), nestedAggregation);

		nestedAggregationResult.addChildrenAggregationResults(
			aggregationResults);

		return nestedAggregationResult;
	}

	@Override
	public PercentileRanksAggregationResult visit(
		PercentileRanksAggregation percentileRanksAggregation) {

		PercentileRanks percentileRanks =
			(PercentileRanks)_elasticsearchAggregation;

		PercentileRanksAggregationResult percentileRanksAggregationResult =
			_aggregationResults.percentileRanks(percentileRanks.getName());

		percentileRanks.forEach(
			percentileRank -> percentileRanksAggregationResult.addPercentile(
				percentileRank.getValue(), percentileRank.getPercent()));

		return percentileRanksAggregationResult;
	}

	@Override
	public PercentilesAggregationResult visit(
		PercentilesAggregation percentilesAggregation) {

		Percentiles percentiles = (Percentiles)_elasticsearchAggregation;

		PercentilesAggregationResult percentilesAggregationResult =
			_aggregationResults.percentiles(percentiles.getName());

		percentiles.forEach(
			percentile -> percentilesAggregationResult.addPercentile(
				percentile.getPercent(), percentile.getValue()));

		return percentilesAggregationResult;
	}

	@Override
	public RangeAggregationResult visit(RangeAggregation rangeAggregation) {
		return translateBuckets(
			(Range)_elasticsearchAggregation,
			_aggregationResults.range(_elasticsearchAggregation.getName()),
			rangeAggregation);
	}

	@Override
	public ReverseNestedAggregationResult visit(
		ReverseNestedAggregation reverseNestedAggregation) {

		ReverseNested reverseNested = (ReverseNested)_elasticsearchAggregation;

		ReverseNestedAggregationResult reverseNestedAggregationResult =
			_aggregationResults.reverseNested(
				reverseNested.getName(), reverseNested.getDocCount());

		reverseNestedAggregationResult.addChildrenAggregationResults(
			translateAggregationResults(
				reverseNested.getAggregations(), reverseNestedAggregation));

		return reverseNestedAggregationResult;
	}

	@Override
	public SamplerAggregationResult visit(
		SamplerAggregation samplerAggregation) {

		Sampler sampler = (Sampler)_elasticsearchAggregation;

		SamplerAggregationResult samplerAggregationResult =
			_aggregationResults.sampler(
				sampler.getName(), sampler.getDocCount());

		samplerAggregationResult.addChildrenAggregationResults(
			translateAggregationResults(
				sampler.getAggregations(), samplerAggregation));

		return samplerAggregationResult;
	}

	@Override
	public ScriptedMetricAggregationResult visit(
		ScriptedMetricAggregation scriptedMetricAggregation) {

		ScriptedMetric scriptedMetric =
			(ScriptedMetric)_elasticsearchAggregation;

		return _aggregationResults.scriptedMetric(
			scriptedMetric.getName(), scriptedMetric.aggregation());
	}

	@Override
	public SignificantTermsAggregationResult visit(
		SignificantTermsAggregation significantTermsAggregation) {

		Terms terms = (Terms)_elasticsearchAggregation;

		return translateBuckets(
			terms,
			_aggregationResults.significantTerms(
				terms.getName(), terms.getDocCountError(),
				terms.getSumOfOtherDocCounts()),
			significantTermsAggregation);
	}

	@Override
	public SignificantTextAggregationResult visit(
		SignificantTextAggregation significantTextAggregation) {

		Terms terms = (Terms)_elasticsearchAggregation;

		return translateBuckets(
			terms,
			_aggregationResults.significantText(
				terms.getName(), terms.getDocCountError(),
				terms.getSumOfOtherDocCounts()),
			significantTextAggregation);
	}

	@Override
	public StatsAggregationResult visit(StatsAggregation statsAggregation) {
		Stats stats = (Stats)_elasticsearchAggregation;

		return _aggregationResults.stats(
			stats.getName(), stats.getAvg(), stats.getCount(), stats.getMin(),
			stats.getMax(), stats.getSum());
	}

	@Override
	public SumAggregationResult visit(SumAggregation sumAggregation) {
		Sum sum = (Sum)_elasticsearchAggregation;

		return _aggregationResults.sum(sum.getName(), sum.getValue());
	}

	@Override
	public TermsAggregationResult visit(TermsAggregation termsAggregation) {
		Terms terms = (Terms)_elasticsearchAggregation;

		return translateBuckets(
			terms,
			_aggregationResults.terms(
				terms.getName(), terms.getDocCountError(),
				terms.getSumOfOtherDocCounts()),
			termsAggregation);
	}

	@Override
	public TopHitsAggregationResult visit(
		TopHitsAggregation topHitsAggregation) {

		TopHits topHits = (TopHits)_elasticsearchAggregation;

		SearchHits searchHits = topHits.getHits();

		return _aggregationResults.topHits(
			topHits.getName(), _searchHitsTranslator.translate(searchHits));
	}

	@Override
	public ValueCountAggregationResult visit(
		ValueCountAggregation valueCountAggregation) {

		ValueCount valueCount = (ValueCount)_elasticsearchAggregation;

		return _aggregationResults.valueCount(
			valueCount.getName(), valueCount.getValue());
	}

	@Override
	public WeightedAvgAggregationResult visit(
		WeightedAvgAggregation weightedAvgAggregation) {

		WeightedAvg weightedAvg = (WeightedAvg)_elasticsearchAggregation;

		return _aggregationResults.weightedAvg(
			weightedAvg.getName(), weightedAvg.getValue());
	}

	protected Stream<AggregationResult> translate(
		Aggregations elasticsearchAggregations, Aggregation aggregation) {

		ElasticsearchAggregationResultsTranslator
			elasticsearchAggregationResultsTranslator =
				new ElasticsearchAggregationResultsTranslator(
					this, this, aggregation::getChildAggregation,
					aggregation::getPipelineAggregation);

		return elasticsearchAggregationResultsTranslator.translate(
			elasticsearchAggregations);
	}

	protected List<AggregationResult> translateAggregationResults(
		Aggregations elasticsearchAggregations, Aggregation aggregation) {

		List<AggregationResult> aggregationResults = new ArrayList<>();

		Stream<AggregationResult> stream = translate(
			elasticsearchAggregations, aggregation);

		stream.forEach(aggregationResults::add);

		return aggregationResults;
	}

	protected <T extends BucketAggregationResult> T translateBuckets(
		MultiBucketsAggregation multiBucketsAggregation,
		T bucketAggregationResult, Aggregation aggregation) {

		List<? extends MultiBucketsAggregation.Bucket>
			multiBucketAggregationBuckets =
				multiBucketsAggregation.getBuckets();

		multiBucketAggregationBuckets.forEach(
			multiBucketAggregationBucket -> {
				Bucket bucket = bucketAggregationResult.addBucket(
					multiBucketAggregationBucket.getKeyAsString(),
					multiBucketAggregationBucket.getDocCount());

				Stream<AggregationResult> stream = translate(
					multiBucketAggregationBucket.getAggregations(),
					aggregation);

				stream.forEach(bucket::addChildAggregationResult);
			});

		return bucketAggregationResult;
	}

	protected GeoLocationPoint translateGeoPoint(GeoPoint geoPoint) {
		if (geoPoint == null) {
			return null;
		}

		return _geoBuilders.geoLocationPoint(
			geoPoint.getLat(), geoPoint.getLon());
	}

	private final AggregationResults _aggregationResults;
	private final org.elasticsearch.search.aggregations.Aggregation
		_elasticsearchAggregation;
	private final GeoBuilders _geoBuilders;
	private final SearchHitsTranslator _searchHitsTranslator;

}