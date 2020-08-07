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

package com.liferay.account.service.impl;

import com.liferay.account.configuration.AccountEntryEmailDomainsConfiguration;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.AccountEntryTypeException;
import com.liferay.account.exception.DuplicateAccountEntryIdException;
import com.liferay.account.exception.DuplicateAccountEntryUserRelException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.base.AccountEntryUserRelLocalServiceBaseImpl;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.time.Month;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountEntryUserRel",
	service = AopService.class
)
public class AccountEntryUserRelLocalServiceImpl
	extends AccountEntryUserRelLocalServiceBaseImpl {

	@Override
	public AccountEntryUserRel addAccountEntryUserRel(
			long accountEntryId, long accountUserId)
		throws PortalException {

		AccountEntryUserRel accountEntryUserRel =
			accountEntryUserRelPersistence.fetchByAEI_AUI(
				accountEntryId, accountUserId);

		if (accountEntryUserRel != null) {
			throw new DuplicateAccountEntryUserRelException();
		}

		if (accountEntryId != AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT) {
			accountEntryLocalService.getAccountEntry(accountEntryId);
		}

		User user = userLocalService.getUser(accountUserId);

		_validateEmailAddress(
			user.getCompanyId(), accountEntryId, user.getEmailAddress());

		accountEntryUserRel = createAccountEntryUserRel(
			counterLocalService.increment());

		accountEntryUserRel.setAccountEntryId(accountEntryId);
		accountEntryUserRel.setAccountUserId(accountUserId);

		return addAccountEntryUserRel(accountEntryUserRel);
	}

	@Override
	public AccountEntryUserRel addAccountEntryUserRel(
			long accountEntryId, long creatorUserId, String screenName,
			String emailAddress, Locale locale, String firstName,
			String middleName, String lastName, long prefixId, long suffixId)
		throws PortalException {

		long companyId = CompanyThreadLocal.getCompanyId();

		if (accountEntryId != AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT) {
			AccountEntry accountEntry =
				accountEntryLocalService.getAccountEntry(accountEntryId);

			companyId = accountEntry.getCompanyId();
		}

		_validateEmailAddress(companyId, accountEntryId, emailAddress);

		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = false;
		boolean male = true;
		int birthdayMonth = Month.JANUARY.getValue();
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = null;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		ServiceContext serviceContext = null;

		User user = userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, locale, firstName,
			middleName, lastName, prefixId, suffixId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
			roleIds, userGroupIds, sendEmail, serviceContext);

		return accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntryId, user.getUserId());
	}

	@Override
	public void addAccountEntryUserRels(
			long accountEntryId, long[] accountUserIds)
		throws PortalException {

		for (long accountUserId : accountUserIds) {
			addAccountEntryUserRel(accountEntryId, accountUserId);
		}
	}

	@Override
	public AccountEntryUserRel addPersonTypeAccountEntryUserRel(
			long accountEntryId, long creatorUserId, String screenName,
			String emailAddress, Locale locale, String firstName,
			String middleName, String lastName, long prefixId, long suffixId)
		throws PortalException {

		AccountEntry accountEntry = accountEntryLocalService.getAccountEntry(
			accountEntryId);

		if (!Objects.equals(
				AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON,
				accountEntry.getType())) {

			throw new AccountEntryTypeException();
		}

		deleteAccountEntryUserRelsByAccountEntryId(accountEntryId);

		return addAccountEntryUserRel(
			accountEntryId, creatorUserId, screenName, emailAddress, locale,
			firstName, middleName, lastName, prefixId, suffixId);
	}

	@Override
	public void deleteAccountEntryUserRels(
			long accountEntryId, long[] accountUserIds)
		throws PortalException {

		for (long accountUserId : accountUserIds) {
			accountEntryUserRelPersistence.removeByAEI_AUI(
				accountEntryId, accountUserId);
		}
	}

	@Override
	public void deleteAccountEntryUserRelsByAccountEntryId(
		long accountEntryId) {

		for (AccountEntryUserRel accountEntryUserRel :
				getAccountEntryUserRelsByAccountEntryId(accountEntryId)) {

			deleteAccountEntryUserRel(accountEntryUserRel);
		}
	}

	@Override
	public List<AccountEntryUserRel> getAccountEntryUserRelsByAccountEntryId(
		long accountEntryId) {

		return accountEntryUserRelPersistence.findByAEI(accountEntryId);
	}

	@Override
	public List<AccountEntryUserRel> getAccountEntryUserRelsByAccountUserId(
		long accountUserId) {

		return accountEntryUserRelPersistence.findByAUI(accountUserId);
	}

	@Override
	public long getAccountEntryUserRelsCountByAccountEntryId(
		long accountEntryId) {

		return accountEntryUserRelPersistence.countByAEI(accountEntryId);
	}

	@Override
	public boolean hasAccountEntryUserRel(long accountEntryId, long userId) {
		AccountEntryUserRel accountEntryUserRel =
			accountEntryUserRelPersistence.fetchByAEI_AUI(
				accountEntryId, userId);

		if (accountEntryUserRel != null) {
			return true;
		}

		return false;
	}

	@Override
	public void setPersonTypeAccountEntryUser(long accountEntryId, long userId)
		throws PortalException {

		AccountEntry accountEntry = accountEntryLocalService.getAccountEntry(
			accountEntryId);

		if (!Objects.equals(
				AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON,
				accountEntry.getType())) {

			throw new AccountEntryTypeException();
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Updating user for person account entry: " + accountEntryId);
		}

		List<AccountEntryUserRel> removeAccountEntryUserRels = new ArrayList<>(
			getAccountEntryUserRelsByAccountEntryId(accountEntryId));

		boolean currentAccountUser = removeAccountEntryUserRels.removeIf(
			accountEntryUserRel ->
				accountEntryUserRel.getAccountUserId() == userId);

		removeAccountEntryUserRels.forEach(
			accountEntryUserRel -> {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Removing user: " +
							accountEntryUserRel.getAccountUserId());
				}

				deleteAccountEntryUserRel(accountEntryUserRel);
			});

		if ((userId > 0) && !currentAccountUser) {
			if (_log.isDebugEnabled()) {
				_log.debug("Adding user: " + userId);
			}

			addAccountEntryUserRel(accountEntryId, userId);
		}
	}

	@Override
	public void updateAccountEntryUserRels(
			long[] addAccountEntryIds, long[] deleteAccountEntryIds,
			long accountUserId)
		throws PortalException {

		Set<Long> set = SetUtil.intersect(
			addAccountEntryIds, deleteAccountEntryIds);

		if (!SetUtil.isEmpty(set)) {
			throw new DuplicateAccountEntryIdException();
		}

		for (long addAccountEntryId : addAccountEntryIds) {
			if (!hasAccountEntryUserRel(addAccountEntryId, accountUserId)) {
				addAccountEntryUserRel(addAccountEntryId, accountUserId);
			}
		}

		for (long deleteAccountEntryId : deleteAccountEntryIds) {
			if (hasAccountEntryUserRel(deleteAccountEntryId, accountUserId)) {
				accountEntryUserRelPersistence.removeByAEI_AUI(
					deleteAccountEntryId, accountUserId);
			}
		}
	}

	@Reference
	protected AccountEntryLocalService accountEntryLocalService;

	private void _validateEmailAddress(
			long companyId, long accountEntryId, String emailAddress)
		throws PortalException {

		long userId = GuestOrUserUtil.getGuestOrUserId();

		List<AccountEntryUserRel> accountEntryUserRels =
			accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(userId);

		if (ListUtil.isEmpty(accountEntryUserRels)) {
			return;
		}

		emailAddress = StringUtil.toLowerCase(emailAddress.trim());

		int index = emailAddress.indexOf(CharPool.AT);

		if (index == -1) {
			return;
		}

		String domain = emailAddress.substring(index + 1);

		AccountEntryEmailDomainsConfiguration
			accountEntryEmailDomainsConfiguration =
				_configurationProvider.getCompanyConfiguration(
					AccountEntryEmailDomainsConfiguration.class, companyId);

		String[] blockedDomains = StringUtil.split(
			accountEntryEmailDomainsConfiguration.blockedEmailDomains(),
			StringPool.RETURN_NEW_LINE);

		if (ArrayUtil.contains(blockedDomains, domain)) {
			throw new UserEmailAddressException.MustNotUseBlockedDomain(
				emailAddress,
				StringUtil.merge(blockedDomains, StringPool.COMMA_AND_SPACE));
		}

		if (!accountEntryEmailDomainsConfiguration.
				enableEmailDomainValidation()) {

			return;
		}

		AccountEntry accountEntry = accountEntryLocalService.getAccountEntry(
			accountEntryId);

		String[] domains = StringUtil.split(accountEntry.getDomains());

		if (!ArrayUtil.contains(domains, domain)) {
			throw new UserEmailAddressException.MustHaveValidDomain(
				emailAddress, accountEntry.getDomains());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryUserRelLocalServiceImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}