/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.query.ConstantScoreQuery;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.QueryVisitor;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = ConstantScoreQueryTranslator.class)
public class ConstantScoreQueryTranslatorImpl
	implements ConstantScoreQueryTranslator {

	@Override
	public QueryBuilder translate(
		ConstantScoreQuery constantScoreQuery,
		QueryVisitor<QueryBuilder> queryVisitor) {

		Query query = constantScoreQuery.getQuery();

		QueryBuilder queryBuilder = query.accept(queryVisitor);

		return QueryBuilders.constantScoreQuery(queryBuilder);
	}

}