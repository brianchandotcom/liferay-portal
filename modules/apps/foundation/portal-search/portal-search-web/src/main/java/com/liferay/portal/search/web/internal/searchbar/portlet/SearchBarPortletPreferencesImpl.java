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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;
import com.liferay.portal.search.web.internal.util.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author André de Oliveira
 */
public class SearchBarPortletPreferencesImpl
	implements SearchBarPortletPreferences {

	public SearchBarPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferencesOptional);
	}

	@Override
	public Optional<String> getDestination() {
		return _portletPreferencesHelper.getString(
			SearchBarPortletPreferences.PREFERENCE_DESTINATION);
	}

	@Override
	public String getDestinationString() {
		Optional<String> value = getDestination();

		return value.orElse(StringPool.BLANK);
	}

	@Override
	public Optional<String> getKeywordsParameterName() {
		return _portletPreferencesHelper.getString(
			SearchBarPortletPreferences.PREFERENCE_KEYWORDS_PARAMETER_NAME);
	}

	@Override
	public String getKeywordsParameterNameString() {
		Optional<String> value = getKeywordsParameterName();

		return value.orElse(
			SearchBarPortletPreferences.DEFAULT_KEYWORDS_PARAMETER_NAME);
	}

	@Override
	public String getScopeString() {
		SearchScopePreference searchScopePreference =
			getSearchScopePreference();

		return searchScopePreference.getPreferenceString();
	}

	@Override
	public SearchScopePreference getSearchScopePreference() {
		Optional<String> value = _portletPreferencesHelper.getString(
			SearchBarPortletPreferences.PREFERENCE_SCOPE);

		Optional<SearchScopePreference> searchScope = value.map(
			SearchScopePreference::getSearchScopePreference);

		return searchScope.orElse(SearchBarPortletPreferences.DEFAULT_SCOPE);
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}