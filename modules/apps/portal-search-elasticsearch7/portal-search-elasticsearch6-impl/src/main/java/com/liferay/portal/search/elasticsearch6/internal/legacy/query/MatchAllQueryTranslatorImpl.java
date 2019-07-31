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

package com.liferay.portal.search.elasticsearch6.internal.legacy.query;

import com.liferay.portal.kernel.search.generic.MatchAllQuery;

import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = MatchAllQueryTranslator.class)
public class MatchAllQueryTranslatorImpl implements MatchAllQueryTranslator {

	@Override
	public QueryBuilder translate(MatchAllQuery matchAllQuery) {
		MatchAllQueryBuilder matchAllQueryBuilder =
			QueryBuilders.matchAllQuery();

		if (!matchAllQuery.isDefaultBoost()) {
			matchAllQueryBuilder.boost(matchAllQuery.getBoost());
		}

		return matchAllQueryBuilder;
	}

}