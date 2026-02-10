/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class MultiLineStringShape extends Shape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

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
			return new MultiLineStringShape(coordinates, _lineStringShapes);
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

	private MultiLineStringShape(
		List<Coordinate> coordinates, List<LineStringShape> lineStringShapes) {

		super(coordinates);

		_lineStringShapes = Collections.unmodifiableList(lineStringShapes);
	}

	private final List<LineStringShape> _lineStringShapes;

}