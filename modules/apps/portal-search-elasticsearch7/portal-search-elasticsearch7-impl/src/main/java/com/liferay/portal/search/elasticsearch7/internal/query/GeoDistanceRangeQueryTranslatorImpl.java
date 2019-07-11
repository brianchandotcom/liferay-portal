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

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.geolocation.GeoDistance;
import com.liferay.portal.search.query.GeoDistanceRangeQuery;
import com.liferay.portal.search.query.geolocation.ShapeRelation;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = GeoDistanceRangeQueryTranslator.class)
public class GeoDistanceRangeQueryTranslatorImpl
	implements GeoDistanceRangeQueryTranslator {

	@Override
	public QueryBuilder translate(GeoDistanceRangeQuery geoDistanceRangeQuery) {
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(
			geoDistanceRangeQuery.getField());

		GeoDistance geoDistanceLowerBound =
			geoDistanceRangeQuery.getLowerBoundGeoDistance();

		rangeQueryBuilder.from(geoDistanceLowerBound.toString());

		rangeQueryBuilder.includeLower(geoDistanceRangeQuery.isIncludesLower());
		rangeQueryBuilder.includeUpper(geoDistanceRangeQuery.isIncludesUpper());

		GeoDistance geoDistanceUpperBound =
			geoDistanceRangeQuery.getUpperBoundGeoDistance();

		rangeQueryBuilder.to(geoDistanceUpperBound.toString());

		if (geoDistanceRangeQuery.getShapeRelation() != null) {
			ShapeRelation shapeRelation =
				geoDistanceRangeQuery.getShapeRelation();

			String shapeRelationName = shapeRelation.name();

			rangeQueryBuilder.relation(
				StringUtil.toLowerCase(shapeRelationName));
		}

		return rangeQueryBuilder;
	}

}