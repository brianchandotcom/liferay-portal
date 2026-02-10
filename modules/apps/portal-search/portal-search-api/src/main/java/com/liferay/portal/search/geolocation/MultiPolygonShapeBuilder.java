/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class MultiPolygonShapeBuilder
	extends ShapeBuilder<MultiPolygonShapeBuilder> {

	public MultiPolygonShapeBuilder addPolygonShape(PolygonShape polygonShape) {
		_polygonShapes.add(polygonShape);

		return this;
	}

	public MultiPolygonShape build() {
		return new MultiPolygonShape(coordinates, _orientation, _polygonShapes);
	}

	public MultiPolygonShapeBuilder orientation(Orientation orientation) {
		_orientation = orientation;

		return this;
	}

	public MultiPolygonShapeBuilder polygonShapes(
		PolygonShape... polygonShapes) {

		_polygonShapes.clear();

		Collections.addAll(_polygonShapes, polygonShapes);

		return this;
	}

	private Orientation _orientation;
	private final List<PolygonShape> _polygonShapes = new ArrayList<>();

}