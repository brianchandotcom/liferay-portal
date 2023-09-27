/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.FilterVisitor;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = BooleanFilterTranslator.class)
public class BooleanFilterTranslatorImpl implements BooleanFilterTranslator {

	@Override
	public QueryBuilder translate(
		BooleanFilter booleanFilter,
		FilterVisitor<QueryBuilder> filterVisitor) {

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		for (BooleanClause<Filter> booleanClause :
				booleanFilter.getMustBooleanClauses()) {

			QueryBuilder queryBuilder = translate(booleanClause, filterVisitor);

			boolQueryBuilder.must(queryBuilder);
		}

		for (BooleanClause<Filter> booleanClause :
				booleanFilter.getMustNotBooleanClauses()) {

			QueryBuilder queryBuilder = translate(booleanClause, filterVisitor);

			boolQueryBuilder.mustNot(queryBuilder);
		}

		for (BooleanClause<Filter> booleanClause :
				booleanFilter.getShouldBooleanClauses()) {

			QueryBuilder queryBuilder = translate(booleanClause, filterVisitor);

			boolQueryBuilder.should(queryBuilder);
		}

		return boolQueryBuilder;
	}

	protected QueryBuilder translate(
		BooleanClause<Filter> booleanClause,
		FilterVisitor<QueryBuilder> filterVisitor) {

		Filter filter = booleanClause.getClause();

		return filter.accept(filterVisitor);
	}

}