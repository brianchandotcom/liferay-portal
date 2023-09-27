/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.suggestions;

import com.liferay.portal.search.suggestions.Suggestion;
import com.liferay.portal.search.suggestions.SuggestionsContributorResults;
import com.liferay.portal.search.suggestions.SuggestionsContributorResultsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class SuggestionsContributorResultsBuilderImpl
	implements SuggestionsContributorResultsBuilder {

	public SuggestionsContributorResultsBuilderImpl attribute(
		String name, Object value) {

		if (_attributes == null) {
			_attributes = new HashMap<>();
		}

		_attributes.put(name, value);

		return this;
	}

	@Override
	public SuggestionsContributorResults build() {
		return new SuggestionsContributorResultsImpl(
			_attributes, _displayGroupName, _suggestions);
	}

	@Override
	public SuggestionsContributorResultsBuilder displayGroupName(
		String displayGroupName) {

		_displayGroupName = displayGroupName;

		return this;
	}

	@Override
	public SuggestionsContributorResultsBuilder suggestions(
		List<Suggestion> suggestions) {

		_suggestions = suggestions;

		return this;
	}

	private Map<String, Object> _attributes;
	private String _displayGroupName;
	private List<Suggestion> _suggestions;

}