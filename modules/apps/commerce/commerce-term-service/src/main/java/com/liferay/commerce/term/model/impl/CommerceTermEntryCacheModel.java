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

package com.liferay.commerce.term.model.impl;

import com.liferay.commerce.term.model.CommerceTermEntry;
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
 * The cache model class for representing CommerceTermEntry in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceTermEntryCacheModel
	implements CacheModel<CommerceTermEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceTermEntryCacheModel)) {
			return false;
		}

		CommerceTermEntryCacheModel commerceTermEntryCacheModel =
			(CommerceTermEntryCacheModel)object;

		if ((commerceTermEntryId ==
				commerceTermEntryCacheModel.commerceTermEntryId) &&
			(mvccVersion == commerceTermEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceTermEntryId);

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
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceTermEntryId=");
		sb.append(commerceTermEntryId);
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
		sb.append(", label=");
		sb.append(label);
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
	public CommerceTermEntry toEntityModel() {
		CommerceTermEntryImpl commerceTermEntryImpl =
			new CommerceTermEntryImpl();

		commerceTermEntryImpl.setMvccVersion(mvccVersion);

		if (externalReferenceCode == null) {
			commerceTermEntryImpl.setExternalReferenceCode("");
		}
		else {
			commerceTermEntryImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commerceTermEntryImpl.setCommerceTermEntryId(commerceTermEntryId);
		commerceTermEntryImpl.setCompanyId(companyId);
		commerceTermEntryImpl.setUserId(userId);

		if (userName == null) {
			commerceTermEntryImpl.setUserName("");
		}
		else {
			commerceTermEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTermEntryImpl.setCreateDate(null);
		}
		else {
			commerceTermEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTermEntryImpl.setModifiedDate(null);
		}
		else {
			commerceTermEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceTermEntryImpl.setActive(active);

		if (description == null) {
			commerceTermEntryImpl.setDescription("");
		}
		else {
			commerceTermEntryImpl.setDescription(description);
		}

		if (displayDate == Long.MIN_VALUE) {
			commerceTermEntryImpl.setDisplayDate(null);
		}
		else {
			commerceTermEntryImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			commerceTermEntryImpl.setExpirationDate(null);
		}
		else {
			commerceTermEntryImpl.setExpirationDate(new Date(expirationDate));
		}

		if (label == null) {
			commerceTermEntryImpl.setLabel("");
		}
		else {
			commerceTermEntryImpl.setLabel(label);
		}

		if (name == null) {
			commerceTermEntryImpl.setName("");
		}
		else {
			commerceTermEntryImpl.setName(name);
		}

		commerceTermEntryImpl.setPriority(priority);

		if (type == null) {
			commerceTermEntryImpl.setType("");
		}
		else {
			commerceTermEntryImpl.setType(type);
		}

		if (typeSettings == null) {
			commerceTermEntryImpl.setTypeSettings("");
		}
		else {
			commerceTermEntryImpl.setTypeSettings(typeSettings);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceTermEntryImpl.setLastPublishDate(null);
		}
		else {
			commerceTermEntryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		commerceTermEntryImpl.setStatus(status);
		commerceTermEntryImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commerceTermEntryImpl.setStatusByUserName("");
		}
		else {
			commerceTermEntryImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commerceTermEntryImpl.setStatusDate(null);
		}
		else {
			commerceTermEntryImpl.setStatusDate(new Date(statusDate));
		}

		commerceTermEntryImpl.resetOriginalValues();

		return commerceTermEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		externalReferenceCode = objectInput.readUTF();

		commerceTermEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		active = objectInput.readBoolean();
		description = (String)objectInput.readObject();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		label = objectInput.readUTF();
		name = objectInput.readUTF();

		priority = objectInput.readDouble();
		type = objectInput.readUTF();
		typeSettings = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
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

		objectOutput.writeLong(commerceTermEntryId);

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
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(description);
		}

		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);

		if (label == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(label);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeDouble(priority);

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
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
	public String externalReferenceCode;
	public long commerceTermEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean active;
	public String description;
	public long displayDate;
	public long expirationDate;
	public String label;
	public String name;
	public double priority;
	public String type;
	public String typeSettings;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}