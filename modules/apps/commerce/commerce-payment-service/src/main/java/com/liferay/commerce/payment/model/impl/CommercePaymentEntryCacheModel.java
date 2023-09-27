/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.model.impl;

import com.liferay.commerce.payment.model.CommercePaymentEntry;
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
 * The cache model class for representing CommercePaymentEntry in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommercePaymentEntryCacheModel
	implements CacheModel<CommercePaymentEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePaymentEntryCacheModel)) {
			return false;
		}

		CommercePaymentEntryCacheModel commercePaymentEntryCacheModel =
			(CommercePaymentEntryCacheModel)object;

		if ((commercePaymentEntryId ==
				commercePaymentEntryCacheModel.commercePaymentEntryId) &&
			(mvccVersion == commercePaymentEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commercePaymentEntryId);

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
		StringBundler sb = new StringBundler(37);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commercePaymentEntryId=");
		sb.append(commercePaymentEntryId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", commerceChannelId=");
		sb.append(commerceChannelId);
		sb.append(", amount=");
		sb.append(amount);
		sb.append(", callbackURL=");
		sb.append(callbackURL);
		sb.append(", currencyCode=");
		sb.append(currencyCode);
		sb.append(", paymentIntegrationKey=");
		sb.append(paymentIntegrationKey);
		sb.append(", paymentIntegrationType=");
		sb.append(paymentIntegrationType);
		sb.append(", paymentStatus=");
		sb.append(paymentStatus);
		sb.append(", redirectURL=");
		sb.append(redirectURL);
		sb.append(", transactionCode=");
		sb.append(transactionCode);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePaymentEntry toEntityModel() {
		CommercePaymentEntryImpl commercePaymentEntryImpl =
			new CommercePaymentEntryImpl();

		commercePaymentEntryImpl.setMvccVersion(mvccVersion);
		commercePaymentEntryImpl.setCommercePaymentEntryId(
			commercePaymentEntryId);
		commercePaymentEntryImpl.setCompanyId(companyId);
		commercePaymentEntryImpl.setUserId(userId);

		if (userName == null) {
			commercePaymentEntryImpl.setUserName("");
		}
		else {
			commercePaymentEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePaymentEntryImpl.setCreateDate(null);
		}
		else {
			commercePaymentEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePaymentEntryImpl.setModifiedDate(null);
		}
		else {
			commercePaymentEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		commercePaymentEntryImpl.setClassNameId(classNameId);
		commercePaymentEntryImpl.setClassPK(classPK);
		commercePaymentEntryImpl.setCommerceChannelId(commerceChannelId);
		commercePaymentEntryImpl.setAmount(amount);

		if (callbackURL == null) {
			commercePaymentEntryImpl.setCallbackURL("");
		}
		else {
			commercePaymentEntryImpl.setCallbackURL(callbackURL);
		}

		if (currencyCode == null) {
			commercePaymentEntryImpl.setCurrencyCode("");
		}
		else {
			commercePaymentEntryImpl.setCurrencyCode(currencyCode);
		}

		if (paymentIntegrationKey == null) {
			commercePaymentEntryImpl.setPaymentIntegrationKey("");
		}
		else {
			commercePaymentEntryImpl.setPaymentIntegrationKey(
				paymentIntegrationKey);
		}

		commercePaymentEntryImpl.setPaymentIntegrationType(
			paymentIntegrationType);
		commercePaymentEntryImpl.setPaymentStatus(paymentStatus);

		if (redirectURL == null) {
			commercePaymentEntryImpl.setRedirectURL("");
		}
		else {
			commercePaymentEntryImpl.setRedirectURL(redirectURL);
		}

		if (transactionCode == null) {
			commercePaymentEntryImpl.setTransactionCode("");
		}
		else {
			commercePaymentEntryImpl.setTransactionCode(transactionCode);
		}

		commercePaymentEntryImpl.resetOriginalValues();

		return commercePaymentEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		commercePaymentEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		commerceChannelId = objectInput.readLong();
		amount = (BigDecimal)objectInput.readObject();
		callbackURL = (String)objectInput.readObject();
		currencyCode = objectInput.readUTF();
		paymentIntegrationKey = objectInput.readUTF();

		paymentIntegrationType = objectInput.readInt();

		paymentStatus = objectInput.readInt();
		redirectURL = (String)objectInput.readObject();
		transactionCode = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commercePaymentEntryId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(commerceChannelId);
		objectOutput.writeObject(amount);

		if (callbackURL == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(callbackURL);
		}

		if (currencyCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(currencyCode);
		}

		if (paymentIntegrationKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(paymentIntegrationKey);
		}

		objectOutput.writeInt(paymentIntegrationType);

		objectOutput.writeInt(paymentStatus);

		if (redirectURL == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(redirectURL);
		}

		if (transactionCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(transactionCode);
		}
	}

	public long mvccVersion;
	public long commercePaymentEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long commerceChannelId;
	public BigDecimal amount;
	public String callbackURL;
	public String currencyCode;
	public String paymentIntegrationKey;
	public int paymentIntegrationType;
	public int paymentStatus;
	public String redirectURL;
	public String transactionCode;

}