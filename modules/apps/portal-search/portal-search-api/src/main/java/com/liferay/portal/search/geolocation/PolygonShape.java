/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class PolygonShape extends Shape {

	public abstract List<LineStringShape> getHoles();

	public abstract Orientation getOrientation();

	public abstract LineStringShape getShell();

	protected PolygonShape(List<Coordinate> coordinates) {
		super(coordinates);
	}

}