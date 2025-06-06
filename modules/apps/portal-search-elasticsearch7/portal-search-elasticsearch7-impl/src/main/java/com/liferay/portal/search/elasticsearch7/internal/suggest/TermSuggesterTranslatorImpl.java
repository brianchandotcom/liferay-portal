/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.suggest;

import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.TermSuggester;
import com.liferay.portal.kernel.util.Validator;

import org.elasticsearch.search.suggest.SortBy;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = TermSuggesterTranslator.class)
public class TermSuggesterTranslatorImpl implements TermSuggesterTranslator {

	@Override
	public SuggestionBuilder translate(TermSuggester termSuggester) {
		TermSuggestionBuilder termSuggesterBuilder =
			SuggestBuilders.termSuggestion(termSuggester.getField());

		if (Validator.isNotNull(termSuggester.getAnalyzer())) {
			termSuggesterBuilder.analyzer(termSuggester.getAnalyzer());
		}

		if (termSuggester.getAccuracy() != null) {
			termSuggesterBuilder.accuracy(termSuggester.getAccuracy());
		}

		if (termSuggester.getMaxEdits() != null) {
			termSuggesterBuilder.maxEdits(termSuggester.getMaxEdits());
		}

		if (termSuggester.getMaxInspections() != null) {
			termSuggesterBuilder.maxInspections(
				termSuggester.getMaxInspections());
		}

		if (termSuggester.getMaxTermFreq() != null) {
			termSuggesterBuilder.maxTermFreq(termSuggester.getMaxTermFreq());
		}

		if (termSuggester.getMinWordLength() != null) {
			termSuggesterBuilder.minWordLength(
				termSuggester.getMinWordLength());
		}

		if (termSuggester.getMinDocFreq() != null) {
			termSuggesterBuilder.minDocFreq(termSuggester.getMinDocFreq());
		}

		if (termSuggester.getPrefixLength() != null) {
			termSuggesterBuilder.prefixLength(termSuggester.getPrefixLength());
		}

		if (termSuggester.getShardSize() != null) {
			termSuggesterBuilder.shardSize(termSuggester.getShardSize());
		}

		if (termSuggester.getSize() != null) {
			termSuggesterBuilder.size(termSuggester.getSize());
		}

		if (termSuggester.getSort() != null) {
			termSuggesterBuilder.sort(_translateSort(termSuggester.getSort()));
		}

		if (termSuggester.getStringDistance() != null) {
			termSuggesterBuilder.stringDistance(
				_translateDistance(termSuggester.getStringDistance()));
		}

		if (termSuggester.getSuggestMode() != null) {
			termSuggesterBuilder.suggestMode(
				_translateMode(termSuggester.getSuggestMode()));
		}

		termSuggesterBuilder.text(termSuggester.getValue());

		return termSuggesterBuilder;
	}

	private TermSuggestionBuilder.StringDistanceImpl _translateDistance(
		Suggester.StringDistance stringDistance) {

		if (stringDistance == Suggester.StringDistance.DAMERAU_LEVENSHTEIN) {
			return TermSuggestionBuilder.StringDistanceImpl.DAMERAU_LEVENSHTEIN;
		}
		else if (stringDistance == Suggester.StringDistance.JAROWINKLER) {
			return TermSuggestionBuilder.StringDistanceImpl.JARO_WINKLER;
		}
		else if (stringDistance == Suggester.StringDistance.LEVENSTEIN) {
			return TermSuggestionBuilder.StringDistanceImpl.LEVENSHTEIN;
		}
		else if (stringDistance == Suggester.StringDistance.NGRAM) {
			return TermSuggestionBuilder.StringDistanceImpl.NGRAM;
		}

		return TermSuggestionBuilder.StringDistanceImpl.INTERNAL;
	}

	private TermSuggestionBuilder.SuggestMode _translateMode(
		Suggester.SuggestMode suggestMode) {

		if (suggestMode == Suggester.SuggestMode.ALWAYS) {
			return TermSuggestionBuilder.SuggestMode.ALWAYS;
		}
		else if (suggestMode == Suggester.SuggestMode.POPULAR) {
			return TermSuggestionBuilder.SuggestMode.POPULAR;
		}

		return TermSuggestionBuilder.SuggestMode.MISSING;
	}

	private SortBy _translateSort(Suggester.Sort sort) {
		if (sort == Suggester.Sort.FREQUENCY) {
			return SortBy.FREQUENCY;
		}

		return SortBy.SCORE;
	}

}