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
 * Provides a wrapper for {@link AccountGroupRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupRelService
 * @generated
 */
public class AccountGroupRelServiceWrapper
	implements AccountGroupRelService, ServiceWrapper<AccountGroupRelService> {

	public AccountGroupRelServiceWrapper() {
		this(null);
	}

	public AccountGroupRelServiceWrapper(
		AccountGroupRelService accountGroupRelService) {

		_accountGroupRelService = accountGroupRelService;
	}

	@Override
	public com.liferay.account.model.AccountGroupRel addAccountGroupRel(
			long accountGroupId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountGroupRelService.addAccountGroupRel(
			accountGroupId, className, classPK);
	}

	@Override
	public void addAccountGroupRels(
			long accountGroupId, String className, long[] classPKs)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountGroupRelService.addAccountGroupRels(
			accountGroupId, className, classPKs);
	}

	@Override
	public void deleteAccountGroupRels(
			long accountGroupId, String className, long[] classPKs)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountGroupRelService.deleteAccountGroupRels(
			accountGroupId, className, classPKs);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _accountGroupRelService.getOSGiServiceIdentifier();
	}

	@Override
	public AccountGroupRelService getWrappedService() {
		return _accountGroupRelService;
	}

	@Override
	public void setWrappedService(
		AccountGroupRelService accountGroupRelService) {

		_accountGroupRelService = accountGroupRelService;
	}

	private AccountGroupRelService _accountGroupRelService;

}