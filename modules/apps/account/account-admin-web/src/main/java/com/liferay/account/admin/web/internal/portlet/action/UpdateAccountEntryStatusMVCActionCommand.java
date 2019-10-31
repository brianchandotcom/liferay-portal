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
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

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
		"javax.portlet.name=" + AccountsPortletKeys.ACCOUNTS_ADMIN,
		"mvc.command.name=/account_admin/update_account_entry_status"
	},
	service = MVCActionCommand.class
)
public class UpdateAccountEntryStatusMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long[] accountEntryIds = ParamUtil.getLongValues(
			actionRequest, "accountEntryIds");

		if (cmd.equals(Constants.DEACTIVATE)) {
			_accountEntryLocalService.deactivateAccountEntries(accountEntryIds);
		}
		else if (cmd.equals(Constants.RESTORE)) {
			_accountEntryLocalService.activateAccountEntries(accountEntryIds);
		}
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

}