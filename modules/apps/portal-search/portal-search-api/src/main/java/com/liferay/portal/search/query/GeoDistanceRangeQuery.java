/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.geolocation.GeoLocationPoint;
import com.liferay.portal.search.query.geolocation.ShapeRelation;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class GeoDistanceRangeQuery extends RangeTermQuery {

	public abstract GeoDistance getLowerBoundGeoDistance();

	public abstract GeoLocationPoint getPinGeoLocationPoint();

	public abstract ShapeRelation getShapeRelation();

	public abstract int getSortOrder();

	public abstract GeoDistance getUpperBoundGeoDistance();

	public abstract void setShapeRelation(ShapeRelation shapeRelation);

}