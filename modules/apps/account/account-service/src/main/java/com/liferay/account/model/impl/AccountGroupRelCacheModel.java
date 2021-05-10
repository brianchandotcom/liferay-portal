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

import com.liferay.account.model.AccountGroupRel;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AccountGroupRel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountGroupRelCacheModel
	implements CacheModel<AccountGroupRel>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AccountGroupRelCacheModel)) {
			return false;
		}

		AccountGroupRelCacheModel accountGroupRelCacheModel =
			(AccountGroupRelCacheModel)object;

		if ((accountGroupRelId ==
				accountGroupRelCacheModel.accountGroupRelId) &&
			(mvccVersion == accountGroupRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, accountGroupRelId);

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
		StringBundler sb = new StringBundler(21);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", accountGroupRelId=");
		sb.append(accountGroupRelId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", accountGroupId=");
		sb.append(accountGroupId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AccountGroupRel toEntityModel() {
		AccountGroupRelImpl accountGroupRelImpl = new AccountGroupRelImpl();

		accountGroupRelImpl.setMvccVersion(mvccVersion);
		accountGroupRelImpl.setAccountGroupRelId(accountGroupRelId);
		accountGroupRelImpl.setCompanyId(companyId);
		accountGroupRelImpl.setUserId(userId);

		if (userName == null) {
			accountGroupRelImpl.setUserName("");
		}
		else {
			accountGroupRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			accountGroupRelImpl.setCreateDate(null);
		}
		else {
			accountGroupRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			accountGroupRelImpl.setModifiedDate(null);
		}
		else {
			accountGroupRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		accountGroupRelImpl.setAccountGroupId(accountGroupId);
		accountGroupRelImpl.setClassNameId(classNameId);
		accountGroupRelImpl.setClassPK(classPK);

		accountGroupRelImpl.resetOriginalValues();

		return accountGroupRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		accountGroupRelId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		accountGroupId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(accountGroupRelId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(accountGroupId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);
	}

	public long mvccVersion;
	public long accountGroupRelId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long accountGroupId;
	public long classNameId;
	public long classPK;

}