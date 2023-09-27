/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof GeoLocationPoint)) {
			return false;
		}

		GeoLocationPoint geoLocationPoint = (GeoLocationPoint)object;

		if ((Double.compare(geoLocationPoint.getLatitude(), _latitude) != 0) ||
			(Double.compare(geoLocationPoint.getLongitude(), _longitude) !=
				0)) {

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

		return (31 * hashCode) + (int)(value ^ (value >>> 32));
	}

	private final double _latitude;
	private final double _longitude;

}