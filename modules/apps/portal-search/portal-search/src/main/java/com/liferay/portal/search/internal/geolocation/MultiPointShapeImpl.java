/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.Coordinate;
import com.liferay.portal.search.geolocation.MultiPointShape;
import com.liferay.portal.search.geolocation.MultiPointShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class MultiPointShapeImpl extends MultiPointShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public static class MultiPointShapeBuilderImpl
		extends ShapeBuilder<MultiPointShapeBuilder>
		implements MultiPointShapeBuilder {

		@Override
		public MultiPointShape build() {
			return new MultiPointShapeImpl(coordinates);
		}

	}

	private MultiPointShapeImpl(List<Coordinate> coordinates) {
		super(coordinates);
	}

}