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
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.permission.RolePermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.roles.admin.constants.RolesAdminPortletKeys;

import java.util.ResourceBundle;

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
		"path=/edit_role.jsp", "path=/edit_role_assignments.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class DefinePermissionsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getLocale(portletRequest), getClass());

		return LanguageUtil.get(resourceBundle, "define-permissions");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		try {
			PortletURL portletURL = PortletURLFactoryUtil.create(
				portletRequest, RolesAdminPortletKeys.ROLES_ADMIN,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter("mvcPath", "/edit_role_permissions.jsp");
			portletURL.setParameter(Constants.CMD, Constants.VIEW);
			portletURL.setParameter(
				"redirect", PortalUtil.getCurrentURL(portletRequest));
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
		return 102;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			long roleId = _getRoleId(portletRequest);

			Role role = _roleService.fetchRole(roleId);

			String roleName = role.getName();

			if (!roleName.equals(RoleConstants.ADMINISTRATOR) &&
				!roleName.equals(RoleConstants.SITE_OWNER) &&
				!roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) &&
				!roleName.equals(RoleConstants.ORGANIZATION_OWNER) &&
				!roleName.equals(RoleConstants.OWNER) &&
				!roleName.equals(RoleConstants.SITE_ADMINISTRATOR) &&
				RolePermissionUtil.contains(
					themeDisplay.getPermissionChecker(), roleId,
					ActionKeys.DEFINE_PERMISSIONS)) {

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