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

package com.liferay.commerce.tax.engine.fixed.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceTaxFixedRateAddressRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRel
 * @generated
 */
@ProviderType
public class CommerceTaxFixedRateAddressRelCacheModel implements CacheModel<CommerceTaxFixedRateAddressRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceTaxFixedRateAddressRelCacheModel)) {
			return false;
		}

		CommerceTaxFixedRateAddressRelCacheModel commerceTaxFixedRateAddressRelCacheModel =
			(CommerceTaxFixedRateAddressRelCacheModel)obj;

		if (commerceTaxFixedRateAddressRelId == commerceTaxFixedRateAddressRelCacheModel.commerceTaxFixedRateAddressRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceTaxFixedRateAddressRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{commerceTaxFixedRateAddressRelId=");
		sb.append(commerceTaxFixedRateAddressRelId);
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
		sb.append(", commerceTaxMethodId=");
		sb.append(commerceTaxMethodId);
		sb.append(", commerceTaxFixedRateId=");
		sb.append(commerceTaxFixedRateId);
		sb.append(", commerceCountryId=");
		sb.append(commerceCountryId);
		sb.append(", commerceRegionId=");
		sb.append(commerceRegionId);
		sb.append(", zip=");
		sb.append(zip);
		sb.append(", rate=");
		sb.append(rate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceTaxFixedRateAddressRel toEntityModel() {
		CommerceTaxFixedRateAddressRelImpl commerceTaxFixedRateAddressRelImpl = new CommerceTaxFixedRateAddressRelImpl();

		commerceTaxFixedRateAddressRelImpl.setCommerceTaxFixedRateAddressRelId(commerceTaxFixedRateAddressRelId);
		commerceTaxFixedRateAddressRelImpl.setGroupId(groupId);
		commerceTaxFixedRateAddressRelImpl.setCompanyId(companyId);
		commerceTaxFixedRateAddressRelImpl.setUserId(userId);

		if (userName == null) {
			commerceTaxFixedRateAddressRelImpl.setUserName("");
		}
		else {
			commerceTaxFixedRateAddressRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceTaxFixedRateAddressRelImpl.setCreateDate(null);
		}
		else {
			commerceTaxFixedRateAddressRelImpl.setCreateDate(new Date(
					createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceTaxFixedRateAddressRelImpl.setModifiedDate(null);
		}
		else {
			commerceTaxFixedRateAddressRelImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceTaxFixedRateAddressRelImpl.setCommerceTaxMethodId(commerceTaxMethodId);
		commerceTaxFixedRateAddressRelImpl.setCommerceTaxFixedRateId(commerceTaxFixedRateId);
		commerceTaxFixedRateAddressRelImpl.setCommerceCountryId(commerceCountryId);
		commerceTaxFixedRateAddressRelImpl.setCommerceRegionId(commerceRegionId);

		if (zip == null) {
			commerceTaxFixedRateAddressRelImpl.setZip("");
		}
		else {
			commerceTaxFixedRateAddressRelImpl.setZip(zip);
		}

		commerceTaxFixedRateAddressRelImpl.setRate(rate);

		commerceTaxFixedRateAddressRelImpl.resetOriginalValues();

		return commerceTaxFixedRateAddressRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceTaxFixedRateAddressRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceTaxMethodId = objectInput.readLong();

		commerceTaxFixedRateId = objectInput.readLong();

		commerceCountryId = objectInput.readLong();

		commerceRegionId = objectInput.readLong();
		zip = objectInput.readUTF();

		rate = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceTaxFixedRateAddressRelId);

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

		objectOutput.writeLong(commerceTaxMethodId);

		objectOutput.writeLong(commerceTaxFixedRateId);

		objectOutput.writeLong(commerceCountryId);

		objectOutput.writeLong(commerceRegionId);

		if (zip == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(zip);
		}

		objectOutput.writeDouble(rate);
	}

	public long commerceTaxFixedRateAddressRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceTaxMethodId;
	public long commerceTaxFixedRateId;
	public long commerceCountryId;
	public long commerceRegionId;
	public String zip;
	public double rate;
}