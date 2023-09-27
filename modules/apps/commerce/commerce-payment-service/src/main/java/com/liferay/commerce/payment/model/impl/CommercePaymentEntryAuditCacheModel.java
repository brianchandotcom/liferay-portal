/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.model.impl;

import com.liferay.commerce.payment.model.CommercePaymentEntryAudit;
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
 * The cache model class for representing CommercePaymentEntryAudit in entity cache.
 *
 * @author Luca Pellizzon
 * @generated
 */
public class CommercePaymentEntryAuditCacheModel
	implements CacheModel<CommercePaymentEntryAudit>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommercePaymentEntryAuditCacheModel)) {
			return false;
		}

		CommercePaymentEntryAuditCacheModel
			commercePaymentEntryAuditCacheModel =
				(CommercePaymentEntryAuditCacheModel)object;

		if ((commercePaymentEntryAuditId ==
				commercePaymentEntryAuditCacheModel.
					commercePaymentEntryAuditId) &&
			(mvccVersion == commercePaymentEntryAuditCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commercePaymentEntryAuditId);

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
		sb.append(", commercePaymentEntryAuditId=");
		sb.append(commercePaymentEntryAuditId);
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
		sb.append(", commercePaymentEntryId=");
		sb.append(commercePaymentEntryId);
		sb.append(", amount=");
		sb.append(amount);
		sb.append(", currencyCode=");
		sb.append(currencyCode);
		sb.append(", logType=");
		sb.append(logType);
		sb.append(", logTypeSettings=");
		sb.append(logTypeSettings);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommercePaymentEntryAudit toEntityModel() {
		CommercePaymentEntryAuditImpl commercePaymentEntryAuditImpl =
			new CommercePaymentEntryAuditImpl();

		commercePaymentEntryAuditImpl.setMvccVersion(mvccVersion);
		commercePaymentEntryAuditImpl.setCommercePaymentEntryAuditId(
			commercePaymentEntryAuditId);
		commercePaymentEntryAuditImpl.setCompanyId(companyId);
		commercePaymentEntryAuditImpl.setUserId(userId);

		if (userName == null) {
			commercePaymentEntryAuditImpl.setUserName("");
		}
		else {
			commercePaymentEntryAuditImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commercePaymentEntryAuditImpl.setCreateDate(null);
		}
		else {
			commercePaymentEntryAuditImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commercePaymentEntryAuditImpl.setModifiedDate(null);
		}
		else {
			commercePaymentEntryAuditImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commercePaymentEntryAuditImpl.setCommercePaymentEntryId(
			commercePaymentEntryId);
		commercePaymentEntryAuditImpl.setAmount(amount);

		if (currencyCode == null) {
			commercePaymentEntryAuditImpl.setCurrencyCode("");
		}
		else {
			commercePaymentEntryAuditImpl.setCurrencyCode(currencyCode);
		}

		if (logType == null) {
			commercePaymentEntryAuditImpl.setLogType("");
		}
		else {
			commercePaymentEntryAuditImpl.setLogType(logType);
		}

		if (logTypeSettings == null) {
			commercePaymentEntryAuditImpl.setLogTypeSettings("");
		}
		else {
			commercePaymentEntryAuditImpl.setLogTypeSettings(logTypeSettings);
		}

		commercePaymentEntryAuditImpl.resetOriginalValues();

		return commercePaymentEntryAuditImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		commercePaymentEntryAuditId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commercePaymentEntryId = objectInput.readLong();
		amount = (BigDecimal)objectInput.readObject();
		currencyCode = objectInput.readUTF();
		logType = objectInput.readUTF();
		logTypeSettings = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(commercePaymentEntryAuditId);

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

		objectOutput.writeLong(commercePaymentEntryId);
		objectOutput.writeObject(amount);

		if (currencyCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(currencyCode);
		}

		if (logType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(logType);
		}

		if (logTypeSettings == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(logTypeSettings);
		}
	}

	public long mvccVersion;
	public long commercePaymentEntryAuditId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commercePaymentEntryId;
	public BigDecimal amount;
	public String currencyCode;
	public String logType;
	public String logTypeSettings;

}