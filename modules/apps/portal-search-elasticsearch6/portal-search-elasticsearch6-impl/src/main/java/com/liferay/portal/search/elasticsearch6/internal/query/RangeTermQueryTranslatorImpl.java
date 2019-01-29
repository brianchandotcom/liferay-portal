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

package com.liferay.portal.search.elasticsearch6.internal.query;

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