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

import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;

import java.util.Optional;

/**
 * @author André de Oliveira
 */
public interface SearchBarPortletPreferences {

	public static final String DESTINATION_PREFERENCE_KEY = "destination";

	public static final String KEYWORDS_PARAMETER_NAME_PREFERENCE_KEY =
		"keywordsParameterName";

	public static final String SCOPE_PARAMETER_NAME_PREFERENCE_KEY =
		"scopeParameterName";

	public static final String SEARCH_SCOPE_PREFERENCE_KEY = "searchScope";

	public Optional<String> getDestination();

	public String getDestinationString();

	public String getKeywordsParameterName();

	public String getScopeParameterName();

	public SearchScopePreference getSearchScopePreference();

	public String getSearchScopePreferenceString();

}