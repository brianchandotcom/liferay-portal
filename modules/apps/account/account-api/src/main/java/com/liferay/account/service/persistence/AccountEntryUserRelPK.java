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

package com.liferay.account.service.persistence;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AccountEntryUserRelPK
	implements Comparable<AccountEntryUserRelPK>, Serializable {

	public long accountEntryId;
	public long userId;

	public AccountEntryUserRelPK() {
	}

	public AccountEntryUserRelPK(long accountEntryId, long userId) {
		this.accountEntryId = accountEntryId;
		this.userId = userId;
	}

	public long getAccountEntryId() {
		return accountEntryId;
	}

	public void setAccountEntryId(long accountEntryId) {
		this.accountEntryId = accountEntryId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public int compareTo(AccountEntryUserRelPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (accountEntryId < pk.accountEntryId) {
			value = -1;
		}
		else if (accountEntryId > pk.accountEntryId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (userId < pk.userId) {
			value = -1;
		}
		else if (userId > pk.userId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AccountEntryUserRelPK)) {
			return false;
		}

		AccountEntryUserRelPK pk = (AccountEntryUserRelPK)obj;

		if ((accountEntryId == pk.accountEntryId) && (userId == pk.userId)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = HashUtil.hash(hashCode, accountEntryId);
		hashCode = HashUtil.hash(hashCode, userId);

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(6);

		sb.append("{");

		sb.append("accountEntryId=");

		sb.append(accountEntryId);
		sb.append(", userId=");

		sb.append(userId);

		sb.append("}");

		return sb.toString();
	}

}