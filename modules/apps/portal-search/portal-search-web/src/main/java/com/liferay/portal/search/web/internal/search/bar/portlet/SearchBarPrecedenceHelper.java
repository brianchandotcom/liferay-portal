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

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.internal.portlet.preferences.PortletPreferencesLookup;

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

		if (isSamePortlet(portlet, portletId)) {
			return false;
		}

		if (!SearchBarPortletDestinationUtil.isSameDestination(
				getSearchBarPortletPreferences(portlet, themeDisplay),
				themeDisplay)) {

			return false;
		}

		return true;
	}

	protected Optional<Portlet> findHeaderSearchBarPortletOptional(
		ThemeDisplay themeDisplay) {

		Stream<Portlet> stream = getPortletsStream(themeDisplay);

		return stream.filter(
			this::isHeaderSearchBar
		).findAny();
	}

	protected Stream<Portlet> getPortletsStream(ThemeDisplay themeDisplay) {
		Layout layout = themeDisplay.getLayout();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets(false);

		return portlets.stream();
	}

	protected SearchBarPortletPreferences getSearchBarPortletPreferences(
		Portlet portlet, ThemeDisplay themeDisplay) {

		return new SearchBarPortletPreferencesImpl(
			portletPreferencesLookup.fetchPreferences(portlet, themeDisplay));
	}

	protected boolean isHeaderSearchBar(Portlet portlet) {
		if (portlet.isStatic() &&
			Objects.equals(
				portlet.getPortletName(), SearchBarPortletKeys.SEARCH_BAR)) {

			return true;
		}

		return false;
	}

	protected boolean isSamePortlet(Portlet portlet, String portletId) {
		if (Objects.equals(portlet.getPortletId(), portletId)) {
			return true;
		}

		return false;
	}

	@Reference
	protected PortletPreferencesLookup portletPreferencesLookup;

}