/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import java.math.BigDecimal;

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
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		commerceInventoryBookedQuantityId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		bookedNote = objectInput.readUTF();
		expirationDate = objectInput.readLong();
		quantity = (BigDecimal)objectInput.readObject();
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
	public long commerceInventoryBookedQuantityId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String bookedNote;
	public long expirationDate;
	public BigDecimal quantity;
	public String sku;
	public String unitOfMeasureKey;

}