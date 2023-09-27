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
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class PolygonShapeImpl extends BaseShapeImpl implements PolygonShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	@Override
	public List<LineStringShape> getHoles() {
		return Collections.unmodifiableList(_holeLineStringShapes);
	}

	@Override
	public Orientation getOrientation() {
		return _orientation;
	}

	@Override
	public LineStringShape getShell() {
		return _shell;
	}

	public static class PolygonShapeBuilderImpl implements PolygonShapeBuilder {

		@Override
		public PolygonShapeBuilder addCoordinate(Coordinate coordinate) {
			_polygonShapeImpl.addCoordinate(coordinate);

			return this;
		}

		@Override
		public PolygonShapeBuilder addHole(LineStringShape lineStringShape) {
			_polygonShapeImpl._holeLineStringShapes.add(lineStringShape);

			return this;
		}

		@Override
		public PolygonShape build() {
			return new PolygonShapeImpl(_polygonShapeImpl);
		}

		@Override
		public PolygonShapeBuilder coordinates(Coordinate... coordinates) {
			_polygonShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public PolygonShapeBuilder coordinates(List<Coordinate> coordinates) {
			_polygonShapeImpl.setCoordinates(coordinates);

			return this;
		}

		@Override
		public PolygonShapeBuilder holes(LineStringShape... lineStringShapes) {
			_polygonShapeImpl._holeLineStringShapes.clear();

			Collections.addAll(
				_polygonShapeImpl._holeLineStringShapes, lineStringShapes);

			return this;
		}

		@Override
		public PolygonShapeBuilder orientation(Orientation orientation) {
			_polygonShapeImpl._orientation = orientation;

			return this;
		}

		@Override
		public PolygonShapeBuilder shell(LineStringShape shell) {
			_polygonShapeImpl._shell = shell;

			return this;
		}

		private final PolygonShapeImpl _polygonShapeImpl =
			new PolygonShapeImpl();

	}

	protected PolygonShapeImpl() {
	}

	protected PolygonShapeImpl(PolygonShapeImpl polygonShapeImpl) {
		_orientation = polygonShapeImpl._orientation;
		_shell = polygonShapeImpl._shell;

		_holeLineStringShapes.addAll(polygonShapeImpl._holeLineStringShapes);

		setCoordinates(polygonShapeImpl.getCoordinates());
	}

	private final List<LineStringShape> _holeLineStringShapes =
		new ArrayList<>();
	private Orientation _orientation;
	private LineStringShape _shell;

}