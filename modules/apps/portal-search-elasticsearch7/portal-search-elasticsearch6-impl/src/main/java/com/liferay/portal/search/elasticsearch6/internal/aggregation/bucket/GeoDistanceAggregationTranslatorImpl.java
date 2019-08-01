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

package com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.bucket.GeoDistanceAggregation;
import com.liferay.portal.search.aggregation.bucket.Range;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.BaseFieldAggregationTranslator;
import com.liferay.portal.search.elasticsearch6.internal.geolocation.DistanceUnitTranslator;
import com.liferay.portal.search.elasticsearch6.internal.geolocation.GeoDistanceTypeTranslator;
import com.liferay.portal.search.elasticsearch6.internal.geolocation.GeoLocationPointTranslator;

import java.util.List;

import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.GeoDistanceAggregationBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = GeoDistanceAggregationTranslator.class)
public class GeoDistanceAggregationTranslatorImpl
	implements GeoDistanceAggregationTranslator {

	@Override
	public GeoDistanceAggregationBuilder translate(
		GeoDistanceAggregation geoDistanceAggregation,
		AggregationTranslator<AggregationBuilder> aggregationTranslator,
		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator) {

		GeoPoint geoPoint = GeoLocationPointTranslator.translate(
			geoDistanceAggregation.getGeoLocationPoint());

		GeoDistanceAggregationBuilder geoDistanceAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.geoDistance(
					baseMetricsAggregation.getName(), geoPoint),
				geoDistanceAggregation, aggregationTranslator,
				pipelineAggregationTranslator);

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

	private final BaseFieldAggregationTranslator
		_baseFieldAggregationTranslator = new BaseFieldAggregationTranslator();
	private final DistanceUnitTranslator _distanceUnitTranslator =
		new DistanceUnitTranslator();
	private final GeoDistanceTypeTranslator _geoDistanceTypeTranslator =
		new GeoDistanceTypeTranslator();

}