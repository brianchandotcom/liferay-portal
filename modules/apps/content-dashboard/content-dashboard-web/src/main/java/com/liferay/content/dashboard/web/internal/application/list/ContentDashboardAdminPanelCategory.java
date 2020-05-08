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

package com.liferay.content.dashboard.web.internal.application.list;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"panel.category.key=" + PanelCategoryKeys.GLOBAL_MENU_APPLICATIONS,
		"panel.category.order:Integer=401"
	},
	service = PanelCategory.class
)
public class ContentDashboardAdminPanelCategory extends BasePanelCategory {

	public static final String CONTROL_PANEL_CONTENT_DASHBOARD_ADMIN =
		"CONTROL_PANEL_CONTENT_DASHBOARD_ADMIN";

	@Override
	public String getKey() {
		return CONTROL_PANEL_CONTENT_DASHBOARD_ADMIN;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return ResourceBundleUtil.getString(
			resourceBundle, "content-dashboard");
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group) {
		return true;
	}

}