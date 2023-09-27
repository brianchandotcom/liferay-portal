/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.suggest;

import com.liferay.portal.kernel.search.SearchContext;
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
	property = "search.engine.impl=Elasticsearch",
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

	@Reference
	private CompletionSuggesterTranslator _completionSuggesterTranslator;

	@Reference
	private PhraseSuggesterTranslator _phraseSuggesterTranslator;

	@Reference
	private TermSuggesterTranslator _termSuggesterTranslator;

}