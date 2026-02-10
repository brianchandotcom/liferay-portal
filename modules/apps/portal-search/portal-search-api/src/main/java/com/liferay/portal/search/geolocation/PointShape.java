/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class PointShape extends Shape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public static class PointShapeBuilderImpl
		extends ShapeBuilder<PointShapeBuilder> implements PointShapeBuilder {

		@Override
		public PointShape build() {
			return new PointShape(coordinates);
		}

	}

	private PointShape(List<Coordinate> coordinates) {
		super(coordinates);
	}

}