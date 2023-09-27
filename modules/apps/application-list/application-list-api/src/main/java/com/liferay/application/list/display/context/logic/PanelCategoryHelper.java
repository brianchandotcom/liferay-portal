/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			if (hasPortlet(portletId, curPanelCategory.getKey()) ||
				containsPortlet(portletId, curPanelCategory.getKey())) {

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
					group) ||
				containsPortlet(
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
		if (containsPortlet(portletId, PanelCategoryKeys.APPLICATIONS_MENU) ||
			containsPortlet(portletId, PanelCategoryKeys.ROOT)) {

			return true;
		}

		return false;
	}

	public boolean isApplicationsMenuApp(String portletId) {
		return containsPortlet(portletId, PanelCategoryKeys.APPLICATIONS_MENU);
	}

	public boolean isControlPanelApp(String portletId) {
		return containsPortlet(portletId, PanelCategoryKeys.CONTROL_PANEL);
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