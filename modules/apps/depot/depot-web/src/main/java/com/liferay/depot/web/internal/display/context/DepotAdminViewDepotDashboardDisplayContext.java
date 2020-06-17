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

import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public class DepotAdminViewDepotDashboardDisplayContext {

	public DepotAdminViewDepotDashboardDisplayContext(
		Group group, PanelCategoryRegistry panelCategoryRegistry,
		PermissionChecker permissionChecker) {

		_group = group;
		_panelCategoryRegistry = panelCategoryRegistry;
		_permissionChecker = permissionChecker;
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
	private final PanelCategoryRegistry _panelCategoryRegistry;
	private final PermissionChecker _permissionChecker;

}