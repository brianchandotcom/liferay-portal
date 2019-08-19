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

package com.liferay.portal.search.test.internal.util;

import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.DateRangeTermFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.FilterVisitor;
import com.liferay.portal.kernel.search.filter.GeoBoundingBoxFilter;
import com.liferay.portal.kernel.search.filter.GeoDistanceFilter;
import com.liferay.portal.kernel.search.filter.GeoDistanceRangeFilter;
import com.liferay.portal.kernel.search.filter.GeoPolygonFilter;
import com.liferay.portal.kernel.search.filter.MissingFilter;
import com.liferay.portal.kernel.search.filter.PrefixFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;

/**
 * @author André de Oliveira
 */
public class BaseTestFilterVisitor<T> implements FilterVisitor<T> {

	@Override
	public T visit(BooleanFilter booleanFilter) {
		return null;
	}

	@Override
	public T visit(DateRangeTermFilter dateRangeTermFilter) {
		return null;
	}

	@Override
	public T visit(ExistsFilter existsFilter) {
		return null;
	}

	@Override
	public T visit(GeoBoundingBoxFilter geoBoundingBoxFilter) {
		return null;
	}

	@Override
	public T visit(GeoDistanceFilter geoDistanceFilter) {
		return null;
	}

	@Override
	public T visit(GeoDistanceRangeFilter geoDistanceRangeFilter) {
		return null;
	}

	@Override
	public T visit(GeoPolygonFilter geoPolygonFilter) {
		return null;
	}

	@Override
	public T visit(MissingFilter missingFilter) {
		return null;
	}

	@Override
	public T visit(PrefixFilter prefixFilter) {
		return null;
	}

	@Override
	public T visit(QueryFilter queryFilter) {
		return null;
	}

	@Override
	public T visit(RangeTermFilter rangeTermFilter) {
		return null;
	}

	@Override
	public T visit(TermFilter termFilter) {
		return null;
	}

	@Override
	public T visit(TermsFilter termsFilter) {
		return null;
	}

}