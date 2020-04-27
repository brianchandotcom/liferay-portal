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
 * Provides a wrapper for {@link AccountEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryService
 * @generated
 */
public class AccountEntryServiceWrapper
	implements AccountEntryService, ServiceWrapper<AccountEntryService> {

	public AccountEntryServiceWrapper(AccountEntryService accountEntryService) {
		_accountEntryService = accountEntryService;
	}

	@Override
	public com.liferay.account.model.AccountEntry addAccountEntry(
			long userId, long parentAccountEntryId, String name,
			String description, String[] domains, byte[] logoBytes, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.addAccountEntry(
			userId, parentAccountEntryId, name, description, domains, logoBytes,
			status);
	}

	@Override
	public java.util.List<com.liferay.account.model.AccountEntry>
			getAccountEntries(
				long companyId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.account.model.AccountEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _accountEntryService.getAccountEntries(
			companyId, status, start, end, obc);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _accountEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.account.model.AccountEntry> search(
			String keywords, java.util.LinkedHashMap<String, Object> params,
			int cur, int delta, String orderByField, boolean reverse) {

		return _accountEntryService.search(
			keywords, params, cur, delta, orderByField, reverse);
	}

	@Override
	public AccountEntryService getWrappedService() {
		return _accountEntryService;
	}

	@Override
	public void setWrappedService(AccountEntryService accountEntryService) {
		_accountEntryService = accountEntryService;
	}

	private AccountEntryService _accountEntryService;

}