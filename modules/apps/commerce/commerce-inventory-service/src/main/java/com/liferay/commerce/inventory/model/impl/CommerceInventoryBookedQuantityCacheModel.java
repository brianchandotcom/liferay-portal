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

import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
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
 * The cache model class for representing CommerceInventoryBookedQuantity in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommerceInventoryBookedQuantityCacheModel
	implements CacheModel<CommerceInventoryBookedQuantity>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceInventoryBookedQuantityCacheModel)) {
			return false;
		}

		CommerceInventoryBookedQuantityCacheModel
			commerceInventoryBookedQuantityCacheModel =
				(CommerceInventoryBookedQuantityCacheModel)object;

		if ((commerceInventoryBookedQuantityId ==
				commerceInventoryBookedQuantityCacheModel.
					commerceInventoryBookedQuantityId) &&
			(mvccVersion ==
				commerceInventoryBookedQuantityCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceInventoryBookedQuantityId);

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
		StringBundler sb = new StringBundler(25);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commerceInventoryBookedQuantityId=");
		sb.append(commerceInventoryBookedQuantityId);
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
		sb.append(", bookedNote=");
		sb.append(bookedNote);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
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
	public CommerceInventoryBookedQuantity toEntityModel() {
		CommerceInventoryBookedQuantityImpl
			commerceInventoryBookedQuantityImpl =
				new CommerceInventoryBookedQuantityImpl();

		commerceInventoryBookedQuantityImpl.setMvccVersion(mvccVersion);
		commerceInventoryBookedQuantityImpl.
			setCommerceInventoryBookedQuantityId(
				commerceInventoryBookedQuantityId);
		commerceInventoryBookedQuantityImpl.setCompanyId(companyId);
		commerceInventoryBookedQuantityImpl.setUserId(userId);

		if (userName == null) {
			commerceInventoryBookedQuantityImpl.setUserName("");
		}
		else {
			commerceInventoryBookedQuantityImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceInventoryBookedQuantityImpl.setCreateDate(null);
		}
		else {
			commerceInventoryBookedQuantityImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceInventoryBookedQuantityImpl.setModifiedDate(null);
		}
		else {
			commerceInventoryBookedQuantityImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (bookedNote == null) {
			commerceInventoryBookedQuantityImpl.setBookedNote("");
		}
		else {
			commerceInventoryBookedQuantityImpl.setBookedNote(bookedNote);
		}

		if (expirationDate == Long.MIN_VALUE) {
			commerceInventoryBookedQuantityImpl.setExpirationDate(null);
		}
		else {
			commerceInventoryBookedQuantityImpl.setExpirationDate(
				new Date(expirationDate));
		}

		commerceInventoryBookedQuantityImpl.setQuantity(quantity);

		if (sku == null) {
			commerceInventoryBookedQuantityImpl.setSku("");
		}
		else {
			commerceInventoryBookedQuantityImpl.setSku(sku);
		}

		if (unitOfMeasureKey == null) {
			commerceInventoryBookedQuantityImpl.setUnitOfMeasureKey("");
		}
		else {
			commerceInventoryBookedQuantityImpl.setUnitOfMeasureKey(
				unitOfMeasureKey);
		}

		commerceInventoryBookedQuantityImpl.resetOriginalValues();

		return commerceInventoryBookedQuantityImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		commerceInventoryBookedQuantityId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		bookedNote = objectInput.readUTF();
		expirationDate = objectInput.readLong();

		quantity = objectInput.readInt();
		sku = objectInput.readUTF();
		unitOfMeasureKey = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commerceInventoryBookedQuantityId);

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

		if (bookedNote == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(bookedNote);
		}

		objectOutput.writeLong(expirationDate);

		objectOutput.writeInt(quantity);

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
	public long commerceInventoryBookedQuantityId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String bookedNote;
	public long expirationDate;
	public int quantity;
	public String sku;
	public String unitOfMeasureKey;

}