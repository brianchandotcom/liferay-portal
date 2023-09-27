/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.model.impl;

import com.liferay.commerce.order.rule.model.COREntry;
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
 * The cache model class for representing COREntry in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class COREntryCacheModel
	implements CacheModel<COREntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof COREntryCacheModel)) {
			return false;
		}

		COREntryCacheModel corEntryCacheModel = (COREntryCacheModel)object;

		if ((COREntryId == corEntryCacheModel.COREntryId) &&
			(mvccVersion == corEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, COREntryId);

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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", COREntryId=");
		sb.append(COREntryId);
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
		sb.append(", active=");
		sb.append(active);
		sb.append(", description=");
		sb.append(description);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
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
	public COREntry toEntityModel() {
		COREntryImpl corEntryImpl = new COREntryImpl();

		corEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			corEntryImpl.setUuid("");
		}
		else {
			corEntryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			corEntryImpl.setExternalReferenceCode("");
		}
		else {
			corEntryImpl.setExternalReferenceCode(externalReferenceCode);
		}

		corEntryImpl.setCOREntryId(COREntryId);
		corEntryImpl.setCompanyId(companyId);
		corEntryImpl.setUserId(userId);

		if (userName == null) {
			corEntryImpl.setUserName("");
		}
		else {
			corEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			corEntryImpl.setCreateDate(null);
		}
		else {
			corEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			corEntryImpl.setModifiedDate(null);
		}
		else {
			corEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		corEntryImpl.setActive(active);

		if (description == null) {
			corEntryImpl.setDescription("");
		}
		else {
			corEntryImpl.setDescription(description);
		}

		if (displayDate == Long.MIN_VALUE) {
			corEntryImpl.setDisplayDate(null);
		}
		else {
			corEntryImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			corEntryImpl.setExpirationDate(null);
		}
		else {
			corEntryImpl.setExpirationDate(new Date(expirationDate));
		}

		if (name == null) {
			corEntryImpl.setName("");
		}
		else {
			corEntryImpl.setName(name);
		}

		corEntryImpl.setPriority(priority);

		if (type == null) {
			corEntryImpl.setType("");
		}
		else {
			corEntryImpl.setType(type);
		}

		if (typeSettings == null) {
			corEntryImpl.setTypeSettings("");
		}
		else {
			corEntryImpl.setTypeSettings(typeSettings);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			corEntryImpl.setLastPublishDate(null);
		}
		else {
			corEntryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		corEntryImpl.setStatus(status);
		corEntryImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			corEntryImpl.setStatusByUserName("");
		}
		else {
			corEntryImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			corEntryImpl.setStatusDate(null);
		}
		else {
			corEntryImpl.setStatusDate(new Date(statusDate));
		}

		corEntryImpl.resetOriginalValues();

		return corEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		COREntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		active = objectInput.readBoolean();
		description = objectInput.readUTF();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		name = objectInput.readUTF();

		priority = objectInput.readInt();
		type = objectInput.readUTF();
		typeSettings = (String)objectInput.readObject();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(COREntryId);

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

		objectOutput.writeBoolean(active);

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(priority);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (typeSettings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(typeSettings);
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
	public String uuid;
	public String externalReferenceCode;
	public long COREntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean active;
	public String description;
	public long displayDate;
	public long expirationDate;
	public String name;
	public int priority;
	public String type;
	public String typeSettings;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}