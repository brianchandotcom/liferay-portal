/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.MultiPolygonShape;
import com.liferay.portal.search.geolocation.MultiPolygonShapeBuilder;
import com.liferay.portal.search.geolocation.Orientation;
import com.liferay.portal.search.geolocation.PolygonShape;
import com.liferay.portal.search.geolocation.ShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class MultiPolygonShapeImpl extends MultiPolygonShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	@Override
	public Orientation getOrientation() {
		return _orientation;
	}

	@Override
	public List<PolygonShape> getPolygonShapes() {
		return _polygonShapes;
	}

	public static class MultiPolygonShapeBuilderImpl
		extends ShapeBuilder<MultiPolygonShapeBuilder>
		implements MultiPolygonShapeBuilder {

		@Override
		public MultiPolygonShapeBuilder addPolygonShape(
			PolygonShape polygonShape) {

			_polygonShapes.add(polygonShape);

			return this;
		}

		@Override
		public MultiPolygonShape build() {
			return new MultiPolygonShapeImpl(
				coordinates, _orientation, _polygonShapes);
		}

		@Override
		public MultiPolygonShapeBuilder orientation(Orientation orientation) {
			_orientation = orientation;

			return this;
		}

		@Override
		public MultiPolygonShapeBuilder polygonShapes(
			PolygonShape... polygonShapes) {

			_polygonShapes.clear();

			Collections.addAll(_polygonShapes, polygonShapes);

			return this;
		}

		private Orientation _orientation;
		private final List<PolygonShape> _polygonShapes = new ArrayList<>();

	}

	private MultiPolygonShapeImpl(
		List<Coordinate> coordinates, Orientation orientation,
		List<PolygonShape> polygonShapes) {

		super(coordinates);

		_orientation = orientation;
		_polygonShapes = Collections.unmodifiableList(polygonShapes);
	}

	private final Orientation _orientation;
	private final List<PolygonShape> _polygonShapes;

}