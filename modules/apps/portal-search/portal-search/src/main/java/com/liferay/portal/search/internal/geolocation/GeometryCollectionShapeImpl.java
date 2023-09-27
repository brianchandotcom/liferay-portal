/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.GeometryCollectionShape;
import com.liferay.portal.search.geolocation.GeometryCollectionShapeBuilder;
import com.liferay.portal.search.geolocation.Shape;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class GeometryCollectionShapeImpl
	extends BaseShapeImpl implements GeometryCollectionShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	@Override
	public List<Shape> getShapes() {
		return Collections.unmodifiableList(_shapes);
	}

	public static class GeometryCollectionShapeBuilderImpl
		implements GeometryCollectionShapeBuilder {

		@Override
		public GeometryCollectionShapeBuilder addCoordinate(
			Coordinate coordinate) {

			_geometryCollectionShapeImpl.addCoordinate(coordinate);

			return this;
		}

		@Override
		public GeometryCollectionShapeBuilder addShape(Shape shape) {
			_geometryCollectionShapeImpl._shapes.add(shape);

			return this;
		}

		@Override
		public GeometryCollectionShape build() {
			return new GeometryCollectionShapeImpl(
				_geometryCollectionShapeImpl);
		}

		@Override
		public GeometryCollectionShapeBuilder coordinates(
			Coordinate... coordinates) {

			_geometryCollectionShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public GeometryCollectionShapeBuilder coordinates(
			List<Coordinate> coordinates) {

			_geometryCollectionShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public GeometryCollectionShapeBuilder shapes(Shape... shapes) {
			_geometryCollectionShapeImpl._shapes.clear();

			Collections.addAll(_geometryCollectionShapeImpl._shapes, shapes);

			return this;
		}

		private final GeometryCollectionShapeImpl _geometryCollectionShapeImpl =
			new GeometryCollectionShapeImpl();

	}

	protected GeometryCollectionShapeImpl() {
	}

	protected GeometryCollectionShapeImpl(
		GeometryCollectionShapeImpl geometryCollectionShapeImpl) {

		_shapes.addAll(geometryCollectionShapeImpl._shapes);

		setCoordinates(geometryCollectionShapeImpl.getCoordinates());
	}

	private final List<Shape> _shapes = new ArrayList<>();

}