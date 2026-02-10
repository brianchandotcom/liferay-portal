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

	protected EnvelopeShape(
		List<Coordinate> coordinates, Coordinate bottomRightCoordinate,
		Coordinate topLeftCoordinate) {

		super(coordinates);

		_bottomRightCoordinate = bottomRightCoordinate;
		_topLeftCoordinate = topLeftCoordinate;
	}

	private final Coordinate _bottomRightCoordinate;
	private final Coordinate _topLeftCoordinate;

}