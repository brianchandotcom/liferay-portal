/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.CircleShape;
import com.liferay.portal.search.geolocation.CircleShapeBuilder;
import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class CircleShapeImpl extends BaseShapeImpl implements CircleShape {

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

	public static class CircleShapeBuilderImpl implements CircleShapeBuilder {

		@Override
		public CircleShapeBuilder addCoordinate(Coordinate coordinate) {
			_circleShapeImpl.addCoordinate(coordinate);

			return this;
		}

		@Override
		public CircleShape build() {
			return new CircleShapeImpl(_circleShapeImpl);
		}

		@Override
		public CircleShapeBuilder center(Coordinate coordinate) {
			_circleShapeImpl._centerCoordinate = coordinate;

			return this;
		}

		@Override
		public CircleShapeBuilder coordinates(Coordinate... coordinates) {
			_circleShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public CircleShapeBuilder coordinates(List<Coordinate> coordinates) {
			_circleShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public CircleShapeBuilder radius(GeoDistance geoDistance) {
			_circleShapeImpl._radiusGeoDistance = geoDistance;

			return this;
		}

		private final CircleShapeImpl _circleShapeImpl = new CircleShapeImpl();

	}

	protected CircleShapeImpl() {
	}

	protected CircleShapeImpl(CircleShapeImpl circleShapeImpl) {
		_centerCoordinate = circleShapeImpl._centerCoordinate;
		_radiusGeoDistance = circleShapeImpl._radiusGeoDistance;

		setCoordinates(circleShapeImpl.getCoordinates());
	}

	private Coordinate _centerCoordinate;
	private GeoDistance _radiusGeoDistance;

}