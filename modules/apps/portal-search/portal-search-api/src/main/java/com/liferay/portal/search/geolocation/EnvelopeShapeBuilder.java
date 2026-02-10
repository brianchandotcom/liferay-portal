/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

/**
 * @author André de Oliveira
 */
public class EnvelopeShapeBuilder extends ShapeBuilder<EnvelopeShapeBuilder> {

	public EnvelopeShapeBuilder bottomRight(Coordinate coordinate) {
		_bottomRightCoordinate = coordinate;

		return this;
	}

	public EnvelopeShape build() {
		return new EnvelopeShape(
			coordinates, _bottomRightCoordinate, _topLeftCoordinate);
	}

	public EnvelopeShapeBuilder topLeft(Coordinate coordinate) {
		_topLeftCoordinate = coordinate;

		return this;
	}

	private Coordinate _bottomRightCoordinate;
	private Coordinate _topLeftCoordinate;

}