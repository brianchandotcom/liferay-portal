/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class EnvelopeShape extends Shape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public Coordinate getBottomRight() {
		return _bottomRightCoordinate;
	}

	public Coordinate getTopLeft() {
		return _topLeftCoordinate;
	}

	public static class EnvelopeShapeBuilderImpl
		extends ShapeBuilder<EnvelopeShapeBuilder>
		implements EnvelopeShapeBuilder {

		@Override
		public EnvelopeShapeBuilder bottomRight(Coordinate coordinate) {
			_bottomRightCoordinate = coordinate;

			return this;
		}

		@Override
		public EnvelopeShape build() {
			return new EnvelopeShape(
				coordinates, _bottomRightCoordinate, _topLeftCoordinate);
		}

		@Override
		public EnvelopeShapeBuilder topLeft(Coordinate coordinate) {
			_topLeftCoordinate = coordinate;

			return this;
		}

		private Coordinate _bottomRightCoordinate;
		private Coordinate _topLeftCoordinate;

	}

	private EnvelopeShape(
		List<Coordinate> coordinates, Coordinate bottomRightCoordinate,
		Coordinate topLeftCoordinate) {

		super(coordinates);

		_bottomRightCoordinate = bottomRightCoordinate;
		_topLeftCoordinate = topLeftCoordinate;
	}

	private final Coordinate _bottomRightCoordinate;
	private final Coordinate _topLeftCoordinate;

}