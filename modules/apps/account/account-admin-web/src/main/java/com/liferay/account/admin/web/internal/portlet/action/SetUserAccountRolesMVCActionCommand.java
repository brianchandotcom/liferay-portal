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
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT,
		"mvc.command.name=/account_admin/set_user_account_roles"
	},
	service = MVCActionCommand.class
)
public class SetUserAccountRolesMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long accountEntryId = ParamUtil.getLong(
			actionRequest, "accountEntryId");
		long[] accountRoleIds = ParamUtil.getLongValues(
			actionRequest, "accountRoleIds");
		long accountUserId = ParamUtil.getLong(actionRequest, "accountUserId");

		List<AccountRole> removeAccountRoles = new ArrayList<>();

		List<AccountRole> currentAccountRoles =
			_accountRoleLocalService.getAccountRoles(
				accountEntryId, accountUserId);

		for (AccountRole accountRole : currentAccountRoles) {
			if (!ArrayUtil.contains(
					accountRoleIds, accountRole.getAccountRoleId())) {

				removeAccountRoles.add(accountRole);
			}
		}

		_accountRoleLocalService.associateUser(
			accountEntryId, accountRoleIds, accountUserId);

		for (AccountRole accountRole : removeAccountRoles) {
			_accountRoleLocalService.unassociateUser(
				accountEntryId, accountRole.getAccountRoleId(), accountUserId);
		}
	}

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

}