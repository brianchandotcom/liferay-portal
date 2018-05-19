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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.display.context.SearchScope;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;

/**
 * @author André de Oliveira
 */
public class SearchBarPortletDisplayBuilder {

	public SearchBarPortletDisplayBuilder(
		Http http, LayoutLocalService layoutLocalService, Portal portal) {

		_http = http;
		_layoutLocalService = layoutLocalService;
		_portal = portal;
	}

	public SearchBarPortletDisplayContext build() {
		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			new SearchBarPortletDisplayContext();

		searchBarPortletDisplayContext.setAvailableEverythingSearchScope(
			isAvailableEverythingSearchScope());
		searchBarPortletDisplayContext.setCurrentSiteSearchScopeParameterString(
			SearchScope.THIS_SITE.getParameterString());
		searchBarPortletDisplayContext.setEverythingSearchScopeParameterString(
			SearchScope.EVERYTHING.getParameterString());
		searchBarPortletDisplayContext.setKeywords(getKeywords());
		searchBarPortletDisplayContext.setKeywordsParameterName(
			_keywordsParameterName);

		if (_searchScopePreference ==
				SearchScopePreference.LET_THE_USER_CHOOSE) {

			searchBarPortletDisplayContext.setLetTheUserChooseTheSearchScope(
				true);
		}

		searchBarPortletDisplayContext.setScopeParameterName(
			_scopeParameterName);
		searchBarPortletDisplayContext.setScopeParameterValue(
			getScopeParameterValue());

		setSelectedSearchScope(searchBarPortletDisplayContext);

		if (Validator.isBlank(_destination)) {
			searchBarPortletDisplayContext.setSearchURL(getURLCurrentPath());
		}
		else {
			String destinationURL = getDestinationURL();

			if (destinationURL == null) {
				searchBarPortletDisplayContext.setDestinationUnreachable(true);
			}
			else {
				searchBarPortletDisplayContext.setSearchURL(destinationURL);
			}
		}

		return searchBarPortletDisplayContext;
	}

	public void setDestination(String destination) {
		_destination = destination;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setKeywordsParameterName(String keywordsParameterName) {
		_keywordsParameterName = keywordsParameterName;
	}

	public void setScopeParameterName(String scopeParameterName) {
		_scopeParameterName = scopeParameterName;
	}

	public void setScopeParameterValue(String scopeParameterValue) {
		_scopeParameterValue = scopeParameterValue;
	}

	public void setSearchScopePreference(
		SearchScopePreference searchScopePreference) {

		_searchScopePreference = searchScopePreference;
	}

	public void setThemeDisplay(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	protected Layout getDestinationLayout() {
		String destination = _destination;

		if (!StringUtil.startsWith(destination, CharPool.SLASH)) {
			destination = StringPool.SLASH.concat(destination);
		}

		return _layoutLocalService.fetchLayoutByFriendlyURL(
			_themeDisplay.getScopeGroupId(), false, destination);
	}

	protected String getDestinationURL() {
		Layout layout = getDestinationLayout();

		if (layout == null) {
			return null;
		}

		String layoutFriendlyURL = getLayoutFriendlyURL(layout);

		if (layoutFriendlyURL == null) {
			return null;
		}

		return layoutFriendlyURL;
	}

	protected String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		return StringPool.BLANK;
	}

	protected String getLayoutFriendlyURL(Layout layout) {
		try {
			return _portal.getLayoutFriendlyURL(layout, _themeDisplay);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get friendly URL for layout " +
						layout.getLinkedToLayout(),
					pe);
			}

			return null;
		}
	}

	protected String getScopeParameterValue() {
		if (_scopeParameterValue != null) {
			return _scopeParameterValue;
		}

		return StringPool.BLANK;
	}

	protected SearchScope getSearchScope() {
		if (_scopeParameterValue != null) {
			return SearchScope.getSearchScope(_scopeParameterValue);
		}

		SearchScope searchScope = _searchScopePreference.getSearchScope();

		if (searchScope != null) {
			return searchScope;
		}

		return SearchScope.THIS_SITE;
	}

	protected String getURLCurrentPath() {
		return _http.getPath(_themeDisplay.getURLCurrent());
	}

	protected boolean isAvailableEverythingSearchScope() {
		Group group = _themeDisplay.getScopeGroup();

		if (group.isStagingGroup()) {
			return false;
		}

		return true;
	}

	protected void setSelectedSearchScope(
		SearchBarPortletDisplayContext searchBarPortletDisplayContext) {

		SearchScope searchScope = getSearchScope();

		if (searchScope == SearchScope.EVERYTHING) {
			searchBarPortletDisplayContext.setSelectedEverythingSearchScope(
				true);
		}

		if (searchScope == SearchScope.THIS_SITE) {
			searchBarPortletDisplayContext.setSelectedCurrentSiteSearchScope(
				true);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchBarPortletDisplayBuilder.class);

	private String _destination;
	private final Http _http;
	private String _keywords;
	private String _keywordsParameterName;
	private final LayoutLocalService _layoutLocalService;
	private final Portal _portal;
	private String _scopeParameterName;
	private String _scopeParameterValue;
	private SearchScopePreference _searchScopePreference;
	private ThemeDisplay _themeDisplay;

}