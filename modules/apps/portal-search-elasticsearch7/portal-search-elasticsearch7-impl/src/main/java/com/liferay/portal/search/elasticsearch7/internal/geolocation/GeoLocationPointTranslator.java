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

package com.liferay.portal.search.elasticsearch7.internal.geolocation;

import com.liferay.portal.search.geolocation.GeoLocationPoint;

import org.elasticsearch.common.geo.GeoPoint;

/**
 * @author André de Oliveira
 */
public class GeoLocationPointTranslator {

	public static GeoPoint translate(GeoLocationPoint geoLocationPoint) {
		if (geoLocationPoint.getGeoHashLong() != null) {
			return GeoPoint.fromGeohash(geoLocationPoint.getGeoHashLong());
		}

		if (geoLocationPoint.getGeoHash() != null) {
			return new GeoPoint(geoLocationPoint.getGeoHash());
		}

		return new GeoPoint(
			geoLocationPoint.getLatitude(), geoLocationPoint.getLongitude());
	}

}