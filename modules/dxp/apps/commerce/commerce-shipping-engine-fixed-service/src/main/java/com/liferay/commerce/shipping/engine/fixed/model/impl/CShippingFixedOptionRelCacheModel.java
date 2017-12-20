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

package com.liferay.commerce.shipping.engine.fixed.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CShippingFixedOptionRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CShippingFixedOptionRel
 * @generated
 */
@ProviderType
public class CShippingFixedOptionRelCacheModel implements CacheModel<CShippingFixedOptionRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CShippingFixedOptionRelCacheModel)) {
			return false;
		}

		CShippingFixedOptionRelCacheModel cShippingFixedOptionRelCacheModel = (CShippingFixedOptionRelCacheModel)obj;

		if (CShippingFixedOptionRelId == cShippingFixedOptionRelCacheModel.CShippingFixedOptionRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CShippingFixedOptionRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(37);

		sb.append("{CShippingFixedOptionRelId=");
		sb.append(CShippingFixedOptionRelId);
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
		sb.append(", commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);
		sb.append(", commerceShippingFixedOptionId=");
		sb.append(commerceShippingFixedOptionId);
		sb.append(", commerceWarehouseId=");
		sb.append(commerceWarehouseId);
		sb.append(", commerceCountryId=");
		sb.append(commerceCountryId);
		sb.append(", commerceRegionId=");
		sb.append(commerceRegionId);
		sb.append(", zip=");
		sb.append(zip);
		sb.append(", weightFrom=");
		sb.append(weightFrom);
		sb.append(", weightTo=");
		sb.append(weightTo);
		sb.append(", fixedPrice=");
		sb.append(fixedPrice);
		sb.append(", rateUnitWeightPrice=");
		sb.append(rateUnitWeightPrice);
		sb.append(", ratePercentage=");
		sb.append(ratePercentage);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CShippingFixedOptionRel toEntityModel() {
		CShippingFixedOptionRelImpl cShippingFixedOptionRelImpl = new CShippingFixedOptionRelImpl();

		cShippingFixedOptionRelImpl.setCShippingFixedOptionRelId(CShippingFixedOptionRelId);
		cShippingFixedOptionRelImpl.setGroupId(groupId);
		cShippingFixedOptionRelImpl.setCompanyId(companyId);
		cShippingFixedOptionRelImpl.setUserId(userId);

		if (userName == null) {
			cShippingFixedOptionRelImpl.setUserName("");
		}
		else {
			cShippingFixedOptionRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cShippingFixedOptionRelImpl.setCreateDate(null);
		}
		else {
			cShippingFixedOptionRelImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cShippingFixedOptionRelImpl.setModifiedDate(null);
		}
		else {
			cShippingFixedOptionRelImpl.setModifiedDate(new Date(modifiedDate));
		}

		cShippingFixedOptionRelImpl.setCommerceShippingMethodId(commerceShippingMethodId);
		cShippingFixedOptionRelImpl.setCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
		cShippingFixedOptionRelImpl.setCommerceWarehouseId(commerceWarehouseId);
		cShippingFixedOptionRelImpl.setCommerceCountryId(commerceCountryId);
		cShippingFixedOptionRelImpl.setCommerceRegionId(commerceRegionId);

		if (zip == null) {
			cShippingFixedOptionRelImpl.setZip("");
		}
		else {
			cShippingFixedOptionRelImpl.setZip(zip);
		}

		cShippingFixedOptionRelImpl.setWeightFrom(weightFrom);
		cShippingFixedOptionRelImpl.setWeightTo(weightTo);
		cShippingFixedOptionRelImpl.setFixedPrice(fixedPrice);
		cShippingFixedOptionRelImpl.setRateUnitWeightPrice(rateUnitWeightPrice);
		cShippingFixedOptionRelImpl.setRatePercentage(ratePercentage);

		cShippingFixedOptionRelImpl.resetOriginalValues();

		return cShippingFixedOptionRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		CShippingFixedOptionRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceShippingMethodId = objectInput.readLong();

		commerceShippingFixedOptionId = objectInput.readLong();

		commerceWarehouseId = objectInput.readLong();

		commerceCountryId = objectInput.readLong();

		commerceRegionId = objectInput.readLong();
		zip = objectInput.readUTF();

		weightFrom = objectInput.readDouble();

		weightTo = objectInput.readDouble();

		fixedPrice = objectInput.readDouble();

		rateUnitWeightPrice = objectInput.readDouble();

		ratePercentage = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(CShippingFixedOptionRelId);

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

		objectOutput.writeLong(commerceShippingMethodId);

		objectOutput.writeLong(commerceShippingFixedOptionId);

		objectOutput.writeLong(commerceWarehouseId);

		objectOutput.writeLong(commerceCountryId);

		objectOutput.writeLong(commerceRegionId);

		if (zip == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(zip);
		}

		objectOutput.writeDouble(weightFrom);

		objectOutput.writeDouble(weightTo);

		objectOutput.writeDouble(fixedPrice);

		objectOutput.writeDouble(rateUnitWeightPrice);

		objectOutput.writeDouble(ratePercentage);
	}

	public long CShippingFixedOptionRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceShippingMethodId;
	public long commerceShippingFixedOptionId;
	public long commerceWarehouseId;
	public long commerceCountryId;
	public long commerceRegionId;
	public String zip;
	public double weightFrom;
	public double weightTo;
	public double fixedPrice;
	public double rateUnitWeightPrice;
	public double ratePercentage;
}