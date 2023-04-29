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

package com.liferay.site.admin.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.permission.GroupPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.configuration.MenuAccessConfigurationProvider;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SITE_SETTINGS,
		"mvc.command.name=/site_settings/edit_menu_access_configuration"
	},
	service = MVCActionCommand.class
)
public class EditMenuAccessConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_groupPermission.check(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup(),
			ActionKeys.UPDATE);

		String[] roleSearchContainerPrimaryKeys = ParamUtil.getStringValues(
			actionRequest, "roleSearchContainerPrimaryKeys");

		List<String> roleIds = new ArrayList<>();

		for (String roleId : roleSearchContainerPrimaryKeys) {
			Role role = _roleLocalService.fetchRole(Long.valueOf(roleId));

			if (role != null) {
				roleIds.add(roleId);
			}
		}

		_menuAccessConfigurationProvider.updateMenuAccessConfiguration(
			themeDisplay.getScopeGroupId(), ArrayUtil.toStringArray(roleIds),
			ParamUtil.getBoolean(actionRequest, "showControlMenuByRole"));
	}

	@Reference
	private GroupPermission _groupPermission;

	@Reference
	private MenuAccessConfigurationProvider _menuAccessConfigurationProvider;

	@Reference
	private RoleLocalService _roleLocalService;

}