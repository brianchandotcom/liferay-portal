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
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Country in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CountryCacheModel
	implements CacheModel<Country>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CountryCacheModel)) {
			return false;
		}

		CountryCacheModel countryCacheModel = (CountryCacheModel)object;

		if ((countryId == countryCacheModel.countryId) &&
			(mvccVersion == countryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, countryId);

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
		StringBundler sb = new StringBundler(45);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", defaultLanguageId=");
		sb.append(defaultLanguageId);
		sb.append(", countryId=");
		sb.append(countryId);
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
		sb.append(", billingAllowed=");
		sb.append(billingAllowed);
		sb.append(", shippingAllowed=");
		sb.append(shippingAllowed);
		sb.append(", a2=");
		sb.append(a2);
		sb.append(", a3=");
		sb.append(a3);
		sb.append(", number=");
		sb.append(number);
		sb.append(", subjectToVAT=");
		sb.append(subjectToVAT);
		sb.append(", idd=");
		sb.append(idd);
		sb.append(", zipRequired=");
		sb.append(zipRequired);
		sb.append(", position=");
		sb.append(position);
		sb.append(", active=");
		sb.append(active);
		sb.append(", groupFilterEnabled=");
		sb.append(groupFilterEnabled);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Country toEntityModel() {
		CountryImpl countryImpl = new CountryImpl();

		countryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			countryImpl.setUuid("");
		}
		else {
			countryImpl.setUuid(uuid);
		}

		if (defaultLanguageId == null) {
			countryImpl.setDefaultLanguageId("");
		}
		else {
			countryImpl.setDefaultLanguageId(defaultLanguageId);
		}

		countryImpl.setCountryId(countryId);
		countryImpl.setCompanyId(companyId);
		countryImpl.setUserId(userId);

		if (userName == null) {
			countryImpl.setUserName("");
		}
		else {
			countryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			countryImpl.setCreateDate(null);
		}
		else {
			countryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			countryImpl.setModifiedDate(null);
		}
		else {
			countryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			countryImpl.setName("");
		}
		else {
			countryImpl.setName(name);
		}

		countryImpl.setBillingAllowed(billingAllowed);
		countryImpl.setShippingAllowed(shippingAllowed);

		if (a2 == null) {
			countryImpl.setA2("");
		}
		else {
			countryImpl.setA2(a2);
		}

		if (a3 == null) {
			countryImpl.setA3("");
		}
		else {
			countryImpl.setA3(a3);
		}

		if (number == null) {
			countryImpl.setNumber("");
		}
		else {
			countryImpl.setNumber(number);
		}

		countryImpl.setSubjectToVAT(subjectToVAT);

		if (idd == null) {
			countryImpl.setIdd("");
		}
		else {
			countryImpl.setIdd(idd);
		}

		countryImpl.setZipRequired(zipRequired);
		countryImpl.setPosition(position);
		countryImpl.setActive(active);
		countryImpl.setGroupFilterEnabled(groupFilterEnabled);

		if (lastPublishDate == Long.MIN_VALUE) {
			countryImpl.setLastPublishDate(null);
		}
		else {
			countryImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		countryImpl.resetOriginalValues();

		return countryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		defaultLanguageId = objectInput.readUTF();

		countryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		billingAllowed = objectInput.readBoolean();

		shippingAllowed = objectInput.readBoolean();
		a2 = objectInput.readUTF();
		a3 = objectInput.readUTF();
		number = objectInput.readUTF();

		subjectToVAT = objectInput.readBoolean();
		idd = objectInput.readUTF();

		zipRequired = objectInput.readBoolean();

		position = objectInput.readDouble();

		active = objectInput.readBoolean();

		groupFilterEnabled = objectInput.readBoolean();
		lastPublishDate = objectInput.readLong();
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

		if (defaultLanguageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(defaultLanguageId);
		}

		objectOutput.writeLong(countryId);

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

		objectOutput.writeBoolean(billingAllowed);

		objectOutput.writeBoolean(shippingAllowed);

		if (a2 == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(a2);
		}

		if (a3 == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(a3);
		}

		if (number == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(number);
		}

		objectOutput.writeBoolean(subjectToVAT);

		if (idd == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(idd);
		}

		objectOutput.writeBoolean(zipRequired);

		objectOutput.writeDouble(position);

		objectOutput.writeBoolean(active);

		objectOutput.writeBoolean(groupFilterEnabled);
		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public String defaultLanguageId;
	public long countryId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public boolean billingAllowed;
	public boolean shippingAllowed;
	public String a2;
	public String a3;
	public String number;
	public boolean subjectToVAT;
	public String idd;
	public boolean zipRequired;
	public double position;
	public boolean active;
	public boolean groupFilterEnabled;
	public long lastPublishDate;

}