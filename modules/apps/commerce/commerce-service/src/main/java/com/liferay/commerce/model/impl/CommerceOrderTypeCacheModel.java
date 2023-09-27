/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CommerceOrderType;
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
 * The cache model class for representing CommerceOrderType in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceOrderTypeCacheModel
	implements CacheModel<CommerceOrderType>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceOrderTypeCacheModel)) {
			return false;
		}

		CommerceOrderTypeCacheModel commerceOrderTypeCacheModel =
			(CommerceOrderTypeCacheModel)object;

		if ((commerceOrderTypeId ==
				commerceOrderTypeCacheModel.commerceOrderTypeId) &&
			(mvccVersion == commerceOrderTypeCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceOrderTypeId);

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
		StringBundler sb = new StringBundler(41);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceOrderTypeId=");
		sb.append(commerceOrderTypeId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", active=");
		sb.append(active);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", displayOrder=");
		sb.append(displayOrder);
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
	public CommerceOrderType toEntityModel() {
		CommerceOrderTypeImpl commerceOrderTypeImpl =
			new CommerceOrderTypeImpl();

		commerceOrderTypeImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceOrderTypeImpl.setUuid("");
		}
		else {
			commerceOrderTypeImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commerceOrderTypeImpl.setExternalReferenceCode("");
		}
		else {
			commerceOrderTypeImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commerceOrderTypeImpl.setCommerceOrderTypeId(commerceOrderTypeId);
		commerceOrderTypeImpl.setCompanyId(companyId);
		commerceOrderTypeImpl.setUserId(userId);

		if (userName == null) {
			commerceOrderTypeImpl.setUserName("");
		}
		else {
			commerceOrderTypeImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceOrderTypeImpl.setCreateDate(null);
		}
		else {
			commerceOrderTypeImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceOrderTypeImpl.setModifiedDate(null);
		}
		else {
			commerceOrderTypeImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceOrderTypeImpl.setName("");
		}
		else {
			commerceOrderTypeImpl.setName(name);
		}

		if (description == null) {
			commerceOrderTypeImpl.setDescription("");
		}
		else {
			commerceOrderTypeImpl.setDescription(description);
		}

		commerceOrderTypeImpl.setActive(active);

		if (displayDate == Long.MIN_VALUE) {
			commerceOrderTypeImpl.setDisplayDate(null);
		}
		else {
			commerceOrderTypeImpl.setDisplayDate(new Date(displayDate));
		}

		commerceOrderTypeImpl.setDisplayOrder(displayOrder);

		if (expirationDate == Long.MIN_VALUE) {
			commerceOrderTypeImpl.setExpirationDate(null);
		}
		else {
			commerceOrderTypeImpl.setExpirationDate(new Date(expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceOrderTypeImpl.setLastPublishDate(null);
		}
		else {
			commerceOrderTypeImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		commerceOrderTypeImpl.setStatus(status);
		commerceOrderTypeImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commerceOrderTypeImpl.setStatusByUserName("");
		}
		else {
			commerceOrderTypeImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commerceOrderTypeImpl.setStatusDate(null);
		}
		else {
			commerceOrderTypeImpl.setStatusDate(new Date(statusDate));
		}

		commerceOrderTypeImpl.resetOriginalValues();

		return commerceOrderTypeImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commerceOrderTypeId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		active = objectInput.readBoolean();
		displayDate = objectInput.readLong();

		displayOrder = objectInput.readInt();
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

		objectOutput.writeLong(commerceOrderTypeId);

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

		objectOutput.writeBoolean(active);
		objectOutput.writeLong(displayDate);

		objectOutput.writeInt(displayOrder);
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
	public String uuid;
	public String externalReferenceCode;
	public long commerceOrderTypeId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public boolean active;
	public long displayDate;
	public int displayOrder;
	public long expirationDate;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}