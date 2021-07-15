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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.internal.display.context.Keywords;
import com.liferay.portal.search.web.internal.display.context.SearchScope;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletDestinationUtil;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletPreferences;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPrecedenceHelper;
import com.liferay.portal.search.web.internal.util.SearchOptionalUtil;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SearchBarPortletKeys.SEARCH_BAR,
	service = PortletSharedSearchContributor.class
)
public class SearchBarPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SearchBarPortletPreferences searchBarPortletPreferences =
			new SearchBarPortletPreferencesImpl(
				portletSharedSearchSettings.getPortletPreferencesOptional());

		SearchRequestBuilder searchRequestBuilder =
			portletSharedSearchSettings.getFederatedSearchRequestBuilder(
				searchBarPortletPreferences.getFederatedSearchKeyOptional());

		if (!shouldContributeToCurrentPageSearch(
				searchBarPortletPreferences, portletSharedSearchSettings)) {

			return;
		}

		searchRequestBuilder.withSearchContext(
			searchContext -> searchContext.setIncludeInternalAssetCategories(
				false));

		setKeywords(
			searchRequestBuilder, searchBarPortletPreferences,
			portletSharedSearchSettings);

		setScopeParameterName(
			searchBarPortletPreferences, portletSharedSearchSettings);

		filterByThisSite(
			searchRequestBuilder, searchBarPortletPreferences,
			portletSharedSearchSettings);
	}

	protected void filterByThisSite(
		SearchRequestBuilder searchRequestBuilder,
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SearchScope searchScope = getSearchScope(
			searchBarPortletPreferences, portletSharedSearchSettings);

		if (searchScope == SearchScope.THIS_SITE) {
			searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setGroupIds(
					getGroupIds(portletSharedSearchSettings)));

			return;
		}

		ThemeDisplay themeDisplay =
			portletSharedSearchSettings.getThemeDisplay();

		Group group = groupLocalService.fetchGroup(
			themeDisplay.getScopeGroupId());

		if (!searchBarPortletPreferences.isShowStagedResults() ||
			!group.isStagingGroup()) {

			searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setIncludeStagingGroups(false));
		}
	}

	protected SearchScope getDefaultSearchScope() {
		SearchBarPortletPreferences searchBarPortletPreferences =
			new SearchBarPortletPreferencesImpl(Optional.empty());

		SearchScopePreference searchScopePreference =
			searchBarPortletPreferences.getSearchScopePreference();

		return searchScopePreference.getSearchScope();
	}

	protected long[] getGroupIds(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		ThemeDisplay themeDisplay =
			portletSharedSearchSettings.getThemeDisplay();

		try {
			List<Long> groupIds = new ArrayList<>();

			groupIds.add(themeDisplay.getScopeGroupId());

			List<Group> groups = groupLocalService.getGroups(
				themeDisplay.getCompanyId(), Layout.class.getName(),
				themeDisplay.getScopeGroupId());

			for (Group group : groups) {
				groupIds.add(group.getGroupId());
			}

			return ArrayUtil.toLongArray(groupIds);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return new long[] {themeDisplay.getScopeGroupId()};
		}
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

		Optional<String> optional =
			portletSharedSearchSettings.getParameterOptional(
				searchBarPortletPreferences.getScopeParameterName());

		return optional.map(
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
		SearchRequestBuilder searchRequestBuilder,
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String parameterName =
			searchBarPortletPreferences.getKeywordsParameterName();

		portletSharedSearchSettings.setKeywordsParameterName(parameterName);

		SearchOptionalUtil.copy(
			() -> portletSharedSearchSettings.getParameterOptional(
				parameterName),
			value -> {
				Keywords keywords = new Keywords(value);

				searchRequestBuilder.queryString(keywords.getKeywords());

				if (isLuceneSyntax(searchBarPortletPreferences, keywords)) {
					setLuceneSyntax(searchRequestBuilder);
				}
			});
	}

	protected void setLuceneSyntax(SearchRequestBuilder searchRequestBuilder) {
		searchRequestBuilder.withSearchContext(
			searchContext -> searchContext.setAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_LUCENE_SYNTAX,
				Boolean.TRUE));
	}

	protected void setScopeParameterName(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		portletSharedSearchSettings.setScopeParameterName(
			searchBarPortletPreferences.getScopeParameterName());
	}

	protected boolean shouldContributeToCurrentPageSearch(
		SearchBarPortletPreferences searchBarPortletPreferences,
		PortletSharedSearchSettings portletSharedSearchSettings) {

		if (!SearchBarPortletDestinationUtil.isSameDestination(
				searchBarPortletPreferences,
				portletSharedSearchSettings.getThemeDisplay()) ||
			searchBarPrecedenceHelper.
				isSearchBarInBodyWithHeaderSearchBarAlreadyPresent(
					portletSharedSearchSettings.getThemeDisplay(),
					portletSharedSearchSettings.getPortletId())) {

			return false;
		}

		return true;
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected SearchBarPrecedenceHelper searchBarPrecedenceHelper;

	private static final Log _log = LogFactoryUtil.getLog(
		SearchBarPortletSharedSearchContributor.class);

}