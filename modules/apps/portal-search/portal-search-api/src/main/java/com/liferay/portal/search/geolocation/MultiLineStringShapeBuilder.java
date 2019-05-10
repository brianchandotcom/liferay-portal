/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.geolocation;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface MultiLineStringShapeBuilder {

	public MultiLineStringShapeBuilder addCoordinate(Coordinate coordinate);

	public MultiLineStringShapeBuilder addLineStringShape(
		LineStringShape lineStringShape);

	public MultiLineStringShape build();

	public MultiLineStringShapeBuilder coordinates(Coordinate... coordinates);

	public MultiLineStringShapeBuilder coordinates(
		List<Coordinate> coordinates);

	public MultiLineStringShapeBuilder lineStringShapes(
		LineStringShape... lineStringShapes);

	public MultiLineStringShapeBuilder lineStringShapes(
		List<LineStringShape> lineStringShapes);

}