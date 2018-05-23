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

package com.liferay.commerce.product.type.virtual.order.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceVirtualOrderItem in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItem
 * @generated
 */
@ProviderType
public class CommerceVirtualOrderItemCacheModel implements CacheModel<CommerceVirtualOrderItem>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceVirtualOrderItemCacheModel)) {
			return false;
		}

		CommerceVirtualOrderItemCacheModel commerceVirtualOrderItemCacheModel = (CommerceVirtualOrderItemCacheModel)obj;

		if (commerceVirtualOrderItemId == commerceVirtualOrderItemCacheModel.commerceVirtualOrderItemId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceVirtualOrderItemId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(37);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceVirtualOrderItemId=");
		sb.append(commerceVirtualOrderItemId);
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
		sb.append(", commerceOrderItemId=");
		sb.append(commerceOrderItemId);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append(", url=");
		sb.append(url);
		sb.append(", activationStatus=");
		sb.append(activationStatus);
		sb.append(", duration=");
		sb.append(duration);
		sb.append(", usages=");
		sb.append(usages);
		sb.append(", maxUsages=");
		sb.append(maxUsages);
		sb.append(", active=");
		sb.append(active);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceVirtualOrderItem toEntityModel() {
		CommerceVirtualOrderItemImpl commerceVirtualOrderItemImpl = new CommerceVirtualOrderItemImpl();

		if (uuid == null) {
			commerceVirtualOrderItemImpl.setUuid("");
		}
		else {
			commerceVirtualOrderItemImpl.setUuid(uuid);
		}

		commerceVirtualOrderItemImpl.setCommerceVirtualOrderItemId(commerceVirtualOrderItemId);
		commerceVirtualOrderItemImpl.setGroupId(groupId);
		commerceVirtualOrderItemImpl.setCompanyId(companyId);
		commerceVirtualOrderItemImpl.setUserId(userId);

		if (userName == null) {
			commerceVirtualOrderItemImpl.setUserName("");
		}
		else {
			commerceVirtualOrderItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceVirtualOrderItemImpl.setCreateDate(null);
		}
		else {
			commerceVirtualOrderItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceVirtualOrderItemImpl.setModifiedDate(null);
		}
		else {
			commerceVirtualOrderItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceVirtualOrderItemImpl.setCommerceOrderItemId(commerceOrderItemId);
		commerceVirtualOrderItemImpl.setFileEntryId(fileEntryId);

		if (url == null) {
			commerceVirtualOrderItemImpl.setUrl("");
		}
		else {
			commerceVirtualOrderItemImpl.setUrl(url);
		}

		commerceVirtualOrderItemImpl.setActivationStatus(activationStatus);
		commerceVirtualOrderItemImpl.setDuration(duration);
		commerceVirtualOrderItemImpl.setUsages(usages);
		commerceVirtualOrderItemImpl.setMaxUsages(maxUsages);
		commerceVirtualOrderItemImpl.setActive(active);

		if (startDate == Long.MIN_VALUE) {
			commerceVirtualOrderItemImpl.setStartDate(null);
		}
		else {
			commerceVirtualOrderItemImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			commerceVirtualOrderItemImpl.setEndDate(null);
		}
		else {
			commerceVirtualOrderItemImpl.setEndDate(new Date(endDate));
		}

		commerceVirtualOrderItemImpl.resetOriginalValues();

		return commerceVirtualOrderItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commerceVirtualOrderItemId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceOrderItemId = objectInput.readLong();

		fileEntryId = objectInput.readLong();
		url = objectInput.readUTF();

		activationStatus = objectInput.readInt();

		duration = objectInput.readLong();

		usages = objectInput.readInt();

		maxUsages = objectInput.readInt();

		active = objectInput.readBoolean();
		startDate = objectInput.readLong();
		endDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(commerceVirtualOrderItemId);

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

		objectOutput.writeLong(commerceOrderItemId);

		objectOutput.writeLong(fileEntryId);

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}

		objectOutput.writeInt(activationStatus);

		objectOutput.writeLong(duration);

		objectOutput.writeInt(usages);

		objectOutput.writeInt(maxUsages);

		objectOutput.writeBoolean(active);
		objectOutput.writeLong(startDate);
		objectOutput.writeLong(endDate);
	}

	public String uuid;
	public long commerceVirtualOrderItemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceOrderItemId;
	public long fileEntryId;
	public String url;
	public int activationStatus;
	public long duration;
	public int usages;
	public int maxUsages;
	public boolean active;
	public long startDate;
	public long endDate;
}