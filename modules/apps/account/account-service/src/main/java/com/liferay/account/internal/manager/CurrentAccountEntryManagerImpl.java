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
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 * @author Drew Brokke
 */
@Component(immediate = true, service = CurrentAccountEntryManager.class)
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

		List<AccountEntry> userAccountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				userId, AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, null,
				_getAllowedTypes(groupId), WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		AccountEntry accountEntry = _getAccountEntryFromHttpSession(groupId);

		if (_isValid(accountEntry, userAccountEntries)) {
			return accountEntry;
		}

		accountEntry = _getAccountEntryFromPortalPreferences(groupId, userId);

		if (_isValid(accountEntry, userAccountEntries)) {
			_saveInHttpSession(accountEntry.getAccountEntryId(), groupId);

			return accountEntry;
		}

		if (!userAccountEntries.isEmpty()) {
			accountEntry = userAccountEntries.get(0);

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

			_saveInHttpSession(accountEntryId, groupId);

			_saveInPortalPreferences(accountEntryId, groupId, userId);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private AccountEntry _getAccountEntryFromHttpSession(long groupId) {
		HttpSession httpSession = PortalSessionThreadLocal.getHttpSession();

		if (httpSession == null) {
			return null;
		}

		long currentAccountEntryId = GetterUtil.getLong(
			httpSession.getAttribute(_getKey(groupId)));

		return _accountEntryLocalService.fetchAccountEntry(
			currentAccountEntryId);
	}

	private AccountEntry _getAccountEntryFromPortalPreferences(
		long groupId, long userId) {

		PortalPreferences portalPreferences = _getPortalPreferences(userId);

		long accountEntryId = GetterUtil.getLong(
			portalPreferences.getValue(
				AccountEntry.class.getName(), _getKey(groupId)));

		if (accountEntryId > 0) {
			return _accountEntryLocalService.fetchAccountEntry(accountEntryId);
		}

		return null;
	}

	private String[] _getAllowedTypes(long groupId) {
		return _accountEntryGroupSettings.getAllowedTypes(groupId);
	}

	private String _getKey(long groupId) {
		return AccountWebKeys.CURRENT_ACCOUNT_ENTRY_ID + groupId;
	}

	private PortalPreferences _getPortalPreferences(long userId) {
		return _portletPreferencesFactory.getPortalPreferences(userId, true);
	}

	private boolean _isValid(
		AccountEntry accountEntry, List<AccountEntry> userAccountEntries) {

		if ((accountEntry != null) &&
			((accountEntry.getAccountEntryId() ==
				AccountConstants.ACCOUNT_ENTRY_ID_GUEST) ||
			 userAccountEntries.contains(accountEntry))) {

			return true;
		}

		return false;
	}

	private void _saveInHttpSession(long accountEntryId, long groupId) {
		HttpSession httpSession = PortalSessionThreadLocal.getHttpSession();

		if (httpSession == null) {
			return;
		}

		httpSession.setAttribute(_getKey(groupId), accountEntryId);
	}

	private void _saveInPortalPreferences(
		long accountEntryId, long groupId, long userId) {

		PortalPreferences portalPreferences = _getPortalPreferences(userId);

		String key = _getKey(groupId);

		long currentAccountEntryId = GetterUtil.getLong(
			portalPreferences.getValue(
				AccountEntry.class.getName(), key,
				String.valueOf(AccountConstants.ACCOUNT_ENTRY_ID_GUEST)));

		if (currentAccountEntryId == accountEntryId) {
			return;
		}

		portalPreferences.setValue(
			AccountEntry.class.getName(), key, String.valueOf(accountEntryId));

		_portalPreferencesLocalService.updatePreferences(
			userId, PortletKeys.PREFS_OWNER_TYPE_USER, portalPreferences);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CurrentAccountEntryManagerImpl.class);

	@Reference
	private AccountEntryGroupSettings _accountEntryGroupSettings;

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private UserLocalService _userLocalService;

}