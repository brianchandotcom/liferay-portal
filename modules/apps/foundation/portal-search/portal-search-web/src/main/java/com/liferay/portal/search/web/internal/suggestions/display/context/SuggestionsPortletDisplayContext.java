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