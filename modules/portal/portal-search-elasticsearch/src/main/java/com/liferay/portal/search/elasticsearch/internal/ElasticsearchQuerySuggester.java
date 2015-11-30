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

package com.liferay.portal.search.elasticsearch.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.suggest.AggregateSuggester;
import com.liferay.portal.kernel.search.suggest.BaseQuerySuggester;
import com.liferay.portal.kernel.search.suggest.PhraseSuggester;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.SuggesterResult;
import com.liferay.portal.kernel.search.suggest.SuggesterResults;
import com.liferay.portal.kernel.search.suggest.SuggesterTranslator;
import com.liferay.portal.kernel.search.suggest.TermSuggester;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnectionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.elasticsearch.action.suggest.SuggestRequestBuilder;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, property = {"search.engine.impl=Elasticsearch"},
	service = QuerySuggester.class
)
public class ElasticsearchQuerySuggester extends BaseQuerySuggester {

	@Override
	public Map<String, List<String>> spellCheckKeywords(
		SearchContext searchContext, int max) {

		String field = DocumentImpl.getLocalizedName(
			searchContext.getLocale(), Field.SPELL_CHECK_WORD);

		TermSuggester termSuggester = new TermSuggester(
			"spellCheckRequest", field, searchContext.getKeywords());

		termSuggester.setSize(max);

		SuggesterResults suggesterResults = suggest(
			searchContext, termSuggester);

		Map<String, List<String>> suggestionsMap = new HashMap<>();

		for (SuggesterResult suggesterResult :
				suggesterResults.getSuggesterResults()) {

			for (SuggesterResult.Entry suggesterResultEntry :
					suggesterResult.getEntries()) {

				String text = suggesterResultEntry.getText();

				List<String> suggestionsList = suggestionsMap.get(text);

				if (suggestionsList == null) {
					suggestionsList = new ArrayList<>();

					suggestionsMap.put(text, suggestionsList);
				}

				for (SuggesterResult.Entry.Option suggesterResultEntryOption :
						suggesterResultEntry.getOptions()) {

					suggestionsList.add(suggesterResultEntryOption.getText());
				}
			}
		}

		return suggestionsMap;
	}

	@Override
	public SuggesterResults suggest(
		SearchContext searchContext, Suggester suggester) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		Client client = _elasticsearchConnectionManager.getClient();

		SuggestBuilder suggestBuilder = _suggesterTranslator.translate(
			suggester, searchContext);

		SuggestRequestBuilder suggestRequestBuilder = client.prepareSuggest(
			String.valueOf(searchContext.getCompanyId()));

		for (SuggestBuilder.SuggestionBuilder<?> suggestionBuilder :
				suggestBuilder.getSuggestion()) {

			suggestRequestBuilder.addSuggestion(suggestionBuilder);
		}

		if (suggester instanceof AggregateSuggester) {
			AggregateSuggester aggregateSuggester =
				(AggregateSuggester)suggester;

			suggestRequestBuilder.setSuggestText(aggregateSuggester.getValue());
		}

		SuggestResponse suggestResponse = suggestRequestBuilder.get();

		Suggest suggest = suggestResponse.getSuggest();

		SuggesterResults suggesterResults = new SuggesterResults();

		for (Suggest.Suggestion
				<? extends Suggest.Suggestion.Entry
					<? extends Suggest.Suggestion.Entry.Option>> suggestion :
						suggest) {

			SuggesterResult suggesterResult = translate(suggestion);

			suggesterResults.addSuggesterResult(suggesterResult);
		}

		if (_log.isInfoEnabled()) {
			stopWatch.stop();

			_log.info(
				"Spell checked keywords in " + stopWatch.getTime() + "ms");
		}

		return suggesterResults;
	}

	@Override
	public String[] suggestKeywordQueries(
		SearchContext searchContext, int max) {

		List<String> keywordQueries = new ArrayList<>();

		String field = DocumentImpl.getLocalizedName(
			searchContext.getLocale(), Field.KEYWORD_SEARCH);

		PhraseSuggester phraseSuggester = new PhraseSuggester(
			"keywordQueryRequest", field, searchContext.getKeywords());

		phraseSuggester.setSize(max);

		SuggesterResults suggesterResults = suggest(
			searchContext, phraseSuggester);

		SuggesterResult suggesterResult = suggesterResults.getSuggesterResult(
			phraseSuggester.getName());

		List<SuggesterResult.Entry> suggesterResultEntries =
			suggesterResult.getEntries();

		for (SuggesterResult.Entry suggesterResultEntry :
				suggesterResultEntries) {

			for (SuggesterResult.Entry.Option suggesterResultEntryOption :
					suggesterResultEntry.getOptions()) {

				String optionText = String.valueOf(
					suggesterResultEntryOption.getText());

				keywordQueries.add(optionText);
			}
		}

		return keywordQueries.toArray(new String[keywordQueries.size()]);
	}

	@Reference(unbind = "-")
	protected void setElasticsearchConnectionManager(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setQueryTranslator(
		SuggesterTranslator<SuggestBuilder> suggesterTranslator) {

		_suggesterTranslator = suggesterTranslator;
	}

	protected SuggesterResult translate(
		Suggest.Suggestion
			<? extends Suggest.Suggestion.Entry
				<? extends Suggest.Suggestion.Entry.Option>> suggestion) {

		SuggesterResult suggesterResult = new SuggesterResult(
			suggestion.getName());

		for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>
				suggestionEntry : suggestion) {

			SuggesterResult.Entry suggesterResultEntry = translate(
				suggestionEntry);

			suggesterResult.addEntry(suggesterResultEntry);
		}

		return suggesterResult;
	}

	protected SuggesterResult.Entry.Option translate(
		Suggest.Suggestion.Entry.Option suggestionEntryOption) {

		SuggesterResult.Entry.Option suggesterResultEntryOption =
			new SuggesterResult.Entry.Option(
				suggestionEntryOption.getText().string(),
				suggestionEntryOption.getScore());

		if (suggestionEntryOption.getHighlighted() != null) {
			suggesterResultEntryOption.setHighlightedText(
				suggestionEntryOption.getHighlighted().string());
		}

		if (suggestionEntryOption instanceof TermSuggestion.Entry.Option) {
			TermSuggestion.Entry.Option termSuggestionEntryOption =
				(TermSuggestion.Entry.Option)suggestionEntryOption;

			suggesterResultEntryOption.setFrequency(
				termSuggestionEntryOption.getFreq());
		}

		return suggesterResultEntryOption;
	}

	protected SuggesterResult.Entry translate(
		Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>
			suggestionEntry) {

		Text text = suggestionEntry.getText();

		SuggesterResult.Entry suggesterResultEntry = new SuggesterResult.Entry(
			text.string());

		List<? extends Suggest.Suggestion.Entry.Option>
			suggestionEntryOptions = suggestionEntry.getOptions();

		for (Suggest.Suggestion.Entry.Option suggestionEntryOption :
				suggestionEntryOptions) {

			SuggesterResult.Entry.Option suggesterResultEntryOption = translate(
				suggestionEntryOption);

			suggesterResultEntry.addOption(suggesterResultEntryOption);
		}

		return suggesterResultEntry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchQuerySuggester.class);

	private volatile ElasticsearchConnectionManager
		_elasticsearchConnectionManager;
	private volatile SuggesterTranslator<SuggestBuilder> _suggesterTranslator;

}