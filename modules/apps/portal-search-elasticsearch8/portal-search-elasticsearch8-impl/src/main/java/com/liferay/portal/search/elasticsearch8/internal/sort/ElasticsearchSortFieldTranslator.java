/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.sort;

import co.elastic.clients.elasticsearch._types.NestedSortValue;
import co.elastic.clients.elasticsearch._types.ScriptSortType;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.mapping.FieldType;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.search.elasticsearch8.internal.geolocation.GeoTranslator;
import com.liferay.portal.search.elasticsearch8.internal.query.ElasticsearchQueryVisitor;
import com.liferay.portal.search.elasticsearch8.internal.script.ScriptTranslator;
import com.liferay.portal.search.elasticsearch8.internal.util.ConversionUtil;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.GeoDistanceSort;
import com.liferay.portal.search.sort.NestedSort;
import com.liferay.portal.search.sort.ScoreSort;
import com.liferay.portal.search.sort.ScriptSort;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortFieldTranslator;
import com.liferay.portal.search.sort.SortMode;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.SortVisitor;

/**
 * @author Michael C. Han
 */
public class ElasticsearchSortFieldTranslator
	implements SortFieldTranslator<SortOptions>, SortVisitor<SortOptions> {

	@Override
	public SortOptions translate(Sort sort) {
		return sort.accept(this);
	}

	@Override
	public SortOptions visit(FieldSort fieldSort) {
		co.elastic.clients.elasticsearch._types.FieldSort.Builder builder =
			SortOptionsBuilders.field();

		builder.field(fieldSort.getField());

		if (fieldSort.getMissing() != null) {
			builder.missing(
				ConversionUtil.toFieldValue(fieldSort.getMissing()));
		}

		if (fieldSort.getSortMode() != null) {
			builder.mode(translateSortMode(fieldSort.getSortMode()));
		}

		if (fieldSort.getNestedSort() != null) {
			builder.nested(translateNestedSort(fieldSort.getNestedSort()));
		}

		builder.order(translateSortOrder(fieldSort.getSortOrder()));
		builder.unmappedType(FieldType.Keyword);

		return SortOptions.of(
			sortOptions -> sortOptions.field(builder.build()));
	}

	@Override
	public SortOptions visit(GeoDistanceSort geoDistanceSort) {
		co.elastic.clients.elasticsearch._types.GeoDistanceSort.Builder
			builder = SortOptionsBuilders.geoDistance();

		if (geoDistanceSort.getGeoDistanceType() != null) {
			builder.distanceType(
				_geoTranslator.translateGeoDistanceType(
					geoDistanceSort.getGeoDistanceType()));
		}

		builder.field(geoDistanceSort.getField());
		builder.location(
			TransformUtil.transform(
				geoDistanceSort.getGeoLocationPoints(),
				_geoTranslator::translateGeoLocationPoint));

		if (geoDistanceSort.getSortMode() != null) {
			builder.mode(translateSortMode(geoDistanceSort.getSortMode()));
		}

		if (geoDistanceSort.getSortOrder() != null) {
			builder.order(translateSortOrder(geoDistanceSort.getSortOrder()));
		}

		if (geoDistanceSort.getDistanceUnit() != null) {
			builder.unit(
				_geoTranslator.translateDistanceUnit(
					geoDistanceSort.getDistanceUnit()));
		}

		return SortOptions.of(
			sortOptions -> sortOptions.geoDistance(builder.build()));
	}

	@Override
	public SortOptions visit(ScoreSort scoreSort) {
		co.elastic.clients.elasticsearch._types.ScoreSort.Builder builder =
			SortOptionsBuilders.score();

		if (scoreSort.getSortOrder() != null) {
			builder.order(translateSortOrder(scoreSort.getSortOrder()));
		}

		return SortOptions.of(
			sortOptions -> sortOptions.score(builder.build()));
	}

	@Override
	public SortOptions visit(ScriptSort scriptSort) {
		co.elastic.clients.elasticsearch._types.ScriptSort.Builder builder =
			SortOptionsBuilders.script();

		if (scriptSort.getSortMode() != null) {
			builder.mode(translateSortMode(scriptSort.getSortMode()));
		}

		if (scriptSort.getNestedSort() != null) {
			builder.nested(translateNestedSort(scriptSort.getNestedSort()));
		}

		builder.order(translateSortOrder(scriptSort.getSortOrder()));
		builder.script(_scriptTranslator.translate(scriptSort.getScript()));

		if (scriptSort.getScriptSortType() ==
				ScriptSort.ScriptSortType.NUMBER) {

			builder.type(ScriptSortType.Number);
		}
		else {
			builder.type(ScriptSortType.String);
		}

		return SortOptions.of(
			sortOptions -> sortOptions.script(builder.build()));
	}

	protected NestedSortValue translateNestedSort(NestedSort nestedSort) {
		NestedSortValue.Builder builder = new NestedSortValue.Builder();

		if (nestedSort.getFilterQuery() != null) {
			builder.filter(
				new Query(
					ElasticsearchQueryVisitor.INSTANCE.translate(
						nestedSort.getFilterQuery())));
		}

		builder.maxChildren(nestedSort.getMaxChildren());

		if (nestedSort.getNestedSort() != null) {
			builder.nested(translateNestedSort(nestedSort.getNestedSort()));
		}

		builder.path(nestedSort.getPath());

		return builder.build();
	}

	protected co.elastic.clients.elasticsearch._types.SortMode
		translateSortMode(SortMode sortMode) {

		if (sortMode == SortMode.AVG) {
			return co.elastic.clients.elasticsearch._types.SortMode.Avg;
		}
		else if (sortMode == SortMode.MAX) {
			return co.elastic.clients.elasticsearch._types.SortMode.Max;
		}
		else if (sortMode == SortMode.MEDIAN) {
			return co.elastic.clients.elasticsearch._types.SortMode.Median;
		}
		else if (sortMode == SortMode.MIN) {
			return co.elastic.clients.elasticsearch._types.SortMode.Min;
		}
		else if (sortMode == SortMode.SUM) {
			return co.elastic.clients.elasticsearch._types.SortMode.Sum;
		}

		throw new IllegalArgumentException("Invalid sort mode " + sortMode);
	}

	protected co.elastic.clients.elasticsearch._types.SortOrder
		translateSortOrder(SortOrder sortOrder) {

		if ((sortOrder == SortOrder.ASC) || (sortOrder == null)) {
			return co.elastic.clients.elasticsearch._types.SortOrder.Asc;
		}

		return co.elastic.clients.elasticsearch._types.SortOrder.Desc;
	}

	private final GeoTranslator _geoTranslator = new GeoTranslator();
	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}