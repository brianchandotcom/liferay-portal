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

package com.liferay.commerce.account.admin.web.internal.portlet.action;

import com.liferay.commerce.account.constants.CommerceAccountPortletKeys;
import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.CommerceAccountUserRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserGroupRoleService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAccountPortletKeys.COMMERCE_ACCOUNT_ADMIN,
		"mvc.command.name=editCommerceAccountUserRel"
	},
	service = MVCActionCommand.class
)
public class EditCommerceAccountUserRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommerceAccountUserRel(ActionRequest actionRequest)
		throws Exception {

		long[] addUserIds;

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		long commerceAccountUserId = ParamUtil.getLong(
			actionRequest, "commerceAccountUserId");

		if (commerceAccountUserId > 0) {
			addUserIds = new long[] {commerceAccountUserId};
		}
		else {
			addUserIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "userIds"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAccountUserRel.class.getName(), actionRequest);

		_commerceAccountUserRelService.addCommerceAccountUserRels(
			commerceAccountId, addUserIds, null, null, serviceContext);
	}

	protected void deleteCommerceAccountUserRels(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceAccountUserRelIds = null;

		long commerceAccountId = ParamUtil.getLong(
			actionRequest, "commerceAccountId");

		long commerceAccountUserId = ParamUtil.getLong(
			actionRequest, "commerceAccountUserId");

		if (commerceAccountUserId > 0) {
			deleteCommerceAccountUserRelIds = new long[] {
				commerceAccountUserId
			};
		}
		else {
			deleteCommerceAccountUserRelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceAccountUserRelIds"),
				0L);
		}

		_commerceAccountUserRelService.deleteCommerceAccountUserRels(
			commerceAccountId, deleteCommerceAccountUserRelIds);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_MULTIPLE)) {

				addCommerceAccountUserRel(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommerceUserRoles(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceAccountUserRels(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchAccountException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				_log.error(e, e);
			}
		}
	}

	protected void updateCommerceUserRoles(ActionRequest actionRequest)
		throws Exception {

		long commerceAccountGroupId = ParamUtil.getLong(
			actionRequest, "commerceAccountGroupId");

		long commerceAccountUserId = ParamUtil.getLong(
			actionRequest, "commerceAccountUserId");

		long[] roleIds = ParamUtil.getLongValues(actionRequest, "roleIds");
		long[] originalRoleIds = ParamUtil.getLongValues(
			actionRequest, "originalRoleIds");

		_userGroupRoleService.updateUserGroupRoles(
			commerceAccountUserId, commerceAccountGroupId, roleIds,
			originalRoleIds);

		_userGroupRoleService.addUserGroupRoles(
			commerceAccountUserId, commerceAccountGroupId, roleIds);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceAccountUserRelMVCActionCommand.class);

	@Reference
	private CommerceAccountUserRelService _commerceAccountUserRelService;

	@Reference
	private UserGroupRoleService _userGroupRoleService;

}