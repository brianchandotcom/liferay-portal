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

package com.liferay.portal.search.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.AggregationVisitor;
import com.liferay.portal.search.aggregation.bucket.GeoDistanceAggregation;
import com.liferay.portal.search.aggregation.bucket.Range;
import com.liferay.portal.search.geolocation.DistanceUnit;
import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeoDistanceType;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.internal.aggregation.BaseFieldAggregation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class GeoDistanceAggregationImpl
	extends BaseFieldAggregation implements GeoDistanceAggregation {

	public GeoDistanceAggregationImpl(
		String name, String field, GeoLocationPoint geoLocationPoint) {

		super(name, field);

		_geoLocationPoint = geoLocationPoint;
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	@Override
	public void addRange(Range range) {
		_ranges.add(range);
	}

	@Override
	public void addRanges(Range... ranges) {
		Collections.addAll(_ranges, ranges);
	}

	@Override
	public void addUnboundedFrom(Double from) {
		addRange(new Range(from, null));
	}

	@Override
	public void addUnboundedFrom(String key, Double from) {
		addRange(new Range(key, from, null));
	}

	@Override
	public void addUnboundedTo(String key, Double to) {
		addRange(new Range(key, null, to));
	}

	@Override
	public DistanceUnit getDistanceUnit() {
		return _distanceUnit;
	}

	@Override
	public GeoDistance getGeoDistance() {
		return _geoDistance;
	}

	@Override
	public GeoDistanceType getGeoDistanceType() {
		return _geoDistanceType;
	}

	@Override
	public GeoLocationPoint getGeoLocationPoint() {
		return _geoLocationPoint;
	}

	@Override
	public Boolean getKeyed() {
		return _keyed;
	}

	@Override
	public List<Range> getRanges() {
		return Collections.unmodifiableList(_ranges);
	}

	@Override
	public void setDistanceUnit(DistanceUnit distanceUnit) {
		_distanceUnit = distanceUnit;
	}

	@Override
	public void setGeoDistance(GeoDistance geoDistance) {
		_geoDistance = geoDistance;
	}

	@Override
	public void setGeoDistanceType(GeoDistanceType geoDistanceType) {
		_geoDistanceType = geoDistanceType;
	}

	@Override
	public void setGeoLocationPoint(GeoLocationPoint geoLocationPoint) {
		_geoLocationPoint = geoLocationPoint;
	}

	@Override
	public void setKeyed(Boolean keyed) {
		_keyed = keyed;
	}

	private DistanceUnit _distanceUnit;
	private GeoDistance _geoDistance;
	private GeoDistanceType _geoDistanceType;
	private GeoLocationPoint _geoLocationPoint;
	private Boolean _keyed;
	private final List<Range> _ranges = new ArrayList<>();

}