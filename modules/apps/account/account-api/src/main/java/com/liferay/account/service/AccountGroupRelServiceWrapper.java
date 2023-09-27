/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public com.liferay.account.model.AccountGroupRel deleteAccountGroupRel(
			long accountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountGroupRelService.deleteAccountGroupRel(accountGroupRelId);
	}

	@Override
	public void deleteAccountGroupRels(
			long accountGroupId, String className, long[] classPKs)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountGroupRelService.deleteAccountGroupRels(
			accountGroupId, className, classPKs);
	}

	@Override
	public com.liferay.account.model.AccountGroupRel fetchAccountGroupRel(
			long accountGroupId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountGroupRelService.fetchAccountGroupRel(
			accountGroupId, className, classPK);
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