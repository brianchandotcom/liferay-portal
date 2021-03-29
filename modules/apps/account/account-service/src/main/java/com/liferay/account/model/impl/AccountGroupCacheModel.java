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

import com.liferay.account.model.AccountGroup;
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
 * The cache model class for representing AccountGroup in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountGroupCacheModel
	implements CacheModel<AccountGroup>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AccountGroupCacheModel)) {
			return false;
		}

		AccountGroupCacheModel accountGroupCacheModel =
			(AccountGroupCacheModel)object;

		if ((accountGroupId == accountGroupCacheModel.accountGroupId) &&
			(mvccVersion == accountGroupCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, accountGroupId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", accountGroupId=");
		sb.append(accountGroupId);
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
		sb.append(", defaultAccountGroup=");
		sb.append(defaultAccountGroup);
		sb.append(", description=");
		sb.append(description);
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AccountGroup toEntityModel() {
		AccountGroupImpl accountGroupImpl = new AccountGroupImpl();

		accountGroupImpl.setMvccVersion(mvccVersion);

		if (externalReferenceCode == null) {
			accountGroupImpl.setExternalReferenceCode("");
		}
		else {
			accountGroupImpl.setExternalReferenceCode(externalReferenceCode);
		}

		accountGroupImpl.setAccountGroupId(accountGroupId);
		accountGroupImpl.setCompanyId(companyId);
		accountGroupImpl.setUserId(userId);

		if (userName == null) {
			accountGroupImpl.setUserName("");
		}
		else {
			accountGroupImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			accountGroupImpl.setCreateDate(null);
		}
		else {
			accountGroupImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			accountGroupImpl.setModifiedDate(null);
		}
		else {
			accountGroupImpl.setModifiedDate(new Date(modifiedDate));
		}

		accountGroupImpl.setDefaultAccountGroup(defaultAccountGroup);

		if (description == null) {
			accountGroupImpl.setDescription("");
		}
		else {
			accountGroupImpl.setDescription(description);
		}

		if (name == null) {
			accountGroupImpl.setName("");
		}
		else {
			accountGroupImpl.setName(name);
		}

		if (type == null) {
			accountGroupImpl.setType("");
		}
		else {
			accountGroupImpl.setType(type);
		}

		accountGroupImpl.resetOriginalValues();

		return accountGroupImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		externalReferenceCode = objectInput.readUTF();

		accountGroupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		defaultAccountGroup = objectInput.readBoolean();
		description = objectInput.readUTF();
		name = objectInput.readUTF();
		type = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(accountGroupId);

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

		objectOutput.writeBoolean(defaultAccountGroup);

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long mvccVersion;
	public String externalReferenceCode;
	public long accountGroupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean defaultAccountGroup;
	public String description;
	public String name;
	public String type;

}