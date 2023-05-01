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

package com.liferay.portal.search.web.internal.search.options.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.web.internal.portlet.preferences.BasePortletPreferences;

import javax.portlet.PortletPreferences;

/**
 * @author Wade Cao
 */
public class SearchOptionsPortletPreferencesImpl
	extends BasePortletPreferences implements SearchOptionsPortletPreferences {

	public SearchOptionsPortletPreferencesImpl(
		PortletPreferences portletPreferences) {

		super(portletPreferences);
	}

	@Override
	public String getFederatedSearchKey() {
		return getString(
			SearchOptionsPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY,
			StringPool.BLANK);
	}

	@Override
	public boolean isAllowEmptySearches() {
		return getBoolean(
			SearchOptionsPortletPreferences.PREFERENCE_KEY_ALLOW_EMPTY_SEARCHES,
			false);
	}

	@Override
	public boolean isBasicFacetSelection() {
		return getBoolean(
			SearchOptionsPortletPreferences.
				PREFERENCE_KEY_BASIC_FACET_SELECTION,
			false);
	}

}