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
import com.liferay.portal.search.geolocation.MultiPointShape;
import com.liferay.portal.search.geolocation.MultiPointShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class MultiPointShapeImpl extends ShapeImpl implements MultiPointShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public static class MultiPointShapeBuilderImpl
		implements MultiPointShapeBuilder {

		@Override
		public MultiPointShapeBuilder addCoordinate(Coordinate coordinate) {
			_multiPointShapeImpl.addCoordinate(coordinate);

			return this;
		}

		@Override
		public MultiPointShape build() {
			return new MultiPointShapeImpl(_multiPointShapeImpl);
		}

		@Override
		public MultiPointShapeBuilder coordinates(Coordinate... coordinates) {
			_multiPointShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public MultiPointShapeBuilder coordinates(
			List<Coordinate> coordinates) {

			_multiPointShapeImpl.setCoordinates(coordinates);

			return this;
		}

		private final MultiPointShapeImpl _multiPointShapeImpl =
			new MultiPointShapeImpl();

	}

	protected MultiPointShapeImpl() {
	}

	protected MultiPointShapeImpl(MultiPointShapeImpl multiPointShapeImpl) {
		setCoordinates(multiPointShapeImpl.getCoordinates());
	}

}