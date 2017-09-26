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

package com.liferay.portal.search.web.internal.search.bar.portlet.shared.search;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.web.internal.display.context.Keywords;
import com.liferay.portal.search.web.internal.display.context.SearchScope;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;
import com.liferay.portal.search.web.internal.search.bar.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletPreferences;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletPreferencesImpl;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SearchBarPortletKeys.SEARCH_BAR
)
public class SearchBarPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SearchBarPortletPreferences searchBarPortletPreferences =
			new SearchBarPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferences());

		setKeywords(searchBarPortletPreferences, portletSharedSearchSettings);

		filterByThisSite(
			searchBarPortletPreferences, portletSharedSearchSettings);
	}

	protected void filterByThisSite(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SearchScope searchScope = getSearchScope(
			searchBarPortletPreferences, portletSharedSearchSettings);

		if (searchScope != SearchScope.THIS_SITE) {
			return;
		}

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		searchContext.setGroupIds(
			new long[] {getScopeGroupId(portletSharedSearchSettings)});
	}

	protected SearchScope getDefaultSearchScope() {
		SearchBarPortletPreferences searchBarPortletPreferences =
			new SearchBarPortletPreferencesImpl(Optional.empty());

		SearchScopePreference searchScopePreference =
			searchBarPortletPreferences.getSearchScopePreference();

		return searchScopePreference.getSearchScope();
	}

	protected long getScopeGroupId(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		ThemeDisplay themeDisplay =
			portletSharedSearchSettings.getThemeDisplay();

		return themeDisplay.getScopeGroupId();
	}

	protected SearchScope getSearchScope(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SearchScopePreference searchScopePreference =
			searchBarPortletPreferences.getSearchScopePreference();

		if (searchScopePreference !=
				SearchScopePreference.LET_THE_USER_CHOOSE) {

			return searchScopePreference.getSearchScope();
		}

		Optional<String> parameterValueOptional =
			portletSharedSearchSettings.getParameter(
				searchBarPortletPreferences.getScopeParameterName());

		return parameterValueOptional.map(
			SearchScope::getSearchScope
		).orElseGet(
			this::getDefaultSearchScope
		);
	}

	protected boolean isLuceneSyntax(
		SearchBarPortletPreferences searchBarPortletPreferences,
		Keywords keywords) {

		if (searchBarPortletPreferences.isUseAdvancedSearchSyntax() ||
			keywords.isLuceneSyntax()) {

			return true;
		}

		return false;
	}

	protected void setKeywords(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		Optional<String> optional = portletSharedSearchSettings.getParameter(
			searchBarPortletPreferences.getKeywordsParameterName());

		optional.ifPresent(
			value -> {
				Keywords keywords = new Keywords(value);

				portletSharedSearchSettings.setKeywords(keywords.getKeywords());

				if (isLuceneSyntax(searchBarPortletPreferences, keywords)) {
					setLuceneSyntax(portletSharedSearchSettings);
				}
			});

		portletSharedSearchSettings.setKeywordsParameterName(
			searchBarPortletPreferences.getKeywordsParameterName());
	}

	protected void setLuceneSyntax(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		searchContext.setAttribute("luceneSyntax", Boolean.TRUE);
	}

}