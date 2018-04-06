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

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.suggest.AggregateSuggester;
import com.liferay.portal.kernel.search.suggest.CompletionSuggester;
import com.liferay.portal.kernel.search.suggest.PhraseSuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.SuggesterTranslator;
import com.liferay.portal.kernel.search.suggest.SuggesterVisitor;
import com.liferay.portal.kernel.search.suggest.TermSuggester;

import org.elasticsearch.search.suggest.SuggestBuilder;

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
	implements SuggesterTranslator<SuggestBuilder>,
			   SuggesterVisitor<SuggestBuilder> {

	@Override
	public SuggestBuilder translate(
		Suggester suggester, SearchContext searchContext) {

		return suggester.accept(this);
	}

	@Override
	public SuggestBuilder visit(AggregateSuggester aggregateSuggester) {
		return aggregateSuggesteTranslator.translate(aggregateSuggester, this);
	}

	@Override
	public SuggestBuilder visit(CompletionSuggester completionSuggester) {
		return completionSuggesterTranslator.translate(completionSuggester);
	}

	@Override
	public SuggestBuilder visit(PhraseSuggester phraseSuggester) {
		return phraseSuggesterTranslator.translate(phraseSuggester);
	}

	@Override
	public SuggestBuilder visit(TermSuggester termSuggester) {
		return termSuggesterTranslator.translate(termSuggester);
	}

	@Reference
	protected AggregateSuggesterTranslator aggregateSuggesteTranslator;

	@Reference
	protected CompletionSuggesterTranslator completionSuggesterTranslator;

	@Reference
	protected PhraseSuggesterTranslator phraseSuggesterTranslator;

	@Reference
	protected TermSuggesterTranslator termSuggesterTranslator;

}