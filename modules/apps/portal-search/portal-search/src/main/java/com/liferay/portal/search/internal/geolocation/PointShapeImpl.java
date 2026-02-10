/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.PointShape;
import com.liferay.portal.search.geolocation.PointShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class PointShapeImpl extends BaseShapeImpl implements PointShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public static class PointShapeBuilderImpl
		extends ShapeBuilder<PointShapeBuilder> implements PointShapeBuilder {

		@Override
		public PointShape build() {
			_pointShapeImpl.setCoordinates(coordinates);

			return new PointShapeImpl(_pointShapeImpl);
		}

		private final PointShapeImpl _pointShapeImpl = new PointShapeImpl();

	}

	protected PointShapeImpl() {
	}

	protected PointShapeImpl(PointShapeImpl pointShapeImpl) {
		setCoordinates(pointShapeImpl.getCoordinates());
	}

}