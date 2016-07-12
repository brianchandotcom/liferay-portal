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

package com.liferay.portal.security.sso.ntlm.internal;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Marcellus Tavares
 */
public class NtlmServiceAccount {

	public NtlmServiceAccount(String account, String password) {
		setAccount(account);
		setPassword(password);
	}

	public String getAccount() {
		return _account;
	}

	public String getAccountName() {
		return _accountName;
	}

	public String getComputerName() {
		return _computerName;
	}

	public String getPassword() {
		return _password;
	}

	public void setAccount(String account) {
		_account = account;

		_accountName = _account.substring(0, _account.indexOf(CharPool.AT));
		_computerName = _account.substring(
			0, _account.indexOf(StringPool.DOLLAR));
	}

	public void setPassword(String password) {
		_password = password;
	}

	private String _account;
	private String _accountName;
	private String _computerName;
	private String _password;

}