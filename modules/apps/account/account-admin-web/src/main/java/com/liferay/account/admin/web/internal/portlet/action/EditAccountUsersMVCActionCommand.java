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

package com.liferay.account.admin.web.internal.portlet.action;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Albert Lee
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_USERS_ADMIN,
		"mvc.command.name=/account_admin/edit_account_users"
	},
	service = MVCActionCommand.class
)
public class EditAccountUsersMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteUsers(ActionRequest actionRequest) throws Exception {
		long[] accountUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "accountUserIds"), 0L);

		for (long accountUserId : accountUserIds) {
			_userService.deleteUser(accountUserId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DEACTIVATE) ||
				cmd.equals(Constants.RESTORE)) {

				updateAccountUsersStatus(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteUsers(actionRequest);
			}
		}
		catch (Exception e) {
			String mvcPath = "/account_users_admin/view.jsp";

			if (e instanceof NoSuchUserException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				mvcPath = "/account_users_admin/error.jsp";
			}
			else {
				throw e;
			}

			actionResponse.setRenderParameter("mvcPath", mvcPath);
		}
	}

	protected void updateAccountUsersStatus(ActionRequest actionRequest)
		throws Exception {

		long[] accountUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "accountUserIds"), 0L);

		for (long accountUserId : accountUserIds) {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			int status = WorkflowConstants.STATUS_APPROVED;

			if (cmd.equals(Constants.DEACTIVATE)) {
				status = WorkflowConstants.STATUS_INACTIVE;
			}

			_userService.updateStatus(
				accountUserId, status,
				ServiceContextFactory.getInstance(
					User.class.getName(), actionRequest));
		}
	}

	@Reference
	private UserService _userService;

}