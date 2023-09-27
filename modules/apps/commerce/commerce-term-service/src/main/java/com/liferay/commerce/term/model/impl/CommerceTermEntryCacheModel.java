/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", defaultLanguageId=");
		sb.append(defaultLanguageId);
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
	public CommerceTermEntry toEntityModel() {
		CommerceTermEntryImpl commerceTermEntryImpl =
			new CommerceTermEntryImpl();

		commerceTermEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceTermEntryImpl.setUuid("");
		}
		else {
			commerceTermEntryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commerceTermEntryImpl.setExternalReferenceCode("");
		}
		else {
			commerceTermEntryImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		if (defaultLanguageId == null) {
			commerceTermEntryImpl.setDefaultLanguageId("");
		}
		else {
			commerceTermEntryImpl.setDefaultLanguageId(defaultLanguageId);
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
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();
		defaultLanguageId = objectInput.readUTF();

		commerceTermEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		active = objectInput.readBoolean();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
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

		if (defaultLanguageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(defaultLanguageId);
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
		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);

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
	public String uuid;
	public String externalReferenceCode;
	public String defaultLanguageId;
	public long commerceTermEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean active;
	public long displayDate;
	public long expirationDate;
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