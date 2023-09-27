/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.query.DateRangeTermQuery;

import java.util.TimeZone;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = DateRangeTermQueryTranslator.class)
public class DateRangeTermQueryTranslatorImpl
	implements DateRangeTermQueryTranslator {

	@Override
	public QueryBuilder translate(DateRangeTermQuery dateRangeTermQuery) {
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(
			dateRangeTermQuery.getField());

		if (dateRangeTermQuery.getDateFormat() != null) {
			rangeQueryBuilder.format(dateRangeTermQuery.getDateFormat());
		}

		rangeQueryBuilder.from(dateRangeTermQuery.getLowerBound());
		rangeQueryBuilder.includeLower(dateRangeTermQuery.isIncludesLower());
		rangeQueryBuilder.includeUpper(dateRangeTermQuery.isIncludesUpper());

		TimeZone timeZone = dateRangeTermQuery.getTimeZone();

		if (timeZone != null) {
			rangeQueryBuilder.timeZone(timeZone.getID());
		}

		rangeQueryBuilder.to(dateRangeTermQuery.getUpperBound());

		return rangeQueryBuilder;
	}

}