/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class MultiPolygonShape extends Shape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public Orientation getOrientation() {
		return _orientation;
	}

	public List<PolygonShape> getPolygonShapes() {
		return _polygonShapes;
	}

	protected MultiPolygonShape(
		List<Coordinate> coordinates, Orientation orientation,
		List<PolygonShape> polygonShapes) {

		super(coordinates);

		_orientation = orientation;
		_polygonShapes = Collections.unmodifiableList(polygonShapes);
	}

	private final Orientation _orientation;
	private final List<PolygonShape> _polygonShapes;

}