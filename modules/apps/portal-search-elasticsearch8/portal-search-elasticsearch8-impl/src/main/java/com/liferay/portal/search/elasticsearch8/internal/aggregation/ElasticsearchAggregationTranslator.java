/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.aggregation;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.AggregationVisitor;
import com.liferay.portal.search.aggregation.FieldAggregation;
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
import com.liferay.portal.search.aggregation.bucket.Range;
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
import com.liferay.portal.search.aggregation.metrics.PercentilesMethod;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregation;
import com.liferay.portal.search.aggregation.metrics.StatsAggregation;
import com.liferay.portal.search.aggregation.metrics.SumAggregation;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregation;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregation;
import com.liferay.portal.search.aggregation.metrics.WeightedAvgAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.bucket.OrderTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.bucket.SignificantTermsAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.bucket.SignificantTextAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.bucket.TermsAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.metrics.ScriptedMetricAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.metrics.TopHitsAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.metrics.WeightedAvgAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.geolocation.DistanceUnitTranslator;
import com.liferay.portal.search.elasticsearch8.internal.geolocation.GeoDistanceTypeTranslator;
import com.liferay.portal.search.elasticsearch8.internal.geolocation.GeoLocationPointTranslator;
import com.liferay.portal.search.elasticsearch8.internal.query.ElasticsearchQueryTranslator;
import com.liferay.portal.search.query.QueryTranslator;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.join.aggregations.ChildrenAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.geogrid.GeoGridAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.HistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.LongBounds;
import org.elasticsearch.search.aggregations.bucket.nested.ReverseNestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.AbstractRangeBuilder;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.GeoDistanceAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregator;
import org.elasticsearch.search.aggregations.bucket.sampler.DiversifiedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.sampler.SamplerAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ExtendedStatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.GeoBoundsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.PercentileRanksAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.PercentilesAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValuesSourceAggregationBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = AggregationTranslator.class
)
public class ElasticsearchAggregationTranslator
	implements AggregationTranslator<AggregationBuilder>,
			   AggregationVisitor<AggregationBuilder> {

	@Override
	public AggregationBuilder translate(Aggregation aggregation) {
		return aggregation.accept(this);
	}

	@Override
	public AggregationBuilder visit(AvgAggregation avgAggregation) {
		return _assemble(
			AggregationBuilders.avg(avgAggregation.getName()), avgAggregation);
	}

	@Override
	public AggregationBuilder visit(
		CardinalityAggregation cardinalityAggregation) {

		CardinalityAggregationBuilder cardinalityAggregationBuilder =
			AggregationBuilders.cardinality(cardinalityAggregation.getName());

		if (cardinalityAggregation.getPrecisionThreshold() != null) {
			cardinalityAggregationBuilder.precisionThreshold(
				cardinalityAggregation.getPrecisionThreshold());
		}

		return _assemble(cardinalityAggregationBuilder, cardinalityAggregation);
	}

	@Override
	public AggregationBuilder visit(ChildrenAggregation childrenAggregation) {
		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> new ChildrenAggregationBuilder(
				baseMetricsAggregation.getName(),
				childrenAggregation.getChildType()),
			childrenAggregation, this, _pipelineAggregationTranslator);
	}

	@Override
	public AggregationBuilder visit(
		DateHistogramAggregation dateHistogramAggregation) {

		DateHistogramAggregationBuilder dateHistogramAggregationBuilder =
			AggregationBuilders.dateHistogram(
				dateHistogramAggregation.getName());

		if (ListUtil.isNotEmpty(dateHistogramAggregation.getOrders())) {
			List<BucketOrder> bucketOrders = _orderTranslator.translate(
				dateHistogramAggregation.getOrders());

			dateHistogramAggregationBuilder.order(bucketOrders);
		}

		if ((dateHistogramAggregation.getMaxBound() != null) &&
			(dateHistogramAggregation.getMinBound() != null)) {

			LongBounds longBounds = new LongBounds(
				dateHistogramAggregation.getMinBound(),
				dateHistogramAggregation.getMaxBound());

			dateHistogramAggregationBuilder.extendedBounds(longBounds);
		}

		if (dateHistogramAggregation.getMinDocCount() != null) {
			dateHistogramAggregationBuilder.minDocCount(
				dateHistogramAggregation.getMinDocCount());
		}

		if (dateHistogramAggregation.getDateHistogramInterval() != null) {
			dateHistogramAggregationBuilder.dateHistogramInterval(
				new DateHistogramInterval(
					dateHistogramAggregation.getDateHistogramInterval()));
		}

		if (dateHistogramAggregation.getInterval() != null) {
			dateHistogramAggregationBuilder.interval(
				dateHistogramAggregation.getInterval());
		}

		if (dateHistogramAggregation.getOffset() != null) {
			dateHistogramAggregationBuilder.offset(
				dateHistogramAggregation.getOffset());
		}

		return _assemble(
			dateHistogramAggregationBuilder, dateHistogramAggregation);
	}

	@Override
	public AggregationBuilder visit(DateRangeAggregation dateRangeAggregation) {
		DateRangeAggregationBuilder dateRangeAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.dateRange(
					baseMetricsAggregation.getName()),
				dateRangeAggregation, this, _pipelineAggregationTranslator);

		populateRangeAggregationBuilder(
			dateRangeAggregation, dateRangeAggregationBuilder);

		return dateRangeAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(
		DiversifiedSamplerAggregation diversifiedSamplerAggregation) {

		DiversifiedAggregationBuilder diversifiedAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation ->
					AggregationBuilders.diversifiedSampler(
						diversifiedSamplerAggregation.getName()),
				diversifiedSamplerAggregation, this,
				_pipelineAggregationTranslator);

		if (diversifiedSamplerAggregation.getExecutionHint() != null) {
			diversifiedAggregationBuilder.executionHint(
				diversifiedSamplerAggregation.getExecutionHint());
		}

		if (diversifiedSamplerAggregation.getMaxDocsPerValue() != null) {
			diversifiedAggregationBuilder.maxDocsPerValue(
				diversifiedSamplerAggregation.getMaxDocsPerValue());
		}

		if (diversifiedSamplerAggregation.getShardSize() != null) {
			diversifiedAggregationBuilder.shardSize(
				diversifiedSamplerAggregation.getShardSize());
		}

		return diversifiedAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(
		ExtendedStatsAggregation extendedStatsAggregation) {

		ExtendedStatsAggregationBuilder extendedStatsAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.extendedStats(
					baseMetricsAggregation.getName()),
				extendedStatsAggregation, this, _pipelineAggregationTranslator);

		if (extendedStatsAggregation.getSigma() != null) {
			extendedStatsAggregationBuilder.sigma(
				extendedStatsAggregation.getSigma());
		}

		return extendedStatsAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(FilterAggregation filterAggregation) {
		QueryBuilder filterQueryBuilder = _queryTranslator.translate(
			filterAggregation.getFilterQuery());

		FilterAggregationBuilder filterAggregationBuilder =
			AggregationBuilders.filter(
				filterAggregation.getName(), filterQueryBuilder);

		_baseAggregationTranslator.translate(
			filterAggregationBuilder, filterAggregation, this,
			_pipelineAggregationTranslator);

		return filterAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(FiltersAggregation filtersAggregation) {
		List<FiltersAggregation.KeyedQuery> keyedQueries =
			filtersAggregation.getKeyedQueries();

		List<FiltersAggregator.KeyedFilter> keyedFilters = new ArrayList<>(
			keyedQueries.size());

		keyedQueries.forEach(
			keyedQuery -> {
				QueryBuilder filterQueryBuilder = _queryTranslator.translate(
					keyedQuery.getQuery());

				keyedFilters.add(
					new FiltersAggregator.KeyedFilter(
						keyedQuery.getKey(), filterQueryBuilder));
			});

		FiltersAggregationBuilder filtersAggregationBuilder =
			AggregationBuilders.filters(
				filtersAggregation.getName(),
				keyedFilters.toArray(
					new FiltersAggregator.KeyedFilter[keyedQueries.size()]));

		if (filtersAggregation.getOtherBucket() != null) {
			filtersAggregationBuilder.otherBucket(
				filtersAggregation.getOtherBucket());
		}

		if (filtersAggregation.getOtherBucketKey() != null) {
			filtersAggregationBuilder.otherBucketKey(
				filtersAggregation.getOtherBucketKey());
		}

		_baseAggregationTranslator.translate(
			filtersAggregationBuilder, filtersAggregation, this,
			_pipelineAggregationTranslator);

		return filtersAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(GeoBoundsAggregation geoBoundsAggregation) {
		GeoBoundsAggregationBuilder geoBoundsAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.geoBounds(
					geoBoundsAggregation.getName()),
				geoBoundsAggregation, this, _pipelineAggregationTranslator);

		if (geoBoundsAggregation.getWrapLongitude() != null) {
			geoBoundsAggregationBuilder.wrapLongitude(
				geoBoundsAggregation.getWrapLongitude());
		}

		return geoBoundsAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(
		GeoCentroidAggregation geoCentroidAggregation) {

		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.geoCentroid(
				geoCentroidAggregation.getName()),
			geoCentroidAggregation, this, _pipelineAggregationTranslator);
	}

	@Override
	public AggregationBuilder visit(
		GeoDistanceAggregation geoDistanceAggregation) {

		GeoPoint geoPoint = GeoLocationPointTranslator.translate(
			geoDistanceAggregation.getGeoLocationPoint());

		GeoDistanceAggregationBuilder geoDistanceAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.geoDistance(
					baseMetricsAggregation.getName(), geoPoint),
				geoDistanceAggregation, this, _pipelineAggregationTranslator);

		if (geoDistanceAggregation.getDistanceUnit() != null) {
			geoDistanceAggregationBuilder.unit(
				_distanceUnitTranslator.translate(
					geoDistanceAggregation.getDistanceUnit()));
		}

		if (geoDistanceAggregation.getGeoDistanceType() != null) {
			GeoDistance geoDistance = _geoDistanceTypeTranslator.translate(
				geoDistanceAggregation.getGeoDistanceType());

			geoDistanceAggregationBuilder.distanceType(geoDistance);
		}

		if (geoDistanceAggregation.getKeyed() != null) {
			geoDistanceAggregationBuilder.keyed(
				geoDistanceAggregation.getKeyed());
		}

		List<Range> rangeAggregationRanges = geoDistanceAggregation.getRanges();

		rangeAggregationRanges.forEach(
			rangeAggregationRange -> {
				GeoDistanceAggregationBuilder.Range range =
					new GeoDistanceAggregationBuilder.Range(
						rangeAggregationRange.getKey(),
						rangeAggregationRange.getFrom(),
						rangeAggregationRange.getTo());

				geoDistanceAggregationBuilder.addRange(range);
			});

		return geoDistanceAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(
		GeoHashGridAggregation geoHashGridAggregation) {

		GeoGridAggregationBuilder geoGridAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.geohashGrid(
					geoHashGridAggregation.getName()),
				geoHashGridAggregation, this, _pipelineAggregationTranslator);

		if (geoHashGridAggregation.getPrecision() != null) {
			geoGridAggregationBuilder.precision(
				geoHashGridAggregation.getPrecision());
		}

		if (geoHashGridAggregation.getShardSize() != null) {
			geoGridAggregationBuilder.shardSize(
				geoHashGridAggregation.getShardSize());
		}

		if (geoHashGridAggregation.getSize() != null) {
			geoGridAggregationBuilder.size(geoHashGridAggregation.getSize());
		}

		return geoGridAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(GlobalAggregation globalAggregation) {
		return _assemble(
			AggregationBuilders.global(globalAggregation.getName()),
			globalAggregation);
	}

	@Override
	public AggregationBuilder visit(HistogramAggregation histogramAggregation) {
		HistogramAggregationBuilder histogramAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.histogram(
					baseMetricsAggregation.getName()),
				histogramAggregation, this, _pipelineAggregationTranslator);

		if (ListUtil.isNotEmpty(histogramAggregation.getOrders())) {
			List<BucketOrder> bucketOrders = _orderTranslator.translate(
				histogramAggregation.getOrders());

			histogramAggregationBuilder.order(bucketOrders);
		}

		if ((histogramAggregation.getMaxBound() != null) &&
			(histogramAggregation.getMinBound() != null)) {

			histogramAggregationBuilder.extendedBounds(
				histogramAggregation.getMinBound(),
				histogramAggregation.getMaxBound());
		}

		if (histogramAggregation.getMinDocCount() != null) {
			histogramAggregationBuilder.minDocCount(
				histogramAggregation.getMinDocCount());
		}

		if (histogramAggregation.getInterval() != null) {
			histogramAggregationBuilder.interval(
				histogramAggregation.getInterval());
		}

		if (histogramAggregation.getOffset() != null) {
			histogramAggregationBuilder.offset(
				histogramAggregation.getOffset());
		}

		return histogramAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(MaxAggregation maxAggregation) {
		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.max(
				baseMetricsAggregation.getName()),
			maxAggregation, this, _pipelineAggregationTranslator);
	}

	@Override
	public AggregationBuilder visit(MinAggregation minAggregation) {
		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.min(
				baseMetricsAggregation.getName()),
			minAggregation, this, _pipelineAggregationTranslator);
	}

	@Override
	public AggregationBuilder visit(MissingAggregation missingAggregation) {
		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.missing(
				baseMetricsAggregation.getName()),
			missingAggregation, this, _pipelineAggregationTranslator);
	}

	@Override
	public AggregationBuilder visit(NestedAggregation nestedAggregation) {
		return _assemble(
			AggregationBuilders.nested(
				nestedAggregation.getName(), nestedAggregation.getPath()),
			nestedAggregation);
	}

	@Override
	public AggregationBuilder visit(
		PercentileRanksAggregation percentileRanksAggregation) {

		PercentileRanksAggregationBuilder percentileRanksAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.percentileRanks(
					baseMetricsAggregation.getName(),
					percentileRanksAggregation.getValues()),
				percentileRanksAggregation, this,
				_pipelineAggregationTranslator);

		if (percentileRanksAggregation.getCompression() != null) {
			percentileRanksAggregationBuilder.compression(
				percentileRanksAggregation.getCompression());
		}

		if (percentileRanksAggregation.getHdrSignificantValueDigits() != null) {
			percentileRanksAggregationBuilder.numberOfSignificantValueDigits(
				percentileRanksAggregation.getHdrSignificantValueDigits());
		}

		if (percentileRanksAggregation.getKeyed() != null) {
			percentileRanksAggregationBuilder.keyed(
				percentileRanksAggregation.getKeyed());
		}

		if (percentileRanksAggregation.getPercentilesMethod() != null) {
			PercentilesMethod percentilesMethod =
				percentileRanksAggregation.getPercentilesMethod();

			percentileRanksAggregationBuilder.method(
				org.elasticsearch.search.aggregations.metrics.PercentilesMethod.
					valueOf(percentilesMethod.name()));
		}

		return percentileRanksAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(
		PercentilesAggregation percentilesAggregation) {

		PercentilesAggregationBuilder percentilesAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.percentiles(
					baseMetricsAggregation.getName()),
				percentilesAggregation, this, _pipelineAggregationTranslator);

		if (percentilesAggregation.getCompression() != null) {
			percentilesAggregationBuilder.compression(
				percentilesAggregation.getCompression());
		}

		if (percentilesAggregation.getHdrSignificantValueDigits() != null) {
			percentilesAggregationBuilder.numberOfSignificantValueDigits(
				percentilesAggregation.getHdrSignificantValueDigits());
		}

		if (percentilesAggregation.getKeyed() != null) {
			percentilesAggregationBuilder.keyed(
				percentilesAggregation.getKeyed());
		}

		double[] percents = percentilesAggregation.getPercents();

		if (percents != null) {
			percentilesAggregationBuilder.percentiles(percents);
		}

		if (percentilesAggregation.getPercentilesMethod() != null) {
			PercentilesMethod percentilesMethod =
				percentilesAggregation.getPercentilesMethod();

			percentilesAggregationBuilder.method(
				org.elasticsearch.search.aggregations.metrics.PercentilesMethod.
					valueOf(percentilesMethod.name()));
		}

		return percentilesAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(RangeAggregation rangeAggregation) {
		RangeAggregationBuilder rangeAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.range(
					baseMetricsAggregation.getName()),
				rangeAggregation, this, _pipelineAggregationTranslator);

		populateRangeAggregationBuilder(
			rangeAggregation, rangeAggregationBuilder);

		return rangeAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(
		ReverseNestedAggregation reverseNestedAggregation) {

		ReverseNestedAggregationBuilder reverseNestedAggregationBuilder =
			AggregationBuilders.reverseNested(
				reverseNestedAggregation.getName());

		if (reverseNestedAggregation.getPath() != null) {
			reverseNestedAggregationBuilder.path(
				reverseNestedAggregation.getPath());
		}

		return _assemble(
			reverseNestedAggregationBuilder, reverseNestedAggregation);
	}

	@Override
	public AggregationBuilder visit(SamplerAggregation samplerAggregation) {
		SamplerAggregationBuilder samplerAggregationBuilder =
			AggregationBuilders.sampler(samplerAggregation.getName());

		if (samplerAggregation.getShardSize() != null) {
			samplerAggregationBuilder.shardSize(
				samplerAggregation.getShardSize());
		}

		return _assemble(samplerAggregationBuilder, samplerAggregation);
	}

	@Override
	public AggregationBuilder visit(
		ScriptedMetricAggregation scriptedMetricAggregation) {

		return _scriptedMetricAggregationTranslator.translate(
			scriptedMetricAggregation, this, _pipelineAggregationTranslator);
	}

	@Override
	public AggregationBuilder visit(
		SignificantTermsAggregation significantTermsAggregation) {

		return _assemble(
			_significantTermsAggregationTranslator.translate(
				significantTermsAggregation),
			significantTermsAggregation);
	}

	@Override
	public AggregationBuilder visit(
		SignificantTextAggregation significantTextAggregation) {

		return _significantTextAggregationTranslator.translate(
			significantTextAggregation, this, _pipelineAggregationTranslator);
	}

	@Override
	public AggregationBuilder visit(StatsAggregation statsAggregation) {
		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.stats(
				baseMetricsAggregation.getName()),
			statsAggregation, this, _pipelineAggregationTranslator);
	}

	@Override
	public AggregationBuilder visit(SumAggregation sumAggregation) {
		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.sum(
				baseMetricsAggregation.getName()),
			sumAggregation, this, _pipelineAggregationTranslator);
	}

	@Override
	public AggregationBuilder visit(TermsAggregation termsAggregation) {
		return _assemble(
			_termsAggregationTranslator.translate(termsAggregation),
			termsAggregation);
	}

	@Override
	public AggregationBuilder visit(TopHitsAggregation topHitsAggregation) {
		return _topHitsAggregationTranslator.translate(
			topHitsAggregation, this, _pipelineAggregationTranslator);
	}

	@Override
	public AggregationBuilder visit(
		ValueCountAggregation valueCountAggregation) {

		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.count(
				baseMetricsAggregation.getName()),
			valueCountAggregation, this, _pipelineAggregationTranslator);
	}

	@Override
	public AggregationBuilder visit(
		WeightedAvgAggregation weightedAvgAggregation) {

		return _weightedAvgAggregationTranslator.translate(
			weightedAvgAggregation, this, _pipelineAggregationTranslator);
	}

	protected void populateRangeAggregationBuilder(
		RangeAggregation rangeAggregation,
		AbstractRangeBuilder abstractRangeBuilder) {

		if (rangeAggregation.getFormat() != null) {
			abstractRangeBuilder.format(rangeAggregation.getFormat());
		}

		if (rangeAggregation.getKeyed() != null) {
			abstractRangeBuilder.keyed(rangeAggregation.getKeyed());
		}

		List<Range> rangeAggregationRanges = rangeAggregation.getRanges();

		rangeAggregationRanges.forEach(
			rangeAggregationRange -> {
				RangeAggregator.Range range = new RangeAggregator.Range(
					rangeAggregationRange.getKey(),
					rangeAggregationRange.getFrom(),
					rangeAggregationRange.getFromAsString(),
					rangeAggregationRange.getTo(),
					rangeAggregationRange.getToAsString());

				abstractRangeBuilder.addRange(range);
			});
	}

	private <AB extends AggregationBuilder> AB _assemble(
		AB aggregationBuilder, Aggregation aggregation) {

		AggregationBuilderAssemblerFactory aggregationBuilderAssemblerFactory =
			new AggregationBuilderAssemblerFactory(
				_pipelineAggregationTranslator);

		AggregationBuilderAssemblerImpl aggregationBuilderAssemblerImpl =
			aggregationBuilderAssemblerFactory.getAggregationBuilderAssembler(
				this);

		return aggregationBuilderAssemblerImpl.assembleAggregation(
			aggregationBuilder, aggregation);
	}

	private <VSAB extends ValuesSourceAggregationBuilder> VSAB _assemble(
		VSAB valuesSourceAggregationBuilder,
		FieldAggregation fieldAggregation) {

		AggregationBuilderAssemblerFactory aggregationBuilderAssemblerFactory =
			new AggregationBuilderAssemblerFactory(
				_pipelineAggregationTranslator);

		AggregationBuilderAssemblerImpl aggregationBuilderAssemblerImpl =
			aggregationBuilderAssemblerFactory.getAggregationBuilderAssembler(
				this);

		return aggregationBuilderAssemblerImpl.assembleFieldAggregation(
			valuesSourceAggregationBuilder, fieldAggregation);
	}

	private final BaseAggregationTranslator _baseAggregationTranslator =
		new BaseAggregationTranslator();
	private final BaseFieldAggregationTranslator
		_baseFieldAggregationTranslator = new BaseFieldAggregationTranslator();
	private final DistanceUnitTranslator _distanceUnitTranslator =
		new DistanceUnitTranslator();
	private final GeoDistanceTypeTranslator _geoDistanceTypeTranslator =
		new GeoDistanceTypeTranslator();
	private final OrderTranslator _orderTranslator = new OrderTranslator();

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private PipelineAggregationTranslator<PipelineAggregationBuilder>
		_pipelineAggregationTranslator;

	private final QueryTranslator<QueryBuilder> _queryTranslator =
		new ElasticsearchQueryTranslator();
	private final ScriptedMetricAggregationTranslator
		_scriptedMetricAggregationTranslator =
			new ScriptedMetricAggregationTranslator();
	private final SignificantTermsAggregationTranslator
		_significantTermsAggregationTranslator =
			new SignificantTermsAggregationTranslator();
	private final SignificantTextAggregationTranslator
		_significantTextAggregationTranslator =
			new SignificantTextAggregationTranslator();
	private final TermsAggregationTranslator _termsAggregationTranslator =
		new TermsAggregationTranslator();
	private final TopHitsAggregationTranslator _topHitsAggregationTranslator =
		new TopHitsAggregationTranslator();
	private final WeightedAvgAggregationTranslator
		_weightedAvgAggregationTranslator =
			new WeightedAvgAggregationTranslator();

}