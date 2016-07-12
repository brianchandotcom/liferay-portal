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

package com.liferay.portal.search.web.internal.display.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public enum SearchScope {

	EVERYTHING("everything"), THIS_SITE("this-site");

	public static SearchScope getSearchScope(String parameterString) {
		SearchScope searchScope = _searchScopes.get(parameterString);

		if (searchScope == null) {
			throw new IllegalArgumentException(
				"The string " + parameterString +
					" does not correspond to a valid search scope");
		}

		return searchScope;
	}

	public String getParameterString() {
		return _parameterString;
	}

	private SearchScope(String parameterString) {
		_parameterString = parameterString;
	}

	private static final Map<String, SearchScope> _searchScopes =
		new HashMap<>();

	static {
		for (SearchScope searchScope : SearchScope.values()) {
			_searchScopes.put(searchScope._parameterString, searchScope);
		}
	}

	private final String _parameterString;

}