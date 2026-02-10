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
public class GeometryCollectionShape extends Shape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public List<Shape> getShapes() {
		return _shapes;
	}

	protected GeometryCollectionShape(
		List<Coordinate> coordinates, List<Shape> shapes) {

		super(coordinates);

		_shapes = Collections.unmodifiableList(shapes);
	}

	private final List<Shape> _shapes;

}