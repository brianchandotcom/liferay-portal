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

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.portal.search.filter.DateRangeFilter;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eric Yan
 * @author André de Oliveira
 */
@Component(immediate = true, service = DateRangeFilterTranslator.class)
public class DateRangeFilterTranslatorImpl
	implements DateRangeFilterTranslator {

	@Override
	public QueryBuilder translate(DateRangeFilter dateRangeFilter) {
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(
			dateRangeFilter.getFieldName());

		if (dateRangeFilter.getFormat() != null) {
			rangeQueryBuilder.format(dateRangeFilter.getFormat());
		}

		rangeQueryBuilder.from(dateRangeFilter.getFrom());
		rangeQueryBuilder.includeLower(dateRangeFilter.isIncludeLower());
		rangeQueryBuilder.includeUpper(dateRangeFilter.isIncludeUpper());

		if (dateRangeFilter.getTimeZoneId() != null) {
			rangeQueryBuilder.timeZone(dateRangeFilter.getTimeZoneId());
		}

		rangeQueryBuilder.to(dateRangeFilter.getTo());

		return rangeQueryBuilder;
	}

}