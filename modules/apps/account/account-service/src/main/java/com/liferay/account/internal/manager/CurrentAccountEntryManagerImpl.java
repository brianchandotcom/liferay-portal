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
import com.liferay.account.exception.AccountEntryTypeException;
import com.liferay.account.manager.CurrentAccountEntryManager;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.settings.AccountEntryGroupSettings;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Comparator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 * @author Drew Brokke
 */
@Component(service = CurrentAccountEntryManager.class)
public class CurrentAccountEntryManagerImpl
	implements CurrentAccountEntryManager {

	@Override
	public AccountEntry getCurrentAccountEntry(long groupId, long userId)
		throws PortalException {

		AccountEntry guestAccountEntry =
			_accountEntryLocalService.getGuestAccountEntry(
				CompanyThreadLocal.getCompanyId());

		if (userId <= UserConstants.USER_ID_DEFAULT) {
			return guestAccountEntry;
		}

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			return guestAccountEntry;
		}

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		AccountEntry accountEntry =
			_currentAccountEntryManagerStore.getAccountEntryFromHttpSession(
				groupId);

		String[] allowedTypes = _getAllowedTypes(groupId);

		if (_isValid(accountEntry, allowedTypes, permissionChecker)) {
			return accountEntry;
		}

		accountEntry =
			_currentAccountEntryManagerStore.
				getAccountEntryFromPortalPreferences(groupId, userId);

		if (_isValid(accountEntry, allowedTypes, permissionChecker)) {
			_currentAccountEntryManagerStore.saveInHttpSession(
				accountEntry.getAccountEntryId(), groupId);

			return accountEntry;
		}

		accountEntry = _getDefaultAccountEntry(
			allowedTypes, permissionChecker, userId);

		if (accountEntry != null) {
			setCurrentAccountEntry(
				accountEntry.getAccountEntryId(), groupId, userId);

			return accountEntry;
		}

		setCurrentAccountEntry(
			AccountConstants.ACCOUNT_ENTRY_ID_GUEST, groupId, userId);

		return null;
	}

	@Override
	public void setCurrentAccountEntry(
		long accountEntryId, long groupId, long userId) {

		try {
			AccountEntry accountEntry =
				_accountEntryLocalService.fetchAccountEntry(accountEntryId);

			if ((accountEntry != null) &&
				!ArrayUtil.contains(
					_getAllowedTypes(groupId), accountEntry.getType())) {

				throw new AccountEntryTypeException(
					"Cannot set a current account entry of a disallowed " +
						"type: " + accountEntry.getType());
			}

			_currentAccountEntryManagerStore.setCurrentAccountEntry(
				accountEntryId, groupId, userId);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private String[] _getAllowedTypes(long groupId) {
		return _accountEntryGroupSettings.getAllowedTypes(groupId);
	}

	private AccountEntry _getDefaultAccountEntry(
			String[] allowedTypes, PermissionChecker permissionChecker,
			long userId)
		throws PortalException {

		List<AccountEntry> accountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				userId, AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, null,
				allowedTypes, WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		accountEntries.sort(Comparator.comparing(AccountEntry::getName));

		for (AccountEntry accountEntry : accountEntries) {
			if (_accountEntryModelResourcePermission.contains(
					permissionChecker, accountEntry.getAccountEntryId(),
					ActionKeys.VIEW)) {

				return accountEntry;
			}
		}

		return null;
	}

	private boolean _isValid(
		AccountEntry accountEntry, String[] allowedTypes,
		PermissionChecker permissionChecker) {

		try {
			if ((accountEntry != null) && accountEntry.isApproved() &&
				((accountEntry.getAccountEntryId() ==
					AccountConstants.ACCOUNT_ENTRY_ID_GUEST) ||
				 ArrayUtil.contains(allowedTypes, accountEntry.getType())) &&
				_accountEntryModelResourcePermission.contains(
					permissionChecker, accountEntry.getAccountEntryId(),
					ActionKeys.VIEW)) {

				return true;
			}
		}
		catch (PortalException portalException) {
			if (_log.isInfoEnabled()) {
				_log.info(portalException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CurrentAccountEntryManagerImpl.class);

	@Reference
	private AccountEntryGroupSettings _accountEntryGroupSettings;

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private CurrentAccountEntryManagerStore _currentAccountEntryManagerStore;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private UserLocalService _userLocalService;

}