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

package com.liferay.portal.search.web.internal.search.bar.portlet;

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
			SearchBarPortletPreferences.DESTINATION_PREFERENCE_KEY);
	}

	@Override
	public String getDestinationString() {
		Optional<String> valueOptional = getDestination();

		return valueOptional.orElse(StringPool.BLANK);
	}

	@Override
	public String getKeywordsParameterName() {
		return _portletPreferencesHelper.getString(
			SearchBarPortletPreferences.KEYWORDS_PARAMETER_NAME_PREFERENCE_KEY,
			"q");
	}

	@Override
	public String getScopeParameterName() {
		return _portletPreferencesHelper.getString(
			SearchBarPortletPreferences.SCOPE_PARAMETER_NAME_PREFERENCE_KEY,
			"scope");
	}

	@Override
	public SearchScopePreference getSearchScopePreference() {
		Optional<String> valueOptional = _portletPreferencesHelper.getString(
			SearchBarPortletPreferences.SEARCH_SCOPE_PREFERENCE_KEY);

		Optional<SearchScopePreference> searchScopePreferenceOptional =
			valueOptional.map(SearchScopePreference::getSearchScopePreference);

		return searchScopePreferenceOptional.orElse(
			SearchScopePreference.EVERYTHING);
	}

	@Override
	public String getSearchScopePreferenceString() {
		SearchScopePreference searchScopePreference =
			getSearchScopePreference();

		return searchScopePreference.getPreferenceString();
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}