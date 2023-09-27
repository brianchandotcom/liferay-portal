/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions;

import com.liferay.portal.search.suggestions.Suggestion;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class SuggestionsContributorResultsImpl
	implements SuggestionsContributorResults {

	public SuggestionsContributorResultsImpl(
		Map<String, Object> attributes, String displayGroupName,
		List<Suggestion> suggestions) {

		_attributes = attributes;
		_displayGroupName = displayGroupName;
		_suggestions = suggestions;
	}

	@Override
	public Map<String, Object> getAttributes() {
		if (_attributes == null) {
			return Collections.emptyMap();
		}

		return _attributes;
	}

	@Override
	public String getDisplayGroupName() {
		return _displayGroupName;
	}

	@Override
	public List<Suggestion> getSuggestions() {
		if (_suggestions == null) {
			return Collections.emptyList();
		}

		return _suggestions;
	}

	private final Map<String, Object> _attributes;
	private final String _displayGroupName;
	private final List<Suggestion> _suggestions;

}