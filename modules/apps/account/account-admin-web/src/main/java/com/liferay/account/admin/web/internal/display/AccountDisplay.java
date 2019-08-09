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

package com.liferay.account.admin.web.internal.display;

import com.liferay.petra.string.StringPool;

/**
 * @author Pei-Jung Lan
 */
public class AccountDisplay {

	public long getAccountId() {
		return _accountId;
	}

	public String getAccountOwner() {
		return _accountOwner;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public String getParentAccountName() {
		return _parentAccountName;
	}

	public String getStatusLabel() {
		return _statusLabel;
	}

	public String getWebsite() {
		return _website;
	}

	public AccountDisplay setAccountId(long accountId) {
		_accountId = accountId;

		return this;
	}

	public AccountDisplay setAccountOwner(String accountOwner) {
		_accountOwner = accountOwner;

		return this;
	}

	public AccountDisplay setDescription(String description) {
		_description = description;

		return this;
	}

	public AccountDisplay setName(String name) {
		_name = name;

		return this;
	}

	public AccountDisplay setParentAccountName(String parentAccountName) {
		_parentAccountName = parentAccountName;

		return this;
	}

	public AccountDisplay setStatusLabel(String statusLabel) {
		_statusLabel = statusLabel;

		return this;
	}

	public AccountDisplay setWebsite(String website) {
		_website = website;

		return this;
	}

	private long _accountId;
	private String _accountOwner = StringPool.BLANK;
	private String _description = StringPool.BLANK;
	private String _name = StringPool.BLANK;
	private String _parentAccountName = StringPool.BLANK;
	private String _statusLabel = StringPool.BLANK;
	private String _website = StringPool.BLANK;

}