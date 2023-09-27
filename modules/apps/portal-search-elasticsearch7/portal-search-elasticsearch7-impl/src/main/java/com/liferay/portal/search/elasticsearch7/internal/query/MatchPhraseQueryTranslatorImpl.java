/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

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