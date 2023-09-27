/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class MultiPointShapeImpl
	extends BaseShapeImpl implements MultiPointShape {

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