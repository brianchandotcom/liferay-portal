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

import com.liferay.portal.search.query.BoostingQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.QueryVisitor;

import org.elasticsearch.index.query.BoostingQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = BoostingQueryTranslator.class)
public class BoostingQueryTranslatorImpl implements BoostingQueryTranslator {

	@Override
	public QueryBuilder translate(
		BoostingQuery boostingQuery, QueryVisitor<QueryBuilder> queryVisitor) {

		Query positiveQuery = boostingQuery.getPositiveQuery();

		QueryBuilder positiveQueryBuilder = positiveQuery.accept(queryVisitor);

		Query negativeQuery = boostingQuery.getNegativeQuery();

		QueryBuilder negativeQueryBuilder = negativeQuery.accept(queryVisitor);

		BoostingQueryBuilder boostingQueryBuilder = QueryBuilders.boostingQuery(
			positiveQueryBuilder, negativeQueryBuilder);

		Float negativeBoost = boostingQuery.getNegativeBoost();

		if (negativeBoost != null) {
			boostingQueryBuilder.negativeBoost(negativeBoost);
		}

		return boostingQueryBuilder;
	}

}