/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class GeoDistanceSort extends Sort {

	public GeoDistanceSort() {
		setType(GEO_DISTANCE_TYPE);
	}

	public void addGeoHash(String geoHash) {
		_geoHashes.add(geoHash);
	}

	public void addGeoHash(String... geoHashes) {
		Collections.addAll(_geoHashes, geoHashes);
	}

	public void addGeoLocationPoint(GeoLocationPoint geoLocationPoint) {
		_geoLocationPoints.add(geoLocationPoint);
	}

	public void addGeoLocationPoints(GeoLocationPoint... geoLocationPoints) {
		Collections.addAll(_geoLocationPoints, geoLocationPoints);
	}

	public List<String> getGeoHashes() {
		return Collections.unmodifiableList(_geoHashes);
	}

	public List<GeoLocationPoint> getGeoLocationPoints() {
		return Collections.unmodifiableList(_geoLocationPoints);
	}

	private final List<String> _geoHashes = new ArrayList<>();
	private final List<GeoLocationPoint> _geoLocationPoints = new ArrayList<>();

}