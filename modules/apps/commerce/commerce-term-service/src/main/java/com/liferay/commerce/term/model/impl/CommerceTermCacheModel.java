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

import com.liferay.commerce.term.model.CommerceTerm;
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
 * The cache model class for representing CommerceTerm in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceTermCacheModel
	implements CacheModel<CommerceTerm>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceTermCacheModel)) {
			return false;
		}

		CommerceTermCacheModel commerceTermCacheModel =
			(CommerceTermCacheModel)object;

		if ((commerceTermId == commerceTermCacheModel.commerceTermId) &&
			(mvccVersion == commerceTermCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceTermId);

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
		sb.append(", commerceTermId=");
		sb.append(commerceTermId);
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
	public CommerceTerm toEntityModel() {
		CommerceTermImpl commerceTermImpl = new CommerceTermImpl();

		commerceTermImpl.setMvccVersion(mvccVersion);

		if (externalReferenceCode == null) {
			commerceTermImpl.setExternalReferenceCode("");
		}
		else {
			commerceTermImpl.setExternalReferenceCode(externalReferenceCode);
		}

		commerceTermImpl.setCommerceTermId(commerceTermId);
		commerceTermImpl.setCompanyId(companyId);
		commerceTermImpl.setUserId(userId);

		if (userName == null) {
			commerceTermImpl.setUserName("");
		}
		else {
			commerceTermImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTermImpl.setCreateDate(null);
		}
		else {
			commerceTermImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTermImpl.setModifiedDate(null);
		}
		else {
			commerceTermImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceTermImpl.setActive(active);

		if (description == null) {
			commerceTermImpl.setDescription("");
		}
		else {
			commerceTermImpl.setDescription(description);
		}

		if (displayDate == Long.MIN_VALUE) {
			commerceTermImpl.setDisplayDate(null);
		}
		else {
			commerceTermImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			commerceTermImpl.setExpirationDate(null);
		}
		else {
			commerceTermImpl.setExpirationDate(new Date(expirationDate));
		}

		if (label == null) {
			commerceTermImpl.setLabel("");
		}
		else {
			commerceTermImpl.setLabel(label);
		}

		if (name == null) {
			commerceTermImpl.setName("");
		}
		else {
			commerceTermImpl.setName(name);
		}

		commerceTermImpl.setPriority(priority);

		if (type == null) {
			commerceTermImpl.setType("");
		}
		else {
			commerceTermImpl.setType(type);
		}

		if (typeSettings == null) {
			commerceTermImpl.setTypeSettings("");
		}
		else {
			commerceTermImpl.setTypeSettings(typeSettings);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceTermImpl.setLastPublishDate(null);
		}
		else {
			commerceTermImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		commerceTermImpl.setStatus(status);
		commerceTermImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commerceTermImpl.setStatusByUserName("");
		}
		else {
			commerceTermImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commerceTermImpl.setStatusDate(null);
		}
		else {
			commerceTermImpl.setStatusDate(new Date(statusDate));
		}

		commerceTermImpl.resetOriginalValues();

		return commerceTermImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		externalReferenceCode = objectInput.readUTF();

		commerceTermId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		active = objectInput.readBoolean();
		description = objectInput.readUTF();
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

		objectOutput.writeLong(commerceTermId);

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
	public long commerceTermId;
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