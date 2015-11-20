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

package com.liferay.portal.search.elasticsearch.internal.filter;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.DateRangeTermFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.FilterTranslator;
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
import com.liferay.portal.search.elasticsearch.filter.BooleanFilterTranslator;
import com.liferay.portal.search.elasticsearch.filter.DateRangeTermFilterTranslator;
import com.liferay.portal.search.elasticsearch.filter.ExistsFilterTranslator;
import com.liferay.portal.search.elasticsearch.filter.GeoBoundingBoxFilterTranslator;
import com.liferay.portal.search.elasticsearch.filter.GeoDistanceFilterTranslator;
import com.liferay.portal.search.elasticsearch.filter.GeoDistanceRangeFilterTranslator;
import com.liferay.portal.search.elasticsearch.filter.GeoPolygonFilterTranslator;
import com.liferay.portal.search.elasticsearch.filter.MissingFilterTranslator;
import com.liferay.portal.search.elasticsearch.filter.PrefixFilterTranslator;
import com.liferay.portal.search.elasticsearch.filter.QueryFilterTranslator;
import com.liferay.portal.search.elasticsearch.filter.RangeTermFilterTranslator;
import com.liferay.portal.search.elasticsearch.filter.TermFilterTranslator;
import com.liferay.portal.search.elasticsearch.filter.TermsFilterTranslator;

