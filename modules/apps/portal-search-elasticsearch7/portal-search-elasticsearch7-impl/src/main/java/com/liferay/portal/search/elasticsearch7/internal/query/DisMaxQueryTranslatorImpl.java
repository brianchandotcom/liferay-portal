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

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.query.DisMaxQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.QueryVisitor;

import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = DisMaxQueryTranslator.class)
public class DisMaxQueryTranslatorImpl implements DisMaxQueryTranslator {

	@Override
	public QueryBuilder translate(
		DisMaxQuery disMaxQuery, QueryVisitor<QueryBuilder> queryVisitor) {

		DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();

		for (Query query : disMaxQuery.getQueries()) {
			QueryBuilder queryBuilder = query.accept(queryVisitor);

			disMaxQueryBuilder.add(queryBuilder);
		}

		if (disMaxQuery.getTieBreaker() != null) {
			disMaxQueryBuilder.tieBreaker(disMaxQuery.getTieBreaker());
		}

		return disMaxQueryBuilder;
	}

}