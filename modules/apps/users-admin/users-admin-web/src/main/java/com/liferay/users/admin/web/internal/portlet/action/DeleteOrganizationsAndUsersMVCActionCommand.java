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

package com.liferay.users.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.exception.RequiredOrganizationException;
import com.liferay.portal.kernel.exception.RequiredUserException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/users_admin/delete_organizations_and_users"
	},
	service = MVCActionCommand.class
)
public class DeleteOrganizationsAndUsersMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteOrganizations(ActionRequest actionRequest)
		throws Exception {

		long[] deleteOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteOrganizationIds"), 0L);

		for (long deleteOrganizationId : deleteOrganizationIds) {
			_organizationService.deleteOrganization(deleteOrganizationId);
		}
	}

	protected void deleteUsers(ActionRequest actionRequest) throws Exception {
		long[] deleteUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteUserIds"), 0L);

		for (long deleteUserId : deleteUserIds) {
			_userService.deleteUser(deleteUserId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			deleteOrganizations(actionRequest);
			deleteUsers(actionRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrganizationException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof RequiredOrganizationException ||
					 e instanceof RequiredUserException) {

				SessionErrors.add(actionRequest, e.getClass());

				String redirect = _portal.escapeRedirect(
					ParamUtil.getString(actionRequest, "onErrorRedirect"));

				if (Validator.isNotNull(redirect)) {
					sendRedirect(actionRequest, actionResponse, redirect);
				}
			}
			else {
				throw e;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setOrganizationService(
		OrganizationService organizationService) {

		_organizationService = organizationService;
	}

	@Reference(unbind = "-")
	protected void setUserService(UserService userService) {
		_userService = userService;
	}

	private OrganizationService _organizationService;

	@Reference
	private Portal _portal;

	private UserService _userService;

}