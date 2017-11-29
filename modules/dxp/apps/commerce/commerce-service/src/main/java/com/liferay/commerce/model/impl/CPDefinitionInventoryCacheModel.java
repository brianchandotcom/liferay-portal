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

import com.liferay.commerce.model.CPDefinitionInventory;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CPDefinitionInventory in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionInventory
 * @generated
 */
@ProviderType
public class CPDefinitionInventoryCacheModel implements CacheModel<CPDefinitionInventory>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionInventoryCacheModel)) {
			return false;
		}

		CPDefinitionInventoryCacheModel cpDefinitionInventoryCacheModel = (CPDefinitionInventoryCacheModel)obj;

		if (CPDefinitionInventoryId == cpDefinitionInventoryCacheModel.CPDefinitionInventoryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPDefinitionInventoryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(39);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPDefinitionInventoryId=");
		sb.append(CPDefinitionInventoryId);
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
		sb.append(", CPDefinitionInventoryEngine=");
		sb.append(CPDefinitionInventoryEngine);
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
	public CPDefinitionInventory toEntityModel() {
		CPDefinitionInventoryImpl cpDefinitionInventoryImpl = new CPDefinitionInventoryImpl();

		if (uuid == null) {
			cpDefinitionInventoryImpl.setUuid("");
		}
		else {
			cpDefinitionInventoryImpl.setUuid(uuid);
		}

		cpDefinitionInventoryImpl.setCPDefinitionInventoryId(CPDefinitionInventoryId);
		cpDefinitionInventoryImpl.setGroupId(groupId);
		cpDefinitionInventoryImpl.setCompanyId(companyId);
		cpDefinitionInventoryImpl.setUserId(userId);

		if (userName == null) {
			cpDefinitionInventoryImpl.setUserName("");
		}
		else {
			cpDefinitionInventoryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpDefinitionInventoryImpl.setCreateDate(null);
		}
		else {
			cpDefinitionInventoryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpDefinitionInventoryImpl.setModifiedDate(null);
		}
		else {
			cpDefinitionInventoryImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpDefinitionInventoryImpl.setCPDefinitionId(CPDefinitionId);

		if (CPDefinitionInventoryEngine == null) {
			cpDefinitionInventoryImpl.setCPDefinitionInventoryEngine("");
		}
		else {
			cpDefinitionInventoryImpl.setCPDefinitionInventoryEngine(CPDefinitionInventoryEngine);
		}

		if (lowStockActivity == null) {
			cpDefinitionInventoryImpl.setLowStockActivity("");
		}
		else {
			cpDefinitionInventoryImpl.setLowStockActivity(lowStockActivity);
		}

		cpDefinitionInventoryImpl.setDisplayAvailability(displayAvailability);
		cpDefinitionInventoryImpl.setDisplayStockQuantity(displayStockQuantity);
		cpDefinitionInventoryImpl.setMinStockQuantity(minStockQuantity);
		cpDefinitionInventoryImpl.setBackOrders(backOrders);
		cpDefinitionInventoryImpl.setMinCartQuantity(minCartQuantity);
		cpDefinitionInventoryImpl.setMaxCartQuantity(maxCartQuantity);

		if (allowedCartQuantities == null) {
			cpDefinitionInventoryImpl.setAllowedCartQuantities("");
		}
		else {
			cpDefinitionInventoryImpl.setAllowedCartQuantities(allowedCartQuantities);
		}

		cpDefinitionInventoryImpl.setMultipleCartQuantity(multipleCartQuantity);

		cpDefinitionInventoryImpl.resetOriginalValues();

		return cpDefinitionInventoryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPDefinitionInventoryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();
		CPDefinitionInventoryEngine = objectInput.readUTF();
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
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPDefinitionInventoryId);

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

		if (CPDefinitionInventoryEngine == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(CPDefinitionInventoryEngine);
		}

		if (lowStockActivity == null) {
			objectOutput.writeUTF("");
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
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(allowedCartQuantities);
		}

		objectOutput.writeInt(multipleCartQuantity);
	}

	public String uuid;
	public long CPDefinitionInventoryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public String CPDefinitionInventoryEngine;
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