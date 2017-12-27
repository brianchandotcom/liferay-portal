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

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceShippingFixedOptionRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRel
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionRelCacheModel implements CacheModel<CommerceShippingFixedOptionRel>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceShippingFixedOptionRelCacheModel)) {
			return false;
		}

		CommerceShippingFixedOptionRelCacheModel commerceShippingFixedOptionRelCacheModel =
			(CommerceShippingFixedOptionRelCacheModel)obj;

		if (commerceShippingFixedOptionRelId == commerceShippingFixedOptionRelCacheModel.commerceShippingFixedOptionRelId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceShippingFixedOptionRelId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(37);

		sb.append("{commerceShippingFixedOptionRelId=");
		sb.append(commerceShippingFixedOptionRelId);
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
	public CommerceShippingFixedOptionRel toEntityModel() {
		CommerceShippingFixedOptionRelImpl commerceShippingFixedOptionRelImpl = new CommerceShippingFixedOptionRelImpl();

		commerceShippingFixedOptionRelImpl.setCommerceShippingFixedOptionRelId(commerceShippingFixedOptionRelId);
		commerceShippingFixedOptionRelImpl.setGroupId(groupId);
		commerceShippingFixedOptionRelImpl.setCompanyId(companyId);
		commerceShippingFixedOptionRelImpl.setUserId(userId);

		if (userName == null) {
			commerceShippingFixedOptionRelImpl.setUserName("");
		}
		else {
			commerceShippingFixedOptionRelImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceShippingFixedOptionRelImpl.setCreateDate(null);
		}
		else {
			commerceShippingFixedOptionRelImpl.setCreateDate(new Date(
					createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceShippingFixedOptionRelImpl.setModifiedDate(null);
		}
		else {
			commerceShippingFixedOptionRelImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		commerceShippingFixedOptionRelImpl.setCommerceShippingMethodId(commerceShippingMethodId);
		commerceShippingFixedOptionRelImpl.setCommerceShippingFixedOptionId(commerceShippingFixedOptionId);
		commerceShippingFixedOptionRelImpl.setCommerceWarehouseId(commerceWarehouseId);
		commerceShippingFixedOptionRelImpl.setCommerceCountryId(commerceCountryId);
		commerceShippingFixedOptionRelImpl.setCommerceRegionId(commerceRegionId);

		if (zip == null) {
			commerceShippingFixedOptionRelImpl.setZip("");
		}
		else {
			commerceShippingFixedOptionRelImpl.setZip(zip);
		}

		commerceShippingFixedOptionRelImpl.setWeightFrom(weightFrom);
		commerceShippingFixedOptionRelImpl.setWeightTo(weightTo);
		commerceShippingFixedOptionRelImpl.setFixedPrice(fixedPrice);
		commerceShippingFixedOptionRelImpl.setRateUnitWeightPrice(rateUnitWeightPrice);
		commerceShippingFixedOptionRelImpl.setRatePercentage(ratePercentage);

		commerceShippingFixedOptionRelImpl.resetOriginalValues();

		return commerceShippingFixedOptionRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceShippingFixedOptionRelId = objectInput.readLong();

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
		objectOutput.writeLong(commerceShippingFixedOptionRelId);

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

	public long commerceShippingFixedOptionRelId;
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