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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceInventory;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceInventory in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceInventory
 * @generated
 */
@ProviderType
public class CommerceInventoryCacheModel implements CacheModel<CommerceInventory>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceInventoryCacheModel)) {
			return false;
		}

		CommerceInventoryCacheModel commerceInventoryCacheModel = (CommerceInventoryCacheModel)obj;

		if (commerceInventoryId == commerceInventoryCacheModel.commerceInventoryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceInventoryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(39);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceInventoryId=");
		sb.append(commerceInventoryId);
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
		sb.append(", commerceInventoryEngine=");
		sb.append(commerceInventoryEngine);
		sb.append(", lowStockActivity=");
		sb.append(lowStockActivity);
		sb.append(", displayAvailability=");
		sb.append(displayAvailability);
		sb.append(", displayStockQuantity=");
		sb.append(displayStockQuantity);
		sb.append(", minStockQuantity=");
		sb.append(minStockQuantity);
		sb.append(", backOrders=");
		sb.append(backOrders);
		sb.append(", minCartQuantity=");
		sb.append(minCartQuantity);
		sb.append(", maxCartQuantity=");
		sb.append(maxCartQuantity);
		sb.append(", allowedCartQuantities=");
		sb.append(allowedCartQuantities);
		sb.append(", multipleCartQuantity=");
		sb.append(multipleCartQuantity);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceInventory toEntityModel() {
		CommerceInventoryImpl commerceInventoryImpl = new CommerceInventoryImpl();

		if (uuid == null) {
			commerceInventoryImpl.setUuid(StringPool.BLANK);
		}
		else {
			commerceInventoryImpl.setUuid(uuid);
		}

		commerceInventoryImpl.setCommerceInventoryId(commerceInventoryId);
		commerceInventoryImpl.setGroupId(groupId);
		commerceInventoryImpl.setCompanyId(companyId);
		commerceInventoryImpl.setUserId(userId);

		if (userName == null) {
			commerceInventoryImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceInventoryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceInventoryImpl.setCreateDate(null);
		}
		else {
			commerceInventoryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceInventoryImpl.setModifiedDate(null);
		}
		else {
			commerceInventoryImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceInventoryImpl.setCPDefinitionId(CPDefinitionId);

		if (commerceInventoryEngine == null) {
			commerceInventoryImpl.setCommerceInventoryEngine(StringPool.BLANK);
		}
		else {
			commerceInventoryImpl.setCommerceInventoryEngine(commerceInventoryEngine);
		}

		if (lowStockActivity == null) {
			commerceInventoryImpl.setLowStockActivity(StringPool.BLANK);
		}
		else {
			commerceInventoryImpl.setLowStockActivity(lowStockActivity);
		}

		commerceInventoryImpl.setDisplayAvailability(displayAvailability);
		commerceInventoryImpl.setDisplayStockQuantity(displayStockQuantity);
		commerceInventoryImpl.setMinStockQuantity(minStockQuantity);
		commerceInventoryImpl.setBackOrders(backOrders);
		commerceInventoryImpl.setMinCartQuantity(minCartQuantity);
		commerceInventoryImpl.setMaxCartQuantity(maxCartQuantity);

		if (allowedCartQuantities == null) {
			commerceInventoryImpl.setAllowedCartQuantities(StringPool.BLANK);
		}
		else {
			commerceInventoryImpl.setAllowedCartQuantities(allowedCartQuantities);
		}

		commerceInventoryImpl.setMultipleCartQuantity(multipleCartQuantity);

		commerceInventoryImpl.resetOriginalValues();

		return commerceInventoryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commerceInventoryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();
		commerceInventoryEngine = objectInput.readUTF();
		lowStockActivity = objectInput.readUTF();

		displayAvailability = objectInput.readBoolean();

		displayStockQuantity = objectInput.readBoolean();

		minStockQuantity = objectInput.readInt();

		backOrders = objectInput.readBoolean();

		minCartQuantity = objectInput.readInt();

		maxCartQuantity = objectInput.readInt();
		allowedCartQuantities = objectInput.readUTF();

		multipleCartQuantity = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(commerceInventoryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(CPDefinitionId);

		if (commerceInventoryEngine == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(commerceInventoryEngine);
		}

		if (lowStockActivity == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(lowStockActivity);
		}

		objectOutput.writeBoolean(displayAvailability);

		objectOutput.writeBoolean(displayStockQuantity);

		objectOutput.writeInt(minStockQuantity);

		objectOutput.writeBoolean(backOrders);

		objectOutput.writeInt(minCartQuantity);

		objectOutput.writeInt(maxCartQuantity);

		if (allowedCartQuantities == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(allowedCartQuantities);
		}

		objectOutput.writeInt(multipleCartQuantity);
	}

	public String uuid;
	public long commerceInventoryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public String commerceInventoryEngine;
	public String lowStockActivity;
	public boolean displayAvailability;
	public boolean displayStockQuantity;
	public int minStockQuantity;
	public boolean backOrders;
	public int minCartQuantity;
	public int maxCartQuantity;
	public String allowedCartQuantities;
	public int multipleCartQuantity;
}