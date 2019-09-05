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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Address in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AddressCacheModel
	implements CacheModel<Address>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AddressCacheModel)) {
			return false;
		}

		AddressCacheModel addressCacheModel = (AddressCacheModel)obj;

		if ((addressId == addressCacheModel.addressId) &&
			(mvccVersion == addressCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, addressId);

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
		StringBundler sb = new StringBundler(41);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", addressId=");
		sb.append(addressId);
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
		sb.append(", regionId=");
		sb.append(regionId);
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append(", typeId=");
		sb.append(typeId);
		sb.append(", mailing=");
		sb.append(mailing);
		sb.append(", primary=");
		sb.append(primary);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Address toEntityModel() {
		AddressImpl addressImpl = new AddressImpl();

		addressImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			addressImpl.setUuid("");
		}
		else {
			addressImpl.setUuid(uuid);
		}

		addressImpl.setAddressId(addressId);
		addressImpl.setCompanyId(companyId);
		addressImpl.setUserId(userId);

		if (userName == null) {
			addressImpl.setUserName("");
		}
		else {
			addressImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			addressImpl.setCreateDate(null);
		}
		else {
			addressImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			addressImpl.setModifiedDate(null);
		}
		else {
			addressImpl.setModifiedDate(new Date(modifiedDate));
		}

		addressImpl.setClassNameId(classNameId);
		addressImpl.setClassPK(classPK);

		if (street1 == null) {
			addressImpl.setStreet1("");
		}
		else {
			addressImpl.setStreet1(street1);
		}

		if (street2 == null) {
			addressImpl.setStreet2("");
		}
		else {
			addressImpl.setStreet2(street2);
		}

		if (street3 == null) {
			addressImpl.setStreet3("");
		}
		else {
			addressImpl.setStreet3(street3);
		}

		if (city == null) {
			addressImpl.setCity("");
		}
		else {
			addressImpl.setCity(city);
		}

		if (zip == null) {
			addressImpl.setZip("");
		}
		else {
			addressImpl.setZip(zip);
		}

		addressImpl.setRegionId(regionId);
		addressImpl.setCountryId(countryId);
		addressImpl.setTypeId(typeId);
		addressImpl.setMailing(mailing);
		addressImpl.setPrimary(primary);

		addressImpl.resetOriginalValues();

		return addressImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		addressId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		street1 = objectInput.readUTF();
		street2 = objectInput.readUTF();
		street3 = objectInput.readUTF();
		city = objectInput.readUTF();
		zip = objectInput.readUTF();

		regionId = objectInput.readLong();

		countryId = objectInput.readLong();

		typeId = objectInput.readLong();

		mailing = objectInput.readBoolean();

		primary = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(addressId);

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

		objectOutput.writeLong(regionId);

		objectOutput.writeLong(countryId);

		objectOutput.writeLong(typeId);

		objectOutput.writeBoolean(mailing);

		objectOutput.writeBoolean(primary);
	}

	public long mvccVersion;
	public String uuid;
	public long addressId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public String street1;
	public String street2;
	public String street3;
	public String city;
	public String zip;
	public long regionId;
	public long countryId;
	public long typeId;
	public boolean mailing;
	public boolean primary;

}