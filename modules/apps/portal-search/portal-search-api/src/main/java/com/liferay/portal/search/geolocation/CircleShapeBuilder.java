/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

/**
 * @author André de Oliveira
 */
public class CircleShapeBuilder extends ShapeBuilder<CircleShapeBuilder> {

	public CircleShape build() {
		return new CircleShape(
			coordinates, _centerCoordinate, _radiusGeoDistance);
	}

	public CircleShapeBuilder center(Coordinate coordinate) {
		_centerCoordinate = coordinate;

		return this;
	}

	public CircleShapeBuilder radius(GeoDistance geoDistance) {
		_radiusGeoDistance = geoDistance;

		return this;
	}

	private Coordinate _centerCoordinate;
	private GeoDistance _radiusGeoDistance;

}