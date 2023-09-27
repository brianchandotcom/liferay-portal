/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CommerceOrder;
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
 * The cache model class for representing CommerceOrder in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceOrderCacheModel
	implements CacheModel<CommerceOrder>, Externalizable, MVCCModel {

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

		if ((commerceOrderId == commerceOrderCacheModel.commerceOrderId) &&
			(mvccVersion == commerceOrderCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceOrderId);

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
		StringBundler sb = new StringBundler(153);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
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
		sb.append(", billingAddressId=");
		sb.append(billingAddressId);
		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);
		sb.append(", commerceCurrencyId=");
		sb.append(commerceCurrencyId);
		sb.append(", commerceOrderTypeId=");
		sb.append(commerceOrderTypeId);
		sb.append(", commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);
		sb.append(", deliveryCommerceTermEntryId=");
		sb.append(deliveryCommerceTermEntryId);
		sb.append(", paymentCommerceTermEntryId=");
		sb.append(paymentCommerceTermEntryId);
		sb.append(", shippingAddressId=");
		sb.append(shippingAddressId);
		sb.append(", advanceStatus=");
		sb.append(advanceStatus);
		sb.append(", commercePaymentMethodKey=");
		sb.append(commercePaymentMethodKey);
		sb.append(", couponCode=");
		sb.append(couponCode);
		sb.append(", deliveryCommerceTermEntryDescription=");
		sb.append(deliveryCommerceTermEntryDescription);
		sb.append(", deliveryCommerceTermEntryName=");
		sb.append(deliveryCommerceTermEntryName);
		sb.append(", lastPriceUpdateDate=");
		sb.append(lastPriceUpdateDate);
		sb.append(", manuallyAdjusted=");
		sb.append(manuallyAdjusted);
		sb.append(", orderDate=");
		sb.append(orderDate);
		sb.append(", orderStatus=");
		sb.append(orderStatus);
		sb.append(", paymentCommerceTermEntryDescription=");
		sb.append(paymentCommerceTermEntryDescription);
		sb.append(", paymentCommerceTermEntryName=");
		sb.append(paymentCommerceTermEntryName);
		sb.append(", paymentStatus=");
		sb.append(paymentStatus);
		sb.append(", printedNote=");
		sb.append(printedNote);
		sb.append(", purchaseOrderNumber=");
		sb.append(purchaseOrderNumber);
		sb.append(", requestedDeliveryDate=");
		sb.append(requestedDeliveryDate);
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
		sb.append(", shippingDiscountPercentageLevel1WithTaxAmount=");
		sb.append(shippingDiscountPercentageLevel1WithTaxAmount);
		sb.append(", shippingDiscountPercentageLevel2WithTaxAmount=");
		sb.append(shippingDiscountPercentageLevel2WithTaxAmount);
		sb.append(", shippingDiscountPercentageLevel3WithTaxAmount=");
		sb.append(shippingDiscountPercentageLevel3WithTaxAmount);
		sb.append(", shippingDiscountPercentageLevel4WithTaxAmount=");
		sb.append(shippingDiscountPercentageLevel4WithTaxAmount);
		sb.append(", shippingDiscountWithTaxAmount=");
		sb.append(shippingDiscountWithTaxAmount);
		sb.append(", shippingOptionName=");
		sb.append(shippingOptionName);
		sb.append(", shippingWithTaxAmount=");
		sb.append(shippingWithTaxAmount);
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
		sb.append(", subtotalDiscountPercentageLevel1WithTaxAmount=");
		sb.append(subtotalDiscountPercentageLevel1WithTaxAmount);
		sb.append(", subtotalDiscountPercentageLevel2WithTaxAmount=");
		sb.append(subtotalDiscountPercentageLevel2WithTaxAmount);
		sb.append(", subtotalDiscountPercentageLevel3WithTaxAmount=");
		sb.append(subtotalDiscountPercentageLevel3WithTaxAmount);
		sb.append(", subtotalDiscountPercentageLevel4WithTaxAmount=");
		sb.append(subtotalDiscountPercentageLevel4WithTaxAmount);
		sb.append(", subtotalDiscountWithTaxAmount=");
		sb.append(subtotalDiscountWithTaxAmount);
		sb.append(", subtotalWithTaxAmount=");
		sb.append(subtotalWithTaxAmount);
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
		sb.append(", totalDiscountPercentageLevel1WithTaxAmount=");
		sb.append(totalDiscountPercentageLevel1WithTaxAmount);
		sb.append(", totalDiscountPercentageLevel2WithTaxAmount=");
		sb.append(totalDiscountPercentageLevel2WithTaxAmount);
		sb.append(", totalDiscountPercentageLevel3WithTaxAmount=");
		sb.append(totalDiscountPercentageLevel3WithTaxAmount);
		sb.append(", totalDiscountPercentageLevel4WithTaxAmount=");
		sb.append(totalDiscountPercentageLevel4WithTaxAmount);
		sb.append(", totalDiscountWithTaxAmount=");
		sb.append(totalDiscountWithTaxAmount);
		sb.append(", totalWithTaxAmount=");
		sb.append(totalWithTaxAmount);
		sb.append(", transactionId=");
		sb.append(transactionId);
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

		commerceOrderImpl.setMvccVersion(mvccVersion);

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

		commerceOrderImpl.setBillingAddressId(billingAddressId);
		commerceOrderImpl.setCommerceAccountId(commerceAccountId);
		commerceOrderImpl.setCommerceCurrencyId(commerceCurrencyId);
		commerceOrderImpl.setCommerceOrderTypeId(commerceOrderTypeId);
		commerceOrderImpl.setCommerceShippingMethodId(commerceShippingMethodId);
		commerceOrderImpl.setDeliveryCommerceTermEntryId(
			deliveryCommerceTermEntryId);
		commerceOrderImpl.setPaymentCommerceTermEntryId(
			paymentCommerceTermEntryId);
		commerceOrderImpl.setShippingAddressId(shippingAddressId);

		if (advanceStatus == null) {
			commerceOrderImpl.setAdvanceStatus("");
		}
		else {
			commerceOrderImpl.setAdvanceStatus(advanceStatus);
		}

		if (commercePaymentMethodKey == null) {
			commerceOrderImpl.setCommercePaymentMethodKey("");
		}
		else {
			commerceOrderImpl.setCommercePaymentMethodKey(
				commercePaymentMethodKey);
		}

		if (couponCode == null) {
			commerceOrderImpl.setCouponCode("");
		}
		else {
			commerceOrderImpl.setCouponCode(couponCode);
		}

		if (deliveryCommerceTermEntryDescription == null) {
			commerceOrderImpl.setDeliveryCommerceTermEntryDescription("");
		}
		else {
			commerceOrderImpl.setDeliveryCommerceTermEntryDescription(
				deliveryCommerceTermEntryDescription);
		}

		if (deliveryCommerceTermEntryName == null) {
			commerceOrderImpl.setDeliveryCommerceTermEntryName("");
		}
		else {
			commerceOrderImpl.setDeliveryCommerceTermEntryName(
				deliveryCommerceTermEntryName);
		}

		if (lastPriceUpdateDate == Long.MIN_VALUE) {
			commerceOrderImpl.setLastPriceUpdateDate(null);
		}
		else {
			commerceOrderImpl.setLastPriceUpdateDate(
				new Date(lastPriceUpdateDate));
		}

		commerceOrderImpl.setManuallyAdjusted(manuallyAdjusted);

		if (orderDate == Long.MIN_VALUE) {
			commerceOrderImpl.setOrderDate(null);
		}
		else {
			commerceOrderImpl.setOrderDate(new Date(orderDate));
		}

		commerceOrderImpl.setOrderStatus(orderStatus);

		if (paymentCommerceTermEntryDescription == null) {
			commerceOrderImpl.setPaymentCommerceTermEntryDescription("");
		}
		else {
			commerceOrderImpl.setPaymentCommerceTermEntryDescription(
				paymentCommerceTermEntryDescription);
		}

		if (paymentCommerceTermEntryName == null) {
			commerceOrderImpl.setPaymentCommerceTermEntryName("");
		}
		else {
			commerceOrderImpl.setPaymentCommerceTermEntryName(
				paymentCommerceTermEntryName);
		}

		commerceOrderImpl.setPaymentStatus(paymentStatus);

		if (printedNote == null) {
			commerceOrderImpl.setPrintedNote("");
		}
		else {
			commerceOrderImpl.setPrintedNote(printedNote);
		}

		if (purchaseOrderNumber == null) {
			commerceOrderImpl.setPurchaseOrderNumber("");
		}
		else {
			commerceOrderImpl.setPurchaseOrderNumber(purchaseOrderNumber);
		}

		if (requestedDeliveryDate == Long.MIN_VALUE) {
			commerceOrderImpl.setRequestedDeliveryDate(null);
		}
		else {
			commerceOrderImpl.setRequestedDeliveryDate(
				new Date(requestedDeliveryDate));
		}

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
		commerceOrderImpl.setShippingDiscountPercentageLevel1WithTaxAmount(
			shippingDiscountPercentageLevel1WithTaxAmount);
		commerceOrderImpl.setShippingDiscountPercentageLevel2WithTaxAmount(
			shippingDiscountPercentageLevel2WithTaxAmount);
		commerceOrderImpl.setShippingDiscountPercentageLevel3WithTaxAmount(
			shippingDiscountPercentageLevel3WithTaxAmount);
		commerceOrderImpl.setShippingDiscountPercentageLevel4WithTaxAmount(
			shippingDiscountPercentageLevel4WithTaxAmount);
		commerceOrderImpl.setShippingDiscountWithTaxAmount(
			shippingDiscountWithTaxAmount);

		if (shippingOptionName == null) {
			commerceOrderImpl.setShippingOptionName("");
		}
		else {
			commerceOrderImpl.setShippingOptionName(shippingOptionName);
		}

		commerceOrderImpl.setShippingWithTaxAmount(shippingWithTaxAmount);
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
		commerceOrderImpl.setSubtotalDiscountPercentageLevel1WithTaxAmount(
			subtotalDiscountPercentageLevel1WithTaxAmount);
		commerceOrderImpl.setSubtotalDiscountPercentageLevel2WithTaxAmount(
			subtotalDiscountPercentageLevel2WithTaxAmount);
		commerceOrderImpl.setSubtotalDiscountPercentageLevel3WithTaxAmount(
			subtotalDiscountPercentageLevel3WithTaxAmount);
		commerceOrderImpl.setSubtotalDiscountPercentageLevel4WithTaxAmount(
			subtotalDiscountPercentageLevel4WithTaxAmount);
		commerceOrderImpl.setSubtotalDiscountWithTaxAmount(
			subtotalDiscountWithTaxAmount);
		commerceOrderImpl.setSubtotalWithTaxAmount(subtotalWithTaxAmount);
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
		commerceOrderImpl.setTotalDiscountPercentageLevel1WithTaxAmount(
			totalDiscountPercentageLevel1WithTaxAmount);
		commerceOrderImpl.setTotalDiscountPercentageLevel2WithTaxAmount(
			totalDiscountPercentageLevel2WithTaxAmount);
		commerceOrderImpl.setTotalDiscountPercentageLevel3WithTaxAmount(
			totalDiscountPercentageLevel3WithTaxAmount);
		commerceOrderImpl.setTotalDiscountPercentageLevel4WithTaxAmount(
			totalDiscountPercentageLevel4WithTaxAmount);
		commerceOrderImpl.setTotalDiscountWithTaxAmount(
			totalDiscountWithTaxAmount);
		commerceOrderImpl.setTotalWithTaxAmount(totalWithTaxAmount);

		if (transactionId == null) {
			commerceOrderImpl.setTransactionId("");
		}
		else {
			commerceOrderImpl.setTransactionId(transactionId);
		}

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

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		commerceOrderId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		billingAddressId = objectInput.readLong();

		commerceAccountId = objectInput.readLong();

		commerceCurrencyId = objectInput.readLong();

		commerceOrderTypeId = objectInput.readLong();

		commerceShippingMethodId = objectInput.readLong();

		deliveryCommerceTermEntryId = objectInput.readLong();

		paymentCommerceTermEntryId = objectInput.readLong();

		shippingAddressId = objectInput.readLong();
		advanceStatus = objectInput.readUTF();
		commercePaymentMethodKey = objectInput.readUTF();
		couponCode = objectInput.readUTF();
		deliveryCommerceTermEntryDescription = (String)objectInput.readObject();
		deliveryCommerceTermEntryName = objectInput.readUTF();
		lastPriceUpdateDate = objectInput.readLong();

		manuallyAdjusted = objectInput.readBoolean();
		orderDate = objectInput.readLong();

		orderStatus = objectInput.readInt();
		paymentCommerceTermEntryDescription = (String)objectInput.readObject();
		paymentCommerceTermEntryName = objectInput.readUTF();

		paymentStatus = objectInput.readInt();
		printedNote = objectInput.readUTF();
		purchaseOrderNumber = objectInput.readUTF();
		requestedDeliveryDate = objectInput.readLong();
		shippingAmount = (BigDecimal)objectInput.readObject();
		shippingDiscountAmount = (BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel1 = (BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel2 = (BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel3 = (BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel4 = (BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel1WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel2WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel3WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		shippingDiscountPercentageLevel4WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		shippingDiscountWithTaxAmount = (BigDecimal)objectInput.readObject();
		shippingOptionName = objectInput.readUTF();
		shippingWithTaxAmount = (BigDecimal)objectInput.readObject();
		subtotal = (BigDecimal)objectInput.readObject();
		subtotalDiscountAmount = (BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel1 = (BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel2 = (BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel3 = (BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel4 = (BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel1WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel2WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel3WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		subtotalDiscountPercentageLevel4WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		subtotalDiscountWithTaxAmount = (BigDecimal)objectInput.readObject();
		subtotalWithTaxAmount = (BigDecimal)objectInput.readObject();
		taxAmount = (BigDecimal)objectInput.readObject();
		total = (BigDecimal)objectInput.readObject();
		totalDiscountAmount = (BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel1 = (BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel2 = (BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel3 = (BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel4 = (BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel1WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel2WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel3WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		totalDiscountPercentageLevel4WithTaxAmount =
			(BigDecimal)objectInput.readObject();
		totalDiscountWithTaxAmount = (BigDecimal)objectInput.readObject();
		totalWithTaxAmount = (BigDecimal)objectInput.readObject();
		transactionId = (String)objectInput.readObject();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
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

		objectOutput.writeLong(billingAddressId);

		objectOutput.writeLong(commerceAccountId);

		objectOutput.writeLong(commerceCurrencyId);

		objectOutput.writeLong(commerceOrderTypeId);

		objectOutput.writeLong(commerceShippingMethodId);

		objectOutput.writeLong(deliveryCommerceTermEntryId);

		objectOutput.writeLong(paymentCommerceTermEntryId);

		objectOutput.writeLong(shippingAddressId);

		if (advanceStatus == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(advanceStatus);
		}

		if (commercePaymentMethodKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(commercePaymentMethodKey);
		}

		if (couponCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(couponCode);
		}

		if (deliveryCommerceTermEntryDescription == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(deliveryCommerceTermEntryDescription);
		}

		if (deliveryCommerceTermEntryName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(deliveryCommerceTermEntryName);
		}

		objectOutput.writeLong(lastPriceUpdateDate);

		objectOutput.writeBoolean(manuallyAdjusted);
		objectOutput.writeLong(orderDate);

		objectOutput.writeInt(orderStatus);

		if (paymentCommerceTermEntryDescription == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(paymentCommerceTermEntryDescription);
		}

		if (paymentCommerceTermEntryName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(paymentCommerceTermEntryName);
		}

		objectOutput.writeInt(paymentStatus);

		if (printedNote == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(printedNote);
		}

		if (purchaseOrderNumber == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(purchaseOrderNumber);
		}

		objectOutput.writeLong(requestedDeliveryDate);
		objectOutput.writeObject(shippingAmount);
		objectOutput.writeObject(shippingDiscountAmount);
		objectOutput.writeObject(shippingDiscountPercentageLevel1);
		objectOutput.writeObject(shippingDiscountPercentageLevel2);
		objectOutput.writeObject(shippingDiscountPercentageLevel3);
		objectOutput.writeObject(shippingDiscountPercentageLevel4);
		objectOutput.writeObject(shippingDiscountPercentageLevel1WithTaxAmount);
		objectOutput.writeObject(shippingDiscountPercentageLevel2WithTaxAmount);
		objectOutput.writeObject(shippingDiscountPercentageLevel3WithTaxAmount);
		objectOutput.writeObject(shippingDiscountPercentageLevel4WithTaxAmount);
		objectOutput.writeObject(shippingDiscountWithTaxAmount);

		if (shippingOptionName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(shippingOptionName);
		}

		objectOutput.writeObject(shippingWithTaxAmount);
		objectOutput.writeObject(subtotal);
		objectOutput.writeObject(subtotalDiscountAmount);
		objectOutput.writeObject(subtotalDiscountPercentageLevel1);
		objectOutput.writeObject(subtotalDiscountPercentageLevel2);
		objectOutput.writeObject(subtotalDiscountPercentageLevel3);
		objectOutput.writeObject(subtotalDiscountPercentageLevel4);
		objectOutput.writeObject(subtotalDiscountPercentageLevel1WithTaxAmount);
		objectOutput.writeObject(subtotalDiscountPercentageLevel2WithTaxAmount);
		objectOutput.writeObject(subtotalDiscountPercentageLevel3WithTaxAmount);
		objectOutput.writeObject(subtotalDiscountPercentageLevel4WithTaxAmount);
		objectOutput.writeObject(subtotalDiscountWithTaxAmount);
		objectOutput.writeObject(subtotalWithTaxAmount);
		objectOutput.writeObject(taxAmount);
		objectOutput.writeObject(total);
		objectOutput.writeObject(totalDiscountAmount);
		objectOutput.writeObject(totalDiscountPercentageLevel1);
		objectOutput.writeObject(totalDiscountPercentageLevel2);
		objectOutput.writeObject(totalDiscountPercentageLevel3);
		objectOutput.writeObject(totalDiscountPercentageLevel4);
		objectOutput.writeObject(totalDiscountPercentageLevel1WithTaxAmount);
		objectOutput.writeObject(totalDiscountPercentageLevel2WithTaxAmount);
		objectOutput.writeObject(totalDiscountPercentageLevel3WithTaxAmount);
		objectOutput.writeObject(totalDiscountPercentageLevel4WithTaxAmount);
		objectOutput.writeObject(totalDiscountWithTaxAmount);
		objectOutput.writeObject(totalWithTaxAmount);

		if (transactionId == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(transactionId);
		}

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
	public String uuid;
	public String externalReferenceCode;
	public long commerceOrderId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long billingAddressId;
	public long commerceAccountId;
	public long commerceCurrencyId;
	public long commerceOrderTypeId;
	public long commerceShippingMethodId;
	public long deliveryCommerceTermEntryId;
	public long paymentCommerceTermEntryId;
	public long shippingAddressId;
	public String advanceStatus;
	public String commercePaymentMethodKey;
	public String couponCode;
	public String deliveryCommerceTermEntryDescription;
	public String deliveryCommerceTermEntryName;
	public long lastPriceUpdateDate;
	public boolean manuallyAdjusted;
	public long orderDate;
	public int orderStatus;
	public String paymentCommerceTermEntryDescription;
	public String paymentCommerceTermEntryName;
	public int paymentStatus;
	public String printedNote;
	public String purchaseOrderNumber;
	public long requestedDeliveryDate;
	public BigDecimal shippingAmount;
	public BigDecimal shippingDiscountAmount;
	public BigDecimal shippingDiscountPercentageLevel1;
	public BigDecimal shippingDiscountPercentageLevel2;
	public BigDecimal shippingDiscountPercentageLevel3;
	public BigDecimal shippingDiscountPercentageLevel4;
	public BigDecimal shippingDiscountPercentageLevel1WithTaxAmount;
	public BigDecimal shippingDiscountPercentageLevel2WithTaxAmount;
	public BigDecimal shippingDiscountPercentageLevel3WithTaxAmount;
	public BigDecimal shippingDiscountPercentageLevel4WithTaxAmount;
	public BigDecimal shippingDiscountWithTaxAmount;
	public String shippingOptionName;
	public BigDecimal shippingWithTaxAmount;
	public BigDecimal subtotal;
	public BigDecimal subtotalDiscountAmount;
	public BigDecimal subtotalDiscountPercentageLevel1;
	public BigDecimal subtotalDiscountPercentageLevel2;
	public BigDecimal subtotalDiscountPercentageLevel3;
	public BigDecimal subtotalDiscountPercentageLevel4;
	public BigDecimal subtotalDiscountPercentageLevel1WithTaxAmount;
	public BigDecimal subtotalDiscountPercentageLevel2WithTaxAmount;
	public BigDecimal subtotalDiscountPercentageLevel3WithTaxAmount;
	public BigDecimal subtotalDiscountPercentageLevel4WithTaxAmount;
	public BigDecimal subtotalDiscountWithTaxAmount;
	public BigDecimal subtotalWithTaxAmount;
	public BigDecimal taxAmount;
	public BigDecimal total;
	public BigDecimal totalDiscountAmount;
	public BigDecimal totalDiscountPercentageLevel1;
	public BigDecimal totalDiscountPercentageLevel2;
	public BigDecimal totalDiscountPercentageLevel3;
	public BigDecimal totalDiscountPercentageLevel4;
	public BigDecimal totalDiscountPercentageLevel1WithTaxAmount;
	public BigDecimal totalDiscountPercentageLevel2WithTaxAmount;
	public BigDecimal totalDiscountPercentageLevel3WithTaxAmount;
	public BigDecimal totalDiscountPercentageLevel4WithTaxAmount;
	public BigDecimal totalDiscountWithTaxAmount;
	public BigDecimal totalWithTaxAmount;
	public String transactionId;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}