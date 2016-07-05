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

		String value = matchQuery.getValue();

		if (value.startsWith(StringPool.QUOTE) &&
			value.endsWith(StringPool.QUOTE)) {

			matchQueryType = MatchQuery.Type.PHRASE;

			value = value.substring(1, value.length() - 1);

			if (value.endsWith(StringPool.STAR)) {
				matchQueryType = MatchQuery.Type.PHRASE_PREFIX;

				value = value.substring(0, value.length() - 1);
			}
		}

		if (matchQueryType == null) {
			matchQueryType = MatchQuery.Type.BOOLEAN;
		}

		org.apache.lucene.search.Query query = createQuery(
			matchQueryType, matchQuery.getField(), value);

		if ((query instanceof PhraseQuery) && (matchQuery.getSlop() != null)) {
			PhraseQuery phraseQuery = (PhraseQuery)query;

			phraseQuery.setSlop(matchQuery.getSlop());
		}

		if (!matchQuery.isDefaultBoost()) {
			query.setBoost(matchQuery.getBoost());
		}

		return query;
	}

	protected org.apache.lucene.search.Query createPhraseQuery(
		String field, String value) {

		PhraseQuery phraseQuery = new PhraseQuery();

		phraseQuery.add(new Term(field, value));

		return phraseQuery;
	}

	protected org.apache.lucene.search.Query createPrefixQuery(
		String field, String value) {

		return new PrefixQuery(new Term(field, value));
	}

	protected org.apache.lucene.search.Query createQuery(
		MatchQuery.Type matchQueryType, String field, String value) {

		if (matchQueryType == MatchQuery.Type.BOOLEAN) {
			return parse(field, value);
		}
		else if (matchQueryType == MatchQuery.Type.PHRASE) {
			return createPhraseQuery(field, value);
		}
		else if (matchQueryType == MatchQuery.Type.PHRASE_PREFIX) {
			return createPrefixQuery(field, value);
		}

		throw new IllegalArgumentException(
			"Invalid match query type: " + matchQueryType);
	}

	protected org.apache.lucene.search.Query parse(String field, String value) {
		QueryParser queryParser = new QueryParser(field, new KeywordAnalyzer());

		try {
			return queryParser.parse(value);
		}
		catch (ParseException pe) {
			throw new IllegalArgumentException(pe);
		}
	}

}