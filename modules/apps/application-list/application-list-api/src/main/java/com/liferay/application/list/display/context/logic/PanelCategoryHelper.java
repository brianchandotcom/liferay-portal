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

package com.liferay.application.list.display.context.logic;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class PanelCategoryHelper {

	public PanelCategoryHelper(
		PanelAppRegistry panelAppRegistry,
		PanelCategoryRegistry panelCategoryRegistry) {

		_panelAppRegistry = panelAppRegistry;
		_panelCategoryRegistry = panelCategoryRegistry;
	}

	public boolean containsPortlet(String portletId, String panelCategoryKey) {
		for (PanelCategory curPanelCategory :
				_panelCategoryRegistry.getChildPanelCategories(
					panelCategoryKey)) {

			if (hasPortlet(portletId, curPanelCategory.getKey())) {
				return true;
			}

			if (containsPortlet(portletId, curPanelCategory.getKey())) {
				return true;
			}
		}

		return hasPortlet(portletId, panelCategoryKey);
	}

	public boolean containsPortlet(
		String portletId, String panelCategoryKey,
		PermissionChecker permissionChecker, Group group) {

		for (PanelCategory curPanelCategory :
				_panelCategoryRegistry.getChildPanelCategories(
					panelCategoryKey, permissionChecker, group)) {

			if (hasPortlet(
					portletId, curPanelCategory.getKey(), permissionChecker,
					group)) {

				return true;
			}

			if (containsPortlet(
					portletId, curPanelCategory.getKey(), permissionChecker,
					group)) {

				return true;
			}
		}

		return hasPortlet(
			portletId, panelCategoryKey, permissionChecker, group);
	}

	public List<PanelApp> getAllPanelApps(String panelCategoryKey) {
		List<PanelApp> panelApps = new ArrayList<>();

		panelApps.addAll(_panelAppRegistry.getPanelApps(panelCategoryKey));

		for (PanelCategory childPanelCategory :
				_panelCategoryRegistry.getChildPanelCategories(
					panelCategoryKey)) {

			panelApps.addAll(getAllPanelApps(childPanelCategory.getKey()));
		}

		return panelApps;
	}

	public String getFirstPortletId(
		String panelCategoryKey, PermissionChecker permissionChecker,
		Group group) {

		PanelApp panelApp = _panelAppRegistry.getFirstPanelApp(
			panelCategoryKey, permissionChecker, group);

		if (panelApp != null) {
			return panelApp.getPortletId();
		}

		List<PanelCategory> panelCategories =
			_panelCategoryRegistry.getChildPanelCategories(
				panelCategoryKey, permissionChecker, group);

		if (panelCategories.isEmpty()) {
			return null;
		}

		for (PanelCategory panelCategory : panelCategories) {
			panelApp = _panelAppRegistry.getFirstPanelApp(
				panelCategory.getKey(), permissionChecker, group);

			if (panelApp != null) {
				return panelApp.getPortletId();
			}
		}

		return null;
	}

	public int getNotificationsCount(
		String panelCategoryKey, PermissionChecker permissionChecker,
		Group group, User user) {

		int count =
			_panelCategoryRegistry.getChildPanelCategoriesNotificationsCount(
				this, panelCategoryKey, permissionChecker, group, user);

		count += _panelAppRegistry.getPanelAppsNotificationsCount(
			panelCategoryKey, permissionChecker, group, user);

		return count;
	}

	public boolean hasPanelApp(String portletId) {
		return containsPortlet(portletId, PanelCategoryKeys.ROOT);
	}

	protected boolean hasPortlet(String portletId, String panelCategoryKey) {
		Iterable<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			panelCategoryKey);

		for (PanelApp panelApp : panelApps) {
			if (portletId.equals(panelApp.getPortletId())) {
				return true;
			}
		}

		return false;
	}

	protected boolean hasPortlet(
		String portletId, String panelCategoryKey,
		PermissionChecker permissionChecker, Group group) {

		Iterable<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			panelCategoryKey, permissionChecker, group);

		for (PanelApp panelApp : panelApps) {
			if (portletId.equals(panelApp.getPortletId())) {
				return true;
			}
		}

		return false;
	}

	private final PanelAppRegistry _panelAppRegistry;
	private final PanelCategoryRegistry _panelCategoryRegistry;

}