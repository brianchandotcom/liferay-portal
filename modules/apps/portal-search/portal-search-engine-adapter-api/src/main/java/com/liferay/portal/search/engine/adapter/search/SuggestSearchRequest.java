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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.suggest.Suggester;

/**
 * @author Michael C. Han
 */
@ProviderType
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

	public String[] getIndexNames() {
		return _indexNames;
	}

	public Suggester getSuggester() {
		return _suggester;
	}

	public void setSuggester(Suggester suggester) {
		_suggester = suggester;
	}

	private final String[] _indexNames;
	private Suggester _suggester;

}