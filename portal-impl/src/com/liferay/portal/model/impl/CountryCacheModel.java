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

/**
 * The cache model class for representing Country in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CountryCacheModel
	implements CacheModel<Country>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CountryCacheModel)) {
			return false;
		}

		CountryCacheModel countryCacheModel = (CountryCacheModel)obj;

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
		StringBundler sb = new StringBundler(19);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", countryId=");
		sb.append(countryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", a2=");
		sb.append(a2);
		sb.append(", a3=");
		sb.append(a3);
		sb.append(", number=");
		sb.append(number);
		sb.append(", idd=");
		sb.append(idd);
		sb.append(", zipRequired=");
		sb.append(zipRequired);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Country toEntityModel() {
		CountryImpl countryImpl = new CountryImpl();

		countryImpl.setMvccVersion(mvccVersion);
		countryImpl.setCountryId(countryId);

		if (name == null) {
			countryImpl.setName("");
		}
		else {
			countryImpl.setName(name);
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

		if (number == null) {
			countryImpl.setNumber("");
		}
		else {
			countryImpl.setNumber(number);
		}

		if (idd == null) {
			countryImpl.setIdd("");
		}
		else {
			countryImpl.setIdd(idd);
		}

		countryImpl.setZipRequired(zipRequired);
		countryImpl.setActive(active);

		countryImpl.resetOriginalValues();

		return countryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		countryId = objectInput.readLong();
		name = objectInput.readUTF();
		a2 = objectInput.readUTF();
		a3 = objectInput.readUTF();
		number = objectInput.readUTF();
		idd = objectInput.readUTF();

		zipRequired = objectInput.readBoolean();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(countryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

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

		if (idd == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(idd);
		}

		objectOutput.writeBoolean(zipRequired);

		objectOutput.writeBoolean(active);
	}

	public long mvccVersion;
	public long countryId;
	public String name;
	public String a2;
	public String a3;
	public String number;
	public String idd;
	public boolean zipRequired;
	public boolean active;

}