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

package com.liferay.account.model.impl;

import com.liferay.account.model.AccountGroupAccountEntryRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AccountGroupAccountEntryRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountGroupAccountEntryRelCacheModel
	implements CacheModel<AccountGroupAccountEntryRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AccountGroupAccountEntryRelCacheModel)) {
			return false;
		}

		AccountGroupAccountEntryRelCacheModel
			accountGroupAccountEntryRelCacheModel =
				(AccountGroupAccountEntryRelCacheModel)object;

		if ((AccountGroupAccountEntryRelId ==
				accountGroupAccountEntryRelCacheModel.
					AccountGroupAccountEntryRelId) &&
			(mvccVersion ==
				accountGroupAccountEntryRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, AccountGroupAccountEntryRelId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", AccountGroupAccountEntryRelId=");
		sb.append(AccountGroupAccountEntryRelId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", accountGroupId=");
		sb.append(accountGroupId);
		sb.append(", accountEntryId=");
		sb.append(accountEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AccountGroupAccountEntryRel toEntityModel() {
		AccountGroupAccountEntryRelImpl accountGroupAccountEntryRelImpl =
			new AccountGroupAccountEntryRelImpl();

		accountGroupAccountEntryRelImpl.setMvccVersion(mvccVersion);
		accountGroupAccountEntryRelImpl.setAccountGroupAccountEntryRelId(
			AccountGroupAccountEntryRelId);
		accountGroupAccountEntryRelImpl.setCompanyId(companyId);
		accountGroupAccountEntryRelImpl.setAccountGroupId(accountGroupId);
		accountGroupAccountEntryRelImpl.setAccountEntryId(accountEntryId);

		accountGroupAccountEntryRelImpl.resetOriginalValues();

		return accountGroupAccountEntryRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		AccountGroupAccountEntryRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		accountGroupId = objectInput.readLong();

		accountEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(AccountGroupAccountEntryRelId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(accountGroupId);

		objectOutput.writeLong(accountEntryId);
	}

	public long mvccVersion;
	public long AccountGroupAccountEntryRelId;
	public long companyId;
	public long accountGroupId;
	public long accountEntryId;

}