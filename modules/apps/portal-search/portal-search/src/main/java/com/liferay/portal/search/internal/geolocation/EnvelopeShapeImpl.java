/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.EnvelopeShape;
import com.liferay.portal.search.geolocation.EnvelopeShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class EnvelopeShapeImpl extends EnvelopeShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	@Override
	public Coordinate getBottomRight() {
		return _bottomRightCoordinate;
	}

	@Override
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
			return new EnvelopeShapeImpl(
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

	private EnvelopeShapeImpl(
		List<Coordinate> coordinates, Coordinate bottomRightCoordinate,
		Coordinate topLeftCoordinate) {

		super(coordinates);

		_bottomRightCoordinate = bottomRightCoordinate;
		_topLeftCoordinate = topLeftCoordinate;
	}

	private final Coordinate _bottomRightCoordinate;
	private final Coordinate _topLeftCoordinate;

}