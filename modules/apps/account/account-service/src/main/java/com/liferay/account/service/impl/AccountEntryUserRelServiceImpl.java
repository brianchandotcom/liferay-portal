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

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

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
			String middleName, String lastName, long prefixId, long suffixId)
		throws PortalException {

		_modelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.MANAGE_USERS);

		return accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntryId, creatorUserId, screenName, emailAddress, locale,
			firstName, middleName, lastName, prefixId, suffixId);
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
	public void deleteAccountEntryUserRelByEmailAddress(
			long accountEntryId, String emailAddress)
		throws PortalException {

		_modelResourcePermission.check(
			getPermissionChecker(), accountEntryId, ActionKeys.MANAGE_USERS);

		accountEntryUserRelLocalService.deleteAccountEntryUserRelByEmailAddress(
			accountEntryId, emailAddress);
	}

	private static volatile ModelResourcePermission<AccountEntry>
		_modelResourcePermission = ModelResourcePermissionFactory.getInstance(
			AccountEntryUserRelServiceImpl.class, "_modelResourcePermission",
			AccountEntry.class);

}