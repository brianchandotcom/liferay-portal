/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.display.context;

import java.util.EnumSet;
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
		new HashMap<String, SearchScope>() {
			{
				for (SearchScope searchScope :
						EnumSet.allOf(SearchScope.class)) {

					put(searchScope._parameterString, searchScope);
				}
			}
		};

	private final String _parameterString;

}