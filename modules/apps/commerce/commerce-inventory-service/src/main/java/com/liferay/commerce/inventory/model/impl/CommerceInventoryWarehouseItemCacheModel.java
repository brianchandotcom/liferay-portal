/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import java.math.BigDecimal;

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
		StringBundler sb = new StringBundler(29);

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
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append(", reservedQuantity=");
		sb.append(reservedQuantity);
		sb.append(", sku=");
		sb.append(sku);
		sb.append(", unitOfMeasureKey=");
		sb.append(unitOfMeasureKey);
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
		commerceInventoryWarehouseItemImpl.setQuantity(quantity);
		commerceInventoryWarehouseItemImpl.setReservedQuantity(
			reservedQuantity);

		if (sku == null) {
			commerceInventoryWarehouseItemImpl.setSku("");
		}
		else {
			commerceInventoryWarehouseItemImpl.setSku(sku);
		}

		if (unitOfMeasureKey == null) {
			commerceInventoryWarehouseItemImpl.setUnitOfMeasureKey("");
		}
		else {
			commerceInventoryWarehouseItemImpl.setUnitOfMeasureKey(
				unitOfMeasureKey);
		}

		commerceInventoryWarehouseItemImpl.resetOriginalValues();

		return commerceInventoryWarehouseItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

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
		quantity = (BigDecimal)objectInput.readObject();
		reservedQuantity = (BigDecimal)objectInput.readObject();
		sku = objectInput.readUTF();
		unitOfMeasureKey = objectInput.readUTF();
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
		objectOutput.writeObject(quantity);
		objectOutput.writeObject(reservedQuantity);

		if (sku == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sku);
		}

		if (unitOfMeasureKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(unitOfMeasureKey);
		}
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
	public BigDecimal quantity;
	public BigDecimal reservedQuantity;
	public String sku;
	public String unitOfMeasureKey;

}