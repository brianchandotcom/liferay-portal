/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.filter;

/**
 * @author Michael C. Han
 */
public interface FilterVisitor<T> {

	public T visit(BooleanFilter booleanFilter);

	public T visit(DateRangeTermFilter dateRangeTermFilter);

	public T visit(ExistsFilter existsFilter);

	public T visit(GeoBoundingBoxFilter geoBoundingBoxFilter);

	public T visit(GeoDistanceFilter geoDistanceFilter);

	public T visit(GeoDistanceRangeFilter geoDistanceRangeFilter);

	public T visit(GeoPolygonFilter geoPolygonFilter);

	public T visit(MissingFilter missingFilter);

	public T visit(PrefixFilter prefixFilter);

	public T visit(QueryFilter queryFilter);

	public T visit(RangeTermFilter rangeTermFilter);

	public T visit(TermFilter termFilter);

	public T visit(TermsFilter termsFilter);

}