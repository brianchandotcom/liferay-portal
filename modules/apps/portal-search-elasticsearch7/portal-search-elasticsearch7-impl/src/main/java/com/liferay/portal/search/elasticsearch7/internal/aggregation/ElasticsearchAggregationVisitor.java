/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation;

import com.liferay.portal.search.aggregation.AggregationVisitor;
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
import com.liferay.portal.search.aggregation.metrics.PercentilesMethod;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregation;
import com.liferay.portal.search.aggregation.metrics.StatsAggregation;
import com.liferay.portal.search.aggregation.metrics.SumAggregation;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregation;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregation;
import com.liferay.portal.search.aggregation.metrics.WeightedAvgAggregation;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.DateHistogramAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.DateRangeAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.FilterAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.FiltersAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.GeoDistanceAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.HistogramAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.RangeAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.SignificantTermsAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.SignificantTextAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket.TermsAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.ScriptedMetricAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.TopHitsAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics.WeightedAvgAggregationTranslator;

import org.elasticsearch.join.aggregations.ChildrenAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.geogrid.GeoGridAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.global.GlobalAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ReverseNestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.sampler.DiversifiedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.sampler.SamplerAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.SignificantTermsAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ExtendedStatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.GeoBoundsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.PercentileRanksAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.PercentilesAggregationBuilder;

/**
 * @author Michael C. Han
 */
