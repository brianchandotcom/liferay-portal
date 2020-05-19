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

import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.base.AccountGroupLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the account group local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.account.service.AccountGroupLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountGroup",
	service = AopService.class
)
public class AccountGroupLocalServiceImpl
	extends AccountGroupLocalServiceBaseImpl {

	@Override
	public AccountGroup addAccountGroup(
			long userId, String name, String description)
		throws PortalException {

		long accountGroupId = counterLocalService.increment();

		AccountGroup accountGroup = accountGroupPersistence.create(
			accountGroupId);

		User user = userLocalService.getUser(userId);

		accountGroup.setCompanyId(user.getCompanyId());
		accountGroup.setUserId(user.getUserId());
		accountGroup.setUserName(user.getFullName());

		accountGroup.setName(name);
		accountGroup.setDescription(description);

		return accountGroupPersistence.update(accountGroup);
	}

	@Override
	public AccountGroup deleteAccountGroup(AccountGroup accountGroup) {
		return super.deleteAccountGroup(accountGroup);
	}

	@Override
	public AccountGroup deleteAccountGroup(long accountGroupId)
		throws PortalException {

		return deleteAccountGroup(getAccountGroup(accountGroupId));
	}

	@Override
	public AccountGroup updateAccountGroup(
			long accountGroupId, long userId, String name, String description)
		throws PortalException {

		AccountGroup accountGroup = accountGroupPersistence.fetchByPrimaryKey(
			accountGroupId);

		User user = userLocalService.getUser(userId);

		accountGroup.setCompanyId(user.getCompanyId());

		accountGroup.setName(name);
		accountGroup.setDescription(description);

		return accountGroupPersistence.update(accountGroup);
	}

}