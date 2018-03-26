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

package com.liferay.portal.search.web.internal.suggestions.portlet;

/**
 * @author Adam Brandizzi
 */
public interface SuggestionsPortletPreferences {

	public static final String PREFERENCE_KEY_QUERY_INDEXING_ENABLED =
		"queryIndexingEnabled";

	public static final String PREFERENCE_KEY_QUERY_INDEXING_THRESHOLD =
		"queryIndexingThreshold";

	public static final String
		PREFERENCE_KEY_RELATED_QUERIES_SUGGESTIONS_DISPLAY_THRESHOLD =
			"relatedQueriesSuggestionsDisplayThreshold";

	public static final String
		PREFERENCE_KEY_RELATED_QUERIES_SUGGESTIONS_ENABLED =
			"relatedQueriesSuggestionsEnabled";

	public static final String PREFERENCE_KEY_RELATED_QUERIES_SUGGESTIONS_MAX =
		"relatedQueriesSuggestionsMax";

	public static final String
		PREFERENCE_KEY_SPELL_CHECK_SUGGESTION_DISPLAY_THRESHOLD =
			"spellCheckSuggestionDisplayThreshold";

	public static final String PREFERENCE_KEY_SPELL_CHECK_SUGGESTION_ENABLED =
		"spellCheckSuggestionEnabled";

	public int getQueryIndexingThreshold();

	public int getRelatedQueriesSuggestionsDisplayThreshold();

	public int getRelatedQueriesSuggestionsMax();

	public int getSpellCheckSuggestionDisplayThreshold();

	public boolean isQueryIndexingEnabled();

	public boolean isRelatedQueriesSuggestionsEnabled();

	public boolean isSpellCheckSuggestionEnabled();

}