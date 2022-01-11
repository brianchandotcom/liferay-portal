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

package com.liferay.account.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AccountGroupService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupService
 * @generated
 */
public class AccountGroupServiceWrapper
	implements AccountGroupService, ServiceWrapper<AccountGroupService> {

	public AccountGroupServiceWrapper() {
		this(null);
	}

	public AccountGroupServiceWrapper(AccountGroupService accountGroupService) {
		_accountGroupService = accountGroupService;
	}

	@Override
	public com.liferay.account.model.AccountGroup addAccountGroup(
			long userId, String description, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountGroupService.addAccountGroup(userId, description, name);
	}

	@Override
	public com.liferay.account.model.AccountGroup deleteAccountGroup(
			long accountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountGroupService.deleteAccountGroup(accountGroupId);
	}

	@Override
	public void deleteAccountGroups(long[] accountGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountGroupService.deleteAccountGroups(accountGroupIds);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _accountGroupService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.account.model.AccountGroup> searchAccountGroups(
				long companyId, String keywords, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.account.model.AccountGroup> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _accountGroupService.searchAccountGroups(
			companyId, keywords, start, end, orderByComparator);
	}

	@Override
	public com.liferay.account.model.AccountGroup updateAccountGroup(
			long accountGroupId, String description, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountGroupService.updateAccountGroup(
			accountGroupId, description, name);
	}

	@Override
	public AccountGroupService getWrappedService() {
		return _accountGroupService;
	}

	@Override
	public void setWrappedService(AccountGroupService accountGroupService) {
		_accountGroupService = accountGroupService;
	}

	private AccountGroupService _accountGroupService;

}