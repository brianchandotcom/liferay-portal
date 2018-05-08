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

package com.liferay.roles.selector.web.internal.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserGroupGroupRoleService;
import com.liferay.portal.kernel.service.UserGroupRoleService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.roles.selector.web.internal.constants.RolesSelectorPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-roles-selector",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Roles Selector",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + RolesSelectorPortletKeys.ROLES_SELECTOR,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class RolesSelectorPortlet extends MVCPortlet {

	public void editUserGroupGroupRoleUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		long[] addUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserGroupIds"), 0L);
		long[] removeUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserGroupIds"), 0L);

		_userGroupGroupRoleService.addUserGroupGroupRoles(
			addUserGroupIds, groupId, roleId);
		_userGroupGroupRoleService.deleteUserGroupGroupRoles(
			removeUserGroupIds, groupId, roleId);
	}

	public void editUserGroupRoleUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long roleId = ParamUtil.getLong(actionRequest, "roleId");

		long[] addUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserIds"), 0L);
		long[] removeUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserIds"), 0L);

		_userGroupRoleService.addUserGroupRoles(addUserIds, groupId, roleId);
		_userGroupRoleService.deleteUserGroupRoles(
			removeUserIds, groupId, roleId);
	}

	@Reference(unbind = "-")
	protected void setUserGroupGroupRoleService(
		UserGroupGroupRoleService userGroupGroupRoleService) {

		_userGroupGroupRoleService = userGroupGroupRoleService;
	}

	@Reference(unbind = "-")
	protected void setUserGroupRoleService(
		UserGroupRoleService userGroupRoleService) {

		_userGroupRoleService = userGroupRoleService;
	}

	private UserGroupGroupRoleService _userGroupGroupRoleService;
	private UserGroupRoleService _userGroupRoleService;

}