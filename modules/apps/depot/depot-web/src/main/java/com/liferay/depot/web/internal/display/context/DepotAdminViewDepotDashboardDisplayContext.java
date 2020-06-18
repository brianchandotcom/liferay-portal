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

package com.liferay.depot.web.internal.display.context;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.depot.web.internal.servlet.taglib.clay.DepotDashboardApplicationHorizontalCard;
import com.liferay.depot.web.internal.servlet.taglib.clay.DepotDashboardApplicationVerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.HorizontalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DepotAdminViewDepotDashboardDisplayContext {

	public DepotAdminViewDepotDashboardDisplayContext(
		HttpServletRequest httpServletRequest, Group group,
		PanelAppRegistry panelAppRegistry,
		PanelCategoryRegistry panelCategoryRegistry,
		PermissionChecker permissionChecker, Portal portal) {

		_httpServletRequest = httpServletRequest;
		_group = group;
		_panelAppRegistry = panelAppRegistry;
		_panelCategoryRegistry = panelCategoryRegistry;
		_permissionChecker = permissionChecker;
		_portal = portal;
	}

	public HorizontalCard getDepotDashboardApplicationHorizontalCard(
		PanelApp panelApp, Locale locale) {

		return new DepotDashboardApplicationHorizontalCard(
			_getPortletURL(panelApp),
			_portal.getPortletTitle(panelApp.getPortletId(), locale));
	}

	public VerticalCard getDepotDashboardApplicationVerticalCard(
		PanelApp panelApp, Locale locale) {

		return new DepotDashboardApplicationVerticalCard(
			_getPortletURL(panelApp),
			_portal.getPortletTitle(panelApp.getPortletId(), locale));
	}

	public Collection<PanelApp> getPanelApps(PanelCategory panelCategory)
		throws PortalException {

		Collection<PanelApp> panelApps = new ArrayList<>();

		for (PanelApp panelApp :
				_panelAppRegistry.getPanelApps(panelCategory.getKey())) {

			if (panelApp.isShow(_permissionChecker, _group)) {
				panelApps.add(panelApp);
			}
		}

		return panelApps;
	}

	public Iterable<PanelCategory> getPanelCategories() throws PortalException {
		Collection<PanelCategory> panelCategories = new ArrayList<>();

		for (String panelCategoryKey : _PANEL_CATEGORY_KEYS) {
			PanelCategory panelCategory =
				_panelCategoryRegistry.getPanelCategory(panelCategoryKey);

			if ((panelCategory != null) &&
				panelCategory.isShow(_permissionChecker, _group)) {

				panelCategories.add(panelCategory);
			}
		}

		return panelCategories;
	}

	public boolean isPrimaryPanelCategory(PanelCategory panelCategory) {
		if (Objects.equals(panelCategory.getKey(), _PANEL_CATEGORY_KEYS[0])) {
			return true;
		}

		return false;
	}

	private String _getPortletURL(PanelApp panelApp) {
		PortletURL portletURL = _portal.getControlPanelPortletURL(
			_httpServletRequest, _group, panelApp.getPortletId(), 0, 0,
			PortletRequest.RENDER_PHASE);

		return portletURL.toString();
	}

	// Order is important.

	private static final String[] _PANEL_CATEGORY_KEYS = {
		PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT,
		PanelCategoryKeys.SITE_ADMINISTRATION_CATEGORIZATION,
		PanelCategoryKeys.SITE_ADMINISTRATION_RECYCLE_BIN,
		PanelCategoryKeys.SITE_ADMINISTRATION_MEMBERS,
		PanelCategoryKeys.SITE_ADMINISTRATION_CONFIGURATION,
		PanelCategoryKeys.SITE_ADMINISTRATION_PUBLISHING
	};

	private final Group _group;

	private final HttpServletRequest _httpServletRequest;
	private final PanelAppRegistry _panelAppRegistry;
	private final PanelCategoryRegistry _panelCategoryRegistry;
	private final PermissionChecker _permissionChecker;
	private final Portal _portal;

}