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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.suggest.AggregateSuggester;
import com.liferay.portal.kernel.search.suggest.PhraseSuggester;
import com.liferay.portal.kernel.search.suggest.QuerySuggester;
import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.SuggesterResult;
import com.liferay.portal.kernel.search.suggest.SuggesterResults;
import com.liferay.portal.kernel.search.suggest.SuggesterTranslator;
import com.liferay.portal.kernel.search.suggest.TermSuggester;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch.internal.index.IndexNameBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

import org.elasticsearch.action.suggest.SuggestRequestBuilder;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.Suggest.Suggestion;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry.Option;
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
public class ElasticsearchQuerySuggester implements QuerySuggester {

	@Override
	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		Suggester suggester = createSpellCheckSuggester(searchContext, 1);

		Suggest suggest = doSuggest(suggester, searchContext);

		List<String> words = new ArrayList<>();

		for (Suggest.Suggestion
				<? extends Suggest.Suggestion.Entry
					<? extends Suggest.Suggestion.Entry.Option>> suggestion :
						suggest) {

			for (Suggest.Suggestion.Entry
					<? extends Suggest.Suggestion.Entry.Option>
						suggestionEntry : suggestion) {

				Text text = getWord(suggestionEntry);

				words.add(text.string());
			}
		}

		return StringUtil.merge(words, StringPool.SPACE);
	}

	@Override
	public Map<String, List<String>> spellCheckKeywords(
		SearchContext searchContext, int max) {

		Suggester suggester = createSpellCheckSuggester(searchContext, max);

		Suggest suggest = doSuggest(suggester, searchContext);

		Map<String, List<String>> suggestionsMap = new LinkedHashMap<>();

		for (Suggest.Suggestion
				<? extends Suggest.Suggestion.Entry
					<? extends Suggest.Suggestion.Entry.Option>> suggestion :
						suggest) {

			for (Suggest.Suggestion.Entry
					<? extends Suggest.Suggestion.Entry.Option>
						suggestionEntry : suggestion) {

				List<String> suggestionsList = new ArrayList<>();

				for (Suggest.Suggestion.Entry.Option suggestionEntryOption :
						suggestionEntry.getOptions()) {

					Text optionText = suggestionEntryOption.getText();

					suggestionsList.add(optionText.string());
				}

				Text text = suggestionEntry.getText();

				suggestionsMap.put(text.string(), suggestionsList);
			}
		}

		return suggestionsMap;
	}

	@Override
	public SuggesterResults suggest(
		SearchContext searchContext, Suggester suggester) {

		Suggest suggest = doSuggest(suggester, searchContext);

		SuggesterResults suggesterResults = new SuggesterResults();

		for (Suggest.Suggestion
				<? extends Suggest.Suggestion.Entry
					<? extends Suggest.Suggestion.Entry.Option>> suggestion :
						suggest) {

			suggesterResults.addSuggesterResult(translate(suggestion));
		}

		return suggesterResults;
	}

	@Override
	public String[] suggestKeywordQueries(
		SearchContext searchContext, int max) {

		Suggester suggester = createQuerySuggester(searchContext, max);

		Suggest suggest = doSuggest(suggester, searchContext);

		Suggestion<? extends Entry<? extends Option>> suggestion =
			suggest.getSuggestion(suggester.getName());

		if (suggestion == null) {
			return StringPool.EMPTY_ARRAY;
		}

		List<String> keywordQueries = new ArrayList<>();

		for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>
				suggestionEntry : suggestion) {

			for (Suggest.Suggestion.Entry.Option suggestionEntryOption :
					suggestionEntry.getOptions()) {

				Text optionText = suggestionEntryOption.getText();

				keywordQueries.add(optionText.string());
			}
		}

		return keywordQueries.toArray(new String[keywordQueries.size()]);
	}

	protected PhraseSuggester createQuerySuggester(
		SearchContext searchContext, int max) {

		Localization localization = getLocalization();

		String field = localization.getLocalizedName(
			Field.KEYWORD_SEARCH, searchContext.getLanguageId());

		PhraseSuggester phraseSuggester = new PhraseSuggester(
			"keywordQueryRequest", field, searchContext.getKeywords());

		phraseSuggester.setSize(max);

		return phraseSuggester;
	}

	protected Suggester createSpellCheckSuggester(
		SearchContext searchContext, int max) {

		Localization localization = getLocalization();

		String field = localization.getLocalizedName(
			Field.SPELL_CHECK_WORD, searchContext.getLanguageId());

		TermSuggester termSuggester = new TermSuggester(
			"spellCheckRequest", field, searchContext.getKeywords());

		termSuggester.setSize(max);

		return termSuggester;
	}

	protected Suggest doSuggest(
		Suggester suggester, SearchContext searchContext) {

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		Client client = elasticsearchConnectionManager.getClient();

		SuggestRequestBuilder suggestRequestBuilder = client.prepareSuggest(
			indexNameBuilder.getIndexName(searchContext.getCompanyId()));

		SuggestBuilder suggestBuilder = suggesterTranslator.translate(
			suggester, searchContext);

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

		if (_log.isInfoEnabled()) {
			stopWatch.stop();

			_log.info(
				"Spell checked keywords in " + stopWatch.getTime() + "ms");
		}

		return suggest;
	}

	protected Localization getLocalization() {

		// See LPS-72507 and LPS-76500

		if (localization != null) {
			return localization;
		}

		return LocalizationUtil.getLocalization();
	}

	protected Text getWord(Entry<? extends Option> suggestionEntry) {
		List<? extends Suggest.Suggestion.Entry.Option> suggestionEntryOptions =
			suggestionEntry.getOptions();

		if (suggestionEntryOptions.isEmpty()) {
			return suggestionEntry.getText();
		}

		Suggest.Suggestion.Entry.Option suggestionEntryOption =
			suggestionEntryOptions.get(0);

		return suggestionEntryOption.getText();
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

		Text text = suggestionEntryOption.getText();

		SuggesterResult.Entry.Option suggesterResultEntryOption =
			new SuggesterResult.Entry.Option(
				text.string(), suggestionEntryOption.getScore());

		Text highlighted = suggestionEntryOption.getHighlighted();

		if (suggestionEntryOption.getHighlighted() != null) {
			suggesterResultEntryOption.setHighlightedText(highlighted.string());
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

		List<? extends Suggest.Suggestion.Entry.Option> suggestionEntryOptions =
			suggestionEntry.getOptions();

		for (Suggest.Suggestion.Entry.Option suggestionEntryOption :
				suggestionEntryOptions) {

			SuggesterResult.Entry.Option suggesterResultEntryOption = translate(
				suggestionEntryOption);

			suggesterResultEntry.addOption(suggesterResultEntryOption);
		}

		return suggesterResultEntry;
	}

	@Reference(unbind = "-")
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	@Reference(unbind = "-")
	protected IndexNameBuilder indexNameBuilder;

	protected Localization localization;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	protected SuggesterTranslator<SuggestBuilder> suggesterTranslator;

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchQuerySuggester.class);

}