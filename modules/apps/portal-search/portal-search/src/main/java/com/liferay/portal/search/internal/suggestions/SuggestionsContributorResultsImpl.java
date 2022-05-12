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