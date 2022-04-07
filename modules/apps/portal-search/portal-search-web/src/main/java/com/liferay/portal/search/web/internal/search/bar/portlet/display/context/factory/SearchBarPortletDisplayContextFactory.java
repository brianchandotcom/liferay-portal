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

package com.liferay.portal.search.web.internal.search.bar.portlet.display.context.factory;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.internal.display.context.SearchScope;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;
import com.liferay.portal.search.web.internal.portlet.preferences.PortletPreferencesLookup;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletDestinationUtil;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletPreferences;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.search.bar.portlet.configuration.SearchBarPortletInstanceConfiguration;
import com.liferay.portal.search.web.internal.search.bar.portlet.display.context.SearchBarPortletDisplayContext;
import com.liferay.portal.search.web.internal.search.bar.portlet.helper.SearchBarPrecedenceHelper;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.search.request.SearchSettings;

import java.util.Optional;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author André de Oliveira
 */
public class SearchBarPortletDisplayContextFactory {

	public SearchBarPortletDisplayContextFactory(
		Http http, LayoutLocalService layoutLocalService, Portal portal,
		RenderRequest renderRequest) {

		_http = http;
		_layoutLocalService = layoutLocalService;
		_portal = portal;
		_renderRequest = renderRequest;
	}

	public SearchBarPortletDisplayContext create(
		PortletPreferencesLookup portletPreferencesLookup,
		PortletSharedSearchRequest portletSharedSearchRequest,
		SearchBarPrecedenceHelper searchBarPrecedenceHelper) {

		SearchBarPortletDisplayContext searchBarPortletDisplayContext =
			new SearchBarPortletDisplayContext();

		SearchBarPortletPreferences searchBarPortletPreferences =
			new SearchBarPortletPreferencesImpl(
				Optional.ofNullable(_renderRequest.getPreferences()));

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String destination = searchBarPortletPreferences.getDestinationString();

		if (Validator.isBlank(destination)) {
			searchBarPortletDisplayContext.setSearchURL(
				_getURLCurrentPath(themeDisplay));
		}
		else {
			String destinationURL = _getDestinationURL(
				destination, themeDisplay);

			if (destinationURL == null) {
				searchBarPortletDisplayContext.setDestinationUnreachable(true);
				searchBarPortletDisplayContext.setRenderNothing(true);

				return searchBarPortletDisplayContext;
			}

			searchBarPortletDisplayContext.setSearchURL(destinationURL);
		}

		PortletSharedSearchResponse portletSharedSearchResponse =
			portletSharedSearchRequest.search(_renderRequest);

		SearchBarPortletInstanceConfiguration
			searchBarPortletInstanceConfiguration =
				getSearchBarPortletInstanceConfiguration(
					themeDisplay.getPortletDisplay());

		searchBarPortletDisplayContext.setAvailableEverythingSearchScope(
			isAvailableEverythingSearchScope());
		searchBarPortletDisplayContext.setCurrentSiteSearchScopeParameterString(
			SearchScope.THIS_SITE.getParameterString());
		searchBarPortletDisplayContext.setDisplayStyleGroupId(
			getDisplayStyleGroupId(
				searchBarPortletInstanceConfiguration, themeDisplay));
		searchBarPortletDisplayContext.setEmptySearchEnabled(
			_isEmptySearchEnabled(portletSharedSearchResponse));
		searchBarPortletDisplayContext.setEverythingSearchScopeParameterString(
			SearchScope.EVERYTHING.getParameterString());

		SearchResponse searchResponse = _getSearchResponse(
			portletSharedSearchResponse, searchBarPortletPreferences);

		SearchRequest searchRequest = searchResponse.getRequest();

		searchBarPortletDisplayContext.setInputPlaceholder(
			LanguageUtil.get(
				getHttpServletRequest(_renderRequest), "search-..."));
		searchBarPortletDisplayContext.setKeywords(
			Optional.ofNullable(
				searchRequest.getQueryString()
			).orElse(
				StringPool.BLANK
			));
		searchBarPortletDisplayContext.setKeywordsParameterName(
			_getKeywordsParameterName(
				portletPreferencesLookup,
				portletSharedSearchResponse.getSearchSettings(),
				searchBarPrecedenceHelper, searchBarPortletPreferences,
				themeDisplay));

		SearchScopePreference searchScopePreference =
			searchBarPortletPreferences.getSearchScopePreference();

		if (searchScopePreference ==
				SearchScopePreference.LET_THE_USER_CHOOSE) {

			searchBarPortletDisplayContext.setLetTheUserChooseTheSearchScope(
				true);
		}

		String paginationStartParameterName =
			searchRequest.getPaginationStartParameterName();

		searchBarPortletDisplayContext.setPaginationStartParameterName(
			(paginationStartParameterName != null) ?
				paginationStartParameterName : StringPool.BLANK);

		String scopeParameterName = _getScopeParameterName(
			portletPreferencesLookup, searchBarPrecedenceHelper,
			portletSharedSearchResponse.getSearchSettings(),
			searchBarPortletPreferences, themeDisplay);

		Optional<String> scopeParameterValueOptional =
			portletSharedSearchResponse.getParameter(
				scopeParameterName, _renderRequest);

		String scopeParameterValue = scopeParameterValueOptional.orElse(null);

		searchBarPortletDisplayContext.setScopeParameterName(
			scopeParameterName);
		searchBarPortletDisplayContext.setScopeParameterValue(
			(scopeParameterValue != null) ? scopeParameterValue :
				StringPool.BLANK);
		searchBarPortletDisplayContext.setSearchBarPortletInstanceConfiguration(
			searchBarPortletInstanceConfiguration);

		_setSelectedSearchScope(
			searchBarPortletDisplayContext, searchScopePreference,
			scopeParameterValue);

		if (searchBarPortletPreferences.isInvisible()) {
			searchBarPortletDisplayContext.setRenderNothing(true);
		}

		return searchBarPortletDisplayContext;
	}

