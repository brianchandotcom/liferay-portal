/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPDefinitionLink;
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
 * The cache model class for representing CPDefinitionLink in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CPDefinitionLinkCacheModel
	implements CacheModel<CPDefinitionLink>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDefinitionLinkCacheModel)) {
			return false;
		}

		CPDefinitionLinkCacheModel cpDefinitionLinkCacheModel =
			(CPDefinitionLinkCacheModel)object;

		if ((CPDefinitionLinkId ==
				cpDefinitionLinkCacheModel.CPDefinitionLinkId) &&
			(mvccVersion == cpDefinitionLinkCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPDefinitionLinkId);

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
		StringBundler sb = new StringBundler(43);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionLinkId=");
		sb.append(CPDefinitionLinkId);
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
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", CProductId=");
		sb.append(CProductId);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", type=");
		sb.append(type);
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
	public CPDefinitionLink toEntityModel() {
		CPDefinitionLinkImpl cpDefinitionLinkImpl = new CPDefinitionLinkImpl();

		cpDefinitionLinkImpl.setMvccVersion(mvccVersion);
		cpDefinitionLinkImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			cpDefinitionLinkImpl.setUuid("");
		}
		else {
			cpDefinitionLinkImpl.setUuid(uuid);
		}

		cpDefinitionLinkImpl.setCPDefinitionLinkId(CPDefinitionLinkId);
		cpDefinitionLinkImpl.setGroupId(groupId);
		cpDefinitionLinkImpl.setCompanyId(companyId);
		cpDefinitionLinkImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionLinkImpl.setUserName("");
		}
		else {
			cpDefinitionLinkImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionLinkImpl.setCreateDate(null);
		}
		else {
			cpDefinitionLinkImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionLinkImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionLinkImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpDefinitionLinkImpl.setCPDefinitionId(CPDefinitionId);
		cpDefinitionLinkImpl.setCProductId(CProductId);

		if (displayDate == Long.MIN_VALUE) {
			cpDefinitionLinkImpl.setDisplayDate(null);
		}
		else {
			cpDefinitionLinkImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			cpDefinitionLinkImpl.setExpirationDate(null);
		}
		else {
			cpDefinitionLinkImpl.setExpirationDate(new Date(expirationDate));
		}

		cpDefinitionLinkImpl.setPriority(priority);

		if (type == null) {
			cpDefinitionLinkImpl.setType("");
		}
		else {
			cpDefinitionLinkImpl.setType(type);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			cpDefinitionLinkImpl.setLastPublishDate(null);
		}
		else {
			cpDefinitionLinkImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		cpDefinitionLinkImpl.setStatus(status);
		cpDefinitionLinkImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			cpDefinitionLinkImpl.setStatusByUserName("");
		}
		else {
			cpDefinitionLinkImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			cpDefinitionLinkImpl.setStatusDate(null);
		}
		else {
			cpDefinitionLinkImpl.setStatusDate(new Date(statusDate));
		}

		cpDefinitionLinkImpl.resetOriginalValues();

		return cpDefinitionLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();

		CPDefinitionLinkId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();

		CProductId = objectInput.readLong();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();

		priority = objectInput.readDouble();
		type = objectInput.readUTF();
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

		objectOutput.writeLong(CPDefinitionLinkId);

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

		objectOutput.writeLong(CPDefinitionId);

		objectOutput.writeLong(CProductId);
		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);

		objectOutput.writeDouble(priority);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

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
	public long CPDefinitionLinkId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public long CProductId;
	public long displayDate;
	public long expirationDate;
	public double priority;
	public String type;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}