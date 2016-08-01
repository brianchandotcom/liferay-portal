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

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public enum SearchScopePreference {

	EVERYTHING("everything", SearchScope.EVERYTHING),
	LET_THE_USER_CHOOSE("let-the-user-choose", null),
	THIS_SITE("this-site", SearchScope.THIS_SITE);

	public static SearchScopePreference getSearchScopePreference(
		String preferenceString) {

		if (Validator.isNull(preferenceString)) {
			return SearchScopePreference.THIS_SITE;
		}

		SearchScopePreference searchScopePreference =
			_searchScopePreferences.get(preferenceString);

		if (searchScopePreference == null) {
			throw new IllegalArgumentException(
				"The string " + preferenceString +
					" does not correspond to a valid search scope preference");
		}

		return searchScopePreference;
	}

	public String getPreferenceString() {
		return _preferenceString;
	}

	public SearchScope getSearchScope() {
		return _searchScope;
	}

	private SearchScopePreference(
		String preferenceString, SearchScope searchScope) {

		_preferenceString = preferenceString;
		_searchScope = searchScope;
	}

	private static final Map<String, SearchScopePreference>
		_searchScopePreferences = new HashMap<>();

	static {
		for (SearchScopePreference searchScopePreference :
				SearchScopePreference.values()) {

			_searchScopePreferences.put(
				searchScopePreference._preferenceString, searchScopePreference);
		}
	}

	private final String _preferenceString;
	private final SearchScope _searchScope;

}