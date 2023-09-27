/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.legacy.query;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.DisMaxQuery;
import com.liferay.portal.kernel.search.query.QueryVisitor;

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

		if (!disMaxQuery.isDefaultBoost()) {
			disMaxQueryBuilder.boost(disMaxQuery.getBoost());
		}

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