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

import com.liferay.portal.search.query.MatchPhrasePrefixQuery;

import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = MatchPhrasePrefixQueryTranslator.class)
public class MatchPhrasePrefixQueryTranslatorImpl
	implements MatchPhrasePrefixQueryTranslator {

	@Override
	public QueryBuilder translate(
		MatchPhrasePrefixQuery matchPhrasePrefixQuery) {

		MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder =
			QueryBuilders.matchPhrasePrefixQuery(
				matchPhrasePrefixQuery.getField(),
				matchPhrasePrefixQuery.getValue());

		if (matchPhrasePrefixQuery.getAnalyzer() != null) {
			matchPhrasePrefixQueryBuilder.analyzer(
				matchPhrasePrefixQuery.getAnalyzer());
		}

		if (matchPhrasePrefixQuery.getSlop() != null) {
			matchPhrasePrefixQueryBuilder.slop(
				matchPhrasePrefixQuery.getSlop());
		}

		if (matchPhrasePrefixQuery.getMaxExpansions() != null) {
			matchPhrasePrefixQueryBuilder.maxExpansions(
				matchPhrasePrefixQuery.getMaxExpansions());
		}

		return matchPhrasePrefixQueryBuilder;
	}

}