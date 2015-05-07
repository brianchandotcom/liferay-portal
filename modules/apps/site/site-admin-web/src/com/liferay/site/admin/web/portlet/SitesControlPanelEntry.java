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

package com.liferay.site.admin.web.portlet;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.BaseControlPanelEntry;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.site.admin.web.constants.SitesAdminPortletKeys;

import java.util.LinkedHashMap;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 * @author Sergio González
 * @author Miguel Pastor
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + SitesAdminPortletKeys.SITES_ADMIN},
	service = ControlPanelEntry.class
)
public class SitesControlPanelEntry extends BaseControlPanelEntry {

	@Override
	protected boolean hasPermissionImplicitlyGranted(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		if (PropsValues.SITES_CONTROL_PANEL_MEMBERS_VISIBLE) {
			LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

			groupParams.put("site", Boolean.TRUE);
			groupParams.put("usersGroups", permissionChecker.getUserId());

			int count = GroupLocalServiceUtil.searchCount(
				permissionChecker.getCompanyId(), null, null, groupParams);

			if (count > 0) {
				return true;
			}
		}

		return false;
	}

}