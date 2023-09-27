/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.geolocation;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.geolocation.GeoLocationPoint;

/**
 * @author Michael C. Han
 * @author Wade Cao
 * @author André de Oliveira
 */
public class GeoLocationPointImpl implements GeoLocationPoint {

	public static GeoLocationPoint fromGeoHash(String geoHash) {
		GeoLocationPointImpl geoLocationPointImpl = new GeoLocationPointImpl();

		geoLocationPointImpl._geoHash = geoHash;

		return geoLocationPointImpl;
	}

	public static GeoLocationPoint fromGeoHashLong(long geoHashLong) {
		GeoLocationPointImpl geoLocationPointImpl = new GeoLocationPointImpl();

		geoLocationPointImpl._geoHashLong = geoHashLong;

		return geoLocationPointImpl;
	}

	public static GeoLocationPoint fromLatitudeLongitude(
		double latitude, double longitude) {

		GeoLocationPointImpl geoLocationPointImpl = new GeoLocationPointImpl();

		geoLocationPointImpl._latitude = latitude;
		geoLocationPointImpl._longitude = longitude;

		return geoLocationPointImpl;
	}

	@Override
	public String getGeoHash() {
		return _geoHash;
	}

	@Override
	public Long getGeoHashLong() {
		return _geoHashLong;
	}

	@Override
	public Double getLatitude() {
		return _latitude;
	}

	@Override
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