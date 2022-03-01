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

package com.liferay.account.model.impl;

import com.liferay.account.model.AccountEntry;
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
 * The cache model class for representing AccountEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AccountEntryCacheModel
	implements CacheModel<AccountEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AccountEntryCacheModel)) {
			return false;
		}

		AccountEntryCacheModel accountEntryCacheModel =
			(AccountEntryCacheModel)object;

		if ((accountEntryId == accountEntryCacheModel.accountEntryId) &&
			(mvccVersion == accountEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, accountEntryId);

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
		StringBundler sb = new StringBundler(47);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", accountEntryId=");
		sb.append(accountEntryId);
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
		sb.append(", defaultBillingAddressId=");
		sb.append(defaultBillingAddressId);
		sb.append(", defaultCPaymentMethodKey=");
		sb.append(defaultCPaymentMethodKey);
		sb.append(", defaultDeliveryCTermEntryId=");
		sb.append(defaultDeliveryCTermEntryId);
		sb.append(", defaultPaymentCTermEntryId=");
		sb.append(defaultPaymentCTermEntryId);
		sb.append(", defaultShippingAddressId=");
		sb.append(defaultShippingAddressId);
		sb.append(", parentAccountEntryId=");
		sb.append(parentAccountEntryId);
		sb.append(", description=");
		sb.append(description);
		sb.append(", domains=");
		sb.append(domains);
		sb.append(", emailAddress=");
		sb.append(emailAddress);
		sb.append(", logoId=");
		sb.append(logoId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", taxExemptionCode=");
		sb.append(taxExemptionCode);
		sb.append(", taxIdNumber=");
		sb.append(taxIdNumber);
		sb.append(", type=");
		sb.append(type);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AccountEntry toEntityModel() {
		AccountEntryImpl accountEntryImpl = new AccountEntryImpl();

		accountEntryImpl.setMvccVersion(mvccVersion);

		if (externalReferenceCode == null) {
			accountEntryImpl.setExternalReferenceCode("");
		}
		else {
			accountEntryImpl.setExternalReferenceCode(externalReferenceCode);
		}

		accountEntryImpl.setAccountEntryId(accountEntryId);
		accountEntryImpl.setCompanyId(companyId);
		accountEntryImpl.setUserId(userId);

		if (userName == null) {
			accountEntryImpl.setUserName("");
		}
		else {
			accountEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			accountEntryImpl.setCreateDate(null);
		}
		else {
			accountEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			accountEntryImpl.setModifiedDate(null);
		}
		else {
			accountEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		accountEntryImpl.setDefaultBillingAddressId(defaultBillingAddressId);

		if (defaultCPaymentMethodKey == null) {
			accountEntryImpl.setDefaultCPaymentMethodKey("");
		}
		else {
			accountEntryImpl.setDefaultCPaymentMethodKey(
				defaultCPaymentMethodKey);
		}

		accountEntryImpl.setDefaultDeliveryCTermEntryId(
			defaultDeliveryCTermEntryId);
		accountEntryImpl.setDefaultPaymentCTermEntryId(
			defaultPaymentCTermEntryId);
		accountEntryImpl.setDefaultShippingAddressId(defaultShippingAddressId);
		accountEntryImpl.setParentAccountEntryId(parentAccountEntryId);

		if (description == null) {
			accountEntryImpl.setDescription("");
		}
		else {
			accountEntryImpl.setDescription(description);
		}

		if (domains == null) {
			accountEntryImpl.setDomains("");
		}
		else {
			accountEntryImpl.setDomains(domains);
		}

		if (emailAddress == null) {
			accountEntryImpl.setEmailAddress("");
		}
		else {
			accountEntryImpl.setEmailAddress(emailAddress);
		}

		accountEntryImpl.setLogoId(logoId);

		if (name == null) {
			accountEntryImpl.setName("");
		}
		else {
			accountEntryImpl.setName(name);
		}

		if (taxExemptionCode == null) {
			accountEntryImpl.setTaxExemptionCode("");
		}
		else {
			accountEntryImpl.setTaxExemptionCode(taxExemptionCode);
		}

		if (taxIdNumber == null) {
			accountEntryImpl.setTaxIdNumber("");
		}
		else {
			accountEntryImpl.setTaxIdNumber(taxIdNumber);
		}

		if (type == null) {
			accountEntryImpl.setType("");
		}
		else {
			accountEntryImpl.setType(type);
		}

		accountEntryImpl.setStatus(status);

		accountEntryImpl.resetOriginalValues();

		return accountEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		externalReferenceCode = objectInput.readUTF();

		accountEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		defaultBillingAddressId = objectInput.readLong();
		defaultCPaymentMethodKey = objectInput.readUTF();

		defaultDeliveryCTermEntryId = objectInput.readLong();

		defaultPaymentCTermEntryId = objectInput.readLong();

		defaultShippingAddressId = objectInput.readLong();

		parentAccountEntryId = objectInput.readLong();
		description = objectInput.readUTF();
		domains = objectInput.readUTF();
		emailAddress = objectInput.readUTF();

		logoId = objectInput.readLong();
		name = objectInput.readUTF();
		taxExemptionCode = objectInput.readUTF();
		taxIdNumber = objectInput.readUTF();
		type = objectInput.readUTF();

		status = objectInput.readInt();
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

		objectOutput.writeLong(accountEntryId);

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

		objectOutput.writeLong(defaultBillingAddressId);

		if (defaultCPaymentMethodKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(defaultCPaymentMethodKey);
		}

		objectOutput.writeLong(defaultDeliveryCTermEntryId);

		objectOutput.writeLong(defaultPaymentCTermEntryId);

		objectOutput.writeLong(defaultShippingAddressId);

		objectOutput.writeLong(parentAccountEntryId);

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (domains == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(domains);
		}

		if (emailAddress == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(emailAddress);
		}

		objectOutput.writeLong(logoId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (taxExemptionCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(taxExemptionCode);
		}

		if (taxIdNumber == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(taxIdNumber);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeInt(status);
	}

	public long mvccVersion;
	public String externalReferenceCode;
	public long accountEntryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long defaultBillingAddressId;
	public String defaultCPaymentMethodKey;
	public long defaultDeliveryCTermEntryId;
	public long defaultPaymentCTermEntryId;
	public long defaultShippingAddressId;
	public long parentAccountEntryId;
	public String description;
	public String domains;
	public String emailAddress;
	public long logoId;
	public String name;
	public String taxExemptionCode;
	public String taxIdNumber;
	public String type;
	public int status;

}