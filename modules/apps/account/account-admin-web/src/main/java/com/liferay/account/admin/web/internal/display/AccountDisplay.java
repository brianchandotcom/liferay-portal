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
		return _builder._accountId;
	}

	public String getAccountOwner() {
		return _builder._accountOwner;
	}

	public String getDescription() {
		return _builder._description;
	}

	public String getName() {
		return _builder._name;
	}

	public String getParentAccountName() {
		return _builder._parentAccountName;
	}

	public String getStatusLabel() {
		return _builder._statusLabel;
	}

	public String getWebsite() {
		return _builder._website;
	}

	public static class Builder {

		public Builder accountId(long accountId) {
			_accountId = accountId;

			return this;
		}

		public Builder accountOwner(String accountOwner) {
			_accountOwner = accountOwner;

			return this;
		}

		public AccountDisplay build() {
			return new AccountDisplay(this);
		}

		public Builder description(String description) {
			_description = description;

			return this;
		}

		public Builder name(String name) {
			_name = name;

			return this;
		}

		public Builder parentAccountName(String parentAccountName) {
			_parentAccountName = parentAccountName;

			return this;
		}

		public Builder statusLabel(String statusLabel) {
			_statusLabel = statusLabel;

			return this;
		}

		public Builder website(String website) {
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

	private AccountDisplay(Builder builder) {
		_builder = builder;
	}

	private final Builder _builder;

}