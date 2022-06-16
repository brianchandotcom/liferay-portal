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

package com.liferay.commerce.inventory.model.impl;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
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
 * The cache model class for representing CommerceInventoryWarehouseItem in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceInventoryWarehouseItemCacheModel
	implements CacheModel<CommerceInventoryWarehouseItem>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceInventoryWarehouseItemCacheModel)) {
			return false;
		}

		CommerceInventoryWarehouseItemCacheModel
			commerceInventoryWarehouseItemCacheModel =
				(CommerceInventoryWarehouseItemCacheModel)object;

		if ((commerceInventoryWarehouseItemId ==
				commerceInventoryWarehouseItemCacheModel.
					commerceInventoryWarehouseItemId) &&
			(mvccVersion ==
				commerceInventoryWarehouseItemCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceInventoryWarehouseItemId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceInventoryWarehouseItemId=");
		sb.append(commerceInventoryWarehouseItemId);
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
		sb.append(", commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);
		sb.append(", sku=");
		sb.append(sku);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append(", reservedQuantity=");
		sb.append(reservedQuantity);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceInventoryWarehouseItem toEntityModel() {
		CommerceInventoryWarehouseItemImpl commerceInventoryWarehouseItemImpl =
			new CommerceInventoryWarehouseItemImpl();

		commerceInventoryWarehouseItemImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceInventoryWarehouseItemImpl.setUuid("");
		}
		else {
			commerceInventoryWarehouseItemImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commerceInventoryWarehouseItemImpl.setExternalReferenceCode("");
		}
		else {
			commerceInventoryWarehouseItemImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commerceInventoryWarehouseItemImpl.setCommerceInventoryWarehouseItemId(
			commerceInventoryWarehouseItemId);
		commerceInventoryWarehouseItemImpl.setCompanyId(companyId);
		commerceInventoryWarehouseItemImpl.setUserId(userId);

		if (userName == null) {
			commerceInventoryWarehouseItemImpl.setUserName("");
		}
		else {
			commerceInventoryWarehouseItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceInventoryWarehouseItemImpl.setCreateDate(null);
		}
		else {
			commerceInventoryWarehouseItemImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceInventoryWarehouseItemImpl.setModifiedDate(null);
		}
		else {
			commerceInventoryWarehouseItemImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceInventoryWarehouseItemImpl.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);

		if (sku == null) {
			commerceInventoryWarehouseItemImpl.setSku("");
		}
		else {
			commerceInventoryWarehouseItemImpl.setSku(sku);
		}

		commerceInventoryWarehouseItemImpl.setQuantity(quantity);
		commerceInventoryWarehouseItemImpl.setReservedQuantity(
			reservedQuantity);

		commerceInventoryWarehouseItemImpl.resetOriginalValues();

		return commerceInventoryWarehouseItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commerceInventoryWarehouseItemId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceInventoryWarehouseId = objectInput.readLong();
		sku = objectInput.readUTF();

		quantity = objectInput.readInt();

		reservedQuantity = objectInput.readInt();
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

		objectOutput.writeLong(commerceInventoryWarehouseItemId);

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

		objectOutput.writeLong(commerceInventoryWarehouseId);

		if (sku == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sku);
		}

		objectOutput.writeInt(quantity);

		objectOutput.writeInt(reservedQuantity);
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long commerceInventoryWarehouseItemId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceInventoryWarehouseId;
	public String sku;
	public int quantity;
	public int reservedQuantity;

}