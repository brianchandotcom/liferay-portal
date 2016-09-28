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

package com.liferay.portal.kernel.search.geolocation;

/**
 * @author Michael C. Han
 */
public class GeoLocationPoint {

	public GeoLocationPoint(double latitude, double longitude) {
		_latitude = latitude;
		_longitude = longitude;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof GeoLocationPoint)) {
			return false;
		}

		GeoLocationPoint geoLocationPoint = (GeoLocationPoint)obj;

		if (Double.compare(geoLocationPoint.getLatitude(), _latitude) != 0) {
			return false;
		}

		if (Double.compare(geoLocationPoint.getLongitude(), _longitude) != 0) {
			return false;
		}

		return true;
	}

	public double getLatitude() {
		return _latitude;
	}

	public double getLongitude() {
		return _longitude;
	}

	@Override
	public int hashCode() {
		long value = Double.doubleToLongBits(_latitude);

		int hashCode = (int)(value ^ (value >>> 32));

		value = Double.doubleToLongBits(_longitude);

		hashCode = 31 * hashCode + (int)(value ^ (value >>> 32));

		return hashCode;
	}

	private final double _latitude;
	private final double _longitude;

}