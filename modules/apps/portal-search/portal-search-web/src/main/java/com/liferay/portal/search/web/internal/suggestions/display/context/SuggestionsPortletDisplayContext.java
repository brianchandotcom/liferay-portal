/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.suggestions.display.context;

import java.util.Collections;
import java.util.List;

/**
 * @author Adam Brandizzi
 */
public class SuggestionsPortletDisplayContext {

	public List<SuggestionDisplayContext> getRelatedQueriesSuggestions() {
		return _relatedQueriesSuggestions;
	}

	public SuggestionDisplayContext getSpellCheckSuggestion() {
		return _spellCheckSuggestion;
	}

	public boolean hasRelatedQueriesSuggestions() {
		return _hasRelatedQueriesSuggestions;
	}

	public boolean hasSpellCheckSuggestion() {
		return _hasSpellCheckSuggestion;
	}

	public boolean isRelatedQueriesSuggestionsEnabled() {
		return _relatedQueriesSuggestionsEnabled;
	}

	public boolean isSpellCheckSuggestionEnabled() {
		return _spellCheckSuggestionEnabled;
	}

	public void setHasRelatedQueriesSuggestions(
		boolean hasRelatedQueriesSuggestions) {

		_hasRelatedQueriesSuggestions = hasRelatedQueriesSuggestions;
	}

	public void setHasSpellCheckSuggestion(boolean hasSpellCheckSuggestion) {
		_hasSpellCheckSuggestion = hasSpellCheckSuggestion;
	}

	public void setRelatedQueriesSuggestions(
		List<SuggestionDisplayContext> relatedQueriesSuggestions) {

		_relatedQueriesSuggestions = relatedQueriesSuggestions;
	}

	public void setRelatedQueriesSuggestionsEnabled(
		boolean relatedQueriesSuggestionsEnabled) {

		_relatedQueriesSuggestionsEnabled = relatedQueriesSuggestionsEnabled;
	}

	public void setSpellCheckSuggestion(
		SuggestionDisplayContext spellCheckSuggesion) {

		_spellCheckSuggestion = spellCheckSuggesion;
	}

	public void setSpellCheckSuggestionEnabled(
		boolean spellCheckSuggestionEnabled) {

		_spellCheckSuggestionEnabled = spellCheckSuggestionEnabled;
	}

	private boolean _hasRelatedQueriesSuggestions;
	private boolean _hasSpellCheckSuggestion;
	private List<SuggestionDisplayContext> _relatedQueriesSuggestions =
		Collections.emptyList();
	private boolean _relatedQueriesSuggestionsEnabled;
	private SuggestionDisplayContext _spellCheckSuggestion;
	private boolean _spellCheckSuggestionEnabled;

}