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
public class MultiLineStringShapeBuilder
	extends ShapeBuilder<MultiLineStringShapeBuilder> {

	public MultiLineStringShapeBuilder addLineStringShape(
		LineStringShape lineStringShape) {

		_lineStringShapes.add(lineStringShape);

		return this;
	}

	public MultiLineStringShape build() {
		return new MultiLineStringShape(coordinates, _lineStringShapes);
	}

	public MultiLineStringShapeBuilder lineStringShapes(
		LineStringShape... lineStringShapes) {

		_lineStringShapes.clear();

		Collections.addAll(_lineStringShapes, lineStringShapes);

		return this;
	}

	public MultiLineStringShapeBuilder lineStringShapes(
		List<LineStringShape> lineStringShapes) {

		_lineStringShapes.clear();

		_lineStringShapes.addAll(lineStringShapes);

		return this;
	}

	private final List<LineStringShape> _lineStringShapes = new ArrayList<>();

}