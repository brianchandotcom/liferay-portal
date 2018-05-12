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

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.web.internal.display.context.SearchScope;
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

		Optional<Long> groupIdOptional = getThisSiteGroupId(
			searchBarPortletPreferences, portletSharedSearchSettings);

		groupIdOptional.ifPresent(
			groupId -> {
				portletSharedSearchSettings.addCondition(
					new BooleanClauseImpl(
						new TermQueryImpl(
							Field.GROUP_ID, String.valueOf(groupId)),
						BooleanClauseOccur.MUST));
			});
	}

	protected long getScopeGroupId(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		ThemeDisplay themeDisplay =
			portletSharedSearchSettings.getThemeDisplay();

		return themeDisplay.getScopeGroupId();
	}

	protected Optional<SearchScope> getSearchScope(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String parameterName =
			searchBarPortletPreferences.getScopeParameterName();

		Optional<String> parameterValueOptional =
			portletSharedSearchSettings.getParameter(parameterName);

		Optional<SearchScope> searchScopeOptional = parameterValueOptional.map(
			SearchScope::getSearchScope);

		return searchScopeOptional;
	}

	protected Optional<Long> getThisSiteGroupId(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		Optional<SearchScope> searchScopeOptional = getSearchScope(
			searchBarPortletPreferences, portletSharedSearchSettings);

		Optional<SearchScope> thisSiteSearchScopeOptional =
			searchScopeOptional.filter(SearchScope.THIS_SITE::equals);

		return thisSiteSearchScopeOptional.map(
			searchScope -> getScopeGroupId(portletSharedSearchSettings));
	}

	protected void setKeywords(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		Optional<String> parameterValueOptional =
			portletSharedSearchSettings.getParameter(
				searchBarPortletPreferences.getKeywordsParameterName());

		parameterValueOptional.ifPresent(
			portletSharedSearchSettings::setKeywords);

		portletSharedSearchSettings.setKeywordsParameterName(
			searchBarPortletPreferences.getKeywordsParameterName());
	}

}