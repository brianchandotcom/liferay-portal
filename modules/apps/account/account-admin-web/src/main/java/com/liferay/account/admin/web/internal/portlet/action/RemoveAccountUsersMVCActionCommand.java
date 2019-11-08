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

import com.liferay.account.constants.AccountsPortletKeys;
import com.liferay.account.exception.NoSuchEntryUserRelException;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

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
		"javax.portlet.name=" + AccountsPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"mvc.command.name=/account_admin/remove_account_users"
	},
	service = MVCActionCommand.class
)
public class RemoveAccountUsersMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long accountEntryId = ParamUtil.getLong(
				actionRequest, "accountEntryId");
			long[] accountUserIds = ParamUtil.getLongValues(
				actionRequest, "accountUserIds");

			_accountEntryUserRelLocalService.deleteAccountEntryUserRels(
				accountEntryId, accountUserIds);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryUserRelException) {
				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}

			actionResponse.setRenderParameter("mvcPath", "/view.jsp");
		}
	}

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

}