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

package com.liferay.portal.search.web.internal.search.bar.portlet.helper;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.internal.portlet.preferences.PortletPreferencesLookup;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletDestinationUtil;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletPreferences;
import com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletPreferencesImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = SearchBarPrecedenceHelper.class)
public class SearchBarPrecedenceHelper {

	public Optional<Portlet> findHeaderSearchBarPortletOptional(
		ThemeDisplay themeDisplay) {

		Stream<Portlet> stream = _getPortletsStream(themeDisplay);

		return stream.filter(
			this::_isHeaderSearchBar
		).findAny();
	}

	public boolean isDisplayWarningIgnoredConfiguration(
		ThemeDisplay themeDisplay, boolean usePortletResource) {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String id = portletDisplay.getId();

		if (usePortletResource) {
			id = portletDisplay.getPortletResource();
		}

		if (id.endsWith("_INSTANCE_templateSearch")) {
			return false;
		}

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		boolean hasEditConfigurationPermission =
			permissionChecker.hasPermission(
				themeDisplay.getScopeGroupId(), SearchBarPortletKeys.SEARCH_BAR,
				SearchBarPortletKeys.SEARCH_BAR, ActionKeys.CONFIGURATION);

		if (hasEditConfigurationPermission &&
			isSearchBarInBodyWithHeaderSearchBarAlreadyPresent(
				themeDisplay, SearchBarPortletKeys.SEARCH_BAR)) {

			return true;
		}

		return false;
	}

	public boolean isSearchBarInBodyWithHeaderSearchBarAlreadyPresent(
		ThemeDisplay themeDisplay, String portletId) {

		Optional<Portlet> optional = findHeaderSearchBarPortletOptional(
			themeDisplay);

		if (!optional.isPresent()) {
			return false;
		}

		Portlet portlet = optional.get();

		if (_isSamePortlet(portlet, portletId)) {
			return false;
		}

		SearchBarPortletPreferences searchBarPortletPreferences1 =
			_getSearchBarPortletPreferences(portlet, themeDisplay);

		if (!SearchBarPortletDestinationUtil.isSameDestination(
				searchBarPortletPreferences1, themeDisplay)) {

			return false;
		}

		SearchBarPortletPreferences searchBarPortletPreferences2 =
			_getSearchBarPortletPreferences(portletId, themeDisplay);

		if (!Objects.equals(
				searchBarPortletPreferences1.getFederatedSearchKeyString(),
				searchBarPortletPreferences2.getFederatedSearchKeyString())) {

			return false;
		}

		return true;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortletPreferencesLookup(
		PortletPreferencesLookup portletPreferencesLookup) {

		_portletPreferencesLookup = portletPreferencesLookup;
	}

	private Stream<Portlet> _getPortletsStream(ThemeDisplay themeDisplay) {
		Layout layout = themeDisplay.getLayout();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets(false);

		return portlets.stream();
	}

	private SearchBarPortletPreferences _getSearchBarPortletPreferences(
		Portlet portlet, ThemeDisplay themeDisplay) {

		if (portlet == null) {
			return new SearchBarPortletPreferencesImpl(Optional.empty());
		}

		return new SearchBarPortletPreferencesImpl(
			_portletPreferencesLookup.fetchPreferences(portlet, themeDisplay));
	}

	private SearchBarPortletPreferences _getSearchBarPortletPreferences(
		String portletId, ThemeDisplay themeDisplay) {

		return _getSearchBarPortletPreferences(
			_portletLocalService.getPortletById(
				themeDisplay.getCompanyId(), portletId),
			themeDisplay);
	}

	private boolean _isHeaderSearchBar(Portlet portlet) {
		if (portlet.isStatic() &&
			Objects.equals(
				portlet.getPortletName(), SearchBarPortletKeys.SEARCH_BAR)) {

			return true;
		}

		return false;
	}

	private boolean _isSamePortlet(Portlet portlet, String portletId) {
		if (Objects.equals(portlet.getPortletId(), portletId)) {
			return true;
		}

		return false;
	}

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLookup _portletPreferencesLookup;

}