/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.CircleShape;
import com.liferay.portal.search.geolocation.CircleShapeBuilder;
import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.ShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class CircleShapeImpl extends CircleShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	@Override
	public Coordinate getCenter() {
		return _centerCoordinate;
	}

	@Override
	public GeoDistance getRadius() {
		return _radiusGeoDistance;
	}

	public static class CircleShapeBuilderImpl
		extends ShapeBuilder<CircleShapeBuilder> implements CircleShapeBuilder {

		@Override
		public CircleShape build() {
			return new CircleShapeImpl(
				coordinates, _centerCoordinate, _radiusGeoDistance);
		}

		@Override
		public CircleShapeBuilder center(Coordinate coordinate) {
			_centerCoordinate = coordinate;

			return this;
		}

		@Override
		public CircleShapeBuilder radius(GeoDistance geoDistance) {
			_radiusGeoDistance = geoDistance;

			return this;
		}

		private Coordinate _centerCoordinate;
		private GeoDistance _radiusGeoDistance;

	}

	private CircleShapeImpl(
		List<Coordinate> coordinates, Coordinate centerCoordinate,
		GeoDistance radiusGeoDistance) {

		super(coordinates);

		_centerCoordinate = centerCoordinate;
		_radiusGeoDistance = radiusGeoDistance;
	}

	private final Coordinate _centerCoordinate;
	private final GeoDistance _radiusGeoDistance;

}