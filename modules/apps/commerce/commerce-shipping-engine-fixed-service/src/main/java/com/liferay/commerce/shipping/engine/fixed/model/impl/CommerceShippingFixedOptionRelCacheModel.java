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
		sb.append(", commerceInventoryWarehouseId=");
		sb.append(commerceInventoryWarehouseId);
		sb.append(", commerceShippingFixedOptionId=");
		sb.append(commerceShippingFixedOptionId);
		sb.append(", commerceShippingMethodId=");
		sb.append(commerceShippingMethodId);
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append(", regionId=");
		sb.append(regionId);
		sb.append(", fixedPrice=");
		sb.append(fixedPrice);
		sb.append(", ratePercentage=");
		sb.append(ratePercentage);
		sb.append(", rateUnitWeightPrice=");
		sb.append(rateUnitWeightPrice);
		sb.append(", weightFrom=");
		sb.append(weightFrom);
		sb.append(", weightTo=");
		sb.append(weightTo);
		sb.append(", zip=");
		sb.append(zip);
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

		commerceShippingFixedOptionRelImpl.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
		commerceShippingFixedOptionRelImpl.setCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId);
		commerceShippingFixedOptionRelImpl.setCommerceShippingMethodId(
			commerceShippingMethodId);
		commerceShippingFixedOptionRelImpl.setCountryId(countryId);
		commerceShippingFixedOptionRelImpl.setRegionId(regionId);
		commerceShippingFixedOptionRelImpl.setFixedPrice(fixedPrice);
		commerceShippingFixedOptionRelImpl.setRatePercentage(ratePercentage);
		commerceShippingFixedOptionRelImpl.setRateUnitWeightPrice(
			rateUnitWeightPrice);
		commerceShippingFixedOptionRelImpl.setWeightFrom(weightFrom);
		commerceShippingFixedOptionRelImpl.setWeightTo(weightTo);

		if (zip == null) {
			commerceShippingFixedOptionRelImpl.setZip("");
		}
		else {
			commerceShippingFixedOptionRelImpl.setZip(zip);
		}

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

		commerceInventoryWarehouseId = objectInput.readLong();

		commerceShippingFixedOptionId = objectInput.readLong();

		commerceShippingMethodId = objectInput.readLong();

		countryId = objectInput.readLong();

		regionId = objectInput.readLong();
		fixedPrice = (BigDecimal)objectInput.readObject();

		ratePercentage = objectInput.readDouble();
		rateUnitWeightPrice = (BigDecimal)objectInput.readObject();

		weightFrom = objectInput.readDouble();

		weightTo = objectInput.readDouble();
		zip = objectInput.readUTF();
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

		objectOutput.writeLong(commerceInventoryWarehouseId);

		objectOutput.writeLong(commerceShippingFixedOptionId);

		objectOutput.writeLong(commerceShippingMethodId);

		objectOutput.writeLong(countryId);

		objectOutput.writeLong(regionId);
		objectOutput.writeObject(fixedPrice);

		objectOutput.writeDouble(ratePercentage);
		objectOutput.writeObject(rateUnitWeightPrice);

		objectOutput.writeDouble(weightFrom);

		objectOutput.writeDouble(weightTo);

		if (zip == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(zip);
		}
	}

	public long mvccVersion;
	public long commerceShippingFixedOptionRelId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long commerceInventoryWarehouseId;
	public long commerceShippingFixedOptionId;
	public long commerceShippingMethodId;
	public long countryId;
	public long regionId;
	public BigDecimal fixedPrice;
	public double ratePercentage;
	public BigDecimal rateUnitWeightPrice;
	public double weightFrom;
	public double weightTo;
	public String zip;

}