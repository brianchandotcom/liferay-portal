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

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.math.BigDecimal;

import java.util.Date;

/**
 * The cache model class for representing CommerceOrder in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceOrderCacheModel
	implements CacheModel<CommerceOrder>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceOrderCacheModel)) {
			return false;
		}

		CommerceOrderCacheModel commerceOrderCacheModel =
			(CommerceOrderCacheModel)object;

		if (commerceOrderId == commerceOrderCacheModel.commerceOrderId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceOrderId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(139);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceOrderId=");
		sb.append(commerceOrderId);
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
		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);
		sb.append(", commerceCurrencyId=");
		sb.append(commerceCurrencyId);
		sb.append(", commerceOrderTypeId=");
		sb.append(commerceOrderTypeId);
		sb.append(", billingAddressId=");
		sb.append(billingAddressId);
		sb.append(", shippingAddressId=");
		sb.append(shippingAddressId);
		sb.append(", commercePaymentMethodKey=");
		sb.append(commercePaymentMethodKey);
		sb.append(", transactionId=");
		sb.append(transactionId);
		sb.append(", commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);
		sb.append(", shippingOptionName=");
		sb.append(shippingOptionName);
		sb.append(", purchaseOrderNumber=");
		sb.append(purchaseOrderNumber);
		sb.append(", couponCode=");
		sb.append(couponCode);
		sb.append(", lastPriceUpdateDate=");
		sb.append(lastPriceUpdateDate);
		sb.append(", subtotal=");
		sb.append(subtotal);
		sb.append(", subtotalDiscountAmount=");
		sb.append(subtotalDiscountAmount);
		sb.append(", subtotalDiscountPercentageLevel1=");
		sb.append(subtotalDiscountPercentageLevel1);
		sb.append(", subtotalDiscountPercentageLevel2=");
		sb.append(subtotalDiscountPercentageLevel2);
		sb.append(", subtotalDiscountPercentageLevel3=");
		sb.append(subtotalDiscountPercentageLevel3);
		sb.append(", subtotalDiscountPercentageLevel4=");
		sb.append(subtotalDiscountPercentageLevel4);
		sb.append(", shippingAmount=");
		sb.append(shippingAmount);
		sb.append(", shippingDiscountAmount=");
		sb.append(shippingDiscountAmount);
		sb.append(", shippingDiscountPercentageLevel1=");
		sb.append(shippingDiscountPercentageLevel1);
		sb.append(", shippingDiscountPercentageLevel2=");
		sb.append(shippingDiscountPercentageLevel2);
		sb.append(", shippingDiscountPercentageLevel3=");
		sb.append(shippingDiscountPercentageLevel3);
		sb.append(", shippingDiscountPercentageLevel4=");
		sb.append(shippingDiscountPercentageLevel4);
		sb.append(", taxAmount=");
		sb.append(taxAmount);
		sb.append(", total=");
		sb.append(total);
		sb.append(", totalDiscountAmount=");
		sb.append(totalDiscountAmount);
		sb.append(", totalDiscountPercentageLevel1=");
		sb.append(totalDiscountPercentageLevel1);
		sb.append(", totalDiscountPercentageLevel2=");
		sb.append(totalDiscountPercentageLevel2);
		sb.append(", totalDiscountPercentageLevel3=");
		sb.append(totalDiscountPercentageLevel3);
		sb.append(", totalDiscountPercentageLevel4=");
		sb.append(totalDiscountPercentageLevel4);
		sb.append(", subtotalWithTaxAmount=");
		sb.append(subtotalWithTaxAmount);
		sb.append(", subtotalDiscountWithTaxAmount=");
		sb.append(subtotalDiscountWithTaxAmount);
		sb.append(", subtotalDiscountPercentageLevel1WithTaxAmount=");
		sb.append(subtotalDiscountPercentageLevel1WithTaxAmount);
		sb.append(", subtotalDiscountPercentageLevel2WithTaxAmount=");
		sb.append(subtotalDiscountPercentageLevel2WithTaxAmount);
		sb.append(", subtotalDiscountPercentageLevel3WithTaxAmount=");
		sb.append(subtotalDiscountPercentageLevel3WithTaxAmount);
		sb.append(", subtotalDiscountPercentageLevel4WithTaxAmount=");
		sb.append(subtotalDiscountPercentageLevel4WithTaxAmount);
		sb.append(", shippingWithTaxAmount=");
		sb.append(shippingWithTaxAmount);
		sb.append(", shippingDiscountWithTaxAmount=");
		sb.append(shippingDiscountWithTaxAmount);
		sb.append(", shippingDiscountPercentageLevel1WithTaxAmount=");
		sb.append(shippingDiscountPercentageLevel1WithTaxAmount);
		sb.append(", shippingDiscountPercentageLevel2WithTaxAmount=");
		sb.append(shippingDiscountPercentageLevel2WithTaxAmount);
		sb.append(", shippingDiscountPercentageLevel3WithTaxAmount=");
		sb.append(shippingDiscountPercentageLevel3WithTaxAmount);
		sb.append(", shippingDiscountPercentageLevel4WithTaxAmount=");
		sb.append(shippingDiscountPercentageLevel4WithTaxAmount);
		sb.append(", totalWithTaxAmount=");
		sb.append(totalWithTaxAmount);
		sb.append(", totalDiscountWithTaxAmount=");
		sb.append(totalDiscountWithTaxAmount);
		sb.append(", totalDiscountPercentageLevel1WithTaxAmount=");
		sb.append(totalDiscountPercentageLevel1WithTaxAmount);
		sb.append(", totalDiscountPercentageLevel2WithTaxAmount=");
		sb.append(totalDiscountPercentageLevel2WithTaxAmount);
		sb.append(", totalDiscountPercentageLevel3WithTaxAmount=");
		sb.append(totalDiscountPercentageLevel3WithTaxAmount);
		sb.append(", totalDiscountPercentageLevel4WithTaxAmount=");
		sb.append(totalDiscountPercentageLevel4WithTaxAmount);
		sb.append(", advanceStatus=");
		sb.append(advanceStatus);
		sb.append(", paymentStatus=");
		sb.append(paymentStatus);
		sb.append(", orderDate=");
		sb.append(orderDate);
		sb.append(", orderStatus=");
		sb.append(orderStatus);
		sb.append(", printedNote=");
		sb.append(printedNote);
		sb.append(", requestedDeliveryDate=");
		sb.append(requestedDeliveryDate);
		sb.append(", manuallyAdjusted=");
		sb.append(manuallyAdjusted);
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
	public CommerceOrder toEntityModel() {
		CommerceOrderImpl commerceOrderImpl = new CommerceOrderImpl();

		if (uuid == null) {
			commerceOrderImpl.setUuid("");
		}
		else {
			commerceOrderImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			commerceOrderImpl.setExternalReferenceCode("");
		}
		else {
			commerceOrderImpl.setExternalReferenceCode(externalReferenceCode);
		}

		commerceOrderImpl.setCommerceOrderId(commerceOrderId);
		commerceOrderImpl.setGroupId(groupId);
		commerceOrderImpl.setCompanyId(companyId);
		commerceOrderImpl.setUserId(userId);

		if (userName == null) {
			commerceOrderImpl.setUserName("");
		}
		else {
			commerceOrderImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceOrderImpl.setCreateDate(null);
		}
		else {
			commerceOrderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceOrderImpl.setModifiedDate(null);
		}
		else {
			commerceOrderImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceOrderImpl.setCommerceAccountId(commerceAccountId);
		commerceOrderImpl.setCommerceCurrencyId(commerceCurrencyId);
		commerceOrderImpl.setCommerceOrderTypeId(commerceOrderTypeId);
		commerceOrderImpl.setBillingAddressId(billingAddressId);
		commerceOrderImpl.setShippingAddressId(shippingAddressId);

		if (commercePaymentMethodKey == null) {
			commerceOrderImpl.setCommercePaymentMethodKey("");
		}
		else {
			commerceOrderImpl.setCommercePaymentMethodKey(
				commercePaymentMethodKey);
		}

		if (transactionId == null) {
			commerceOrderImpl.setTransactionId("");
		}
		else {
			commerceOrderImpl.setTransactionId(transactionId);
		}

		commerceOrderImpl.setCommerceShippingMethodId(commerceShippingMethodId);

		if (shippingOptionName == null) {
			commerceOrderImpl.setShippingOptionName("");
		}
		else {
			commerceOrderImpl.setShippingOptionName(shippingOptionName);
		}

		if (purchaseOrderNumber == null) {
			commerceOrderImpl.setPurchaseOrderNumber("");
		}
		else {
			commerceOrderImpl.setPurchaseOrderNumber(purchaseOrderNumber);
		}

		if (couponCode == null) {
			commerceOrderImpl.setCouponCode("");
		}
		else {
			commerceOrderImpl.setCouponCode(couponCode);
		}

		if (lastPriceUpdateDate == Long.MIN_VALUE) {
			commerceOrderImpl.setLastPriceUpdateDate(null);
		}
		else {
			commerceOrderImpl.setLastPriceUpdateDate(
				new Date(lastPriceUpdateDate));
		}

		commerceOrderImpl.setSubtotal(subtotal);
		commerceOrderImpl.setSubtotalDiscountAmount(subtotalDiscountAmount);
		commerceOrderImpl.setSubtotalDiscountPercentageLevel1(
			subtotalDiscountPercentageLevel1);
		commerceOrderImpl.setSubtotalDiscountPercentageLevel2(
			subtotalDiscountPercentageLevel2);
		commerceOrderImpl.setSubtotalDiscountPercentageLevel3(
			subtotalDiscountPercentageLevel3);
		commerceOrderImpl.setSubtotalDiscountPercentageLevel4(
			subtotalDiscountPercentageLevel4);
		commerceOrderImpl.setShippingAmount(shippingAmount);
		commerceOrderImpl.setShippingDiscountAmount(shippingDiscountAmount);
		commerceOrderImpl.setShippingDiscountPercentageLevel1(
			shippingDiscountPercentageLevel1);
		commerceOrderImpl.setShippingDiscountPercentageLevel2(
			shippingDiscountPercentageLevel2);
		commerceOrderImpl.setShippingDiscountPercentageLevel3(
			shippingDiscountPercentageLevel3);
		commerceOrderImpl.setShippingDiscountPercentageLevel4(
			shippingDiscountPercentageLevel4);
		commerceOrderImpl.setTaxAmount(taxAmount);
		commerceOrderImpl.setTotal(total);
		commerceOrderImpl.setTotalDiscountAmount(totalDiscountAmount);
		commerceOrderImpl.setTotalDiscountPercentageLevel1(
			totalDiscountPercentageLevel1);
		commerceOrderImpl.setTotalDiscountPercentageLevel2(
			totalDiscountPercentageLevel2);
		commerceOrderImpl.setTotalDiscountPercentageLevel3(
			totalDiscountPercentageLevel3);
		commerceOrderImpl.setTotalDiscountPercentageLevel4(
			totalDiscountPercentageLevel4);
		commerceOrderImpl.setSubtotalWithTaxAmount(subtotalWithTaxAmount);
		commerceOrderImpl.setSubtotalDiscountWithTaxAmount(
			subtotalDiscountWithTaxAmount);
		commerceOrderImpl.setSubtotalDiscountPercentageLevel1WithTaxAmount(
			subtotalDiscountPercentageLevel1WithTaxAmount);
		commerceOrderImpl.setSubtotalDiscountPercentageLevel2WithTaxAmount(
			subtotalDiscountPercentageLevel2WithTaxAmount);
		commerceOrderImpl.setSubtotalDiscountPercentageLevel3WithTaxAmount(
			subtotalDiscountPercentageLevel3WithTaxAmount);
		commerceOrderImpl.setSubtotalDiscountPercentageLevel4WithTaxAmount(
			subtotalDiscountPercentageLevel4WithTaxAmount);
		commerceOrderImpl.setShippingWithTaxAmount(shippingWithTaxAmount);
		commerceOrderImpl.setShippingDiscountWithTaxAmount(
			shippingDiscountWithTaxAmount);
		commerceOrderImpl.setShippingDiscountPercentageLevel1WithTaxAmount(
			shippingDiscountPercentageLevel1WithTaxAmount);
		commerceOrderImpl.setShippingDiscountPercentageLevel2WithTaxAmount(
			shippingDiscountPercentageLevel2WithTaxAmount);
		commerceOrderImpl.setShippingDiscountPercentageLevel3WithTaxAmount(
			shippingDiscountPercentageLevel3WithTaxAmount);
		commerceOrderImpl.setShippingDiscountPercentageLevel4WithTaxAmount(
			shippingDiscountPercentageLevel4WithTaxAmount);
		commerceOrderImpl.setTotalWithTaxAmount(totalWithTaxAmount);
		commerceOrderImpl.setTotalDiscountWithTaxAmount(
			totalDiscountWithTaxAmount);
		commerceOrderImpl.setTotalDiscountPercentageLevel1WithTaxAmount(
			totalDiscountPercentageLevel1WithTaxAmount);
		commerceOrderImpl.setTotalDiscountPercentageLevel2WithTaxAmount(
			totalDiscountPercentageLevel2WithTaxAmount);
		commerceOrderImpl.setTotalDiscountPercentageLevel3WithTaxAmount(
			totalDiscountPercentageLevel3WithTaxAmount);
		commerceOrderImpl.setTotalDiscountPercentageLevel4WithTaxAmount(
			totalDiscountPercentageLevel4WithTaxAmount);

		if (advanceStatus == null) {
			commerceOrderImpl.setAdvanceStatus("");
		}
		else {
			commerceOrderImpl.setAdvanceStatus(advanceStatus);
		}

		commerceOrderImpl.setPaymentStatus(paymentStatus);

		if (orderDate == Long.MIN_VALUE) {
			commerceOrderImpl.setOrderDate(null);
		}
		else {
			commerceOrderImpl.setOrderDate(new Date(orderDate));
		}

		commerceOrderImpl.setOrderStatus(orderStatus);

		if (printedNote == null) {
			commerceOrderImpl.setPrintedNote("");
		}
		else {
			commerceOrderImpl.setPrintedNote(printedNote);
		}

		if (requestedDeliveryDate == Long.MIN_VALUE) {
			commerceOrderImpl.setRequestedDeliveryDate(null);
		}
		else {
			commerceOrderImpl.setRequestedDeliveryDate(
				new Date(requestedDeliveryDate));
		}

		commerceOrderImpl.setManuallyAdjusted(manuallyAdjusted);
		commerceOrderImpl.setStatus(status);
		commerceOrderImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commerceOrderImpl.setStatusByUserName("");
		}
		else {
			commerceOrderImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commerceOrderImpl.setStatusDate(null);
		}
		else {
			commerceOrderImpl.setStatusDate(new Date(statusDate));
		}

		commerceOrderImpl.resetOriginalValues();

		return commerceOrderImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commerceOrderId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceAccountId = objectInput.readLong();

		commerceCurrencyId = objectInput.readLong();

		commerceOrderTypeId = objectInput.readLong();

		billingAddressId = objectInput.readLong();

		shippingAddressId = objectInput.readLong();
		commercePaymentMethodKey = objectInput.readUTF();
		transactionId = (String)objectInput.readObject();

		commerceShippingMethodId = objectInput.readLong();
		shippingOptionName = objectInput.readUTF();
		purchaseOrderNumber = objectInput.readUTF();
		couponCode = objectInput.readUTF();
		lastPriceUpdateDate = objectInput.readLong();
		subtotal = (BigDecimal)objectInput.readObject();
		subtotalDiscountAmount = (BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel1 = (BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel2 = (BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel3 = (BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel4 = (BigDecimal)objectInput.readObject();
		shippingAmount = (BigDecimal)objectInput.readObject();
		shippingDiscountAmount = (BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel1 = (BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel2 = (BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel3 = (BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel4 = (BigDecimal)objectInput.readObject();
		taxAmount = (BigDecimal)objectInput.readObject();
		total = (BigDecimal)objectInput.readObject();
		totalDiscountAmount = (BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel1 = (BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel2 = (BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel3 = (BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel4 = (BigDecimal)objectInput.readObject();
		subtotalWithTaxAmount = (BigDecimal)objectInput.readObject();
		subtotalDiscountWithTaxAmount = (BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel1WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel2WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel3WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel4WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		shippingWithTaxAmount = (BigDecimal)objectInput.readObject();
		shippingDiscountWithTaxAmount = (BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel1WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel2WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel3WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel4WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		totalWithTaxAmount = (BigDecimal)objectInput.readObject();
		totalDiscountWithTaxAmount = (BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel1WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel2WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel3WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel4WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		advanceStatus = objectInput.readUTF();

		paymentStatus = objectInput.readInt();
		orderDate = objectInput.readLong();

		orderStatus = objectInput.readInt();
		printedNote = objectInput.readUTF();
		requestedDeliveryDate = objectInput.readLong();

		manuallyAdjusted = objectInput.readBoolean();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
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

		objectOutput.writeLong(commerceOrderId);

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

		objectOutput.writeLong(commerceAccountId);

		objectOutput.writeLong(commerceCurrencyId);

		objectOutput.writeLong(commerceOrderTypeId);

		objectOutput.writeLong(billingAddressId);

		objectOutput.writeLong(shippingAddressId);

		if (commercePaymentMethodKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(commercePaymentMethodKey);
		}

		if (transactionId == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(transactionId);
		}

		objectOutput.writeLong(commerceShippingMethodId);

		if (shippingOptionName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(shippingOptionName);
		}

		if (purchaseOrderNumber == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(purchaseOrderNumber);
		}

		if (couponCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(couponCode);
		}

		objectOutput.writeLong(lastPriceUpdateDate);
		objectOutput.writeObject(subtotal);
		objectOutput.writeObject(subtotalDiscountAmount);
		objectOutput.writeObject(subtotalDiscountPercentageLevel1);
		objectOutput.writeObject(subtotalDiscountPercentageLevel2);
		objectOutput.writeObject(subtotalDiscountPercentageLevel3);
		objectOutput.writeObject(subtotalDiscountPercentageLevel4);
		objectOutput.writeObject(shippingAmount);
		objectOutput.writeObject(shippingDiscountAmount);
		objectOutput.writeObject(shippingDiscountPercentageLevel1);
		objectOutput.writeObject(shippingDiscountPercentageLevel2);
		objectOutput.writeObject(shippingDiscountPercentageLevel3);
		objectOutput.writeObject(shippingDiscountPercentageLevel4);
		objectOutput.writeObject(taxAmount);
		objectOutput.writeObject(total);
		objectOutput.writeObject(totalDiscountAmount);
		objectOutput.writeObject(totalDiscountPercentageLevel1);
		objectOutput.writeObject(totalDiscountPercentageLevel2);
		objectOutput.writeObject(totalDiscountPercentageLevel3);
		objectOutput.writeObject(totalDiscountPercentageLevel4);
		objectOutput.writeObject(subtotalWithTaxAmount);
		objectOutput.writeObject(subtotalDiscountWithTaxAmount);
		objectOutput.writeObject(subtotalDiscountPercentageLevel1WithTaxAmount);
		objectOutput.writeObject(subtotalDiscountPercentageLevel2WithTaxAmount);
		objectOutput.writeObject(subtotalDiscountPercentageLevel3WithTaxAmount);
		objectOutput.writeObject(subtotalDiscountPercentageLevel4WithTaxAmount);
		objectOutput.writeObject(shippingWithTaxAmount);
		objectOutput.writeObject(shippingDiscountWithTaxAmount);
		objectOutput.writeObject(shippingDiscountPercentageLevel1WithTaxAmount);
		objectOutput.writeObject(shippingDiscountPercentageLevel2WithTaxAmount);
		objectOutput.writeObject(shippingDiscountPercentageLevel3WithTaxAmount);
		objectOutput.writeObject(shippingDiscountPercentageLevel4WithTaxAmount);
		objectOutput.writeObject(totalWithTaxAmount);
		objectOutput.writeObject(totalDiscountWithTaxAmount);
		objectOutput.writeObject(totalDiscountPercentageLevel1WithTaxAmount);
		objectOutput.writeObject(totalDiscountPercentageLevel2WithTaxAmount);
		objectOutput.writeObject(totalDiscountPercentageLevel3WithTaxAmount);
		objectOutput.writeObject(totalDiscountPercentageLevel4WithTaxAmount);

		if (advanceStatus == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(advanceStatus);
		}

		objectOutput.writeInt(paymentStatus);
		objectOutput.writeLong(orderDate);

		objectOutput.writeInt(orderStatus);

		if (printedNote == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(printedNote);
		}

		objectOutput.writeLong(requestedDeliveryDate);

		objectOutput.writeBoolean(manuallyAdjusted);

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

	public String uuid;
	public String externalReferenceCode;
	public long commerceOrderId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceAccountId;
	public long commerceCurrencyId;
	public long commerceOrderTypeId;
	public long billingAddressId;
	public long shippingAddressId;
	public String commercePaymentMethodKey;
	public String transactionId;
	public long commerceShippingMethodId;
	public String shippingOptionName;
	public String purchaseOrderNumber;
	public String couponCode;
	public long lastPriceUpdateDate;
	public BigDecimal subtotal;
	public BigDecimal subtotalDiscountAmount;
	public BigDecimal subtotalDiscountPercentageLevel1;
	public BigDecimal subtotalDiscountPercentageLevel2;
	public BigDecimal subtotalDiscountPercentageLevel3;
	public BigDecimal subtotalDiscountPercentageLevel4;
	public BigDecimal shippingAmount;
	public BigDecimal shippingDiscountAmount;
	public BigDecimal shippingDiscountPercentageLevel1;
	public BigDecimal shippingDiscountPercentageLevel2;
	public BigDecimal shippingDiscountPercentageLevel3;
	public BigDecimal shippingDiscountPercentageLevel4;
	public BigDecimal taxAmount;
	public BigDecimal total;
	public BigDecimal totalDiscountAmount;
	public BigDecimal totalDiscountPercentageLevel1;
	public BigDecimal totalDiscountPercentageLevel2;
	public BigDecimal totalDiscountPercentageLevel3;
	public BigDecimal totalDiscountPercentageLevel4;
	public BigDecimal subtotalWithTaxAmount;
	public BigDecimal subtotalDiscountWithTaxAmount;
	public BigDecimal subtotalDiscountPercentageLevel1WithTaxAmount;
	public BigDecimal subtotalDiscountPercentageLevel2WithTaxAmount;
	public BigDecimal subtotalDiscountPercentageLevel3WithTaxAmount;
	public BigDecimal subtotalDiscountPercentageLevel4WithTaxAmount;
	public BigDecimal shippingWithTaxAmount;
	public BigDecimal shippingDiscountWithTaxAmount;
	public BigDecimal shippingDiscountPercentageLevel1WithTaxAmount;
	public BigDecimal shippingDiscountPercentageLevel2WithTaxAmount;
	public BigDecimal shippingDiscountPercentageLevel3WithTaxAmount;
	public BigDecimal shippingDiscountPercentageLevel4WithTaxAmount;
	public BigDecimal totalWithTaxAmount;
	public BigDecimal totalDiscountWithTaxAmount;
	public BigDecimal totalDiscountPercentageLevel1WithTaxAmount;
	public BigDecimal totalDiscountPercentageLevel2WithTaxAmount;
	public BigDecimal totalDiscountPercentageLevel3WithTaxAmount;
	public BigDecimal totalDiscountPercentageLevel4WithTaxAmount;
	public String advanceStatus;
	public int paymentStatus;
	public long orderDate;
	public int orderStatus;
	public String printedNote;
	public long requestedDeliveryDate;
	public boolean manuallyAdjusted;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}