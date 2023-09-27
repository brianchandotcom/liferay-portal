/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.model.impl;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
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
 * The cache model class for representing CommercePriceEntry in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceEntryCacheModel
	implements CacheModel<CommercePriceEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePriceEntryCacheModel)) {
			return false;
		}

		CommercePriceEntryCacheModel commercePriceEntryCacheModel =
			(CommercePriceEntryCacheModel)object;

		if ((commercePriceEntryId ==
				commercePriceEntryCacheModel.commercePriceEntryId) &&
			(mvccVersion == commercePriceEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commercePriceEntryId);

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
		StringBundler sb = new StringBundler(65);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commercePriceEntryId=");
		sb.append(commercePriceEntryId);
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
		sb.append(", commercePriceListId=");
		sb.append(commercePriceListId);
		sb.append(", CPInstanceUuid=");
		sb.append(CPInstanceUuid);
		sb.append(", CProductId=");
		sb.append(CProductId);
		sb.append(", bulkPricing=");
		sb.append(bulkPricing);
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
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", hasTierPrice=");
		sb.append(hasTierPrice);
		sb.append(", price=");
		sb.append(price);
		sb.append(", priceOnApplication=");
		sb.append(priceOnApplication);
		sb.append(", promoPrice=");
		sb.append(promoPrice);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append(", unitOfMeasureKey=");
		sb.append(unitOfMeasureKey);
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
	public CommercePriceEntry toEntityModel() {
		CommercePriceEntryImpl commercePriceEntryImpl =
			new CommercePriceEntryImpl();

		commercePriceEntryImpl.setMvccVersion(mvccVersion);
		commercePriceEntryImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			commercePriceEntryImpl.setUuid("");
		}
		else {
			commercePriceEntryImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commercePriceEntryImpl.setExternalReferenceCode("");
		}
		else {
			commercePriceEntryImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commercePriceEntryImpl.setCommercePriceEntryId(commercePriceEntryId);
		commercePriceEntryImpl.setCompanyId(companyId);
		commercePriceEntryImpl.setUserId(userId);

		if (userName == null) {
			commercePriceEntryImpl.setUserName("");
		}
		else {
			commercePriceEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePriceEntryImpl.setCreateDate(null);
		}
		else {
			commercePriceEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePriceEntryImpl.setModifiedDate(null);
		}
		else {
			commercePriceEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		commercePriceEntryImpl.setCommercePriceListId(commercePriceListId);

		if (CPInstanceUuid == null) {
			commercePriceEntryImpl.setCPInstanceUuid("");
		}
		else {
			commercePriceEntryImpl.setCPInstanceUuid(CPInstanceUuid);
		}

		commercePriceEntryImpl.setCProductId(CProductId);
		commercePriceEntryImpl.setBulkPricing(bulkPricing);
		commercePriceEntryImpl.setDiscountDiscovery(discountDiscovery);
		commercePriceEntryImpl.setDiscountLevel1(discountLevel1);
		commercePriceEntryImpl.setDiscountLevel2(discountLevel2);
		commercePriceEntryImpl.setDiscountLevel3(discountLevel3);
		commercePriceEntryImpl.setDiscountLevel4(discountLevel4);

		if (displayDate == Long.MIN_VALUE) {
			commercePriceEntryImpl.setDisplayDate(null);
		}
		else {
			commercePriceEntryImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			commercePriceEntryImpl.setExpirationDate(null);
		}
		else {
			commercePriceEntryImpl.setExpirationDate(new Date(expirationDate));
		}

		commercePriceEntryImpl.setHasTierPrice(hasTierPrice);
		commercePriceEntryImpl.setPrice(price);
		commercePriceEntryImpl.setPriceOnApplication(priceOnApplication);
		commercePriceEntryImpl.setPromoPrice(promoPrice);
		commercePriceEntryImpl.setQuantity(quantity);

		if (unitOfMeasureKey == null) {
			commercePriceEntryImpl.setUnitOfMeasureKey("");
		}
		else {
			commercePriceEntryImpl.setUnitOfMeasureKey(unitOfMeasureKey);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commercePriceEntryImpl.setLastPublishDate(null);
		}
		else {
			commercePriceEntryImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		commercePriceEntryImpl.setStatus(status);
		commercePriceEntryImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commercePriceEntryImpl.setStatusByUserName("");
		}
		else {
			commercePriceEntryImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commercePriceEntryImpl.setStatusDate(null);
		}
		else {
			commercePriceEntryImpl.setStatusDate(new Date(statusDate));
		}

		commercePriceEntryImpl.resetOriginalValues();

		return commercePriceEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commercePriceEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commercePriceListId = objectInput.readLong();
		CPInstanceUuid = objectInput.readUTF();

		CProductId = objectInput.readLong();

		bulkPricing = objectInput.readBoolean();

		discountDiscovery = objectInput.readBoolean();
		discountLevel1 = (BigDecimal)objectInput.readObject();
		discountLevel2 = (BigDecimal)objectInput.readObject();
		discountLevel3 = (BigDecimal)objectInput.readObject();
		discountLevel4 = (BigDecimal)objectInput.readObject();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();

		hasTierPrice = objectInput.readBoolean();
		price = (BigDecimal)objectInput.readObject();

		priceOnApplication = objectInput.readBoolean();
		promoPrice = (BigDecimal)objectInput.readObject();
		quantity = (BigDecimal)objectInput.readObject();
		unitOfMeasureKey = objectInput.readUTF();
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

		objectOutput.writeLong(commercePriceEntryId);

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

		objectOutput.writeLong(commercePriceListId);

		if (CPInstanceUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(CPInstanceUuid);
		}

		objectOutput.writeLong(CProductId);

		objectOutput.writeBoolean(bulkPricing);

		objectOutput.writeBoolean(discountDiscovery);
		objectOutput.writeObject(discountLevel1);
		objectOutput.writeObject(discountLevel2);
		objectOutput.writeObject(discountLevel3);
		objectOutput.writeObject(discountLevel4);
		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);

		objectOutput.writeBoolean(hasTierPrice);
		objectOutput.writeObject(price);

		objectOutput.writeBoolean(priceOnApplication);
		objectOutput.writeObject(promoPrice);
		objectOutput.writeObject(quantity);

		if (unitOfMeasureKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(unitOfMeasureKey);
		}

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
	public long commercePriceEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commercePriceListId;
	public String CPInstanceUuid;
	public long CProductId;
	public boolean bulkPricing;
	public boolean discountDiscovery;
	public BigDecimal discountLevel1;
	public BigDecimal discountLevel2;
	public BigDecimal discountLevel3;
	public BigDecimal discountLevel4;
	public long displayDate;
	public long expirationDate;
	public boolean hasTierPrice;
	public BigDecimal price;
	public boolean priceOnApplication;
	public BigDecimal promoPrice;
	public BigDecimal quantity;
	public String unitOfMeasureKey;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}