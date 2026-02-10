/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.LineStringShape;
import com.liferay.portal.search.geolocation.MultiLineStringShape;
import com.liferay.portal.search.geolocation.MultiLineStringShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class MultiLineStringShapeImpl extends MultiLineStringShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	@Override
	public List<LineStringShape> getLineStringShapes() {
		return _lineStringShapes;
	}

	public static class MultiLineStringShapeBuilderImpl
		extends ShapeBuilder<MultiLineStringShapeBuilder>
		implements MultiLineStringShapeBuilder {

		@Override
		public MultiLineStringShapeBuilder addLineStringShape(
			LineStringShape lineStringShape) {

			_lineStringShapes.add(lineStringShape);

			return this;
		}

		@Override
		public MultiLineStringShape build() {
			return new MultiLineStringShapeImpl(coordinates, _lineStringShapes);
		}

		@Override
		public MultiLineStringShapeBuilder lineStringShapes(
			LineStringShape... lineStringShapes) {

			_lineStringShapes.clear();

			Collections.addAll(_lineStringShapes, lineStringShapes);

			return this;
		}

		@Override
		public MultiLineStringShapeBuilder lineStringShapes(
			List<LineStringShape> lineStringShapes) {

			_lineStringShapes.clear();

			_lineStringShapes.addAll(lineStringShapes);

			return this;
		}

		private final List<LineStringShape> _lineStringShapes =
			new ArrayList<>();

	}

	private MultiLineStringShapeImpl(
		List<Coordinate> coordinates, List<LineStringShape> lineStringShapes) {

		super(coordinates);

		_lineStringShapes = Collections.unmodifiableList(lineStringShapes);
	}

	private final List<LineStringShape> _lineStringShapes;

}