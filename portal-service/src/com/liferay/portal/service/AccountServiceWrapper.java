/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link AccountService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AccountService
 * @generated
 */
public class AccountServiceWrapper implements AccountService,
	ServiceWrapper<AccountService> {
	public AccountServiceWrapper(AccountService accountService) {
		_accountService = accountService;
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public AccountService getWrappedAccountService() {
		return _accountService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedAccountService(AccountService accountService) {
		_accountService = accountService;
	}

	public AccountService getWrappedService() {
		return _accountService;
	}

	public void setWrappedService(AccountService accountService) {
		_accountService = accountService;
	}

	private AccountService _accountService;
}