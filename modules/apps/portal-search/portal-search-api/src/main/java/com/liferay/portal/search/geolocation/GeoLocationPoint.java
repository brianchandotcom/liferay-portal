/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;

/**
 * @author Michael C. Han
 */
public class GeoLocationPoint {

	public static GeoLocationPoint fromGeoHash(String geoHash) {
		GeoLocationPoint geoLocationPoint = new GeoLocationPoint();

		geoLocationPoint._geoHash = geoHash;

		return geoLocationPoint;
	}

	public static GeoLocationPoint fromGeoHashLong(long geoHashLong) {
		GeoLocationPoint geoLocationPoint = new GeoLocationPoint();

		geoLocationPoint._geoHashLong = geoHashLong;

		return geoLocationPoint;
	}

	public static GeoLocationPoint fromLatitudeLongitude(
		double latitude, double longitude) {

		GeoLocationPoint geoLocationPoint = new GeoLocationPoint();

		geoLocationPoint._latitude = latitude;
		geoLocationPoint._longitude = longitude;

		return geoLocationPoint;
	}

	public String getGeoHash() {
		return _geoHash;
	}

	public Long getGeoHashLong() {
		return _geoHashLong;
	}

	public Double getLatitude() {
		return _latitude;
	}

	public Double getLongitude() {
		return _longitude;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			CharPool.OPEN_PARENTHESIS, _latitude, CharPool.COMMA, _longitude,
			CharPool.CLOSE_PARENTHESIS);
	}

	private String _geoHash;
	private Long _geoHashLong;
	private Double _latitude;
	private Double _longitude;

}