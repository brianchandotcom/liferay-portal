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

package com.liferay.portal.search.web.internal.searchbar.portlet;

import com.liferay.portal.search.web.internal.display.context.SearchScope;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;

/**
 * @author André de Oliveira
 */
public class SearchBarPortletDisplayBuilder {

	public SearchBarPortletDisplayContext build() {
		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			new SearchBarPortletDisplayContext();

		searchBarPortletDisplayContext.setKeywords(_keywords);
		searchBarPortletDisplayContext.setKeywordsParameterName(
			_keywordsParameterName);

		if (_searchScopePreference ==
				SearchScopePreference.LET_THE_USER_CHOOSE) {

			searchBarPortletDisplayContext.setScopePreferenceLetTheUserChoose(
				true);
		}

		if (_searchScope == SearchScope.EVERYTHING) {
			searchBarPortletDisplayContext.setScopeEverythingSelected(true);
		}

		if (_searchScope == SearchScope.THIS_SITE) {
			searchBarPortletDisplayContext.setScopeSiteSelected(true);
		}

		searchBarPortletDisplayContext.setScopeParameterString(
			_searchScope.getParameterString());
		searchBarPortletDisplayContext.setScopePreferenceEverythingAvailable(
			_scopePreferenceEverythingAvailable);

		return searchBarPortletDisplayContext;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setKeywordsParameterName(String keywordsParameterName) {
		_keywordsParameterName = keywordsParameterName;
	}

	public void setScope(SearchScope searchScope) {
		_searchScope = searchScope;
	}

	public void setScopePreferenceEverythingAvailable(
		boolean scopePreferenceEverythingAvailable) {

		_scopePreferenceEverythingAvailable =
			scopePreferenceEverythingAvailable;
	}

	public void setSearchScopePreference(
		SearchScopePreference searchScopePreference) {

		_searchScopePreference = searchScopePreference;
	}

	private String _keywords;
	private String _keywordsParameterName;
	private boolean _scopePreferenceEverythingAvailable;
	private SearchScope _searchScope;
	private SearchScopePreference _searchScopePreference;

}