/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.CompletionSuggest;
import co.elastic.clients.elasticsearch.core.search.CompletionSuggestOption;
import co.elastic.clients.elasticsearch.core.search.FieldSuggester;
import co.elastic.clients.elasticsearch.core.search.PhraseSuggest;
import co.elastic.clients.elasticsearch.core.search.PhraseSuggestOption;
import co.elastic.clients.elasticsearch.core.search.Suggestion;
import co.elastic.clients.elasticsearch.core.search.TermSuggest;
import co.elastic.clients.elasticsearch.core.search.TermSuggestOption;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.kernel.search.suggest.Suggester;
import com.liferay.portal.kernel.search.suggest.SuggesterTranslator;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.suggest.ElasticsearchSuggesterTranslator;
import com.liferay.portal.search.elasticsearch8.internal.util.ConversionUtil;
import com.liferay.portal.search.elasticsearch8.internal.util.SetterUtil;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SuggestSearchResult;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class SuggestSearchRequestExecutor {

	public SuggestSearchRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public SuggestSearchResponse execute(
		SuggestSearchRequest suggestSearchRequest) {

		SearchResponse<JsonData> searchResponse = getSearchResponse(
			_createSearchRequest(suggestSearchRequest), suggestSearchRequest);

		Map<String, List<Suggestion<JsonData>>> suggests =
			searchResponse.suggest();

		SuggestSearchResponse suggestSearchResponse =
			new SuggestSearchResponse();

		if (MapUtil.isEmpty(suggests)) {
			return suggestSearchResponse;
		}

		for (Map.Entry<String, List<Suggestion<JsonData>>> entry :
				suggests.entrySet()) {

			suggestSearchResponse.addSuggestSearchResult(
				_translateSuggests(entry.getKey(), entry.getValue()));
		}

		return suggestSearchResponse;
	}

	protected SearchResponse getSearchResponse(
		SearchRequest searchRequest,
		SuggestSearchRequest suggestSearchRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				suggestSearchRequest.getConnectionId(),
				suggestSearchRequest.isPreferLocalCluster());

		try {
			return elasticsearchClient.search(searchRequest, JsonData.class);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SearchRequest _createSearchRequest(
		SuggestSearchRequest suggestSearchRequest) {

		SearchRequest.Builder searchRequestBuilder =
			new SearchRequest.Builder();

		searchRequestBuilder.index(
			Arrays.asList(suggestSearchRequest.getIndexNames()));

		Map<String, Suggester> suggesters =
			suggestSearchRequest.getSuggesterMap();

		co.elastic.clients.elasticsearch.core.search.Suggester.Builder
			suggesterBuilder =
				new co.elastic.clients.elasticsearch.core.search.Suggester.
					Builder();

		for (Map.Entry<String, Suggester> entry : suggesters.entrySet()) {
			suggesterBuilder.suggesters(
				entry.getKey(),
				_suggesterTranslator.translate(entry.getValue(), null));
		}

		SetterUtil.setNotBlankString(
			suggesterBuilder::text, suggestSearchRequest.getGlobalText());

		searchRequestBuilder.suggest(suggesterBuilder.build());

		return searchRequestBuilder.build();
	}

	private SuggestSearchResult.Entry _translateCompletionSuggest(
		CompletionSuggest<JsonData> completionSuggest) {

		SuggestSearchResult.Entry entry = new SuggestSearchResult.Entry(
			completionSuggest.text());

		for (CompletionSuggestOption<JsonData> completionSuggestOption :
				completionSuggest.options()) {

			entry.addOption(
				new SuggestSearchResult.Entry.Option(
					completionSuggestOption.text(),
					ConversionUtil.toFloat(completionSuggestOption.score())));
		}

		return entry;
	}

	private SuggestSearchResult.Entry _translatePhraseSuggest(
		PhraseSuggest phraseSuggest) {

		SuggestSearchResult.Entry entry = new SuggestSearchResult.Entry(
			phraseSuggest.text());

		for (PhraseSuggestOption phraseSuggestOption :
				phraseSuggest.options()) {

			SuggestSearchResult.Entry.Option option =
				new SuggestSearchResult.Entry.Option(
					phraseSuggestOption.text(),
					ConversionUtil.toFloat(phraseSuggestOption.score()));

			SetterUtil.setNotBlankString(
				option::setHighlightedText, phraseSuggestOption.highlighted());

			entry.addOption(option);
		}

		return entry;
	}

	private SuggestSearchResult _translateSuggests(
		String name, List<Suggestion<JsonData>> suggests) {

		SuggestSearchResult suggestSearchResult = new SuggestSearchResult(name);

		for (Suggestion<JsonData> suggest : suggests) {
			if (suggest.isCompletion()) {
				suggestSearchResult.addEntry(
					_translateCompletionSuggest(suggest.completion()));
			}
			else if (suggest.isPhrase()) {
				suggestSearchResult.addEntry(
					_translatePhraseSuggest(suggest.phrase()));
			}
			else {
				suggestSearchResult.addEntry(
					_translateTermSuggest(suggest.term()));
			}
		}

		return suggestSearchResult;
	}

	private SuggestSearchResult.Entry _translateTermSuggest(
		TermSuggest termSuggest) {

		SuggestSearchResult.Entry entry = new SuggestSearchResult.Entry(
			termSuggest.text());

		for (TermSuggestOption termSuggestOption : termSuggest.options()) {
			SuggestSearchResult.Entry.Option option =
				new SuggestSearchResult.Entry.Option(
					termSuggestOption.text(),
					ConversionUtil.toFloat(termSuggestOption.score()));

			option.setFrequency(Math.toIntExact(termSuggestOption.freq()));

			entry.addOption(option);
		}

		return entry;
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;
	private final SuggesterTranslator<FieldSuggester> _suggesterTranslator =
		new ElasticsearchSuggesterTranslator();

}