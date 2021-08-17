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

package com.liferay.account.internal.manager;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountWebKeys;
import com.liferay.account.manager.CurrentAccountEntryManager;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = CurrentAccountEntryManager.class)
public class CurrentAccountEntryManagerImpl
	implements CurrentAccountEntryManager {

	public AccountEntry getCurrentAccountEntry(long groupId, long userId)
		throws PortalException {

		AccountEntry accountEntry = null;

		HttpSession httpSession = PortalSessionThreadLocal.getHttpSession();

		long currentAccountEntryId = GetterUtil.getLong(
			httpSession.getAttribute(
				AccountWebKeys.CURRENT_ACCOUNT_ENTRY_ID + groupId));

		if (currentAccountEntryId > 0) {
			accountEntry = _accountEntryLocalService.fetchAccountEntry(
				currentAccountEntryId);
		}

		if ((accountEntry == null) ||
			!Objects.equals(
				WorkflowConstants.STATUS_APPROVED, accountEntry.getStatus())) {

			accountEntry = _getAccountEntry(userId);

			if (accountEntry == null) {
				setCurrentAccountEntry(-1, groupId, userId);
			}
			else {
				setCurrentAccountEntry(
					accountEntry.getAccountEntryId(), groupId, userId);
			}
		}

		return accountEntry;
	}

	public void setCurrentAccountEntry(
		long accountEntryId, long groupId, long userId) {

		HttpSession httpSession = PortalSessionThreadLocal.getHttpSession();

		httpSession.setAttribute(
			AccountWebKeys.CURRENT_ACCOUNT_ENTRY_ID + groupId, accountEntryId);
	}

	private AccountEntry _getAccountEntry(long userId) throws PortalException {
		User user = _userLocalService.fetchUser(userId);

		if ((user == null) || user.isDefaultUser()) {
			return _accountEntryLocalService.getGuestAccountEntry(
				CompanyThreadLocal.getCompanyId());
		}

		List<AccountEntry> accountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				user.getUserId(),
				AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, null,
				new String[] {
					AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
				},
				0, 1);

		if (accountEntries.size() == 1) {
			return accountEntries.get(0);
		}

		return null;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}