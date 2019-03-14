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
import com.liferay.portal.search.geolocation.LineStringShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class LineStringShapeImpl extends ShapeImpl implements LineStringShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public static class LineStringShapeBuilderImpl
		implements LineStringShapeBuilder {

		@Override
		public LineStringShapeBuilder addCoordinate(Coordinate coordinate) {
			_lineStringShapeImpl.addCoordinate(coordinate);

			return this;
		}

		@Override
		public LineStringShape build() {
			return new LineStringShapeImpl(_lineStringShapeImpl);
		}

		@Override
		public LineStringShapeBuilder coordinates(Coordinate... coordinates) {
			_lineStringShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public LineStringShapeBuilder coordinates(
			List<Coordinate> coordinates) {

			_lineStringShapeImpl.setCoordinates(coordinates);

			return this;
		}

		private final LineStringShapeImpl _lineStringShapeImpl =
			new LineStringShapeImpl();

	}

	protected LineStringShapeImpl() {
	}

	protected LineStringShapeImpl(LineStringShapeImpl lineStringShapeImpl) {
		setCoordinates(lineStringShapeImpl.getCoordinates());
	}

}