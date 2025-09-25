/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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