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

package com.liferay.portal.search.elasticsearch.internal.suggest;

import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.kernel.search.suggest.PhraseSuggester;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.suggest.PhraseSuggesterTranslator;

import java.util.Set;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.phrase.PhraseSuggestionBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = PhraseSuggesterTranslator.class)
public class PhraseSuggesterTranslatorImpl
	extends BaseSuggesterTranslatorImpl implements PhraseSuggesterTranslator {

	@Override
	public SuggestBuilder translate(PhraseSuggester phraseSuggester) {
		SuggestBuilder suggestBuilder = new SuggestBuilder(
			phraseSuggester.getName());

		PhraseSuggestionBuilder phraseSuggestionBuilder =
			SuggestBuilders.phraseSuggestion(phraseSuggester.getName());

		phraseSuggestionBuilder.field(phraseSuggester.getField());

		if (Validator.isNotNull(phraseSuggester.getAnalyzer())) {
			phraseSuggestionBuilder.analyzer(phraseSuggester.getAnalyzer());
		}

		translate(
			phraseSuggester.getCandidateGenerators(), phraseSuggestionBuilder);

		translate(phraseSuggester.getCollate(), phraseSuggestionBuilder);

		if (phraseSuggester.getConfidence() != null) {
			phraseSuggestionBuilder.confidence(phraseSuggester.getConfidence());
		}

		if (phraseSuggester.isForceUnigrams() != null) {
			phraseSuggestionBuilder.forceUnigrams(
				phraseSuggester.isForceUnigrams());
		}

		if (phraseSuggester.getGramSize() != null) {
			phraseSuggestionBuilder.gramSize(phraseSuggester.getGramSize());
		}

		if (phraseSuggester.getMaxErrors() != null) {
			phraseSuggestionBuilder.maxErrors(phraseSuggester.getMaxErrors());
		}

		if (Validator.isNotNull(phraseSuggester.getPostHighlightFilter()) &&
			Validator.isNotNull(phraseSuggester.getPreHighlightFilter())) {

			phraseSuggestionBuilder.highlight(
				phraseSuggester.getPreHighlightFilter(),
				phraseSuggester.getPostHighlightFilter());
		}

		if (phraseSuggester.getRealWordErrorLikelihood() != null) {
			phraseSuggestionBuilder.realWordErrorLikelihood(
				phraseSuggester.getRealWordErrorLikelihood());
		}

		if (phraseSuggester.getSeparator() != null) {
			phraseSuggestionBuilder.separator(phraseSuggester.getSeparator());
		}

		if (phraseSuggester.getShardSize() != null) {
			phraseSuggestionBuilder.shardSize(phraseSuggester.getShardSize());
		}

		if (phraseSuggester.getSize() != null) {
			phraseSuggestionBuilder.size(phraseSuggester.getSize());
		}

		if (phraseSuggester.getTokenLimit() != null) {
			phraseSuggestionBuilder.tokenLimit(phraseSuggester.getTokenLimit());
		}

		phraseSuggestionBuilder.text(phraseSuggester.getValue());

		suggestBuilder.addSuggestion(phraseSuggestionBuilder);

		return suggestBuilder;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(search.engine.impl=Elasticsearch)"
	)
	protected void setQueryTranslator(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_queryTranslator = queryTranslator;
	}

	protected void translate(
		PhraseSuggester.Collate collate,
		PhraseSuggestionBuilder phraseSuggestionBuilder) {

		if ((collate != null) && (_queryTranslator != null)) {
			QueryBuilder queryBuilder = _queryTranslator.translate(
				collate.getQuery(), null);

			phraseSuggestionBuilder.collateParams(collate.getParams());

			if (collate.isPrune() != null) {
				phraseSuggestionBuilder.collatePrune(collate.isPrune());
			}

			phraseSuggestionBuilder.collateQuery(queryBuilder.toString());
		}
	}

	protected void translate(
		Set<PhraseSuggester.CandidateGenerator> candidateGenerators,
		PhraseSuggestionBuilder phraseSuggestionBuilder) {

		for (PhraseSuggester.CandidateGenerator candidateGenerator :
				candidateGenerators) {

			PhraseSuggestionBuilder.DirectCandidateGenerator
				directCandidateGenerator =
					new PhraseSuggestionBuilder.DirectCandidateGenerator(
						candidateGenerator.getField());

			if (candidateGenerator.getAccuracy() != null) {
				directCandidateGenerator.accuracy(
					candidateGenerator.getAccuracy());
			}

			if (candidateGenerator.getMaxEdits() != null) {
				directCandidateGenerator.maxEdits(
					candidateGenerator.getMaxEdits());
			}

			if (candidateGenerator.getMaxInspections() != null) {
				directCandidateGenerator.maxInspections(
					candidateGenerator.getMaxInspections());
			}

			if (candidateGenerator.getMaxTermFreq() != null) {
				directCandidateGenerator.maxTermFreq(
					candidateGenerator.getMaxTermFreq());
			}

			if (candidateGenerator.getMinWordLength() != null) {
				directCandidateGenerator.minWordLength(
					candidateGenerator.getMinWordLength());
			}

			if (candidateGenerator.getMinDocFreq() != null) {
				directCandidateGenerator.minDocFreq(
					candidateGenerator.getMinDocFreq());
			}

			if (candidateGenerator.getPrefixLength() != null) {
				directCandidateGenerator.prefixLength(
					candidateGenerator.getPrefixLength());
			}

			if (Validator.isNotNull(
					candidateGenerator.getPostFilterAnalyzer())) {

				directCandidateGenerator.postFilter(
					candidateGenerator.getPostFilterAnalyzer());
			}

			if (Validator.isNotNull(
					candidateGenerator.getPreFilterAnalyzer())) {

				directCandidateGenerator.preFilter(
					candidateGenerator.getPreFilterAnalyzer());
			}

			if (candidateGenerator.getSize() != null) {
				directCandidateGenerator.size(candidateGenerator.getSize());
			}

			if (candidateGenerator.getSort() != null) {
				directCandidateGenerator.sort(
					translate(candidateGenerator.getSort()));
			}

			if (candidateGenerator.getStringDistance() != null) {
				directCandidateGenerator.stringDistance(
					translate(candidateGenerator.getStringDistance()));
			}

			if (candidateGenerator.getSuggestMode() != null) {
				directCandidateGenerator.suggestMode(
					translate(candidateGenerator.getSuggestMode()));
			}

			phraseSuggestionBuilder.addCandidateGenerator(
				directCandidateGenerator);
		}
	}

	protected void unsetQueryTranslator(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_queryTranslator = null;
	}

	private QueryTranslator<QueryBuilder> _queryTranslator;

}