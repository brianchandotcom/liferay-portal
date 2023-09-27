/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.suggestions.display.context.builder;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.search.suggest.KeywordsSuggestionHolder;
import com.liferay.portal.search.web.internal.suggestions.display.context.SuggestionDisplayContext;
import com.liferay.portal.search.web.internal.suggestions.display.context.SuggestionsPortletDisplayContext;

import java.util.List;
import java.util.Objects;

/**
 * @author Adam Brandizzi
 */
public class SuggestionsPortletDisplayContextBuilder {

	public SuggestionsPortletDisplayContextBuilder(Html html) {
		_html = html;
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

		StringBundler sb = new StringBundler(suggestedKeywords.size() * 2);

		for (String suggestedKeyword : suggestedKeywords) {
			sb.append(
				_formatSuggestedKeyword(
					suggestedKeyword,
					keywordsSuggestionHolder.hasChanged(suggestedKeyword)));
			sb.append(StringPool.SPACE);
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private List<SuggestionDisplayContext> _buildRelatedQueriesSuggestions() {
		return TransformUtil.transform(
			_relatedQueriesSuggestions, this::_buildSuggestionDisplayContext);
	}

	private String _buildSearchURL(
		KeywordsSuggestionHolder keywordsSuggestionHolder) {

		String parameterValue = StringUtil.merge(
			keywordsSuggestionHolder.getSuggestedKeywords(), StringPool.SPACE);

		return HttpComponentsUtil.setParameter(
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

		if (changed) {
			sb.append("changed-keyword\"><strong>");
			sb.append(_html.escape(keyword));
			sb.append("</strong>");
		}
		else {
			sb.append("unchanged-keyword\">");
			sb.append(_html.escape(keyword));
		}

		sb.append("</span>");

		return sb.toString();
	}

	private boolean _isValidSuggestion(String suggestion) {
		if (Objects.equals(_keywords, suggestion) ||
			Validator.isNull(suggestion)) {

			return false;
		}

		return true;
	}

	private final Html _html;
	private String _keywords;
	private String _keywordsParameterName;
	private List<String> _relatedQueriesSuggestions;
	private boolean _relatedQueriesSuggestionsEnabled;
	private String _searchURL;
	private String _spellCheckSuggestion;
	private boolean _spellCheckSuggestionEnabled;

}