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

package com.liferay.commerce.price.list.model.impl;

import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
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
 * The cache model class for representing CommerceTierPriceEntry in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceTierPriceEntryCacheModel
	implements CacheModel<CommerceTierPriceEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceTierPriceEntryCacheModel)) {
			return false;
		}

		CommerceTierPriceEntryCacheModel commerceTierPriceEntryCacheModel =
			(CommerceTierPriceEntryCacheModel)object;

		if ((commerceTierPriceEntryId ==
				commerceTierPriceEntryCacheModel.commerceTierPriceEntryId) &&
			(mvccVersion == commerceTierPriceEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceTierPriceEntryId);

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
		StringBundler sb = new StringBundler(53);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceTierPriceEntryId=");
		sb.append(commerceTierPriceEntryId);
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
		sb.append(", commercePriceEntryId=");
		sb.append(commercePriceEntryId);
		sb.append(", price=");
		sb.append(price);
		sb.append(", promoPrice=");
		sb.append(promoPrice);
		sb.append(", discountDiscovery=");
		sb.append(discountDiscovery);
		sb.append(", discountLevel1=");
		sb.append(discountLevel1);
		sb.append(", discountLevel2=");
		sb.append(discountLevel2);
		sb.append(", discountLevel3=");
		sb.append(discountLevel3);
		sb.append(", discountLevel4=");
		sb.append(discountLevel4);
		sb.append(", minQuantity=");
		sb.append(minQuantity);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceTierPriceEntry toEntityModel() {
		CommerceTierPriceEntryImpl commerceTierPriceEntryImpl =
			new CommerceTierPriceEntryImpl();

		commerceTierPriceEntryImpl.setMvccVersion(mvccVersion);
		commerceTierPriceEntryImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			commerceTierPriceEntryImpl.setUuid("");
		}
		else {
			commerceTierPriceEntryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commerceTierPriceEntryImpl.setExternalReferenceCode("");
		}
		else {
			commerceTierPriceEntryImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commerceTierPriceEntryImpl.setCommerceTierPriceEntryId(
			commerceTierPriceEntryId);
		commerceTierPriceEntryImpl.setCompanyId(companyId);
		commerceTierPriceEntryImpl.setUserId(userId);

		if (userName == null) {
			commerceTierPriceEntryImpl.setUserName("");
		}
		else {
			commerceTierPriceEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTierPriceEntryImpl.setCreateDate(null);
		}
		else {
			commerceTierPriceEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTierPriceEntryImpl.setModifiedDate(null);
		}
		else {
			commerceTierPriceEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceTierPriceEntryImpl.setCommercePriceEntryId(
			commercePriceEntryId);
		commerceTierPriceEntryImpl.setPrice(price);
		commerceTierPriceEntryImpl.setPromoPrice(promoPrice);
		commerceTierPriceEntryImpl.setDiscountDiscovery(discountDiscovery);
		commerceTierPriceEntryImpl.setDiscountLevel1(discountLevel1);
		commerceTierPriceEntryImpl.setDiscountLevel2(discountLevel2);
		commerceTierPriceEntryImpl.setDiscountLevel3(discountLevel3);
		commerceTierPriceEntryImpl.setDiscountLevel4(discountLevel4);
		commerceTierPriceEntryImpl.setMinQuantity(minQuantity);

		if (displayDate == Long.MIN_VALUE) {
			commerceTierPriceEntryImpl.setDisplayDate(null);
		}
		else {
			commerceTierPriceEntryImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			commerceTierPriceEntryImpl.setExpirationDate(null);
		}
		else {
			commerceTierPriceEntryImpl.setExpirationDate(
				new Date(expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceTierPriceEntryImpl.setLastPublishDate(null);
		}
		else {
			commerceTierPriceEntryImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		commerceTierPriceEntryImpl.setStatus(status);
		commerceTierPriceEntryImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commerceTierPriceEntryImpl.setStatusByUserName("");
		}
		else {
			commerceTierPriceEntryImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commerceTierPriceEntryImpl.setStatusDate(null);
		}
		else {
			commerceTierPriceEntryImpl.setStatusDate(new Date(statusDate));
		}

		commerceTierPriceEntryImpl.resetOriginalValues();

		return commerceTierPriceEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commerceTierPriceEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commercePriceEntryId = objectInput.readLong();
		price = (BigDecimal)objectInput.readObject();
		promoPrice = (BigDecimal)objectInput.readObject();

		discountDiscovery = objectInput.readBoolean();
		discountLevel1 = (BigDecimal)objectInput.readObject();
		discountLevel2 = (BigDecimal)objectInput.readObject();
		discountLevel3 = (BigDecimal)objectInput.readObject();
		discountLevel4 = (BigDecimal)objectInput.readObject();

		minQuantity = objectInput.readInt();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(commerceTierPriceEntryId);

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

		objectOutput.writeLong(commercePriceEntryId);
		objectOutput.writeObject(price);
		objectOutput.writeObject(promoPrice);

		objectOutput.writeBoolean(discountDiscovery);
		objectOutput.writeObject(discountLevel1);
		objectOutput.writeObject(discountLevel2);
		objectOutput.writeObject(discountLevel3);
		objectOutput.writeObject(discountLevel4);

		objectOutput.writeInt(minQuantity);
		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public String externalReferenceCode;
	public long commerceTierPriceEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commercePriceEntryId;
	public BigDecimal price;
	public BigDecimal promoPrice;
	public boolean discountDiscovery;
	public BigDecimal discountLevel1;
	public BigDecimal discountLevel2;
	public BigDecimal discountLevel3;
	public BigDecimal discountLevel4;
	public int minQuantity;
	public long displayDate;
	public long expirationDate;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}