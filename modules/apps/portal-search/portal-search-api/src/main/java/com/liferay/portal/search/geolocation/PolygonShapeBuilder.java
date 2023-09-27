/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface PolygonShapeBuilder {

	public PolygonShapeBuilder addCoordinate(Coordinate coordinate);

	public PolygonShapeBuilder addHole(LineStringShape lineStringShape);

	public PolygonShape build();

	public PolygonShapeBuilder coordinates(Coordinate... coordinates);

	public PolygonShapeBuilder coordinates(List<Coordinate> coordinates);

	public PolygonShapeBuilder holes(LineStringShape... lineStringShapes);

	public PolygonShapeBuilder orientation(Orientation orientation);

	public PolygonShapeBuilder shell(LineStringShape shell);

}