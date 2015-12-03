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

package com.liferay.user.groups.admin.web.custom.attributes;

import com.liferay.portal.model.UserGroup;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.BaseCustomAttributesDisplay;
import com.liferay.portlet.expando.model.CustomAttributesDisplay;
import com.liferay.user.groups.admin.constants.UserGroupsAdminPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Hugo Huijser
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + UserGroupsAdminPortletKeys.USER_GROUPS_ADMIN},
	service = CustomAttributesDisplay.class
)
public class UserGroupCustomAttributesDisplay
	extends BaseCustomAttributesDisplay {

	@Override
	public String getClassName() {
		return UserGroup.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "icon-group";
	}

	@Override
	public String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() +
			"/common/assign_user_group_roles.png";
	}

}