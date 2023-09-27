/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

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