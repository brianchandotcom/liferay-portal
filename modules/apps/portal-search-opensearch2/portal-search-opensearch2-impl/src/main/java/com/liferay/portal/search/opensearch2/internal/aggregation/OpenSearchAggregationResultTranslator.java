/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.aggregation;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
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
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.opensearch2.internal.hits.HitsMetadataTranslator;
import com.liferay.portal.search.opensearch2.internal.util.OpenSearchStringUtil;

import java.util.List;
import java.util.Map;

import org.opensearch.client.opensearch._types.GeoBounds;
import org.opensearch.client.opensearch._types.GeoLocation;
import org.opensearch.client.opensearch._types.LatLonGeoLocation;
import org.opensearch.client.opensearch._types.TopLeftBottomRightGeoBounds;
import org.opensearch.client.opensearch._types.aggregations.Aggregate;
import org.opensearch.client.opensearch._types.aggregations.AvgAggregate;
import org.opensearch.client.opensearch._types.aggregations.Buckets;
import org.opensearch.client.opensearch._types.aggregations.CardinalityAggregate;
import org.opensearch.client.opensearch._types.aggregations.ChildrenAggregate;
import org.opensearch.client.opensearch._types.aggregations.DateHistogramAggregate;
import org.opensearch.client.opensearch._types.aggregations.DateHistogramBucket;
import org.opensearch.client.opensearch._types.aggregations.DateRangeAggregate;
import org.opensearch.client.opensearch._types.aggregations.ExtendedStatsAggregate;
import org.opensearch.client.opensearch._types.aggregations.FilterAggregate;
import org.opensearch.client.opensearch._types.aggregations.FiltersAggregate;
import org.opensearch.client.opensearch._types.aggregations.FiltersBucket;
import org.opensearch.client.opensearch._types.aggregations.GeoBoundsAggregate;
import org.opensearch.client.opensearch._types.aggregations.GeoCentroidAggregate;
import org.opensearch.client.opensearch._types.aggregations.GeoDistanceAggregate;
import org.opensearch.client.opensearch._types.aggregations.GeoHashGridAggregate;
import org.opensearch.client.opensearch._types.aggregations.GeoHashGridBucket;
import org.opensearch.client.opensearch._types.aggregations.GlobalAggregate;
import org.opensearch.client.opensearch._types.aggregations.HdrPercentileRanksAggregate;
import org.opensearch.client.opensearch._types.aggregations.HdrPercentilesAggregate;
import org.opensearch.client.opensearch._types.aggregations.HistogramAggregate;
import org.opensearch.client.opensearch._types.aggregations.HistogramBucket;
import org.opensearch.client.opensearch._types.aggregations.LongTermsAggregate;
import org.opensearch.client.opensearch._types.aggregations.LongTermsBucket;
import org.opensearch.client.opensearch._types.aggregations.MaxAggregate;
import org.opensearch.client.opensearch._types.aggregations.MinAggregate;
import org.opensearch.client.opensearch._types.aggregations.MissingAggregate;
import org.opensearch.client.opensearch._types.aggregations.NestedAggregate;
import org.opensearch.client.opensearch._types.aggregations.Percentiles;
import org.opensearch.client.opensearch._types.aggregations.RangeAggregate;
import org.opensearch.client.opensearch._types.aggregations.RangeBucket;
import org.opensearch.client.opensearch._types.aggregations.ReverseNestedAggregate;
import org.opensearch.client.opensearch._types.aggregations.SamplerAggregate;
import org.opensearch.client.opensearch._types.aggregations.ScriptedMetricAggregate;
import org.opensearch.client.opensearch._types.aggregations.StatsAggregate;
import org.opensearch.client.opensearch._types.aggregations.StringTermsAggregate;
import org.opensearch.client.opensearch._types.aggregations.StringTermsBucket;
import org.opensearch.client.opensearch._types.aggregations.SumAggregate;
import org.opensearch.client.opensearch._types.aggregations.TDigestPercentileRanksAggregate;
import org.opensearch.client.opensearch._types.aggregations.TDigestPercentilesAggregate;
import org.opensearch.client.opensearch._types.aggregations.TopHitsAggregate;
import org.opensearch.client.opensearch._types.aggregations.ValueCountAggregate;
import org.opensearch.client.opensearch._types.aggregations.WeightedAvgAggregate;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class OpenSearchAggregationResultTranslator
	implements AggregationResultTranslator, AggregationResultTranslatorFactory,
			   PipelineAggregationResultTranslatorFactory {

	public OpenSearchAggregationResultTranslator(
		Aggregate aggregate, AggregationResults aggregationResults,
		GeoBuilders geoBuilders,
		HitsMetadataTranslator hitsMetadataTranslator) {

		_aggregate = aggregate;
		_aggregationResults = aggregationResults;
		_geoBuilders = geoBuilders;
		_hitsMetadataTranslator = hitsMetadataTranslator;
	}

	@Override
	public AggregationResultTranslator createAggregationResultTranslator(
		Aggregate aggregate) {

		return new OpenSearchAggregationResultTranslator(
			aggregate, _aggregationResults, _geoBuilders,
			_hitsMetadataTranslator);
	}

	@Override
	public PipelineAggregationResultTranslator
		createPipelineAggregationResultTranslator(Aggregate aggregate) {

		return new OpenSearchPipelineAggregationResultTranslator(
			aggregate, _aggregationResults);
	}

	@Override
	public AvgAggregationResult visit(AvgAggregation avgAggregation) {
		AvgAggregate avgAggregate = _aggregate.avg();

		return _aggregationResults.avg(
			avgAggregation.getName(), avgAggregate.value());
	}

	@Override
	public CardinalityAggregationResult visit(
		CardinalityAggregation cardinalityAggregation) {

		CardinalityAggregate cardinalityAggregate = _aggregate.cardinality();

		return _aggregationResults.cardinality(
			cardinalityAggregation.getName(), cardinalityAggregate.value());
	}

	@Override
	public ChildrenAggregationResult visit(
		ChildrenAggregation childrenAggregation) {

		ChildrenAggregate childrenAggregate = _aggregate.children();

		ChildrenAggregationResult childrenAggregationResult =
			_aggregationResults.children(
				childrenAggregation.getName(), childrenAggregate.docCount());

		childrenAggregationResult.addChildrenAggregationResults(
			translate(childrenAggregation, childrenAggregate.aggregations()));

		return childrenAggregationResult;
	}

	@Override
	public DateHistogramAggregationResult visit(
		DateHistogramAggregation dateHistogramAggregation) {

		DateHistogramAggregate dateHistogramAggregate =
			_aggregate.dateHistogram();

		DateHistogramAggregationResult dateHistogramAggregationResult =
			_aggregationResults.dateHistogram(
				dateHistogramAggregation.getName());

		Buckets<DateHistogramBucket> buckets = dateHistogramAggregate.buckets();

		if (buckets.isArray()) {
			ListUtil.isNotEmptyForEach(
				buckets.array(),
				dateHistogramBucket -> {
					Bucket bucket = dateHistogramAggregationResult.addBucket(
						OpenSearchStringUtil.getFirstStringValue(
							dateHistogramBucket::keyAsString,
							dateHistogramBucket::key),
						dateHistogramBucket.docCount());

					_addBucketChildAggregationResults(
						dateHistogramAggregation,
						dateHistogramBucket.aggregations(), bucket);
				});
		}
		else {
			MapUtil.isNotEmptyForEach(
				buckets.keyed(),
				(key, dateHistogramBucket) -> {
					Bucket bucket = dateHistogramAggregationResult.addBucket(
						key, dateHistogramBucket.docCount());

					_addBucketChildAggregationResults(
						dateHistogramAggregation,
						dateHistogramBucket.aggregations(), bucket);
				});
		}

		return dateHistogramAggregationResult;
	}

	@Override
	public RangeAggregationResult visit(
		DateRangeAggregation dateRangeAggregation) {

		DateRangeAggregate dateRangeAggregate = _aggregate.dateRange();

		return _translateRangeBuckets(
			dateRangeAggregation, dateRangeAggregate.buckets(),
			_aggregationResults.range(dateRangeAggregation.getName()));
	}

	@Override
	public DiversifiedSamplerAggregationResult visit(
		DiversifiedSamplerAggregation diversifiedSamplerAggregation) {

		SamplerAggregate samplerAggregate = _aggregate.sampler();

		DiversifiedSamplerAggregationResult
			diversifiedSamplerAggregationResult =
				_aggregationResults.diversifiedSampler(
					diversifiedSamplerAggregation.getName(),
					samplerAggregate.docCount());

		diversifiedSamplerAggregationResult.addChildrenAggregationResults(
			translate(
				diversifiedSamplerAggregation,
				samplerAggregate.aggregations()));

		return diversifiedSamplerAggregationResult;
	}

	@Override
	public ExtendedStatsAggregationResult visit(
		ExtendedStatsAggregation extendedStatsAggregation) {

		ExtendedStatsAggregate extendedStatsAggregate =
			_aggregate.extendedStats();

		return _aggregationResults.extendedStats(
			extendedStatsAggregation.getName(), extendedStatsAggregate.avg(),
			extendedStatsAggregate.count(), extendedStatsAggregate.min(),
			extendedStatsAggregate.max(), extendedStatsAggregate.sum(),
			extendedStatsAggregate.sumOfSquares(),
			extendedStatsAggregate.variance(),
			extendedStatsAggregate.stdDeviation());
	}

	@Override
	public FilterAggregationResult visit(FilterAggregation filterAggregation) {
		FilterAggregate filterAggregate = _aggregate.filter();

		FilterAggregationResult filterAggregationResult =
			_aggregationResults.filter(
				filterAggregation.getName(), filterAggregate.docCount());

		filterAggregationResult.addChildrenAggregationResults(
			translate(filterAggregation, filterAggregate.aggregations()));

		return filterAggregationResult;
	}

	@Override
	public FiltersAggregationResult visit(
		FiltersAggregation filtersAggregation) {

		FiltersAggregate filtersAggregate = _aggregate.filters();

		FiltersAggregationResult filtersAggregationResult =
			_aggregationResults.filters(filtersAggregation.getName());

		Buckets<FiltersBucket> buckets = filtersAggregate.buckets();

		MapUtil.isNotEmptyForEach(
			buckets.keyed(),
			(key, filtersBucket) -> {
				Bucket bucket = filtersAggregationResult.addBucket(
					key, filtersBucket.docCount());

				_addBucketChildAggregationResults(
					filtersAggregation, filtersBucket.aggregations(), bucket);
			});

		return filtersAggregationResult;
	}

	@Override
	public GeoBoundsAggregationResult visit(
		GeoBoundsAggregation geoBoundsAggregation) {

		GeoBoundsAggregate geoBoundsAggregate = _aggregate.geoBounds();

		GeoBounds geoBounds = geoBoundsAggregate.bounds();

		if (!geoBounds.isTlbr()) {
			throw new UnsupportedOperationException();
		}

		TopLeftBottomRightGeoBounds topLeftBottomRightGeoBounds =
			geoBounds.tlbr();

		return _aggregationResults.geoBounds(
			geoBoundsAggregation.getName(),
			_translateGeoLocation(topLeftBottomRightGeoBounds.topLeft()),
			_translateGeoLocation(topLeftBottomRightGeoBounds.bottomRight()));
	}

	@Override
	public GeoCentroidAggregationResult visit(
		GeoCentroidAggregation geoCentroidAggregation) {

		GeoCentroidAggregate geoCentroidAggregate = _aggregate.geoCentroid();

		GeoLocation geoLocation = geoCentroidAggregate.location();

		return _aggregationResults.geoCentroid(
			geoCentroidAggregation.getName(),
			_translateGeoLocation(geoLocation), geoCentroidAggregate.count());
	}

	@Override
	public GeoDistanceAggregationResult visit(
		GeoDistanceAggregation geoDistanceAggregation) {

		GeoDistanceAggregate geoDistanceAggregate = _aggregate.geoDistance();

		return _translateRangeBuckets(
			geoDistanceAggregation, geoDistanceAggregate.buckets(),
			_aggregationResults.geoDistance(geoDistanceAggregation.getName()));
	}

	@Override
	public GeoHashGridAggregationResult visit(
		GeoHashGridAggregation geoHashGridAggregation) {

		GeoHashGridAggregate geoHashGridAggregate = _aggregate.geohashGrid();

		GeoHashGridAggregationResult geoHashGridAggregationResult =
			_aggregationResults.geoHashGrid(geoHashGridAggregation.getName());

		Buckets<GeoHashGridBucket> buckets = geoHashGridAggregate.buckets();

		if (buckets.isArray()) {
			ListUtil.isNotEmptyForEach(
				buckets.array(),
				geoHashGridBucket -> {
					Bucket bucket = geoHashGridAggregationResult.addBucket(
						geoHashGridBucket.key(), geoHashGridBucket.docCount());

					_addBucketChildAggregationResults(
						geoHashGridAggregation,
						geoHashGridBucket.aggregations(), bucket);
				});
		}
		else {
			MapUtil.isNotEmptyForEach(
				buckets.keyed(),
				(key, geoHashGridBucket) -> {
					Bucket bucket = geoHashGridAggregationResult.addBucket(
						key, geoHashGridBucket.docCount());

					_addBucketChildAggregationResults(
						geoHashGridAggregation,
						geoHashGridBucket.aggregations(), bucket);
				});
		}

		return geoHashGridAggregationResult;
	}

	@Override
	public GlobalAggregationResult visit(GlobalAggregation globalAggregation) {
		GlobalAggregate globalAggregate = _aggregate.global();

		GlobalAggregationResult globalAggregationResult =
			_aggregationResults.global(
				globalAggregation.getName(), globalAggregate.docCount());

		globalAggregationResult.addChildrenAggregationResults(
			translate(globalAggregation, globalAggregate.aggregations()));

		return globalAggregationResult;
	}

	@Override
	public HistogramAggregationResult visit(
		HistogramAggregation histogramAggregation) {

		HistogramAggregate histogramAggregate = _aggregate.histogram();

		HistogramAggregationResult histogramAggregationResult =
			_aggregationResults.histogram(histogramAggregation.getName());

		Buckets<HistogramBucket> buckets = histogramAggregate.buckets();

		if (buckets.isArray()) {
			ListUtil.isNotEmptyForEach(
				buckets.array(),
				histogramBucket -> {
					Bucket bucket = histogramAggregationResult.addBucket(
						OpenSearchStringUtil.getFirstStringValue(
							histogramBucket::keyAsString, histogramBucket::key),
						histogramBucket.docCount());

					_addBucketChildAggregationResults(
						histogramAggregation, histogramBucket.aggregations(),
						bucket);
				});
		}
		else {
			MapUtil.isNotEmptyForEach(
				buckets.keyed(),
				(key, histogramBucket) -> {
					Bucket bucket = histogramAggregationResult.addBucket(
						key, histogramBucket.docCount());

					_addBucketChildAggregationResults(
						histogramAggregation, histogramBucket.aggregations(),
						bucket);
				});
		}

		return histogramAggregationResult;
	}

	@Override
	public MaxAggregationResult visit(MaxAggregation maxAggregation) {
		MaxAggregate maxAggregate = _aggregate.max();

		return _aggregationResults.max(
			maxAggregation.getName(), maxAggregate.value());
	}

	@Override
	public MinAggregationResult visit(MinAggregation minAggregation) {
		MinAggregate minAggregate = _aggregate.min();

		return _aggregationResults.min(
			minAggregation.getName(), minAggregate.value());
	}

	@Override
	public MissingAggregationResult visit(
		MissingAggregation missingAggregation) {

		MissingAggregate missingAggregate = _aggregate.missing();

		MissingAggregationResult missingAggregationResult =
			_aggregationResults.missing(
				missingAggregation.getName(), missingAggregate.docCount());

		missingAggregationResult.addChildrenAggregationResults(
			translate(missingAggregation, missingAggregate.aggregations()));

		return missingAggregationResult;
	}

	@Override
	public NestedAggregationResult visit(NestedAggregation nestedAggregation) {
		NestedAggregate nestedAggregate = _aggregate.nested();

		NestedAggregationResult nestedAggregationResult =
			_aggregationResults.nested(
				nestedAggregation.getName(), nestedAggregate.docCount());

		List<AggregationResult> aggregationResults = translate(
			nestedAggregation, nestedAggregate.aggregations());

		nestedAggregationResult.addChildrenAggregationResults(
			aggregationResults);

		return nestedAggregationResult;
	}

	@Override
	public PercentileRanksAggregationResult visit(
		PercentileRanksAggregation percentileRanksAggregation) {

		Percentiles percentiles = null;

		if (_aggregate.isHdrPercentileRanks()) {
			HdrPercentileRanksAggregate hdrPercentileRanksAggregate =
				_aggregate.hdrPercentileRanks();

			percentiles = hdrPercentileRanksAggregate.values();
		}
		else if (_aggregate.isTdigestPercentileRanks()) {
			TDigestPercentileRanksAggregate tDigestPercentileRanksAggregate =
				_aggregate.tdigestPercentileRanks();

			percentiles = tDigestPercentileRanksAggregate.values();
		}
		else {
			throw new UnsupportedOperationException();
		}

		PercentileRanksAggregationResult percentileRanksAggregationResult =
			_aggregationResults.percentileRanks(
				percentileRanksAggregation.getName());

		if (percentiles.isArray()) {
			ListUtil.isNotEmptyForEach(
				percentiles.array(),
				arrayPercentilesItem ->
					percentileRanksAggregationResult.addPercentile(
						Double.valueOf(arrayPercentilesItem.key()),
						arrayPercentilesItem.value()));
		}
		else {
			MapUtil.isNotEmptyForEach(
				percentiles.keyed(),
				(key, value) -> percentileRanksAggregationResult.addPercentile(
					Double.valueOf(key), GetterUtil.getDouble(value)));
		}

		return percentileRanksAggregationResult;
	}

	@Override
	public PercentilesAggregationResult visit(
		PercentilesAggregation percentilesAggregation) {

		Percentiles percentiles;

		if (_aggregate.isHdrPercentiles()) {
			HdrPercentilesAggregate hdrPercentilesAggregate =
				_aggregate.hdrPercentiles();

			percentiles = hdrPercentilesAggregate.values();
		}
		else if (_aggregate.isTdigestPercentiles()) {
			TDigestPercentilesAggregate tDigestPercentilesAggregate =
				_aggregate.tdigestPercentiles();

			percentiles = tDigestPercentilesAggregate.values();
		}
		else {
			throw new UnsupportedOperationException();
		}

		PercentilesAggregationResult percentilesAggregationResult =
			_aggregationResults.percentiles(percentilesAggregation.getName());

		if (percentiles.isArray()) {
			ListUtil.isNotEmptyForEach(
				percentiles.array(),
				percentilesItem -> percentilesAggregationResult.addPercentile(
					Double.valueOf(percentilesItem.key()),
					GetterUtil.getDouble(percentilesItem.value())));
		}
		else {
			MapUtil.isNotEmptyForEach(
				percentiles.keyed(),
				(key, value) -> percentilesAggregationResult.addPercentile(
					Double.valueOf(key), GetterUtil.getDouble(value)));
		}

		return percentilesAggregationResult;
	}

	@Override
	public RangeAggregationResult visit(RangeAggregation rangeAggregation) {
		RangeAggregate rangeAggregate = _aggregate.range();

		return _translateRangeBuckets(
			rangeAggregation, rangeAggregate.buckets(),
			_aggregationResults.range(rangeAggregation.getName()));
	}

	@Override
	public ReverseNestedAggregationResult visit(
		ReverseNestedAggregation reverseNestedAggregation) {

		ReverseNestedAggregate reverseNestedAggregate =
			_aggregate.reverseNested();

		ReverseNestedAggregationResult reverseNestedAggregationResult =
			_aggregationResults.reverseNested(
				reverseNestedAggregation.getName(),
				reverseNestedAggregate.docCount());

		reverseNestedAggregationResult.addChildrenAggregationResults(
			translate(
				reverseNestedAggregation,
				reverseNestedAggregate.aggregations()));

		return reverseNestedAggregationResult;
	}

	@Override
	public SamplerAggregationResult visit(
		SamplerAggregation samplerAggregation) {

		SamplerAggregate samplerAggregate = _aggregate.sampler();

		SamplerAggregationResult samplerAggregationResult =
			_aggregationResults.sampler(
				samplerAggregation.getName(), samplerAggregate.docCount());

		samplerAggregationResult.addChildrenAggregationResults(
			translate(samplerAggregation, samplerAggregate.aggregations()));

		return samplerAggregationResult;
	}

	@Override
	public ScriptedMetricAggregationResult visit(
		ScriptedMetricAggregation scriptedMetricAggregation) {

		ScriptedMetricAggregate scriptedMetricAggregate =
			_aggregate.scriptedMetric();

		return _aggregationResults.scriptedMetric(
			scriptedMetricAggregation.getName(),
			scriptedMetricAggregate.value());
	}

	@Override
	public SignificantTermsAggregationResult visit(
		SignificantTermsAggregation significantTermsAggregation) {

		throw new UnsupportedOperationException();
	}

	@Override
	public SignificantTextAggregationResult visit(
		SignificantTextAggregation significantTextAggregation) {

		throw new UnsupportedOperationException();
	}

	@Override
	public StatsAggregationResult visit(StatsAggregation statsAggregation) {
		StatsAggregate statsAggregate = _aggregate.stats();

		return _aggregationResults.stats(
			statsAggregation.getName(), statsAggregate.avg(),
			statsAggregate.count(), statsAggregate.min(), statsAggregate.max(),
			statsAggregate.sum());
	}

	@Override
	public SumAggregationResult visit(SumAggregation sumAggregation) {
		SumAggregate sumAggregate = _aggregate.sum();

		return _aggregationResults.sum(
			sumAggregation.getName(), sumAggregate.value());
	}

	@Override
	public TermsAggregationResult visit(TermsAggregation termsAggregation) {
		if (_aggregate.isSterms()) {
			StringTermsAggregate stringTermsAggregate = _aggregate.sterms();

			return _translateStringTermBuckets(
				termsAggregation, stringTermsAggregate.buckets(),
				_aggregationResults.terms(
					termsAggregation.getName(),
					stringTermsAggregate.docCountErrorUpperBound(),
					stringTermsAggregate.sumOtherDocCount()));
		}
		else if (_aggregate.isLterms()) {
			LongTermsAggregate longTermsAggregate = _aggregate.lterms();

			return _translateLongTermBuckets(
				termsAggregation, longTermsAggregate.buckets(),
				_aggregationResults.terms(
					termsAggregation.getName(),
					longTermsAggregate.docCountErrorUpperBound(),
					longTermsAggregate.sumOtherDocCount()));
		}

		throw new UnsupportedOperationException();
	}

	@Override
	public TopHitsAggregationResult visit(
		TopHitsAggregation topHitsAggregation) {

		TopHitsAggregate topHitsAggregate = _aggregate.topHits();

		return _aggregationResults.topHits(
			topHitsAggregation.getName(),
			_hitsMetadataTranslator.translate(topHitsAggregate.hits()));
	}

	@Override
	public ValueCountAggregationResult visit(
		ValueCountAggregation valueCountAggregation) {

		ValueCountAggregate valueCountAggregate = _aggregate.valueCount();

		Double value = Double.valueOf(valueCountAggregate.value());

		return _aggregationResults.valueCount(
			valueCountAggregation.getName(), value.longValue());
	}

	@Override
	public WeightedAvgAggregationResult visit(
		WeightedAvgAggregation weightedAvgAggregation) {

		WeightedAvgAggregate weightedAvgAggregate = _aggregate.weightedAvg();

		return _aggregationResults.weightedAvg(
			weightedAvgAggregation.getName(), weightedAvgAggregate.value());
	}

	protected List<AggregationResult> translate(
		Aggregation aggregation, Map<String, Aggregate> aggregations) {

		OpenSearchAggregationResultsTranslator
			openSearchAggregationResultsTranslator =
				new OpenSearchAggregationResultsTranslator(
					aggregation::getChildAggregation, this,
					aggregation::getPipelineAggregation, this);

		return openSearchAggregationResultsTranslator.translate(aggregations);
	}

	private void _addBucketChildAggregationResults(
		Aggregation aggregation, Map<String, Aggregate> aggregations,
		Bucket bucket) {

		for (AggregationResult aggregationResult :
				translate(aggregation, aggregations)) {

			bucket.addChildAggregationResult(aggregationResult);
		}
	}

	private GeoLocationPoint _translateGeoLocation(GeoLocation geoLocation) {
		if ((geoLocation == null) || !geoLocation.isLatlon()) {
			return null;
		}

		LatLonGeoLocation latLonGeoLocation = geoLocation.latlon();

		return _geoBuilders.geoLocationPoint(
			latLonGeoLocation.lat(), latLonGeoLocation.lon());
	}

	private <T extends BucketAggregationResult> T _translateLongTermBuckets(
		Aggregation aggregation, Buckets<LongTermsBucket> buckets,
		T bucketAggregationResult) {

		if (buckets.isArray()) {
			ListUtil.isNotEmptyForEach(
				buckets.array(),
				longTermsBucket -> {
					Bucket bucket = bucketAggregationResult.addBucket(
						longTermsBucket.key(), longTermsBucket.docCount());

					_addBucketChildAggregationResults(
						aggregation, longTermsBucket.aggregations(), bucket);
				});
		}
		else {
			MapUtil.isNotEmptyForEach(
				buckets.keyed(),
				(key, longTermsBucket) -> {
					Bucket bucket = bucketAggregationResult.addBucket(
						longTermsBucket.key(), longTermsBucket.docCount());

					_addBucketChildAggregationResults(
						aggregation, longTermsBucket.aggregations(), bucket);
				});
		}

		return bucketAggregationResult;
	}

	private <T extends BucketAggregationResult> T _translateRangeBuckets(
		Aggregation aggregation, Buckets<RangeBucket> buckets,
		T bucketAggregationResult) {

		if (buckets.isArray()) {
			ListUtil.isNotEmptyForEach(
				buckets.array(),
				rangeBucket -> {
					Bucket bucket = bucketAggregationResult.addBucket(
						rangeBucket.key(), rangeBucket.docCount());

					_addBucketChildAggregationResults(
						aggregation, rangeBucket.aggregations(), bucket);
				});
		}
		else {
			MapUtil.isNotEmptyForEach(
				buckets.keyed(),
				(key, rangeBucket) -> {
					Bucket bucket = bucketAggregationResult.addBucket(
						key, rangeBucket.docCount());

					_addBucketChildAggregationResults(
						aggregation, rangeBucket.aggregations(), bucket);
				});
		}

		return bucketAggregationResult;
	}

	private <T extends BucketAggregationResult> T _translateStringTermBuckets(
		Aggregation aggregation, Buckets<StringTermsBucket> buckets,
		T bucketAggregationResult) {

		if (buckets.isArray()) {
			ListUtil.isNotEmptyForEach(
				buckets.array(),
				stringTermsBucket -> {
					Bucket bucket = bucketAggregationResult.addBucket(
						stringTermsBucket.key(), stringTermsBucket.docCount());

					_addBucketChildAggregationResults(
						aggregation, stringTermsBucket.aggregations(), bucket);
				});
		}
		else {
			MapUtil.isNotEmptyForEach(
				buckets.keyed(),
				(key, stringTermsBucket) -> {
					Bucket bucket = bucketAggregationResult.addBucket(
						stringTermsBucket.key(), stringTermsBucket.docCount());

					_addBucketChildAggregationResults(
						aggregation, stringTermsBucket.aggregations(), bucket);
				});
		}

		return bucketAggregationResult;
	}

	private final Aggregate _aggregate;
	private final AggregationResults _aggregationResults;
	private final GeoBuilders _geoBuilders;
	private final HitsMetadataTranslator _hitsMetadataTranslator;

}