/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = NestedQueryTranslator.class)
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