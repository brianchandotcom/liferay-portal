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

package com.liferay.roles.admin.web.internal.portlet.configuration.icon;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.roles.admin.constants.RolesAdminPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RolesAdminPortletKeys.ROLES_ADMIN,
		"path=/edit_role.jsp", "path=/edit_role_assignments.jsp",
		"path=/edit_role_permissions.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class DeleteRolePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "delete");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		try {
			PortletURL portletURL = PortletURLFactoryUtil.create(
				portletRequest, RolesAdminPortletKeys.ROLES_ADMIN,
				PortletRequest.ACTION_PHASE);

			portletURL.setParameter(ActionRequest.ACTION_NAME, "deleteRole");
			portletURL.setParameter("mvcPath", "/view.jsp");
			portletURL.setParameter(
				"roleId", String.valueOf(_getRoleId(portletRequest)));

			return portletURL.toString();
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	@Override
	public double getWeight() {
		return 101;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			long roleId = _getRoleId(portletRequest);

			Role role = _roleService.fetchRole(roleId);

			if (!role.isSystem() &&
				RolePermissionUtil.contains(
					themeDisplay.getPermissionChecker(), roleId,
					ActionKeys.DELETE)) {

				return true;
			}

			return false;
		}
		catch (Exception e) {
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setRoleService(RoleService roleService) {
		_roleService = roleService;
	}

	private long _getRoleId(PortletRequest portletRequest) {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return ParamUtil.getLong(request, "roleId");
	}

	private RoleService _roleService;

}