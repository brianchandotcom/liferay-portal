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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface AggregationVisitor<T> {

	public T visit(AvgAggregation avgAggregation);

	public T visit(CardinalityAggregation cardinalityAggregation);

	public T visit(ChildrenAggregation childrenAggregation);

	public T visit(DateHistogramAggregation dateHistogramAggregation);

	public T visit(DateRangeAggregation dateRangeAggregation);

	public T visit(DiversifiedSamplerAggregation diversifiedSamplerAggregation);

	public T visit(ExtendedStatsAggregation extendedStatsAggregation);

	public T visit(FilterAggregation filterAggregation);

	public T visit(FiltersAggregation filtersAggregation);

	public T visit(GeoBoundsAggregation geoBoundsAggregation);

	public T visit(GeoCentroidAggregation geoCentroidAggregation);

	public T visit(GeoDistanceAggregation geoDistanceAggregation);

	public T visit(GeoHashGridAggregation geoHashGridAggregation);

	public T visit(GlobalAggregation globalAggregation);

	public T visit(HistogramAggregation histogramAggregation);

	public T visit(MaxAggregation maxAggregation);

	public T visit(MinAggregation minAggregation);

	public T visit(MissingAggregation missingAggregation);

	public T visit(NestedAggregation nestedAggregation);

	public T visit(PercentileRanksAggregation percentileRanksAggregation);

	public T visit(PercentilesAggregation percentilesAggregation);

	public T visit(RangeAggregation rangeAggregation);

	public T visit(ReverseNestedAggregation reverseNestedAggregation);

	public T visit(SamplerAggregation samplerAggregation);

	public T visit(ScriptedMetricAggregation scriptedMetricAggregation);

	public T visit(SignificantTermsAggregation significantTermsAggregation);

	public T visit(SignificantTextAggregation significantTextAggregation);

	public T visit(StatsAggregation statsAggregation);

	public T visit(SumAggregation sumAggregation);

	public T visit(TermsAggregation termsAggregation);

	public T visit(TopHitsAggregation topHitsAggregation);

	public T visit(ValueCountAggregation valueCountAggregation);

	public T visit(WeightedAvgAggregation weightedAvgAggregation);

}