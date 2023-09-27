/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.query.RangeTermQuery;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = RangeTermQueryTranslator.class)
public class RangeTermQueryTranslatorImpl implements RangeTermQueryTranslator {

	@Override
	public QueryBuilder translate(RangeTermQuery rangeTermQuery) {
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(
			rangeTermQuery.getField());

		rangeQueryBuilder.from(rangeTermQuery.getLowerBound());
		rangeQueryBuilder.includeLower(rangeTermQuery.isIncludesLower());
		rangeQueryBuilder.includeUpper(rangeTermQuery.isIncludesUpper());
		rangeQueryBuilder.to(rangeTermQuery.getUpperBound());

		return rangeQueryBuilder;
	}

}