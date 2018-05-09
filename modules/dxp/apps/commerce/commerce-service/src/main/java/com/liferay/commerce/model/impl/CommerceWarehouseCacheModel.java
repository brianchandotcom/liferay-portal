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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceWarehouse;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceWarehouse in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouse
 * @generated
 */
@ProviderType
public class CommerceWarehouseCacheModel implements CacheModel<CommerceWarehouse>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceWarehouseCacheModel)) {
			return false;
		}

		CommerceWarehouseCacheModel commerceWarehouseCacheModel = (CommerceWarehouseCacheModel)obj;

		if (commerceWarehouseId == commerceWarehouseCacheModel.commerceWarehouseId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceWarehouseId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(41);

		sb.append("{commerceWarehouseId=");
		sb.append(commerceWarehouseId);
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
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", active=");
		sb.append(active);
		sb.append(", street1=");
		sb.append(street1);
		sb.append(", street2=");
		sb.append(street2);
		sb.append(", street3=");
		sb.append(street3);
		sb.append(", city=");
		sb.append(city);
		sb.append(", zip=");
		sb.append(zip);
		sb.append(", commerceRegionId=");
		sb.append(commerceRegionId);
		sb.append(", commerceCountryId=");
		sb.append(commerceCountryId);
		sb.append(", latitude=");
		sb.append(latitude);
		sb.append(", longitude=");
		sb.append(longitude);
		sb.append(", primary=");
		sb.append(primary);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceWarehouse toEntityModel() {
		CommerceWarehouseImpl commerceWarehouseImpl = new CommerceWarehouseImpl();

		commerceWarehouseImpl.setCommerceWarehouseId(commerceWarehouseId);
		commerceWarehouseImpl.setGroupId(groupId);
		commerceWarehouseImpl.setCompanyId(companyId);
		commerceWarehouseImpl.setUserId(userId);

		if (userName == null) {
			commerceWarehouseImpl.setUserName("");
		}
		else {
			commerceWarehouseImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceWarehouseImpl.setCreateDate(null);
		}
		else {
			commerceWarehouseImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceWarehouseImpl.setModifiedDate(null);
		}
		else {
			commerceWarehouseImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceWarehouseImpl.setName("");
		}
		else {
			commerceWarehouseImpl.setName(name);
		}

		if (description == null) {
			commerceWarehouseImpl.setDescription("");
		}
		else {
			commerceWarehouseImpl.setDescription(description);
		}

		commerceWarehouseImpl.setActive(active);

		if (street1 == null) {
			commerceWarehouseImpl.setStreet1("");
		}
		else {
			commerceWarehouseImpl.setStreet1(street1);
		}

		if (street2 == null) {
			commerceWarehouseImpl.setStreet2("");
		}
		else {
			commerceWarehouseImpl.setStreet2(street2);
		}

		if (street3 == null) {
			commerceWarehouseImpl.setStreet3("");
		}
		else {
			commerceWarehouseImpl.setStreet3(street3);
		}

		if (city == null) {
			commerceWarehouseImpl.setCity("");
		}
		else {
			commerceWarehouseImpl.setCity(city);
		}

		if (zip == null) {
			commerceWarehouseImpl.setZip("");
		}
		else {
			commerceWarehouseImpl.setZip(zip);
		}

		commerceWarehouseImpl.setCommerceRegionId(commerceRegionId);
		commerceWarehouseImpl.setCommerceCountryId(commerceCountryId);
		commerceWarehouseImpl.setLatitude(latitude);
		commerceWarehouseImpl.setLongitude(longitude);
		commerceWarehouseImpl.setPrimary(primary);

		commerceWarehouseImpl.resetOriginalValues();

		return commerceWarehouseImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceWarehouseId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		active = objectInput.readBoolean();
		street1 = objectInput.readUTF();
		street2 = objectInput.readUTF();
		street3 = objectInput.readUTF();
		city = objectInput.readUTF();
		zip = objectInput.readUTF();

		commerceRegionId = objectInput.readLong();

		commerceCountryId = objectInput.readLong();

		latitude = objectInput.readDouble();

		longitude = objectInput.readDouble();

		primary = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceWarehouseId);

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

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeBoolean(active);

		if (street1 == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(street1);
		}

		if (street2 == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(street2);
		}

		if (street3 == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(street3);
		}

		if (city == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(city);
		}

		if (zip == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(zip);
		}

		objectOutput.writeLong(commerceRegionId);

		objectOutput.writeLong(commerceCountryId);

		objectOutput.writeDouble(latitude);

		objectOutput.writeDouble(longitude);

		objectOutput.writeBoolean(primary);
	}

	public long commerceWarehouseId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public boolean active;
	public String street1;
	public String street2;
	public String street3;
	public String city;
	public String zip;
	public long commerceRegionId;
	public long commerceCountryId;
	public double latitude;
	public double longitude;
	public boolean primary;
}