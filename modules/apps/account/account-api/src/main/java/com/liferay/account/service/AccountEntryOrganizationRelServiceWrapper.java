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
 * Provides a wrapper for {@link AccountEntryOrganizationRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryOrganizationRelService
 * @generated
 */
public class AccountEntryOrganizationRelServiceWrapper
	implements AccountEntryOrganizationRelService,
			   ServiceWrapper<AccountEntryOrganizationRelService> {

	public AccountEntryOrganizationRelServiceWrapper() {
		this(null);
	}

	public AccountEntryOrganizationRelServiceWrapper(
		AccountEntryOrganizationRelService accountEntryOrganizationRelService) {

		_accountEntryOrganizationRelService =
			accountEntryOrganizationRelService;
	}

	@Override
	public com.liferay.account.model.AccountEntryOrganizationRel
			addAccountEntryOrganizationRel(
				long accountEntryId, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryOrganizationRelService.
			addAccountEntryOrganizationRel(accountEntryId, organizationId);
	}

	@Override
	public void addAccountEntryOrganizationRels(
			long accountEntryId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryOrganizationRelService.addAccountEntryOrganizationRels(
			accountEntryId, organizationIds);
	}

	@Override
	public void deleteAccountEntryOrganizationRel(
			long accountEntryId, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryOrganizationRelService.deleteAccountEntryOrganizationRel(
			accountEntryId, organizationId);
	}

	@Override
	public void deleteAccountEntryOrganizationRels(
			long accountEntryId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_accountEntryOrganizationRelService.deleteAccountEntryOrganizationRels(
			accountEntryId, organizationIds);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _accountEntryOrganizationRelService.getOSGiServiceIdentifier();
	}

	@Override
	public AccountEntryOrganizationRelService getWrappedService() {
		return _accountEntryOrganizationRelService;
	}

	@Override
	public void setWrappedService(
		AccountEntryOrganizationRelService accountEntryOrganizationRelService) {

		_accountEntryOrganizationRelService =
			accountEntryOrganizationRelService;
	}

	private AccountEntryOrganizationRelService
		_accountEntryOrganizationRelService;

}