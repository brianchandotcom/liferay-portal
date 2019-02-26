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

package com.liferay.portal.search.elasticsearch6.internal.suggest;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.suggest.AggregateSuggester;
import com.liferay.portal.kernel.search.suggest.CompletionSuggester;
import com.liferay.portal.kernel.search.suggest.PhraseSuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.SuggesterTranslator;
import com.liferay.portal.kernel.search.suggest.SuggesterVisitor;
import com.liferay.portal.kernel.search.suggest.TermSuggester;

import org.elasticsearch.search.suggest.SuggestionBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = "search.engine.impl=Elasticsearch",
	service = SuggesterTranslator.class
)
public class ElasticsearchSuggesterTranslator
	implements SuggesterTranslator<SuggestionBuilder>,
			   SuggesterVisitor<SuggestionBuilder> {

	@Override
	public SuggestionBuilder translate(
		Suggester suggester, SearchContext searchContext) {

		return suggester.accept(this);
	}

	@Override
	public SuggestionBuilder visit(AggregateSuggester aggregateSuggester) {
		return null;
	}

	@Override
	public SuggestionBuilder visit(CompletionSuggester completionSuggester) {
		return _completionSuggesterTranslator.translate(completionSuggester);
	}

	@Override
	public SuggestionBuilder visit(PhraseSuggester phraseSuggester) {
		return _phraseSuggesterTranslator.translate(phraseSuggester);
	}

	@Override
	public SuggestionBuilder visit(TermSuggester termSuggester) {
		return _termSuggesterTranslator.translate(termSuggester);
	}

	@Reference(unbind = "-")
	protected void setCompletionSuggesterTranslator(
		CompletionSuggesterTranslator completionSuggesterTranslator) {

		_completionSuggesterTranslator = completionSuggesterTranslator;
	}

	@Reference(unbind = "-")
	protected void setPhraseSuggesterTranslator(
		PhraseSuggesterTranslator phraseSuggesterTranslator) {

		_phraseSuggesterTranslator = phraseSuggesterTranslator;
	}

	@Reference(unbind = "-")
	protected void setTermSuggesterTranslator(
		TermSuggesterTranslator termSuggesterTranslator) {

		_termSuggesterTranslator = termSuggesterTranslator;
	}

	private CompletionSuggesterTranslator _completionSuggesterTranslator;
	private PhraseSuggesterTranslator _phraseSuggesterTranslator;
	private TermSuggesterTranslator _termSuggesterTranslator;

}