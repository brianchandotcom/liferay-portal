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

import com.liferay.commerce.model.CPDefinitionInventory;
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
 * The cache model class for representing CPDefinitionInventory in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CPDefinitionInventoryCacheModel
	implements CacheModel<CPDefinitionInventory>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDefinitionInventoryCacheModel)) {
			return false;
		}

		CPDefinitionInventoryCacheModel cpDefinitionInventoryCacheModel =
			(CPDefinitionInventoryCacheModel)object;

		if ((CPDefinitionInventoryId ==
				cpDefinitionInventoryCacheModel.CPDefinitionInventoryId) &&
			(mvccVersion == cpDefinitionInventoryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, CPDefinitionInventoryId);

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
		sb.append(", minOrderQuantity=");
		sb.append(minOrderQuantity);
		sb.append(", maxOrderQuantity=");
		sb.append(maxOrderQuantity);
		sb.append(", allowedOrderQuantities=");
		sb.append(allowedOrderQuantities);
		sb.append(", multipleOrderQuantity=");
		sb.append(multipleOrderQuantity);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPDefinitionInventory toEntityModel() {
		CPDefinitionInventoryImpl cpDefinitionInventoryImpl =
			new CPDefinitionInventoryImpl();

		cpDefinitionInventoryImpl.setMvccVersion(mvccVersion);
		cpDefinitionInventoryImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			cpDefinitionInventoryImpl.setUuid("");
		}
		else {
			cpDefinitionInventoryImpl.setUuid(uuid);
		}

		cpDefinitionInventoryImpl.setCPDefinitionInventoryId(
			CPDefinitionInventoryId);
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
			cpDefinitionInventoryImpl.setCPDefinitionInventoryEngine(
				CPDefinitionInventoryEngine);
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
		cpDefinitionInventoryImpl.setMinOrderQuantity(minOrderQuantity);
		cpDefinitionInventoryImpl.setMaxOrderQuantity(maxOrderQuantity);

		if (allowedOrderQuantities == null) {
			cpDefinitionInventoryImpl.setAllowedOrderQuantities("");
		}
		else {
			cpDefinitionInventoryImpl.setAllowedOrderQuantities(
				allowedOrderQuantities);
		}

		cpDefinitionInventoryImpl.setMultipleOrderQuantity(
			multipleOrderQuantity);

		cpDefinitionInventoryImpl.resetOriginalValues();

		return cpDefinitionInventoryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
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

		minOrderQuantity = objectInput.readInt();

		maxOrderQuantity = objectInput.readInt();
		allowedOrderQuantities = objectInput.readUTF();

		multipleOrderQuantity = objectInput.readInt();
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

		objectOutput.writeInt(minOrderQuantity);

		objectOutput.writeInt(maxOrderQuantity);

		if (allowedOrderQuantities == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(allowedOrderQuantities);
		}

		objectOutput.writeInt(multipleOrderQuantity);
	}

	public long mvccVersion;
	public long ctCollectionId;
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
	public int minOrderQuantity;
	public int maxOrderQuantity;
	public String allowedOrderQuantities;
	public int multipleOrderQuantity;

}