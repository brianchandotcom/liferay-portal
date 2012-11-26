/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.sitesadmin;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletCategoryKeys;
import com.liferay.portlet.DefaultControlPanelEntry;

/**
 * @author Eric Min
 */
public class SiteSettingsControlPanelEntry extends DefaultControlPanelEntry {

	@Override
	public boolean hasPermissionDenied(
			Portlet portlet, String category, ThemeDisplay themeDisplay)
		throws Exception {

		String controlPanelCategory = themeDisplay.getControlPanelCategory();

		if (controlPanelCategory.equals(PortletCategoryKeys.CONTENT)) {
			return true;
		}

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (scopeGroup.isCompany() || scopeGroup.isUser()) {
			return true;
		}

		if (!GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), scopeGroup.getGroupId(),
				ActionKeys.UPDATE)) {

			return true;
		}

		return false;
	}

}