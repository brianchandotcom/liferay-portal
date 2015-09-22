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

package com.liferay.application.list.taglib.display.context.logic;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;

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

	public boolean containsPortlet(
		String portletId, PanelCategory panelCategory) {

		for (PanelCategory curPanelCategory :
				_panelCategoryRegistry.getChildPanelCategories(panelCategory)) {

			if (hasPortlet(portletId, curPanelCategory)) {
				return true;
			}

			if (containsPortlet(portletId, curPanelCategory)) {
				return true;
			}
		}

		return hasPortlet(portletId, panelCategory);
	}

	public String getFirstPortletId(
		String panelCategoryKey, PermissionChecker permissionChecker,
		Group group) {

		PanelCategory panelCategory =
			_panelCategoryRegistry.getFirstChildPanelCategory(
				panelCategoryKey, permissionChecker, group);

		if (panelCategory == null) {
			return null;
		}

		PanelApp panelApp = _panelAppRegistry.getFirstPanelApp(
			panelCategory, permissionChecker, group);

		if (panelApp == null) {
			return null;
		}

		return panelApp.getPortletId();
	}

	private boolean hasPortlet(String portletId, PanelCategory panelCategory) {
		Iterable<PanelApp> panelApps = _panelAppRegistry.getPanelApps(
			panelCategory);

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