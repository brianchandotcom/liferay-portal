/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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