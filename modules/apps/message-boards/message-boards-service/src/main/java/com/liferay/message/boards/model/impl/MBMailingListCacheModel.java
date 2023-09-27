/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model.impl;

import com.liferay.message.boards.model.MBMailingList;
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
 * The cache model class for representing MBMailingList in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBMailingListCacheModel
	implements CacheModel<MBMailingList>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MBMailingListCacheModel)) {
			return false;
		}

		MBMailingListCacheModel mbMailingListCacheModel =
			(MBMailingListCacheModel)object;

		if ((mailingListId == mbMailingListCacheModel.mailingListId) &&
			(mvccVersion == mbMailingListCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, mailingListId);

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
		StringBundler sb = new StringBundler(57);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
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

		mbMailingListImpl.setMvccVersion(mvccVersion);
		mbMailingListImpl.setCtCollectionId(ctCollectionId);

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
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
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
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

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

	public long mvccVersion;
	public long ctCollectionId;
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