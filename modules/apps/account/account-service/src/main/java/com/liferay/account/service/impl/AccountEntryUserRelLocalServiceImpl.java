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

import com.liferay.account.exception.DuplicateAccountEntryUserRelException;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.base.AccountEntryUserRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

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

	/**
	 * Creates an AccountEntryUserRel and adds it to the database. An
	 * AccountEntryUserRel is essentially an "AccountEntry membership".
	 *
	 * @param accountEntryId the primary key of the AccountEntry
	 * @param accountUserId the primary key of the User
	 * @return the AccountEntryUserRel
	 * @review
	 */
	@Override
	public AccountEntryUserRel addAccountEntryUserRel(
			long accountEntryId, long accountUserId)
		throws PortalException {

		AccountEntryUserRel existingAccountEntryUserRel =
			accountEntryUserRelPersistence.fetchByA_A(
				accountEntryId, accountUserId);

		if (existingAccountEntryUserRel != null) {
			throw new DuplicateAccountEntryUserRelException();
		}

		accountEntryLocalService.getAccountEntry(accountEntryId);
		userLocalService.getUser(accountUserId);

		long accountEntryUserRelId = counterLocalService.increment();

		AccountEntryUserRel accountEntryUserRel = createAccountEntryUserRel(
			accountEntryUserRelId);

		accountEntryUserRel.setAccountEntryUserRelId(accountEntryUserRelId);
		accountEntryUserRel.setAccountEntryId(accountEntryId);
		accountEntryUserRel.setAccountUserId(accountUserId);

		return addAccountEntryUserRel(accountEntryUserRel);
	}

	@Reference
	protected AccountEntryLocalService accountEntryLocalService;

}