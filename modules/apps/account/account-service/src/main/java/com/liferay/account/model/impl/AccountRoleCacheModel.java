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

import com.liferay.account.model.AccountRole;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing AccountRole in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountRoleCacheModel
	implements CacheModel<AccountRole>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AccountRoleCacheModel)) {
			return false;
		}

		AccountRoleCacheModel accountRoleCacheModel =
			(AccountRoleCacheModel)obj;

		if ((accountRoleId == accountRoleCacheModel.accountRoleId) &&
			(mvccVersion == accountRoleCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, accountRoleId);

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
		sb.append(", accountRoleId=");
		sb.append(accountRoleId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", accountEntryId=");
		sb.append(accountEntryId);
		sb.append(", roleId=");
		sb.append(roleId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AccountRole toEntityModel() {
		AccountRoleImpl accountRoleImpl = new AccountRoleImpl();

		accountRoleImpl.setMvccVersion(mvccVersion);
		accountRoleImpl.setAccountRoleId(accountRoleId);
		accountRoleImpl.setCompanyId(companyId);
		accountRoleImpl.setAccountEntryId(accountEntryId);
		accountRoleImpl.setRoleId(roleId);

		accountRoleImpl.resetOriginalValues();

		return accountRoleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		accountRoleId = objectInput.readLong();

		companyId = objectInput.readLong();

		accountEntryId = objectInput.readLong();

		roleId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(accountRoleId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(accountEntryId);

		objectOutput.writeLong(roleId);
	}

	public long mvccVersion;
	public long accountRoleId;
	public long companyId;
	public long accountEntryId;
	public long roleId;

}