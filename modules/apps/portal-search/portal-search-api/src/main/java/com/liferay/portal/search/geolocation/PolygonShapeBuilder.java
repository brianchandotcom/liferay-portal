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
public class PolygonShapeBuilder extends ShapeBuilder<PolygonShapeBuilder> {

	public PolygonShapeBuilder addHole(LineStringShape lineStringShape) {
		_holeLineStringShapes.add(lineStringShape);

		return this;
	}

	public PolygonShape build() {
		return new PolygonShape(
			coordinates, _holeLineStringShapes, _orientation, _shell);
	}

	public PolygonShapeBuilder holes(LineStringShape... lineStringShapes) {
		_holeLineStringShapes.clear();

		Collections.addAll(_holeLineStringShapes, lineStringShapes);

		return this;
	}

	public PolygonShapeBuilder orientation(Orientation orientation) {
		_orientation = orientation;

		return this;
	}

	public PolygonShapeBuilder shell(LineStringShape shell) {
		_shell = shell;

		return this;
	}

	private final List<LineStringShape> _holeLineStringShapes =
		new ArrayList<>();
	private Orientation _orientation;
	private LineStringShape _shell;

}