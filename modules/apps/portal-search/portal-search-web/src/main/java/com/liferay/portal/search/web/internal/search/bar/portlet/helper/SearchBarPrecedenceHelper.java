/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = SearchBarPrecedenceHelper.class)
public class SearchBarPrecedenceHelper {

	public Portlet findHeaderSearchBarPortlet(ThemeDisplay themeDisplay) {
		List<Portlet> portlets = _getPortlets(themeDisplay);

		Portlet headerSearchBarPortlet = null;

		for (Portlet portlet : portlets) {
			if (_isHeaderSearchBar(portlet)) {
				headerSearchBarPortlet = portlet;

				break;
			}
		}

		return headerSearchBarPortlet;
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

		Portlet headerSearchBarPortlet = findHeaderSearchBarPortlet(
			themeDisplay);

		if ((headerSearchBarPortlet == null) ||
			_isSamePortlet(headerSearchBarPortlet, portletId)) {

			return false;
		}

		SearchBarPortletPreferences searchBarPortletPreferences1 =
			_getSearchBarPortletPreferences(
				headerSearchBarPortlet, themeDisplay);

		if (!SearchBarPortletDestinationUtil.isSameDestination(
				searchBarPortletPreferences1, themeDisplay)) {

			return false;
		}

		SearchBarPortletPreferences searchBarPortletPreferences2 =
			_getSearchBarPortletPreferences(portletId, themeDisplay);

		if (!Objects.equals(
				searchBarPortletPreferences1.getFederatedSearchKey(),
				searchBarPortletPreferences2.getFederatedSearchKey())) {

			return false;
		}

		return true;
	}

	private List<Portlet> _getPortlets(ThemeDisplay themeDisplay) {
		Layout layout = themeDisplay.getLayout();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		return layoutTypePortlet.getAllPortlets(false);
	}

	private SearchBarPortletPreferences _getSearchBarPortletPreferences(
		Portlet portlet, ThemeDisplay themeDisplay) {

		if (portlet == null) {
			return new SearchBarPortletPreferencesImpl(null);
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