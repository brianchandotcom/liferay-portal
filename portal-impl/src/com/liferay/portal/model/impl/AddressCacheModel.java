/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AddressCacheModel)) {
			return false;
		}

		AddressCacheModel addressCacheModel = (AddressCacheModel)object;

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
		StringBundler sb = new StringBundler(57);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
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
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append(", listTypeId=");
		sb.append(listTypeId);
		sb.append(", regionId=");
		sb.append(regionId);
		sb.append(", city=");
		sb.append(city);
		sb.append(", description=");
		sb.append(description);
		sb.append(", latitude=");
		sb.append(latitude);
		sb.append(", longitude=");
		sb.append(longitude);
		sb.append(", mailing=");
		sb.append(mailing);
		sb.append(", name=");
		sb.append(name);
		sb.append(", primary=");
		sb.append(primary);
		sb.append(", street1=");
		sb.append(street1);
		sb.append(", street2=");
		sb.append(street2);
		sb.append(", street3=");
		sb.append(street3);
		sb.append(", validationDate=");
		sb.append(validationDate);
		sb.append(", validationStatus=");
		sb.append(validationStatus);
		sb.append(", zip=");
		sb.append(zip);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Address toEntityModel() {
		AddressImpl addressImpl = new AddressImpl();

		addressImpl.setMvccVersion(mvccVersion);
		addressImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			addressImpl.setUuid("");
		}
		else {
			addressImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			addressImpl.setExternalReferenceCode("");
		}
		else {
			addressImpl.setExternalReferenceCode(externalReferenceCode);
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
		addressImpl.setCountryId(countryId);
		addressImpl.setListTypeId(listTypeId);
		addressImpl.setRegionId(regionId);

		if (city == null) {
			addressImpl.setCity("");
		}
		else {
			addressImpl.setCity(city);
		}

		if (description == null) {
			addressImpl.setDescription("");
		}
		else {
			addressImpl.setDescription(description);
		}

		addressImpl.setLatitude(latitude);
		addressImpl.setLongitude(longitude);
		addressImpl.setMailing(mailing);

		if (name == null) {
			addressImpl.setName("");
		}
		else {
			addressImpl.setName(name);
		}

		addressImpl.setPrimary(primary);

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

		if (validationDate == Long.MIN_VALUE) {
			addressImpl.setValidationDate(null);
		}
		else {
			addressImpl.setValidationDate(new Date(validationDate));
		}

		addressImpl.setValidationStatus(validationStatus);

		if (zip == null) {
			addressImpl.setZip("");
		}
		else {
			addressImpl.setZip(zip);
		}

		addressImpl.resetOriginalValues();

		return addressImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		addressId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		countryId = objectInput.readLong();

		listTypeId = objectInput.readLong();

		regionId = objectInput.readLong();
		city = objectInput.readUTF();
		description = objectInput.readUTF();

		latitude = objectInput.readDouble();

		longitude = objectInput.readDouble();

		mailing = objectInput.readBoolean();
		name = objectInput.readUTF();

		primary = objectInput.readBoolean();
		street1 = objectInput.readUTF();
		street2 = objectInput.readUTF();
		street3 = objectInput.readUTF();
		validationDate = objectInput.readLong();

		validationStatus = objectInput.readInt();
		zip = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
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

		objectOutput.writeLong(countryId);

		objectOutput.writeLong(listTypeId);

		objectOutput.writeLong(regionId);

		if (city == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(city);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeDouble(latitude);

		objectOutput.writeDouble(longitude);

		objectOutput.writeBoolean(mailing);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeBoolean(primary);

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

		objectOutput.writeLong(validationDate);

		objectOutput.writeInt(validationStatus);

		if (zip == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(zip);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public String externalReferenceCode;
	public long addressId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long countryId;
	public long listTypeId;
	public long regionId;
	public String city;
	public String description;
	public double latitude;
	public double longitude;
	public boolean mailing;
	public String name;
	public boolean primary;
	public String street1;
	public String street2;
	public String street3;
	public long validationDate;
	public int validationStatus;
	public String zip;

}