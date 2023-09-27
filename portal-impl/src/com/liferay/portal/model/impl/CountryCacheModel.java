/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		sb.append(", a2=");
		sb.append(a2);
		sb.append(", a3=");
		sb.append(a3);
		sb.append(", active=");
		sb.append(active);
		sb.append(", billingAllowed=");
		sb.append(billingAllowed);
		sb.append(", groupFilterEnabled=");
		sb.append(groupFilterEnabled);
		sb.append(", idd=");
		sb.append(idd);
		sb.append(", name=");
		sb.append(name);
		sb.append(", number=");
		sb.append(number);
		sb.append(", position=");
		sb.append(position);
		sb.append(", shippingAllowed=");
		sb.append(shippingAllowed);
		sb.append(", subjectToVAT=");
		sb.append(subjectToVAT);
		sb.append(", zipRequired=");
		sb.append(zipRequired);
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

		countryImpl.setActive(active);
		countryImpl.setBillingAllowed(billingAllowed);
		countryImpl.setGroupFilterEnabled(groupFilterEnabled);

		if (idd == null) {
			countryImpl.setIdd("");
		}
		else {
			countryImpl.setIdd(idd);
		}

		if (name == null) {
			countryImpl.setName("");
		}
		else {
			countryImpl.setName(name);
		}

		if (number == null) {
			countryImpl.setNumber("");
		}
		else {
			countryImpl.setNumber(number);
		}

		countryImpl.setPosition(position);
		countryImpl.setShippingAllowed(shippingAllowed);
		countryImpl.setSubjectToVAT(subjectToVAT);
		countryImpl.setZipRequired(zipRequired);

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
		a2 = objectInput.readUTF();
		a3 = objectInput.readUTF();

		active = objectInput.readBoolean();

		billingAllowed = objectInput.readBoolean();

		groupFilterEnabled = objectInput.readBoolean();
		idd = objectInput.readUTF();
		name = objectInput.readUTF();
		number = objectInput.readUTF();

		position = objectInput.readDouble();

		shippingAllowed = objectInput.readBoolean();

		subjectToVAT = objectInput.readBoolean();

		zipRequired = objectInput.readBoolean();
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

		objectOutput.writeBoolean(active);

		objectOutput.writeBoolean(billingAllowed);

		objectOutput.writeBoolean(groupFilterEnabled);

		if (idd == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(idd);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (number == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(number);
		}

		objectOutput.writeDouble(position);

		objectOutput.writeBoolean(shippingAllowed);

		objectOutput.writeBoolean(subjectToVAT);

		objectOutput.writeBoolean(zipRequired);
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
	public String a2;
	public String a3;
	public boolean active;
	public boolean billingAllowed;
	public boolean groupFilterEnabled;
	public String idd;
	public String name;
	public String number;
	public double position;
	public boolean shippingAllowed;
	public boolean subjectToVAT;
	public boolean zipRequired;
	public long lastPublishDate;

}