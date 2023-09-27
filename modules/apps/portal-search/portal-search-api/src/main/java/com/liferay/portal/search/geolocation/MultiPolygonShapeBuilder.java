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
public interface MultiPolygonShapeBuilder {

	public MultiPolygonShapeBuilder addCoordinate(Coordinate coordinate);

	public MultiPolygonShapeBuilder addPolygonShape(PolygonShape polygonShape);

	public MultiPolygonShape build();

	public MultiPolygonShapeBuilder coordinates(Coordinate... coordinates);

	public MultiPolygonShapeBuilder coordinates(List<Coordinate> coordinates);

	public MultiPolygonShapeBuilder orientation(Orientation orientation);

	public MultiPolygonShapeBuilder polygonShapes(
		PolygonShape... polygonShapeBuilders);

}