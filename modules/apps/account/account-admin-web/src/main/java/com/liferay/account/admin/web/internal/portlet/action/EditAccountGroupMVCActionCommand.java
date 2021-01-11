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
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

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
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_GROUPS_ADMIN,
		"mvc.command.name=/account_admin/edit_account_group"
	},
	service = MVCActionCommand.class
)
public class EditAccountGroupMVCActionCommand extends BaseMVCActionCommand {

	protected AccountGroup addAccountGroup(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String description = ParamUtil.getString(actionRequest, "description");
		String name = ParamUtil.getString(actionRequest, "name");

		return _accountGroupLocalService.addAccountGroup(
			themeDisplay.getUserId(), description, name);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (cmd.equals(Constants.ADD)) {
			AccountGroup accountGroup = addAccountGroup(actionRequest);

			redirect = _http.setParameter(
				redirect, actionResponse.getNamespace() + "accountGroupId",
				accountGroup.getAccountGroupId());
		}
		else if (cmd.equals(Constants.UPDATE)) {
			updateAccountGroup(actionRequest);
		}

		if (Validator.isNotNull(redirect)) {
			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	protected void updateAccountGroup(ActionRequest actionRequest)
		throws Exception {

		long accountGroupId = ParamUtil.getLong(
			actionRequest, "accountGroupId");

		String description = ParamUtil.getString(actionRequest, "description");
		String name = ParamUtil.getString(actionRequest, "name");

		_accountGroupLocalService.updateAccountGroup(
			accountGroupId, description, name);
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private Http _http;

}