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
public abstract class Shape {

	public abstract <T> T accept(ShapeTranslator<T> shapeTranslator);

	public List<Coordinate> getCoordinates() {
		return _coordinates;
	}

	protected Shape(List<Coordinate> coordinates) {
		_coordinates = Collections.unmodifiableList(coordinates);
	}

	private final List<Coordinate> _coordinates;

}