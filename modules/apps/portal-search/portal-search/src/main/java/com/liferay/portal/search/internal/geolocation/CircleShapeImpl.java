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

import com.liferay.portal.search.geolocation.CircleShape;
import com.liferay.portal.search.geolocation.CircleShapeBuilder;
import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class CircleShapeImpl extends ShapeImpl implements CircleShape {

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