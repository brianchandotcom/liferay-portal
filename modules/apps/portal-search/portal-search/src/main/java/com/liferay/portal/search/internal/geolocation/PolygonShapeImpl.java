/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.LineStringShape;
import com.liferay.portal.search.geolocation.Orientation;
import com.liferay.portal.search.geolocation.PolygonShape;
import com.liferay.portal.search.geolocation.PolygonShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class PolygonShapeImpl extends PolygonShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	@Override
	public List<LineStringShape> getHoles() {
		return _holeLineStringShapes;
	}

	@Override
	public Orientation getOrientation() {
		return _orientation;
	}

	@Override
	public LineStringShape getShell() {
		return _shell;
	}

	public static class PolygonShapeBuilderImpl
		extends ShapeBuilder<PolygonShapeBuilder>
		implements PolygonShapeBuilder {

		@Override
		public PolygonShapeBuilder addHole(LineStringShape lineStringShape) {
			_holeLineStringShapes.add(lineStringShape);

			return this;
		}

		@Override
		public PolygonShape build() {
			return new PolygonShapeImpl(
				coordinates, _holeLineStringShapes, _orientation, _shell);
		}

		@Override
		public PolygonShapeBuilder holes(LineStringShape... lineStringShapes) {
			_holeLineStringShapes.clear();

			Collections.addAll(_holeLineStringShapes, lineStringShapes);

			return this;
		}

		@Override
		public PolygonShapeBuilder orientation(Orientation orientation) {
			_orientation = orientation;

			return this;
		}

		@Override
		public PolygonShapeBuilder shell(LineStringShape shell) {
			_shell = shell;

			return this;
		}

		private final List<LineStringShape> _holeLineStringShapes =
			new ArrayList<>();
		private Orientation _orientation;
		private LineStringShape _shell;

	}

	private PolygonShapeImpl(
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