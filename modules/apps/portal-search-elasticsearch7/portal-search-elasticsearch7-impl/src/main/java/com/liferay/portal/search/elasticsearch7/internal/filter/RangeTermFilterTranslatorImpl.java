/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.portal.kernel.search.filter.RangeTermFilter;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = RangeTermFilterTranslator.class)
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