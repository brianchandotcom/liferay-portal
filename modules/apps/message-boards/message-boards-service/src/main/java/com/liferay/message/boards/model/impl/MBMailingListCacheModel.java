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

package com.liferay.message.boards.model.impl;

import com.liferay.message.boards.model.MBMailingList;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing MBMailingList in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBMailingListCacheModel
	implements CacheModel<MBMailingList>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MBMailingListCacheModel)) {
			return false;
		}

		MBMailingListCacheModel mbMailingListCacheModel =
			(MBMailingListCacheModel)obj;

		if (mailingListId == mbMailingListCacheModel.mailingListId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, mailingListId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(53);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", mailingListId=");
		sb.append(mailingListId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", categoryId=");
		sb.append(categoryId);
		sb.append(", emailAddress=");
		sb.append(emailAddress);
		sb.append(", inProtocol=");
		sb.append(inProtocol);
		sb.append(", inServerName=");
		sb.append(inServerName);
		sb.append(", inServerPort=");
		sb.append(inServerPort);
		sb.append(", inUseSSL=");
		sb.append(inUseSSL);
		sb.append(", inUserName=");
		sb.append(inUserName);
		sb.append(", inPassword=");
		sb.append(inPassword);
		sb.append(", inReadInterval=");
		sb.append(inReadInterval);
		sb.append(", outEmailAddress=");
		sb.append(outEmailAddress);
		sb.append(", outCustom=");
		sb.append(outCustom);
		sb.append(", outServerName=");
		sb.append(outServerName);
		sb.append(", outServerPort=");
		sb.append(outServerPort);
		sb.append(", outUseSSL=");
		sb.append(outUseSSL);
		sb.append(", outUserName=");
		sb.append(outUserName);
		sb.append(", outPassword=");
		sb.append(outPassword);
		sb.append(", allowAnonymous=");
		sb.append(allowAnonymous);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MBMailingList toEntityModel() {
		MBMailingListImpl mbMailingListImpl = new MBMailingListImpl();

		if (uuid == null) {
			mbMailingListImpl.setUuid("");
		}
		else {
			mbMailingListImpl.setUuid(uuid);
		}

		mbMailingListImpl.setMailingListId(mailingListId);
		mbMailingListImpl.setGroupId(groupId);
		mbMailingListImpl.setCompanyId(companyId);
		mbMailingListImpl.setUserId(userId);

		if (userName == null) {
			mbMailingListImpl.setUserName("");
		}
		else {
			mbMailingListImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			mbMailingListImpl.setCreateDate(null);
		}
		else {
			mbMailingListImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			mbMailingListImpl.setModifiedDate(null);
		}
		else {
			mbMailingListImpl.setModifiedDate(new Date(modifiedDate));
		}

		mbMailingListImpl.setCategoryId(categoryId);

		if (emailAddress == null) {
			mbMailingListImpl.setEmailAddress("");
		}
		else {
			mbMailingListImpl.setEmailAddress(emailAddress);
		}

		if (inProtocol == null) {
			mbMailingListImpl.setInProtocol("");
		}
		else {
			mbMailingListImpl.setInProtocol(inProtocol);
		}

		if (inServerName == null) {
			mbMailingListImpl.setInServerName("");
		}
		else {
			mbMailingListImpl.setInServerName(inServerName);
		}

		mbMailingListImpl.setInServerPort(inServerPort);
		mbMailingListImpl.setInUseSSL(inUseSSL);

		if (inUserName == null) {
			mbMailingListImpl.setInUserName("");
		}
		else {
			mbMailingListImpl.setInUserName(inUserName);
		}

		if (inPassword == null) {
			mbMailingListImpl.setInPassword("");
		}
		else {
			mbMailingListImpl.setInPassword(inPassword);
		}

		mbMailingListImpl.setInReadInterval(inReadInterval);

		if (outEmailAddress == null) {
			mbMailingListImpl.setOutEmailAddress("");
		}
		else {
			mbMailingListImpl.setOutEmailAddress(outEmailAddress);
		}

		mbMailingListImpl.setOutCustom(outCustom);

		if (outServerName == null) {
			mbMailingListImpl.setOutServerName("");
		}
		else {
			mbMailingListImpl.setOutServerName(outServerName);
		}

		mbMailingListImpl.setOutServerPort(outServerPort);
		mbMailingListImpl.setOutUseSSL(outUseSSL);

		if (outUserName == null) {
			mbMailingListImpl.setOutUserName("");
		}
		else {
			mbMailingListImpl.setOutUserName(outUserName);
		}

		if (outPassword == null) {
			mbMailingListImpl.setOutPassword("");
		}
		else {
			mbMailingListImpl.setOutPassword(outPassword);
		}

		mbMailingListImpl.setAllowAnonymous(allowAnonymous);
		mbMailingListImpl.setActive(active);

		mbMailingListImpl.resetOriginalValues();

		return mbMailingListImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		mailingListId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		categoryId = objectInput.readLong();
		emailAddress = objectInput.readUTF();
		inProtocol = objectInput.readUTF();
		inServerName = objectInput.readUTF();

		inServerPort = objectInput.readInt();

		inUseSSL = objectInput.readBoolean();
		inUserName = objectInput.readUTF();
		inPassword = objectInput.readUTF();

		inReadInterval = objectInput.readInt();
		outEmailAddress = objectInput.readUTF();

		outCustom = objectInput.readBoolean();
		outServerName = objectInput.readUTF();

		outServerPort = objectInput.readInt();

		outUseSSL = objectInput.readBoolean();
		outUserName = objectInput.readUTF();
		outPassword = objectInput.readUTF();

		allowAnonymous = objectInput.readBoolean();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(mailingListId);

		objectOutput.writeLong(groupId);

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

		objectOutput.writeLong(categoryId);

		if (emailAddress == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(emailAddress);
		}

		if (inProtocol == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(inProtocol);
		}

		if (inServerName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(inServerName);
		}

		objectOutput.writeInt(inServerPort);

		objectOutput.writeBoolean(inUseSSL);

		if (inUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(inUserName);
		}

		if (inPassword == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(inPassword);
		}

		objectOutput.writeInt(inReadInterval);

		if (outEmailAddress == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(outEmailAddress);
		}

		objectOutput.writeBoolean(outCustom);

		if (outServerName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(outServerName);
		}

		objectOutput.writeInt(outServerPort);

		objectOutput.writeBoolean(outUseSSL);

		if (outUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(outUserName);
		}

		if (outPassword == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(outPassword);
		}

		objectOutput.writeBoolean(allowAnonymous);

		objectOutput.writeBoolean(active);
	}

	public String uuid;
	public long mailingListId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long categoryId;
	public String emailAddress;
	public String inProtocol;
	public String inServerName;
	public int inServerPort;
	public boolean inUseSSL;
	public String inUserName;
	public String inPassword;
	public int inReadInterval;
	public String outEmailAddress;
	public boolean outCustom;
	public String outServerName;
	public int outServerPort;
	public boolean outUseSSL;
	public String outUserName;
	public String outPassword;
	public boolean allowAnonymous;
	public boolean active;

}