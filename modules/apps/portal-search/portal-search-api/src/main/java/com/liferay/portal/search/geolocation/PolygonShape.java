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
public class PolygonShape extends Shape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public List<LineStringShape> getHoles() {
		return _holeLineStringShapes;
	}

	public Orientation getOrientation() {
		return _orientation;
	}

	public LineStringShape getShell() {
		return _shell;
	}

	protected PolygonShape(
		List<Coordinate> coordinates,
		List<LineStringShape> holeLineStringShapes, Orientation orientation,
		LineStringShape shell) {

		super(coordinates);

		_holeLineStringShapes = Collections.unmodifiableList(
			holeLineStringShapes);
		_orientation = orientation;
		_shell = shell;
	}

	private final List<LineStringShape> _holeLineStringShapes;
	private final Orientation _orientation;
	private final LineStringShape _shell;

}