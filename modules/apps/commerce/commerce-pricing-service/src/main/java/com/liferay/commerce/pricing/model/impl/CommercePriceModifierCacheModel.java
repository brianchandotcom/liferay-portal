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

package com.liferay.commerce.pricing.model.impl;

import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

import java.util.Date;

/**
 * The cache model class for representing CommercePriceModifier in entity cache.
 *
 * @author Riccardo Alberti
 * @generated
 */
public class CommercePriceModifierCacheModel
	implements CacheModel<CommercePriceModifier>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceModifierCacheModel)) {
			return false;
		}

		CommercePriceModifierCacheModel commercePriceModifierCacheModel =
			(CommercePriceModifierCacheModel)object;

		if ((commercePriceModifierId ==
				commercePriceModifierCacheModel.commercePriceModifierId) &&
			(mvccVersion == commercePriceModifierCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commercePriceModifierId);

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
		StringBundler sb = new StringBundler(51);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commercePriceModifierId=");
		sb.append(commercePriceModifierId);
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
		sb.append(", commercePriceListId=");
		sb.append(commercePriceListId);
		sb.append(", title=");
		sb.append(title);
		sb.append(", target=");
		sb.append(target);
		sb.append(", modifierAmount=");
		sb.append(modifierAmount);
		sb.append(", modifierType=");
		sb.append(modifierType);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", active=");
		sb.append(active);
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
	public CommercePriceModifier toEntityModel() {
		CommercePriceModifierImpl commercePriceModifierImpl =
			new CommercePriceModifierImpl();

		commercePriceModifierImpl.setMvccVersion(mvccVersion);
		commercePriceModifierImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			commercePriceModifierImpl.setUuid("");
		}
		else {
			commercePriceModifierImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commercePriceModifierImpl.setExternalReferenceCode("");
		}
		else {
			commercePriceModifierImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commercePriceModifierImpl.setCommercePriceModifierId(
			commercePriceModifierId);
		commercePriceModifierImpl.setGroupId(groupId);
		commercePriceModifierImpl.setCompanyId(companyId);
		commercePriceModifierImpl.setUserId(userId);

		if (userName == null) {
			commercePriceModifierImpl.setUserName("");
		}
		else {
			commercePriceModifierImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePriceModifierImpl.setCreateDate(null);
		}
		else {
			commercePriceModifierImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePriceModifierImpl.setModifiedDate(null);
		}
		else {
			commercePriceModifierImpl.setModifiedDate(new Date(modifiedDate));
		}

		commercePriceModifierImpl.setCommercePriceListId(commercePriceListId);

		if (title == null) {
			commercePriceModifierImpl.setTitle("");
		}
		else {
			commercePriceModifierImpl.setTitle(title);
		}

		if (target == null) {
			commercePriceModifierImpl.setTarget("");
		}
		else {
			commercePriceModifierImpl.setTarget(target);
		}

		commercePriceModifierImpl.setModifierAmount(modifierAmount);

		if (modifierType == null) {
			commercePriceModifierImpl.setModifierType("");
		}
		else {
			commercePriceModifierImpl.setModifierType(modifierType);
		}

		commercePriceModifierImpl.setPriority(priority);
		commercePriceModifierImpl.setActive(active);

		if (displayDate == Long.MIN_VALUE) {
			commercePriceModifierImpl.setDisplayDate(null);
		}
		else {
			commercePriceModifierImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			commercePriceModifierImpl.setExpirationDate(null);
		}
		else {
			commercePriceModifierImpl.setExpirationDate(
				new Date(expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commercePriceModifierImpl.setLastPublishDate(null);
		}
		else {
			commercePriceModifierImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		commercePriceModifierImpl.setStatus(status);
		commercePriceModifierImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commercePriceModifierImpl.setStatusByUserName("");
		}
		else {
			commercePriceModifierImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commercePriceModifierImpl.setStatusDate(null);
		}
		else {
			commercePriceModifierImpl.setStatusDate(new Date(statusDate));
		}

		commercePriceModifierImpl.resetOriginalValues();

		return commercePriceModifierImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commercePriceModifierId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commercePriceListId = objectInput.readLong();
		title = objectInput.readUTF();
		target = objectInput.readUTF();
		modifierAmount = (BigDecimal)objectInput.readObject();
		modifierType = objectInput.readUTF();

		priority = objectInput.readDouble();

		active = objectInput.readBoolean();
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

		objectOutput.writeLong(commercePriceModifierId);

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

		objectOutput.writeLong(commercePriceListId);

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (target == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(target);
		}

		objectOutput.writeObject(modifierAmount);

		if (modifierType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(modifierType);
		}

		objectOutput.writeDouble(priority);

		objectOutput.writeBoolean(active);
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
	public long commercePriceModifierId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commercePriceListId;
	public String title;
	public String target;
	public BigDecimal modifierAmount;
	public String modifierType;
	public double priority;
	public boolean active;
	public long displayDate;
	public long expirationDate;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}