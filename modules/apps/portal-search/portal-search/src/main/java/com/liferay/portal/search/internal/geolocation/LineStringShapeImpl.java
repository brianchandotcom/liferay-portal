/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class LineStringShapeImpl
	extends BaseShapeImpl implements LineStringShape {

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