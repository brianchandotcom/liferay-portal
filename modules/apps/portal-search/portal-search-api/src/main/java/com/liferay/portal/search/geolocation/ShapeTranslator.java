/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.geolocation;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface ShapeTranslator<T> {

	public T translate(CircleShape circleShape);

	public T translate(EnvelopeShape envelopeShape);

	public T translate(GeometryCollectionShape geometryCollectionShape);

	public T translate(LineStringShape lineStringShape);

	public T translate(MultiLineStringShape multiLineStringShape);

	public T translate(MultiPointShape multiPointShape);

	public T translate(MultiPolygonShape multiPolygonShape);

	public T translate(PointShape pointShape);

	public T translate(PolygonShape polygonShape);

}