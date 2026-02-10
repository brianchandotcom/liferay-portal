/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.geolocation;

import com.liferay.portal.search.geolocation.LineStringShape;
import com.liferay.portal.search.geolocation.LineStringShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeBuilder;
import com.liferay.portal.search.geolocation.ShapeTranslator;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class LineStringShapeImpl
	extends BaseShapeImpl implements LineStringShape {

	@Override
	public <T> T accept(ShapeTranslator<T> shapeTranslator) {
		return shapeTranslator.translate(this);
	}

	public static class LineStringShapeBuilderImpl
		extends ShapeBuilder<LineStringShapeBuilder>
		implements LineStringShapeBuilder {

		@Override
		public LineStringShape build() {
			_lineStringShapeImpl.setCoordinates(coordinates);

			return new LineStringShapeImpl(_lineStringShapeImpl);
		}

		private final LineStringShapeImpl _lineStringShapeImpl =
			new LineStringShapeImpl();

	}

	protected LineStringShapeImpl() {
	}

	protected LineStringShapeImpl(LineStringShapeImpl lineStringShapeImpl) {
		setCoordinates(lineStringShapeImpl.getCoordinates());
	}

}