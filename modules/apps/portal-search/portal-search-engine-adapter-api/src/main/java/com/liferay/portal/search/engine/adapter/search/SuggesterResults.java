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

import com.liferay.portal.kernel.search.suggest.SuggesterResult;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class SuggesterResults {

	public void addSuggesterResult(SuggesterResult suggesterResult) {
		_suggesterResults.put(suggesterResult.getName(), suggesterResult);
	}

	public Collection<String> getSuggesterNames() {
		return Collections.unmodifiableCollection(_suggesterResults.keySet());
	}

	public SuggesterResult getSuggesterResult(String name) {
		return _suggesterResults.get(name);
	}

	public Collection<SuggesterResult> getSuggesterResults() {
		return Collections.unmodifiableCollection(_suggesterResults.values());
	}

	private final Map<String, SuggesterResult> _suggesterResults =
		new HashMap<>();

}