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
		StringBundler sb = new StringBundler(55);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
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
		sb.append(", cancelURL=");
		sb.append(cancelURL);
		sb.append(", currencyCode=");
		sb.append(currencyCode);
		sb.append(", errorMessages=");
		sb.append(errorMessages);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", note=");
		sb.append(note);
		sb.append(", payload=");
		sb.append(payload);
		sb.append(", paymentIntegrationKey=");
		sb.append(paymentIntegrationKey);
		sb.append(", paymentIntegrationType=");
		sb.append(paymentIntegrationType);
		sb.append(", paymentStatus=");
		sb.append(paymentStatus);
		sb.append(", reasonKey=");
		sb.append(reasonKey);
		sb.append(", reasonName=");
		sb.append(reasonName);
		sb.append(", redirectURL=");
		sb.append(redirectURL);
		sb.append(", transactionCode=");
		sb.append(transactionCode);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePaymentEntry toEntityModel() {
		CommercePaymentEntryImpl commercePaymentEntryImpl =
			new CommercePaymentEntryImpl();

		commercePaymentEntryImpl.setMvccVersion(mvccVersion);

		if (externalReferenceCode == null) {
			commercePaymentEntryImpl.setExternalReferenceCode("");
		}
		else {
			commercePaymentEntryImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

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

		if (cancelURL == null) {
			commercePaymentEntryImpl.setCancelURL("");
		}
		else {
			commercePaymentEntryImpl.setCancelURL(cancelURL);
		}

		if (currencyCode == null) {
			commercePaymentEntryImpl.setCurrencyCode("");
		}
		else {
			commercePaymentEntryImpl.setCurrencyCode(currencyCode);
		}

		if (errorMessages == null) {
			commercePaymentEntryImpl.setErrorMessages("");
		}
		else {
			commercePaymentEntryImpl.setErrorMessages(errorMessages);
		}

		if (languageId == null) {
			commercePaymentEntryImpl.setLanguageId("");
		}
		else {
			commercePaymentEntryImpl.setLanguageId(languageId);
		}

		if (note == null) {
			commercePaymentEntryImpl.setNote("");
		}
		else {
			commercePaymentEntryImpl.setNote(note);
		}

		if (payload == null) {
			commercePaymentEntryImpl.setPayload("");
		}
		else {
			commercePaymentEntryImpl.setPayload(payload);
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

		if (reasonKey == null) {
			commercePaymentEntryImpl.setReasonKey("");
		}
		else {
			commercePaymentEntryImpl.setReasonKey(reasonKey);
		}

		if (reasonName == null) {
			commercePaymentEntryImpl.setReasonName("");
		}
		else {
			commercePaymentEntryImpl.setReasonName(reasonName);
		}

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

		commercePaymentEntryImpl.setType(type);

		commercePaymentEntryImpl.resetOriginalValues();

		return commercePaymentEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		externalReferenceCode = objectInput.readUTF();

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
		cancelURL = (String)objectInput.readObject();
		currencyCode = objectInput.readUTF();
		errorMessages = (String)objectInput.readObject();
		languageId = objectInput.readUTF();
		note = (String)objectInput.readObject();
		payload = (String)objectInput.readObject();
		paymentIntegrationKey = objectInput.readUTF();

		paymentIntegrationType = objectInput.readInt();

		paymentStatus = objectInput.readInt();
		reasonKey = objectInput.readUTF();
		reasonName = objectInput.readUTF();
		redirectURL = (String)objectInput.readObject();
		transactionCode = objectInput.readUTF();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

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

		if (cancelURL == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(cancelURL);
		}

		if (currencyCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(currencyCode);
		}

		if (errorMessages == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(errorMessages);
		}

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		if (note == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(note);
		}

		if (payload == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(payload);
		}

		if (paymentIntegrationKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(paymentIntegrationKey);
		}

		objectOutput.writeInt(paymentIntegrationType);

		objectOutput.writeInt(paymentStatus);

		if (reasonKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(reasonKey);
		}

		if (reasonName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(reasonName);
		}

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

		objectOutput.writeInt(type);
	}

	public long mvccVersion;
	public String externalReferenceCode;
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
	public String cancelURL;
	public String currencyCode;
	public String errorMessages;
	public String languageId;
	public String note;
	public String payload;
	public String paymentIntegrationKey;
	public int paymentIntegrationType;
	public int paymentStatus;
	public String reasonKey;
	public String reasonName;
	public String redirectURL;
	public String transactionCode;
	public int type;

}