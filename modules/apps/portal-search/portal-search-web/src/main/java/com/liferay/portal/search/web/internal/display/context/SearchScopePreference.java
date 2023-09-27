/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.display.context;

import com.liferay.portal.kernel.util.Validator;

import java.util.EnumSet;
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
		_searchScopePreferences = new HashMap<String, SearchScopePreference>() {
			{
				for (SearchScopePreference searchScopePreference :
						EnumSet.allOf(SearchScopePreference.class)) {

					put(
						searchScopePreference._preferenceString,
						searchScopePreference);
				}
			}
		};

	private final String _preferenceString;
	private final SearchScope _searchScope;

}