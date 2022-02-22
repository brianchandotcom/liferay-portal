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

package com.liferay.server.admin.web.internal.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.server.admin.web.internal.constants.ServerAdminNavigationEntryConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mariano Álvaro Sáiz
 */
public class ViewPortalPropertiesDisplayContext {

	public ViewPortalPropertiesDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public PortletURL getClearResultsURL() throws PortletException {
		if (_clearResultsURL != null) {
			return _clearResultsURL;
		}

		_clearResultsURL = PortletURLBuilder.create(
			PortletURLUtil.clone(getPortletURL(), _liferayPortletResponse)
		).setKeywords(
			StringPool.BLANK
		).setNavigation(
			(String)null
		).buildPortletURL();

		return _clearResultsURL;
	}

	public List<String> getOverriddenProperties() {
		if (_overriddenProperties != null) {
			return _overriddenProperties;
		}

		_loadFilteredProperties();

		return _overriddenProperties;
	}

	public PortletURL getPortletURL() {
		if (_portletURL != null) {
			return _portletURL;
		}

		_portletURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/server_admin/view"
		).setTabs1(
			ParamUtil.getString(_httpServletRequest, "tabs1", "resources")
		).setParameter(
			"delta",
			ParamUtil.getInteger(
				_httpServletRequest, SearchContainer.DEFAULT_DELTA_PARAM,
				SearchContainer.DEFAULT_DELTA)
		).setParameter(
			"screenNavigationCategoryKey",
			ParamUtil.getString(
				_httpServletRequest, "screenNavigationCategoryKey",
				ServerAdminNavigationEntryConstants.
					CATEGORY_KEY_PORTAL_PROPERTIES)
		).setParameter(
			"screenNavigationEntryKey",
			ParamUtil.getString(
				_httpServletRequest, "screenNavigationEntryKey",
				ServerAdminNavigationEntryConstants.ENTRY_KEY_PORTAL_PROPERTIES)
		).buildPortletURL();

		return _portletURL;
	}

	public SearchContainer<Map.Entry<String, String>> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		String emptyResultsMessage = null;

		if (_isPortalPropertiesTab()) {
			emptyResultsMessage =
				"no-portal-properties-were-found-that-matched-the-keywords";
		}
		else {
			emptyResultsMessage =
				"no-system-properties-were-found-that-matched-the-keywords";
		}

		_searchContainer = new SearchContainer(
			_liferayPortletRequest, getPortletURL(), null, emptyResultsMessage);

		List<Map.Entry<String, String>> filteredProperties =
			_loadFilteredProperties();

		_searchContainer.setResultsAndTotal(
			() -> ListUtil.subList(
				filteredProperties, _searchContainer.getStart(),
				_searchContainer.getEnd()),
			filteredProperties.size());

		return _searchContainer;
	}

	public int getSearchContainerTotal() {
		SearchContainer<Map.Entry<String, String>> searchContainer =
			getSearchContainer();

		return searchContainer.getTotal();
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	private boolean _isPortalPropertiesTab() {
		if (_portalPropertiesTab != null) {
			return _portalPropertiesTab;
		}

		String tab = ParamUtil.getString(_httpServletRequest, "tabs2");

		_portalPropertiesTab = tab.equals("portal-properties");

		return _portalPropertiesTab;
	}

	private List<Map.Entry<String, String>> _loadFilteredProperties() {
		if (_filteredProperties != null) {
			return _filteredProperties;
		}

		Map<String, String> filteredProperties = new TreeMap<>();

		_overriddenProperties = new ArrayList<>();

		PortletPreferences serverPortletPreferences =
			PrefsPropsUtil.getPreferences();

		Map<String, String[]> serverPortletPreferencesMap =
			serverPortletPreferences.getMap();

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(_themeDisplay.getCompanyId());

		Map<String, String[]> companyPortletPreferencesMap =
			companyPortletPreferences.getMap();

		Properties properties = PropsUtil.getProperties(true);

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String property = (String)entry.getKey();
			String value = StringPool.BLANK;

			boolean overriddenPropertyValue = false;

			if (serverPortletPreferencesMap.containsKey(property) ||
				companyPortletPreferencesMap.containsKey(property)) {

				overriddenPropertyValue = true;
			}

			if (ArrayUtil.contains(
					PropsValues.ADMIN_OBFUSCATED_PROPERTIES, property)) {

				value = StringPool.EIGHT_STARS;
			}
			else if (serverPortletPreferencesMap.containsKey(property)) {
				value = serverPortletPreferences.getValue(
					property, StringPool.BLANK);
			}
			else if (companyPortletPreferencesMap.containsKey(property)) {
				value = companyPortletPreferences.getValue(
					property, StringPool.BLANK);
			}
			else {
				value = (String)entry.getValue();
			}

			String keywords = _getKeywords();

			if (Validator.isNull(keywords) || property.contains(keywords) ||
				value.contains(keywords)) {

				filteredProperties.put(property, value);

				if (overriddenPropertyValue) {
					_overriddenProperties.add(property);
				}
			}
		}

		_filteredProperties = ListUtil.fromCollection(
			filteredProperties.entrySet());

		return _filteredProperties;
	}

	private PortletURL _clearResultsURL;
	private List<Map.Entry<String, String>> _filteredProperties;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private List<String> _overriddenProperties;
	private Boolean _portalPropertiesTab;
	private PortletURL _portletURL;
	private final RenderResponse _renderResponse;
	private SearchContainer<Map.Entry<String, String>> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}