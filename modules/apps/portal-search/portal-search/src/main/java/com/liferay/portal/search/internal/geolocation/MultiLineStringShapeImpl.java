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
import com.liferay.portal.search.geolocation.LineStringShape;
import com.liferay.portal.search.geolocation.MultiLineStringShape;
import com.liferay.portal.search.geolocation.MultiLineStringShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class MultiLineStringShapeImpl
	extends ShapeImpl implements MultiLineStringShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	@Override
	public List<LineStringShape> getLineStringShapes() {
		return Collections.unmodifiableList(_lineStringShapes);
	}

	public static class MultiLineStringShapeBuilderImpl
		implements MultiLineStringShapeBuilder {

		@Override
		public MultiLineStringShapeBuilder addCoordinate(
			Coordinate coordinate) {

			_multiLineStringShapeImpl.addCoordinate(coordinate);

			return this;
		}

		@Override
		public MultiLineStringShapeBuilder addLineStringShape(
			LineStringShape lineStringShape) {

			_multiLineStringShapeImpl._lineStringShapes.add(lineStringShape);

			return this;
		}

		@Override
		public MultiLineStringShape build() {
			return new MultiLineStringShapeImpl(_multiLineStringShapeImpl);
		}

		@Override
		public MultiLineStringShapeBuilder coordinates(
			Coordinate... coordinates) {

			_multiLineStringShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public MultiLineStringShapeBuilder coordinates(
			List<Coordinate> coordinates) {

			_multiLineStringShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public MultiLineStringShapeBuilder lineStringShapes(
			LineStringShape... lineStringShapes) {

			_multiLineStringShapeImpl._lineStringShapes.clear();

			Collections.addAll(
				_multiLineStringShapeImpl._lineStringShapes, lineStringShapes);

			return this;
		}

		@Override
		public MultiLineStringShapeBuilder lineStringShapes(
			List<LineStringShape> lineStringShapes) {

			_multiLineStringShapeImpl._lineStringShapes.clear();

			_multiLineStringShapeImpl._lineStringShapes.addAll(
				lineStringShapes);

			return this;
		}

		private final MultiLineStringShapeImpl _multiLineStringShapeImpl =
			new MultiLineStringShapeImpl();

	}

	protected MultiLineStringShapeImpl() {
	}

	protected MultiLineStringShapeImpl(
		MultiLineStringShapeImpl multiLineStringShapeImpl) {

		_lineStringShapes.addAll(multiLineStringShapeImpl._lineStringShapes);

		setCoordinates(multiLineStringShapeImpl.getCoordinates());
	}

	private final List<LineStringShape> _lineStringShapes = new ArrayList<>();

}