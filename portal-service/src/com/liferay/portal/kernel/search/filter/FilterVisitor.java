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