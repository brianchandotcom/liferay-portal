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

import com.liferay.portal.kernel.search.suggest.Suggester;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class SuggestSearchRequest
	implements SearchRequest<SuggestSearchResponse> {

	public SuggestSearchRequest(String... indexNames) {
		_indexNames = indexNames;
	}

	@Override
	public SuggestSearchResponse accept(
		SearchRequestExecutor searchRequestExecutor) {

		return searchRequestExecutor.executeSearchRequest(this);
	}

	public void addSuggester(Suggester suggester) {
		_suggesterMap.put(suggester.getName(), suggester);
	}

	public String getGlobalText() {
		return _globalText;
	}

	public String[] getIndexNames() {
		return _indexNames;
	}

	public Suggester getSuggester(String name) {
		return _suggesterMap.get(name);
	}

	public Map<String, Suggester> getSuggesterMap() {
		return _suggesterMap;
	}

	public void setGlobalText(String globalText) {
		_globalText = globalText;
	}

	private String _globalText;
	private final String[] _indexNames;
	private Map<String, Suggester> _suggesterMap = new HashMap<>();

}