	protected Layout fetchLayoutByFriendlyURL(
		long groupId, String friendlyURL) {

		Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
			groupId, false, friendlyURL);

		if (layout != null) {
			return layout;
		}

		return _layoutLocalService.fetchLayoutByFriendlyURL(
			groupId, true, friendlyURL);
	}

	protected long getDisplayStyleGroupId(
		SearchBarPortletInstanceConfiguration
			searchBarPortletInstanceConfiguration,
		ThemeDisplay themeDisplay) {

		long displayStyleGroupId =
			searchBarPortletInstanceConfiguration.displayStyleGroupId();

		if (displayStyleGroupId <= 0) {
			displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return displayStyleGroupId;
	}

	protected HttpServletRequest getHttpServletRequest(
		RenderRequest renderRequest) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);

		return liferayPortletRequest.getHttpServletRequest();
	}

	protected String getLayoutFriendlyURL(
		Layout layout, ThemeDisplay themeDisplay) {

		try {
			return _portal.getLayoutFriendlyURL(layout, themeDisplay);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get friendly URL for layout " +
						layout.getLinkedToLayout(),
					portalException);
			}

			return null;
		}
	}

	protected SearchBarPortletInstanceConfiguration
		getSearchBarPortletInstanceConfiguration(
			PortletDisplay portletDisplay) {

		try {
			return portletDisplay.getPortletInstanceConfiguration(
				SearchBarPortletInstanceConfiguration.class);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	protected SearchScope getSearchScope(
		SearchScopePreference searchScopePreference,
		String scopeParameterValue) {

		if (scopeParameterValue != null) {
			return SearchScope.getSearchScope(scopeParameterValue);
		}

		SearchScope searchScope = searchScopePreference.getSearchScope();

		if (searchScope != null) {
			return searchScope;
		}

		return SearchScope.THIS_SITE;
	}

	protected boolean isAvailableEverythingSearchScope() {
		return true;
	}

	private String _getDestinationURL(
		String friendlyURL, ThemeDisplay themeDisplay) {

		Layout layout = fetchLayoutByFriendlyURL(
			themeDisplay.getScopeGroupId(), _slashify(friendlyURL));

		if (layout == null) {
			return null;
		}

		return getLayoutFriendlyURL(layout, themeDisplay);
	}

	private String _getKeywordsParameterName(
		PortletPreferencesLookup portletPreferencesLookup,
		SearchSettings searchSettings,
		SearchBarPrecedenceHelper searchBarPrecedenceHelper,
		SearchBarPortletPreferences searchBarPortletPreferences,
		ThemeDisplay themeDisplay) {

		Optional<Portlet> headerSearchBarOptional =
			searchBarPrecedenceHelper.findHeaderSearchBarPortletOptional(
				themeDisplay);

		if (headerSearchBarOptional.isPresent()) {
			Optional<PortletPreferences> headerPortletPreferencesOptional =
				portletPreferencesLookup.fetchPreferences(
					headerSearchBarOptional.get(), themeDisplay);

			if (headerPortletPreferencesOptional.isPresent() &&
				SearchBarPortletDestinationUtil.isSameDestination(
					headerPortletPreferencesOptional.get(), themeDisplay)) {

				Optional<String> optional =
					searchSettings.getKeywordsParameterName();

				return optional.orElse(
					searchBarPortletPreferences.getKeywordsParameterName());
			}
		}

		return searchBarPortletPreferences.getKeywordsParameterName();
	}

	private String _getScopeParameterName(
		PortletPreferencesLookup portletPreferencesLookup,
		SearchBarPrecedenceHelper searchBarPrecedenceHelper,
		SearchSettings searchSettings,
		SearchBarPortletPreferences searchBarPortletPreferences,
		ThemeDisplay themeDisplay) {

		Optional<Portlet> headerSearchBarOptional =
			searchBarPrecedenceHelper.findHeaderSearchBarPortletOptional(
				themeDisplay);

		if (headerSearchBarOptional.isPresent()) {
			Optional<PortletPreferences> headerPortletPreferencesOptional =
				portletPreferencesLookup.fetchPreferences(
					headerSearchBarOptional.get(), themeDisplay);

			if (headerPortletPreferencesOptional.isPresent() &&
				SearchBarPortletDestinationUtil.isSameDestination(
					headerPortletPreferencesOptional.get(), themeDisplay)) {

				Optional<String> optional =
					searchSettings.getScopeParameterName();

				return optional.orElse(
					searchBarPortletPreferences.getScopeParameterName());
			}
		}

		return searchBarPortletPreferences.getScopeParameterName();
	}

	private SearchResponse _getSearchResponse(
		PortletSharedSearchResponse portletSharedSearchResponse,
		SearchBarPortletPreferences searchBarPortletPreferences) {

		return portletSharedSearchResponse.getFederatedSearchResponse(
			searchBarPortletPreferences.getFederatedSearchKeyOptional());
	}

	private String _getURLCurrentPath(ThemeDisplay themeDisplay) {
		return _http.getPath(themeDisplay.getURLCurrent());
	}

	private boolean _isEmptySearchEnabled(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest searchRequest = searchResponse.getRequest();

		return searchRequest.isEmptySearchEnabled();
	}

	private void _setSelectedSearchScope(
		SearchBarPortletDisplayContext searchBarPortletDisplayContext,
		SearchScopePreference searchScopePreference,
		String scopeParameterValue) {

		SearchScope searchScope = getSearchScope(
			searchScopePreference, scopeParameterValue);

		if (searchScope == SearchScope.EVERYTHING) {
			searchBarPortletDisplayContext.setSelectedEverythingSearchScope(
				true);
		}

		if (searchScope == SearchScope.THIS_SITE) {
			searchBarPortletDisplayContext.setSelectedCurrentSiteSearchScope(
				true);
		}
	}

	private String _slashify(String s) {
		if (s.charAt(0) == CharPool.SLASH) {
			return s;
		}

		return StringPool.SLASH.concat(s);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchBarPortletDisplayContextFactory.class);

	private final Http _http;
	private final LayoutLocalService _layoutLocalService;
	private final Portal _portal;
	private final RenderRequest _renderRequest;

}