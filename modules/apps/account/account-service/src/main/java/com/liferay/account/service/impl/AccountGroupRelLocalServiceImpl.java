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
import com.liferay.account.exception.DuplicateAccountGroupRelException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.base.AccountGroupRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountGroupRel",
	service = AopService.class
)
public class AccountGroupRelLocalServiceImpl
	extends AccountGroupRelLocalServiceBaseImpl {

	@Override
	public AccountGroupRel addAccountGroupRel(
			long accountGroupId, String className, long classPK)
		throws PortalException {

		long classNameId = _classNameLocalService.getClassNameId(className);

		AccountGroupRel accountGroupRel =
			accountGroupRelPersistence.fetchByA_C_C(
				accountGroupId, classNameId, classPK);

		if (accountGroupRel != null) {
			throw new DuplicateAccountGroupRelException();
		}

		if (Objects.equals(AccountEntry.class.getName(), className) &&
			(classPK != AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT) &&
			(classPK != AccountConstants.ACCOUNT_ENTRY_ID_GUEST)) {

			_accountEntryLocalService.getAccountEntry(classPK);
		}

		accountGroupRel = createAccountGroupRel(
			counterLocalService.increment());

		AccountGroup accountGroup = _accountGroupLocalService.getAccountGroup(
			accountGroupId);

		User user = GuestOrUserUtil.getGuestOrUser(accountGroup.getCompanyId());

		accountGroupRel.setCompanyId(user.getCompanyId());
		accountGroupRel.setUserId(user.getUserId());
		accountGroupRel.setUserName(user.getFullName());

		accountGroupRel.setCreateDate(new Date());
		accountGroupRel.setModifiedDate(new Date());
		accountGroupRel.setAccountGroupId(accountGroupId);
		accountGroupRel.setClassNameId(classNameId);
		accountGroupRel.setClassPK(classPK);

		return addAccountGroupRel(accountGroupRel);
	}

	@Override
	public void addAccountGroupRels(
			long accountGroupId, String className, long[] classPKs)
		throws PortalException {

		for (long classPK : classPKs) {
			addAccountGroupRel(accountGroupId, className, classPK);
		}
	}

	@Override
	public void deleteAccountGroupRels(
			long accountGroupId, String className, long[] classPKs)
		throws PortalException {

		for (long classPK : classPKs) {
			accountGroupRelPersistence.removeByA_C_C(
				accountGroupId,
				_classNameLocalService.getClassNameId(className), classPK);
		}
	}

	@Override
	public void deleteAccountGroupRels(String className, long[] classPKs) {
		for (long classPK : classPKs) {
			accountGroupRelPersistence.removeByC_C(
				_classNameLocalService.getClassNameId(className), classPK);
		}
	}

	@Override
	public void deleteAccountGroupRelsByAccountGroupId(long accountGroupId) {
		accountGroupRelPersistence.removeByAccountGroupId(accountGroupId);
	}

	@Override
	public AccountGroupRel fetchAccountGroupRel(
		long accountGroupId, String className, long classPK) {

		return accountGroupRelPersistence.fetchByA_C_C(
			accountGroupId, _classNameLocalService.getClassNameId(className),
			classPK);
	}

	@Override
	public List<AccountGroupRel> getAccountGroupRels(
		String className, long classPK) {

		return accountGroupRelPersistence.findByC_C(
			_classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public List<AccountGroupRel> getAccountGroupRels(
		String className, long classPK, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return accountGroupRelPersistence.findByC_C(
			_classNameLocalService.getClassNameId(className), classPK, start,
			end, orderByComparator);
	}

	@Override
	public List<AccountGroupRel> getAccountGroupRelsByAccountGroupId(
		long accountGroupId) {

		return accountGroupRelPersistence.findByAccountGroupId(accountGroupId);
	}

	@Override
	public List<AccountGroupRel> getAccountGroupRelsByAccountGroupId(
		long accountGroupId, int start, int end,
		OrderByComparator<AccountGroupRel> orderByComparator) {

		return accountGroupRelPersistence.findByAccountGroupId(
			accountGroupId, start, end, orderByComparator);
	}

	@Override
	public int getAccountGroupRelsCount(String className, long classPK) {
		return accountGroupRelPersistence.countByC_C(
			_classNameLocalService.getClassNameId(className), classPK);
	}

	@Override
	public long getAccountGroupRelsCountByAccountGroupId(long accountGroupId) {
		return accountGroupRelPersistence.countByAccountGroupId(accountGroupId);
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}