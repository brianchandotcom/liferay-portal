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

package com.liferay.portal.search.internal.analysis;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.analysis.KeywordTokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class FullTextQueryBuilder {

	public FullTextQueryBuilder(KeywordTokenizer keywordTokenizer) {
		_keywordTokenizer = keywordTokenizer;
	}

	public Query build(String field, String keywords) {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

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
			booleanQueryImpl.add(
				_createPhraseQuery(field, phrase), BooleanClauseOccur.MUST);
		}

		if (!words.isEmpty()) {
			_addSentenceQueries(
				field, StringUtil.merge(words, StringPool.SPACE),
				booleanQueryImpl);
		}

		booleanQueryImpl.add(
			_createExactMatchQuery(field, keywords), BooleanClauseOccur.SHOULD);

		return booleanQueryImpl;
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

	private void _addSentenceQueries(
		String field, String sentence, BooleanQueryImpl booleanQueryImpl) {

		booleanQueryImpl.add(
			_createMandatoryQuery(field, sentence), BooleanClauseOccur.MUST);

		if (_proximitySlop != null) {
			booleanQueryImpl.add(
				_createProximityQuery(field, sentence),
				BooleanClauseOccur.SHOULD);
		}
	}

	private Query _createAutocompleteQuery(String field, String value) {
		PhraseQueryBuilder builder = new PhraseQueryBuilder();

		builder.setMaxExpansions(_maxExpansions);

		builder.setPrefix(true);

		return builder.build(field, value);
	}

	private Query _createExactMatchQuery(String field, String keywords) {
		PhraseQueryBuilder builder = new PhraseQueryBuilder();

		builder.setBoost(_exactMatchBoost);

		return builder.build(field, keywords);
	}

	private Query _createMandatoryQuery(String field, String sentence) {
		Query matchQuery = _createMatchQuery(field, sentence);

		if (!_autocomplete) {
			return matchQuery;
		}

		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		booleanQueryImpl.add(matchQuery, BooleanClauseOccur.SHOULD);

		booleanQueryImpl.add(
			_createAutocompleteQuery(field, sentence),
			BooleanClauseOccur.SHOULD);

		return booleanQueryImpl;
	}

	private Query _createMatchQuery(String field, String value) {
		return new MatchQuery(field, value);
	}

	private Query _createPhraseQuery(String field, String phrase) {
		PhraseQueryBuilder builder = new PhraseQueryBuilder();

		builder.setTrailingStarAware(true);

		return builder.build(field, phrase);
	}

	private Query _createProximityQuery(String field, String value) {
		PhraseQueryBuilder builder = new PhraseQueryBuilder();

		builder.setSlop(_proximitySlop);

		return builder.build(field, value);
	}

	private boolean _autocomplete;
	private float _exactMatchBoost;
	private final KeywordTokenizer _keywordTokenizer;
	private Integer _maxExpansions;
	private Integer _proximitySlop;

}