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
 * Provides a wrapper for {@link AccountEntryUserRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryUserRelService
 * @generated
 */
public class AccountEntryUserRelServiceWrapper
	implements AccountEntryUserRelService,
			   ServiceWrapper<AccountEntryUserRelService> {

	public AccountEntryUserRelServiceWrapper(
		AccountEntryUserRelService accountEntryUserRelService) {

		_accountEntryUserRelService = accountEntryUserRelService;
	}

	@Override
	public com.liferay.account.model.AccountEntryUserRel addAccountEntryUserRel(
			long accountEntryId, long creatorUserId, String screenName,
			String emailAddress, java.util.Locale locale, String firstName,
			String middleName, String lastName, long prefixId, long suffixId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryUserRelService.addAccountEntryUserRel(
			accountEntryId, creatorUserId, screenName, emailAddress, locale,
			firstName, middleName, lastName, prefixId, suffixId);
	}

	@Override
	public com.liferay.account.model.AccountEntryUserRel
			addAccountEntryUserRelByEmailAddress(
				long accountEntryId, String emailAddress, long[] accountRoleIds,
				String userExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryUserRelService.addAccountEntryUserRelByEmailAddress(
			accountEntryId, emailAddress, accountRoleIds,
			userExternalReferenceCode, serviceContext);
	}

	@Override
	public void deleteAccountEntryUserRelByEmailAddress(
			long accountEntryId, String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryUserRelService.deleteAccountEntryUserRelByEmailAddress(
			accountEntryId, emailAddress);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _accountEntryUserRelService.getOSGiServiceIdentifier();
	}

	@Override
	public AccountEntryUserRelService getWrappedService() {
		return _accountEntryUserRelService;
	}

	@Override
	public void setWrappedService(
		AccountEntryUserRelService accountEntryUserRelService) {

		_accountEntryUserRelService = accountEntryUserRelService;
	}

	private AccountEntryUserRelService _accountEntryUserRelService;

}