public class ElasticsearchAggregationVisitor
	implements AggregationVisitor<AggregationBuilder> {

	public static final ElasticsearchAggregationVisitor INSTANCE =
		new ElasticsearchAggregationVisitor();

	@Override
	public AggregationBuilder visit(AvgAggregation avgAggregation) {
		AvgAggregationBuilder avgAggregationBuilder = AggregationBuilders.avg(
			avgAggregation.getName());

		_baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> avgAggregationBuilder, avgAggregation);

		return avgAggregationBuilder;
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

		_baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> cardinalityAggregationBuilder,
			cardinalityAggregation);

		return cardinalityAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(ChildrenAggregation childrenAggregation) {
		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> new ChildrenAggregationBuilder(
				baseMetricsAggregation.getName(),
				childrenAggregation.getChildType()),
			childrenAggregation);
	}

	@Override
	public AggregationBuilder visit(
		DateHistogramAggregation dateHistogramAggregation) {

		DateHistogramAggregationBuilder dateHistogramAggregationBuilder =
			_dateHistogramAggregationTranslator.translate(
				dateHistogramAggregation);

		_baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> dateHistogramAggregationBuilder,
			dateHistogramAggregation);

		return dateHistogramAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(DateRangeAggregation dateRangeAggregation) {
		return _dateRangeAggregationTranslator.translate(dateRangeAggregation);
	}

	@Override
	public AggregationBuilder visit(
		DiversifiedSamplerAggregation diversifiedSamplerAggregation) {

		DiversifiedAggregationBuilder diversifiedAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation ->
					AggregationBuilders.diversifiedSampler(
						diversifiedSamplerAggregation.getName()),
				diversifiedSamplerAggregation);

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
				extendedStatsAggregation);

		if (extendedStatsAggregation.getSigma() != null) {
			extendedStatsAggregationBuilder.sigma(
				extendedStatsAggregation.getSigma());
		}

		return extendedStatsAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(FilterAggregation filterAggregation) {
		return _filterAggregationTranslator.translate(filterAggregation);
	}

	@Override
	public AggregationBuilder visit(FiltersAggregation filtersAggregation) {
		return _filtersAggregationTranslator.translate(filtersAggregation);
	}

	@Override
	public AggregationBuilder visit(GeoBoundsAggregation geoBoundsAggregation) {
		GeoBoundsAggregationBuilder geoBoundsAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.geoBounds(
					geoBoundsAggregation.getName()),
				geoBoundsAggregation);

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
			geoCentroidAggregation);
	}

	@Override
	public AggregationBuilder visit(
		GeoDistanceAggregation geoDistanceAggregation) {

		return _geoDistanceAggregationTranslator.translate(
			geoDistanceAggregation);
	}

	@Override
	public AggregationBuilder visit(
		GeoHashGridAggregation geoHashGridAggregation) {

		GeoGridAggregationBuilder geoGridAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.geohashGrid(
					geoHashGridAggregation.getName()),
				geoHashGridAggregation);

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
		GlobalAggregationBuilder globalAggregationBuilder =
			AggregationBuilders.global(globalAggregation.getName());

		_baseAggregationTranslator.translate(
			globalAggregationBuilder, globalAggregation);

		return globalAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(HistogramAggregation histogramAggregation) {
		return _histogramAggregationTranslator.translate(histogramAggregation);
	}

	@Override
	public AggregationBuilder visit(MaxAggregation maxAggregation) {
		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.max(
				baseMetricsAggregation.getName()),
			maxAggregation);
	}

	@Override
	public AggregationBuilder visit(MinAggregation minAggregation) {
		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.min(
				baseMetricsAggregation.getName()),
			minAggregation);
	}

	@Override
	public AggregationBuilder visit(MissingAggregation missingAggregation) {
		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.missing(
				baseMetricsAggregation.getName()),
			missingAggregation);
	}

	@Override
	public AggregationBuilder visit(NestedAggregation nestedAggregation) {
		NestedAggregationBuilder nestedAggregationBuilder =
			AggregationBuilders.nested(
				nestedAggregation.getName(), nestedAggregation.getPath());

		_baseAggregationTranslator.translate(
			nestedAggregationBuilder, nestedAggregation);

		return nestedAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(
		PercentileRanksAggregation percentileRanksAggregation) {

		PercentileRanksAggregationBuilder percentileRanksAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.percentileRanks(
					baseMetricsAggregation.getName(),
					percentileRanksAggregation.getValues()),
				percentileRanksAggregation);

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
				percentilesAggregation);

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
		return _rangeAggregationTranslator.translate(rangeAggregation);
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

		_baseAggregationTranslator.translate(
			reverseNestedAggregationBuilder, reverseNestedAggregation);

		return reverseNestedAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(SamplerAggregation samplerAggregation) {
		SamplerAggregationBuilder samplerAggregationBuilder =
			AggregationBuilders.sampler(samplerAggregation.getName());

		if (samplerAggregation.getShardSize() != null) {
			samplerAggregationBuilder.shardSize(
				samplerAggregation.getShardSize());
		}

		_baseAggregationTranslator.translate(
			samplerAggregationBuilder, samplerAggregation);

		return samplerAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(
		ScriptedMetricAggregation scriptedMetricAggregation) {

		return _scriptedMetricAggregationTranslator.translate(
			scriptedMetricAggregation);
	}

	@Override
	public AggregationBuilder visit(
		SignificantTermsAggregation significantTermsAggregation) {

		SignificantTermsAggregationBuilder significantTermsAggregationBuilder =
			_significantTermsAggregationTranslator.translate(
				significantTermsAggregation);

		_baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> significantTermsAggregationBuilder,
			significantTermsAggregation);

		return significantTermsAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(
		SignificantTextAggregation significantTextAggregation) {

		return _significantTextAggregationTranslator.translate(
			significantTextAggregation);
	}

	@Override
	public AggregationBuilder visit(StatsAggregation statsAggregation) {
		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.stats(
				baseMetricsAggregation.getName()),
			statsAggregation);
	}

	@Override
	public AggregationBuilder visit(SumAggregation sumAggregation) {
		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.sum(
				baseMetricsAggregation.getName()),
			sumAggregation);
	}

	@Override
	public AggregationBuilder visit(TermsAggregation termsAggregation) {
		TermsAggregationBuilder termsAggregationBuilder =
			_termsAggregationTranslator.translate(termsAggregation);

		_baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> termsAggregationBuilder,
			termsAggregation);

		return termsAggregationBuilder;
	}

	@Override
	public AggregationBuilder visit(TopHitsAggregation topHitsAggregation) {
		return _topHitsAggregationTranslator.translate(topHitsAggregation);
	}

	@Override
	public AggregationBuilder visit(
		ValueCountAggregation valueCountAggregation) {

		return _baseFieldAggregationTranslator.translate(
			baseMetricsAggregation -> AggregationBuilders.count(
				baseMetricsAggregation.getName()),
			valueCountAggregation);
	}

	@Override
	public AggregationBuilder visit(
		WeightedAvgAggregation weightedAvgAggregation) {

		return _weightedAvgAggregationTranslator.translate(
			weightedAvgAggregation);
	}

	private ElasticsearchAggregationVisitor() {
	}

	private final BaseAggregationTranslator _baseAggregationTranslator =
		new BaseAggregationTranslator();
	private final BaseFieldAggregationTranslator
		_baseFieldAggregationTranslator = new BaseFieldAggregationTranslator();
	private final DateHistogramAggregationTranslator
		_dateHistogramAggregationTranslator =
			new DateHistogramAggregationTranslator();
	private final DateRangeAggregationTranslator
		_dateRangeAggregationTranslator = new DateRangeAggregationTranslator();
	private final FilterAggregationTranslator _filterAggregationTranslator =
		new FilterAggregationTranslator();
	private final FiltersAggregationTranslator _filtersAggregationTranslator =
		new FiltersAggregationTranslator();
	private final GeoDistanceAggregationTranslator
		_geoDistanceAggregationTranslator =
			new GeoDistanceAggregationTranslator();
	private final HistogramAggregationTranslator
		_histogramAggregationTranslator = new HistogramAggregationTranslator();
	private final RangeAggregationTranslator _rangeAggregationTranslator =
		new RangeAggregationTranslator();
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