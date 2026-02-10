/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public abstract class ShapeBuilder<T> {

	public T addCoordinate(Coordinate coordinate) {
		coordinates.add(coordinate);

		return (T)this;
	}

	public T coordinates(Coordinate... coordinates) {
		this.coordinates.clear();

		Collections.addAll(this.coordinates, coordinates);

		return (T)this;
	}

	public T coordinates(List<Coordinate> coordinates) {
		this.coordinates.clear();

		this.coordinates.addAll(coordinates);

		return (T)this;
	}

	protected final List<Coordinate> coordinates = new ArrayList<>();

}