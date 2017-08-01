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

package com.liferay.commerce.address.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.address.model.CommerceCountry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceCountry in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountry
 * @generated
 */
@ProviderType
public class CommerceCountryCacheModel implements CacheModel<CommerceCountry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCountryCacheModel)) {
			return false;
		}

		CommerceCountryCacheModel commerceCountryCacheModel = (CommerceCountryCacheModel)obj;

		if (commerceCountryId == commerceCountryCacheModel.commerceCountryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceCountryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{commerceCountryId=");
		sb.append(commerceCountryId);
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
		sb.append(", allowsBilling=");
		sb.append(allowsBilling);
		sb.append(", allowsShipping=");
		sb.append(allowsShipping);
		sb.append(", twoLettersISOCode=");
		sb.append(twoLettersISOCode);
		sb.append(", threeLettersISOCode=");
		sb.append(threeLettersISOCode);
		sb.append(", numericISOCode=");
		sb.append(numericISOCode);
		sb.append(", subjectToVAT=");
		sb.append(subjectToVAT);
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", published=");
		sb.append(published);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceCountry toEntityModel() {
		CommerceCountryImpl commerceCountryImpl = new CommerceCountryImpl();

		commerceCountryImpl.setCommerceCountryId(commerceCountryId);
		commerceCountryImpl.setGroupId(groupId);
		commerceCountryImpl.setCompanyId(companyId);
		commerceCountryImpl.setUserId(userId);

		if (userName == null) {
			commerceCountryImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceCountryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceCountryImpl.setCreateDate(null);
		}
		else {
			commerceCountryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceCountryImpl.setModifiedDate(null);
		}
		else {
			commerceCountryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceCountryImpl.setName(StringPool.BLANK);
		}
		else {
			commerceCountryImpl.setName(name);
		}

		commerceCountryImpl.setAllowsBilling(allowsBilling);
		commerceCountryImpl.setAllowsShipping(allowsShipping);

		if (twoLettersISOCode == null) {
			commerceCountryImpl.setTwoLettersISOCode(StringPool.BLANK);
		}
		else {
			commerceCountryImpl.setTwoLettersISOCode(twoLettersISOCode);
		}

		if (threeLettersISOCode == null) {
			commerceCountryImpl.setThreeLettersISOCode(StringPool.BLANK);
		}
		else {
			commerceCountryImpl.setThreeLettersISOCode(threeLettersISOCode);
		}

		commerceCountryImpl.setNumericISOCode(numericISOCode);
		commerceCountryImpl.setSubjectToVAT(subjectToVAT);
		commerceCountryImpl.setPriority(priority);
		commerceCountryImpl.setPublished(published);

		commerceCountryImpl.resetOriginalValues();

		return commerceCountryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		commerceCountryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		allowsBilling = objectInput.readBoolean();

		allowsShipping = objectInput.readBoolean();
		twoLettersISOCode = objectInput.readUTF();
		threeLettersISOCode = objectInput.readUTF();

		numericISOCode = objectInput.readInt();

		subjectToVAT = objectInput.readBoolean();

		priority = objectInput.readDouble();

		published = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(commerceCountryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeBoolean(allowsBilling);

		objectOutput.writeBoolean(allowsShipping);

		if (twoLettersISOCode == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(twoLettersISOCode);
		}

		if (threeLettersISOCode == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(threeLettersISOCode);
		}

		objectOutput.writeInt(numericISOCode);

		objectOutput.writeBoolean(subjectToVAT);

		objectOutput.writeDouble(priority);

		objectOutput.writeBoolean(published);
	}

	public long commerceCountryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public boolean allowsBilling;
	public boolean allowsShipping;
	public String twoLettersISOCode;
	public String threeLettersISOCode;
	public int numericISOCode;
	public boolean subjectToVAT;
	public double priority;
	public boolean published;
}