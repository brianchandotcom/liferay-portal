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

package com.liferay.portal.search.elasticsearch7.internal.legacy.query;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.query.QueryVisitor;

import org.apache.lucene.search.join.ScoreMode;

import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = NestedQueryTranslator.class)
public class NestedQueryTranslatorImpl implements NestedQueryTranslator {

	@Override
	public QueryBuilder translate(
		NestedQuery nestedQuery, QueryVisitor<QueryBuilder> queryVisitor) {

		Query query = nestedQuery.getQuery();

		QueryBuilder queryBuilder = query.accept(queryVisitor);

		NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery(
			nestedQuery.getPath(), queryBuilder, ScoreMode.Total);

		if (!nestedQuery.isDefaultBoost()) {
			nestedQueryBuilder.boost(nestedQuery.getBoost());
		}

		return nestedQueryBuilder;
	}

}