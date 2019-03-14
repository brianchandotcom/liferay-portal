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

import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.MultiPolygonShape;
import com.liferay.portal.search.geolocation.MultiPolygonShapeBuilder;
import com.liferay.portal.search.geolocation.Orientation;
import com.liferay.portal.search.geolocation.PolygonShape;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class MultiPolygonShapeImpl
	extends ShapeImpl implements MultiPolygonShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	@Override
	public Orientation getOrientation() {
		return _orientation;
	}

	@Override
	public List<PolygonShape> getPolygonShapes() {
		return Collections.unmodifiableList(_polygonShapes);
	}

	public static class MultiPolygonShapeBuilderImpl
		implements MultiPolygonShapeBuilder {

		@Override
		public MultiPolygonShapeBuilder addCoordinate(Coordinate coordinate) {
			_multiPolygonShapeImpl.addCoordinate(coordinate);

			return this;
		}

		@Override
		public MultiPolygonShapeBuilder addPolygonShape(
			PolygonShape polygonShape) {

			_multiPolygonShapeImpl._polygonShapes.add(polygonShape);

			return this;
		}

		@Override
		public MultiPolygonShape build() {
			return new MultiPolygonShapeImpl(_multiPolygonShapeImpl);
		}

		@Override
		public MultiPolygonShapeBuilder coordinates(Coordinate... coordinates) {
			_multiPolygonShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public MultiPolygonShapeBuilder coordinates(
			List<Coordinate> coordinates) {

			_multiPolygonShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public MultiPolygonShapeBuilder orientation(Orientation orientation) {
			_multiPolygonShapeImpl._orientation = orientation;

			return this;
		}

		@Override
		public MultiPolygonShapeBuilder polygonShapes(
			PolygonShape... polygonShapes) {

			_multiPolygonShapeImpl._polygonShapes.clear();

			Collections.addAll(
				_multiPolygonShapeImpl._polygonShapes, polygonShapes);

			return this;
		}

		private final MultiPolygonShapeImpl _multiPolygonShapeImpl =
			new MultiPolygonShapeImpl();

	}

	protected MultiPolygonShapeImpl() {
	}

	protected MultiPolygonShapeImpl(
		MultiPolygonShapeImpl multiPolygonShapeImpl) {

		_orientation = multiPolygonShapeImpl._orientation;

		_polygonShapes.addAll(multiPolygonShapeImpl._polygonShapes);

		setCoordinates(multiPolygonShapeImpl.getCoordinates());
	}

	private Orientation _orientation;
	private final List<PolygonShape> _polygonShapes = new ArrayList<>();

}