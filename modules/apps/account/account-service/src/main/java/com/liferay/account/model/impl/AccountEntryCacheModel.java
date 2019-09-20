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

import com.liferay.account.model.AccountEntry;
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
 * The cache model class for representing AccountEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountEntryCacheModel
	implements CacheModel<AccountEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AccountEntryCacheModel)) {
			return false;
		}

		AccountEntryCacheModel accountEntryCacheModel =
			(AccountEntryCacheModel)obj;

		if ((accountEntryId == accountEntryCacheModel.accountEntryId) &&
			(mvccVersion == accountEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, accountEntryId);

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
		sb.append(", accountEntryId=");
		sb.append(accountEntryId);
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
		sb.append(", parentAccountEntryId=");
		sb.append(parentAccountEntryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", logoId=");
		sb.append(logoId);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AccountEntry toEntityModel() {
		AccountEntryImpl accountEntryImpl = new AccountEntryImpl();

		accountEntryImpl.setMvccVersion(mvccVersion);
		accountEntryImpl.setAccountEntryId(accountEntryId);
		accountEntryImpl.setCompanyId(companyId);
		accountEntryImpl.setUserId(userId);

		if (userName == null) {
			accountEntryImpl.setUserName("");
		}
		else {
			accountEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			accountEntryImpl.setCreateDate(null);
		}
		else {
			accountEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			accountEntryImpl.setModifiedDate(null);
		}
		else {
			accountEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		accountEntryImpl.setParentAccountEntryId(parentAccountEntryId);

		if (name == null) {
			accountEntryImpl.setName("");
		}
		else {
			accountEntryImpl.setName(name);
		}

		if (description == null) {
			accountEntryImpl.setDescription("");
		}
		else {
			accountEntryImpl.setDescription(description);
		}

		accountEntryImpl.setLogoId(logoId);
		accountEntryImpl.setStatus(status);

		accountEntryImpl.resetOriginalValues();

		return accountEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		accountEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		parentAccountEntryId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		logoId = objectInput.readLong();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(accountEntryId);

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

		objectOutput.writeLong(parentAccountEntryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(logoId);

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public long accountEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentAccountEntryId;
	public String name;
	public String description;
	public long logoId;
	public int status;

}