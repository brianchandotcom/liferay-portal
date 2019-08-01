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

package com.liferay.portal.search.elasticsearch6.internal.filter;

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
import com.liferay.portal.search.filter.DateRangeFilter;
import com.liferay.portal.search.filter.FilterVisitor;
import com.liferay.portal.search.filter.TermsSetFilter;

import org.elasticsearch.index.query.QueryBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 * @author Marco Leo
 */
@Component(
	immediate = true, property = "search.engine.impl=Elasticsearch",
	service = {FilterToQueryBuilderTranslator.class, FilterTranslator.class}
)
public class ElasticsearchFilterTranslator
	implements FilterToQueryBuilderTranslator, FilterTranslator<QueryBuilder>,
			   FilterVisitor<QueryBuilder> {

	public void setQueryFilterTranslator(
		QueryFilterTranslator queryFilterTranslator) {

		this.queryFilterTranslator = queryFilterTranslator;
	}

	@Override
	public QueryBuilder translate(Filter filter, SearchContext searchContext) {
		return filter.accept(this);
	}

	@Override
	public QueryBuilder visit(BooleanFilter booleanFilter) {
		return booleanFilterTranslator.translate(booleanFilter, this);
	}

	@Override
	public QueryBuilder visit(DateRangeFilter dateRangeFilter) {
		return dateRangeFilterTranslator.translate(dateRangeFilter);
	}

	@Override
	public QueryBuilder visit(DateRangeTermFilter dateRangeTermFilter) {
		return dateRangeTermFilterTranslator.translate(dateRangeTermFilter);
	}

	@Override
	public QueryBuilder visit(ExistsFilter existsFilter) {
		return existsFilterTranslator.translate(existsFilter);
	}

	@Override
	public QueryBuilder visit(GeoBoundingBoxFilter geoBoundingBoxFilter) {
		return geoBoundingBoxFilterTranslator.translate(geoBoundingBoxFilter);
	}

	@Override
	public QueryBuilder visit(GeoDistanceFilter geoDistanceFilter) {
		return geoDistanceFilterTranslator.translate(geoDistanceFilter);
	}

	@Override
	public QueryBuilder visit(GeoDistanceRangeFilter geoDistanceRangeFilter) {
		return geoDistanceRangeFilterTranslator.translate(
			geoDistanceRangeFilter);
	}

	@Override
	public QueryBuilder visit(GeoPolygonFilter geoPolygonFilter) {
		return geoPolygonFilterTranslator.translate(geoPolygonFilter);
	}

	@Override
	public QueryBuilder visit(MissingFilter missingFilter) {
		return missingFilterTranslator.translate(missingFilter);
	}

	@Override
	public QueryBuilder visit(PrefixFilter prefixFilter) {
		return prefixFilterTranslator.translate(prefixFilter);
	}

	@Override
	public QueryBuilder visit(QueryFilter queryFilter) {
		if (queryFilterTranslator == null) {
			throw new IllegalStateException(
				"No queryFilter translator configured");
		}

		return queryFilterTranslator.translate(queryFilter);
	}

	@Override
	public QueryBuilder visit(RangeTermFilter rangeTermFilter) {
		return rangeTermFilterTranslator.translate(rangeTermFilter);
	}

	@Override
	public QueryBuilder visit(TermFilter termFilter) {
		return termFilterTranslator.translate(termFilter);
	}

	@Override
	public QueryBuilder visit(TermsFilter termsFilter) {
		return termsFilterTranslator.translate(termsFilter);
	}

	@Override
	public QueryBuilder visit(TermsSetFilter termsSetFilter) {
		return termsSetFilterTranslator.translate(termsSetFilter);
	}

	@Reference
	protected BooleanFilterTranslator booleanFilterTranslator;

	@Reference
	protected DateRangeFilterTranslator dateRangeFilterTranslator;

	@Reference
	protected DateRangeTermFilterTranslator dateRangeTermFilterTranslator;

	@Reference
	protected ExistsFilterTranslator existsFilterTranslator;

	@Reference
	protected GeoBoundingBoxFilterTranslator geoBoundingBoxFilterTranslator;

	@Reference
	protected GeoDistanceFilterTranslator geoDistanceFilterTranslator;

	@Reference
	protected GeoDistanceRangeFilterTranslator geoDistanceRangeFilterTranslator;

	@Reference
	protected GeoPolygonFilterTranslator geoPolygonFilterTranslator;

	@Reference
	protected MissingFilterTranslator missingFilterTranslator;

	@Reference
	protected PrefixFilterTranslator prefixFilterTranslator;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile QueryFilterTranslator queryFilterTranslator;

	@Reference
	protected RangeTermFilterTranslator rangeTermFilterTranslator;

	@Reference
	protected TermFilterTranslator termFilterTranslator;

	@Reference
	protected TermsFilterTranslator termsFilterTranslator;

	@Reference
	protected TermsSetFilterTranslator termsSetFilterTranslator;

}