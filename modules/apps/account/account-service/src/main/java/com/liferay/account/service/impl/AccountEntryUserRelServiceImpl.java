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

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.base.AccountEntryUserRelServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @see    AccountEntryUserRelServiceBaseImpl
 */
@Component(
	property = {
		"json.web.service.context.name=account",
		"json.web.service.context.path=AccountEntryUserRel"
	},
	service = AopService.class
)
public class AccountEntryUserRelServiceImpl
	extends AccountEntryUserRelServiceBaseImpl {

	@Override
	public AccountEntryUserRel addAccountEntryUserRel(
			long accountEntryId, long creatorUserId, String screenName,
			String emailAddress, Locale locale, String firstName,
			String middleName, String lastName, long prefixListTypeId,
			long suffixListTypeId, String jobTitle,
			ServiceContext serviceContext)
		throws PortalException {

		_modelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.MANAGE_USERS);

		return accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntryId, creatorUserId, screenName, emailAddress, locale,
			firstName, middleName, lastName, prefixListTypeId, suffixListTypeId,
			jobTitle, serviceContext);
	}

	@Override
	public AccountEntryUserRel addAccountEntryUserRelByEmailAddress(
			long accountEntryId, String emailAddress, long[] accountRoleIds,
			String userExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		_modelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.MANAGE_USERS);

		return accountEntryUserRelLocalService.
			addAccountEntryUserRelByEmailAddress(
				accountEntryId, emailAddress, accountRoleIds,
				userExternalReferenceCode, serviceContext);
	}

	@Override
	public void addAccountEntryUserRels(
			long accountEntryId, long[] accountUserIds)
		throws PortalException {

		_modelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.MANAGE_USERS);

		accountEntryUserRelLocalService.addAccountEntryUserRels(
			accountEntryId, accountUserIds);
	}

	@Override
	public AccountEntryUserRel addPersonTypeAccountEntryUserRel(
			long accountEntryId, long creatorUserId, String screenName,
			String emailAddress, Locale locale, String firstName,
			String middleName, String lastName, long prefixListTypeId,
			long suffixListTypeId, String jobTitle,
			ServiceContext serviceContext)
		throws PortalException {

		_modelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.MANAGE_USERS);

		return accountEntryUserRelLocalService.addPersonTypeAccountEntryUserRel(
			accountEntryId, creatorUserId, screenName, emailAddress, locale,
			firstName, middleName, lastName, prefixListTypeId, suffixListTypeId,
			jobTitle, serviceContext);
	}

	@Override
	public void deleteAccountEntryUserRelByEmailAddress(
			long accountEntryId, String emailAddress)
		throws PortalException {

		_modelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.MANAGE_USERS);

		accountEntryUserRelLocalService.deleteAccountEntryUserRelByEmailAddress(
			accountEntryId, emailAddress);
	}

	@Override
	public void deleteAccountEntryUserRels(
			long accountEntryId, long[] accountUserIds)
		throws PortalException {

		_modelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.MANAGE_USERS);

		accountEntryUserRelLocalService.deleteAccountEntryUserRels(
			accountEntryId, accountUserIds);
	}

	@Override
	public void setPersonTypeAccountEntryUser(long accountEntryId, long userId)
		throws PortalException {

		_modelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.MANAGE_USERS);

		accountEntryUserRelLocalService.setPersonTypeAccountEntryUser(
			accountEntryId, userId);
	}

	private static volatile ModelResourcePermission<AccountEntry>
		_modelResourcePermission = ModelResourcePermissionFactory.getInstance(
			AccountEntryUserRelServiceImpl.class, "_modelResourcePermission",
			AccountEntry.class);

	@Reference
	private UserLocalService _userLocalService;

}