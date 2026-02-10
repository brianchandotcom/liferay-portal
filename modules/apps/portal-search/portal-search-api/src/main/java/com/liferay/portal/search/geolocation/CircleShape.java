/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class CircleShape extends Shape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public Coordinate getCenter() {
		return _centerCoordinate;
	}

	public GeoDistance getRadius() {
		return _radiusGeoDistance;
	}

	protected CircleShape(
		List<Coordinate> coordinates, Coordinate centerCoordinate,
		GeoDistance radiusGeoDistance) {

		super(coordinates);

		_centerCoordinate = centerCoordinate;
		_radiusGeoDistance = radiusGeoDistance;
	}

	private final Coordinate _centerCoordinate;
	private final GeoDistance _radiusGeoDistance;

}