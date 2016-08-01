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

package com.liferay.my.account.web.internal.portlet.action;

import com.liferay.my.account.web.internal.constants.MyAccountPortletKeys;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MyAccountPortletKeys.MY_ACCOUNT,
		"mvc.command.name=/users_admin/edit_user"
	},
	service = MVCActionCommand.class
)
public class EditUserMVCActionCommand
	extends
		com.liferay.users.admin.web.portlet.action.EditUserMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		if (redirectToLogin(actionRequest, actionResponse)) {
			return;
		}

		super.doProcessAction(actionRequest, actionResponse);
	}

	@Override
	protected Object[] updateUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String currentPassword = actionRequest.getParameter("password0");
		String newPassword = actionRequest.getParameter("password1");

		User user = PortalUtil.getSelectedUser(actionRequest);

		if (Validator.isNotNull(currentPassword)) {
			if (Validator.isNull(newPassword)) {
				throw new UserPasswordException.MustNotBeNull(user.getUserId());
			}

			Company company = PortalUtil.getCompany(actionRequest);

			String authType = company.getAuthType();

			Map<String, String[]> headerMap = new HashMap<>();
			Map<String, String[]> parameterMap = new HashMap<>();
			Map<String, Object> resultsMap = new HashMap<>();

			int authResult = Authenticator.FAILURE;

			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				authResult = userLocalService.authenticateByEmailAddress(
					company.getCompanyId(), user.getEmailAddress(),
					currentPassword, headerMap, parameterMap, resultsMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				authResult = userLocalService.authenticateByUserId(
					company.getCompanyId(), user.getUserId(), currentPassword,
					headerMap, parameterMap, resultsMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				authResult = userLocalService.authenticateByScreenName(
					company.getCompanyId(), user.getScreenName(),
					currentPassword, headerMap, parameterMap, resultsMap);
			}

			if (authResult == Authenticator.FAILURE) {
				throw new UserPasswordException.MustMatchCurrentPassword(
					user.getUserId());
			}
		}
		else if (Validator.isNotNull(newPassword)) {
			throw new UserPasswordException.MustNotBeNull(user.getUserId());
		}

		return super.updateUser(actionRequest, actionResponse);
	}

}