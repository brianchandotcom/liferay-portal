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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.DuplicateAccountEntryIdException;
import com.liferay.account.exception.DuplicateAccountEntryUserRelException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.base.AccountEntryUserRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.SetUtil;

import java.time.Month;

import java.util.List;
import java.util.Locale;
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

		userLocalService.getUser(accountUserId);

		accountEntryUserRel = createAccountEntryUserRel(
			counterLocalService.increment());

		accountEntryUserRel.setAccountEntryId(accountEntryId);
		accountEntryUserRel.setAccountUserId(accountUserId);

		accountEntryUserRel = addAccountEntryUserRel(accountEntryUserRel);

		return accountEntryUserRel;
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

		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = false;
		long facebookId = 0;
		String openId = null;
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
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

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
	public void deleteAccountEntryUserRels(
			long accountEntryId, long[] accountUserIds)
		throws PortalException {

		for (long accountUserId : accountUserIds) {
			accountEntryUserRelPersistence.removeByAEI_AUI(
				accountEntryId, accountUserId);
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

}