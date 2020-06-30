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

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

import java.util.Date;

/**
 * The cache model class for representing CommerceOrderItem in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceOrderItemCacheModel
	implements CacheModel<CommerceOrderItem>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceOrderItemCacheModel)) {
			return false;
		}

		CommerceOrderItemCacheModel commerceOrderItemCacheModel =
			(CommerceOrderItemCacheModel)obj;

		if (commerceOrderItemId ==
				commerceOrderItemCacheModel.commerceOrderItemId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceOrderItemId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(63);

		sb.append("{externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceOrderItemId=");
		sb.append(commerceOrderItemId);
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
		sb.append(", commerceOrderId=");
		sb.append(commerceOrderId);
		sb.append(", CProductId=");
		sb.append(CProductId);
		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append(", shippedQuantity=");
		sb.append(shippedQuantity);
		sb.append(", json=");
		sb.append(json);
		sb.append(", name=");
		sb.append(name);
		sb.append(", sku=");
		sb.append(sku);
		sb.append(", unitPrice=");
		sb.append(unitPrice);
		sb.append(", promoPrice=");
		sb.append(promoPrice);
		sb.append(", discountAmount=");
		sb.append(discountAmount);
		sb.append(", finalPrice=");
		sb.append(finalPrice);
		sb.append(", discountPercentageLevel1=");
		sb.append(discountPercentageLevel1);
		sb.append(", discountPercentageLevel2=");
		sb.append(discountPercentageLevel2);
		sb.append(", discountPercentageLevel3=");
		sb.append(discountPercentageLevel3);
		sb.append(", discountPercentageLevel4=");
		sb.append(discountPercentageLevel4);
		sb.append(", subscription=");
		sb.append(subscription);
		sb.append(", deliveryGroup=");
		sb.append(deliveryGroup);
		sb.append(", shippingAddressId=");
		sb.append(shippingAddressId);
		sb.append(", printedNote=");
		sb.append(printedNote);
		sb.append(", requestedDeliveryDate=");
		sb.append(requestedDeliveryDate);
		sb.append(", bookedQuantityId=");
		sb.append(bookedQuantityId);
		sb.append(", manuallyAdjusted=");
		sb.append(manuallyAdjusted);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceOrderItem toEntityModel() {
		CommerceOrderItemImpl commerceOrderItemImpl =
			new CommerceOrderItemImpl();

		if (externalReferenceCode == null) {
			commerceOrderItemImpl.setExternalReferenceCode("");
		}
		else {
			commerceOrderItemImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		commerceOrderItemImpl.setCommerceOrderItemId(commerceOrderItemId);
		commerceOrderItemImpl.setGroupId(groupId);
		commerceOrderItemImpl.setCompanyId(companyId);
		commerceOrderItemImpl.setUserId(userId);

		if (userName == null) {
			commerceOrderItemImpl.setUserName("");
		}
		else {
			commerceOrderItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceOrderItemImpl.setCreateDate(null);
		}
		else {
			commerceOrderItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceOrderItemImpl.setModifiedDate(null);
		}
		else {
			commerceOrderItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceOrderItemImpl.setCommerceOrderId(commerceOrderId);
		commerceOrderItemImpl.setCProductId(CProductId);
		commerceOrderItemImpl.setCPInstanceId(CPInstanceId);
		commerceOrderItemImpl.setQuantity(quantity);
		commerceOrderItemImpl.setShippedQuantity(shippedQuantity);

		if (json == null) {
			commerceOrderItemImpl.setJson("");
		}
		else {
			commerceOrderItemImpl.setJson(json);
		}

		if (name == null) {
			commerceOrderItemImpl.setName("");
		}
		else {
			commerceOrderItemImpl.setName(name);
		}

		if (sku == null) {
			commerceOrderItemImpl.setSku("");
		}
		else {
			commerceOrderItemImpl.setSku(sku);
		}

		commerceOrderItemImpl.setUnitPrice(unitPrice);
		commerceOrderItemImpl.setPromoPrice(promoPrice);
		commerceOrderItemImpl.setDiscountAmount(discountAmount);
		commerceOrderItemImpl.setFinalPrice(finalPrice);
		commerceOrderItemImpl.setDiscountPercentageLevel1(
			discountPercentageLevel1);
		commerceOrderItemImpl.setDiscountPercentageLevel2(
			discountPercentageLevel2);
		commerceOrderItemImpl.setDiscountPercentageLevel3(
			discountPercentageLevel3);
		commerceOrderItemImpl.setDiscountPercentageLevel4(
			discountPercentageLevel4);
		commerceOrderItemImpl.setSubscription(subscription);

		if (deliveryGroup == null) {
			commerceOrderItemImpl.setDeliveryGroup("");
		}
		else {
			commerceOrderItemImpl.setDeliveryGroup(deliveryGroup);
		}

		commerceOrderItemImpl.setShippingAddressId(shippingAddressId);

		if (printedNote == null) {
			commerceOrderItemImpl.setPrintedNote("");
		}
		else {
			commerceOrderItemImpl.setPrintedNote(printedNote);
		}

		if (requestedDeliveryDate == Long.MIN_VALUE) {
			commerceOrderItemImpl.setRequestedDeliveryDate(null);
		}
		else {
			commerceOrderItemImpl.setRequestedDeliveryDate(
				new Date(requestedDeliveryDate));
		}

		commerceOrderItemImpl.setBookedQuantityId(bookedQuantityId);
		commerceOrderItemImpl.setManuallyAdjusted(manuallyAdjusted);

		commerceOrderItemImpl.resetOriginalValues();

		return commerceOrderItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		externalReferenceCode = objectInput.readUTF();

		commerceOrderItemId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceOrderId = objectInput.readLong();

		CProductId = objectInput.readLong();

		CPInstanceId = objectInput.readLong();

		quantity = objectInput.readInt();

		shippedQuantity = objectInput.readInt();
		json = (String)objectInput.readObject();
		name = objectInput.readUTF();
		sku = objectInput.readUTF();
		unitPrice = (BigDecimal)objectInput.readObject();
		promoPrice = (BigDecimal)objectInput.readObject();
		discountAmount = (BigDecimal)objectInput.readObject();
		finalPrice = (BigDecimal)objectInput.readObject();
		discountPercentageLevel1 = (BigDecimal)objectInput.readObject();
		discountPercentageLevel2 = (BigDecimal)objectInput.readObject();
		discountPercentageLevel3 = (BigDecimal)objectInput.readObject();
		discountPercentageLevel4 = (BigDecimal)objectInput.readObject();

		subscription = objectInput.readBoolean();
		deliveryGroup = objectInput.readUTF();

		shippingAddressId = objectInput.readLong();
		printedNote = objectInput.readUTF();
		requestedDeliveryDate = objectInput.readLong();

		bookedQuantityId = objectInput.readLong();

		manuallyAdjusted = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(commerceOrderItemId);

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

		objectOutput.writeLong(commerceOrderId);

		objectOutput.writeLong(CProductId);

		objectOutput.writeLong(CPInstanceId);

		objectOutput.writeInt(quantity);

		objectOutput.writeInt(shippedQuantity);

		if (json == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(json);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (sku == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sku);
		}

		objectOutput.writeObject(unitPrice);
		objectOutput.writeObject(promoPrice);
		objectOutput.writeObject(discountAmount);
		objectOutput.writeObject(finalPrice);
		objectOutput.writeObject(discountPercentageLevel1);
		objectOutput.writeObject(discountPercentageLevel2);
		objectOutput.writeObject(discountPercentageLevel3);
		objectOutput.writeObject(discountPercentageLevel4);

		objectOutput.writeBoolean(subscription);

		if (deliveryGroup == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(deliveryGroup);
		}

		objectOutput.writeLong(shippingAddressId);

		if (printedNote == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(printedNote);
		}

		objectOutput.writeLong(requestedDeliveryDate);

		objectOutput.writeLong(bookedQuantityId);

		objectOutput.writeBoolean(manuallyAdjusted);
	}

	public String externalReferenceCode;
	public long commerceOrderItemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceOrderId;
	public long CProductId;
	public long CPInstanceId;
	public int quantity;
	public int shippedQuantity;
	public String json;
	public String name;
	public String sku;
	public BigDecimal unitPrice;
	public BigDecimal promoPrice;
	public BigDecimal discountAmount;
	public BigDecimal finalPrice;
	public BigDecimal discountPercentageLevel1;
	public BigDecimal discountPercentageLevel2;
	public BigDecimal discountPercentageLevel3;
	public BigDecimal discountPercentageLevel4;
	public boolean subscription;
	public String deliveryGroup;
	public long shippingAddressId;
	public String printedNote;
	public long requestedDeliveryDate;
	public long bookedQuantityId;
	public boolean manuallyAdjusted;

}