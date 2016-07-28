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

import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;

import java.util.Optional;

/**
 * @author André de Oliveira
 */
public interface SearchBarPortletPreferences {

	public static final String DEFAULT_KEYWORDS_PARAMETER_NAME = "q";

	public static final SearchScopePreference DEFAULT_SCOPE =
		SearchScopePreference.EVERYTHING;

	public static final String PREFERENCE_DESTINATION = "destination";

	public static final String PREFERENCE_KEYWORDS_PARAMETER_NAME =
		"keywordsParameterName";

	public static final String PREFERENCE_SCOPE = "scope";

	public Optional<String> getDestination();

	public String getDestinationString();

	public Optional<String> getKeywordsParameterName();

	public String getKeywordsParameterNameString();

	public String getScopeString();

	public SearchScopePreference getSearchScopePreference();

}