import org.elasticsearch.index.query.FilterBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = {"search.engine.impl=Elasticsearch"},
	service = FilterTranslator.class
)
public class ElasticsearchFilterTranslator
	implements FilterTranslator<FilterBuilder>, FilterVisitor<FilterBuilder> {

	@Override
	public FilterBuilder translate(Filter filter, SearchContext searchContext) {
		return filter.accept(this);
	}

	@Override
	public FilterBuilder visit(BooleanFilter booleanFilter) {
		return _booleanQueryTranslator.translate(booleanFilter, this);
	}

	@Override
	public FilterBuilder visit(DateRangeTermFilter dateRangeTermFilter) {
		return _dateRangeTermFilterTranslator.translate(dateRangeTermFilter);
	}

	@Override
	public FilterBuilder visit(ExistsFilter existsFilter) {
		return _existsFilterTranslator.translate(existsFilter);
	}

	@Override
	public FilterBuilder visit(GeoBoundingBoxFilter geoBoundingBoxFilter) {
		return _geoBoundingBoxFilterTranslator.translate(geoBoundingBoxFilter);
	}

	@Override
	public FilterBuilder visit(GeoDistanceFilter geoDistanceFilter) {
		return _geoDistanceFilterTranslator.translate(geoDistanceFilter);
	}

	@Override
	public FilterBuilder visit(GeoDistanceRangeFilter geoDistanceRangeFilter) {
		return _geoDistanceRangeFilterTranslator.translate(
			geoDistanceRangeFilter);
	}

	@Override
	public FilterBuilder visit(GeoPolygonFilter geoPolygonFilter) {
		return _geoPolygonFilterTranslator.translate(geoPolygonFilter);
	}

	@Override
	public FilterBuilder visit(MissingFilter missingFilter) {
		return _missingFilterTranslator.translate(missingFilter);
	}

	@Override
	public FilterBuilder visit(PrefixFilter prefixFilter) {
		return _prefixFilterTranslator.translate(prefixFilter);
	}

	@Override
	public FilterBuilder visit(QueryFilter queryFilter) {
		return _queryFilterTranslator.translate(queryFilter);
	}

	@Override
	public FilterBuilder visit(RangeTermFilter rangeTermFilter) {
		return _rangeTermFilterTranslator.translate(rangeTermFilter);
	}

	@Override
	public FilterBuilder visit(TermFilter termFilter) {
		return _termFilterTranslator.translate(termFilter);
	}

	@Override
	public FilterBuilder visit(TermsFilter termsFilter) {
		return _termsFilterTranslator.translate(termsFilter);
	}

	@Reference(unbind = "-")
	protected void setBooleanQueryTranslator(
		BooleanFilterTranslator booleanQueryTranslator) {

		_booleanQueryTranslator = booleanQueryTranslator;
	}

	@Reference(unbind = "-")
	protected void setDateRangeTermFilterTranslator(
		DateRangeTermFilterTranslator dateRangeTermFilterTranslator) {

		_dateRangeTermFilterTranslator = dateRangeTermFilterTranslator;
	}

	@Reference(unbind = "-")
	protected void setExistsFilterTranslator(
		ExistsFilterTranslator existsFilterTranslator) {

		_existsFilterTranslator = existsFilterTranslator;
	}

	@Reference(unbind = "-")
	protected void setGeoBoundingBoxFilterTranslator(
		GeoBoundingBoxFilterTranslator geoBoundingBoxFilterTranslator) {

		_geoBoundingBoxFilterTranslator = geoBoundingBoxFilterTranslator;
	}

	@Reference(unbind = "-")
	protected void setGeoDistanceFilterTranslator(
		GeoDistanceFilterTranslator geoDistanceFilterTranslator) {

		_geoDistanceFilterTranslator = geoDistanceFilterTranslator;
	}

	@Reference(unbind = "-")
	protected void setGeoDistanceRangeFilterTranslator(
		GeoDistanceRangeFilterTranslator geoDistanceRangeFilterTranslator) {

		_geoDistanceRangeFilterTranslator = geoDistanceRangeFilterTranslator;
	}

	@Reference(unbind = "-")
	protected void setGeoPolygonFilterTranslator(
		GeoPolygonFilterTranslator geoPolygonFilterTranslator) {

		_geoPolygonFilterTranslator = geoPolygonFilterTranslator;
	}

	@Reference(unbind = "-")
	protected void setMissingFilterTranslator(
		MissingFilterTranslator missingFilterTranslator) {

		_missingFilterTranslator = missingFilterTranslator;
	}

	@Reference(unbind = "-")
	protected void setPrefixFilterTranslator(
		PrefixFilterTranslator prefixFilterTranslator) {

		_prefixFilterTranslator = prefixFilterTranslator;
	}

	@Reference(unbind = "-")
	protected void setQueryFilterTranslator(
		QueryFilterTranslator queryFilterTranslator) {

		_queryFilterTranslator = queryFilterTranslator;
	}

	@Reference(unbind = "-")
	protected void setRangeTermFilterTranslator(
		RangeTermFilterTranslator rangeTermFilterTranslator) {

		_rangeTermFilterTranslator = rangeTermFilterTranslator;
	}

	@Reference(unbind = "-")
	protected void setTermFilterTranslator(
		TermFilterTranslator termFilterTranslator) {

		_termFilterTranslator = termFilterTranslator;
	}

	@Reference(unbind = "-")
	protected void setTermsFilterTranslator(
		TermsFilterTranslator termsFilterTranslator) {

		_termsFilterTranslator = termsFilterTranslator;
	}

	private volatile BooleanFilterTranslator _booleanQueryTranslator;
	private volatile DateRangeTermFilterTranslator _dateRangeTermFilterTranslator;
	private volatile ExistsFilterTranslator _existsFilterTranslator;
	private volatile GeoBoundingBoxFilterTranslator _geoBoundingBoxFilterTranslator;
	private volatile GeoDistanceFilterTranslator _geoDistanceFilterTranslator;
	private volatile GeoDistanceRangeFilterTranslator _geoDistanceRangeFilterTranslator;
	private volatile GeoPolygonFilterTranslator _geoPolygonFilterTranslator;
	private volatile MissingFilterTranslator _missingFilterTranslator;
	private volatile PrefixFilterTranslator _prefixFilterTranslator;
	private volatile QueryFilterTranslator _queryFilterTranslator;
	private volatile RangeTermFilterTranslator _rangeTermFilterTranslator;
	private volatile TermFilterTranslator _termFilterTranslator;
	private volatile TermsFilterTranslator _termsFilterTranslator;

}