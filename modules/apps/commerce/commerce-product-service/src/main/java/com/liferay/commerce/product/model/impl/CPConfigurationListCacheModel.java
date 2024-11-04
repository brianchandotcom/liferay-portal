/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPConfigurationList;
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
 * The cache model class for representing CPConfigurationList in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPConfigurationListCacheModel
	implements CacheModel<CPConfigurationList>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPConfigurationListCacheModel)) {
			return false;
		}

		CPConfigurationListCacheModel cpConfigurationListCacheModel =
			(CPConfigurationListCacheModel)object;

		if ((CPConfigurationListId ==
				cpConfigurationListCacheModel.CPConfigurationListId) &&
			(mvccVersion == cpConfigurationListCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPConfigurationListId);

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
		StringBundler sb = new StringBundler(45);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", CPConfigurationListId=");
		sb.append(CPConfigurationListId);
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
		sb.append(", parentCPConfigurationListId=");
		sb.append(parentCPConfigurationListId);
		sb.append(", masterCPConfigurationList=");
		sb.append(masterCPConfigurationList);
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPConfigurationList toEntityModel() {
		CPConfigurationListImpl cpConfigurationListImpl =
			new CPConfigurationListImpl();

		cpConfigurationListImpl.setMvccVersion(mvccVersion);
		cpConfigurationListImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			cpConfigurationListImpl.setUuid("");
		}
		else {
			cpConfigurationListImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			cpConfigurationListImpl.setExternalReferenceCode("");
		}
		else {
			cpConfigurationListImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		cpConfigurationListImpl.setCPConfigurationListId(CPConfigurationListId);
		cpConfigurationListImpl.setGroupId(groupId);
		cpConfigurationListImpl.setCompanyId(companyId);
		cpConfigurationListImpl.setUserId(userId);

		if (userName == null) {
			cpConfigurationListImpl.setUserName("");
		}
		else {
			cpConfigurationListImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpConfigurationListImpl.setCreateDate(null);
		}
		else {
			cpConfigurationListImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpConfigurationListImpl.setModifiedDate(null);
		}
		else {
			cpConfigurationListImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpConfigurationListImpl.setParentCPConfigurationListId(
			parentCPConfigurationListId);
		cpConfigurationListImpl.setMasterCPConfigurationList(
			masterCPConfigurationList);

		if (name == null) {
			cpConfigurationListImpl.setName("");
		}
		else {
			cpConfigurationListImpl.setName(name);
		}

		cpConfigurationListImpl.setPriority(priority);

		if (displayDate == Long.MIN_VALUE) {
			cpConfigurationListImpl.setDisplayDate(null);
		}
		else {
			cpConfigurationListImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			cpConfigurationListImpl.setExpirationDate(null);
		}
		else {
			cpConfigurationListImpl.setExpirationDate(new Date(expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			cpConfigurationListImpl.setLastPublishDate(null);
		}
		else {
			cpConfigurationListImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		cpConfigurationListImpl.setStatus(status);
		cpConfigurationListImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			cpConfigurationListImpl.setStatusByUserName("");
		}
		else {
			cpConfigurationListImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			cpConfigurationListImpl.setStatusDate(null);
		}
		else {
			cpConfigurationListImpl.setStatusDate(new Date(statusDate));
		}

		cpConfigurationListImpl.resetOriginalValues();

		return cpConfigurationListImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		CPConfigurationListId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		parentCPConfigurationListId = objectInput.readLong();

		masterCPConfigurationList = objectInput.readBoolean();
		name = objectInput.readUTF();

		priority = objectInput.readDouble();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(CPConfigurationListId);

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

		objectOutput.writeLong(parentCPConfigurationListId);

		objectOutput.writeBoolean(masterCPConfigurationList);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeDouble(priority);
		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public String externalReferenceCode;
	public long CPConfigurationListId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentCPConfigurationListId;
	public boolean masterCPConfigurationList;
	public String name;
	public double priority;
	public long displayDate;
	public long expirationDate;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}