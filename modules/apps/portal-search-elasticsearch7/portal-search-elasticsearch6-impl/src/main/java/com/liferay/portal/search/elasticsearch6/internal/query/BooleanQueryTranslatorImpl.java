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

import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.QueryVisitor;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = BooleanQueryTranslator.class)
public class BooleanQueryTranslatorImpl implements BooleanQueryTranslator {

	@Override
	public QueryBuilder translate(
		BooleanQuery booleanQuery, QueryVisitor<QueryBuilder> queryVisitor) {

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		processQueryClause(
			booleanQuery.getMustQueryClauses(), queryVisitor,
			boolQueryBuilder::must);

		processQueryClause(
			booleanQuery.getMustNotQueryClauses(), queryVisitor,
			boolQueryBuilder::mustNot);

		processQueryClause(
			booleanQuery.getShouldQueryClauses(), queryVisitor,
			boolQueryBuilder::should);

		processQueryClause(
			booleanQuery.getFilterQueryClauses(), queryVisitor,
			boolQueryBuilder::filter);

		if (booleanQuery.getAdjustPureNegative() != null) {
			boolQueryBuilder.adjustPureNegative(
				booleanQuery.getAdjustPureNegative());
		}

		if (booleanQuery.getMinimumShouldMatch() != null) {
			boolQueryBuilder.minimumShouldMatch(
				booleanQuery.getMinimumShouldMatch());
		}

		boolQueryBuilder.queryName(booleanQuery.getQueryName());

		return boolQueryBuilder;
	}

	protected void processQueryClause(
		List<Query> queryClauses, QueryVisitor<QueryBuilder> queryVisitor,
		QueryBuilderConsumer queryBuilderConsumer) {

		for (Query queryClause : queryClauses) {
			QueryBuilder queryBuilder = queryClause.accept(queryVisitor);

			queryBuilderConsumer.accept(queryBuilder);
		}
	}

	protected interface QueryBuilderConsumer {

		public void accept(QueryBuilder queryBuilder);

	}

}