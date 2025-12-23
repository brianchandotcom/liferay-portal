/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.legacy.sort;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.GeoDistanceType;
import co.elastic.clients.elasticsearch._types.NestedSortValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.mapping.FieldType;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GeoDistanceSort;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch8.internal.geolocation.GeoTranslator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class SortTranslator {

	public List<SortOptions> translateSorts(Sort[] sorts) {
		List<SortOptions> sortOptions = new ArrayList<>();

		if (ArrayUtil.isEmpty(sorts)) {
			return sortOptions;
		}

		Set<String> sortFieldNames = new HashSet<>();

		for (Sort sort : sorts) {
			if (sort == null) {
				continue;
			}

			String sortFieldName = _getSortFieldName(sort);

			if (sortFieldNames.contains(sortFieldName)) {
				continue;
			}

			sortFieldNames.add(sortFieldName);

			sortOptions.add(_getSortOptions(sortFieldName, sort));
		}

		return sortOptions;
	}

	private SortOptions _getFieldSortOptions(String fieldName, Sort sort) {
		co.elastic.clients.elasticsearch._types.FieldSort.Builder builder =
			SortOptionsBuilders.field();

		builder.field(fieldName);

		if (sort.isReverse()) {
			builder.order(SortOrder.Desc);
		}
		else {
			builder.order(SortOrder.Asc);
		}

		builder.unmappedType(FieldType.Keyword);

		return SortOptions.of(
			sortOptions -> sortOptions.field(builder.build()));
	}

	private SortOptions _getGeoDistanceSortOptions(
		String fieldName, Sort sort) {

		GeoDistanceSort geoDistanceSort = (GeoDistanceSort)sort;

		co.elastic.clients.elasticsearch._types.GeoDistanceSort.Builder
			builder = SortOptionsBuilders.geoDistance();

		builder.distanceType(GeoDistanceType.Arc);
		builder.field(fieldName);
		builder.location(
			TransformUtil.transform(
				geoDistanceSort.getGeoLocationPoints(),
				_geoTranslator::translateGeoLocationPoint));

		if (geoDistanceSort.isReverse()) {
			builder.order(SortOrder.Desc);
		}

		return SortOptions.of(
			sortOptions -> sortOptions.geoDistance(builder.build()));
	}

	private SortOptions _getNestedFieldSortOptions(
		String fieldName, Sort sort) {

		co.elastic.clients.elasticsearch._types.FieldSort.Builder builder =
			SortOptionsBuilders.field();

		String[] fieldNameParts = StringUtil.split(fieldName, StringPool.POUND);

		builder.field(fieldNameParts[0]);

		builder.nested(
			NestedSortValue.of(
				nestedSortValue -> nestedSortValue.path(
					"nestedFieldArray"
				).filter(
					new Query(
						QueryBuilders.term(
						).field(
							"nestedFieldArray.fieldName"
						).value(
							FieldValue.of(fieldNameParts[1])
						).build())
				)));

		if (sort.isReverse()) {
			builder.order(SortOrder.Desc);
		}

		return SortOptions.of(
			sortOptions -> sortOptions.field(builder.build()));
	}

	private SortOptions _getScoreSortOptions(Sort sort) {
		co.elastic.clients.elasticsearch._types.ScoreSort.Builder builder =
			SortOptionsBuilders.score();

		if (sort.isReverse()) {
			builder.order(SortOrder.Asc);
		}

		return SortOptions.of(
			sortOptions -> sortOptions.score(builder.build()));
	}

	private String _getSortFieldName(Sort sort) {
		String sortFieldName = sort.getFieldName();

		if (Objects.equals(sortFieldName, Field.ENTRY_CLASS_NAME) ||
			Objects.equals(sortFieldName, Field.PRIORITY) ||
			Objects.equals(sortFieldName, _SCORE_FIELD_NAME) ||
			Objects.equals(sortFieldName, "_id") ||
			Objects.equals(sortFieldName, "_index") ||
			StringUtil.endsWith(sortFieldName, "_sortable.keyword") ||
			StringUtil.startsWith(sortFieldName, "nestedFieldArray.")) {

			return sortFieldName;
		}

		return Field.getSortFieldName(sort, _SCORE_FIELD_NAME);
	}

	private SortOptions _getSortOptions(String fieldName, Sort sort) {
		if (fieldName.equals(_SCORE_FIELD_NAME)) {
			return _getScoreSortOptions(sort);
		}

		if (sort.getType() == Sort.GEO_DISTANCE_TYPE) {
			return _getGeoDistanceSortOptions(fieldName, sort);
		}

		if (fieldName.startsWith("nestedFieldArray.")) {
			return _getNestedFieldSortOptions(fieldName, sort);
		}

		return _getFieldSortOptions(fieldName, sort);
	}

	private static final String _SCORE_FIELD_NAME = "_score";

	private final GeoTranslator _geoTranslator = new GeoTranslator();

}