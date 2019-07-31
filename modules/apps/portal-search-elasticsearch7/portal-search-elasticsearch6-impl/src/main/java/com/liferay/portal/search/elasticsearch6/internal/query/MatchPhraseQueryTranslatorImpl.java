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

import com.liferay.portal.search.query.MatchPhraseQuery;

import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = MatchPhraseQueryTranslator.class)
public class MatchPhraseQueryTranslatorImpl
	implements MatchPhraseQueryTranslator {

	@Override
	public QueryBuilder translate(MatchPhraseQuery matchPhraseQuery) {
		MatchPhraseQueryBuilder matchPhraseQueryBuilder =
			QueryBuilders.matchPhraseQuery(
				matchPhraseQuery.getField(), matchPhraseQuery.getValue());

		if (matchPhraseQuery.getAnalyzer() != null) {
			matchPhraseQueryBuilder.analyzer(matchPhraseQuery.getAnalyzer());
		}

		if (matchPhraseQuery.getSlop() != null) {
			matchPhraseQueryBuilder.slop(matchPhraseQuery.getSlop());
		}

		return matchPhraseQueryBuilder;
	}

}