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

package com.liferay.portal.search.elasticsearch7.internal.sort;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GeoDistanceSort;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = SortTranslator.class)
public class DefaultSortTranslator implements SortTranslator {

	@Override
	public void translate(
		SearchSourceBuilder searchSourceBuilder, Sort[] sorts) {

		if (ArrayUtil.isEmpty(sorts)) {
			return;
		}

		Set<String> sortFieldNames = new HashSet<>();

		for (Sort sort : sorts) {
			if (sort == null) {
				continue;
			}

			String sortFieldName = getSortFieldName(sort, "_score");

			if (sortFieldNames.contains(sortFieldName)) {
				continue;
			}

			sortFieldNames.add(sortFieldName);

			searchSourceBuilder.sort(getSortBuilder(sort, sortFieldName));
		}
	}

	protected SortBuilder<?> getFieldSortBuilder(Sort sort, String fieldName) {
		FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(fieldName);

		fieldSortBuilder.unmappedType("keyword");

		if (sort.isReverse()) {
			fieldSortBuilder.order(SortOrder.DESC);
		}

		return fieldSortBuilder;
	}

	protected SortBuilder<?> getGeoDistanceSortBuilder(
		Sort sort, String fieldName) {

		GeoDistanceSort geoDistanceSort = (GeoDistanceSort)sort;

		List<GeoPoint> geoPoints = new ArrayList<>();

		for (GeoLocationPoint geoLocationPoint :
				geoDistanceSort.getGeoLocationPoints()) {

			geoPoints.add(
				new GeoPoint(
					geoLocationPoint.getLatitude(),
					geoLocationPoint.getLongitude()));
		}

		GeoDistanceSortBuilder geoDistanceSortBuilder =
			SortBuilders.geoDistanceSort(
				fieldName, geoPoints.toArray(new GeoPoint[0]));

		geoDistanceSortBuilder.geoDistance(GeoDistance.ARC);

		Collection<String> geoHashes = geoDistanceSort.getGeoHashes();

		if (!geoHashes.isEmpty()) {
			geoDistanceSort.addGeoHash(geoHashes.toArray(new String[0]));
		}

		if (sort.isReverse()) {
			geoDistanceSortBuilder.order(SortOrder.DESC);
		}

		return geoDistanceSortBuilder;
	}

	protected SortBuilder<?> getScoreSortBuilder(Sort sort) {
		SortBuilder<?> sortBuilder = SortBuilders.scoreSort();

		if (sort.isReverse()) {
			sortBuilder.order(SortOrder.ASC);
		}

		return sortBuilder;
	}

	protected SortBuilder<?> getSortBuilder(Sort sort, String fieldName) {
		if (fieldName.equals("_score")) {
			return getScoreSortBuilder(sort);
		}

		if (sort.getType() == Sort.GEO_DISTANCE_TYPE) {
			return getGeoDistanceSortBuilder(sort, fieldName);
		}

		return getFieldSortBuilder(sort, fieldName);
	}

	protected String getSortFieldName(Sort sort, String scoreFieldName) {
		String sortFieldName = sort.getFieldName();

		if (Objects.equals(sortFieldName, Field.PRIORITY)) {
			return sortFieldName;
		}

		if (Objects.equals(sortFieldName, "_score")) {
			return sortFieldName;
		}

		return Field.getSortFieldName(sort, scoreFieldName);
	}

}