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

import com.liferay.portal.kernel.search.filter.RangeTermFilter;
import com.liferay.portal.search.elasticsearch.filter.RangeTermFilterTranslator;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = RangeTermFilterTranslator.class)
public class RangeTermFilterTranslatorImpl
	implements RangeTermFilterTranslator {

	@Override
	public QueryBuilder translate(RangeTermFilter rangeTermFilter) {
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(
			rangeTermFilter.getField());

		rangeQueryBuilder.from(rangeTermFilter.getLowerBound());
		rangeQueryBuilder.includeLower(rangeTermFilter.isIncludesLower());
		rangeQueryBuilder.includeUpper(rangeTermFilter.isIncludesUpper());
		rangeQueryBuilder.to(rangeTermFilter.getUpperBound());

		return rangeQueryBuilder;
	}

}