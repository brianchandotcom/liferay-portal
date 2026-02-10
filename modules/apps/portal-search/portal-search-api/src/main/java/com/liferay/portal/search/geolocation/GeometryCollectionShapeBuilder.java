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
public class GeometryCollectionShapeBuilder
	extends ShapeBuilder<GeometryCollectionShapeBuilder> {

	public GeometryCollectionShapeBuilder addShape(Shape shape) {
		_shapes.add(shape);

		return this;
	}

	public GeometryCollectionShape build() {
		return new GeometryCollectionShape(coordinates, _shapes);
	}

	public GeometryCollectionShapeBuilder shapes(Shape... shapes) {
		_shapes.clear();

		Collections.addAll(_shapes, shapes);

		return this;
	}

	private final List<Shape> _shapes = new ArrayList<>();

}