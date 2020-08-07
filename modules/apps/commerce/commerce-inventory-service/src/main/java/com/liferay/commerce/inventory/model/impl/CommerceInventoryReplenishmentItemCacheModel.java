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

import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
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
 * The cache model class for representing CommerceInventoryReplenishmentItem in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceInventoryReplenishmentItemCacheModel
	implements CacheModel<CommerceInventoryReplenishmentItem>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceInventoryReplenishmentItemCacheModel)) {
			return false;
		}

		CommerceInventoryReplenishmentItemCacheModel
			commerceInventoryReplenishmentItemCacheModel =
				(CommerceInventoryReplenishmentItemCacheModel)object;

		if ((commerceInventoryReplenishmentItemId ==
				commerceInventoryReplenishmentItemCacheModel.
					commerceInventoryReplenishmentItemId) &&
			(mvccVersion ==
				commerceInventoryReplenishmentItemCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceInventoryReplenishmentItemId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commerceInventoryReplenishmentItemId=");
		sb.append(commerceInventoryReplenishmentItemId);
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
		sb.append(", availabilityDate=");
		sb.append(availabilityDate);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceInventoryReplenishmentItem toEntityModel() {
		CommerceInventoryReplenishmentItemImpl
			commerceInventoryReplenishmentItemImpl =
				new CommerceInventoryReplenishmentItemImpl();

		commerceInventoryReplenishmentItemImpl.setMvccVersion(mvccVersion);
		commerceInventoryReplenishmentItemImpl.
			setCommerceInventoryReplenishmentItemId(
				commerceInventoryReplenishmentItemId);
		commerceInventoryReplenishmentItemImpl.setCompanyId(companyId);
		commerceInventoryReplenishmentItemImpl.setUserId(userId);

		if (userName == null) {
			commerceInventoryReplenishmentItemImpl.setUserName("");
		}
		else {
			commerceInventoryReplenishmentItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceInventoryReplenishmentItemImpl.setCreateDate(null);
		}
		else {
			commerceInventoryReplenishmentItemImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceInventoryReplenishmentItemImpl.setModifiedDate(null);
		}
		else {
			commerceInventoryReplenishmentItemImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceInventoryReplenishmentItemImpl.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);

		if (sku == null) {
			commerceInventoryReplenishmentItemImpl.setSku("");
		}
		else {
			commerceInventoryReplenishmentItemImpl.setSku(sku);
		}

		if (availabilityDate == Long.MIN_VALUE) {
			commerceInventoryReplenishmentItemImpl.setAvailabilityDate(null);
		}
		else {
			commerceInventoryReplenishmentItemImpl.setAvailabilityDate(
				new Date(availabilityDate));
		}

		commerceInventoryReplenishmentItemImpl.setQuantity(quantity);

		commerceInventoryReplenishmentItemImpl.resetOriginalValues();

		return commerceInventoryReplenishmentItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceInventoryReplenishmentItemId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceInventoryWarehouseId = objectInput.readLong();
		sku = objectInput.readUTF();
		availabilityDate = objectInput.readLong();

		quantity = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceInventoryReplenishmentItemId);

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

		objectOutput.writeLong(availabilityDate);

		objectOutput.writeInt(quantity);
	}

	public long mvccVersion;
	public long commerceInventoryReplenishmentItemId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceInventoryWarehouseId;
	public String sku;
	public long availabilityDate;
	public int quantity;

}