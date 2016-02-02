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

package com.liferay.portal.search.solr.internal.query;

import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.solr.query.MatchQueryTranslator;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = MatchQueryTranslator.class)
public class MatchQueryTranslatorImpl implements MatchQueryTranslator {

	@Override
	public org.apache.lucene.search.Query translate(MatchQuery matchQuery) {
		MatchQuery.Type matchQueryType = matchQuery.getType();

		if (matchQueryType == MatchQuery.Type.BOOLEAN) {
			QueryParser queryParser = new QueryParser(
				matchQuery.getField(), new KeywordAnalyzer());

			try {
				return queryParser.parse(matchQuery.getValue());
			}
			catch (ParseException pe) {
				throw new IllegalArgumentException(pe);
			}
		}

		String value = matchQuery.getValue();

		if (value.startsWith(StringPool.QUOTE) &&
			value.endsWith(StringPool.QUOTE)) {

			value = value.substring(1, value.length() - 1);

			if (value.endsWith(StringPool.STAR)) {
				value = value.substring(0, value.length() - 1);

				matchQueryType = MatchQuery.Type.PHRASE_PREFIX;
			}
			else {
				matchQueryType = MatchQuery.Type.PHRASE;
			}
		}

		Term term = new Term(matchQuery.getField(), value);

		org.apache.lucene.search.Query query = null;

		if (matchQueryType == MatchQuery.Type.PHRASE_PREFIX) {
			query = new PrefixQuery(term);
		}
		else {
			PhraseQuery phraseQuery = new PhraseQuery();

			phraseQuery.add(term);

			if (matchQuery.getSlop() != null) {
				phraseQuery.setSlop(matchQuery.getSlop());
			}

			query = phraseQuery;
		}

		if (!matchQuery.isDefaultBoost()) {
			query.setBoost(matchQuery.getBoost());
		}

		return query;
	}

}