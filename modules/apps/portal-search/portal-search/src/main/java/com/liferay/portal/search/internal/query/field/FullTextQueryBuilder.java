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

package com.liferay.portal.search.internal.query.field;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.analysis.KeywordTokenizer;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class FullTextQueryBuilder {

	public FullTextQueryBuilder(
		KeywordTokenizer keywordTokenizer, Queries queries) {

		_keywordTokenizer = keywordTokenizer;
		_queries = queries;
	}

	public Query build(String field, String keywords) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		List<String> tokens = _keywordTokenizer.tokenize(keywords);

		List<String> phrases = new ArrayList<>(tokens.size());
		List<String> words = new ArrayList<>(tokens.size());

		for (String token : tokens) {
			if (StringUtil.startsWith(token, CharPool.QUOTE)) {
				phrases.add(StringUtil.unquote(token));
			}
			else {
				words.add(token);
			}
		}

		for (String phrase : phrases) {
			booleanQuery.addMustQueryClauses(createPhraseQuery(field, phrase));
		}

		if (!words.isEmpty()) {
			addSentenceQueries(
				field, StringUtil.merge(words, StringPool.SPACE), booleanQuery);
		}

		booleanQuery.addShouldQueryClauses(
			createExactMatchQuery(field, keywords));

		return booleanQuery;
	}

	public void setAutocomplete(boolean autocomplete) {
		_autocomplete = autocomplete;
	}

	public void setExactMatchBoost(float exactMatchBoost) {
		_exactMatchBoost = exactMatchBoost;
	}

	public void setMaxExpansions(int maxExpansions) {
		_maxExpansions = maxExpansions;
	}

	public void setProximitySlop(int proximitySlop) {
		_proximitySlop = proximitySlop;
	}

	protected void addSentenceQueries(
		String field, String sentence, BooleanQuery booleanQuery) {

		booleanQuery.addMustQueryClauses(createMandatoryQuery(field, sentence));

		if (_proximitySlop != null) {
			booleanQuery.addShouldQueryClauses(
				createProximityQuery(field, sentence));
		}
	}

	protected Query createAutocompleteQuery(String field, String value) {
		PhraseQueryBuilder builder = new PhraseQueryBuilder(_queries);

		builder.setMaxExpansions(_maxExpansions);

		builder.setPrefix(true);

		return builder.build(field, value);
	}

	protected Query createExactMatchQuery(String field, String keywords) {
		PhraseQueryBuilder builder = new PhraseQueryBuilder(_queries);

		builder.setBoost(_exactMatchBoost);

		return builder.build(field, keywords);
	}

	protected Query createMandatoryQuery(String field, String sentence) {
		Query matchQuery = createMatchQuery(field, sentence);

		if (!_autocomplete) {
			return matchQuery;
		}

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addShouldQueryClauses(matchQuery);

		booleanQuery.addShouldQueryClauses(
			createAutocompleteQuery(field, sentence));

		return booleanQuery;
	}

	protected Query createMatchQuery(String field, String value) {
		return _queries.match(field, value);
	}

	protected Query createPhraseQuery(String field, String phrase) {
		PhraseQueryBuilder builder = new PhraseQueryBuilder(_queries);

		builder.setTrailingStarAware(true);

		return builder.build(field, phrase);
	}

	protected Query createProximityQuery(String field, String value) {
		PhraseQueryBuilder builder = new PhraseQueryBuilder(_queries);

		builder.setSlop(_proximitySlop);

		return builder.build(field, value);
	}

	private boolean _autocomplete;
	private float _exactMatchBoost;
	private final KeywordTokenizer _keywordTokenizer;
	private Integer _maxExpansions;
	private Integer _proximitySlop;
	private final Queries _queries;

}