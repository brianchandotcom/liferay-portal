/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.model.impl;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
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
 * The cache model class for representing CommerceShippingFixedOptionRel in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceShippingFixedOptionRelCacheModel
	implements CacheModel<CommerceShippingFixedOptionRel>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceShippingFixedOptionRelCacheModel)) {
			return false;
		}

		CommerceShippingFixedOptionRelCacheModel
			commerceShippingFixedOptionRelCacheModel =
				(CommerceShippingFixedOptionRelCacheModel)object;

		if ((commerceShippingFixedOptionRelId ==
				commerceShippingFixedOptionRelCacheModel.
					commerceShippingFixedOptionRelId) &&
			(mvccVersion ==
				commerceShippingFixedOptionRelCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceShippingFixedOptionRelId);

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
		StringBundler sb = new StringBundler(39);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", commerceShippingFixedOptionRelId=");
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
		sb.append(", commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append(", regionId=");
		sb.append(regionId);
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
		CommerceShippingFixedOptionRelImpl commerceShippingFixedOptionRelImpl =
			new CommerceShippingFixedOptionRelImpl();

		commerceShippingFixedOptionRelImpl.setMvccVersion(mvccVersion);
		commerceShippingFixedOptionRelImpl.setCommerceShippingFixedOptionRelId(
			commerceShippingFixedOptionRelId);
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
			commerceShippingFixedOptionRelImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceShippingFixedOptionRelImpl.setModifiedDate(null);
		}
		else {
			commerceShippingFixedOptionRelImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		commerceShippingFixedOptionRelImpl.setCommerceShippingMethodId(
			commerceShippingMethodId);
		commerceShippingFixedOptionRelImpl.setCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId);
		commerceShippingFixedOptionRelImpl.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
		commerceShippingFixedOptionRelImpl.setCountryId(countryId);
		commerceShippingFixedOptionRelImpl.setRegionId(regionId);

		if (zip == null) {
			commerceShippingFixedOptionRelImpl.setZip("");
		}
		else {
			commerceShippingFixedOptionRelImpl.setZip(zip);
		}

		commerceShippingFixedOptionRelImpl.setWeightFrom(weightFrom);
		commerceShippingFixedOptionRelImpl.setWeightTo(weightTo);
		commerceShippingFixedOptionRelImpl.setFixedPrice(fixedPrice);
		commerceShippingFixedOptionRelImpl.setRateUnitWeightPrice(
			rateUnitWeightPrice);
		commerceShippingFixedOptionRelImpl.setRatePercentage(ratePercentage);

		commerceShippingFixedOptionRelImpl.resetOriginalValues();

		return commerceShippingFixedOptionRelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		commerceShippingFixedOptionRelId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		commerceShippingMethodId = objectInput.readLong();

		commerceShippingFixedOptionId = objectInput.readLong();

		commerceInventoryWarehouseId = objectInput.readLong();

		countryId = objectInput.readLong();

		regionId = objectInput.readLong();
		zip = objectInput.readUTF();

		weightFrom = objectInput.readDouble();

		weightTo = objectInput.readDouble();
		fixedPrice = (BigDecimal)objectInput.readObject();
		rateUnitWeightPrice = (BigDecimal)objectInput.readObject();

		ratePercentage = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

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

		objectOutput.writeLong(commerceInventoryWarehouseId);

		objectOutput.writeLong(countryId);

		objectOutput.writeLong(regionId);

		if (zip == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(zip);
		}

		objectOutput.writeDouble(weightFrom);

		objectOutput.writeDouble(weightTo);
		objectOutput.writeObject(fixedPrice);
		objectOutput.writeObject(rateUnitWeightPrice);

		objectOutput.writeDouble(ratePercentage);
	}

	public long mvccVersion;
	public long commerceShippingFixedOptionRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceShippingMethodId;
	public long commerceShippingFixedOptionId;
	public long commerceInventoryWarehouseId;
	public long countryId;
	public long regionId;
	public String zip;
	public double weightFrom;
	public double weightTo;
	public BigDecimal fixedPrice;
	public BigDecimal rateUnitWeightPrice;
	public double ratePercentage;

}