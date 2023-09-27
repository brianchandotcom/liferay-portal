/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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