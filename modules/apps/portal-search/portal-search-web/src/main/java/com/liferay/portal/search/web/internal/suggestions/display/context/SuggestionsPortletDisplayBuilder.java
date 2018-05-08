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

package com.liferay.portal.search.web.internal.suggestions.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.search.suggest.KeywordsSuggestionHolder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Adam Brandizzi
 */
public class SuggestionsPortletDisplayBuilder {

	public SuggestionsPortletDisplayBuilder(Html html, Http http) {
		_html = html;
		_http = http;
	}

	public SuggestionsPortletDisplayContext build() {
		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext =
			new SuggestionsPortletDisplayContext();

		buildRelatedQueriesSuggestions(suggestionsPortletDisplayContext);
		buildSpellCheckSuggestion(suggestionsPortletDisplayContext);

		return suggestionsPortletDisplayContext;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setKeywordsParameterName(String keywordsParameterName) {
		_keywordsParameterName = keywordsParameterName;
	}

	public void setRelatedQueriesSuggestions(
		List<String> relatedQueriesSuggestions) {

		_relatedQueriesSuggestions = relatedQueriesSuggestions;
	}

	public void setRelatedQueriesSuggestionsEnabled(
		boolean relatedQueriesSuggestionsEnabled) {

		_relatedQueriesSuggestionsEnabled = relatedQueriesSuggestionsEnabled;
	}

	public void setSearchURL(String searchURL) {
		_searchURL = searchURL;
	}

	public void setSpellCheckSuggestion(String spellCheckSuggestion) {
		_spellCheckSuggestion = spellCheckSuggestion;
	}

	public void setSpellCheckSuggestionEnabled(
		boolean spellCheckSuggestionEnabled) {

		_spellCheckSuggestionEnabled = spellCheckSuggestionEnabled;
	}

	protected void buildRelatedQueriesSuggestions(
		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext) {

		if (!_relatedQueriesSuggestionsEnabled) {
			return;
		}

		suggestionsPortletDisplayContext.setRelatedQueriesSuggestionsEnabled(
			true);

		List<SuggestionDisplayContext> relatedQueriesSuggestions =
			_buildRelatedQueriesSuggestions();

		suggestionsPortletDisplayContext.setHasRelatedQueriesSuggestions(
			!relatedQueriesSuggestions.isEmpty());
		suggestionsPortletDisplayContext.setRelatedQueriesSuggestions(
			relatedQueriesSuggestions);
	}

	protected void buildSpellCheckSuggestion(
		SuggestionsPortletDisplayContext suggestionsPortletDisplayContext) {

		if (!_spellCheckSuggestionEnabled) {
			return;
		}

		suggestionsPortletDisplayContext.setSpellCheckSuggestionEnabled(true);

		SuggestionDisplayContext suggestionDisplayContext =
			_buildSuggestionDisplayContext(_spellCheckSuggestion);

		if (suggestionDisplayContext == null) {
			return;
		}

		suggestionsPortletDisplayContext.setHasSpellCheckSuggestion(true);
		suggestionsPortletDisplayContext.setSpellCheckSuggestion(
			suggestionDisplayContext);
	}

	private String _buildFormattedKeywords(
		KeywordsSuggestionHolder keywordsSuggestionHolder) {

		List<String> suggestedKeywords =
			keywordsSuggestionHolder.getSuggestedKeywords();

		Stream<String> stream = suggestedKeywords.stream();

		return stream.map(
			keyword -> _formatSuggestedKeyword(
				keyword, keywordsSuggestionHolder.hasChanged(keyword))
		).collect(
			Collectors.joining(StringPool.SPACE)
		);
	}

	private List<SuggestionDisplayContext> _buildRelatedQueriesSuggestions() {
		Stream<String> stream = _relatedQueriesSuggestions.stream();

		return stream.map(
			this::_buildSuggestionDisplayContext
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);
	}

	private String _buildSearchURL(
		KeywordsSuggestionHolder keywordsSuggestionHolder) {

		List<String> suggestedKeywords =
			keywordsSuggestionHolder.getSuggestedKeywords();

		String parameterValue = String.join(
			StringPool.SPACE, suggestedKeywords);

		return _http.setParameter(
			_searchURL, _keywordsParameterName, parameterValue);
	}

	private SuggestionDisplayContext _buildSuggestionDisplayContext(
		String suggestion) {

		if (!_isValidSuggestion(suggestion)) {
			return null;
		}

		SuggestionDisplayContext suggestionDisplayContext =
			new SuggestionDisplayContext();

		KeywordsSuggestionHolder keywordsSuggestionHolder =
			new KeywordsSuggestionHolder(suggestion, _keywords);

		suggestionDisplayContext.setSuggestedKeywordsFormatted(
			_buildFormattedKeywords(keywordsSuggestionHolder));

		suggestionDisplayContext.setURL(
			_buildSearchURL(keywordsSuggestionHolder));

		return suggestionDisplayContext;
	}

	private String _formatSuggestedKeyword(String keyword, boolean changed) {
		StringBundler sb = new StringBundler(5);

		sb.append("<span class=\"");

		String keywordCssClass = "unchanged-keyword";

		if (changed) {
			keywordCssClass = "changed-keyword";
		}

		sb.append(keywordCssClass);

		sb.append("\">");
		sb.append(_html.escape(keyword));
		sb.append("</span>");

		return sb.toString();
	}

	private boolean _isValidSuggestion(String suggestion) {
		if (Objects.equals(_keywords, suggestion)) {
			return false;
		}

		if (Validator.isNull(suggestion)) {
			return false;
		}

		return true;
	}

	private final Html _html;
	private final Http _http;
	private String _keywords;
	private String _keywordsParameterName;
	private List<String> _relatedQueriesSuggestions;
	private boolean _relatedQueriesSuggestionsEnabled;
	private String _searchURL;
	private String _spellCheckSuggestion;
	private boolean _spellCheckSuggestionEnabled;

}