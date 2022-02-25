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

import com.liferay.commerce.model.CommerceShipmentItem;
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
 * The cache model class for representing CommerceShipmentItem in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShipmentItemCacheModel
	implements CacheModel<CommerceShipmentItem>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceShipmentItemCacheModel)) {
			return false;
		}

		CommerceShipmentItemCacheModel commerceShipmentItemCacheModel =
			(CommerceShipmentItemCacheModel)object;

		if ((commerceShipmentItemId ==
				commerceShipmentItemCacheModel.commerceShipmentItemId) &&
			(mvccVersion == commerceShipmentItemCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceShipmentItemId);

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
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceShipmentItemId=");
		sb.append(commerceShipmentItemId);
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
		sb.append(", commerceShipmentId=");
		sb.append(commerceShipmentId);
		sb.append(", commerceOrderItemId=");
		sb.append(commerceOrderItemId);
		sb.append(", commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceShipmentItem toEntityModel() {
		CommerceShipmentItemImpl commerceShipmentItemImpl =
			new CommerceShipmentItemImpl();

		commerceShipmentItemImpl.setMvccVersion(mvccVersion);

		if (externalReferenceCode == null) {
			commerceShipmentItemImpl.setExternalReferenceCode("");
		}
		else {
			commerceShipmentItemImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commerceShipmentItemImpl.setCommerceShipmentItemId(
			commerceShipmentItemId);
		commerceShipmentItemImpl.setGroupId(groupId);
		commerceShipmentItemImpl.setCompanyId(companyId);
		commerceShipmentItemImpl.setUserId(userId);

		if (userName == null) {
			commerceShipmentItemImpl.setUserName("");
		}
		else {
			commerceShipmentItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceShipmentItemImpl.setCreateDate(null);
		}
		else {
			commerceShipmentItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceShipmentItemImpl.setModifiedDate(null);
		}
		else {
			commerceShipmentItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceShipmentItemImpl.setCommerceShipmentId(commerceShipmentId);
		commerceShipmentItemImpl.setCommerceOrderItemId(commerceOrderItemId);
		commerceShipmentItemImpl.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
		commerceShipmentItemImpl.setQuantity(quantity);

		commerceShipmentItemImpl.resetOriginalValues();

		return commerceShipmentItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		externalReferenceCode = objectInput.readUTF();

		commerceShipmentItemId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceShipmentId = objectInput.readLong();

		commerceOrderItemId = objectInput.readLong();

		commerceInventoryWarehouseId = objectInput.readLong();

		quantity = objectInput.readInt();
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

		objectOutput.writeLong(commerceShipmentItemId);

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

		objectOutput.writeLong(commerceShipmentId);

		objectOutput.writeLong(commerceOrderItemId);

		objectOutput.writeLong(commerceInventoryWarehouseId);

		objectOutput.writeInt(quantity);
	}

	public long mvccVersion;
	public String externalReferenceCode;
	public long commerceShipmentItemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceShipmentId;
	public long commerceOrderItemId;
	public long commerceInventoryWarehouseId;
	public int quantity;

}