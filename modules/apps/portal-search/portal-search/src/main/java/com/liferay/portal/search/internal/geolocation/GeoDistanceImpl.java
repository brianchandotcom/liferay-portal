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

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.DistanceUnit;
import com.liferay.portal.search.geolocation.GeoDistance;

/**
 * @author Michael C. Han
 */
public class GeoDistanceImpl implements GeoDistance {

	@Override
	public double getDistance() {
		return _distance;
	}

	@Override
	public DistanceUnit getDistanceUnit() {
		return _distanceUnit;
	}

	@Override
	public String toString() {
		return _distance + _distanceUnit.getUnit();
	}

	protected GeoDistanceImpl(double distance) {
		this(distance, DistanceUnit.METERS);
	}

	protected GeoDistanceImpl(double distance, DistanceUnit distanceUnit) {
		_distance = distance;
		_distanceUnit = distanceUnit;
	}

	private final double _distance;
	private final DistanceUnit _distanceUnit;

}