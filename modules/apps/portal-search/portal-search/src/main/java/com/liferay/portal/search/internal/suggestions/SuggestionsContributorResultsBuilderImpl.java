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