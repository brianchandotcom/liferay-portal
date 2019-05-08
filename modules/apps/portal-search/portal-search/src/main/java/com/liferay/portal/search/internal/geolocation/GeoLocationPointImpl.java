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