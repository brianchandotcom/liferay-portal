/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class GeometryCollectionShape extends Shape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public List<Shape> getShapes() {
		return _shapes;
	}

	public static class GeometryCollectionShapeBuilderImpl
		extends ShapeBuilder<GeometryCollectionShapeBuilder>
		implements GeometryCollectionShapeBuilder {

		@Override
		public GeometryCollectionShapeBuilder addShape(Shape shape) {
			_shapes.add(shape);

			return this;
		}

		@Override
		public GeometryCollectionShape build() {
			return new GeometryCollectionShape(coordinates, _shapes);
		}

		@Override
		public GeometryCollectionShapeBuilder shapes(Shape... shapes) {
			_shapes.clear();

			Collections.addAll(_shapes, shapes);

			return this;
		}

		private final List<Shape> _shapes = new ArrayList<>();

	}

	private GeometryCollectionShape(
		List<Coordinate> coordinates, List<Shape> shapes) {

		super(coordinates);

		_shapes = Collections.unmodifiableList(shapes);
	}

	private final List<Shape> _shapes;

}