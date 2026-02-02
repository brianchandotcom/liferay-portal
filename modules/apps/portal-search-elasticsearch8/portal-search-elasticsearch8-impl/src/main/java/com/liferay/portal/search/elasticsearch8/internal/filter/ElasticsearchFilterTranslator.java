/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.filter;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.GeoBounds;
import co.elastic.clients.elasticsearch._types.TopLeftBottomRightGeoBounds;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.DateRangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ExistsQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoBoundingBoxQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoDistanceQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoPolygonPoints;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoPolygonQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.PrefixQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermRangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsSetQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.UntypedRangeQuery;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.DateRangeTermFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.FilterTranslator;
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
import com.liferay.portal.kernel.search.geolocation.DistanceUnit;
import com.liferay.portal.kernel.search.geolocation.GeoDistance;
import com.liferay.portal.kernel.search.geolocation.GeoLocationPoint;
import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.search.elasticsearch8.internal.geolocation.GeoTranslator;
import com.liferay.portal.search.elasticsearch8.internal.legacy.query.ElasticsearchQueryTranslator;
import com.liferay.portal.search.elasticsearch8.internal.util.QueryUtil;
import com.liferay.portal.search.elasticsearch8.internal.util.SetterUtil;
import com.liferay.portal.search.filter.DateRangeFilter;
import com.liferay.portal.search.filter.FilterVisitor;
import com.liferay.portal.search.filter.RangeFilter;
import com.liferay.portal.search.filter.TermsSetFilter;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.text.Format;
import java.text.ParseException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Marco Leo
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = FilterTranslator.class
)
public class ElasticsearchFilterTranslator
	implements FilterTranslator<QueryVariant>, FilterVisitor<QueryVariant> {

	@Override
	public QueryVariant translate(Filter filter, SearchContext searchContext) {
		return filter.accept(this);
	}

	@Override
	public QueryVariant visit(BooleanFilter booleanFilter) {
		BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

		for (BooleanClause<Filter> booleanClause :
				booleanFilter.getMustBooleanClauses()) {

			boolQueryBuilder.must(new Query(translate(booleanClause, this)));
		}

		for (BooleanClause<Filter> booleanClause :
				booleanFilter.getMustNotBooleanClauses()) {

			boolQueryBuilder.mustNot(new Query(translate(booleanClause, this)));
		}

		for (BooleanClause<Filter> booleanClause :
				booleanFilter.getShouldBooleanClauses()) {

			boolQueryBuilder.should(new Query(translate(booleanClause, this)));
		}

		return boolQueryBuilder.build();
	}

	@Override
	public QueryVariant visit(DateRangeFilter dateRangeFilter) {
		RangeQuery.Builder builder = QueryBuilders.range();

		DateRangeQuery.Builder dateRangeQueryBuilder =
			new DateRangeQuery.Builder();

		dateRangeQueryBuilder.field(dateRangeFilter.getFieldName());

		SetterUtil.setNotBlankString(
			dateRangeQueryBuilder::format, dateRangeFilter.getFormat());

		QueryUtil.setRanges(
			dateRangeQueryBuilder, dateRangeFilter.isIncludeLower(),
			dateRangeFilter.isIncludeUpper(), dateRangeFilter.getFrom(),
			dateRangeFilter.getTo());

		SetterUtil.setNotBlankString(
			dateRangeQueryBuilder::timeZone, dateRangeFilter.getTimeZoneId());

		builder.date(dateRangeQueryBuilder.build());

		return builder.build();
	}

	@Override
	public QueryVariant visit(DateRangeTermFilter dateRangeTermFilter) {
		DateRangeQuery.Builder dateRangeQueryBuilder =
			new DateRangeQuery.Builder();

		dateRangeQueryBuilder.field(dateRangeTermFilter.getField());

		Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
			dateRangeTermFilter.getDateFormat(),
			dateRangeTermFilter.getTimeZone());

		SetterUtil.setNotBlankString(
			dateRangeQueryBuilder::format, dateRangeTermFilter.getDateFormat());

		try {
			QueryUtil.setRanges(
				dateRangeQueryBuilder, dateRangeTermFilter.isIncludesLower(),
				dateRangeTermFilter.isIncludesUpper(),
				format.parseObject(dateRangeTermFilter.getLowerBound()),
				format.parseObject(dateRangeTermFilter.getUpperBound()));
		}
		catch (ParseException parseException) {
			throw new IllegalArgumentException(
				"Invalid date range " + dateRangeTermFilter, parseException);
		}

		RangeQuery.Builder builder = QueryBuilders.range();

		builder.date(dateRangeQueryBuilder.build());

		return builder.build();
	}

	@Override
	public QueryVariant visit(ExistsFilter existsFilter) {
		return ExistsQuery.of(
			existsQuery -> existsQuery.field(existsFilter.getField()));
	}

	@Override
	public QueryVariant visit(GeoBoundingBoxFilter geoBoundingBoxFilter) {
		TopLeftBottomRightGeoBounds tlbr = TopLeftBottomRightGeoBounds.of(
			topLeftBottomRightGeoBounds ->
				topLeftBottomRightGeoBounds.bottomRight(
					_geoTranslator.translateGeoLocationPoint(
						geoBoundingBoxFilter.getBottomRightGeoLocationPoint())
				).topLeft(
					_geoTranslator.translateGeoLocationPoint(
						geoBoundingBoxFilter.getTopLeftGeoLocationPoint())
				));

		return GeoBoundingBoxQuery.of(
			geoBoundingBoxQuery -> geoBoundingBoxQuery.field(
				geoBoundingBoxFilter.getField()
			).boundingBox(
				GeoBounds.of(geoBounds -> geoBounds.tlbr(tlbr))
			));
	}

	@Override
	public QueryVariant visit(GeoDistanceFilter geoDistanceFilter) {
		return GeoDistanceQuery.of(
			geoDistanceQuery -> geoDistanceQuery.distance(
				String.valueOf(geoDistanceFilter.getGeoDistance())
			).field(
				geoDistanceFilter.getField()
			).location(
				_geoTranslator.translateGeoLocationPoint(
					geoDistanceFilter.getPinGeoLocationPoint())
			));
	}

	@Override
	public QueryVariant visit(GeoDistanceRangeFilter geoDistanceRangeFilter) {
		GeoDistanceQuery.Builder builder = QueryBuilders.geoDistance();

		GeoDistance geoDistance =
			geoDistanceRangeFilter.getUpperBoundGeoDistance();

		DistanceUnit distanceUnit = geoDistance.getDistanceUnit();

		builder.distance(
			String.valueOf(geoDistance.getDistance()) + distanceUnit.getUnit());

		builder.field(geoDistanceRangeFilter.getField());
		builder.location(
			_geoTranslator.translateGeoLocationPoint(
				geoDistanceRangeFilter.getPinGeoLocationPoint()));

		return builder.build();
	}

	@Override
	public QueryVariant visit(GeoPolygonFilter geoPolygonFilter) {
		GeoPolygonPoints.Builder builder = new GeoPolygonPoints.Builder();

		for (GeoLocationPoint geoLocationPoint :
				geoPolygonFilter.getGeoLocationPoints()) {

			builder.points(
				_geoTranslator.translateGeoLocationPoint(geoLocationPoint));
		}

		return GeoPolygonQuery.of(
			geoPolygonQuery -> geoPolygonQuery.field(
				geoPolygonFilter.getField()
			).polygon(
				builder.build()
			));
	}

	@Override
	public QueryVariant visit(MissingFilter missingFilter) {
		return BoolQuery.of(
			boolQuery -> boolQuery.mustNot(
				new Query(
					ExistsQuery.of(
						existsQuery -> existsQuery.field(
							missingFilter.getField())))));
	}

	@Override
	public QueryVariant visit(PrefixFilter prefixFilter) {
		return PrefixQuery.of(
			prefixQuery -> prefixQuery.field(
				prefixFilter.getField()
			).value(
				prefixFilter.getPrefix()
			));
	}

	@Override
	public QueryVariant visit(QueryFilter queryFilter) {
		return _queryTranslator.translate(queryFilter.getQuery(), null);
	}

	@Override
	public QueryVariant visit(RangeFilter rangeFilter) {
		RangeQuery.Builder builder = QueryBuilders.range();

		UntypedRangeQuery.Builder untypedRangeQueryBuilder =
			new UntypedRangeQuery.Builder();

		untypedRangeQueryBuilder.field(rangeFilter.getFieldName());

		SetterUtil.setNotBlankString(
			untypedRangeQueryBuilder::format, rangeFilter.getFormat());

		QueryUtil.setRanges(
			untypedRangeQueryBuilder, rangeFilter.isIncludeLower(),
			rangeFilter.isIncludeUpper(), rangeFilter.getFrom(),
			rangeFilter.getTo());

		SetterUtil.setNotBlankString(
			untypedRangeQueryBuilder::timeZone, rangeFilter.getTimeZoneId());

		builder.untyped(untypedRangeQueryBuilder.build());

		return builder.build();
	}

	@Override
	public QueryVariant visit(RangeTermFilter rangeTermFilter) {
		RangeQuery.Builder builder = QueryBuilders.range();

		TermRangeQuery.Builder termRangeQueryBuilder =
			new TermRangeQuery.Builder();

		termRangeQueryBuilder.field(rangeTermFilter.getField());

		QueryUtil.setRanges(
			termRangeQueryBuilder, rangeTermFilter.isIncludesLower(),
			rangeTermFilter.isIncludesUpper(), rangeTermFilter.getLowerBound(),
			rangeTermFilter.getUpperBound());

		builder.term(termRangeQueryBuilder.build());

		return builder.build();
	}

	@Override
	public QueryVariant visit(TermFilter termFilter) {
		return TermQuery.of(
			termQuery -> termQuery.field(
				termFilter.getField()
			).value(
				FieldValue.of(termFilter.getValue())
			));
	}

	@Override
	public QueryVariant visit(TermsFilter termsFilter) {
		return QueryUtil.translateTerms(
			null, termsFilter.getField(), termsFilter.getValues());
	}

	@Override
	public QueryVariant visit(TermsSetFilter termsSetFilter) {
		TermsSetQuery.Builder builder = QueryBuilders.termsSet();

		builder.field(termsSetFilter.getFieldName());

		SetterUtil.setNotBlankString(
			builder::minimumShouldMatchField,
			termsSetFilter.getMinimumShouldMatchField());

		builder.terms(termsSetFilter.getValues());

		return builder.build();
	}

	@Activate
	protected void activate() {
		_queryTranslator = new ElasticsearchQueryTranslator(indexNameBuilder);
	}

	protected QueryVariant translate(
		BooleanClause<Filter> booleanClause,
		FilterVisitor<QueryVariant> filterVisitor) {

		Filter filter = booleanClause.getClause();

		return filter.accept(filterVisitor);
	}

	@Reference
	protected IndexNameBuilder indexNameBuilder;

	private final GeoTranslator _geoTranslator = new GeoTranslator();
	private QueryTranslator<QueryVariant> _queryTranslator;

}