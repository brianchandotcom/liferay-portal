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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing EmailAddress in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class EmailAddressCacheModel
	implements CacheModel<EmailAddress>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EmailAddressCacheModel)) {
			return false;
		}

		EmailAddressCacheModel emailAddressCacheModel =
			(EmailAddressCacheModel)obj;

		if ((emailAddressId == emailAddressCacheModel.emailAddressId) &&
			(mvccVersion == emailAddressCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, emailAddressId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", emailAddressId=");
		sb.append(emailAddressId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", address=");
		sb.append(address);
		sb.append(", typeId=");
		sb.append(typeId);
		sb.append(", primary=");
		sb.append(primary);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public EmailAddress toEntityModel() {
		EmailAddressImpl emailAddressImpl = new EmailAddressImpl();

		emailAddressImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			emailAddressImpl.setUuid("");
		}
		else {
			emailAddressImpl.setUuid(uuid);
		}

		emailAddressImpl.setEmailAddressId(emailAddressId);
		emailAddressImpl.setCompanyId(companyId);
		emailAddressImpl.setUserId(userId);

		if (userName == null) {
			emailAddressImpl.setUserName("");
		}
		else {
			emailAddressImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			emailAddressImpl.setCreateDate(null);
		}
		else {
			emailAddressImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			emailAddressImpl.setModifiedDate(null);
		}
		else {
			emailAddressImpl.setModifiedDate(new Date(modifiedDate));
		}

		emailAddressImpl.setClassNameId(classNameId);
		emailAddressImpl.setClassPK(classPK);

		if (address == null) {
			emailAddressImpl.setAddress("");
		}
		else {
			emailAddressImpl.setAddress(address);
		}

		emailAddressImpl.setTypeId(typeId);
		emailAddressImpl.setPrimary(primary);

		emailAddressImpl.resetOriginalValues();

		return emailAddressImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		emailAddressId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		address = objectInput.readUTF();

		typeId = objectInput.readLong();

		primary = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(emailAddressId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (address == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(address);
		}

		objectOutput.writeLong(typeId);

		objectOutput.writeBoolean(primary);
	}

	public long mvccVersion;
	public String uuid;
	public long emailAddressId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String address;
	public long typeId;
	public boolean primary;

}