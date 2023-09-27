/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.geolocation;

import com.liferay.portal.search.geolocation.GeoDistanceType;

import org.elasticsearch.common.geo.GeoDistance;

/**
 * @author Michael C. Han
 */
public class GeoDistanceTypeTranslator {

	public GeoDistance translate(GeoDistanceType geoDistanceType) {
		if (geoDistanceType == GeoDistanceType.ARC) {
			return GeoDistance.ARC;
		}
		else if (geoDistanceType == GeoDistanceType.PLANE) {
			return GeoDistance.PLANE;
		}

		throw new IllegalArgumentException(
			"Invalid GeoDistanceType: " + geoDistanceType);
	}

}