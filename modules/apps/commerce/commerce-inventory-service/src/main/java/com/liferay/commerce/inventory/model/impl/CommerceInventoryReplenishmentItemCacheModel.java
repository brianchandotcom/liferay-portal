/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import java.math.BigDecimal;

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
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
		sb.append(", availabilityDate=");
		sb.append(availabilityDate);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append(", sku=");
		sb.append(sku);
		sb.append(", unitOfMeasureKey=");
		sb.append(unitOfMeasureKey);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceInventoryReplenishmentItem toEntityModel() {
		CommerceInventoryReplenishmentItemImpl
			commerceInventoryReplenishmentItemImpl =
				new CommerceInventoryReplenishmentItemImpl();

		commerceInventoryReplenishmentItemImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceInventoryReplenishmentItemImpl.setUuid("");
		}
		else {
			commerceInventoryReplenishmentItemImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commerceInventoryReplenishmentItemImpl.setExternalReferenceCode("");
		}
		else {
			commerceInventoryReplenishmentItemImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

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

		if (availabilityDate == Long.MIN_VALUE) {
			commerceInventoryReplenishmentItemImpl.setAvailabilityDate(null);
		}
		else {
			commerceInventoryReplenishmentItemImpl.setAvailabilityDate(
				new Date(availabilityDate));
		}

		commerceInventoryReplenishmentItemImpl.setQuantity(quantity);

		if (sku == null) {
			commerceInventoryReplenishmentItemImpl.setSku("");
		}
		else {
			commerceInventoryReplenishmentItemImpl.setSku(sku);
		}

		if (unitOfMeasureKey == null) {
			commerceInventoryReplenishmentItemImpl.setUnitOfMeasureKey("");
		}
		else {
			commerceInventoryReplenishmentItemImpl.setUnitOfMeasureKey(
				unitOfMeasureKey);
		}

		commerceInventoryReplenishmentItemImpl.resetOriginalValues();

		return commerceInventoryReplenishmentItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commerceInventoryReplenishmentItemId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceInventoryWarehouseId = objectInput.readLong();
		availabilityDate = objectInput.readLong();
		quantity = (BigDecimal)objectInput.readObject();
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
		objectOutput.writeLong(availabilityDate);
		objectOutput.writeObject(quantity);

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
	public long commerceInventoryReplenishmentItemId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceInventoryWarehouseId;
	public long availabilityDate;
	public BigDecimal quantity;
	public String sku;
	public String unitOfMeasureKey;

}