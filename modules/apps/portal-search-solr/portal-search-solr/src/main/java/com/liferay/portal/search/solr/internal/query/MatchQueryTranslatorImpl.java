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
import com.liferay.portal.kernel.search.generic.MatchQuery.Type;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.solr.query.MatchQueryTranslator;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
@Component(immediate = true, service = MatchQueryTranslator.class)
public class MatchQueryTranslatorImpl implements MatchQueryTranslator {

	@Override
	public org.apache.lucene.search.Query translate(MatchQuery matchQuery) {
		org.apache.lucene.search.Query query = translateMatchQuery(matchQuery);

		if (!matchQuery.isDefaultBoost()) {
			query.setBoost(matchQuery.getBoost());
		}

		return query;
	}

	protected Type getType(MatchQuery matchQuery) {
		Type type = matchQuery.getType();

		if (type != null) {
			return type;
		}

		return MatchQuery.Type.BOOLEAN;
	}

	protected String getValue(MatchQuery matchQuery) {
		String value = matchQuery.getValue();

		if (value.charAt(0) == CharPool.STAR) {
			value = value.substring(1);
		}

		return value;
	}

	protected org.apache.lucene.search.Query translateMatchQuery(
		MatchQuery matchQuery) {

		String field = matchQuery.getField();
		MatchQuery.Type matchQueryType = getType(matchQuery);
		String value = getValue(matchQuery);

		if ((value.charAt(0) == CharPool.QUOTE) &&
			(value.charAt(value.length() - 1) == CharPool.QUOTE)) {

			matchQueryType = MatchQuery.Type.PHRASE;
			value = StringUtil.unquote(value);

			if (value.charAt(value.length() - 1) == CharPool.STAR) {
				matchQueryType = MatchQuery.Type.PHRASE_PREFIX;
				value = value.substring(0, value.length() - 1);
			}
		}

		if (matchQueryType == MatchQuery.Type.BOOLEAN) {
			return translateQueryTypeBoolean(field, value);
		}

		if (matchQueryType == MatchQuery.Type.PHRASE) {
			return translateQueryTypePhrase(field, value, matchQuery.getSlop());
		}

		if (matchQueryType == MatchQuery.Type.PHRASE_PREFIX) {
			return translateQueryTypePhrasePrefix(field, value);
		}

		throw new IllegalArgumentException(
			"Unknown match query type " + matchQueryType);
	}

	protected org.apache.lucene.search.Query translateQueryTypeBoolean(
		String field, String value) {

		if (value.charAt(value.length() - 1) == CharPool.STAR) {
			value = value.substring(0, value.length() - 1);
		}

		QueryParser queryParser = new QueryParser(field, new KeywordAnalyzer());

		try {
			return queryParser.parse(value);
		}
		catch (ParseException pe) {
			throw new IllegalArgumentException(pe);
		}
	}

	protected org.apache.lucene.search.Query translateQueryTypePhrase(
		String field, String value, Integer slop) {

		PhraseQuery phraseQuery = new PhraseQuery();

		phraseQuery.add(_createUnquotedTerm(field, value));

		if (slop != null) {
			phraseQuery.setSlop(slop);
		}

		return phraseQuery;
	}

	protected org.apache.lucene.search.Query translateQueryTypePhrasePrefix(
		String field, String value) {

		char[] chars = new char[] {
			CharPool.CLOSE_PARENTHESIS, CharPool.OPEN_PARENTHESIS,
			CharPool.SPACE
		};

		if (StringUtils.containsAny(value, chars)) {
			return translateQueryTypePhrase(field, value + CharPool.STAR, null);
		}

		return new PrefixQuery(_createUnquotedTerm(field, value));
	}

	private static Term _createUnquotedTerm(String field, String value) {
		return new Term(field, StringUtil.unquote(value));
	}

}