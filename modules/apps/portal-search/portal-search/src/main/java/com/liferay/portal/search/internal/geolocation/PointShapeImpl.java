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
import com.liferay.portal.search.geolocation.PointShape;
import com.liferay.portal.search.geolocation.PointShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class PointShapeImpl extends ShapeImpl implements PointShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public static class PointShapeBuilderImpl implements PointShapeBuilder {

		@Override
		public PointShapeBuilder addCoordinate(Coordinate coordinate) {
			_pointShapeImpl.addCoordinate(coordinate);

			return this;
		}

		@Override
		public PointShape build() {
			return new PointShapeImpl(_pointShapeImpl);
		}

		@Override
		public PointShapeBuilder coordinates(Coordinate... coordinates) {
			_pointShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public PointShapeBuilder coordinates(List<Coordinate> coordinates) {
			_pointShapeImpl.setCoordinates(coordinates);

			return this;
		}

		private final PointShapeImpl _pointShapeImpl = new PointShapeImpl();

	}

	protected PointShapeImpl() {
	}

	protected PointShapeImpl(PointShapeImpl pointShapeImpl) {
		setCoordinates(pointShapeImpl.getCoordinates());
	}

}