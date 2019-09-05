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

package com.liferay.portal.search.engine.adapter.search;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class SuggestSearchResponse implements SearchResponse {

	public void addSuggestSearchResult(
		SuggestSearchResult suggestSearchResult) {

		_suggestSearchResultMap.put(
			suggestSearchResult.getName(), suggestSearchResult);
	}

	public Collection<String> getSuggesterNames() {
		return Collections.unmodifiableCollection(
			_suggestSearchResultMap.keySet());
	}

	public SuggestSearchResult getSuggesterResult(String name) {
		return _suggestSearchResultMap.get(name);
	}

	public Map<String, SuggestSearchResult> getSuggestSearchResultMap() {
		return Collections.unmodifiableMap(_suggestSearchResultMap);
	}

	public Collection<SuggestSearchResult> getSuggestSearchResults() {
		return Collections.unmodifiableCollection(
			_suggestSearchResultMap.values());
	}

	private final Map<String, SuggestSearchResult> _suggestSearchResultMap =
		new HashMap<>();

}