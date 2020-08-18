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

package com.liferay.commerce.account.model.impl;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceAccount in entity cache.
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceAccountCacheModel
	implements CacheModel<CommerceAccount>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceAccountCacheModel)) {
			return false;
		}

		CommerceAccountCacheModel commerceAccountCacheModel =
			(CommerceAccountCacheModel)object;

		if (commerceAccountId == commerceAccountCacheModel.commerceAccountId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceAccountId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(47);

		sb.append("{externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", commerceAccountId=");
		sb.append(commerceAccountId);
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
		sb.append(", parentCommerceAccountId=");
		sb.append(parentCommerceAccountId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", logoId=");
		sb.append(logoId);
		sb.append(", email=");
		sb.append(email);
		sb.append(", taxId=");
		sb.append(taxId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", active=");
		sb.append(active);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", defaultBillingAddressId=");
		sb.append(defaultBillingAddressId);
		sb.append(", defaultShippingAddressId=");
		sb.append(defaultShippingAddressId);
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
	public CommerceAccount toEntityModel() {
		CommerceAccountImpl commerceAccountImpl = new CommerceAccountImpl();

		if (externalReferenceCode == null) {
			commerceAccountImpl.setExternalReferenceCode("");
		}
		else {
			commerceAccountImpl.setExternalReferenceCode(externalReferenceCode);
		}

		commerceAccountImpl.setCommerceAccountId(commerceAccountId);
		commerceAccountImpl.setCompanyId(companyId);
		commerceAccountImpl.setUserId(userId);

		if (userName == null) {
			commerceAccountImpl.setUserName("");
		}
		else {
			commerceAccountImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceAccountImpl.setCreateDate(null);
		}
		else {
			commerceAccountImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceAccountImpl.setModifiedDate(null);
		}
		else {
			commerceAccountImpl.setModifiedDate(new Date(modifiedDate));
		}

		commerceAccountImpl.setParentCommerceAccountId(parentCommerceAccountId);

		if (name == null) {
			commerceAccountImpl.setName("");
		}
		else {
			commerceAccountImpl.setName(name);
		}

		commerceAccountImpl.setLogoId(logoId);

		if (email == null) {
			commerceAccountImpl.setEmail("");
		}
		else {
			commerceAccountImpl.setEmail(email);
		}

		if (taxId == null) {
			commerceAccountImpl.setTaxId("");
		}
		else {
			commerceAccountImpl.setTaxId(taxId);
		}

		commerceAccountImpl.setType(type);
		commerceAccountImpl.setActive(active);

		if (displayDate == Long.MIN_VALUE) {
			commerceAccountImpl.setDisplayDate(null);
		}
		else {
			commerceAccountImpl.setDisplayDate(new Date(displayDate));
		}

		commerceAccountImpl.setDefaultBillingAddressId(defaultBillingAddressId);
		commerceAccountImpl.setDefaultShippingAddressId(
			defaultShippingAddressId);

		if (expirationDate == Long.MIN_VALUE) {
			commerceAccountImpl.setExpirationDate(null);
		}
		else {
			commerceAccountImpl.setExpirationDate(new Date(expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			commerceAccountImpl.setLastPublishDate(null);
		}
		else {
			commerceAccountImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		commerceAccountImpl.setStatus(status);
		commerceAccountImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			commerceAccountImpl.setStatusByUserName("");
		}
		else {
			commerceAccountImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			commerceAccountImpl.setStatusDate(null);
		}
		else {
			commerceAccountImpl.setStatusDate(new Date(statusDate));
		}

		commerceAccountImpl.resetOriginalValues();

		return commerceAccountImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		externalReferenceCode = objectInput.readUTF();

		commerceAccountId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		parentCommerceAccountId = objectInput.readLong();
		name = objectInput.readUTF();

		logoId = objectInput.readLong();
		email = objectInput.readUTF();
		taxId = objectInput.readUTF();

		type = objectInput.readInt();

		active = objectInput.readBoolean();
		displayDate = objectInput.readLong();

		defaultBillingAddressId = objectInput.readLong();

		defaultShippingAddressId = objectInput.readLong();
		expirationDate = objectInput.readLong();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(commerceAccountId);

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

		objectOutput.writeLong(parentCommerceAccountId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(logoId);

		if (email == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(email);
		}

		if (taxId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(taxId);
		}

		objectOutput.writeInt(type);

		objectOutput.writeBoolean(active);
		objectOutput.writeLong(displayDate);

		objectOutput.writeLong(defaultBillingAddressId);

		objectOutput.writeLong(defaultShippingAddressId);
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

	public String externalReferenceCode;
	public long commerceAccountId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentCommerceAccountId;
	public String name;
	public long logoId;
	public String email;
	public String taxId;
	public int type;
	public boolean active;
	public long displayDate;
	public long defaultBillingAddressId;
	public long defaultShippingAddressId;
	public long expirationDate;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}